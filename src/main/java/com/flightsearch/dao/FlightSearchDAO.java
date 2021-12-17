package com.flightsearch.dao;

import com.flightsearch.HibernateUtil;
import com.flightsearch.data.Cities;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FlightSearchDAO {

    public List<String> getCityNames(){
        Session session = HibernateUtil.getSession();
        List<String> cities = session.createQuery("select l.cityName from Cities l").list();
        return cities;
    }

}
