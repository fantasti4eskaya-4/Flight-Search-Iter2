package com.flightsearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.util.Collections;

@SpringBootApplication
public class Main {

    public static void main(String[] args){
        SpringApplication springMvcApplication = new SpringApplication(Main.class);
		springMvcApplication.setDefaultProperties(Collections.singletonMap("server.port", "8088"));
		springMvcApplication.run(args);

    }
}
