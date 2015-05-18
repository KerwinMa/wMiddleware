/*
 *   DiscountApplied.java
 * 
 * Copyright(c) 2014 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.witbooking.middleware.model.dynamicPriceVariation.BookingPriceRule;
import com.witbooking.middleware.model.values.RangeValue;

import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 15/04/14
 */
public class BookingPriceRulesApplied implements Serializable {

    private BookingPriceRule bookingPriceRule;
    private RangeValue<Float> variationAmount;
    private float totalVariationAmount;

    public BookingPriceRule getBookingPriceRule() {
        return bookingPriceRule;
    }

    public void setBookingPriceRule(BookingPriceRule bookingPriceRule) {
        this.bookingPriceRule = bookingPriceRule;
    }

    public RangeValue<Float> getVariationAmount() {
        return variationAmount;
    }

    public void setVariationAmount(RangeValue<Float> variationAmount) {
        this.variationAmount = variationAmount;
    }

    public float getTotalVariationAmount() {
        return totalVariationAmount;
    }

    public void setTotalVariationAmount(float totalVariationAmount) {
        this.totalVariationAmount = totalVariationAmount;
    }
}
