package com.flightsearch.utils;

import com.flightsearch.data.Ticket;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class TicketParser {

    public static Ticket[] getTickets(JSONObject root) {

        DataGenerator dataGenerator = new DataGenerator();

        Ticket[] tickets = new Ticket[2];
        JSONObject priceData = (JSONObject) root.get("price");
        String price = (String) priceData.get("total");

        JSONArray itineraries = (JSONArray) root.get("itineraries");
        for (int i = 0; i < itineraries.size(); i++) {
            JSONObject segmentsData = (JSONObject) itineraries.get(i);
            JSONArray segments = (JSONArray) segmentsData.get("segments");
            for (int j = 0; j < segments.size(); j++) {
                JSONObject obj = (JSONObject) segments.get(j);
                String carrierCode = (String) obj.get("carrierCode");

                JSONObject arrival = (JSONObject) obj.get("arrival");
                JSONObject departure = (JSONObject) obj.get("departure");

                String arrivalData = (String) arrival.get("at");
                String departureData = (String) departure.get("at");

                String arrivalIata = (String) arrival.get("iataCode");
                String departureIata = (String) departure.get("iataCode");

                Ticket ticket = new Ticket();

                ticket.setDeparture(departureIata);
                ticket.setArrival(arrivalIata);
                ticket.setCarrier(carrierCode);

                ticket.setArrivalDate(arrivalData.split("T")[0]);
                ticket.setDepartureDate(departureData.split("T")[0]);
                ticket.setArrivalHour(arrivalData.split("T")[1]);
                ticket.setDepartureHour(departureData.split("T")[1]);
                ticket.setPrice(price);
                ticket.setOriginLon(dataGenerator.getCoordinates(departureIata)[0]);
                ticket.setOriginLat(dataGenerator.getCoordinates(departureIata)[1]);
                ticket.setDestinationLon(dataGenerator.getCoordinates(arrivalIata)[0]);
                ticket.setDestinationLat(dataGenerator.getCoordinates(arrivalIata)[1]);

                if (i == 0) {
                    tickets[0] = ticket;
                } else {
                    tickets[1] = ticket;
                }
            }
        }

        return tickets;
    }
}