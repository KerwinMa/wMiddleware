/*
 *  AdmARIBeanLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.siteminder;

import javax.ejb.Local;

/**
 * Local Interface for the Session Bean SiteMinderEnqueuer
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 04-dec-2013
 */
@Local
public interface SiteMinderEnqueuerLocal {

    public String reportReservation(String reservationId, String hotelTicker);

}
