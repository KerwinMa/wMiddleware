/*
 *  DailyValuesDBHandler.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.db.handlers;

import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.sqlinstructions.SQLInstructions;
import com.witbooking.middleware.exceptions.CalculateFormulaException;
import com.witbooking.middleware.exceptions.CurrencyConverterException;
import com.witbooking.middleware.exceptions.DateFormatException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.values.*;
import com.witbooking.middleware.model.values.types.ConstantValue;
import com.witbooking.middleware.model.values.types.FormulaValue;
import com.witbooking.middleware.model.values.types.SharedValue;
import com.witbooking.middleware.model.values.types.Value;
import com.witbooking.middleware.utils.DateUtil;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 21-ene-2013
 */
@SuppressWarnings("SuspiciousMethodCalls")
public class DailyValuesDBHandler extends DBHandler {

    /**
     * Object used to report on the Server's Log using Apache log4j.
     */
    private static final Logger logger = Logger.getLogger(DailyValuesDBHandler.class);
    private Date startDate;
    private Date endDate;
    //Map<"Ticker", Map<"Element_Type", RangeValue>>
//   private Map<String, Map<String, RangeValue>> rangeValuesHash;
    private Map<String, HashRangeValue> mapHashRangeValue;
    private InventoryDBHandler inventoryDBHandler;

    //ARI Optimization Step1
//    private float currencyMultiplier = 1;
    private String defaultCurrency = "";
    private CurrencyExchange currencyExchange;

    private static int MAX_RECURSION_LEVEL = 1;

    /**
     * Creates a new instance of
     * <code>DailyValuesDBHandler</code> without params.
     */
    public DailyValuesDBHandler() {
        this.inventoryDBHandler = new InventoryDBHandler();
        this.mapHashRangeValue = new HashMap<>();
    }

    public DailyValuesDBHandler(DBConnection dbConnection, Date startDate, Date endDate) {
        super(dbConnection);
        this.inventoryDBHandler = new InventoryDBHandler(dbConnection);
        this.startDate = DateUtil.toBeginningOfTheDay(startDate);
        this.endDate = DateUtil.toBeginningOfTheDay(endDate);
        this.mapHashRangeValue = new HashMap<>();
    }

    public DailyValuesDBHandler(DBConnection dbConnection, Date startDate, Date endDate, String locale) {
        super(dbConnection);
        this.inventoryDBHandler = new InventoryDBHandler(dbConnection, locale);
        this.startDate = DateUtil.toBeginningOfTheDay(startDate);
        this.endDate = DateUtil.toBeginningOfTheDay(endDate);
        this.mapHashRangeValue = new HashMap<>();
    }

    public DailyValuesDBHandler(InventoryDBHandler inventoryDBHandler, Date startDate, Date endDate) {
        super(inventoryDBHandler.getDbConnection());
        this.inventoryDBHandler = inventoryDBHandler;
        this.startDate = DateUtil.toBeginningOfTheDay(startDate);
        this.endDate = DateUtil.toBeginningOfTheDay(endDate);
        this.mapHashRangeValue = new HashMap<>();
    }

    public int updateRatesByTicker(String ticker, RangeValue<Float> values) throws DBAccessException, NonexistentValueException {
        return updateDailyValuesByTicker(ticker, values, getElementTypeId(HashRangeValue.RATE));
    }

    public int updateVariableByTicker(String ticker, RangeValue<Float> values) throws DBAccessException, NonexistentValueException {
        return updateDailyValuesByTicker(ticker, values, getElementTypeId(HashRangeValue.VARIABLE));
    }

    //This changes the current Availability taking into consideration 
    //the total Availability and the ReservationRS's quantity for this Inventory
    public int updateAvailabilityByTicker(final String tickerInv, RangeValue newValues) throws DBAccessException, NonexistentValueException {
        if (newValues == null) {
            return 0;
        }
        String ticker = getSuperTicker(tickerInv, 0);
        //if the SuperTicker is null, it can be updated
        if (ticker == null) {
            return 0;
        }
        RangeValue<Integer> newTotalValues = new RangeValue<>(AvailabilityDataValue.DEFAULT_VALUE);
        RangeValue<Integer> totalValues = getTotalAvailabilityByTicker(ticker);
        RangeValue<Integer> actualValues = getAvailabilityByTicker(ticker);
        Date dateIterator = (Date) startDate.clone();
        while (DateUtil.dateBetweenDaysRange(dateIterator, startDate, endDate)) {
            Integer totalValuesInt = 0;
            Integer actualValuesInt = 0;
            //[Bug] Should ask if #totalValues and #actualValues are null for v6,
            // because we can't know if exists this Availability values
            //Use new Float(totalValues.getFinalValueForADate(dateIterator)+"").intValue() to avoid Cast Exception
            if (totalValues != null) {
                totalValuesInt = totalValues.getValueForADate(dateIterator);
                if (totalValuesInt == null)
                    totalValuesInt = 0;
            }
            if (actualValues != null) {
                actualValuesInt = actualValues.getValueForADate(dateIterator);
                if (actualValuesInt == null)
                    actualValuesInt = 0;
            }
            int newTotalValue;
            int newActualValue;
            //Object avoiding cast exceptions
            Object newDayValue = newValues.getValueForADate(dateIterator);
            if (newDayValue != null) {
                newActualValue = new Float(newDayValue + "").intValue();
                //(totalValuesInt - actualValuesInt)= number of bookings this day
                newTotalValue = totalValuesInt - actualValuesInt + newActualValue;
            } else {
                newTotalValue = totalValuesInt;
                newActualValue = actualValuesInt;
            }
            if (actualValues != null) {
                actualValues.putValueForADate(dateIterator, newActualValue);
            }
            newTotalValues.putValueForADate(dateIterator, newTotalValue);
            DateUtil.incrementDays(dateIterator, 1);
        }
        return updateDailyValuesByTicker(ticker, newTotalValues, getElementTypeId(HashRangeValue.TOTAL_AVAILABILITY));
    }

    public int updateLocksByTicker(String ticker, RangeValue<Boolean> values) throws DBAccessException, NonexistentValueException {
        return updateDailyValuesByTicker(ticker, values, getElementTypeId(HashRangeValue.LOCK));
    }

    public int updateMinStayByTicker(String ticker, RangeValue<Integer> values) throws DBAccessException, NonexistentValueException {
        return updateDailyValuesByTicker(ticker, values, getElementTypeId(HashRangeValue.MIN_STAY));
    }

    public int updateMaxStayByTicker(String ticker, RangeValue<Integer> values) throws DBAccessException, NonexistentValueException {
        return updateDailyValuesByTicker(ticker, values, getElementTypeId(HashRangeValue.MAX_STAY));
    }

    public int updateMinNoticeByTicker(String ticker, RangeValue<Integer> values) throws DBAccessException, NonexistentValueException {
        return updateDailyValuesByTicker(ticker, values, getElementTypeId(HashRangeValue.MIN_NOTICE));
    }

    public int updateMaxNoticeByTicker(String ticker, RangeValue<Integer> values) throws DBAccessException, NonexistentValueException {
        return updateDailyValuesByTicker(ticker, values, getElementTypeId(HashRangeValue.MAX_NOTICE));
    }

