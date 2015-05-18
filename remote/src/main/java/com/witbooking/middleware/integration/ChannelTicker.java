package com.witbooking.middleware.integration;

/**
 * ChannelTicker.java
 * User: jose
 * Date: 11/5/13
 * Time: 9:40 AM
 */
public enum ChannelTicker {
    BOOKING("BOOKING"),
    TRIPADVISOR("TRIPADVISOR"),
    RATEGAIN("RATEGAIN"),
    SITEMINDER("SITEMINDER"),
    PARITYRATE("PARITYRATE"),
    DINGUS("DINGUS"),
    OTHER("OTHER");

    private final String value;

    private ChannelTicker(String value) {
        this.value = value;
    }

    public static ChannelTicker fromValue(String v) throws IllegalArgumentException {
        for (ChannelTicker c : ChannelTicker.values()) {
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
