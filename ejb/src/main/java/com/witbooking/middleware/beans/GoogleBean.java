package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.*;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.integration.google.model.Bid;
import com.witbooking.middleware.integration.google.model.feed.*;
import com.witbooking.middleware.integration.google.model.feed.Amenity;
import com.witbooking.middleware.integration.google.model.hpa.*;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.Configuration;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.*;
import java.util.Date;

/**
 * GoogleBean.java
 * User: jose
 * Date: 4/9/14
 * Time: 12:50 PM
 */
@Stateless
public class GoogleBean implements GoogleBeanLocal, GoogleBeanRemote {

    private static final Logger logger = Logger.getLogger(GoogleBean.class);
    public static final String ADDRESS_FORMAT = "simple";

    /**
     * Implements feed the general information of all hotels.
     *
     * @return
     * @throws DBAccessException
     * @throws NonexistentValueException
     * @throws ExternalFileException
     */
    public Listings getLocalFeed() throws DBAccessException, NonexistentValueException, ExternalFileException {
        final Listings listings = new Listings();
        listings.setLanguage(LanguageContent.EN);
        final String locale = "eng";
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        try {
            //Use the WitMetaDataDBHandler to get the production hotelticker list.
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            for (final String hotelTicker : witMetaDataDBHandler.getHotelListProduction()) {
                DBConnection dbConnection = null;
                try {
                    try {
                        dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
                    } catch (NonexistentValueException e) {
                        logger.error(e);
                        logger.error("Error in ticker: " + hotelTicker);
                        continue;
                    } catch (ExternalFileException e) {
                        logger.error(e);
                        logger.error("Error in ticker: " + hotelTicker);
                        continue;
                    } catch (DBAccessException e) {
                        logger.error(e);
                        logger.error("Error in ticker: " + hotelTicker);
                        continue;
                    }
                    //Get the Hotel object.
                    final Hotel hotel = new InventoryDBHandler(dbConnection, locale).getHotel();
                    listings.addListing(configureListing(hotel, hotelTicker, dbConnection));
                } catch (Exception ex) {
                    logger.error(ex);
                } finally {
                    DAOUtil.close(dbConnection);
                }
            }
            return listings;
        } finally {
            if (witMetaDataDBHandler != null) DAOUtil.close(witMetaDataDBHandler.getDbConnection());
        }
    }

    /**
     * Generate the Listing corresponding to the information given.
     *
     * @param hotel
     * @param hotelTicker
     * @param dbConnection
     * @return
     * @throws DBAccessException
     */
    private Listing configureListing(final Hotel hotel, final String hotelTicker, final DBConnection dbConnection)
            throws DBAccessException {
        final Listing listing = new Listing();
        listing.setId(hotelTicker);
        //Configuring address
        listing.setAddress(getAddress(hotel));
        listing.setCountry(hotel.getCountry());
        listing.setLatitude(hotel.getLatitude());
        listing.setLongitude(hotel.getLongitude());
        listing.addPhone(filterPhone(hotel.getPhone()));
        final HotelType type = hotel.getType();
        final Category category = new Category();
        if (type != null) {
            category.setValue(type.getType());
        } else {
            category.setValue(HotelType.HOTEL.getType());
        }
        listing.addCategory(category);
        final HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
        listing.addName(hotelConfigurationDBHandler.getHotelName());
        String url = hotelConfigurationDBHandler.getProcessedURL(hotelTicker, "en");
        listing.setContent(getContent(hotel, url, hotelConfigurationDBHandler.getAmenities()));
        return listing;
    }

    /**
     * Utility function to parser the phone number.
     *
     * @param phone
     * @return
     */
    private Phone filterPhone(String phone) {
        final String regex = "^[\\w\\s\\+\\(\\)]+$";
        final String regexReplace = "[^\\w\\s\\+\\(\\)]";
        return (phone == null)
                ? new Phone(MiddlewareProperties.WITBOOKING_PHONE)
                : new Phone((phone.matches(regex)) ? phone : phone.replaceAll(regexReplace, ""));
    }

