package com.witbooking.middleware.integration.google;

import com.witbooking.middleware.beans.GoogleBeanLocal;
import com.witbooking.middleware.db.handlers.WitMetaDataDBHandler;
import com.witbooking.middleware.exceptions.DateFormatException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.integration.google.model.feed.Listings;
import com.witbooking.middleware.integration.google.model.hpa.*;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.XMLUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.ws.rs.*;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;

/**
 * GoogleResource.java
 * User: jose
 * Date: 4/10/14
 * Time: 10:39 AM
 */
@Path("Google")
@RequestScoped
public class GoogleResource {


    private static final Logger logger = Logger.getLogger(GoogleResource.class);
    @EJB
    private GoogleBeanLocal googleBeanLocal;


    /**
     * Resources used to publish our Hotel Feed.
     *
     * @return
     */
    @GET
    @Produces("application/xml;charset=utf-8")
    @Path("hotelFeed/witbooking-local.xml")
    public Listings getGoogleFeed() {
        Listings ret = null;
        try {
            ret = googleBeanLocal.getLocalFeed();
        } catch (Exception e) {
            logger.error(e);
        }
        return ret;
    }

    /**
     * Resource used to publish our exchange rates.
     * @return
     */
    @GET
    @Produces("application/xml;charset=utf-8")
    @Path("/exchangeRates")
    public ExchangeRates getExchangeRates() {
        try {
            return googleBeanLocal.getCurrencyExchange();
        } catch (MiddlewareException e) {
            logger.error(e);
            return null;
        }
    }

    /**
     * Resources used to publish the Hotel info feed of the hotelTicker given.
     * @param hotelTicker
     * @return
     */
    @GET
    @Produces("application/xml;charset=utf-8")
    @Path("/hotelInfoFeed/{hotelTicker}")
    public HotelInfoFeed getARI(@PathParam("hotelTicker") String hotelTicker) {
        try {
            return googleBeanLocal.getHotelInfoFeed(hotelTicker);
        } catch (MiddlewareException e) {
            logger.error(e);
            return new HotelInfoFeed(e.toString());
        }
    }

    /**
     * Resources internal to test our getARI function.
     * @param dateString
     * @param nights
     * @param hotelTicker
     * @return
     */
    @GET
    @Produces("application/xml;charset=utf-8")
    @Path("/ariQ/{hotelTicker}")
    public Transaction getARIQ(@QueryParam("date") String dateString,
                              @QueryParam("nights") Integer nights,
                              @PathParam("hotelTicker") String hotelTicker) {
        Transaction ret = null;
        try {
            Date date;
            try {
                date = dateString == null ? new Date() : DateUtil.stringToCalendarDate(dateString);
            } catch (DateFormatException e) {
                logger.error(e);
                date = new Date();
            }
            final WitMetaDataDBHandler witMetaDataDBHandler = new WitMetaDataDBHandler();
            final Query query = new Query();
            query.setNights(new BigInteger(nights == null ? "2" : nights + ""));
            query.setCheckin(XMLUtils.getXMLGeCalendarFromDate(date));
            final Query.PropertyList value = new Query.PropertyList();
            if (hotelTicker == null)
                value.addAll(witMetaDataDBHandler.getHotelListProduction());
            else
                value.addElement(hotelTicker);
            query.setPropertyList(value);
            ret = googleBeanLocal.getARI(query);
        } catch (Exception e) {
            logger.error(e);
        }
        return ret;
    }

    /**
     * Resources used to publish the ARI information of the hotel and dates given in Query Object.
     * @param query
     * @return
     */
    @POST
    @Consumes("application/xml")
    @Produces("application/xml;charset=utf-8")
    @Path("/queryControl")
    public Transaction getARI(Query query) {
        return googleBeanLocal.getARI(query);
    }


    /**
     * Resources used to publish the bid of the hotelTicker given. If the hotelTicker given is null or Empty,
     * return all the bids configured.
     * @param hotelTicker
     * @return
     */
    @GET
    @Produces("application/xml;charset=utf-8")
    @Path("/bids/{hotelTicker}")
    public Bids getGoogleBids(@PathParam("hotelTicker")String hotelTicker) {
        Bids bid = null;
        try {
            if (hotelTicker == null || hotelTicker.isEmpty())
                bid = googleBeanLocal.getBid();
            else {
                bid = googleBeanLocal.getBid(Arrays.asList(hotelTicker));
            }
        } catch (MiddlewareException e) {
            logger.error(e);
        }
        return bid;
    }

    /**
     * Resources used to publish our configuration file.
     * @return
     */
    @GET
    @Path("/configuration")
    public Configuration getConfiguration() {
        return googleBeanLocal.getConfiguration();
    }
}