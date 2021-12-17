package com.flightsearch.data;

import javax.persistence.*;

@Entity
@Table(name = "Cities")
public class Cities {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "country_code")
    private String countryCode;

    @Column(name = "city_code")
    private String cityCode;

    @Column(name = "city_name")
    private String cityName;

    public String getCountryCode() {
        return countryCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public String getCityName() {
        return cityName;
    }
}
