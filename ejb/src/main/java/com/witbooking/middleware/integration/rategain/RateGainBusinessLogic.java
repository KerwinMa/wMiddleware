/*
 *  RateGainResource.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.rategain;

import com.witbooking.middleware.beans.IntegrationBeanLocal;
import com.witbooking.middleware.beans.RateGainBeanLocal;
import com.witbooking.middleware.beans.handler.SecuritySOAPHandler;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.exceptions.integration.rategain.RateGainException;
import com.witbooking.middleware.integration.ChannelConnectionType;
import com.witbooking.middleware.integration.ChannelQueueStatus;
import com.witbooking.middleware.integration.ChannelTicker;
import com.witbooking.middleware.integration.EntryQueueItem;
import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRQ;
import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRS;
import com.witbooking.middleware.integration.ota.model.SoapFault;
import com.witbooking.middleware.integration.rategain.model.*;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.XMLUtils;
import org.apache.log4j.Logger;

import javax.ejb.EJB;
import javax.xml.bind.JAXBException;
import javax.xml.bind.UnmarshalException;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.util.*;

/**
 * Web Service.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 21, 2013
 */
public abstract class RateGainBusinessLogic {

    private static final Logger logger = Logger.getLogger(RateGainBusinessLogic.class);
    @EJB
    protected RateGainBeanLocal rateGainBean;
    @EJB
    protected IntegrationBeanLocal integrationBeanLocal;

    private HotelPropertyListGetRS hotelPropertyListGet(HotelPropertyListGetRQ request) {
        logger.debug("getHotelPropertyListByGet");
        HotelPropertyListGetRS response;
        try {
            response = rateGainBean.getHotelPropertyList(request);
        } catch (RateGainException ex) {
            logger.error(ex);
            response = new HotelPropertyListGetRS(ErrorType.getErrorTypeUnknownCodeUndetermined());
        }
        return response;
    }

    private HotelProductListGetRS hotelProductListGet(HotelProductListGetRQ request) {
        HotelProductListGetRS response;
        try {
            logger.debug("HotelProductListGetRQ");
            response = rateGainBean.getHotelProductList(request);
        } catch (RateGainException ex) {
            logger.error(ex);
            response = new HotelProductListGetRS(ErrorType.getErrorTypeUnknownCodeUndetermined());
        }
        return response;
    }

    private HotelARIGetRS hotelARIGet(HotelARIGetRQ request) {
        HotelARIGetRS response;
        try {
            logger.debug("HotelARIGet");
            response = rateGainBean.getHotelARIGetRS(request);
            return response;
        } catch (RateGainException ex) {
            logger.error(ex);
            response = new HotelARIGetRS(ErrorType.getErrorTypeUnknownCodeUndetermined());
        }
        return response;

    }

    private HotelARIUpdateRS hotelARIUpdate(HotelARIUpdateRQ request) {
        HotelARIUpdateRS response;
        try {
            logger.debug("HotelARIUpdate");
            response = rateGainBean.getHotelARIUpdateRS(request);
        } catch (RateGainException ex) {
            logger.error(ex);
            response = new HotelARIUpdateRS(ErrorType.getErrorTypeUnknownCodeUndetermined());
        }
        return response;
    }

    protected abstract String reportReservation(String reservationId, String hotelTicker) throws IntegrationException;

    protected OTAHotelResNotifRS otaHotelResNotif(OTAHotelResNotifRQ otaHotelResNotifRQ) {
        logger.debug("OTAHotelResNotif");
//        try {
//            logger.debug("otaHotelResNotifRQ: ");
//            String rq=XMLUtils.marshalFromObject(otaHotelResNotifRQ, true);
//            logger.debug(rq);
//        } catch (JAXBException ex) {
//            logger.error(ex);
//        }

        // Create SOAP Connection
        SOAPConnectionFactory soapConnectionFactory = null;
        OTAHotelResNotifRS otaHotelResNotifRS = null;
        try {
            soapConnectionFactory = SOAPConnectionFactory.newInstance();
            SOAPConnection soapConnection = soapConnectionFactory.createConnection();

            // Send SOAP Message to SOAP Server
            logger.debug("Calling to RateGain...");
            SOAPMessage soapResponse = soapConnection.call(
                    SecuritySOAPHandler.createSOAPRequestRateGain(otaHotelResNotifRQ),
                    MiddlewareProperties.RATEGAIN_RES_URL);
            logger.debug("Connection Success!");
            soapConnection.close();
            if (soapResponse.getSOAPBody() != null && soapResponse.getSOAPBody().getChildNodes() != null) {
                final String soapBody =
                        XMLUtils.parseDocumentToString(soapResponse.getSOAPBody().getChildNodes().item(0));
                logger.debug("Response SOAP Body Message = " + soapBody);
                try {
                    otaHotelResNotifRS = (OTAHotelResNotifRS) XMLUtils.unmarshalFromString(soapBody, OTAHotelResNotifRS.class);
                } catch (UnmarshalException ex) {
                    logger.error(ex);
                    SoapFault soapFault = (SoapFault) XMLUtils.unmarshalFromString(soapBody, SoapFault.class);
                    otaHotelResNotifRS = new OTAHotelResNotifRS(
                            com.witbooking.middleware.integration.ota.model.ErrorType.getSoapFault(soapFault));
                }
            }
        } catch (SOAPException ex) {
            logger.error(ex);
            otaHotelResNotifRS = new OTAHotelResNotifRS(
                    com.witbooking.middleware.integration.ota.model.ErrorType.getUnableToProcess(ex.getMessage()));
        } catch (Exception ex) {
            logger.error(ex);
            otaHotelResNotifRS = new OTAHotelResNotifRS(
                    com.witbooking.middleware.integration.ota.model.ErrorType.getUnableToProcess(ex.getMessage()));
        }
        return otaHotelResNotifRS;
    }

