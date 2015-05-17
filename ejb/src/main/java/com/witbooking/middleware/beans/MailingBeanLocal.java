/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.MailingException;
import com.witbooking.middleware.integration.EntryQueue;

import javax.ejb.Local;

/**
 * @author jose
 */
@Local
public interface MailingBeanLocal {

    public void sendMailPreOrPostStay(String hotelTicker, int daysBetween, boolean postStay) throws MailingException;

    public void sendMail(EntryQueue entryQueue, String response) throws MailingException;

    public void sendMail(EntryQueue entryQueue, Exception ex, String response) throws MailingException;
}
