/*
 *  RateGainBeanRemote.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.integration.rategain.RateGainException;
import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRQ;
import com.witbooking.middleware.integration.rategain.model.*;

import javax.ejb.Remote;

/**
 * Remote Interface for the Session Bean RateGainBeanRemote
 *
 * @author Christian Delgado
 * @date 13-may-2013
 * @version 1.0
 * @since
 */
@Remote
public interface RateGainBeanRemote {

    public HotelPropertyListGetRS getHotelPropertyList(final HotelPropertyListGetRQ resquest) throws RateGainException;

    public HotelProductListGetRS getHotelProductList(final HotelProductListGetRQ resquest) throws RateGainException;

    public HotelARIGetRS getHotelARIGetRS(final HotelARIGetRQ request) throws RateGainException;

    public HotelARIUpdateRS getHotelARIUpdateRS(final HotelARIUpdateRQ request) throws RateGainException;

    public OTAHotelResNotifRQ getOTAHotelResNotifRQ(final String reservationId,
                                                    final String hotelTicker) throws RateGainException;
}
