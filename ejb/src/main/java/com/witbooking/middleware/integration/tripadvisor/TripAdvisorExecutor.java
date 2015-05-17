package com.witbooking.middleware.integration.tripadvisor;

import com.witbooking.middleware.exceptions.integration.tripadvisor.TripAdvisorException;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;

/**
 * TripAdvisorExecutor.java
 * User: jose
 * Date: 11/14/13
 * Time: 12:43 PM
 */
@Stateless
public class TripAdvisorExecutor extends TripAdvisorBusinessLogic implements TripAdvisorExecutorLocal{

    private static final Logger logger = Logger.getLogger(TripAdvisorExecutor.class);
    private static final String paramsNull = "Given parameters can't be null.";

    @Override
    public String reportReservation(String reservationId, String hotelTicker) {
        return super.reportReservation(reservationId, hotelTicker);
    }

    @Override
    public String updateReservation(String reservationId, String hotelTicker) {
        return super.updateReservation(reservationId, hotelTicker);
    }

    @Override
    public String cancelReservation(String reservationId, String hotelTicker) {
        return super.cancelReservation(reservationId, hotelTicker);
    }

    @Override
    protected String createRequestReservationLogic(String reservationId, String hotelTicker) {
        String response;
        try {
            if (reservationId != null && hotelTicker != null) {
                tripAdvisorLocal.createReservation(reservationId, hotelTicker);
                response = integrationBeanLocal.success();
            } else {
                response = integrationBeanLocal.error(paramsNull);
            }
//            response = (reservationId != null && hotelTicker != null)
//                    ? tripAdvisorLocal.createReservation(reservationId, hotelTicker)
//                    : "{\"error\":\"Given parameters can't be null.\"}";
        } catch (TripAdvisorException e) {
            logger.error(e);
            response = integrationBeanLocal.error(e.getUserMessage());
        }
        return response;
    }

    @Override
    protected String updateRequestReservationLogic(String reservationId, String hotelTicker) {
        String response;
        try {
            if ((reservationId != null && hotelTicker != null)) {
                tripAdvisorLocal.updateReservation(reservationId, hotelTicker);
                response = integrationBeanLocal.success();
            } else {
                response = integrationBeanLocal.error(paramsNull);
            }
        } catch (TripAdvisorException e) {
            logger.error(e);
            response = integrationBeanLocal.error(e);
        }
        return response;
    }

    @Override
    protected String cancelRequestReservationLogic(String reservationId, String hotelTicker) {
        String response;
        try {
            if ((reservationId != null && hotelTicker != null)) {
                tripAdvisorLocal.cancelReservation(hotelTicker, reservationId);
                response = integrationBeanLocal.success();
            } else {
                response = integrationBeanLocal.error(paramsNull);
            }
        } catch (TripAdvisorException e) {
            logger.error(e);
            response = integrationBeanLocal.error(e);
        }
        return response;
    }

}