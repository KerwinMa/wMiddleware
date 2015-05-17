/*
 *  CurrencyExchange.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.witbooking.middleware.exceptions.NonexistentValueException;

import java.io.Serializable;
import java.util.Map;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 15-abr-2013
 * @version 1.0
 * @since
 */
public class CurrencyExchange implements Serializable {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;
   //Base Currency to convert
   private String currency;
   //Prices for every currency to convert Map<Currency,Price>
   private Map<String, Float> prices;

   /**
    * Creates a new instance of
    * <code>CurrencyExchange</code> without params.
    */
   public CurrencyExchange() {
   }

   public CurrencyExchange(String currency, Map<String, Float> prices) {
      this.currency = currency;
      this.prices = prices;
   }

   public String getCurrencyBase() {
      return currency;
   }

   public void setCurrencyBase(String currency) {
      this.currency = currency;
   }

   public Map<String, Float> getPrices() {
      return prices;
   }

   public void setPrices(Map<String, Float> prices) {
      this.prices = prices;
   }

   public float getPrice(String currencyToConvert) throws NonexistentValueException {
       if (this.prices.containsKey(currencyToConvert)) {
           return this.prices.get(currencyToConvert);
       } else {
           throw new NonexistentValueException("Invalid Currency Value '" + currencyToConvert + "'");
       }
   }

    @Override
    public String toString() {
        String ret = "CurrencyExchange{" + "currency=" + currency + ", prices= {";
        for (Map.Entry<String, Float> entry : prices.entrySet()){
            ret += entry.getKey()+":"+entry.getValue()+", ";
        }
        ret += "} }";
        return ret;
    }



}
