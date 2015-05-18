/*
 *  BookingBeanRemote.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.integration.booking.BookingException;

import javax.ejb.Remote;
import java.util.Date;

/**
 * Insert description here
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @since 1.0 , Sep 3, 2013
 */
@Remote
public interface BookingBeanRemote {

    public void handleCancelledReservations(String bookingHotelTicker, String reservationId, Date lastChange) throws BookingException;

    public void handleModifiedReservations(String bookingHotelTicker, String reservationId, Date lastChange) throws BookingException;

    public void handleNewReserves(String bookingHotelTicker, String reservationId, Date lastChange) throws BookingException;

}
