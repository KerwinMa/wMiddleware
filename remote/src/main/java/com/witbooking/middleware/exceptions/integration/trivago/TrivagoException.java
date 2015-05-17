/*
 *  TrivagoException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions.integration.trivago;

import com.witbooking.middleware.exceptions.MiddlewareException;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 04-abr-2013
 * @version 1.0
 * @since
 */
public class TrivagoException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>TrivagoException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public TrivagoException(String msg) {
      super(ERR_TRIVAGO_SERVICE, DESERR_TRIVAGO_SERVICE, msg);
   }

   public TrivagoException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public TrivagoException(Exception ex) {
      super(ex, ERR_TRIVAGO_SERVICE, ex.getMessage(), DESERR_TRIVAGO_SERVICE);
   }

   public TrivagoException(Exception ex, String message) {
      super(ex, ERR_TRIVAGO_SERVICE, DESERR_TRIVAGO_SERVICE, message);
   }

   public TrivagoException(MiddlewareException ex) {
      super(ex);
   }
}
