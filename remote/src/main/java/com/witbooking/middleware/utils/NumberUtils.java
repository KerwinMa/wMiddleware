/*
 *   NumberUtils.java
 * 
 * Copyright(c) 2014 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.utils;

import org.apache.log4j.Logger;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 03/06/14
 */
public class NumberUtils implements Serializable{

    private static final Logger logger = Logger.getLogger(StringUtils.class);

    /**
     * Round to certain number of decimals
     *
     * @param number
     * @param decimalPlace
     * @return
     */
    public static float roundFloat(float number, int decimalPlace) {
        try {
            BigDecimal bd = new BigDecimal(String.valueOf(number));
            bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
            return bd.floatValue();
        }catch (Exception e){
            logger.error(e);
            return number;
        }
    }

}
