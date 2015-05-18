/*
 *  AdmARIBeanRemote.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.exceptions.beans.AdmARIException;
import com.witbooking.middleware.model.Reservation;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Christian Delgado
 */
@Remote
public interface AdmARIBeanRemote {

   public List<Reservation> getReservationsBetweenCheckInDates(Date startDate, Date endDate, DBCredentials dbCredentials) throws AdmARIException;

   public DBCredentials getTestDBCredentials();

   public DBCredentials getDBCredentials(String host, String port, String nameDB, String userDB, String passDB);

   public String getTestServices();
}
