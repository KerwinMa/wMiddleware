/*
 *  FrontEndException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions;

/**
 *
 *
 * @author Jorge Lucic
 * @version 1.0
 * @date 11/29/14
 * @since 1.0
 */
 public class BookingPriceRuleException extends MiddlewareException {

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
   public BookingPriceRuleException(String msg) {
      super(ERR_BOOKING_PRICE_RULE_SERVICE, DESERR_BOOKING_PRICE_RULE_SERVICE, msg);
   }

   public BookingPriceRuleException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public BookingPriceRuleException(Exception ex) {
      super(ex, ERR_BOOKING_PRICE_RULE_SERVICE, ex.getMessage(), DESERR_BOOKING_PRICE_RULE_SERVICE);
   }

   public BookingPriceRuleException(Exception ex, String message) {
      super(ex, ERR_BOOKING_PRICE_RULE_SERVICE, DESERR_BOOKING_PRICE_RULE_SERVICE, message);
   }

   public BookingPriceRuleException(MiddlewareException ex) {
      super(ex);
   }
}
