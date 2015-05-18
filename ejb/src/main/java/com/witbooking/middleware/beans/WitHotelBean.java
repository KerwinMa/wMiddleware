/*
 *  FrontEndBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.FrontEndException;
import com.witbooking.middleware.model.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Session Bean implementation class WitHotelBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 08-may-2013
 */
@Stateless
public class WitHotelBean implements WitHotelBeanRemote, WitHotelBeanLocal {

    @EJB
    private ServerInformationBeanLocal serverInformationBeanLocal;

    public Establishment getEstablishment(String hotelTicker, String locale) throws FrontEndException {
        return serverInformationBeanLocal.getEstablishment(hotelTicker, locale);
    }

    public List<Accommodation> getAccommodations(String hotelTicker, String locale) throws FrontEndException {
        return serverInformationBeanLocal.getAccommodations(hotelTicker, locale);
    }

    public List<Discount> getDiscounts(String hotelTicker, String locale) throws FrontEndException {
        return serverInformationBeanLocal.getDiscounts(hotelTicker, locale);
    }

    public List<Service> getServices(String hotelTicker, String locale) throws FrontEndException {
        return serverInformationBeanLocal.getServices(hotelTicker, locale);
    }

    public List<Language> getLanguages(String hotelTicker) throws FrontEndException {
        return serverInformationBeanLocal.getLanguages(hotelTicker);
    }

    public List<LocationPoint> getLocationPoints(String hotelTicker, String locale) throws FrontEndException {
        return serverInformationBeanLocal.getLocationPoints(hotelTicker, locale);
    }

    public List<Review> getReviews(String hotelTicker, String locale) throws FrontEndException {
        return serverInformationBeanLocal.getReviews(hotelTicker, locale);
    }

    public boolean subscribeNewsletter(String hotelTicker, String email, String language) throws FrontEndException {
        return serverInformationBeanLocal.subscribeNewsletter(hotelTicker, email, language);
    }

    public List<Currency> getCurrencies(String hotelTicker) throws FrontEndException {
        return serverInformationBeanLocal.getCurrencies(hotelTicker);
    }

    public WitHotel getHotel(String hotelTicker) throws FrontEndException {
        return serverInformationBeanLocal.getHotel(hotelTicker);
    }

    public Properties getConfigurations(String hotelTicker) throws FrontEndException {
        return serverInformationBeanLocal.getConfigurations(hotelTicker);
    }

    public List<Page> getPages(final String hotelTicker, final String locale) throws FrontEndException {
        return serverInformationBeanLocal.getPages(hotelTicker, locale);
    }


    public List<String> sendEmail(String fromAddress, String fromName, String subject, String contentHTML,
                            List<String> toAddress, String toName, List<String> tags) throws FrontEndException{
        return serverInformationBeanLocal.sendEmail(fromAddress, fromName, subject, contentHTML, toAddress, toName, tags);
    }

    @Override
    public List<String> getLocationTypes() throws FrontEndException {
        return serverInformationBeanLocal.getLocationTypes();
    }

    public String testFrontEndServices() {
        return serverInformationBeanLocal.testFrontEndServices();
    }

    public Map<String, Establishment> getWitHotelVisualRepresentation(final String hotelTicker,
                                                                      final List<String> parametersName)
            throws FrontEndException {
        return serverInformationBeanLocal.getWitHotelVisualRepresentation(hotelTicker, parametersName);
    }
}
