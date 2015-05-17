/*
 *  Guest.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 24-ene-2013
 * @version 1.0
 * @since
 */
public class Guest implements Serializable {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String ticker;
   private String name;
   private int ageQualifyingCode;//OTA Code   
   //Tipe Of Guest
   private static Guest ADULT = new Guest(1, "adult", "Adult", 10);
   private static Guest CHILD = new Guest(2, "child", "Child", 8);
   private static Guest INFANT = new Guest(3, "infant", "Infant", 7);
   private static Guest SENIOR = new Guest(4, "senior", "Senior", 11);
   /**
    * Creates a new instance of
    * <code>Guest</code> without params.
    */
   public Guest() {
   }

   public Guest(Integer id, String ticker, String name, int ageQualifyingCode) {
      this.id = id;
      this.ticker = ticker;
      this.name = name;
      this.ageQualifyingCode = ageQualifyingCode;
   }

   public static Guest getAdult() {
      return ADULT;
   }

   public static Guest getChild() {
      return CHILD;
   }

   public static Guest getInfant() {
      return INFANT;
   }

    public boolean isAdult() {
        return "adult".equalsIgnoreCase(ticker);
    }

    public boolean isChild() {
        return "child".equalsIgnoreCase(ticker);
    }

    public boolean isInfant() {
        return "infant".equalsIgnoreCase(ticker);
    }

   public static Guest getSenior() {
      //TODO:PmsXchange Senior value
      return SENIOR;
   }

   @Override
   public String toString() {
      return ticker;
   }

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getTicker() {
      return ticker;
   }

   public void setTicker(String ticker) {
      this.ticker = ticker;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getAgeQualifyingCode() {
      return ageQualifyingCode;
   }

   public void setAgeQualifyingCode(int ageQualifyingCode) {
      this.ageQualifyingCode = ageQualifyingCode;
   }
}
