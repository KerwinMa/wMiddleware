/*
 *  InventoryDBHandler.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.db.handlers;

import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.sqlinstructions.SQLInstructions;
import com.witbooking.middleware.db.handlers.sqlinstructions.TableConnectionsQueue;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.integration.*;
import com.witbooking.middleware.integration.google.model.Bid;
import com.witbooking.middleware.model.TransferData;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 31-ene-2013
 */
public final class WitMetaDataDBHandler extends DBHandler {

    /**
     * Object used to report on the Server's Log using Apache log4j.
     */
    private static final Logger logger = Logger.getLogger(WitMetaDataDBHandler.class);
    public static final Boolean UNIQUE = true, NOT_UNIQUE = false;

    /**
     * Creates a new instance of
     * <code>HotelConfigurationDBHandler</code> without parameters.
     */
    public WitMetaDataDBHandler() throws ExternalFileException, DBAccessException, NonexistentValueException {
        this.setDbConnection(new DBConnection(DBProperties.getDBSupportByTicker(SQLInstructions.WitMetaDataDBHandler.DB_WITMETADATA_TICKER)));
    }

    public WitMetaDataDBHandler(DBConnection dbConnection) {
        super(dbConnection);
    }

    public int insertDeeplinkLog(String direccion, String url, int hotelId, Date checkIn, Date checkOut,
                                 int adults, String language, String currency, int roomType, String trackingId, String bucket) throws DBAccessException {
        List values = new ArrayList();
        values.add(direccion);
        values.add(url);
        values.add(hotelId);
        values.add(new java.sql.Date(checkIn.getTime()));
        values.add(new java.sql.Date(checkOut.getTime()));
        values.add(adults);
        values.add(language);
        values.add(currency);
        values.add(roomType);
        values.add(trackingId);
        values.add(bucket);
        PreparedStatement statement = null;
        try {
            statement = prepareStatement(
                    SQLInstructions.WitMetaDataDBHandler.INSERT_DEEP_LINK_LOG, values);
            return executeUpdate(statement);
        } finally {
            DAOUtil.close(statement);
        }
    }

    public List<String> getHotelWithGoogle() throws DBAccessException {
        return getHotelListProduction();
    }

    public List<String> getHotelListProduction() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> hotelList = new ArrayList<String>();
        try {
            statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.GET_HOTEL_LIST_ACTIVE_V6);
            resultSet = execute(statement);
            while (next(resultSet)) {
                hotelList.add(getString(resultSet, 1));
            }
//            logger.debug("hotelList: "+hotelList);
            return hotelList;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public Map<String, Integer> getHotelListActivePreOrPostStayMail(boolean postStay) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<String, Integer> hotelMap = new HashMap<String, Integer>();
        try {
            if (postStay) {
                statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.GET_HOTEL_LIST_POST_STAY_MAIL_ACTIVE);
            } else {
                statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.GET_HOTEL_LIST_PRE_STAY_MAIL_ACTIVE);
            }
            resultSet = execute(statement);
            while (next(resultSet)) {
                hotelMap.put(getString(resultSet, 1), getInt(resultSet, 2));
            }
            return hotelMap;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<String> getHotelListActiveTransfers() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> hotelList = new ArrayList<>();
        try {
            statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.GET_HOTEL_LIST_ACTIVE_TRANSFERS);
            resultSet = execute(statement);
            while (next(resultSet)) {
                hotelList.add(getString(resultSet, 1));
            }
