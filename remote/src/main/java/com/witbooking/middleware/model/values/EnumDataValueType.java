/*
 *  EnumDataValueType.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values;

import java.io.Serializable;

/**
 * @author Christian Delgado
 */
public enum EnumDataValueType implements Serializable {

    NULL_VALUE(0), CONSTANT(1), OWN(2), SHARED(3), FORMULA(4);
    int type;

    private EnumDataValueType(int type) {
        this.type = type;
    }

    //This is how get values id from DB version 6.1
    public static EnumDataValueType getValidDataValueType(int value) {
        switch (value) {
            case 1:
                return CONSTANT;
            case 2:
                return OWN;
            case 3:
                return SHARED;
            case 4:
                return FORMULA;
            default:
//            throw new DataValueFormatException("Invalid DataValue Type {id: " + value + "}");
                return NULL_VALUE;
        }
    }

    //Values id from DB version 6.1
    public int getType() {
        return type;
    }
}
