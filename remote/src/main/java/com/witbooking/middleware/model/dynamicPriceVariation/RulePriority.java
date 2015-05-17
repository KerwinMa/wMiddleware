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
public enum RulePriority implements Serializable {

    NONE_TYPE(0),HIGH(30), MEDIUM(20), LOW(10);
    int priority;

    private RulePriority(int priority) {
        this.priority = priority;
    }

    public static RulePriority getConditionType(int value) {
        switch (value) {
            case 30:
                return HIGH;
            case 20:
                return MEDIUM;
            case 10:
                return LOW;
            default:
                return NONE_TYPE;
        }
    }

    public int getPriority() {
        return priority;
    }
}
