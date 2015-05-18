/*
 *   ManagementVisualRepresentation.java
 * 
 * Copyright(c) 2015 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.witbooking.middleware.model.values.HashRangeValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 09/04/2015
 */
public class ManagementVisualRepresentation {

    private List<HashRangeValue> rangeValues;
    private List<DataValueHolder> valueHolders;
    private Map<String, Object> managementParams;

    public static String RANGE_VALUES_KEY = "ARIValues";
    public static String VALUE_HOLDERS_KEY = "ManagementData";
    public static String MANAGEMENT_PARAMS_KEY = "ManagementParams";

    //Management Parameters Keys Strings
    public static String MINIMUM_PRICE_LEGACY = "maximumprice";
    public static String MAXIMUM_PRICE_LEGACY = "minimumprice";
    public static String WARNING_LIMIT_AVAILABILITY_LEGACY = "limiteAvisoDisponibilidad";
    public static String MINIMUM_PRICE = "minPrice";
    public static String MAXIMUM_PRICE = "maxPrice";
    public static String WARNING_LIMIT_AVAILABILITY = "warningAvailability";


    public ManagementVisualRepresentation() {
        this.valueHolders = new ArrayList<>();
        this.rangeValues = new ArrayList<>();
        this.managementParams = new HashMap<>();
    }

    public List<HashRangeValue> getRangeValues() {
        return rangeValues;
    }

    public void setRangeValues(List<HashRangeValue> rangeValues) {
        if (rangeValues != null)
            this.rangeValues = rangeValues;
    }

    public void addRangeValue(HashRangeValue rangeValue) {
        this.rangeValues.add(rangeValue);
    }

    public List<DataValueHolder> getValueHolders() {
        return valueHolders;
    }

    public void setValueHolders(List<DataValueHolder> valueHolders) {
        if (valueHolders != null)
            this.valueHolders = valueHolders;
    }

    public void addValueHolder(DataValueHolder valueHolders) {
        this.valueHolders.add(valueHolders);
    }

    public Map<String, Object> getManagementParams() {
        return managementParams;
    }

    public void setManagementParams(Map<String, Object> managementParams) {
        this.managementParams = managementParams;
    }

    public void addManagementParam(String key, Object value) {
        this.managementParams.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ManagementVisualRepresentation)) return false;
        ManagementVisualRepresentation that = (ManagementVisualRepresentation) o;
        if (!rangeValues.equals(that.rangeValues)) return false;
        if (!valueHolders.equals(that.valueHolders)) return false;
        if (!managementParams.equals(that.managementParams)) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = rangeValues != null ? rangeValues.hashCode() : 0;
        result = 31 * result + (valueHolders != null ? valueHolders.hashCode() : 0);
        result = 31 * result + (managementParams != null ? managementParams.hashCode() : 0);
        return result;
    }
}
