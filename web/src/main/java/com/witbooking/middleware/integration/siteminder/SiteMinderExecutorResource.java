package com.witbooking.middleware.integration.siteminder;

import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRQ;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * SiteMinderExecutorResource.java
 * User: jose
 * Date: 11/14/13
 * Time: 3:49 PM
 */
@Path("/Execute/SiteMinder")
@RequestScoped
public class SiteMinderExecutorResource {

    @EJB
    private SiteMinderExecutorLocal siteMinderExecutorLocal;

    private static final Logger logger = Logger.getLogger(SiteMinderExecutorResource.class);

    @POST
    @Path("/ReportReservation")
    public String reportReservation(
            @FormParam("reservationId") String reservationId,
            @FormParam("hotelTicker") String hotelTicker) {
        return siteMinderExecutorLocal.reportReservation(reservationId, hotelTicker);
    }

    @POST
    @Path("/getOTAHotelResNotifRQ")
    public OTAHotelResNotifRQ getOTAHotelResNotifRQ(
            @FormParam("reservationId") String reservationId,
            @FormParam("hotelTicker") String hotelTicker) {
        return siteMinderExecutorLocal.getOTAHotelResNotifRQ(reservationId, hotelTicker);
    }
}