/*
 *  TrivagoResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.trivago;

/**
 * REST Web Service
 *
 * @author Christian Delgado
 */
/*
TODO: Removed the Trivago REST Services
@Path("trivago/TrvgHotels")
@RequestScoped
*/
public class TrivagoResource {
   /*
   private static final Logger logger = Logger.getLogger(TrivagoResource.class);


    @EJB
    private TrivagoBeanLocal trivagoBean;
   public TrivagoResource() {
   }

   @GET
   @Produces("application/xml")
   @Path("/list")
   public HotelList getHotelListByGet() {
      logger.debug("getHotelListByGet");
      try {
         return trivagoBean.getHotelList();
      } catch (TrivagoException ex) {
         logger.error(ex);
         HotelList error = new HotelList();
         error.setError(ex.getUserMessage());
         return error;

      }
   }

   @POST
   @Produces("application/xml")
   @Path("/list")
   public HotelList getHotelListByPost() throws TrivagoException {
      logger.debug("getHotelListByPost");
      try {
         return trivagoBean.getHotelList();
      } catch (TrivagoException ex) {
         logger.error(ex);
         HotelList error = new HotelList();
         error.setError(ex.getUserMessage());
         return error;

      }
   }

   @GET
   @Produces("application/xml")
   @Path("/deeplink")
   public DeepLink getDeepLinkByGet(@QueryParam("hotel_id") String hotelId,
           @QueryParam("check_in") String checkin,
           @QueryParam("check_out") String checkout,
           @QueryParam("language") String language,
           @QueryParam("adults") int adults,
           @QueryParam("room_type") int roomType,
           @QueryParam("currency") String currency) throws TrivagoException {
      logger.debug("getDeepLinkByGet");
      Date checkInDate = null;
      Date checkOutDate = null;
      try {
         checkInDate = DateUtil.stringToCalendarDate(checkin);
         checkOutDate = DateUtil.stringToCalendarDate(checkout);
      } catch (ParseException ex) {
         throw new TrivagoException(ex);
      }
      try {
         return trivagoBean.getDeepLink(hotelId, checkInDate, checkOutDate, language, adults, roomType, currency);
      } catch (TrivagoException ex) {
         logger.error(ex);
         DeepLink error = new LinkError(ex.getUserMessage());
         return error;
      }

   }

   @POST
   @Produces("application/xml")
   @Path("/deeplink")
   public DeepLink getDeepLinkByPost(@FormParam("hotel_id") String hotelId,
           @FormParam("check_in") String checkin,
           @FormParam("check_out") String checkout,
           @FormParam("language") String language,
           @FormParam("adults") int adults,
           @FormParam("room_type") int roomType,
           @FormParam("currency") String currency) throws TrivagoException {
      logger.debug("getDeepLinkByPost");
      Date checkInDate = null;
      Date checkOutDate = null;
      try {
         checkInDate = DateUtil.stringToCalendarDate(checkin);
         checkOutDate = DateUtil.stringToCalendarDate(checkout);
      } catch (ParseException ex) {
         throw new TrivagoException(ex);
      }
      try {
         return trivagoBean.getDeepLink(hotelId, checkInDate, checkOutDate, language, adults, roomType, currency);
      } catch (TrivagoException ex) {
         logger.error(ex);
         DeepLink error = new LinkError(ex.getUserMessage());
         return error;
      }

   }

   @GET
   @Produces("application/xml")
   @Path("/availabilityrequest")
   public AvailabilityResponse getAvailabilityByGet(@QueryParam("xml") AvailabilityRequest availabilityRequest) throws TrivagoException {
      logger.debug("getAvailabilityByGet");
      try {
         return trivagoBean.getAvailability(availabilityRequest);
      } catch (TrivagoException ex) {
         logger.error(ex);
         AvailabilityResponse error = new AvailabilityResponse();
         error.setError(ex.getUserMessage());
         return error;

      }
   }

   @POST
   @Produces("application/xml")
   @Path("/availabilityrequest")
   public AvailabilityResponse getAvailabilityByPost(@FormParam("xml") AvailabilityRequest availabilityRequest) throws TrivagoException {
      logger.debug("getAvailabilityByPost");
      try {
         return trivagoBean.getAvailability(availabilityRequest);
      } catch (TrivagoException ex) {
         logger.error(ex);
         AvailabilityResponse error = new AvailabilityResponse();
         error.setError(ex.getUserMessage());
         return error;

      }
   }

   @GET
   @Produces("application/xml")
   @Path("/testServicesTrivago")
   public AvailabilityResponse testServicesTrivagoByGet() {
      return trivagoBean.testServicesTrivago();
   }

   @POST
   @Produces("application/xml")
   @Path("/testServicesTrivago")
   public AvailabilityResponse testServicesTrivagoByPost() {
      return trivagoBean.testServicesTrivago();
   }
   */
}
