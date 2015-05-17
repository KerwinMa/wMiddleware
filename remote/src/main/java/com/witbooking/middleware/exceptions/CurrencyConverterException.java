/*
 *  CurrencyConverterException.java
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
public class CurrencyConverterException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>CurrencyConverterException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public CurrencyConverterException(String msg) {
      super(ERR_CONVERTER_CURRENCY, msg, DESERR_CONVERTER_CURRENCY);
   }

   public CurrencyConverterException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public CurrencyConverterException(Exception ex) {
      super(ex, ERR_CONVERTER_CURRENCY, ex.getMessage(), DESERR_CONVERTER_CURRENCY);
   }
   public CurrencyConverterException(MiddlewareException ex) {
      super(ex);
   }
}