    public String getSuperTicker(String ticker, final int elementTypeId) throws DBAccessException, NonexistentValueException {
        if (ticker == null)
            return null;
        Inventory inventory = inventoryDBHandler.getInventoryByTicker(ticker);
        if (inventory == null) {
            Discount discount = inventoryDBHandler.getDiscountByTicker(ticker);
            if (discount == null) {
                Service service = inventoryDBHandler.getServicesByTicker(ticker);
                if (service == null) {
                    //looking for variables
                    if (mapHashRangeValue.get(ticker) == null) {
                        throw new NonexistentValueException("Invalid ticker '" + ticker + "'");
                    }
                }
            }
            return ticker;
        }
//        logger.debug("inventory:" + inventory);
        EnumDataValueType valueType = EnumDataValueType.NULL_VALUE;
        DataValue dataValue;
        switch (elementTypeId) {
            case 0:
                dataValue = inventory.getAvailability();
                break;
            case 1:
                dataValue = inventory.getRate();
                break;
            case 2:
                dataValue = inventory.getAvailability();
                break;
            case 3:
                dataValue = inventory.getLock();
                break;
            case 4:
                dataValue = inventory.getMinStay();
                break;
            case 6:
                dataValue = inventory.getMaxStay();
                break;
            case 7:
                dataValue = inventory.getMinNotice();
                break;
            case 8:
                dataValue = inventory.getMaxNotice();
                break;
            default:
                return null;
        }
        if (dataValue != null)
            valueType = dataValue.getValueType();
        switch (valueType) {
            case SHARED:
                return ((SharedValue) dataValue.getValue()).getTicker();
            case OWN:
                return ticker;
            default:
                return null;
        }
    }


    private int updateDailyValuesByTicker(final String tickerInv, final RangeValue values, final int elementTypeId)
            throws DBAccessException, NonexistentValueException {
        String ticker = getSuperTicker(tickerInv, elementTypeId);
        if (ticker == null) {
            return 0;
        }
        String elementType = getElementType(elementTypeId);
        logger.debug("updateDailyValuesByTicker: '" + ticker + "' Element:'" + elementType + "'" + " value: " + values);
        if (values == null) {
            return 0;
        }
        //Added jus the Dates that had Values to update in the RangeValues
        List<Long> dateListToUpdate = values.dateLongListWithValues();
        int totalDays = dateListToUpdate.size();
        if (totalDays == 0) {
            return 0;
        }
//        logger.debug(" Total Days to modify: " + totalDays);
        String sqlCommand;
        PreparedStatement statement = null;
        Date dateModify = new Date();
        sqlCommand = SQLInstructions.DailyValuesDBHandler.UPDATE_DAILY_VALUE_BY_TICKER;
        int[] total;
        try {
            statement = prepareStatement(sqlCommand);
            //Modify the old values from the mapHashRangeValue, If exist!
            boolean existHashRangeValue = false;
            if (mapHashRangeValue.get(ticker) != null) {
                if (mapHashRangeValue.get(ticker).getRangeValue(elementType) != null) {
                    existHashRangeValue = true;
                }
            }
            for (Long longDate : dateListToUpdate) {
                Date dateIter = new Date(longDate);
                Object val = values.getFinalValueForADate(dateIter);
                List<Object> valuesSQL = new ArrayList<>();
                final Float value = setElementValue(elementTypeId, val);
//                logger.debug("valueStored: " + value + " day: " + DateUtil.calendarFormat(dateIter) + " val: " + val + " valSinFinal:" +
//                        " " +
//                        values.getValueForADate(dateIter));
                valuesSQL.add(value);
                valuesSQL.add(new java.sql.Timestamp(dateModify.getTime()));
                valuesSQL.add(ticker);
                valuesSQL.add(elementTypeId);
                valuesSQL.add(new java.sql.Date(longDate));
                addBatch(statement, valuesSQL);
                //logger.debug(" addBatch: " + statement);
                //Modify the old values from the mapHashRangeValue, If exist!
                if (existHashRangeValue) {
                    mapHashRangeValue.get(ticker).getRangeValue(elementType).putValueForADate(dateIter, val);
                }
            }
            //Updating values added above.
            total = executeBatch(statement);
        } finally {
            DAOUtil.close(statement);
        }
        int updated = 0;
        List<Long> dateListToInsert = new ArrayList<>();
        //Checking in which days perform the update successful.
        for (int row = 0; row < total.length; row++) {
            int i = total[row];
            //If the row wasn't updated, verify if have to be inserted
            if (i == 0) {
                dateListToInsert.add(dateListToUpdate.get(row));
            }
            updated = updated + i;
        }
        logger.debug(" Total 'inventario' rows updated: " + updated);
        if (updated == totalDays) {
            return updated;
        }
        if (updated == 0) {
            if (!validateIfInventoryElementExists(ticker, elementType)) {
                logger.error("The Element '" + elementType
                        + "' for the ticker '" + ticker + "' Have to be OWN to update.");
                throw new NonexistentValueException("The Element '" + elementType
                        + "' for the ticker '" + ticker + "' Have to be OWN to update.");
            }
        }
        //Validate if exists all the dates of the range in the DB, insert the dates if not exist
        final boolean existAllDays = validateIfExistsDateInRange(dateListToUpdate.get(0),
                dateListToUpdate.get(dateListToUpdate.size() - 1));
        if (existAllDays) {
            insertDaysBetweenRange(dateListToUpdate.get(0), dateListToUpdate.get(dateListToUpdate.size() - 1));
        }

        //Insert the Value for the date Range
        sqlCommand = SQLInstructions.DailyValuesDBHandler.INSERT_DAILY_VALUE_BY_TICKER;
        int inserted = 0;
        try {
            statement = prepareStatement(sqlCommand);
            for (Long longDate : dateListToInsert) {
                List<Object> valuesSQL = new ArrayList<>();
                valuesSQL.add(setElementValue(elementTypeId, values.getFinalValueForADate(new Date(longDate))));
                valuesSQL.add(new java.sql.Timestamp(dateModify.getTime()));
                valuesSQL.add(ticker);
                valuesSQL.add(elementTypeId);
                valuesSQL.add(new java.sql.Date(longDate));
                addBatch(statement, valuesSQL);
//            logger.debug(" addBatch: " + statement);
            }
            int[] totalInserted = executeBatch(statement);
            for (int i : totalInserted) {
                inserted = inserted + i;
            }
        } finally {
            DAOUtil.close(statement);
        }
        logger.debug(" Total 'inventario' rows inserted: " + inserted);
        return inserted + updated;
    }

    private boolean validateIfExistsDateInRange(final long start, final long end) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Object> valuesSQL = new ArrayList<>();
        final int totalDays = DateUtil.daysBetweenDates(new Date(start), new Date(end));
        try {
            valuesSQL.add(new java.sql.Date(start));
            valuesSQL.add(new java.sql.Date(end));
            statement = prepareStatement(SQLInstructions.DailyValuesDBHandler.VALIDATE_IF_ALL_DAYS_IN_RANGE_EXISTS, valuesSQL);
            resultSet = execute(statement);
            int sizeDays = 0;
            if (last(resultSet)) {
                sizeDays = getRow(resultSet);
            }
            final boolean ret = sizeDays < totalDays;
            if (ret)
                logger.debug(" Have to insert " + (totalDays - sizeDays) + " Dates.");
            return ret;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }


    private void insertDaysBetweenRange(final long start, final long end) throws DBAccessException {
        logger.info(" InsertDaysBetweenRange: " + new Date(start) + " to " + new Date(end));
        PreparedStatement statement = null;
        String sqlCommand = SQLInstructions.DailyValuesDBHandler.INSERT_NEW_DAYS;
        List<Object> valuesSQL = new ArrayList<>();
        valuesSQL.add(new java.sql.Date(start));
        valuesSQL.add(new java.sql.Date(start));
        valuesSQL.add(new java.sql.Date(end));
        valuesSQL.add(new java.sql.Date(start));
        valuesSQL.add(new java.sql.Date(end));
        try {
            statement = prepareStatement(sqlCommand, valuesSQL);
            int totalDates = executeUpdate(statement);
            logger.info(" Total of Dates inserted: " + totalDates);
        } finally {
            DAOUtil.close(statement);
        }
    }

