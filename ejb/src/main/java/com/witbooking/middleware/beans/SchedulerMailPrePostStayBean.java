/*
 *  SchedulerMailPrePostStayBean.java
 *
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 *
 */
package com.witbooking.middleware.beans;


import com.witbooking.middleware.db.handlers.WitMetaDataDBHandler;
import com.witbooking.middleware.exceptions.MailingException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;
import java.util.Map;

/**
 * Session Bean implementation class SchedulerMailPrePostStayBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 16-dec-2013
 */
@Singleton
@Startup
public class SchedulerMailPrePostStayBean implements SchedulerMailPrePostStayBeanLocal {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SchedulerMailPrePostStayBean.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    TimerService prePostStayMailTimer;

    @EJB
    MailingBeanLocal mailingService;


    @PostConstruct
    public void constructSchedulerTimer() {
        createMailPrePostStayTimer();
    }

    private void createMailPrePostStayTimer() {
        ScheduleExpression schedule = new ScheduleExpression();
        schedule.hour(MiddlewareProperties.SCHEDULER_MAIL_HOUR);
        schedule.minute(MiddlewareProperties.SCHEDULER_MAIL_MIN);
        schedule.second(MiddlewareProperties.SCHEDULER_MAIL_SEG);
        TimerConfig tc = new TimerConfig();
        tc.setPersistent(false);
        prePostStayMailTimer.createCalendarTimer(schedule, tc);
        logger.debug("PrePostStayMailTimer created: " + DateUtil.timestampFormat(new Date()));
        logger.debug("PrePostStayMailTimer created: " + schedule);
    }

    private void removeMailPostStayTimer() {
        for (Object obj : prePostStayMailTimer.getTimers()) {
            Timer timer = (Timer) obj;
            logger.debug("MailPostStayTimer Removed: " + timer.getSchedule());
            timer.cancel();
        }
    }

    @Override
    @Timeout
    public void mailPrePostStay() {
        logger.debug("Invoked sendMailPreOrPostStay: " + DateUtil.timestampFormat(new Date()));
        if (!MiddlewareProperties.mailingSchedulerIsActivate()) {
            logger.debug("Integration mailing scheduler is disable by witbooking.properties. If you wan to activate, " +
                    "just set in witbooking.properties ACTIVATE_MAILING_SCHEDULER_INTEGRATION=true");
            return;
        }
        try {
            Map<String, Integer> hotelMap;
            WitMetaDataDBHandler witMetaDataDBHandler = null;
            try {
                witMetaDataDBHandler = new WitMetaDataDBHandler();
                //Sending Email POST Stay
                hotelMap = witMetaDataDBHandler.getHotelListActivePreOrPostStayMail(true);
                for (String hotelTicker : hotelMap.keySet()) {
                    mailingService.sendMailPreOrPostStay(hotelTicker, hotelMap.get(hotelTicker), true);
                }
                //Sending Email PRE Stay
                hotelMap = witMetaDataDBHandler.getHotelListActivePreOrPostStayMail(false);
                for (String hotelTicker : hotelMap.keySet()) {
                    mailingService.sendMailPreOrPostStay(hotelTicker, hotelMap.get(hotelTicker), false);
                }
            } catch (DBAccessException e) {
                logger.error(e);
                throw new MailingException(e);
            } finally {
                if (witMetaDataDBHandler != null) {
                    witMetaDataDBHandler.closeDbConnection();
                }
            }
        } catch (MiddlewareException ex) {
            logger.error(ex.getMessage());
        }
        logger.debug("Executed the sendMailPreOrPostStay: " + DateUtil.timestampFormat(new Date()));
    }
}
