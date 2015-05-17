/*
 *  HashRangeResult.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 27-mar-2014
 */
public class HashRangeResult implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private String ticker;
    private HashMap<String, Object> results;

    /**
     * Creates a new instance of
     * <code>HashRangeValue</code> without params.
     */
    public HashRangeResult() {
    }

    public HashRangeResult(String ticker) {
        this.ticker = ticker;
        this.results = new HashMap<String, Object>();
    }

    public HashRangeResult(String ticker, HashMap<String, Object> hashRangeValues) {
        this.ticker = ticker;
        this.results = hashRangeValues;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public HashMap<String, Object> getResults() {
        return results;
    }

    public void setResults(HashMap<String, Object> hashRangeValues) {
        this.results = hashRangeValues;
    }

    public Object getRangeResult(String key) {
        return results.get(key);
    }

    public void putRangeResults(String key, Object result) {
        this.results.put(key, result);
    }

    public void removeRangeResults(String key) {
        this.results.remove(key);
    }

    //used to export to simplified JSON
    public static Map<String, Map<String, Object>> listToMapRangeResult(List<HashRangeResult> hashRangeResultList) {
        Map<String, Map<String, Object>> mapRangeValues = new HashMap<>();
        for (HashRangeResult hashRangeValue : hashRangeResultList) {
            mapRangeValues.put(hashRangeValue.getTicker(), hashRangeValue.getResults());
        }
        return mapRangeValues;
    }

    @Override
    public String toString() {
        return "HashRangeResult{" + "ticker='" + ticker + "', values=" + results + '}';
    }
}
