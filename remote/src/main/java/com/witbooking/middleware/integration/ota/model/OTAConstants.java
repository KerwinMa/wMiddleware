package com.witbooking.middleware.integration.ota.model;

/**
 * OTAConstants.java
 * User: jose
 * Date: 9/27/13
 * Time: 11:22 AM
 */
public class OTAConstants {

    public static final class Error {

        public static final class Type {

            public static final String UNKNOWN = "1";
            public static final String NO_IMPLEMENTATION = "2";
            public static final String BIZ_RULE = "3";
            public static final String AUTHENTICATION = "4";
            public static final String AUTHORIZATION = "6";
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
            public static final String NO_HOTEL_MATCH_FOUND = "424";
            public static final String UNABLE_TO_PROCESS = "450";
            public static final String INVALID_CURRENCY = "558";
        }
    }
}