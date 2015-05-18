/*
 *  ReservationDBHandler.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.db.handlers;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.sqlinstructions.SQLInstructions;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.integration.mandrill.model.Event;
import com.witbooking.middleware.integration.mandrill.model.Message;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.model.values.RateDataValue;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.JsonUtils;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 28-feb-2013
 */
public class ReservationDBHandler extends DBHandler {

    /**
     * Object used to report on the Server's Log using Apache log4j.
     */
    private static final Logger logger = Logger.getLogger(ReservationDBHandler.class);
    //    private final Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    private InventoryDBHandler inventoryDBHandler;
    /**
     * Number of attributes of reservation table.
     */
    private static final int NUMBER_ATTRIBUTES = 55;
    /**
     * Constant used to recognize whether the type is insert.
     */
    private static final boolean IS_INSERT = true;
    /**
     * Constant used to recognize whether the type is update.
     */
    private static final boolean IS_UPDATE = false;
    //

    /**
     * Creates a new instance of
     * <code>ReservationDBHandler</code> without params.
     */
    public ReservationDBHandler() {
        super();
    }

    public ReservationDBHandler(DBConnection dbConnection) {
        super(dbConnection);
    }

    public ReservationDBHandler(InventoryDBHandler inventoryDBHandler) {
        super(inventoryDBHandler.getDbConnection());
        this.inventoryDBHandler = inventoryDBHandler;
    }

    public InventoryDBHandler getInventoryDBHandler() {
        return inventoryDBHandler;
    }

    public void setInventoryDBHandler(InventoryDBHandler inventoryDBHandler) {
        this.inventoryDBHandler = inventoryDBHandler;
    }

/*
    public int[] updateReservationEmailInfo(List<Event> eventList) throws DBAccessException {
        if (eventList == null) {
            return new int[]{};
        }
        final String userSqlCommand = SQLInstructions.ReservationDBHandler.UPDATE_USER_RESERVATION_EMAIL_INFO + " ; ";
        final PreparedStatement userStatement = prepareStatement(userSqlCommand);

        final String hotelSqlCommand = SQLInstructions.ReservationDBHandler.UPDATE_HOTEL_RESERVATION_EMAIL_INFO + " ; ";
        final PreparedStatement hotelStatement = prepareStatement(userSqlCommand);
        boolean hasHotelConfirmations=false;
        boolean hasUserConfirmations=false;
        for (Event event : eventList) {
            if(event.getMessage().getTags().contains(Message.MESSAGE_TAG_HOTEL_CONFIRMATION)){
                addBatch(userStatement,  Arrays.asList("1",event.get_id()) );
                hasHotelConfirmations=true;
            }else{
                addBatch(hotelStatement,  Arrays.asList("1",event.get_id()) );
                hasUserConfirmations=true;
            }
        }
        int[] totalUpdated=null;
        int[] totalUpdatedHotel=null;
        int[] totalUpdatedUser=null;
        if(hasHotelConfirmations){
            totalUpdatedHotel = executeBatch(hotelStatement);
            DAOUtil.close(hotelStatement);
        }
        if(hasUserConfirmations){
            totalUpdatedUser = executeBatch(userStatement);
            DAOUtil.close(userStatement);
        }
        if(hasHotelConfirmations && hasUserConfirmations){
            totalUpdated = Arrays.copyOf(totalUpdatedHotel, totalUpdatedHotel.length + totalUpdatedUser.length);
            System.arraycopy(totalUpdatedUser, 0, totalUpdated, totalUpdatedHotel.length, totalUpdatedUser.length);
        }else{
            totalUpdated=totalUpdatedHotel==null?totalUpdatedUser:totalUpdatedHotel;
        }

        return totalUpdated;
    }
*/

    public int updateReservationEmailInfo(EmailData emailData) throws DBAccessException {

        if (emailData == null) {
            return -1;
        }
        PreparedStatement statement;
        String query = null;
        if (emailData.getMessageType().equals(Message.MessageType.HOTEL_CONFIRMATION)) {
            query = SQLInstructions.ReservationDBHandler.UPDATE_HOTEL_RESERVATION_EMAIL_INFO;
        } else if (emailData.getMessageType().equals(Message.MessageType.USER_CONFIRMATION)) {
            query = SQLInstructions.ReservationDBHandler.UPDATE_USER_RESERVATION_EMAIL_INFO;
        } else {
            logger.error("Invalid Message Type for EmailData " + emailData.getMessageType());
        }

        ArrayList<Object> values = new ArrayList<Object>();
        String emailDataStatus = emailData.getLastEmailStatus().getValue();

        values.add(emailDataStatus);
        values.add(emailData.getReservationID());

        statement = prepareStatement(query, values);

        int totalInserted = executeUpdate(statement);
        DAOUtil.close(statement);
        return totalInserted;
    }

    public int updateReservationEmailInfo(EmailData clientEmailData, EmailData hotelEmailData) throws DBAccessException {

        if (clientEmailData == null && hotelEmailData == null) {
            return -1;
        }
        PreparedStatement statement;

        ArrayList<Object> values = new ArrayList<Object>();
        String clientEmailDataStatus = Event.EventType.INVALID.getValue();
        String hotelEmailDataStatus = Event.EventType.INVALID.getValue();
        if (clientEmailData != null && clientEmailData.getLastEmailStatus() != null) {
            clientEmailDataStatus = clientEmailData.getLastEmailStatus().getValue();
        }
        if (hotelEmailData != null && hotelEmailData.getLastEmailStatus() != null) {
            hotelEmailDataStatus = hotelEmailData.getLastEmailStatus().getValue();
        }

        values.add(clientEmailDataStatus);
        values.add(hotelEmailDataStatus);
        values.add(clientEmailData.getReservationID());

        statement = prepareStatement(SQLInstructions.ReservationDBHandler.UPDATE_RESERVATION_EMAIL_INFO, values);

        int totalInserted = executeUpdate(statement);
        DAOUtil.close(statement);
        return totalInserted;
    }

