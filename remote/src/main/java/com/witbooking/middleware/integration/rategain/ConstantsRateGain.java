/*
 *  ConstantsRateGain.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.rategain;

/**
 * Constant information about RateGain.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 20, 2013
 */
public final class ConstantsRateGain {

    public static final String CHANNEL_ID = "WBKG";
    public static final String CHANNEL_NAME = "Witbooking";
    public static final String SERVICE_NAMESPACE = "http://cgbridge.rategain.com/2011A/ReservationService/HotelResNotif";

    public static final class Error {
        private static final String MSG_ERROR_PARSING = "Error parsing xml given.";

        public static final class Type {

            public static final String UNKNOWN = "1";
            public static final String NO_IMPLEMENTATION = "2";
            public static final String BIZ_RULE = "3";
            public static final String PROTOCOL_VIOLATION = "7";
            public static final String AUTHENTICATION = "4";
            public static final String AUTHENTICATION_MODEL = "9";
            public static final String REQUIRED_FIELD_MISSING = "10";
            public static final String APPLICATION_ERROR = "13";
            public static final String PROCESSING_EXCEPTION = "12";
        }

        public static final class Code {
            public static final String PASSWORD_INVALID = "175";
            public static final String UNDETERMINED_ERROR = "197";
            public static final String INVALID_VALUE = "320";
            public static final String REQUIRED_FIELD_MISSING = "321";
            public static final String INVALID_PROPERTY_CODE = "400";
            public static final String INVALID_ROOM_TYPE_CODE = "402";
            public static final String NO_HOTEL_MATCH_FOUND = "424";
            public static final String UNABLE_TO_PROCESS = "450";
            public static final String INVALID_CURRENCY = "558";
        }
    }
}
