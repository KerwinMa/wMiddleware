/*
 *  InvalidEntryException.java
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
public class InvalidEntryException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>InvalidEntryException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public InvalidEntryException(String msg) {
      super(ERR_INVALID_ENTRY_VALUE, msg, DESERR_INVALID_ENTRY_VALUE);
   }

   public InvalidEntryException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public InvalidEntryException(Exception ex) {
      super(ex, ERR_INVALID_ENTRY_VALUE, ex.getMessage(), DESERR_INVALID_ENTRY_VALUE);
   }

   public InvalidEntryException(Exception ex, String message) {
      super(ex, ERR_INVALID_ENTRY_VALUE, message, DESERR_INVALID_ENTRY_VALUE);
   }

   public InvalidEntryException(MiddlewareException ex) {
      super(ex);
   }
}
