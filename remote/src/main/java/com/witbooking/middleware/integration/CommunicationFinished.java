package com.witbooking.middleware.integration;

/**
 * CommunicationFinished.java
 * User: jose
 * Date: 11/8/13
 * Time: 9:08 AM
 */
public enum CommunicationFinished {
    FINISHED(true), NOT_FINISHED(false);
    private final Boolean value;

    CommunicationFinished(Boolean value) {
        this.value = value;
    }

    public Boolean getValue() {
        return value;
    }
}