/*
 *  SchedulerReviewReservationsBean.java
 *
 * Copyright(c) 2015 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 *
 */
package com.witbooking.middleware.beans;


import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.db.handlers.WitMetaDataDBHandler;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.RemoteServiceException;
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
 * Session Bean implementation class SchedulerReviewReservationsBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 03-nov-2014
 */
@Singleton
@Startup
public class SchedulerReviewReservationsBean implements SchedulerReviewReservationsBeanLocal {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SchedulerReviewReservationsBean.class);

    private static final String CC_NUMBER = "IYp1T2sws6dlMf7yB7cSNg==";
    private static final String CC_VALID_TO = "uYHe6zvyURIl8Ndm8DBQsA==";
    private static final String CC_SECURITY_CODE = "erypabv89pWz/dtEHC0OoA==";

    private static final int DAYS_BEFORE_DELETE_CC = -10;


    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    TimerService reviewReservationsTimer;


    @PostConstruct
    public void constructSchedulerTimer() {
        createReviewReservationsTimer();
    }

    private void createReviewReservationsTimer() {
        ScheduleExpression schedule = new ScheduleExpression();
        schedule.hour(MiddlewareProperties.SCHEDULER_RESERVATIONS_HOUR);
        schedule.minute(MiddlewareProperties.SCHEDULER_RESERVATIONS_MIN);
        schedule.second(MiddlewareProperties.SCHEDULER_RESERVATIONS_SEG);
        TimerConfig tc = new TimerConfig();
        tc.setPersistent(false);
        reviewReservationsTimer.createCalendarTimer(schedule, tc);
        logger.debug("ReviewReservationsTimer created: " + DateUtil.timestampFormat(new Date()));
        logger.debug("ReviewReservationsTimer created: " + schedule);
    }

    private void removeReviewReservationsTimer() {
        for (Object obj : reviewReservationsTimer.getTimers()) {
            Timer timer = (Timer) obj;
            logger.debug("ReviewReservationsTimer Removed: " + timer.getSchedule());
            timer.cancel();
        }
    }

    @Override
    @Timeout
    public void updateCreditCard() {
        logger.debug("Invoked updateCreditCard: " + DateUtil.timestampFormat(new Date()));
        if (!MiddlewareProperties.schedulerIsActivate()) {
            logger.info("Review Reservations scheduler is disable by witbooking.properties. If you wan to activate, " +
                    "just set in witbooking.properties ACTIVATE_SCHEDULER_INTEGRATION=true");
            return;
        }
        int daysBefore = -10;
        try {
            if (MiddlewareProperties.DAYS_BEFORE_DELETE_CC == null ||
                    MiddlewareProperties.DAYS_BEFORE_DELETE_CC.isEmpty()) {
                logger.info("Review Reservations scheduler is disable by witbooking.properties. If you wan to activate, " +
                        "just set avalid value for DAYS_BEFORE_DELETE_CC in witbooking.properties ");
                return;
            } else {
                daysBefore = Integer.parseInt(MiddlewareProperties.DAYS_BEFORE_DELETE_CC);
            }
            daysBefore = Math.abs(daysBefore) * -1;
            WitMetaDataDBHandler witMetaDataDBHandler = null;
            List<String> hotelTickers = null;
            try {
                witMetaDataDBHandler = new WitMetaDataDBHandler();
                //Getting the Hotels Data
                hotelTickers = witMetaDataDBHandler.getHotelListProduction();
            } catch (Exception e) {
                logger.error(e);
                throw new MiddlewareException(e);
            } finally {
                if (witMetaDataDBHandler != null) {
                    witMetaDataDBHandler.closeDbConnection();
                }
            }
            if (hotelTickers == null || hotelTickers.isEmpty()) {
                throw new MiddlewareException(new NullPointerException("Empty hotelTicker List"));
            }
            logger.debug("hotelTickers: " + hotelTickers.size());
            Date date = DateUtil.toBeginningOfTheDay(new Date());
            DateUtil.incrementDays(date, daysBefore);
            logger.debug("Deleting CreditCards for the day: " + DateUtil.timestampFormat(date));
            for (String hotelTicker : hotelTickers) {
                DBConnection dbConnection = null;
                try {
                    logger.debug("HotelTicker: " + hotelTicker);
                    DBCredentials dbCredential = getDBCredentials(hotelTicker);
                    dbConnection = new DBConnection(dbCredential);
                    ReservationDBHandler reservationDBHandler = new ReservationDBHandler(dbConnection);
                    reservationDBHandler.updateCreditCardBeforeDate(CC_NUMBER, CC_VALID_TO, CC_SECURITY_CODE, date);
                } catch (Exception ex) {
                    //If we find an error, we continue with the next hotel
                    logger.error(ex.getMessage());
                    EmailsUtils.sendEmailToAdmins("Error Deleting CreditCards: " + hotelTicker,
                            "Error Deleting CreditCards for hotel: '" + hotelTicker + "' <br/>",
                            Arrays.asList("WitBookerAPI Errors", "Error Deleting CreditCards"), ex);
                    DAOUtil.close(dbConnection);
                }
            }

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            EmailsUtils.sendEmailToAdmins("Error Deleting CreditCards", "Error Deleting CreditCards <br/><br/>",
                    Arrays.asList("WitBookerAPI Errors", "Error Deleting CreditCards"), ex);
        }
        logger.debug("Executed the updateCreditCard: " + DateUtil.timestampFormat(new Date()));
    }

    private DBCredentials getDBCredentials(String hotelTicker) throws RemoteServiceException {
        try {
            return DBProperties.getDBCustomerByTicker(hotelTicker);
        } catch (MiddlewareException ex) {
            logger.error("Error getting the DBCredential for the Hotel '" + hotelTicker + "': " + ex.getMessage());
            throw new RemoteServiceException(ex);
        }
    }
}
