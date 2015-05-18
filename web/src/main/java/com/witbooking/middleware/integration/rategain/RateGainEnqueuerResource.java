/*
 *  RateGainResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.rategain;

import com.witbooking.middleware.beans.IntegrationBeanLocal;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;

/**
 * Web Service.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 21, 2013
 */
@Path("RateGain")
@RequestScoped
public class RateGainEnqueuerResource {

    @EJB
    private RateGainEnqueuerLocal rateGainEnqueuerLocal;
    @EJB
    private IntegrationBeanLocal integrationBeanLocal;


    @POST
    @Path("/ReportReservation")
    public String reportReservation(
            @FormParam("reservationId") String reservationId,
            @FormParam("hotelTicker") String hotelTicker) {
        return rateGainEnqueuerLocal.reportReservation(reservationId, hotelTicker);
    }


    @GET
    @Path("services")
    public String handlingRequestByGet() {
        return integrationBeanLocal.error("Method Not Allowed by GET.");
    }

    @POST
    @Path("services")
    @Produces("application/xml")
    public String handlingRequest(final String requestBody) {
        return rateGainEnqueuerLocal.handlingRequest(requestBody);
    }
}
