/*
 *  ScrappingBeanRemote.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.ScrappingException;
import com.witbooking.middleware.model.Reservation;
import com.witbooking.middleware.model.Tax;
import com.witbooking.middleware.model.values.HashRangeValue;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 * Remote Interface for the Session Bean ScrappingBeanRemote
 *
 * @author Christian Delgado
 * @date 19-abr-2013
 * @version 1.0
 * @since
 */
@Remote
public interface ScrappingBeanRemote {

    public String login(String username, String password) throws ScrappingException;

    public List<Reservation> getReservationsByCreationDate(Date dateStart, Date dateEnd, String hotelTicker) throws ScrappingException;

    public List<Tax> getTaxes(String hotelTicker) throws ScrappingException;

    public List<HashRangeValue> getARIValues(Date startDate, Date endDate, String hotelTicker) throws ScrappingException;

    public int updateARIValues(List<HashRangeValue> mapValues, Date startDate, Date endDate, String hotelTicker) throws ScrappingException;
}
