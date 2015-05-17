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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.util.Currency;
import java.util.Date;
import java.util.List;

/**
 * This class will hold the response for a HotelAvailabilityRequest 
 * Use toJson to serialize into the correct format for the response to be sent
 *
 */
public class AvailabilityResponseV4 implements Validatable
{
    private static final String NAME = "AvailabilityResponse";

    @SerializedName("api_version")
    private final Integer m_apiVersion = Versions.VERSION_4;
    @SerializedName("hotel_ids")
    private final List<Integer> m_hotelIds;
    @SerializedName("start_date")
    private final Date m_startDate;
    @SerializedName("end_date")
    private final Date m_endDate;
    @SerializedName("num_adults")
    private final Integer m_numAdults;
    @SerializedName("num_rooms")
    private final Integer m_numRooms;
    @SerializedName("currency")
    private final String m_currency;
    @SerializedName("user_country")
    private final String m_userCountry;
    @SerializedName("device_type")
    private final String m_deviceType;
    @SerializedName("query_key")    
    private final String m_queryKey;
    @SerializedName("lang")
    private final String m_lang;
    @SerializedName("num_hotels")
    private final Integer m_numHotels;
    @SerializedName("hotels")
    private final List<AvailabilityHotelV4> m_hotels;
    @SerializedName("errors")
    private final List<ErrorV4> m_errors;
    
    private AvailabilityResponseV4(Builder builder) 
    {
         m_hotelIds    = HACUtils.createImmutableListOrNull(builder.m_hotelIds);   
         m_startDate   = builder.m_startDate; 
         m_endDate     = builder.m_endDate;    
         m_numAdults   = builder.m_numAdults;  
         m_numRooms    = builder.m_numRooms;   
         m_currency    = builder.m_currency != null ? builder.m_currency.getCurrencyCode() : null;
         m_userCountry = builder.m_userCountry;
         m_deviceType  = builder.m_deviceType; 
         m_queryKey    = builder.m_queryKey;   
         m_lang        = builder.m_lang;       
         m_numHotels   = builder.m_numHotels; 
         //for most of our fields we want to store null if it is empty, but for this we need to store an empty array in order to serialize correctly
         m_hotels      = ImmutableList.<AvailabilityHotelV4>copyOf(HACUtils.createEmptyListIfNull(builder.m_hotels));
         m_errors      = HACUtils.createImmutableListOrNull(builder.m_errors); 
    }
    
    public static class Builder implements ValidatingBuilder<AvailabilityResponseV4>
    {
        private List<Integer> m_hotelIds;
        private Date m_startDate;
        private Date m_endDate;
        private Integer m_numAdults;
        private Integer m_numRooms;
        private Currency m_currency;
        private String m_userCountry;
        private String m_deviceType;
        private String m_queryKey;
        private String m_lang;
        private Integer m_numHotels;
        private List<AvailabilityHotelV4> m_hotels;
        private List<ErrorV4> m_errors;

        public Builder hotelIds(List<Integer> hotelIds)
        {
            m_hotelIds = hotelIds;
            return this; 
        }
        
        public Builder startDate(Date startDate)
        { 
            m_startDate = startDate;
            return this; 
        }
        
        public Builder endDate(Date endDate)
        { 
            m_endDate = endDate; 
            return this; 
        }
        
        public Builder numAdults(Integer numAdults)
        { 
            m_numAdults = numAdults; 
            return this; 
        }
        
        public Builder numRooms(Integer numRooms)
        { 
            m_numRooms = numRooms;
            return this; 
        }
        
        public Builder currency(Currency currency)
        { 
            m_currency = currency; 
            return this; 
        }
        
        public Builder userCountry(String userCountry)
        { 
            m_userCountry = userCountry; 
            return this;
        }
        
        public Builder deviceType(String deviceType)
        { 
            m_deviceType = deviceType;
            return this; 
        }
        
        public Builder queryKey(String queryKey)
        { 
            m_queryKey = queryKey;
            return this;
        }
        
