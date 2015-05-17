/*
 *  TripAdvisorResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.tripadvisor;

import com.witbooking.middleware.beans.IntegrationBeanLocal;
import com.witbooking.middleware.beans.TripAdvisorLocalBean;
import com.witbooking.middleware.exceptions.integration.tripadvisor.TripAdvisorException;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 29, 2013
 */
public abstract class TripAdvisorBusinessLogic implements Serializable {

    private static final Logger logger = Logger.getLogger(TripAdvisorBusinessLogic.class);
    @EJB
    protected TripAdvisorLocalBean tripAdvisorLocal;
    @EJB
    protected IntegrationBeanLocal integrationBeanLocal;

    protected abstract String createRequestReservationLogic(String reservationId, String hotelTicker);

    protected abstract String updateRequestReservationLogic(String reservationId, String hotelTicker);

    protected abstract String cancelRequestReservationLogic(String reservationId, String hotelTicker);

    protected String reportReservation(String reservationId, String hotelTicker) {
        return createRequestReservationLogic(reservationId, hotelTicker);
    }

    protected String updateReservation(String reservationId, String hotelTicker) {
        logger.debug("updateReservation:: reservationId: " + reservationId + " hotelTicker: " + hotelTicker);
        return updateRequestReservationLogic(reservationId, hotelTicker);
    }

    protected String cancelReservation(String reservationId, String hotelTicker) {
        logger.debug("cancelReservation:: reservationId: " + reservationId + " hotelTicker: " + hotelTicker);
        return cancelRequestReservationLogic(reservationId, hotelTicker);
    }

    protected String listAllReservations(String hotelTicker) {
        logger.debug("listReservation::hotelTicker: " + hotelTicker);
        try {
            return tripAdvisorLocal.listReserves(hotelTicker);
        } catch (TripAdvisorException e) {
            logger.error(e);
            return integrationBeanLocal.error(e);
        }
    }

    protected String listReservation(String hotelTicker, String reservationId) {
        logger.debug("listReservation:: reservationId: " + reservationId + " hotelTicker: " + hotelTicker);
        try {
            return tripAdvisorLocal.listReservation(hotelTicker, reservationId);
        } catch (TripAdvisorException e) {
            logger.error(e);
            return integrationBeanLocal.error(e);
        }
    }

    protected String checkOptionsIn(String hotelTicker) {
        logger.debug("checkOptionsIn::hotelTicker: " + hotelTicker);
        try {
            return tripAdvisorLocal.checkOptionsIn(hotelTicker);
        } catch (TripAdvisorException e) {
            logger.error(e);
            return "{ \"error\": \"" + e.getUserMessage() + "\"}";
        }
    }
}