    protected String handlingRequest(final String requestBody) {
        logger.debug("handlingRequest: " + requestBody);
        final String handlingRequest = requestBody;
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        String returnObjectString = null;
        String hotelCode = null;
        ChannelConnectionType type = null;
        Map<String, EntryQueueItem> queueItemsMap=new HashMap<String, EntryQueueItem>();
        try {
            final String stringPackage = "com.witbooking.middleware.integration.rategain.model";
            final Object requestObject = XMLUtils.unmarshalFromString(handlingRequest, stringPackage);
            Object returnObject;
            logger.debug("UnmarshalClass: " + requestObject.getClass() + "");
            if (requestObject instanceof HotelPropertyListGetRQ) {
                returnObject = hotelPropertyListGet((HotelPropertyListGetRQ) requestObject);
                type = ChannelConnectionType.OFFER_HOTEL_NAME;
                status = ((HotelPropertyListGetRS) returnObject).getSuccess() == null
                        ? ChannelQueueStatus.FAIL
                        : ChannelQueueStatus.SUCCESS;
            } else if (requestObject instanceof HotelProductListGetRQ) {
                returnObject = hotelProductListGet((HotelProductListGetRQ) requestObject);
                type = ChannelConnectionType.OFFER_INVENTORY;
                status = ((HotelProductListGetRS) returnObject).getSuccess() == null
                        ? ChannelQueueStatus.FAIL
                        : ChannelQueueStatus.SUCCESS;
                if (status == ChannelQueueStatus.SUCCESS) {
                    hotelCode = ((HotelProductListGetRS) returnObject).getHotelProducts().getHotelCode();
                }
            } else if (requestObject instanceof HotelARIGetRQ) {
                final HotelARIGetRQ hotelARIGet = (HotelARIGetRQ) requestObject;
                if (hotelARIGet != null)
                    hotelCode = hotelARIGet.getHotelARIGetRequestHotelTicker();
                returnObject = hotelARIGet(hotelARIGet);
                type = ChannelConnectionType.OFFER_ARI;
                status = ((HotelARIGetRS) returnObject).getSuccess() == null
                        ? ChannelQueueStatus.FAIL
                        : ChannelQueueStatus.SUCCESS;
                if (status == ChannelQueueStatus.SUCCESS) {
                    hotelCode = hotelARIGet.getHotelARIGetRequestHotelTicker();
                    for (final HotelARIGetRQ.HotelARIGetRequests.HotelARIGetRequest hotelARIGetRequest : hotelARIGet.getHotelARIGetRequests().getHotelARIGetRequest()) {
                        final Date startDate = hotelARIGetRequest.getApplicationControl().getStart();
                        final Date endDate = hotelARIGetRequest.getApplicationControl().getEnd();
                        final String itemId = hotelARIGetRequest.getProductReference().getInvTypeCode();
                        //Adding the EntryQueueItems, to the map
                        EntryQueueItem queueItem = queueItemsMap.get(itemId);
                        if (queueItem == null) {
                            queueItemsMap.put(itemId, new EntryQueueItem(itemId, startDate, endDate));
                        } else {
                            try {
                                if (queueItem.getStart().after(startDate)) {
                                    queueItem.setStart(startDate);
                                }
                                if (queueItem.getEnd().before(endDate)) {
                                    queueItem.setEnd(endDate);
                                }
                            } catch (Exception e) {
                                logger.error(e);
                                queueItemsMap.put(itemId, new EntryQueueItem(itemId, startDate, endDate));
                            }
                        }
                    }
                }
            } else if (requestObject instanceof HotelARIUpdateRQ) {
                final HotelARIUpdateRQ ariUpdateRQ = (HotelARIUpdateRQ) requestObject;
                if (ariUpdateRQ != null)
                    hotelCode = ariUpdateRQ.getHotelARIUpdateRequestHotelTicker();
                returnObject = hotelARIUpdate(ariUpdateRQ);
                type = ChannelConnectionType.OFFER_UPDATE_ARI;
                status = ((HotelARIUpdateRS) returnObject).getSuccess() == null
                        ? ChannelQueueStatus.FAIL
                        : ChannelQueueStatus.SUCCESS;
                if (status == ChannelQueueStatus.SUCCESS) {
                    for (final HotelARIDataType hotelARIDataType : ariUpdateRQ.getHotelARIUpdateRequestHotelARIData()) {
                        final Date startDate = hotelARIDataType.getApplicationControlStartDate();
                        final Date endDate = hotelARIDataType.getApplicationControlEndDate();
                        final String itemId = hotelARIDataType.getProductReferenceInvTypeCode();
                        //Adding the EntryQueueItems, to the map
                        EntryQueueItem queueItem = queueItemsMap.get(itemId);
                        if (queueItem == null) {
                            queueItemsMap.put(itemId, new EntryQueueItem(itemId, startDate, endDate));
                        } else {
                            try {
                                if (queueItem.getStart().after(startDate)) {
                                    queueItem.setStart(startDate);
                                }
                                if (queueItem.getEnd().before(endDate)) {
                                    queueItem.setEnd(endDate);
                                }
                            } catch (Exception e) {
                                logger.error(e);
                                queueItemsMap.put(itemId, new EntryQueueItem(itemId, startDate, endDate));
                            }
                        }
                    }
                }
//            } else if (requestObject instanceof OTAHotelResNotifRQ) {
//                returnObject = otaHotelResNotif((OTAHotelResNotifRQ) requestObject);
//                type = ChannelConnectionType.OFFER_INVENTORY;
            } else {
                logger.debug("No service found for class: " + requestObject.getClass());
                ErrorType errorType = ErrorType.getErrorTypeUnknownCodeUndetermined();
                errorType.setValue("Request Unknown");
                returnObjectString = XMLUtils.marshalFromObject(errorType, false);
                return returnObjectString;
            }
            logger.debug("Return Class: " + returnObject.getClass() + "");
            returnObjectString = XMLUtils.marshalFromObject(returnObject, false);
            return returnObjectString;
        } catch (JAXBException ex) {
            logger.error(ex);
            try {
                //MSG_ERROR_PARSING
                ErrorType errorType = ErrorType.getErrorTypeUnknownCodeUndetermined();
                errorType.setValue("Error Parsing XML");
                returnObjectString = XMLUtils.marshalFromObject(ErrorType.getErrorTypeUnknownCodeUndetermined(), false);
                return returnObjectString;
            } catch (JAXBException ex1) {
                logger.error(ex);
                returnObjectString = "<ErrorType Type=\"1\" Code=\"197\" xmlns=\"http://cgbridge.rategain.com/OTA/2012/05\">Error Parsing XML</ErrorType>";
                return returnObjectString;
            }
        } catch (Exception ex) {
            logger.error(ex);
            try {
                //MSG_ERROR_PARSING
                ErrorType errorType = ErrorType.getErrorTypeUnknownCodeUndetermined();
                errorType.setValue("ex");
                returnObjectString = XMLUtils.marshalFromObject(ErrorType.getErrorTypeUnknownCodeUndetermined(), false);
                return returnObjectString;
            } catch (JAXBException ex1) {
                logger.error(ex1);
                returnObjectString = "<ErrorType Type=\"1\" Code=\"448\" xmlns=\"http://cgbridge.rategain.com/OTA/2012/05\">" +
                        "Error Unknown. " + ex1.getMessage() + "</ErrorType>";
                return returnObjectString;
            }
        } finally {
            try {
                final Set<EntryQueueItem> items = new HashSet<EntryQueueItem>(queueItemsMap.values());
                final Integer id = integrationBeanLocal.storeSingleConnection
                        (hotelCode, ChannelTicker.RATEGAIN, type, status, items,
                                requestBody.replace("\n", "").replace("\r", ""), returnObjectString);
//                final Integer id = integrationBeanLocal.storeSingleConnection
//                        (hotelCode, ChannelTicker.RATEGAIN, type, status, items);
//                final String requestCommunicationId = integrationBeanLocal.generateRequestCommunicationId(id);
//                final String responseCommunicationId = integrationBeanLocal.generateResponseCommunicationId(id);
//                logger.info(requestCommunicationId + requestBody.replace("\n", "").replace("\r", ""));
//                logger.info(responseCommunicationId + returnObjectString);
            } catch (IntegrationException e) {
                logger.error(e);
            }
        }

    }
}
