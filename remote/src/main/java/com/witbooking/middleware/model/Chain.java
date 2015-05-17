/*
 *  Chain.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.util.List;
import java.util.Properties;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 15-may-2013
 * @version 1.0
 * @since
 */
public class Chain extends Establishment {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;
   private List<Establishment> establishments;

   /**
    * Creates a new instance of
    * <code>Chain</code> without params.
    */
   public Chain() {
   }

   public Chain(List<Establishment> establishments) {
      this.establishments = establishments;
   }

   public Chain(Integer id, String ticker, String name, String description, String phone,
           String emailAdmin, boolean active, Properties configuration) {
      super(id, ticker, name, description, phone, emailAdmin, active, configuration);
   }

   public Chain(List<Establishment> establishments, Integer id, String ticker, String name,
           String description, String phone, String emailAdmin, boolean active, Properties configuration) {
      super(id, ticker, name, description, phone, emailAdmin, active, configuration);
      this.establishments = establishments;
   }

   public List<Establishment> getEstablishments() {
      return establishments;
   }

   public void setEstablishments(List<Establishment> establishments) {
      this.establishments = establishments;
   }
}
