package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.exceptions.integration.rategain.RateGainException;
import com.witbooking.middleware.integration.ota.model.*;

import javax.ejb.Local;

/**
 * SiteMinderBeanLocal.java
 * User: jose
 * Date: 9/26/13
 * Time: 11:53 AM
 */
@Local
public interface SiteMinderBeanLocal {

    public OTAHotelAvailRS OTAHotelAvailRQ(final OTAHotelAvailRQ otaHotelAvailRQ);

    public OTAHotelAvailNotifRS OTAHotelAvailNotifRQ(final OTAHotelAvailNotifRQ ota_hotelAvailNotifRQ);

    public OTAHotelRateAmountNotifRS OTAHotelRateAmountNotifRQ(final OTAHotelRateAmountNotifRQ otaHotelRateAmountNotifRQ);

    public OTAHotelResNotifRQ getOTAHotelResNotifRQ(final String reservationId, final String hotelTicker) throws RateGainException;

    public void cancelBehavior(final String hotelTicker, final String reservationId) throws ExternalFileException, DBAccessException, NonexistentValueException;

}