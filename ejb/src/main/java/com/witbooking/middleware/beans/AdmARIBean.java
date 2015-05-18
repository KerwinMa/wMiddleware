/*
 *  AdmARIBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.exceptions.beans.AdmARIException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.Reservation;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.io.File;
import java.util.Date;
import java.util.List;

/**
 * Session Bean implementation class AdmARIBean
 *
 * @author Christian Delgado
 * @date 02-Abr-2013
 * @version 1.0
 * @since
 */
@WebService()
@Stateless
public class AdmARIBean implements AdmARIBeanRemote, AdmARIBeanLocal {

   private static final Logger logger = Logger.getLogger(AdmARIBean.class);

   @WebMethod()
   @WebResult(name="reservations")
   public List<Reservation> getReservationsBetweenCheckInDates(
           @WebParam(name = "startDate") Date startDate,
           @WebParam(name = "endDate") Date endDate, 
           @WebParam(name = "dbCredentials") DBCredentials dbCredentials) throws AdmARIException {
      logger.debug("getReservationsBetweenCheckInDates: (" + DateUtil.calendarFormat(startDate)
              + ", " + DateUtil.calendarFormat(endDate) + ", " + dbCredentials + ")");
      try {
         DBConnection dbConnection = new DBConnection(dbCredentials);
         InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, "");
         ReservationDBHandler reservationDBHandler = new ReservationDBHandler(inventoryDBHandler);
         List<Reservation> list= reservationDBHandler.getReservationsBetweenCheckInDates(startDate, endDate);
         inventoryDBHandler.closeDbConnection();
         return list;
      } catch (DBAccessException ex) {
         logger.error(ex.getMessage());
         throw new AdmARIException(ex);
      }
   }

   @WebMethod()
   @WebResult(name="dbCredentials")
   public DBCredentials getDBCredentials(
           @WebParam(name = "host") String host,
           @WebParam(name = "port") String port,
           @WebParam(name = "nameDB") String nameDB,
           @WebParam(name = "userDB") String userDB,
           @WebParam(name = "passDB") String passDB) {
      return new DBCredentials(host, port, nameDB, userDB, passDB, "hoteldemo.com.v6");
   }

   @WebMethod()
   @WebResult(name="dbCredentials")
   public DBCredentials getTestDBCredentials() {
      System.out.println("Test:" + DateUtil.timestampFormat(new Date()));
      String db_host = "localhost";
      String db_port = "3306";
      String db_name = "bk_demowitbcn6";
      String db_user = "root";
      String db_pass = "";

      DBCredentials dbCredentials = new DBCredentials(db_host, db_port, db_name, db_user, db_pass, "hoteldemo.com.v6");
      logger.debug(dbCredentials);
      return dbCredentials;
   }

   @WebMethod()
   @WebResult(name="response")
   public String getTestServices() {
      logger.debug("getTestServices");
      try {
         logger.debug(" CUSTOMERS_DB_FILE:" + MiddlewareProperties.CUSTOMERS_DB_FILE);
      } catch (Throwable e) {
         File fichero = new File(MiddlewareProperties.CONFIG_FILE);
         return "Error loading the Properties File. Read:" + fichero.canRead() + ". File Path Required: " + fichero.getAbsolutePath() + "";
      }
      return "CORRECT. CUSTOMERS_DB_FILE:" + MiddlewareProperties.CUSTOMERS_DB_FILE;
   }
}
