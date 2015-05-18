/*
 *  AdmARIBeanLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.booking;

import com.witbooking.middleware.exceptions.InvalidEntryException;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Local Interface for the Session Bean BookingExecutor
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 04-dec-2013
 */
@Local
public interface BookingExecutorLocal {

    public String updateARI(String hotelTicker, List<String> inventoryTickers, Date start, Date end);

    public String updateAmount(String hotelTicker, List<String> inventoryTickers, Date start, Date end);

    public String updateARI(String hotelTicker, List<String> inventoryTickers, String startString, String endString);

    public String updateAmount(String hotelTicker, List<String> inventoryTickers, String startString, String endString);

    public String getNotification(String hotelId, String reservationId, Date lastChange, String notificationType) throws InvalidEntryException;

}
