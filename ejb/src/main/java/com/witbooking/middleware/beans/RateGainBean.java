/*
 *  RateGainBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.DailyValuesDBHandler;
import com.witbooking.middleware.db.handlers.HotelConfigurationDBHandler;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.RemoteServiceException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.exceptions.integration.rategain.RateGainException;
import com.witbooking.middleware.integration.ota.model.*;
import com.witbooking.middleware.integration.rategain.ConstantsRateGain;
import com.witbooking.middleware.integration.rategain.model.*;
import com.witbooking.middleware.integration.rategain.model.ErrorType;
import com.witbooking.middleware.integration.rategain.model.ErrorsType;
import com.witbooking.middleware.integration.rategain.model.HotelType;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.values.DailyValue;
import com.witbooking.middleware.model.values.EnumDataValueType;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.model.values.RateDataValue;
import com.witbooking.middleware.model.values.types.DaysCondition;
import com.witbooking.middleware.model.values.types.SharedValue;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.OTAUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.math.BigDecimal;
import java.util.*;

/**
 * Session Bean implementation class RateGainBean
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 20, 2013
 */
@Stateless
public class RateGainBean implements RateGainBeanRemote, RateGainBeanLocal {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RateGainBean.class);
    @EJB
    private ConnectionBeanLocal connectionBean;
    private static final String MSG_ERROR_WEB_SERVICES_CONNECTION = "Error communicating with our web services, please try again later.";
    private static final String LANGUAGE = "eng";
    private static final String USE_FULL_RATES = "useFullRatesChMg";
    private static final String SEND_CHILD_INV_TICKERS = "sendChildInvChMg";
    //private static final String MSG_ERR_INVALID_HOTEL_CODE = "The hotel code given is invalid.";
