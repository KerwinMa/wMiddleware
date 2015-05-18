/*
 *  AvailabilityRequest.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.trivago;

import java.io.Serializable;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 08-abr-2013
 * @version 1.0
 * @since
 */
@XmlRootElement(name = "availabilityRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"username", "password", "hotelIdentifier", "arrivalDate",
   "departureDate", "adults", "children", "currency", "language"})
public class AvailabilityRequest implements Serializable {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;
   @XmlElementWrapper(name = "hotelIdentifier")
   @XmlElement(name = "hotelId")
   private List<String> hotelIdentifier;
   private String arrivalDate;
   private String departureDate;
   private int adults;
   private int children;
   private String currency;
   private String language;
   private String username;
   private String password;

   /**
    * Creates a new instance of
    * <code>AvailabilityRequest</code> without params.
    */
   public AvailabilityRequest() {
   }

   public AvailabilityRequest(List<String> hotelIdentifier, String arrivalDate,
           String departureDate, int adults, int children, String currency,
           String language, String username, String password) {
      this.hotelIdentifier = hotelIdentifier;
      this.arrivalDate = arrivalDate;
      this.departureDate = departureDate;
      this.adults = adults;
      this.children = children;
      this.currency = currency;
      this.language = language;
      this.username = username;
      this.password = password;
   }

   public List<String> getHotelIdentifier() {
      return hotelIdentifier;
   }

   public void setHotelIdentifier(List<String> hotelIdentifier) {
      this.hotelIdentifier = hotelIdentifier;
   }

   public String getArrivalDate() {
      return arrivalDate;
   }

   public void setArrivalDate(String arrivalDate) {
      this.arrivalDate = arrivalDate;
   }

   public String getDepartureDate() {
      return departureDate;
   }

   public void setDepartureDate(String departureDate) {
      this.departureDate = departureDate;
   }

   public int getAdults() {
      return adults;
   }

   public void setAdults(int adults) {
      this.adults = adults;
   }

   public int getChildren() {
      return children;
   }

   public void setChildren(int children) {
      this.children = children;
   }

   public String getCurrency() {
      return currency;
   }

   public void setCurrency(String currency) {
      this.currency = currency;
   }

   public String getLanguage() {
      return language;
   }

   public void setLanguage(String language) {
      this.language = language;
   }

   public String getUsername() {
      return username;
   }

   public void setUsername(String username) {
      this.username = username;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   @Override
   public String toString() {
      return "AvailabilityRequest{" + "username=" + username + ", password=" + password + ", hotelIdentifier=" + hotelIdentifier + ", arrivalDate=" + arrivalDate + ", departureDate=" + departureDate + ", adults=" + adults + ", children=" + children + ", currency=" + currency + ", language=" + language + '}';
   }
}
