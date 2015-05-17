package com.witbooking.middleware.model.response;

import com.witbooking.middleware.model.Accommodation;
import org.matheclipse.generic.interfaces.Pair;

import java.util.Date;
import java.util.List;

/**
 * Room.java
 * User: jose
 * Date: 12/3/13
 * Time: 9:55 AM
 */
public class Room {

    private List<String> images;
    private String description;
    private Float minPrice;
    List<Elem> elems;
    private Accommodation accommodation;

    public static final class Elem {
        private String conditionName, configurationName, mealPlanName, inventoryId;
        private Float price;
        private Integer avaibality;
        //FECHA DE APLICACION
        private List<Pair<Date, String>> discountsApplied;
        private boolean spent;
    }
}