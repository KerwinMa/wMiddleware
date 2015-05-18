/*
 *  FrontEndBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.handlers.BookingPriceRuleDBHandler;
import com.witbooking.middleware.db.handlers.DailyValuesDBHandler;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.exceptions.BookingPriceRuleException;
import com.witbooking.middleware.exceptions.FrontEndException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.Inventory;
import com.witbooking.middleware.model.dynamicPriceVariation.BookingPriceRule;
import com.witbooking.middleware.model.values.HashRangeValue;

import javax.ejb.Stateless;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Wrapper bean for BookingPriceRulesActions
 *
 * @author Jorge Lucic
 * @version 1.0
 * @date 11/29/14
 * @since 1.0
 */

@Stateless
public class BookingPriceRuleBean implements BookingPriceRuleBeanRemote, BookingPriceRuleBeanLocal {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BookingPriceRuleBean.class);


    @Override
    public List<BookingPriceRule> getRules(String ticker) throws BookingPriceRuleException {
        BookingPriceRuleDBHandler bookingPriceRuleDBHandler=new BookingPriceRuleDBHandler();
        List<BookingPriceRule> bookingPriceRules;
        try {
            bookingPriceRules=bookingPriceRuleDBHandler.getBookingPriceRules(ticker);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BookingPriceRuleException(e);
        }
        return bookingPriceRules;
    }

    @Override
    public BookingPriceRule setRule(String ticker, BookingPriceRule bookingPriceRule) throws BookingPriceRuleException {
        BookingPriceRuleDBHandler bookingPriceRuleDBHandler=new BookingPriceRuleDBHandler();
        try {
            bookingPriceRule=bookingPriceRuleDBHandler.setBookingPriceRules(ticker,bookingPriceRule);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BookingPriceRuleException(e);
        }
        return bookingPriceRule;
    }

    @Override
    public Long deleteRule(String ticker, String id) throws BookingPriceRuleException {
        BookingPriceRuleDBHandler bookingPriceRuleDBHandler=new BookingPriceRuleDBHandler();
        Long result;
        try {
            result=bookingPriceRuleDBHandler.deleteBookingPriceRules(ticker,id);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new BookingPriceRuleException(e);
        }
        return result;
    }

}
