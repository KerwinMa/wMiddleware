/*
 *  HotelList.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.trivago;

import com.witbooking.middleware.db.DBCredentials;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 04-abr-2013
 * @version 1.0
 * @since
 */
@XmlRootElement(name = "hotels")
@XmlAccessorType(XmlAccessType.FIELD)
public class HotelList implements Serializable {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   @XmlElement(name = "hotel")
   private List<HotelTrivago> hotels;
   @XmlElement(name = "error")
   private String error;

   public HotelList() {
      this.hotels = new ArrayList<HotelTrivago>();
   }

   public List<HotelTrivago> getHotels() {
      return hotels;
   }

   public void setHotels(List<HotelTrivago> hotels) {
      this.hotels = hotels;
   }

   public String getError() {
      return error;
   }

   public void setError(String error) {
      this.error = error;
   }

   public void add(HotelTrivago hotel) {
      hotels.add(hotel);
   }

   public HotelTrivago get(int index) {
      return hotels.get(index);
   }

   public HotelTrivago getHotelById(String idHotel) {
      if (idHotel == null || "".equals(idHotel)) {
         return null;
      }
      for (HotelTrivago trivagoHotel : hotels) {
         if (idHotel.equals(trivagoHotel.getId())) {
            return trivagoHotel;
         }
      }
      return null;
   }

   @Override
   public String toString() {
      return "TrivagoHotelList{" + "hotels=" + hotels + '}';
   }

   @XmlRootElement(name = "hotel")
   @XmlAccessorType(XmlAccessType.FIELD)
   @XmlType(propOrder = {"id", "name", "address", "city", "country", "lid", "bucketid"})
   public static class HotelTrivago {

      private String id;
      private String name;
      private String address;
      private String city;
      private String country;
      private String lid;
      private String bucketid;
      //to ignore in XML representacion
      @XmlTransient
      private String ticker;
      @XmlTransient
      private DBCredentials dbCredentials;
      @XmlTransient
      private boolean active = true;

      /**
       * Creates a new instance of
       * <code>HotelTrivago</code> without params.
       */
      public HotelTrivago() {
      }

      public HotelTrivago(String id, String name, String address, String city,
              String country, String lid, String bucketid, String ticker,
              DBCredentials dbCredentials) {
         this.id = id;
         this.name = name;
         this.address = address;
         this.city = city;
         this.country = country;
         this.lid = lid;
         this.bucketid = bucketid;
         this.ticker = ticker;
         this.dbCredentials = dbCredentials;
      }

      public String getId() {
         return id;
      }

      public void setId(String id) {
         this.id = id;
      }

      public String getName() {
         return name;
      }

      public void setName(String name) {
         this.name = name;
      }

      public String getAddress() {
         return address;
      }

      public void setAddress(String address) {
         this.address = address;
      }

      public String getCity() {
         return city;
      }

      public void setCity(String city) {
         this.city = city;
      }

      public String getCountry() {
         return country;
      }

      public void setCountry(String country) {
         this.country = country;
      }

      public String getLid() {
         return lid;
      }

      public void setLid(String lid) {
         this.lid = lid;
      }

      public String getBucketid() {
         return bucketid;
      }

      public void setBucketid(String bucketid) {
         this.bucketid = bucketid;
      }

      public String getTicker() {
         return ticker;
      }

      public void setTicker(String ticker) {
         this.ticker = ticker;
      }

      public DBCredentials getDbCredentials() {
         return dbCredentials;
      }

      public void setDbCredentials(DBCredentials dbCredentials) {
         this.dbCredentials = dbCredentials;
      }

      public boolean isActive() {
         return active;
      }

      public void setActive(boolean active) {
         this.active = active;
      }

      @Override
      public String toString() {
         return "TrivagoHotel{" + "id=" + id + ", name=" + name + ", address=" + address
                 + ", city=" + city + ", country=" + country + ", lid=" + lid
                 + ", bucketid=" + bucketid + ", ticker=" + ticker + ", dbCredentials="
                 + dbCredentials + ", active=" + active + '}';
      }
   }
}
