/*
 *  HashRangeValue.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values;

import java.io.Serializable;
import java.util.*;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 22-abr-2013
 */
public class HashRangeValue implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private String ticker;
    private Map<String, RangeValue> hashRangeValues;
    //keys for the map
    public static final String RATE = "rate";
    public static final String PROMOTIONAL_RATE = "promoRate";
    public static final String ACTUAL_AVAILABILITY = "availability";
    public static final String TOTAL_AVAILABILITY = "totalAvailability";
    public static final String LOCK = "closed";
    public static final String MIN_STAY = "minStay";
    public static final String MAX_STAY = "maxStay";
    public static final String MIN_NOTICE = "minNotice";
    public static final String MAX_NOTICE = "maxNotice";
    public static final String VARIABLE = "variable";
    public static final String DISCOUNTS_APPLIED = "discountsApplied";
    public static final String RULES_APPLIED = "rulesApplied";

    /**
     * Creates a new instance of
     * <code>HashRangeValue</code> without params.
     */
    public HashRangeValue() {
    }

    public HashRangeValue(String ticker) {
        this.ticker = ticker;
        this.hashRangeValues = new HashMap<String, RangeValue>();
    }

    public HashRangeValue(String ticker, HashMap<String, RangeValue> hashRangeValues) {
        this.ticker = ticker;
        this.hashRangeValues = hashRangeValues;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Map<String, RangeValue> getHashRangeValues() {
        return hashRangeValues;
    }

    public void setHashRangeValues(Map<String, RangeValue> hashRangeValues) {
        this.hashRangeValues = hashRangeValues;
    }

    public RangeValue getRangeValue(String key) {
        return hashRangeValues.get(key);
    }

    public void putRangeValues(String key, RangeValue rangeValue) {
        this.hashRangeValues.put(key, rangeValue);
    }

    public void removeRangeValues(String key) {
        this.hashRangeValues.remove(key);
    }

    public Date getRangeStartDate() {
        Date startDate = null;
        for (RangeValue rangeValue : hashRangeValues.values()) {
            Date rangeDate = rangeValue.getRangeStartDate();
            if (startDate == null || (rangeDate != null && rangeDate.before(startDate))) {
                startDate = rangeDate;
            }
        }
        return startDate;
    }

    public Date getRangeEndDate() {
        Date startDate = null;
        for (RangeValue rangeValue : hashRangeValues.values()) {
            Date rangeDate = rangeValue.getRangeEndDate();
            if (startDate == null || (rangeDate != null && rangeDate.after(startDate))) {
                startDate = rangeDate;
            }
        }
        return startDate;
    }

    public static Date getListHashRangeStartDate(List<HashRangeValue> hashRangeValueList) {
        Date startDate = null;
        for (HashRangeValue hashRangeValue : hashRangeValueList) {
            Date rangeDate = hashRangeValue.getRangeStartDate();
            if (startDate == null || (rangeDate != null && rangeDate.before(startDate))) {
                startDate = rangeDate;
            }
        }
        return startDate;
    }

    public static Date getListHashRangeEndDate(List<HashRangeValue> hashRangeValueList) {
        Date endDate = null;
        for (HashRangeValue hashRangeValue : hashRangeValueList) {
            Date rangeDate = hashRangeValue.getRangeEndDate();
            if (endDate == null || (rangeDate != null && rangeDate.after(endDate))) {
                endDate = rangeDate;
            }
        }
        return endDate;
    }

    public static Map<String, Map<String, RangeValue>> listToMapRangeValues(List<HashRangeValue> hashRangeValueList) {
        Map<String, Map<String, RangeValue>> mapRangeValues = new HashMap<>();
        for (HashRangeValue hashRangeValue : hashRangeValueList) {
            mapRangeValues.put(hashRangeValue.getTicker(), hashRangeValue.getHashRangeValues());
        }
        return mapRangeValues;
    }

    public static List<HashRangeValue> mapToListHashRangeValue(Map<String, Map<String, RangeValue>> mapRangeValues) {
        List<HashRangeValue> hashRangeValueList = new ArrayList<>();
        for (Map.Entry<String, Map<String, RangeValue>> mapEntry : mapRangeValues.entrySet()) {
            HashRangeValue hashRangeValue = new HashRangeValue();
            hashRangeValue.setTicker(mapEntry.getKey());
            hashRangeValue.setHashRangeValues(mapEntry.getValue());
            hashRangeValueList.add(hashRangeValue);
        }
        return hashRangeValueList;
    }

    @Override
    public String toString() {
        return "HashRangeValue{" + "ticker='" + ticker + "', values=" + hashRangeValues + '}';
    }
}
