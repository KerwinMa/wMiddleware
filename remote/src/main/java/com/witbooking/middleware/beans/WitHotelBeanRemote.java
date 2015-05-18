/*
 *  WitHotelBeanRemote.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.FrontEndException;
import com.witbooking.middleware.model.*;

import javax.ejb.Remote;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Remote Interface for the Session Bean WitHotelBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 08-may-2013
 */
@Remote
public interface WitHotelBeanRemote {

    public Establishment getEstablishment(String hotelTicker, String locale) throws FrontEndException;

    public List<Accommodation> getAccommodations(String hotelTicker, String locale) throws FrontEndException;

    public List<Discount> getDiscounts(String hotelTicker, String locale) throws FrontEndException;

    public List<Service> getServices(String hotelTicker, String locale) throws FrontEndException;

    public List<Language> getLanguages(String hotelTicker) throws FrontEndException;

    public List<LocationPoint> getLocationPoints(String hotelTicker, String locale) throws FrontEndException;

    public List<Review> getReviews(String hotelTicker, String locale) throws FrontEndException;

    public List<Currency> getCurrencies(String hotelTicker) throws FrontEndException;

    public WitHotel getHotel(String hotelTicker) throws FrontEndException;

    public Properties getConfigurations(String hotelTicker) throws FrontEndException;

    public List<String> getLocationTypes() throws FrontEndException;

    public boolean subscribeNewsletter(String hotelTicker, String email, String language) throws FrontEndException;

    public String testFrontEndServices();

    public Map<String, Establishment> getWitHotelVisualRepresentation(final String hotelTicker,
                                                                      final List<String> parametersName)
            throws FrontEndException;

    public List<Page> getPages(final String hotelTicker, final String locale) throws FrontEndException;

    public List<String> sendEmail(String fromAddress, String fromName, String subject, String contentHTML,
                                  List<String> toAddress, String toName, List<String> tags) throws FrontEndException;

}
