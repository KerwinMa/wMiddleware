/*
 *  AdmARIBeanLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.rategain;

import javax.ejb.Local;

/**
 * Local Interface for the Session Bean RateGainEnqueuer
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 04-dec-2013
 */
@Local
public interface RateGainEnqueuerLocal {

    public String reportReservation(String reservationId, String hotelTicker);

    public String handlingRequest(final String requestBody);

}
