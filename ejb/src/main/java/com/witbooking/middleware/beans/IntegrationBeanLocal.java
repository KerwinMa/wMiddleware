package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.integration.*;
import com.witbooking.middleware.model.Reservation;
import com.witbooking.middleware.model.channelsIntegration.Channel;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * IntegrationLocalBean.java
 * User: jose
 * Date: 11/4/13
 * Time: 5:58 PM
 */
@Local
public interface IntegrationBeanLocal {

//    public String getNewReservesFromBooking(String hotelTicker) throws IntegrationException;

    //    public String notifyNewEntry(String hotelTicker,ChannelTicker channelTicker,ChannelConnectionType type,
//                                       String reservationId,final Date executeRequestedDate)
//            throws IntegrationException;
    public String notifyNewEntry(String hotelTicker, ChannelTicker channelTicker, ChannelConnectionType type,
                                 String reservationId, final Date executeRequestedDate, final List<ChannelConnectionType> typesToCheck)
            throws IntegrationException;

    public String notifyNewEntryForItems(String hotelTicker, ChannelTicker channelTicker,
                                         ChannelConnectionType type, List<EntryQueueItem> items,
                                         final Date executionRequestedDate) throws IntegrationException;

    public String error(Exception ex);

    public String error(MiddlewareException ex);

    public String error(String errorMessage);

    public String success();

    public String successWithWarning(String warning);

    public Integer storeSingleConnection(final String hotelTicker, final ChannelTicker channelTicker,
                                         final ChannelConnectionType type, final ChannelQueueStatus status,
                                         Set<EntryQueueItem> items, final Object request, final Object response)
            throws IntegrationException;

//    public String generateRequestCommunicationId(final Integer id);
//
//    public String generateResponseCommunicationId(final Integer id);

    public Date getStartDayFromReservation(final String hotelTicker, final String reservationId)
            throws IntegrationException;

    public Boolean checkExists(String hotelTicker, String reservationId, ChannelTicker channelTicker,
                               List<ChannelConnectionType> typesToCheck)
            throws IntegrationException;

    public Set<EntryQueue> getEntriesQueue(final String hotelTicker, String reservationId, final ChannelTicker channelTicker,
                                           final CommunicationFinished finished,
                                           final List<ChannelConnectionType> types)
            throws IntegrationException;

    public List<Integer> changeStatus(final int entryQueueId, final ChannelQueueStatus status, final CommunicationFinished finished) throws IntegrationException;

    public Integer reportAnConnection(final String hotelTicker,
                                      final String reservationId, final ChannelTicker channelTicker,
                                      final ChannelConnectionType type, final ChannelQueueStatus status, final Object request, final Object response)
            throws IntegrationException;

    public Integer reportAnConnection(final String hotelTicker,
                                      final ChannelTicker channelTicker, final ChannelConnectionType type,
                                      final ChannelQueueStatus status, List<EntryQueueItem> items, final Object request,
                                      final Object response)
            throws IntegrationException;

    public String enqueueReservationForIntegration(final String reservationId, final String hotelTicker,
                                                   final Channel channel, final ChannelConnectionType type) throws IntegrationException;

    public String enqueueReservationPush(final String reservationId, final String hotelTicker, String type) throws IntegrationException;

    public void postReservationProcess(final List<Reservation> reservations, InventoryDBHandler inventoryDBHandler) throws IntegrationException;
}
