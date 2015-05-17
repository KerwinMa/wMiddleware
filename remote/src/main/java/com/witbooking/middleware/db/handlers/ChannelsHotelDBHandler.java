package com.witbooking.middleware.db.handlers;

import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.sqlinstructions.SQLInstructions;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.exceptions.integration.booking.BookingException;
import com.witbooking.middleware.integration.booking.model.Constants;
import com.witbooking.middleware.model.channelsIntegration.BookingMapping;
import com.witbooking.middleware.model.channelsIntegration.Channel;
import com.witbooking.middleware.model.channelsIntegration.ChannelMappingAbstract;
import com.witbooking.middleware.model.channelsIntegration.ChannelsHandler;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * <code>ChannelsHotelDBHandler</code> class is responsible for managing
 * connections to the database to get the mapping of values between the channels
 * and our system.
 *
 * @author Jose Francisco Fiorillo
 * @author jffiorillo@gmail.com
 */
public final class ChannelsHotelDBHandler extends DBHandler {

    private static final Logger logger = Logger.getLogger(ChannelsHotelDBHandler.class);
    /**
     * channelsHandler is the
     * <code>Object</code> who store the entries searched in the database. This
     * will avoid having to re-search the database and input sought.
     */
    private final ChannelsHandler channelsHandler = new ChannelsHandler();
    /**
     * channels is a
     * <code>List</code> who store the entries searched in the database. This
     * will avoid having to re-search the database and input sought.
     */
    private final List<Channel> channels = new ArrayList<Channel>();

    /**
     * Creates a new instance of
     * <code>ChannelsHotelDBHandler</code> without parameters.
     */
    public ChannelsHotelDBHandler() {
        super();
    }

    /**
     * Creates a new instance of
     * <code>ChannelsHotelDBHandler</code>.
     *
     * @param dbConnection the database connection.
     */
    public ChannelsHotelDBHandler(DBConnection dbConnection) {
        super(dbConnection);
    }

    private List<Channel> getChannels(String sqlCommand, List values)
            throws DBAccessException {
        PreparedStatement statement = prepareStatement(sqlCommand, values);
        ResultSet resultSet = execute(statement);
        List<Channel> ret = getChannelFromResulSet(resultSet);
        DAOUtil.close(statement, resultSet);
        return ret;
    }

    public List<Channel> getChannels()
            throws DBAccessException {
        PreparedStatement statement = prepareStatement(SQLInstructions.ChannelsHotelDBHandler.SELECT_CHANNELS);
        ResultSet resultSet = execute(statement);
        List<Channel> ret = getChannelFromResulSet(resultSet);
        DAOUtil.close(statement, resultSet);
        return ret;
    }

    private ChannelMappingAbstract getChannelMapping(String sqlCommand,
                                                     String channelTicker,
                                                     String inventoryTicker,
                                                     List values)
            throws DBAccessException {
        PreparedStatement statement = prepareStatement(sqlCommand, values);
        ResultSet resultSet = execute(statement);
        ChannelMappingAbstract ret = getIntegrationChannelInterfaceFromResultSet(resultSet, channelTicker, inventoryTicker);
        DAOUtil.close(statement, resultSet);
        return ret;
    }

