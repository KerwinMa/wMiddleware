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
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.integration.mandrill.model.Event;
import com.witbooking.middleware.integration.mandrill.model.Message;
import com.witbooking.middleware.model.EmailData;
import com.witbooking.middleware.model.EmailEvent;
import com.witbooking.middleware.resources.DBProperties;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Insert description here
 *
 * @author mongoose
 * @version 1.0
 * @date 21-ene-2015
 */
public class EmailDataDBHandler extends DBHandler {


    private static final Logger logger = Logger.getLogger(EmailDataDBHandler.class);

    public EmailDataDBHandler() throws ExternalFileException, DBAccessException, NonexistentValueException {
        this.setDbConnection(new DBConnection(DBProperties.getDBSupportByTicker(SQLInstructions.WitMetaDataDBHandler.DB_WITMETADATA_TICKER)));
    }

    public EmailDataDBHandler(DBConnection dbConnection) {
        super(dbConnection);
    }

    public EmailData getEmailData(String emailID) throws DBAccessException, NonexistentValueException, SQLException {

        String query=SQLInstructions.WitMetaDataDBHandler.GET_EMAIL_DATA;
        List<String> values = new ArrayList<String>();
        values.add(emailID);

        PreparedStatement statement = prepareStatement(query, values);

        ResultSet resultSet = execute(statement);
        if(resultSet==null || !resultSet.isBeforeFirst()){
            logger.debug("There are no email data for emailID "+emailID);
            return null;
        }

        EmailData emailData=new EmailData();
        while (next(resultSet)) {
            emailData.setReservationID(getString(resultSet, 1));
            emailData.setHotelTicker(getString(resultSet, 2));
            emailData.setEmailID(getString(resultSet, 3));
            emailData.setMessageType(Message.MessageType.getFromValue(getString(resultSet, 4)));
            emailData.setLastEmailStatus(Event.EventType.getFromValue(getString(resultSet, 5)));
        }
        DAOUtil.close(statement, resultSet);
        return emailData;
    }

    public int saveEmailData(EmailData emailData) throws DBAccessException {
        String sqlCommand;
        PreparedStatement statement;
        sqlCommand = SQLInstructions.WitMetaDataDBHandler.INSERT_EMAIL_DATA;

        ArrayList<Object> values = new ArrayList<Object>();
        values.add(emailData.getReservationID());
        values.add(emailData.getHotelTicker());
        values.add(emailData.getEmailID());
        values.add(emailData.getMessageType().getValue());
        values.add(emailData.getLastEmailStatus().getValue());


        statement = prepareStatement(sqlCommand, values);
        int totalInserted = executeUpdate(statement);
        DAOUtil.close(statement);
        return totalInserted;
    }

    public int saveEmailEvent(EmailEvent emailEvent) throws DBAccessException {
        String sqlCommand;
        PreparedStatement statement;
        sqlCommand = SQLInstructions.WitMetaDataDBHandler.INSERT_EMAIL_EVENT;

        ArrayList<Object> values = new ArrayList<Object>();

        values.add(emailEvent.getEventType().getValue());
        values.add(emailEvent.getEventData());
        values.add(emailEvent.getEventCounter());
        values.add(emailEvent.getTimestamp());
        values.add(emailEvent.isAddedToReservation());
        values.add(emailEvent.getEmailID());

        statement = prepareStatement(sqlCommand, values);
        int totalInserted = executeUpdate(statement);
        DAOUtil.close(statement);
        return totalInserted;
    }

    public int updateEmailDataStatus(EmailData emailData) throws DBAccessException {
        String sqlCommand;
        PreparedStatement statement;
        sqlCommand = SQLInstructions.WitMetaDataDBHandler.UPDATE_EMAIL_DATA_STATUS;

        ArrayList<Object> values = new ArrayList<Object>();
        values.add(emailData.getLastEmailStatus().getValue());
        values.add(emailData.getEmailID());

        statement = prepareStatement(sqlCommand, values);
        int totalInserted = executeUpdate(statement);
        DAOUtil.close(statement);
        return totalInserted;
    }

}