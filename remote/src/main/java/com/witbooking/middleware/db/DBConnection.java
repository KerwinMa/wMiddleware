/*
 *  DBConnection.java
 * 
 * Copyright(c) 2013 Witbooking.com.  All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.db;

import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.resources.DBProperties;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines a series of structures for handling the connection to a mySQL database
 * manager.
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 18-ene-2013
 * @since 1.0
 */
public final class DBConnection {

    /**
     * Object used to report on the Server's Log using Apache log4j.
     */
    private static final Logger logger = Logger.getLogger(DBConnection.class);
    /**
     * A {@code Connection} Object to connect with the database through plain SQL using JDBC.
     */
    private Connection connection = null;

    private DBCredentials dbCredentials;

    //Attribute to make a Profiler for the SQL Connection
    public boolean profilingMode = false;
    public Map<String, Map<String, Long>> queryCounter;
    //Static values for the current profiler
    private static Map<String, DBConnection> dbConnectionsProfiled;
    private static boolean MUST_PROFILE = false;

    /**
     * Creates a new instance of
     * <code>DBConnection</code> without params.
     */
    public DBConnection() {
    }

    public DBConnection(DBCredentials dbCredentials) throws DBAccessException {
        this.dbCredentials = dbCredentials;
        this.openConnection();
    }

    public DBConnection(String hotelTicker) throws NonexistentValueException, ExternalFileException, DBAccessException {
        this.dbCredentials = DBProperties.getDBCustomerByTicker(hotelTicker);
        this.openConnection();
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }


    public DBCredentials getDbCredentials() {
        return dbCredentials;
    }

    public void setDbCredentials(DBCredentials dbCredentials) {
        this.dbCredentials = dbCredentials;
    }

    /**
     * Opens a new connection to a jdbc database configured through the {@link Connection} class.
     *
     * @throws DBAccessException
     */
    private void openConnection()
            throws DBAccessException {
        String dbURL = "jdbc:mysql://" + dbCredentials.getHost()
                + ":" + dbCredentials.getPort()
                + "/" + dbCredentials.getNameDB();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(dbURL, dbCredentials.getUserDB(), dbCredentials.getPassDB());
        } catch (SQLException ex) {
            ex.printStackTrace();
            logger.error("[DBConnection] The database could not be accessed. URL: " + dbURL + " user: '" + dbCredentials.getUserDB() + "' pass: '" + dbCredentials.getPassDB() + "'");
            throw DBAccessException.dbConnectionError(ex, "[DBConnection] The database could not be accessed");
        } catch (ClassNotFoundException ex) {
            logger.error("[DBConnection] JDBC driver com.mysql.jdbc.Driver was not found");
            throw DBAccessException.dbConnectionError(ex, "[DBConnection] JDBC driver com.mysql.jdbc.Driver was not found");
        }
        //Setting the profiler values
        if (MUST_PROFILE) {
            profilingMode = true;
            queryCounter = new HashMap<String, Map<String, Long>>();
        }
    }

    /**
     * Closes an open database {@link Connection}, associated with the calling instance of the class.
     */
    public void closeConnection() throws DBAccessException {
        if (profilingMode && MUST_PROFILE) {
            if (dbConnectionsProfiled == null) {
            }
            String key = dbCredentials.getNameDB();
            if (dbConnectionsProfiled.containsKey(key)) {
                key = key + "_DUPLICATED_" + dbConnectionsProfiled.size();
            }
            dbConnectionsProfiled.put(key, this);
        }
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            logger.error("[DBConnection] JDBC Closing Connection Errors. " + ex.getMessage());
            throw DBAccessException.dbConnectionError(ex, "[DBConnection] JDBC Closing Connection Errors.");
        }
    }

    public static boolean MUST_PROFILE() {
        return MUST_PROFILE;
    }

    public static void setMUST_PROFILE(boolean MUST_PROFILE) {
        DBConnection.MUST_PROFILE = MUST_PROFILE;
        if (MUST_PROFILE) {
            dbConnectionsProfiled = new HashMap<String, DBConnection>();
        } else {
            dbConnectionsProfiled = null;
        }
    }

    public static Map<String, DBConnection> getDbConnectionsProfiled() {
        return dbConnectionsProfiled;
    }

    public String printMapQuery(){
        //TODO: imprimir en json el dbConnectionsProfiled
        return "";
    }
}
