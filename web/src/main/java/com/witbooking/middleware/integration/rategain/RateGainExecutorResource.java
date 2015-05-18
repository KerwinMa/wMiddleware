package com.witbooking.middleware.integration.rategain;

import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRQ;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * RateGainExecutorResource.java
 * User: jose
 * Date: 11/14/13
 * Time: 11:32 AM
 */
@Path("/Execute/RateGain")
@RequestScoped
public class RateGainExecutorResource {

    @EJB
    private RateGainExecutorLocal rateGainExecutorLocal;

    private static final org.apache.log4j.Logger logger = Logger.getLogger(RateGainExecutorResource.class);

    @POST
    @Path("/ReportReservation")
    public String reportReservation(@FormParam("reservationId") String reservationId,
                                    @FormParam("hotelTicker") String hotelTicker) {
        return rateGainExecutorLocal.reportReservation(reservationId,hotelTicker);
    }

    @POST
    @Path("/getOTAHotelResNotifRQ")
    public OTAHotelResNotifRQ getOTAHotelResNotifRQ(
            @FormParam("reservationId") String reservationId,
            @FormParam("hotelTicker") String hotelTicker) {
        return rateGainExecutorLocal.getOTAHotelResNotifRQ(reservationId, hotelTicker);
    }
}