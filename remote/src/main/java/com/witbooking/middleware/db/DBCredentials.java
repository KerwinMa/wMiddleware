/*
 *  DBCredentials.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.db;

import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 02-abr-2013
 * @version 1.0
 * @since
 */
public class DBCredentials implements Serializable {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;
   private String host;
   private String port = "3306";//default value for MySQL: 3306
   private String nameDB;
   private String userDB;
   private String passDB;
   private String ticker;

   public DBCredentials() {
   }

   public DBCredentials(String host, String port, String nameDB, String userDB, String passDB, String ticker) {
      this.host = host;
      this.port = port;
      this.nameDB = nameDB;
      this.userDB = userDB;
      this.passDB = passDB;
      this.ticker = ticker;
   }

   /**
    * Get the value of passDB
    *
    * @return the value of passDB
    */
   public String getPassDB() {
      return passDB;
   }

   /**
    * Set the value of passDB
    *
    * @param passDB new value of passDB
    */
   public void setPassDB(String passDB) {
      this.passDB = passDB;
   }

   /**
    * Get the value of userDB
    *
    * @return the value of userDB
    */
   public String getUserDB() {
      return userDB;
   }

   /**
    * Set the value of userDB
    *
    * @param userDB new value of userDB
    */
   public void setUserDB(String userDB) {
      this.userDB = userDB;
   }

   /**
    * Get the value of nameDB
    *
    * @return the value of nameDB
    */
   public String getNameDB() {
      return nameDB;
   }

   /**
    * Set the value of nameDB
    *
    * @param nameDB new value of nameDB
    */
   public void setNameDB(String nameDB) {
      this.nameDB = nameDB;
   }

   /**
    * Get the value of port
    *
    * @return the value of port
    */
   public String getPort() {
      return port;
   }

   /**
    * Set the value of port
    *
    * @param port new value of port
    */
   public void setPort(String port) {
      this.port = port;
   }

   /**
    * Get the value of host
    *
    * @return the value of host
    */
   public String getHost() {
      return host;
   }

   /**
    * Set the value of host
    *
    * @param host new value of host
    */
   public void setHost(String host) {
      this.host = host;
   }

   /**
    * Get the value of ticker
    *
    * @return the value of ticker
    */
   public String getTicker() {
      return ticker;
   }

   /**
    * Set the value of ticker
    *
    * @param ticker new value of ticker
    */
   public void setTicker(String ticker) {
      this.ticker = ticker;
   }

   @Override
   public String toString() {
      return "DBCredentials{" + "host=" + host + ", port=" + port + ", nameDB=" + nameDB + ", userDB=" + userDB + ", passDB=" + passDB + ", ticker=" + ticker +'}';
   }
}
