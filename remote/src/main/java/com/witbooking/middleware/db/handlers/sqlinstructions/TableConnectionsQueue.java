package com.witbooking.middleware.db.handlers.sqlinstructions;

import com.witbooking.middleware.integration.CommunicationFinished;

/**
 * TableConnectionsQueue.java
 * User: jose
 * Date: 11/4/13
 * Time: 3:17 PM
 */
public final class TableConnectionsQueue {

    public static final String TBL_NAME = "connections_queue";
    public static final String ID = "id";
    public static final String HOTEL_TICKER = "hotel_ticker";
    public static final String CREATION_DATE = "creation_date";
    public static final String LAST_EXECUTE_DATE = "last_execute_date";
    public static final String TOTAL_EXECUTED = "total_request_executed";
    public static final String FINISHED = "finished";
    public static final String EXECUTION_REQUESTED_DATE = "execution_requested_date";
    public static final String CHANNEL = "channel";
    public static final String TYPE = "type";
    public static final String STATUS = "status";


    public static class TableConnectionStatus {
        public static final String TBL_NAME = "connection_status";
        public static final String STATUS = "STATUS";

    }

    public static class TableConnectionsQueueItems {
        public static final String TBL_NAME = "connections_queue_items";
        public static final String ITEM_ID = "item_id";
        public static final String CONNECTION = "connection";
        public static final String START = "start";
        public static final String END = "end";
//        public static final String ID = "id";
    }

    public static class TableConnectionsDetails {
        public static final String TBL_NAME = "connections_details";
        public static final String ID = "id";
        public static final String CONNECTION = "connection";
        public static final String STATUS = "status";
        public static final String EXECUTE_DATE = "execute_date";
        public static final String COMMUNICATION_ID = "communication_id";
    }

    public static final class TableChannelsConnections {
        public static final String TBL_NAME = "channels_connections";
        public static final String TYPE = "type";
    }

    public static final class TableChannelsConnectionsType {
        public static final String TBL_NAME = "channels_connections_type";
        public static final String TYPE = "type";
        public static final String CHANNEL = "channel";
        public static final String MAX_RESEND = "max_resend";
    }


//    public static final String GET_ALL_CONNECTIONS_REQUEST = "SELECT * FROM " + TBL_NAME + " WHERE " + FINISHED +
//            " = false AND (" + EXECUTION_REQUESTED_DATE + " <= NOW() OR " + EXECUTION_REQUESTED_DATE + " IS NULL );";

    public static final String GET_ENTRY_QUEUE_FROM_ID = "SELECT * FROM " + TBL_NAME + " WHERE " + ID + "=? ;";
    public static final String GET_ITEMS_FROM_CONNECTION_ID = "SELECT * FROM " +
            TableConnectionsQueueItems.TBL_NAME + " WHERE " + TableConnectionsQueueItems.CONNECTION + " = ? ;";
    public static final String GET_ENTRY_QUEUE_UNFINISHED_FROM_ITEM = "SELECT * FROM " + TBL_NAME + " AS c, " +
            TableConnectionsQueueItems.TBL_NAME + " AS i WHERE i." + TableConnectionsQueueItems.ITEM_ID
            + "<=>? AND i." + TableConnectionsQueueItems.START + "<=>? AND i." + TableConnectionsQueueItems.END
            + "<=>? AND c." + FINISHED + "=" + CommunicationFinished.NOT_FINISHED.getValue() + " AND c.id=i.connection "
            + " AND c." + HOTEL_TICKER + "=? AND c." + TYPE + "=? AND c." + CHANNEL + "=? ;";

    //    public static final String INSERT_BOOKING_GET_RESERVES = "INSERT INTO " + TBL_NAME + "(" + HOTEL_TICKER + "," + CHANNEL + "," + TYPE + "," + STATUS + ") VALUES (?,?,?,?);";
    public static final String INSERT_NEW_RESERVATION_INTO_CONNECTIONS_QUEUE = "INSERT INTO " + TBL_NAME + "(" +
            HOTEL_TICKER + "," + CHANNEL + "," + TYPE + "," + EXECUTION_REQUESTED_DATE + "," + STATUS + ") VALUES (?,?,?,?,?);";

    public static final String INSERT_ITEM_ID = "INSERT INTO " + TableConnectionsQueueItems.TBL_NAME + "("
            + TableConnectionsQueueItems.CONNECTION + "," + TableConnectionsQueueItems.ITEM_ID + ","
            + TableConnectionsQueueItems.START + "," + TableConnectionsQueueItems.END
            + ") VALUES (?,?,?,?);";

    public static final String INSERT_CONNECTIONS_DETAILS = "INSERT INTO " + TableConnectionsDetails.TBL_NAME + "(" +
            TableConnectionsDetails.CONNECTION + "," + TableConnectionsDetails.STATUS + ")" +
            "VALUES (?,?);";

