/*
 *  SchedulerValidateTransfersBean.java
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
import com.witbooking.middleware.model.TransferData;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;
import java.util.List;

/**
 * Session Bean implementation class SchedulerValidateTransfersBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 03-nov-2014
 */
@Singleton
@Startup
public class SchedulerValidateTransfersBean implements SchedulerValidateTransfersBeanLocal {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SchedulerValidateTransfersBean.class);


    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    TimerService validateTransfersTimer;

    @EJB
    ValidateTransfersBeanLocal validateTransfersBean;


    @PostConstruct
    public void constructSchedulerTimer() {
        createValidateTransfersTimer();
    }

    private void createValidateTransfersTimer() {
        ScheduleExpression schedule = new ScheduleExpression();
        schedule.hour(MiddlewareProperties.SCHEDULER_TRANSFERS_HOUR);
        schedule.minute(MiddlewareProperties.SCHEDULER_TRANSFERS_MIN);
        schedule.second(MiddlewareProperties.SCHEDULER_TRANSFERS_SEG);
        TimerConfig tc = new TimerConfig();
        tc.setPersistent(false);
        validateTransfersTimer.createCalendarTimer(schedule, tc);
        logger.debug("validateTransfersTimer created: " + DateUtil.timestampFormat(new Date()));
        logger.debug("validateTransfersTimer created: " + schedule);
    }

    private void removeValidateTransfersTimer() {
        for (Object obj : validateTransfersTimer.getTimers()) {
            Timer timer = (Timer) obj;
            logger.debug("MailPostStayTimer Removed: " + timer.getSchedule());
            timer.cancel();
        }
    }

    @Override
    @Timeout
    public void validateTransfersReservations() {
        logger.debug("Invoked validateTransfersReservations: " + DateUtil.timestampFormat(new Date()));
        if (!MiddlewareProperties.transfersSchedulerIsActivate()) {
            logger.info("Transfer scheduler is disable by witbooking.properties. If you wan to activate, " +
                    "just set in witbooking.properties ACTIVATE_SCHEDULER_TRANSFERS_VALIDATION=true");
            return;
        }
        try {
            List<TransferData> transferDataList;
            WitMetaDataDBHandler witMetaDataDBHandler = null;
            try {
                witMetaDataDBHandler = new WitMetaDataDBHandler();
                //Getting the transfers Data
                transferDataList = witMetaDataDBHandler.getTransferDataList();
                for (TransferData transferData : transferDataList) {
                    int res = validateTransfersBean.confirmReservationStatusTransfers(transferData);
                    if (res > 0)
                        logger.info("Reservations Changed: '" + res + "' hotelTicker: '" + transferData.getTicker() + "'");
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
        logger.debug("Executed the validateTransfersReservations: " + DateUtil.timestampFormat(new Date()));
    }
}
