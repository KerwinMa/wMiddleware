/*
 *   EmailsUtils.java
 * 
 * Copyright(c) 2014 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.utils;

import com.microtripit.mandrillapp.lutung.MandrillApi;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage;
import com.microtripit.mandrillapp.lutung.view.MandrillMessage.Recipient;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.witbooking.middleware.exceptions.MailingException;
import com.witbooking.middleware.exceptions.RemoteServiceException;
import com.witbooking.middleware.model.Smtp;
import com.witbooking.middleware.resources.MiddlewareProperties;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 18/12/14
 */
public class EmailsUtils {

    public static String STATUS_EMAIL_CONFIRMED = "sent";

    private static final Logger logger = Logger.getLogger(EmailsUtils.class);


    public static String sendEmail(String fromAddress, String fromName, String subject, String contentHTML,
                                   String toAddress, String toName, List<String> tags) throws RemoteServiceException {

        List<String> status = sendEmail(fromAddress, fromName, subject, contentHTML,
                Arrays.asList(toAddress), toName, tags);
        if (status.isEmpty())
            return "";
        else
            return status.get(0);
    }


    /**
     * Sending Emails throw Mandrill Services using the Luntung library (https://github.com/rschreijer/lutung)
     * <p/>
     * Mandrill API Documentation: (https://mandrillapp.com/api/docs/)
     *
     * @param fromAddress
     * @param fromName
     * @param subject
     * @param contentHTML
     * @param toAddress
     * @param toName
     * @return
     * @throws RemoteServiceException
     */
    public static List<String> sendEmail(String fromAddress, String fromName, String subject, String contentHTML,
                                         List<String> toAddress, String toName, List<String> tags)
            throws RemoteServiceException {
        List<String> status = new ArrayList<>();
        try {
            MandrillApi mandrillApi = new MandrillApi(MiddlewareProperties.MANDRILL_API_KEY);
            // create your message
            MandrillMessage message = new MandrillMessage();
            message.setSubject(subject);
            message.setHtml(contentHTML);
            message.setAutoText(true);
            message.setFromEmail(fromAddress);
            message.setFromName(fromName);
            // add recipients
            List<Recipient> recipients = new ArrayList<>();
            for (String address : toAddress) {
                Recipient recipient = new Recipient();
                recipient.setEmail(address);
                recipient.setName(toName);
                recipients.add(recipient);
            }
            message.setTo(recipients);
            message.setPreserveRecipients(true);
            if (tags != null && !tags.isEmpty())
                message.setTags(tags);
            MandrillMessageStatus[] messageStatusReports;
            messageStatusReports = mandrillApi.messages().send(message, true);
            for (MandrillMessageStatus messageStatusReport : messageStatusReports) {
                String statusEmail = messageStatusReport.getStatus().trim();
                if (!STATUS_EMAIL_CONFIRMED.equalsIgnoreCase(statusEmail.trim())) {
                    logger.error("Email Status Fail - "
                            + " id:" + messageStatusReport.getId()
                            + " fromEmail:" + fromAddress
                            + " toEmail:" + messageStatusReport.getEmail()
                            + " status:" + statusEmail
                            + " subject: " + subject
                            + " rejectReason:" + messageStatusReport.getRejectReason());
                }
                if (messageStatusReport.getRejectReason() != null && !messageStatusReport.getRejectReason().isEmpty())
                    statusEmail += " rejectReason: " + messageStatusReport.getRejectReason();
                status.add(statusEmail);
            }
        } catch (Exception ex) {
            logger.error("MandrillApiError: " + ex);
            throw new RemoteServiceException(ex);
        }
        return status;
    }

    public static List<String> sendEmailToAdmins(String subject, String contentHTML, List<String> tags)
            throws RemoteServiceException {
        List<String> status = new ArrayList<>();
        for (String mailAdmin : MiddlewareProperties.NOTIFICATIONS_EMAIL_RECIPIENTS) {
            String mailResponse = null;
            try {
                mailResponse = EmailsUtils.sendEmail("admin@witbooking.com", "WitBookingAPI",
                        subject, contentHTML, mailAdmin, "WitBooker Admin", tags);
            } catch (RemoteServiceException e) {
                try {
                    sendEmailByJava(subject,
                            contentHTML + "<br/><br/><b>ALERT!<b/> ERROR SENDING THIS EMAIL FROM MANDRILL. SENT FROM JAVA MAIL!<br/>",
                            Arrays.asList(mailAdmin));
                } catch (MailingException ex) {
                    logger.error("Error Sending Emails from Java to WitBookerAdmin: email:'" + mailAdmin + "' Error: " + ex);
                }
            }
            if (!mailResponse.equalsIgnoreCase(EmailsUtils.STATUS_EMAIL_CONFIRMED)) {
                logger.error("Error in the Mailing sender to WitBooker Admin: email:'" + mailAdmin + "' ");
                try {
                    sendEmailByJava(subject,
                            contentHTML + "<br/><br/><b>ALERT!<b/> ERROR SENDING THIS EMAIL FROM MANDRILL. SENT FROM JAVA MAIL!<br/>",
                            Arrays.asList(mailAdmin));
                } catch (MailingException e) {
                    logger.error("Error Sending Emails from Java to WitBookerAdmin: email:'" + mailAdmin + "' Error: " + e);
                }
            }
            status.add(mailResponse);
        }
        return status;
    }

    public static List<String> sendEmailToAdmins(String subject, String contentHTML, List<String> tags, Exception ex) {
        String computerName = MiddlewareProperties.URL_WITBOOKER_V6;
        String computerNickName = "";
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
        try {
            String body = "There was a problem in the server ";
            if (!computerNickName.isEmpty()) {
                subject += " Server: " + computerNickName;
                body += "<b>" + computerNickName + "</b> (" + computerName + ").";
            } else {
                subject += " Server: " + computerName;
                body += "<b>" + computerName + "</b>.";
            }
            body += "<br/><br/>" + contentHTML + " <br/><br/>  Exception: " + ex;
            for (StackTraceElement stackTraceElement : ex.getStackTrace()) {
                body = body + " <br/>             " + stackTraceElement;
            }
            return EmailsUtils.sendEmailToAdmins(subject, body, tags);
        } catch (Exception e) {
            logger.error("Error sending email to Admin for Errors:'" + "' Error: " + e);
            return null;
        }
    }


    public static void sendEmailByJava(String subject, String content, List<String> address) throws MailingException {
        Transport transport = null;
        try {
            Smtp smtp = new Smtp();
            //Create the mail session
            Session mailSession;
            mailSession = Session.getInstance(smtp.toProperties());
            //Create the connection
            transport = mailSession.getTransport(smtp.getKindConnection());
            transport.connect(smtp.getHost(), smtp.getUser(), smtp.getPassword());
            InternetAddress fromAddress = null;
            try {
                fromAddress = new InternetAddress(MiddlewareProperties.NOTIFICATIONS_EMAIL_ADDRESS, "WB-API Notifications");
            } catch (UnsupportedEncodingException e) {
            }
            if (address != null && !address.isEmpty()) {
                MimeMessage msg = new MimeMessage(mailSession);
                msg.setSubject(subject, "utf-8");
                InternetAddress[] addressTo = new InternetAddress[address.size()];
                for (int i = 0; i < address.size(); i++) {
                    addressTo[i] = new InternetAddress(address.get(i));
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
}