    private List<Channel> getChannelFromResulSet(ResultSet resultSet)
            throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        List<Channel> items = new ArrayList<>();
        while (next(resultSet)) {
            final int id = getInt(resultSet, SQLInstructions.ChannelsHotelDBHandler.ID);
            final String channelTicker = getString(resultSet, SQLInstructions.ChannelsHotelDBHandler.CHANNEL_TICKER);
            final String user = getString(resultSet, SQLInstructions.ChannelsHotelDBHandler.USER);
            final String password = getString(resultSet, SQLInstructions.ChannelsHotelDBHandler.PASSWORD);
            final float multiplier = getFloat(resultSet, SQLInstructions.ChannelsHotelDBHandler.MULTIPLIER);
            final int agentId = getInt(resultSet, SQLInstructions.ChannelsHotelDBHandler.AGENT_ID);
            final byte protocolPush = getByte(resultSet, SQLInstructions.ChannelsHotelDBHandler.PROTOCOL_PUSH);
            final String addressPush = getString(resultSet, SQLInstructions.ChannelsHotelDBHandler.ADDRESS_PUSH);
            final boolean active = getBoolean(resultSet, SQLInstructions.ChannelsHotelDBHandler.ACTIVE);
            items.add(new Channel(id, channelTicker, user, password, multiplier, agentId, protocolPush,
                    addressPush, active));
        }
        channels.addAll(items);
        return items;
    }

    private ChannelMappingAbstract getIntegrationChannelInterfaceFromResultSet(ResultSet resultSet,
                                                                               String channelTicker,
                                                                               String inventoryTicker) throws DBAccessException {
        ChannelMappingAbstract ret = null;

        if (resultSet != null
                && channelTicker != null
                && inventoryTicker != null) {
            if (BookingMapping.isSameTicketChannel(channelTicker)) {
                HashMap<String, String> codes = new HashMap<>();
                while (next(resultSet)) {
                    final String key = getString(resultSet, SQLInstructions.ChannelsHotelDBHandler.KEY);
                    final String value = getString(resultSet, SQLInstructions.ChannelsHotelDBHandler.VALUE);
                    codes.put(key, value);
                }
                //Create de new BookingMapping element.
                ret = new BookingMapping(inventoryTicker, codes);
                //Add the element to the channelsHandler.
                channelsHandler.add(ret);
            }
        }
        return ret;
    }

    /**
     * Return the codes representation of the inventoryTicker on the specific
     * channel (channelTicker).
     *
     * @param inventoryTicker the inventoryTicker who want get the codes.
     * @param channelTicker   the channelTicker who want get the codes.
     * @return the codes list who represent the hotel on the channel.
     * @throws DBAccessException
     */
    public ChannelMappingAbstract getCodesFromInventoryTicker(String channelTicker, String inventoryTicker) throws DBAccessException {
        if (channelsHandler.getItems() == null || channelsHandler.getItems().isEmpty()) {
            //Update the channelHandler
            updateChannelHandler(channelTicker);
        }
        ChannelMappingAbstract codes = channelsHandler.getCodesFromInventoryTicker(channelTicker, inventoryTicker);
        if (codes == null) {
            ChannelMappingAbstract item = getChannelMapping(SQLInstructions.ChannelsHotelDBHandler.SELECT_CODES_FROM_INVENTORY_TICKER,
                    channelTicker, inventoryTicker, Arrays.asList(channelTicker, inventoryTicker));
            return item;
        } else {
            return codes;
        }
    }

    private String findInChannelHandler(String channelTicker, List<String> codes) {
        String inventoryTicker = null;
        if (channelTicker != null && codes != null) {
            if (BookingMapping.isSameTicketChannel(channelTicker) && codes.size() == 2) {
                Map<String, String> toCompare = BookingMapping.genHashToCompare(codes.get(0), codes.get(1));
                inventoryTicker = channelsHandler.getInventoryTickerFromCodes(channelTicker, toCompare);
            }
        }
        return inventoryTicker;
    }

    /**
     * Update all entries of specific channel. If channel ticker passed is
     * <code>null</code> does nothing.
     *
     * @param channelTicker The channel ticker is updated.
     */
    private void updateChannelHandler(String channelTicker) throws DBAccessException {
        if (channelTicker != null) {
            if (channelTicker.equals(Constants.CHANNEL_TICKER)) {
                final String sqlCommand = SQLInstructions.ChannelsHotelDBHandler.SELECT_CHANNEL_MAPPING;
                PreparedStatement statement = prepareStatement(sqlCommand, Arrays.asList(channelTicker));
                ResultSet resultSet = execute(statement);
                //Stores all codes and inventoryTicker from resultSet.
                Map<String, Map<String, String>> items = new HashMap<>();
                while (next(resultSet)) {
                    final String inventoryTicker = getString(resultSet, SQLInstructions.ChannelsHotelDBHandler.CHANNEL_TICKER);
                    final String codeKey = getString(resultSet, SQLInstructions.ChannelsHotelDBHandler.KEY);
                    final String codeValue = getString(resultSet, SQLInstructions.ChannelsHotelDBHandler.VALUE);
                    if (!items.keySet().contains(inventoryTicker)) {
                        items.put(inventoryTicker, BookingMapping.genHashToCompare());
                    }
                    //Check value type.
                    final String key = codeKey.equals(Constants.KEY_RATE_PLAN)
                            ? Constants.KEY_RATE_PLAN
                            : codeKey.equals(Constants.KEY_ROOM_STAY)
                            ? Constants.KEY_ROOM_STAY
                            : null;
                    if (key != null) {
                        items.get(inventoryTicker).put(key, codeValue);
                    }
                }
                for (Map.Entry<String, Map<String, String>> item : items.entrySet()) {
                    final BookingMapping mapping = new BookingMapping(item.getKey(), item.getValue());
                    channelsHandler.update(mapping);
                }
                DAOUtil.close(statement, resultSet);
            }
        }
    }

    /**
     * Find the inventoryTicker represented in the channelTicker given with the
     * codes given.
     *
     * @param channelTicker the channel ticker.
     * @param codes         the codes given to search.
     * @return <code>inventoyTicker</code> if channelTicker and codes are * *
     * not <code>null</code> and found the inventoryTicker represented in the
     * given channel ticker with the given codes, otherwise <code>null<code>.
     */
    public String getInventoryTickerFromCodes(String channelTicker, List<String> codes) throws DBAccessException, BookingException {
        String inventoryTicker = null;
        if (channelTicker != null && codes != null) {
            //Find if the mapping is charge in the channelHandler already.
            inventoryTicker = findInChannelHandler(channelTicker, codes);
            //If not is charged.
            if (inventoryTicker == null) {
                //Update the channelHandler
                updateChannelHandler(channelTicker);
                //Find again.
                inventoryTicker = findInChannelHandler(channelTicker, codes);
            }
        } else {
            //TODO TBE Colocar mensaje
            throw new BookingException("Invalid channelTicker or channelCodes");
        }
        return inventoryTicker;
    }

    /**
     * Return Channel Object from a specific channelTicker.
     *
     * @param channelTicker
     * @return Channel object
     * @throws DBAccessException
     */
    public Channel getChannelByChannelTicker(String channelTicker) throws DBAccessException {
        for (Channel item : channels) {
            if (item.equalsChannelHotelTicker(channelTicker)) {
                return item;
            }
        }
        final String where = SQLInstructions.ChannelsHotelDBHandler.getSelectChannels("WHERE channel_ticker=?");
        List<Channel> items = getChannels(where, Arrays.asList(channelTicker));
        if (items == null || items.isEmpty()) {
            return null;
        } else {
            channels.addAll(items);
            return items.get(0);
        }
    }
}
