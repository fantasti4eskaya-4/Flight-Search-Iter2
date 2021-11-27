package com.flightsearch.utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

@Repository
public class AmadeusToken {
    public static String getToken() throws IOException, ParseException {
        String address = "https://test.api.amadeus.com/v1/security/oauth2/token";
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
        String reqBody = "grant_type=client_credentials" +
                "&" +
                "client_id=9FOMnGacnyCxYOgkGgsyzJ6fiGK2Kiur" +
                "&" +
                "client_secret=TUZABzQ01qPXEL9a";
        writer.write(reqBody);
        writer.close();

        InputStreamReader reader = new InputStreamReader(connection.getInputStream());

        JSONParser jsonParser = new JSONParser();
        JSONObject root = (JSONObject) jsonParser.parse(reader);

        String header = root.get("token_type") + " " + root.get("access_token");
        return header;
    }
}
