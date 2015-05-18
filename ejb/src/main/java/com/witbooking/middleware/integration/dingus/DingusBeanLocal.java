/*
 *   DingusBeanLocal.java
 * 
 * Copyright(c) 2015 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.dingus;

import com.witbooking.middleware.exceptions.integration.dingus.DingusException;
import com.witbooking.middleware.integration.dingus.model.*;

import javax.ejb.Local;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 04/03/2015
 */
@Local
public interface DingusBeanLocal {

    public GetHotelInfoResponse getHotelInfo(GetHotelInfoRQ request) throws DingusException;

    public HotelAvailRateUpdateRS updateARIValues(HotelAvailRateUpdateRQ request) throws DingusException;

    public BookingRetrievalResponse bookingRetrieval(BookingRetrievalRQ request) throws DingusException;

}
