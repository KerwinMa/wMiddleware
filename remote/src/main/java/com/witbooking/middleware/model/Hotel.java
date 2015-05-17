/*
 *  Hotel.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.google.common.base.Objects;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Properties;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 15-may-2013
 */
public class Hotel extends Establishment {

    private static final Logger logger = Logger.getLogger(Hotel.class);

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private String address;
    private String city;
    private String zone;
    private String country;
    private String emailReservation;
    private Float latitude;
    private Float longitude;
    private byte valuation;//stars
    //Representa el tipo
    private HotelType type;


    /**
     * Creates a new instance of
     * <code>Hotel</code> without params.
     */
    public Hotel() {
        super();
    }

    protected Hotel(final Establishment other) {
        super(other);
        logger.debug("Hotel : Establishment");
    }

    protected Hotel(final Hotel other) {
        super(other);
        logger.debug("Hotel : Hotel");
//        super(other.id, other.ticker, other.name, other.description, other.phone, other.emailAdmin,other.active, other.configuration);
        if (other != null) {
            this.address = other.address;
            this.city = other.city;
            this.zone = other.zone;
            this.country = other.country;
            this.emailReservation = other.emailReservation;
            this.latitude = other.latitude;
            this.longitude = other.longitude;
            this.valuation = other.valuation;
        }
    }

    public Hotel(String address, String city, String zone, String country, String emailReservation,
                 Float latitude, Float longitude, byte valuation, Integer id, String ticker, String name,
                 String description, String phone, String emailAdmin, boolean active, Properties configuration) {
        super(id, ticker, name, description, phone, emailAdmin, active, configuration);
        this.address = address;
        this.city = city;
        this.zone = zone;
        this.country = country;
        this.emailReservation = emailReservation;
        this.latitude = latitude;
        this.longitude = longitude;
        this.valuation = valuation;
    }

    public HotelType getType() {
        return type;
    }

    public void setType(HotelType type) {
        this.type = type;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmailReservation() {
        return emailReservation;
    }

    public void setEmailReservation(String emailReservation) {
        this.emailReservation = emailReservation;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public byte getValuation() {
        return valuation;
    }

    public void setValuation(byte valuation) {
        this.valuation = valuation;
    }

    public List<Language> getLanguages() {
        return visualRepresentation != null ? visualRepresentation.getLanguages() : null;
    }

    public List<Currency> getCurrencies() {
        return visualRepresentation != null ? visualRepresentation.getCurrencies() : null;
    }

    @Override
    public String toString() {
        return super.toString() + Objects.toStringHelper(this)
                .add("address", address)
                .add("city", city)
                .add("zone", zone)
                .add("country", country)
                .add("emailReservation", emailReservation)
                .add("latitude", latitude)
                .add("longitude", longitude)
                .add("valuation", valuation)
                .toString();
    }
}
