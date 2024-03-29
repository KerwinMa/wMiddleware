/*
 *  MealPlan.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 24-ene-2013
 */
public class MealPlan implements Serializable, Comparable<MealPlan> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String ticker;
    private String name;
    private Date dateModification;
    private int order;

    /**
     * Creates a new instance of
     * <code>MealPlan</code> without params.
     */
    public MealPlan() {
    }

    public MealPlan(Integer id, String ticker, String name, Date dateModification) {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.dateModification = dateModification;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    /*
     * This return true if the MealPlan had breakfast
     * TODO: para version 5 solo se filtra por solo alojamiento
     */
    public boolean hasBreakfast() {
        //TODO: Sacar este valor de algun lado, para no cablearlo
        return !this.ticker.contains("SA");
    }

    public boolean hasLunch() {
        //TODO: Sacar este valor de algun lado, para no cablearlo
        return !this.ticker.contains("SA") && !this.ticker.contains("HD");
    }

    public boolean hasDinner() {
        //TODO: Sacar este valor de algun lado, para no cablearlo
        return this.ticker.contains("PC") || this.ticker.contains("TI");
    }

    @Override
    public String toString() {
        return "MealPlan{" + "id=" + id + ", ticker=" + ticker + ", name=" + name
                //              + ", dateModification=" + dateModification
                + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MealPlan other = (MealPlan) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.ticker == null) ? (other.ticker != null) : !this.ticker.equals(other.ticker)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.dateModification != other.dateModification && (this.dateModification == null || !this.dateModification.equals(other.dateModification))) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        return true;
    }


    @Override
    public int compareTo(MealPlan o) {
        return order < o.getOrder() ? -1 : order > o.getOrder() ? 1 : 0;
    }

}
