/*
 *  RemoteServiceException.java
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
public class RemoteServiceException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>RemoteServiceException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public RemoteServiceException(String msg) {
      super(ERR_REMOTE_SERVICE, DESERR_REMOTE_SERVICE, msg);
   }

   public RemoteServiceException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public RemoteServiceException(Exception ex) {
      super(ex, ERR_REMOTE_SERVICE, ex.getMessage(), DESERR_REMOTE_SERVICE);
   }

   public RemoteServiceException(Exception ex, String message) {
      super(ex, ERR_REMOTE_SERVICE, DESERR_REMOTE_SERVICE, message);
   }

   public RemoteServiceException(MiddlewareException ex) {
      super(ex);
   }
}
