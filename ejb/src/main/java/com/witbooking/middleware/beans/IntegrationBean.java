package com.witbooking.middleware.beans;

import com.google.common.base.Joiner;
import com.google.gson.JsonObject;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.*;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.integration.*;
import com.witbooking.middleware.integration.mandrill.model.Event;
import com.witbooking.middleware.integration.rategain.RateGainEnqueuerLocal;
import com.witbooking.middleware.integration.siteminder.SiteMinderEnqueuerLocal;
import com.witbooking.middleware.integration.tripadvisor.TripAdvisorEnqueuerLocal;
import com.witbooking.middleware.model.Reservation;
import com.witbooking.middleware.model.channelsIntegration.Channel;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.EmailsUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.*;

/**
 * IntegrationBean.java
 * User: jose
 * Date: 11/4/13
 * Time: 4:00 PM
 */
@Stateless
public class IntegrationBean implements IntegrationBeanLocal {

    private static final Logger logger = Logger.getLogger(IntegrationBean.class);


    @EJB
    private TripAdvisorEnqueuerLocal tripAdvisorEnqueuerLocal;
    @EJB
    private SiteMinderEnqueuerLocal siteMinderEnqueuerLocal;
    @EJB
    private RateGainEnqueuerLocal rateGainEnqueuerLocal;
    @EJB
    private ConnectionBeanLocal connectionBeanLocal;
    @EJB
    private BookingBeanLocal bookingBeanLocal;

    private static final String LOGGER_PREFIX_KEY = "WIT_INTE_KEY";

    //Integrations at reservationTime
    public static final String SITEMINDER_INTEGRATION = "siteMinderIntegration";
    public static final String TRIPADVISOR_RE_INTEGRATION = "tripAdvisorREIntegration";
    public static final String RATEGAIN_INTEGRATION = "rateGainIntegration";
    public static final String EMAIL_AVAILABILITY_MSG = "emailAvisoDispo";
    public static final String LIMIT_AVAILABILITY_MSG = "limiteAvisoDisponibilidad";

    public enum SuccessMessage {
        ERROR(0, "error"), SUCCESS(1, null), WARNING(2, "warning");
        private int value;
        private String message;

        SuccessMessage(int value, String message) {
            this.value = value;
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public int getValue() {
            return value;
        }
    }
//    private static final Integer ERROR = 0 , SUCCESS = 1, WARNING = 2;

    private String getWarningMessage(String hotelTicker, String reservationId, ChannelTicker channel, List<ChannelConnectionType> types) {
        return "Entry of hotelTicker " + hotelTicker + " reservationId: " + reservationId + " Channel: " + channel +
                " and any of these Types: " + Joiner.on(",").join(types) + " aren't finished already on queue.";
    }

    private String getWarningMessageForItems(String hotelTicker, List<EntryQueueItem> items,
                                             ChannelTicker channel, List<ChannelConnectionType> types) {
        return "Entry of hotelTicker " + hotelTicker + " Items: " + Joiner.on(",").join(items) + " Channel: " + channel +
                " and any of these Types: " + Joiner.on(",").join(types) + " aren't finished already on queue.";
    }

    @Override
    public String notifyNewEntry(String hotelTicker, ChannelTicker channelTicker,
                                 ChannelConnectionType type, String reservationId,
                                 final Date executionRequestedDate,
                                 List<ChannelConnectionType> typesToCheck)
            throws IntegrationException {
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        if (typesToCheck == null)
            typesToCheck = new ArrayList<>();
        typesToCheck.add(type);
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            if (checkExists(hotelTicker, reservationId, channelTicker, typesToCheck)) {
                final String warningMessage = getWarningMessage(hotelTicker, reservationId, channelTicker, typesToCheck);
                logger.warn(warningMessage);
                return warningMessage;
            }
            List<EntryQueueItem> items = null;
            if (reservationId != null && !reservationId.isEmpty()) {
                items = new ArrayList<>();
                items.add(new EntryQueueItem(reservationId));
            }
            witMetaDataDBHandler.insertNewConnection(hotelTicker, channelTicker, type, executionRequestedDate, items);
            return null;
        } catch (ExternalFileException | NonexistentValueException | DBAccessException e) {
            logger.error(e);
            throw new IntegrationException(e);
        } finally {
            close(witMetaDataDBHandler);
        }
    }


