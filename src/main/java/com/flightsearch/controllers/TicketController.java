package com.flightsearch.controllers;

import com.flightsearch.TicketSearchAmadeusService;
import com.flightsearch.dao.FlightSearchDAO;
import com.flightsearch.data.Ticket;
import com.flightsearch.exeptions.NoTicketThisDataException;
import com.flightsearch.exeptions.WrongInputDataException;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TicketController {

    private TicketSearchAmadeusService ticketSearch;
    private FlightSearchDAO flightSearchDAO;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private List<String> citiesName;

    @Autowired
    public void setTicketSearchAmadeusService(TicketSearchAmadeusService ticketSearchAmadeusService) {
        this.ticketSearch = ticketSearchAmadeusService;
    }

    @Autowired
    public void setFlightSearchDAO(FlightSearchDAO flightSearchDAO) {
        this.flightSearchDAO = flightSearchDAO;
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
        return flightSearchDAO.getCityNames();
    }
}
