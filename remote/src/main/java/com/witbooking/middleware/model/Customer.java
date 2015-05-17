/*
 *  Customer.java
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
 * @date 28-ene-2013
 * @version 1.0
 * @since
 */
public class Customer implements Serializable {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;
   private String id;
   private String personalId;
   private String givenName;
   private String surName;
   private String email;
   private String telephone;
   private CreditCard creditCard;
   private boolean mailOption;
   private String country;
   private String address;
   private String ipOrder;

   /**
    * Creates a new instance of
    * <code>Customer</code> without params.
    */
   public Customer() {
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getGivenName() {
      return givenName;
   }

   public void setGivenName(String givenName) {
      this.givenName = givenName;
   }

   public String getSurName() {
      return surName;
   }

   public void setSurName(String surName) {
      this.surName = surName;
   }

   public String getCompleteName(){
       final String name = this.givenName == null ? "" : this.givenName;
       final String surName = this.surName == null ? "" : this.surName;
       return surName.equals("") ? name : name.equals("") ? surName : surName + ", " + name;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getTelephone() {
      return telephone;
   }

   public void setTelephone(String telephone) {
      this.telephone = telephone;
   }

   public CreditCard getCreditCard() {
      return creditCard;
   }

   public void setCreditCard(CreditCard creditCard) {
      this.creditCard = creditCard;
   }

   public boolean isMailOption() {
      return mailOption;
   }

   public void setMailOption(boolean mailOption) {
      this.mailOption = mailOption;
   }

   public String getPersonalId() {
      return personalId;
   }

   public void setPersonalId(String personalId) {
      this.personalId = personalId;
   }

   public String getCountry() {
      return country;
   }

   public void setCountry(String country) {
      this.country = country;
   }

   public String getIpOrder() {
      return ipOrder;
   }

   public void setIpOrder(String ipOrder) {
      this.ipOrder = ipOrder;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }



   @Override
   public String toString() {
      return "Customer{" + "id=" + id + ", personalId=" + personalId + ", givenName=" + givenName + ", surName=" + surName + ", email=" + email + ", telephone=" + telephone + ", creditCard=" + creditCard + ", mailOption=" + mailOption + ", country=" + country + ", address=" + address + ", ipOrder=" + ipOrder + '}';
   }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Customer other = (Customer) obj;
        if ((this.personalId == null) ? (other.personalId != null) : !this.personalId.equals(other.personalId)) {
            return false;
        }
        if ((this.givenName == null) ? (other.givenName != null) : !this.givenName.equals(other.givenName)) {
            return false;
        }
        if ((this.surName == null) ? (other.surName != null) : !this.surName.equals(other.surName)) {
            return false;
        }
        if ((this.email == null) ? (other.email != null) : !this.email.equals(other.email)) {
            return false;
        }
        if ((this.telephone == null) ? (other.telephone != null) : !this.telephone.equals(other.telephone)) {
            return false;
        }
        if (this.creditCard != other.creditCard && (this.creditCard == null || !this.creditCard.equals(other.creditCard))) {
            return false;
        }
        if (this.mailOption != other.mailOption) {
            return false;
        }
        if ((this.country == null) ? (other.country != null) : !this.country.equals(other.country)) {
            return false;
        }
        if ((this.address == null) ? (other.address != null) : !this.address.equals(other.address)) {
            return false;
        }
        if ((this.ipOrder == null) ? (other.ipOrder != null) : !this.ipOrder.equals(other.ipOrder)) {
            return false;
        }
        return true;
    }




}