    /**
     * Validate if exist a value to an inventory ticker and elementTypeId (tarifa, bloqueo,variable) (inventario_tipos table)
     *
     * @param ticker
     * @param elementType
     * @throws NonexistentValueException
     * @throws DBAccessException
     */
    public boolean validateIfInventoryElementExists(final String ticker, final String elementType)
            throws NonexistentValueException, DBAccessException {
        ResultSet resultSet = null;
        PreparedStatement statement = null;
        int elementTypeId = getElementTypeId(elementType);
        //Validate if exists the element for the ticker given in the DB
        String sqlCommand = SQLInstructions.DailyValuesDBHandler.VALIDATE_IF_INVENTORY_EXISTS;
        List<Object> valuesSQL = new ArrayList<>();
        valuesSQL.add(ticker);
        valuesSQL.add(elementTypeId);
        try {
            statement = prepareStatement(sqlCommand, valuesSQL);
            resultSet = execute(statement);
            return next(resultSet);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public RangeValue<Float> getFullRatesByTicker(String ticker) throws DBAccessException {
        return getFullRatesByTicker(ticker, new Date(), null);
    }

    private RangeValue<Float> getFullRatesByTicker(String ticker, Date notice, String promoCode) throws DBAccessException {
        try {
            return getFullRatesByTicker(ticker, null, notice, promoCode);
        } catch (CurrencyConverterException ex) {
            //shouldn't never happen
            logger.error(" Error getting the currency Exchange:" + ex.getMessage());
            return getRatesByTicker(ticker);
        }
    }

    public RangeValue<Float> getFullRatesByTicker(String ticker, String currency) throws DBAccessException, CurrencyConverterException {
        return getFullRatesByTicker(ticker, currency, new Date(), null);
    }

    public RangeValue<Float> getFullRatesByTicker(String ticker, String currency, String promoCode) throws DBAccessException, CurrencyConverterException {
        return getFullRatesByTicker(ticker, currency, new Date(), promoCode);
    }

    public float getCurrencyMultiplier(String currencyCode) throws DBAccessException, CurrencyConverterException {
        float currencyMultiplier = 1;
        if (currencyCode != null && !currencyCode.trim().isEmpty()) {
            getDefaultCurrency();
            if (!currencyCode.equalsIgnoreCase(defaultCurrency)) {
                if (currencyExchange == null) {
                    CurrencyDBHandler currencyDBHandler = null;
                    try {
                        currencyDBHandler = new CurrencyDBHandler();
                        currencyExchange = currencyDBHandler.getCurrencyExchange(defaultCurrency);
//                    logger.debug(" Currency Price: " + defaultCurrency + "-" + currencyCode + "=" + currencyMultiplier);
                    } catch (Exception ex) {
                        logger.error(" Error getting the currency Exchange: " + ex.getMessage());
                        currencyExchange = null;
                        throw new CurrencyConverterException(ex);
                    } finally {
                        if (currencyDBHandler != null) {
                            currencyDBHandler.closeDbConnection();
                        }
                    }
                }
                try {
                    currencyMultiplier = currencyExchange.getPrice(currencyCode);
                } catch (NonexistentValueException ex) {
                    logger.debug(" Error getting the currency Exchange: " + ex.getMessage());
                    throw new CurrencyConverterException(ex);
                }
            }
        }
        return currencyMultiplier;
    }

    //this are Rates with Discounts applied, in the currency selected, and with ask in the Date notice selected
    public RangeValue<Float> getFullRatesByTicker(String ticker, String currency, Date notice, String promoCode) throws DBAccessException,
            CurrencyConverterException {
        RangeValue<Float> values = getRatesByTicker(ticker);
        if (values == null) {
            //If values is null, we had a problem calculating the rates
            return null;
        }
        if (values.getDailySet().isEmpty()) {
            //If values is empty, we had a problem calculating the rates
            return values.clone();
        }
        RangeValue<Float> fullRates = new RangeValue<>(values.getDefaultValue());
        final RangeValue<Map.Entry<String, Float>> finalDiscountApplied = getFinalDiscountApplied(ticker, currency, notice, promoCode);
        Date dateIterator = (Date) startDate.clone();
        DateUtil.toBeginningOfTheDay(dateIterator);
        float currencyMultiplier = 1;
        try {
            currencyMultiplier = getCurrencyMultiplier(currency);
        } catch (CurrencyConverterException e) {
            logger.error(e);
        }
        while (DateUtil.dateBetweenDaysRange(dateIterator, startDate, endDate)) {
            final Map.Entry<String, Float> discountValueForDateIterator = finalDiscountApplied.getFinalValueForADate(dateIterator);
            Float finalValue = 0f;
            if (values.getValueForADate(dateIterator) != null) {
                finalValue = discountValueForDateIterator == null
                        ? (values.getValueForADate(dateIterator) * currencyMultiplier)
                        : (values.getValueForADate(dateIterator) * currencyMultiplier) - discountValueForDateIterator.getValue();
//            fullRates.putValueForADate(dateIterator, NumberUtils.roundFloat(finalValue, 2));
            }
            fullRates.putValueForADate(dateIterator, finalValue);
            DateUtil.incrementDays(dateIterator, 1);
        }
        return fullRates;
    }

    public RangeValue<Map.Entry<String, Float>> getFinalDiscountApplied(final String inventoryTicker,
                                                                        String currency, Date notice, String promoCode)
            throws DBAccessException {
        return getFinalDiscountApplied(inventoryDBHandler.getInventoryByTicker(inventoryTicker), currency, notice, promoCode);
    }

    public RangeValue<Map.Entry<String, Float>> getFinalDiscountApplied(final Inventory inventory,
                                                                        String currency, Date notice, final String promoCode)
            throws DBAccessException {
        RangeValue<Map.Entry<String, Float>> discounts = new RangeValue<>();
        List<Discount> discountList = inventory.getDiscountList();
        if (discountList == null) {
            return discounts;
        }
        RangeValue<Float> values = getRatesByTicker(inventory.getTicker());
        Date dateIterator = (Date) startDate.clone();
        DateUtil.toBeginningOfTheDay(dateIterator);
        final int daysStay = DateUtil.daysBetweenDates(startDate, endDate) + 1;
        if (notice == null) {
            notice = new Date();
        }
        //Represents the number of days between the booking's first date and the date that the booking was made​​ (notice).
        int daysNotice;
        try {
            daysNotice = DateUtil.daysBetweenDates(DateUtil.stringToCalendarDate(DateUtil.calendarFormat(notice)), startDate);
        } catch (DateFormatException e) {
            daysNotice = DateUtil.daysBetweenDates(notice, startDate) + 1;
            logger.error(e);
        }
        float currencyMultiplier = 1;
        try {
            currencyMultiplier = getCurrencyMultiplier(currency);
        } catch (CurrencyConverterException e) {
            logger.error(e);
        }
        while (DateUtil.dateBetweenDaysRange(dateIterator, startDate, endDate)) {
            Float actualRate = values.getValueForADate(dateIterator);
            //If the Actual rate is null, don't add it
            if (actualRate != null) {
                float reductionFinal = 0;
                Discount bestDiscount = null;
                for (Discount discount : discountList) {
                    if (discount.isActive()) {
                        float reductionDiscount;
                        reductionDiscount = 0;
                        //Ask If the discount is Locked
                        //ask if is in a valid Contract Period
                        if (discount.getLock().getValueType() == EnumDataValueType.SHARED
                                || discount.getLock().getValueType() == EnumDataValueType.OWN) {
                            RangeValue<Boolean> lockValue = this.getLockByTicker(discount.getTicker());
                            if (!lockValue.getFinalValueForADate(dateIterator) && discount.isContractible(notice)) {
                                reductionDiscount = Math.abs(discount.getApplicableDiscount(dateIterator, promoCode));
//                                reductionDiscount = Math.abs(discount.getApplicableDiscountWithoutPromoCode(dateIterator));
                            }
                        } else {
                            if (discount.isContractible(notice)) {
                                reductionDiscount = Math.abs(discount.getApplicableDiscount(dateIterator, promoCode));
//                            reductionDiscount = Math.abs(discount.getApplicableDiscountWithoutPromoCode(dateIterator));
                            }
                        }
                        if (reductionDiscount != 0) {
                            //Ask for the others restrictions
                            List<Map.Entry<DataValue<Integer>, Boolean>> valuesToCheck = new ArrayList<>();
                            //MinStay
                            valuesToCheck.add(new AbstractMap.SimpleEntry<DataValue<Integer>, Boolean>(discount.getMinStay(), true));
                            //MinNotice
                            valuesToCheck.add(new AbstractMap.SimpleEntry<DataValue<Integer>, Boolean>(discount.getMinNotice(), true));
                            //MaxStay
                            valuesToCheck.add(new AbstractMap.SimpleEntry<DataValue<Integer>, Boolean>(discount.getMaxStay(), false));
                            //MaxNotice
                            valuesToCheck.add(new AbstractMap.SimpleEntry<DataValue<Integer>, Boolean>(discount.getMaxNotice(), false));
                            for (final Map.Entry<DataValue<Integer>, Boolean> entry : valuesToCheck) {
                                // Checking if is NoticeDataValue should be compare with daysNotice,
                                // otherwise is compare with daysStay
                                final DataValue<Integer> dayValue = entry.getKey();
                                final Integer comparator = (dayValue instanceof NoticeDataValue) ? daysNotice : daysStay;
                                if (reductionDiscount != 0 &&
                                        dayValue.getValueType() == EnumDataValueType.CONSTANT &&
                                        ((ConstantValue<Integer>) dayValue.getValue()).getValue() >= 0
//                                    && ((ConstantValue<Integer>) entry.getValue()).getValue() > comparator
                                        ) {
                                    if (entry.getValue()
                                            ? ((ConstantValue<Integer>) dayValue.getValue()).getValue() > comparator
                                            : ((ConstantValue<Integer>) dayValue.getValue()).getValue() < comparator
                                            ) {
                                        reductionDiscount = 0;
                                    }
                                }
                            }
                            if (reductionDiscount != 0 && discount.isPercentage()) {
                                reductionDiscount = Math.abs(actualRate * reductionDiscount / 100);
                            }
                        }
                        if (reductionDiscount > reductionFinal) {
                            reductionFinal = reductionDiscount;
                            //Better discount found, Let's store it.
                            bestDiscount = discount;
                        }
                    }
                }
                if (bestDiscount != null) {
//                    reductionFinal = (discounts.containsKey(bestDiscount)) ? reductionFinal + discounts.get(bestDiscount) : reductionFinal;
//                    discounts.put(bestDiscount, reductionFinal * currencyMultiplier);
//                    reductionFinal=NumberUtils.roundFloat(reductionFinal * currencyMultiplier, 2);
//                    discounts.putValueForADate(dateIterator, new AbstractMap.SimpleEntry<String, Float>(bestDiscount.getTicker(), reductionFinal));
                    discounts.putValueForADate(dateIterator, new AbstractMap.SimpleEntry<>(bestDiscount.getTicker(), reductionFinal * currencyMultiplier));
                }
            }
            DateUtil.incrementDays(dateIterator, 1);
        }
        return discounts;
    }

    public RangeValue<Float> getRatesByTicker(String ticker) throws DBAccessException {
        return getRatesByTicker(ticker, 0);
    }

    private RangeValue<Float> getRatesByTicker(String ticker, int recursion) throws DBAccessException {
        if (recursion > MAX_RECURSION_LEVEL) {
            logger.error("Invalid Shared RateDataValue Hotel: '" +
                    getDbConnection().getDbCredentials().getTicker() + "' for ticker: '" + ticker + "'");
            return new RangeValue<>(RateDataValue.DEFAULT_VALUE);
        }
        HashRangeValue dailyRanges;
        RangeValue<Float> values = null;
        if (mapHashRangeValue.get(ticker) != null) {
            if (mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.RATE) != null) {
                return mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.RATE).clone();
            } else if (mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.VARIABLE) != null) {
                //If the ticker exist and don't have rates, can be a variable, check it!
                return mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.VARIABLE);
            }
        } else {
            dailyRanges = new HashRangeValue(ticker);
            mapHashRangeValue.put(ticker, dailyRanges);
        }
        dailyRanges = mapHashRangeValue.get(ticker);
        EnumDataValueType typeRate = EnumDataValueType.NULL_VALUE;
        Value<Float> valueRate = null;
        DataValueHolder dataValueHolder = getDataValueHolderByTicker(ticker);
        if (dataValueHolder != null && dataValueHolder.getRate() != null) {
            typeRate = dataValueHolder.getRate().getValueType();
            valueRate = dataValueHolder.getRate().getValue();
        }
        if (typeRate == EnumDataValueType.SHARED) {
            //warning for errors in migrations V5
            String sharedTicker = ((SharedValue) valueRate).getTicker();
            if (sharedTicker.equalsIgnoreCase(ticker)) {
                values = new RangeValue<>(RateDataValue.DEFAULT_VALUE);
            } else {
                values = getRatesByTicker(sharedTicker, recursion + 1);
            }
        } else if (typeRate == EnumDataValueType.FORMULA) {
            values = new RangeValue<>(RateDataValue.DEFAULT_VALUE);
            FormulaValue<Float> formulaValue = ((FormulaValue<Float>) valueRate);
            Date dateIterator = (Date) startDate.clone();
            DateUtil.toBeginningOfTheDay(dateIterator);
            while (DateUtil.dateBetweenDaysRange(dateIterator, startDate, endDate)) {
                Map<String, Float> variableValues = new HashMap<>();
                //if some variable of the formula is null, the value for that day is null
                boolean isNull = false;
                for (String tickerFormula : formulaValue.getTickersSet()) {
                    Float varValue = null;
                    //warning for errors in migrations V5
                    if (!tickerFormula.equalsIgnoreCase(ticker)) {
                        try {
                            varValue = getRatesByTicker(tickerFormula, recursion + 1).getValueForADate(dateIterator);
                        } catch (Throwable ex) {
                            logger.error(" Error calculating the formula for the ticker '" + ticker
                                    + "' tickerFormula:'" + tickerFormula + "' formulaValue:'" + formulaValue.getFormulaValue()
                                    + "':" + ex.getMessage());
                        }
                        //If the value for an Inventory is 0, the formula value should be null.
                        if (varValue != null && varValue == 0
                                && inventoryDBHandler.getObjectInMapperFromTicker(Inventory.class.getSimpleName(), tickerFormula) != null) {
                            varValue = null;
                        }
                    }
                    if (varValue == null) {
                        isNull = true;
                        break;
                    }
                    variableValues.put(tickerFormula, varValue);
                }
                if (!isNull) {
                    //TODO: validate the formula values
                    Float dayValue;
                    try {
                        dayValue = formulaValue.calculateFormula(variableValues);
                    } catch (CalculateFormulaException ex) {
                        logger.error(" Error calculating the rate formula for the date: '" +
                                DateUtil.calendarFormat(dateIterator) + "' Hotel: '" + this.getDbConnection()
                                .getDbCredentials().getNameDB() + "' Ticker: '" + ticker + "' : " +
                                ex.getMessage());
                        return new RangeValue<>(RateDataValue.DEFAULT_VALUE);
                    }
                    values.putValueForADate(dateIterator, dayValue);
                }
                DateUtil.incrementDays(dateIterator, 1);
            }
        } else if (typeRate == EnumDataValueType.CONSTANT) {
            //Possibles constant prices for Services or Extras
            float constant = ((ConstantValue<Float>) valueRate).getConstantValue();
            values = new RangeValue<>(new DailyValue<>(startDate, endDate, constant), constant);
        } else {
            values = new RangeValue<>(RateDataValue.DEFAULT_VALUE);
        }
        dailyRanges.putRangeValues(HashRangeValue.RATE, values);
        return values != null ? values.clone() : null;
    }

    public RangeValue<Integer> getAvailabilityByTicker(String ticker) throws DBAccessException {
        return getAvailabilityByTicker(ticker, 0);
    }

    private RangeValue<Integer> getAvailabilityByTicker(String ticker, int recursion) throws DBAccessException {
        if (recursion > MAX_RECURSION_LEVEL) {
            logger.error("Invalid Shared AvailabilityDataValue Hotel: '" +
                    getDbConnection().getDbCredentials().getTicker() + "' for ticker: '" + ticker + "'");
            return new RangeValue<>(AvailabilityDataValue.DEFAULT_VALUE);
        }
        HashRangeValue dailyRanges;
        RangeValue<Integer> values = null;
        if (mapHashRangeValue.get(ticker) != null) {
            if (mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.ACTUAL_AVAILABILITY) != null) {
                return mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.ACTUAL_AVAILABILITY).clone();
            }
        } else {
            dailyRanges = new HashRangeValue(ticker);
            mapHashRangeValue.put(ticker, dailyRanges);
        }
        dailyRanges = mapHashRangeValue.get(ticker);

        Inventory inventory = inventoryDBHandler.getInventoryByTicker(ticker);
