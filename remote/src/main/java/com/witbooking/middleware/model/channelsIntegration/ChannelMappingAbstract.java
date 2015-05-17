/**
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved. This software is the
 * proprietary information of Witbooking.com
 */
package com.witbooking.middleware.model.channelsIntegration;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ChannelMappingAbstract is the abstract base class for all mapping channel
 * class. Implements all the necessary functions to compare and get the values
 * of each channel codes.
 * <p>
 * All mapping channel class should extends of this class and define their codes
 * and static function genHashToCompare.
 *
 * @author Jose Francisco Fiorillo  < jffiorillo@gmail.com >
 * @since 1.0 , Aug 7, 2013
 */
public abstract class ChannelMappingAbstract {

    /**
     * Represents the inventory ticker of this entry.
     */
    protected final String inventoryTicker;
    /**
     * Stores the keys and values of the input. Keys are defined in the child
     * class.
     */
    protected Map<String, String> mapping;

    /**
     * Creates a new instance of <code>ChannelMappingAbstract</code>.
     *
     * @param inventoryTicker Inventory ticker of this entry.
     * @param initialCapacity Number of keys stores on mapping.
     */
    protected ChannelMappingAbstract(String inventoryTicker, int initialCapacity) {
        this.inventoryTicker = inventoryTicker;
        mapping = new HashMap<>(initialCapacity);
    }

    /**
     * Returns the channelTicker defined in the child class. The channel ticker
     * represents the channel type. Eg: "BOOKING.COM" represents BookingMapping
     * class. getChannelTicker of BookingMapping object return "BOOKING.COM".
     *
     * @return the channelTicker that represents the child class.
     */
    public abstract String getChannelTicker();

    /**
     * Creates an returns a copy of <code>mapping</code>.
     *
     * @return clone of <code>mapping</code>.
     */
    public Map<String, String> getMapping() {
        Map<String, String> clone = new HashMap<>();
        for (Entry<String, String> item : mapping.entrySet()) {
            clone.put(item.getKey(), item.getValue());
        }
        return clone;
    }

    /**
     * InventoryTicker getter.
     *
     * @return inventoryTicker.
     */
    public String getInventoryTicker() {
        return this.inventoryTicker;
    }

    /**
     * Compare the channelTicker given with his our channelTicker.
     *
     * @param channelTicker the channelTicker with which to compare.
     * @return <code>true</code> if channelTicker is not null and his our
     * channelTicker is equals to channelTicker given, *      * otherwise <code>false</code>.
     */
    protected boolean equalsChannelTicker(final String channelTicker) {
        //return channelTicker != null ? channelTicker.equals(getChannelTicker()) : getChannelTicker() == null;
        return channelTicker != null ? channelTicker.equals(getChannelTicker()) : false;
    }

    /**
     * Compare the channelTicker and codes given with his our channelTicker and
     * codes (mapping).
     *
     * @param channelTicker the channelTicker with which to compare.
     * @param codes the codes with which to compare.
     * @return <code>true</code> if channelTicker given are not null and equals
     * to his our respectively, otherwise <code>false</code>.
     */
    public boolean equalsCodes(final String channelTicker, final Map<String, String> codes) {
        return equalsChannelTicker(channelTicker)
                && codes != null
                && codes.equals(mapping);
    }

    /**
     * Compare the channelTicker and inventoryTicker given with his our
     * channelTicker and inventoryTicker respectively.
     *
     * @param channelTicker the channelTicker with which to compare.
     * @param inventoryTicker the inventoryTicker with which to compare.
     * @return <code>true</code> if inventoryTicker and channelTicker given are
     * not null and his our inventoryTicker and channelTicker are equals to
     * inventoryTicker and channelTicker given, otherwise <code>false</code>.
     */
    public boolean equalsInventoryTicker(final String channelTicker, final String inventoryTicker) {
        return inventoryTicker != null
                && equalsChannelTicker(channelTicker)
                && inventoryTicker.equals(this.inventoryTicker);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChannelMappingAbstract other = (ChannelMappingAbstract) obj;
        if ((this.inventoryTicker == null) ? (other.inventoryTicker != null) : !this.inventoryTicker.equals(other.inventoryTicker)) {
            return false;
        }
        if (this.mapping != other.mapping && (this.mapping == null || !this.mapping.equals(other.mapping))) {
            return false;
        }
        return true;
    }
}
