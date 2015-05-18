/*
 *   JSONReservationSerializer.java
 * 
 * Copyright(c) 2014 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.utils.serializers;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.witbooking.middleware.model.Reservation;
import com.witbooking.middleware.model.RoomStay;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.JSONExclusionStrategy;

import java.lang.reflect.Type;
import java.util.Date;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 29/04/14
 */
public class JSONReservationSerializer implements JsonSerializer<Reservation> {


    @Override
    public JsonElement serialize(final Reservation reservation, final Type typeOfSrc, final JsonSerializationContext context) {
        Gson gsonReservation = new GsonBuilder()
                .setExclusionStrategies(JSONExclusionStrategy.JSONExclusionStrategyReservation()).setDateFormat
                        ("yyyy-MM-dd").create();
        final JsonObject jsonObject = (JsonObject) gsonReservation.toJsonTree(reservation, new TypeToken<Reservation>() {
        }.getType());
        jsonObject.addProperty("dateCreation", DateUtil.isoDateTimeFormat(reservation.getDateCreation()));
        Date modificationDate = null;
        int i = 0;
        JsonArray roomsStaysJson = jsonObject.getAsJsonArray("roomStays");
        for (RoomStay roomStay : reservation.getRoomStays()) {
            JsonObject roomsStayJson = roomsStaysJson.get(i).getAsJsonObject();
            if (roomStay.getDateModification() != null) {
                roomsStayJson.addProperty("dateModification", DateUtil.isoDateTimeFormat(roomStay.getDateModification()));
                if (modificationDate == null || modificationDate.before(roomStay.getDateModification())) {
                    modificationDate = roomStay.getDateModification();
                }
            }
            if (roomStay.getCancellationDate() != null) {
                roomsStayJson.addProperty("dateCancellation", DateUtil.isoDateTimeFormat(roomStay.getCancellationDate()));
                if (modificationDate == null || modificationDate.before(roomStay.getCancellationDate())) {
                    modificationDate = roomStay.getCancellationDate();
                }
            }
            i++;
        }
        if (modificationDate != null)
            jsonObject.addProperty("dateModification", DateUtil.isoDateTimeFormat(modificationDate));
        return jsonObject;
    }
}