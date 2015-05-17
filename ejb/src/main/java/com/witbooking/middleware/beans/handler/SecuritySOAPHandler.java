/*
 *   java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans.handler;

import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRQ;
import com.witbooking.middleware.integration.rategain.ConstantsRateGain;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.XMLUtils;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 30/09/13
 */

public class SecuritySOAPHandler
        implements SOAPHandler<SOAPMessageContext> {
    private static final Logger logger = Logger.getLogger(SecuritySOAPHandler.class);

    public static final String AUTHN_PREFIX = "wsse";
    public static final String ADD_PREFIX = "add";
    public static final String AUTHN_LNAME = "Security";
    public static final String AUTHN_URI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd";
    public static final String PASSWORD_URI = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText";
    public static final String AUTHN_STAUTS = "authnStatus";
    public static final String OTA_NAMESPACE = "http://www.opentravel.org/OTA/2003/05";
    public static final String ADD_NS = "http://schemas.xmlsoap.org/ws/2004/08/addressing";

    private static String LOGIN = "login";
    private static String PWD = "passwd";

    @Override
    public void close(MessageContext context) {
//        logger.debug("SecuritySOAPHandler::close");
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
//        logger.debug("SecuritySOAPHandler::handleFault");
        return true;
    }

    @Override
    public Set<QName> getHeaders() {
//        logger.debug("SecuritySOAPHandler::getHeaders");
        final QName securityHeader = new QName(AUTHN_URI, AUTHN_LNAME, AUTHN_PREFIX);
        HashSet<QName> headers = new HashSet<QName>();
        headers.add(securityHeader);
        return headers;
//        return null;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext soapMessageContext) {
//        logger.debug("SecuritySOAPHandler::handleMessage");
        try {
            Boolean isResponse = (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
            // inbound message from client
            if (!isResponse) {
                logger.debug("SOAP message incoming...");
                SOAPMessage soapMsg = soapMessageContext.getMessage();
                SOAPHeader header = soapMsg.getSOAPHeader();
                if (header == null) {
                    logger.debug("No headers found in the input SOAP request.");
                } else {
                    soapMessageContext.put(AUTHN_STAUTS, Boolean.TRUE);
                    String login = "";
                    String pass = "";
                    Iterator headerChildElements = header.getChildElements(new QName(AUTHN_URI, AUTHN_LNAME));
                    // iterate through child elements
                    while (headerChildElements.hasNext()) {
                        SOAPElement security = (SOAPElement) headerChildElements.next();
                        Iterator usernameTokenIter = security.getChildElements(new QName(AUTHN_URI, "UsernameToken"));
                        while (usernameTokenIter.hasNext()) {
                            SOAPElement child = (SOAPElement) usernameTokenIter.next();
                            // loop through security child elements
                            Iterator usernameIter = child.getChildElements(new QName(AUTHN_URI, "Username"));
                            Iterator passwordIter = child.getChildElements(new QName(AUTHN_URI, "Password"));
                            while (usernameIter.hasNext()) {
                                SOAPElement next = (SOAPElement) usernameIter.next();
                                login = next.getValue();
                            }
                            while (passwordIter.hasNext()) {
                                SOAPElement next = (SOAPElement) passwordIter.next();
                                pass = next.getValue();
                            }
                        }
                    }
//                    logger.debug("Username: " + login + " Password: " + pass);
                    soapMessageContext.put(LOGIN, login);
                    soapMessageContext.put(PWD, pass);
                }
                if (soapMsg.getSOAPBody() != null && soapMsg.getSOAPBody().getChildNodes() != null) {
                    final String soapBody =
                            XMLUtils.parseDocumentToString(soapMsg.getSOAPBody());
                    logger.debug("Request SOAP Body Message = " + soapBody);
                }
            } else {
                SOAPMessage soapMsg = soapMessageContext.getMessage();
                if (soapMsg.getSOAPBody() != null && soapMsg.getSOAPBody().getChildNodes() != null) {
                    final String soapBody =
                            XMLUtils.parseDocumentToString(soapMsg.getSOAPBody());
                    logger.debug("Response SOAP Body Message = " + soapBody);
                }
                logger.debug("SOAP message departing...");
            }
        } catch (SOAPException ex) {
            logger.error(ex.getMessage());
        }
        soapMessageContext.setScope(AUTHN_STAUTS, MessageContext.Scope.APPLICATION);
        soapMessageContext.setScope(LOGIN, MessageContext.Scope.APPLICATION);
        soapMessageContext.setScope(PWD, MessageContext.Scope.APPLICATION);
        return true;
    }

    public static SOAPMessage createSOAPRequestBasicAuth(Object bodyContent,
                                                         String username, String password) throws SOAPException, JAXBException {
        MessageFactory messageFactory = MessageFactory.newInstance();
        SOAPMessage soapMessage = messageFactory.createMessage();
        SOAPPart soapPart = soapMessage.getSOAPPart();
        // SOAP Envelope
        SOAPEnvelope envelope = soapPart.getEnvelope();
        envelope.addNamespaceDeclaration("ota", OTA_NAMESPACE);
        QName qNameMustUnderstand = new QName("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand");
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        // WSSecurity <Security> header
        SOAPElement wsSecHeaderElm = soapFactory.createElement("Security", AUTHN_PREFIX, AUTHN_URI);

        SOAPElement userNameTokenElm = soapFactory.createElement("UsernameToken", AUTHN_PREFIX, AUTHN_URI);

        SOAPElement userNameElm = soapFactory.createElement("Username", AUTHN_PREFIX, AUTHN_URI);
        userNameElm.addTextNode(username);

        SOAPElement passwdElm = soapFactory.createElement("Password", AUTHN_PREFIX, AUTHN_URI);
        passwdElm.addTextNode(password);
        passwdElm.addAttribute(new QName("Type"), PASSWORD_URI);

        userNameTokenElm.addChildElement(userNameElm);
        userNameTokenElm.addChildElement(passwdElm);

        // add child elements to the root element
        wsSecHeaderElm.addChildElement(userNameTokenElm);
        wsSecHeaderElm.addAttribute(qNameMustUnderstand, "1");

        // Get SOAPHeader instance for SOAP envelope
        SOAPHeader soapHeader = envelope.getHeader();

        // add SOAP element for header to SOAP header object
        soapHeader.addChildElement(wsSecHeaderElm);

        // SOAP Body
        if (bodyContent != null) {
            SOAPBody soapBody = envelope.getBody();
            JAXBContext jc = JAXBContext.newInstance(bodyContent.getClass());
            Marshaller um = jc.createMarshaller();
            um.marshal(bodyContent, soapBody);
            logger.debug("Request SOAP Body Message = " +
                    XMLUtils.parseDocumentToString(soapMessage.getSOAPBody()));
        }
        soapMessage.saveChanges();

        /* Print the request message */
//        logger.debug("Request SOAP Message = " + XMLUtils.soapMessageToString(soapMessage));
        return soapMessage;
    }

    public static SOAPMessage createSOAPRequestRateGain(OTAHotelResNotifRQ otaHotelResNotifRQ) throws SOAPException,
            JAXBException {
        SOAPMessage soapMessage = createSOAPRequestBasicAuth(null,
                MiddlewareProperties.RATEGAIN_USER_ID,
                MiddlewareProperties.RATEGAIN_PASSWORD);
        QName qNameMustUnderstand = new QName("http://schemas.xmlsoap.org/soap/envelope/", "mustUnderstand");
        SOAPFactory soapFactory = SOAPFactory.newInstance();
        // SOAP Envelope
        SOAPEnvelope envelope = soapMessage.getSOAPPart().getEnvelope();
        // Get SOAPHeader instance for SOAP envelope
        SOAPHeader soapHeader = envelope.getHeader();
        // WSAction <Action> header
        SOAPElement wsActionHeaderElm = soapFactory.createElement(
                "Action",
                ADD_PREFIX,
                ADD_NS);
        wsActionHeaderElm.addTextNode(ConstantsRateGain.SERVICE_NAMESPACE);
        wsActionHeaderElm.addAttribute(qNameMustUnderstand, "1");

        // WSTo <To> header
        SOAPElement wToHeaderElm = soapFactory.createElement(
                "To",
                ADD_PREFIX,
                ADD_NS);
        wToHeaderElm.addTextNode(MiddlewareProperties.RATEGAIN_RES_URL);
        wToHeaderElm.addAttribute(qNameMustUnderstand, "1");
        soapHeader.addChildElement(wsActionHeaderElm);
        soapHeader.addChildElement(wToHeaderElm);
        // SOAP Body
        if (otaHotelResNotifRQ != null) {
            SOAPBody soapBody = envelope.getBody();
            JAXBContext jc = JAXBContext.newInstance(otaHotelResNotifRQ.getClass());
            Marshaller um = jc.createMarshaller();
            um.marshal(otaHotelResNotifRQ, soapBody);
            logger.debug("Request RateGain SOAP Body Message = " +
                    XMLUtils.parseDocumentToString(soapMessage.getSOAPBody()));
        }
        soapMessage.saveChanges();
//        logger.debug("Request RateGain SOAP Message = " + XMLUtils.soapMessageToString(soapMessage));
        return soapMessage;
    }
}