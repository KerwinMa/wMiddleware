package com.witbooking.middleware.integration;

/**
 * ChannelConnectionType.java
 * User: jose
 * Date: 11/5/13
 * Time: 9:41 AM
 */
public enum ChannelConnectionType {
    EXECUTE_UPDATE_ARI("EXECUTE_UPDATE_ARI"),
    EXECUTE_UPDATE_AMOUNT("EXECUTE_UPDATE_AMOUNT"),
    GET_RESERVES("GET_RESERVES"),
    GET_MODIFIED_RESERVES("GET_MODIFIED_RESERVES"),
    GET_CANCELLED_RESERVES("GET_CANCELLED_RESERVES"),
    OFFER_HOTEL_NAME("OFFER_HOTEL_NAME"),
    OFFER_INVENTORY("OFFER_INVENTORY"),
    OFFER_ARI("OFFER_ARI"),
    OFFER_UPDATE_ARI("OFFER_UPDATE_ARI"),
    OFFER_UPDATE_AVAILABILITY_ARI("OFFER_UPDATE_AVAILABILITY_ARI"),
    OFFER_UPDATE_AMOUNT_ARI("OFFER_UPDATE_AMOUNT_ARI"),
    NOTIFY_RESERVES("NOTIFY_RESERVES"),
    NOTIFY_MODIFIED_RESERVES("NOTIFY_MODIFIED_RESERVES"),
    NOTIFY_CANCELLED_RESERVES("NOTIFY_CANCELLED_RESERVES"),
    CREATE_MAIL_REQUEST("CREATE_MAIL_REQUEST"),
    LIST_MAIL_REQUEST_FROM_HOTEL("LIST_MAIL_REQUEST_FROM_HOTEL"),
    LIST_MAIL_REQUEST_FROM_RESERVATION("LIST_MAIL_REQUEST_FROM_RESERVATION"),
    UPDATE_MAIL_REQUEST("UPDATE_MAIL_REQUEST"),
    CANCEL_MAIL_REQUEST("CANCEL_MAIL_REQUEST"),
    CHECK_OPT_IN("CHECK_OPT_IN");
    private final String value;

    private ChannelConnectionType(String value) {
        this.value = value;
    }

    public static ChannelConnectionType fromValue(String v) {
        for (ChannelConnectionType c : ChannelConnectionType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException("Invalid ChannelConnectionType: "+v);
    }

    public String getValue() {
        return value;
    }

    public String getPushTypeValue() {
        if (this == NOTIFY_RESERVES)
            return "create";
        if (this == NOTIFY_MODIFIED_RESERVES)
            return "modify";
        if (this == NOTIFY_CANCELLED_RESERVES)
            return "cancel";
        throw new IllegalArgumentException("Invalid Push Value");
    }
}
