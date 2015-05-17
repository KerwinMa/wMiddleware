/*
 *  TransferData.java
 * 
 * Copyright(c) 2014 Witbooking.com. All Rights Reserved.
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
 * @date 20-oct-2014
 */
public class TransferData implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    private String ticker;
    private String message;
    private Integer releaseMin;
    private Integer lockHours;

    /**
     * Creates a new instance of
     * <code>TransferData</code> without params.
     */
    public TransferData() {
    }

    public TransferData(String ticker, String message, Integer releaseMin, Integer lockHours) {
        this.ticker = ticker;
        this.message = message;
        this.releaseMin = releaseMin;
        this.lockHours = lockHours;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getReleaseMin() {
        return releaseMin;
    }

    public void setReleaseMin(Integer releaseMin) {
        this.releaseMin = releaseMin;
    }

    public Integer getLockHours() {
        return lockHours;
    }

    public void setLockHours(Integer lockHours) {
        this.lockHours = lockHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TransferData)) return false;

        TransferData that = (TransferData) o;

        if (lockHours != null ? !lockHours.equals(that.lockHours) : that.lockHours != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (releaseMin != null ? !releaseMin.equals(that.releaseMin) : that.releaseMin != null) return false;
        if (ticker != null ? !ticker.equals(that.ticker) : that.ticker != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = ticker != null ? ticker.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (releaseMin != null ? releaseMin.hashCode() : 0);
        result = 31 * result + (lockHours != null ? lockHours.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TransferData{" +
                "ticker='" + ticker + '\'' +
                ", message='" + message + '\'' +
                ", releaseMin=" + releaseMin +
                ", lockHours=" + lockHours +
                '}';
    }
}
