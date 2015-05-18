/*
 *  EndpointAPIBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.DailyValuesDBHandler;
import com.witbooking.middleware.db.handlers.HotelConfigurationDBHandler;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.exceptions.InvalidEntryException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.WitBookerAPIException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.values.EnumDataValueType;
import com.witbooking.middleware.model.values.HashRangeResult;
import com.witbooking.middleware.model.values.HashRangeValue;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.NumberUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

/**
 * Session Bean implementation class EndpointAPIBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 19-abr-2013
 */
@Stateless
public class EndpointAPIBean implements EndpointAPIBeanRemote, EndpointAPIBeanLocal {

    private static final Logger logger = Logger.getLogger(EndpointAPIBean.class);

    public static final String ENDPOINT_VERSION = "1.2.2";

    @EJB
    private ConnectionBeanLocal connectionBean;

    public String getHotelTickers(String username, String password) throws WitBookerAPIException {
        try {
            return connectionBean.getHotelTickers(username, password);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new WitBookerAPIException(ex);
        }
    }

    public List<Tax> getTaxes(String hotelTicker) throws WitBookerAPIException {
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(getDBCredentials(hotelTicker));
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, "");
            return inventoryDBHandler.getTaxes();
        } catch (DBAccessException ex) {
            logger.error("Hotel: " + hotelTicker + " error: " + ex);
            throw new WitBookerAPIException(ex);
        } finally {
            if (dbConnection != null) {
                try {
                    dbConnection.closeConnection();
                } catch (DBAccessException ex) {
                    logger.error(ex.getMessage());
                    throw new WitBookerAPIException(ex);
                }
            }
        }
    }


    public List<Inventory> getInventories(String hotelTicker, String locale) throws WitBookerAPIException {
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(getDBCredentials(hotelTicker));
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, locale + "");
            return inventoryDBHandler.getInventoriesValid();
        } catch (DBAccessException ex) {
            logger.error("Hotel: " + hotelTicker + " error: " + ex);
            throw new WitBookerAPIException(ex);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }


    public List<Service> getServices(String hotelTicker, String locale) throws WitBookerAPIException {
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(getDBCredentials(hotelTicker));
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, locale + "");
            return inventoryDBHandler.getServicesValid();
        } catch (DBAccessException ex) {
            logger.error("Hotel: " + hotelTicker + " error: " + ex);
            throw new WitBookerAPIException(ex);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    public List<Discount> getDiscounts(String hotelTicker, String locale) throws WitBookerAPIException {
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(getDBCredentials(hotelTicker));
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, locale + "");
            return inventoryDBHandler.getDiscountsValid();
        } catch (DBAccessException ex) {
            logger.error("Hotel: " + hotelTicker + " error: " + ex);
            throw new WitBookerAPIException(ex);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    public List<DataValueHolder> getARIManagementData(String hotelTicker, String locale) throws WitBookerAPIException {
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(getDBCredentials(hotelTicker));
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, locale + "");
            List<DataValueHolder> valueHolders = new ArrayList<>();
            valueHolders.addAll(inventoryDBHandler.getInventoriesValid());
            valueHolders.addAll(inventoryDBHandler.getDiscountsValid());
            valueHolders.addAll(inventoryDBHandler.getServicesValid());
            return valueHolders;
        } catch (DBAccessException ex) {
            logger.error("Hotel: " + hotelTicker + " error: " + ex);
            throw new WitBookerAPIException(ex);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    /**
     * Method that return all the ARI Values for the OWN values
     *
     * @param startDate
     * @param endDate
     * @param hotelTicker
     * @param invTickers
     * @param adults
     * @return
     * @throws WitBookerAPIException
     * @throws InvalidEntryException
     */
    public List<HashRangeValue> getARIValues(Date startDate, Date endDate, String hotelTicker, List<String> invTickers,
                                             Integer adults) throws WitBookerAPIException, InvalidEntryException {
        validateDates(startDate, endDate);
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(getDBCredentials(hotelTicker));
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection);
            List<Inventory> inventoryList;
            //if the invTickers List is empty must return the valueTypes for all the inventories,
            // or the inventory List with the selected adults
            if (invTickers == null || invTickers.isEmpty()) {
                if (adults == null)
                    inventoryList = inventoryDBHandler.getInventoriesValid();
                else
                    inventoryList = inventoryDBHandler.getInventoriesByAdults(adults);
            } else {
                inventoryList = inventoryDBHandler.getInventoriesByTickers(invTickers);
            }
            List<HashRangeValue> mapValues = new ArrayList<>();
            if (!inventoryList.isEmpty()) {
                DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, startDate, endDate);
                dailyValuesDBHandler.getInventoryValuesBetweenDates();
                for (Inventory inventory : inventoryList) {
                    if (inventory.isValid()) {
                        if (adults == null || inventory.getConfiguration().getAdults() == adults) {
                            HashRangeValue hashRangeValue = new HashRangeValue(inventory.getTicker());

                            if (inventory.getRate() != null && inventory.getRate().getValueType() == EnumDataValueType.OWN) {
                                hashRangeValue.putRangeValues(HashRangeValue.RATE, dailyValuesDBHandler.getRatesByTicker(inventory.getTicker()));
                            }
                            if (inventory.getAvailability() != null && inventory.getAvailability().getValueType() == EnumDataValueType.OWN) {
                                hashRangeValue.putRangeValues(HashRangeValue.ACTUAL_AVAILABILITY, dailyValuesDBHandler.getAvailabilityByTicker(inventory.getTicker()));
                            }
                            if (inventory.getLock() != null && inventory.getLock().getValueType() == EnumDataValueType.OWN) {
                                hashRangeValue.putRangeValues(HashRangeValue.LOCK, dailyValuesDBHandler.getLockByTicker(inventory.getTicker()));
                            }
                            if (inventory.getMinStay() != null && inventory.getMinStay().getValueType() == EnumDataValueType.OWN) {
                                hashRangeValue.putRangeValues(HashRangeValue.MIN_STAY, dailyValuesDBHandler.getMinStayByTicker(inventory.getTicker()));
                            }
                            if (inventory.getMaxStay() != null && inventory.getMaxStay().getValueType() == EnumDataValueType.OWN) {
                                hashRangeValue.putRangeValues(HashRangeValue.MAX_STAY, dailyValuesDBHandler.getMaxStayByTicker(inventory.getTicker()));
                            }
                            if (inventory.getMinNotice() != null && inventory.getMinNotice().getValueType() == EnumDataValueType.OWN) {
                                hashRangeValue.putRangeValues(HashRangeValue.MIN_NOTICE, dailyValuesDBHandler.getMinNoticeByTicker(inventory.getTicker()));
                            }
                            if (inventory.getMaxNotice() != null && inventory.getMaxNotice().getValueType() == EnumDataValueType.OWN) {
                                hashRangeValue.putRangeValues(HashRangeValue.MAX_NOTICE, dailyValuesDBHandler.getMaxNoticeByTicker(inventory.getTicker()));
                            }
                            mapValues.add(hashRangeValue);
                        }
                    }
                }
            }
            return mapValues;
        } catch (MiddlewareException ex) {
            logger.error("Hotel: " + hotelTicker + " error: " + ex);
            throw new WitBookerAPIException(ex);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    public List<HashRangeValue> getFullARIValues(final String hotelTicker, final Date start, final Date end)
            throws WitBookerAPIException {
        return getManagementARIValues(hotelTicker, start, end, "").getRangeValues();
    }

    public ManagementVisualRepresentation getManagementARIValues(final String hotelTicker, final Date start, final Date end, String locale)
            throws WitBookerAPIException {
        ManagementVisualRepresentation result = new ManagementVisualRepresentation();
        DBConnection dbConnection = null;
        if (start == null || end == null || hotelTicker == null || hotelTicker.trim().isEmpty()) {
            return result;
        }
        try {
            dbConnection = new DBConnection(hotelTicker);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, locale + "");
            final DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, start, end);

            List<Discount> discountsActives = inventoryDBHandler.getDiscountsValid();
            List<Service> servicesActives = inventoryDBHandler.getServicesValid();
            List<Inventory> inventoryList = inventoryDBHandler.getInventoriesValid();
            List<String> variablesList = inventoryDBHandler.getAllVariables();
            dailyValuesDBHandler.getInventoryValuesBetweenDates();
            //order lists
            Collections.sort(inventoryList);

            result.getValueHolders().addAll(inventoryList);
            result.getValueHolders().addAll(discountsActives);
            result.getValueHolders().addAll(servicesActives);

            for (Inventory inventory : inventoryList) {
                String inventoryTicker = inventory.getTicker();
                HashRangeValue hashRangeValue = new HashRangeValue(inventoryTicker);
                hashRangeValue.putRangeValues(HashRangeValue.RATE, dailyValuesDBHandler.getRatesByTicker(inventoryTicker));
                hashRangeValue.putRangeValues(HashRangeValue.ACTUAL_AVAILABILITY, dailyValuesDBHandler.getAvailabilityByTicker(inventoryTicker));
                hashRangeValue.putRangeValues(HashRangeValue.LOCK, dailyValuesDBHandler.getLockByTicker(inventoryTicker));
                if (!EnumDataValueType.NULL_VALUE.equals(inventory.getMinStay().getValueType()))
                    hashRangeValue.putRangeValues(HashRangeValue.MIN_STAY, dailyValuesDBHandler.getMinStayByTicker(inventoryTicker));
                if (!EnumDataValueType.NULL_VALUE.equals(inventory.getMaxStay().getValueType()))
                    hashRangeValue.putRangeValues(HashRangeValue.MAX_STAY, dailyValuesDBHandler.getMaxStayByTicker(inventoryTicker));
                if (!EnumDataValueType.NULL_VALUE.equals(inventory.getMinNotice().getValueType()))
                    hashRangeValue.putRangeValues(HashRangeValue.MIN_NOTICE, dailyValuesDBHandler.getMinNoticeByTicker(inventoryTicker));
                if (!EnumDataValueType.NULL_VALUE.equals(inventory.getMaxNotice().getValueType()))
                    hashRangeValue.putRangeValues(HashRangeValue.MAX_NOTICE, dailyValuesDBHandler.getMaxNoticeByTicker(inventoryTicker));
                result.addRangeValue(hashRangeValue);
            }
            for (final Discount discountActive : discountsActives) {
                String discountTicker = discountActive.getTicker();
                if (discountTicker != null && !discountTicker.isEmpty()) {
                    if (!EnumDataValueType.NULL_VALUE.equals(discountActive.getLock().getValueType())) {
                        HashRangeValue hashRangeValue = new HashRangeValue(discountTicker);
                        hashRangeValue.putRangeValues(HashRangeValue.LOCK, dailyValuesDBHandler.getLockByTicker(discountTicker));
                        result.addRangeValue(hashRangeValue);
                    }
                }
            }
            for (final Service serviceActive : servicesActives) {
                String serviceTicker = serviceActive.getTicker();
                if (serviceTicker != null && !serviceTicker.isEmpty()) {
                    HashRangeValue hashRangeValue = new HashRangeValue(serviceTicker);
                    if (!EnumDataValueType.NULL_VALUE.equals(serviceActive.getLock().getValueType()))
                        hashRangeValue.putRangeValues(HashRangeValue.LOCK, dailyValuesDBHandler.getLockByTicker(serviceTicker));
                    if (!EnumDataValueType.NULL_VALUE.equals(serviceActive.getRate().getValueType()))
                        hashRangeValue.putRangeValues(HashRangeValue.RATE, dailyValuesDBHandler.getRatesByTicker(serviceTicker));
                    if (!hashRangeValue.getHashRangeValues().isEmpty()) {
                        result.addRangeValue(hashRangeValue);
                    }
                    //remove from variables
                    if (variablesList.contains(serviceTicker))
                        variablesList.remove(serviceTicker);
                }
            }
            for (String variable : variablesList) {
                HashRangeValue hashRangeValue = new HashRangeValue(variable);
                hashRangeValue.putRangeValues(HashRangeValue.VARIABLE, dailyValuesDBHandler.getRatesByTicker(variable));
                result.addRangeValue(hashRangeValue);
            }
            //getting the properties required to handle the Management data
            HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
            List<String> list = new ArrayList<>();
            list.add(ManagementVisualRepresentation.MINIMUM_PRICE_LEGACY);
            list.add(ManagementVisualRepresentation.MAXIMUM_PRICE_LEGACY);
            list.add(ManagementVisualRepresentation.WARNING_LIMIT_AVAILABILITY_LEGACY);
            Properties props = hotelConfigurationDBHandler.getHotelProperties(list);

            Float minPrice = null;
            Float maxPrice = null;
            Float availLimit = null;
            try {
                if (props.getProperty(ManagementVisualRepresentation.MINIMUM_PRICE_LEGACY) != null)
                    minPrice = Float.parseFloat(props.getProperty(ManagementVisualRepresentation.MINIMUM_PRICE_LEGACY));
                if (props.getProperty(ManagementVisualRepresentation.MAXIMUM_PRICE_LEGACY) != null)
                    maxPrice = Float.parseFloat(props.getProperty(ManagementVisualRepresentation.MAXIMUM_PRICE_LEGACY));
                if (props.getProperty(ManagementVisualRepresentation.WARNING_LIMIT_AVAILABILITY_LEGACY) != null)
                    availLimit = Float.parseFloat(props.getProperty(ManagementVisualRepresentation.WARNING_LIMIT_AVAILABILITY_LEGACY));
            } catch (NumberFormatException ex) {
                logger.error("Hotel: " + hotelTicker + " error: " + ex);
            }
            result.addManagementParam(ManagementVisualRepresentation.MINIMUM_PRICE, minPrice);
            result.addManagementParam(ManagementVisualRepresentation.MAXIMUM_PRICE, maxPrice);
            result.addManagementParam(ManagementVisualRepresentation.WARNING_LIMIT_AVAILABILITY, availLimit);
        } catch (MiddlewareException e) {
            logger.error("Hotel: " + hotelTicker + " error: " + e);
            throw new WitBookerAPIException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
        return result;
    }

    public List<HashRangeResult> getBookingValues(Date checkInDate, Date checkOutDate, String hotelTicker,
                                                  List<String> invTickers, Integer adults, String currency,
                                                  String promotionalCode) throws WitBookerAPIException, InvalidEntryException {

        if (checkInDate == null || checkOutDate == null) {
            throw new InvalidEntryException("Invalid dates.");
        }
        if (hotelTicker == null || hotelTicker.isEmpty()) {
            throw new InvalidEntryException("Invalid HotelTicker.");
        }
        Date startDate = DateUtil.toBeginningOfTheDay(checkInDate);
        Date endDate = DateUtil.toBeginningOfTheDay(checkOutDate);
        final int nights = DateUtil.daysBetweenDates(startDate, endDate);
        //I don't calculate the rates and Availability for the check out day
        DateUtil.incrementDays(endDate, -1);
        if (nights <= 0)
            throw new WitBookerAPIException("The 'checkInDate' must be earlier than the 'checkOutDate'");
        int hourNotice = DateUtil.hoursBetweenDates(new Date(), DateUtil.cloneAndIncrementDays(startDate, 1));
        if (hourNotice <= 0)
            throw new WitBookerAPIException("The 'checkInDate' can't be before today.");
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(getDBCredentials(hotelTicker));
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection);
            List<Inventory> inventoryList;
            //if the invTickers List is empty must return the valueTypes for all the inventories
            // or the inventory List with the selected adults
            if (invTickers == null || invTickers.isEmpty()) {
                if (adults == null)
                    inventoryList = inventoryDBHandler.getInventoriesValid();
                else
                    inventoryList = inventoryDBHandler.getInventoriesByAdults(adults);
            } else {
                inventoryList = inventoryDBHandler.getInventoriesByTickers(invTickers);
            }
            List<HashRangeResult> mapValues = new ArrayList<>();
            if (!inventoryList.isEmpty()) {
                DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, startDate, endDate);
                dailyValuesDBHandler.getInventoryValuesBetweenDates();
                for (Inventory inventory : inventoryList) {
                    if (inventory.isValid()) {
                        if (adults == null || inventory.getConfiguration().getAdults() == adults) {
                            boolean validInv = true;
                            Float rateValue = null;
                            Integer availabilityValue = null;
                            //Checking restrictions
                            //There should be availability grater than zero, And isn't locked, etc...
                            try {
                                if (validInv) {
                                    final int checkInDay = (DateUtil.getDayOfWeek(checkInDate) % 7);
                                    if (!inventory.getCheckInDays().getDayValue(checkInDay)) {
                                        validInv = false;
                                    }
                                }
                                if (validInv) {
                                    final int checkOutDay = (DateUtil.getDayOfWeek(checkOutDate) % 7);
                                    if (!inventory.getCheckOutDays().getDayValue(checkOutDay)) {
                                        validInv = false;
                                    }
                                }
                                if (validInv) {
                                    RangeValue<Boolean> lock = dailyValuesDBHandler.getLockByTicker(inventory.getTicker());
                                    if (lock == null) {
                                        validInv = false;
                                    } else if (lock.hasValueEqualsTo(true)) {
                                        validInv = false;
                                    }
                                }
                                if (validInv && inventory.getMinStay() != null && inventory.getMinStay().getValueType() != EnumDataValueType.NULL_VALUE) {
                                    RangeValue<Integer> minStay = dailyValuesDBHandler.getMinStayByTicker(inventory.getTicker());
                                    if (minStay != null) {
                                        Integer minStayValue = minStay.getMaxValue();
                                        if (minStayValue != null && minStayValue > nights) {
                                            validInv = false;
                                        }
                                    }
                                }
                                if (validInv && inventory.getMaxStay() != null && inventory.getMaxStay().getValueType() != EnumDataValueType.NULL_VALUE) {
                                    RangeValue<Integer> maxStay = dailyValuesDBHandler.getMaxStayByTicker(inventory.getTicker());
                                    if (maxStay != null) {
                                        Integer maxStayValue = maxStay.getMinValue();
                                        if (maxStayValue != null && maxStayValue < nights) {
                                            validInv = false;
                                        }
                                    }
                                }
                                if (validInv && inventory.getMinNotice() != null && inventory.getMinNotice().getValueType() != EnumDataValueType.NULL_VALUE) {
                                    RangeValue<Integer> minNotice = dailyValuesDBHandler.getMinNoticeByTicker(inventory.getTicker());
                                    if (minNotice != null) {
                                        Integer minNoticeValue = minNotice.getValueForADate(startDate);
                                        if (minNoticeValue != null && minNoticeValue > hourNotice) {
                                            validInv = false;
                                        }
                                    }
                                }
                                if (validInv && inventory.getMaxNotice() != null && inventory.getMaxNotice().getValueType() != EnumDataValueType.NULL_VALUE) {
                                    RangeValue<Integer> maxNotice = dailyValuesDBHandler.getMaxNoticeByTicker(inventory.getTicker());
                                    if (maxNotice != null) {
                                        Integer maxNoticeValue = maxNotice.getValueForADate(startDate);
                                        if (maxNoticeValue != null && maxNoticeValue < hourNotice) {
                                            validInv = false;
                                        }
                                    }
                                }
                                if (validInv) {
                                    RangeValue<Integer> availability = dailyValuesDBHandler.getAvailabilityByTicker(inventory.getTicker());
                                    if (availability != null) {
                                        availabilityValue = availability.getMinValue();
                                        if (availabilityValue == null || availabilityValue < 1) {
                                            validInv = false;
                                        }
                                    } else {
                                        validInv = false;
                                    }
                                }
                                if (validInv) {
                                    RangeValue<Float> rate = dailyValuesDBHandler.getFullRatesByTicker(inventory.getTicker(), currency, promotionalCode);
                                    if (rate != null && !rate.hasValueEqualsToFloat(0)) {
                                        rateValue = rate.getSumValues();
                                    } else {
                                        validInv = false;
                                    }
                                }
                            } catch (Exception e) {
                                logger.error("Hotel: " + hotelTicker + " error: " + e.getClass() + " " + e);
                                validInv = false;
                            }
                            if (validInv && rateValue != null && availabilityValue != null) {
                                HashRangeResult hashRangeResult = new HashRangeResult(inventory.getTicker());
                                hashRangeResult.putRangeResults(HashRangeValue.RATE, NumberUtils.roundFloat(rateValue, 2));
                                hashRangeResult.putRangeResults(HashRangeValue.ACTUAL_AVAILABILITY, availabilityValue);
                                List<Map.Entry<String, Float>> discounts =
                                        dailyValuesDBHandler.getFinalDiscountApplied(inventory.getTicker(), currency, null,
                                                promotionalCode).getValuesForEachDay();
                                float discountValue = 0;
                                for (Map.Entry<String, Float> discount : discounts) {
                                    if (discount != null && discount.getValue() != null) {
                                        discountValue = discountValue + Math.abs(discount.getValue());
                                    }
                                }
                                if (discountValue > 0) {
                                    hashRangeResult.putRangeResults("discount", NumberUtils.roundFloat(discountValue, 2));
                                }
                                mapValues.add(hashRangeResult);
                            }
                        }
                    }
                }
            }
            return mapValues;
        } catch (MiddlewareException ex) {
            logger.error("Hotel: " + hotelTicker + " error: " + ex);
            throw new WitBookerAPIException(ex);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    public List<HashRangeResult> updateARIValues(List<HashRangeValue> mapValues, String hotelTicker) throws WitBookerAPIException, InvalidEntryException {
        DBConnection dbConnection = null;
        if (mapValues == null || mapValues.isEmpty()) {
            throw new InvalidEntryException("Invalid Values to Update.");
        }
        if (hotelTicker == null || hotelTicker.isEmpty()) {
            throw new InvalidEntryException("Invalid HotelTicker.");
        }
        try {
            dbConnection = new DBConnection(getDBCredentials(hotelTicker));
            Date startDate = HashRangeValue.getListHashRangeStartDate(mapValues);
            Date endDate = HashRangeValue.getListHashRangeEndDate(mapValues);
            if (startDate == null || endDate == null)
                throw new WitBookerAPIException("Error in the dailySet dates.");
            DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(dbConnection, startDate, endDate);
            dailyValuesDBHandler.getInventoryValuesBetweenDates();
            List<HashRangeResult> mapResult = new ArrayList<>();
            for (HashRangeValue hashRangeValue : mapValues) {
                String inventoryTicker = hashRangeValue.getTicker();
                HashRangeResult hashRangeResult = new HashRangeResult(inventoryTicker);
                for (String valueType : hashRangeValue.getHashRangeValues().keySet()) {
                    try {
                        RangeValue rangeValue = hashRangeValue.getRangeValue(valueType);
                        if (valueType.equals(HashRangeValue.RATE) && rangeValue.isOperator()) {
                            RangeValue<Float> oldRangeValue = dailyValuesDBHandler.getRatesByTicker(inventoryTicker);
                            rangeValue.operateRangeValue(oldRangeValue, true);
                        }
                        dailyValuesDBHandler.updateValuesByTicker(inventoryTicker, rangeValue, valueType);
                        hashRangeResult.putRangeResults(valueType, "success");
                    } catch (Exception e) {
                        logger.error("Hotel: " + hotelTicker + " error: " + e.getMessage());
                        hashRangeResult.putRangeResults(valueType, "error: " + e.getMessage());
                    }
                }
                mapResult.add(hashRangeResult);
            }
            return mapResult;
        } catch (WitBookerAPIException ex) {
            logger.error("Hotel: " + hotelTicker + " error: " + ex);
            throw ex;
        } catch (Exception ex) {
            logger.error("Hotel: " + hotelTicker + " error: " + ex);
            throw new WitBookerAPIException(ex);
        } finally {
            if (dbConnection != null) {
                try {
                    dbConnection.closeConnection();
                } catch (DBAccessException ex) {
                    logger.error("Hotel: " + hotelTicker + " error: " + ex);
                }
            }
        }
    }

    public List<Reservation> getReservationsByCreationDate(Date startDate, Date endDate, String hotelTicker)
            throws WitBookerAPIException, InvalidEntryException {
        validateDates(startDate, endDate);
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(getDBCredentials(hotelTicker));
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, "");
            ReservationDBHandler reservationDBHandler = new ReservationDBHandler(inventoryDBHandler);
            return reservationDBHandler.getReservationsBetweenCreationOrModificationDates(startDate, endDate);
        } catch (DBAccessException ex) {
            logger.error("Hotel: " + hotelTicker + " error: " + ex);
            throw new WitBookerAPIException(ex);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    public Reservation getReservationsByReservationId(String reservationId, String hotelTicker)
            throws WitBookerAPIException, InvalidEntryException {
        if (reservationId == null || reservationId.isEmpty()) {
            throw new InvalidEntryException("Invalid ReservationId");
        }
        if (hotelTicker == null || hotelTicker.isEmpty()) {
            throw new InvalidEntryException("Invalid HotelTicker.");
        }
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(getDBCredentials(hotelTicker));
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, "");
            ReservationDBHandler reservationDBHandler = new ReservationDBHandler(inventoryDBHandler);
            return reservationDBHandler.getReservationByReservationId(reservationId);
        } catch (DBAccessException ex) {
            logger.error("Hotel: " + hotelTicker + " error: " + ex);
            throw new WitBookerAPIException(ex);
        } finally {
            if (dbConnection != null) {
                try {
                    dbConnection.closeConnection();
                } catch (DBAccessException ex) {
                    logger.error("Hotel: " + hotelTicker + " error: " + ex);
                }
            }
        }
    }

    private boolean validateDates(Date startDate, Date endDate) throws InvalidEntryException {
        if (startDate == null || endDate == null) {
            throw new InvalidEntryException("The dates can't be null");
        }
        if (DateUtil.daysBetweenDates(startDate, endDate) < 0)
            throw new InvalidEntryException("The 'startDate' must be earlier than the 'endDate'");
        else
            return true;
    }

    /**
     * Information Contact for the WitBooking API services
     */
    public String contactInfo() {
        JsonArray infoContacts = new JsonArray();

        JsonObject contact1 = new JsonObject();
        contact1.addProperty("fullName", MiddlewareProperties.SUPPORT_FULL_NAME);
        contact1.addProperty("email", MiddlewareProperties.SUPPORT_MAIL);
        contact1.addProperty("phoneNumber", MiddlewareProperties.WITBOOKING_PHONE);
        infoContacts.add(contact1);

        JsonObject contact2 = new JsonObject();
        contact2.addProperty("fullName", MiddlewareProperties.INTEGRATION_MANAGER_FULL_NAME);
        contact2.addProperty("email", MiddlewareProperties.INTEGRATION_MANAGER_EMAIL);
        contact2.addProperty("phoneNumber", MiddlewareProperties.WITBOOKING_PHONE);
        infoContacts.add(contact2);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("api_version", ENDPOINT_VERSION);
        jsonObject.add("info_contacts", infoContacts);
        return jsonObject + "";
    }

    private DBCredentials getDBCredentials(String hotelTicker) throws WitBookerAPIException {
        try {
            return DBProperties.getDBCustomerByTicker(hotelTicker);
        } catch (MiddlewareException ex) {
            logger.error("Error getting the DBCredential for the Hotel '" + hotelTicker + "': " + ex.getMessage());
            throw new WitBookerAPIException(ex);
        }
    }
}
