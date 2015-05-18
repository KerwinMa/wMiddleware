package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.FrontEndException;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.values.HashRangeValue;

import javax.ejb.Remote;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * ServerInformationBeanRemote.java
 * User: jose
 * Date: 12/13/13
 * Time: 6:57 PM
 */
@Remote
public interface ServerInformationBeanRemote {

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

    public HotelMedia insertHotelMediaIfNotExists(final String hotelTicker, final String entity,
                                                  final Integer idEntity, final String fileName) throws FrontEndException;

    public Map<String, Establishment> getWitBookerVisualRepresentation(final String hotelTicker,
                                                                       final List<String> propertyNames) throws FrontEndException;

    public Map<String, Establishment> getWitHotelVisualRepresentation(final String hotelTicker,
                                                                      final List<String> propertyNames) throws FrontEndException;

    public List<HashRangeValue> getARI(final String hotelTicker, List<String> inventoryTickers,
                                       final Date start, final Date end,
                                       final String currency, final String promotionalCode , final String country) throws FrontEndException;

    public List<Page> getPages(final String hotelTicker, final String locale) throws FrontEndException;

    public CurrencyExchange getCurrenciesExchange(String currency) throws FrontEndException;

    public Map getCountriesMap() throws FrontEndException;

    public Map<String, Object> getQueryExecutionTimes(final String ticker);

    public int insertConversionQuery(final String hotelTicker, final String clientIp, final Date checkInDate,
                                     final Date checkOutDate, final Integer rooms, final int adults,
                                     final int children, final int infants, final String language,
                                     final boolean isSoldOut, final boolean isChain,
                                     final String channelId, final String trackingId) throws FrontEndException;

    public Reservation insertReservation(final String hotelTicker, Reservation reservation) throws FrontEndException;

    public CurrencyExchange getCurrencyExchange(String masterCurrency) throws FrontEndException;
}
