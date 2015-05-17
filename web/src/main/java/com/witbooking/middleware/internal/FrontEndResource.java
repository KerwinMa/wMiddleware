/*
 *  FrontEndResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.internal;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.witbooking.middleware.beans.EmailDataBeanLocal;
import com.witbooking.middleware.beans.FrontEndBeanLocal;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.DailyValuesDBHandler;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.exceptions.DateFormatException;
import com.witbooking.middleware.exceptions.EmailDataException;
import com.witbooking.middleware.exceptions.FrontEndException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.EmailEvent;
import com.witbooking.middleware.model.Inventory;
import com.witbooking.middleware.model.values.HashRangeValue;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.JsonUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * REST Web Service
 *
 * @author Christian Delgado
 */
@Path("internal/frontend")
@Stateless
public class FrontEndResource {

    @EJB
    private FrontEndBeanLocal frontEndBean;
    @EJB
    private EmailDataBeanLocal emailDataBeanLocal;

    private static final Logger logger = Logger.getLogger(FrontEndResource.class);

    /**
     * Creates a new instance of FrontEndResource
     */
    public FrontEndResource() {
    }

    @POST
    @Produces("application/json")
    @Path("/getInventory")
    public String getInventoryByPost(@FormParam("hotelTicker") String hotelTicker, @FormParam("locale") String locale) {
        logger.debug("getInventoryByPost");
        List<Inventory> inventoryList;
        DBConnection dbConnection = null;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            dbConnection = new DBConnection(dbCredential);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, locale);
            inventoryList = frontEndBean.getInventory(inventoryDBHandler);
            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            return gson.toJsonTree(inventoryList) + "";
        } catch (DBAccessException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        } catch (FrontEndException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    @GET
    @Produces("application/json")
    @Path("/getInventory")
    public String getInventoryByGet(@QueryParam("hotelTicker") String hotelTicker,
                                    @QueryParam("locale") String locale) {
        logger.debug("getInventoryByGet");
        List<Inventory> inventoryList;
        DBConnection dbConnection = null;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            dbConnection = new DBConnection(dbCredential);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, locale);
            inventoryList = frontEndBean.getInventory(inventoryDBHandler);
            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
            return gson.toJsonTree(inventoryList) + "";
        } catch (DBAccessException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        } catch (FrontEndException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    @POST
    @Produces("application/json")
    @Path("/getARIValues")
    public String getARIValuesByPost(@FormParam("hotelTicker") String hotelTicker,
                                     @FormParam("locale") String locale,
                                     @FormParam("startDate") String startDateString,
                                     @FormParam("endDate") String endDateString,
                                     @FormParam("currency") String currency) {
        logger.debug("getARIValuesPost");

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtil.stringToCalendarDate(startDateString);
            endDate = DateUtil.stringToCalendarDate(endDateString);
        } catch (DateFormatException ex) {
            return ex.getMessage();
        }
        List<Inventory> inventoryList;
        DBConnection dbConnection = null;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            dbConnection = new DBConnection(dbCredential);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, locale);
            inventoryList = frontEndBean.getInventory(inventoryDBHandler);
            DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, startDate, endDate);
            List<HashRangeValue> mapValues = frontEndBean.getARIValues(dailyValuesDBHandler, inventoryList, startDate, endDate, currency);
            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
            return gson.toJsonTree(mapValues) + "";
        } catch (DBAccessException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        } catch (FrontEndException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        } finally {
            DAOUtil.close(dbConnection);
        }
    }


    @GET
    @Produces("application/json")
    @Path("/getARIValues")
    public String getARIValuesByGet(@QueryParam("hotelTicker") String hotelTicker,
                                    @QueryParam("locale") String locale,
                                    @QueryParam("startDate") String startDateString,
                                    @QueryParam("endDate") String endDateString,
                                    @QueryParam("currency") String currency) {
        logger.debug("getARIValuesByGet");

        Date startDate = null;
        Date endDate = null;
        try {
            startDate = DateUtil.stringToCalendarDate(startDateString);
            endDate = DateUtil.stringToCalendarDate(endDateString);
        } catch (DateFormatException ex) {
            return ex.getMessage();
        }
        List<Inventory> inventoryList;
        DBConnection dbConnection = null;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            dbConnection = new DBConnection(dbCredential);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, locale);
            inventoryList = frontEndBean.getInventory(inventoryDBHandler);
            DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, startDate, endDate);
            List<HashRangeValue> mapValues = frontEndBean.getARIValues(dailyValuesDBHandler, inventoryList, startDate, endDate, currency);
            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
            return gson.toJsonTree(mapValues) + "";
        } catch (DBAccessException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        } catch (FrontEndException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    @GET
    @Path("/test")
    public String testServicesFrontEndByGet() {
        logger.debug("testServicesFrontEndByGet");
        return frontEndBean.testFrontEndServices();
    }

    @POST
    @Path("/test")
    public String testServicesFrontEndByPost(final String requestBody) {
        logger.debug("testServicesFrontEndByPost: " + requestBody);
        String returnString = "raw: '" + requestBody;
        return returnString;
    }

    @POST
    @Path("/mandrillMessageEvents")
    public String mandrillMessageEvents(final @FormParam("mandrill_events") String mandrillData) {
        logger.debug("mandrillMessageEvents: " + mandrillData);
        List<EmailEvent.MandrillEvent> mandrillEvents = JsonUtils.gsonInstance().fromJson(mandrillData, new TypeToken<ArrayList<EmailEvent.MandrillEvent>>() {
        }.getType());
        int totalUpdated = 0;
        for (EmailEvent.MandrillEvent mandrillEvent : mandrillEvents) {
            try {
                totalUpdated += emailDataBeanLocal.saveEmailEventData(mandrillEvent._id, mandrillEvent.event, " ", mandrillEvent.ts, "1");
            } catch (EmailDataException e) {
                logger.error("Could not update email event data for email " + mandrillEvent._id);
            }
        }
        return "" + totalUpdated;
    }

    @GET
    @Path("/dbCredentials")
    public String getTestDBCredentials(@QueryParam("hotelTicker") String hotelTicker) {
        logger.debug("getTestDBCredentials");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            return gson.toJsonTree(frontEndBean.getTestDBCredentials(hotelTicker)) + "";
        } catch (FrontEndException ex) {
            logger.error(ex);
            return gson.toJsonTree(ex) + "";
        }
    }

    private DBCredentials getDBCredentials(String hotelTicker) throws FrontEndException {
        try {
            return DBProperties.getDBCustomerByTicker(hotelTicker);
        } catch (MiddlewareException ex) {
            logger.error("Error getting the DBCredential for the Hotel '" + hotelTicker + "': " + ex.getMessage());
            throw new FrontEndException(ex);
        }
    }
}
