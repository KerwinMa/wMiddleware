package com.witbooking.middleware.model.dynamicPriceVariation;

import java.util.Date;
import java.util.Map;

/**
 * Created by mongoose on 9/29/14.
 */
public interface Evaluable {

    public Boolean evaluate(Map<String, Object> arguments);

}