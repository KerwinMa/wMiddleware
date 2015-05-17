/*
 *  ScrappingException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 04-abr-2013
 * @version 1.0
 * @since
 */
public class ScrappingException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>ScrappingException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public ScrappingException(String msg) {
      super(ERR_SCRAPPING_SERVICE, DESERR_SCRAPPING_SERVICE, msg);
   }

   public ScrappingException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public ScrappingException(Exception ex) {
      super(ex, ERR_SCRAPPING_SERVICE, ex.getMessage(), DESERR_SCRAPPING_SERVICE);
   }

   public ScrappingException(Exception ex, String message) {
      super(ex, ERR_SCRAPPING_SERVICE, DESERR_SCRAPPING_SERVICE, message);
   }

   public ScrappingException(MiddlewareException ex) {
      super(ex);
   }
}
