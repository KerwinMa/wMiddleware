/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.EmailDataException;
import com.witbooking.middleware.integration.mandrill.model.Event;
import com.witbooking.middleware.model.EmailData;

import javax.ejb.Local;

/**
 * @author mongoose
 */
@Local
public interface EmailDataBeanLocal {

    public int saveEmailEventData(String emailID, String eventType, String eventData, String timestamp, String quantity) throws EmailDataException;

    public int saveEmailEventData(String reservationID, String hotelTicker, String emailID, String messageType, String eventType, String eventData, String timestamp) throws EmailDataException;

    public EmailData getEmailEventData(String emailID, Event.EventType eventType) throws EmailDataException;

    public EmailData getEmailEventData(String emailID) throws EmailDataException;

}
