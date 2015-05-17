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

import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This class is used to deserialize a HotelAvailabilityRequest object and then provide a simple access point for all the data contained in the request
 */
public class HotelAvailabilityRequestV4 {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HotelAvailabilityRequestV4.class);
    private static final String API_VERSION_KEY = "api_version";
    private static final String HOTELS_KEY = "hotels";
    private static final String START_DATE_KEY = "start_date";
    private static final String END_DATE_KEY = "end_date";
    private static final String NUM_ADULTS_KEY = "num_adults";
    private static final String NUM_ROOMS_KEY = "num_rooms";
    private static final String LANG_KEY = "lang";
    private static final String CURRENCY_KEY = "currency";
    private static final String USER_COUNTRY_KEY = "user_country";
    private static final String DEVICE_TYPE_KEY = "device_type";
    private static final String QUERY_KEY_KEY = "query_key";

    private  Integer m_apiVersion;
    private  List<AvailabilityRequestHotelV4> m_hotels;
    private  Date m_startDate;
    private  Date m_endDate;
    private  Integer m_adults;
    private  Integer m_numRooms;
    private  String m_lang;
    private  Currency m_currency;
    private  String m_userCountry;
    private  String m_deviceType;
    private  String m_queryKey;

    /**
     * This will construct a HotelAvailabilityRequest, given a valid HotelAvailabilityRequest received from TripAdvisor
     *
     * @throws Exception
     */
    public HotelAvailabilityRequestV4(String apiVersion, String hotels, String startDate, String endDate, String numAdults,
                                      String numRooms, String lang, String currency, String userCountry, String deviceType,
                                      String queryKey) throws Exception {
        //create a new Date Format instance, because it is not thread safe
        DateFormat dateFormat = new SimpleDateFormat(GSONV4.DATE_FORMAT);
        m_startDate = dateFormat.parse(startDate);
        m_endDate = dateFormat.parse(endDate);
        m_apiVersion = Integer.valueOf(apiVersion);
        m_hotels = _getHotels(hotels);
        m_adults = Integer.valueOf(numAdults);
        m_numRooms = Integer.valueOf(numRooms);
        m_lang = lang;
        m_userCountry = userCountry;
        m_deviceType = deviceType;
        m_queryKey = queryKey;
        //TODO: handle this error
        try {
            m_currency = currency != null ? Currency.getInstance(currency) : null;
        }catch (Exception e){
            logger.debug(e);
            logger.debug("Currency error: "+currency);
            m_currency =Currency.getInstance("EUR");
        }
    }

    private HotelAvailabilityRequestV4(Builder builder) {
        m_apiVersion = builder.m_apiVersion;
        m_hotels = builder.m_hotels;
        m_startDate = builder.m_startDate;
        m_endDate = builder.m_endDate;
        m_adults = builder.m_adults;
        m_numRooms = builder.m_numRooms;
        m_lang = builder.m_lang;
        m_currency = builder.m_currency;
        m_userCountry = builder.m_userCountry;
        m_deviceType = builder.m_deviceType;
        m_queryKey = builder.m_queryKey;
    }

    /**
     * This builder allows you to create HotelAvailabilityRequests for testing purposes
     */
    public static class Builder {
        private Integer m_apiVersion;
        private List<AvailabilityRequestHotelV4> m_hotels;
        private Date m_startDate;
        private Date m_endDate;
        private Integer m_adults;
        private Integer m_numRooms;
        private String m_lang;
        private Currency m_currency;
        private String m_userCountry;
        private String m_deviceType;
        private String m_queryKey;

        public Builder apiVersion(Integer apiVersion) {
            m_apiVersion = apiVersion;
            return this;
        }

        public Builder hotels(List<AvailabilityRequestHotelV4> hotels) {
            m_hotels = hotels;
            return this;
        }

        public Builder startDate(Date startDate) {
            m_startDate = startDate;
            return this;
        }

        public Builder endDate(Date endDate) {
            m_endDate = endDate;
            return this;
        }

        public Builder adults(Integer adults) {
            m_adults = adults;
            return this;
        }

        public Builder numRooms(Integer numRooms) {
            m_numRooms = numRooms;
            return this;
        }

        public Builder lang(String lang) {
            m_lang = lang;
            return this;
        }

        public Builder currency(Currency currency) {
            m_currency = currency;
            return this;
        }

        public Builder userCountry(String userCountry) {
            m_userCountry = userCountry;
            return this;
        }

        public Builder deviceType(String deviceType) {
            m_deviceType = deviceType;
            return this;
        }

        public Builder queryKey(String queryKey) {
            m_queryKey = queryKey;
            return this;
        }

        public HotelAvailabilityRequestV4 buildWithoutValidation() {
            return new HotelAvailabilityRequestV4(this);
        }
    }

    public int getApiVersion() {
        return m_apiVersion;
    }

    public List<AvailabilityRequestHotelV4> getHotels() {
        return m_hotels;
    }

    public Date getStartDate() {
        return m_startDate;
    }

    public Date getEndDate() {
        return m_endDate;
    }

    public int getAdults() {
        return m_adults;
    }

    public int getNumRooms() {
        return m_numRooms;
    }

    public String getLang() {
        return m_lang;
    }

    public Currency getCurrency() {
        return m_currency;
    }

    public String getUserCountry() {
        return m_userCountry;
    }

    public String getDeviceType() {
        return m_deviceType;
    }

    public String getQueryKey() {
        return m_queryKey;
    }

    private static Map<String, String> _getSimpleMap(Map<String, String[]> params) {
        Map<String, String> simple = new HashMap<String, String>();
        for (String key : params.keySet()) {
            simple.put(key, params.get(key)[0]);
        }
        return simple;
    }

    private List<AvailabilityRequestHotelV4> _getHotels(String sHotels) throws JsonParseException {
        Type listType = new TypeToken<List<AvailabilityRequestHotelV4>>() {
        }.getType();

        return GSONV4.GSON.get().fromJson(sHotels, listType);
    }


    @Override
    public String toString() {
        return "HotelAvailabilityRequestV4{" +
                "m_apiVersion=" + m_apiVersion +
                ", m_hotels=" + m_hotels +
                ", m_startDate=" + m_startDate +
                ", m_endDate=" + m_endDate +
                ", m_adults=" + m_adults +
                ", m_numRooms=" + m_numRooms +
                ", m_lang='" + m_lang + '\'' +
                ", m_currency=" + m_currency +
                ", m_userCountry='" + m_userCountry + '\'' +
                ", m_deviceType='" + m_deviceType + '\'' +
                ", m_queryKey='" + m_queryKey + '\'' +
                '}';
    }
}