    private List<Reservation> getReservations(String sqlCommand, List values) throws DBAccessException {
        PreparedStatement statement = prepareStatement(sqlCommand, values);
        ResultSet resultSet = execute(statement);
        List<Reservation> ret = getReservationsFromResultSet(resultSet);
//        for (int j = 0; j < ret.size(); j++) {
//            final  
//            for (int i = j; i < ret.size(); i++) {
//                if (item != ret.get(i)) {
//                    if (item.getReservationId() != null &&) {
//                        
//                    }
//                }
//            }
//        }
        DAOUtil.close(statement, resultSet);
        return ret;
    }

    //reserved
    public List<Reservation> getReservationByInventoriesOccupied(Date startDate, Date endDate, List<Integer> inventoryIds)
            throws DBAccessException {
        String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION
                + "WHERE reservas.tiposhabs_id in (";

        for (int i = 0; i < inventoryIds.size(); i++) {
            if (i > 0) {
                sqlCommand += ",  ";
            }
            sqlCommand += inventoryIds.get(i);
        }
        sqlCommand += ")  "
                + "AND (? BETWEEN reservas.fechaentrada AND DATE_ADD(reservas.fechasalida, INTERVAL -1 DAY) "
                + "OR reservas.fechaentrada BETWEEN ? AND ?) "
                + "AND estado = 'reserva' "
                + "ORDER BY reservas.fechaentrada ASC; ";
        final List<java.sql.Date> values = new ArrayList<>();
        values.add(new java.sql.Date(startDate.getTime()));
        values.add(new java.sql.Date(startDate.getTime()));
        values.add(new java.sql.Date(endDate.getTime()));
        return getReservations(sqlCommand, values);
    }

    public List<Reservation> getReservationsBetweenCreationDates(Date startDate, Date endDate)
            throws DBAccessException {
        final String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION
                + "WHERE reservas.timestamp BETWEEN ? AND ? "
                + "ORDER BY reservas.timestamp ASC; ";
        if (endDate == null) {
            endDate = new Date();
        }
        final List<java.sql.Timestamp> values = new ArrayList<>();
        values.add(new java.sql.Timestamp(startDate.getTime()));
        values.add(new java.sql.Timestamp(endDate.getTime()));
        return getReservations(sqlCommand, values);
    }

    public List<Reservation> getReservationsBetweenCreationOrModificationDates(Date startDate, Date endDate)
            throws DBAccessException {
        final String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION
                + "WHERE reservas.timestamp BETWEEN ? AND ? "
                + "OR reservas.ultimamodificacion BETWEEN ? AND ? "
                + "OR reservas.f_cancelacion BETWEEN ? AND ? "
                + "ORDER BY ultima_fecha ASC; ";
        final java.sql.Timestamp starTime = new java.sql.Timestamp(startDate.getTime());
        final java.sql.Timestamp endTime = new java.sql.Timestamp(endDate.getTime());
        java.sql.Timestamp[] values = new java.sql.Timestamp[]{starTime, endTime, starTime, endTime, starTime, endTime};
        return getReservations(sqlCommand, Arrays.asList(values));
    }

    public List<Reservation> getReservationsByInventoryBetweenCreationDates(int inventoryId, Date startDate, Date endDate)
            throws DBAccessException {
        final String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION
                + "WHERE reservas.timestamp BETWEEN ? AND ? "
                + "AND reservas.tiposhabs_id=? "
                + "ORDER BY reservas.fechaentrada ASC;";
        final List<Object> values = new ArrayList<>();

        values.add(new java.sql.Timestamp(startDate.getTime()));
        values.add(new java.sql.Timestamp(endDate.getTime()));
        values.add(inventoryId);
        return getReservations(sqlCommand, values);
    }

    public List<Reservation> getReservationsByInventoryBetweenCheckInDates(int inventoryId, Date startDate, Date endDate)
            throws DBAccessException {
        final String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION
                + "WHERE reservas.fechaentrada BETWEEN ? AND ? "
                + "AND reservas.tiposhabs_id=? "
                + "ORDER BY reservas.fechaentrada ASC; ";
        final List<Object> values = new ArrayList<>();
        values.add(new java.sql.Date(startDate.getTime()));
        values.add(new java.sql.Date(endDate.getTime()));
        values.add(inventoryId);
        return getReservations(sqlCommand, values);
    }

    public List<Reservation> getReservationsBetweenCheckInDates(Date startDate, Date endDate)
            throws DBAccessException {
        final String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION
                + "WHERE reservas.fechaentrada BETWEEN ? AND ? "
                + "ORDER BY reservas.fechaentrada ASC;";
        final List<java.sql.Date> values = new ArrayList<>();
        values.add(new java.sql.Date(startDate.getTime()));
        values.add(new java.sql.Date(endDate.getTime()));
        return getReservations(sqlCommand, values);
    }

    public List<Reservation> getReservationsBetweenCheckOutDates(Date startDate, Date endDate)
            throws DBAccessException {
        final String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION
                + "WHERE reservas.fechasalida BETWEEN ? AND ? "
                + "ORDER BY reservas.fechaentrada ASC;";

        final List<java.sql.Date> values = new ArrayList<>();
        values.add(new java.sql.Date(startDate.getTime()));
        values.add(new java.sql.Date(endDate.getTime()));
        return getReservations(sqlCommand, values);
    }

    public Reservation getReservationByReservationId(String reservationId)
            throws DBAccessException {
        final String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION
                + "WHERE reservas.idgeneradomulti=?;";
        final List<Reservation> reservation = getReservations(sqlCommand, Arrays.asList(reservationId));
        return reservation != null && !reservation.isEmpty() ? reservation.get(0) : null;
    }

