/*
 *   ConstantsSiteMinder.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.tripadvisor;

import com.witbooking.middleware.resources.MiddlewareProperties;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 07/10/13
 */
public final class ConstantsTripAdvisor {

    public static final String REQUESTOR_CHANNEL_ID = "WTB";
    public static final String TYPE_RESERVATION = "22";
    public static final String CHANNEL_NAME = "TripAdvisor";

    public static final String URL_CREATE_REQUEST = MiddlewareProperties.TRIP_ADVISOR_URL + "email_requests";
    public static final String URL_UPDATE_REQUEST = URL_CREATE_REQUEST + "/";
    public static final String URL_DELETE_REQUEST = URL_UPDATE_REQUEST;
    public static final String URL_LIST_RESERVES_BY_HOTEL = URL_CREATE_REQUEST;
    public static final String URL_CHECK_OPTIONS_IN = MiddlewareProperties.TRIP_ADVISOR_URL + "/location/";
    public static final String[] VALID_LANGUAGES = new String[]{
            "da", "de", "el", "en_UK", "EN_US", "es", "es_AR", "es_MX", "fr", "in", "it", "ja", "ko",
            "ni", "no", "pl", "pt", "ru", "sv", "th", "tr", "zh_CN", "zh_TW"};

    public String[] roomCodes = new String[]{"SINGLE", "QUEEN"};
    public static final String COUNTRY = "ES";

}
