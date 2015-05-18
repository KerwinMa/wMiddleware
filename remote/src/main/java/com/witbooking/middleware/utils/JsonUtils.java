/*
 *  JsonUtils.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.utils;

import com.fatboyindustrial.gsonjodatime.DateTimeConverter;
import com.fatboyindustrial.gsonjodatime.LocalTimeConverter;
import com.google.gson.*;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.dynamicPriceVariation.Condition;
import com.witbooking.middleware.model.values.*;
import com.witbooking.middleware.model.values.types.ConstantValue;
import com.witbooking.middleware.model.values.types.FormulaValue;
import com.witbooking.middleware.model.values.types.SharedValue;
import com.witbooking.middleware.utils.serializers.JSONConditionSerializer;
import org.joda.time.DateTime;
import org.joda.time.LocalTime;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Json utilities class.
 *
 * @author Jose Francisco Fiorillo  < jffiorillo@gmail.com >
 * @since 1.0 , Aug 29, 2013
 */
public final class JsonUtils {

    //    private static final Gson gson;
    public static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(RateDataValue.class, new DataValueAdapter())
            .registerTypeAdapter(AvailabilityDataValue.class, new DataValueAdapter())
            .registerTypeAdapter(LockDataValue.class, new DataValueAdapter())
            .registerTypeAdapter(StayDataValue.class, new DataValueAdapter())
            .registerTypeAdapter(NoticeDataValue.class, new DataValueAdapter())
            .registerTypeAdapter(Condition.class, new JSONConditionSerializer())
            .registerTypeAdapter(LocalTime.class, new LocalTimeConverter())
            .registerTypeAdapter(DateTime.class, new DateTimeConverter())
            .create();

    public static final Gson gsonInstance() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(RateDataValue.class, new DataValueAdapter())
                .registerTypeAdapter(AvailabilityDataValue.class, new DataValueAdapter())
                .registerTypeAdapter(LockDataValue.class, new DataValueAdapter())
                .registerTypeAdapter(StayDataValue.class, new DataValueAdapter())
                .registerTypeAdapter(NoticeDataValue.class, new DataValueAdapter())
                .registerTypeAdapter(Condition.class, new JSONConditionSerializer())
                .registerTypeAdapter(LocalTime.class, new LocalTimeConverter())
                .registerTypeAdapter(DateTime.class, new DateTimeConverter())
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
                .create();
        return gson;
    }

    public static final String toJson(Object elem) {
        return gson.toJson(elem);
    }


    public static JsonArray dataValueHolderResumeToJsonArray(List<DataValueHolder> valueHolderList, boolean printType) {
        JsonArray jsonArray = new JsonArray();
        for (DataValueHolder item : valueHolderList) {
            jsonArray.add(JsonUtils.dataValueHolderResumeToJsonObject(item, printType));
        }
        return jsonArray;
    }

    public static JsonObject dataValueHolderResumeToJsonObject(DataValueHolder valueHolder, boolean printType) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("ticker", valueHolder.getTicker());
        if (printType)
            jsonObject.addProperty("type", valueHolder.getClass().getSimpleName());
        if (valueHolder instanceof Inventory) {
            jsonObject.addProperty("name", ((Inventory) valueHolder).getFullName());
        } else {
            jsonObject.addProperty("name", valueHolder.getName());
        }
        jsonObject.addProperty("visible", valueHolder.isActive());
        if (valueHolder instanceof Inventory)
            jsonObject.add("occupancy", configurationToJson(((Inventory) valueHolder).getConfiguration()));
        if (!(valueHolder instanceof Discount))
            jsonObject.add(HashRangeValue.RATE, dataValueToJson(valueHolder.getRate()));
        if (valueHolder instanceof Inventory)
            jsonObject.add(HashRangeValue.ACTUAL_AVAILABILITY, dataValueToJson(valueHolder.getAvailability()));
        jsonObject.add(HashRangeValue.LOCK, dataValueToJson(valueHolder.getLock()));
        jsonObject.add(HashRangeValue.MIN_STAY, dataValueToJson(valueHolder.getMinStay()));
        jsonObject.add(HashRangeValue.MAX_STAY, dataValueToJson(valueHolder.getMaxStay()));
        jsonObject.add(HashRangeValue.MIN_NOTICE, dataValueToJson(valueHolder.getMinNotice()));
        jsonObject.add(HashRangeValue.MAX_NOTICE, dataValueToJson(valueHolder.getMaxNotice()));
        return jsonObject;
    }

    public static JsonObject configurationToJson(Configuration configuration) {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<Guest, Integer> guest : configuration.getGuests().entrySet()) {
            jsonObject.addProperty(guest.getKey().getTicker(), guest.getValue());
        }
        return jsonObject;
    }

    public static JsonObject dataValueToJson(DataValue dataValue) {
        JsonObject jsonObject = new JsonObject();
        if (dataValue == null) {
            jsonObject.add("type", new JsonPrimitive(EnumDataValueType.NULL_VALUE + ""));
        } else {
            jsonObject.add("type", new JsonPrimitive(dataValue.getValueType() + ""));
            switch (dataValue.getValueType()) {
                case SHARED:
                    jsonObject.addProperty("ticker", ((SharedValue) dataValue.getValue()).getTicker() + "");
                    break;
                case FORMULA:
                    jsonObject.addProperty("formula", ((FormulaValue) dataValue.getValue()).getFormulaValue() + "");
                    break;
                case CONSTANT:
                    jsonObject.addProperty("constant", ((ConstantValue) dataValue.getValue()).getConstantValue() + "");
                    break;
                default:
                    break;
            }
        }
        return jsonObject;
    }

    public static JsonObject exceptionToJson(Exception e) {
        JsonObject errorObject = new JsonObject();
        errorObject.addProperty("error", e + "");
        return errorObject;
    }
}

class DataValueAdapter implements JsonSerializer<DataValue> {
    @Override
    public JsonElement serialize(DataValue rateDataValue, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject result = new JsonObject();
        final Gson gson = new Gson();
//        result.getAsJsonObject(gson.toJson(rateDataValue.getValue()));
        final JsonElement result1 = gson.toJsonTree(rateDataValue.getValue());
        result.add("value", result1);
        result.add("type", new JsonPrimitive(rateDataValue.getValueType() + ""));
        return result;
//        result.add("value",gson.toJson(rateDataValue.getValue()));
//        return result;
    }
}