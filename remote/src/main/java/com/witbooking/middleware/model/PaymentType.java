/*
 *  PaymentTypes.java
 * 
 * Copyright(c) 2014 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.io.Serializable;
import java.util.Date;

/**
 * PaymentTypes
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 27-agu-2013
 */
public class PaymentType implements Serializable, Comparable<PaymentType> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String ticker;
    private String name;
    private String description;
    private Float paymentPercentage;   //%
    private boolean advancePayment;
    private boolean active;
    private Date dateCreation;
    private Date dateModification;
    private int order;


    /**
     * Creates a new instance of
     * <code>Condition</code> without params.
     */
    public PaymentType() {
    }

    public PaymentType(Integer id, String ticker, String name, String description, Float paymentPercentage, boolean advancePayment, boolean active, Date dateCreation, Date dateModification, int order) {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.description = description;
        this.paymentPercentage = paymentPercentage;
        this.advancePayment = advancePayment;
        this.active = active;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.order = order;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getPaymentPercentage() {
        return paymentPercentage;
    }

    public void setPaymentPercentage(Float paymentPercentage) {
        this.paymentPercentage = paymentPercentage;
    }

    public boolean isAdvancePayment() {
        return advancePayment;
    }

    public void setAdvancePayment(boolean advancePayment) {
        this.advancePayment = advancePayment;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
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

    @Override
    public int compareTo(PaymentType o) {
        return order < o.getOrder() ? -1 : order > o.getOrder() ? 1 : 0;
    }
}
