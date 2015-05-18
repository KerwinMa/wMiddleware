package com.witbooking.middleware.integration.siteminder;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * SiteMinderEnqueuerResources.java
 * User: jose
 * Date: 9/26/13
 * Time: 11:51 AM
 */
@Path("SiteMinder")
@RequestScoped
public class SiteMinderEnqueuerResources {

    @EJB
    private SiteMinderEnqueuerLocal siteMinderEnqueuerLocal;

//    private static final org.apache.log4j.Logger logger = Logger.getLogger(SiteMinderEnqueuerResources.class);

    @POST
    @Path("/ReportReservation")
    public String reportReservation(
            @FormParam("reservationId") String reservationId,
            @FormParam("hotelTicker") String hotelTicker) {
        return siteMinderEnqueuerLocal.reportReservation(reservationId, hotelTicker);
    }

}