/*
 *  FrontEndResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.internal;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.witbooking.middleware.beans.BookingPriceRuleBean;
import com.witbooking.middleware.beans.BookingPriceRuleBeanLocal;
import com.witbooking.middleware.exceptions.BookingPriceRuleException;
import com.witbooking.middleware.model.dynamicPriceVariation.BookingPriceRule;
import com.witbooking.middleware.utils.JsonUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.List;

/**
 * REST Endpoint for Booking Price Rules
 *
 * @author Jorge Lucic
 * @version 1.0
 * @date 11/29/14
 * @since 1.0
 */
@Path("bookingPriceRule")
@Stateless
public class BookingPriceRuleResource {

    @EJB
    private BookingPriceRuleBeanLocal bookingPriceRuleBeanLocal;
    private static final Logger logger = Logger.getLogger(BookingPriceRuleResource.class);

    /**
     * Creates a new instance of BookingPriceRuleResource
     */
    public BookingPriceRuleResource() {
    }



    @GET
    @Produces("application/json")
    @Path("{ticker}")
    public String getBookingPriceRules(@PathParam("ticker") String ticker) {
        logger.debug("getBookingPriceRules");
        try {
            if(bookingPriceRuleBeanLocal==null){bookingPriceRuleBeanLocal=new BookingPriceRuleBean();}
            List<BookingPriceRule> bookingPriceRules=bookingPriceRuleBeanLocal.getRules(ticker);
            Gson gson = JsonUtils.gsonInstance();
            return gson.toJson(bookingPriceRules,new TypeToken<ArrayList<BookingPriceRule>>(){}.getType());
        } catch (BookingPriceRuleException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        }
    }

    @GET
    @Produces("application/json")
    @Path("{ticker}/{id}")
    public String getBookingPriceRules(@PathParam("ticker") String ticker, @PathParam("id") String id) {
        logger.debug("getBookingPriceRules");
        try {
            if(bookingPriceRuleBeanLocal==null){bookingPriceRuleBeanLocal=new BookingPriceRuleBean();}
            List<BookingPriceRule> bookingPriceRules=bookingPriceRuleBeanLocal.getRules(ticker);
            Gson gson = JsonUtils.gsonInstance();
            try{
                Long idL=Long.parseLong(id, 10);
                for (BookingPriceRule bookingPriceRule:bookingPriceRules){
                    if(bookingPriceRule.getId().equals(idL)){
                        return gson.toJson(bookingPriceRule,BookingPriceRule.class);
                    }
                }
            }catch (NumberFormatException ex){
                logger.error("Error in rule ID '" + id + "' hotel: '" + ticker + "'");
                return gson.toJson(null);
            }
            return gson.toJson(null);
        } catch (BookingPriceRuleException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        }
    }

    @POST
    @Produces("application/json")
    @Path("{ticker}")
    public String setBookingPriceRule(String bookingPriceRuleJson, @PathParam("ticker") String ticker) {
        BookingPriceRule bookingPriceRule;
        Gson gson = JsonUtils.gsonInstance();
        try {
            if(bookingPriceRuleBeanLocal==null){bookingPriceRuleBeanLocal=new BookingPriceRuleBean();}
            bookingPriceRule = gson.fromJson(bookingPriceRuleJson,BookingPriceRule.class);
            bookingPriceRule=bookingPriceRuleBeanLocal.setRule(ticker,bookingPriceRule);
            return String.valueOf(bookingPriceRule.getId());
        }  catch (BookingPriceRuleException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        }catch (JsonSyntaxException ex) {
            logger.error(ex);
            return ex.getMessage();
        }
    }

    @DELETE
    @Produces("application/json")
    @Path("{ticker}/{id}")
    public String deleteBookingPriceRule(@PathParam("ticker") String ticker, @PathParam("id") String id) {
        try {
            if(bookingPriceRuleBeanLocal==null){bookingPriceRuleBeanLocal=new BookingPriceRuleBean();}
            return String.valueOf(bookingPriceRuleBeanLocal.deleteRule(ticker,id));
        }  catch (BookingPriceRuleException ex) {
            logger.error(ex);
            return ex.getUserMessage();
        }
    }


}
