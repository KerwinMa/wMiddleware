/*
 *  TripAdvisorLocalBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.RemoteServiceException;
import com.witbooking.middleware.exceptions.integration.tripadvisor.TripAdvisorException;

import javax.ejb.Local;

/**
 * @author Jose Francisco Fiorillo
 */
@Local
public interface TripAdvisorLocalBean {

    public String config();

    public String hotelInventory(String api_version, String lang) throws RemoteServiceException;

    public String hotelAvailability(String apiVersion, String hotels, String startDate, String endDate, String numAdults,
                                    String numRooms, String lang, String currency, String userCountry, String deviceType,
                                    String queryKey);

    public void updateReservation(String reservationId, String hotelTicker) throws TripAdvisorException;

    public void createReservation(String reservationId, String hotelTicker) throws TripAdvisorException;

    public String listReserves(String hotelTicker) throws TripAdvisorException;

    public String listReservation(String hotelTicker, String reservationId) throws TripAdvisorException;

    public void cancelReservation(String hotelTicker, String reservationsID) throws TripAdvisorException;

    public String checkOptionsIn(String hotelTicker) throws TripAdvisorException;

}
