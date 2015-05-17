/*
 *  NonexistentValueException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */

package com.witbooking.middleware.exceptions.beans;

import com.witbooking.middleware.exceptions.MiddlewareException;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 25-mar-2013
 * @version 1.0
 * @since 
 */
public class AdmARIException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

    
    /**
     * Constructs an instance of <code>AdmARIException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public AdmARIException(String msg) {
        super(ERR_TICKER_NOT_EXIST, msg, DESERR_TICKER_NOT_EXIST);
    }
    
    public AdmARIException(MiddlewareException ex) {
        super(ex);
    }
    
    public AdmARIException(Exception ex, String code, String description, String userMessage) {
        super(ex, code, description, userMessage);
    }

    public AdmARIException(Exception ex) {
        super(ex, ERR_TICKER_NOT_EXIST, ex.getMessage(), DESERR_TICKER_NOT_EXIST);
    }
    
    public AdmARIException(Exception ex, String message) {
        super(ex, ERR_TICKER_NOT_EXIST, message, DESERR_TICKER_NOT_EXIST);
    }    
      



}
