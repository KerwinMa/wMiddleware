/*
 *  DBAccessException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions.db;

import com.witbooking.middleware.exceptions.MiddlewareException;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 18-ene-2013
 */
public class DBAccessException extends MiddlewareException {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an instance of
     * <code>DBAccessException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public DBAccessException(String msg) {
        super(ERR_DB_ACCESS, msg, msg);
    }

    public DBAccessException(Exception ex, String code, String description, String userMessage) {
        super(ex, code, description, userMessage);
    }

    public DBAccessException(Exception ex) {
        super(ex, ERR_DB_ACCESS, ex.getMessage(), DESERR_DB_ACCESS);
    }

    public DBAccessException(Exception ex, String message) {
        super(ex, ERR_DB_ACCESS, message, DESERR_DB_ACCESS);
    }

    public DBAccessException(MiddlewareException ex) {
        super(ex);
    }

    public static DBAccessException dbConnectionError(Exception ex, String message) {
        return new DBAccessException(ex, ERR_DB_CONNECTION, message, DESERR_DB_CONNECTION);
    }

    public static DBAccessException dbParseEntityError(Exception ex, String message) {
        return new DBAccessException(ex, ERR_PARSE_DB_ENTITY, message, DESERR_PARSE_DB_ENTITY);
    }
}
