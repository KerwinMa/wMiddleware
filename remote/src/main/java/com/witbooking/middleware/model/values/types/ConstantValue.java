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
public class ConstantValue<E> implements Value<E>, Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private E constantValue;

    /**
     * Creates a new instance of
     * <code>OwnValue</code> without params.
     */
    public ConstantValue() {
    }

    public ConstantValue(E constantValue) {
        this.constantValue = constantValue;
    }

    public E getConstantValue() {
        return constantValue;
    }

    public void setConstantValue(E constantValue) {
        this.constantValue = constantValue;
    }

    @Override
    public String toString() {
        return "ConstantValue{" + "constantValue=" + constantValue + '}';
    }

    @Override
    public EnumDataValueType getValueType() {
        return EnumDataValueType.CONSTANT;
    }

    @Override
    public String printStringValue() {
        return constantValue + "";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ConstantValue other = (ConstantValue) obj;
        return this.getValue().equals(other.getValue());
    }

    public E getValue() {
        return constantValue;
    }
}
