package com.flightsearch;

import com.flightsearch.data.Ticket;
import com.flightsearch.exeptions.NoTicketThisDataException;
import com.flightsearch.exeptions.WrongInputDataException;
import com.flightsearch.utils.DataGenerator;
import com.flightsearch.utils.TicketParser;
import com.flightsearch.utils.URLCreator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class TicketSearchAmadeusService {

    public List<Ticket[]> getOffers(String origin, String destination, LocalDate departureDate, LocalDate returnDate) throws IOException, ParseException, NoTicketThisDataException, WrongInputDataException {
        String originIATA = DataGenerator.getIATA(origin);
        String departureIATA = DataGenerator.getIATA(destination);

        HttpURLConnection connection = URLCreator.getFlightSearchConnection(originIATA, departureIATA, departureDate, returnDate);
        InputStreamReader reader = new InputStreamReader(connection.getInputStream());

        JSONParser jsonParser = new JSONParser();
        JSONObject root = (JSONObject) jsonParser.parse(reader);
        JSONArray data = (JSONArray) root.get("data");

        JSONArray flightOffersSearch = new JSONArray();
        for (int i = 0; i < data.size(); i++) {
            System.out.println(data.get(i));
            flightOffersSearch.add(data.get(i));
            if (flightOffersSearch.size() == 12)
                break;
        }
        if (flightOffersSearch.size() == 0) {
            throw new NoTicketThisDataException();
        }
        JSONObject pricePostBody = new JSONObject();
        JSONObject dataPrice = new JSONObject();
        dataPrice.put("type", "flight-offers-pricing");
        dataPrice.put("flightOffers", flightOffersSearch);
        pricePostBody.put("data", dataPrice);

        /*CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = URLCreator.getFlightPriceConnection();
        StringEntity entity = new StringEntity(pricePostBody.toString());
        httpPost.setEntity(entity);
        CloseableHttpResponse response = client.execute(httpPost);
        JSONObject priceRoot = (JSONObject) jsonParser.parse(new InputStreamReader(response.getEntity().getContent()));
        response.close();
        client.close();*/

        JSONObject priceRootData = (JSONObject) pricePostBody.get("data");
        JSONArray flightOffers = (JSONArray) priceRootData.get("flightOffers");

        System.out.println("__");
        System.out.println(flightOffers);
        List<Ticket[]> ticketList = new ArrayList<>();
        for (int i = 0; i < flightOffers.size(); i++) {
            JSONObject obj = (JSONObject) flightOffers.get(i);
            Ticket[] ticket = TicketParser.getTickets(obj);
            boolean isAdded = true;
            for (Ticket[] tick : ticketList) {
                if (tick[0].equals(ticket[0])) {
                    if (ticket[1] != null) {
                        if (tick[1].equals(ticket[1])) {
                            isAdded = false;
                            break;
                        }
                    } else {
                        isAdded = false;
                        break;
                    }
                }
            }
            if (isAdded) {
                ticket[0].setArrivalCountry(DataGenerator.getCountryName(destination));
                ticket[0].setDepartureCountry(DataGenerator.getCountryName(origin));
                if (ticket[1] != null) {
                    ticket[1].setArrivalCountry(DataGenerator.getCountryName(origin));
                    ticket[1].setDepartureCountry(DataGenerator.getCountryName(destination));
                }
                ticketList.add(ticket);
            }
        }

        if (ticketList.size() == 0) {
            throw new WrongInputDataException();
        }

        return ticketList;
    }
}
