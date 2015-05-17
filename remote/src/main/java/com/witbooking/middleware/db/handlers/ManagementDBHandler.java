/*
 *  InventoryDBHandler.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.db.handlers;

import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import org.apache.log4j.Logger;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 31-ene-2013
 */
public class ManagementDBHandler extends DBHandler {

    /**
     * Object used to report on the Server's Log using Apache log4j.
     */
    private static final Logger logger = Logger.getLogger(ManagementDBHandler.class);

    /**
     * Creates a new instance of
     * <code>HotelConfigurationDBHandler</code> without params.
     */
    public ManagementDBHandler() {
    }

    public ManagementDBHandler(DBConnection dbConnection) {
        super(dbConnection);
    }

    public ManagementDBHandler(DBCredentials dbCredentials) throws DBAccessException {
        super(new DBConnection(dbCredentials));
    }
}