//            logger.debug("hotelList: "+hotelList);
            return hotelList;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    /**
     * Returns the hotel ticker on our system for a channel and hotel tickers
     * given.
     *
     * @param channelTicker      The channel ticker. (eg: BOOKING)
     * @param channelHotelTicker the channel hotel ticker (eg:184565)
     * @return The hotel ticker on our system.
     * @throws DBAccessException
     */
    public String getHotelTickerFromChannelHotelTicker(String channelTicker, String channelHotelTicker) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(
                    SQLInstructions.WitMetaDataDBHandler.GET_HOTEL_TICKER_FROM_CHANNEL_HOTEL_TICKER,
                    Arrays.asList(new String[]{channelTicker, channelHotelTicker}));
            resultSet = execute(statement);
            return next(resultSet) ? getString(resultSet, SQLInstructions.WitMetaDataDBHandler.HOTEL_TICKER) : null;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }


    /**
     * Returns the channel hotel ticker on our system for a channel and a hotel
     * given.
     *
     * @param channelTicker      The channel ticker. (eg: BOOKING)
     * @param channelHotelTicker the channel hotel ticker (eg:184565)
     * @return The hotel ticker on our system.
     * @throws DBAccessException
     */
    public String getChannelHotelTickerFromHotelTicker(String channelTicker, String channelHotelTicker) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(
                    SQLInstructions.WitMetaDataDBHandler.GET_CHANNEL_HOTEL_TICKER_FROM_HOTEL_TICKER,
                    Arrays.asList(new String[]{channelTicker, channelHotelTicker}));
            resultSet = execute(statement);
            return next(resultSet) ? getString(resultSet, SQLInstructions.WitMetaDataDBHandler.HOTEL_TICKER) : null;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public int createOrReplaceTripAdvisorReviewId(String hotelTicker, String reservationId, String tripAdvisorRequestId)
            throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            List values = new ArrayList();
            values.add(hotelTicker);
            values.add(reservationId);
            values.add(tripAdvisorRequestId);
            values.add(tripAdvisorRequestId);
            statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.INSERT_OR_REPLACE_TRIP_ADVISOR_REVIEW_EXPRESS_ID, values);
            return executeUpdate(statement);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public String getTripAdvisorReviewId(String hotelTicker, String reservationId) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            List values = new ArrayList();
            values.add(hotelTicker);
            values.add(reservationId);
            statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.GET_TRIP_ADVISOR_REVIEW_EXPRESS_ID, values);
            resultSet = execute(statement);
            return next(resultSet) ? getString(resultSet, SQLInstructions.WitMetaDataDBHandler.TableTripAdvisorReviewExpress.TRIP_ADVISOR_ID) : null;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }


    public List<EntryQueue> getAllRequestPending() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<EntryQueue> entries = new ArrayList<EntryQueue>();
        try {
            List values = new ArrayList();
            values.add(MiddlewareProperties.INTEGRATION_MANAGER_MAX_RESEND);
            statement = prepareStatement(TableConnectionsQueue.GET_ALL_CONNECTIONS_REQUEST, values);
            logger.debug("execute pending");
            resultSet = execute(statement);
            logger.debug("executed");
            while (next(resultSet)) {
                final int connectionId = getInt(resultSet, TableConnectionsQueue.ID);
                final String hotelTicker = getString(resultSet, TableConnectionsQueue.HOTEL_TICKER);
                final Date creationDate = getDate(resultSet, TableConnectionsQueue.CREATION_DATE);
                final Date lastExecuteDate = getDate(resultSet, TableConnectionsQueue.LAST_EXECUTE_DATE);
                final int totalRequestExecuted = getInt(resultSet, TableConnectionsQueue.TOTAL_EXECUTED);
                final Boolean finished = getBoolean(resultSet, TableConnectionsQueue.FINISHED);
                final Date executeRequestedDate = getDate(resultSet, TableConnectionsQueue.EXECUTION_REQUESTED_DATE);
                final String channelTicker = getString(resultSet, TableConnectionsQueue.CHANNEL);
                final String status = getString(resultSet, TableConnectionsQueue.STATUS);
                final String type = getString(resultSet, TableConnectionsQueue.TYPE);
                //Getting items list
                List<EntryQueueItem> items = getEntryItemsByEntryQueueId(connectionId);
                entries.add(new EntryQueue(connectionId, totalRequestExecuted, channelTicker, hotelTicker, creationDate, lastExecuteDate, executeRequestedDate, finished, type, status, items));
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
        return entries;
    }

    public int insertNewConnection(final String hotelTicker, final ChannelTicker channelTicker,
                                   final ChannelConnectionType channelConnectionType, final Date executionRequestedDate,
                                   final List<EntryQueueItem> items)
            throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int rowId = -1;
        List values = new ArrayList();
        values.add(hotelTicker);
        values.add(channelTicker.getValue());
        values.add(channelConnectionType.getValue());
        values.add(executionRequestedDate);
        values.add(ChannelQueueStatus.PENDING.getValue());
        try {
            statement = prepareStatement(TableConnectionsQueue.INSERT_NEW_RESERVATION_INTO_CONNECTIONS_QUEUE, values, WITH_GENERATED_KEYS);
            rowId = executeUpdate(statement);
            if (rowId == -1) {
                throw new DBAccessException("EntryQueue id not added.");
            }
            resultSet = getGeneratedKeys(statement);
            next(resultSet);
            rowId = getInt(resultSet, 1);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
        if (items != null && !items.isEmpty()) {
            try {
                statement = prepareStatement(TableConnectionsQueue.INSERT_ITEM_ID);
                for (EntryQueueItem item : items) {
                    values = new ArrayList<>();
                    values.add(rowId);
                    values.add(item.getItemId());
                    values.add(item.getStart());
                    values.add(item.getEnd());
                    addBatch(statement, values);
                }
                executeBatch(statement);
            } finally {
                DAOUtil.close(statement);
            }
        }
        return rowId;
    }


    public EntryQueue getEntryQueueById(final Integer entryQueueId) throws DBAccessException {
        EntryQueue entryQueue = null;
        if (entryQueueId != null) {
            List values = new ArrayList();
            values.add(entryQueueId);
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            try {
                statement = prepareStatement(TableConnectionsQueue.GET_ENTRY_QUEUE_FROM_ID, values);
                resultSet = execute(statement);
                List<EntryQueue> entryQueues = getEntriesFromResultSet(resultSet);
                if (!entryQueues.isEmpty()) {
                    entryQueue = entryQueues.get(0);
                    entryQueue.setItems(getEntryItemsByEntryQueueId(entryQueueId));
                }
            } finally {
                DAOUtil.close(statement, resultSet);
            }
        }
        return entryQueue;
    }


    public List<EntryQueueItem> getEntryItemsByEntryQueueId(final Integer entryQueueId) throws DBAccessException {
        List<EntryQueueItem> items = null;
        if (entryQueueId != null) {
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            List values = new ArrayList(1);
            values.add(entryQueueId);
            try {
                statement = prepareStatement(TableConnectionsQueue.GET_ITEMS_FROM_CONNECTION_ID, values);
                resultSet = execute(statement);
                items = getEntryItemsFromResultSet(resultSet);
            } finally {
                DAOUtil.close(statement, resultSet);
            }
        }
        return items;
    }

    /**
     * Return those not finished entries how match with the types given.
     *
     * @param hotelTicker
     * @param channelTicker
     * @param types
     * @return
     * @throws DBAccessException
     */
    public Set<EntryQueue> getEntriesQueue(final String hotelTicker,
                                           final String itemId,
                                           final ChannelTicker channelTicker,
                                           final CommunicationFinished communicationFinished,
                                           final List<ChannelConnectionType> types)
            throws DBAccessException {
        List<EntryQueue> allEntriesQueue = new ArrayList<EntryQueue>();
        PreparedStatement statement = null, statement1 = null;
        ResultSet resultSet = null, resultSet1 = null;
        List values = new ArrayList();
        values.add(hotelTicker);
        values.add(channelTicker.getValue());
        values.add(communicationFinished.getValue());
        for (final ChannelConnectionType type : types) {
            try {
                values.add(type == null ? null : type.getValue());
                statement = prepareStatement(TableConnectionsQueue.GET_ENTRY, values);
                resultSet = execute(statement);
                allEntriesQueue = getEntriesFromResultSet(resultSet);
                for (final EntryQueue entryQueue : allEntriesQueue) {
                    //Getting items list
                    entryQueue.setItems(getEntryItemsByEntryQueueId(entryQueue.getId()));
                }
                values.remove(values.size() - 1);
            } finally {
                DAOUtil.close(statement, resultSet);
            }
        }
        final Set<EntryQueue> entriesQueue = new HashSet<EntryQueue>();
        //Verificamos si nos piden una EntryQueue que contenga un itemId en especifico.
        if (itemId != null) {
            for (final EntryQueue entryQueue : allEntriesQueue) {
                if (entryQueue.getItems() != null) {
                    for (final EntryQueueItem entryQueueItem : entryQueue.getItems()) {
                        if (itemId.equals(entryQueueItem.getItemId())) {
                            entriesQueue.add(entryQueue);
                            break;
                        }
                    }
                }
            }
        } else {
            entriesQueue.addAll(allEntriesQueue);
        }
        return entriesQueue;
    }

    private List<EntryQueueItem> getEntryItemsFromResultSet(final ResultSet resultSet)
            throws DBAccessException {
        List<EntryQueueItem> entryQueueItems = new ArrayList<EntryQueueItem>();
        while (next(resultSet)) {
            entryQueueItems.add(new EntryQueueItem(
                    getString(resultSet, TableConnectionsQueue.TableConnectionsQueueItems.ITEM_ID),
                    getDate(resultSet, TableConnectionsQueue.TableConnectionsQueueItems.START),
                    getDate(resultSet, TableConnectionsQueue.TableConnectionsQueueItems.END)));
        }
        return entryQueueItems;
    }

    private List<EntryQueue> getEntriesFromResultSet(final ResultSet resultSet)
            throws DBAccessException {
        List<EntryQueue> entriesQueue = new ArrayList<EntryQueue>();
        while (next(resultSet)) {
            entriesQueue.add(new EntryQueue(getInt(resultSet, TableConnectionsQueue.ID),
                    getInt(resultSet, TableConnectionsQueue.TOTAL_EXECUTED),
                    getString(resultSet, TableConnectionsQueue.CHANNEL),
                    getString(resultSet, TableConnectionsQueue.HOTEL_TICKER),
                    getDate(resultSet, TableConnectionsQueue.CREATION_DATE),
                    getDate(resultSet, TableConnectionsQueue.LAST_EXECUTE_DATE),
                    getDate(resultSet, TableConnectionsQueue.EXECUTION_REQUESTED_DATE),
                    getBoolean(resultSet, TableConnectionsQueue.FINISHED),
                    getString(resultSet, TableConnectionsQueue.TYPE),
                    getString(resultSet, TableConnectionsQueue.STATUS)));
        }
        return entriesQueue;
    }

    public Boolean checkExists(final String hotelTicker, final ChannelTicker channelTicker,
                               final List<ChannelConnectionType> types, final String reservationId)
            throws DBAccessException {
        return checkExists(hotelTicker, channelTicker, types, reservationId, UNIQUE);
    }

    public Boolean checkExists(final String hotelTicker, final ChannelTicker channelTicker,
                               final List<ChannelConnectionType> types, final String reservationId,
                               final boolean unique)
            throws DBAccessException {
        for (final ChannelConnectionType type : types) {
            if (getEntryId(hotelTicker, channelTicker, type, reservationId, unique) != null) {
                return true;
            }
        }
        return false;
    }

    public Integer getEntryId(final String hotelTicker, final ChannelTicker channelTicker,
                              final ChannelConnectionType type, final String reservationId, final boolean unique)
            throws DBAccessException {
        PreparedStatement statement = null, statementToQueueItems = null;
        ResultSet resultSet = null, resultSetFromQueueItems = null;
        List<String> values = new ArrayList<String>();
        values.add(hotelTicker);
        values.add(channelTicker.getValue());
        values.add(type == null ? null : type.getValue());
        try {
            statement = prepareStatement(TableConnectionsQueue.GET_ID_OF_ENTRY, values);
            resultSet = execute(statement);
            if (reservationId == null && next(resultSet)) {
                return getInt(resultSet, TableConnectionsQueue.ID);
            } else {
                while (next(resultSet)) {
                    final int entryQueueId = getInt(resultSet, TableConnectionsQueue.ID);
                    List<String> valuesToQueueItems = new ArrayList<String>();
                    valuesToQueueItems.add(entryQueueId + "");
                    valuesToQueueItems.add(reservationId);
                    try {
                        statementToQueueItems = prepareStatement(TableConnectionsQueue.CHECK_CONNECTIONS_QUEUE_ITEMS, valuesToQueueItems);
                        resultSetFromQueueItems = execute(statementToQueueItems);
                        next(resultSetFromQueueItems);
                        if (getInt(resultSetFromQueueItems, "EXIST") > 0) {
                            if (unique) {
                                //Check uniqueness.
                                PreparedStatement statementUniqueQueueItems = null;
                                ResultSet resultSetUniqueQueueItems = null;
                                try {
                                    statementUniqueQueueItems = prepareStatement(TableConnectionsQueue.CHECK_UNIQUE_CONNECTIONS_QUEUE_ITEMS, valuesToQueueItems);
                                    resultSetUniqueQueueItems = execute(statementUniqueQueueItems);
                                    next(resultSetUniqueQueueItems);
                                    if (getInt(resultSetUniqueQueueItems, "EXIST") == 0)
                                        return entryQueueId;
                                } finally {
                                    DAOUtil.close(statementUniqueQueueItems, resultSetUniqueQueueItems);
                                }
                            } else {
                                return entryQueueId;
                            }
                        }
                    } finally {
                        DAOUtil.close(statementToQueueItems, resultSetFromQueueItems);
                    }
                }
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
        return null;
    }

    public Integer getEntryIdFromItem(final String hotelTicker, final ChannelTicker channelTicker,
                                      final ChannelConnectionType type, final EntryQueueItem item)
            throws DBAccessException {
        if (item == null)
            return null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<String> valuesToQueueItems = new ArrayList<>();
        valuesToQueueItems.add(item.getItemId());
        valuesToQueueItems.add(DateUtil.calendarFormat(item.getStart()));
        valuesToQueueItems.add(DateUtil.calendarFormat(item.getEnd()));
        valuesToQueueItems.add(hotelTicker);
        valuesToQueueItems.add(type == null ? null : type.getValue());
        valuesToQueueItems.add(channelTicker.getValue());
        try {
            statement = prepareStatement(TableConnectionsQueue.GET_ENTRY_QUEUE_UNFINISHED_FROM_ITEM, valuesToQueueItems);
            resultSet = execute(statement);
            if (next(resultSet)) {
                return getInt(resultSet, 1);
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
        return null;
    }

    public List<Integer> changeStatus(final int entryQueueId, final ChannelQueueStatus status, final CommunicationFinished finished)
            throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        final boolean finishedPassed = finished != null;
        List values = new ArrayList();
        values.add(status.getValue());
        if (finishedPassed) values.add(finished.getValue());
        values.add(entryQueueId);
        List<Integer> valuesUpdated = new ArrayList<Integer>();
        try {
            statement = prepareStatement(finishedPassed ? TableConnectionsQueue.UPDATE_STATUS_WITH_FINISHED : TableConnectionsQueue.UPDATE_STATUS, values, WITH_GENERATED_KEYS);
            executeUpdate(statement);
            resultSet = getGeneratedKeys(statement);
            while (next(resultSet)) {
                valuesUpdated.add(getInt(resultSet, 1));
            }
            return valuesUpdated;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public Integer storeSingleConnection(final String hotelTicker, final ChannelTicker channelTicker,
                                         final ChannelConnectionType type, final ChannelQueueStatus status,
                                         final Iterable<EntryQueueItem> itemsId)
            throws DBAccessException {
        return storeSingleConnection(hotelTicker, channelTicker, type, status, itemsId, 1, true);
    }


    public Integer createNewEntry(final String hotelTicker, final ChannelTicker channelTicker,
                                  final ChannelConnectionType type, final ChannelQueueStatus status,
                                  final Iterable<EntryQueueItem> itemsId)
            throws DBAccessException {
        return storeSingleConnection(hotelTicker, channelTicker, type, status, itemsId, 0, false);
    }

    /**
     * Store a singleton connection.
     *
     * @param hotelTicker
     * @param channelTicker
     * @param type
     * @param status
     * @param itemsId
     * @return The id of table connections_details corresponding to the inserted item.
     * @throws DBAccessException
     */
    private Integer storeSingleConnection(final String hotelTicker, final ChannelTicker channelTicker,
                                          final ChannelConnectionType type, final ChannelQueueStatus status,
                                          final Iterable<EntryQueueItem> itemsId, int executed, final Boolean finished)
            throws DBAccessException {
        List values = new ArrayList();
        values.add(hotelTicker);
        values.add(channelTicker.getValue());
        values.add(type != null ? type.getValue() : null);
        values.add(executed);
        values.add(status == ChannelQueueStatus.SUCCESS || finished);
        values.add(status.getValue());
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        final DBAccessException dbAccessException = new DBAccessException("Error storing single connection.");
        try {
            statement = prepareStatement(TableConnectionsQueue.INSERT_SINGLE_CONNECTION_WITH_HOTEL_TICKER,
                    values, WITH_GENERATED_KEYS);
            final int numberOfRowInserted = executeUpdate(statement);
            if (numberOfRowInserted < 1) {
                throw dbAccessException;
            } else {
                resultSet = getGeneratedKeys(statement);
                //Check if itemsId isn't null.
                if (next(resultSet)) {
                    final int rowId = getInt(resultSet, 1);
                    PreparedStatement statement1 = null;
                    if (itemsId != null) {
                        values = new ArrayList<String>();
                        for (final EntryQueueItem itemId : itemsId) {
                            try {
                                values.add(rowId);
                                values.add(itemId.getItemId());
                                values.add(itemId.getStart());
                                values.add(itemId.getEnd());
                                statement1 = prepareStatement(TableConnectionsQueue.INSERT_ITEM_ID, values);
                                executeUpdate(statement1);
                                values.clear();
                            } finally {
                                DAOUtil.close(statement1);
                            }
                        }
                    }
                    ResultSet resultSet1 = null;
                    try {
                        values.clear();
                        values.add(rowId);
                        values.add(status.getValue());
                        statement1 = prepareStatement(TableConnectionsQueue.INSERT_CONNECTIONS_DETAILS, values, WITH_GENERATED_KEYS);
                        executeUpdate(statement1);
                        resultSet1 = getGeneratedKeys(statement1);
                        if (next(resultSet1)) {
                            return getInt(resultSet1, 1);
                        } else {
                            throw dbAccessException;
                        }
                    } finally {
                        DAOUtil.close(statement1, resultSet1);
                    }
                } else {
                    throw dbAccessException;
                }
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    /**
     * @param hotelTicker
     * @param entryQueueItem
     * @param channelTicker
     * @param type
     * @param status
     * @return The id of connectionsDetails created on this connection.
     * @throws DBAccessException
     */
    public Integer reportAConnection(final String hotelTicker, final EntryQueueItem entryQueueItem, final ChannelTicker channelTicker,
                                     final ChannelConnectionType type, final ChannelQueueStatus status)
            throws DBAccessException {
        Integer entryId = getEntryId(hotelTicker, channelTicker, type, entryQueueItem == null ? null : entryQueueItem.getItemId(), UNIQUE);
        //Check if exists.
        if (entryId != null) {
            return reportAnExistingConnection(status, entryId);
        } else {
            List<EntryQueueItem> newItems = null;
            if (entryQueueItem != null) {
                newItems = new ArrayList<>();
                newItems.add(entryQueueItem);
            }
            return createNewEntry(hotelTicker, channelTicker, type, status, newItems);
        }
    }

    /**
     * @param hotelTicker
     * @param entryQueueItems
     * @param channelTicker
     * @param type
     * @param status
     * @return The id of connectionsDetails created on this connection.
     * @throws DBAccessException
     */
    public Integer reportAConnectionWithItems(final String hotelTicker, final List<EntryQueueItem> entryQueueItems, final ChannelTicker channelTicker,
                                              final ChannelConnectionType type, final ChannelQueueStatus status)
            throws DBAccessException {
        Integer entryId = null;
        List<EntryQueueItem> newItems = new ArrayList<>();
        Set<Integer> ids = new HashSet<>();
        for (EntryQueueItem entryQueueItem : entryQueueItems) {
            entryId = getEntryIdFromItem(hotelTicker, channelTicker, type, entryQueueItem);
            //Check if exists.
            if (entryId != null) {
                ids.add(entryId.intValue());
            } else {
                newItems.add(entryQueueItem);
            }
        }
        for (Integer id : ids) {
            entryId = reportAnExistingConnection(status, id);
        }
        if (!newItems.isEmpty())
            entryId = createNewEntry(hotelTicker, channelTicker, type, status, newItems);
        return entryId;
    }

    public Boolean checkIfSendMail(final Integer entryQueueId) throws DBAccessException {
        List values = new ArrayList();
        Integer max_resend = MiddlewareProperties.INTEGRATION_MANAGER_MAX_RESEND;
        if (max_resend == null) {
            max_resend = 3;
        }
        values.add(max_resend);
        values.add(entryQueueId);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(TableConnectionsQueue.CHECK_SEND_MAIL, values);
            resultSet = execute(statement);
            return (next(resultSet) && getInt(resultSet, 1) < 1);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public Boolean checkIsChain(final String ticker) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List values = new ArrayList();
        try {
            values.add(ticker);
            statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.CHECK_IF_IS_CHAIN, values);
            resultSet = execute(statement);
            if (next(resultSet)) {
                return getBoolean(resultSet, SQLInstructions.WitMetaDataDBHandler.TableInstanciasAplicaciones.ATT_CHECK_AGREGADOR);
            } else {
                logger.error("hotelTicker: '" + ticker + "' not found in " + SQLInstructions.WitMetaDataDBHandler.TableInstanciasAplicaciones.TBL_NAME);
                return false;
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public Boolean checkIsDemo(final String ticker) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List values = new ArrayList();
        try {
            values.add(ticker);
            statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.CHECK_IF_IS_DEMO, values);
            resultSet = execute(statement);
            if (next(resultSet)) {
                return getBoolean(resultSet, SQLInstructions.WitMetaDataDBHandler.TableInstanciasAplicaciones.ATT_CHECK_DEMO);
            } else {
                logger.error("hotelTicker: '" + ticker + "' not found in " + SQLInstructions.WitMetaDataDBHandler.TableInstanciasAplicaciones.TBL_NAME);
                return false;
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public Boolean checkHasTransferData(final String ticker) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List values = new ArrayList();
        try {
            values.add(ticker);
            statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.CHECK_IF_HAS_TRANSFERS, values);
            resultSet = execute(statement);
            if (next(resultSet)) {
                return getBoolean(resultSet, SQLInstructions.WitMetaDataDBHandler.TableInstanciasAplicaciones.ATT_HAS_TRANSFERS);
            } else {
                logger.error("hotelTicker: '" + ticker + "' not found in " + SQLInstructions.WitMetaDataDBHandler.TableInstanciasAplicaciones.TBL_NAME);
                return false;
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<String> getHotelTickersFromChain(final String ticker, Set<String> chainTickerChecked)
            throws DBAccessException {
        final ArrayList<String> hotelTickers = new ArrayList<String>();
        //TODO Verificar si alguno de estos es alguna cadena, para recursivamente buscar los tickets de los hoteles de dicha cadena.
        if (chainTickerChecked == null)
            chainTickerChecked = new HashSet<String>();
        if (!chainTickerChecked.contains(ticker)) {
            chainTickerChecked.add(ticker);
            PreparedStatement statement = null;
            ResultSet resultSet = null;
            List values = new ArrayList();
            try {
                values.add(ticker);
                statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.GET_HOTELS_FROM_CHAIN, values);
                resultSet = execute(statement);
                while (next(resultSet)) {
                    final String hotelTicker = getString(resultSet, SQLInstructions.WitMetaDataDBHandler.TableChain.ATT_HOTEL);
                    hotelTickers.add(hotelTicker);
                    //Verifying if is a chain, to recursively search for related hotels.
                    if (checkIsChain(hotelTicker)) {
                        hotelTickers.addAll(getHotelTickersFromChain(hotelTicker, chainTickerChecked));
                    }
                }
            } finally {
                DAOUtil.close(statement, resultSet);
            }
        }
        return hotelTickers;
    }


    private Integer reportAnExistingConnection(final ChannelQueueStatus status, final int entryId)
            throws DBAccessException {
        if (status == null) {
            throw new DBAccessException(new NullPointerException("Status given is null."));
        }
        List values = new ArrayList();
        values.add(entryId);
        values.add(status.getValue());
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Integer newEntryConnectionsDetailsId = -1;
        try {
            statement = prepareStatement(TableConnectionsQueue.INSERT_CONNECTIONS_DETAILS, values, WITH_GENERATED_KEYS);
            newEntryConnectionsDetailsId = executeUpdate(statement);
            if (newEntryConnectionsDetailsId == -1) {
                throw new DBAccessException("EntryQueue id not added.");
            }
            resultSet = getGeneratedKeys(statement);
            next(resultSet);
            newEntryConnectionsDetailsId = getInt(resultSet, 1);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
        Boolean finished = ChannelQueueStatus.SUCCESS == status;
        values.clear();
        values.add(finished);
        values.add(status.getValue());
        values.add(entryId);
        try {
            statement = prepareStatement(TableConnectionsQueue.UPDATE_CONNECTIONS_QUEUE, values);
            int numberOfRowUpdated = executeUpdate(statement);
            if (numberOfRowUpdated < 1) {
                throw new DBAccessException("The EntryQueue  with id= " + entryId + " should be updated to finished = " +
                        finished + " and status= " + status.getValue() + " but when try to update get this number of row updated: " + numberOfRowUpdated);
            }
        } finally {
            DAOUtil.close(statement);
        }
        return newEntryConnectionsDetailsId;
    }

    /**
     * Check is with this new execution of an entryQueue, that entryQueue should change his status of finalization to true.
     *
     * @param channelTicker EntryQueue channel.
     * @param type          EntryQueue type.
     * @param entryId       EntryQueue id.
     * @return <code>true</code> if the total entry executed stored in DB plus one, is higher or equal to max_resend
     * store in DB for that channelTicker-Type (if exist) or higher or equal to
     * MiddlewareProperties#INTEGRATION_MANAGER_MAX_RESEND, otherwise <code>false</code>.
     * @throws DBAccessException
     */
    private Boolean isFinishedEntryQueue(final ChannelTicker channelTicker, final ChannelConnectionType type, final Integer entryId)
            throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List values = new ArrayList();
        values.add(channelTicker.getValue());
        values.add(type.getValue());
        final Integer max_resend = getMaxResend(channelTicker, type);
        try {
            statement = prepareStatement(TableConnectionsQueue.GET_TOTAL_OF_ENTRY, values);
            resultSet = execute(statement);
            if (next(resultSet)) {
                return getInt(resultSet, TableConnectionsQueue.TOTAL_EXECUTED) + 1 >= max_resend;
            } else {
                String msg = "EntryQueue with id " + entryId + " not found.";
                logger.error(msg);
                throw new DBAccessException(msg);
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    /**
     * Get the max_resend configured to Channel-Type given.
     *
     * @param channelTicker
     * @param type
     * @return max_resend stored in DB for that channelTicker-Type (if exist) or MiddlewareProperties#INTEGRATION_MANAGER_MAX_RESEND
     * @throws DBAccessException
     */
    private Integer getMaxResend(final ChannelTicker channelTicker, final ChannelConnectionType type)
            throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List values = new ArrayList();
        values.add(channelTicker.getValue());
        values.add(type.getValue());
        try {
            statement = prepareStatement(TableConnectionsQueue.GET_MAX_RESEND, values);
            resultSet = execute(statement);
            return (next(resultSet))
                    ? getInt(resultSet, TableConnectionsQueue.TableChannelsConnectionsType.MAX_RESEND)
                    : MiddlewareProperties.INTEGRATION_MANAGER_MAX_RESEND;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<Bid> getGoogleBid() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.GET_GOOGLE_BIDS);
            resultSet = execute(statement);
            return getGoogleBidFromResultSet(resultSet);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }


    public List<Bid> getGoogleBid(List<String> hotelTickers) throws DBAccessException {
        if (hotelTickers == null || hotelTickers.isEmpty()) return getGoogleBid();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sqlCommand =
                SQLInstructions.WitMetaDataDBHandler.GET_GOOGLE_BIDS + " WHERE " +
                        SQLInstructions.WitMetaDataDBHandler.GoogleBids.ATT_HOTEL_TICKER + " IN (";
        for (final String hotelTicker : hotelTickers) {
            sqlCommand += " '" + hotelTicker + "' , ";
        }
        sqlCommand = sqlCommand.substring(0, sqlCommand.length() - 3) + " );";
        try {
            statement = prepareStatement(sqlCommand);
            resultSet = execute(statement);
            return getGoogleBidFromResultSet(resultSet);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }


    public Bid getGoogleBid(final String hotelTicker) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List values = new ArrayList();
        values.add(hotelTicker);
        try {
            statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.GET_GOOGLE_BIDS_FOR_HOTEL, values);
            resultSet = execute(statement);
            List<Bid> googleBidFromResultSet = getGoogleBidFromResultSet(resultSet);
            return googleBidFromResultSet == null || googleBidFromResultSet.isEmpty()
                    ? null
                    : googleBidFromResultSet.get(0);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    private List<Bid> getGoogleBidFromResultSet(ResultSet resultSet) throws DBAccessException {
        List<Bid> bids = new ArrayList<Bid>();
        while (next(resultSet)) {
            bids.add(new Bid(
                    getString(resultSet, SQLInstructions.WitMetaDataDBHandler.GoogleBids.ATT_HOTEL_TICKER),
                    getBoolean(resultSet, SQLInstructions.WitMetaDataDBHandler.GoogleBids.ATT_ACTIVE),
                    getFloat(resultSet, SQLInstructions.WitMetaDataDBHandler.GoogleBids.ATT_BID)));
        }
        return bids;
    }

    public Map<String, Map<String, String>> getCountriesMap() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Map<String, Map<String, String>> countriesMap = new HashMap<>();
        try {
            statement = prepareStatement(SQLInstructions.WitMetaDataDBHandler.GET_COUNTRIES_MAP);
            resultSet = execute(statement);
            Map<String, String> es = new HashMap<>();
            Map<String, String> en = new HashMap<>();
            Map<String, String> fr = new HashMap<>();
            Map<String, String> it = new HashMap<>();
            Map<String, String> local = new HashMap<>();
            while (next(resultSet)) {
                String alpha2 = getString(resultSet, "alpha2");
                es.put(alpha2, getString(resultSet, "es"));
                en.put(alpha2, getString(resultSet, "en"));
                fr.put(alpha2, getString(resultSet, "fr"));
                it.put(alpha2, getString(resultSet, "it"));
                local.put(alpha2, getString(resultSet, "local"));
            }
            countriesMap.put("es", es);
            countriesMap.put("en", en);
            countriesMap.put("fr", fr);
            countriesMap.put("it", it);
            countriesMap.put("local", local);
            return countriesMap;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public TransferData getTransferData(final String hotelTicker, final String lang) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        TransferData transferData = null;
        List values = new ArrayList();
        values.add(lang != null && !lang.trim().isEmpty() ? lang.trim() : "");
        values.add(hotelTicker != null && !hotelTicker.isEmpty() ? hotelTicker : "");
        try {
            String query = SQLInstructions.WitMetaDataDBHandler.GET_TRANSFERS + " WHERE t.ticker=?;";
            statement = prepareStatement(query, values);
            resultSet = execute(statement);
            if (next(resultSet)) {
                transferData = new TransferData();
                transferData.setTicker(getString(resultSet, "ticker"));
                transferData.setMessage(getTranslation(resultSet, "message_trans", "message"));
                transferData.setReleaseMin(getInt(resultSet, "releaseMin"));
                transferData.setLockHours(getInt(resultSet, "lockHours"));
            }
//            logger.debug("transferData: "+transferData);
            return transferData;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<TransferData> getTransferDataList() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<TransferData> transferDataList = new ArrayList<>();
        try {
            String query = SQLInstructions.WitMetaDataDBHandler.GET_TRANSFERS + ";";
            statement = prepareStatement(query, Arrays.asList(""));
            resultSet = execute(statement);
            while (next(resultSet)) {
                TransferData transferData = new TransferData();
                transferData.setTicker(getString(resultSet, "ticker"));
                transferData.setMessage(getString(resultSet, "message"));
                transferData.setReleaseMin(getInt(resultSet, "releaseMin"));
                transferData.setLockHours(getInt(resultSet, "lockHours"));
                transferDataList.add(transferData);
            }
//            logger.debug("transferData: "+transferData);
            return transferDataList;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }


    public int insertTrackingQuery(Date checkInDate, Date checkOutDate, String hotelTicker, String channelId,
                                   String language, String trackingId, String clientIp) throws DBAccessException {
        String sqlCommand;
        PreparedStatement statement;
        sqlCommand = SQLInstructions.WitMetaDataDBHandler.INSERT_TRACKING_QUERY;
        ArrayList<Object> values = new ArrayList<>();
        values.add(new java.sql.Date(checkInDate.getTime()));
        values.add(new java.sql.Date(checkOutDate.getTime()));
        values.add(hotelTicker);
        values.add(channelId);
        values.add(language);
        values.add(trackingId);
        values.add(clientIp);
        statement = prepareStatement(sqlCommand, values);
        int totalInserted = executeUpdate(statement);
        DAOUtil.close(statement);
        return totalInserted;
    }

}