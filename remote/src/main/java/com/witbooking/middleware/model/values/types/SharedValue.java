/*
 *  OwnValue.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values.types;

import com.witbooking.middleware.model.values.EnumDataValueType;

import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 30-ene-2013
 */
public class SharedValue<E> implements Value<E>, Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private String ticker;

    /**
     * Creates a new instance of
     * <code>OwnValue</code> without params.
     */
    public SharedValue() {
    }

    public SharedValue(String ticker) {
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    @Override
    public EnumDataValueType getValueType() {
        return EnumDataValueType.SHARED;
    }

    @Override
    public String toString() {
        return "SharedValue{" + "ticker=" + ticker + '}';
    }

    @Override
    public String printStringValue() {
        return ticker;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SharedValue<E> other = (SharedValue<E>) obj;
        if ((this.ticker == null) ? (other.ticker != null) : !this.ticker.equals(other.ticker)) {
            return false;
        }
        return true;
    }


}
