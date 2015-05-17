/*
 *  OrderedService.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 29-ene-2014
 */
public class ServiceRequested implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private Integer serviceId;
    private String serviceTicker;
    private String serviceName;
    private boolean daily;
    private byte type;//0=habitacion, 1=persona, 2=unidad
    private int quantity;
    private float totalServiceAmount;
    //private RangeValue<Float> servicePrice;

    /**
     * Creates a new instance of
     * <code>ServiceRequested</code> without params.
     */
    public ServiceRequested() {
    }

    //public ServiceRequested()

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public float getTotalServiceAmount() {
        return totalServiceAmount;
    }

    public void setTotalServiceAmount(float totalServiceAmount) {
        this.totalServiceAmount = totalServiceAmount;
    }

    public String getServiceTicker() {
        return serviceTicker;
    }

    public void setServiceTicker(String serviceTicker) {
        this.serviceTicker = serviceTicker;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public boolean isDaily() {
        return daily;
    }

    public void setDaily(boolean daily) {
        this.daily = daily;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    // values from OTA ServicePricingType Enum
    public void setServiceTypeFromOTA(int servicePricingType) {
        type = 2;
        daily = false;
        if(servicePricingType==3||servicePricingType==4||servicePricingType==6){
            daily=true;
        }
        if(servicePricingType==2||servicePricingType==4||servicePricingType==6){
            type=1;
        }
        if(servicePricingType==1||servicePricingType==3||servicePricingType==5){
            type=0;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ServiceRequested)) return false;

        ServiceRequested that = (ServiceRequested) o;
        if (quantity != that.quantity) return false;
        if (Float.compare(that.totalServiceAmount, totalServiceAmount) != 0) return false;
        if (serviceId != null ? !serviceId.equals(that.serviceId) : that.serviceId != null) return false;
//        if (servicePrice != null ? !servicePrice.equals(that.servicePrice) : that.servicePrice != null) return false;
        if (serviceName != null ? !serviceName.equals(that.serviceName) : that.serviceName != null) return false;
        if (serviceTicker != null ? !serviceTicker.equals(that.serviceTicker) : that.serviceTicker != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serviceId != null ? serviceId.hashCode() : 0;
        result = 31 * result + (serviceTicker != null ? serviceTicker.hashCode() : 0);
        result = 31 * result + (serviceName != null ? serviceName.hashCode() : 0);
        result = 31 * result + (totalServiceAmount != +0.0f ? Float.floatToIntBits(totalServiceAmount) : 0);
//        result = 31 * result + (servicePrice != null ? servicePrice.hashCode() : 0);
        result = 31 * result + quantity;
        return result;
    }
}
