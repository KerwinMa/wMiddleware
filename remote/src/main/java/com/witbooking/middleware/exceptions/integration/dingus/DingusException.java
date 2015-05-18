/*
 *  DingusException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions.integration.dingus;

import com.witbooking.middleware.exceptions.MiddlewareException;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 04-mar-2015
 * @version 1.0
 * @since
 */
public class DingusException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>DingusException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public DingusException(String msg) {
      super(ERR_DINGUS_SERVICE, DESERR_DINGUS_SERVICE, msg);
   }

   public DingusException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public DingusException(Exception ex) {
      super(ex, ERR_DINGUS_SERVICE, ex.getMessage(), DESERR_DINGUS_SERVICE);
   }

   public DingusException(Exception ex, String message) {
      super(ex, ERR_DINGUS_SERVICE, DESERR_DINGUS_SERVICE, message);
   }

   public DingusException(MiddlewareException ex) {
      super(ex);
   }
}
