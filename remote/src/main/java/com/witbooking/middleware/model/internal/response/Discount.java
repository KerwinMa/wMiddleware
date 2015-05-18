package com.witbooking.middleware.model.internal.response;

import java.util.Date;
import java.util.List;

/**
 * Discount.java
 * User: jose
 * Date: 12/3/13
 * Time: 1:09 PM
 */
public class Discount {

    private String name;
    private String description;
    private Date startValidPeriod;
    private Date endValidPeriod;
    private Float discount;
    /**
     * This list represents any of those values: Min stay, max stay, min notice, max notice.
     * Can be null if don't have any of those values configured.
     */
    List<String> items;
    private Boolean cumulative;
    private String promotionalCode;
}