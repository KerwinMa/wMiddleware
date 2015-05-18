/*
 *   DingusBean.java
 * 
 * Copyright(c) 2015 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.dingus;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.witbooking.middleware.beans.ConnectionBean;
import com.witbooking.middleware.beans.ConnectionBeanLocal;
import com.witbooking.middleware.beans.IntegrationBeanLocal;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.DailyValuesDBHandler;
import com.witbooking.middleware.db.handlers.HotelConfigurationDBHandler;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.exceptions.AuthenticationException;
import com.witbooking.middleware.exceptions.InvalidEntryException;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.exceptions.integration.dingus.DingusException;
import com.witbooking.middleware.integration.ChannelConnectionType;
import com.witbooking.middleware.integration.ChannelQueueStatus;
import com.witbooking.middleware.integration.ChannelTicker;
import com.witbooking.middleware.integration.EntryQueueItem;
import com.witbooking.middleware.integration.dingus.model.*;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.resources.DBProperties;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 04/03/2015
 */
@Stateless
public class DingusBean implements DingusBeanLocal {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DingusBean.class);
    @EJB
    private ConnectionBeanLocal connectionBean;
    @EJB
    private IntegrationBeanLocal integrationBeanLocal;
    private static final String HAS_ONE_RATE_CHILD = "hasOneRateChild";
    private static final String JUNIORS_ENABLED = "juniorsEnabled";
    private static final String MIN_CHILD_B_AGE = "minedadnino";
    private static final String MAX_CHILD_B_AGE = "maxedadnino";

    @Override
    public GetHotelInfoResponse getHotelInfo(final GetHotelInfoRQ request) throws DingusException {
        logger.debug("getHotelInfo");
        GetHotelInfoResponse response = new GetHotelInfoResponse();
        List<String> hotelTickers;
        try {
            //Validate the credentials
            hotelTickers = validateCredentials(request.getUser(), request.getPassword(), request.getHotelCode());
        } catch (AuthenticationException e) {
            response.addErrorMessage(e.getUserMessage());
            return response;
        }
        DBConnection dBConnection = null;
        ChannelQueueStatus requestStatus = ChannelQueueStatus.FAIL;
        try {
            for (String hotelTicker : hotelTickers) {
                try {
                    dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
                    final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dBConnection);
                    GetHotelInfoResponse.Hotel hotel = new GetHotelInfoResponse.Hotel();
                    Hotel establishment = inventoryDBHandler.getHotel();
                    hotel.setName(establishment.getName());
                    hotel.setCode(hotelTicker);
                    String currency = "EUR";
                    HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dBConnection);
                    try {
                        currency = hotelConfigurationDBHandler.getDefaultCurrency();
                    } catch (Exception e) {
                        logger.error(e);
                    }
                    for (Inventory inventory : inventoryDBHandler.getInventoriesValid()) {
                        GetHotelInfoResponse.Hotel.Rooms.Room room =
                                hotel.getRooms().getRoomByCode(inventory.getAccommodation().getTicker());
                        if (room == null) {
                            room = new GetHotelInfoResponse.Hotel.Rooms.Room();
                            room.setCode(inventory.getAccommodation().getTicker());
                            room.setDescription(inventory.getAccommodation().getName());
                            hotel.addRoom(room);
                        }
                        String mealPlanCode = ConstantsDingus.MealPlan.getCodeFromTicker(inventory.getMealPlan().getTicker());
                        GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans.MealPlan mealPlan =
                                room.getMealPlans().getMealPlanByCode(mealPlanCode);
                        if (mealPlan == null) {
                            mealPlan = new GetHotelInfoResponse.Hotel.Rooms.Room.MealPlans.MealPlan();
                            mealPlan.setCode(mealPlanCode);
                            mealPlan.setDescription(inventory.getMealPlan().getName());
                            room.addMealPlan(mealPlan);
                        }
                        GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans.RatePlan ratePlan =
                                room.getRatePlans().getRatePlanByCode(inventory.getCondition().getTicker());
                        if (ratePlan == null) {
                            ratePlan = new GetHotelInfoResponse.Hotel.Rooms.Room.RatePlans.RatePlan();
                            ratePlan.setCode(inventory.getCondition().getTicker());
                            ratePlan.setDescription(inventory.getCondition().getName());
                            ratePlan.setCurrencyCode(currency);
                            room.addRatePlan(ratePlan);
                        }
                        int persons = inventory.getConfiguration().getTotalGuests();
                        int adults = inventory.getConfiguration().getAdults();
                        int children = inventory.getConfiguration().getMinors();
                        if (room.getOccupations().getMaxNumberOfPersons() == null || room.getOccupations().getMaxNumberOfPersons() < persons)
                            room.getOccupations().setMaxNumberOfPersons(persons);
                        if (room.getOccupations().getMinNumberOfPersons() == null || room.getOccupations().getMinNumberOfPersons() > persons)
                            room.getOccupations().setMinNumberOfPersons(persons);
                        if (room.getOccupations().getMaxNumberOfAdults() == null || room.getOccupations().getMaxNumberOfAdults() < adults)
                            room.getOccupations().setMaxNumberOfAdults(adults);
                        if (room.getOccupations().getMinNumberOfAdults() == null || room.getOccupations().getMinNumberOfAdults() > adults)
                            room.getOccupations().setMinNumberOfAdults(adults);
                        if (room.getOccupations().getMaxNumberOfChildren() == null || room.getOccupations().getMaxNumberOfChildren() < children)
                            room.getOccupations().setMaxNumberOfChildren(children);
                        if (room.getOccupations().getMinNumberOfChildren() == null || room.getOccupations().getMinNumberOfChildren() > children)
                            room.getOccupations().setMinNumberOfChildren(children);
                    }
                    response.addHotel(hotel);
                } catch (Exception ex) {
                    logger.error(ex);
                    response.addErrorMessage("Error in hotel:'" + hotelTicker + "' " + ex.getMessage());
                } finally {
                    DAOUtil.close(dBConnection);
                }
            }
            requestStatus = ChannelQueueStatus.SUCCESS;
        } finally {
            try {
                integrationBeanLocal.storeSingleConnection(request.getHotelCode(), ChannelTicker.DINGUS,
                        ChannelConnectionType.OFFER_INVENTORY, requestStatus, null, request, response);
            } catch (IntegrationException e) {
                logger.error(e);
            }
        }
        return response;
    }

    @Override
    public HotelAvailRateUpdateRS updateARIValues(final HotelAvailRateUpdateRQ request) throws DingusException {
        logger.debug("updateARIValues");
        HotelAvailRateUpdateRS response = new HotelAvailRateUpdateRS();
        if (request.getCredentials() == null || request.getHotelAvailRateMessages() == null || request.getHotelAvailRateMessages().getHotelCode() == null) {
            logger.debug("A credential value is null");
            response.addErrorMessage("Invalid Credential Values, It can't be null.");
            return response;
        }
        String hotelTicker = request.getHotelAvailRateMessages().getHotelCode();
        try {
            //Validate the credentials for this hotelCode
            validateCredentials(request.getCredentials().getUser(), request.getCredentials().getPassword(), hotelTicker);
        } catch (AuthenticationException e) {
            response.addErrorMessage(e.getUserMessage());
            return response;
        }
        DBConnection dBConnection = null;
        Map<String, EntryQueueItem> queueItemsMap = new HashMap<>();
        ChannelQueueStatus requestStatus = ChannelQueueStatus.FAIL;
        try {
            dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dBConnection);

            HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dBConnection);
            boolean hasOneRateChild = false;
            try {
                List<String> values = new ArrayList<>();
                values.add(HAS_ONE_RATE_CHILD);
                //if HAS_ONE_RATE_CHILD is true, we have to add the price ChildA to children Rates
                String hasOneRateChildProp = (String) hotelConfigurationDBHandler.getHotelProperties(values).get(HAS_ONE_RATE_CHILD);
                if (hasOneRateChildProp != null && hasOneRateChildProp.trim().equals("1"))
                    hasOneRateChild = true;
            } catch (Exception e) {
                logger.error(e);
            }
            for (HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage availRateMessage : request.getHotelAvailRateMessages().getHotelAvailRateMessageList()) {
                if (availRateMessage.getRoomCode() == null || availRateMessage.getRatePlanCode() == null)
                    throw new InvalidEntryException("Invalid RoomCode or RatePlanCode");
                if (availRateMessage.getToDate() == null || availRateMessage.getFromDate() == null)
                    throw new InvalidEntryException("Invalid Dates");
                Date fromDate = availRateMessage.getFromDate();
                Date toDate = availRateMessage.getToDate();
                final DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, fromDate, toDate);
                RangeValue<Integer> roomsToSell = new RangeValue<>(fromDate, toDate, availRateMessage.getRoomsToSell(), null);
                RangeValue<Integer> release = new RangeValue<>(fromDate, toDate, availRateMessage.getRelease(), null);
                RangeValue<Integer> minStay = new RangeValue<>(fromDate, toDate, availRateMessage.getMinimumStay(), null);
                RangeValue<Integer> maxStay = new RangeValue<>(fromDate, toDate, availRateMessage.getMaximumStay(), null);
                RangeValue<Boolean> closed = new RangeValue<>(fromDate, toDate, availRateMessage.getClosed(), null);
                for (HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate rate : availRateMessage.getRates().getRateList()) {
                    for (HotelAvailRateUpdateRQ.HotelAvailRateMessages.HotelAvailRateMessage.Rates.Rate.MealPlans.MealPlan mealPlan : rate.getMealPlans().getMealPlanList()) {
                        String mealPlanTicker = ConstantsDingus.MealPlan.getTickerFromCode(mealPlan.getCode());
                        if (mealPlanTicker.isEmpty())
                            response.addErrorMessage("Invalid MealPlanCode: '" + mealPlan.getCode() + "'");
                        else {
                            for (Inventory inventory : inventoryDBHandler.getInventoriesByRoomMealPlanRatePlan(availRateMessage.getRoomCode(), mealPlanTicker, availRateMessage.getRatePlanCode())) {
                                String inventoryTicker = inventory.getTicker();
                                try {
                                    if (inventory.getAvailability().isOwnValue() || inventory.getAvailability().isSharedValue()) {
                                        //Calculate the rate by person
                                        dailyValuesDBHandler.updateAvailabilityByTicker(inventoryTicker, roomsToSell);
                                        float price = 0;
                                        Configuration configuration = inventory.getConfiguration();
                                        Integer adults = configuration.getAdults();
                                        Integer children = configuration.getChildren();
                                        Integer babies = configuration.getInfants();
                                        if (adults + children + babies == 1) {
                                            //price by single use
                                            price += mealPlan.getPrice(ConstantsDingus.Price.SINGLE_USE);
                                        } else {
                                            if (adults < 3) {
                                                price += adults * mealPlan.getPrice(ConstantsDingus.Price.ADULT);
                                            } else {
                                                price += 2 * mealPlan.getPrice(ConstantsDingus.Price.ADULT);
                                                price += (adults - 2) * mealPlan.getPrice(ConstantsDingus.Price.THIRD_ADULT);
                                            }
                                            // if hasOneRateChild==TRUE, we just use price for CHILD_A for children
                                            if (hasOneRateChild) {
                                                babies = children;
                                                children = 0;
                                            }
                                            if (children > 0) {
                                                price += mealPlan.getPrice(ConstantsDingus.Price.FIRST_CHILD_B);
                                                if (children > 1)
                                                    price += (children - 1) * mealPlan.getPrice(ConstantsDingus.Price.SECOND_CHILD_B);
                                            }
                                            if (babies > 0) {
                                                price += mealPlan.getPrice(ConstantsDingus.Price.FIRST_CHILD_A);
                                                if (babies > 1)
                                                    price += (babies - 1) * mealPlan.getPrice(ConstantsDingus.Price.SECOND_CHILD_A);
                                            }
                                        }
                                        dailyValuesDBHandler.updateRatesByTicker(inventoryTicker, new RangeValue<>(fromDate, toDate, price, null));
                                    }
                                    //updating the other ARI values
                                    if (inventory.getAvailability().isOwnValue() || inventory.getAvailability().isSharedValue())
                                        dailyValuesDBHandler.updateAvailabilityByTicker(inventoryTicker, roomsToSell);
                                    if (inventory.getMinStay().isOwnValue() || inventory.getMinStay().isSharedValue())
                                        dailyValuesDBHandler.updateMinStayByTicker(inventoryTicker, minStay);
                                    if (inventory.getMaxStay().isOwnValue() || inventory.getMaxStay().isSharedValue())
                                        dailyValuesDBHandler.updateMaxStayByTicker(inventoryTicker, maxStay);
                                    if (inventory.getLock().isOwnValue() || inventory.getLock().isSharedValue())
                                        dailyValuesDBHandler.updateLocksByTicker(inventoryTicker, closed);
                                    //TODO: validate if 'RELEASE' is MinNotice or MaxNotice
                                    if (inventory.getMinNotice().isOwnValue() || inventory.getMinNotice().isSharedValue())
                                        dailyValuesDBHandler.updateMinNoticeByTicker(inventoryTicker, release);
                                    //Adding the EntryQueueItems, to the map
                                    EntryQueueItem queueItem = queueItemsMap.get(inventoryTicker);
                                    if (queueItem == null) {
                                        queueItemsMap.put(inventoryTicker, new EntryQueueItem(inventoryTicker, fromDate, toDate));
                                    } else {
                                        try {
                                            if (queueItem.getStart().after(fromDate)) {
                                                queueItem.setStart(fromDate);
                                            }
                                            if (queueItem.getEnd().before(toDate)) {
                                                queueItem.setEnd(toDate);
                                            }
                                        } catch (Exception e) {
                                            logger.error(e);
                                            queueItemsMap.put(inventoryTicker, new EntryQueueItem(inventoryTicker, fromDate, toDate));
                                        }
                                    }
                                } catch (Exception ex) {
                                    logger.error("Error updating the inventory '" + inventoryTicker + "' : " + ex);
                                    response.addErrorMessage("Error updating the inventory '" + inventoryTicker + "' : " + ex.getMessage());
                                }
                            }
                        }
                    }
                }
            }
            requestStatus = ChannelQueueStatus.SUCCESS;
            response.setSuccess();
        } catch (Exception ex) {
            logger.error(ex);
            response.addErrorMessage(ex.getMessage());
        } finally {
            try {
                integrationBeanLocal.storeSingleConnection(hotelTicker, ChannelTicker.DINGUS,
                        ChannelConnectionType.EXECUTE_UPDATE_ARI, requestStatus, new HashSet<>(queueItemsMap.values()),
                        request, response);
            } catch (IntegrationException e) {
                logger.error(e);
            }
            DAOUtil.close(dBConnection);
        }
        return response;
    }

    @Override
    public BookingRetrievalResponse bookingRetrieval(final BookingRetrievalRQ request) throws DingusException {
        BookingRetrievalResponse response = new BookingRetrievalResponse();
        response.setResponseDate(new Date());
        if (request.getHotelCode() == null) {
            logger.debug("HotelCode value is null");
            response.addErrorMessage("Invalid HotelCode Value, It can't be null.");
            return response;
        }
        String hotelTicker = request.getHotelCode();
        try {
            //Validate the credentials for this hotelCode
            validateCredentials(request.getUser(), request.getPassword(), hotelTicker);
        } catch (AuthenticationException e) {
            response.addErrorMessage(e.getUserMessage());
            return response;
        }
        DBConnection dBConnection = null;
        ChannelQueueStatus requestStatus = ChannelQueueStatus.FAIL;
        Set<EntryQueueItem> items = new HashSet<>();
        try {
            dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dBConnection);
            final ReservationDBHandler reservationDBHandler = new ReservationDBHandler(inventoryDBHandler);
            List<Reservation> reservations;
            if (request.getReservationID() != null && !request.getReservationID().isEmpty()) {
                reservations = new ArrayList<>();
                Reservation reservation = reservationDBHandler.getReservationByReservationId(request.getReservationID());
                if (reservation != null)
                    reservations.add(reservation);
            } else {
                //validate the DateTimes
                if (request.getModifiedDateFrom() == null || request.getModifiedDateTo() == null) {
                    logger.debug("DateTime is null");
                    response.addErrorMessage("Invalid DateTime Value, It can't be null.");
                    return response;
                }
                reservations = reservationDBHandler.getReservationsBetweenCreationOrModificationDates(request.getModifiedDateFrom(), request.getModifiedDateTo());
            }
            if (reservations.isEmpty()) {
                items.add(new EntryQueueItem("EMPTY", request.getModifiedDateFrom(), request.getModifiedDateTo()));
                requestStatus = ChannelQueueStatus.SUCCESS;
                return response;
            }
            Hotel establishment = inventoryDBHandler.getHotel();
            String hotelName = establishment.getName();
            String currency = "EUR";
            HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dBConnection);
            int minChildBAge=3;
            int maxChildBAge=12;
            boolean juniorEnabled=false;
            try {
                List<String> values = new ArrayList<>();
                values.add(MIN_CHILD_B_AGE);
                values.add(MAX_CHILD_B_AGE);
                values.add(JUNIORS_ENABLED);
                Properties props = hotelConfigurationDBHandler.getHotelProperties(values);
                String juniorEnabledProp = (String) props.get(JUNIORS_ENABLED);
                if (juniorEnabledProp != null && juniorEnabledProp.trim().equals("1"))
                    juniorEnabled = true;
                String minChildBAgeProp = (String) props.get(MIN_CHILD_B_AGE);
                if (minChildBAgeProp != null)
                    minChildBAge = Integer.parseInt(minChildBAgeProp);
                String maxChildBAgeProp = (String) props.get(MAX_CHILD_B_AGE);
                if (maxChildBAgeProp != null)
                    maxChildBAge = Integer.parseInt(maxChildBAgeProp);
            } catch (Exception e) {
                logger.error(e);
            }
            try {
                currency = hotelConfigurationDBHandler.getDefaultCurrency();
            } catch (Exception e) {
                logger.error(e);
            }
            for (Reservation res : reservations) {
                BookingRetrievalResponse.HotelReservations.HotelReservation hotelReservation =
                        new BookingRetrievalResponse.HotelReservations.HotelReservation();

                String statusCode = ConstantsDingus.HotelReservation.RESERVED;
                hotelReservation.setHotelCode(hotelTicker);
                hotelReservation.setHotelName(hotelName);
                hotelReservation.setCheckInDate(res.getFirstCheckIn());
                hotelReservation.setCheckOutDate(res.getLastCheckOut());
                hotelReservation.setBookingDate(res.getDateCreation());
                Date lastModifyDate = res.getDateCreation();

                //filling the Reservation info (Total and reservationID)
                BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo reservationInfo =
                        new BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo();
                reservationInfo.setReservationIDs(res.getReservationId());
                BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Total total =
                        new BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Total();
                total.setAmountAfterTax(res.getAmountAfterTax());
                total.setCurrencyCode(currency);
                reservationInfo.setTotal(total);
                //Filling the customer and creditCard info
                Customer customerRes = res.getCustomer();
                BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer customer =
                        new BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo.Customer();
                //we don't have some address fields (streetNumber, city, PostalCode)
                customer.setAddress(new BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo
                        .Customer.Address(customerRes.getAddress(), null, null, null, customerRes.getCountry()));
                customer.setLeadPax(new BookingRetrievalResponse.HotelReservations.HotelReservation.ReservationInfo
                        .Customer.LeadPax(customerRes.getGivenName(), customerRes.getSurName()));
                //Decrypting the creditCard Values
                String cardNumber;
                String expireDate;
                String seriesCode;
                try {
                    cardNumber = connectionBean.encryptionPHP(ConnectionBean.DECRYPT, customerRes.getCreditCard().getCardNumberEncrypted());
                } catch (Exception ex) {
                    cardNumber = customerRes.getCreditCard().getCardNumberEncrypted();
                    logger.error(ex);
                }
                try {
                    seriesCode = connectionBean.encryptionPHP(ConnectionBean.DECRYPT, customerRes.getCreditCard().getSeriesCodeEncrypted());
                } catch (Exception ex) {
                    seriesCode = customerRes.getCreditCard().getSeriesCodeEncrypted();
                    logger.error(ex);
                }
                try {
                    expireDate = connectionBean.encryptionPHP(ConnectionBean.DECRYPT, customerRes.getCreditCard().getExpireDateEncrypted())
                            .replaceAll("/", "");
                } catch (Exception ex) {
                    expireDate = customerRes.getCreditCard().getExpireDateEncrypted() + "";
                    logger.error(ex);
                }
                customer.setPaymentForm(new BookingRetrievalResponse.HotelReservations.HotelReservation
                        .ReservationInfo.Customer.PaymentForm(customerRes.getCreditCard().getCardHolderName(),
                        customerRes.getCreditCard().getCardOTACode(), cardNumber,
                        seriesCode, expireDate));
                reservationInfo.setCustomer(customer);
                hotelReservation.setReservationInfo(reservationInfo);

                List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room> confirmRes =
                        new ArrayList<>();
                List<BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room> cancelledRes =
                        new ArrayList<>();
                String comments = "";
                //loop for all the roomStays
                for (RoomStay roomStay : res.getRoomStays()) {
                    Inventory inventory = inventoryDBHandler.getInventoryByTicker(roomStay.getInventoryTicker());
                    BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room room =
                            new BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room();

                    //the reservation comments are in the SpecialRequest
                    if (roomStay.getComments() != null && !roomStay.getComments().equals(comments)) {
                        comments = roomStay.getComments();
                        hotelReservation.addSpecialRequest(comments);
                    }

                    room.setCheckInDate(roomStay.getDateCheckIn());
                    room.setCheckOutDate(roomStay.getDateCheckOut());
                    room.setMealPlanCode(ConstantsDingus.MealPlan.getCodeFromTicker(inventory.getMealPlan().getTicker()));
                    room.setMealPlanDescription(inventory.getMealPlan().getName());
                    room.setNumberOfAdults(inventory.getConfiguration().getAdults());
                    if(juniorEnabled){
                        room.setNumberOfBabies(0);
                        room.setNumberOfChildren(inventory.getConfiguration().getChildren() + inventory.getConfiguration().getInfants());
                    }else{
                        room.setNumberOfBabies(inventory.getConfiguration().getInfants());
                        room.setNumberOfChildren(inventory.getConfiguration().getChildren());
                    }
                    room.setNumberOfGuests(inventory.getConfiguration().getTotalGuests());
                    room.setRatePlanCode(inventory.getCondition().getTicker());
                    room.setRatePlanDescription(inventory.getCondition().getName());
                    room.setRoomCode(inventory.getAccommodation().getTicker());
                    room.setRoomDescription(inventory.getAccommodation().getName());

                    //Adding generic name for all guest, as Dingus required
                    //in our model we just have name for one guest
                    room.addGuest(new BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests
                            .Guest(null, customerRes.getGivenName(), customerRes.getSurName(), 30));
                    for (int i = 2; i <= inventory.getConfiguration().getAdults(); i++) {
                        room.addGuest(new BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests
                                .Guest(null, "Adult " + i, "Adult " + i, 30));
                    }
                    for (int i = 1; i <= inventory.getConfiguration().getChildren(); i++) {
                        room.addGuest(new BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests
                                .Guest(null, "ChildB " + i, "ChildB " + i, maxChildBAge));
                    }
                    for (int i = 1; i <= inventory.getConfiguration().getInfants(); i++) {
                        room.addGuest(new BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.Guests
                                .Guest(null, "ChildA " + i, "ChildA " + i, minChildBAge-1));
                    }

                    //filling the prices for each day
                    Map<Date, Float> dailyPrices = roomStay.getRoomRates().getMapValuesForEachDay();
                    //TODO: ask if required the complete dailyPrice (with the discounts and extras)
                    for (Date date : dailyPrices.keySet()) {
                        room.addDailyRate(new BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room
                                .Pricing.DailyRates.DailyRate(date, dailyPrices.get(date), currency));
                    }
                    //filling the total price for the room
                    room.getPricing().setRoomTotals(
                            new BookingRetrievalResponse.HotelReservations.HotelReservation.Rooms.Room.
                                    Pricing.RoomTotals(roomStay.getTotalAmount(), currency));

                    if (roomStay.getCancellationDate() != null && lastModifyDate.before(roomStay.getCancellationDate())) {
                        statusCode = ConstantsDingus.HotelReservation.AMENDED;
                        lastModifyDate = roomStay.getCancellationDate();
                    } else if (roomStay.getDateModification() != null && lastModifyDate.before(roomStay.getDateModification())) {
                        statusCode = ConstantsDingus.HotelReservation.AMENDED;
                        lastModifyDate = roomStay.getDateModification();
                    }
                    //adding the reservation to the right list
                    if (roomStay.getStatus() == Reservation.ReservationStatus.CANCEL)
                        cancelledRes.add(room);
                    else
                        confirmRes.add(room);

                    for (ServiceRequested service : roomStay.getServices()) {
                        JsonObject jsonValue = new JsonObject();
                        jsonValue.addProperty("roomStayID", roomStay.getIdConfirmation());
                        jsonValue.addProperty("serviceID", service.getServiceTicker());
                        jsonValue.addProperty("total", (Math.round(service.getTotalServiceAmount() * 1000) / 1000));
                        jsonValue.addProperty("currency", currency);
                        hotelReservation.addAdditionalInfo(new BookingRetrievalResponse.HotelReservations.HotelReservation
                                .AdditionalInfoList.AdditionalInfo(jsonValue + "", ConstantsDingus.HotelReservation.SERVICE_CODE));
                    }
                    for (DiscountApplied discount : roomStay.getDiscounts()) {
                        JsonObject jsonValue = new JsonObject();
                        jsonValue.addProperty("roomStayID", roomStay.getIdConfirmation());
                        jsonValue.addProperty("discountID", discount.getDiscountTicker());
                        jsonValue.addProperty("total", Math.abs(Math.round(discount.getTotalDiscountAmount() * 1000) / 1000));
                        jsonValue.addProperty("currency", currency);
                        hotelReservation.addAdditionalInfo(new BookingRetrievalResponse.HotelReservations.HotelReservation
                                .AdditionalInfoList.AdditionalInfo(jsonValue + "", ConstantsDingus.HotelReservation.DISCOUNT_CODE));

                    }
                }
                //if all the reservations are cancelled, we have to send all. if not, we sent just the active reservations
                if (res.getRoomStays().size() == cancelledRes.size()) {
                    statusCode = ConstantsDingus.HotelReservation.CANCELLED;
                    hotelReservation.addRoomList(cancelledRes);
                } else {
                    hotelReservation.addRoomList(confirmRes);
                }
                hotelReservation.setResStatus(statusCode);
                hotelReservation.setLastModifyDateTime(lastModifyDate);
                response.addHotelReservation(hotelReservation);
                //adding the reservation to the EntryQueueItems
                items.add(new EntryQueueItem(res.getReservationId(), request.getModifiedDateFrom(), request.getModifiedDateTo()));
            }
            requestStatus = ChannelQueueStatus.SUCCESS;
        } catch (Exception ex) {
            logger.error(ex);
            response.addErrorMessage(ex.getMessage());
        } finally {
            try {
                integrationBeanLocal.storeSingleConnection(hotelTicker, ChannelTicker.DINGUS,
                        ChannelConnectionType.NOTIFY_RESERVES, requestStatus, items, request, response);
            } catch (IntegrationException e) {
                logger.error(e);
            }
            DAOUtil.close(dBConnection);
        }
        return response;
    }


    /**
     * Performs the credentials validation of each request
     *
     * @return <code>null</code> if don't find any error, otherwise a new {@link com.witbooking.middleware.integration.dingus.model.ErrorType}
     */
    private List<String> validateCredentials(final String username, final String password, final String hotelTicker) throws AuthenticationException {
        logger.debug("validateCredentials");
        if (username == null || password == null || username.trim().isEmpty() || password.trim().isEmpty()) {
            logger.debug("A credential value is null");
            throw new AuthenticationException("Invalid Credential Values, It can't be null.");
        }
        try {
            logger.debug("user: '" + username + "' is trying this service.");
            final String hotelTickersList = connectionBean.getHotelTickers(username, password);
//            logger.debug(hotelTickersList);
            if (hotelTickersList == null || hotelTickersList.trim().equals("-1")) {
                throw new AuthenticationException(ConstantsDingus.Error.AUTHENTICATION);
            }
            final JsonParser jsonParser = new JsonParser();
            final JsonElement jsonResponse = jsonParser.parse(hotelTickersList);
            if (jsonResponse == null || jsonResponse.isJsonNull() || !jsonResponse.isJsonArray()) {
                //This case means an internal error.  there was an error in the authentication.
                throw new AuthenticationException(ConstantsDingus.Error.AUTHENTICATION);
            } else {
                List<String> hotelList = new ArrayList<>();
                //This case means that the users are perfectly authentic.
                for (JsonElement jsonElement : jsonResponse.getAsJsonArray()) {
                    if (jsonElement != null && !jsonElement.isJsonNull()) {
                        if (hotelTicker != null) {
                            if (jsonElement.getAsString().trim().equalsIgnoreCase(hotelTicker)) {
                                logger.debug("Access granted for the hotel: " + hotelTicker);
                                hotelList.add(jsonElement.getAsString());
                                return hotelList;
                            }
                        } else {
                            hotelList.add(jsonElement.getAsString());
                        }
                    }
                }
                if (hotelTicker != null && hotelList.isEmpty())
                    throw new AuthenticationException("This username doesn't have permissions on the hotel: '" + hotelTicker + "'");
                return hotelList;
            }
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            logger.error("Exception to validating the credentials: '" + username + "'" + ex, ex);
            throw new AuthenticationException(ex + " Error handling the authentication");
        }
    }
}
