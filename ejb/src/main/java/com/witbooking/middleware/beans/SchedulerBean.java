/*
 *  SchedulerBean.java
 *
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 *
 */
package com.witbooking.middleware.beans;


import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import java.util.Date;

//TODO: rename SchedulerBean to SchedulerExecutorBean
/**
 * @author jose
 */
@Singleton
@Startup
public class SchedulerBean implements SchedulerBeanLocal{

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(SchedulerBean.class);

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Resource
    TimerService integrationExecutorTimer;

    @EJB
    private IntegrationExecuteBeanLocal integrationExecuteBeanLocal;

    @PostConstruct
    public void constructSchedulerTimer() {
        createIntegrationExecutorTimer();
    }

    private void createIntegrationExecutorTimer() {
        ScheduleExpression schedule = new ScheduleExpression();
        schedule.hour(MiddlewareProperties.SCHEDULER_EXECUTOR_HOUR);
        schedule.minute(MiddlewareProperties.SCHEDULER_EXECUTOR_MIN);
        schedule.second(MiddlewareProperties.SCHEDULER_EXECUTOR_SEG);
        TimerConfig tc = new TimerConfig();
        tc.setPersistent(false);
        integrationExecutorTimer.createCalendarTimer(schedule, tc);
        logger.debug("IntegrationExecutorTimer created: " + DateUtil.timestampFormat(new Date()));
        logger.debug("IntegrationExecutorTimer created: " + schedule);
    }

    private void removeIntegrationExecutorTimer() {
        for (Object obj : integrationExecutorTimer.getTimers()) {
            javax.ejb.Timer timer = (javax.ejb.Timer) obj;
            logger.debug("IntegrationExecutorTimer Removed: " + timer.getSchedule());
            timer.cancel();
        }
    }

    @Override
    @Timeout
    public void integrationExecutor() {
        logger.debug("Invoked IntegrationExecutor: " + DateUtil.timestampFormat(new Date()));
        if (!MiddlewareProperties.schedulerIsActivate()) {
            logger.info("Integration scheduler is disable by witbooking.properties. If you wan to activate, " +
                    "just set in witbooking.properties ACTIVATE_SCHEDULER_INTEGRATION=true");
            return;
        }
        try {
            integrationExecuteBeanLocal.executePending();
        } catch (IntegrationException ex) {
            logger.error(ex.getMessage());
        }
        try {
            String hour = MiddlewareProperties.SCHEDULER_EXECUTOR_HOUR;
            String min = MiddlewareProperties.SCHEDULER_EXECUTOR_MIN;
            String sec = MiddlewareProperties.SCHEDULER_EXECUTOR_SEG;

            MiddlewareProperties.updateParameter("SCHEDULER_EXECUTOR_HOUR");
            MiddlewareProperties.updateParameter("SCHEDULER_EXECUTOR_MIN");
            MiddlewareProperties.updateParameter("SCHEDULER_EXECUTOR_SEG");
            if (!hour.equals(MiddlewareProperties.SCHEDULER_EXECUTOR_HOUR) ||
                    !min.equals(MiddlewareProperties.SCHEDULER_EXECUTOR_MIN) ||
                    !sec.equals(MiddlewareProperties.SCHEDULER_EXECUTOR_SEG)) {
                logger.debug("Recreating the IntegrationExecutorTimer with a new Scheduler Configuration.");
                removeIntegrationExecutorTimer();
                createIntegrationExecutorTimer();
            }
        } catch (Exception ex) {
            logger.error("Error updating the Scheduler Properties");
            logger.error(ex.getMessage());
        }
    }

}
