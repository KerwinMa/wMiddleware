/*
 *  EnumDataValueType.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.dynamicPriceVariation;

import java.io.Serializable;

/**
 * @author Christian Delgado
 */
public enum ConditionType implements Serializable {

    NONE_TYPE(0),CONTRACT(1), STAY(2), EXACT(3), LIKE(4), INCLUDE(5), EXCLUDE(6) , ALL(7);;
    int type;

    private ConditionType(int type) {
        this.type = type;
    }

    //This is how get values id from DB version 6.1
    public static ConditionType getConditionType(int value) {
        switch (value) {
            case 1:
                return CONTRACT;
            case 2:
                return STAY;
            case 3:
                return EXACT;
            case 4:
                return LIKE;
            case 5:
                return LIKE;
            case 6:
                return EXCLUDE;
            case 7:
                return ALL;
            default:
                return NONE_TYPE;
        }
    }

    //Values id from DB version 6.1
    public int getType() {
        return type;
    }
}
