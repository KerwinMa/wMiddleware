/*
 *  Configuration.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 24-ene-2013
 * @version 1.0
 * @since
 */
public class Configuration implements Serializable {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;
   private Integer id;
   private String ticker;
   private String name;
   private Map<Guest, Integer> guests;
   private Date dateCreation;
   private Date dateModification;

   /**
    * Creates a new instance of
    * <code>Configuration</code> without params.
    */
   public Configuration() {
      this.guests = new HashMap<Guest, Integer>();
   }

   public Configuration(Integer id, String ticker, String name, Map<Guest, Integer> guests) {
      this.id = id;
      this.ticker = ticker;
      this.name = name;
      this.guests = guests;
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

   public Map<Guest, Integer> getGuests() {
      return guests;
   }

   public void setGuests(Map<Guest, Integer> guests) {
      this.guests = guests;
   }

   public Date getDateCreation() {
      return dateCreation;
   }

   public void setDateCreation(Date dateCreation) {
      this.dateCreation = dateCreation;
   }

   public Date getDateModification() {
      return dateModification;
   }

   public void setDateModification(Date dateModification) {
      this.dateModification = dateModification;
   }

    public int getAdults() {
        for (Guest guest : guests.keySet()) {
            if (guest.isAdult()) {
                return guests.get(guest) != null ? guests.get(guest) : 0;
            }
        }
        return 0;
    }

    public int getChildren() {
        for (Guest guest : guests.keySet()) {
            if (guest.isChild()) {
                return guests.get(guest) != null ? guests.get(guest) : 0;
            }
        }
        return 0;
    }

    public int getInfants() {
        for (Guest guest : guests.keySet()) {
            if (guest.isInfant()) {
                return guests.get(guest) != null ? guests.get(guest) : 0;
            }
        }
        return 0;
    }

    public int getTotalGuests() {
        int total = 0;
        for (Integer number : guests.values()) {
            if (number != null)
                total = total + number;
        }
        return total;
    }


   @Override
   public String toString() {
      String print = "Configuration{"
              + "id=" + id
              + ", ticker=" + ticker
              + ", name=" + name
              //              + ", dateCreation=" + dateCreation
              //              + ", dateModification=" + dateModification
              + ", guests=[";

      Iterator<Guest> iterator = guests.keySet().iterator();

      while (iterator.hasNext()) {
         Guest key = iterator.next();

         print = print + key.getName() + ":" + guests.get(key);
         if (iterator.hasNext()) {
            print = print + ", ";
         }
      }

      print = print + "]}";
      return print;
   }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Configuration other = (Configuration) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.ticker == null) ? (other.ticker != null) : !this.ticker.equals(other.ticker)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if (this.guests != other.guests && (this.guests == null || !this.guests.equals(other.guests))) {
            return false;
        }
        if (this.dateCreation != other.dateCreation && (this.dateCreation == null || !this.dateCreation.equals(other.dateCreation))) {
            return false;
        }
        if (this.dateModification != other.dateModification && (this.dateModification == null || !this.dateModification.equals(other.dateModification))) {
            return false;
        }
        return true;
    }
   
}
