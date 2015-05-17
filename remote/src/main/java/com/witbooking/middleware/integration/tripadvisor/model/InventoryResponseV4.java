/*
 * Copyright (C) 2013 Tripadvisor LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.witbooking.middleware.integration.tripadvisor.model;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This class will hold the response for an InventoryRequest 
 * Use toJson to serialize into the correct format for the response to be sent
 *
 */
public class InventoryResponseV4 implements Validatable
{
    private static final String NAME = "InventoryResponse";

    @SerializedName("api_version")
    private final Integer m_apiVersion = Versions.VERSION_4;
    @SerializedName("lang")
    private final String m_lang;
    @SerializedName("hotels")
    private final List<InventoryHotelV4> m_hotels;
    @SerializedName("errors")
    private final List<ErrorV4> m_errors;

    public InventoryResponseV4(Builder builder)
    {
        m_lang   = builder.m_lang;
        m_hotels = HACUtils.createImmutableListOrNull(builder.m_hotels);
        m_errors = HACUtils.createImmutableListOrNull(builder.m_errors);
    }
    
    public static class Builder implements ValidatingBuilder<InventoryResponseV4>
    {
        private String m_lang;
        private List<InventoryHotelV4> m_hotels;
        private List<ErrorV4> m_errors;

        public Builder lang(String lang)
        { 
            m_lang = lang;
            return this; 
        }
        
        public Builder hotels(List<InventoryHotelV4> hotels) 
        { 
            m_hotels = hotels; 
            return this; 
        }
        
        public Builder errors(List<ErrorV4> errors) 
        { 
            m_errors = errors; 
            return this; 
        }

        @Override
        public InventoryResponseV4 buildWithoutValidation()
        {
            return new InventoryResponseV4(this);
        }
        
        @Override
        public InventoryResponseV4 build()
        {
            InventoryResponseV4 inventoryResponse = new InventoryResponseV4(this);
            List<String> validationErrors = inventoryResponse.validate();
            ValidationUtils.generateFailureMessage(validationErrors);
            return inventoryResponse;
        }        
    }

    public Integer getApiVersion()
    {
        return m_apiVersion;
    }

    public String getLang()
    {
        return m_lang;
    }

    /**
     * @return Hotels list, will return empty list if not specified
     */
    public List<InventoryHotelV4> getHotels()
    {
        return HACUtils.createEmptyListIfNull(m_hotels);
    }
    
    /**
     * @return Errors list, will return empty list if not specified
     */
    public List<ErrorV4> getErrors()
    {
        return HACUtils.createEmptyListIfNull(m_errors);
    }

    public List<String> validate()
    {
        List<String> validationErrors = Lists.newArrayList();

        ValidationUtils.notNull("api_version", NAME, m_apiVersion, validationErrors);
        ValidationUtils.notNull("lang", NAME, m_lang, validationErrors);
        ValidationUtils.notNull("hotels", NAME, m_hotels, validationErrors);

        for (InventoryHotelV4 hotel : getHotels())
        {
            validationErrors.addAll(hotel.validate());
        }

        for (ErrorV4 error : getErrors())
        {
            validationErrors.addAll(error.validate());
        }

        return validationErrors;
    }

    public String toJson() 
    {
        return GSONV4.GSON.get().toJson(this);
    }

    public static InventoryResponseV4 fromJson(String sJsonString) 
    {
        return GSONV4.GSON.get().fromJson(sJsonString, InventoryResponseV4.class);
    }

}
