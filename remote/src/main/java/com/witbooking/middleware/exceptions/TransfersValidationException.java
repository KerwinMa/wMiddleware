/*
 *  TransfersValidationException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 31-oct-2014
 * @version 1.0
 * @since
 */
public class TransfersValidationException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>TransfersValidationException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public TransfersValidationException(String msg) {
      super(ERR_TRANSFERS_VALIDATION, DESERR_TRANSFERS_VALIDATION, msg);
   }

   public TransfersValidationException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public TransfersValidationException(Exception ex) {
      super(ex, ERR_TRANSFERS_VALIDATION, ex.getMessage(), DESERR_TRANSFERS_VALIDATION);
   }

   public TransfersValidationException(Exception ex, String message) {
      super(ex, ERR_TRANSFERS_VALIDATION, DESERR_TRANSFERS_VALIDATION, message);
   }

   public TransfersValidationException(MiddlewareException ex) {
      super(ex);
   }
}
