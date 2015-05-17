/*
 *  MailingException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 22-nov-2013
 * @version 1.0
 * @since
 */
public class EmailDataException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>MailingException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public EmailDataException(String msg) {
      super(ERR_EMAIL_DATA_SERVICE, DESERR_EMAIL_DATA_SERVICE, msg);
   }

   public EmailDataException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public EmailDataException(Exception ex) {
      super(ex, ERR_EMAIL_DATA_SERVICE, ex.getMessage(), DESERR_EMAIL_DATA_SERVICE);
   }

   public EmailDataException(Exception ex, String message) {
      super(ex, ERR_EMAIL_DATA_SERVICE, DESERR_EMAIL_DATA_SERVICE, message);
   }

   public EmailDataException(MiddlewareException ex) {
      super(ex);
   }
}
