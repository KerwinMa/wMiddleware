/*
 *   DingusResource.java
 * 
 * Copyright(c) 2015 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.dingus;

import com.witbooking.middleware.integration.dingus.model.*;
import com.witbooking.middleware.utils.HttpConnectionUtils;
import com.witbooking.middleware.utils.XMLUtils;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBException;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 23/03/2015
 */
@Path("/dingus")
@Stateless
public class DingusResource {
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(DingusResource.class);

    @EJB
    private DingusBeanLocal dingusBeanLocal;

    @POST
    @Produces("application/xml;charset=utf-8")
    @Path("/GetHotelInfoRQ")
    public GetHotelInfoResponse getHotelInfoRQ(final GetHotelInfoRQ request) throws JAXBException {
        logger.debug("getHotelInfoRQ");
        try {
            return dingusBeanLocal.getHotelInfo(request);
        } catch (Exception ex) {
            logger.error(ex);
            GetHotelInfoResponse response = new GetHotelInfoResponse();
            response.addErrorMessage(ex + "");
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    XMLUtils.marshalFromObject(response, true));
        }
    }


    @POST
    @Produces("application/xml;charset=utf-8")
    @Path("/HotelAvailRateUpdateRQ")
    public HotelAvailRateUpdateRS hotelAvailRateUpdateRQ(final HotelAvailRateUpdateRQ request) throws JAXBException {
        logger.debug("hotelAvailRateUpdateRQ");
        try {
            return dingusBeanLocal.updateARIValues(request);
        } catch (Exception ex) {
            logger.error(ex);
            HotelAvailRateUpdateRS response = new HotelAvailRateUpdateRS();
            response.addErrorMessage(ex + "");
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    XMLUtils.marshalFromObject(response, true));
        }
    }


    @POST
    @Produces("application/xml;charset=utf-8")
    @Path("/BookingRetrievalRQ")
    public BookingRetrievalResponse bookingRetrievalRQ(final BookingRetrievalRQ request) throws JAXBException {
        logger.debug("bookingRetrievalRQ");
        try {
            return dingusBeanLocal.bookingRetrieval(request);
        } catch (Exception ex) {
            logger.error(ex);
            BookingRetrievalResponse response = new BookingRetrievalResponse();
            response.addErrorMessage(ex + "");
            throw HttpConnectionUtils.generateJaxException(ex, "application/json;charset=utf-8",
                    XMLUtils.marshalFromObject(response, true));
        }
    }
}
