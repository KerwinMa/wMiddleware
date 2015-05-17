/*
 *  RateGainEnqueuer.java
 *
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 *
 */
package com.witbooking.middleware.integration.rategain;

import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.integration.ChannelConnectionType;
import com.witbooking.middleware.integration.ChannelTicker;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;

/**
 * Web Service.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 21, 2013
 */
@Stateless
public class RateGainEnqueuer extends RateGainBusinessLogic implements RateGainEnqueuerLocal {


    private static final Logger logger = Logger.getLogger(RateGainEnqueuer.class);

    @Override
    public String reportReservation(String reservationId, String hotelTicker) {
        logger.debug("ReportReservation");

        final ChannelConnectionType type = ChannelConnectionType.NOTIFY_RESERVES;
        try {
            final String info = integrationBeanLocal.notifyNewEntry(hotelTicker, ChannelTicker.RATEGAIN, type, reservationId, null, null);
            if (info != null) {
                return integrationBeanLocal.successWithWarning(info);
            }
            return integrationBeanLocal.success();
        } catch (IntegrationException e) {
            logger.error(e);
            return integrationBeanLocal.error(e);
        }
    }

    @Override
    public String handlingRequest(final String requestBody){
        return super.handlingRequest(requestBody);
    }
}
