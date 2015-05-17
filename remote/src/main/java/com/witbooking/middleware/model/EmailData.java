package com.witbooking.middleware.model;

import com.witbooking.middleware.integration.mandrill.model.Event;
import com.witbooking.middleware.integration.mandrill.model.Message;
import org.apache.log4j.Logger;

/**
 * Created by mongoose on 1/21/15.
 */

public class EmailData {

    private static final Logger logger = Logger.getLogger(EmailData.class);
    /**
     *This is the ID of the reservation associated with the email
     */
    private String reservationID;
    /**
     *The hotel ticker that corresponds to the reservation
     */
    private String hotelTicker;
    /**
     *The uniqueID of the email from the third party email service (e.g. Mandrill)
     */
    private String emailID;
    /**
     *Message Type see class @MessageType
     */
    private Message.MessageType messageType;
    /**
     *Boolean to indicate if the email data has been added to its related reservation in the corresponding Reservation DB Data
     */
    private Event.EventType lastEmailStatus;


    public String getReservationID() {
        return reservationID;
    }

    public void setReservationID(String reservationID) {
        this.reservationID = reservationID;
    }

    public String getHotelTicker() {
        return hotelTicker;
    }

    public void setHotelTicker(String hotelTicker) {
        this.hotelTicker = hotelTicker;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public Message.MessageType getMessageType() {
        return messageType;
    }

    public void setMessageType(Message.MessageType messageType) {
        this.messageType = messageType;
    }

    public Event.EventType getLastEmailStatus() {
        return lastEmailStatus;
    }

    public void setLastEmailStatus(Event.EventType lastEmailStatus) {
        this.lastEmailStatus = lastEmailStatus;
    }
}
