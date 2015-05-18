/*
 *  AvailabilityRequest.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.trivago;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 08-abr-2013
 * @version 1.0
 * @since
 */
@XmlRootElement(name = "availabilityResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class AvailabilityResponse implements Serializable {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;
   @XmlElement(name = "hotel")
   private List<HotelRate> hotels;
   @XmlElement(name = "error")
   private String error;

   public AvailabilityResponse() {
      this.hotels = new ArrayList<HotelRate>();
   }

   public AvailabilityResponse(List<HotelRate> hotels) {
      this.hotels = hotels;
   }

   public AvailabilityResponse(String error) {
      this.error = error;
   }

   public List<HotelRate> getHotels() {
      return hotels;
   }

   public void setHotels(List<HotelRate> hotels) {
      this.hotels = hotels;
   }

   public String getError() {
      return error;
   }

   public void setError(String error) {
      this.error = error;
   }

   @Override
   public String toString() {
      return "AvailabilityResponse{" + "hotels=" + hotels + '}';
   }

   @XmlRootElement(name = "hotel")
   @XmlAccessorType(XmlAccessType.FIELD)
   public static class HotelRate {

      @XmlAttribute(name = "id")
      private String id;
      @XmlElement(name = "room")
      private List<Room> rooms;

      public HotelRate() {
      }

      public HotelRate(String id, List<Room> rooms) {
         this.id = id;
         this.rooms = rooms;
      }

      public String getId() {
         return id;
      }

      public void setId(String id) {
         this.id = id;
      }

      public HotelRate(List<Room> rooms) {
         this.rooms = rooms;
      }

      public List<Room> getRooms() {
         return rooms;
      }

      public void setRooms(List<Room> rooms) {
         this.rooms = rooms;
      }

      @Override
      public String toString() {
         return "HotelRate{" + "id=" + id + ", rooms=" + rooms + '}';
      }
   }

   @XmlRootElement(name = "room")
   @XmlAccessorType(XmlAccessType.FIELD)
   public static class Room {

      @XmlAttribute(name = "name")
      private String name;
      @XmlElement(name = "rate")
      private Rate rate;

      public Room() {
      }

      public Room(String name, Rate rate) {
         this.name = name;
         this.rate = rate;
      }

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public Rate getRate() {
         return rate;
      }

      public void setRate(Rate rate) {
         this.rate = rate;
      }

      @Override
      public String toString() {
         return "Room{" + "name=" + name + ", rate=" + rate + '}';
      }
   }

   @XmlRootElement(name = "rate")
   @XmlAccessorType(XmlAccessType.FIELD)
   public static class Rate {

      @XmlAttribute(name = "name")
      private String name;
      @XmlElement(name = "price")
      private Price price;
      @XmlElement(name = "creditcard")
      private CreditCard creditCard;
      @XmlElement(name = "cancellation")
      private Cancellation cancellation;

      public Rate() {
      }

      public Rate(String name, Price price, CreditCard creditCard,
              Cancellation cancellation) {
         this.name = name;
         this.price = price;
         this.cancellation = cancellation;
         this.creditCard = creditCard;
      }

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public Price getPrice() {
         return price;
      }

      public void setPrice(Price price) {
         this.price = price;
      }

      public CreditCard getCreditCard() {
         return creditCard;
      }

      public void setCreditCard(CreditCard creditCard) {
         this.creditCard = creditCard;
      }

      public Cancellation getCancellation() {
         return cancellation;
      }

      public void setCancellation(Cancellation cancellation) {
         this.cancellation = cancellation;
      }

      @Override
      public String toString() {
         return "Rate{" + "name=" + name + ", price=" + price + ", creditCard=" + creditCard + ", cancellation=" + cancellation + '}';
      }
   }

   @XmlRootElement(name = "creditcard")
   @XmlAccessorType(XmlAccessType.FIELD)
   public static class CreditCard {

      @XmlAttribute(name = "required")
      private boolean required;
      //For now, this value is always true in agreement with Trivago.
      //Because, in WitBooker, the CC is always required. 

      public CreditCard() {
      }

      public boolean isRequired() {
         return required;
      }

      public void setRequired(boolean required) {
         this.required = required;
      }

      @Override
      public String toString() {
         return required + "";
      }
   }

   @XmlRootElement(name = "cancellation")
   @XmlAccessorType(XmlAccessType.FIELD)
   public static class Cancellation {

      @XmlAttribute(name = "possible")
      private boolean possible;

      public Cancellation() {
      }

      public Cancellation(boolean possible) {
         this.possible = possible;
      }

      public boolean isPossible() {
         return possible;
      }

      public void setPossible(boolean possible) {
         this.possible = possible;
      }

      @Override
      public String toString() {
         return possible + "";
      }
   }

   @XmlRootElement(name = "price")
   @XmlAccessorType(XmlAccessType.PROPERTY)
   @XmlType(propOrder = {"netPriceFormatted", "cityTaxFormatted", "feesOtherTaxFormatted", "currency", "breakfast"})
   public static class Price {

      private float netPrice;
      //For now, this value is always 0 in agreement with Trivago
      private float cityTax = 0;
      //For now, this value is always 0 in agreement with Trivago.
      private float feesOtherTax = 0;
      private String currency;
      private Breakfast breakfast;

      public Price() {
      }

      public Price(float netPrice, String currency, Breakfast breakfast) {
         this.netPrice = netPrice;
         this.currency = currency;
         this.breakfast = breakfast;
      }

      @XmlElement(name = "netprice")
      public String getNetPriceFormatted() {
         return new DecimalFormat("0.##").format(netPrice);
      }

      public void setNetPriceFormatted(String price) {
         this.netPrice = Float.parseFloat(price);
      }

      @XmlTransient
      public float getNetPrice() {
         return netPrice;
      }

      public void setNetPrice(float netPrice) {
         this.netPrice = netPrice;
      }

      @XmlElement(name = "city_tax")
      public String getCityTaxFormatted() {
         return new DecimalFormat("0.00").format(cityTax);
      }

      public void setCityTaxFormatted(String cityTax) {
         this.cityTax = Float.parseFloat(cityTax);
      }

      @XmlTransient
      public float getCityTax() {
         return cityTax;
      }

      public void setCityTax(float cityTax) {
         this.cityTax = cityTax;
      }

      @XmlElement(name = "fees_other_taxes")
      public String getFeesOtherTaxFormatted() {
         return new DecimalFormat("0.00").format(feesOtherTax);
      }

      public void setFeesOtherTaxFormatted(String feesOtherTax) {
         this.feesOtherTax = Float.parseFloat(feesOtherTax);
      }

      @XmlTransient
      public float getFeesOtherTax() {
         return feesOtherTax;
      }

      public void setFeesOtherTax(float feesOtherTax) {
         this.feesOtherTax = feesOtherTax;
      }

      @XmlElement(name = "currency")
      public String getCurrency() {
         return currency;
      }

      public void setCurrency(String currency) {
         this.currency = currency;
      }

      @XmlElement(name = "breakfast")
      public Breakfast getBreakfast() {
         return breakfast;
      }

      public void setBreakfast(Breakfast breakfast) {
         this.breakfast = breakfast;
      }

      @Override
      public String toString() {
         return "Price{" + "netPrice=" + netPrice + ", cityTax=" + cityTax + ", feesOtherTax=" + feesOtherTax + ", currency=" + currency + ", breakfast=" + breakfast + '}';
      }
   }

   @XmlRootElement(name = "breakfast")
   @XmlAccessorType(XmlAccessType.PROPERTY)
   public static class Breakfast {

      private boolean breakfastIncluded;
      //For now, this value is always 0 in agreement with Trivago
      //Because the breakfast's Price is include in the netPrice. 
      private float breakfastPrice = 0;

      public Breakfast() {
      }

      public Breakfast(boolean breakfastIncluded) {
         this.breakfastIncluded = breakfastIncluded;
      }

      @XmlAttribute(name = "included")
      public boolean isBreakfastIncluded() {
         return breakfastIncluded;
      }

      public void setBreakfastIncluded(boolean breakfastIncluded) {
         this.breakfastIncluded = breakfastIncluded;
      }

      @XmlAttribute(name = "price")
      public String getBreakfastPriceFormatted() {
         return new DecimalFormat("0.00").format(breakfastPrice);
      }

      public void setBreakfastPriceFormatted(String breakfastPrice) {
         this.breakfastPrice = Float.parseFloat(breakfastPrice);
      }

      public void setBreakfastPrice(float breakfastPrice) {
         this.breakfastPrice = breakfastPrice;
      }

      @XmlTransient
      public float getBreakfastPrice() {
         return breakfastPrice;
      }

      @Override
      public String toString() {
         return "Breakfast{" + "breakfastIncluded=" + breakfastIncluded + ", breakfastPrice=" + breakfastPrice + '}';
      }
   }
}
