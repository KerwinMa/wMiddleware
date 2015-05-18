/*
 *  FrontEndBeanLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.DailyValuesDBHandler;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.exceptions.FrontEndException;
import com.witbooking.middleware.model.Inventory;
import com.witbooking.middleware.model.values.HashRangeValue;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Local Interface for the Session Bean FrontEndBeanLocal
 *
 * @author Christian Delgado
 * @date 08-may-2013
 * @version 1.0
 * @since
 */
@Local
public interface FrontEndBeanLocal {

   public List<Inventory> getInventory(InventoryDBHandler inventoryDBHandler) throws FrontEndException;

   public List<HashRangeValue> getARIValues(DailyValuesDBHandler dailyValuesDBHandler,
           List<Inventory> inventoryList, Date startDate, Date endDate, String currency)
           throws FrontEndException;

   public String testFrontEndServices();

   public DBCredentials getTestDBCredentials(String hotelTicker) throws FrontEndException;
}
