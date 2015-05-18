/*
 *   BookingSchedulerBean.java
 * 
 * Copyright(c) 2015 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.ChannelsHotelDBHandler;
import com.witbooking.middleware.db.handlers.WitMetaDataDBHandler;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.exceptions.integration.booking.BookingException;
import com.witbooking.middleware.integration.booking.model.Constants;
import com.witbooking.middleware.model.channelsIntegration.Channel;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.EmailsUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 10/04/2015
 */
@Singleton
@Startup
public class SchedulerBookingBean {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SchedulerBookingBean.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    TimerService bookingTimerService;

    @EJB
    BookingBeanLocal bookingBeanLocal;

    @EJB
    private MailingBeanLocal mailingBeanLocal;


    @PostConstruct
    public void constructSchedulerTimer() {
        createBookingTimer();
    }

    private void createBookingTimer() {
        ScheduleExpression schedule = new ScheduleExpression();
        schedule.hour(MiddlewareProperties.SCHEDULER_BOOKING_HOUR);
        schedule.minute(MiddlewareProperties.SCHEDULER_BOOKING_MIN);
        schedule.second(MiddlewareProperties.SCHEDULER_BOOKING_SEG);
        TimerConfig tc = new TimerConfig();
        tc.setPersistent(false);
        bookingTimerService.createCalendarTimer(schedule, tc);
        logger.debug("bookingTimer created: " + DateUtil.timestampFormat(new Date()));
        logger.debug("bookingTimer created: " + schedule);
    }

    private void removeBookingTimer() {
        for (Object obj : bookingTimerService.getTimers()) {
            Timer timer = (Timer) obj;
            logger.debug("bookingTimerService Removed: " + timer.getSchedule());
            timer.cancel();
        }
    }

    //    @Override
    @Timeout
    public void askForReservations() {
        logger.debug("Invoked askForReservations: " + DateUtil.timestampFormat(new Date()));
        if (!MiddlewareProperties.schedulerIsActivate()) {
            logger.info("Integration scheduler is disable by witbooking.properties. If you wan to activate, " +
                    "just set in witbooking.properties ACTIVATE_SCHEDULER_INTEGRATION=true");
            return;
        }
        try {
            List<String> hotelIds;
            WitMetaDataDBHandler witMetaDataDBHandler = null;
            try {
                witMetaDataDBHandler = new WitMetaDataDBHandler();
                //Getting the transfers Data
                hotelIds = witMetaDataDBHandler.getChannelHotelTickerFromChannel(Constants.CHANNEL_TICKER);
                for (String hotelId : hotelIds) {
                    DBConnection dBConnection = null;
                    String hotelTicker = "";
                    try {
                        hotelTicker = witMetaDataDBHandler.getHotelTickerFromChannelHotelTicker(Constants.CHANNEL_TICKER, hotelId);
                        dBConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
                        final ChannelsHotelDBHandler channelsHotelDBHandler = new ChannelsHotelDBHandler(dBConnection);
                        Channel channel = channelsHotelDBHandler.getChannelByChannelTicker(Constants.CHANNEL_TICKER);
                        if (channel == null)
                            throw new NullPointerException("BOOKING Channel in Hotel '" + hotelTicker + "' can't be found.");
                        if (channel.isActive()) {
                            logger.debug("asking For new Reservations for: '" + hotelId + "'");
                            bookingBeanLocal.handleNewReserves(hotelId, null, null);
                            bookingBeanLocal.handleModifiedReservations(hotelId, null, null);
                        }
                    } catch (Exception ex) {
                        logger.error(ex);
                        EmailsUtils.sendEmailToAdmins("Error in Booking Scheduler for: " + hotelTicker,
                                "Error sending confirmation email for hotel: '" + hotelTicker + "'",
                                Arrays.asList("WitBookerAPI Errors", "Error Booking Scheduler"), ex);
                    } finally {
                        DAOUtil.close(dBConnection);
                    }
                }
            } catch (DBAccessException e) {
                logger.error(e);
                throw new BookingException(e);
            } finally {
                if (witMetaDataDBHandler != null) {
                    witMetaDataDBHandler.closeDbConnection();
                }
            }
        } catch (MiddlewareException ex) {
            logger.error(ex.getMessage());
        }
        logger.debug("Executed the bookingTimerService: " + DateUtil.timestampFormat(new Date()));
    }
}
