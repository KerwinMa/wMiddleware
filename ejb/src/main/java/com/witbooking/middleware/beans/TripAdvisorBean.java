/*
 *  TripAdvisorBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.*;
import com.witbooking.middleware.exceptions.*;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.exceptions.integration.tripadvisor.TripAdvisorException;
import com.witbooking.middleware.integration.ChannelConnectionType;
import com.witbooking.middleware.integration.ChannelQueueStatus;
import com.witbooking.middleware.integration.ChannelTicker;
import com.witbooking.middleware.integration.EntryQueueItem;
import com.witbooking.middleware.integration.tripadvisor.ConstantsTripAdvisor;
import com.witbooking.middleware.integration.tripadvisor.model.*;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.values.DailyValue;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.HttpConnectionUtils;
import com.witbooking.middleware.utils.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.Currency;

/**
 * Insert description here
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 29, 2013
 */
@Stateless
public class TripAdvisorBean implements TripAdvisorLocalBean, TripAdvisorRemoteBean {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TripAdvisorBean.class);
    private final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").disableHtmlEscaping().create();
    private static final String TRACKING_ANALYTICS = "utm_source=TripAdvisor&utm_medium=cpc&utm_campaign=TripAdvisor+TripConnect";

    @EJB
    private IntegrationBeanLocal integrationBeanLocal;


    @Override
    public String checkOptionsIn(final String hotelTicker) throws TripAdvisorException {
        final DefaultHttpClient client = new DefaultHttpClient();
        String checkOptionsInString = null;
        String request = null, response = null;
        ChannelQueueStatus requestStatus = ChannelQueueStatus.FAIL;
        try {
            final HttpResponse httpResponse = sendGetMessage(client, ConstantsTripAdvisor.URL_CHECK_OPTIONS_IN +
                    hotelTicker);
            if (httpResponse != null && httpResponse.getEntity() != null) {
                checkOptionsInString = EntityUtils.toString(httpResponse.getEntity());
                try {
                    final ReviewExpress.CheckOptionsInRS checkOptionsInRS = gson.fromJson(checkOptionsInString, ReviewExpress.CheckOptionsInRS.class);
                    if (checkOptionsInRS != null) {
                        response = gson.toJson(checkOptionsInRS);
//                        logger.info(response);
                        requestStatus = ChannelQueueStatus.SUCCESS;
                        return response;
//                        return checkOptionsInRS.getReview_express_opted_in();
                    } else {
                        throw new TripAdvisorException("Object parsed is null.");
                    }
                } catch (JsonSyntaxException ex) {
                    logger.error(ex);
                    throw new TripAdvisorException(ex);
                }
            } else {
                throw new TripAdvisorException("Http Response is null or Entity is null.");
            }
        } catch (IOException e) {
            logger.error(e);
            throw new TripAdvisorException(e);
        } finally {
            client.getConnectionManager().shutdown();
            try {
                integrationBeanLocal.storeSingleConnection(null, ChannelTicker.TRIPADVISOR,
                        ChannelConnectionType.OFFER_ARI, requestStatus, null, request, response);
//                final Integer id = integrationBeanLocal.storeSingleConnection(null, ChannelTicker.TRIPADVISOR,
//                        ChannelConnectionType.OFFER_ARI, requestStatus, null,request,response);
//                final String requestCommunicationId = integrationBeanLocal.generateRequestCommunicationId(id);
//                final String responseCommunicationId = integrationBeanLocal.generateResponseCommunicationId(id);
//                logger.info(requestCommunicationId + request);
//                logger.info(responseCommunicationId + response);
            } catch (IntegrationException e) {
                logger.error(e);
            }
        }
    }

    @Override
    public String listReserves(String hotelTicker) throws TripAdvisorException {
        final DefaultHttpClient client = new DefaultHttpClient();
        ChannelQueueStatus requestStatus = ChannelQueueStatus.FAIL;
        final String request = "?location_id=" + hotelTicker;
        String response = null;
        try {
            final HttpResponse httpResponse =
                    sendGetMessage(client, ConstantsTripAdvisor.URL_LIST_RESERVES_BY_HOTEL + request);
//            httpResponse = HttpConnectionUtils.getData(URL_LIST_RESERVES_BY_HOTEL + "?location_id=" + hotelTicker, null);
            if (httpResponse != null && httpResponse.getEntity() != null) {
                try {
                    response = EntityUtils.toString(httpResponse.getEntity());
                    final ReviewExpress.ErrorType errorType = gson.fromJson(response,
                            ReviewExpress.ErrorType.class);
                    if (errorType != null && errorType.getError() != null) {
                        logger.error(gson.toJson(errorType));
                        return gson.toJson(errorType);

                    }
                    final ReviewExpress.ListReservesRS listReservesRS = gson.fromJson(response, ReviewExpress.ListReservesRS.class);
                    requestStatus = ChannelQueueStatus.SUCCESS;
                    return gson.toJson(listReservesRS);
                } catch (IOException e) {
                    logger.error(e);
                    throw new TripAdvisorException(e);
                } catch (JsonSyntaxException e) {
                    logger.error(e);
                    throw new TripAdvisorException(e);
                }
            } else {
                throw new TripAdvisorException("Http Response is null or Entity is null.");
            }
        } catch (IOException e) {
            logger.error(e);
            throw new TripAdvisorException(e);
        } finally {
            client.getConnectionManager().shutdown();
            try {
                integrationBeanLocal.storeSingleConnection(hotelTicker, ChannelTicker.TRIPADVISOR,
                        ChannelConnectionType.LIST_MAIL_REQUEST_FROM_HOTEL, requestStatus, null, request, response);
//                final Integer id = integrationBeanLocal.storeSingleConnection(null, ChannelTicker.TRIPADVISOR,
//                        ChannelConnectionType.LIST_MAIL_REQUEST_FROM_HOTEL, requestStatus, null);
//                final String requestCommunicationId = integrationBeanLocal.generateRequestCommunicationId(id);
//                final String responseCommunicationId = integrationBeanLocal.generateResponseCommunicationId(id);
//                logger.info(requestCommunicationId + request);
//                logger.info(responseCommunicationId + response);
            } catch (IntegrationException e) {
                logger.error(e);
            }
        }
    }

    @Override
    public String listReservation(String hotelTicker, String reservationId) throws TripAdvisorException {
        final DefaultHttpClient client = new DefaultHttpClient();
        String request = getTripAdvisorId(hotelTicker, reservationId), response = null;
        ChannelQueueStatus requestStatus = ChannelQueueStatus.FAIL;
        try {
            if (request == null) {
                request = "hotelticker=\"" + hotelTicker + "\" and reservationId=\"" + reservationId + "\"";
                response = "TripAdvisorId of reservationId=\"" + reservationId + "\" and hotelTicker=\"" + hotelTicker + "\" isn't defined.";
                throw new TripAdvisorException(response);
            }
            final HttpResponse httpResponse =
                    sendGetMessage(client, ConstantsTripAdvisor.URL_LIST_RESERVES_BY_HOTEL + "/" + request);
            if (httpResponse != null && httpResponse.getEntity() != null) {

                try {
                    response = EntityUtils.toString(httpResponse.getEntity());
                    final ReviewExpress.ErrorType errorType = gson.fromJson(response,
                            ReviewExpress.ErrorType.class);
                    if (errorType != null && errorType.getError() != null) {
                        logger.error(gson.toJson(errorType));
                        return gson.toJson(errorType);

                    }
                    final ReviewExpress.ReservationRS reservationRS = gson.fromJson(response, ReviewExpress.ReservationRS.class);
                    requestStatus = ChannelQueueStatus.SUCCESS;
                    return gson.toJson(reservationRS);
                } catch (IOException e) {
                    logger.error(e);
                    throw new TripAdvisorException(e);
                }
            } else {
                throw new TripAdvisorException("Http Response is null or Entity is null.");
            }
        } catch (IOException e) {
            logger.error(e);
            throw new TripAdvisorException(e);
        } finally {
            client.getConnectionManager().shutdown();
            try {
                integrationBeanLocal.storeSingleConnection(hotelTicker, ChannelTicker.TRIPADVISOR,
                        ChannelConnectionType.LIST_MAIL_REQUEST_FROM_HOTEL, requestStatus, null
                        , request, response == null ? response : response.replace("\n", "").replace("\t", ""));
//                final Integer id = integrationBeanLocal.storeSingleConnection(null, ChannelTicker.TRIPADVISOR,
//                        ChannelConnectionType.LIST_MAIL_REQUEST_FROM_HOTEL, requestStatus, null);
//                final String requestCommunicationId = integrationBeanLocal.generateRequestCommunicationId(id);
//                final String responseCommunicationId = integrationBeanLocal.generateResponseCommunicationId(id);
//                logger.info(requestCommunicationId + request);
//                logger.info(responseCommunicationId + response == null ? response : response.replace("\n", "").replace("\t", ""));
            } catch (IntegrationException e) {
                logger.error(e);
            }
        }
    }

    @Override
    public void cancelReservation(String hotelTicker, String reservationId) throws TripAdvisorException {
        final DefaultHttpClient client = new DefaultHttpClient();
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        String tripAdvisorId = getTripAdvisorId(hotelTicker, reservationId);
        String responseString = null;
        try {
            final HttpResponse httpResponse = deleteMessage(client, ConstantsTripAdvisor.URL_DELETE_REQUEST + tripAdvisorId);
            if (httpResponse != null && httpResponse.getEntity() != null) {
                responseString = EntityUtils.toString(httpResponse.getEntity());
                final ReviewExpress.ErrorType errorType = gson.fromJson(responseString,
                        ReviewExpress.ErrorType.class);
                if (errorType != null && errorType.getError() != null) {
                    logger.error(gson.toJson(errorType));
//                    return gson.toJson(errorType);
                    throw new TripAdvisorException(errorType.toString());

                }
                ReviewExpress.CancelReservationRS cancelReservationRS = gson.fromJson(responseString, ReviewExpress.CancelReservationRS.class);
                logger.debug(gson.toJson(cancelReservationRS));
                if (cancelReservationRS == null) {
                    throw new TripAdvisorException("Object parsed is null.");
                } else {
                    if (cancelReservationRS.getError() == null) {
                        status = ChannelQueueStatus.SUCCESS;
//                        return gson.toJson(cancelReservationRS);
                    } else {
                        throw new TripAdvisorException(cancelReservationRS.getError().toString());
                    }
                }
            } else {
                throw new TripAdvisorException("Http Response is null or Entity is null.");
            }
        } catch (IOException e) {
            logger.error(e);
            throw new TripAdvisorException(e);
        } catch (JsonSyntaxException e) {
            logger.error(e);
            throw new TripAdvisorException(e);
        } finally {
            client.getConnectionManager().shutdown();
            try {
                integrationBeanLocal.reportAnConnection(hotelTicker, reservationId,
                        ChannelTicker.TRIPADVISOR, ChannelConnectionType.CANCEL_MAIL_REQUEST, status, tripAdvisorId, responseString);
//                Integer itemId = integrationBeanLocal.reportAnConnection(hotelTicker, reservationId,
//                        ChannelTicker.TRIPADVISOR, ChannelConnectionType.CANCEL_MAIL_REQUEST, status);
//                String requestCommunicationId = integrationBeanLocal.generateRequestCommunicationId(itemId);
//                String responseCommunicationId = integrationBeanLocal.generateResponseCommunicationId(itemId);
//                logger.info(requestCommunicationId + " " + tripAdvisorId);
//                logger.info(responseCommunicationId + " " + responseString);
            } catch (IntegrationException ex) {
                logger.error(ex);
            }
        }
    }

    /**
     * @param reservationId
     * @param hotelTicker
     * @return
     * @throws TripAdvisorException
     */
    public void createReservation(String reservationId, String hotelTicker) throws TripAdvisorException {
        String responseString = null;
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        String request = null;
        DefaultHttpClient client = null;
        try {
            client = new DefaultHttpClient();
            final ReviewExpress.CreateReviewExpressRQ create = getInformation(reservationId, hotelTicker);
            List<ReviewExpress.CreateReviewExpressRQ> ret = new ArrayList<ReviewExpress.CreateReviewExpressRQ>();
            ret.add(create);
            request = gson.toJson(ret);
            final HttpResponse httpResponse;
            httpResponse = sendPostMessage(client, ConstantsTripAdvisor.URL_CREATE_REQUEST, request);
            if (httpResponse != null && httpResponse.getEntity() != null) {
                responseString = EntityUtils.toString(httpResponse.getEntity());
                try {
                    final ReviewExpress.CreateReviewExpressRS[] responseObj = gson.fromJson(responseString, ReviewExpress.CreateReviewExpressRS[].class);
                    String responseToReturn = gson.toJson(responseObj);
                    logger.debug(responseToReturn);
                    if (responseObj != null) {
                        //Se supone que solo habra una reservado.
                        for (final ReviewExpress.CreateReviewExpressRS createReviewExpressRS : responseObj) {
                            if (createReviewExpressRS != null) {
                                final String requestId = createReviewExpressRS.getRequest_id();
                                if (createReviewExpressRS.getError() != null) {
                                    throw new TripAdvisorException(createReviewExpressRS.getError().toString());
                                } else if (requestId == null) {
                                    throw new TripAdvisorException("requestId got is null.");
                                } else {
                                    final int rowId;
                                    try {
                                        rowId = storeTripAdvisorId(hotelTicker, reservationId, requestId);
                                        status = ChannelQueueStatus.SUCCESS;
                                        logger.debug("rowID: " + rowId);
                                    } catch (DBAccessException e) {
                                        logger.error(e);
                                        throw new TripAdvisorException(e);
                                    } catch (NonexistentValueException e) {
                                        logger.error(e);
                                        throw new TripAdvisorException(e);
                                    } catch (ExternalFileException e) {
                                        logger.error(e);
                                        throw new TripAdvisorException(e);
                                    }
                                }
                            } else {
                                //Se supone que solo se envia una reserva, por eso se devuelve esto, porque la unica reserva es nula.
                                throw new TripAdvisorException("Object parsed is null.");
                            }
                        }
//                        return responseToReturn;
                    } else {
                        throw new TripAdvisorException("Object parsed is null.");
                    }
                } catch (JsonSyntaxException ex) {
                    try {
                        final ReviewExpress.ErrorType errorType = gson.fromJson(responseString,
                                ReviewExpress.ErrorType.class);
                        if (errorType != null && errorType.getError() != null) {
                            logger.error(gson.toJson(errorType));
                            throw new TripAdvisorException(errorType.toString());
                        }
                        logger.error(ex);
                        throw new TripAdvisorException(ex);
                    } catch (Exception e) {
                        logger.error(ex);
                        throw new TripAdvisorException(ex);
                    }
                }
            } else {
                throw new TripAdvisorException("Http Response is null or Entity is null.");
            }
        } catch (IOException e) {
            logger.error(e);
            throw new TripAdvisorException(e);
        } finally {
            HttpConnectionUtils.close(client);
            try {
                integrationBeanLocal.reportAnConnection(hotelTicker, reservationId,
                        ChannelTicker.TRIPADVISOR, ChannelConnectionType.CREATE_MAIL_REQUEST, status, request, responseString);
//                Integer itemId = integrationBeanLocal.reportAnConnection(hotelTicker, reservationId,
//                        ChannelTicker.TRIPADVISOR, ChannelConnectionType.CREATE_MAIL_REQUEST, status);
//                String requestCommunicationId = integrationBeanLocal.generateRequestCommunicationId(itemId);
//                String responseCommunicationId = integrationBeanLocal.generateResponseCommunicationId(itemId);
//                logger.info(requestCommunicationId + " " + request);
//                logger.info(responseCommunicationId + " " + responseString);
            } catch (IntegrationException ex) {
                logger.error(ex);
            }
        }
    }

    public void updateReservation(String reservationId, String hotelTicker) throws TripAdvisorException {
        String request = null;
        DefaultHttpClient client = null;
        String responseString = null;
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        try {
            final ReviewExpress.CreateReviewExpressRQ update = getInformation(reservationId, hotelTicker);
            client = new DefaultHttpClient();
            request = gson.toJson(update);
            final HttpResponse httpResponse = putMessage(client, ConstantsTripAdvisor.URL_UPDATE_REQUEST + getTripAdvisorId(hotelTicker,
                    reservationId), request);
            if (httpResponse != null && httpResponse.getEntity() != null) {
                responseString = EntityUtils.toString(httpResponse.getEntity());
                if (responseString != null) {
                    final ReviewExpress.ErrorType errorType = gson.fromJson(responseString,
                            ReviewExpress.ErrorType.class);
                    if (errorType != null && errorType.getError() != null) {
                        logger.error(gson.toJson(errorType));
//                        return gson.toJson(errorType);
                        throw new TripAdvisorException(errorType.toString());
                    }
                    final ReviewExpress.UpdateReserveRS response = gson.fromJson(responseString, ReviewExpress.UpdateReserveRS.class);
                    logger.debug(gson.toJson(response));
                    if (response != null && response.getError() != null) {
                        status = ChannelQueueStatus.SUCCESS;
//                    if (response != null) {
//                        return gson.toJson(response);
                    } else {
                        throw new TripAdvisorException("Object parsed is null.");
                    }
                } else {
                    throw new TripAdvisorException("Response got is null.");
                }
            } else {
                throw new TripAdvisorException("Connection is null.");
            }
        } catch (IOException e) {
            logger.error(e);
            throw new TripAdvisorException(e);
        } finally {
            HttpConnectionUtils.close(client);
            try {
                integrationBeanLocal.reportAnConnection(hotelTicker, reservationId,
                        ChannelTicker.TRIPADVISOR, ChannelConnectionType.UPDATE_MAIL_REQUEST, status, request, responseString);
//                Integer itemId = integrationBeanLocal.reportAnConnection(hotelTicker, reservationId,
//                        ChannelTicker.TRIPADVISOR, ChannelConnectionType.UPDATE_MAIL_REQUEST, status);
//                String requestCommunicationId = integrationBeanLocal.generateRequestCommunicationId(itemId);
//                String responseCommunicationId = integrationBeanLocal.generateResponseCommunicationId(itemId);
//                logger.info(requestCommunicationId + " " + request);
//                logger.info(responseCommunicationId + " " + responseString);
            } catch (IntegrationException ex) {
                logger.error(ex);
            }
        }
    }

    private ReviewExpress.CreateReviewExpressRQ getInformation(String reservationId, String hotelTicker) throws TripAdvisorException {
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            final ReservationDBHandler reservationDBHandler = new ReservationDBHandler(dbConnection);
            final Reservation reservation = reservationDBHandler.getReservationByReservationId(reservationId);
            if (reservation == null) {
                throw new TripAdvisorException(new NullPointerException("The Reservation doesn't exist."), "The Reservation doesn't exist.");
            }
            if (reservation.getCustomer() == null) {
                throw new TripAdvisorException(new NullPointerException("Customer is null."), "Customer is null.");
            }
            final String email = reservation.getCustomer().getEmail();
            final String country = reservation.getCustomer().getCountry();
            final String language = StringUtils.arrayContainsCaseInsensitive(ConstantsTripAdvisor.VALID_LANGUAGES, reservation.getLanguage())
                    ? reservation.getLanguage()
                    : "en_UK";
            Date checkIn = null, checkOut = null;
            for (final RoomStay roomStay : reservation.getRoomStays()) {
                final Date dateCheckIn = roomStay.getDateCheckIn();
                if (checkIn == null || checkIn.after(dateCheckIn)) {
                    checkIn = dateCheckIn;
                }
                final Date dateCheckOut = roomStay.getDateCheckOut();
                if (checkOut == null || checkOut.before(dateCheckOut)) {
                    checkOut = dateCheckOut;
                }
                if (checkOut.before(new Date())) {
                    throw new TripAdvisorException("checkout date before today.");
                }
            }
            return new ReviewExpress.CreateReviewExpressRQ(hotelTicker,
                    email, checkIn, checkOut, language, country == null ? ConstantsTripAdvisor.COUNTRY : country);
        } catch (MiddlewareException e) {
            logger.error(e);
            throw new TripAdvisorException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    /**
     * An example of how to respond to a config request
     */
    public String config() {
        // need to build a configuration with multiple parameters so declare it early
        ConfigurationV4.Builder configV4 = new ConfigurationV4.Builder();
        String response = null;
        ChannelQueueStatus requestStatus = ChannelQueueStatus.SUCCESS;
        try {
            //put all builders in a try block in order to send any unknown errors back in an error response
            try {
                // test contacts
                ContactV4 informationContact = new ContactV4.Builder()
                        .email(MiddlewareProperties.SUPPORT_MAIL_ES)
                        .fullName(MiddlewareProperties.SUPPORT_FULL_NAME)
                        .phoneNumber(MiddlewareProperties.WITBOOKING_PHONE)
                        .build();

                ImmutableList.Builder<ContactV4> informationalContactsBuilder = ImmutableList.builder();
                informationalContactsBuilder.add(informationContact);
                configV4.infoContacts(informationalContactsBuilder.build());

                // It is not necessary to use an immutable list, but it is not bad practice
                ImmutableList.Builder<ContactV4> emergencyContactsBuilder = ImmutableList.builder();
                emergencyContactsBuilder.add(new ContactV4.Builder().email(MiddlewareProperties.INTEGRATION_MANAGER_EMAIL).fullName
                        (MiddlewareProperties.INTEGRATION_MANAGER_FULL_NAME)
                        .phoneNumber(MiddlewareProperties.WITBOOKING_PHONE).build());
                emergencyContactsBuilder.add(informationContact);
                configV4.emergencyContacts(emergencyContactsBuilder.build());

                // languages
                configV4.languages(ImmutableList.of("es", "ca", "en", "fr", "de"));

                // API preferences
                configV4.fiveMinuteRateLimit(2000).prefHotels(10);

                ConfigurationResponseV4.Builder configResponseBuilder = new ConfigurationResponseV4.Builder().configuration(configV4.build());

                response = configResponseBuilder.build().toJson();
            } catch (Exception e) {
                response = ErrorV4.generateErrorResponse(e);
                requestStatus = ChannelQueueStatus.FAIL;
            }
            return response;
        } finally {
            try {
                final String request = " Esta llamada no recibe parametros de request.";
                integrationBeanLocal.storeSingleConnection(null, ChannelTicker.TRIPADVISOR,
                        ChannelConnectionType.OFFER_HOTEL_NAME, requestStatus, null, request, response);
//                final Integer id = integrationBeanLocal.storeSingleConnection(null, ChannelTicker.TRIPADVISOR,
//                        ChannelConnectionType.OFFER_HOTEL_NAME, requestStatus, null);
//                final String requestCommunicationId = integrationBeanLocal.generateRequestCommunicationId(id);
//                final String responseCommunicationId = integrationBeanLocal.generateResponseCommunicationId(id);
//                logger.info(requestCommunicationId + " Esta llamada no recibe parametros de request.");
//                logger.info(responseCommunicationId + response);
            } catch (IntegrationException e) {
                logger.error(e);
            }
        }
    }

    /**
     * An example of how to respond to a hotel inventory request
     */
    public String hotelInventory(String api_version, String lang) throws RemoteServiceException {
        String response = null;
        ChannelQueueStatus requestStatus = ChannelQueueStatus.FAIL;
        try {
            // verify the api version is valid
            if (api_version == null) {
                //TODO: manage error return
                response = ErrorV4.ErrorUnknown().toJson();
                return response;
            }

            Integer nApiVersion = Integer.parseInt(api_version);
            if (nApiVersion != Versions.VERSION_4) {
                //TODO: manage error return
                response = ErrorV4.ErrorUnknown().toJson();
                return response;
            }
            List<String> hotelTickers = null;

            WitMetaDataDBHandler witMetaDataDBHandler = null;
            try {
                witMetaDataDBHandler = new WitMetaDataDBHandler();
                hotelTickers = witMetaDataDBHandler.getHotelListProduction();
            } catch (Exception e) {
                logger.error(e);
                throw new RemoteServiceException(e);
            } finally {
                if (witMetaDataDBHandler != null) {
                    try {
                        witMetaDataDBHandler.closeDbConnection();
                    } catch (DBAccessException e) {
                        logger.error(e);
                        throw new RemoteServiceException(e);
                    }
                }
            }
            logger.debug("hotelTickers: " + hotelTickers.size());
            if (hotelTickers == null || hotelTickers.isEmpty()) {
                response = ErrorV4.ErrorHotelListIsEmpty().toJson();
                return response;
            }
            //put all builders in a try block in order to send any unknown errors back in an error response
            try {
                List<InventoryHotelV4> hotels = new ArrayList<InventoryHotelV4>();
                Set<String> urlHotelPages = new HashSet<String>();
                //TODO: Manage Amenities
                for (String hotelTicker : hotelTickers) {
                    DBConnection dbConnection = null;
                    try {

                        logger.debug("HotelTicker: " + hotelTicker);
                        DBCredentials dbCredential = getDBCredentials(hotelTicker);
                        dbConnection = new DBConnection(dbCredential);
                        HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
                        String[] language = getLanguage(hotelConfigurationDBHandler, lang);
                        InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, language[1]);
                        Map<String, InventoryRoomTypeV4> roomTypes = null;
                        //TODO: This is if we have to send the Inventory List
//                    List<Inventory> inventoryList = inventoryDBHandler.getInventoriesActives();
//                    if (!inventoryList.isEmpty()) {
//                        for (Inventory inventory : inventoryList) {
//                            roomTypes = Maps.newHashMap();
//                            roomTypes.put(inventory.getName(), new InventoryRoomTypeV4(new InventoryRoomTypeV4.Builder()
//                                    .desc(inventory.getAccommodation().getDescription())));
//                            //TODO: Others properties
//                            //.URL_CHECK_OPTIONS_IN("https://www.withotels.com/rooms")
//                        }
//                    }
                        List<Accommodation> accommodationList = inventoryDBHandler.getAccommodationsActives();
                        if (!accommodationList.isEmpty()) {
                            roomTypes = Maps.newHashMap();
                            for (Accommodation accommodation : accommodationList) {
                                InventoryRoomTypeV4.Builder roombuilder = new InventoryRoomTypeV4.Builder();
                                if (accommodation.getDescription().length() <= 500) {
                                    roombuilder.desc(accommodation.getDescription());
                                } else {
                                    roombuilder.desc(accommodation.getName() + " Description.");
                                }
                                roomTypes.put(accommodation.getName(), new InventoryRoomTypeV4(
                                        roombuilder));
                                //TODO: Others properties
                                //.URL_CHECK_OPTIONS_IN("https://www.withotels.com/rooms")
                            }
                        }
                        Hotel establishment = (Hotel) inventoryDBHandler.getHotel();
                        String urlLink;
                        urlLink = hotelConfigurationDBHandler.getWebHotelURL();
                        //If the hotel don't have Web URL, we send the reservation web in step1
                        if (urlLink == null || urlLink.isEmpty() || urlLink.contains("www.witbooking.com") ||
                                (!urlLink.startsWith("http://") && !urlLink.startsWith("https://"))) {
                            urlLink = MiddlewareProperties.URL_WITBOOKER_V6
                                    + "select/" + hotelTicker + "/" + language[0] + "/reservationsv6/step1";
                        }
                        if (urlHotelPages.contains(urlLink)) {
                            urlLink = urlLink + "#" + hotelTicker;
                        }
                        urlHotelPages.add(urlLink);
                        InventoryHotelV4.Builder builder = new InventoryHotelV4.Builder();
                        builder.city(establishment.getCity());
                        builder.country(establishment.getCountry());
                        if (establishment.getDescription().length() <= 500) {
                            builder.desc(establishment.getDescription());
                        } else {
                            builder.desc(establishment.getName() + " Description.");
                        }
                        builder.email(establishment.getEmailAdmin());
                        if (establishment.getLatitude() != null &&
                                establishment.getLongitude() != null &&
                                establishment.getLatitude() > 0 &&
                                establishment.getLongitude() > 0) {
                            builder.latitude(establishment.getLatitude());
                            builder.longitude(establishment.getLongitude());
                        }
                        builder.name(establishment.getName());
                        builder.partnerId(hotelTicker);
                        builder.phone(establishment.getPhone());
                        if (establishment.getAddress() != null && !establishment.getAddress().isEmpty()) {
                            builder.street(establishment.getAddress());
                        } else {
                            builder.street(establishment.getCity() + ", " + establishment.getZone());
                        }
                        builder.url(urlLink);
                        builder.roomTypes(roomTypes);

                        //TODO: Others properties
                        //builder.state(establishment.getZone());
                        //builder..fax(establishment.getFax())
                        //builder..postalCode(establishment.getPostalCode())
                        //builder..taId(258705)
                        try {
                            InventoryHotelV4 inventoryHotel = builder.build();
//                        logger.debug("HotelTicker Added: " + hotelTicker);
                            hotels.add(inventoryHotel);
                        } catch (Exception ex) {
                            logger.error("Error in the TripAdvisor Service. InventoryHotel: " +
                                    "[Hotel: '" + hotelTicker + "']");
                            logger.error(ex);
                        }
                        inventoryDBHandler.closeDbConnection();
                    } catch (Exception ex) {
                        logger.error(ex.getMessage());
                        if (dbConnection != null) {
                            try {
                                dbConnection.closeConnection();
                            } catch (DBAccessException e) {
                                logger.error(e);
                                throw new RemoteServiceException(e);
                            }
                        }
                        //If we find an error, we continue with the next hotel
                        //throw new RemoteServiceException(ex);
                    }
                }

                logger.debug("hotels: " + hotels.size());

                // build the inventory response, the majority of the work will be done retrieving the wanted hotels from the database
                InventoryResponseV4.Builder inventoryResponseBuilder = new InventoryResponseV4.Builder()
                        .lang(lang)
                        .hotels(hotels);


                response = inventoryResponseBuilder.build().toJson();
                requestStatus = ChannelQueueStatus.SUCCESS;
            } catch (RemoteServiceException e) {
                response = ErrorV4.generateErrorResponse(e);
            }
            return response;
        } finally {
            try {
                final String request = "Request{ \"api_version\" : \"" + api_version + "\" , \"lang\" :" + lang + "\" }";
                integrationBeanLocal.storeSingleConnection(null, ChannelTicker.TRIPADVISOR,
                        ChannelConnectionType.OFFER_INVENTORY, requestStatus, null, request, response);
//                final Integer id = integrationBeanLocal.storeSingleConnection(null, ChannelTicker.TRIPADVISOR,
//                        ChannelConnectionType.OFFER_ARI, requestStatus, null);
//                final String requestCommunicationId = integrationBeanLocal.generateRequestCommunicationId(id);
//                final String responseCommunicationId = integrationBeanLocal.generateResponseCommunicationId(id);
//                logger.info(requestCommunicationId + request);
//                logger.info(responseCommunicationId + response);
            } catch (IntegrationException e) {
                logger.error(e);
            }
        }
    }

    /**
     * An example of the most common request, a HotelAvailabilityRequest
     */
    public String hotelAvailability(String apiVersion, String hotelsString, String startDateString, String endDateString,
                                    String numAdultsString, String numRoomsString, String langString, String currencyString,
                                    String userCountryString, String deviceTypeString, String queryKeyString) {

        AvailabilityResponseV4.Builder availabilityResponseBuilder = new AvailabilityResponseV4.Builder();

        // parsing the hotel availability request is needed to echo back all of the required parameters and fetch the correct hotels
        HotelAvailabilityRequestV4 requestV4 = null;
        String response = null;
        ChannelQueueStatus requestStatus = ChannelQueueStatus.FAIL;
        final Set<EntryQueueItem> items = new HashSet<EntryQueueItem>();
        String hotelTicker = "";
        try {
            try {
                requestV4 = new HotelAvailabilityRequestV4(apiVersion, hotelsString, startDateString, endDateString, numAdultsString, numRoomsString, langString, currencyString, userCountryString, deviceTypeString, queryKeyString);
            } catch (Exception e) {
                e.printStackTrace();
                List<ErrorV4> errors = new ArrayList<>();
                ErrorV4 error = new ErrorV4.Builder().errorCode(ErrorV4.CANNOT_PARSE_REQUEST).message("Unable to parse request").timeout(0).build();
                errors.add(error);
                availabilityResponseBuilder.errors(errors);

                response = availabilityResponseBuilder.buildWithoutValidation().toJson();
                return response;
            }

            //put all builders in a try block in order to send any unknown errors back in an error response
            try {
                // in order to populate the hotels list they will need to be retrieved from your database
                List<AvailabilityHotelV4> availabilityHotel = new ArrayList<>();
                final Date startDate = requestV4.getStartDate();
                final Date endDate = (Date) requestV4.getEndDate().clone();
                final int nights = DateUtil.daysBetweenDates(startDate, endDate);
                //I don't calculate the rates and Availability for the check out day
                DateUtil.incrementDays(endDate, -1);
                String language = requestV4.getLang();
                List<Integer> hotelIds = new ArrayList<>();

                final int numRooms = requestV4.getNumRooms();
                final int adults = requestV4.getAdults();
                final String queryKey = requestV4.getQueryKey();
                for (final AvailabilityRequestHotelV4 hotelRequested : requestV4.getHotels()) {
                    DBConnection dbConnection = null;
                    hotelTicker = hotelRequested.getPartnerId();
                    items.add(new EntryQueueItem(hotelTicker, startDate, endDate));
                    final Integer hotelRequestedTaId = hotelRequested.getTaId();
                    try {
                        dbConnection = new DBConnection(getDBCredentials(hotelTicker));
                        final String[] languageTuple = getLanguage(new HotelConfigurationDBHandler(dbConnection), language);
                        InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, languageTuple[1]);
                        language = languageTuple[0];
                        DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, startDate, endDate);
                        dailyValuesDBHandler.getInventoryValuesBetweenDates();
                        String currencyCode = requestV4.getCurrency().getCurrencyCode();
                        //Ask if the currency requested is Valid in WitBooker
                        Currency currency = null;
                        try {
                            HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
                            List<com.witbooking.middleware.model.Currency> currencies = hotelConfigurationDBHandler.getActivesCurrencies();
                            for (com.witbooking.middleware.model.Currency currencyModel : currencies) {
                                if (currencyModel.getCode().equals(currencyCode)) {
                                    dailyValuesDBHandler.getCurrencyMultiplier(currencyCode);
                                    currency = Currency.getInstance(currencyCode);
                                    break;
                                }
                            }
                        } catch (Exception e) {
                            logger.debug("Currency unknown: " + currency + " mg: " + e);
                        }
                        if (currency == null) {
                            logger.debug("CurrencyCode unknown: " + currencyCode);
                            try {
                                currencyCode = dailyValuesDBHandler.getDefaultCurrency();
                                currency = Currency.getInstance(currencyCode);
                            } catch (Exception ex) {
                                logger.debug("Currency unknown: " + currencyCode + " mg: " + ex);
                                currencyCode = "EUR";
                                currency = Currency.getInstance(currencyCode);
                            }
                        }
                        List<Inventory> inventoryList = inventoryDBHandler.getInventoriesByAdults(adults);
                        final String urlLink = MiddlewareProperties.URL_WITBOOKER_V6
                                + "select/" + hotelTicker
                                + "/" + language + "/reservationsv6/step1"
                                + "?fini=" + DateUtil.calendarFormat(startDate)
                                + "&noches=" + nights
                                + "&setconversion=" + currencyCode
                                + "&tracking_id=" + queryKey
                                + "&channel=" + ConstantsTripAdvisor.CHANNEL_NAME
                                + "&adultos=" + adults;
                        Map<String, AvailabilityRoomTypeV4> roomTypes = Maps.newHashMap();
                        int hourNotice;
                        try {
                            hourNotice = DateUtil.hoursBetweenDates(DateUtil.stringToCalendarDate(DateUtil.calendarFormat
                                    (new Date())), startDate);
                        } catch (DateFormatException e) {
                            hourNotice = DateUtil.hoursBetweenDates(new Date(), startDate);
                            logger.error(e);
                        }

                        if (!inventoryList.isEmpty()) {
                            for (final Inventory inventory : inventoryList) {
                                boolean validInv = true;
                                RangeValue<Float> ratesByTicker = null;
                                //Checking restrictions
                                //There should be availability grater than zero, And isn't locked, etc...
                                try {
                                    if (validInv) {
                                        final int checkInDay = (DateUtil.getDayOfWeek(startDate) % 7);
                                        if (!inventory.getCheckInDays().getDayValue(checkInDay)) {
                                            validInv = false;
                                        }
                                    }
                                    if (validInv) {
                                        final int checkOutDay = (DateUtil.getDayOfWeek(endDate) % 7);
                                        if (!inventory.getCheckOutDays().getDayValue(checkOutDay)) {
                                            validInv = false;
                                        }
                                    }
                                    if (validInv) {
                                        RangeValue<Integer> availability = dailyValuesDBHandler.getAvailabilityByTicker(inventory.getTicker());
                                        if (availability.hasValueEqualsTo(0)) {
                                            validInv = false;
                                        }
                                    }
                                    if (validInv) {
                                        RangeValue<Boolean> lock = dailyValuesDBHandler.getLockByTicker(inventory.getTicker());
                                        if (lock != null && lock.hasValueEqualsTo(true)) {
                                            validInv = false;
                                        }
                                    }
                                    if (validInv) {
                                        ratesByTicker = dailyValuesDBHandler.getFullRatesByTicker(inventory.getTicker(), currencyCode);
                                        if (ratesByTicker.hasValueEqualsToFloat(0)) {
                                            validInv = false;
                                        }
                                    }
                                    if (validInv) {
                                        RangeValue<Integer> minStay = dailyValuesDBHandler.getMinStayByTicker(inventory.getTicker());
                                        if (minStay != null && minStay.getFinalValueForADate(startDate) != null &&
                                                minStay.getFinalValueForADate(startDate) > nights) {
                                            validInv = false;
                                        }
                                    }
                                    if (validInv) {
                                        RangeValue<Integer> maxStay = dailyValuesDBHandler.getMaxStayByTicker(inventory
                                                .getTicker());
                                        if (maxStay != null && maxStay.getFinalValueForADate(startDate) != null &&
                                                maxStay.getFinalValueForADate(startDate) < nights) {
                                            validInv = false;
                                        }
                                    }
                                    if (validInv) {
                                        RangeValue<Integer> minNotice = dailyValuesDBHandler.getMinNoticeByTicker(inventory
                                                .getTicker());
                                        if (minNotice != null && minNotice.getFinalValueForADate(startDate) != null &&
                                                minNotice.getFinalValueForADate(startDate) > hourNotice) {
                                            validInv = false;
                                        }
                                    }
                                    if (validInv) {
                                        RangeValue<Integer> maxNotice = dailyValuesDBHandler.getMaxNoticeByTicker(inventory
                                                .getTicker());
                                        if (maxNotice != null && maxNotice.getFinalValueForADate(startDate) != null &&
                                                maxNotice.getFinalValueForADate(startDate) < hourNotice) {
                                            validInv = false;
                                        }
                                    }
                                } catch (Exception e) {
                                    logger.error("Error in the TripAdvisor Service. RoomType: " +
                                            "[Hotel: '" + hotelTicker + "', Inventory: '" +
                                            inventory.getTicker() + ", requestV4: " +
                                            requestV4 + ", mg:" + e + "']");
                                    for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                                        logger.error(" " + stackTraceElement);
                                    }
                                    validInv = false;
                                }
                                if (validInv) {
                                    float rate = 0;
                                    for (DailyValue<Float> dayValue : ratesByTicker.getDailySet()) {
                                        rate = rate + (dayValue.getValue() * (dayValue.daysBetweenDates() + 1));
                                    }
                                    //rate = ratesByTicker.getSumValues();
                                    float taxes = 0;
                                    for (final Tax tax : inventoryDBHandler.getTaxes()) {
                                        final float value = tax.getValue();
                                        taxes = taxes + (value > 1 && value < 100 ? value * rate / 100 : value * rate);
                                    }
                                    BigDecimal priceWithOutTaxes = new BigDecimal(rate - taxes);
                                    BigDecimal priceWithTaxes = new BigDecimal(rate);
                                    BigDecimal taxesAtCheckout = new BigDecimal(taxes);
                                    List<RoomDiscountV4> discounts = new ArrayList<RoomDiscountV4>();
                                    //TODO: check the code for add the discounts correctly
                                /*
                                for (final Map.Entry<Discount, Float> discountDummy :
                                        dailyValuesDBHandler.getFinalDiscountApplied(inventory, currencyCode, null).entrySet()) {
                                    BigDecimal reduction = new BigDecimal(Math.round(Math.abs(discountDummy.getKey().getReduction())));
                                    final boolean percentage = discountDummy.getKey().isPercentage();
                                    final Float discountDummyValue = discountDummy.getValue();
                                    BigDecimal finalPrice = new BigDecimal(discountDummyValue);
                                    finalPrice = finalPrice.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                                    reduction = reduction.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                                    try {
                                        RoomDiscountV4 discount = new RoomDiscountV4.Builder()
                                                .marketingText(discountDummy.getKey().getName())
                                                .isPercent(percentage)
                                                .amount(percentage ? reduction : finalPrice)
                                                .price(finalPrice)
                                                .fees(BigDecimal.ZERO)
                                                .feesAtCheckout(BigDecimal.ZERO)
                                                .taxes(BigDecimal.ZERO)
                                                .taxesAtCheckout(BigDecimal.ZERO)
                                                .finalPrice(finalPrice)
                                                .build();
//                                logger.debug("discount added: "+discount.getFinalPrice()+" "+discountDummyValue+" finalPrice: "+finalPrice);
                                        discounts.add(discount);

                                    } catch (Exception ex) {
                                        logger.error("Error in the TripAdvisor Service. RoomDiscount: " +
                                                "[Hotel: '" + hotelTicker + "', Inventory: '" +
                                                inventory.getTicker() + "', Discount: '" +
                                                discountDummy.getKey().getTicker() + "']");
                                        logger.error(ex);
                                    }
                                }
                                //*/
                                    final AvailabilityRoomTypeV4.Builder roomBuilder = new AvailabilityRoomTypeV4.Builder();
                                    if (!discounts.isEmpty()) {
                                        roomBuilder.discounts(discounts);
//                                logger.error("Discounts added: " + discounts);
                                    } else {
//                                logger.debug("Discounts is empty");
                                    }
                                    //Validations with default values
                                    if (priceWithTaxes == null) {
                                        priceWithTaxes = new BigDecimal(0);
                                    }
                                    //add to the URL the number of children and babies
                                    String urlExtended = urlLink;
                                    urlExtended = urlExtended + "&ninos=" + inventory.getConfiguration().getChildren();
                                    urlExtended = urlExtended + "&bebes=" + inventory.getConfiguration().getInfants();
                                    urlExtended = urlExtended + "&tickerInv=" + inventory.getTicker();
                                    //Added to the URL the Tracking for Google Analytics
                                    urlExtended = urlExtended + "&" + TRACKING_ANALYTICS;
                                    priceWithOutTaxes = priceWithOutTaxes.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                                    priceWithTaxes = priceWithTaxes.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                                    taxesAtCheckout = taxesAtCheckout.setScale(2, BigDecimal.ROUND_HALF_EVEN);
                                    roomBuilder.price(priceWithOutTaxes)
                                            .finalPrice(priceWithTaxes)
                                            .fees(BigDecimal.ZERO)
                                            .feesAtCheckout(BigDecimal.ZERO)
                                            .taxesAtCheckout(BigDecimal.ZERO)
                                            .taxes(taxesAtCheckout)
                                            .numRooms(numRooms)
                                            .url(urlExtended)
                                            .currency(currency)
                                    ;
                                    final String roomTypeName = inventory.getFullName();
                                    if (priceWithOutTaxes != null && priceWithTaxes != null
                                            && priceWithOutTaxes.floatValue() > 0 && priceWithTaxes.floatValue() > 0) {
                                        try {
                                            if (roomTypeName.length() <= 95) {
                                                roomTypes.put(roomTypeName, roomBuilder.build());
                                            } else {
                                                roomTypes.put(roomTypeName.substring(0, 93) +
                                                        "_" + roomTypes.size(), roomBuilder.build());
                                            }
                                        } catch (Exception ex) {
                                            logger.error("Error in the TripAdvisor Service. RoomType: " +
                                                    "[Hotel: '" + hotelTicker + "', Inventory: '" +
                                                    inventory.getTicker() + "']");
                                            logger.error(ex);
                                        }
                                    }
                                }
                            }
                        }
                        if (roomTypes != null && roomTypes.size() > 0) {
                            try {
                                final AvailabilityHotelV4 hotel = new AvailabilityHotelV4.Builder()
                                        .hotelId(hotelRequestedTaId)
                                        .roomTypes(roomTypes)
                                        .build();
                                availabilityHotel.add(hotel);
                            } catch (Exception ex) {
                                logger.error("Error in the TripAdvisor Service. AvailabilityHotel: " +
                                        "[Hotel: '" + hotelTicker + "']");
                                logger.error(ex);
                            }
                        }
                        hotelIds.add(hotelRequestedTaId);
                    } catch (Exception ex) {
                        logger.error("Error in the TripAdvisor Service. [Hotel: '" + hotelTicker + "'] : " + ex);
                    } finally {
                        DAOUtil.close(dbConnection);
                    }
                }

                // the majority of the fields should be echoed from the request, so just take them directly
                availabilityResponseBuilder
                        .currency(requestV4.getCurrency())
                        .deviceType(requestV4.getDeviceType())
                        .startDate(requestV4.getStartDate())
                        .endDate(requestV4.getEndDate())
                        .lang(language)
                        .numAdults(adults)
                        .numRooms(numRooms)
                        .queryKey(queryKey)
                        .userCountry(requestV4.getUserCountry())
                        .hotelIds(hotelIds)
                        .hotels(availabilityHotel)
                        .numHotels(availabilityHotel.size());
                response = availabilityResponseBuilder.build().toJson();
            } catch (Exception e) {
                response = ErrorV4.generateErrorResponse(e);
            }
            requestStatus = ChannelQueueStatus.SUCCESS;
            return response;
        } finally {
            try {
                final String request = "HotelAvailabilityRequestV4{" +
                        "m_apiVersion=" + apiVersion +
                        ", m_hotels=" + hotelsString +
                        ", m_startDate=" + startDateString +
                        ", m_endDate=" + endDateString +
                        ", m_adults=" + numAdultsString +
                        ", m_numRooms=" + numRoomsString +
                        ", m_lang='" + langString + '\'' +
                        ", m_currency=" + currencyString +
                        ", m_userCountry='" + userCountryString + '\'' +
                        ", m_deviceType='" + deviceTypeString + '\'' +
                        ", m_queryKey='" + queryKeyString + '\'' +
                        '}';
                integrationBeanLocal.storeSingleConnection(hotelTicker, ChannelTicker.TRIPADVISOR,
                        ChannelConnectionType.OFFER_ARI, requestStatus, items, requestV4 == null ? request : requestV4, response);
//                final Integer id = integrationBeanLocal.storeSingleConnection(null, ChannelTicker.TRIPADVISOR,
//                        ChannelConnectionType.OFFER_ARI, requestStatus, items);
//                final String requestCommunicationId = integrationBeanLocal.generateRequestCommunicationId(id);
//                final String responseCommunicationId = integrationBeanLocal.generateResponseCommunicationId(id);
//                logger.info(requestCommunicationId + requestV4 == null ? request : requestV4);
//                logger.info(responseCommunicationId + response);
            } catch (IntegrationException e) {
                logger.error(e);
            }
        }
    }

    private HttpResponse sendGetMessage(final DefaultHttpClient client, final String url) throws IOException {
        final Properties properties = new Properties();
        properties.put("Content-Type", "application/json");
        properties.put("X-TripAdvisor-API-Key", MiddlewareProperties.TRIP_ADVISOR_KEY);
        logger.debug("Get url: " + url);
        return HttpConnectionUtils.getData(client, url, properties);
    }

    private HttpResponse deleteMessage(final DefaultHttpClient client, final String url) throws IOException {
        final Properties properties = new Properties();
        properties.put("Content-Type", "application/json");
        properties.put("X-TripAdvisor-API-Key", MiddlewareProperties.TRIP_ADVISOR_KEY);
        logger.debug("Put url: " + url);
        return HttpConnectionUtils.deleteData(client, url, properties);
    }

    private HttpResponse sendPostMessage(final DefaultHttpClient client, final String url, final String body) throws IOException {
        final Properties properties = new Properties();
        properties.put("Content-Type", "application/json");
        properties.put("X-TripAdvisor-API-Key", MiddlewareProperties.TRIP_ADVISOR_KEY);
        logger.debug("Post url: " + url);
        logger.debug("Post Body: " + body);
        return HttpConnectionUtils.postData(client, url, body, properties);
    }

    private HttpResponse putMessage(final DefaultHttpClient client, final String url, final String body) {
        final Properties properties = new Properties();
        properties.put("Content-Type", "application/json");
        properties.put("X-TripAdvisor-API-Key", MiddlewareProperties.TRIP_ADVISOR_KEY);
        logger.debug("Put url: " + url);
        logger.debug("Put Body: " + body);
        return HttpConnectionUtils.putData(client, url, body, properties);
    }

    private int storeTripAdvisorId(final String hotelTicker, final String reservationId, final String tripAdvisorId)
            throws DBAccessException, NonexistentValueException, ExternalFileException {
        final WitMetaDataDBHandler witMetaDataDBHandler = new WitMetaDataDBHandler();
        try {
            return witMetaDataDBHandler.createOrReplaceTripAdvisorReviewId(hotelTicker, reservationId, tripAdvisorId);
        } finally {
            DAOUtil.close(witMetaDataDBHandler.getDbConnection());
        }
    }

    private String getTripAdvisorId(final String hotelTicker, final String reservationId) throws TripAdvisorException {
        final WitMetaDataDBHandler witMetaDataDBHandler;
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
        } catch (ExternalFileException e) {
            logger.error(e);
            throw new TripAdvisorException(e);
        } catch (DBAccessException e) {
            logger.error(e);
            throw new TripAdvisorException(e);
        } catch (NonexistentValueException e) {
            logger.error(e);
            throw new TripAdvisorException(e);
        }
        try {
            try {
                return witMetaDataDBHandler.getTripAdvisorReviewId(hotelTicker, reservationId);
            } catch (DBAccessException e) {
                logger.error(e);
                throw new TripAdvisorException(e);
            }
        } finally {
            DAOUtil.close(witMetaDataDBHandler.getDbConnection());
        }
    }

    private DBCredentials getDBCredentials(String hotelTicker) throws RemoteServiceException {
        try {
            return DBProperties.getDBCustomerByTicker(hotelTicker);
        } catch (MiddlewareException ex) {
            logger.error("Error getting the DBCredential for the Hotel '" + hotelTicker + "': " + ex.getMessage());
            throw new RemoteServiceException(ex);
        }
    }


    private String[] getLanguage(HotelConfigurationDBHandler hotelConfigurationDBHandler, String language) throws DBAccessException {
        List<String[]> codes = hotelConfigurationDBHandler.getActiveLanguageCodes();
        for (String[] langCode : codes) {
            if (language.contains(langCode[0])) {
                return langCode;
            }
        }
        //If the hotel doesn't contents the languade, find for the ENG as default
        if (codes != null && !codes.isEmpty() && language.contains("en")) {
            for (String[] code : codes) {
                if ("en".equalsIgnoreCase(code[0])) {
                    return new String[]{"en", "eng"};
                }
            }
            return codes.get(0);
        }
        return new String[]{"es", "spa"};
    }
}
