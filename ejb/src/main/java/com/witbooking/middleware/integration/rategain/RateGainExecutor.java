package com.witbooking.middleware.integration.rategain;

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
 * RateGainExecutorResource.java
 * User: jose
 * Date: 11/14/13
 * Time: 11:32 AM
 */
@Stateless
public class RateGainExecutor extends RateGainBusinessLogic implements RateGainExecutorLocal {

    private static final Logger logger = Logger.getLogger(RateGainExecutor.class);

    @Override
    public String reportReservation(final String reservationId, final String hotelTicker) {
        logger.debug("ReportReservation");
        OTAHotelResNotifRQ notifRQ = null;
        OTAHotelResNotifRS otaHotelResNotifRS = null;
        String response = null;
        ChannelQueueStatus status = ChannelQueueStatus.FAIL;
        try {
            notifRQ = rateGainBean.getOTAHotelResNotifRQ(reservationId, hotelTicker);
//            otaHotelResNotif(rateGainBean.getOTAHotelResNotifRQ(reservationId, hotelTicker));
            otaHotelResNotifRS = otaHotelResNotif(notifRQ);
            if (otaHotelResNotifRS == null) {
                response = integrationBeanLocal.error("Error got was null.");
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
                    logger.error("Error in RateGain ResNotif: "+errorMessage);
                }
            }
        } catch (Exception ex) {
            logger.error(ex);
//            otaHotelResNotifRS = new OTAHotelResNotifRS(com.witbooking.middleware.integration.ota.model.ErrorType.getUnableToProcess(ex.getMessage()));
            response = integrationBeanLocal.error(ex);
        } finally {
            try {
                integrationBeanLocal.reportAnConnection(hotelTicker, reservationId, ChannelTicker.RATEGAIN,
                        ChannelConnectionType.NOTIFY_RESERVES, status, notifRQ, otaHotelResNotifRS);
            } catch (IntegrationException ex) {
                logger.error(ex);
            }

        }
        return response;
    }



    @Override
    public OTAHotelResNotifRQ getOTAHotelResNotifRQ(final String reservationId, final String hotelTicker) {
        logger.debug("ReportReservation");
        OTAHotelResNotifRQ notifRQ = null;
        try {
            logger.debug("hotelTicker: " + hotelTicker);
            logger.debug("reservationId: " + reservationId);
            notifRQ = rateGainBean.getOTAHotelResNotifRQ(reservationId, hotelTicker);
        } catch (Exception ex) {
            logger.error(ex);
        }
        return notifRQ;
    }
}