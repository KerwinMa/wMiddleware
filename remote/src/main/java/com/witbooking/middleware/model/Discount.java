/*
 *  Discount.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.witbooking.middleware.model.values.*;
import com.witbooking.middleware.utils.DateUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 07-mar-2013
 */
public class Discount implements Serializable, DataValueHolder, Comparable<Discount> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String ticker;
    private String name;
    private String description;
    private float reduction;
    private boolean percentage = true;//True=percentage, False=price
    private LockDataValue lock;
    private Date startValidPeriod;
    private Date endValidPeriod;
    private String promoCode;
    private boolean active = true;
    private int order;
    //These attributes should be eliminated
    private Date startContractPeriod;
    private Date endContractPeriod;
    private StayDataValue minStay;
    private StayDataValue maxStay;
    private NoticeDataValue minNotice;
    private NoticeDataValue maxNotice;
    private List<Media> media;

    /**
     * Creates a new instance of
     * <code>Discount</code> without params.
     */
    public Discount() {
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

    public Float getReduction() {
        return reduction;
    }

    public void setReduction(Float reduction) {
        if (reduction != null)
            this.reduction = reduction;
        else
            this.reduction = 0f;
    }

    public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }

    public LockDataValue getLock() {
        return lock;
    }

    public void setLock(LockDataValue lock) {
        this.lock = lock;
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

    public Date getStartContractPeriod() {
        return startContractPeriod;
    }

    public void setStartContractPeriod(Date startContractPeriod) {
        this.startContractPeriod = startContractPeriod;
    }

    public Date getEndContractPeriod() {
        return endContractPeriod;
    }

    public void setEndContractPeriod(Date endContractPeriod) {
        this.endContractPeriod = endContractPeriod;
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

    public float getApplicableDiscount(Date date) {
        if (DateUtil.dateBetweenDaysRange(date, startValidPeriod, endValidPeriod)) {
            return this.reduction;
        }
        return 0;
    }

    public boolean isContractible(Date date) {
        if (date == null) {
            return true;
        }
        Date dateCompare = DateUtil.toBeginningOfTheDay(date);
        if (startContractPeriod != null && dateCompare.before(startContractPeriod)) {
            return false;
        }
        if (endContractPeriod != null && dateCompare.after(endContractPeriod)) {
            return false;
        }
        return true;
    }

    public float getApplicableDiscount(Date date, String promoCodeGiven) {
        if (promoCode == null || promoCode.trim().isEmpty() ||
                (promoCodeGiven != null &&
                        !Collections.disjoint(Arrays.asList(promoCode.replace(" ", "").split(",")),
                                Arrays.asList(promoCodeGiven.replace(" ", "").split(","))))) {
            return getApplicableDiscount(date);
        }
        return 0;
    }

    public float getApplicableDiscountWithoutPromoCode(Date date) {
        if (promoCode == null || promoCode.trim().isEmpty()) {
            return getApplicableDiscount(date);
        }
        return 0;
    }

    public boolean hasExpiration() {
        if (DateUtil.daysBetweenDates(startValidPeriod, endValidPeriod) > 730) {
            return false;
        }
        return true;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public RateDataValue getRate() {
        throw new UnsupportedOperationException("The Discount Object '" + ticker +
                "' Doesn't have Rate DataValue.");
    }

    public void setRate(RateDataValue rate) {
        throw new UnsupportedOperationException("The Discount Object '" + ticker +
                "' Doesn't have Rate DataValue.");
    }

    public AvailabilityDataValue getAvailability() {
        throw new UnsupportedOperationException("The Discount Object '" + ticker +
                "' Doesn't have Availability DataValue.");
    }

    public void setAvailability(AvailabilityDataValue availability) {
        throw new UnsupportedOperationException("The Discount Object '" + ticker +
                "' Doesn't have Availability DataValue.");
    }

    @Override
    public String toString() {
        return "Discount{" + "id=" + id + ", ticker=" + ticker + ", name=" + name
//                + ", description=" + description
                + ", reduction=" + reduction + ", percentage=" + percentage
                + ", active=" + active + ", order=" + order
//                + ", lock=" + lock
                + ", startValidPeriod=" + startValidPeriod + ", endValidPeriod=" + endValidPeriod + ", promoCode=" + promoCode + ", startContractPeriod=" + startContractPeriod + ", endContractPeriod=" + endContractPeriod
//                + ", minStay=" + minStay + ", maxStay=" + maxStay + ", minNotice=" + minNotice + ", maxNotice=" + maxNotice
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
        final Discount other = (Discount) obj;
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
        if (Float.floatToIntBits(this.reduction) != Float.floatToIntBits(other.reduction)) {
            return false;
        }
        if (this.percentage != other.percentage) {
            return false;
        }
        /*
        if (this.lock != other.lock && (this.lock == null || !this.lock.equals(other.lock))) {
            return false;
        }
        */
        if (this.startValidPeriod != other.startValidPeriod && (this.startValidPeriod == null || !this.startValidPeriod.equals(other.startValidPeriod))) {
            return false;
        }
        if (this.endValidPeriod != other.endValidPeriod && (this.endValidPeriod == null || !this.endValidPeriod.equals(other.endValidPeriod))) {
            return false;
        }
        if ((this.promoCode == null) ? (other.promoCode != null) : !this.promoCode.equals(other.promoCode)) {
            return false;
        }
        if (this.active != other.active) {
            return false;
        }
        if (this.startContractPeriod != other.startContractPeriod && (this.startContractPeriod == null || !this.startContractPeriod.equals(other.startContractPeriod))) {
            return false;
        }
        if (this.endContractPeriod != other.endContractPeriod && (this.endContractPeriod == null || !this.endContractPeriod.equals(other.endContractPeriod))) {
            return false;
        }
        /*
        if (this.minStay != other.minStay && (this.minStay == null || !this.minStay.equals(other.minStay))) {
            return false;
        }
        if (this.maxStay != other.maxStay && (this.maxStay == null || !this.maxStay.equals(other.maxStay))) {
            return false;
        }
        if (this.minNotice != other.minNotice && (this.minNotice == null || !this.minNotice.equals(other.minNotice))) {
            return false;
        }
        if (this.maxNotice != other.maxNotice && (this.maxNotice == null || !this.maxNotice.equals(other.maxNotice))) {
            return false;
        }
        */
        return true;
    }


    @Override
    public int compareTo(Discount o) {
        return order < o.getOrder() ? -1 : order > o.getOrder() ? 1 : 0;
    }

}
