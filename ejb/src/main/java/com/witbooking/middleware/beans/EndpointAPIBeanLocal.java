/*
 *  EndpointAPIBeanLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.InvalidEntryException;
import com.witbooking.middleware.exceptions.WitBookerAPIException;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.values.HashRangeResult;
import com.witbooking.middleware.model.values.HashRangeValue;

import javax.ejb.Local;
import java.util.Date;
import java.util.List;

/**
 * Local Interface for the Session Bean EndpointAPIBeanLocal
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 19-abr-2013
 * @since 1.0
 */
@Local
public interface EndpointAPIBeanLocal {

    public String getHotelTickers(String username, String password) throws WitBookerAPIException;

    public List<Reservation> getReservationsByCreationDate(Date startDate, Date endDate, String hotelTicker) throws WitBookerAPIException, InvalidEntryException;

    public Reservation getReservationsByReservationId(String reservationId, String hotelTicker) throws WitBookerAPIException, InvalidEntryException;

    public List<Inventory> getInventories(String hotelTicker, String locale) throws WitBookerAPIException;

    public List<DataValueHolder> getARIManagementData(String hotelTicker, String locale) throws WitBookerAPIException;

    public List<Service> getServices(String hotelTicker, String locale) throws WitBookerAPIException;

    public List<Discount> getDiscounts(String hotelTicker, String locale) throws WitBookerAPIException;

    public List<Tax> getTaxes(String hotelTicker) throws WitBookerAPIException;

    public ManagementVisualRepresentation getManagementARIValues(final String hotelTicker, final Date start, final Date end, String locale) throws WitBookerAPIException;

    public List<HashRangeValue> getFullARIValues(String hotelTicker, Date start, Date end) throws WitBookerAPIException;

    public List<HashRangeValue> getARIValues(Date startDate, Date endDate, String hotelTicker, List<String> invTickers, Integer adults) throws WitBookerAPIException, InvalidEntryException;

    public List<HashRangeResult> updateARIValues(List<HashRangeValue> mapValues, String hotelTicker) throws WitBookerAPIException, InvalidEntryException;

    public List<HashRangeResult> getBookingValues(Date checkInDate, Date checkOutDate, String hotelTicker,
                                                  List<String> invTickers, Integer adults, String currency,
                                                  String promotionalCode) throws WitBookerAPIException, InvalidEntryException;

    public String contactInfo();
}
