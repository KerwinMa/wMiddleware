package com.witbooking.middleware.integration.booking;

import com.witbooking.middleware.exceptions.InvalidEntryException;
import com.witbooking.middleware.exceptions.integration.booking.BookingException;
import com.witbooking.middleware.integration.ChannelConnectionType;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import java.util.Date;
import java.util.List;

/**
 * BookingInstantlyResource.java
 * User: jose
 * Date: 11/14/13
 * Time: 9:46 AM
 */
@Stateless
public class BookingExecutor extends BookingBusinessLogic implements BookingExecutorLocal {

    private static final Logger logger = Logger.getLogger(BookingExecutor.class);

    @Override
    protected String getNotificationLogic(String hotelId, String reservationId, Date lastChange, final ChannelConnectionType type) {
        String response;
        try {
            if (type.equals(ChannelConnectionType.GET_RESERVES)) {
                bookingBean.handleNewReserves(hotelId, reservationId, lastChange);
            } else if (type.equals(ChannelConnectionType.GET_MODIFIED_RESERVES)){
                bookingBean.handleModifiedReservations(hotelId, reservationId, lastChange);
            }else if (type.equals(ChannelConnectionType.GET_CANCELLED_RESERVES)){
                bookingBean.handleCancelledReservations(hotelId, reservationId, lastChange);
            }
            response = integrationBeanLocal.success();
        } catch (BookingException e) {
            logger.error(e);
            response = integrationBeanLocal.error(e);
        }
        return response;
    }

    @Override
    protected String updateARILogic(String hotelTicker, List<String> inventoryTickers, Date start, Date end) {
        String response;
        try {
            bookingBean.updateARI(hotelTicker, inventoryTickers, start, end);
            response = integrationBeanLocal.success();
        } catch (BookingException e) {
            logger.error(e);
            response = integrationBeanLocal.error(e);
        }
        return response;
    }

    @Override
    protected String updateAmountLogic(String hotelTicker, List<String> inventoryTickers, Date start, Date end) {
        String response;
        try {
            bookingBean.updateAmount(hotelTicker, inventoryTickers, start, end);
            response = integrationBeanLocal.success();
        } catch (BookingException e) {
            logger.error(e);
            response = integrationBeanLocal.error(e);
        }
        return response;
    }

    @Override
    public String updateARI(String hotelTicker, List<String> inventoryTickers, Date start, Date end) {
        return super.updateARI(hotelTicker, inventoryTickers, start, end);
    }

    @Override
    public String updateAmount(String hotelTicker, List<String> inventoryTickers, Date start, Date end){
        return super.updateAmount(hotelTicker, inventoryTickers, start, end);
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