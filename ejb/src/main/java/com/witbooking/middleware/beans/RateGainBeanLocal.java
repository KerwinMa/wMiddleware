/*
 *  RateGainBeanLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.integration.rategain.RateGainException;
import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRQ;
import com.witbooking.middleware.integration.rategain.model.*;

import javax.ejb.Local;

/**
 * Insert description here
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 20, 2013
 */
@Local
public interface RateGainBeanLocal {

    public HotelPropertyListGetRS getHotelPropertyList(final HotelPropertyListGetRQ request) throws RateGainException;

    public HotelProductListGetRS getHotelProductList(final HotelProductListGetRQ request) throws RateGainException;

    public HotelARIGetRS getHotelARIGetRS(final HotelARIGetRQ request) throws RateGainException;

    public HotelARIUpdateRS getHotelARIUpdateRS(final HotelARIUpdateRQ request) throws RateGainException;

    public OTAHotelResNotifRQ getOTAHotelResNotifRQ(final String reservationId,
                                                    final String hotelTicker) throws RateGainException;
}
