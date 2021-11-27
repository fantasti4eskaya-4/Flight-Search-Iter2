package com.flightsearch.utils;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class DataGenerator {
    private static String[] getAirportData(String term){
        int attempts = 0;
        String[] airportData = new String[6];
        while (true) {
            try {
                CloseableHttpClient httpclient = HttpClients.createDefault();
                HttpGet httpget = new HttpGet("http://autocomplete.travelpayouts.com/places2?term=" + URLEncoder.encode(term, StandardCharsets.UTF_8.name()) + "&locale=en&types[]=city");
                HttpResponse httpresponse = httpclient.execute(httpget);

                JSONParser jsonParser = new JSONParser();
                JSONArray root = (JSONArray) jsonParser.parse(new InputStreamReader(httpresponse.getEntity().getContent()));
                JSONObject obj = (JSONObject) root.get(0);

                String airports = (String) obj.get("main_airport_name");
                String nameLocation = (String) obj.get("name");
                String nameCountry = (String) obj.get("country_name");
                JSONObject coordinatesData = (JSONObject) obj.get("coordinates");

                String lon = coordinatesData.get("lon").toString();
                String lat = coordinatesData.get("lat").toString();

                String code = (String) obj.get("code");
                airportData[0] = code;
                airportData[1] = airports;
                airportData[2] = nameLocation;
                airportData[3] = lat;
                airportData[4] = lon;
                airportData[5] = nameCountry;

                break;
            } catch (Exception e) {
                attempts++;
                if (attempts == 20) {
                    break;
                }
                continue;
            }
        }
        return airportData;
    }


    public static String getIATA(String term) {
        return getAirportData(term)[0];
    }

    public static String[] getCoordinates(String term){
        String[] data = getAirportData(term);
        String[] coordinates = new String[2];
        coordinates[0] = data[4]; //Lon
        coordinates[1] = data[3]; //Lat
        return  coordinates;
    }

    public static String getCountryName(String term){
        return getAirportData(term)[5];
    }
}
