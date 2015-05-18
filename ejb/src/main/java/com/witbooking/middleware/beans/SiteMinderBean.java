package com.witbooking.middleware.beans;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.witbooking.middleware.beans.handler.SecuritySOAPHandler;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.DailyValuesDBHandler;
import com.witbooking.middleware.db.handlers.HotelConfigurationDBHandler;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.exceptions.integration.rategain.RateGainException;
import com.witbooking.middleware.integration.ChannelConnectionType;
import com.witbooking.middleware.integration.ChannelQueueStatus;
import com.witbooking.middleware.integration.ChannelTicker;
import com.witbooking.middleware.integration.EntryQueueItem;
import com.witbooking.middleware.integration.ota.model.*;
import com.witbooking.middleware.integration.siteminder.ConstantsSiteMinder;
import com.witbooking.middleware.model.Inventory;
import com.witbooking.middleware.model.Reservation;
import com.witbooking.middleware.model.RoomStay;
import com.witbooking.middleware.model.values.EnumDataValueType;
import com.witbooking.middleware.model.values.HashRangeValue;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.XMLUtils;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.*;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.handler.MessageContext;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * SiteMinderBean.java
 * User: jose
 * Date: 9/19/13
 * Time: 2:27 PM
 */
@Stateless
@WebService(
        portName = "SiteMinderPort",
        serviceName = "SiteMinderService",
        targetNamespace = "http://www.opentravel.org/OTA/2003/05"
)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@HandlerChain(file = "handlers.xml")
public class SiteMinderBean implements SiteMinderBeanLocal, SiteMinderBeanRemote {

