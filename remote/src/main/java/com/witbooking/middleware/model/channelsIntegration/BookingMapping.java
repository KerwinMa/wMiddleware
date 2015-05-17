/**
 * BookingMapping.java
 *
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved. This software is the
 * proprietary information of Witbooking.com
 */
package com.witbooking.middleware.model.channelsIntegration;

import com.witbooking.middleware.integration.booking.model.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * BookingMapping class represents a Booking type entry. Has the name of the
 * keys and channel ticker Booking types.
 *
 * @author Jose Francisco Fiorillo  < jffiorillo@gmail.com >
 * @see ChannelMappingAbstract
 * @since 1.0 , Aug 7, 2013
 */
public final class BookingMapping extends ChannelMappingAbstract {

    /**
     * Creates a new instance of
     * <code>BookingMapping</code>.
     *
     * @param inventoryTicker Inventory Ticker of this entry.
     * @param roomStayTicker  Room Stay ticker of this entry.
     * @param ratePlanTicker  Rate Plan code of this entry.
     */
    public BookingMapping(String inventoryTicker, String roomStayTicker, String ratePlanTicker) {
        super(inventoryTicker, Constants.NUMBER_OF_KEYS);
        mapping.put(Constants.KEY_ROOM_STAY, roomStayTicker);
        mapping.put(Constants.KEY_RATE_PLAN, ratePlanTicker);
    }

    /**
     * Creates a new instance of
     * <code>BookingMapping</code> from the inventoryTicker and a
     * <code>Hashmap</code> how contents the entries of the keys (
     * <code>KEY_ROOM_STAY</code>,
     * <code>KEY_RATE_PLAN</code>).
     *
     * @param inventoryTicker Inventory Ticker of this entry.
     * @param mapping         <code>HashMap</code> who has the values of <code>KEY_ROOM_STAY</code> and <code>KEY_RATE_PLAN</code> .
     */
    public BookingMapping(String inventoryTicker, Map<String, String> mapping) {
        super(inventoryTicker, Constants.NUMBER_OF_KEYS);
        final String roomStayTicker =
                mapping != null
                        ? mapping.get(Constants.KEY_ROOM_STAY)
                        : null;
        final String ratePlanTicker =
                mapping != null
                        ? mapping.get(Constants.KEY_RATE_PLAN)
                        : null;
        this.mapping.put(Constants.KEY_ROOM_STAY, roomStayTicker);
        this.mapping.put(Constants.KEY_RATE_PLAN, ratePlanTicker);
    }

    /**
     * Returns the Room Stay ticker of this entry. Find in mapping the value of
     * KEY_ROOM_STAY and return it.
     *
     * @return Room Stay ticker.
     */
    protected String getRoomStay() {
        return mapping.get(Constants.KEY_ROOM_STAY);
    }

    /**
     * Returns the Rate Plan ticker of this entry. Find in mapping the value of
     * KEY_RATE_PLAN and return it.
     *
     * @return Rate Plan ticker.
     */
    protected String getRatePlan() {
        return mapping.get(Constants.KEY_RATE_PLAN);
    }

    /**
     * Returns the channel ticker of this entry. Channel ticker is a static
     * variable defined in the class.
     *
     * @return channel ticker.
     */
    @Override
    public String getChannelTicker() {
        return Constants.CHANNEL_TICKER;
    }

    /**
     * This method is used to generate the skeleton of a Booking type HashMap
     * and fill the values of each key and after use it to equalsCodes function.
     * <p/>
     * Create new HashMap and insert the Booking type keys on it. The value in
     * the HashMap for each key is null.
     *
     * @param roomStayTicker room stay ticker.
     * @param ratePlanTicker rate plan ticker.
     * @return Returns the skeleton of a Booking type HashMap.
     */
    public static Map<String, String> genHashToCompare(String roomStayTicker, String ratePlanTicker) {
        Map<String, String> ret = new HashMap<>(Constants.NUMBER_OF_KEYS);
        ret.put(Constants.KEY_ROOM_STAY, roomStayTicker);
        ret.put(Constants.KEY_RATE_PLAN, ratePlanTicker);
        return ret;
    }

    /**
     * This method is used to generate the skeleton of a Booking type HashMap
     * and fill the values of each key and after use it to equalsCodes function.
     * <p/>
     * Create new HashMap and insert the Booking type keys on it. The value in
     * the HashMap for each key is null.
     *
     * @return Returns the skeleton of a Booking type HashMap.
     */
    public static Map<String, String> genHashToCompare() {
        Map<String, String> ret = new HashMap<>(Constants.NUMBER_OF_KEYS);
        ret.put(Constants.KEY_ROOM_STAY, null);
        ret.put(Constants.KEY_RATE_PLAN, null);
        return ret;
    }

    /**
     * Indicates if whether some other channelTicker is "equal to" Booking
     * channelTicker.
     *
     * @param channelTicker the channelTicker with which to compare.
     * @return <code>true</code> if channelTicker given is not null and is
     *         equals to Booking channelTicker, <code>false</code> otherwise.
     */
    public static boolean isSameTicketChannel(final String channelTicker) {
        return channelTicker != null
                && Constants.CHANNEL_TICKER.equals(channelTicker);
    }

    @Override
    public String toString() {
        final String roomStayTicker = getRoomStay() == null ? "" : getRoomStay();
        final String ratePlanTicker = getRatePlan() == null ? "" : getRatePlan();
        return "BookingMapping{" + "inventoryTicker=" + inventoryTicker
                + ", roomStayTicker=" + roomStayTicker + ", ratePlanTicker="
                + ratePlanTicker + '}';
    }

//    @Override
//    public boolean equals(Object obj) {
//         if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        //final BookingMapping other = (BookingMapping) obj;
//        return super.equals(obj);
//    }
}