    private static final String CHECK_SHORTER_THAN_MAX_RESEND_FROM =
            " FROM " + TableChannelsConnectionsType.TBL_NAME + " as t INNER JOIN " + TBL_NAME + " as q ON "
                    + " t." + TableChannelsConnectionsType.TYPE + " = q." + TYPE + " AND "
                    + " t." + TableChannelsConnectionsType.CHANNEL + " = q." + CHANNEL + " ";

    private static final String CHECK_SHORTER_THAN_MAX_RESEND_SELECT =
            "( t." + TableChannelsConnectionsType.MAX_RESEND + " IS NULL OR q." + TOTAL_EXECUTED + " < t." + TableChannelsConnectionsType.MAX_RESEND +
                    ") AND ( t." + TableChannelsConnectionsType.MAX_RESEND + " IS NOT NULL OR " + TOTAL_EXECUTED + " < ? ) ";

    public static final String GET_ALL_CONNECTIONS_REQUEST = "SELECT * "
            + CHECK_SHORTER_THAN_MAX_RESEND_FROM
            + " WHERE q." + FINISHED + " = false "
            + " AND (q." + EXECUTION_REQUESTED_DATE + " <= NOW() OR q." + EXECUTION_REQUESTED_DATE + " IS NULL ) "
            + " AND " + CHECK_SHORTER_THAN_MAX_RESEND_SELECT + ";";

    public static final String CHECK_SEND_MAIL = "SELECT " + CHECK_SHORTER_THAN_MAX_RESEND_SELECT
            + CHECK_SHORTER_THAN_MAX_RESEND_FROM + " WHERE q." + ID + "=? ;";

    public static final String GET_MAX_RESEND = "SELECT " + TableChannelsConnectionsType.MAX_RESEND + " FROM " +
            TableChannelsConnectionsType.TBL_NAME + " WHERE " + TableChannelsConnectionsType.CHANNEL + "=? AND " + TableChannelsConnectionsType.TYPE + "=? ;";

    public static final String GET_TOTAL_OF_ENTRY = "SELECT " + TOTAL_EXECUTED + " FROM " + TBL_NAME + " WHERE " + ID + "=? ;";

    public static final String UPDATE_CONNECTIONS_QUEUE = "UPDATE " + TBL_NAME + " SET " + LAST_EXECUTE_DATE + "=NOW() , " +
            TOTAL_EXECUTED + "=" + TOTAL_EXECUTED + "+1 , " + FINISHED + "=? , " + STATUS + "=? WHERE " + ID + "=? ;";

    public static final String GET_ID_OF_ENTRY = "SELECT " + ID + " FROM " + TBL_NAME + " WHERE " +
            HOTEL_TICKER + "=? AND " + CHANNEL + "=? AND " + TYPE + "=? AND " + FINISHED + "=" + CommunicationFinished.NOT_FINISHED.getValue() + ";";
    public static final String GET_ENTRY = "SELECT * FROM " + TBL_NAME + " WHERE " +
            HOTEL_TICKER + "=? AND " + CHANNEL + "=? AND " + TYPE + "=? AND " + FINISHED + "=?;";

    public static final String CHECK_CONNECTIONS_QUEUE_ITEMS = "SELECT EXISTS(SELECT 1 FROM " +
            TableConnectionsQueueItems.TBL_NAME + " WHERE " + TableConnectionsQueueItems.CONNECTION +
            "=? AND " + TableConnectionsQueueItems.ITEM_ID + "=?) as EXIST;";
    public static final String CHECK_UNIQUE_CONNECTIONS_QUEUE_ITEMS = "SELECT EXISTS(SELECT 1 FROM " +
            TableConnectionsQueueItems.TBL_NAME + " WHERE " + TableConnectionsQueueItems.CONNECTION +
            "=? AND " + TableConnectionsQueueItems.ITEM_ID + "!= ?) as EXIST;";
    public static final String UPDATE_STATUS = "UPDATE " + TBL_NAME + " SET " + STATUS + "=? WHERE " + ID + "=? ;";
    public static final String UPDATE_STATUS_WITH_FINISHED = "UPDATE " + TBL_NAME + " SET " + STATUS + "=? , " + FINISHED + "=? WHERE " + ID + "=? ;";


    public static final String INSERT_SINGLE_CONNECTION_WITH_HOTEL_TICKER = "INSERT " + TBL_NAME +
            "(" + HOTEL_TICKER + "," + CHANNEL + "," + TYPE + "," + LAST_EXECUTE_DATE + "," + TOTAL_EXECUTED + "," + FINISHED + "," + STATUS + ") VALUES " +
            "(?,?,?,NOW(),?,?,?)";
//            "(?,?,?,NOW(),1," + CommunicationFinished.FINISHED.getValue() + ",?)";
}