//    private static final String MSG_ERROR_PARSING = "Error parsing xml given.";

    @Override
    public OTAHotelResNotifRQ getOTAHotelResNotifRQ(final String reservationId,
                                                    final String hotelTicker) throws RateGainException {
        OTAHotelResNotifRQ ret = null;
        DBConnection dBConnection = null;
        try {
            dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dBConnection);
            final ReservationDBHandler reservationDBHandler = new ReservationDBHandler(inventoryDBHandler);
            Reservation reservation = reservationDBHandler.getReservationByReservationId(reservationId);
            if (reservation != null) {
                ret = getOTAHotelResNotifRQ(reservation, dBConnection, inventoryDBHandler, hotelTicker);
            } else {
                throw new RateGainException(new NullPointerException("ReservationRS with ticker " + reservationId + " can not be found."));
            }
        } catch (DBAccessException | ExternalFileException | NonexistentValueException ex) {
            logger.error(ex);
            throw new RateGainException(ex);
        } finally {
            DAOUtil.close(dBConnection);
        }
        return ret;
    }

    @Override
    public HotelARIUpdateRS getHotelARIUpdateRS(final HotelARIUpdateRQ hotelARIUpdateRQ) throws RateGainException {
        //Validating request
        RateGainRSInterface rateGain = validate(hotelARIUpdateRQ, HotelARIUpdateRS.class);
        if (rateGain != null) {
            return (HotelARIUpdateRS) rateGain;
        } else {
            logger.debug("NO ENCONTRO ERRORES");
        }
        //All NullPointerException already validated.
        //Getting the hotelTicker of request.
        final String hotelTicker = hotelARIUpdateRQ.getHotelARIUpdateRequestHotelTicker();
        DBConnection dBConnection = null;
        try {
            dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            final List<HotelARIDataType> hotelARIData = hotelARIUpdateRQ.getHotelARIUpdateRequestHotelARIData();
            for (final HotelARIDataType itemARIData : hotelARIData) {
                final Calendar calendar = Calendar.getInstance();
                final ApplicationControlType applicationControlType = itemARIData.getApplicationControl();
                final Date start = itemARIData.getApplicationControlStartDate();
//                calendar.setTime(itemARIData.getApplicationControlEndDate());
//                calendar.add(Calendar.DAY_OF_YEAR, 1);
                final Date end = itemARIData.getApplicationControlEndDate();

                List<Date> daysToEdit = new ArrayList<Date>();
                Date dateIterator = new Date(start.getTime());
                final boolean anyDay = applicationControlType.anyDay();
                final HashMap<Integer, Boolean> daysValid = new HashMap<Integer, Boolean>();
                daysValid.put(Calendar.MONDAY, applicationControlType.isMon());
                daysValid.put(Calendar.THURSDAY, applicationControlType.isThu());
                daysValid.put(Calendar.WEDNESDAY, applicationControlType.isWed());
                daysValid.put(Calendar.TUESDAY, applicationControlType.isTue());
                daysValid.put(Calendar.FRIDAY, applicationControlType.isFri());
                daysValid.put(Calendar.SATURDAY, applicationControlType.isSat());
                daysValid.put(Calendar.SUNDAY, applicationControlType.isSun());
                while (dateIterator.before(end) || dateIterator.equals(end)) {
                    calendar.setTime(dateIterator);
                    if (anyDay) {
                        if (daysValid.get(calendar.get(Calendar.DAY_OF_WEEK))) {
                            daysToEdit.add(dateIterator);
                        }
                    } else {
                        daysToEdit.add(dateIterator);
                    }
                    calendar.add(Calendar.DAY_OF_YEAR, 1);
                    dateIterator = calendar.getTime();
                }
                if (daysToEdit.isEmpty()) {
                    final ErrorType errorType = ErrorType.getErrorTypeNoImplementationCodeInvalidValue("Range value date given is null");
                    return new HotelARIUpdateRS(errorType, hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
                }
                final String invTypeCode = itemARIData.getProductReferenceInvTypeCode();
//                final String ratePlanCode = itemARIData.getProductReferenceRatePlanCode();
                final String currency = itemARIData.getRateAmountsCurrency();
                //Validate currency type.
                if (currency != null) {
                    final String defaultCurrency = new HotelConfigurationDBHandler(dBConnection).getDefaultCurrency();
                    if (defaultCurrency != null && !defaultCurrency.equals("") && !currency.equals(defaultCurrency)) {
                        final ErrorType errorType = ErrorType.getErrorTypeNoImplementationCodeInvalidCurrency("Currency type must be " + defaultCurrency);
                        return new HotelARIUpdateRS(errorType, hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
                    }
                }
                final Float rate = itemARIData.getRateAmountsAmount();
                final AvailabilityStatusType lock = itemARIData.getAvailabilityMaster();
                final Integer availability = itemARIData.getBookingLimitTransientAllotmentAllotment();
                final Integer minNotice = itemARIData.getBookingRulesMinAdvancedBookingOffSet();
                final Integer maxNotice = itemARIData.getBookingRulesMaxAdvancedBookingOffset();
                final Integer minStay = itemARIData.getBookingRulesMinLoSOnArrival();
                final Integer maxStay = itemARIData.getBookingRulesMaxLoSOnArrival();
                //TODO hace falta implementar el update de la descripcion de las lineas de inventario.
//                final String description = itemARIData.getDescription();
                //Getting necessary objects to make the update.
                final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dBConnection);
                final DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, start, end);
                final Inventory inventory = inventoryDBHandler.getInventoryByTicker(invTypeCode);
                dailyValuesDBHandler.getInventoryValuesBetweenDates();
                //Checking if try to updated an  property who is not own type.
                if (rate != null && !EnumDataValueType.OWN.equals(inventory.getRate().getValueType())) {
                    final ErrorType error = ErrorType.getErrorTypeNoImplementationCodeInvalidValue(
                            getMSG(dBConnection, "/HotelARIUpdateRQ/HotelARIUpdateRequest/HotelARIData/RateAmounts/Base/Amount", hotelTicker));
                    return new HotelARIUpdateRS(error, hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
                }
                if (lock != null && !EnumDataValueType.OWN.equals(inventory.getLock().getValueType())) {
                    final ErrorType error = ErrorType.getErrorTypeNoImplementationCodeInvalidValue(
                            getMSG(dBConnection, "/HotelARIUpdateRQ/HotelARIUpdateRequest/HotelARIData/Availability/Master", hotelTicker));
                    return new HotelARIUpdateRS(error, hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
                }
                if (availability != null && !EnumDataValueType.OWN.equals(inventory.getAvailability().getValueType())) {
                    final ErrorType error = ErrorType.getErrorTypeNoImplementationCodeInvalidValue(
                            getMSG(dBConnection, "/HotelARIUpdateRQ/HotelARIUpdateRequest/HotelARIData/BookingLimit/TransientAllotment/Allotment", hotelTicker));
                    return new HotelARIUpdateRS(error, hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
                }
                if (minNotice != null && !EnumDataValueType.OWN.equals(inventory.getMinNotice().getValueType())) {
                    final ErrorType error = ErrorType.getErrorTypeNoImplementationCodeInvalidValue(
                            getMSG(dBConnection, "/HotelARIUpdateRQ/HotelARIUpdateRequest/HotelARIData/BookingRules/MinAdvancedBookingOffset", hotelTicker));
                    return new HotelARIUpdateRS(error, hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
                }
                if (maxNotice != null && !EnumDataValueType.OWN.equals(inventory.getMaxNotice().getValueType())) {
                    final ErrorType error = ErrorType.getErrorTypeNoImplementationCodeInvalidValue(
                            getMSG(dBConnection, "/HotelARIUpdateRQ/HotelARIUpdateRequest/HotelARIData/BookingRules/MaxAdvancedBookingOffset", hotelTicker));
                    return new HotelARIUpdateRS(error, hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
                }
                if (minStay != null && !EnumDataValueType.OWN.equals(inventory.getMinStay().getValueType())) {
                    final ErrorType error = ErrorType.getErrorTypeNoImplementationCodeInvalidValue(
                            getMSG(dBConnection, "/HotelARIUpdateRQ/HotelARIUpdateRequest/HotelARIData/BookingRules/MinLoSOnArrival", hotelTicker));
                    return new HotelARIUpdateRS(error, hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
                }
                if (maxStay != null && !EnumDataValueType.OWN.equals(inventory.getMaxStay().getValueType())) {
                    final ErrorType error = ErrorType.getErrorTypeNoImplementationCodeInvalidValue(
                            getMSG(dBConnection, "/HotelARIUpdateRQ/HotelARIUpdateRequest/HotelARIData/BookingRules/MaxLoSOnArrival", hotelTicker));
                    return new HotelARIUpdateRS(error, hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
                }
                int rows = 0;
                //RangeValues to update.
                final RangeValue<Float> ratesRangeValue = rate == null ? null : dailyValuesDBHandler.getFullRatesByTicker(invTypeCode);
                final RangeValue<Boolean> locksRangeValue = lock == null ? null : dailyValuesDBHandler.getLockByTicker(invTypeCode);
                final RangeValue<Integer> availabilityRangeValue = availability == null ? null : dailyValuesDBHandler.getAvailabilityByTicker(invTypeCode);
                final RangeValue<Integer> minNoticeRangeValue = minNotice == null ? null : dailyValuesDBHandler.getMinNoticeByTicker(invTypeCode);
                final RangeValue<Integer> maxNoticeRangeValue = maxNotice == null ? null : dailyValuesDBHandler.getMaxNoticeByTicker(invTypeCode);
                final RangeValue<Integer> minStayRangeValue = minStay == null ? null : dailyValuesDBHandler.getMinStayByTicker(invTypeCode);
                final RangeValue<Integer> maxStayRangeValue = maxStay == null ? null : dailyValuesDBHandler.getMaxStayByTicker(invTypeCode);
                for (Date date : daysToEdit) {
                    if (ratesRangeValue != null) {
                        ratesRangeValue.putValueForADate(date, rate);
                    }
                    if (locksRangeValue != null) {
                        locksRangeValue.putValueForADate(date, lock == AvailabilityStatusType.CLOSED);
                    }
                    if (availabilityRangeValue != null) {
                        availabilityRangeValue.putValueForADate(date, availability);
                    }
                    //TODO: the notices values is given in hours for v6. This can change.
                    if (minNoticeRangeValue != null) {
                        minNoticeRangeValue.putValueForADate(date, minNotice * 24);
                    }
                    if (maxNoticeRangeValue != null) {
                        maxNoticeRangeValue.putValueForADate(date, maxNotice * 24);
                    }
                    if (minStayRangeValue != null) {
                        minStayRangeValue.putValueForADate(date, minStay);
                    }
                    if (maxStayRangeValue != null) {
                        maxStayRangeValue.putValueForADate(date, maxStay);
                    }
                }
                if (ratesRangeValue != null) {
                    rows = rows + dailyValuesDBHandler.updateRatesByTicker(invTypeCode, ratesRangeValue);
                }
                if (locksRangeValue != null) {
                    rows = rows + dailyValuesDBHandler.updateLocksByTicker(invTypeCode, locksRangeValue);
                }
                if (availabilityRangeValue != null) {
                    rows = rows + dailyValuesDBHandler.updateAvailabilityByTicker(invTypeCode, availabilityRangeValue);
                }
                if (minNoticeRangeValue != null) {
                    rows = rows + dailyValuesDBHandler.updateMinNoticeByTicker(invTypeCode, minNoticeRangeValue);
                }
                if (maxNoticeRangeValue != null) {
                    rows = rows + dailyValuesDBHandler.updateMaxNoticeByTicker(invTypeCode, maxNoticeRangeValue);
                }
                if (minStayRangeValue != null) {
                    rows = rows + dailyValuesDBHandler.updateMinStayByTicker(invTypeCode, minStayRangeValue);
                }
                if (maxStayRangeValue != null) {
                    rows = rows + dailyValuesDBHandler.updateMaxStayByTicker(invTypeCode, maxStayRangeValue);
                }
                logger.debug("Rows updated: " + rows);
            }
        } catch (DBAccessException ex) {
            return new HotelARIUpdateRS(handleException(ex, ErrorType.getErrorTypeApplicationErrorCodeUndeterminedError()), hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
        } catch (ExternalFileException ex) {
            return new HotelARIUpdateRS(handleException(ex, ErrorType.getErrorTypeApplicationErrorCodeUndeterminedError()), hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
        } catch (NonexistentValueException ex) {
            //hotelTicker given not found.
            return new HotelARIUpdateRS(handleException(ex, ErrorType.getErrorTypeProccessingExceptionCodeNoHotelMatchFound(msgHotelNotFound(hotelTicker))),
                    hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
        } finally {
            DAOUtil.close(dBConnection);
        }
        return new HotelARIUpdateRS(hotelARIUpdateRQ.getVersion(), hotelARIUpdateRQ.getTarget());
    }

    @Override
    public HotelARIGetRS getHotelARIGetRS(final HotelARIGetRQ hotelARIGetRQ) throws RateGainException {
        //Validating request
        RateGainRSInterface rateGain = validate(hotelARIGetRQ, HotelARIGetRS.class);
        if (rateGain
                != null) {
            return (HotelARIGetRS) rateGain;
        }
        //Getting the hotelTicker of request.
        final String hotelTicker = hotelARIGetRQ.getHotelARIGetRequestHotelTicker();
        //Getting the hotel database connection.
        DBConnection dBConnection = null;
        try {
            dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            //Setting response hotel code.
            final HotelARIDataSetType hotelARIDataSetType = new HotelARIDataSetType();
            hotelARIDataSetType.setHotelCode(hotelTicker);
            final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dBConnection, LANGUAGE);
            final String currency = getDefaultCurrency(dBConnection);
            for (final HotelARIGetRQ.HotelARIGetRequests.HotelARIGetRequest hotelARIGetRequest : hotelARIGetRQ.getHotelARIGetRequests().getHotelARIGetRequest()) {
                final Date start = hotelARIGetRequest.getApplicationControl().getStart();
                final Date end = hotelARIGetRequest.getApplicationControl().getEnd();
                final String roomStay = hotelARIGetRequest.getProductReference().getInvTypeCode();
                final String ratePlan = hotelARIGetRequest.getProductReference().getRatePlanCode();
                final DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, start, end);
                dailyValuesDBHandler.getInventoryValuesBetweenDates();
                final Inventory inventory = inventoryDBHandler.getInventoryByTicker(roomStay);
                logger.debug("Inventory: " + inventory);
                if (inventory == null) {
                    final String errorMsg = "Inventory Type Not Found: " + roomStay + ".";
                    final ErrorType error = ErrorType.getErrorTypeRequiredFieldMissingCodeInvalidRoomTypeCode(errorMsg);
                    return new HotelARIGetRS(handleException(new NullPointerException(errorMsg), error),
                            hotelARIGetRQ.getVersion(), hotelARIGetRQ.getTarget());
                }
                final String mealPlanTicker = inventory.getMealPlan().getTicker();
                final DaysCondition checkInDaysCondition = inventory.getCheckInDays();
                final DaysCondition checkOutDaysCondition = inventory.getCheckOutDays();
                final String description = inventory.getName();
                Date dateIterator = start;
                RangeValue<Float> values = dailyValuesDBHandler.getFullRatesByTicker(inventory.getTicker());
                while (dateIterator.before(end) || dateIterator.equals(end)) {
                    //Rate at @dateIterator.
                    Float rate = dailyValuesDBHandler.getRatesByTicker(inventory.getTicker()).getFinalValueForADate(dateIterator);
                    rate = rate == null ? 0 : rate;
                    //Promotional Rate at @dateIterator.
                    Float promotionalRate = values.getFinalValueForADate(dateIterator);
                    promotionalRate = promotionalRate == null ? 0 : rate;
                    final float discount = rate > promotionalRate ? rate - promotionalRate : 0;
                    final Calendar calendar = Calendar.getInstance();
                    calendar.setTime(dateIterator);
//                    calendar.add(Calendar.DAY_OF_YEAR, 1);
//                    final Date dateAfterIterator = calendar.getTime();
                    //Look @dateIterator date.
                    final AvailabilityStatusType master = dailyValuesDBHandler.getLockByTicker(inventory.getTicker()).getFinalValueForADate(dateIterator)
                            ? AvailabilityStatusType.CLOSED
                            : AvailabilityStatusType.OPEN;
                    final int day = calendar.get(Calendar.DAY_OF_WEEK) - 1;
                    //Arrival @dateIterator date.
                    final AvailabilityStatusType arrival = checkInDaysCondition.getDayValue(day)
                            ? AvailabilityStatusType.OPEN
                            : AvailabilityStatusType.CLOSED;
                    //Departure @dateIterator date.
                    final AvailabilityStatusType departure = checkOutDaysCondition.getDayValue(day)
                            ? AvailabilityStatusType.OPEN
                            : AvailabilityStatusType.CLOSED;
                    //Availability at @dateIterator.
                    final Integer allotment = dailyValuesDBHandler.getAvailabilityByTicker(inventory.getTicker()).getFinalValueForADate(dateIterator);
                    //TODO: the notices values is given in hours for v6. This can change.
                    //MinNotice of @dateIterator.
                    Integer minNotice = dailyValuesDBHandler.getMinNoticeByTicker(inventory.getTicker()).getFinalValueForADate(dateIterator);
                    if (minNotice != null) {
                        minNotice = (int) Math.ceil((double) minNotice / 24);
                    }
                    //MaxNotice of @dateIterator.
                    Integer maxNotice = dailyValuesDBHandler.getMaxNoticeByTicker(inventory.getTicker()).getFinalValueForADate(dateIterator);
                    if (maxNotice != null) {
                        maxNotice = (int) Math.ceil((double) maxNotice / 24);
                    }
                    //MinStay of @dateIterator.
                    final Integer minStay = dailyValuesDBHandler.getMinStayByTicker(inventory.getTicker()).getFinalValueForADate(dateIterator);
                    //MaxStay of @dateIterator.
                    final Integer maxStay = dailyValuesDBHandler.getMaxStayByTicker(inventory.getTicker()).getFinalValueForADate(dateIterator);
                    hotelARIDataSetType.addHotelARIDataOrHotelARIStatus(
                            new HotelARIDataType(
                                    roomStay, ratePlan, (Date) dateIterator.clone(), (Date) dateIterator.clone(), currency,
                                    OccupancyCodeType.SR, promotionalRate, mealPlanTicker,
                                    discount, master, arrival, departure, allotment, minNotice,
                                    maxNotice, minStay, maxStay, description)
                    );
                    //Increase the date.
                    DateUtil.incrementDays(dateIterator, 1);
                }
            }
            return new HotelARIGetRS(hotelARIGetRQ.getTarget(), hotelARIGetRQ.getVersion(), hotelARIDataSetType);
        } catch (DBAccessException ex) {
            return new HotelARIGetRS(handleException(ex, ErrorType.getErrorTypeApplicationErrorCodeUndeterminedError()),
                    hotelARIGetRQ.getVersion(), hotelARIGetRQ.getTarget());
        } catch (ExternalFileException ex) {
            return new HotelARIGetRS(handleException(ex, ErrorType.getErrorTypeApplicationErrorCodeUndeterminedError()),
                    hotelARIGetRQ.getVersion(), hotelARIGetRQ.getTarget());
        } catch (NonexistentValueException ex) {
            final ErrorType errorInvalidHotelCode = ErrorType.getErrorTypeProccessingExceptionCodeNoHotelMatchFound(msgHotelNotFound(hotelTicker));
            //hotelTicker given not found.
            return new HotelARIGetRS(handleException(ex, errorInvalidHotelCode),
                    hotelARIGetRQ.getVersion(), hotelARIGetRQ.getTarget());
        } finally {
            DAOUtil.close(dBConnection);
        }
    }

    /**
     * Returns an hotel tickers list related with the username and password
     * given.
     *
     * @param request XML String who represents an
     *                {@link HotelPropertyListGetRQ} object.
     * @return {@link HotelPropertyListGetRS} with a list of hotel tickers
     * related with the username and password given.
     * @throws RateGainException
     * @see HotelPropertyListGetRS
     * @see HotelPropertyListGetRQ
     * @see ScrappingBeanLocal
     */
    @Override
    public HotelPropertyListGetRS getHotelPropertyList(final HotelPropertyListGetRQ request) throws RateGainException {
//        logger.debug("getHotelPropertyList");
        HotelPropertyListGetRS ret;
        try {
            //First validate the information received.
            RateGainRSInterface rateGain = validate(request, HotelPropertyListGetRS.class);
            if (rateGain != null) {
                return (HotelPropertyListGetRS) rateGain;
            }
            final String user = request.getUserName();
//            logger.debug("user: " + user);
            final String password = request.getPassword();
//            logger.debug("password: " + password);
            // @see ConnectionBean#getHotelTickers
            final String hotelTickersList = connectionBean.getHotelTickers(user, password);
//            logger.debug(hotelTickersList);
            final JsonParser jsonParser = new JsonParser();
            final JsonElement jsonResponse = jsonParser.parse(hotelTickersList);
            if (jsonResponse == null || jsonResponse.isJsonNull()) {
                //This case means an internal error.
                //TODO NOW ERROR RECIBI ALGO NULO, REPORTAR PARA NOSOTROS
                final ErrorType err = ErrorType.getErrorTypeApplicationErrorCodeUndeterminedError();
                err.setValue(MSG_ERROR_WEB_SERVICES_CONNECTION);
                ret = new HotelPropertyListGetRS(err,
                        request.getVersion(),
                        request.getTarget());
            } else if (!jsonResponse.isJsonArray()) {
                //This case means there was an error in the authentication.
                ret = new HotelPropertyListGetRS(ErrorType.getErrorTypeAuthenticationCodePasswordInvalid(null),
                        request.getVersion(),
                        request.getTarget());
            } else {
                //This case means that the users are perfectly authentic.
                ret = getHotelList(jsonResponse.getAsJsonArray(), request.getVersion(), request.getTarget());
            }
            return ret;
        } catch (RemoteServiceException ex) {
            return new HotelPropertyListGetRS(handleException(ex));
        }
    }

    /**
     * @param request XML String who represents *
     *                {@link HotelPropertyListGetRQ}. <code>HotelPropertyListGetRQ</code>
     *                contains the username, the password and the hotelTicker to consult.
     * @return An {@link HotelProductListGetRS} who contains a list of Room Stay
     * and Rate Plans of the inventory active.
     * @throws RateGainException contains any type of exception that occurred.
     * @see InventoryDBHandler#getInventoryForScraping()
     * @see HotelProductListGetRS
     * @see HotelProductListGetRQ
     */
    @Override
    public HotelProductListGetRS getHotelProductList(final HotelProductListGetRQ request) throws RateGainException {
        HotelProductListGetRS ret;
        //First validate the information received.
        RateGainRSInterface rateGain = validate(request, HotelProductListGetRS.class);
        if (rateGain
                != null) {
            return (HotelProductListGetRS) rateGain;
        }
        final String hotelTicker;
        //Getting hotelTicker from request. Already earned this is not null. 
        hotelTicker = request.getHotelProductListRequest().getHotelCode();
        //Getting Hotel database connection.
        DBConnection dBConnection = null;
        try {
            try {
                dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            } catch (ExternalFileException ex) {
                return new HotelProductListGetRS(handleException(ex), request.getVersion(), request.getTarget());
            } catch (NonexistentValueException ex) {
                //Invalid hotel ticker.
                return new HotelProductListGetRS(handleException(ex, ErrorType.getErrorTypeProccessingExceptionCodeNoHotelMatchFound(msgHotelNotFound(hotelTicker))),
                        request.getVersion(), request.getTarget());
            } catch (DBAccessException ex) {
                return new HotelProductListGetRS(handleException(ex), request.getVersion(), request.getTarget());
            }
            final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dBConnection);
            try {
                List<HotelProductsType.HotelProduct> products = new ArrayList<HotelProductsType.HotelProduct>();
                for (Inventory item : inventoryDBHandler.getInventoriesOwnRates()) {
                    //validate for possible errors in Migrations v5
                    if (item.isValid()) {
                        products.add(new HotelProductsType.HotelProduct(item.getTicker(), item.getTicker(),
                                item.getAccommodation().getName(),
                                getRatePlanName(item)));
                    }
                }
                ret = new HotelProductListGetRS(new HotelProductsType(products, hotelTicker), request.getVersion(), request.getTarget());
            } catch (DBAccessException ex) {
                return new HotelProductListGetRS(handleException(ex), request.getVersion(), request.getTarget());
            }
        } finally {
            DAOUtil.close(dBConnection);
        }
        return ret;
    }

    /**
     * Performs the validation functions of each request defined on
     * {@link RateGainValidationRQInterface#validate()} and {@link RateGainValidationRQInterface#validateClass()
     * }.
     *
     * @param request  The request object to validate.
     * @param retClass The class to return if an error occurs.
     * @return <code>null</code> if don't find any error, otherwise new
     * {@link RateGainRSInterface} of type <code>retClass</code> passed by
     * parameters.
     */
    private RateGainRSInterface validate(RateGainValidationRQInterface request, Class<? extends RateGainRSInterface> retClass) {
        if (request == null) {
            logger.debug("Request is null");
            return getError(retClass, new ErrorsType(ErrorType.getErrorTypeProcessingExceptionCodeInvalidValue("Request can not be null.")));
        }
        ErrorsType validate = request.validate();
        //Validates the user and password received.
        final String username = request.getUserName();
        final String password = request.getPassword();
        try {
            final boolean login = connectionBean.validateAuthentication(username, password);
            if (!login) {
                final String MSG_ERR = "User " + username + " and " + password + " don't match. Could be username not valid or password not valid.";
                if (validate == null) {
                    validate = new ErrorsType(ErrorType.getErrorTypeAuthenticationCodePasswordInvalid(MSG_ERR));
                } else {
                    validate.addErrorType(ErrorType.getErrorTypeAuthenticationCodePasswordInvalid(MSG_ERR));
                }
                return getError(retClass, validate);
            }
        } catch (/*Scrapping*/Exception ex) {
            return getError(retClass, new ErrorsType(handleException(ex)));
        }
        return validate == null ? null : getError(retClass, validate);
    }


    private OTAHotelResNotifRQ getOTAHotelResNotifRQ(final Reservation reservation,
                                                     final DBConnection dBConnection,
                                                     final InventoryDBHandler inventoryDBHandler,
                                                     final String hotelTicker)
            throws RateGainException {
        if (reservation == null) {
            throw new RateGainException(new NullPointerException("ReservationRS can not be null."));
        }
        final HotelReservationsType.HotelReservation otaReservation = new HotelReservationsType.HotelReservation();
        otaReservation.setCreateDateTime(reservation.getDateCreation());
        otaReservation.setResStatus(getResStatus(reservation));
        final float taxPercentage = getTaxPercentage(inventoryDBHandler);
        Date modified = reservation.getDateCreation();
        //Set the information of each RoomStay.
        for (final RoomStay roomStay : reservation.getRoomStays()) {
            otaReservation.addRoomStays(getRoomStay(roomStay, reservation,
                    inventoryDBHandler, hotelTicker, taxPercentage, dBConnection));
            if (roomStay.getDateModification() != null && modified.before(roomStay.getDateModification())) {
                modified = roomStay.getDateModification();
            }
        }
        otaReservation.setLastModifyDateTime(modified);
        //ResGuest
        otaReservation.setResGuests(getResGuest(reservation));
        //ResGlobalInfo
        otaReservation.setResGlobalInfo(getResGlobalInfoType(reservation));
        return new OTAHotelResNotifRQ(hotelTicker, ConstantsRateGain.CHANNEL_ID,
                ConstantsRateGain.CHANNEL_NAME, otaReservation);
    }

    private TransactionActionType getResStatus(Reservation reservation) {
        if (reservation == null) {
            return null;
        }
        if (reservation.getStatus() == null) {
            logger.info(reservation);
        }
        TransactionActionType status = TransactionActionType.MODIFY;
        //if you modify or cancel one of the RoomStay, all the reservation is modified or canceled
        Date modified = null;
        for (final RoomStay roomStay : reservation.getRoomStays()) {
            if (roomStay.getDateModification() != null) {
                modified = roomStay.getDateModification();
            }
        }
        switch (reservation.getStatus()) {
            case RESERVATION:
                status = TransactionActionType.COMMIT;
                if (modified != null) {
                    return TransactionActionType.MODIFY;
                }
                break;
            case PRE_RESERVATION:
                status = TransactionActionType.COMMIT;
                if (modified != null) {
                    return TransactionActionType.MODIFY;
                }
                break;
            case CANCEL:
                status = TransactionActionType.CANCEL;
                break;
        }
        return status;
    }

    private String getMSG(final DBConnection dbConnection, final String msg, final String hotelTicker) {
        String hotelName = null;
        HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
        try {
            hotelName = hotelConfigurationDBHandler.getHotelName();
        } catch (DBAccessException e) {
            logger.error(e);
        }
        hotelName = hotelName == null ? hotelTicker : hotelName;
        return msg + " can not be set to " + hotelTicker + " because it is not an OWN value. Need to be OWN value. " +
                "Please contact to " + hotelName + " manager to change this property on our system and then later can modify this property" +
                " by your system. ";
    }

    private String getDefaultCurrency(DBConnection dBConnection) throws DBAccessException {
        final String currency = new HotelConfigurationDBHandler(dBConnection).getDefaultCurrency();
        return currency == null || currency.equals("") ? "EUR" : currency;
    }

    private ErrorType handleException(final Exception ex, final ErrorType ret) {
        logger.error(ex);
        ret.setValue(ex.toString());
        if (ex != null)
            ex.printStackTrace();
        return ret;
    }

    private ErrorType handleException(final Exception ex) {
        logger.error(ex);
        final ErrorType ret = ErrorType.getErrorTypeUnknownCodeUndetermined();
        ret.setValue(ex.toString());
        return ret;
    }

    private String getRatePlanName(Inventory item) {
        String ratePlanName = "";
        if (item != null) {
            ratePlanName += item.getConfiguration() != null && item.getConfiguration().getName() != null
                    ? item.getConfiguration().getName()
                    : "";
            ratePlanName += item.getMealPlan() != null && item.getMealPlan().getName() != null
                    ? "".equals(ratePlanName) ? item.getMealPlan().getName() : " , " + item.getMealPlan().getName()
                    : "";
            ratePlanName += item.getCondition() != null && item.getCondition().getName() != null
                    ? "".equals(ratePlanName) ? item.getCondition().getName() : " , " + item.getCondition().getName()
                    : "";
        }
        return ratePlanName;
    }

    private RateGainRSInterface getError(final Class<? extends RateGainRSInterface> retClass,
                                         final ErrorsType errorType) {
        if (retClass.equals(HotelPropertyListGetRS.class)) {
            return new HotelPropertyListGetRS(errorType);
        } else if (retClass.equals(HotelProductListGetRS.class)) {
            return new HotelProductListGetRS(errorType);
        } else if (retClass.equals(HotelARIGetRS.class)) {
            return new HotelARIGetRS(errorType);
        } else if (retClass.equals(HotelARIUpdateRS.class)) {
            return new HotelARIUpdateRS(errorType);
        }
        return null;
    }

    private RoomStaysType.RoomStay getRoomStay(
            final RoomStay roomStay, final Reservation reservation,
            final InventoryDBHandler inventoryDBHandler, final String hotelTicker,
            final float taxPercentage, final DBConnection dBConnection)
            throws RateGainException {
        final RoomStaysType.RoomStay roomStayRateGain = new RoomStaysType.RoomStay();
        final Inventory inventory;
        final HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dBConnection);
        String hotelName = "";
        boolean useFullRatesChMg = false;
        boolean sendChildInvChMg = false;
        try {
            hotelName = hotelConfigurationDBHandler.getHotelName();
            List<String> values = new ArrayList<>();
            values.add(USE_FULL_RATES);
            values.add(SEND_CHILD_INV_TICKERS);
            //if USE_FULL_RATES is true, we send all the real values in the reservation's rates (dailyValues +extras -discounts)
            String includeRates = (String) hotelConfigurationDBHandler.getHotelProperties(values).get(USE_FULL_RATES);
            if (includeRates != null && includeRates.trim().equals("1"))
                useFullRatesChMg = true;

            //if SEND_CHILD_INV_TICKERS is true, we send the child Inventory Ticker if the Inventory reserved has
            // Shared Availability. if not we send the parent Inventory Ticker
            String sendChildInv = (String) hotelConfigurationDBHandler.getHotelProperties(values).get(SEND_CHILD_INV_TICKERS);
            if (sendChildInv != null && sendChildInv.trim().equals("1"))
                sendChildInvChMg = true;
        } catch (Exception ex) {
            logger.error(ex);
        }
        String roomTicker = roomStay.getInventoryTicker();
        try {
            inventory = inventoryDBHandler.getInventoryByTicker(roomTicker);
        } catch (DBAccessException ex) {
            logger.error(ex);
            throw new RateGainException(ex);
        }
        if (inventory.getAvailability().isSharedValue() && !sendChildInvChMg)
            roomTicker = ((SharedValue) inventory.getAvailability().getValue()).getTicker();
        //Set RoomStayRateGain.RoomTypes
        roomStayRateGain.setRoomTypes(getRoomTypeType(roomStay, inventory, roomTicker));
        //Set RoomStayRateGain.RatePlans
        final MealPlan mealPlan = inventory.getMealPlan();
        roomStayRateGain.setRatePlans(roomTicker,
                mealPlan.hasBreakfast(), mealPlan.hasLunch(), mealPlan.hasDinner());
        //Adding RoomStayRateGain.GuestCount
        for (Map.Entry<Guest, Integer> item : inventory.getConfiguration().getGuests().entrySet()) {
            roomStayRateGain.addGuestCount(
                    new GuestCountType.GuestCount(
                            item.getKey().getAgeQualifyingCode() + "",
                            item.getValue())
            );
        }
        //Set RoomStayRateGain.TimeStamp
        roomStayRateGain.setTimeSpan(roomStay.getDateCheckIn(), roomStay.getDateCheckOut());
        //Set RoomStayRateGain.Total
        roomStayRateGain.setTotal(roomStay.getTotalAmount() / taxPercentage, roomStay.getTotalAmount(), reservation.getCurrency());
        //Set RoomStayRateGain.RoomRates
        roomStayRateGain.setRoomRates(getRoomRate(roomStay, taxPercentage, reservation.getCurrency(),
                useFullRatesChMg, roomTicker));
        roomStayRateGain.addBasicPropertyInfo(hotelTicker, hotelName);
        roomStayRateGain.addComment(roomStay.getComments());
        roomStayRateGain.addComment(reservation.getComments());
        return roomStayRateGain;
    }

    private RoomStayType.RoomRates.RoomRate getRoomRate(final RoomStay roomStay, final float taxPercentage,
                                                        String currency, boolean useFullRatesChMg, final String roomTicker) {
        final RoomStayType.RoomRates.RoomRate roomRate = new RoomStayType.RoomRates.RoomRate();
        roomRate.setRatePlanCode(roomTicker);
        roomRate.setRoomTypeCode(roomTicker);
        int units = roomStay.getQuantity();
        if (units < 1) {
            units = 1;
        }
        roomRate.setNumberOfUnits(units);
        Set<DailyValue<Float>> dailyValueSet;
        //if useFullRatesChMg is true, we send all the real values in the reservation's rates (dailyvalues +extras -discounts)
        if (useFullRatesChMg) {
            Date startDateIter = roomStay.getRoomRates().getRangeStartDate();
            Date endDateIter = roomStay.getRoomRates().getRangeEndDate();
            Date dateIterator = (Date) startDateIter.clone();
            float totalServices = 0;
            //We have to add the daily price for all the extras requested
            for (ServiceRequested serviceRequested : roomStay.getServices()) {
                totalServices = serviceRequested.getTotalServiceAmount() + totalServices;
            }
            int days = DateUtil.daysBetweenDates(startDateIter, endDateIter) + 1;
            if (totalServices > 0 && days > 0)
                totalServices = (float) Math.round((totalServices / days) * 1000) / 1000;

            RangeValue<Float> fullRates = new RangeValue<Float>(RateDataValue.DEFAULT_VALUE);
            //We have subtract the discounts from the daily rate
            while (DateUtil.dateBetweenDaysRange(dateIterator, startDateIter, endDateIter)) {
                float actualRate = roomStay.getRoomRates().getFinalValueForADate(dateIterator);
                Float discount = null;
                for (DiscountApplied discountApplied : roomStay.getDiscounts()) {
                    discount = discountApplied.getDiscountPrice().getValueForADate(dateIterator);
                    //If percentage, Round 3 decimals
                    if (discountApplied.isPercentage())
                        discount = (float) Math.round(actualRate * discount * 10) / 1000;
                    if (discount != null)
                        break;
                }
                if (discount != null && discount != 0)
                    actualRate = (float) Math.round((actualRate + discount.floatValue()) * 1000) / 1000;
                fullRates.putValueForADate(dateIterator, actualRate + totalServices);
                DateUtil.incrementDays(dateIterator, 1);
            }
            dailyValueSet = fullRates.getDailySet();
        } else {
            dailyValueSet = roomStay.getRoomRatesSetDailyValues();
        }
        for (DailyValue<Float> rate : dailyValueSet) {
            final Date endDate = new Date(rate.getEndDate().getTime());
            DateUtil.incrementDays(endDate, 1);
            final float amountAfterTax = rate.getValue() * units;
            final float amountBeforeTax = amountAfterTax / taxPercentage;
            roomRate.addRate(new RateType.Rate(rate.getStartDate(),
                    endDate, amountBeforeTax, amountAfterTax,
                    currency));
        }
        return roomRate;
    }

    private float getTaxPercentage(final InventoryDBHandler inventoryDBHandler) {
        List<Tax> taxes = null;
        try {
            taxes = inventoryDBHandler.getTaxes();
        } catch (DBAccessException ex) {
            logger.error(ex);
        }
        return taxes == null || taxes.isEmpty()
                ? 1
                : 1 + taxes.get(0).getValue();

    }

    private RoomTypeType getRoomTypeType(final RoomStay roomStay, final Inventory inventory, String roomTicker) {
        final RoomTypeType roomType = new RoomTypeType();
        roomType.setRoomTypeCode(roomTicker);
        roomType.setNumberOfUnits(roomStay.getQuantity());
        roomType.setRoomDescriptionName(inventory.getAccommodationName());
        return roomType;
    }

    private ResGlobalInfoType getResGlobalInfoType(final Reservation reservation) {
        final CreditCard creditCard = reservation.getCustomer().getCreditCard();
        final String cardHolderName = creditCard.getCardHolderName();
        final String cardCode = OTAUtils.getCardOTACode(creditCard.getCardCode());
        String cardNumber;
        String expireDate;
        try {
            cardNumber = connectionBean.encryptionPHP(ConnectionBean.DECRYPT, creditCard.getCardNumberEncrypted());
        } catch (Exception ex) {
            cardNumber = creditCard.getCardNumberEncrypted();
            logger.error(ex);
        }
        try {
            expireDate = connectionBean.encryptionPHP(ConnectionBean.DECRYPT, creditCard.getExpireDateEncrypted())
                    .replaceAll("/", "");
        } catch (Exception ex) {
            expireDate = creditCard.getExpireDateEncrypted() + "";
            logger.error(ex);
        }
        final String cardType = "1";
        Date start = null;
        Date finish = null;
        for (final RoomStay roomStay : reservation.getRoomStays()) {
            final Date dateCheckIn = roomStay.getDateCheckIn();
            final Date dateCheckOut = roomStay.getDateCheckOut();
            start = start == null
                    ? dateCheckIn
                    : (start.after(dateCheckIn))
                    ? dateCheckIn
                    : start;
            finish = finish == null
                    ? dateCheckOut
                    : (finish.before(dateCheckOut))
                    ? dateCheckOut
                    : finish;
        }
        final String duration = DateUtil.daysBetweenDates(start, finish) + "";
        final float amountBeforeTax = reservation.getAmountBeforeTax();
        final float amountAfterTax = reservation.getAmountAfterTax();
        final String currency = reservation.getCurrency();
        return new ResGlobalInfoType(reservation.getReservationId(),
                cardHolderName, cardType, cardCode, cardNumber, expireDate, start, finish, duration, amountBeforeTax, amountAfterTax, currency);
    }

    private String msgHotelNotFound(final String hotelTicker) {
        return "HotelCode " + hotelTicker + " given does not match with any HotelCode.";
    }

    /**
     * Get ResGuest Property from ReservationRS.
     *
     * @param reservation
     * @return
     * @throws RateGainException
     */
    private ResGuestsType getResGuest(final Reservation reservation)
            throws RateGainException {
        final Customer customer = reservation.getCustomer();
        if (customer == null) {
            throw new RateGainException(new NullPointerException("Customer is null."));
        }
        final List<String> givenNames = new ArrayList<String>();
        final String surName;
        if (customer.getGivenName() != null && !customer.getGivenName().trim().isEmpty()) {
            givenNames.add(customer.getGivenName());
        } else {
            givenNames.add("Name  Undefined");
        }
        if (customer.getSurName() != null && !customer.getSurName().trim().isEmpty()) {
            surName = customer.getSurName();
        } else {
            surName = "SurName Undefined";
        }
//        logger.debug("Customer: " + customer);
        //TODO Cuando se tenga una tabla ISO de paises con sus codigos en la base de datos,
        // pasar el nombre del pais y no el codigo del pais duplicado.
        return new ResGuestsType("10", givenNames, surName,
                customer.getTelephone(), customer.getEmail(), customer.getAddress(),
                customer.getCountry(), customer.getCountry());
    }

    /**
     * Finds hotel name for each hotel ticker passed.
     *
     * @param jsonArrayHotelTickers Json Array of hotel tickers.
     * @return if <code>jsonArrayHotelTickers</code> is <code>null</code> then
     * returns <code>null</code>, otherwise <code>new HotelListType</code> with
     * the corresponds hotel ticker and hotel name.
     */
    private HotelPropertyListGetRS getHotelList(final JsonArray jsonArrayHotelTickers,
                                                final BigDecimal version, final String target) {
        if (jsonArrayHotelTickers == null) {
            return null;
        }
        final HotelListType hotelListType = new HotelListType();
        final Iterator<JsonElement> iterator = jsonArrayHotelTickers.iterator();
        while (iterator.hasNext()) {
            final JsonElement next = iterator.next();
            if (next != null && !next.isJsonNull()) {
                final String hotelTicker = next.getAsString();
                if (hotelTicker != null) {
                    String hotelName = hotelTicker;
                    DBConnection dBConnection = null;
                    try {
                        dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
                        HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dBConnection);
                        hotelName = hotelConfigurationDBHandler.getHotelName();
                    } catch (ExternalFileException ex) {
                        //TODO NOW REPORTAR PARA NOSOTROS QUE HUBO UN ERROR TRATANDO DE CONECTARSE A LA BASE DE DATOS DEL HOTEL hotelTicker. REPORTAR PARA NOSOTROS
                        logger.error(ex);
                    } catch (DBAccessException ex) {
                        //TODO NOW REPORTAR PARA NOSOTROS QUE HUBO UN ERROR TRATANDO DE CONECTARSE A LA BASE DE DATOS DEL HOTEL hotelTicker. REPORTAR PARA NOSOTROS
                        logger.error(ex);
                    } catch (NonexistentValueException ex) {
                        //TODO NOW REPORTAR PARA NOSOTROS QUE HUBO UN ERROR TRATANDO DE CONECTARSE A LA BASE DE DATOS DEL HOTEL hotelTicker. ??  REPORTAR PARA NOSOTROS
                        logger.error(ex);
                    } finally {
                        DAOUtil.close(dBConnection);
                    }
                    hotelListType.getHotel().add(new HotelType(hotelTicker, hotelName == null ? hotelTicker : hotelName));
                } else {
                    logger.error("hotelTicker is null");
                }
            } else {
                //TODO NOW RECIBI ALGO NULO ??
                logger.error("Json is Null");
            }
        }
        return new HotelPropertyListGetRS(hotelListType, version, target);
    }
}