        public Builder lang(String lang)                                   
        { 
            m_lang = lang; 
            return this;
        }
        
        public Builder numHotels(Integer numHotels)
        { 
            m_numHotels = numHotels; 
            return this;
        }
        
        public Builder hotels(List<AvailabilityHotelV4> hotels) 
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
        public AvailabilityResponseV4 buildWithoutValidation()
        {
            return new AvailabilityResponseV4(this);
        }
        
        @Override
        public AvailabilityResponseV4 build()
        {
            AvailabilityResponseV4 availabilityResponse = new AvailabilityResponseV4(this);
            List<String> validationErrors = availabilityResponse.validate();
            ValidationUtils.generateFailureMessage(validationErrors);
            return availabilityResponse;
        }        
    }
    
    /**
     * @return Hotels list, will return empty list if not specified
     */
    public List<AvailabilityHotelV4> getHotels()
    {
        return HACUtils.createEmptyListIfNull(m_hotels);
    }

    public Integer getApiVersion()
    {
        return m_apiVersion;
    }

    /**
     * @return HotelIds list, will return empty list if not specified
     */
    public List<Integer> getHotelIds()
    {        
        return HACUtils.createEmptyListIfNull(m_hotelIds);
    }

    public Date getStartDate()
    {
        return m_startDate;
    }

    public Date getEndDate()
    {
        return m_endDate;
    }

    public String getLang()
    {
        return m_lang;
    }

    public Integer getNumAdults()
    {
        return m_numAdults;
    }

    public Integer getNumHotels()
    {
        return m_numHotels;
    }        

    /**
     * @return Errors list, will return empty list if not specified
     */
    public List<ErrorV4> getErrors()
    {
        return HACUtils.createEmptyListIfNull(m_errors);
    }

    public Integer getNumRooms()
    {
        return m_numRooms;
    }

    public Currency getCurrency()
    {
        return Currency.getInstance(m_currency);
    }

    public String getDeviceType()
    {
        return m_deviceType;
    }

    public String getUserCountry()
    {
        return m_userCountry;
    }

    public String getQueryKey()
    {
        return m_queryKey;
    }

    @Override
    public List<String> validate()
    {
        List<String> validationErrors = Lists.newArrayList();

        ValidationUtils.notNull("api_version", NAME, m_apiVersion, validationErrors);
        ValidationUtils.notNull("hotel_ids", NAME, m_hotelIds, validationErrors);
        ValidationUtils.notNull("start_date", NAME, m_startDate, validationErrors);
        ValidationUtils.notNull("end_date", NAME, m_endDate, validationErrors);
        ValidationUtils.notNull("num_adults", NAME, m_numAdults, validationErrors);
        ValidationUtils.notZero("num_adults", NAME, m_numAdults, validationErrors);
        ValidationUtils.notNull("num_rooms", NAME, m_numRooms, validationErrors);
        ValidationUtils.notZero("num_rooms", NAME, m_numRooms, validationErrors);
        ValidationUtils.notNull("query_key", NAME, m_queryKey, validationErrors);
        ValidationUtils.notNull("lang", NAME, m_lang, validationErrors);
        ValidationUtils.notNull("num_hotels", NAME, m_numHotels, validationErrors);
        ValidationUtils.notNull("hotels", NAME, m_hotels, validationErrors);

        int hotelsSize = 0;
        hotelsSize = getHotels().size();
        for (AvailabilityHotelV4 hotel : getHotels())
        {
            validationErrors.addAll(hotel.validate());
        }
        
        if (m_numHotels != null)
        {
            if (hotelsSize != m_numHotels)
            {
                validationErrors.add("num_hotels in " + NAME + " (" + m_numHotels + ") does not match the number of hotels in the response (" + hotelsSize + ")");
            }
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

    public static AvailabilityResponseV4 fromJson(String sJsonString) 
    {
        return GSONV4.GSON.get().fromJson(sJsonString, AvailabilityResponseV4.class);
    }
}