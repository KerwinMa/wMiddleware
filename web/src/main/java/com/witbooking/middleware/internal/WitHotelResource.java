package com.witbooking.middleware.internal;

import com.google.gson.Gson;
import com.witbooking.middleware.beans.ServerInformationBeanLocal;
import com.witbooking.middleware.exceptions.DateFormatException;
import com.witbooking.middleware.exceptions.FrontEndException;
import com.witbooking.middleware.model.HotelMedia;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.JsonUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import java.util.Date;

/**
 * WitHotelResource.java
 * User: jose
 * Date: 12/5/13
 * Time: 11:37 AM
 */
@Path("internal/withotel")
@RequestScoped
public class WitHotelResource {

    private static final Logger logger = Logger.getLogger(FrontEndResource.class);
    @EJB
    private ServerInformationBeanLocal serverInformationBeanLocal;

    @POST
    @Path("/insert")
    public String insertHotelMedia(@FormParam("hotelTicker") final String hotelTicker,
                                   @FormParam("entity") final String entity,
                                   @FormParam("idEntity") final Integer idEntity,
                                   @FormParam("fileName") final String fileName) {
        logger.debug("hotelTicker: " + hotelTicker + " entity: " + entity + " idEntity: " + idEntity + " fileName: " + fileName);
        try {
            final HotelMedia hotelMedia = serverInformationBeanLocal.insertHotelMediaIfNotExists(hotelTicker, entity, idEntity, fileName);
            return hotelMedia == null ? "hotelMedia ya existe" : hotelMedia.toString();
        } catch (FrontEndException e) {
            logger.error(e);
            return e.toString();
        }
    }

    @GET
    @Path("/getWitBookerVisualRepresentation")
    @Produces("application/json")
    public String getEstablishmentInfo(@QueryParam("hotelTicker") final String hotelTicker) {
        try {
            return JsonUtils.toJson(serverInformationBeanLocal.getWitBookerVisualRepresentation(hotelTicker, null));
//            return serverInformationBeanLocal.getWitBookerHotelVisualRepresentation(Arrays.asList(new String[]{hotelTicker}),locale,Arrays.asList(new String[]{"fltr_tipo_ocupacion"})).toString();
        } catch (FrontEndException e) {
            logger.error(e);
            return "error";
        }
    }

    @GET

    @Path("/getPages")
    @Produces("application/json")
    public String getPages(@FormParam("hotelTicker") final String hotelTicker,
                           @FormParam("locale") final String locale) {
        try {
            return new Gson().toJson(serverInformationBeanLocal.getPages(hotelTicker, locale));
        } catch (FrontEndException e) {
            logger.error(e);
        }
        return "{'error' : 'error'}";
    }

    @GET
    @Path("/getARI")
    @Produces("application/json")
    public String getARI(@QueryParam("hotelTicker") String hotelTicker,
                         @QueryParam("currency") String currency,
                         @QueryParam("promotionalCode") String promotionalCode,
                         @QueryParam("startDate") String startDate,
                         @QueryParam("endDate") String endDate,
                         @QueryParam("country") String country) {
        try {
            if (hotelTicker == null) {
                hotelTicker = "hoteldemo.com.v6";
            }
            if (currency == null) {
                currency = "EUR";
            }
            //final Calendar calendar = Calendar.getInstance();
            final Date start = (startDate == null || startDate.isEmpty())
                    ? DateUtil.toBeginningOfTheDay(new Date())
                    : DateUtil.stringToCalendarDate(startDate);
            final Date end = (endDate == null || endDate.isEmpty())
                    ? DateUtil.cloneAndIncrementDays(start, 1)
                    : DateUtil.stringToCalendarDate(endDate);
            return JsonUtils.toJson(serverInformationBeanLocal.getARI(hotelTicker, null, start, end, currency, promotionalCode,country));
        } catch (FrontEndException e) {
            logger.error(e);
        } catch (DateFormatException e) {
            logger.error(e);
        }
        return "error";
    }

    @GET
    @Path("/getQueryExecutionTimes")
    @Produces("application/json")
    public String getQueryExecutionTimes(@QueryParam("ticker") final String ticker) {
        try {
            return JsonUtils.toJson(serverInformationBeanLocal.getQueryExecutionTimes(ticker));
        } catch (Exception e) {
            logger.error(e);
            return "error";
        }
    }
}