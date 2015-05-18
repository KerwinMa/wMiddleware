/*
 *  TripAdvisorRemoteBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.RemoteServiceException;

import javax.ejb.Remote;

/**
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Aug 29, 2013
 */
@Remote
public interface TripAdvisorRemoteBean {

    public String config();

    public String hotelInventory(String api_version, String lang) throws RemoteServiceException;

    public String hotelAvailability(String apiVersion, String hotels, String startDate, String endDate, String numAdults,
                                    String numRooms, String lang, String currency, String userCountry, String deviceType,
                                    String queryKey);
}
