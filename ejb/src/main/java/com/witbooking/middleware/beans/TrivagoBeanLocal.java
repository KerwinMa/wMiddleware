/*
 *   TrivagoBeanLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.integration.trivago.TrivagoException;
import com.witbooking.middleware.integration.trivago.AvailabilityRequest;
import com.witbooking.middleware.integration.trivago.AvailabilityResponse;
import com.witbooking.middleware.integration.trivago.DeepLink;
import com.witbooking.middleware.integration.trivago.HotelList;

import javax.ejb.Local;
import java.util.Date;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 28/06/13
 */
@Local
public interface TrivagoBeanLocal {

    public HotelList getHotelList() throws TrivagoException;

    public DeepLink getDeepLink(String hotelId, Date check_in, Date check_out, String paramLanguage,
                                int adults, int roomType, String currency) throws TrivagoException;

    public AvailabilityResponse getAvailability(AvailabilityRequest availabilityRequest) throws TrivagoException;

    public AvailabilityResponse testServicesTrivago();
}
