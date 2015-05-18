package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.integration.rategain.RateGainException;
import com.witbooking.middleware.integration.ota.model.*;

import javax.ejb.Local;

/**
 * OTAServicesBeanLocal.java
 * User: jose
 * Date: 4/29/13
 * Time: 03:04 PM
 */
@Local
public interface OTAServicesBeanLocal {

    public OTAHotelAvailRS OTAHotelAvailRQ(final OTAHotelAvailRQ otaHotelAvailRQ);

    public OTAHotelAvailNotifRS OTAHotelAvailNotifRQ(final OTAHotelAvailNotifRQ ota_hotelAvailNotifRQ);

    public OTAHotelRateAmountNotifRS OTAHotelRateAmountNotifRQ(final OTAHotelRateAmountNotifRQ otaHotelRateAmountNotifRQ);

    public OTAHotelResNotifRQ getOTAHotelResNotifRQ(final String reservationId, final String hotelTicker) throws RateGainException;
}