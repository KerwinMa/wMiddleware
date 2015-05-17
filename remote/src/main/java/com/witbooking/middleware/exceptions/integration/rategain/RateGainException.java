/*
 *  RateGainException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions.integration.rategain;

import com.witbooking.middleware.exceptions.MiddlewareException;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 08-may-2013
 * @version 1.0
 * @since
 */
public class RateGainException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>RateGainException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public RateGainException(String msg) {
      super(ERR_RATEGAIN_SERVICE, DESERR_RATEGAIN_SERVICE, msg);
   }

   public RateGainException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public RateGainException(Exception ex) {
      super(ex, ERR_RATEGAIN_SERVICE, ex.getMessage(), DESERR_RATEGAIN_SERVICE);
   }

   public RateGainException(Exception ex, String message) {
      super(ex, ERR_RATEGAIN_SERVICE, DESERR_RATEGAIN_SERVICE, message);
   }

   public RateGainException(MiddlewareException ex) {
      super(ex);
   }
}
