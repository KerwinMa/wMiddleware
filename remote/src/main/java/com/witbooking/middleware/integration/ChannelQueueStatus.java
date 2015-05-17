package com.witbooking.middleware.integration;

/**
 * Status.java
 * User: jose
 * Date: 11/5/13
 * Time: 9:42 AM
 */
public enum ChannelQueueStatus {
    FAIL("FAIL"),
    PENDING("PENDING"),
    CANCELED("CANCELED"),
    SUCCESS("SUCCESS");
    private final String value;

    private ChannelQueueStatus(String value) {
        this.value = value;
    }

    public static ChannelQueueStatus fromValue(String v) throws IllegalArgumentException {
        for (ChannelQueueStatus c : ChannelQueueStatus.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public String getValue() {
        return value;
    }
}