/*
 *   DiscountApplied.java
 * 
 * Copyright(c) 2014 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.witbooking.middleware.model.values.RangeValue;

import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 15/04/14
 */
public class DiscountApplied implements Serializable {

    private Integer discountId;
    private String discountTicker;
    private String discountName;
    private boolean percentage = true;//True=percentage, False=price
    private float reduction;
    private RangeValue<Float> discountPrice;
    private float totalDiscountAmount;

    public Integer getDiscountId() {
        return discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

    public String getDiscountTicker() {
        return discountTicker;
    }

    public void setDiscountTicker(String discountTicker) {
        this.discountTicker = discountTicker;
    }

    public String getDiscountName() {
        return discountName;
    }

    public void setDiscountName(String discountName) {
        this.discountName = discountName;
    }

    public float getTotalDiscountAmount() {
        return totalDiscountAmount;
    }

    public void setTotalDiscountAmount(float totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public RangeValue<Float> getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(RangeValue<Float> discountPrice) {
        this.discountPrice = discountPrice;
    }


    public boolean isPercentage() {
        return percentage;
    }

    public void setPercentage(boolean percentage) {
        this.percentage = percentage;
    }

    public float getReduction() {
        return reduction;
    }

    public void setReduction(float reduction) {
        this.reduction = reduction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DiscountApplied)) return false;

        DiscountApplied that = (DiscountApplied) o;

        if (percentage != that.percentage) return false;
        if (Float.compare(that.totalDiscountAmount, totalDiscountAmount) != 0) return false;
        if (Float.compare(that.reduction, reduction) != 0) return false;
        if (discountId != null ? !discountId.equals(that.discountId) : that.discountId != null) return false;
        if (discountName != null ? !discountName.equals(that.discountName) : that.discountName != null) return false;
        if (discountPrice != null ? !discountPrice.equals(that.discountPrice) : that.discountPrice != null)
            return false;
        if (discountTicker != null ? !discountTicker.equals(that.discountTicker) : that.discountTicker != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = discountId != null ? discountId.hashCode() : 0;
        result = 31 * result + (discountTicker != null ? discountTicker.hashCode() : 0);
        result = 31 * result + (discountName != null ? discountName.hashCode() : 0);
        result = 31 * result + (percentage ? 1 : 0);
        result = 31 * result + (totalDiscountAmount != +0.0f ? Float.floatToIntBits(totalDiscountAmount) : 0);
        result = 31 * result + (reduction != +0.0f ? Float.floatToIntBits(reduction) : 0);
        result = 31 * result + (discountPrice != null ? discountPrice.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DiscountApplied{" +
                "discountId=" + discountId +
                ", discountTicker='" + discountTicker + '\'' +
                ", discountName='" + discountName + '\'' +
                ", percentage=" + percentage +
                ", totalDiscountAmount=" + totalDiscountAmount +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
