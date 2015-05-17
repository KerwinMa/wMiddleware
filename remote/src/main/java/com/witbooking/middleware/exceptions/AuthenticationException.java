/*
 *  AuthenticationException.java
 * 
 * Copyright(c) 2014  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 08-may-2014
 */
public class AuthenticationException extends MiddlewareException {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    public AuthenticationException() {
        super(ERR_AUTHENTICATION, DESERR_AUTHENTICATION, "Authentication Error");
    }

    /**
     * Constructs an instance of
     * <code>RemoteServiceException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public AuthenticationException(String msg) {
        super(ERR_AUTHENTICATION, DESERR_AUTHENTICATION, msg);
    }

    public AuthenticationException(Exception ex, String code, String description, String userMessage) {
        super(ex, code, description, userMessage);
    }

    public AuthenticationException(Exception ex) {
        super(ex, ERR_AUTHENTICATION, ex.getMessage(), DESERR_AUTHENTICATION);
    }

    public AuthenticationException(Exception ex, String message) {
        super(ex, ERR_AUTHENTICATION, DESERR_AUTHENTICATION, message);
    }

    public AuthenticationException(MiddlewareException ex) {
        super(ex);
    }
}
