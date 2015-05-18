/*
 *  ScrappingBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.DailyValuesDBHandler;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.ScrappingException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.Inventory;
import com.witbooking.middleware.model.Reservation;
import com.witbooking.middleware.model.Tax;
import com.witbooking.middleware.model.values.HashRangeValue;
import com.witbooking.middleware.resources.DBProperties;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Session Bean implementation class ScrappingBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 19-abr-2013
 */
@Stateless
@WebService()
public class ScrappingBean implements ScrappingBeanRemote, ScrappingBeanLocal {

    private static final Logger logger = Logger.getLogger(ScrappingBean.class);
    private static final String locale = "eng";

    @EJB
    private ConnectionBeanLocal connectionBean;

    @WebMethod()
    @WebResult(name = "login")
    public String login(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password) throws ScrappingException {
        try {
            final String hotelTickersList = connectionBean.getHotelTickers(username, password);
            return hotelTickersList;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new ScrappingException(ex);
        }
    }

    @WebMethod()
    @WebResult(name = "reservations")
    public List<Reservation> getReservationsByCreationDate(
            @WebParam(name = "startDate") Date startDate,
            @WebParam(name = "endDate") Date endDate,
            @WebParam(name = "hotelTicker") String hotelTicker)
            throws ScrappingException {
        DBConnection dBConnection = null;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            dBConnection = new DBConnection(dbCredential);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dBConnection, "");
            ReservationDBHandler reservationDBHandler = new ReservationDBHandler(inventoryDBHandler);
            List<Reservation> reservationList = reservationDBHandler.getReservationsBetweenCreationOrModificationDates(startDate, endDate);
            reservationDBHandler.closeDbConnection();
            return reservationList;
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new ScrappingException(ex);
        } finally {
            DAOUtil.close(dBConnection);
        }
    }

    @WebMethod()
    @WebResult(name = "taxes")
    public List<Tax> getTaxes(
            @WebParam(name = "hotelTicker") String hotelTicker) throws ScrappingException {
        InventoryDBHandler inventoryDBHandler = null;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            inventoryDBHandler = new InventoryDBHandler(new DBConnection(dbCredential), locale);
            List<Tax> taxList = inventoryDBHandler.getTaxes();
            inventoryDBHandler.closeDbConnection();
            return taxList;
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new ScrappingException(ex);
        } finally {
            if (inventoryDBHandler != null) {
                try {
                    inventoryDBHandler.closeDbConnection();
                } catch (DBAccessException ex) {
                    logger.error(ex.getMessage());
                    throw new ScrappingException(ex);
                }
            }
        }
    }

    @WebMethod()
    @WebResult(name = "hashRangeValues")
    public List<HashRangeValue> getARIValues(
            @WebParam(name = "startDate") Date startDate,
            @WebParam(name = "endDate") Date endDate,
            @WebParam(name = "hotelTicker") String hotelTicker) throws ScrappingException {
        DBConnection dBConnection = null;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            dBConnection = new DBConnection(dbCredential);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dBConnection, locale);
            List<Inventory> inventoryList = inventoryDBHandler.getInventoryForScraping();
            List<HashRangeValue> mapValues = new ArrayList<HashRangeValue>();
            if (!inventoryList.isEmpty()) {
                DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, startDate, endDate);
                dailyValuesDBHandler.getInventoryValuesBetweenDates();
                for (Inventory inventory : inventoryList) {
                    HashRangeValue hashRangeValue = new HashRangeValue(inventory.getTicker());
                    hashRangeValue.putRangeValues(HashRangeValue.RATE, dailyValuesDBHandler.getRatesByTicker(inventory.getTicker()));
                    hashRangeValue.putRangeValues(HashRangeValue.ACTUAL_AVAILABILITY, dailyValuesDBHandler.getAvailabilityByTicker(inventory.getTicker()));
                    hashRangeValue.putRangeValues(HashRangeValue.LOCK, dailyValuesDBHandler.getLockByTicker(inventory.getTicker()));
                    hashRangeValue.putRangeValues(HashRangeValue.MIN_STAY, dailyValuesDBHandler.getMinStayByTicker(inventory.getTicker()));
                    mapValues.add(hashRangeValue);
                }
                dailyValuesDBHandler.closeDbConnection();
            }
            return mapValues;
        } catch (MiddlewareException ex) {
            logger.error(ex.getMessage());
            throw new ScrappingException(ex);
        } finally {
            DAOUtil.close(dBConnection);
        }
    }

    @WebMethod()
    @WebResult(name = "rows")
    public int updateARIValues(
            @WebParam(name = "mapValues") List<HashRangeValue> mapValues,
            @WebParam(name = "startDate") Date startDate,
            @WebParam(name = "endDate") Date endDate,
            @WebParam(name = "hotelTicker") String hotelTicker) throws ScrappingException {
        DailyValuesDBHandler dailyValuesDBHandler = null;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            dailyValuesDBHandler = new DailyValuesDBHandler(new DBConnection(dbCredential), startDate, endDate);
            dailyValuesDBHandler.getInventoryValuesBetweenDates();
            int rows = 0;
            for (HashRangeValue hashRangeValue : mapValues) {
                String inventoryTicker = hashRangeValue.getTicker();
                rows = rows + dailyValuesDBHandler.updateRatesByTicker(inventoryTicker, hashRangeValue.getRangeValue(HashRangeValue.RATE));
                rows = rows + dailyValuesDBHandler.updateAvailabilityByTicker(inventoryTicker, hashRangeValue.getRangeValue(HashRangeValue.ACTUAL_AVAILABILITY));
                rows = rows + dailyValuesDBHandler.updateLocksByTicker(inventoryTicker, hashRangeValue.getRangeValue(HashRangeValue.LOCK));
                rows = rows + dailyValuesDBHandler.updateMinStayByTicker(inventoryTicker, hashRangeValue.getRangeValue(HashRangeValue.MIN_STAY));
            }
            dailyValuesDBHandler.closeDbConnection();
            return rows;
        } catch (NonexistentValueException ex) {
            logger.error(ex.getMessage());
            throw new ScrappingException(ex);
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new ScrappingException(ex);
        } finally {
            if (dailyValuesDBHandler != null) {
                try {
                    dailyValuesDBHandler.getDbConnection().closeConnection();
                } catch (DBAccessException ex) {
                    logger.error(ex.getMessage());
                    throw new ScrappingException(ex);
                }
            }
        }
    }

    private DBCredentials getDBCredentials(String hotelTicker) throws ScrappingException {
        try {
            return DBProperties.getDBCustomerByTicker(hotelTicker);
        } catch (MiddlewareException ex) {
            logger.error("Error getting the DBCredential for the Hotel '" + hotelTicker + "': " + ex.getMessage());
            throw new ScrappingException(ex);
        }
    }
}
