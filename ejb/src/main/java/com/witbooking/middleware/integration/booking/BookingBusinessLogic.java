package com.witbooking.middleware.integration.booking;

import com.witbooking.middleware.beans.BookingBeanLocal;
import com.witbooking.middleware.beans.IntegrationBeanLocal;
import com.witbooking.middleware.exceptions.DateFormatException;
import com.witbooking.middleware.exceptions.InvalidEntryException;
import com.witbooking.middleware.integration.ChannelConnectionType;
import com.witbooking.middleware.utils.DateUtil;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import java.util.Date;
import java.util.List;

/**
 * BookingBusinessLogic.java
 * User: jose
 * Date: 11/14/13
 * Time: 10:16 AM
 */
public abstract class BookingBusinessLogic {

    public static final String TYPE_CANCEL = "cancellation";
    public static final String TYPE_MODIFICATION = "modification";
    public static final String TYPE_CONFIRMATION = "confirmation";
    private static final org.apache.log4j.Logger logger = Logger.getLogger(BookingBusinessLogic.class);
    protected static final String errorNullMessage = "Invalid Entry. Some required params are null.";
    @EJB
    protected BookingBeanLocal bookingBean;
    @EJB
    protected IntegrationBeanLocal integrationBeanLocal;

    protected abstract String getNotificationLogic(String hotelId, String reservationId, Date lastChange, ChannelConnectionType type);

    protected String getNotification(String hotelId, String reservationId, Date lastChange, String notificationType) throws InvalidEntryException {
        final String response;
        if (notificationType == null)
            notificationType = TYPE_CONFIRMATION;
        if (hotelId == null || hotelId.isEmpty()) {
            throw new InvalidEntryException(errorNullMessage + " hotelId");
        } else {
            final ChannelConnectionType type = TYPE_CONFIRMATION.equals(notificationType)
                    ? ChannelConnectionType.GET_RESERVES
                    : TYPE_MODIFICATION.equals(notificationType)
                    ? ChannelConnectionType.GET_MODIFIED_RESERVES
                    : TYPE_CANCEL.equals(notificationType)
                    ? ChannelConnectionType.GET_CANCELLED_RESERVES
                    : null;
            if (type == null) {
                throw new InvalidEntryException("notificationType '" + notificationType + "' doesn't match any valid value.");
            } else {
                response = getNotificationLogic(hotelId, reservationId, lastChange, type);
            }
        }
        return response;
    }

    protected abstract String updateARILogic(String hotelTicker, List<String> inventoryTickers, Date start, Date end);

    protected abstract String updateAmountLogic(String hotelTicker, List<String> inventoryTickers, Date start, Date end);

    public String updateARI(String hotelTicker, List<String> inventoryTickers, Date start, Date end) {
        return updateARILogic(hotelTicker, inventoryTickers, start, end);
    }

    public String updateARI(String hotelTicker, List<String> inventoryTickers, String startString, String endString) {
        final String response;
        if (hotelTicker == null || inventoryTickers == null || startString == null || endString == null) {
            response = integrationBeanLocal.error(errorNullMessage);
        } else {
            try {
                final Date start = DateUtil.stringToCalendarDate(startString);
                final Date end = DateUtil.stringToCalendarDate(endString);
                return updateARILogic(hotelTicker, inventoryTickers, start, end);
            } catch (DateFormatException e) {
                logger.error(e);
                response = integrationBeanLocal.error(e);
            }
        }
        return response;
    }

    public String updateAmount(String hotelTicker, List<String> inventoryTickers, String startString, String endString) {
        String response;
        if (hotelTicker == null || inventoryTickers == null || startString == null || endString == null) {

            response = integrationBeanLocal.error(errorNullMessage);
        } else {
            try {
                final Date start = DateUtil.stringToCalendarDate(startString);
                final Date end = DateUtil.stringToCalendarDate(endString);
                return updateAmountLogic(hotelTicker, inventoryTickers, start, end);
            } catch (DateFormatException e) {
                logger.error(e);
                response = integrationBeanLocal.error(e);
            }
        }
        return response;
    }

    public String updateAmount(String hotelTicker, List<String> inventoryTickers, Date start, Date end) {
        return updateAmountLogic(hotelTicker, inventoryTickers, start, end);
    }

}