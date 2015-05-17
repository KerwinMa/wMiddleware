/*
 *  PropertiesException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */

package com.witbooking.middleware.exceptions;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 5-may-2013
 * @version 1.0
 * @since 
 */
public class PropertiesException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;


    public PropertiesException(Exception ex, String code, String description, String userMessage) {
        super(ex, code, description, userMessage);
    }

    public PropertiesException(Exception ex) {
        super(ex);
    }

    public PropertiesException(MiddlewareException ex) {
        super(ex);
    }

    public PropertiesException() {
        super();
    }

    public PropertiesException(String code, String description,
            String userMessage) {
        super(code, description, userMessage);
    }

    public static PropertiesException propertyFileNotFound(Exception ex) {
        return new PropertiesException(ex, ERR_PROP_FILE_NOTFOUND, DERR_PROP_FILE_NOTFOUND, DERR_PROP_FILE_NOTFOUND);
    }

    public static PropertiesException propertyNotFound(String property) {
        return new PropertiesException(new Exception(property + " " + DERR_PROP_NOTFOUND), ERR_PROP_NOTFOUND, property + " " + DERR_PROP_NOTFOUND, property + " " + DERR_PROP_FILE_NOTFOUND);
    }
}
