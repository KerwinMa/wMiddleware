/*
 *  Condition.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 24-ene-2013
 */
public class Condition implements Serializable, Comparable<Condition> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String ticker;
    private String name;
    private String color;
    private Float earlyCharge;   //%
    private Float minimumCharge; //€
    private Date dateModification;
    private String description;
    private int order;
    private String htmlEntrada;
    private String htmlSalida;
    private String htmlCancelaciones;
    private String htmlCondNinos;
    private String htmlMascotas;
    private String htmlGrupos;
    private String htmlInfoAdicional;
    List<PaymentType> paymentTypeList;
    private boolean payFirstNight;

    /**
     * Creates a new instance of
     * <code>Condition</code> without params.
     */
    public Condition() {
    }

    public Condition(Integer id, String ticker, String name, Float earlyCharge, Float minimumCharge, Date dateModification, String description) {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.earlyCharge = earlyCharge;
        this.minimumCharge = minimumCharge;
        this.dateModification = dateModification;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getHtmlEntrada() {
        return htmlEntrada;
    }

    public void setHtmlEntrada(String htmlEntrada) {
        this.htmlEntrada = htmlEntrada;
    }

    public String getHtmlSalida() {
        return htmlSalida;
    }

    public void setHtmlSalida(String htmlSalida) {
        this.htmlSalida = htmlSalida;
    }

    public String getHtmlCancelaciones() {
        return htmlCancelaciones;
    }

    public void setHtmlCancelaciones(String htmlCancelaciones) {
        this.htmlCancelaciones = htmlCancelaciones;
    }

    public String getHtmlCondNinos() {
        return htmlCondNinos;
    }

    public void setHtmlCondNinos(String htmlCondNinos) {
        this.htmlCondNinos = htmlCondNinos;
    }

    public String getHtmlMascotas() {
        return htmlMascotas;
    }

    public void setHtmlMascotas(String htmlMascotas) {
        this.htmlMascotas = htmlMascotas;
    }

    public String getHtmlGrupos() {
        return htmlGrupos;
    }

    public void setHtmlGrupos(String htmlGrupos) {
        this.htmlGrupos = htmlGrupos;
    }

    public String getHtmlInfoAdicional() {
        return htmlInfoAdicional;
    }

    public void setHtmlInfoAdicional(String htmlInfoAdicional) {
        this.htmlInfoAdicional = htmlInfoAdicional;
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

    public Float getEarlyCharge() {
        return earlyCharge;
    }

    public void setEarlyCharge(Float earlyCharge) {
        this.earlyCharge = earlyCharge;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getMinimumCharge() {
        return minimumCharge;
    }

    public void setMinimumCharge(Float minimumCharge) {
        this.minimumCharge = minimumCharge;
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

    public List<PaymentType> getPaymentTypeList() {
        return paymentTypeList;
    }

    public void setPaymentTypeList(List<PaymentType> paymentTypeList) {
        this.paymentTypeList = paymentTypeList;
    }

    public void addPaymentType(PaymentType paymentType) {
        if (paymentTypeList == null) {
            paymentTypeList = new ArrayList<PaymentType>();
        }
        int index = paymentTypeList.indexOf(paymentType);
        if (index < 0) {
            paymentTypeList.add(paymentType);
        }
    }

    public boolean isPayFirstNight() {
        return payFirstNight;
    }

    public void setPayFirstNight(boolean payFirstNight) {
        this.payFirstNight = payFirstNight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Condition)) return false;

        Condition condition = (Condition) o;

        if (order != condition.order) return false;
        if (color != null ? !color.equals(condition.color) : condition.color != null) return false;
        if (dateModification != null ? !dateModification.equals(condition.dateModification) : condition.dateModification != null)
            return false;
        if (description != null ? !description.equals(condition.description) : condition.description != null)
            return false;
        if (earlyCharge != null ? !earlyCharge.equals(condition.earlyCharge) : condition.earlyCharge != null)
            return false;
        if (htmlCancelaciones != null ? !htmlCancelaciones.equals(condition.htmlCancelaciones) : condition.htmlCancelaciones != null)
            return false;
        if (htmlCondNinos != null ? !htmlCondNinos.equals(condition.htmlCondNinos) : condition.htmlCondNinos != null)
            return false;
        if (htmlEntrada != null ? !htmlEntrada.equals(condition.htmlEntrada) : condition.htmlEntrada != null)
            return false;
        if (htmlGrupos != null ? !htmlGrupos.equals(condition.htmlGrupos) : condition.htmlGrupos != null) return false;
        if (htmlInfoAdicional != null ? !htmlInfoAdicional.equals(condition.htmlInfoAdicional) : condition.htmlInfoAdicional != null)
            return false;
        if (htmlMascotas != null ? !htmlMascotas.equals(condition.htmlMascotas) : condition.htmlMascotas != null)
            return false;
        if (htmlSalida != null ? !htmlSalida.equals(condition.htmlSalida) : condition.htmlSalida != null) return false;
        if (id != null ? !id.equals(condition.id) : condition.id != null) return false;
        if (minimumCharge != null ? !minimumCharge.equals(condition.minimumCharge) : condition.minimumCharge != null)
            return false;
        if (name != null ? !name.equals(condition.name) : condition.name != null) return false;
        if (ticker != null ? !ticker.equals(condition.ticker) : condition.ticker != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", name='" + name + '\'' +
                ", color='" + color + '\'' +
                ", earlyCharge=" + earlyCharge +
                ", minimumCharge=" + minimumCharge +
                ", dateModification=" + dateModification +
                ", description='" + description + '\'' +
                ", order=" + order +
                ", paymentTypeList='" + paymentTypeList + '\'' +
                ", htmlEntrada='" + htmlEntrada + '\'' +
                ", htmlSalida='" + htmlSalida + '\'' +
                ", htmlCancelaciones='" + htmlCancelaciones + '\'' +
                ", htmlCondNinos='" + htmlCondNinos + '\'' +
                ", htmlMascotas='" + htmlMascotas + '\'' +
                ", htmlGrupos='" + htmlGrupos + '\'' +
                ", htmlInfoAdicional='" + htmlInfoAdicional + '\'' +
                ", payFirstNight='" + payFirstNight + '\'' +
                '}';
    }

    @Override
    public int compareTo(Condition o) {
        return order < o.getOrder() ? -1 : order > o.getOrder() ? 1 : 0;
    }
}
