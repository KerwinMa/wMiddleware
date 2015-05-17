package com.witbooking.middleware.utils.serializers;

import com.google.gson.*;
import com.witbooking.middleware.model.dynamicPriceVariation.Condition;
import com.witbooking.middleware.utils.JsonUtils;

import java.lang.reflect.Type;

/**
 * Created by mongoose on 11/26/14.
 */
public class JSONConditionSerializer implements JsonSerializer<Condition>, JsonDeserializer<Condition> {

    private static final String TYPE="type";

    @Override
    public JsonElement serialize(Condition src, Type typeOfSrc, JsonSerializationContext context) {

        Gson gson = JsonUtils.gson;
        JsonElement jsonElement =gson.toJsonTree(src);
        JsonObject result = jsonElement.getAsJsonObject();
        result.add(TYPE, new JsonPrimitive(src.getClass().getSimpleName()));

        return result;
    }

    @Override
    public Condition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        Gson gson = JsonUtils.gson;
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get(TYPE).getAsString();
        jsonObject.remove(TYPE);

        try {
            return context.deserialize(jsonObject, Class.forName(Condition.class.getPackage().getName()+"."+type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Unknown element type: " + type, cnfe);
        }
    }
}