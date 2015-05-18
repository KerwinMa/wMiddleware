/*
 *  BookingResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.booking;

import com.witbooking.middleware.exceptions.InvalidEntryException;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.integration.ChannelConnectionType;
import com.witbooking.middleware.integration.ChannelTicker;
import com.witbooking.middleware.integration.EntryQueueItem;
import com.witbooking.middleware.utils.DateUtil;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import java.util.*;

/**
 * Insert description here
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 21, 2013
 */
@Stateless
public class BookingEnqueuer extends BookingBusinessLogic implements BookingEnqueuerLocal {

    private static final Logger logger = Logger.getLogger(BookingEnqueuer.class);

    /**
     * Creates a new instance of
     * <code>BookingResource</code> without parameters.
     */
    public BookingEnqueuer() {
    }

    @Override
    protected String getNotificationLogic(String hotelId, String reservationId, Date lastChange, final ChannelConnectionType type) {
        String response;
        try {
            String item=null;
            if (reservationId != null)
                item = reservationId;
            else if (lastChange != null)
                item = DateUtil.timestampFormat(lastChange);
            final String warning = integrationBeanLocal.notifyNewEntry(hotelId, ChannelTicker.BOOKING, type,
                    item, lastChange, null);
            response = warning == null ? integrationBeanLocal.success() : integrationBeanLocal.successWithWarning(warning);
        } catch (IntegrationException e) {
            logger.error(e);
            response = integrationBeanLocal.error(e);
        }
        return response;
    }

    @Override
    protected String updateARILogic(String hotelTicker, List<String> inventoryTickers, Date start, Date end) {
        logger.debug("updateARILogic:: inventoryID: " + inventoryTickers + " hotelTicker: " + hotelTicker);
        String response;
        try {
            Set<EntryQueueItem> items = new HashSet<>();
            for (String inventoryTicker : inventoryTickers) {
                items.add(new EntryQueueItem(inventoryTicker, start, end));
            }
            final String warning = integrationBeanLocal.notifyNewEntryForItems(hotelTicker, ChannelTicker.BOOKING,
                    ChannelConnectionType.EXECUTE_UPDATE_ARI, new ArrayList<>(items), null);
            response = warning == null ? integrationBeanLocal.success() : integrationBeanLocal.successWithWarning(warning);
        } catch (IntegrationException e) {
            logger.error(e);
            response = integrationBeanLocal.error(e);
        }
        return response;
    }

    @Override
    protected String updateAmountLogic(String hotelTicker, List<String> inventoryTickers, Date start, Date end) {
        logger.debug("updateAmountLogic:: inventoryID: " + inventoryTickers + " hotelTicker: " + hotelTicker);
        String response;
        try {
            Set<EntryQueueItem> items = new HashSet<>();
            for (String inventoryTicker : inventoryTickers) {
                items.add(new EntryQueueItem(inventoryTicker, start, end));
            }
            final String warning = integrationBeanLocal.notifyNewEntryForItems(hotelTicker, ChannelTicker.BOOKING,
                    ChannelConnectionType.EXECUTE_UPDATE_ARI, new ArrayList<>(items), null);
            response = warning == null ? integrationBeanLocal.success() : integrationBeanLocal.successWithWarning(warning);
        } catch (IntegrationException e) {
            logger.error(e);
            response = integrationBeanLocal.error(e);
        }
        return response;
    }

    @Override
    public String getNotification(String hotelId, String reservationId, Date lastChange, String notificationType) throws InvalidEntryException {
        return super.getNotification(hotelId, reservationId, lastChange, notificationType);
    }

    @Override
    public String updateARI(String hotelTicker, List<String> inventoryTickers, String startString, String endString) {
        return super.updateARI(hotelTicker, inventoryTickers, startString, endString);
    }

    @Override
    public String updateAmount(String hotelTicker, List<String> inventoryTickers, String startString, String endString) {
        return super.updateAmount(hotelTicker, inventoryTickers, startString, endString);
    }
}
