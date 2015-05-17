package com.witbooking.middleware.integration.google.model;

/**
 * Bid.java
 * User: jose
 * Date: 5/06/14
 * Time: 15:56
 */
public class Bid {
    private String hotelTicker;
    private boolean active;
    private float bid;

    public Bid(String hotelTicker, boolean active, float bid) {
        this.hotelTicker = hotelTicker;
        this.active = active;
        this.bid = bid;
    }

    public String getHotelTicker() {
        return hotelTicker;
    }

    public boolean isActive() {
        return active;
    }

    public float getBid() {
        return bid;
    }
}