    public List<Reservation> getReservationListByReservationIds(List<String> reservationIds) throws DBAccessException {
        if (reservationIds == null || reservationIds.isEmpty()) {
            logger.error("Empty Reservations to Update: " + reservationIds);
            return new ArrayList<>();
        }
        String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION
                + " WHERE reservas.idgeneradomulti IN (";
        for (int i = 0; i < reservationIds.size(); i++) {
            if (i > 0) {
                sqlCommand += ", ";
            }
            sqlCommand += "?";

        }
        sqlCommand += ");";
        return getReservations(sqlCommand, reservationIds);
    }

    public List<Reservation> getReservationsByStatus(Reservation.ReservationStatus status, byte paymentStatus) throws DBAccessException {
        final String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION
                + "WHERE reservas.estado=? "
                + "AND reservas.estadoampliado=? "
                + "ORDER BY reservas.timestamp ASC; ";
        final List<String> values = new ArrayList<>();
        values.add(status.getStringValue());
        values.add(paymentStatus + "");
        return getReservations(sqlCommand, values);
    }

    public Map<String, String> getReservationCodes(String reservationId, String roomStayId)
            throws DBAccessException {
        final String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION_CODES
                + "WHERE reservas.idgeneradomulti=? OR reservas.idgenerado=?;";
        PreparedStatement statement = prepareStatement(sqlCommand, Arrays.asList(new String[]{reservationId, roomStayId}));
        ResultSet resultSet = execute(statement);
        Map<String, String> mapCodes = new HashMap<>(2);
        mapCodes.put("reservationId", reservationId);
        mapCodes.put("roomStayId", roomStayId);
        if (resultSet == null) {
            return mapCodes;
        }
        while (next(resultSet)) {
            String mCode = getString(resultSet, 1);
            String pCode = getString(resultSet, 2);
            if (mCode != null && mCode.trim().equals(reservationId))
                mapCodes.put("reservationId", null);
            if (pCode != null && pCode.trim().equals(roomStayId))
                mapCodes.put("roomStayId", null);
        }
        DAOUtil.close(statement, resultSet);
        return mapCodes;
    }

    public List<Reservation> getReservationsByRoomStayIdConfirmation(String idConfirmation)
            throws DBAccessException {
        final String sqlCommand = SQLInstructions.ReservationDBHandler.SELECT_RESERVATION
                + "WHERE reservas.idgenerado=?;";
        return getReservations(sqlCommand, Arrays.asList(idConfirmation));
    }

    public int cancelReservationByReservationId(String reservationId)
            throws DBAccessException {
        List<String> values = new ArrayList<>();
        values.add(SQLInstructions.ReservationDBHandler.VAL_CANCEL_ATT_STATUS_TBL_RESERVATION);
        values.add(reservationId);
        PreparedStatement statement = prepareStatement(SQLInstructions.ReservationDBHandler.UPDATE_CANCELLATION_BY_ID_CONFIRMATION, values);
        final int updatedEntries = executeUpdate(statement);
        DAOUtil.close(statement);
        return updatedEntries;
    }

    public int cancelRoomStayByIdConfirmation(String roomStayIdConfirmation)
            throws DBAccessException {
        List<String> values = new ArrayList<>();
        values.add(SQLInstructions.ReservationDBHandler.VAL_CANCEL_ATT_STATUS_TBL_RESERVATION);
        values.add(roomStayIdConfirmation);
        PreparedStatement statement = prepareStatement(SQLInstructions.ReservationDBHandler.UPDATE_CANCELLATION_BY_ROOM_STAY_ID_CONFIRMATION, values);
        final int updatedEntries = executeUpdate(statement);
        DAOUtil.close(statement);
        return updatedEntries;
    }

    public int updateCreditCardBeforeDate(String ccNumber, String ccValid, String ccCCVCode, Date lastDate)
            throws DBAccessException {
        List<Object> values = new ArrayList<>();
        values.add(ccNumber);
        values.add(ccValid);
        values.add(ccCCVCode);
        values.add(new java.sql.Date(lastDate.getTime()));
        PreparedStatement statement = prepareStatement(SQLInstructions.ReservationDBHandler.UPDATE_CREDIT_CARD, values);
        final int updatedEntries = executeUpdate(statement);
        DAOUtil.close(statement);
        return updatedEntries;
    }

    public int[] updateReservations(List<Reservation> reservationList) throws DBAccessException {
        if (reservationList == null) {
            return new int[]{};
        }
        final String sqlCommand = SQLInstructions.ReservationDBHandler.UPDATE_RESERVATION_BY_ROOM_STAY_ID_CONFIRMATION + " ; ";
        final PreparedStatement statement = prepareStatement(sqlCommand);
        for (Reservation reservation : reservationList) {
            for (RoomStay roomStay : reservation.getRoomStays()) {
                List values = getInsertOrUpdateValues(reservation, roomStay, IS_UPDATE);
                values.remove(0);
                addBatch(statement, values);
            }
        }
        int[] totalUpdated = executeBatch(statement);
        DAOUtil.close(statement);
        return totalUpdated;
    }

    public int insertReservations(List<Reservation> reservationList)
            throws DBAccessException {
        if (reservationList == null || reservationList.isEmpty()) {
            return 0;
        }
        String sqlCommand = SQLInstructions.ReservationDBHandler.INSERT_RESERVATION;
        final PreparedStatement statement = prepareStatement(sqlCommand);
        for (Reservation reservation : reservationList) {
            for (RoomStay roomStay : reservation.getRoomStays()) {
                List<Object> values = getInsertOrUpdateValues(reservation, roomStay, IS_INSERT);
                if (!values.isEmpty()) {
                    addBatch(statement, values);
                }
            }
        }
        int[] totalInserted = executeBatch(statement);
        DAOUtil.close(statement);
        int sum = 0;
        for (int i : totalInserted)
            sum += i;
        return sum;
    }

