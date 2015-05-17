/*
 *  TripAdvisorResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.tripadvisor;

import com.witbooking.middleware.exceptions.RemoteServiceException;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.exceptions.integration.tripadvisor.TripAdvisorException;
import com.witbooking.middleware.integration.*;
import com.witbooking.middleware.integration.tripadvisor.model.ErrorV4;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Insert description here
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 29, 2013
 */
@Stateless
public class TripAdvisorEnqueuer extends TripAdvisorBusinessLogic implements TripAdvisorEnqueuerLocal {

    private static final Logger logger = Logger.getLogger(TripAdvisorEnqueuer.class);

    @Override
    protected String createRequestReservationLogic(String reservationId, String hotelTicker) {
        logger.debug("reportReservation:: reservationId: " + reservationId + " hotelTicker: " + hotelTicker);
        String response;
        try {
            final Date firstCheckIn = integrationBeanLocal.getStartDayFromReservation(hotelTicker, reservationId);
            if (firstCheckIn == null) {
                throw new IntegrationException("First Day of CheckIn is null" + "reservationId:" + reservationId + " hotelTicker: " + hotelTicker);
            }
            final String warning = integrationBeanLocal.notifyNewEntry(hotelTicker, ChannelTicker.TRIPADVISOR, ChannelConnectionType.CREATE_MAIL_REQUEST, reservationId, firstCheckIn, null);
            response = warning == null ? integrationBeanLocal.success() : integrationBeanLocal.successWithWarning(warning);
        } catch (IntegrationException e) {
            logger.error(e);
            response = integrationBeanLocal.error(e);
        }
        return response;
    }

    @Override
    protected String updateRequestReservationLogic(String reservationId, String hotelTicker) {
        logger.debug("updateReservation:: reservationId: " + reservationId + " hotelTicker: " + hotelTicker);
        try {
            final Date firstCheckIn = integrationBeanLocal.getStartDayFromReservation(hotelTicker, reservationId);
            if (firstCheckIn == null) {
                throw new IntegrationException("First Day of CheckIn is null" + "reservationId:" + reservationId + " hotelTicker: " + hotelTicker);
            }
            List<ChannelConnectionType> typesToCheck = new ArrayList<ChannelConnectionType>();
            typesToCheck.add(ChannelConnectionType.CREATE_MAIL_REQUEST);
            final String warning = integrationBeanLocal.notifyNewEntry(hotelTicker, ChannelTicker.TRIPADVISOR, ChannelConnectionType.UPDATE_MAIL_REQUEST, reservationId, firstCheckIn, typesToCheck);
            return warning == null ? integrationBeanLocal.success() : integrationBeanLocal.successWithWarning(warning);
        } catch (IntegrationException e) {
            logger.error(e);
            return integrationBeanLocal.error(e);
        }
    }

