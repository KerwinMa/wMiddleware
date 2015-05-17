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
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.HotelConfigurationDBHandler;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.exceptions.MailingException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.RemoteServiceException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.integration.EntryQueue;
import com.witbooking.middleware.model.Customer;
import com.witbooking.middleware.model.Poll;
import com.witbooking.middleware.model.Reservation;
import com.witbooking.middleware.model.Smtp;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.EmailsUtils;

import javax.ejb.Stateless;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.*;

/**
 *
 */
@Stateless
public class MailingBean implements MailingBeanLocal {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(MailingBean.class);
    //TODO:This is used to prevent the access to the database to search the code of language every time (v6)
    //Remove in v7, and save in the reservation the 3 digits locale for the language
    private static Map<String, String> languages = null;

    private static final String FROM_EMAIL_ADDRESS_PROPERTY = "emailRemitenteSobreescrito_client";
    private static final String FROM_EMAIL_NAME_PROPERTY = "emailRemitenteSobreescrito_nombre";

    public void sendMailPreOrPostStay(String hotelTicker, int daysBetween, boolean postStay) throws MailingException {
        //If is a PostStay Mail, need to calculate the days before today (negative days)
        String mailType;
        if (postStay) {
            daysBetween = daysBetween * (-1);
            mailType = "PostStayMails";
        } else {
            mailType = "PreStayMails";
        }
        logger.debug("Sending " + mailType + ": " + hotelTicker + ", days: " + daysBetween);
        fillLanguagesMap();
        DBConnection dbConnection = null;
        try {
            // Get the credentials
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            //Make the database connection
            dbConnection = new DBConnection(dbCredential);
            HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
            // Get Today
            final Date dateToAsk = new Date();
            //Get Date with daysBefore today
            DateUtil.incrementDays(dateToAsk, daysBetween);
            final ReservationDBHandler resDB = new ReservationDBHandler(dbConnection);
            String content = "";
            String subject = "";
            String fromEmail = "noreply@witbooking.com";
            String fromName = null;
            try {
                //Obtain the email sender values
                Properties senderProperties = hotelConfigurationDBHandler.getHotelProperties(
                        Arrays.asList(FROM_EMAIL_ADDRESS_PROPERTY, FROM_EMAIL_NAME_PROPERTY));
                //TODO: The email used to be "noreply@witbooking.com", this is if we want to send the PreStay from a specific email address
//                fromEmail = senderProperties.getProperty(FROM_EMAIL_ADDRESS_PROPERTY);
//                if (fromEmail == null || fromEmail.isEmpty()) {
//                    fromEmail = "noreply@witbooking.com";
//                    logger.error("Property '" + FROM_EMAIL_ADDRESS_PROPERTY + "' is null in hotel: '" + hotelTicker + "'.");
//                }
                fromName = senderProperties.getProperty(FROM_EMAIL_NAME_PROPERTY);
                if (fromName == null || fromName.isEmpty()) {
                    fromName = "Sistema de Reservas WitBooker";
                    logger.error("Property '" + FROM_EMAIL_NAME_PROPERTY + "' is null in hotel: '" + hotelTicker + "'.");
                }
            } catch (Exception e) {
                logger.error(e);
            }
            List<Reservation> reservations = null;
            //Get all the reservations, By CheckOut or CheckIn
            if (postStay) {
                reservations = resDB.getReservationsBetweenCheckOutDates(dateToAsk, dateToAsk);
            } else {
                reservations = resDB.getReservationsBetweenCheckInDates(dateToAsk, dateToAsk);
            }
            List<String> emailsTags = new ArrayList<>(2);
            emailsTags.add(mailType);
            int mailsSent = 0;
            // Get the poll from database
            Poll poll = null;
            for (final Reservation item : reservations) {
                if (poll == null || !poll.getLanguage().equalsIgnoreCase(languages.get(item.getLanguage()))) {
                    poll = hotelConfigurationDBHandler.getPoll(languages.get(item.getLanguage()), postStay);
                }
                if (poll != null && item.getStatus() != Reservation.ReservationStatus.CANCEL) {
                    //Set Subject
                    subject = poll.getTitle();
                    content = poll.getContent();
                    //Add the Poll link if this is a PostStay Mail
                    if (postStay) {
                        final String url = MiddlewareProperties.URL_WITBOOKER_V6 + "select/" +
                                hotelTicker + "/" + item.getLanguage() +
                                "/reservationsv6/responderencuesta/" +
                                item.getReservationId();
                        //TODO: ask if we can use google API Shortener
                        //url = TinyURLUtils.shorter(url);
                        content = content.replaceAll("\\[link\\]", " " + url + " ");
                    }
                    //Set recipient mail
                    final Customer cus = item.getCustomer();
                    // Send the message
                    sendMessageByExternalSystem(subject, content, cus.getEmail(), cus.getCompleteName(), fromEmail, fromName, emailsTags);
                    mailsSent++;
                }
            }
            logger.debug((postStay ? "PostStay" : "PreStay") + " Mails Sent by '" + hotelTicker + "': " + mailsSent);
        } catch (DBAccessException | RemoteServiceException e) {
            logger.error(e);
            throw new MailingException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    public void sendMail(EntryQueue entryQueue, final String response) throws MailingException {
        sendMail(entryQueue, null, response);
    }

    public void sendMail(EntryQueue entryQueue, final Exception ex, final String response) throws MailingException {
        Transport transport = null;
        try {
            Smtp smtp = new Smtp();
            //Create the mail session
            Session mailSession;
            mailSession = Session.getInstance(smtp.toProperties());
            //Create the connection
            transport = mailSession.getTransport(smtp.getKindConnection());
            transport.connect(smtp.getHost(), smtp.getUser(), smtp.getPassword());
            String content, subject, computerName = MiddlewareProperties.URL_WITBOOKER_V6, computerNickName = "";
            subject = "[WB-API] Error in EntryQueue " + entryQueue.getChannelTicker() + " for hotel: " + entryQueue
                    .getHotelTicker();
            try {
                computerName = InetAddress.getLocalHost().getHostName();
                computerNickName = (MiddlewareProperties.PROD_SERVER_NAME != null && MiddlewareProperties.PROD_SERVER_NAME.equals(computerName))
                        ? "Production"
                        : (MiddlewareProperties.TEST_SERVER_NAME != null && MiddlewareProperties.TEST_SERVER_NAME.equals(computerName))
                        ? "Test"
                        : (MiddlewareProperties.LUKE_SERVER_NAME != null && MiddlewareProperties.LUKE_SERVER_NAME.equals(computerName))
                        ? "Luke"
                        : "";
            } catch (Exception e) {
                logger.error(e);
            }
            subject += " Server: " + ((!computerNickName.isEmpty()) ? computerNickName : computerName) + ".";
            content = "There was a problem in the server "
                    + ((computerNickName.isEmpty()) ? "<b>" + computerName + "</b>" : "<b>" + computerNickName + "</b> (" + computerName + ")") +
                    " with the next<b> EntryQueue:</b> <br/><br/>";
            content = content + entryQueue.prettyPrintHtml();
            content = content + " <br/><br/> Problem: <br/><br/>";
            if (ex == null) {
                content = content + "&emsp; 'It was reached the limit of executions for this EntryQueue.' ";
            } else {
                content = content + " &emsp; " + ex;
            }
            content += "<br/><br/> The <b>response</b> received was: ";
            if (response == null) {
                content += "null";
            } else {
                content += "<br/><br/>'" + response + "'";
            }
            logger.debug("Sending email to: " + MiddlewareProperties.NOTIFICATIONS_EMAIL_RECIPIENTS);


            InternetAddress fromAddress = null;
            try {
                fromAddress = new InternetAddress(MiddlewareProperties.NOTIFICATIONS_EMAIL_ADDRESS, "WB-API Notifications");
            } catch (UnsupportedEncodingException e) {
            }
            ///
            if (MiddlewareProperties.NOTIFICATIONS_EMAIL_RECIPIENTS != null && !MiddlewareProperties.NOTIFICATIONS_EMAIL_RECIPIENTS.isEmpty()) {
                MimeMessage msg = new MimeMessage(mailSession);
                msg.setSubject(subject, "utf-8");
                InternetAddress[] addressTo = new InternetAddress[MiddlewareProperties.NOTIFICATIONS_EMAIL_RECIPIENTS.size()];
                for (int i = 0; i < MiddlewareProperties.NOTIFICATIONS_EMAIL_RECIPIENTS.size(); i++) {
                    addressTo[i] = new InternetAddress(MiddlewareProperties.NOTIFICATIONS_EMAIL_RECIPIENTS.get(i));
                }
                //Set recipient mail
                if (fromAddress != null) {
                    msg.setFrom(fromAddress);
                }
                msg.setRecipients(Message.RecipientType.BCC, addressTo);
                //Set body text
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(content, "text/html; charset=utf-8");
                // Multipart message
                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart);
                msg.setContent(multipart);
                // Send the message
                try {
                    transport.sendMessage(msg, msg.getAllRecipients());
                } catch (MessagingException e) {
                    logger.error(e);
                    throw new MailingException(e);
                }
            } else {
                logger.debug("Trying to send a mail to a null or empty list of recipients. This is the content of the message:\n '" + content + "'");
            }
            logger.debug("Email sent.");
        } catch (Exception e) {
            logger.error(e);
            throw new MailingException(e);
        } finally {
            try {
                if (transport != null && transport.isConnected()) {
                    //Close the email connection
                    transport.close();
                }
            } catch (MessagingException e) {
                logger.error(e);
            }

        }
    }

    private void sendMessageByExternalSystem(String subject, String content, String toAddress, String toName,
                                             String fromEmail, String fromName, List<String> tags) throws RemoteServiceException {
        if (toAddress != null && !toAddress.isEmpty()) {
            EmailsUtils.sendEmail(fromEmail, fromName, subject, content, toAddress, toName, tags);
        } else {
            logger.debug("Trying to send a mail to a null or empty list of recipients. This is the content of the message:\n '" + content + "'");
        }
    }

    private DBCredentials getDBCredentials(String hotelTicker) throws MailingException {
        try {
            return DBProperties.getDBCustomerByTicker(hotelTicker);
        } catch (MiddlewareException ex) {
            logger.error("Error getting the DBCredential for the Hotel '" + hotelTicker + "': " + ex.getMessage());
            throw new MailingException(ex);
        }
    }

    private void fillLanguagesMap() {
        if (languages == null) {
            languages = new HashMap<String, String>();
            languages.put("es", "spa");
            languages.put("ca", "cat");
            languages.put("en", "eng");
            languages.put("de", "deu");
            languages.put("it", "ita");
            languages.put("fr", "fre");
            languages.put("pt", "por");
            languages.put("ru", "rus");
            languages.put("nl", "dut");
            languages.put("ar", "ara");
            languages.put("zh", "chi");
        }
    }
}
