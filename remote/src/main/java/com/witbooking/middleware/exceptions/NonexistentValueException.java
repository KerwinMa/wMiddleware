/*
 *  NonexistentValueException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 25-mar-2013
 * @version 1.0
 * @since
 */
public class NonexistentValueException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>DataValueFormatException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public NonexistentValueException(String msg) {
      super(ERR_TICKER_NOT_EXIST, msg, DESERR_TICKER_NOT_EXIST);
   }

   public NonexistentValueException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public NonexistentValueException(Exception ex) {
      super(ex, ERR_TICKER_NOT_EXIST, ex.getMessage(), DESERR_TICKER_NOT_EXIST);
   }

   public NonexistentValueException(Exception ex, String message) {
      super(ex, ERR_TICKER_NOT_EXIST, message, DESERR_TICKER_NOT_EXIST);
   }

   public NonexistentValueException(MiddlewareException ex) {
      super(ex);
   }
}
