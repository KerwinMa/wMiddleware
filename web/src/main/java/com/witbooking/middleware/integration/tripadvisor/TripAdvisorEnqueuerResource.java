/*
 *  TripAdvisorResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.tripadvisor;

import com.witbooking.middleware.exceptions.integration.tripadvisor.TripAdvisorException;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;

/**
 * Insert description here
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 29, 2013
 */
@Path("/TripAdvisor")
@RequestScoped
public class TripAdvisorEnqueuerResource {

    @EJB
    private TripAdvisorEnqueuerLocal tripAdvisorEnqueuerLocal;

    private static final Logger logger = Logger.getLogger(TripAdvisorEnqueuerResource.class);

    @POST
    @Produces("application/json")
    @Path("hotel_availability")
    public String getHotelAvailabilityByPost(
            @FormParam("api_version") String apiVersion,
            @FormParam("hotels") String hotelsJson,
            @FormParam("start_date") String startDate,
            @FormParam("end_date") String endDate,
            @FormParam("num_adults") String numAdults,
            @FormParam("num_rooms") String numRooms,
            @FormParam("lang") String lang,
            @FormParam("currency") String currency,
            @FormParam("user_country") String userCountry,
            @FormParam("device_type") String deviceType,
            @FormParam("query_key") String queryKey) throws TripAdvisorException {
        logger.debug("getHotelAvailabilityByPost");
        return tripAdvisorEnqueuerLocal.getHotelAvailability(apiVersion, hotelsJson, startDate, endDate, numAdults, numRooms, lang,
                currency, userCountry, deviceType, queryKey);

    }

    @GET
    @Produces("application/json")
    @Path("hotel_availability")
    public String getHotelAvailabilityByGet(
            @QueryParam("api_version") String apiVersion,
            @QueryParam("hotels") String hotelsJson,
            @QueryParam("start_date") String startDate,
            @QueryParam("end_date") String endDate,
            @QueryParam("num_adults") String numAdults,
            @QueryParam("num_rooms") String numRooms,
            @QueryParam("lang") String lang,
            @QueryParam("currency") String currency,
            @QueryParam("user_country") String userCountry,
            @QueryParam("device_type") String deviceType,
            @QueryParam("query_key") String queryKey) throws TripAdvisorException {
        logger.debug("getHotelAvailabilityByGet");
        return tripAdvisorEnqueuerLocal.getHotelAvailability(apiVersion, hotelsJson, startDate, endDate, numAdults, numRooms, lang,
                currency, userCountry, deviceType, queryKey);

    }

    @GET
    @Produces("application/json")
    @Path("hotel_inventory")
    public String getHotelInventoryByGet(
            @QueryParam("api_version") String api_version,
            @QueryParam("lang") String lang) {
        logger.debug("getHotelInventoryByGet");
        return tripAdvisorEnqueuerLocal.getHotelInventory(api_version, lang);
    }

    @POST
    @Produces("application/json")
    @Path("hotel_inventory")
    public String getHotelInventoryByPost(
            @FormParam("api_version") String api_version,
            @FormParam("lang") String lang) {
        logger.debug("getHotelInventoryByPost");
        return tripAdvisorEnqueuerLocal.getHotelInventory(api_version, lang);
    }

    @GET
    @Produces("application/json")
    @Path("config")
    public String getConfigurationByGet() {
        logger.debug("getConfigurationByGet");
        return tripAdvisorEnqueuerLocal.getConfiguration();
    }

    @POST
    @Produces("application/json")
    @Path("config")
    public String getConfigurationByPost() {
        logger.debug("getConfigurationByPost");
        return tripAdvisorEnqueuerLocal.getConfiguration();
    }

    @POST
    @Produces("application/json")
    @Path("/ReportReservation")
    public String reportReservation(
            @FormParam("reservationId") String reservationId,
            @FormParam("hotelTicker") String hotelTicker) {
        return tripAdvisorEnqueuerLocal.reportReservation(reservationId, hotelTicker);
    }

    @POST
    @Produces("application/json")
    @Path("/UpdateReservation")
    public String updateReservation(
            @FormParam("reservationId") String reservationId,
            @FormParam("hotelTicker") String hotelTicker) {
        return tripAdvisorEnqueuerLocal.updateReservation(reservationId, hotelTicker);
    }

    @POST
    @Produces("application/json")
    @Path("/ListAllReservations")
    public String listAllReservations(@FormParam("hotelTicker") String hotelTicker) {
        return tripAdvisorEnqueuerLocal.listAllReservations(hotelTicker);
    }

    @POST
    @Produces("application/json")
    @Path("/ListReservation")
    public String listReservation(@FormParam("hotelTicker") String hotelTicker,
                                  @FormParam("reservationId") String reservationId) {
        return tripAdvisorEnqueuerLocal.listReservation(hotelTicker, reservationId);
    }

    @POST
    @Produces("application/json")
    @Path("/CancelReservation")
    public String cancelReservation(
            @FormParam("reservationId") String reservationId,
            @FormParam("hotelTicker") String hotelTicker) {
        return tripAdvisorEnqueuerLocal.cancelReservation(reservationId, hotelTicker);
    }

    @POST
    @Produces("application/json")
    @Path("CheckOptionsIn")
    public String checkOptionsIn(@FormParam("hotelTicker") String hotelTicker) {
        return tripAdvisorEnqueuerLocal.checkOptionsIn(hotelTicker);
    }

}
