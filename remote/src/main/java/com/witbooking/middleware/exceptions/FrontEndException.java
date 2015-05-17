/*
 *  FrontEndException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 08-may-2013
 * @version 1.0
 * @since
 */
public class FrontEndException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>FrontEndException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public FrontEndException(String msg) {
      super(ERR_FRONTEND_SERVICE, DESERR_FRONTEND_SERVICE, msg);
   }

   public FrontEndException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public FrontEndException(Exception ex) {
      super(ex, ERR_FRONTEND_SERVICE, ex.getMessage(), DESERR_FRONTEND_SERVICE);
   }

   public FrontEndException(Exception ex, String message) {
      super(ex, ERR_FRONTEND_SERVICE, DESERR_FRONTEND_SERVICE, message);
   }

   public FrontEndException(MiddlewareException ex) {
      super(ex);
   }
}
