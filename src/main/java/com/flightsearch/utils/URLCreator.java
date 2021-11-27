package com.flightsearch.utils;


import org.apache.http.client.methods.HttpPost;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;

@Component
public class URLCreator {

    public static HttpPost getFlightPriceConnection() throws IOException, ParseException {
        String address = "https://test.api.amadeus.com/v1/shopping/flight-offers/pricing?forceClass=false";
        HttpPost httpPost = new HttpPost(address);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", AmadeusToken.getToken());

        return httpPost;
    }

    public static HttpURLConnection getFlightSearchConnection(String origin, String destination, LocalDate departureDate, LocalDate returnDate) throws IOException, ParseException {
        String address = "https://test.api.amadeus.com/v2/shopping/flight-offers?" +
                "originLocationCode=" + origin +
                "&" +
                "destinationLocationCode=" + destination +
                "&" +
                "departureDate=" + departureDate +
                "&" +
                "currencyCode=RUB" +
                "&" +
                "adults=" + "1" +
                "&" +
                "nonStop=false" +
                "&" +
                "max=10";
        if (returnDate != null) {
            address += "&returnDate=" + returnDate;
        }
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authorization", AmadeusToken.getToken());
        return connection;
    }
}
