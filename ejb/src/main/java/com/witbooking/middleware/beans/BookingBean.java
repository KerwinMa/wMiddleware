/*
 *  BookingBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.google.common.io.BaseEncoding;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.*;
import com.witbooking.middleware.exceptions.*;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.exceptions.integration.booking.BookingException;
import com.witbooking.middleware.integration.ChannelConnectionType;
import com.witbooking.middleware.integration.ChannelQueueStatus;
import com.witbooking.middleware.integration.ChannelTicker;
import com.witbooking.middleware.integration.EntryQueueItem;
import com.witbooking.middleware.integration.booking.model.Constants;
import com.witbooking.middleware.integration.booking.model.request.OTA_HotelAvailNotif.OTA_HotelAvailNotifRQ;
import com.witbooking.middleware.integration.booking.model.request.OTA_HotelRateAmountNotif.BaseByGuestAmt;
import com.witbooking.middleware.integration.booking.model.request.OTA_HotelRateAmountNotif.OTA_HotelRateAmountNotifRQ;
import com.witbooking.middleware.integration.booking.model.request.OTA_HotelResModifyNotifRS;
import com.witbooking.middleware.integration.booking.model.request.OTA_HotelResNotifRS;
import com.witbooking.middleware.integration.booking.model.response.OTA_HotelAvailNotifRS;
import com.witbooking.middleware.integration.booking.model.response.OTA_HotelRateAmountNotifRS;
import com.witbooking.middleware.integration.booking.model.response.OTA_HotelResModifyNotifRQ;
import com.witbooking.middleware.integration.booking.model.response.OTA_StandardResponse;
import com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq.*;
import com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq.ResGuest;
import com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq.RoomStay;
import com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq.Service;
import com.witbooking.middleware.integration.mandrill.model.Event;
import com.witbooking.middleware.integration.mandrill.model.Message;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.channelsIntegration.Channel;
import com.witbooking.middleware.model.channelsIntegration.ChannelMappingAbstract;
import com.witbooking.middleware.model.values.DailyValue;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.EmailsUtils;
import com.witbooking.middleware.utils.HttpConnectionUtils;
import com.witbooking.middleware.utils.XMLUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.*;

/**
 * Insert description here
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 22, 2013
 */
@Stateless
public class BookingBean implements BookingBeanLocal, BookingBeanRemote {

    private static final Logger logger = Logger.getLogger(BookingBean.class);
    @EJB
    private IntegrationBeanLocal integrationBeanLocal;
    @EJB
    private ConnectionBeanLocal connectionBeanLocal;
    @EJB
    private MailingBeanLocal mailingBeanLocal;
//    private static final String URL_AVAIL_NOTIF = "https://supply-xml.booking.com/hotels/ota/OTA_HotelAvailNotif";
//    private static final String URL_RATE_AMOUNT_NOTIF = "https://supply-xml.booking.com/hotels/ota/OTA_HotelRateAmountNotif";
//    private static final String URL_RESERVATIONS = "https://secure-supply-xml.booking.com/hotels/ota/OTA_HotelResNotif";
//    private static final String URL_MODIFIED_RESERVATIONS = "https://secure-supply-xml.booking.com/hotels/ota/OTA_HotelResModifyNotif";

