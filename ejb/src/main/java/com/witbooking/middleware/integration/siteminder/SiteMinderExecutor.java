package com.witbooking.middleware.integration.siteminder;

import com.google.common.base.Joiner;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.integration.ChannelConnectionType;
import com.witbooking.middleware.integration.ChannelQueueStatus;
import com.witbooking.middleware.integration.ChannelTicker;
import com.witbooking.middleware.integration.ota.model.ErrorsType;
import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRQ;
import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRS;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;

/**
 * SiteMinderExecutor.java
 * User: Christian
 * Date: 11/20/13
 * Time: 1:55 PM
 */
@Stateless
public class SiteMinderExecutor extends SiteMinderBusinessLogic implements SiteMinderExecutorLocal {

    private static final Logger logger = Logger.getLogger(SiteMinderExecutor.class);

    @Override
    public String reportReservationLogic(final String reservationId, final String hotelTicker) {
        logger.debug("ReportReservation");
        OTAHotelResNotifRQ notifRQ = null;
        OTAHotelResNotifRS otaHotelResNotifRS = null;
        String response;
        final String request = "reservationId: " + reservationId + " hotelTicker: " + hotelTicker;
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        try {
            logger.debug("hotelTicker: " + hotelTicker);
            logger.debug("reservationId: " + reservationId);
            notifRQ = siteMinderBeanLocal.getOTAHotelResNotifRQ(reservationId, hotelTicker);
            otaHotelResNotifRS = otaHotelResNotif(notifRQ, reservationId, hotelTicker);
            if (otaHotelResNotifRS == null) {
                response = integrationBeanLocal.error("Null response");
            } else {
                if (otaHotelResNotifRS.getSuccess() != null) {
                    response = integrationBeanLocal.success();
                    status = ChannelQueueStatus.SUCCESS;
                } else {
                    ErrorsType errors = otaHotelResNotifRS.getErrors();
                    String errorMessage = (errors != null && errors.getError() != null && errors.getError().size() > 0)
                            ? "errors found: " + Joiner.on(", ").join(errors.getError())
                            : "Success not found, but neither error list.";
                    response = integrationBeanLocal.error(errorMessage);
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
            otaHotelResNotifRS = new OTAHotelResNotifRS(com.witbooking.middleware.integration.ota.model.ErrorType.getUnableToProcess(ex.getMessage()));
            response = integrationBeanLocal.error(ex);
        } finally {
            try {
                integrationBeanLocal.reportAnConnection(hotelTicker, reservationId, ChannelTicker.SITEMINDER, ChannelConnectionType.NOTIFY_RESERVES, status, request, otaHotelResNotifRS);
            } catch (IntegrationException e) {
                logger.error(e);
            }
        }
        return response;
    }

    @Override
    public String reportReservation(String reservationId, String hotelTicker) {
        return super.reportReservation(reservationId, hotelTicker);
    }

    @Override
    public OTAHotelResNotifRQ getOTAHotelResNotifRQ(final String reservationId, final String hotelTicker) {
        logger.debug("ReportReservation");
        OTAHotelResNotifRQ notifRQ = null;
        try {
            logger.debug("hotelTicker: " + hotelTicker);
            logger.debug("reservationId: " + reservationId);
            notifRQ = siteMinderBeanLocal.getOTAHotelResNotifRQ(reservationId, hotelTicker);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return notifRQ;
    }
}