package com.witbooking.middleware.model;

import com.witbooking.middleware.model.values.HashRangeValue;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * ARI.java
 * User: jose
 * Date: 12/13/13
 * Time: 1:25 PM
 */
public class ARI implements Serializable {

    private List<Inventory> inventories;
    private Map<String, HashRangeValue> mapHashRangeValue;
    private List<Discount> discounts;
    //private List<FrontEndMessage> frontEndMessageses;
    private Properties configProperties;

    public ARI(List<Inventory> inventories, List<Discount> discounts) {
        this.inventories = inventories;
        this.discounts = discounts;
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public Map<String, HashRangeValue> getMapHashRangeValue() {
        return mapHashRangeValue;
    }

    public void setMapHashRangeValue(Map<String, HashRangeValue> mapHashRangeValue) {
        this.mapHashRangeValue = mapHashRangeValue;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public Properties getConfigProperties() {
        return configProperties;
    }

    public void setConfigProperties(Properties configProperties) {
        this.configProperties = configProperties;
    }
}