package com.flightsearch.controllers;

import com.flightsearch.TicketSearchAmadeusService;
import com.flightsearch.data.Ticket;
import com.flightsearch.exeptions.NoTicketThisDataException;
import com.flightsearch.exeptions.WrongInputDataException;
import com.flightsearch.utils.DataGenerator;
import org.apache.tomcat.jni.Local;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TicketController {

    private TicketSearchAmadeusService ticketSearch;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private List<String> citiesName;

    @Autowired
    public void setTicketSearchAmadeusService(TicketSearchAmadeusService ticketSearchAmadeusService) {
        this.ticketSearch = ticketSearchAmadeusService;
    }

    @RequestMapping(value = "/find_tickets", method = RequestMethod.POST)
    public List<Ticket[]> getTickets(@RequestBody JSONObject flightData) throws IOException, ParseException, NoTicketThisDataException, WrongInputDataException {
        System.out.println(flightData);

        String originIATA = (String) flightData.get("origin");
        LocalDate originDate = LocalDate.parse((String) flightData.get("originDate"), formatter);
        String departureIATA = (String) flightData.get("destination");
        LocalDate destinationDate = null;
        if (!"".equals(flightData.get("destinationDate")))
            destinationDate = LocalDate.parse((String) flightData.get("destinationDate"), formatter);

        return ticketSearch.getOffers(originIATA, departureIATA, originDate, destinationDate);
    }

    @RequestMapping(value = "/load_cities", method = RequestMethod.POST)
    public List<String> getCitiesNames() throws IOException, ParseException {
        if (citiesName == null)
            citiesName = new ArrayList<>();
        if (citiesName.size() == 0) {
            System.out.println("Start loading cities data");
            URL url = new URL("http://api.travelpayouts.com/data/ru/cities.json");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();

            JSONParser jsonParser = new JSONParser();
            JSONArray root = (JSONArray) jsonParser.parse(new InputStreamReader(inputStream));

            for (int i = 0; i < root.size(); i++) {
                JSONObject cityData = (JSONObject) root.get(i);
                String name = (String) cityData.get("name");
                citiesName.add(name);
            }
        }
        return citiesName;
    }
}
