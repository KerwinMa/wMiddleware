/*
 *  DateFormatException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 12-may-2014
 */
public class DateFormatException extends MiddlewareException {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an instance of
     * <code>DateFormatException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DateFormatException(String msg) {
        super(ERR_DATE_FORMAT, msg, DESERR_DATE_FORMAT);
    }

    public DateFormatException(Exception ex, String code, String description, String userMessage) {
        super(ex, code, description, userMessage);
    }

    public DateFormatException(Exception ex) {
        super(ex, ERR_DATE_FORMAT, ex.getMessage(), DESERR_DATE_FORMAT);
    }

    public DateFormatException(Exception ex, String message) {
        super(ex, ERR_DATE_FORMAT, message, DESERR_DATE_FORMAT);
    }

    public DateFormatException(MiddlewareException ex) {
        super(ex);
    }
}
