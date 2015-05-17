/*
 *  TrivagoException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions.integration.booking;

import com.witbooking.middleware.exceptions.MiddlewareException;

/**
 * Insert description of the Exception here
 *
 * @author Jose Francisco Fiorillo
 * @date 25-Julio-2013
 * @version 1.0
 * @since
 */
public final class BookingException extends MiddlewareException {
    
   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>BookingException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public BookingException(String msg) {
      super(ERR_BOOKING_SERVICE, DESERR_BOOKING_SERVICE, msg);
   }

   public BookingException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public BookingException(Exception ex) {
      super(ex, ERR_BOOKING_SERVICE, ex.getMessage(), DESERR_BOOKING_SERVICE);
   }

   public BookingException(Exception ex, String message) {
      super(ex, ERR_BOOKING_SERVICE, DESERR_BOOKING_SERVICE, message);
   }

   public BookingException(MiddlewareException ex) {
      super(ex);
   }
   
   public static final String USER_OR_PASS_OR_CHANNEL_NULL = " Try to finds the user and password and was not found." ;
}