    protected String generateJSONResumen(RoomStay roomStay,
                                         String reservationCurrency, String customerAddress,
                                         String customerCountry, String customerPersonalId, String reservationComment) {
        //Create the JSON "resumen"
        JsonObject resumenJson = new JsonObject();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        Date dateIterator = (Date) roomStay.getRoomRates().getRangeStartDate().clone();
        JsonArray ratesJson = new JsonArray();
        while (DateUtil.dateBetweenDaysRange(dateIterator, roomStay.getRoomRates().getRangeStartDate(), roomStay.getRoomRates().getRangeEndDate())) {
            JsonObject dayRateJson = new JsonObject();
            float rate = roomStay.getRoomRates().getValueForADate(dateIterator);
            //This is to print the JSON without decimals, if the rate is an integer 
            if (rate == (int) rate) {
                dayRateJson.addProperty(dateFormat.format(dateIterator), (int) rate);
            } else {
                dayRateJson.addProperty(dateFormat.format(dateIterator), rate);
            }
            ratesJson.add(dayRateJson);
            DateUtil.incrementDays(dateIterator, 1);
        }
        resumenJson.add("pricesdetailedinfo", ratesJson);
        resumenJson.addProperty("activecurrency", reservationCurrency);
        resumenJson.addProperty("taxes", 0);

        JsonArray services = new JsonArray();
        if (roomStay.getServices() != null) {
            for (ServiceRequested serviceRequested : roomStay.getServices()) {
                int quantity = serviceRequested.getQuantity();
                if (quantity > 0) {
                    JsonObject service = new JsonObject();
                    service.addProperty("id", serviceRequested.getServiceId() + "");
                    service.addProperty("name", serviceRequested.getServiceName());
                    service.addProperty("codigo", serviceRequested.getServiceTicker());
                    service.addProperty("xdia", serviceRequested.isDaily() ? "1" : "0");
                    service.addProperty("xpersona", serviceRequested.getType() == 1 ? "1" : "0");
                    service.addProperty("eleccionnumerica", serviceRequested.getType() == 2 ? "1" : "0");
                    service.addProperty("computedprice", serviceRequested.getTotalServiceAmount());
                    service.addProperty("ftdcomputedprice", serviceRequested.getTotalServiceAmount() + "");
                    service.addProperty("mnumselect", quantity);
                    service.addProperty("precio", serviceRequested.getTotalServiceAmount() / quantity);
                    services.add(service);
                }
            }
        }
        JsonArray discounts = new JsonArray();
        if (roomStay.getDiscounts() != null) {
            for (DiscountApplied discountApplied : roomStay.getDiscounts()) {
                JsonObject discount = new JsonObject();
                discount.addProperty("id", discountApplied.getDiscountId() + "");
                discount.addProperty("name", discountApplied.getDiscountName());
                discount.addProperty("codigo", discountApplied.getDiscountTicker());
                discount.addProperty("computedprice", discountApplied.getTotalDiscountAmount() + "");
                if (discountApplied.isPercentage()) {
                    discount.addProperty("precio", (String) null);
                    discount.addProperty("porcentaje", "-" + Math.abs(discountApplied.getReduction()));
                } else {
                    discount.addProperty("precio", "-" + Math.abs(discountApplied.getReduction()));
                    discount.addProperty("porcentaje", (String) null);
                }
                //fechaspromocionaplicable
                JsonObject promoDailyValue = new JsonObject();
                Date dateIteratorPromo = (Date) discountApplied.getDiscountPrice().getRangeStartDate().clone();
                DecimalFormat df = new DecimalFormat("#.00");
                df.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.US));
                while (DateUtil.dateBetweenDaysRange(dateIteratorPromo,
                        discountApplied.getDiscountPrice().getRangeStartDate(), discountApplied.getDiscountPrice().getRangeEndDate())) {
                    Float rate = discountApplied.getDiscountPrice().getValueForADate(dateIteratorPromo);
                    if (rate != null) {
                        promoDailyValue.addProperty(dateFormat.format(dateIteratorPromo), df.format(rate));
                    }
                    DateUtil.incrementDays(dateIteratorPromo, 1);
                }
                discount.add("fechaspromocionaplicable", promoDailyValue);
                discount.addProperty("selected", true);
                discounts.add(discount);
            }
        }

        JsonArray bookingPriceRules = null;
        try {
            bookingPriceRules = (JsonArray) JsonUtils.gsonInstance().toJsonTree(roomStay.getBookingPriceRules());
        } catch (Exception e) {
            logger.error("Could not serialize booking price rules in reservation " + e.getMessage());
        }

        resumenJson.add("selectedextrasdetails", services);
        resumenJson.add("selectedpromosdetails", discounts);
        if (bookingPriceRules != null) {
            resumenJson.add("bookingPriceRules", bookingPriceRules);
        }

        //The Guest for the reservation for OTA integrations
        if (roomStay.getGuestList() != null && !roomStay.getGuestList().isEmpty()) {
            JsonArray guests = new JsonArray();
            for (ResGuest resGuest : roomStay.getGuestList()) {
                JsonObject guest = new JsonObject();
                guest.addProperty("guestRPH", resGuest.getId());
                guest.addProperty("name", resGuest.getName());
                guests.add(guest);
            }
            resumenJson.add("guests", guests);
        }

        //The additionalRequests for the reservation for OTA integrations
        if (roomStay.getAdditionalRequests() != null && !roomStay.getAdditionalRequests().isEmpty()) {
            JsonObject additionalRequests = new JsonObject();
            for (Map.Entry<String, String> request : roomStay.getAdditionalRequests().entrySet()) {
                additionalRequests.addProperty(request.getKey(), request.getValue());
            }
            resumenJson.add("additionalRequests", additionalRequests);
        }
        //Comments for the reservations
        String comments = "";
        if (reservationComment == null && roomStay.getComments() != null)
            comments = roomStay.getComments().trim();
        else if (reservationComment != null && roomStay.getComments() == null)
            comments = reservationComment.trim();
        else if (reservationComment != null /*&& roomStay.getComments() != null*/)
            comments = reservationComment.trim() + " - " + roomStay.getComments().trim();
        resumenJson.addProperty("observations", comments);

        if (customerAddress != null)
            resumenJson.addProperty("direccion", customerAddress);
        if (customerCountry != null)
            resumenJson.addProperty("pais", customerCountry);
        if (customerPersonalId != null)
            resumenJson.addProperty("dni", customerPersonalId);
        //TODO: validate in the PHP, if I can delete these useless fields in the JSON
        resumenJson.addProperty("suscripcion", "0");
        resumenJson.addProperty("idregimen", "");
        resumenJson.addProperty("tarifasregimen", "");
        resumenJson.addProperty("edadesninos", "[]");
        return resumenJson.toString();
    }

    /**
     * Get all values to enter in the query.
     *
     * @param reservation ReservationRS to insert or update.
     * @param isInsert    <code>true</code> if the query will be * * * * * *
     *                    insert, <code>false</code> is the query will be update.
     * @return
     */
    protected List<Object> getInsertOrUpdateValues(Reservation reservation, RoomStay roomStay, final boolean isInsert) {
        //List values = new ArrayList();
        Customer customer = reservation.getCustomer();
        CreditCard creditCard = customer.getCreditCard();
        //for (RoomStay roomStay : reservation.getRoomStays()) {
        final String resumen = generateJSONResumen(roomStay,
                reservation.getCurrency(),
                customer.getAddress(),
                customer.getCountry(),
                customer.getPersonalId(),
                reservation.getComments());
        final List<Object> roomValues = getList(isInsert);
        //have to remove the first- no update the DataBase ID
        roomValues.set(0, roomStay.getId());
        roomValues.set(1, (roomStay.getDateCheckIn() == null
                ? null
                : new java.sql.Date(roomStay.getDateCheckIn().getTime())));
        roomValues.set(2, (roomStay.getDateCheckOut() == null
                ? null
                : new java.sql.Date(roomStay.getDateCheckOut().getTime())));
        roomValues.set(3, customer.getCompleteName());
        roomValues.set(4, customer.getEmail());
        roomValues.set(5, customer.getTelephone());

        //TODO: if (estado='cancelada') -> set cancel values
        if (roomStay.getStatus() != null)
            roomValues.set(6, roomStay.getStatus().getStringValue());
        else
            roomValues.set(6, reservation.getStatus().getStringValue());
        roomValues.set(7, reservation.getPaymentStatus());
        roomValues.set(8, roomStay.getQuantity());
        roomValues.set(9, roomStay.getNights());
        roomValues.set(10, roomStay.getCapacity());
        roomValues.set(11, roomStay.getBabies());
        roomValues.set(12, roomStay.getTotalAmount());
        roomValues.set(13, resumen);
        roomValues.set(14, reservation.getReservationId());
        roomValues.set(15, roomStay.getIdConfirmation());
        roomValues.set(16, creditCard.getCardNumberEncrypted());
        roomValues.set(17, creditCard.getCardHolderName());
        roomValues.set(18, creditCard.getExpireDateEncrypted());
        roomValues.set(19, creditCard.getCardCode());
        roomValues.set(20, creditCard.getSeriesCodeEncrypted());
        roomValues.set(21,
                (reservation.getDateCreation() == null
                        ? new java.sql.Timestamp(new Date().getTime())
                        : new java.sql.Timestamp(reservation.getDateCreation().getTime()))
        );
        roomValues.set(23, roomStay.getInventoryId());
        roomValues.set(25, reservation.getChannelAddress());
        roomValues.set(26, reservation.getChannelId());
        //importepago
        roomValues.set(28, roomStay.getGuaranteePercentage());
        roomValues.set(29, roomStay.getGuaranteeAmount());
        //sistemapago_id 30
        roomValues.set(31, customer.getIpOrder());
        roomValues.set(32, reservation.isGoogleAnalyticsReported());
        roomValues.set(33, reservation.getLanguage());
        roomValues.set(34, customer.getCountry());
        roomValues.set(35, (roomStay.getDateModification() == null
                ? null
                : new java.sql.Timestamp(roomStay.getDateModification().getTime())));
        roomValues.set(36, reservation.getAgentId());
        roomValues.set(37, roomStay.getCancellationCause());
        roomValues.set(38, (roomStay.getCancellationDate() == null
                ? null
                : new java.sql.Timestamp(roomStay.getCancellationDate().getTime())));
        roomValues.set(39, customer.isMailOption());
        roomValues.set(40, (reservation.getEmailPostStayDate() == null
                ? null
                : new java.sql.Date(reservation.getEmailPostStayDate().getTime())));
        roomValues.set(41, reservation.isReported());
        roomValues.set(42, roomStay.getCanceledByClient());
        //control_confirmadas 43
        roomValues.set(48, reservation.getCodeApplied());
        roomValues.set(49, reservation.getTrackingId());
        roomValues.set(50, reservation.getCancellationRelease());
        roomValues.set(51, customer.getPersonalId());
        //new attributes
        roomValues.set(52, (reservation.getEmailPreStayDate() == null
                ? null
                : new java.sql.Date(reservation.getEmailPreStayDate().getTime())));
        //ReservationRS commission.
        roomValues.set(53, roomStay.getExternalCommission());
        //new referer attribute
        roomValues.set(54, reservation.getReferer());
        if (!isInsert) {
            roomValues.set(55, roomStay.getIdConfirmation());
        }
        //values.addAll(roomValues);
        //}
        //return values;
        return roomValues;
    }

    private static List<Object> getList(final boolean isInsert) {
        final int size = isInsert ? NUMBER_ATTRIBUTES : NUMBER_ATTRIBUTES + 1;
        final List<Object> ret = new ArrayList<>(size);
        //final Integer[] nullValues = new Integer[]{22, 24, 30, 43, 44, 45, 46, 47, 48, 49};
        final Integer[] zeroValues = new Integer[]{27, 28};
        final Integer[] minusOneValues = new Integer[]{50};
        //googleanalyticsok=true, because the reservation come from the outside
        final Integer[] trueValues = new Integer[]{32};
        final Integer[] falseValues = new Integer[]{42};

        for (int i = 0; i < size; i++) {
            ret.add(i, null);
        }
        for (int i : zeroValues) {
            ret.set(i, 0);
        }
        for (int i : minusOneValues) {
            ret.set(i, "-1");
        }
        for (int i : trueValues) {
            ret.set(i, true);
        }
        for (int i : falseValues) {
            ret.set(i, false);
        }
        return ret;
    }

    private List<Reservation> getReservationsFromResultSet(ResultSet resultSet)
            throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        //Map with several multi-reservations
        final Map<String, Reservation> hashMap = new HashMap<>();
        List<Reservation> reservationList = new ArrayList<>();
        final float taxPercentage;
        if (inventoryDBHandler != null && inventoryDBHandler.isComplexInventory()) {
            List<Tax> taxes = null;
            try {
                taxes = inventoryDBHandler.getTaxes();
            } catch (DBAccessException ex) {
                logger.error(ex);
            }
            taxPercentage = taxes == null || taxes.isEmpty()
                    ? 1
                    : 1 + taxes.get(0).getValue();
        } else {
            taxPercentage = 1;
        }
        while (next(resultSet)) {
            final String reservationId = getString(resultSet, 3);
            if (reservationId == null) {
                throw new DBAccessException(new NullPointerException("reservationId is null."));
            }
            Reservation.ReservationStatus status = Reservation.ReservationStatus.getValueOf(getString(resultSet, 6));
            Reservation reservation = hashMap.get(reservationId);
            if (reservation == null) {
                reservation = new Reservation();
                reservation.setReservationId(reservationId);
                reservation.setDateCreation(getTimestamp(resultSet, 4));
                reservation.setStatus(status);
                //TODO: how to calculate?
                //This total. should have the amount with the extras?
//                reservation.setAmountAfterTax(getFloat(resultSet,7));
                //
                reservation.setLanguage(getString(resultSet, 8));
                reservation.setEmailPostStayDate(getDate(resultSet, 9));
                reservation.setChannelId(getString(resultSet, 30));
                reservation.setAgentId(getInt(resultSet, 31));
                reservation.setReported(getBoolean(resultSet, 32));
                reservation.setAmountBeforeTax(getFloat(resultSet, 33));
                reservation.setPaymentStatus((byte) getInt(resultSet, 37));
                reservation.setChannelAddress(getString(resultSet, 38));
                //new attributes
                reservation.setEmailPreStayDate(getDate(resultSet, 29));
                reservation.setCodeApplied(getString(resultSet, 42));
                reservation.setGoogleAnalyticsReported(getBoolean(resultSet, 43));
                reservation.setTrackingId(getString(resultSet, 44));
                reservation.setCancellationRelease(getInt(resultSet, 45));
                reservation.setReferer(getString(resultSet, 49));

                Customer customer = new Customer();
//            customer.setIdConfirmation(getString(resultSet,j));
                String name = getString(resultSet, 13);
                try {
                    if (name != null && !name.trim().isEmpty()) {
                        //Contains the old way to save the Names, and the new way (commas)
                        if (name.contains(",")) {
                            String[] nameArray = name.trim().split(",");
                            customer.setSurName(nameArray[0].trim());
                            if (nameArray.length > 1) {
                                customer.setGivenName(nameArray[1].trim());
                            }
                        } else {
                            String[] nameArray = name.trim().split(" ");
                            customer.setGivenName(nameArray[0].trim());
                            customer.setSurName(name.replaceFirst(nameArray[0], "").trim());
                        }
                    }
                } catch (Exception ex) {
                    logger.error(ex.getMessage() + " Error with reserva_id: '" + getString(resultSet,
                            (1)) + "' hotel: " + getDbConnection().getDbCredentials().getNameDB());
                }
                if (customer.getGivenName() == null || customer.getGivenName().trim().isEmpty())
                    customer.setGivenName("Name Undefined");
                if (customer.getSurName() == null || customer.getSurName().trim().isEmpty())
                    customer.setSurName("SurName Undefined");

                customer.setEmail(getString(resultSet, 12));
                customer.setTelephone(getString(resultSet, 14));
                customer.setMailOption(getBoolean(resultSet, 15));
                customer.setIpOrder(getString(resultSet, 26));
                customer.setCountry(getString(resultSet, 27));
                customer.setPersonalId(getString(resultSet, 28));

                CreditCard creditCard = new CreditCard();
//            creditCard.setIdConfirmation(getString(resultSet,j));
//            creditCard.setCardNumber(getString(resultSet,j));
//            creditCard.setExpireDate("");
//            creditCard.setSeriesCode(getString(resultSet,j));            
                creditCard.setCardCode(getString(resultSet, 16));
                creditCard.setCardHolderName(getString(resultSet, 17));
                creditCard.setCardNumberEncrypted(getString(resultSet, 18));
                creditCard.setExpireDateEncrypted(getString(resultSet, 19));
                creditCard.setSeriesCodeEncrypted(getString(resultSet, 20));

                customer.setCreditCard(creditCard);

                reservation.setCustomer(customer);
                //TODO:get tax from Factory
                //Tax tax = new Tax();
                reservation.setTax(null);
                reservationList.add(reservation);
                hashMap.put(reservationId, reservation);
            }

            RoomStay roomStay = new RoomStay();

            roomStay.setDateCheckIn(getDate(resultSet, 21));
            roomStay.setDateCheckOut(getDate(resultSet, 22));
            roomStay.setQuantity(getInt(resultSet, 24));
            roomStay.setTotalAmount(getFloat(resultSet, 7));
            roomStay.setStatus(status);

            String resumen = getString(resultSet, 25);
            RangeValue<Float> rates = new RangeValue<>(RateDataValue.DEFAULT_VALUE);
            //Parser the JSON resumen
            JsonElement elementJson = null;
            try {
                JsonObject resumenJson;
                JsonParser jsonParser = new JsonParser();
                resumenJson = jsonParser.parse(resumen).getAsJsonObject();
                try {
                    //Get the rates from the JSON (resumen)
                    elementJson = resumenJson.get("pricesdetailedinfo");
                    if (elementJson != null) {
                        JsonArray ratesJson = elementJson.getAsJsonArray();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                        for (JsonElement rate : ratesJson) {
                            Map.Entry<String, JsonElement> entry = rate.getAsJsonObject().entrySet().iterator().next();
                            rates.putValueForADate(dateFormat.parse(entry.getKey()), entry.getValue().getAsFloat());
                        }
                    }
                } catch (Exception ex) {
                    logger.error(" JsonFail.Rates: " + ex.getMessage());
                    logger.error(" JsonFail.Rates: " + elementJson);
                }
                try {
                    //Get the Services Requested from the JSON (resumen)
                    elementJson = resumenJson.get("selectedextrasdetails");
                    if (elementJson != null) {
                        JsonArray servicesJson = elementJson.getAsJsonArray();
                        List<ServiceRequested> services = new ArrayList<>();
                        for (JsonElement service : servicesJson) {
                            ServiceRequested serviceRequested = new ServiceRequested();
                            serviceRequested.setServiceId(service.getAsJsonObject().get("id").getAsInt());
                            serviceRequested.setServiceTicker(service.getAsJsonObject().get("codigo").getAsString());
                            serviceRequested.setServiceName(service.getAsJsonObject().get("name").getAsString());
                            serviceRequested.setQuantity(service.getAsJsonObject().get("mnumselect").getAsInt());
                            serviceRequested.setTotalServiceAmount(service.getAsJsonObject().get("ftdcomputedprice").getAsFloat());
                            //roomStay.getDateCheckIn(), roomStay.getDateCheckOut()
                            serviceRequested.setDaily("1".equals(service.getAsJsonObject().get("xdia") + ""));
                            if ("1".equals(service.getAsJsonObject().get("xpersona") + "")) {
                                serviceRequested.setType((byte) 1);
                            } else if ("1".equals(service.getAsJsonObject().get("eleccionnumerica") + "")) {
                                serviceRequested.setType((byte) 2);
                            } else {
                                serviceRequested.setType((byte) 0);
                            }
                            //service DailyValue
//                            Date dateStart = roomStay.getDateCheckIn();
//                            Date dateEnd;
//                            if (serviceRequested.isDaily()) {
//                                dateEnd = (Date) roomStay.getDateCheckOut().clone();
//                                DateUtil.incrementDays(dateEnd, -1);
//                            } else {
//                                dateEnd = dateStart;
//                            }
//                            DailyValue<Float> dailyValue = new DailyValue<>(dateStart, dateEnd, service.getAsJsonObject().get("precio").getAsFloat());
//                            RangeValue<Float> priceValue = new RangeValue<>(dailyValue, null);
//                            serviceRequested.setServicePrice(priceValue);
                            services.add(serviceRequested);
                        }
                        roomStay.setServices(services);
                    } else {
                        roomStay.setServices(null);
                    }
                } catch (Exception ex) {
                    logger.error(" JsonFail.Services: " + ex.getMessage());
                    logger.error(" JsonFail.Services: " + elementJson);
                }
                try {
                    //Get the Discounts Applied from the JSON (resumen)
                    elementJson = resumenJson.get("selectedpromosdetails");
                    if (elementJson != null) {
                        JsonArray servicesJson = elementJson.getAsJsonArray();
                        List<DiscountApplied> discounts = new ArrayList<>();
                        for (JsonElement discount : servicesJson) {
                            DiscountApplied discountApplied = new DiscountApplied();
                            discountApplied.setDiscountId(discount.getAsJsonObject().get("id").getAsInt());
                            discountApplied.setDiscountTicker(discount.getAsJsonObject().get("codigo").getAsString());
                            discountApplied.setDiscountName(discount.getAsJsonObject().get("name").getAsString());
                            discountApplied.setTotalDiscountAmount(discount.getAsJsonObject().get("computedprice").getAsFloat());

                            elementJson = discount.getAsJsonObject().get("fechaspromocionaplicable");
                            if (elementJson != null) {
                                JsonObject ratesJson = elementJson.getAsJsonObject();
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
                                RangeValue<Float> discountRates = new RangeValue<>(0f);
                                for (Map.Entry<String, JsonElement> entry : ratesJson.entrySet()) {
                                    discountRates.putValueForADate(dateFormat.parse(entry.getKey()),
                                            entry.getValue().getAsFloat());
                                }
                                discountApplied.setDiscountPrice(discountRates);
                            }
                            discounts.add(discountApplied);
                        }
                        roomStay.setDiscounts(discounts);
                    } else {
                        roomStay.setDiscounts(null);
                    }
                } catch (Exception ex) {
                    logger.error(" JsonFail.Discounts: " + ex.getMessage());
                    logger.error(" JsonFail.Discounts: " + elementJson);
                }
                try {
                    //Get the bookingPriceRules from the JSON (resumen)
                    elementJson = resumenJson.get("bookingPriceRules");
                    if (elementJson != null) {
                        JsonArray requestsJson = elementJson.getAsJsonArray();
                        List<BookingPriceRulesApplied> rulesApplied = JsonUtils.gsonInstance().fromJson(requestsJson,
                                new TypeToken<ArrayList<BookingPriceRulesApplied>>() {
                                }.getType());
                        roomStay.setBookingPriceRules(rulesApplied);
                    } else {
                        roomStay.setBookingPriceRules(null);
                    }
                } catch (Exception ex) {
                    logger.error(" JsonFail.BookingPriceRules: " + ex.getMessage());
                    logger.error(" JsonFail.BookingPriceRules: " + elementJson);
                }
                try {
                    //Get the additionalRequests from the JSON (resumen)
                    //The additionalRequests for the reservation for OTA integrations
                    elementJson = resumenJson.get("additionalRequests");
                    if (elementJson != null) {
                        JsonObject requestsJson = elementJson.getAsJsonObject();
                        Map<String, String> additionalRequests = new HashMap<>();
                        for (Map.Entry<String, JsonElement> elementEntry : requestsJson.entrySet()) {
                            additionalRequests.put(elementEntry.getKey(), elementEntry.getValue().getAsString());
                        }
                        roomStay.setAdditionalRequests(additionalRequests);
                    } else {
                        roomStay.setAdditionalRequests(null);
                    }
                } catch (Exception ex) {
                    logger.error(" JsonFail.AdditionalRequests: " + ex.getMessage());
                    logger.error(" JsonFail.AdditionalRequests: " + elementJson);
                }

                try {
                    //Get the Guest from the JSON (resumen)
                    //The Guest for the reservation for OTA integrations
                    elementJson = resumenJson.get("guests");
                    if (elementJson != null) {
                        JsonArray guestsJson = elementJson.getAsJsonArray();
                        List<ResGuest> resGuests = new ArrayList<>();
                        for (JsonElement guest : guestsJson) {
                            ResGuest resGuest = new ResGuest();
                            resGuest.setId(guest.getAsJsonObject().get("guestRPH").getAsInt());
                            resGuest.setName(guest.getAsJsonObject().get("name").getAsString());
                            resGuests.add(resGuest);
                        }
                        roomStay.setGuestList(resGuests);
                    } else {
                        roomStay.setGuestList(null);
                    }
                } catch (Exception ex) {
                    logger.error(" JsonFail.Guests: " + ex.getMessage());
                    logger.error(" JsonFail.Guests: " + elementJson);
                }
                try {
                    //Comments for the reservations
                    elementJson = resumenJson.get("observations");
                    if (elementJson != null) {
                        roomStay.setComments(elementJson.getAsString());
                    }
                    //The Active Currency for the ReservationRS
                    elementJson = resumenJson.get("activecurrency");
                    if (elementJson != null && !elementJson.isJsonNull()) {
                        reservation.setCurrency(elementJson.getAsString());
                    }
                    elementJson = resumenJson.get("dni");
                    if (elementJson != null && !elementJson.isJsonNull() && !elementJson.getAsString().isEmpty()) {
                        reservation.getCustomer().setPersonalId(elementJson.getAsString());
                    }
                    elementJson = resumenJson.get("direccion");
                    if (elementJson != null && !elementJson.isJsonNull() && !elementJson.getAsString().isEmpty()) {
                        reservation.getCustomer().setAddress(elementJson.getAsString());
                    }
                } catch (Exception ex) {
                    logger.error(" JsonFail.ComplementsValues: " + ex.getMessage());
                    logger.error(" JsonFail.ComplementsValues: " + elementJson);
                }
            } catch (Exception ex) {
                logger.error(" JsonFail: " + ex.getMessage());
                logger.error(" JsonFail: " + elementJson);
            }
            roomStay.setRoomRates(rates);

            roomStay.setId(getString(resultSet, 1) + "");
            roomStay.setIdConfirmation(getString(resultSet, 2) + "");
            roomStay.setCapacity(getInt(resultSet, 34));

            roomStay.setDateModification(getTimestamp(resultSet, 5));
            roomStay.setCancellationCause(getString(resultSet, 10));
            roomStay.setCancellationDate(getTimestamp(resultSet, 11));
            roomStay.setCanceledByClient(getBoolean(resultSet, 41));

            //prcjpago
            Float percentage = null;
            if (getString(resultSet, 36) != null && !"".equals(getString(resultSet, 36))
                    && getFloat(resultSet, 36) > 0) {
                percentage = getFloat(resultSet, 36);
                roomStay.setGuaranteePercentage(percentage);
            }
            //calculating the amount Guarantee
            final float totalAmountAfterTax = roomStay.getTotalAmount();
            if (getString(resultSet, 35) != null && !"".equals(getString(resultSet, 35))
                    && getFloat(resultSet, 35) > 0) {
                //depositofijo
                roomStay.setGuaranteeAmount(getFloat(resultSet, 35));
            } else if (percentage != null && percentage > 0) {
                roomStay.setGuaranteeAmount(totalAmountAfterTax * percentage / 100);
            } else {
                roomStay.setGuaranteeAmount(0);
            }
            //Get channel Commission.
            roomStay.setExternalCommission(getFloat(resultSet, 40));
            //Setting up the Inventory related to the reservation
            roomStay.setInventoryId(getInt(resultSet, 23));
            //TODO: Do we need this for V6??
            if (inventoryDBHandler != null && inventoryDBHandler.isComplexInventory()) {
                //TODO:try and catch, because the problem in migration with the reservations and old v5 packages.
                try {
                    Inventory inventory = inventoryDBHandler.getInventoryById(getInt(resultSet, 23));
                    roomStay.setInventoryTicker(inventory.getTicker());
                    roomStay.setAccommodationType(inventory.getAccommodation().getName());
                    roomStay.setMealPlanType(inventory.getMealPlan().getName());
                    roomStay.setConditionType(inventory.getCondition().getName());
                    roomStay.setConfigurationType(inventory.getConfiguration().getName());
                } catch (Exception ex) {
                    logger.error(ex.getMessage() + " Error with reserva_id: '" + getString(resultSet,
                            (1)) + "' hotel: " + getDbConnection().getDbCredentials().getNameDB());
                }
            }
            //adding the roomStay to the reservation-multi
            reservation.addRoomStay(roomStay);
            reservation.increaseAmountAfterTax(totalAmountAfterTax);
//            reservation.setAmountBeforeTax(taxPercentage);
            reservation.setAmountBeforeTax(Float.parseFloat(new DecimalFormat("####.##").format
                    (reservation.getAmountAfterTax() / taxPercentage).replace(',', '.')));

        }
        return reservationList;
    }
}
