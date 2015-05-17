/*
 *  CalculateFormulaException.java
 * 
 * Copyright(c) 2013  Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com.
 */
package com.witbooking.middleware.exceptions;

/**
 * Insert description of the Exception here
 *
 * @author Christian Delgado
 * @date 25-mar-2013
 * @version 1.0
 * @since
 */
public class CalculateFormulaException extends MiddlewareException {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   /**
    * Constructs an instance of
    * <code>DataValueFormatException</code> with the specified detail message.
    *
    * @param msg the detail message.
    */
   public CalculateFormulaException(String msg) {
      super(ERR_PARSING_FORMULA, msg, DESERR_PARSING_FORMULA);
   }

   public CalculateFormulaException(MiddlewareException ex) {
      super(ex);
   }

   public CalculateFormulaException(Exception ex, String code, String description, String userMessage) {
      super(ex, code, description, userMessage);
   }

   public CalculateFormulaException(Exception ex) {
      super(ex, ERR_PARSING_FORMULA, ex.getMessage(), DESERR_PARSING_FORMULA);
   }

   public CalculateFormulaException(Exception ex, String message) {
      super(ex, ERR_PARSING_FORMULA, message, DESERR_PARSING_FORMULA);
   }
}