    @Override
    protected String cancelRequestReservationLogic(String reservationId, String hotelTicker) {
        List<ChannelConnectionType> typesToCheck = new ArrayList<ChannelConnectionType>();
        typesToCheck.add(ChannelConnectionType.CREATE_MAIL_REQUEST);
        try {
            //Verifying create entries.
            if (integrationBeanLocal.checkExists(hotelTicker, reservationId, ChannelTicker.TRIPADVISOR, typesToCheck)) {
                String warning = "These columns have been updated:  ";
                //Getting the create entry id.
                final Set<EntryQueue> entriesQueue = integrationBeanLocal.getEntriesQueue(hotelTicker, reservationId,
                        ChannelTicker.TRIPADVISOR, CommunicationFinished.NOT_FINISHED, typesToCheck);
                //Updating these create entry to Canceled status.
                for (final EntryQueue entryQueue : entriesQueue) {
                    integrationBeanLocal.changeStatus(entryQueue.getId(), ChannelQueueStatus.CANCELED, CommunicationFinished.FINISHED);
                    warning += entryQueue.getId() + ", ";
                }
                warning = warning.substring(0, warning.length() - 2) + "; ";
                return integrationBeanLocal.successWithWarning(warning);

            } else {
                typesToCheck.clear();
                typesToCheck.add(ChannelConnectionType.UPDATE_MAIL_REQUEST);
                String warning = null;
                //Verifying update entries.
                if (integrationBeanLocal.checkExists(hotelTicker, reservationId, ChannelTicker.TRIPADVISOR, typesToCheck)) {
                    final Set<EntryQueue> entriesQueue = integrationBeanLocal.getEntriesQueue(hotelTicker, reservationId,
                            ChannelTicker.TRIPADVISOR, CommunicationFinished.NOT_FINISHED, typesToCheck);
                    warning = "These columns have been updated:  ";
                    for (final EntryQueue entryQueue : entriesQueue) {
                        final Integer id = entryQueue.getId();
                        integrationBeanLocal.changeStatus(id, ChannelQueueStatus.CANCELED, CommunicationFinished.FINISHED);
//                        final List<Integer> integers = integrationBeanLocal.changeStatus(id, ChannelQueueStatus.CANCELED,CommunicationFinished.FINISHED);
                        warning += id + ", ";
                    }
                    warning = warning.substring(0, warning.length() - 2) + "; ";
                }
                final Date lastCheckOut = integrationBeanLocal.getStartDayFromReservation(hotelTicker, reservationId);
                final String msgWarning = integrationBeanLocal.notifyNewEntry(hotelTicker,
                        ChannelTicker.TRIPADVISOR, ChannelConnectionType.CANCEL_MAIL_REQUEST, reservationId,
                        lastCheckOut, null);
                if ((warning != null && msgWarning != null)
                        || msgWarning != null) {
                    warning = warning == null ? msgWarning : warning + " " + msgWarning;
                }
                return warning != null
                        ? integrationBeanLocal.successWithWarning(warning)
                        : integrationBeanLocal.success();
            }
        } catch (IntegrationException e) {
            logger.error(e);
            return integrationBeanLocal.error(e);
        }
    }

    @Override
    public String getConfiguration() {
        return tripAdvisorLocal.config();
    }

    @Override
    public String getHotelInventory(String api_version, String lang) {
        try {
            return tripAdvisorLocal.hotelInventory(api_version, lang);
        } catch (RemoteServiceException e) {
            logger.error(e);
            return ErrorV4.generateErrorResponse(e);
        }
    }

    @Override
    public String getHotelAvailability(String apiVersion, String hotelsJson, String startDate, String endDate,
                                       String numAdults, String numRooms, String lang, String currency,
                                       String userCountry, String deviceType,
                                       String queryKey) throws TripAdvisorException {
//        String[] strings = {apiVersion + "", hotelsJson, startDate, endDate,
//            numAdults + "", numRooms + "", lang, currency,
//            userCountry, deviceType, queryKey};
//        for (String item : strings) {
//            logger.info("item: " + item);
//        }
        logger.debug("getHotelAvailabilityByPost");
        final String request = tripAdvisorLocal.hotelAvailability(apiVersion, hotelsJson, startDate, endDate, numAdults,
                numRooms, lang, currency, userCountry, deviceType, queryKey);
        return request;

    }

    @Override
    public String listAllReservations(String hotelTicker) {
        return super.listAllReservations(hotelTicker);
    }

    @Override
    public String listReservation(String hotelTicker, String reservationId) {
        return super.listReservation(hotelTicker, reservationId);
    }

    @Override
    public String reportReservation(String reservationId, String hotelTicker) {
        return super.reportReservation(reservationId, hotelTicker);
    }

    @Override
    public String updateReservation(String reservationId, String hotelTicker) {
        return super.updateReservation(reservationId, hotelTicker);
    }

    @Override
    public String cancelReservation(String reservationId, String hotelTicker) {
        return super.cancelReservation(reservationId, hotelTicker);
    }

    @Override
    public String checkOptionsIn(String hotelTicker) {
        return super.checkOptionsIn(hotelTicker);
    }

}
