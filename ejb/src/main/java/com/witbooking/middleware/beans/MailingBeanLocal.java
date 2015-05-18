/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.witbooking.middleware.beans;

import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.witbooking.middleware.exceptions.MailingException;
import com.witbooking.middleware.integration.EntryQueue;
import com.witbooking.middleware.model.EmailData;
import com.witbooking.middleware.model.Reservation;

import javax.ejb.Local;
import java.util.List;

/**
 * @author jose
 */
@Local
public interface MailingBeanLocal {

    public void sendMailPreOrPostStay(String hotelTicker, int daysBetween, boolean postStay) throws MailingException;

    public void sendMail(EntryQueue entryQueue, String response) throws MailingException;

    public void sendMail(EntryQueue entryQueue, Exception ex, String response) throws MailingException;

    public List<EmailData> saveReservationEmailData(Reservation reservation, String hotelTicker,
                                                    List<MandrillMessageStatus> messageStatusReports) throws MailingException;

    public List<MandrillMessageStatus> sendConfirmationEmail(String hotelTicker, Reservation reservation) throws
            MailingException;
}
