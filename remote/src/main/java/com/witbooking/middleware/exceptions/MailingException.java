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
public class MailingException extends MiddlewareException {

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
   public MailingException(String msg) {
      super(ERR_MAILING_SERVICE, DESERR_MAILING_SERVICE, msg);
   }

   public MailingException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public MailingException(Exception ex) {
      super(ex, ERR_MAILING_SERVICE, ex.getMessage(), DESERR_MAILING_SERVICE);
   }

   public MailingException(Exception ex, String message) {
      super(ex, ERR_MAILING_SERVICE, DESERR_MAILING_SERVICE, message);
   }

   public MailingException(MiddlewareException ex) {
      super(ex);
   }
}
