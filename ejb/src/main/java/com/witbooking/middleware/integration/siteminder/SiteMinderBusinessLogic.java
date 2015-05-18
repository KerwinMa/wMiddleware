package com.witbooking.middleware.integration.siteminder;

import com.witbooking.middleware.beans.IntegrationBeanLocal;
import com.witbooking.middleware.beans.SiteMinderBeanLocal;
import com.witbooking.middleware.beans.handler.SecuritySOAPHandler;
import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRQ;
import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRS;
import com.witbooking.middleware.integration.ota.model.SoapFault;
import com.witbooking.middleware.integration.ota.model.TransactionActionType;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.XMLUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.xml.bind.UnmarshalException;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

/**
 * SiteMinderBusinessLogic.java
 * User: jose
 * Date: 9/26/13
 * Time: 11:51 AM
 */
public abstract class SiteMinderBusinessLogic {

    private static final Logger logger = Logger.getLogger(SiteMinderBusinessLogic.class);
    @EJB
    protected SiteMinderBeanLocal siteMinderBeanLocal;
    @EJB
    protected IntegrationBeanLocal integrationBeanLocal;

    protected abstract String reportReservationLogic(String reservationId, String hotelTicker);

    protected String reportReservation(String reservationId, String hotelTicker) {
        return reportReservationLogic(reservationId, hotelTicker);
    }

    protected OTAHotelResNotifRS otaHotelResNotif(OTAHotelResNotifRQ otaHotelResNotifRQ, final String reservationId,
                                                  final String hotelTicker) {
        logger.debug("OTAHotelResNotif");

        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = null;
        OTAHotelResNotifRS otaHotelResNotifRS = null;
        String request = null;
        String soapBodyResponse = null;
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            logger.debug("Calling to SiteMinder...: " + MiddlewareProperties.SITEMINDER_RES_URL);
            final SOAPMessage soapMessage = SecuritySOAPHandler.createSOAPRequestBasicAuth(otaHotelResNotifRQ,
                    MiddlewareProperties.SITEMINDER_USER_ID,
                    MiddlewareProperties.SITEMINDER_PASSWORD);
            request = XMLUtils.parseDocumentToString(soapMessage.getSOAPBody());
            SOAPMessage soapResponse = soapConnection.call(
                    soapMessage,
                    MiddlewareProperties.SITEMINDER_RES_URL);
            logger.info("request SOAP Body Message = " + request);
            logger.debug("Success!");
            soapConnection.close();
            soapBodyResponse = XMLUtils.parseDocumentToString(soapResponse.getSOAPBody().getChildNodes().item(0));
            logger.info("Response SOAP Body Message = " + soapBodyResponse);
            try {
                otaHotelResNotifRS = (OTAHotelResNotifRS) XMLUtils.unmarshalFromString(soapBodyResponse, OTAHotelResNotifRS.class);
                //Apply the changes made for a cancellation in Siteminder (-1 in Availability)
                if (otaHotelResNotifRS != null
                        && otaHotelResNotifRS.getSuccess() != null
                        && otaHotelResNotifRQ.getResStatus() == TransactionActionType.CANCEL) {
                    siteMinderBeanLocal.cancelBehavior(hotelTicker, reservationId);
                }
            } catch (UnmarshalException ex) {
                logger.error(ex);
                logger.error("Request SOAP Body Message = " + request);
                logger.error("Response SOAP Body Message = " + soapBodyResponse);
                SoapFault soapFault = (SoapFault) XMLUtils.unmarshalFromString(soapBodyResponse, SoapFault.class);
                otaHotelResNotifRS = new OTAHotelResNotifRS(
                        com.witbooking.middleware.integration.ota.model.ErrorType.getSoapFault(soapFault));
            }
        } catch (SOAPException ex) {
            ex.printStackTrace();
            logger.error(ex);
            otaHotelResNotifRS = new OTAHotelResNotifRS(
                    com.witbooking.middleware.integration.ota.model.ErrorType.getUnableToProcess(ex.getMessage()));
        } catch (Exception ex) {
            ex.printStackTrace();
            logger.error(ex);
            otaHotelResNotifRS = new OTAHotelResNotifRS(
                    com.witbooking.middleware.integration.ota.model.ErrorType.getUnableToProcess(ex.getMessage()));
        }
        return otaHotelResNotifRS;
    }


}