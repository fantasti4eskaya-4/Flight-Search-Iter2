package com.flightsearch.data;

import java.util.Objects;

public class Ticket {

    private String buyLink;

    private String arrival;
    private String departure;

    private String arrivalCountry;
    private String departureCountry;

    private String arrivalDate;
    private String arrivalHour;

    private String departureDate;
    private String departureHour;

    private String price;
    private String carrier;

    private String originLat;
    private String originLon;

    private String destinationLat;
    private String destinationLon;

    public String getDestinationLat() {
        return destinationLat;
    }

    public void setDestinationLat(String destinationLat) {
        this.destinationLat = destinationLat;
    }

    public String getDestinationLon() {
        return destinationLon;
    }

    public void setDestinationLon(String destinationLon) {
        this.destinationLon = destinationLon;
    }

    public String getOriginLat() {
        return originLat;
    }

    public void setOriginLat(String originLat) {
        this.originLat = originLat;
    }

    public String getOriginLon() {
        return originLon;
    }

    public void setOriginLon(String originLon) {
        this.originLon = originLon;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    public String getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(String arrivalDate) {
        this.arrivalDate = arrivalDate;
    }


    public String getArrivalHour() {
        return arrivalHour;
    }

    public void setArrivalHour(String arrivalHour) {
        this.arrivalHour = arrivalHour;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(String departureDate) {
        this.departureDate = departureDate;
    }

    public String getDepartureHour() {
        return departureHour;
    }

    public void setDepartureHour(String departureHour) {
        this.departureHour = departureHour;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrivalCountry() {
        return arrivalCountry;
    }

    public void setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(getBuyLink(), ticket.getBuyLink()) &&
                Objects.equals(getArrival(), ticket.getArrival()) &&
                Objects.equals(getDeparture(), ticket.getDeparture()) &&
                Objects.equals(getArrivalDate(), ticket.getArrivalDate()) &&
                Objects.equals(getArrivalHour(), ticket.getArrivalHour()) &&
                Objects.equals(getDepartureDate(), ticket.getDepartureDate()) &&
                Objects.equals(getDepartureHour(), ticket.getDepartureHour()) &&
                Objects.equals(getPrice(), ticket.getPrice()) &&
                Objects.equals(getCarrier(), ticket.getCarrier()) &&
                Objects.equals(getOriginLat(), ticket.getOriginLat()) &&
                Objects.equals(getOriginLon(), ticket.getOriginLon()) &&
                Objects.equals(getDestinationLat(), ticket.getDestinationLat()) &&
                Objects.equals(getDestinationLon(), ticket.getDestinationLon());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBuyLink(), getArrival(), getDeparture(), getArrivalDate(), getArrivalHour(), getDepartureDate(), getDepartureHour(), getPrice(), getCarrier(), getOriginLat(), getOriginLon(), getDestinationLat(), getDestinationLon());
    }

    @Override
    public String toString() {
        String ticket = "Price: " + price + ", Carrier: " + carrier + "\n" + departure + " (" + departureCountry + ") " + " (" + departureDate + ": " + departureHour + ", lon: " + originLon + ", lat: " + originLat + ") -> " + arrival + " (" + arrivalCountry + ") " + " (" + arrivalDate + ": " + arrivalHour + ", lon: " + destinationLon + ", lat: " + destinationLat + ")";
        if (buyLink != null)
            ticket += "\n" + buyLink;
        return ticket;
    }
}