    @Override
    public String notifyNewEntryForItems(String hotelTicker, ChannelTicker channelTicker,
                                         ChannelConnectionType type, List<EntryQueueItem> items,
                                         final Date executionRequestedDate)
            throws IntegrationException {
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            List<EntryQueueItem> newItems = new ArrayList<>();
            for (EntryQueueItem item : items) {
                Integer entryId = witMetaDataDBHandler.getEntryIdFromItem(hotelTicker, channelTicker, type, item);
                //Check if exists.
                if (entryId == null) {
                    newItems.add(item);
                }
            }
            if (!newItems.isEmpty())
                witMetaDataDBHandler.insertNewConnection(hotelTicker, channelTicker, type, executionRequestedDate, newItems);
            if (newItems.size() < items.size()) {
                final String warningMessage = getWarningMessageForItems(hotelTicker, items, channelTicker, Arrays.asList(type));
                logger.warn(warningMessage);
                return warningMessage;
            }
            return null;
        } catch (ExternalFileException | NonexistentValueException | DBAccessException e) {
            logger.error(e);
            throw new IntegrationException(e);
        } finally {
            close(witMetaDataDBHandler);
        }
    }

    @Override
    public Boolean checkExists(String hotelTicker, String reservationId, ChannelTicker channelTicker,
                               List<ChannelConnectionType> typesToCheck)
            throws IntegrationException {
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            return witMetaDataDBHandler.checkExists(hotelTicker, channelTicker, typesToCheck, reservationId);
        } catch (ExternalFileException | NonexistentValueException | DBAccessException e) {
            logger.error(e);
            throw new IntegrationException(e);
        } finally {
            close(witMetaDataDBHandler);
        }
    }

    @Override
    public Set<EntryQueue> getEntriesQueue(final String hotelTicker,
                                           final String reservationId,
                                           final ChannelTicker channelTicker,
                                           final CommunicationFinished finished,
                                           final List<ChannelConnectionType> typesToCheck)
            throws IntegrationException {
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            return witMetaDataDBHandler.getEntriesQueue(hotelTicker, reservationId, channelTicker, finished, typesToCheck);
        } catch (ExternalFileException | NonexistentValueException | DBAccessException e) {
            logger.error(e);
            throw new IntegrationException(e);
        } finally {
            close(witMetaDataDBHandler);
        }
    }

    /**
     * @param hotelTicker
     * @param channelTicker
     * @param type
     * @param status
     * @param items
     * @return The id of table connections_details corresponding to the inserted item.
     * @throws IntegrationException
     */
    @Override
    public Integer storeSingleConnection(final String hotelTicker, final ChannelTicker channelTicker,
                                         final ChannelConnectionType type, final ChannelQueueStatus status,
                                         Set<EntryQueueItem> items, final Object request, final Object response)
            throws IntegrationException {

        WitMetaDataDBHandler witMetaDataDBHandler = null;
        Integer itemId = null;
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            itemId = witMetaDataDBHandler.storeSingleConnection(hotelTicker, channelTicker, type, status, items);
            return itemId;
        } catch (Exception e) {
            logger.error(e);
            throw new IntegrationException(e);
        } finally {
            close(witMetaDataDBHandler);
            if (itemId != null) printInLog(itemId, request, response);
            else logger.error("itemId is null: hotelTicker= '" + hotelTicker + "' channelTicker: '" + channelTicker +
                    "' type='" + type + "' status='" + status + "' items: '" + items + "' request: '" + request + "' response: '" + response + "'");
        }
    }

    public Integer reportAnConnection(final String hotelTicker, final ChannelTicker channelTicker,
                                      final ChannelConnectionType type, final ChannelQueueStatus status,
                                      List<EntryQueueItem> items, final Object request, final Object response)
            throws IntegrationException {
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        Integer itemId = null;
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            itemId = witMetaDataDBHandler.reportAConnectionWithItems(hotelTicker, items, channelTicker, type, status);
            return itemId;
        } catch (DBAccessException | ExternalFileException | NonexistentValueException e) {
            logger.error(e);
            throw new IntegrationException(e);
        } finally {
            close(witMetaDataDBHandler);
            if (request != null && response != null)
                if (itemId != null) printInLog(itemId, request, response);
                else
                    logger.error("itemId is null: hotelTicker= '" + hotelTicker + "' channelTicker: '" + channelTicker +
                            "' type='" + type + "' status='" + status + "' items: '" + items + "' request: '" + request + "' response: '" + response + "'");
        }
    }

    public Integer reportAnConnection(final String hotelTicker, final String reservationId,
                                      final ChannelTicker channelTicker, final ChannelConnectionType type,
                                      final ChannelQueueStatus status, final Object request, final Object response)
            throws IntegrationException {
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        Integer itemId = null;
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            itemId = witMetaDataDBHandler.reportAConnection(hotelTicker, new EntryQueueItem(reservationId),
                    channelTicker, type, status);
            return itemId;
        } catch (DBAccessException | ExternalFileException | NonexistentValueException e) {
            logger.error(e);
            throw new IntegrationException(e);
        } finally {
            close(witMetaDataDBHandler);
            if (itemId != null) printInLog(itemId, request, response);
            else logger.error("itemId is null: hotelTicker= '" + hotelTicker + "' channelTicker: '" + channelTicker +
                    "' type='" + type + "' status='" + status + "' reservationId: '" + reservationId + "' request: '" + request + "' response: '" + response + "'");
        }
    }

    @Override
    public List<Integer> changeStatus(final int entryQueueId, final ChannelQueueStatus status, final CommunicationFinished finished)
            throws IntegrationException {
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            return witMetaDataDBHandler.changeStatus(entryQueueId, status, finished);
        } catch (DBAccessException | ExternalFileException | NonexistentValueException e) {
            logger.error(e);
            throw new IntegrationException(e);
        } finally {
            close(witMetaDataDBHandler);
        }
    }

    private void printInLog(final Integer itemId, final Object request, final Object response) {
        if (itemId == null) {
            logger.info("itemId given is null, printing request and response with itemId null;");
        }
        String requestCommunicationId = generateRequestCommunicationId(itemId);
        String responseCommunicationId = generateResponseCommunicationId(itemId);
        logger.info(requestCommunicationId + ": " + request);
        logger.info(responseCommunicationId + ": " + response);
    }

    private String generateRequestCommunicationId(final Integer id) {
        return " RQ_" + LOGGER_PREFIX_KEY + "_" + id + " ";
    }

    private String generateResponseCommunicationId(final Integer id) {
        return " RS_" + LOGGER_PREFIX_KEY + "_" + id + " ";
    }

    private String getJsonResponse(SuccessMessage successMessage, String other) {
        JsonObject responseJson = new JsonObject();
        if (successMessage != null) {
            responseJson.addProperty("success", successMessage.getValue());
            String type = successMessage.getMessage();
            if (type != null) {
                responseJson.addProperty(type, other);
            }
        } else {
            logger.error("successMessage given is null");
        }
        return responseJson.toString();
    }


    @Override
    public String error(MiddlewareException ex) {
        return getJsonResponse(SuccessMessage.ERROR, ex != null ? ex.toString().replace("\"", "'") : "");
    }

    @Override
    public String error(Exception ex) {
        return getJsonResponse(SuccessMessage.ERROR, ex != null ? ex.toString() : "");
    }

    @Override
    public String error(String errorMessage) {
        return getJsonResponse(SuccessMessage.ERROR, errorMessage);
    }

    @Override
    public String success() {
        return getJsonResponse(SuccessMessage.SUCCESS, null);
    }

    @Override
    public String successWithWarning(String warning) {
        return getJsonResponse(SuccessMessage.WARNING, warning);
    }

    private void close(WitMetaDataDBHandler witMetaDataDBHandler) {
        if (witMetaDataDBHandler != null)
            DAOUtil.close(witMetaDataDBHandler.getDbConnection());
    }

    @Override
    public Date getStartDayFromReservation(final String hotelTicker, final String reservationId)
            throws IntegrationException {
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            ReservationDBHandler reservationDBHandler = new ReservationDBHandler(dbConnection);
            final Reservation reservationByReservationId = reservationDBHandler.getReservationByReservationId(reservationId);
            if (reservationByReservationId == null) {
                final String msg = "ReservationId given is null.";
                throw new IntegrationException(new NullPointerException(msg), msg);
            }
            return reservationByReservationId.getFirstCheckIn();
        } catch (ExternalFileException | NonexistentValueException | DBAccessException e) {
            logger.error(e);
            throw new IntegrationException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    @Override
    public String enqueueReservationForIntegration(final String reservationId, final String hotelTicker, final Channel channel,
                                                   final ChannelConnectionType type) throws IntegrationException {
        ChannelTicker channelTicker = ChannelTicker.fromValue(channel.getChannelTicker());
        switch (channelTicker) {
            case SITEMINDER:
                return siteMinderEnqueuerLocal.reportReservation(reservationId, hotelTicker);
            case TRIPADVISOR:
                return reportTripAdvisorReservation(reservationId, hotelTicker, type);
            case RATEGAIN:
                return rateGainEnqueuerLocal.reportReservation(reservationId, hotelTicker);
            case OTHER:
                throw new IntegrationException("There is no Integration with the code '" + channel.getChannelTicker() +
                        "' hotel: " + "'" + hotelTicker + "'");
            default:
                return enqueueReservationPush(reservationId, hotelTicker, channel, type);
        }
    }

    private String reportTripAdvisorReservation(final String reservationId, final String hotelTicker, final ChannelConnectionType type) {
        switch (type) {
            case NOTIFY_MODIFIED_RESERVES:
                return tripAdvisorEnqueuerLocal.updateReservation(reservationId, hotelTicker);
            case NOTIFY_CANCELLED_RESERVES:
                return tripAdvisorEnqueuerLocal.cancelReservation(reservationId, hotelTicker);
            default:
                return tripAdvisorEnqueuerLocal.reportReservation(reservationId, hotelTicker);
        }
    }

    public String enqueueReservationPush(final String reservationId, final String hotelTicker, String type)
            throws IntegrationException {
        DBConnection dBConnection = null;
        try {
            dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            final ChannelsHotelDBHandler channelsHotelDBHandler = new ChannelsHotelDBHandler(dBConnection);
            List<Channel> channels = channelsHotelDBHandler.getChannels();
            String enqueueString = "";
            if (type == null || type.trim().isEmpty())
                type = ChannelConnectionType.NOTIFY_RESERVES + "";
            for (Channel channel : channels) {
                if (channel.isActive())
                    enqueueString = enqueueString + enqueueReservationForIntegration(reservationId, hotelTicker, channel, ChannelConnectionType.fromValue(type));
            }
            return enqueueString;
        } catch (Exception ex) {
            logger.error(ex);
            throw new IntegrationException(ex);
        } finally {
            DAOUtil.close(dBConnection);
        }
    }

    public String enqueueReservationPush(final String reservationId, final String hotelTicker, final Channel
            channel, ChannelConnectionType type) {
        //http://confluence.witbooking.com/pages/viewpage.action?pageId=4882665
        //http PUSH Reservation
        if (channel.getTypePush() == 1) {
            try {
                final String info = notifyNewEntry(hotelTicker, ChannelTicker.fromValue(channel.getChannelTicker()), type,
                        reservationId, null, null);
                if (info != null) {
                    return successWithWarning(info);
                }
                return success();
            } catch (IntegrationException e) {
                logger.error(e);
                return error(e);
            }
        }
        //TODO: make the EMAIL PUSH Reservation
        return successWithWarning("Integration without Push Notification");
    }

    public void postReservationProcess(final List<Reservation> reservations, InventoryDBHandler inventoryDBHandler) throws IntegrationException {
        if (reservations == null || inventoryDBHandler == null) {
            logger.error("Invalid Values in postReservationProcess");
            throw new IntegrationException("Invalid Values in postReservationProcess");
        }
        DBConnection dbConnection = inventoryDBHandler.getDbConnection();
        if (dbConnection == null || dbConnection.getDbCredentials() == null) {
            logger.error("Invalid DBConnection Values in postReservationProcess");
            throw new IntegrationException("Invalid DBConnection Values in postReservationProcess");
        }
        final String hotelTicker = dbConnection.getDbCredentials().getTicker();
        try {
            ChannelsHotelDBHandler channelsHotelDBHandler = new ChannelsHotelDBHandler(dbConnection);
            List<Channel> channels = channelsHotelDBHandler.getChannels();

            List<String> configurationKeys = new ArrayList<>();
            //Getting the values from ConfigurationProperties
            configurationKeys.add(LIMIT_AVAILABILITY_MSG);
            configurationKeys.add(EMAIL_AVAILABILITY_MSG);
            HotelConfigurationDBHandler configurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
            Properties properties = configurationDBHandler.getHotelProperties(configurationKeys);
            for (Reservation newReservation : reservations) {
                //if the payment is in standby, the reservation not count as a filled stock
                if (newReservation.isStockReduced()){
                    //Checking if this hotel has any integration with a third-party applications
                    for (Channel channel : channels) {
                        if (channel.isActive()) {
                            try {
                                enqueueReservationForIntegration(newReservation.getReservationId(), hotelTicker, channel,
                                        ChannelConnectionType.NOTIFY_RESERVES);
                            } catch (IntegrationException e) {
                                logger.error(e);
                            }
                        }

                    }
                    //Checking if any Inventory Line has zero or low Availability
                    int minAvailability = 0;
                    if (properties.getProperty(LIMIT_AVAILABILITY_MSG) != null) {
                        minAvailability = Integer.parseInt(properties.getProperty(LIMIT_AVAILABILITY_MSG));
                    }
                    Date startDateIter = newReservation.getFirstCheckIn();
                    Date endDateIter = DateUtil.cloneAndIncrementDays(newReservation.getLastCheckOut(), -1);

                    DailyValuesDBHandler valuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, startDateIter, endDateIter);
                    List<String> invTickers = newReservation.getAllInventoryTickers();
                    valuesDBHandler.getSelectedInventoryValuesBetweenDates(invTickers);
                    for (String ticker : invTickers) {
                        RangeValue<Integer> availabilityValues = valuesDBHandler.getAvailabilityByTicker(ticker);
                        Integer availability = availabilityValues.getMinValue();
                        String htmlContent;
                        String subject = null;
                        if (availability != null && availability <= 0) {
                            //Translate
                            subject = "Aviso habitaciones agotadas";
                        } else if (availability != null && availability <= minAvailability) {
                            //Translate
                            subject = "Aviso baja disponibilidad";
                        }
                        if (subject != null) {
                            htmlContent = subject + ": " + hotelTicker + "<br> ";
                            htmlContent = htmlContent + inventoryDBHandler.getInventoryByTicker(ticker).getFullName() + ": <br>";
                            Date dateIterator = (Date) startDateIter.clone();
                            while (DateUtil.dateBetweenDaysRange(dateIterator, startDateIter, endDateIter)) {
                                //Translate
                                htmlContent = htmlContent + DateUtil.calendarFormat(dateIterator) + " disponibles: "
                                        + availabilityValues.getFinalValueForADate(dateIterator) + "<br>";
                                DateUtil.incrementDays(dateIterator, 1);
                            }
                            String emailAddress = properties.getProperty(EMAIL_AVAILABILITY_MSG);
                            if (emailAddress == null || !emailAddress.contains("@")) {
                                emailAddress = inventoryDBHandler.getHotel().getEmailAdmin();
                            }
                            String mailResponse = Event.MandrillMessageEventType.SENT.getValue();
                            try {
                                if (emailAddress != null && !emailAddress.trim().isEmpty() && emailAddress.contains("@"))
                                    for (String email : emailAddress.split(",")) {
                                        if (!email.trim().isEmpty())
                                            mailResponse = EmailsUtils.sendEmail("admin@witbooking.com", "WitBooking", subject,
                                                    htmlContent, email.trim(), hotelTicker, null);
                                    }
                                if(!mailResponse.equalsIgnoreCase(Event.MandrillMessageEventType.SENT.getValue()) &&
                                        !mailResponse.equalsIgnoreCase(Event.MandrillMessageEventType.QUEUED.getValue())) {
                                    logger.error("Error in sending availability email to hotel:'" + hotelTicker + "' " +
                                            "email:'" + emailAddress + "' ");
                                    EmailsUtils.sendEmailToAdmins("Error in sending availability email to hotel:'" + hotelTicker + "' ",
                                            "Error in sending availability email to hotel:'" + hotelTicker + "' <br/>" +
                                                    "email:'" + emailAddress + "' <br/> status: " + mailResponse + " " +
                                                    "<br/>Content: <br/><br/>" + htmlContent,
                                            Arrays.asList("WitBookerAPI Errors", "Error Availability Emails"));
                                }
                            } catch (Exception ex) {
                                try {
                                    logger.error("Error sending availability email to hotel:'" + hotelTicker + "' " +
                                            "email:'" + emailAddress + "' ");
                                    EmailsUtils.sendEmailToAdmins("Error in sending availability email to hotel:'" + hotelTicker + "' ",
                                            "Error in sending availability email to hotel:'" + hotelTicker + "' <br/>" +
                                                    "email:'" + emailAddress + "' <br/> status: " + mailResponse + " " +
                                                    "<br/>Content: <br/><br/>" + htmlContent,
                                            Arrays.asList("WitBookerAPI Errors", "Error Availability Emails"));
                                } catch (Exception e) {
                                    logger.error("Error sending email to hotel:'" + hotelTicker + "' email:'" + emailAddress
                                            + "' Error: " + e);
                                }
                            }
                        }
                    }
                }
            }
        } catch (MiddlewareException e) {
            logger.error("Error in new Reservation hotel:'" + hotelTicker + "' Exception:" + e);
            throw new IntegrationException(e);
        }
    }

}