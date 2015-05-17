package com.witbooking.middleware.model.channelsIntegration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jose Francisco Fiorillo  < jffiorillo@gmail.com >
 * @since 1.0 , Aug 6, 2013
 */
public final class ChannelsHandler {

    /**
     * Stores the
     * <code>ChannelMappingAbstract</code> given. Each
     * <code>ChannelMappingAbstract</code> represents a mapping entry between
     * our system and one channel.
     */
    private List<ChannelMappingAbstract> items;

    /**
     * Creates a new instance of
     * <code>ChannelsHandler</code> without parameters.
     */
    public ChannelsHandler() {
        items = new ArrayList<>();
    }

    /**
     * Searches the channels entries has loaded with the
     * <code>channelTicker</code> and
     * <code>codes</code>.
     *
     * @param channelTicker <code>channelTicker</code> to search.
     * @param codes <code>codes</code> to search.
     * @return if <code>channelTicker</code> and <code>codes</code> are not null
     * and the inventoryTicker is already stored, returns *
     * the <code>inventoryTicker</code> who is represented in the *
     * channel <code>channelTicker</code> with *
     * that <code>codes</code>, <code>null</code> otherwise.
     */
    public String getInventoryTickerFromCodes(final String channelTicker, final Map<String, String> codes) {
        if (channelTicker != null && codes != null) {
            for (ChannelMappingAbstract item : items) {
                if (item != null
                        && item.equalsCodes(channelTicker, codes)) {
                    return item.getInventoryTicker();
                }
            }
        }
        return null;
    }

    /**
     * Searches the
     * <code>mapping</code> who represents the
     * <code>inventoryTicker</code> in the channel
     * <code>channelTicker</code>.
     *
     * @param channelTicker channel ticker which to search.
     * @param inventoryTicker inventory ticker which to search.
     * @return if channnelTicker and inventoryTicker given are not null or the
     * channelTicker codes are not stored yet at mapping      * return <code>null</code>,
     * otherwise <code>ChannelMappingAbstract</code> who represents the <code>mapping</codes> found.
     */
    public ChannelMappingAbstract getCodesFromInventoryTicker(final String channelTicker, final String inventoryTicker) {
        if (inventoryTicker != null) {
            for (ChannelMappingAbstract item : items) {
                if (item != null
                        && item.equalsInventoryTicker(channelTicker, inventoryTicker)) {
                    return item;
                }
            }
        }
        return null;
    }

    /**
     * This function add a new
     * <code>ChannelMappingAbstract</code> to the list.
     *
     * @param elem the <code>ChannelMappingAbstract</code> to store.
     */
    public void add(ChannelMappingAbstract elem) {
        if (elem != null) {
            this.items.add(elem);
        }
    }
    
    /**
     * This function finds if already have the <code>ChannelMappingAbstract</code> given to updated it, otherwise insert it.
     * If <code>item</code> is null, do nothing.
     *
     * @param elem the <code>ChannelMappingAbstract</code> to updated or insert.
     */
    public void update(final ChannelMappingAbstract elem) {
        if (elem != null && elem.getInventoryTicker() != null && elem.getChannelTicker() != null) {
            for(ChannelMappingAbstract item: this.items){
                if (item.equalsInventoryTicker(elem.getChannelTicker(), elem.getInventoryTicker())){
                    items.remove(item);
                    break;
                }
            }
            this.items.add(elem);
        }
    }

    public List<ChannelMappingAbstract> getItems() {
        return items;
    }

    public void setItems(List<ChannelMappingAbstract> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "ChannelHandler{" + "items=" + items + '}';
    }
}
