/*
 *  TripAdvisorException.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.exceptions.integration.tripadvisor;

import com.witbooking.middleware.exceptions.MiddlewareException;

/**
 * Insert description here
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 29, 2013
 */
public final class TripAdvisorException extends MiddlewareException {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    
       /**
    * Constructs an instance of
    * <code>TripAdvisorException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public TripAdvisorException(String msg) {
      super(ERR_TRIPADVISOR_SERVICE, DESERR_TRIPADVISOR_SERVICE, msg);
   }

   public TripAdvisorException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public TripAdvisorException(Exception ex) {
      super(ex, ERR_TRIPADVISOR_SERVICE, ex.getMessage(), DESERR_TRIPADVISOR_SERVICE);
   }

   public TripAdvisorException(Exception ex, String message) {
      super(ex, ERR_TRIPADVISOR_SERVICE, DESERR_TRIPADVISOR_SERVICE, message);
   }

   public TripAdvisorException(MiddlewareException ex) {
      super(ex);
   }
}
