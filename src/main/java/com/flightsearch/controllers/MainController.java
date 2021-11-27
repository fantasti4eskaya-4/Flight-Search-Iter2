package com.flightsearch.controllers;

import com.flightsearch.TicketSearchAmadeusService;
import com.flightsearch.utils.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;

@Controller
public class MainController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String indexLoad(){

        return "index.html";
    }
}
