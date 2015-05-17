/*
 *  FrontEndBeanLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.BookingPriceRuleException;
import com.witbooking.middleware.model.dynamicPriceVariation.BookingPriceRule;

import javax.ejb.Local;
import java.util.List;

/**
 *
 *
 * @author Jorge Lucic
 * @version 1.0
 * @date 11/29/14
 * @since 1.0
 */
@Local
public interface BookingPriceRuleBeanLocal {

    public List<BookingPriceRule> getRules(String ticker) throws BookingPriceRuleException;

    public BookingPriceRule setRule(String ticker, BookingPriceRule bookingPriceRule) throws BookingPriceRuleException;

    public Long deleteRule(String ticker, String id) throws BookingPriceRuleException;

}