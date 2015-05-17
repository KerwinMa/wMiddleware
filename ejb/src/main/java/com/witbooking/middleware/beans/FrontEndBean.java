/*
 *  FrontEndBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.DailyValuesDBHandler;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.db.handlers.sqlinstructions.SQLInstructions;
import com.witbooking.middleware.exceptions.FrontEndException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.Inventory;
import com.witbooking.middleware.model.values.HashRangeValue;
import com.witbooking.middleware.resources.DBProperties;

import javax.ejb.Stateless;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Session Bean implementation class FrontEndBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 08-may-2013
 */
@Stateless
//@RolesAllowed("AuthorizedClients")
//@HandlerChain(file = "handlers.xml")
public class FrontEndBean implements FrontEndBeanRemote, FrontEndBeanLocal {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(FrontEndBean.class);

    public List<Inventory> getInventory(InventoryDBHandler inventoryDBHandler) throws FrontEndException {
        List<Inventory> inventoryList;
        try {
            inventoryList = inventoryDBHandler.getInventoriesActives();
            return inventoryList;
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        }
    }

    public List<HashRangeValue> getARIValues(DailyValuesDBHandler dailyValuesDBHandler,
                                             List<Inventory> inventoryList, Date startDate, Date endDate, String currency)
            throws FrontEndException {
        if (startDate == null) {
            throw new FrontEndException("startDate is null");
        }
        if (endDate == null) {
            throw new FrontEndException("startDate is null");
        }
        try {
            List<HashRangeValue> mapValues = new ArrayList<HashRangeValue>();
            if (!inventoryList.isEmpty()) {
                dailyValuesDBHandler.getInventoryValuesBetweenDates();
                for (Inventory inventory : inventoryList) {
                    HashRangeValue hashRangeValue = new HashRangeValue(inventory.getTicker());
                    hashRangeValue.putRangeValues(HashRangeValue.RATE, dailyValuesDBHandler.getRatesByTicker(inventory.getTicker()));
                    hashRangeValue.putRangeValues(HashRangeValue.PROMOTIONAL_RATE, dailyValuesDBHandler.getFullRatesByTicker(inventory.getTicker(), currency));
                    hashRangeValue.putRangeValues(HashRangeValue.ACTUAL_AVAILABILITY, dailyValuesDBHandler.getAvailabilityByTicker(inventory.getTicker()));
                    hashRangeValue.putRangeValues(HashRangeValue.LOCK, dailyValuesDBHandler.getLockByTicker(inventory.getTicker()));
                    hashRangeValue.putRangeValues(HashRangeValue.MIN_STAY, dailyValuesDBHandler.getMinStayByTicker(inventory.getTicker()));
                    hashRangeValue.putRangeValues(HashRangeValue.MAX_STAY, dailyValuesDBHandler.getMaxStayByTicker(inventory.getTicker()));
                    hashRangeValue.putRangeValues(HashRangeValue.MIN_NOTICE, dailyValuesDBHandler.getMinNoticeByTicker(inventory.getTicker()));
                    hashRangeValue.putRangeValues(HashRangeValue.MAX_NOTICE, dailyValuesDBHandler.getMaxNoticeByTicker(inventory.getTicker()));
                    mapValues.add(hashRangeValue);
                }
                dailyValuesDBHandler.closeDbConnection();
            }
            return mapValues;
        } catch (MiddlewareException ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        }
    }

    public String testFrontEndServices() {
        File filePath = new File("PATH_FILE");
        logger.debug("file: " + filePath.getAbsolutePath());
        return "ALL ITS OK!";
    }


    public DBCredentials getTestDBCredentials(String hotelTicker) throws FrontEndException {
        DBCredentials dbCredential = null;
        try {
            dbCredential= DBProperties.getDBSupportByTicker(SQLInstructions.WitMetaDataDBHandler.DB_WITMETADATA_TICKER);
            return DBProperties.getDBCustomerByTicker(hotelTicker);
        } catch (MiddlewareException ex) {
            logger.error("Error getting the DBCredential for the Hotel '" + hotelTicker + "': " + ex.getMessage());
            throw new FrontEndException(ex);
        }
    }
}
