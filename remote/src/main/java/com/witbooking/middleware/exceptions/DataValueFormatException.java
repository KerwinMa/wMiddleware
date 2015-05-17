/*
 *  DBAccessException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 18-ene-2013
 * @version 1.0
 * @since
 */
public class DataValueFormatException extends MiddlewareException {

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
   public DataValueFormatException(String msg) {
      super(ERR_DATA_VALUE_FORMAT, msg, DESERR_DATA_VALUE_FORMAT);
   }

   public DataValueFormatException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public DataValueFormatException(Exception ex) {
      super(ex, ERR_DATA_VALUE_FORMAT, ex.getMessage(), DESERR_DATA_VALUE_FORMAT);
   }

   public DataValueFormatException(Exception ex, String message) {
      super(ex, ERR_DATA_VALUE_FORMAT, message, DESERR_DATA_VALUE_FORMAT);
   }

   public DataValueFormatException(MiddlewareException ex) {
      super(ex);
   }
}