    @Override
    public void handleNewReserves(String bookingHotelID, String reservationId, Date lastChange) throws
            BookingException {
        logger.debug("Handling new reservation. bookingHotelID: " + bookingHotelID + " reservationId: " + reservationId +
                " lastChange: " + lastChange);
        OTA_HotelResNotifRS response = new OTA_HotelResNotifRS();
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        String request = null;
        String responseString = null;
        List<EntryQueueItem> entryQueueItems = new ArrayList<>();
        DBConnection dbConnection = null;
        String hotelTicker = null;
        try {
            dbConnection = new DBConnection(getDBCredentialsByBookingID(bookingHotelID));
            hotelTicker = dbConnection.getDbCredentials().getTicker();
            final String encode = getEncodeCredentials(dbConnection);
            request = getReservations(encode, MiddlewareProperties.BOOKING_URL_RESERVATION, reservationId, bookingHotelID, lastChange);
            OTA_HotelResNotifRQ item = castReservations(request);
//            String RUID = XMLUtils.getRUID(request);
//            final int bookingChannelId = channelBeanLocal.getChannelId(BookingMapping.CHANNEL_TICKER);
//            channelBeanLocal.storeInLog(bookingChannelId, bookingHotelID, null, request, RUID);
            if (item == null || item.isEmpty()) {
                logger.debug("The reservations are Empty");
            } else {
                logger.debug(" Receiving " + item.size() + " new Reservations from Booking.com");
                InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection);
                List<Reservation> reservationList = transformReservation(item.getHotelReservations(), inventoryDBHandler);
                final int insertedReservations = insertNewReservations(reservationList, inventoryDBHandler);
                logger.debug("InsertedReservations: " + insertedReservations);
                for (Reservation itemRes : reservationList) {
                    response.add(itemRes.getBookingReservationId(), itemRes.getReservationId());
                    entryQueueItems.add(new EntryQueueItem(itemRes.getBookingReservationId()));
                }
                try {
                    final String xmlRequest = XMLUtils.marshalFromObject(response, XMLUtils.NO_FORMATTED_INPUT);
                    logger.debug(xmlRequest);
                    responseString = sendRequest(MiddlewareProperties.BOOKING_URL_RESERVATION, encode, xmlRequest);
                    logger.debug("responseString: '" + responseString + "'");
                    if (!responseString.isEmpty() && responseString.contains("OTA_HotelResNotifRS")) {
                        response = (OTA_HotelResNotifRS) getResponseFromString(responseString, OTA_HotelResNotifRS.class);
                        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                            throw new BookingException("Error Confirming the Modified Reservation");
                        }
                    }
                } catch (IOException | JAXBException | BookingException e) {
                    String subject = "Error Confirming the Reservation in Booking.com hotel: '" +
                            hotelTicker;
                    logger.error(subject + "' resID:'" + reservationId + "' lastChange: '" + lastChange + "' " +
                            "response: " + responseString);
                    logger.error(e);
                    EmailsUtils.sendEmailToAdmins(subject, subject + "' resID:'" + reservationId + "' lastChange: '" +
                                    lastChange + "'  <br/> Response: " + responseString + " <br/> ",
                            Arrays.asList("WitBookerAPI Errors", "Error Booking Reservation"), e);
                }
            }
            status = ChannelQueueStatus.SUCCESS;
        } catch (Exception ex) {
            logger.error(ex);
            String subject = "Error saving a new Reservation from Booking.com to hotel:'" + hotelTicker + "' ";
            EmailsUtils.sendEmailToAdmins(subject, subject, Arrays.asList("WitBookerAPI Errors", "Error Booking Reservation"), ex);
            throw new BookingException(ex);
        } finally {
            try {
                DAOUtil.close(dbConnection);
                if (reservationId != null) {
                    integrationBeanLocal.reportAnConnection(bookingHotelID, reservationId, ChannelTicker.BOOKING,
                            ChannelConnectionType.GET_RESERVES, status, request, responseString);
                } else if (lastChange != null) {
                    integrationBeanLocal.reportAnConnection(bookingHotelID, DateUtil.timestampFormat(lastChange), ChannelTicker.BOOKING,
                            ChannelConnectionType.GET_RESERVES, status, request, responseString);
                } else {
                    integrationBeanLocal.reportAnConnection(bookingHotelID, ChannelTicker.BOOKING,
                            ChannelConnectionType.GET_RESERVES, status, entryQueueItems, request, responseString);
                }
            } catch (MiddlewareException ex) {
                logger.error(ex);
            }
        }
    }


    @Override
    public void handleCancelledReservations(String bookingHotelID, String reservationId, Date lastChange) throws BookingException {
        OTA_HotelResModifyNotifRS response = new OTA_HotelResModifyNotifRS();
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        String request = null;
        String responseString = null;
        List<EntryQueueItem> entryQueueItems = new ArrayList<>();
        DBConnection dbConnection = null;
        String hotelTicker = null;
        int totalCanceled;
        try {
            dbConnection = new DBConnection(getDBCredentialsByBookingID(bookingHotelID));
            final String encode = getEncodeCredentials(dbConnection);
            request = getReservations(encode, MiddlewareProperties.BOOKING_URL_MODIFIED_RESERVATIONS, reservationId, bookingHotelID, lastChange);
            OTA_HotelResModifyNotifRQ item = castModifiedReservations(request);
//            String RUID = XMLUtils.getRUID(request);
            if (item == null || item.isEmpty()) {
                logger.debug("The reservations are Empty");
            } else {
                logger.debug(" Receiving " + item.size() + " new Reservations from Booking.com");
                hotelTicker = dbConnection.getDbCredentials().getTicker();
                InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection);
                ReservationDBHandler reservationDBHandler = new ReservationDBHandler(inventoryDBHandler);
                for (HotelReservation reservationBooking : item.getHotelReservations()) {
                    final String witBookingReservationId = OTA_HotelResNotifRQ.generateIdConfirmation(reservationBooking);
                    if (reservationBooking.isCancellation()) {
                        totalCanceled = reservationDBHandler.cancelReservationByReservationId(witBookingReservationId);
                        logger.debug("totalCanceled: " + totalCanceled);
                        response.add(reservationBooking.getReservationId(), witBookingReservationId);
                        entryQueueItems.add(new EntryQueueItem(reservationBooking.getReservationId()));
                        if (totalCanceled < 1) {
                            throw new BookingException("Not possible to cancel the reservation with id: " + witBookingReservationId);
                        }
                    }
                }
                try {
                    final String xmlRequest = XMLUtils.marshalFromObject(response, XMLUtils.NO_FORMATTED_INPUT);
                    logger.debug(xmlRequest);
                    responseString = sendRequest(MiddlewareProperties.BOOKING_URL_MODIFIED_RESERVATIONS,
                            encode, xmlRequest);
                    logger.debug("responseString: '" + responseString + "'");
                    if (!responseString.isEmpty() && responseString.contains("OTA_HotelResModifyNotifRS")) {
                        response = (OTA_HotelResModifyNotifRS) getResponseFromString(responseString,
                                OTA_HotelResModifyNotifRS.class);
                        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                            throw new BookingException("Error Confirming the Modified Reservation");
                        }
                    }
                } catch (IOException | JAXBException | BookingException e) {
                    String subject = "Error Confirming the Cancelled Reservation in Booking.com hotel: '" +
                            hotelTicker;
                    logger.error(subject + "' resID:'" + reservationId + "' lastChange: '" + lastChange + "'" + " " +
                            "response: " + responseString);
                    logger.error(e);
                    EmailsUtils.sendEmailToAdmins(subject, subject + "' resID:'" + reservationId + "' lastChange: '" +
                                    lastChange + "'  <br/> Response: " + responseString + " <br/> ",
                            Arrays.asList("WitBookerAPI Errors", "Error Booking Reservation"), e);
                }
            }
            status = ChannelQueueStatus.SUCCESS;
        } catch (Exception ex) {
            logger.error(ex);
            String subject = "Error Cancelling Reservation from Booking.com to hotel:'" + hotelTicker + "' ";
            EmailsUtils.sendEmailToAdmins(subject, subject, Arrays.asList("WitBookerAPI Errors", "Error Booking Reservation"), ex);
            throw new BookingException(ex);
        } finally {
            try {
                DAOUtil.close(dbConnection);
                if (reservationId != null) {
                    integrationBeanLocal.reportAnConnection(bookingHotelID, reservationId, ChannelTicker.BOOKING,
                            ChannelConnectionType.GET_CANCELLED_RESERVES, status, request, responseString);
                } else if (lastChange != null) {
                    integrationBeanLocal.reportAnConnection(bookingHotelID, DateUtil.timestampFormat(lastChange), ChannelTicker.BOOKING,
                            ChannelConnectionType.GET_CANCELLED_RESERVES, status, request, responseString);
                } else {
                    integrationBeanLocal.reportAnConnection(bookingHotelID, ChannelTicker.BOOKING,
                            ChannelConnectionType.GET_CANCELLED_RESERVES, status, entryQueueItems, request, responseString);
                }
            } catch (MiddlewareException ex) {
                logger.error(ex);
            }
        }
    }


    /**
     * Receives the Booking hotel ticker and the booking reservation id and
     * connecting with Booking to request reservations modified.
     * <p/>
     * Modifies reservations received. There is two types of modifications:
     * <ul>
     * <li>Cancellation: update the status to canceled the reservation
     * stored.</li>
     * <li>Modification: Seek reserves stored in the database and verifying
     * adequate room stay updated all. If there is a storage room stay is not
     * received in the modification, must change the status to canceled.</li>
     * </ul>
     */
    @Override
    public void handleModifiedReservations(String bookingHotelID, String reservationId, Date lastChange)
            throws BookingException {
        OTA_HotelResModifyNotifRS response = new OTA_HotelResModifyNotifRS();
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        String request = null;
        String responseString = null;
        List<EntryQueueItem> entryQueueItems = new ArrayList<>();
        DBConnection dbConnection = null;
        String hotelTicker = null;
        try {
            dbConnection = new DBConnection(getDBCredentialsByBookingID(bookingHotelID));
            final String encode = getEncodeCredentials(dbConnection);
            request = getReservations(encode, MiddlewareProperties.BOOKING_URL_MODIFIED_RESERVATIONS, reservationId, bookingHotelID, lastChange);
            OTA_HotelResModifyNotifRQ item = castModifiedReservations(request);
//            String RUID = XMLUtils.getRUID(request);
            if (item == null || item.isEmpty()) {
                logger.debug("The reservations are Empty");
            } else {
                logger.debug(" Receiving " + item.size() + " new Reservations from Booking.com");
                InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection);
                hotelTicker = dbConnection.getDbCredentials().getTicker();
                List<Reservation> reservationList = transformReservation(item.getHotelReservations(), inventoryDBHandler);
                for (Reservation itemRes : reservationList) {
                    response.add(itemRes.getBookingReservationId(), itemRes.getReservationId());
                    entryQueueItems.add(new EntryQueueItem(itemRes.getBookingReservationId()));
                    itemRes.updateModificationDate();
                }
                final int modifiedReservations = updateReservations(reservationList, inventoryDBHandler);
                logger.debug("modifiedReservations: " + modifiedReservations);
                try {
                    final String xmlRequest = XMLUtils.marshalFromObject(response, XMLUtils.NO_FORMATTED_INPUT);
                    logger.debug(xmlRequest);
                    responseString = sendRequest(MiddlewareProperties.BOOKING_URL_MODIFIED_RESERVATIONS, encode, xmlRequest);
//                    String RUID = XMLUtils.getRUID(request);
                    logger.debug("responseString: '" + responseString + "'");
                    if (!responseString.isEmpty() && responseString.contains("OTA_HotelResModifyNotifRS")) {
                        response = (OTA_HotelResModifyNotifRS) getResponseFromString(responseString,
                                OTA_HotelResModifyNotifRS.class);
                        if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                            throw new BookingException("Error Confirming the Modified Reservation");
                        }
                    }
                } catch (IOException | JAXBException | BookingException e) {
                    String subject = "Error Confirming the Modified Reservation in Booking.com hotel: '" +
                            hotelTicker;
                    logger.error(subject + "' resID:'" + reservationId + "' lastChange: '" + lastChange + "'" + " " +
                            "response: " + responseString);
                    logger.error(e);
                    EmailsUtils.sendEmailToAdmins(subject, subject + "' resID:'" + reservationId + "' lastChange: '" +
                                    lastChange + "'  <br/> Response: " + responseString + " <br/> ",
                            Arrays.asList("WitBookerAPI Errors", "Error Booking Reservation"), e);
                }
            }
            status = ChannelQueueStatus.SUCCESS;
        } catch (Exception ex) {
            logger.error(ex);
            String subject = "Error Updating Reservation from Booking.com to hotel:'" + hotelTicker + "' ";
            EmailsUtils.sendEmailToAdmins(subject, subject, Arrays.asList("WitBookerAPI Errors", "Error Booking Reservation"), ex);
            throw new BookingException(ex);
        } finally {
            try {
                DAOUtil.close(dbConnection);
                if (reservationId != null) {
                    integrationBeanLocal.reportAnConnection(bookingHotelID, reservationId, ChannelTicker.BOOKING,
                            ChannelConnectionType.GET_MODIFIED_RESERVES, status, request, responseString);
                } else if (lastChange != null) {
                    integrationBeanLocal.reportAnConnection(bookingHotelID, DateUtil.timestampFormat(lastChange), ChannelTicker.BOOKING,
                            ChannelConnectionType.GET_MODIFIED_RESERVES, status, request, responseString);
                } else {
                    integrationBeanLocal.reportAnConnection(bookingHotelID, ChannelTicker.BOOKING,
                            ChannelConnectionType.GET_MODIFIED_RESERVES, status, entryQueueItems, request, responseString);
                }
            } catch (MiddlewareException ex) {
                logger.error(ex);
            }
        }
    }

    @Override
    public OTA_HotelAvailNotifRS updateARI(final String hotelTicker, List<String> inventoryTickers,
                                           final Date start, final Date end)
            throws BookingException {
        DBConnection dbConnection = null;
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        String requestString = null;
        String responseString = null;
        Map<String, EntryQueueItem> queueItemsMap = new HashMap<>();
        try {
            dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            final ChannelsHotelDBHandler channelsHotelDBHandler = new ChannelsHotelDBHandler(dbConnection);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection);
            Set<String> inventoryToSend = new HashSet<>();
            List<String> items = channelsHotelDBHandler.getTickersMapped(Constants.CHANNEL_TICKER);
            if (inventoryTickers == null || inventoryTickers.isEmpty() || inventoryTickers.containsAll(items)) {
                inventoryToSend.addAll(items);
            } else {
                //getting the children Inventories for ARI values
                for (String item : items) {
                    for (String askedTicker : inventoryTickers) {
                        if (item.equals(askedTicker)) {
                            inventoryToSend.add(item);
                            break;
                        }
                        //if the mapped inventory is child of the inventory asked, add it to the request
                        Inventory childInventory = inventoryDBHandler.getInventoryByTicker(item);
                        Inventory askedInventory = inventoryDBHandler.getInventoryByTicker(askedTicker);
                        if (childInventory != null) {
                            if (childInventory.getAvailability().isChildOf(askedTicker) ||
                                    childInventory.getAvailability().isSiblingOf(askedInventory.getAvailability()) ||
                                    childInventory.getLock().isChildOf(askedTicker) ||
                                    childInventory.getLock().isSiblingOf(askedInventory.getLock()) ||
                                    childInventory.getMinStay().isChildOf(askedTicker) ||
                                    childInventory.getMinStay().isSiblingOf(askedInventory.getMinStay()) ||
                                    childInventory.getMaxStay().isChildOf(askedTicker) ||
                                    childInventory.getMaxStay().isSiblingOf(askedInventory.getMaxStay())) {
                                inventoryToSend.add(item);
                                //added to EntryQueueItems in order to save as success the connection asked
                                queueItemsMap.put(askedTicker, new EntryQueueItem(askedTicker, start, end));
                                break;
                            }
                        }
                    }
                }
            }
            OTA_HotelAvailNotifRQ request = new OTA_HotelAvailNotifRQ();
            final DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, start, end);
            dailyValuesDBHandler.getSelectedInventoryValuesBetweenDates(new ArrayList<>(inventoryToSend));
            for (String inventoryTicker : inventoryToSend) {
                //Adding the EntryQueueItems, to the map
                EntryQueueItem queueItem = queueItemsMap.get(inventoryTicker);
                if (queueItem == null) {
                    queueItemsMap.put(inventoryTicker, new EntryQueueItem(inventoryTicker, start, end));
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
                        queueItemsMap.put(inventoryTicker, new EntryQueueItem(inventoryTicker, start, end));
                    }
                }
                final ChannelMappingAbstract codesFromInventoryTicker = channelsHotelDBHandler
                        .getCodesFromInventoryTicker(Constants.CHANNEL_TICKER, inventoryTicker);
                final Map<String, String> mapping = codesFromInventoryTicker.getMapping();
                if (mapping != null && mapping.size() == 2) {
                    final String ratePlan = mapping.get(Constants.KEY_RATE_PLAN);
                    final String roomStay = mapping.get(Constants.KEY_ROOM_STAY);
                    if (roomStay == null || ratePlan == null) {
                        //If this happen, is because the invTicker is not mapped, I have to pass to the next ticker
                        logger.debug("The inventory: '" + inventoryTicker + "' is not mapped for Booking. hotelTicker: " + hotelTicker);
                        continue;
                    }
                    logger.debug("hotelTicker:" + hotelTicker + "inventoryTicker: " + inventoryTicker + " roomStay: " +
                            roomStay + " ratePlan: " + ratePlan);
                    final RangeValue<Integer> availabilityByTicker = dailyValuesDBHandler.getAvailabilityByTicker(inventoryTicker);
                    final RangeValue<Integer> minStay = dailyValuesDBHandler.getMinStayByTicker(inventoryTicker);
                    final RangeValue<Integer> maxStay = dailyValuesDBHandler.getMaxStayByTicker(inventoryTicker);
                    final RangeValue<Boolean> lock = dailyValuesDBHandler.getLockByTicker(inventoryTicker);
                    for (final DailyValue<Integer> integerDailyValue : availabilityByTicker.getDailySet()) {
                        final Date startDate = integerDailyValue.getStartDate();
                        final Date endDate = integerDailyValue.getEndDate();
                        final Integer value = integerDailyValue.getValue();
                        request.addAmountRoomsAvailable(roomStay, startDate, endDate, value);
                        logger.debug("ITEM: " + roomStay + " start: " + startDate + " end: " + endDate + " value: " + value);
                    }
                    for (final DailyValue<Integer> integerDailyValue : minStay.getDailySet()) {
                        final Date startDate = integerDailyValue.getStartDate();
                        final Date endDate = integerDailyValue.getEndDate();
                        final Integer value = integerDailyValue.getValue();
                        request.addRestrictionOfStayMin(roomStay, ratePlan, startDate, endDate, value);
                        logger.debug("ITEM: " + roomStay + " start: " + startDate + " end: " + endDate + " value: " + value);
                    }
                    for (final DailyValue<Integer> integerDailyValue : maxStay.getDailySet()) {
                        final Date startDate = integerDailyValue.getStartDate();
                        final Date endDate = integerDailyValue.getEndDate();
                        final Integer value = integerDailyValue.getValue();
                        request.addRestrictionOfStayMax(roomStay, ratePlan, startDate, endDate, value);
                        logger.debug("ITEM: " + roomStay + " start: " + startDate + " end: " + endDate + " value: " + value);
                    }
                    for (final DailyValue<Boolean> booleanDailyValue : lock.getDailySet()) {
                        final Date startDate = booleanDailyValue.getStartDate();
                        final Date endDate = booleanDailyValue.getEndDate();
                        final Boolean value = booleanDailyValue.getValue();
                        if (value)
                            request.addLockRestriction(roomStay, ratePlan, startDate, endDate, OTA_HotelAvailNotifRQ.Lock.CLOSE);
                        else
                            request.addLockRestriction(roomStay, ratePlan, startDate, endDate, OTA_HotelAvailNotifRQ.Lock.OPEN);
                    }
                } else {
                    logger.debug(Constants.KEY_ROOM_STAY + " and " + Constants.KEY_RATE_PLAN + " for " + inventoryTicker + " " + "not found.");
                }
            }
            if (request.isEmpty()) {
                logger.warn("Request Empty for hotelTicker: " + hotelTicker + ", invTickers: " + inventoryTickers);
                status = ChannelQueueStatus.SUCCESS;
                return new OTA_HotelAvailNotifRS();
            }
            requestString = XMLUtils.parseToString(request);
            final String encode = getEncodeCredentials(dbConnection);
            OTA_HotelAvailNotifRS response = (OTA_HotelAvailNotifRS) sendRequest(MiddlewareProperties.BOOKING_URL_AVAIL_NOTIF,
                    encode, requestString, OTA_HotelAvailNotifRS.class);
            responseString = XMLUtils.parseToString(response);
            logger.debug(response);
            if (response.success()) {
                status = ChannelQueueStatus.SUCCESS;
            } else {
                //there is a problem with the Minimum Contracted Rooms in Booking.com and the Lock
                if (response.findErrorText(Constants.ERROR_MINIMUM_CONTRACTED_ROOMS)) {
                    request.removeLocksRestrictions();
                    requestString = XMLUtils.parseToString(request);
                    response = (OTA_HotelAvailNotifRS) sendRequest(MiddlewareProperties.BOOKING_URL_AVAIL_NOTIF,
                            encode, requestString, OTA_HotelAvailNotifRS.class);
                    responseString = response.toString();
                    logger.debug(response);
                    status = (response.success()) ? ChannelQueueStatus.SUCCESS : ChannelQueueStatus.FAIL;
                } else {
                    status = ChannelQueueStatus.FAIL;
                }
            }
            if (status == ChannelQueueStatus.FAIL) {
                logger.error(responseString);
            }
            return response;
        } catch (DBAccessException | ExternalFileException | NonexistentValueException | JAXBException | XMLStreamException | IOException e) {
            logger.error(e);
            throw new BookingException(e);
        } finally {
            DAOUtil.close(dbConnection);
            try {
                integrationBeanLocal.reportAnConnection(hotelTicker, ChannelTicker.BOOKING,
                        ChannelConnectionType.EXECUTE_UPDATE_ARI, status,
                        new ArrayList<>(queueItemsMap.values()), requestString, responseString);
            } catch (IntegrationException ex) {
                logger.error(ex);
            }
        }
    }

    public OTA_HotelRateAmountNotifRS updateAmount(final String hotelTicker, List<String> inventoryTickers,
                                                   final Date start, final Date end)
            throws BookingException {
        DBConnection dbConnection = null;
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        String requestString = null;
        String responseString = null;
        Map<String, EntryQueueItem> queueItemsMap = new HashMap<>();
        try {
            dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            final ChannelsHotelDBHandler channelsHotelDBHandler = new ChannelsHotelDBHandler(dbConnection);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection);
            Set<String> inventoryToSend = new HashSet<>();
            List<String> items = channelsHotelDBHandler.getTickersMapped(Constants.CHANNEL_TICKER);
            if (inventoryTickers == null || inventoryTickers.isEmpty() || inventoryTickers.containsAll(items)) {
                inventoryToSend.addAll(items);
            } else {
                //getting the children Inventories for ARI values
                for (String item : items) {
                    for (String askedTicker : inventoryTickers) {
                        if (item.equals(askedTicker)) {
                            inventoryToSend.add(item);
                            break;
                        }
                        Inventory childInventory = inventoryDBHandler.getInventoryByTicker(item);
                        if (childInventory != null) {
                            if (childInventory.getRate().isChildOf(askedTicker)) {
                                inventoryToSend.add(item);
                                break;
                            }
                        }
                    }
                }
            }
            OTA_HotelRateAmountNotifRQ request = new OTA_HotelRateAmountNotifRQ();
            final DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, start, end);
            dailyValuesDBHandler.getSelectedInventoryValuesBetweenDates(new ArrayList<>(inventoryToSend));
            for (String inventoryTicker : inventoryToSend) {
                //Adding the EntryQueueItems, to the map
                EntryQueueItem queueItem = queueItemsMap.get(inventoryTicker);
                if (queueItem == null) {
                    queueItemsMap.put(inventoryTicker, new EntryQueueItem(inventoryTicker, start, end));
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
                        queueItemsMap.put(inventoryTicker, new EntryQueueItem(inventoryTicker, start, end));
                    }
                }
                final ChannelMappingAbstract codesFromInventoryTicker = channelsHotelDBHandler.getCodesFromInventoryTicker(Constants.CHANNEL_TICKER, inventoryTicker);
                final Map<String, String> mapping = codesFromInventoryTicker.getMapping();
                if (mapping != null && mapping.size() == 2) {
                    final String ratePlan = mapping.get(Constants.KEY_RATE_PLAN);
                    final String roomStay = mapping.get(Constants.KEY_ROOM_STAY);
                    if (roomStay == null || ratePlan == null) {
                        //If this happen, is because the invTicker is not mapped, I have to pass to the next ticker
                        logger.debug("The inventory: '" + inventoryTicker + "' is not mapped for Booking. hotelTicker: " + hotelTicker);
                        continue;
                    }
                    RangeValue<Float> ratesByTicker = dailyValuesDBHandler.getRatesByTicker(inventoryTicker);
                    for (final DailyValue<Float> rateDailyValue : ratesByTicker.getDailySet()) {
                        BaseByGuestAmt base = new BaseByGuestAmt();
                        Float amount = rateDailyValue.getValue() * 100;
                        base.setAmountAfterTax(amount.intValue());
                        base.setDecimalPlace(2);
                        request.addRate(roomStay, ratePlan, rateDailyValue.getStartDate(), rateDailyValue.getEndDate(), base);
                    }
                } else {
                    throw new BookingException(Constants.KEY_ROOM_STAY + " and " + Constants.KEY_RATE_PLAN + " for " + inventoryTicker + " not found.");
                }

            }
            if (request.isEmpty()) {
                logger.warn("Request Empty for hotelTicker: " + hotelTicker + ", invTickers: " + inventoryTickers);
                status = ChannelQueueStatus.SUCCESS;
                return new OTA_HotelRateAmountNotifRS();
            }
            //String of request
            requestString = XMLUtils.parseToString(request);
            OTA_HotelRateAmountNotifRS response = (OTA_HotelRateAmountNotifRS)
                    sendRequest(MiddlewareProperties.BOOKING_URL_RATE_AMOUNT_NOTIF,
                            getEncodeCredentials(dbConnection), requestString, OTA_HotelRateAmountNotifRS.class);
            responseString = XMLUtils.parseToString(response);
            if (response.success()) {
                status = ChannelQueueStatus.SUCCESS;
            } else {
                status = ChannelQueueStatus.FAIL;
                logger.error(responseString);
            }
            return response;
        } catch (DBAccessException | ExternalFileException | NonexistentValueException | IOException | XMLStreamException | JAXBException e) {
            logger.error(e);
            throw new BookingException(e);
        } finally {
            DAOUtil.close(dbConnection);
            try {
                integrationBeanLocal.reportAnConnection(hotelTicker, ChannelTicker.BOOKING,
                        ChannelConnectionType.EXECUTE_UPDATE_AMOUNT, status, new ArrayList<>(queueItemsMap.values()),
                        requestString, responseString);
            } catch (IntegrationException ex) {
                logger.error(ex);
            }
        }
    }

    private String getEncodeCredentials(final DBConnection dbConnection)
            throws DBAccessException {
        final ChannelsHotelDBHandler channelsHotelDBHandler = new ChannelsHotelDBHandler(dbConnection);
        final Channel channel = channelsHotelDBHandler.getChannelByChannelTicker(Constants.CHANNEL_TICKER);
        if (channel == null) {
            throw new DBAccessException(Constants.CHANNEL_TICKER + " not found on DDBB.");
        }
        return BaseEncoding.base64().encode((channel.getUser() + ":" + channel.getPassword()).getBytes());
    }

    private DBCredentials getDBCredentialsByBookingID(String bookingHotelId)
            throws BookingException {
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            logger.debug("New reservation for hotel with BookingID: " + bookingHotelId);
            String hotelTicker = witMetaDataDBHandler.getHotelTickerFromChannelHotelTicker(Constants.CHANNEL_TICKER, bookingHotelId);
            return DBProperties.getDBCustomerByTicker(hotelTicker);
        } catch (Exception ex) {
            logger.error("Error getting the DBCredential for the Hotel '" + bookingHotelId + "': " + ex.getMessage());
            throw new BookingException(ex);
        } finally {
            if (witMetaDataDBHandler != null) {
                DAOUtil.close(witMetaDataDBHandler.getDbConnection());
            }
        }
    }

    private String getReservations(String encode, String url, String reservationID, String hotelID, Date lastChange) throws
            BookingException, DBAccessException {
        final HttpClient client = HttpConnectionUtils.generateDefaultClientSSL();
        String stringResponse = "";
        try {
            Map<String, String> parameters = new HashMap<>();
            if (reservationID != null && !reservationID.trim().isEmpty())
                parameters.put(Constants.PARAM_VALUE_RES_ID, reservationID);
            if (hotelID != null && !hotelID.trim().isEmpty())
                parameters.put(Constants.PARAM_VALUE_HOTEL_ID, hotelID);
            if (lastChange != null)
                parameters.put(Constants.PARAM_VALUE_LAST_CHANGE, DateUtil.timestampFormat(lastChange));
            HttpResponse response = HttpConnectionUtils.getData(client, url, encode, parameters);
            if (response != null) {
                HttpEntity entity = response.getEntity();
                stringResponse = entity != null ? EntityUtils.toString(entity) : "";
            } else {
                logger.error("Response got is null");
            }
        } catch (IOException e) {
            logger.error(e);
            throw new BookingException(e);
        } finally {
            if (client != null)
                client.getConnectionManager().shutdown();
        }
        return stringResponse;
    }

    private OTA_HotelResNotifRQ castReservations(String xmlResponse)
            throws BookingException {
        try {
            OTA_HotelResNotifRQ item = (OTA_HotelResNotifRQ) XMLUtils.unmarshalFromString(xmlResponse, OTA_HotelResNotifRQ.class);
            item.setRUID(XMLUtils.getRUID(xmlResponse));
            return item;
        } catch (Exception ex) {
            logger.error("Error casting the Reservation received: " + xmlResponse);
            throw new BookingException(ex);
        }
    }

    private OTA_HotelResModifyNotifRQ castModifiedReservations(String xmlResponse) throws BookingException {
        try {
            OTA_HotelResModifyNotifRQ item = (OTA_HotelResModifyNotifRQ) XMLUtils.unmarshalFromString(xmlResponse, OTA_HotelResModifyNotifRQ.class);
            item.setRUID(XMLUtils.getRUID(xmlResponse));
            return item;
        } catch (Exception ex) {
            logger.error("Error casting the Reservation received: " + xmlResponse);
            throw new BookingException(ex);
        }
    }


    /**
     * @throws IOException               Occurs trying to get {@link HttpEntity} from
     *                                   {@link HttpResponse}.
     * @throws JAXBException             Occurs when trying to "cast" {@link JAXBContext}.
     *                                   See {@link #getResponseFromString(java.lang.String, java.lang.Class)} or
     *                                   {@link XMLUtils#getRUID(java.lang.String)}.
     * @throws XMLStreamException        See {@link XMLUtils#getRUID(java.lang.String)
     *                                   }.
     * @throws ExternalFileException
     * @throws DBAccessException
     * @throws NonexistentValueException
     */
    private OTA_StandardResponse sendRequest(String url, String encoding, String message,
                                             Class<? extends OTA_StandardResponse> itemClass)
            throws IOException, JAXBException, XMLStreamException, DBAccessException {
        OTA_StandardResponse ret = null;
        logger.debug("Message to send: " + message);
        logger.debug("URL: " + url);
        final HttpClient client = HttpConnectionUtils.generateDefaultClientSSL();
        try {
            //client = new DefaultHttpClient();
            HttpResponse response = HttpConnectionUtils.postData(client, url, encoding, message);
            if (response != null) {
                HttpEntity entity = response.getEntity();
                String xmlResponse = EntityUtils.toString(entity);
                logger.debug("Response got " + xmlResponse);
                ret = itemClass.cast(getResponseFromString(xmlResponse, itemClass));
                final String RUID = XMLUtils.getRUID(xmlResponse);
                if (ret != null)
                    ret.setRUID(RUID);
            } else {
                logger.error("Response got is null");
            }
        } finally {
            if (client != null)
                client.getConnectionManager().shutdown();
        }
        return ret;
    }

    private String sendRequest(String url, String encoding, String message)
            throws IOException {
        logger.debug("Message to send: " + message);
        logger.debug("URL: " + url);
        final HttpClient client = HttpConnectionUtils.generateDefaultClientSSL();
        try {
            HttpResponse response = HttpConnectionUtils.postData(client, url, encoding, message);
            if (response != null) {
                HttpEntity entity = response.getEntity();
                String xmlResponse = EntityUtils.toString(entity);
                logger.debug("Response got " + xmlResponse);
                return xmlResponse;
            } else {
                logger.error("Response got is null");
            }
        } finally {
            if (client != null)
                client.getConnectionManager().shutdown();
        }
        return "";
    }

    private OTA_StandardResponse getResponseFromString(String reader, Class<? extends OTA_StandardResponse> itemClass)
            throws JAXBException {
        return (OTA_StandardResponse) XMLUtils.unmarshalFromString(reader, itemClass);
    }

    private List<Reservation> transformReservation(List<HotelReservation> hotelReservations,
                                                   final InventoryDBHandler inventoryDBHandler)
            throws BookingException, DBAccessException {
        final List<Reservation> reservationList = new ArrayList<>();
        final HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(inventoryDBHandler.getDbConnection());
        final String defaultHotelCurrency = hotelConfigurationDBHandler.getDefaultCurrency();
        final Language defaultHotelLocale = hotelConfigurationDBHandler.getDefaultLanguage();
        int cancellationRelease = 0;
        try {
            cancellationRelease = Integer.parseInt(hotelConfigurationDBHandler.getHotelProperties(
                    Arrays.asList("reserva_cancelar_release")).getProperty("reserva_cancelar_release"));
        } catch (Exception e) {
            logger.error("invalid value in 'reserva_cancelar_release' : " + e);
        }
        for (HotelReservation bHotelReservation : hotelReservations) {
            final Reservation reservation = new Reservation();
            final String idConfirmation = OTA_HotelResNotifRQ.generateIdConfirmation(bHotelReservation);
            reservation.setLanguage(defaultHotelLocale.getCode());
            reservation.setReservationId(idConfirmation);
            reservation.setDateCreation(bHotelReservation.getDateCreation());
            reservation.setCustomer(transformCustomer(bHotelReservation.getResGlobalInfo()));
            //Currency
            final String reservationCurrency = bHotelReservation.getCurrency();
            final float reservationTotalAmount = bHotelReservation.getTotalAmount();
            float multiplier = 1;
            try {
                if (!defaultHotelCurrency.equalsIgnoreCase(reservationCurrency + ""))
                    multiplier = getMultiplier(defaultHotelCurrency, reservationCurrency);
                //Always try to save the reservation in the defaultHotelCurrency
                reservation.setCurrency(defaultHotelCurrency);
            } catch (CurrencyConverterException ex) {
                logger.error(" Error Calculating the CurrencyConverter Multiplier: '" + reservationCurrency +
                        "' error:" + ex.getMessage());
                //If there is any problem calculating the multiplier stroe the reservation in the reservationCurrency
                reservation.setCurrency(reservationCurrency);
            }
            reservation.setAmountAfterTax(reservationTotalAmount * multiplier);

            final List<com.witbooking.middleware.model.RoomStay> roomStays = new ArrayList<>();
            for (RoomStay item : bHotelReservation.getRoomStays()) {
                //TODO: probar error mala reserva ID
                roomStays.add(transformRoomStay(item, bHotelReservation.getServices(),
                        bHotelReservation.getResGuest(), reservation.getReservationId(), multiplier,
                        inventoryDBHandler));
            }
            reservation.setRoomStays(roomStays);
            reservation.setComments(bHotelReservation.getComments());
            reservation.setCancellationRelease(cancellationRelease);
            reservation.setStatus(Reservation.ReservationStatus.RESERVATION);
            //Channel source is set.
            reservation.setChannelId(Constants.CHANNEL_TICKER);
            reservation.setChannelAddress(Constants.CHANNEL_TICKER);
            reservationList.add(reservation);
        }
        return reservationList;
    }


    private int manageOldReservations(final Reservation reservation, InventoryDBHandler inventoryDBHandler)
            throws DBAccessException {
        HotelConfigurationDBHandler configurationDBHandler = new HotelConfigurationDBHandler(inventoryDBHandler.getDbConnection());
        List<String> emailsConfirmation = configurationDBHandler.getConfirmationEmails();
        String hotelTicker = inventoryDBHandler.getDbConnection().getDbCredentials().getTicker();
        String subject = "Cancellation fail from Booking.com (" + reservation.getBookingReservationId() + ")";
        String htmlContent = "There was a problem trying to modify some reservations from Booking.com in WitBooking: ";
        htmlContent += "<br/><br/> Reservation Info:<br/>";
        htmlContent += "<br/><b>Id</b>: " + reservation.getBookingReservationId();
        htmlContent += "<br/><b>Creation Date</b>: " + DateUtil.timestampFormat(reservation.getDateCreation());
        htmlContent += "<br/><b>Guest Name</b>: " + reservation.getCustomer().getCompleteName();
        htmlContent += "<br/><b>Guest Email</b>: " + reservation.getCustomer().getEmail();
        htmlContent += "<br/><b>Guest Phone</b>: " + reservation.getCustomer().getTelephone();
        htmlContent += "<br/><br/>This could be a Cancellation, please verify it in Booking.com";
        htmlContent += "<br/><br/>For more information, please go to this link: ";
        htmlContent += "<br/>https://witbooking.zendesk.com/hc/es/articles/204263349-Reservas-canceladas-en-booking-com";
        List<String> mailResponseList;
        try {
            mailResponseList = EmailsUtils.sendEmail("admin@witbooking.com", "WitBooking", subject,
                    htmlContent, emailsConfirmation, hotelTicker, null);
            for (String mailResponse : mailResponseList) {
                if (!mailResponse.equalsIgnoreCase(Event.MandrillMessageEventType.SENT.getValue()) &&
                        !mailResponse.equalsIgnoreCase(Event.MandrillMessageEventType.QUEUED.getValue())) {
                    logger.error("Error in sending Cancellation email From Booking.com to hotel:'" + hotelTicker + "' ");
                    EmailsUtils.sendEmailToAdmins("Error in sending Cancellation email From Booking.com to hotel:'" + hotelTicker + "' ",
                            "Error in sending availability email to hotel:'" + hotelTicker + "' <br/>" +
                                    "email:'" + mailResponseList + "' <br/> status: " + mailResponse + " " +
                                    "<br/>Content: <br/><br/>" + htmlContent,
                            Arrays.asList("WitBookerAPI Errors", "Error Booking.com Emails"));
                }
            }
        } catch (Exception ex) {
            try {
                logger.error("Error in sending Cancellation email From Booking.com to hotel:'" + hotelTicker + "' ");
                EmailsUtils.sendEmailToAdmins("Error in sending Cancellation email From Booking.com to hotel:'" + hotelTicker + "' ",
                        "Error in sending availability email to hotel:'" + hotelTicker + "' <br/>" +
                                "<br/>Content: <br/><br/>" + htmlContent,
                        Arrays.asList("WitBookerAPI Errors", "Error Booking.com Emails"));
            } catch (Exception e) {
                logger.error("Error sending email to hotel:'" + hotelTicker + "' email:'" + emailsConfirmation
                        + "' Error: " + e);
            }
        }
        return 1;
    }

    private int insertNewReservations(final List<Reservation> reservations, InventoryDBHandler inventoryDBHandler)
            throws DBAccessException {
        ReservationDBHandler reservationDBHandler = new ReservationDBHandler(inventoryDBHandler);
        final int insertedReservations = reservationDBHandler.insertReservations(reservations);
        if (insertedReservations < 1) {
            logger.error("Empty Reservations. hotel:'" + inventoryDBHandler.getDbConnection().getDbCredentials().getTicker() + "' Exception:" + reservations);
        }
        List<String> reservationIds = new ArrayList<>();
        String hotelTicker = inventoryDBHandler.getDbConnection().getDbCredentials().getTicker();
        for (Reservation reservation : reservations) {
            reservationIds.add(reservation.getReservationId());
            List<MandrillMessageStatus> messageStatusReports = null;
            try {
                messageStatusReports = mailingBeanLocal.sendConfirmationEmail(hotelTicker, reservation);
                if (messageStatusReports == null || messageStatusReports.isEmpty()) {
                    throw new RemoteServiceException("Error in Service Sending the Email. Response Email is NULL.");
                }
                List<EmailData> emailDataList = mailingBeanLocal.saveReservationEmailData(reservation, hotelTicker, messageStatusReports);
                EmailData clientEmailData = new EmailData();
                EmailData hotelEmailData = new EmailData();
                for (EmailData emailData : emailDataList) {
                    if (emailData.getMessageType() == Message.MessageType.USER_CONFIRMATION) {
                        clientEmailData = emailData;
                    } else {
                        hotelEmailData = emailData;
                    }
                }
                //saving the status for the email sent
                reservationDBHandler.updateReservationEmailInfo(clientEmailData, hotelEmailData);
                Event.EventType eventClient = clientEmailData.getLastEmailStatus();
                if (eventClient == Event.EventType.REJECT || eventClient == Event.EventType.INVALID ||
                        eventClient == Event.EventType.HARD_BOUNCE || eventClient == Event.EventType.SOFT_BOUNCE) {
                    throw new RemoteServiceException("Error in Service Sending the Email.");
                }
            } catch (Exception ex) {
                logger.error("Error Sending the Email. hotel:'" + hotelTicker + "' Exception:" + ex);
                EmailsUtils.sendEmailToAdmins("Error sending confirmation email for: " + hotelTicker,
                        "Error sending confirmation email for hotel: '" + hotelTicker +
                                "' <br/> Reservation: '" + reservation.getReservationId() + "' <br/>" +
                                " <br/> Error: '" + ex + "' <br/>" +
                                " <br/> MandrillMessageStatus: '" + messageStatusReports + "' <br/>" +
                                " <br/>Please Verify and resend the Emails if is necessary!<br/>",
                        Arrays.asList("WitBookerAPI Errors", "Error Confirmation Emails"), ex);
            }
        }
        //Post Process for insert a new Reservation
        try {
            integrationBeanLocal.postReservationProcess(reservationDBHandler.getReservationListByReservationIds(reservationIds), inventoryDBHandler);
        } catch (Exception ex) {
            logger.error("Error in the PostSAveReservation with Booking.com to hotel:'"
                    + inventoryDBHandler.getDbConnection().getDbCredentials().getTicker() + "' Exception:" + ex);
            try {
                String subject = "Error saving a new Reservation from Booking.com to hotel:'" +
                        inventoryDBHandler.getDbConnection().getDbCredentials().getTicker() + "' ";
                String body = subject + " <br/>  Exception: " + ex;
                for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
                    body = body + " <br/>             " + stackTraceElement;
                }
                EmailsUtils.sendEmailToAdmins(subject, body, Arrays.asList("WitBookerAPI Errors", "Error Booking Reservation"));
            } catch (Exception e) {
                logger.error("Error sending email to Admin for Errors:'" + "' Error: " + e);
            }
        }
        return insertedReservations;
    }


    private int updateReservations(final List<Reservation> reservations,
                                   InventoryDBHandler inventoryDBHandler) throws DBAccessException {
        ReservationDBHandler reservationDBHandler = new ReservationDBHandler(inventoryDBHandler);
        int updated = 0;
        String hotelTicker = inventoryDBHandler.getDbConnection().getDbCredentials().getTicker();
        for (Reservation reservation : reservations) {
            List<Reservation> oldReservations = reservationDBHandler.getReservationListByReservationIds(Collections.singletonList(reservation.getReservationId()));
            if (oldReservations == null || oldReservations.isEmpty()) {
                logger.error("Trying to modifying an non-existent Reservation. hotel: '"
                        + hotelTicker + "' Exception:" + reservations);
                if (reservation.getRoomStays().isEmpty()) {
                    //This is a Cancellation for a old reservation (before activate the integration)
                    logger.error("It is a cancellation.");
                    updated = updated + manageOldReservations(reservation, inventoryDBHandler);
                } else {
                    logger.error("It will be inserted for the first time.");
                    updated = updated + insertNewReservations(Collections.singletonList(reservation), inventoryDBHandler);
                }
                continue;
            }
            //manage new roomStays
            List<com.witbooking.middleware.model.RoomStay> resNewToInsert = new ArrayList<>();
            for (com.witbooking.middleware.model.RoomStay newRoomStay : new ArrayList<>(reservation.getRoomStays())) {
                com.witbooking.middleware.model.RoomStay oldRoomStay = oldReservations.get(0).removeRoomStayByIdConfirmation(newRoomStay);
                if (oldRoomStay == null) {
                    resNewToInsert.add(newRoomStay);
                    reservation.removeRoomStayByIdConfirmation(newRoomStay);
                }
            }
            //we have to check if there is any roomStay Cancelled
            for (com.witbooking.middleware.model.RoomStay oldRoomStay : oldReservations.get(0).getRoomStays()) {
                updated = updated + reservationDBHandler.cancelRoomStayByIdConfirmation(oldRoomStay.getIdConfirmation());
                List<MandrillMessageStatus> messageStatusReports = null;
                try {
                    //Send the Cancellation Email
                    messageStatusReports = mailingBeanLocal.sendConfirmationEmail(hotelTicker, reservation);
                    if (messageStatusReports == null || messageStatusReports.isEmpty()) {
                        throw new RemoteServiceException("Error in Service Sending the Email. Response Email is NULL.");
                    }
                } catch (Exception ex) {
                    logger.error("Error Sending the Cancellation Email. hotel:'" + hotelTicker + "' Exception:" + ex);
                    EmailsUtils.sendEmailToAdmins("Error sending Cancellation email for: " + hotelTicker,
                            "Error sending Cancellation email for hotel: '" + hotelTicker +
                                    "' <br/> Reservation: '" + reservation.getReservationId() + "' <br/>" +
                                    " <br/> Error: '" + ex + "' <br/>" +
                                    " <br/> MandrillMessageStatus: '" + messageStatusReports + "' <br/>" +
                                    " <br/>Please Verify and resend the Emails if is necessary!<br/>",
                            Arrays.asList("WitBookerAPI Errors", "Error Confirmation Emails"), ex);
                }
            }
            reservation.setStatus(Reservation.ReservationStatus.RESERVATION);
            //updating the roomStays
            if (!reservation.getRoomStays().isEmpty()) {
                updated = updated + reservationDBHandler.updateReservations(Collections.singletonList(reservation)).length;
            }
            if (!resNewToInsert.isEmpty()) {
                //Inserting the new reservations added
                reservation.setRoomStays(resNewToInsert);
                updated = updated + insertNewReservations(Collections.singletonList(reservation), inventoryDBHandler);
            }
        }
        if (updated < 1) {
            logger.error("Empty Reservations. hotel: '" +
                    inventoryDBHandler.getDbConnection().getDbCredentials().getTicker() + "' Exception:" + reservations);
        }
        return updated;
    }

    private float getMultiplier(final String defaultHotelCurrency, final String reservationCurrency)
            throws CurrencyConverterException {
        CurrencyDBHandler currency = null;
        float multiplier = 1;
        try {
            if (defaultHotelCurrency != null
                    && reservationCurrency != null
                    && !defaultHotelCurrency.equals(reservationCurrency)) {
                currency = new CurrencyDBHandler();
                CurrencyExchange exchange = currency.getCurrencyExchange(reservationCurrency);
                multiplier = exchange.getPrice(defaultHotelCurrency);
            }
        } catch (Exception ex) {
            logger.error(" Error getting the currency Exchange: " + ex.getMessage());
            throw new CurrencyConverterException(ex);
        } finally {
            if (currency != null) {
                DAOUtil.close(currency.getDbConnection());
            }
        }
        return multiplier;
    }

    private com.witbooking.middleware.model.Customer transformCustomer(ResGlobalInfo globalInfo) {
        com.witbooking.middleware.model.Customer ret = new com.witbooking.middleware.model.Customer();
        ret.setGivenName(globalInfo.getGivenName());
        ret.setSurName(globalInfo.getSurname());
        ret.setEmail(globalInfo.getEmail());
        ret.setTelephone(globalInfo.getTelephone());
        ret.setCountry(globalInfo.getCountry());
        ret.setAddress(globalInfo.getAddress());
        ret.setCreditCard(transformCreditCard(globalInfo));
        return ret;
    }

    private com.witbooking.middleware.model.CreditCard transformCreditCard(ResGlobalInfo globalInfo) {
        com.witbooking.middleware.model.CreditCard ret = new com.witbooking.middleware.model.CreditCard();
        //If globalInfo.getGuarantee() is null, the CreditCard is not returned in the response
        if (globalInfo.getGuarantee() != null) {
            ret.setCardHolderName(globalInfo.getCardHolderName());
            final String cardNumber = globalInfo.getCardNumber();
            final short expireDate = globalInfo.getExpireDate();
            final String seriesCode = globalInfo.getSeriesCode();
            ret.setCardNumber(cardNumber);
            ret.setExpireDate(expireDate);
            ret.setSeriesCode(seriesCode);
            ret.setCardCodeFromOTA(globalInfo.getCardCode());
            try {
                ret.setCardNumberEncrypted(connectionBeanLocal.encryptionPHP(ConnectionBeanLocal.ENCRYPT, cardNumber + ""));
                ret.setSeriesCodeEncrypted(connectionBeanLocal.encryptionPHP(ConnectionBeanLocal.ENCRYPT, seriesCode + ""));
                ret.setExpireDateEncrypted(connectionBeanLocal.encryptionPHP(ConnectionBeanLocal.ENCRYPT, ret.getExpireDateString() + ""));
            } catch (Exception e) {
                logger.error(e);
            }
        } else {
            ret.setDefaultValues();
        }
        return ret;
    }

    private com.witbooking.middleware.model.RoomStay transformRoomStay(RoomStay room,
                                                                       List<Service> services,
                                                                       List<ResGuest> resGuests,
                                                                       final String idReservation,
                                                                       final float multiplier,
                                                                       InventoryDBHandler inventoryDBHandler)
            throws DBAccessException, BookingException {
        final ChannelsHotelDBHandler channelsHandlers = new ChannelsHotelDBHandler(inventoryDBHandler.getDbConnection());
        final String roomStay = room.getRoomTypeCode();
        final String ratePlan = room.getRatePlanCode();
        logger.debug(" RoomStay: " + roomStay + " RatePlan: " + ratePlan);
        final String inventoryTicker = channelsHandlers.getInventoryTickerFromCodes(Constants.CHANNEL_TICKER, Arrays.asList(roomStay, ratePlan));
        logger.debug("INVENTORY TICKER: " + inventoryTicker);
        Inventory inventory = inventoryDBHandler.getInventoryByTicker(inventoryTicker);
        com.witbooking.middleware.model.RoomStay ret = new com.witbooking.middleware.model.RoomStay();

        //Inserting inventory things.
        ret.setInventoryTicker(inventoryTicker);

        try {
            ret.setInventoryId(inventory.getId());
            ret.setAccommodationType(inventory.getAccommodation().getName());
            ret.setMealPlanType(inventory.getMealPlan().getName());
            ret.setConfigurationType(inventory.getConfiguration().getName());
            ret.setConditionType(inventory.getCondition().getName());
        } catch (NullPointerException ex) {
            logger.error("Error getting the description from Inventory '" + inventoryTicker + "' Error: " + ex);
            if (inventory == null) {
                logger.error("Error in reservation: '" + idReservation + "' The inventory '" + inventoryTicker
                        + "' doesn't exist!");
                throw new BookingException("Error in reservation: '" + idReservation + "' The inventory '" + inventoryTicker
                        + "' doesn't exist!");
            }
        }

        ret.setMealPlanType(room.getMealPlans());
        ret.setAccommodationType(room.getRoomDescription());

        //Handling comission.
        float bookingCommission = room.getCommission() * multiplier;
        ret.setExternalCommission(bookingCommission);
        final Date dateCheckIn = room.getDateCheckIn();
        final Date dateCheckOut = room.getDateCheckOut();
        ret.setDateCheckIn(dateCheckIn);
        ret.setDateCheckOut(dateCheckOut);
        ret.setIdConfirmation(idReservation + Constants.BOOKING_RESERVATION_ROOM_ID_PREFIX + room.getIdReservationRoom());
        ret.setTotalAmount(room.getTotalAmount() * multiplier);
        //Introduciendo el precio de cada dia.
        ret.setRoomRates(new RangeValue<Float>());
        for (Object[] item : room.getPriceByDay()) {
            ret.getRoomRates().putValueForADate((Date) item[0], (Float) item[1]);
        }
        ret.setQuantity(Constants.ONE_ROOM_OF_THIS_TYPE);
        ret.setCapacity(room.getGuestCount());

        List<com.witbooking.middleware.model.ResGuest> guestList = room.getGuests(resGuests);
        if (guestList != null && !resGuests.isEmpty()) {
            ret.setGuestList(guestList);
        } else {
            ret.setGuestList(null);
        }
        List<com.witbooking.middleware.model.ServiceRequested> serviceList = room.getServices(services);
        if (serviceList != null && !serviceList.isEmpty()) {
            ret.setServices(serviceList);
        } else {
            ret.setServices(null);
        }
        //Smoking values
        Map<String, String> additionalRequests = room.getAdditionalRequests();
        if (additionalRequests != null && !additionalRequests.isEmpty()) {
            ret.setAdditionalRequests(additionalRequests);
        } else {
            ret.setAdditionalRequests(null);
        }
        ret.setCancellationDate(null);
        ret.setStatus(Reservation.ReservationStatus.RESERVATION);
        //Para el reservas de modificados
        //private Date cancellationDate;
        //private String cancellationCause;
        //private Date dateModification;

        return ret;
    }
}
