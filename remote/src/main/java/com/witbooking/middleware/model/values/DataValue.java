/*
 *  DataValue.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values;

import com.witbooking.middleware.model.values.types.Value;

import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 25-ene-2013
 */
public abstract class DataValue<E> implements DataValueInterface<E>, Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    protected Value<E> value;

    /**
     * Creates a new instance of
     * <code>DataValue</code> without params.
     */
    public DataValue() {
    }

    public DataValue(Value<E> value) {
        this.value = value;
    }

    @Override
    public Value<E> getValue() {
        return value;
    }

    @Override
    public void setValue(Value<E> value) {
        this.value = value;
    }

    public EnumDataValueType getValueType() {
        if (value != null) {
            return value.getValueType();
        } else {
            return EnumDataValueType.NULL_VALUE;
        }
    }

    @Override
    public String printValue() {
        return value.printStringValue();
    }

    @Override
    public String toString() {
        return "DataValue{" + "value=" + value + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DataValue other = (DataValue) obj;
        if (this.getValue() != null) {
            if (other.getValue() == null) {
                return false;
            }
            if (!this.getValue().equals(other.getValue())) {
                return false;
            }
        }
//        if (this.getValue() != null) {
//            System.out.println("This: " + this.getValue() + " other: " + other.getValue() + "equals: " + this.getValue().equals(other.getValue()));
//        }
        return true;
    }


    public boolean isNullValue() {
        return (value == null || value.getValueType() == EnumDataValueType.NULL_VALUE);
    }

    public boolean isConstantValue() {
        return (value != null && value.getValueType() == EnumDataValueType.CONSTANT);
    }

    public boolean isOwnValue() {
        return (value != null && value.getValueType() == EnumDataValueType.OWN);
    }

    public boolean isSharedValue() {
        return (value != null && value.getValueType() == EnumDataValueType.SHARED);
    }

    public boolean isFormulaValue() {
        return (value != null && value.getValueType() == EnumDataValueType.FORMULA);
    }
}
