package com.witbooking.middleware.beans;

import com.google.gson.JsonObject;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.ChannelsHotelDBHandler;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.db.handlers.WitMetaDataDBHandler;
import com.witbooking.middleware.exceptions.DateFormatException;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.InvalidEntryException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.integration.*;
import com.witbooking.middleware.integration.booking.BookingBusinessLogic;
import com.witbooking.middleware.integration.booking.BookingExecutorLocal;
import com.witbooking.middleware.integration.rategain.RateGainExecutorLocal;
import com.witbooking.middleware.integration.siteminder.SiteMinderExecutorLocal;
import com.witbooking.middleware.integration.tripadvisor.TripAdvisorExecutorLocal;
import com.witbooking.middleware.model.Reservation;
import com.witbooking.middleware.model.channelsIntegration.Channel;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.HttpConnectionUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Session Bean implementation class IntegrationBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 11/11/13
 */
@Stateless
public class IntegrationExecuteBean implements IntegrationExecuteBeanLocal {

    private static final Logger logger = Logger.getLogger(IntegrationExecuteBean.class);

    @EJB
    private IntegrationBeanLocal integrationBeanLocal;
    @EJB
    private MailingBeanLocal mailingBeanLocal;
    @EJB
    private TripAdvisorExecutorLocal tripAdvisorExecutorLocal;
    @EJB
    private SiteMinderExecutorLocal siteMinderExecutorLocal;
    @EJB
    private RateGainExecutorLocal rateGainExecutorLocal;
    @EJB
    private BookingExecutorLocal bookingExecutorLocal;

