/*
 *  ExternalFileException.java
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
public class ExternalFileException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>ExternalFileException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public ExternalFileException(String msg) {
      super(ERR_READ_FILE, msg, DESERR_READ_FILE);
   }

   public ExternalFileException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public ExternalFileException(Exception ex) {
      super(ex, ERR_READ_FILE, ex.getMessage(), DESERR_READ_FILE);
   }

   public ExternalFileException(Exception ex, String fileName) {
      super(ex, ERR_READ_FILE, ex.getMessage(), fileName + " " + DESERR_READ_FILE);
   }

   public ExternalFileException(MiddlewareException ex) {
      super(ex);
   }
}
