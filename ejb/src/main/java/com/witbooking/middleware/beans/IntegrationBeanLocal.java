package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.integration.*;
import com.witbooking.middleware.model.Reservation;

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
//                                       String reservationId,Date executeRequestedDate)
//            throws IntegrationException;
    public String notifyNewEntry(String hotelTicker, ChannelTicker channelTicker, ChannelConnectionType type,
                                 String reservationId, Date executeRequestedDate, List<ChannelConnectionType> typesToCheck)
            throws IntegrationException;

    public String notifyNewEntryForItems(String hotelTicker, ChannelTicker channelTicker,
                                         ChannelConnectionType type, List<EntryQueueItem> items,
                                         Date executionRequestedDate) throws IntegrationException;

    public String error(Exception ex);

    public String error(MiddlewareException ex);

    public String error(String errorMessage);

    public String success();

    public String successWithWarning(String warning);

    public Integer storeSingleConnection(String hotelTicker, ChannelTicker channelTicker,
                                         ChannelConnectionType type, ChannelQueueStatus status,
                                         Set<EntryQueueItem> items, Object request, Object response)
            throws IntegrationException;

//    public String generateRequestCommunicationId(Integer id);
//
//    public String generateResponseCommunicationId(Integer id);

    public Date getStartDayFromReservation(String hotelTicker, String reservationId)
            throws IntegrationException;

    public Boolean checkExists(String hotelTicker, String reservationId, ChannelTicker channelTicker,
                               List<ChannelConnectionType> typesToCheck)
            throws IntegrationException;

    public Set<EntryQueue> getEntriesQueue(String hotelTicker, String reservationId, ChannelTicker channelTicker,
                                           CommunicationFinished finished,
                                           List<ChannelConnectionType> types)
            throws IntegrationException;

    public List<Integer> changeStatus(int entryQueueId, ChannelQueueStatus status, CommunicationFinished finished) throws IntegrationException;

    public Integer reportAnConnection(String hotelTicker,
                                      String reservationId, ChannelTicker channelTicker,
                                      ChannelConnectionType type, ChannelQueueStatus status, Object request, Object response)
            throws IntegrationException;

    public Integer reportAnConnection(String hotelTicker,
                                      ChannelTicker channelTicker, ChannelConnectionType type,
                                      ChannelQueueStatus status, List<EntryQueueItem> items, Object request,
                                      Object response)
            throws IntegrationException;

    public String enqueueReservationPush(String reservationId, String hotelTicker, String type) throws IntegrationException;

    public void postReservationProcess(List<Reservation> reservations, InventoryDBHandler inventoryDBHandler) throws IntegrationException;
}
