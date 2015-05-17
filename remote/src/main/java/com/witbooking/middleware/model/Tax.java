/*
 *  Tax.java
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
 * @date 01-feb-2013
 * @version 1.0
 * @since
 */
public class Tax implements Serializable {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String ticker;
   private String name;
   private float value;
//   private Date dateCreation;
//   private Date dateModification;

   /**
    * Creates a new instance of
    * <code>Tax</code> without params.
    */
   public Tax() {
   }

   public Tax(Integer id, String ticker, String name, float value) {
      this.id = id;
      this.ticker = ticker;
      this.name = name;
      this.value = value;
   }

//   public Tax(Integer id, String ticker, String name, float value, Date dateCreation, Date dateModification) {
//      this.id = id;
//      this.ticker = ticker;
//      this.name = name;
//      this.value = value;
//      this.dateCreation = dateCreation;
//      this.dateModification = dateModification;
//   }
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

   public float getValue() {
      return value;
   }

   public void setValue(float value) {
      this.value = value;
   }

//   public Date getDateCreation() {
//      return dateCreation;
//   }
//
//   public void setDateCreation(Date dateCreation) {
//      this.dateCreation = dateCreation;
//   }
//
//   public Date getDateModification() {
//      return dateModification;
//   }
//
//   public void setDateModification(Date dateModification) {
//      this.dateModification = dateModification;
//   }
   @Override
   public String toString() {
      return "Tax{" + "id=" + id + ", ticker=" + ticker + ", name=" + name + ", value=" + value + '}';
   }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tax other = (Tax) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.ticker == null) ? (other.ticker != null) : !this.ticker.equals(other.ticker)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (Float.floatToIntBits(this.value) != Float.floatToIntBits(other.value)) {
            return false;
        }
        return true;
    }
   
   
}
