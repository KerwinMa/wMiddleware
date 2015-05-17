package com.witbooking.middleware.model;

import com.witbooking.middleware.integration.mandrill.model.Event;
import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by mongoose on 1/21/15.
 */

public class EmailEvent {

    public class MandrillEvent{
        public String ts;
        public String event;
        public String _id;
    }

    private static final Logger logger = Logger.getLogger(EmailEvent.class);

    /**
     *Unique auto incremental id
     */
    private int id;
    /**
     *Event Type given by third party email service see class @EventType
     */
    private Event.EventType eventType;
    /**
     *Additional Data related to the event
     */
    private String eventData;
    /**
     *Number of times this event has occurred for this particular email
     */
    private int eventCounter=0;
    /**
     *Event Timestamp
     */
    private Date timestamp;
    /**
     *Boolean to indicate if the email data has been added to its related reservation in the corresponding Reservation DB Data
     */
    private boolean addedToReservation;

    /**
     *ID of the related emailID
     */
    private String emailID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Event.EventType getEventType() {
        return eventType;
    }

    public void setEventType(Event.EventType eventType) {
        this.eventType = eventType;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }

    public int getEventCounter() {
        return eventCounter;
    }

    public void setEventCounter(int eventCounter) {
        this.eventCounter = eventCounter;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public void setTimestamp(String timestamp) {
        long timestampHolder=0L;
        try {
            timestampHolder=Long.parseLong(timestamp);
            this.timestamp=new Date(timestampHolder*1000);
        }catch (NumberFormatException ex){
            logger.error("Invalid Timestamp "+timestamp);
            this.timestamp=new Date();
        }
    }

    public boolean isAddedToReservation() {
        return addedToReservation;
    }

    public void setAddedToReservation(boolean addedToReservation) {
        this.addedToReservation = addedToReservation;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }
}


