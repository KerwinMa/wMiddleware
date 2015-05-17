/*
 *  AdmARIBeanLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.rategain;

import com.witbooking.middleware.integration.ota.model.OTAHotelResNotifRQ;

import javax.ejb.Local;

/**
 * Local Interface for the Session Bean RateGainExecutor
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 04-dec-2013
 */
@Local
public interface RateGainExecutorLocal {

    public String reportReservation(String reservationId, String hotelTicker);

    public OTAHotelResNotifRQ getOTAHotelResNotifRQ(final String reservationId, final String hotelTicker);

}
