/*
 *  WitChannelAPIException.java
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
 * @date 04-abr-2013
 */
public class WitBookerAPIException extends MiddlewareException {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an instance of
     * <code>WitBookerAPIException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public WitBookerAPIException(String msg) {
        super(ERR_WITCHANNELAPI_SERVICE, DESERR_WITCHANNELAPI_SERVICE, msg);
    }

    public WitBookerAPIException(String description, String userMessage) {
        super(ERR_WITCHANNELAPI_SERVICE, description, userMessage);
    }

    public WitBookerAPIException(Exception ex, String code, String description, String userMessage) {
        super(ex, code, description, userMessage);
    }

    public WitBookerAPIException(Exception ex) {
        super(ex, ERR_WITCHANNELAPI_SERVICE, ex.getMessage(), DESERR_WITCHANNELAPI_SERVICE);
    }

    public WitBookerAPIException(Exception ex, String message) {
        super(ex, ERR_WITCHANNELAPI_SERVICE, DESERR_WITCHANNELAPI_SERVICE, message);
    }

    public WitBookerAPIException(MiddlewareException ex) {
        super(ex);
    }
}
