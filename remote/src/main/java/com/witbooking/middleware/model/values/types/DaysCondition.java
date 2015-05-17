/*
 *  DaysCondition.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values.types;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Class used to represent a
 * <code>boolean</code> associated with the days of the week. This
 * <code>Object</code> contains an
 * <code>boolean</code>
 * <code>Array</code> with length 7, which represents the value for each day of the week.
 *
 * @author Christian Delgado
 * @date 01-feb-2013
 * @version 1.0
 * @since
 */
public class DaysCondition implements Serializable {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;

   private boolean[] days = new boolean[7];

   /**
    * Creates a new instance of
    * <code>DaysCondition</code> without params.
    */
   public DaysCondition() {
      Arrays.fill(days, true);
   }

   public DaysCondition(boolean value) {
      Arrays.fill(days, value);
   }

   public DaysCondition(boolean days[]) {
      this.days = days;
   }

   public DaysCondition(String setDays) {
      if (setDays.contains("0")) {
         days[0]=true;
      }if (setDays.contains("1")) {
         days[1]=true;
      }if (setDays.contains("2")) {
         days[2]=true;
      }if (setDays.contains("3")) {
         days[3]=true;
      }if (setDays.contains("4")) {
         days[4]=true;
      }if (setDays.contains("5")) {
         days[5]=true;
      }if (setDays.contains("6")) {
         days[6]=true;
      }
   }

   public boolean[] getDays() {
      return days;
   }

   public void setDays(boolean[] days) {
      this.days = days;
   }

   public boolean getDayValue(int day) {
      return days[day];
   }

   public void setDays(int day, boolean value) {
      this.days[day] = value;
   }

   @Override
   public String toString() {
      String print = "DaysCondition{";      
      if (days[0]) {
         print = print + " Su";
      }
      if (days[1]) {
         print = print + " Mo";
      }
      if (days[2]) {
         print = print + " Tu";
      }
      if (days[3]) {
         print = print + " We";
      }
      if (days[4]) {
         print = print + " Th";
      }
      if (days[5]) {
         print = print + " Fr";
      }
      if (days[6]) {
         print = print + " Sa";
      }

      print = print + " } ";
      return print;
   }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DaysCondition other = (DaysCondition) obj;
        if (!Arrays.equals(this.days, other.days)) {
            return false;
        }
        return true;
    }
   
   
   
}
