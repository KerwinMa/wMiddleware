package com.witbooking.middleware.integration.tripadvisor;

import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * TripAdvisorExecutorResource.java
 * User: jose
 * Date: 11/14/13
 * Time: 12:43 PM
 */
@Path("/Execute/TripAdvisor")
@RequestScoped
public class TripAdvisorExecutorResource {

    @EJB
    private TripAdvisorExecutorLocal tripAdvisorExecutorLocal;

    private static final Logger logger = Logger.getLogger(TripAdvisorExecutorResource.class);

    @POST
    @Produces("application/json")
    @Path("/ReportReservation")
    public String reportReservation(
            @FormParam("reservationId") String reservationId,
            @FormParam("hotelTicker") String hotelTicker) {
        return tripAdvisorExecutorLocal.reportReservation(reservationId, hotelTicker);
    }

    @POST
    @Produces("application/json")
    @Path("/UpdateReservation")
    public String updateReservation(
            @FormParam("reservationId") String reservationId,
            @FormParam("hotelTicker") String hotelTicker) {
        return tripAdvisorExecutorLocal.updateReservation(reservationId, hotelTicker);
    }

    @POST
    @Produces("application/json")
    @Path("/CancelReservation")
    public String cancelReservation(
            @FormParam("reservationId") String reservationId,
            @FormParam("hotelTicker") String hotelTicker) {
        return tripAdvisorExecutorLocal.cancelReservation(reservationId, hotelTicker);
    }
}