    /**
     * Utility function to fill the Content.
     *
     * @param hotel
     * @param url
     * @param amenities
     * @return
     */
    private Content getContent(final Hotel hotel, final String url, final List<com.witbooking.middleware.model.Amenity> amenities) {
        final Attributes attributes = new Attributes();
        final Amenities googleAmenities = getGoogleAmenities(amenities);
        attributes.setEmail(hotel.getEmailAdmin());
        attributes.setWebsite(url);
        final HotelData hotelData = new HotelData();
        hotelData.setAmenities(googleAmenities);
        attributes.setHotelData(hotelData);
        final Content content = new Content();
        content.addElement(attributes);
        return content;
    }

    /**
     * Utility function to generate the Address of one Hotel given.
     *
     * @param hotel
     * @return
     */
    private Address getAddress(final Hotel hotel) {
        final String address = hotel.getAddress();
        final String city = hotel.getCity();
        final String zone = hotel.getZone();
        final Address addressGoogle = new Address();
        addressGoogle.setFormat(ADDRESS_FORMAT);
        final String[] values = new String[]{address, city, zone};
        final String[] names = new String[]{"addr1", "city", "zone"};
        for (int i = 0; i < values.length; i++) {
            if (values[i] != null && !values[i].isEmpty()) {
                Component component = new Component();
                component.setContent(values[i]);
                component.setName(names[i]);
                addressGoogle.setContent(component);
            }
        }
        return addressGoogle;
    }

    /**
     * Utility function to map the witbooking amenities to the google amenities configured.
     *
     * @param amenities
     * @return
     */
    private Amenities getGoogleAmenities(List<com.witbooking.middleware.model.Amenity> amenities) {
        final Amenities amenitiesGoogle = new Amenities();
        amenitiesGoogle.setAcceptOnlineBooking(Amenity.YES);
        if (amenities != null) {
            for (final com.witbooking.middleware.model.Amenity amenity : amenities) {
                for (final String attributeName : amenity.getItemsGoogle()) {
                    try {
                        amenitiesGoogle.getClass().getField(attributeName).set(amenitiesGoogle, Amenity.YES);
                    } catch (IllegalAccessException e) {
                        logger.error(e);
                    } catch (NoSuchFieldException e) {
                        logger.error(e);
                    }
                }
            }
        }
        return amenitiesGoogle;
    }

    /**
     * Return the availability/price for the query requested.
     *
     * @param query
     * @return
     */
    public Transaction getARI(Query query) {
        Transaction transaction = new Transaction();
        final XMLGregorianCalendar checkInGregorianCalendar = query.getCheckin();
        final BigInteger nightsBI = query.getNights();
        // Validating the object query has all information that we need.
        if (query != null
                && checkInGregorianCalendar != null
                && nightsBI != BigInteger.ZERO
                && query.getPropertyList() != null
                && query.getPropertyList().getProperty() != null
                && !query.getPropertyList().getProperty().isEmpty()) {
            final java.util.Date checkIn = checkInGregorianCalendar.toGregorianCalendar().getTime();
            final int nights = nightsBI.intValue();
            final java.util.Date checkOut = new Date();
            DateUtil.incrementDays(checkOut, nights);
            //For each hotelTicker
            for (final String hotelTicker : query.getPropertyList().getProperty()) {
                DBConnection dbConnection = null;
                logger.debug("hotelTickerG-" + hotelTicker);
                try {
                    dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
                    final HotelConfigurationDBHandler hotelConfigurationDBHandler =
                            new HotelConfigurationDBHandler(dbConnection);
                    final Language defaultLanguage = hotelConfigurationDBHandler.getDefaultLanguage();
                    final String locale = defaultLanguage != null ? defaultLanguage.getLocale() : null;
                    final DailyValuesDBHandler dailyValuesDBHandler =
                            new DailyValuesDBHandler(dbConnection, checkIn, checkOut, locale);
                    dailyValuesDBHandler.getInventoryValuesBetweenDates();
                    final InventoryDBHandler inventoryDBHandler = dailyValuesDBHandler.getInventoryDBHandler();
                    //Get all taxes defining for the hotel.
                    float tax = getTaxes(inventoryDBHandler.getTaxes());
                    final String defaultCurrency = hotelConfigurationDBHandler.getDefaultCurrency();
                    //For each inventory
                    for (final Inventory inventory : inventoryDBHandler.getInventoriesActives()) {
                        final String inventoryTicker = inventory.getTicker();
                        final RangeValue<Integer> availabilityByTicker = dailyValuesDBHandler.getAvailabilityByTicker(inventoryTicker);
                        final boolean notAvailable = availabilityByTicker.hasValueEqualsTo(0);
                        final RangeValue<Float> rate = dailyValuesDBHandler.getFullRatesByTicker(inventoryTicker);
                        final float totalRate = rate.getSumValues();
                        transaction.addResult(getResult(inventory, inventoryTicker, nightsBI, checkInGregorianCalendar, hotelTicker, locale, notAvailable, defaultCurrency, totalRate, tax));
                    }
                } catch (MiddlewareException e) {
                    logger.error(e);
//                }catch (Exception e){
//                    logger.error(e);
                } finally {
                    DAOUtil.close(dbConnection);
                }
            }
        }
        return transaction;
    }

