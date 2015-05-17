/*
 *   JSONExclusionStrategy.java
 * 
 * Copyright(c) 2014 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.utils;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.values.RangeValue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 14/04/14
 */
public class JSONExclusionStrategy implements ExclusionStrategy {

    //class maps to array of fields to skip in class
    private static Map<Class<?>, String[]> excludedFields;

    public JSONExclusionStrategy() {
        excludedFields = new HashMap<Class<?>, String[]>();
    }

    public JSONExclusionStrategy(Map<Class<?>, String[]> excludedFields) {
        this.excludedFields = excludedFields;
        //all arrays of fields are sorted lexically for faster lookup
        for (Map.Entry<Class<?>, String[]> entry : excludedFields.entrySet()) {
            Arrays.sort(entry.getValue());
        }
    }

    public static JSONExclusionStrategy JSONExclusionStrategyRangeValues() {
        Map<Class<?>, String[]> excludedFields = new HashMap<Class<?>, String[]>();
        excludedFields.put(RangeValue.class, new String[]{"defaultValue"});
        JSONExclusionStrategy jsonExclusionStrategy = new JSONExclusionStrategy(excludedFields);
        return jsonExclusionStrategy;
    }

    public static JSONExclusionStrategy JSONExclusionStrategyReservation() {
        //class maps to array of fields to skip in class
        Map<Class<?>, String[]> excludedFields = new HashMap<Class<?>, String[]>();
        excludedFields.put(Customer.class, new String[]{"creditCard", "mailOption", "id", "ipOrder"});
        excludedFields.put(Reservation.class, new String[]{"reported", "channelId", "agentId", "channelAddress", "referer",
                "paymentStatus", "tax", "emailPostStayDate", "emailPreStayDate", "googleAnalyticsReported", "cancellationRelease",
                "userEmailStatus","hotelEmailStatus"});
        excludedFields.put(RoomStay.class, new String[]{"id", "inventoryId", "externalCommission", "canceledByClient",
                "dateModification", "dateCancellation"});
        excludedFields.put(ServiceRequested.class, new String[]{"daily", "type"});
        excludedFields.put(DiscountApplied.class, new String[]{"reduction"});
        excludedFields.put(Tax.class, new String[]{"id"});
        excludedFields.put(RangeValue.class, new String[]{"defaultValue"});
        JSONExclusionStrategy jsonExclusionStrategy = new JSONExclusionStrategy(excludedFields);
        return jsonExclusionStrategy;
    }


    public boolean shouldSkipClass(Class<?> arg0) {
        return false;
    }

    public boolean shouldSkipField(FieldAttributes fieldAttributes) {
        if (excludedFields.containsKey(fieldAttributes.getDeclaringClass())) {
            return Arrays.binarySearch(excludedFields.get(fieldAttributes.getDeclaringClass()), fieldAttributes.getName()) >= 0;
        }
        return false;
    }
}
