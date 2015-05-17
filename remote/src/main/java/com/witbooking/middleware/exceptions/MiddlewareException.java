/*
 *  MiddlewareException.java
 *
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions;

import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 18-ene-2013
 * @since 1.0
 */
public class MiddlewareException extends Exception implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    protected static final long serialVersionUID = -6204201188522055723L;
    private String code = null;
    private String description = null;
    private String userMessage = null;
    //Error Codes
    public static String ERR_UNKNOWN = "00";
    public static String ERR_PROP_FILE_NOTFOUND = "01";
    public static String ERR_PROP_NOTFOUND = "02";
    public static String ERR_DB_CONNECTION = "03";
    public static String ERR_DB_ACCESS = "04";
    public static String ERR_PARSE_DB_ENTITY = "05";
    public static String ERR_DATA_VALUE_FORMAT = "06";
    public static String ERR_TICKER_NOT_EXIST = "07";
    public static String ERR_PARSING_FORMULA = "08";
    public static String ERR_READ_FILE = "09";
    public static String ERR_TRIVAGO_SERVICE = "10";
    public static String ERR_CONVERTER_CURRENCY = "11";
    public static String ERR_SCRAPPING_SERVICE = "12";
    public static String ERR_FRONTEND_SERVICE = "13";
    public static String ERR_RATEGAIN_SERVICE = "14";
    public static String ERR_WITCHANNELAPI_SERVICE = "15";
    public static String ERR_REMOTE_SERVICE = "16";
    public static String ERR_BOOKING_SERVICE = "17";
    public static String ERR_TRIPADVISOR_SERVICE = "18";
    public static String ERR_INTEGRATION_SERVICE = "19";
    public static String ERR_MAILING_SERVICE = "20";
    public static String ERR_AUTHENTICATION = "21";
    public static String ERR_DATE_FORMAT = "22";
    public static String ERR_TRANSFERS_VALIDATION = "23";
    public static String ERR_BOOKING_PRICE_RULE_SERVICE = "24";
    public static String ERR_EMAIL_DATA_SERVICE = "25";
    public static String ERR_INVALID_ENTRY_VALUE = "26";

    ///
    public static String DESERR_UNKNOWN = "Unknown Error.";
    public static String DERR_PROP_FILE_NOTFOUND = "Configuration file not found.";
    public static String DERR_PROP_NOTFOUND = "Property not found in the Configuration file.";
    public static String DESERR_DB_CONNECTION = "DataBase Connection Error.";
    public static String DESERR_DB_ACCESS = "DataBase Error.";
    public static String DESERR_PARSE_DB_ENTITY = "Parse DB Object Error.";
    public static String DESERR_DATA_VALUE_FORMAT = "Data Value Format Error.";
    public static String DESERR_TICKER_NOT_EXIST = "The Ticker doesn't exist in the database.";
    public static String DESERR_PARSING_FORMULA = "Error Parsing Rate Formula Value.";
    public static String DESERR_TRIVAGO_SERVICE = "Error in the Trivago Web Services.";
    public static String DESERR_READ_FILE = "File can't be read.";
    public static String DESERR_CONVERTER_CURRENCY = "Error whit the Cureency Converter DB.";
    public static String DESERR_SCRAPPING_SERVICE = "Error in the Scrapping Services.";
    public static String DESERR_FRONTEND_SERVICE = "Error in the FrontEnd Services.";
    public static String DESERR_RATEGAIN_SERVICE = "Error in the RateGain Services.";
    public static String DESERR_WITCHANNELAPI_SERVICE = "Error in the WitBookingAPI Services";
    public static String DESERR_REMOTE_SERVICE = "Error accessing the remote services.";
    public static String DESERR_BOOKING_SERVICE = "Error in Booking Web services.";
    public static String DESERR_TRIPADVISOR_SERVICE = "Error in TripAdvisor Web services.";
    public static String DESERR_INTEGRATION_SERVICE = "Error in Integration services.";
    public static String DESERR_MAILING_SERVICE = "Error in the Mailing services.";
    public static String DESERR_AUTHENTICATION = "Authentication Error.";
    public static String DESERR_DATE_FORMAT = "Error in DateTime Value.";
    public static String DESERR_TRANSFERS_VALIDATION = "Error validating the Reservations with Transfers.";
    public static String DESERR_BOOKING_PRICE_RULE_SERVICE = "Error in the Booking Price Rules Service.";
    public static String DESERR_EMAIL_DATA_SERVICE = "Error in Email Data services.";
    public static String DESERR_INVALID_ENTRY_VALUE = "Invalid Entry Value.";

    /**
     * Creates a new instance of
     * <code>MiddlewareException</code> without params.
     */
    public MiddlewareException() {
        super();
        this.code = ERR_UNKNOWN;
        this.description = DESERR_UNKNOWN;
        this.userMessage = DESERR_UNKNOWN;
    }

    public MiddlewareException(Exception ex) {

        super(ex.getMessage(), ex.getCause());
        this.setStackTrace(ex.getStackTrace());
        this.code = ERR_UNKNOWN;
        this.description = ex.getMessage();
        this.userMessage = DESERR_UNKNOWN;
    }

    public MiddlewareException(MiddlewareException ex) {
        super(ex.getMessage(), ex.getCause());
        this.setStackTrace(ex.getStackTrace());
        this.code = ex.getCode();
        this.description = ex.getDescription();
        this.userMessage = ex.getUserMessage();
    }

    public MiddlewareException(String code, String description, String userMessage) {
        super(description);
        this.code = code;
        this.description = description;
        this.userMessage = userMessage;
    }

    public MiddlewareException(Exception ex, String code, String description, String userMessage) {
        super(ex.getMessage(), ex.getCause());
        this.setStackTrace(ex.getStackTrace());
        this.code = code;
        this.description = description;
        this.userMessage = userMessage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public void setUserMessage(String userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public String toString() {
        return "[" + this.code + "]: " + this.description + ": " + (this.getMessage().equals(this.userMessage) ? this.userMessage : this.userMessage + ": " + getMessage());
    }
}
