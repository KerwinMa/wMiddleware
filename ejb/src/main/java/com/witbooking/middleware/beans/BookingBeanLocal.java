/*
 *  BookingBeanLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.integration.booking.BookingException;
import com.witbooking.middleware.integration.booking.model.response.OTA_HotelAvailNotifRS;
import com.witbooking.middleware.integration.booking.model.response.OTA_HotelRateAmountNotifRS;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Local Interface for the Session Bean BookingBeanLocal.
 *
 * @author Jose Francisco Fiorillo  < jffiorillo@gmail.com >
 * @since 1.0 , Aug 22, 2013
 */
@Local
public interface BookingBeanLocal {

    public void handleCancelledReservations(String bookingHotelTicker, String reservationId, Date lastChange) throws BookingException;

    public void handleModifiedReservations(String bookingHotelTicker, String reservationId, Date lastChange) throws BookingException;

    public void handleNewReserves(String bookingHotelTicker, String reservationId, Date lastChange) throws BookingException;

    public OTA_HotelAvailNotifRS updateARI(String hotelTicker, List<String> inventoryTickers, Date start, Date end) throws BookingException;

    public OTA_HotelRateAmountNotifRS updateAmount(String hotelTicker, List<String> inventoryTickers, Date start, Date end) throws BookingException;
}