    /**
     * Utility function to calculate the Taxes
     *
     * @param taxes
     * @return
     */
    private float getTaxes(List<Tax> taxes) {
        float tax = 0;
        for (final Tax taxElms : taxes) {
            tax += taxElms.getValue();
        }
        return tax;
    }

    /**
     * Utility function to generate the Transaction.Result corresponding to the parameters given.
     *
     * @param inventory
     * @param inventoryTicker
     * @param nightsBI
     * @param checkInGregorianCalendar
     * @param hotelTicker
     * @param locale
     * @param notAvailable
     * @param defaultCurrency
     * @param totalRate
     * @param tax
     * @return
     * @throws DBAccessException
     */
    private Transaction.Result getResult(Inventory inventory, final String inventoryTicker, final BigInteger nightsBI,
                                         final XMLGregorianCalendar checkInGregorianCalendar, final String hotelTicker,
                                         final String locale, final boolean notAvailable,
                                         final String defaultCurrency, final float totalRate, final float tax)
            throws DBAccessException {
        Transaction.Result result = new Transaction.Result();
        result.setNights(nightsBI);
        result.setCheckin(checkInGregorianCalendar);
        result.setProperty(hotelTicker);
        result.setRoomID(inventoryTicker);
        result.setChargeCurrency(getChargeType(inventory.getCondition()));
        result.addRoomBundle(getRoomPriceDataType(inventory, locale, inventoryTicker, hotelTicker));
        if (notAvailable) {
            result.setUnavailable();
        } else {
            final float price = totalRate * (1 - tax);
            final float taxes = totalRate * tax;
            result.setBaserate(new BaseRateType(price, defaultCurrency));
            result.setTax(new PriceCurrencyType(taxes, defaultCurrency));
        }
        return result;
    }

    /**
     * Utility function to define the charge condition of a inventory line.
     * Could be, online 100% at the check-in moment or mixed.
     *
     * @param condition
     * @return
     */
    private ChargeCurrencyType getChargeType(final Condition condition) {
        ChargeCurrencyType ret = ChargeCurrencyType.HOTEL;
        try {
            final Float percent = condition.getEarlyCharge();
            final Float minimumCharge = condition.getMinimumCharge();
            if (percent == 100) {
                ret = ChargeCurrencyType.WEB;
            } else if (percent != 0 || minimumCharge != 0) {
                ret = ChargeCurrencyType.DEPOSIT;
            }
        } catch (Exception e) {
            logger.error(e);
        }
        return ret;
    }

    /**
     * Generate the HotelInfoFeed for all hotels.
     *
     * @return
     * @throws MiddlewareException
     */
    public List<HotelInfoFeed> getHotelInfoFeed() throws MiddlewareException {
        DBConnection dbConnectionWitMetadata = null;
        List<HotelInfoFeed> hotelsInfoFeed = new ArrayList<HotelInfoFeed>();
        try {
            final WitMetaDataDBHandler witMetaDataDBHandler = new WitMetaDataDBHandler();
            dbConnectionWitMetadata = witMetaDataDBHandler.getDbConnection();
            //Iterate for every hotel in production.
            for (final String hotelTicker : witMetaDataDBHandler.getHotelListProduction()) {
                hotelsInfoFeed.add(getHotelInfoFeed(hotelTicker));
            }
        } finally {
            DAOUtil.close(dbConnectionWitMetadata);
        }
        return hotelsInfoFeed;
    }

