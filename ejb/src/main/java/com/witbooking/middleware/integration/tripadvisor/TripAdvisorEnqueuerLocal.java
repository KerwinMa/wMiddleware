/*
 *   TripAdvisorExecutorLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.tripadvisor;

import com.witbooking.middleware.exceptions.integration.tripadvisor.TripAdvisorException;

import javax.ejb.Local;

/**
 * Local Interface for the Session Bean TripAdvisorEnqueuer
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 02/12/13
 */
@Local
public interface TripAdvisorEnqueuerLocal {

    public String getConfiguration();

    public String getHotelInventory(String api_version, String lang);

    public String getHotelAvailability(String apiVersion, String hotelsJson, String startDate, String endDate,
                                       String numAdults, String numRooms, String lang, String currency,
                                       String userCountry, String deviceType,
                                       String queryKey) throws TripAdvisorException;

    public String listAllReservations(String hotelTicker);

    public String listReservation(String hotelTicker, String reservationId);

    public String reportReservation(String reservationId, String hotelTicker);

    public String updateReservation(String reservationId, String hotelTicker);

    public String cancelReservation(String reservationId, String hotelTicker);

    public String checkOptionsIn(String hotelTicker);

}