    @Resource
    private javax.xml.ws.WebServiceContext webServiceContext;
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SiteMinderBean.class);

    public SiteMinderBean() {
    }

    @EJB
    private RateGainBeanLocal rateGainBeanLocal;
    @EJB
    private ConnectionBeanLocal connectionBean;
    @EJB
    private IntegrationBeanLocal integrationBeanLocal;


    private OTAHotelAvailNotifRS validateOTAHotelAvailNotifRS(OTAHotelAvailNotifRQ otaHotelAvailNotifRQ) {
        final BigDecimal version = otaHotelAvailNotifRQ.getVersion();
        if (otaHotelAvailNotifRQ == null) {
            return new OTAHotelAvailNotifRS(
                    new ErrorsType(new ErrorType(OTAConstants.Error.Type.PROCESSING_EXCEPTION,
                            OTAConstants.Error.Code.UNABLE_TO_PROCESS,
                            "OTA_HotelAvailNotifRQ is null.")),
                    null, BigDecimal.valueOf(1)
            );

        } else {
            final OTAHotelAvailNotifRQ.AvailStatusMessages availStatusMessages = otaHotelAvailNotifRQ.getAvailStatusMessages();
            final String echoToken = otaHotelAvailNotifRQ.getEchoToken();
            if (echoToken == null || echoToken.equals("")) {
                return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                        OTAConstants.Error.Code.INVALID_VALUE, "EchoToken is null")), echoToken, version);
            }
            if (availStatusMessages == null) {
                //Missing List of messages. Is that an error?
                return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                        OTAConstants.Error.Code.INVALID_VALUE, "request without availStatusMessages")), echoToken, version);
            } else if (availStatusMessages.getHotelCode() == null || availStatusMessages.getHotelCode().equals("")) {
                //Missing HotelCode.
                return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                        OTAConstants.Error.Code.INVALID_VALUE, "Missing Hotel Code.")), echoToken, version);
            }
            final List<AvailStatusMessageType> availStatusMessage = availStatusMessages.getAvailStatusMessage();
            if (availStatusMessage == null || availStatusMessage.isEmpty()) {
                return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                        OTAConstants.Error.Code.INVALID_VALUE, "request without availStatusMessage")), echoToken, version);
            }
            for (AvailStatusMessageType availStatusMessageType : availStatusMessage) {
                final StatusApplicationControlType statusApplicationControl = availStatusMessageType.getStatusApplicationControl();
                if (statusApplicationControl == null) {
                    return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                            OTAConstants.Error.Code.INVALID_VALUE, "availStatusMessage with StatusAplicationControl")), echoToken, version);
                }
                final Date start1 = statusApplicationControl.getStart();
                final Date end = statusApplicationControl.getEnd();
                final String invTypeCode = statusApplicationControl.getInvTypeCode();
                if (start1 == null) {
                    return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                            OTAConstants.Error.Code.INVALID_VALUE, "StatusAplicationControl without start statement.")), echoToken, version);
                } else if (end == null) {
                    return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                            OTAConstants.Error.Code.INVALID_VALUE, "StatusAplicationControl without end statement.")), echoToken, version);
                } else if (start1.after(end)) {
                    return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                            OTAConstants.Error.Code.INVALID_VALUE, "StatusAplicationControl start day after before day.")), echoToken, version);
                }
                if (invTypeCode == null || invTypeCode.equals("")) {
                    return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                            OTAConstants.Error.Code.INVALID_VALUE, "StatusAplicationControl without invTypeCode")), echoToken, version);
                }
                if (availStatusMessageType.getRestrictionStatus() != null) {
                    final String status = availStatusMessageType.getRestrictionStatus().getStatus();
                    if (availStatusMessageType.getRestrictionStatus().getRestriction() != null) {
                        return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                                OTAConstants.Error.Code.UNABLE_TO_PROCESS, "Restriction Arrival or Departure not supported.")), echoToken, version);
                    }
                    if (status == null || (!status.equals(AvailStatusMessageType.RestrictionStatus.OPEN)
                            && !status.equals(AvailStatusMessageType.RestrictionStatus.CLOSE))) {
                        return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                                OTAConstants.Error.Code.INVALID_VALUE, "Malformed RestrictionStatus")), echoToken, version);
                    }
                    final String restriction = availStatusMessageType.getRestrictionStatus().getRestriction();
                    if (restriction != null && !restriction.equals(AvailStatusMessageType.RestrictionStatus.ARRIVAL)
                            && !restriction.equals(AvailStatusMessageType.RestrictionStatus.DEPATURE)) {
                        return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                                OTAConstants.Error.Code.INVALID_VALUE, "Malformed RestrictionStatus")), echoToken, version);
                    }
                }
                if (availStatusMessageType.getLengthsOfStay() != null &&
                        availStatusMessageType.getLengthsOfStay().getLengthOfStay() != null &&
                        !availStatusMessageType.getLengthsOfStay().getLengthOfStay().isEmpty()) {
                    for (final LengthsOfStayType.LengthOfStay itemLength : availStatusMessageType.getLengthsOfStay().getLengthOfStay()) {
                        final String minMaxMessageType = itemLength.getMinMaxMessageType();

                        if (minMaxMessageType != null && !minMaxMessageType.equals(LengthsOfStayType.LengthOfStay.SET_STAY_MAX)
                                && !minMaxMessageType.equals(LengthsOfStayType.LengthOfStay.SET_STAY_MIN)) {
                            return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                                    OTAConstants.Error.Code.INVALID_VALUE, "Malformed LengthsOfStay statement.")), echoToken, version);
                        }
                        if (itemLength.getTime() == null) {
                            return new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                                    OTAConstants.Error.Code.INVALID_VALUE, "Malformed LengthsOfStay statement, missing Time value.")), echoToken, version);
                        }
                    }
                }
            }
        }
        return null;
    }

    @WebMethod(operationName = "OTA_HotelAvailNotifRQ")
    @WebResult(name = "OTA_HotelAvailNotifRS")
    public OTAHotelAvailNotifRS OTAHotelAvailNotifRQ(
            @WebParam(name = "OTA_HotelAvailNotifRQ", mode = WebParam.Mode.IN)
            OTAHotelAvailNotifRQ otaHotelAvailNotifRQ) {
        logger.debug("OTAHotelAvailNotifRQ");
        ChannelQueueStatus requestStatus = ChannelQueueStatus.FAIL;
        OTAHotelAvailNotifRS otaHotelAvailNotifRS = null;
        String hotelTicker = null;
        Map<String, EntryQueueItem> queueItemsMap = new HashMap<String, EntryQueueItem>();
        try {
            //Validating request
            otaHotelAvailNotifRS = validateOTAHotelAvailNotifRS(otaHotelAvailNotifRQ);
            if (otaHotelAvailNotifRS != null) {
                return otaHotelAvailNotifRS;
            }
            final BigDecimal version = otaHotelAvailNotifRQ.getVersion();
            //Request validated
            final OTAHotelAvailNotifRQ.AvailStatusMessages availStatusMessages = otaHotelAvailNotifRQ.getAvailStatusMessages();
            final String echoToken = otaHotelAvailNotifRQ.getEchoToken();
            hotelTicker = availStatusMessages.getHotelCode();
            //validating the username and password
            final ErrorsType authErrors = validLoginBySoapHeader(hotelTicker);
            if (authErrors != null) {
                otaHotelAvailNotifRS = new OTAHotelAvailNotifRS(authErrors, echoToken, version);
                return otaHotelAvailNotifRS;
            }
            DBConnection dBConnection = null;
            final OTAHotelAvailNotifRS otaHotelAvailNotifRSInternalError = new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType
                    (OTAConstants.Error.Type.PROCESSING_EXCEPTION, OTAConstants.Error.Code.UNABLE_TO_PROCESS, "Internal Error. Plase try later.")),
                    otaHotelAvailNotifRQ.getEchoToken(), version
            );
            try {
                try {
                    dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
                    if (dBConnection == null) {
                        otaHotelAvailNotifRS = new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType
                                (OTAConstants.Error.Type.PROCESSING_EXCEPTION, OTAConstants.Error.Code.NO_HOTEL_MATCH_FOUND, "hotel_code: " + hotelTicker + " not match with any of ours hotels.")),
                                otaHotelAvailNotifRQ.getEchoToken(), version
                        );
                        return otaHotelAvailNotifRS;
                    }
                } catch (DBAccessException | ExternalFileException e) {
                    logger.error(e);
                    DAOUtil.close(dBConnection);
                    otaHotelAvailNotifRS = otaHotelAvailNotifRSInternalError;
                    return otaHotelAvailNotifRS;
                } catch (NonexistentValueException e) {
                    logger.error(e);
                    DAOUtil.close(dBConnection);
                    otaHotelAvailNotifRS = new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.PROCESSING_EXCEPTION,
                            OTAConstants.Error.Code.INVALID_PROPERTY_CODE, hotelTicker)),
                            otaHotelAvailNotifRQ.getEchoToken(), version
                    );
                    return otaHotelAvailNotifRS;
                }
                final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dBConnection, "eng");
                //Validating the Inventory Ticker
                for (AvailStatusMessageType item : availStatusMessages.getAvailStatusMessage()) {
                    final String invTypeCode = item.getStatusApplicationControl().getInvTypeCode();
                    final Inventory inventory = inventoryDBHandler.getInventoryByTicker(invTypeCode);
                    if (inventory == null) {
                        //Inventory Ticker not exist
                        otaHotelAvailNotifRS = new OTAHotelAvailNotifRS(new ErrorsType(
                                new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION, OTAConstants.Error.Code.INVALID_VALUE,
                                        "The InvTypeCode '" + invTypeCode + "' doesn't exist.")
                        ), echoToken, version);
                        logger.error("The InvTypeCode '" + invTypeCode + "' doesn't exist for the hotel '"
                                + hotelTicker + "'");
                        return otaHotelAvailNotifRS;
                    }
                }
                for (AvailStatusMessageType item : availStatusMessages.getAvailStatusMessage()) {
                    final StatusApplicationControlType statusApplicationControl = item.getStatusApplicationControl();
                    final Date start = statusApplicationControl.getStart();
                    final Date end = statusApplicationControl.getEnd();
                    final String invTypeCode = statusApplicationControl.getInvTypeCode();
                    //Adding the EntryQueueItems, to the map
                    EntryQueueItem queueItem = queueItemsMap.get(invTypeCode);
                    if (queueItem == null) {
                        queueItemsMap.put(invTypeCode, new EntryQueueItem(invTypeCode, start, end));
                    } else {
                        try {
                            if (queueItem.getStart().after(start)) {
                                queueItem.setStart(start);
                            }
                            if (queueItem.getEnd().before(end)) {
                                queueItem.setEnd(end);
                            }
                        } catch (Exception e) {
                            logger.error(e);
                            queueItemsMap.put(invTypeCode, new EntryQueueItem(invTypeCode, start, end));
                        }
                    }
                    //final String ratePlanCode = statusApplicationControl.getRatePlanCode();
                    final DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, start, end);
                    final Inventory inventory = inventoryDBHandler.getInventoryByTicker(invTypeCode);
                    dailyValuesDBHandler.getInventoryValuesBetweenDates();
                    final Integer availability = item.getBookingLimit();
                    //Getting minStay and maxStay
                    Integer minStay = null;
                    Integer maxStay = null;
                    if (item.getLengthsOfStay() != null && item.getLengthsOfStay().getLengthOfStay() != null && !item.getLengthsOfStay().getLengthOfStay().isEmpty()) {
                        for (final LengthsOfStayType.LengthOfStay itemLength : item.getLengthsOfStay().getLengthOfStay()) {
                            final Integer time = itemLength.getTime();
                            if (itemLength.getMinMaxMessageType().equals(LengthsOfStayType.LengthOfStay.SET_STAY_MIN)) {
                                minStay = time;
                            } else {
                                maxStay = time;
                            }
                        }
                    }
                    //Getting Restriction Status (Lock , minNotice, maxNotice)
                    Boolean lock = null;
                    //TODO cuando se implemente el openToArrival o el openToDeparture. En la validacion de arriba si se recibe una de estas devuelve error de no soportado.
                    Boolean arrival = null;
                    Boolean departure = null;
                    if (item.getRestrictionStatus() != null) {
                        final AvailStatusMessageType.RestrictionStatus restrictionStatus = item.getRestrictionStatus();
                        final String restriction = restrictionStatus.getRestriction();
                        final String status = restrictionStatus.getStatus();

                        if (restriction == null) {
                            lock = status.equals(AvailStatusMessageType.RestrictionStatus.CLOSE);

                        } else {
                            if (restriction.equals(AvailStatusMessageType.RestrictionStatus.ARRIVAL)) {
                                arrival = status.equals(AvailStatusMessageType.RestrictionStatus.OPEN);
                            } else {
                                departure = status.equals(AvailStatusMessageType.RestrictionStatus.OPEN);
                            }
                        }
                    }
                    if (availability != null && !EnumDataValueType.OWN.equals(inventory.getAvailability().getValueType()) &&
                            !EnumDataValueType.SHARED.equals(inventory.getAvailability().getValueType())) {
                        otaHotelAvailNotifRS = new OTAHotelAvailNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                                OTAConstants.Error.Code.INVALID_VALUE, getMSG("OTA_HotelAvailNotifRQ/AvailStatusMessages/AvailStatusMessage/BookingLimit",
                                invTypeCode)
                        )), echoToken, version);
                        logger.error("Invalid availability type in '" + invTypeCode + "' for the hotel '"
                                + hotelTicker + "' type: " + inventory.getAvailability().getValueType());
                        return otaHotelAvailNotifRS;
                    }
                    if (minStay != null && !EnumDataValueType.OWN.equals(inventory.getMinStay().getValueType()) &&
                            !EnumDataValueType.SHARED.equals(inventory.getMinStay().getValueType())) {
                        //If I can't modify this value, I will not try. pass null.
                        minStay = null;
                    }
                    if (maxStay != null && !EnumDataValueType.OWN.equals(inventory.getMaxStay().getValueType()) &&
                            !EnumDataValueType.SHARED.equals(inventory.getMaxStay().getValueType())) {
                        //If I can't modify this value, I will not try. pass null.
                        maxStay = null;
                    }
                    if (lock != null && !EnumDataValueType.OWN.equals(inventory.getLock().getValueType()) &&
                            !EnumDataValueType.SHARED.equals(inventory.getLock().getValueType())) {
                        //If I can't modify this value, I will not try. pass null.
                        lock = null;
                    }
                    final RangeValue<Integer> availabilityRangeValue = availability == null ? null : dailyValuesDBHandler.getAvailabilityByTicker(invTypeCode);
                    final RangeValue<Integer> minStayRangeValue = minStay == null ? null : dailyValuesDBHandler.getMinStayByTicker(invTypeCode);
                    final RangeValue<Integer> maxStayRangeValue = maxStay == null ? null : dailyValuesDBHandler.getMaxStayByTicker(invTypeCode);
                    final RangeValue<Boolean> locksRangeValue = lock == null ? null : dailyValuesDBHandler.getLockByTicker(invTypeCode);

                    //Updating RageValues
                    Date dateIterator = new Date(start.getTime());
                    while (!dateIterator.after(end)) {
                        if (availabilityRangeValue != null) {
                            availabilityRangeValue.putValueForADate(dateIterator, availability);
                        }
                        if (minStayRangeValue != null) {
                            minStayRangeValue.putValueForADate(dateIterator, minStay);
                        }
                        if (maxStayRangeValue != null) {
                            maxStayRangeValue.putValueForADate(dateIterator, maxStay);
                        }
                        if (locksRangeValue != null) {
                            //logger.debug("Poniendo a lock: " + lock + " para el dia: " + dateIterator);
                            locksRangeValue.putValueForADate(dateIterator, lock);
                        }
                        DateUtil.incrementDays(dateIterator, 1);
                    }
                    int rows = 0;
                    if (availabilityRangeValue != null) {
                        rows += dailyValuesDBHandler.updateAvailabilityByTicker(invTypeCode, availabilityRangeValue);
                    }
                    if (minStayRangeValue != null) {
                        rows = rows + dailyValuesDBHandler.updateMinStayByTicker(invTypeCode, minStayRangeValue);
                    }
                    if (maxStayRangeValue != null) {
                        rows = rows + dailyValuesDBHandler.updateMaxStayByTicker(invTypeCode, maxStayRangeValue);
                    }
                    if (locksRangeValue != null) {
                        rows = rows + dailyValuesDBHandler.updateLocksByTicker(invTypeCode, locksRangeValue);
                    }
                    logger.debug("Rows updated: " + rows);
                }
            } catch (DBAccessException | NonexistentValueException e) {
                logger.error(e + " hotelTicker: " + hotelTicker);
                otaHotelAvailNotifRS = otaHotelAvailNotifRSInternalError;
                return otaHotelAvailNotifRS;
            } finally {
                DAOUtil.close(dBConnection);
            }
            otaHotelAvailNotifRS = new OTAHotelAvailNotifRS(echoToken, version);
            requestStatus = ChannelQueueStatus.SUCCESS;
            return otaHotelAvailNotifRS;
        } finally {
            try {
                final Set<EntryQueueItem> items = new HashSet<>(queueItemsMap.values());
                if (requestStatus == ChannelQueueStatus.FAIL) {
                    try {
//                        logger.error("Fail Status: "+ XMLUtils.parseToString(otaHotelAvailNotifRQ));
                        logger.error("Fail Status: " + XMLUtils.parseToString(otaHotelAvailNotifRS));
                    } catch (Exception e) {
//                        logger.error("Fail Status no XML: " + otaHotelAvailNotifRQ);
                        logger.error("Fail Status no XML: " + otaHotelAvailNotifRS);
                    }
                }
                integrationBeanLocal.storeSingleConnection(hotelTicker, ChannelTicker.SITEMINDER,
                        ChannelConnectionType.OFFER_UPDATE_AVAILABILITY_ARI, requestStatus, items,
                        otaHotelAvailNotifRQ, otaHotelAvailNotifRS);
            } catch (IntegrationException e) {
                logger.error(e + " hotelTicker: " + hotelTicker);
            }
        }
    }

    private OTAHotelRateAmountNotifRS validateOTAHotelRateAmountNotifRQ(OTAHotelRateAmountNotifRQ otaHotelRateAmountNotifRQ) {
        if (otaHotelRateAmountNotifRQ == null) {
            return new OTAHotelRateAmountNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                    OTAConstants.Error.Code.INVALID_VALUE, "Request is null.")), null, BigDecimal.valueOf(1));
        }
        final String echoToken = otaHotelRateAmountNotifRQ.getEchoToken();
        final BigDecimal version = otaHotelRateAmountNotifRQ.getVersion();
        if (otaHotelRateAmountNotifRQ.getEchoToken() == null) {
            return new OTAHotelRateAmountNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                    OTAConstants.Error.Code.INVALID_VALUE, "EchoToken is null")), echoToken, version);

        } else if (otaHotelRateAmountNotifRQ.getRateAmountMessages() == null
                || otaHotelRateAmountNotifRQ.getRateAmountMessages().getHotelCode() == null
                || otaHotelRateAmountNotifRQ.getRateAmountMessages().getRateAmountMessage() == null) {
            return new OTAHotelRateAmountNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                    OTAConstants.Error.Code.INVALID_VALUE, "Malformed RateAmountMessages statement.")), echoToken, version);
        }
        for (final RateAmountMessageType rateAmountMessageType : otaHotelRateAmountNotifRQ.getRateAmountMessages().getRateAmountMessage()) {
            if (rateAmountMessageType == null) {
                return new OTAHotelRateAmountNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                        OTAConstants.Error.Code.INVALID_VALUE, "Malformed RateAmountMessage statement.")), echoToken, version);
            } else {
                final StatusApplicationControlType statusApplicationControl = rateAmountMessageType.getStatusApplicationControl();
                if (statusApplicationControl == null) {
                    return new OTAHotelRateAmountNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                            OTAConstants.Error.Code.INVALID_VALUE, "Malformed StatusApplicationControl statement.")), echoToken, version);
                }
                if (statusApplicationControl.getStart() == null
                        || statusApplicationControl.getEnd() == null
                        || statusApplicationControl.getEnd().before(statusApplicationControl.getStart())
                        || statusApplicationControl.getInvTypeCode() == null) {
                    final String value = "Malformed StatusApplicationControl statement.";
                    return new OTAHotelRateAmountNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                            OTAConstants.Error.Code.INVALID_VALUE, value)), echoToken, version);
                }
                if (rateAmountMessageType.getRates() == null
                        || rateAmountMessageType.getRates().getRate() == null
                        || rateAmountMessageType.getRates().getRate().isEmpty()
                        || rateAmountMessageType.getRates().getRate().get(0) == null
                        || rateAmountMessageType.getRates().getRate().get(0).getBaseByGuestAmts() == null
                        || rateAmountMessageType.getRates().getRate().get(0).getBaseByGuestAmts().getBaseByGuestAmt() == null
                        || rateAmountMessageType.getRates().getRate().get(0).getBaseByGuestAmts().getBaseByGuestAmt().isEmpty()
                        || rateAmountMessageType.getRates().getRate().get(0).getBaseByGuestAmts().getBaseByGuestAmt().get(0) == null
                        ) {
                    return new OTAHotelRateAmountNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                            OTAConstants.Error.Code.INVALID_VALUE, "Malformed RateAmountMessage statement.")), echoToken, version);
                }
            }
        }
        return null;
    }

    @WebMethod(operationName = "OTA_HotelRateAmountNotifRQ")
    @WebResult(name = "OTA_HotelRateAmountNotifRS")
    public OTAHotelRateAmountNotifRS OTAHotelRateAmountNotifRQ(
            @WebParam(name = "OTA_HotelRateAmountNotifRQ", mode = WebParam.Mode.IN)
            OTAHotelRateAmountNotifRQ otaHotelRateAmountNotifRQ) {
        logger.debug("OTAHotelRateAmountNotifRQ");
        OTAHotelRateAmountNotifRS otaHotelRateAmountNotifRS = null;
        ChannelQueueStatus requestStatus = ChannelQueueStatus.FAIL;
        String hotelTicker = null;
        Map<String, EntryQueueItem> queueItemsMap = new HashMap<>();
        try {
            otaHotelRateAmountNotifRS = validateOTAHotelRateAmountNotifRQ(otaHotelRateAmountNotifRQ);
            if (otaHotelRateAmountNotifRS != null) {
                return otaHotelRateAmountNotifRS;
            }
            //Request Validated.
            final String echoToken = otaHotelRateAmountNotifRQ.getEchoToken();
            final BigDecimal version = otaHotelRateAmountNotifRQ.getVersion();
            final OTAHotelRateAmountNotifRQ.RateAmountMessages rateAmountMessages = otaHotelRateAmountNotifRQ.getRateAmountMessages();
            hotelTicker = rateAmountMessages.getHotelCode();
            DBConnection dBConnection = null;
            //validating the username and password
            final ErrorsType authErrors = validLoginBySoapHeader(hotelTicker);
            if (authErrors != null) {
                otaHotelRateAmountNotifRS = new OTAHotelRateAmountNotifRS(authErrors, echoToken, version);
                return otaHotelRateAmountNotifRS;
            }
            final OTAHotelRateAmountNotifRS otaHotelRateAmountNotifRSInternalError =
                    new OTAHotelRateAmountNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.PROCESSING_EXCEPTION,
                            OTAConstants.Error.Code.UNABLE_TO_PROCESS, "Internal Error. Plase try later.")),
                            otaHotelRateAmountNotifRQ.getEchoToken(), version);
            try {
                dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            } catch (DBAccessException | ExternalFileException e) {
                logger.error(e);
                DAOUtil.close(dBConnection);
                otaHotelRateAmountNotifRS = otaHotelRateAmountNotifRSInternalError;
                return otaHotelRateAmountNotifRS;
            } catch (NonexistentValueException e) {
                logger.error(e);
                DAOUtil.close(dBConnection);
                otaHotelRateAmountNotifRS = new OTAHotelRateAmountNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.PROCESSING_EXCEPTION,
                        OTAConstants.Error.Code.NO_HOTEL_MATCH_FOUND, "hotel_code: " + hotelTicker + " not match with any of ours hotels.")),
                        otaHotelRateAmountNotifRQ.getEchoToken(), version);
                return otaHotelRateAmountNotifRS;
            }
            try {
                final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dBConnection, "eng");
                //Validating the Inventory Ticker
                for (RateAmountMessageType item : otaHotelRateAmountNotifRQ.getRateAmountMessages().getRateAmountMessage()) {
                    final String invTypeCode = item.getStatusApplicationControl().getInvTypeCode();
                    final Inventory inventory = inventoryDBHandler.getInventoryByTicker(invTypeCode);
                    if (inventory == null) {
                        //Inventory Ticker not exist
                        otaHotelRateAmountNotifRS = new OTAHotelRateAmountNotifRS(new ErrorsType(
                                new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                                        OTAConstants.Error.Code.INVALID_VALUE,
                                        "The InvTypeCode '" + invTypeCode + "' doesn't exist.")), echoToken, version);
                        logger.error("The InvTypeCode '" + invTypeCode + "' doesn't exist for the hotel '" + hotelTicker + "'");
                        return otaHotelRateAmountNotifRS;
                    }
                }
                for (final RateAmountMessageType rateAmountMessageType : otaHotelRateAmountNotifRQ.getRateAmountMessages().getRateAmountMessage()) {
                    final StatusApplicationControlType statusApplicationControl = rateAmountMessageType.getStatusApplicationControl();
                    final Date start = statusApplicationControl.getStart();
                    final Date end = statusApplicationControl.getEnd();
                    final String invTypeCode = statusApplicationControl.getInvTypeCode();
                    //Adding the EntryQueueItems, to the map
                    EntryQueueItem queueItem = queueItemsMap.get(invTypeCode);
                    if (queueItem == null) {
                        queueItemsMap.put(invTypeCode, new EntryQueueItem(invTypeCode, start, end));
                    } else {
                        try {
                            if (queueItem.getStart().after(start)) {
                                queueItem.setStart(start);
                            }
                            if (queueItem.getEnd().before(end)) {
                                queueItem.setEnd(end);
                            }
                        } catch (Exception e) {
                            logger.error(e);
                            queueItemsMap.put(invTypeCode, new EntryQueueItem(invTypeCode, start, end));
                        }
                    }
                    final Inventory inventory = inventoryDBHandler.getInventoryByTicker(invTypeCode);

                    Float rate = null;
                    String currencyCode = null;
                    final RateAmountMessageType.Rates.Rate requestRate = rateAmountMessageType.getRates().getRate().get(0);
                    if (requestRate != null) {
                        final List<RateUploadType.BaseByGuestAmts.BaseByGuestAmt> baseByGuestAmt = requestRate.getBaseByGuestAmts().getBaseByGuestAmt();
                        if (baseByGuestAmt != null && !baseByGuestAmt.isEmpty()) {
                            rate = baseByGuestAmt.get(0).getAmountAfterTax();
                            currencyCode = baseByGuestAmt.get(0).getCurrencyCode();
                        }
                    }
                    if (rate != null) {
                        if (currencyCode != null) {
                            final HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dBConnection);
                            final String defaultCurrency = hotelConfigurationDBHandler.getDefaultCurrency();
                            if (defaultCurrency != null && !defaultCurrency.equals(currencyCode)) {
                                otaHotelRateAmountNotifRS = new OTAHotelRateAmountNotifRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.NO_IMPLEMENTATION,
                                        OTAConstants.Error.Code.INVALID_VALUE, getMSG("CurrencyCode must be " + defaultCurrency,
                                        hotelTicker)
                                )), echoToken, version);
                                return otaHotelRateAmountNotifRS;
                            }
                        }
                        if (EnumDataValueType.OWN.equals(inventory.getRate().getValueType())) {
                            final DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, start, end);
                            final RangeValue<Float> ratesRangeValue = dailyValuesDBHandler.getRatesByTicker(invTypeCode);
                            //Updating RageValues
                            Date dateIterator = new Date(start.getTime());
                            while (!dateIterator.after(end)) {
                                ratesRangeValue.putValueForADate(dateIterator, rate);
                                DateUtil.incrementDays(dateIterator, 1);
                            }
                            int rows = 0;
                            rows = rows + dailyValuesDBHandler.updateRatesByTicker(invTypeCode, ratesRangeValue);
                            logger.debug("Rows updated: " + rows);
                        }
                    }
                }
            } catch (DBAccessException e) {
                logger.error(e);
                otaHotelRateAmountNotifRS = otaHotelRateAmountNotifRSInternalError;
                return otaHotelRateAmountNotifRS;
            } catch (NonexistentValueException e) {
                logger.error(e);
                otaHotelRateAmountNotifRS = otaHotelRateAmountNotifRSInternalError;
                return otaHotelRateAmountNotifRS;
            } finally {
                DAOUtil.close(dBConnection);
            }
            otaHotelRateAmountNotifRS = new OTAHotelRateAmountNotifRS(echoToken, version);
            requestStatus = ChannelQueueStatus.SUCCESS;
            return otaHotelRateAmountNotifRS;
        } finally {
            try {
                if (requestStatus == ChannelQueueStatus.FAIL) {
                    try {
//                        logger.error("Fail Status: "+ XMLUtils.parseToString(otaHotelRateAmountNotifRQ));
                        logger.error("Fail Status: " + XMLUtils.parseToString(otaHotelRateAmountNotifRS));
                    } catch (Exception e) {
//                        logger.error("Fail Status no XML: " + otaHotelRateAmountNotifRQ);
                        logger.error("Fail Status no XML: " + otaHotelRateAmountNotifRS);
                    }
                }
                final Set<EntryQueueItem> items = new HashSet<EntryQueueItem>(queueItemsMap.values());
                integrationBeanLocal.storeSingleConnection(hotelTicker,
                        ChannelTicker.SITEMINDER, ChannelConnectionType.OFFER_UPDATE_AMOUNT_ARI, requestStatus, items,
                        otaHotelRateAmountNotifRQ, otaHotelRateAmountNotifRS);
            } catch (IntegrationException e) {
                logger.error(e);
            }
        }
    }


    @WebMethod(exclude = true)
    public OTAHotelResNotifRQ getOTAHotelResNotifRQ(final String reservationId,
                                                    final String hotelTicker) throws RateGainException {
        logger.debug("hotelTicker: " + hotelTicker);
        logger.debug("reservationId: " + reservationId);
        //TODO: Sacar el OTAHotelResNotifRQ de una clase generica, no del RateGainBean!
        final OTAHotelResNotifRQ otaHotelResNotifRQ = rateGainBeanLocal.getOTAHotelResNotifRQ(reservationId, hotelTicker);
        //TODO:introducir todos estos valores luego de obtener el OTAHotelResNotifRQ de la clase generica
        try {
            otaHotelResNotifRQ.setEchoToken(
                    ConstantsSiteMinder.REQUESTOR_CHANNEL_ID + "_" +
                            otaHotelResNotifRQ.getTimeStamp() + "_" +
                            reservationId
            );
            SourceType sourceType = otaHotelResNotifRQ.getPOS().getSource().get(0);
            sourceType.getRequestorID().setType(ConstantsSiteMinder.TYPE_RESERVATION);
            sourceType.getRequestorID().setID(ConstantsSiteMinder.REQUESTOR_CHANNEL_ID);
            sourceType.getBookingChannel().getCompanyName().setCode(ConstantsSiteMinder.REQUESTOR_CHANNEL_ID);
        } catch (Exception e) {
        }
        //
        for (final HotelReservationsType.HotelReservation hotelReservation : otaHotelResNotifRQ.getHotelReservations().getHotelReservation()) {
            if (hotelReservation != null) {
                //Added the hotelTicker to the reservation, to made it UNIQUE
                hotelReservation.generateUniqueID(reservationId, hotelTicker);
                hotelReservation.getResGlobalInfo().setHotelReservationIDs(null);
                //Checking RoomStays.RoomStay.RoomRates is 1. If its more than 1, should send multiple RoomStay elements.
                if (hotelReservation.getRoomStays() != null && hotelReservation.getRoomStays().getRoomStay() != null) {
                    Map<Integer, RoomStaysType.RoomStay> vals = new HashMap<Integer, RoomStaysType.RoomStay>();
                    for (RoomStaysType.RoomStay roomStay : hotelReservation.getRoomStays().getRoomStay()) {
                        if (roomStay.getRoomTypes() != null && roomStay.getRoomTypes().getRoomType() != null) {
                            for (final RoomTypeType roomTypeType : roomStay.getRoomTypes().getRoomType()) {
                                Integer numberOfUnits = roomTypeType.getNumberOfUnits();
                                if (numberOfUnits != null && numberOfUnits > 1) {
                                    roomStay.getTotal().setAmountAfterTax(roomStay.getTotal().getAmountAfterTax() / numberOfUnits);
                                    roomStay.getTotal().setAmountBeforeTax(roomStay.getTotal().getAmountBeforeTax() / numberOfUnits);
                                    if (roomStay.getTotal().getTaxes() != null &&
                                            roomStay.getTotal().getTaxes().getTax() != null &&
                                            roomStay.getTotal().getTaxes().getTax().get(0) != null)
                                        roomStay.getTotal().getTaxes().getTax().get(0).setAmount(
                                                roomStay.getTotal().getTaxes().getTax().get(0).getAmount() / numberOfUnits);
                                    roomTypeType.setNumberOfUnits(1);
                                }
                            }
                        }
                        if (roomStay.getRoomRates() != null && roomStay.getRoomRates().getRoomRate() != null) {
                            for (RoomStayType.RoomRates.RoomRate roomRate : roomStay.getRoomRates().getRoomRate()) {
                                final Integer numberOfUnits = roomRate.getNumberOfUnits();
                                if (numberOfUnits != null && numberOfUnits > 1) {
                                    roomRate.setNumberOfUnits(1);
                                    vals.put(numberOfUnits, roomStay);
                                }
                                if (roomRate.getRates() != null && roomRate.getRates().getRate() != null) {
                                    for (final RateType.Rate rate : roomRate.getRates().getRate()) {
                                        rate.setRateTimeUnit(TimeUnitType.DAY);
                                        rate.setUnitMultiplier(BigInteger.valueOf(1));
                                        //In the SiteMinder OTA_HotelResNotifRQ, the total room amount should be contained in
                                        // the base amount node, instead of the nightly rate as RateGain.
                                        int dayMultiplier = DateUtil.daysBetweenDates(rate.getEffectiveDate(), rate.getExpireDate());
                                        rate.getBase().setAmountAfterTax(rate.getBase().getAmountAfterTax() *
                                                dayMultiplier / numberOfUnits);
                                        rate.getBase().setAmountBeforeTax(rate.getBase().getAmountBeforeTax() *
                                                dayMultiplier / numberOfUnits);
                                        if (rate.getBase().getTaxes() != null && rate.getBase().getTaxes().getTax() != null &&
                                                rate.getBase().getTaxes().getTax().get(0) != null)
                                            rate.getBase().getTaxes().getTax().get(0).setAmount(
                                                    rate.getBase().getTaxes().getTax().get(0).getAmount() * dayMultiplier / numberOfUnits);
                                    }
                                }
                            }
                        }
                    }
                    for (Map.Entry<Integer, RoomStaysType.RoomStay> elem : vals.entrySet()) {
                        if (elem.getKey() != null && elem.getKey() > 1) {
                            for (int i = 1; i < elem.getKey(); i++) {
                                hotelReservation.getRoomStays().getRoomStay().add(elem.getValue());
                            }
                        }
                    }
                }
                if (hotelReservation.getResGlobalInfo() != null && hotelReservation.getResGlobalInfo().getHotelReservationIDs() != null &&
                        hotelReservation.getResGlobalInfo().getHotelReservationIDs().getHotelReservationID() != null) {
                    for (final HotelReservationIDsType.HotelReservationID hotelReservationID : hotelReservation.getResGlobalInfo().getHotelReservationIDs().getHotelReservationID()) {
                        hotelReservationID.setResIDType("24");
                    }
                }
            }
        }
        return otaHotelResNotifRQ;
    }

    private OTAHotelAvailRS validateOTAHotlAvailRQ(final OTAHotelAvailRQ otaHotelAvailRQ) {
        if (otaHotelAvailRQ == null) {
            return new OTAHotelAvailRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.PROCESSING_EXCEPTION,
                    OTAConstants.Error.Code.REQUIRED_FIELD_MISSING, "OTA_HotelAvailRQ is null")));
        } else {
            if (otaHotelAvailRQ.getEchoToken() == null) {
                return new OTAHotelAvailRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.PROCESSING_EXCEPTION, OTAConstants.Error.Code.REQUIRED_FIELD_MISSING, "Required field missing.")));
            }
            if (otaHotelAvailRQ.getAvailRequestSegments() == null ||
                    otaHotelAvailRQ.getAvailRequestSegments().getAvailRequestSegment() == null ||
                    otaHotelAvailRQ.getAvailRequestSegments().getAvailRequestSegment().isEmpty()) {
                return new OTAHotelAvailRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.PROCESSING_EXCEPTION, OTAConstants.Error.Code.REQUIRED_FIELD_MISSING, "Required field missing.")));
            }
            for (final AvailRequestSegmentsType.AvailRequestSegment availRequestSegment : otaHotelAvailRQ.getAvailRequestSegments().getAvailRequestSegment()) {
                if (availRequestSegment.getHotelSearchCriteria() == null ||
                        availRequestSegment.getHotelSearchCriteria().getCriterion() == null ||
                        availRequestSegment.getHotelSearchCriteria().getCriterion().isEmpty() ||
                        availRequestSegment.getHotelSearchCriteria().getCriterion().get(0).getHotelRef() == null ||
                        availRequestSegment.getHotelSearchCriteria().getCriterion().get(0).getHotelRef().isEmpty() ||
                        availRequestSegment.getHotelSearchCriteria().getCriterion().get(0).getHotelRef().get(0).getHotelCode() == null
                        ) {
                    return new OTAHotelAvailRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.PROCESSING_EXCEPTION, OTAConstants.Error.Code.REQUIRED_FIELD_MISSING, "Required field missing.")));
                }
            }
        }
        return null;
    }

    @WebMethod(operationName = "OTA_HotelAvailRQ")
    @WebResult(name = "OTA_HotelAvailRS")
    public OTAHotelAvailRS OTAHotelAvailRQ(
            @WebParam(name = "OTA_HotelAvailRQ", mode = WebParam.Mode.IN)
            final OTAHotelAvailRQ otaHotelAvailRQ) {
        logger.debug("OTAHotelAvailRQ");
        OTAHotelAvailRS otaHotelAvailRS = null;
        ChannelQueueStatus requestStatus = ChannelQueueStatus.FAIL;
        String hotelTicker = null;
        try {
            //Validating request
            otaHotelAvailRS = validateOTAHotlAvailRQ(otaHotelAvailRQ);
            if (otaHotelAvailRS != null) {
                return otaHotelAvailRS;
            }
            //RequestValidated
            final String echoToken = otaHotelAvailRQ.getEchoToken();
            final BigDecimal version = otaHotelAvailRQ.getVersion();

            final OTAHotelAvailRS otaHotelAvailRSInternalError = new OTAHotelAvailRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.PROCESSING_EXCEPTION, OTAConstants.Error.Code.UNABLE_TO_PROCESS, "Internal Error. Plase try later.")));
            for (final AvailRequestSegmentsType.AvailRequestSegment availRequestSegment : otaHotelAvailRQ.getAvailRequestSegments().getAvailRequestSegment()) {
                hotelTicker = availRequestSegment.getHotelSearchCriteria().getCriterion().get(0).getHotelRef().get(0).getHotelCode();
                DBConnection dbConnection = null;
                //validating the username and password
                final ErrorsType authErrors = validLoginBySoapHeader(hotelTicker);
                if (authErrors != null) {
                    otaHotelAvailRS = new OTAHotelAvailRS(authErrors, version, echoToken);
                    return otaHotelAvailRS;
                }
                try {
                    try {
                        dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
                        if (dbConnection == null) {
//                            throw new NonexistentValueException("");
                            otaHotelAvailRS = new OTAHotelAvailRS(new ErrorsType(new ErrorType
                                    (OTAConstants.Error.Type.PROCESSING_EXCEPTION, OTAConstants.Error.Code.NO_HOTEL_MATCH_FOUND, "hotel_code: " + hotelTicker + " not match with any of ours hotels.")));
                            return otaHotelAvailRS;
                        }
                    } catch (DBAccessException e) {
                        logger.error(e);
                        otaHotelAvailRS = otaHotelAvailRSInternalError;
                        return otaHotelAvailRS;
                    } catch (ExternalFileException e) {
                        logger.error(e);
                        otaHotelAvailRS = otaHotelAvailRSInternalError;
                        return otaHotelAvailRS;
                    } catch (NonexistentValueException e) {
                        logger.error(e);
                        DAOUtil.close(dbConnection);
                        otaHotelAvailRS = new OTAHotelAvailRS(new ErrorsType(new ErrorType(OTAConstants.Error.Type.PROCESSING_EXCEPTION,
                                OTAConstants.Error.Code.INVALID_PROPERTY_CODE, hotelTicker)),
                                version, otaHotelAvailRQ.getEchoToken()
                        );
                        return otaHotelAvailRS;
                    }
                    final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, "eng");
                    try {
                        List<OTAHotelAvailRS.RoomStays.RoomStay> products = new ArrayList<OTAHotelAvailRS.RoomStays.RoomStay>();
                        for (Inventory item : inventoryDBHandler.getInventoriesValid()) {
                            final OTAHotelAvailRS.RoomStays.RoomStay roomStay = new OTAHotelAvailRS.RoomStays.RoomStay();
                            RoomTypeType roomTypeType = new RoomTypeType();
                            roomTypeType.setRoomTypeCode(item.getTicker());
                            final ParagraphType roomDescription = new ParagraphType();
                            //added the inv_ticker, to avoid map errors
                            roomDescription.setName(item.getFullName() + " (" + item.getTicker() + ")");
                            roomDescription.setTextOrImageOrURL(item.getFullName() + " (" + item.getTicker() + ")");
                            roomTypeType.setRoomDescription(roomDescription);
                            roomStay.setRoomTypes(roomTypeType);
                            products.add(roomStay);
                        }
                        otaHotelAvailRS = new OTAHotelAvailRS(echoToken, new BigDecimal(1.0), new OTAHotelAvailRS.RoomStays(products));
                        requestStatus = ChannelQueueStatus.SUCCESS;
                        return otaHotelAvailRS;
                    } catch (DBAccessException ex) {
                        logger.error(ex);
                        otaHotelAvailRS = otaHotelAvailRSInternalError;
                        return otaHotelAvailRS;
                    }
                } finally {
                    DAOUtil.close(dbConnection);
                }
            }
            otaHotelAvailRS = otaHotelAvailRSInternalError;
            return otaHotelAvailRS;
        } finally {
            try {
                if (requestStatus == ChannelQueueStatus.FAIL) {
                    try {
//                        logger.error("Fail Status: "+ XMLUtils.parseToString(otaHotelAvailRQ));
                        logger.error("Fail Status: " + XMLUtils.parseToString(otaHotelAvailRS));
                    } catch (Exception e) {
//                        logger.error("Fail Status no XML: " + otaHotelAvailRQ);
                        logger.error("Fail Status no XML: " + otaHotelAvailRS);
                    }
                }
                integrationBeanLocal.storeSingleConnection(hotelTicker,
                        ChannelTicker.SITEMINDER, ChannelConnectionType.OFFER_INVENTORY, requestStatus
                        , null, otaHotelAvailRQ, otaHotelAvailRS);
            } catch (IntegrationException e) {
                logger.error(e);
            }
        }
    }

    @WebMethod(exclude = true)
    public void cancelBehavior(final String hotelTicker, final String reservationId)
            throws ExternalFileException, DBAccessException, NonexistentValueException {
        logger.debug("hotelTicker: " + hotelTicker);
        logger.debug("reservationId: " + reservationId);
        cancelAllRoomFromSaveReserves(hotelTicker, reservationId);
        roomAvailabilityDecreases(hotelTicker, reservationId);
    }

    private void roomAvailabilityDecreases(final String hotelTicker, final String reservationId)
            throws NonexistentValueException, ExternalFileException, DBAccessException {
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            ReservationDBHandler reservationDBHandler = new ReservationDBHandler(dbConnection);
            final Reservation reservation = reservationDBHandler.getReservationByReservationId(reservationId);
            if (reservation == null)
                throw new DBAccessException("Reservation " + reservationId + " not found.");
            final Date firstCheckIn = reservation.getFirstCheckIn();
            final Date lastCheckOut = DateUtil.cloneAndIncrementDays(reservation.getLastCheckOut(), -1);
            DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(dbConnection, firstCheckIn, lastCheckOut);
            dailyValuesDBHandler.getInventoryValuesBetweenDates();
            for (final RoomStay roomStay : reservation.getRoomStays()) {
                final Date checkIn = roomStay.getDateCheckIn();
                final Date checkOut = DateUtil.cloneAndIncrementDays(roomStay.getDateCheckOut(), -1);
//                final String invTicker = roomStay.getInventoryTicker(dbConnection);
                final String invTicker = dailyValuesDBHandler.getSuperTicker(roomStay.getInventoryTicker
                        (dbConnection), 0);
                //validate if exist the Availability InventoryElement for this Inventory to update the
                if (dailyValuesDBHandler.validateIfInventoryElementExists(invTicker, HashRangeValue.TOTAL_AVAILABILITY)) {
                    //Updating RageValues
                    Date dateIterator = new Date(checkIn.getTime());
                    final RangeValue<Integer> ratesRangeValue = dailyValuesDBHandler.getAvailabilityByTicker(invTicker);
//                    logger.debug("RateBeforeWhile" + ratesRangeValue);
                    while (!dateIterator.after(checkOut)) {
                        final Integer rate = ratesRangeValue.getValueForADate(dateIterator);
                        final int value = rate - 1;
//                        logger.debug("Rate: " + rate + " day: " + dateIterator + " value: " + value);
                        ratesRangeValue.putValueForADate(dateIterator, value);
//                        logger.debug("value putted: " + ratesRangeValue.getFinalValueForADate(dateIterator));
                        DateUtil.incrementDays(dateIterator, 1);
                    }
                    dailyValuesDBHandler.updateAvailabilityByTicker(invTicker, ratesRangeValue);
                } else {
                    logger.error("The Element Type '" + HashRangeValue.TOTAL_AVAILABILITY + "' for the invTicker '"
                            + invTicker + "' doesn't exist in the DB. '" + hotelTicker + "'");
                }
            }
        } catch (Exception ex) {
            logger.error("Error Decreasing roomAvailability '" + ex + "'");
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    private void cancelAllRoomFromSaveReserves(final String hotelTicker, final String reservationId)
            throws NonexistentValueException, ExternalFileException, DBAccessException {
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            ReservationDBHandler reservationDBHandler = new ReservationDBHandler(dbConnection);
            reservationDBHandler.cancelReservationByReservationId(reservationId);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    private String getMSG(final String msg, final String itemTicker) {
        return msg + " can not be set to '" + itemTicker + "' ticker because it is not an own value. Need to be own value. ";
    }

    private ErrorsType validLoginBySoapHeader(final String hotelTicker) {
        try {
            logger.debug("validLoginBySoapHeader");
            MessageContext messageContext = webServiceContext.getMessageContext();
            Boolean authnStatus = false;
            authnStatus = (Boolean) messageContext.get(SecuritySOAPHandler.AUTHN_STAUTS);

            if (authnStatus == null || !authnStatus) {
                logger.error("SOAP Header authentication not found");
                return new ErrorsType(new ErrorType(OTAConstants.Error.Type.AUTHENTICATION,
                        OTAConstants.Error.Code.UNABLE_TO_PROCESS, "SOAP Header authentication not found"));
            }
            String login = (String) messageContext.get("login");
            String passwd = (String) messageContext.get("passwd");
            logger.debug("user: '" + login + "' pwd: '" + passwd + "' try this service.");

            final String hotelTickersList = connectionBean.getHotelTickers(login, passwd);
//            logger.debug(hotelTickersList);
            final JsonParser jsonParser = new JsonParser();
            final JsonElement jsonResponse = jsonParser.parse(hotelTickersList);
            if (jsonResponse == null || jsonResponse.isJsonNull()) {
                //This case means an internal error.
                return new ErrorsType(new ErrorType(OTAConstants.Error.Type.AUTHENTICATION,
                        OTAConstants.Error.Code.UNABLE_TO_PROCESS, "Authentication Error"));
            } else if (!jsonResponse.isJsonArray()) {
                //This case means there was an error in the authentication.
                return new ErrorsType(new ErrorType(OTAConstants.Error.Type.AUTHENTICATION,
                        OTAConstants.Error.Code.UNABLE_TO_PROCESS, "Authentication Error"));
            } else {
                //This case means that the users are perfectly authentic.
                for (Iterator iterator = ((JsonArray) jsonResponse).iterator(); iterator.hasNext(); ) {
                    JsonElement jsonElement = (JsonElement) iterator.next();
                    if (jsonElement != null && !jsonElement.isJsonNull()) {
                        if (jsonElement.getAsString().trim().equalsIgnoreCase(hotelTicker)) {
                            logger.debug("Access granted for the hotel: " + hotelTicker);
                            return null;
                        }
                    }
                }
            }
            return new ErrorsType(new ErrorType(OTAConstants.Error.Type.AUTHORIZATION,
                    OTAConstants.Error.Code.UNABLE_TO_PROCESS,
                    "This username doesn't have permissions on the hotel: " + hotelTicker));
        } catch (Exception ex) {
            logger.error("Exception to parse header authentication: " + ex, ex);
            return new ErrorsType(new ErrorType(OTAConstants.Error.Type.AUTHENTICATION,
                    OTAConstants.Error.Code.UNABLE_TO_PROCESS, "Exception to parse SOAP Header authentication"));
        }
    }

}