    /**
     * Generate the HotelInfoFeed for the hotelTicker given.
     *
     * @param hotelTicker
     * @return
     * @throws MiddlewareException
     */
    public HotelInfoFeed getHotelInfoFeed(final String hotelTicker) throws MiddlewareException {
        HotelInfoFeed hotelInfoFeed = new HotelInfoFeed(hotelTicker);
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(hotelTicker);
            final HotelConfigurationDBHandler hotelConfigurationDBHandler =
                    new HotelConfigurationDBHandler(dbConnection);
            final Language defaultLanguage = hotelConfigurationDBHandler.getDefaultLanguage();
            final String locale = defaultLanguage != null ? defaultLanguage.getLocale() : null;
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, locale);
            for (final Inventory inventory : inventoryDBHandler.getInventoriesActives()) {
                final String inventoryTicker = inventory.getTicker();
                hotelInfoFeed.addProperty(new HotelInfoFeed.Property(inventoryTicker, getRoomPriceDataType(inventory, locale, inventoryTicker, hotelTicker)));
            }
        } finally {
            DAOUtil.close(dbConnection);
        }
        return hotelInfoFeed;
    }

    /**
     * Utility function to the information of the room of the inventory.
     *
     * @param inventory
     * @param locale
     * @param inventoryTicker
     * @param hotelTicker
     * @return
     */
    private RoomPriceDataType getRoomPriceDataType(Inventory inventory, String locale, final String inventoryTicker, final String hotelTicker) {
        final Accommodation accommodation = inventory.getAccommodation();
        final Condition condition = inventory.getCondition();
        final Configuration configuration = inventory.getConfiguration();
        final RoomPriceDataType roomPriceDataType = new RoomPriceDataType();
        roomPriceDataType.setRefundable((condition.getEarlyCharge() == null || condition.getEarlyCharge() == 0)
                && (condition.getMinimumCharge() == null || condition.getMinimumCharge() == 0));
        final int occupancy = configuration.getAdults() + configuration.getChildren() + configuration.getInfants();
        roomPriceDataType.setOccupancy(occupancy);
        //Getting all url images related with this accommodation.
        for (final Media media : accommodation.getMedia()) {
            roomPriceDataType.addPhotoUrl(
                    new PhotoUrlType(locale, media.getFullTitleWithDescription(),
                            media.getUrlPhoto(hotelTicker, inventory.getId()))
            );
        }
        roomPriceDataType.setRoomID(inventoryTicker);
        roomPriceDataType.setRatePlanID(inventoryTicker);
        roomPriceDataType.setBreakfastIncluded(inventory.getMealPlan().hasBreakfast());
        roomPriceDataType.setName(new LocalizedText(locale, inventory.getAccommodationName()));
        roomPriceDataType.setDescription(new LocalizedText(locale, inventory.getFullName()));
        return roomPriceDataType;
    }

    /**
     * Share the currency exchange store in our system. Give the exchange of all currency defined in our system to USD.
     *
     * @return
     * @throws MiddlewareException
     */
    public ExchangeRates getCurrencyExchange() throws MiddlewareException {
        ExchangeRates exchangeRates = new ExchangeRates();
        DBConnection dbConnection = null;
        try {
            CurrencyDBHandler currencyDBHandler = new CurrencyDBHandler();
            final CurrencyExchange currencyExchange = currencyDBHandler.getCurrencyExchange("USD");
            if (currencyExchange != null && currencyExchange.getPrices() != null) {
                final Map<String, Float> prices = currencyExchange.getPrices();
                for (final Map.Entry<String, Float> entry : prices.entrySet()) {
                    final String key = entry.getKey();
                    final Float value = entry.getValue();
                    if (value != null && key != null && !key.isEmpty())
                        exchangeRates.getRate().add(new ExchangeRates.Rate(key, value));
                }
            }
        } finally {
            DAOUtil.close(dbConnection);
        }
        return exchangeRates;
    }

    /**
     * Share the Bid of all hotelTicker given.
     *
     * @param hotelTickers
     * @return
     * @throws MiddlewareException
     */
    public Bids getBid(List<String> hotelTickers) throws MiddlewareException {
        final Bids bids = new Bids();
        DBConnection dbConnectionWitMetadata = null;
        try {
            final WitMetaDataDBHandler witMetaDataDBHandler = new WitMetaDataDBHandler();
            dbConnectionWitMetadata = witMetaDataDBHandler.getDbConnection();
            for (Bid bid : witMetaDataDBHandler.getGoogleBid(hotelTickers)) {
                bids.getBid().add(transformBidToGoogleBid(bid, null));
            }
        } finally {
            DAOUtil.close(dbConnectionWitMetadata);

        }
        return bids;
    }

    /**
     * Share the Bid of all hotels.
     *
     * @return
     * @throws MiddlewareException
     */
    public Bids getBid() throws MiddlewareException {
        final Bids bids = new Bids();
        DBConnection dbConnectionWitMetadata = null;
        try {
            final WitMetaDataDBHandler witMetaDataDBHandler = new WitMetaDataDBHandler();
            dbConnectionWitMetadata = witMetaDataDBHandler.getDbConnection();
            for (final String hotelTicker : witMetaDataDBHandler.getHotelListProduction()) {
                Bids bids1 = getBid(hotelTicker);
                bids.getBid().addAll(bids1.getBid());
            }
        } finally {
            DAOUtil.close(dbConnectionWitMetadata);

        }
        return bids;
    }

    /**
     * Public the bid price for the hotelTicker given.
     *
     * @param hotelTicker
     * @return
     * @throws MiddlewareException
     */
    public Bids getBid(final String hotelTicker) throws MiddlewareException {
        final Bids bids = new Bids();
        DBConnection dbConnection = null;
        DBConnection dbConnectionWitmetadata = null;
        try {
            final WitMetaDataDBHandler witMetaDataDBHandler = new WitMetaDataDBHandler();
            dbConnectionWitmetadata = witMetaDataDBHandler.getDbConnection();
            //Find the information and given back in a Witbooking Bid Object.
            final Bid googleBid = witMetaDataDBHandler.getGoogleBid(hotelTicker);
            Bids.Bid bidAdd;
            //Find if is active to connect to the hotel DDBB and find the currency, otherwise don't need to connect to the DDBB.
            if (googleBid != null && googleBid.isActive()) {
                try {
                    //Settings DDBB connections
                    dbConnection = new DBConnection(hotelTicker);
                    final HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
                    bidAdd = transformBidToGoogleBid(googleBid, hotelConfigurationDBHandler.getDefaultCurrency());
                } catch (Exception ex) {
                    logger.error(ex);
                    bidAdd = transformBidToGoogleBid(googleBid, null);
                }
            } else {
                bidAdd = transformBidToGoogleBid(googleBid, null);
            }
            //Add the bid to the Bids object wrapper.
            bids.addBid(bidAdd);
        } catch (Exception e) {
            logger.error(e);
        } finally {
            DAOUtil.close(dbConnection);
            DAOUtil.close(dbConnectionWitmetadata);
        }
        return bids;
    }

    /**
     * Utility function make to convert Witbooking Bid Object to Google Bid Object.
     *
     * @param customBid
     * @param currency
     * @return
     */
    public Bids.Bid transformBidToGoogleBid(final Bid customBid, final String currency) {
        return (customBid != null && customBid.isActive())
                ? new Bids.Bid(customBid.getHotelTicker(), Bidcurrencytype.Type.PERCENTAGE, currency, customBid.getBid() + "")
                : new Bids.Bid(customBid.getHotelTicker(), Bidcurrencytype.Type.FIXED, null, 0 + "");
    }

    /**
     * Public the configuration file to indicate to Google where our URL points are.
     *
     * @return
     */
    public com.witbooking.middleware.integration.google.model.hpa.Configuration getConfiguration() {
        final com.witbooking.middleware.integration.google.model.hpa.Configuration configuration = new com.witbooking.middleware.integration.google.model.hpa.Configuration();
        configuration.setConfigurationURL("https://www.witbooking.com/WitBookerAPI/Google/configuration");
        configuration.setExchangeRatesURL("https://www.witbooking.com/WitBookerAPI/Google/exchangeRates");
        configuration.setQueryControlURL("https://www.witbooking.com/WitBookerAPI/Google/queryControl");
        configuration.setSimultaneousThreads(BigInteger.ONE);
        return configuration;
    }

}
