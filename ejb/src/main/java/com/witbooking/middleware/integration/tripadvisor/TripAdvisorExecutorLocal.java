/*
 *   TripAdvisorExecutorLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.tripadvisor;

import javax.ejb.Local;

/**
 * Local Interface for the Session Bean TripAdvisorExecutor
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 02/12/13
 */
@Local
public interface TripAdvisorExecutorLocal {

    public String reportReservation(String reservationId, String hotelTicker);

    public String updateReservation(String reservationId, String hotelTicker);

    public String cancelReservation(String reservationId, String hotelTicker);

}