    public void executePending() throws IntegrationException {
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        try {
            MiddlewareProperties.updateParameters();
        } catch (Exception e) {
            logger.error(e);
        }
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            List<EntryQueue> entryQueues = witMetaDataDBHandler.getAllRequestPending();
            for (EntryQueue entryQueue : entryQueues) {
                String response = null;
                try {
                    response = executeEntryQueue(entryQueue);
                    logger.debug("Checking max_resend...");
                    if (witMetaDataDBHandler.checkIfSendMail(entryQueue.getId())) {
                        logger.debug("Reached the max_resend.");
                        if (MiddlewareProperties.mailingSchedulerIsActivate()) {
                            logger.debug("sending mail=> true!");
                            mailingBeanLocal.sendMail(entryQueue, response);
                        } else {
                            logger.debug("Integration mailing scheduler is disable by witbooking.properties. If you wan to activate, " +
                                    "just set in witbooking.properties ACTIVATE_MAILING_SCHEDULER_INTEGRATION=true");
                        }
                    }
                } catch (IntegrationException ex) {
                    logger.error(ex);
                    mailingBeanLocal.sendMail(entryQueue, ex, response);
                } catch (Throwable ex) {
                    //Si es algo muy feo y desconocido lo obligo a que me mande el correo.
                    logger.error(ex);
                    MailingBean mailingBean = new MailingBean();
                    mailingBean.sendMail(entryQueue, new Exception(ex), response);
//                    mailingBeanLocal.sendMail(entryQueue, new Exception(ex), response);
                }
            }
        } catch (Exception e) {
            logger.error(e);
            throw new IntegrationException(e);
        } finally {
            close(witMetaDataDBHandler);
        }
    }

    public String executeEntryQueue(EntryQueue entryQueue) throws IntegrationException, InvalidEntryException {
        checkNull(entryQueue);
        switch (entryQueue.getChannelTicker()) {
            case TRIPADVISOR: {
                return executeEntryQueueForTripAdvisor(entryQueue);
            }
            case RATEGAIN: {
                return executeEntryQueueForRateGain(entryQueue);
            }
            case SITEMINDER: {
                return executeEntryQueueForSiteMinder(entryQueue);
            }
            case BOOKING: {
                return executeEntryQueueForBooking(entryQueue);
            }
            default: {
                ChannelConnectionType type = entryQueue.getType();
                if (type == ChannelConnectionType.NOTIFY_RESERVES ||
                        type == ChannelConnectionType.NOTIFY_MODIFIED_RESERVES ||
                        type == ChannelConnectionType.NOTIFY_CANCELLED_RESERVES)
                    return executeEntryQueueForPushReservation(entryQueue);
                else {
                    JsonObject responseJson = new JsonObject();
                    responseJson.addProperty("error", entryQueue + " has a  invalid Channel Ticker");
                    throw new IntegrationException(responseJson.toString());
                }
            }
        }
    }

    private void checkNull(EntryQueue entryQueue) throws IntegrationException {
        if (entryQueue == null) {
            throw new IntegrationException("EntryQueue " + entryQueue + " passed is null.");
        }
    }

    private void checkFirstQueueItem(EntryQueue entryQueue) throws IntegrationException {
        checkNull(entryQueue);
        if (entryQueue.getItems() == null
                || entryQueue.getItems().isEmpty()
                || entryQueue.getFirstItem() == null
                || entryQueue.getFirstItem().getItemId() == null) {
            throw new IntegrationException(entryQueue + " stacked without Item.");
        }
    }

    private IntegrationException genIntegrationException(EntryQueue entryQueue, ChannelTicker channelTicker) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("error",
                "The integration EntryQueue " +
                        "{\"id\":\"" + entryQueue.getId() + "\"} has a invalid Type for " + channelTicker);
        return new IntegrationException(responseJson.toString());
    }


    private String executeEntryQueueForTripAdvisor(EntryQueue entryQueue) throws IntegrationException {
        checkFirstQueueItem(entryQueue);
        String itemId = entryQueue.getFirstItem().getItemId(), response;
        switch (entryQueue.getType()) {
            case CREATE_MAIL_REQUEST:
                response = tripAdvisorExecutorLocal.reportReservation(itemId, entryQueue.getHotelTicker());
                break;
            case UPDATE_MAIL_REQUEST:
                response = tripAdvisorExecutorLocal.updateReservation(itemId, entryQueue.getHotelTicker());
                break;
            case CANCEL_MAIL_REQUEST:
                response = tripAdvisorExecutorLocal.cancelReservation(itemId, entryQueue.getHotelTicker());
                break;
            default:
                throw genIntegrationException(entryQueue, ChannelTicker.TRIPADVISOR);
        }
        logger.info(response);
        return response;
    }

    private String executeEntryQueueForRateGain(EntryQueue entryQueue) throws IntegrationException {
        checkFirstQueueItem(entryQueue);
        final String reservationId = entryQueue.getFirstItem().getItemId(), response;
        switch (entryQueue.getType()) {
            case NOTIFY_RESERVES:
                response = rateGainExecutorLocal.reportReservation(reservationId, entryQueue.getHotelTicker());
                break;
            default:
                throw genIntegrationException(entryQueue, ChannelTicker.RATEGAIN);
        }
        logger.info(response);
        return response;
    }

    private String executeEntryQueueForSiteMinder(EntryQueue entryQueue) throws IntegrationException {
        checkFirstQueueItem(entryQueue);
        String itemId = entryQueue.getFirstItem().getItemId(), response;
        switch (entryQueue.getType()) {
            case NOTIFY_RESERVES:
                response = siteMinderExecutorLocal.reportReservation(itemId, entryQueue.getHotelTicker());
                break;
            default:
                throw genIntegrationException(entryQueue, ChannelTicker.SITEMINDER);
        }
        logger.info(response);
        return response;
    }

    private String executeEntryQueueForPushReservation(EntryQueue entryQueue) throws IntegrationException {
        checkFirstQueueItem(entryQueue);
        final String reservationId = entryQueue.getFirstItem().getItemId();
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        String response = null;
        String request = null;
        DBConnection dBConnection = null;
        try {
            dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(entryQueue.getHotelTicker()));
            final ChannelsHotelDBHandler channelsHotelDBHandler = new ChannelsHotelDBHandler(dBConnection);
            Channel channel = channelsHotelDBHandler.getChannelByChannelTicker(entryQueue.getChannelTicker() + "");
            if (channel == null)
                throw new IntegrationException(new NullPointerException("Channel with ticker '" +
                        entryQueue.getChannelTicker() + "' can not be found."));
            if (!channel.isActive() && channel.getTypePush() == 0 && (channel.getAddressPush() + "").trim().isEmpty())
                throw new IntegrationException("Problems in Push Reservation with channel: " + channel);
            final ReservationDBHandler reservationDBHandler = new ReservationDBHandler(dBConnection);
            Reservation reservation = reservationDBHandler.getReservationByReservationId(reservationId);
            if (reservation != null) {
                HttpClient client = null;
                try {
                    client = HttpConnectionUtils.generateDefaultClientSSL();
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("hotelTicker", entryQueue.getHotelTicker());
                    parameters.put("reservationId", reservationId);
                    parameters.put("type", entryQueue.getType().getPushTypeValue());
                    request = channel.getAddressPush() + " parameters: " + parameters;
                    HttpResponse httpResponse = HttpConnectionUtils.getData(client, channel.getAddressPush(), null, parameters);
                    if (httpResponse != null) {
                        HttpEntity entity = httpResponse.getEntity();
                        response = EntityUtils.toString(entity);
                        logger.info("Response got :" + response);
                        if (httpResponse.getStatusLine().getStatusCode() != 200) {
                            response = "Invalid Http Response code: " + httpResponse.getStatusLine().getStatusCode() + response;
                            logger.error("Invalid Http Response code: " + httpResponse.getStatusLine().getStatusCode() +
                                    " Response: " + response);
                            throw new IntegrationException("Invalid Http Response code: " +
                                    httpResponse.getStatusLine().getStatusCode() + " - Push: " + channel.getAddressPush() +
                                    " - Response: " + response);
                        }
                        status = ChannelQueueStatus.SUCCESS;
                    } else {
                        logger.info("Response got is null");
                    }
                } catch (IOException ex) {
                    logger.error(ex);
                    throw new IntegrationException(ex);
                } finally {
                    if (client != null)
                        client.getConnectionManager().shutdown();
                }
            } else {
                throw new IntegrationException(new NullPointerException("Reservation with ticker " + reservationId + " can not be " + "found."));
            }
        } catch (DBAccessException | ExternalFileException | NonexistentValueException ex) {
            logger.error(ex);
            throw new IntegrationException(ex);
        } finally {
            DAOUtil.close(dBConnection);
            try {
                integrationBeanLocal.reportAnConnection(entryQueue.getHotelTicker(), reservationId,
                        entryQueue.getChannelTicker(), entryQueue.getType(), status, request, response);
            } catch (IntegrationException ex) {
                logger.error(ex);
                throw ex;
            }
        }
        return response;
    }

    private String executeEntryQueueForBooking(EntryQueue entryQueue) throws IntegrationException, InvalidEntryException {
        String item = null;
        EntryQueueItem firstItem = null;
        try {
            checkFirstQueueItem(entryQueue);
            firstItem = entryQueue.getFirstItem();
        } catch (IntegrationException e) {
        }
        String response;
        switch (entryQueue.getType()) {
            case EXECUTE_UPDATE_ARI:
                if (firstItem == null)
                    throw new IntegrationException(entryQueue + " stacked without Items.");
                response = bookingExecutorLocal.updateARI(entryQueue.getHotelTicker(), entryQueue.getItemsIds(),
                        firstItem.getStart(), firstItem.getEnd());
                break;
            case EXECUTE_UPDATE_AMOUNT:
                if (firstItem == null)
                    throw new IntegrationException(entryQueue + " stacked without Items.");
                response = bookingExecutorLocal.updateAmount(entryQueue.getHotelTicker(), entryQueue.getItemsIds(),
                        firstItem.getStart(), firstItem.getEnd());
                break;
            case GET_RESERVES:
                if (firstItem != null)
                    item = entryQueue.getFirstItem().getItemId();
                if (item != null && DateUtil.isTimestampFormat(item)) {
                    try {
                        item = entryQueue.getFirstItem().getItemId();
                        response = bookingExecutorLocal.getNotification(entryQueue.getHotelTicker(),
                                null, DateUtil.stringToTimestamp(item), BookingBusinessLogic.TYPE_CONFIRMATION);
                    } catch (DateFormatException e) {
                        response = bookingExecutorLocal.getNotification(entryQueue.getHotelTicker(), item, null,
                                BookingBusinessLogic.TYPE_CONFIRMATION);
                    }
                } else {
                    response = bookingExecutorLocal.getNotification(entryQueue.getHotelTicker(), item, null,
                            BookingBusinessLogic.TYPE_CONFIRMATION);
                }
                break;
            case GET_MODIFIED_RESERVES:
                if (firstItem != null)
                    item = entryQueue.getFirstItem().getItemId();
                if (item != null && DateUtil.isTimestampFormat(item)) {
                    try {
                        item = entryQueue.getFirstItem().getItemId();
                        response = bookingExecutorLocal.getNotification(entryQueue.getHotelTicker(),
                                null, DateUtil.stringToTimestamp(item), BookingBusinessLogic.TYPE_MODIFICATION);
                    } catch (DateFormatException e) {
                        response = bookingExecutorLocal.getNotification(entryQueue.getHotelTicker(), item, null,
                                BookingBusinessLogic.TYPE_MODIFICATION);
                    }
                } else {
                    response = bookingExecutorLocal.getNotification(entryQueue.getHotelTicker(), item, null,
                            BookingBusinessLogic.TYPE_MODIFICATION);
                }
                break;
            case GET_CANCELLED_RESERVES:
                if (firstItem != null)
                    item = entryQueue.getFirstItem().getItemId();
                if (item != null && DateUtil.isTimestampFormat(item)) {
                    try {
                        item = entryQueue.getFirstItem().getItemId();
                        response = bookingExecutorLocal.getNotification(entryQueue.getHotelTicker(),
                                null, DateUtil.stringToTimestamp(item), BookingBusinessLogic.TYPE_CANCEL);
                    } catch (DateFormatException e) {
                        response = bookingExecutorLocal.getNotification(entryQueue.getHotelTicker(), item, null,
                                BookingBusinessLogic.TYPE_CANCEL);
                    }
                } else {
                    response = bookingExecutorLocal.getNotification(entryQueue.getHotelTicker(), item, null,
                            BookingBusinessLogic.TYPE_CANCEL);
                }
                break;
            default:
                throw genIntegrationException(entryQueue, ChannelTicker.BOOKING);
        }
        logger.info(response);
        return response;
    }

    private void close(WitMetaDataDBHandler witMetaDataDBHandler) {
        if (witMetaDataDBHandler != null)
            DAOUtil.close(witMetaDataDBHandler.getDbConnection());
    }


}