//        logger.debug("inventory:" + inventory);
        if (inventory != null && inventory.getAvailability().getValueType() == EnumDataValueType.SHARED &&
                !ticker.equalsIgnoreCase(((SharedValue) inventory.getAvailability().getValue()).getTicker())) {
            return getAvailabilityByTicker(((SharedValue) inventory.getAvailability().getValue()).getTicker(), recursion + 1);
        } else {
            values = new RangeValue<>(AvailabilityDataValue.DEFAULT_VALUE);
            RangeValue<Integer> totalAvailability = dailyRanges.getRangeValue(HashRangeValue.TOTAL_AVAILABILITY);
//            logger.debug(" Buscar el arbol de disponibilidad para  " + ticker);
            List<Inventory> inventoryList = inventoryDBHandler.getTreeAvailability(ticker);
            List<Reservation> reservationList;
            if (inventoryList == null || inventoryList.isEmpty()) {
                reservationList = new ArrayList<>();
            } else {
                ReservationDBHandler reservationDBHandler = new ReservationDBHandler(inventoryDBHandler);
                reservationList = reservationDBHandler.getReservationByInventoriesOccupied(startDate, endDate, Inventory.listOfId(inventoryList));
            }
//            logger.debug(" reservationList: " + reservationList);
            Date dateIterator = (Date) startDate.clone();
            DateUtil.toBeginningOfTheDay(dateIterator);
            while (DateUtil.dateBetweenDaysRange(dateIterator, startDate, endDate)) {
                int totalReserved = 0;
                for (Reservation reservation : reservationList) {
                    for (RoomStay roomStay : reservation.getRoomStays()) {
                        if (roomStay.isOccupied(dateIterator)) {
                            totalReserved = totalReserved + roomStay.getQuantity();
                        }
                    }
                }
                int totalRooms = 0;
                if (totalAvailability != null) {
                    totalRooms = totalAvailability.getFinalValueForADate(dateIterator);
                }
                totalReserved = totalRooms - totalReserved;
                if (totalReserved < 0) {
                    totalReserved = 0;
                }
                values.putValueForADate(dateIterator, totalReserved);
                DateUtil.incrementDays(dateIterator, 1);
            }
        }
        dailyRanges.putRangeValues(HashRangeValue.ACTUAL_AVAILABILITY, values);
        return values != null ? values.clone() : null;
    }

    public RangeValue<Integer> getTotalAvailabilityByTicker(String ticker) throws DBAccessException {
        return getTotalAvailabilityByTicker(ticker, 0);
    }

    private RangeValue<Integer> getTotalAvailabilityByTicker(String ticker, int recursion) throws DBAccessException {
        if (recursion > MAX_RECURSION_LEVEL) {
            logger.error("Invalid Shared AvailabilityDataValue Hotel: '" +
                    getDbConnection().getDbCredentials().getTicker() + "' for ticker: '" + ticker + "'");
            return new RangeValue<>(AvailabilityDataValue.DEFAULT_VALUE);
        }
        HashRangeValue dailyRanges;
        RangeValue<Integer> values;
        if (mapHashRangeValue.get(ticker) != null) {
            if (mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.TOTAL_AVAILABILITY) != null) {
                return mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.TOTAL_AVAILABILITY).clone();
            }
            dailyRanges = mapHashRangeValue.get(ticker);
        } else {
            dailyRanges = new HashRangeValue(ticker);
            mapHashRangeValue.put(ticker, dailyRanges);
        }
        Inventory inventory = inventoryDBHandler.getInventoryByTicker(ticker);
        if (inventory != null && inventory.getAvailability().getValueType() == EnumDataValueType.SHARED &&
                !ticker.equalsIgnoreCase(((SharedValue) inventory.getAvailability().getValue()).getTicker())) {
            return getTotalAvailabilityByTicker(((SharedValue) inventory.getAvailability().getValue()).getTicker(), recursion + 1);
        } else {
            values = dailyRanges.getRangeValue(HashRangeValue.TOTAL_AVAILABILITY);
        }
        dailyRanges.putRangeValues(HashRangeValue.TOTAL_AVAILABILITY, values);
        return values != null ? values.clone() : null;
    }

    public RangeValue<Boolean> getLockByTicker(String ticker) throws DBAccessException {
        return getLockByTicker(ticker, 0);
    }

    private RangeValue<Boolean> getLockByTicker(String ticker, int recursion) throws DBAccessException {
        if (recursion > MAX_RECURSION_LEVEL) {
            logger.error("Invalid Shared LockDataValue Hotel: '" +
                    getDbConnection().getDbCredentials().getTicker() + "' for ticker: '" + ticker + "'");
            return new RangeValue<>(LockDataValue.DEFAULT_VALUE);
        }
        HashRangeValue dailyRanges;
        RangeValue<Boolean> values = null;
        if (mapHashRangeValue.get(ticker) != null) {
            if (mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.LOCK) != null) {
                return mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.LOCK).clone();
            }
        } else {
            dailyRanges = new HashRangeValue(ticker);
            mapHashRangeValue.put(ticker, dailyRanges);
        }

        dailyRanges = mapHashRangeValue.get(ticker);

        EnumDataValueType typeLock = EnumDataValueType.NULL_VALUE;
        Value<Boolean> valueLock = null;
        DataValueHolder dataValueHolder = getDataValueHolderByTicker(ticker);
        if (dataValueHolder != null && dataValueHolder.getLock() != null) {
            typeLock = dataValueHolder.getLock().getValueType();
            valueLock = dataValueHolder.getLock().getValue();
        }
        if (typeLock == EnumDataValueType.SHARED &&
                !ticker.equalsIgnoreCase(((SharedValue) valueLock).getTicker())) {
            values = getLockByTicker(((SharedValue) valueLock).getTicker(), recursion + 1);
        }
        if (values == null) {
            values = new RangeValue<>(LockDataValue.DEFAULT_VALUE);
        }
        dailyRanges.putRangeValues(HashRangeValue.LOCK, values);
        return values != null ? values.clone() : null;
    }

    public RangeValue<Integer> getMinStayByTicker(String ticker) throws DBAccessException {
        return getMinStayByTicker(ticker, 0);
    }

    private RangeValue<Integer> getMinStayByTicker(String ticker, int recursion) throws DBAccessException {
        if (recursion > MAX_RECURSION_LEVEL) {
            logger.error("Invalid Shared MinStayDataValue Hotel: '" +
                    getDbConnection().getDbCredentials().getTicker() + "' for ticker: '" + ticker + "'");
            return new RangeValue<>(StayDataValue.DEFAULT_VALUE);
        }
        HashRangeValue dailyRanges;
        RangeValue<Integer> values = null;
        if (mapHashRangeValue.get(ticker) != null) {
            if (mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.MIN_STAY) != null) {
                return mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.MIN_STAY).clone();
            }
        } else {
            dailyRanges = new HashRangeValue(ticker);
            mapHashRangeValue.put(ticker, dailyRanges);
        }

        dailyRanges = mapHashRangeValue.get(ticker);

        EnumDataValueType typeStay = EnumDataValueType.NULL_VALUE;
        Value<Integer> valueStay = null;
        DataValueHolder dataValueHolder = getDataValueHolderByTicker(ticker);
        if (dataValueHolder != null && dataValueHolder.getMinStay() != null) {
            typeStay = dataValueHolder.getMinStay().getValueType();
            valueStay = dataValueHolder.getMinStay().getValue();
        }
        if (typeStay == EnumDataValueType.SHARED &&
                !ticker.equalsIgnoreCase(((SharedValue) valueStay).getTicker())) {
            values = getMinStayByTicker(((SharedValue) valueStay).getTicker(), recursion + 1);
        } else if (typeStay == EnumDataValueType.CONSTANT) {
            int constant = ((ConstantValue<Integer>) valueStay).getConstantValue();
            values = new RangeValue<>(new DailyValue<>(startDate, endDate, constant), constant);
        } else if (typeStay == EnumDataValueType.NULL_VALUE) {
            values = new RangeValue<>(null);
        } else if (typeStay == EnumDataValueType.OWN) {
            values = new RangeValue<>(StayDataValue.DEFAULT_VALUE);
        }
        if (values == null) {
            values = new RangeValue<>(null);
        }
        dailyRanges.putRangeValues(HashRangeValue.MIN_STAY, values);
        return values != null ? values.clone() : null;
    }

    public RangeValue<Integer> getMaxStayByTicker(String ticker) throws DBAccessException {
        return getMaxStayByTicker(ticker, 0);
    }

    private RangeValue<Integer> getMaxStayByTicker(String ticker, int recursion) throws DBAccessException {
        if (recursion > MAX_RECURSION_LEVEL) {
            logger.error("Invalid Shared MaxStayDataValue Hotel: '" +
                    getDbConnection().getDbCredentials().getTicker() + "' for ticker: '" + ticker + "'");
            return new RangeValue<>(StayDataValue.DEFAULT_VALUE);
        }
        HashRangeValue dailyRanges;
        RangeValue<Integer> values = null;
        if (mapHashRangeValue.get(ticker) != null) {
            if (mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.MAX_STAY) != null) {
                return mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.MAX_STAY).clone();
            }
        } else {
            dailyRanges = new HashRangeValue(ticker);
            mapHashRangeValue.put(ticker, dailyRanges);
        }

        dailyRanges = mapHashRangeValue.get(ticker);

        EnumDataValueType typeStay = EnumDataValueType.NULL_VALUE;
        Value<Integer> valueStay = null;
        DataValueHolder dataValueHolder = getDataValueHolderByTicker(ticker);
        if (dataValueHolder != null && dataValueHolder.getMaxStay() != null) {
            typeStay = dataValueHolder.getMaxStay().getValueType();
            valueStay = dataValueHolder.getMaxStay().getValue();
        }
        if (typeStay == EnumDataValueType.SHARED &&
                !ticker.equalsIgnoreCase(((SharedValue) valueStay).getTicker())) {
            values = getMaxStayByTicker(((SharedValue) valueStay).getTicker(), recursion + 1);
        } else if (typeStay == EnumDataValueType.CONSTANT) {
            int constant = ((ConstantValue<Integer>) valueStay).getConstantValue();
            values = new RangeValue<>(new DailyValue<>(startDate, endDate, constant), constant);
        } else if (typeStay == EnumDataValueType.NULL_VALUE) {
            values = new RangeValue<>(null);
        } else if (typeStay == EnumDataValueType.OWN) {
            values = new RangeValue<>(StayDataValue.DEFAULT_VALUE);
        }
        if (values == null) {
            values = new RangeValue<>(null);
        }
        dailyRanges.putRangeValues(HashRangeValue.MAX_STAY, values);
        return values != null ? values.clone() : null;
    }

    public RangeValue<Integer> getMinNoticeByTicker(String ticker) throws DBAccessException {
        return getMinNoticeByTicker(ticker, 0);
    }

    private RangeValue<Integer> getMinNoticeByTicker(String ticker, int recursion) throws DBAccessException {
        if (recursion > MAX_RECURSION_LEVEL) {
            logger.error("Invalid Shared MinNoticeDataValue Hotel: '" +
                    getDbConnection().getDbCredentials().getTicker() + "' for ticker: '" + ticker + "'");
            return new RangeValue<>(NoticeDataValue.DEFAULT_VALUE);
        }
        HashRangeValue dailyRanges;
        RangeValue<Integer> values = null;
        if (mapHashRangeValue.get(ticker) != null) {
            if (mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.MIN_NOTICE) != null) {
                return mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.MIN_NOTICE).clone();
            }
        } else {
            dailyRanges = new HashRangeValue(ticker);
            mapHashRangeValue.put(ticker, dailyRanges);
        }

        dailyRanges = mapHashRangeValue.get(ticker);

        EnumDataValueType typeNotice = EnumDataValueType.NULL_VALUE;
        Value<Integer> valueNotice = null;

        DataValueHolder dataValueHolder = getDataValueHolderByTicker(ticker);
        if (dataValueHolder != null && dataValueHolder.getMinNotice() != null) {
            typeNotice = dataValueHolder.getMinNotice().getValueType();
            valueNotice = dataValueHolder.getMinNotice().getValue();
        }
        if (typeNotice == EnumDataValueType.SHARED &&
                !ticker.equalsIgnoreCase(((SharedValue) valueNotice).getTicker())) {
            values = getMinNoticeByTicker(((SharedValue) valueNotice).getTicker(), recursion + 1);
        } else if (typeNotice == EnumDataValueType.CONSTANT) {
            int constant = ((ConstantValue<Integer>) valueNotice).getConstantValue();
            values = new RangeValue<>(new DailyValue<>(startDate, endDate, constant), constant);
        } else if (typeNotice == EnumDataValueType.NULL_VALUE) {
            values = new RangeValue<>(null);
        } else if (typeNotice == EnumDataValueType.OWN) {
            values = new RangeValue<>(NoticeDataValue.DEFAULT_VALUE);
        }
        if (values == null) {
            values = new RangeValue<>(null);
        }
        dailyRanges.putRangeValues(HashRangeValue.MIN_NOTICE, values);
        return values != null ? values.clone() : null;
    }

    public RangeValue<Integer> getMaxNoticeByTicker(String ticker) throws DBAccessException {
        return getMaxNoticeByTicker(ticker, 0);
    }

    private RangeValue<Integer> getMaxNoticeByTicker(String ticker, int recursion) throws DBAccessException {
        if (recursion > MAX_RECURSION_LEVEL) {
            logger.error("Invalid Shared MaxNoticeDataValue Hotel: '" +
                    getDbConnection().getDbCredentials().getTicker() + "' for ticker: '" + ticker + "'");
            return new RangeValue<>(NoticeDataValue.DEFAULT_VALUE);
        }
        HashRangeValue dailyRanges;
        RangeValue<Integer> values = null;
        if (mapHashRangeValue.get(ticker) != null) {
            if (mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.MAX_NOTICE) != null) {
                return mapHashRangeValue.get(ticker).getRangeValue(HashRangeValue.MAX_NOTICE).clone();
            }
        } else {
            dailyRanges = new HashRangeValue(ticker);
            mapHashRangeValue.put(ticker, dailyRanges);
        }
        dailyRanges = mapHashRangeValue.get(ticker);

        EnumDataValueType typeNotice = EnumDataValueType.NULL_VALUE;
        Value<Integer> valueNotice = null;

        DataValueHolder dataValueHolder = getDataValueHolderByTicker(ticker);
        if (dataValueHolder != null && dataValueHolder.getMaxNotice() != null) {
            typeNotice = dataValueHolder.getMaxNotice().getValueType();
            valueNotice = dataValueHolder.getMaxNotice().getValue();
        }
        if (typeNotice == EnumDataValueType.SHARED &&
                !ticker.equalsIgnoreCase(((SharedValue) valueNotice).getTicker())) {
            values = getMaxNoticeByTicker(((SharedValue) valueNotice).getTicker(), recursion + 1);
        } else if (typeNotice == EnumDataValueType.CONSTANT) {
            int constant = ((ConstantValue<Integer>) valueNotice).getConstantValue();
            values = new RangeValue<>(new DailyValue<>(startDate, endDate, constant), constant);
        } else if (typeNotice == EnumDataValueType.NULL_VALUE) {
            values = new RangeValue<>(null);
        } else if (typeNotice == EnumDataValueType.OWN) {
            values = new RangeValue<>(NoticeDataValue.DEFAULT_VALUE);
        }
        if (values == null) {
            values = new RangeValue<>(null);
        }
        dailyRanges.putRangeValues(HashRangeValue.MAX_NOTICE, values);
        return values != null ? values.clone() : null;
    }


    public void getSelectedInventoryValuesBetweenDates(String ticker) throws DBAccessException,
            NonexistentValueException {
        getSelectedInventoryValuesBetweenDates(Collections.singletonList(ticker));
    }

    public void getSelectedInventoryValuesBetweenDates(List<String> tickerList) throws DBAccessException,
            NonexistentValueException {
        if (tickerList == null || tickerList.isEmpty()) {
            getInventoryValuesBetweenDates();
            return;
        }
        final Set<String> tickers = new HashSet<>();
        for (String ticker : tickerList) {
            DataValueHolder holderByTicker = getDataValueHolderByTicker(ticker);
            if (holderByTicker != null) {
                tickers.add(holderByTicker.getTicker());
                if (holderByTicker.getClass() == Inventory.class) {
                    if (holderByTicker.getRate().isSharedValue())
                        tickers.add(((SharedValue) holderByTicker.getRate().getValue()).getTicker());
                    else if (holderByTicker.getRate().isFormulaValue())
                        tickers.addAll(((FormulaValue) holderByTicker.getRate().getValue()).getTickersSet());
                    if (holderByTicker.getAvailability().isSharedValue())
                        tickers.add(((SharedValue) holderByTicker.getAvailability().getValue()).getTicker());
                }
                if (holderByTicker.getLock().isSharedValue())
                    tickers.add(((SharedValue) holderByTicker.getLock().getValue()).getTicker());
                if (holderByTicker.getMinStay().isSharedValue())
                    tickers.add(((SharedValue) holderByTicker.getMinStay().getValue()).getTicker());
                if (holderByTicker.getMaxStay().isSharedValue())
                    tickers.add(((SharedValue) holderByTicker.getMaxStay().getValue()).getTicker());
                if (holderByTicker.getMinNotice().isSharedValue())
                    tickers.add(((SharedValue) holderByTicker.getMinNotice().getValue()).getTicker());
                if (holderByTicker.getMaxNotice().isSharedValue())
                    tickers.add(((SharedValue) holderByTicker.getMaxNotice().getValue()).getTicker());
            }
        }
        if (tickers.isEmpty()) {
            getInventoryValuesBetweenDates();
            return;
        }
        mapHashRangeValue.clear();
        mapHashRangeValue = new HashMap<>();
        String sqlCommand = SQLInstructions.DailyValuesDBHandler.SELECT_VALUES
                + "WHERE inventario.elemento_id=inventario_elementos.id "
                + "AND inventario_elementos.obsoleto=0 "
                + "AND inventario.fecha_id=fechas.id "
                + "AND fechas.fecha BETWEEN ? AND ? "
                + "AND inventario_elementos.ticker in (";
        for (Iterator<String> iterator = tickers.iterator(); iterator.hasNext(); ) {
            String next = iterator.next();
            sqlCommand += "'" + next + "'";
            if (iterator.hasNext()) {
                sqlCommand += ",  ";
            }
        }
        sqlCommand += ")  ";
        List<Object> valuesSQL = new ArrayList<>();
        valuesSQL.add(new java.sql.Date(startDate.getTime()));
        valuesSQL.add(new java.sql.Date(endDate.getTime()));
        PreparedStatement statement = prepareStatement(sqlCommand, valuesSQL);
        ResultSet resultSet = execute(statement);
        parseResultSetDailyValues(resultSet);
        DAOUtil.close(statement, resultSet);
        //TODO: this validation can be deleted if all the obsolete InventoryElements are correct
        validateMapHashRangeValue();
    }

    public void getInventoryValuesBetweenDates() throws DBAccessException, NonexistentValueException {
        mapHashRangeValue.clear();
        mapHashRangeValue = new HashMap<String, HashRangeValue>();
        final String sqlCommand = SQLInstructions.DailyValuesDBHandler.SELECT_VALUES
                + "WHERE inventario.elemento_id=inventario_elementos.id "
                + "AND inventario_elementos.obsoleto=0 "
                + "AND inventario.fecha_id=fechas.id "
                + "AND fechas.fecha BETWEEN ? AND ? ;";
        List<Object> valuesSQL = new ArrayList<>();
        valuesSQL.add(new java.sql.Date(startDate.getTime()));
        valuesSQL.add(new java.sql.Date(endDate.getTime()));
        PreparedStatement statement = prepareStatement(sqlCommand, valuesSQL);
        ResultSet resultSet = execute(statement);
        parseResultSetDailyValues(resultSet);
        DAOUtil.close(statement, resultSet);
        //TODO: this validation can be deleted if all the obsolete InventoryElements are correct
        validateMapHashRangeValue();
    }

    private void parseResultSetDailyValues(ResultSet resultSet) throws DBAccessException, NonexistentValueException {
        HashRangeValue dailyRanges;
        RangeValue values;
        while (next(resultSet)) {
            Float value = getFloat(resultSet, 1);
            String ticker = getString(resultSet, 2);
            Integer elementId = getInt(resultSet, 3);
            Date date = getDate(resultSet, 4);

            dailyRanges = mapHashRangeValue.get(ticker);
            if (dailyRanges == null) {
                dailyRanges = new HashRangeValue(ticker);
                mapHashRangeValue.put(ticker, dailyRanges);
            }
            values = dailyRanges.getRangeValue(getElementType(elementId));
            if (values == null) {
                values = new RangeValue(defaultElementType(elementId));
                dailyRanges.putRangeValues(getElementType(elementId), values);
            }
            values.putValueForADate(date, getElementValue(elementId, value));
        }
    }

    private void validateMapHashRangeValue() throws DBAccessException {
        //Query all the inventories in one petition
        //TODO: this validation can be deleted if all the obsolete InventoryElements are correct
        inventoryDBHandler.getFullListObjectFromMapper(Inventory.class.getSimpleName());
        inventoryDBHandler.getFullListObjectFromMapper(Service.class.getSimpleName());
        inventoryDBHandler.getFullListObjectFromMapper(Discount.class.getSimpleName());
        for (String ticker : mapHashRangeValue.keySet()) {
            List<String> elementTypes = new ArrayList<>(mapHashRangeValue.get(ticker).getHashRangeValues().keySet());
            DataValueHolder dataValueHolder = getDataValueHolderByTicker(ticker);
            if (dataValueHolder != null) {
                for (String elementType : elementTypes) {
                    EnumDataValueType valueType = EnumDataValueType.NULL_VALUE;
                    try {
                        if (HashRangeValue.ACTUAL_AVAILABILITY.equals(elementType)) {
                            valueType = dataValueHolder.getAvailability().getValueType();
                        } else if (HashRangeValue.RATE.equals(elementType)) {
                            valueType = dataValueHolder.getRate().getValueType();
                        } else if (HashRangeValue.TOTAL_AVAILABILITY.equals(elementType)) {
                            valueType = dataValueHolder.getAvailability().getValueType();
                        } else if (HashRangeValue.LOCK.equals(elementType)) {
                            valueType = dataValueHolder.getLock().getValueType();
                        } else if (HashRangeValue.MIN_STAY.equals(elementType)) {
                            valueType = dataValueHolder.getMinStay().getValueType();
                        } else if (HashRangeValue.VARIABLE.equals(elementType)) {
                            valueType = EnumDataValueType.OWN;
                        } else if (HashRangeValue.MAX_STAY.equals(elementType)) {
                            valueType = dataValueHolder.getMaxStay().getValueType();
                        } else if (HashRangeValue.MIN_NOTICE.equals(elementType)) {
                            valueType = dataValueHolder.getMinNotice().getValueType();
                        } else if (HashRangeValue.MAX_NOTICE.equals(elementType)) {
                            valueType = dataValueHolder.getMaxNotice().getValueType();
                        }
                    } catch (Exception e) {
                        logger.error("Error with elementType '" + elementType + "' for ticker '" + dataValueHolder
                                .getTicker() + "' in hotel '" + getDbConnection().getDbCredentials().getTicker() + "");
                        logger.error(e);
                    }
                    if (valueType != EnumDataValueType.OWN) {
                        mapHashRangeValue.get(ticker).getHashRangeValues().remove(elementType);
                    }
                }
            }
        }
    }

    public int updateValuesByTicker(String ticker, RangeValue values, String elementType) throws DBAccessException,
            NonexistentValueException {
        if (HashRangeValue.ACTUAL_AVAILABILITY.equals(elementType)) {
            return updateAvailabilityByTicker(ticker, values);
        } else {
            return updateDailyValuesByTicker(ticker, values, getElementTypeId(elementType));
        }
    }

    public String printDailyValues() {
        String print = "{DailyValues:";
        for (Map.Entry<String, HashRangeValue> entry : mapHashRangeValue.entrySet()) {
            String ticker = entry.getKey();
            HashRangeValue map = entry.getValue();

            print = print + "{" + ticker + ":";
            for (Map.Entry<String, RangeValue> entry1 : map.getHashRangeValues().entrySet()) {
                String type = entry1.getKey();
                RangeValue rangeValue = entry1.getValue();

                print = print + "{" + type + ":[";
                for (Iterator it = rangeValue.getDailySet().iterator(); it.hasNext(); ) {
                    DailyValue dailyValue = (DailyValue) it.next();
                    print = print + dailyValue;
                    if (it.hasNext()) {
                        print = print + ",";
                    }
                }
                print = print + "]}";

            }
            print = print + "}";
        }
        print = print + "}";
        return print;
    }

    private Object getElementValue(Integer elementId, Float value) {
        switch (elementId) {
            case 1:
                return value;
            case 3:
                if (value > 0) {
                    return Boolean.TRUE;
                } else {
                    return Boolean.FALSE;
                }
            case 5:
                return value;
            default:
                return value.intValue();
        }
    }

    private Float setElementValue(Integer elementId, Object value) {
        String valString = value + "";
        switch (elementId) {
            case 3:
                if (Boolean.parseBoolean(valString)) {
                    return (float) 1;
                } else {
                    return (float) 0;
                }
            default:
                return Float.parseFloat(valString);
        }
    }

    private Object defaultElementType(Integer elementId) {
        switch (elementId) {
            case 1:
                return RateDataValue.DEFAULT_VALUE;
            case 2:
                return AvailabilityDataValue.DEFAULT_VALUE;
            case 3:
                return LockDataValue.DEFAULT_VALUE;
            case 4:
                return StayDataValue.DEFAULT_VALUE;
            case 5:
                return VariableDataValue.DEFAULT_VALUE;
            case 6:
                return StayDataValue.DEFAULT_VALUE;
            case 7:
                return NoticeDataValue.DEFAULT_VALUE;
            case 8:
                return NoticeDataValue.DEFAULT_VALUE;
            default:
                return null;
        }
    }

    private String getElementType(Integer elementId) throws NonexistentValueException {
        switch (elementId) {
            case 0:
                return HashRangeValue.ACTUAL_AVAILABILITY;
            case 1:
                return HashRangeValue.RATE;
            case 2:
                return HashRangeValue.TOTAL_AVAILABILITY;
            case 3:
                return HashRangeValue.LOCK;
            case 4:
                return HashRangeValue.MIN_STAY;
            case 5:
                return HashRangeValue.VARIABLE;
            case 6:
                return HashRangeValue.MAX_STAY;
            case 7:
                return HashRangeValue.MIN_NOTICE;
            case 8:
                return HashRangeValue.MAX_NOTICE;
        }
        throw new NonexistentValueException("Invalid Element Type '" + elementId + "'");
    }

    private int getElementTypeId(String elementType) throws NonexistentValueException {
        if (elementType == null) {
            return -1;
        } else if (HashRangeValue.ACTUAL_AVAILABILITY.equals(elementType)) {
            return 0;
        } else if (HashRangeValue.RATE.equals(elementType)) {
            return 1;
        } else if (HashRangeValue.TOTAL_AVAILABILITY.equals(elementType)) {
            return 2;
        } else if (HashRangeValue.LOCK.equals(elementType)) {
            return 3;
        } else if (HashRangeValue.MIN_STAY.equals(elementType)) {
            return 4;
        } else if (HashRangeValue.VARIABLE.equals(elementType)) {
            return 5;
        } else if (HashRangeValue.MAX_STAY.equals(elementType)) {
            return 6;
        } else if (HashRangeValue.MIN_NOTICE.equals(elementType)) {
            return 7;
        } else if (HashRangeValue.MAX_NOTICE.equals(elementType)) {
            return 8;
        }
        throw new NonexistentValueException("Invalid Element Type '" + elementType + "'");
    }

    private DataValueHolder getDataValueHolderByTicker(String ticker) throws DBAccessException {
        //looking for the correct object
        DataValueHolder holder = (Inventory) inventoryDBHandler.getObjectInMapperFromTicker(Inventory.class.getSimpleName(), ticker);
        if (holder == null || !holder.getTicker().equals(ticker)) {
            holder = (Discount) inventoryDBHandler.getObjectInMapperFromTicker(Discount.class.getSimpleName(), ticker);
            if (holder == null || !holder.getTicker().equals(ticker)) {
                holder = (Service) inventoryDBHandler.getObjectInMapperFromTicker(Service.class.getSimpleName(), ticker);
            }
        }
        return holder;
    }

    /**
     * Getter for property 'defaultCurrency'.
     *
     * @return Value for property 'defaultCurrency'.
     */
    public String getDefaultCurrency() {
        if (defaultCurrency.isEmpty()) {
            HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(inventoryDBHandler.getDbConnection());
            try {
                defaultCurrency = hotelConfigurationDBHandler.getDefaultCurrency();
            } catch (DBAccessException ex) {
                logger.error(" Error getting the Default Currency: " + ex.getMessage());
            }
        }
        return defaultCurrency;
    }

    /**
     * Setter for property 'defaultCurrency'.
     *
     * @param defaultCurrency Value to set for property 'defaultCurrency'.
     */
    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public InventoryDBHandler getInventoryDBHandler() {
        return inventoryDBHandler;
    }

    public void setInventoryDBHandler(InventoryDBHandler inventoryDBHandler) {
        this.inventoryDBHandler = inventoryDBHandler;
    }
}
