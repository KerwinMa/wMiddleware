package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.integration.google.model.feed.Listings;
import com.witbooking.middleware.integration.google.model.hpa.*;

import javax.ejb.Local;
import java.util.List;

/**
 * GoogleBeanLocal.java
 * User: jose
 * Date: 4/9/14
 * Time: 12:51 PM
 */
@Local
public interface GoogleBeanLocal {


    public Listings getLocalFeed() throws DBAccessException, NonexistentValueException, ExternalFileException;

    public Transaction getARI(Query query);

    public HotelInfoFeed getHotelInfoFeed(final String hotelTicker) throws MiddlewareException;

    public ExchangeRates getCurrencyExchange() throws MiddlewareException;

    public Bids getBid() throws MiddlewareException;

    public Bids getBid(List<String> hotelTickers) throws MiddlewareException;

    public Configuration getConfiguration();


}