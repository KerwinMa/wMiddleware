package com.witbooking.middleware.integration.siteminder;

import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.integration.ChannelConnectionType;
import com.witbooking.middleware.integration.ChannelTicker;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;

/**
 * SiteMinderEnqueuer.java
 * User: Christian Delgado
 * Date: 11/20/13
 * Time: 1:58 PM
 */
@Stateless
public class SiteMinderEnqueuer extends SiteMinderBusinessLogic implements SiteMinderEnqueuerLocal {

    private static final Logger logger = Logger.getLogger(SiteMinderEnqueuer.class);

    @Override
    public String reportReservationLogic(String reservationId, String hotelTicker) {
        try {
            final String warning = integrationBeanLocal.notifyNewEntry(hotelTicker,
                    ChannelTicker.SITEMINDER, ChannelConnectionType.NOTIFY_RESERVES, reservationId, null, null);
            return (warning == null)
                    ? integrationBeanLocal.success()
                    : integrationBeanLocal.successWithWarning(warning);
        } catch (IntegrationException e) {
            logger.error(e);
            return integrationBeanLocal.error(e);
        }
    }

    @Override
    public String reportReservation(String reservationId, String hotelTicker) {
        return reportReservationLogic(reservationId, hotelTicker);
    }

}