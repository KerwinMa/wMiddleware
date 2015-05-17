/*
 *  LocationPoint.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 03-Jul-2013
 */
public class LocationPoint implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String description;
    private String contact;
    private String type;
    private Float latitude;
    private Float longitude;
    private String distance;

    public static final String ARRIVE = "arrive";
    public static final String FOOD = "food";
    public static final String BAR = "bar";
    public static final String ATTRACTION = "attraction";
    public static final String SHOP = "shop";
    public static final String TOUR = "tour";

    public static final String ARRIVE_PLANE = "plane";
    public static final String ARRIVE_ARRIVE_TRAIN = "train";
    public static final String ARRIVE_CAR= "car";
    public static final String ARRIVE_BUS = "bus";

    /**
     * Creates a new instance of
     * <code>LocationPoint</code> without params.
     */
    public LocationPoint() {
    }

    public LocationPoint(Integer id, String name, String description, String contact, String type,
                         Float latitude, Float longitude, String distance) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.contact = contact;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LocationPoint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", type='" + type + '\'' +
                ", contact='" + contact + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", distance='" + distance + '\'' +
                '}';
    }
}
