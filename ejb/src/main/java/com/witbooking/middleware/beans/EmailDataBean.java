/*
 *  MailingBean.java
 *
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 *
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.EmailDataDBHandler;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.exceptions.*;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.integration.mandrill.model.*;
import com.witbooking.middleware.integration.mandrill.model.Message;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.resources.DBProperties;

import javax.ejb.Stateless;
import java.sql.SQLException;

/**
 *
 */
@Stateless
public class EmailDataBean implements EmailDataBeanLocal {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(EmailDataBean.class);

    /**
     * Persists new EmailData
     * This method is called for inserting new events for an emailID (most likely 'sent')
     * It retrieves the first event from storage , gets all required info and stores it , as well as
     * storing information directly in the associated reservation on persistent storage
     * @param emailID
     * @param eventType
     * @param eventData
     * @param timestamp
     * @return
     * @throws EmailDataException
     */
    @Override
    public int saveEmailEventData(String emailID, String eventType, String eventData, String timestamp,String quantity) throws EmailDataException {
        EmailDataDBHandler emailDataDBHandler = null;
        DBConnection dbConnection = null;
        try {
            emailDataDBHandler = new EmailDataDBHandler();
            EmailData emailData=null;
            Event.EventType newEventType=Event.EventType.getFromValue(eventType);
            emailData=emailDataDBHandler.getEmailData(emailID);
            EmailEvent emailEvent=new EmailEvent();
            /*
            * For reservation Mails we are sure the mails are bound to exist because the email mapping data is saved when the reservation occurs
            * and the email is sent, as Mandrill gives an email ID even for queued emails, if the ID is not in the DB is either one of these situations
            * 1. It is not a Reservation or tracked mail
            * 2. The id belongs to a mail which was sent before the registration of events was put in production use
            * */
            if(emailData==null){
                logger.debug("No Email Data found for emailID: " + emailID);
                return 0;
            }
            /*After we recover the existing mapping, */

            /*We check if the new Event overrides the last greatest event recorded for this email, if so, we update Reservation at the hotel's Reservation Table*/
            boolean updatedReservation=false;
            if( emailData.getLastEmailStatus().compareTo(newEventType)<0){
                emailData.setLastEmailStatus(newEventType);
                ReservationDBHandler reservationDBHandler;
                dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(emailData.getHotelTicker()));
                reservationDBHandler = new ReservationDBHandler(dbConnection);
                updatedReservation=reservationDBHandler.updateReservationEmailInfo(emailData)>0;
                emailDataDBHandler.updateEmailDataStatus(emailData);
            }

            /*We save the event to the Email Event Table */
            emailEvent.setEventType(newEventType);
            emailEvent.setEventData(eventData);
            emailEvent.setEventCounter(0);
            emailEvent.setTimestamp(timestamp);
            emailEvent.setEmailID(emailData.getEmailID());
            emailEvent.setAddedToReservation(updatedReservation);
            emailDataDBHandler.saveEmailEvent(emailEvent);

        } catch (ExternalFileException | NonexistentValueException | DBAccessException e) {
            logger.error("Error communicating with DB "+e.toString());
            throw new EmailDataException(e);
        } catch (SQLException e) {
            logger.error("Error executing query to get email data " + e.toString());
            throw new EmailDataException(e);
        } finally {
            DAOUtil.close(emailDataDBHandler.getDbConnection());
            DAOUtil.close(dbConnection);
        }

        return 0;
    }

    /**
     * Persists new EmailData
     * This method is called for inserting the first event for an emailID (most likely 'sent').
     * For adding new events this function has been overloaded.
     * @param reservationID
     * @param hotelTicker
     * @param emailID
     * @param messageType
     * @param eventType
     * @param eventData
     * @param timeStamp
     * @return
     * @throws EmailDataException
     */
    @Override
    public int saveEmailEventData(String reservationID, String hotelTicker, String emailID, String messageType, String eventType, String eventData, String timeStamp) throws EmailDataException {
        EmailData emailData=new EmailData();
        emailData.setReservationID(reservationID);
        emailData.setHotelTicker(hotelTicker);
        emailData.setEmailID(emailID);
        emailData.setMessageType(Message.MessageType.getFromValue(messageType));
/*
        emailData.setEventType(Event.EventType.getFromValue(eventType));
        emailData.setEventData(eventData);
        emailData.setTimestamp(timeStamp);
*/
        return 0;
    }

    @Override
    public EmailData getEmailEventData(String emailID, Event.EventType eventType) throws EmailDataException {
        return null;
    }

    @Override
    public EmailData getEmailEventData(String emailID) throws EmailDataException {
        return null;
    }

}
