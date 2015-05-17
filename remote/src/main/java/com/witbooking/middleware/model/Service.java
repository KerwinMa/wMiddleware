/*
 *  Service.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.witbooking.middleware.model.values.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 29-ene-2013
 */
public class Service implements Serializable, DataValueHolder, Comparable<Service> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String ticker;
    private String name;
    private String description;
    private byte type;//0=habitacion, 1=persona, 2=unidad
    private boolean daily;
    private int maxUnits;
    private boolean active = true;
    private boolean obligatory;
    private int order;
    private String promoCode;
    private Date startValidPeriod;
    private Date endValidPeriod;
    private RateDataValue rate;
    private LockDataValue lock;
    //These attributes should be eliminated
    private StayDataValue minStay;
    private StayDataValue maxStay;
    private NoticeDataValue minNotice;
    private NoticeDataValue maxNotice;
    private List<Media> media;

    //TODO Crear funcion para pasar a JSON que reciba "locale": "spa",
//      "computedprice": 100,
//      "ftdcomputedprice": "100.00",
//      "mnumselect": "1"

    /**
     * Creates a new instance of
     * <code>Service</code> without params.
     */
    public Service() {
    }


    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
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

    public int getMaxUnits() {
        return maxUnits;
    }

    public void setMaxUnits(int maxUnits) {
        this.maxUnits = maxUnits;
    }

    /**
     * 0=habitacion 1=persona 2=unidad
     */
    public byte getType() {
        return type;
    }

    /**
     * 0=habitacion 1=persona 2=unidad
     */
    public void setType(byte type) {
        this.type = type;
    }

    public boolean isDaily() {
        return daily;
    }

    public void setDaily(boolean daily) {
        this.daily = daily;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public RateDataValue getRate() {
        return rate;
    }

    public void setRate(RateDataValue rate) {
        this.rate = rate;
    }

    public AvailabilityDataValue getAvailability() {
        throw new UnsupportedOperationException("The Service Object '" + ticker +
                "' Doesn't have Availability DataValue.");
    }

    public void setAvailability(AvailabilityDataValue availability) {
        throw new UnsupportedOperationException("The Service Object '" + ticker +
                "' Doesn't have Availability DataValue.");
    }

    public Date getStartValidPeriod() {
        return startValidPeriod;
    }

    public void setStartValidPeriod(Date startValidPeriod) {
        this.startValidPeriod = startValidPeriod;
    }

    public Date getEndValidPeriod() {
        return endValidPeriod;
    }

    public void setEndValidPeriod(Date endValidPeriod) {
        this.endValidPeriod = endValidPeriod;
    }

    public LockDataValue getLock() {
        return lock;
    }

    public void setLock(LockDataValue lock) {
        this.lock = lock;
    }

    public StayDataValue getMinStay() {
        return minStay;
    }

    public void setMinStay(StayDataValue minStay) {
        this.minStay = minStay;
    }

    public StayDataValue getMaxStay() {
        return maxStay;
    }

    public void setMaxStay(StayDataValue maxStay) {
        this.maxStay = maxStay;
    }

    public NoticeDataValue getMinNotice() {
        return minNotice;
    }

    public void setMinNotice(NoticeDataValue minNotice) {
        this.minNotice = minNotice;
    }

    public NoticeDataValue getMaxNotice() {
        return maxNotice;
    }

    public void setMaxNotice(NoticeDataValue maxNotice) {
        this.maxNotice = maxNotice;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public boolean isObligatory() {
        return obligatory;
    }

    public void setObligatory(boolean obligatory) {
        this.obligatory = obligatory;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    @Override
    public String toString() {
        return "Service{" + "id=" + id + ", ticker=" + ticker + ", name=" + name
//                + ", description=" + description
                + ", type=" + type + ", daily=" + daily + ", maxUnits=" + maxUnits + ", active=" + active + ", order=" + order
                + ", startValidPeriod=" + startValidPeriod + ", endValidPeriod=" + endValidPeriod
//                + ", rate=" + rate + ", lock=" + lock
//              + ", minStay=" + minStay + ", maxStay=" + maxStay + ", minNotice=" + minNotice + ", maxNotice=" + maxNotice
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
        final Service other = (Service) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.ticker == null) ? (other.ticker != null) : !this.ticker.equals(other.ticker)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if (this.type != other.type) {
            return false;
        }
        if (this.daily != other.daily) {
            return false;
        }
        if (this.maxUnits != other.maxUnits) {
            return false;
        }
        if (this.active != other.active) {
            return false;
        }
        if (this.startValidPeriod != other.startValidPeriod && (this.startValidPeriod == null || !this.startValidPeriod.equals(other.startValidPeriod))) {
            return false;
        }
        if (this.endValidPeriod != other.endValidPeriod && (this.endValidPeriod == null || !this.endValidPeriod.equals(other.endValidPeriod))) {
            return false;
        }
        /*
        if (this.rate != other.rate && (this.rate == null || !this.rate.equals(other.rate))) {
            System.out.println("Rate differentes");
            return false;
        }
        if (this.lock != other.lock && (this.lock == null || !this.lock.equals(other.lock))) {
            System.out.println("Lock differentes");
            return false;
        }
        if (this.minStay != other.minStay && (this.minStay == null || !this.minStay.equals(other.minStay))) {
            System.out.println("minStay differentes");
            return false;
        }
        if (this.maxStay != other.maxStay && (this.maxStay == null || !this.maxStay.equals(other.maxStay))) {
            System.out.println("maxStay differentes");
            return false;
        }
        if (this.minNotice != other.minNotice && (this.minNotice == null || !this.minNotice.equals(other.minNotice))) {
            System.out.println("minNotice differentes");
            return false;
        }
        if (this.maxNotice != other.maxNotice && (this.maxNotice == null || !this.maxNotice.equals(other.maxNotice))) {
            System.out.println("maxNotice differentes");
            return false;
        }
        System.out.println("Salio con true");
        */
        return true;
    }


    @Override
    public int compareTo(Service o) {
        return order < o.getOrder() ? -1 : order > o.getOrder() ? 1 : 0;
    }

}
