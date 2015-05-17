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
public class OwnValue<E> implements Value<E>, Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private E defaultValue;

    /**
     * Creates a new instance of
     * <code>OwnValue</code> without params.
     */
    public OwnValue() {
    }

    public OwnValue(E defaultValue) {
        this.defaultValue = defaultValue;
    }

    public E getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(E defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public EnumDataValueType getValueType() {
        return EnumDataValueType.OWN;
    }

    @Override
    public String toString() {
        return "OwnValue{" + "defaultValue=" + defaultValue + '}';
    }

    @Override
    public String printStringValue() {
        return defaultValue + "";
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OwnValue<E> other = (OwnValue<E>) obj;
        if (this.defaultValue != other.defaultValue && (this.defaultValue == null || !this.defaultValue.equals(other.defaultValue))) {
            return false;
        }
        return true;
    }

}
