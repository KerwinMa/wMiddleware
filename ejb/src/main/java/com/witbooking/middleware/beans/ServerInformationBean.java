package com.witbooking.middleware.beans;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.microtripit.mandrillapp.lutung.view.MandrillMessageStatus;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.*;
import com.witbooking.middleware.exceptions.*;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.integration.mandrill.model.Event;
import com.witbooking.middleware.integration.mandrill.model.Message;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.Currency;
import com.witbooking.middleware.model.dynamicPriceVariation.*;
import com.witbooking.middleware.model.values.EnumDataValueType;
import com.witbooking.middleware.model.values.HashRangeValue;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.EmailsUtils;
import com.witbooking.middleware.utils.JsonUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.io.File;
import java.util.*;

/**
 * ServerInformationBean.java
 * User: jose
 * Date: 12/13/13
 * Time: 6:57 PM
 */
@Stateless
public class ServerInformationBean implements ServerInformationBeanLocal, ServerInformationBeanRemote {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(ServerInformationBean.class);
    @EJB
    private ConnectionBeanLocal connectionBeanLocal;
    @EJB
    private IntegrationBeanLocal integrationBeanLocal;

    @EJB
    private BookingPriceRuleBeanLocal bookingPriceRuleBean;

    public static final String EMAIL_AVAILABILITY_MSG = "emailAvisoDispo";
    public static final String LIMIT_AVAILABILITY_MSG = "limiteAvisoDisponibilidad";

    public Establishment getEstablishment(String hotelTicker, String locale) throws FrontEndException {
        Establishment establishment;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(new DBConnection(dbCredential), locale);
            establishment = inventoryDBHandler.getHotel();
            inventoryDBHandler.closeDbConnection();
            return establishment;
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        }
    }

    public List<Accommodation> getAccommodations(String hotelTicker, String locale) throws FrontEndException {
        List<Accommodation> accommodationList;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(new DBConnection(dbCredential), locale);
            accommodationList = inventoryDBHandler.getAccommodationsActives();
            inventoryDBHandler.closeDbConnection();
            return accommodationList;
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        }
    }

    public List<Discount> getDiscounts(String hotelTicker, String locale) throws FrontEndException {
        List<Discount> discountList;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(new DBConnection(dbCredential), locale);
            discountList = inventoryDBHandler.getDiscountsActives();
            inventoryDBHandler.closeDbConnection();
            return discountList;
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        }
    }

    public List<Service> getServices(String hotelTicker, String locale) throws FrontEndException {
        List<Service> serviceList;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(new DBConnection(dbCredential), locale);
            serviceList = inventoryDBHandler.getServicesActives();
            inventoryDBHandler.closeDbConnection();
            return serviceList;
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        }
    }

    public Map<String, Object> getQueryExecutionTimes(final String ticker) {
        Date start = new Date();
        Date end = new Date();
        DateUtil.incrementDays(end, 365);
        /*TODO: DO NOT HARDCODE PARAMS, GET FROM GETPARAMS*/
        List<Map<String, Object>> qperfList = new ArrayList<>();
        long time;
        Map<String, Object> finalResult = new HashMap<>();
        class CustomComparator implements Comparator<Map> {
            @Override
            public int compare(Map o1, Map o2) {
//                int res = ((Long) o2.get("counter")).compareTo((Long) o1.get("counter"));
//                if (res == 0) {
//                    res = ((Long) o2.get("totaltime")).compareTo((Long) o1.get("totaltime"));
//                }
//                return res;
                return ((Long) o2.get("totaltime")).compareTo((Long) o1.get("totaltime"));
            }
        }
        try {
            long startT;
            long endT;
            DBConnection.setMUST_PROFILE(true);
            startT = System.nanoTime();
            getARI(ticker, null, start, end, "EUR", null, null);
            endT = System.nanoTime();
            Map<String, DBConnection> dbConnectionMap = DBConnection.getDbConnectionsProfiled();
            DBConnection.setMUST_PROFILE(false);
            time = (endT - startT);
//            time= TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);

            for (String keyDBConnection : dbConnectionMap.keySet()) {
                long totalQueryTime = 0;
                int totalQueries = 0;
                DBConnection dbConnection = dbConnectionMap.get(keyDBConnection);
                Map<String, Map<String, Long>> queryCounter = dbConnection.queryCounter;
                Iterator it = queryCounter.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pairs = (Map.Entry) it.next();
                    String querySQL = (String) pairs.getKey();
                    Map<String, Long> queryData = (Map<String, Long>) pairs.getValue();

                    Map<String, Object> queryPerfData = new HashMap<>();
                    queryPerfData.putAll(queryData);

                    totalQueries += queryData.get("counter");
                    totalQueryTime += queryData.get("totaltime");

                    queryPerfData.put("sql", querySQL);
                    qperfList.add(queryPerfData);
                    it.remove(); // avoids a ConcurrentModificationException
                }
                Collections.sort(qperfList, new CustomComparator());
                Map<String, Object> result = new HashMap<>();
                result.put("queries", qperfList);
                result.put("totalQueries", totalQueries);
                result.put("totalQueryTime", totalQueryTime);
                result.put("totalARITime", time);
                finalResult.put(keyDBConnection, result);
            }
        } catch (FrontEndException e) {
            e.printStackTrace();
        }
        return finalResult;

    }

    public List<HashRangeValue> getARI(final String hotelTicker, List<String> inventoryTickers,
                                       final Date start, final Date end,
                                       final String currency, final String promotionalCode, final String country) throws FrontEndException {
        List<HashRangeValue> rangeValues = new ArrayList<>();
        DBConnection dbConnection = null;
        if (start == null || end == null || hotelTicker == null || hotelTicker.trim().isEmpty()) {
            return rangeValues;
        }
        try {
            dbConnection = new DBConnection(hotelTicker);
            final Date endDate = (Date) end.clone();
            //I don't calculate the ARI values for the check out day
            DateUtil.incrementDays(endDate, -1);
            InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection, false);
            final DailyValuesDBHandler dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, start, endDate);

            List<Discount> discountsActives = inventoryDBHandler.getDiscountsActives();
            List<Service> servicesActives = inventoryDBHandler.getServicesActives();

            if (inventoryTickers == null || inventoryTickers.isEmpty()) {
                inventoryTickers = new ArrayList<>();
                for (final Inventory inventory : inventoryDBHandler.getInventoriesValid()) {
                    inventoryTickers.add(inventory.getTicker());
                }
                dailyValuesDBHandler.getInventoryValuesBetweenDates();
            } else {
                dailyValuesDBHandler.getSelectedInventoryValuesBetweenDates(inventoryTickers);
            }

            for (final Service serviceActive : servicesActives) {
                String serviceTicker = serviceActive.getTicker();
                if (serviceTicker != null && !serviceTicker.isEmpty()) {
                    HashRangeValue hashRangeValue = new HashRangeValue(serviceTicker);
                    if (EnumDataValueType.OWN.equals(serviceActive.getLock().getValueType())) {
                        hashRangeValue.putRangeValues(HashRangeValue.LOCK, dailyValuesDBHandler.getLockByTicker(serviceTicker));
                    }
                    if (EnumDataValueType.OWN.equals(serviceActive.getRate().getValueType())) {
                        hashRangeValue.putRangeValues(HashRangeValue.RATE, dailyValuesDBHandler.getRatesByTicker(serviceTicker));
                    }
                    if (!hashRangeValue.getHashRangeValues().isEmpty()) {
                        rangeValues.add(hashRangeValue);
                    }
                }
            }
            for (final Discount discountActive : discountsActives) {
                String discountTicker = discountActive.getTicker();
                if (discountTicker != null && !discountTicker.isEmpty()) {
                    HashRangeValue hashRangeValue = new HashRangeValue(discountTicker);
                    hashRangeValue.putRangeValues(HashRangeValue.LOCK, dailyValuesDBHandler.getLockByTicker(discountTicker));
                    rangeValues.add(hashRangeValue);

                }
            }
            for (final String inventoryTicker : inventoryTickers) {
                Inventory inventory = inventoryDBHandler.getInventoryByTicker(inventoryTicker);
                if (inventory != null && inventory.isValid()) {
                    HashRangeValue hashRangeValue = new HashRangeValue(inventoryTicker);
                    hashRangeValue.putRangeValues(HashRangeValue.RATE, dailyValuesDBHandler.getFullRatesByTicker(inventoryTicker, currency, promotionalCode));
                    hashRangeValue.putRangeValues(HashRangeValue.ACTUAL_AVAILABILITY, dailyValuesDBHandler.getAvailabilityByTicker(inventoryTicker));
                    hashRangeValue.putRangeValues(HashRangeValue.LOCK, dailyValuesDBHandler.getLockByTicker(inventoryTicker));
                    hashRangeValue.putRangeValues(HashRangeValue.MIN_STAY, dailyValuesDBHandler.getMinStayByTicker(inventoryTicker));
                    hashRangeValue.putRangeValues(HashRangeValue.MAX_STAY, dailyValuesDBHandler.getMaxStayByTicker(inventoryTicker));
                    hashRangeValue.putRangeValues(HashRangeValue.MIN_NOTICE, dailyValuesDBHandler.getMinNoticeByTicker(inventoryTicker));
                    hashRangeValue.putRangeValues(HashRangeValue.MAX_NOTICE, dailyValuesDBHandler.getMaxNoticeByTicker(inventoryTicker));
                    hashRangeValue.putRangeValues(HashRangeValue.DISCOUNTS_APPLIED, dailyValuesDBHandler.getFinalDiscountApplied(inventoryTicker, currency, null, promotionalCode));
                    rangeValues.add(hashRangeValue);
                }
            }
            boolean rulesActive = true;
            if (rulesActive) {
                try {
                    rangeValues = applyRules(hotelTicker, start, end, inventoryDBHandler, inventoryTickers, country, promotionalCode, rangeValues);
                }catch (Exception ex){
                    logger.error("Error Applying Rules "+ex.getMessage());
                    ex.printStackTrace();
                }
            }

        } catch (MiddlewareException e) {
            logger.debug(e);
            throw new FrontEndException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
        return rangeValues;
    }

    private List<HashRangeValue> applyRules(final String hotelTicker,
                                            final Date start,
                                            final Date end,
                                            final InventoryDBHandler inventoryDBHandler,
                                            final List<String> inventoryTickers,
                                            final String country,
                                            final String promotionalCode,
                                            final List<HashRangeValue> ARIrangeValues) throws FrontEndException {

        try {
            List<BookingPriceRule> bookingPriceRules = bookingPriceRuleBean.getRules(hotelTicker);
            if (bookingPriceRules.isEmpty()) {
                return ARIrangeValues;
            }
            Map arguments = new HashMap();

            DateTime currentDateTimeUTC = new DateTime(DateTimeZone.UTC);
            DateTime startDateTimeUTC = (new DateTime(start)).withZoneRetainFields(DateTimeZone.UTC);

            WeekDayCondition.WeekDay.getFromValue(currentDateTimeUTC.getDayOfWeek());
            arguments.put(DatetimeCondition.ARGUMENT_CURRENT_TIME, new LocalTime().toDateTimeToday().withZone(DateTimeZone.UTC));
            arguments.put(WeekDayCondition.ARGUMENT_WEEK_DAY, WeekDayCondition.WeekDay.getFromValue(startDateTimeUTC.getDayOfWeek()));
            arguments.put(CountryOfOriginCondition.ARGUMENT_COUNTRY, country);
            arguments.put(DatetimeRangeCondition.ARGUMENT_COMPARISON_DATE, startDateTimeUTC);
            arguments.put(CodeCondition.ARGUMENT_CODE, promotionalCode);

            Map<Long, Boolean> oneTimeEvaluationRulesCache = new HashMap<Long, Boolean>();
            Map<String, List<BookingPriceRule>> validRulesForTicker = new HashMap<String, List<BookingPriceRule>>();

            for (BookingPriceRule bookingPriceRule : bookingPriceRules) {
                Boolean bookingPriceRuleEvaluation = null;
                if (!oneTimeEvaluationRulesCache.containsKey(bookingPriceRule.getId())) {
                    bookingPriceRuleEvaluation = bookingPriceRule.evaluate(arguments);
                    oneTimeEvaluationRulesCache.put(bookingPriceRule.getId(), bookingPriceRuleEvaluation);
                } else {
                    bookingPriceRuleEvaluation = oneTimeEvaluationRulesCache.get(bookingPriceRule.getId());
                }
            }

            //Inventory inventory = inventoryDBHandler.getInventoryByTicker(inventoryTicker);
            /*TODO: THE ONLY EVALUATION THAT MUST OCCUR BY EACH TICKER IS THE TICKER CONDITION EVAL, THAT MEANS ALL OTHER CONDITIONS OF A BOOKING RULE CAN BE EVALUATED BEFOREHAND OR ONLY ONCE  */

            for (final String inventoryTicker : inventoryTickers) {
                Boolean bookingPriceRuleEvaluation = null;
                for (BookingPriceRule bookingPriceRule : bookingPriceRules) {
                    TickerCondition tickerCondition = bookingPriceRule.getTickerCondition();
                    arguments.put(TickerCondition.ARGUMENT_TICKER, inventoryTicker);
                    if (!tickerCondition.evaluate(arguments)) {
                        continue;
                    }
                    if (!oneTimeEvaluationRulesCache.containsKey(bookingPriceRule.getId())) {
                        bookingPriceRuleEvaluation = bookingPriceRule.evaluate(arguments);
                        oneTimeEvaluationRulesCache.put(bookingPriceRule.getId(), bookingPriceRuleEvaluation);
                    } else {
                        bookingPriceRuleEvaluation = oneTimeEvaluationRulesCache.get(bookingPriceRule.getId());
                    }
                    if (bookingPriceRuleEvaluation) {
                        if (!validRulesForTicker.containsKey(inventoryTicker)) {
                            validRulesForTicker.put(inventoryTicker, new ArrayList<BookingPriceRule>());
                        }
                        ;
                        validRulesForTicker.get(inventoryTicker).add(bookingPriceRule);
                    }
                }
            }

            Map<String, Map<String, Object>> variationForTicker = new HashMap<>();

            for (Map.Entry<String, List<BookingPriceRule>> entry : validRulesForTicker.entrySet()) {
                String inventoryTicker = entry.getKey();
                List<BookingPriceRule> validRules = entry.getValue();
                Map<String, Object> variation = calculateVariationForBookingPriceRules(validRules);
                variationForTicker.put(inventoryTicker, variation);
            }

            for (HashRangeValue hashRangeValue : ARIrangeValues) {
                Map<String, Object> variation = variationForTicker.get(hashRangeValue.getTicker());
                if (variation == null) {
                    continue;
                }
                Double numericVariation = (Double) variation.get("variation");
                Boolean isPercentage = (Boolean) variation.get("isPercentage");
                hashRangeValue.getHashRangeValues().get(HashRangeValue.RATE).applyVariation(numericVariation, isPercentage);
            }

        } catch (BookingPriceRuleException e) {
            logger.debug(e);
            //throw new FrontEndException(e);
        }

        return ARIrangeValues;
    }

    private Map<String, Object> calculateVariationForBookingPriceRules(List<BookingPriceRule> bookingPriceRules) {
        /*TODO: Implement stackable rules case*/
        Double variation = 1.0;
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("variation", variation);
        result.put("isPercentage", true);

        if (!bookingPriceRules.isEmpty()) {
            BookingPriceRule bookingPriceRule = Collections.max(bookingPriceRules);
            variation = Collections.max(bookingPriceRules).getPriceVariation();
            result.put("variation", bookingPriceRule.getPriceVariation());
            result.put("isPercentage", bookingPriceRule.isPercentage());
        }

        return result;
    }

    public List<Language> getLanguages(String hotelTicker) throws FrontEndException {
        List<Language> languageList;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(new DBConnection(dbCredential));
            languageList = hotelConfigurationDBHandler.getActiveLanguages();
            hotelConfigurationDBHandler.closeDbConnection();
            return languageList;
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        }
    }

    public List<LocationPoint> getLocationPoints(String hotelTicker, String locale) throws FrontEndException {
        WitHotelDBHandler handler = null;
        List<LocationPoint> locations = null;
        try {
            handler = new WitHotelDBHandler();
            locations = handler.getLocationPointsByTicker(hotelTicker, locale);
        } catch (MiddlewareException ex) {
            throw new FrontEndException(ex);
        } finally {
            closeHandler(handler);
        }
        return locations;
    }

    public List<Review> getReviews(String hotelTicker, String locale) throws FrontEndException {
        WitHotelDBHandler handler = null;
        List<Review> reviews = null;
        try {
            handler = new WitHotelDBHandler();
            reviews = handler.getReviewByTicker(hotelTicker, locale);
        } catch (MiddlewareException ex) {
            throw new FrontEndException(ex);
        } finally {
            closeHandler(handler);
        }
        return reviews;
    }

    public boolean subscribeNewsletter(String hotelTicker, String email, String language) throws FrontEndException {
        logger.debug("subscribeNewsletter: ['" + hotelTicker + "', '" + email + "', '" + language + "']");
        HotelConfigurationDBHandler hotelConfigurationDBHandler = null;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            hotelConfigurationDBHandler = new HotelConfigurationDBHandler(new DBConnection(dbCredential));
            NewsletterSubscription newsletterSubscription = new NewsletterSubscription(null, email, language, true);
            if (hotelConfigurationDBHandler.insertNewsletterSubscriptions(newsletterSubscription) > 0) {
                hotelConfigurationDBHandler.closeDbConnection();
                return true;
            } else {
                hotelConfigurationDBHandler.closeDbConnection();
            }
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
//            throw new FrontEndException(ex);
        } finally {
            closeHandler(hotelConfigurationDBHandler);
        }
        return false;
    }

    public List<Currency> getCurrencies(String hotelTicker) throws FrontEndException {
        List<Currency> currencies;
        try {
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(new DBConnection(dbCredential));
            currencies = hotelConfigurationDBHandler.getActivesCurrencies();
            hotelConfigurationDBHandler.closeDbConnection();
            return currencies;
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        }
    }

    public WitHotel getHotel(String hotelTicker) throws FrontEndException {
        WitHotelDBHandler handler = null;
        try {
            handler = new WitHotelDBHandler();
            return handler.getHotelByTicker(hotelTicker);
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        } finally {
            closeHandler(handler);
        }
    }

    private Properties getAllConfigurations(String hotelTicker) throws FrontEndException {
        WitHotelDBHandler witHotelDBHandler = null;
        try {
            witHotelDBHandler = new WitHotelDBHandler();
            return witHotelDBHandler.getConfigurationsByTicker(hotelTicker);
        } catch (MiddlewareException ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        } finally {
            closeHandler(witHotelDBHandler);
        }
    }

    public Properties getConfigurations(String hotelTicker) throws FrontEndException {
        HotelConfigurationDBHandler hotelConfigurationDBHandler = null;
        try {
            Properties props = getAllConfigurations(hotelTicker);

            //Properties from Hotel's DataBase
            DBCredentials dbCredential = getDBCredentials(hotelTicker);
            hotelConfigurationDBHandler = new HotelConfigurationDBHandler(new DBConnection(dbCredential));

            List<String> list = new ArrayList<>();
            list.add("fltr_tipo_ocupacion");
            list.add("minedadnino");
            list.add("maxedadnino");
            list.add("fondoBotonesCuerpo2");
            list.add("googleanalyticscodenumber");
            list.add("defaultLanguage");
            list.add("colorFontForm");
            props.putAll(hotelConfigurationDBHandler.getHotelProperties(list));
            //TODO: rename fondoBotonesCuerpo2
            if (props.get("fondoBotonesCuerpo2") != null) {
                props.put("buttonSecondaryColor", props.get("fondoBotonesCuerpo2"));
                props.remove("fondoBotonesCuerpo2");
            }
            final String defaultCurrency = hotelConfigurationDBHandler.getDefaultCurrency();
            if (defaultCurrency != null) {
                props.put("defaultCurrency", defaultCurrency);
            }
            final String urlHotel = hotelConfigurationDBHandler.getWebHotelURL();
            if (urlHotel != null) {
                props.put("urlwebhotel", urlHotel);
            }
            props.putAll(hotelConfigurationDBHandler.getWitBookerAppearanceProperties());
            props.putAll(hotelConfigurationDBHandler.getMaxPersons());

            return props;
        } catch (MiddlewareException ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        } finally {
            closeHandler(hotelConfigurationDBHandler);
        }
    }

    public List<String> getLocationTypes() throws FrontEndException {
        WitHotelDBHandler handler = null;
        try {
            handler = new WitHotelDBHandler();
            return handler.getLocationsTypes();
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        } finally {
            closeHandler(handler);
        }
    }

    public String testFrontEndServices() {
        try {
            logger.debug(" CUSTOMERS_DB_FILE:" + MiddlewareProperties.CUSTOMERS_DB_FILE);
            File filePath = new File("PATH_FILE");
            logger.debug("file: " + filePath.getAbsolutePath());
        } catch (Throwable e) {
            File file = new File(MiddlewareProperties.CONFIG_FILE);
            return "Error loading the Properties File. Read:" + file.canRead() + ". File Path Required: " + file.getAbsolutePath() + "";
        }
        return "ALL IT'S OK!";
    }

    public Map<String, Establishment> getWitHotelVisualRepresentation(final String hotelTicker,
                                                                      final List<String> propertyNames) throws FrontEndException {
        if (hotelTicker == null)
            throw new NullPointerException("hotelTickers can not be null.");
        Map<String, Establishment> hotelsInfo = new HashMap<>();
        DBConnection dbConnection = null;
        HotelConfigurationDBHandler hotelConfigurationDBHandler;
        try {
            dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
            List<Currency> currencies = null;
            List<Language> languages = null;
            Properties properties = getConfigurations(hotelTicker);
            for (final Language language : hotelConfigurationDBHandler.getActiveLanguages()) {
                if (language != null && language.getLocale() != null) {
                    // If i get currencies and languages stored on currencies and languages
                    // variables, i don't find those values again.
                    final Hotel hotel = hotelConfigurationDBHandler.getWitHotelVisualRepresentation(hotelTicker, propertyNames, language.getLocale(), currencies, languages, properties);
                    // Storing currencies and languages to avoid find it again on database.
                    // Those values are the same for every language and in this for we are iterating over the languages.
                    currencies = hotel.getCurrencies();
                    languages = hotel.getLanguages();
                    hotelsInfo.put(language.getLocale(), hotel);
                }
            }
        } catch (MiddlewareException e) {
            logger.error(e);
            throw new FrontEndException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
        return hotelsInfo;
    }

    public Reservation insertReservation(final String hotelTicker, Reservation reservation) throws FrontEndException {
        logger.debug("New reservation for the Hotel '" + hotelTicker + "'");
        if (hotelTicker == null)
            throw new NullPointerException("hotelTickers can not be null.");
        if (reservation == null)
            throw new NullPointerException("Reservation can not be null.");
        if (reservation.getRoomStays() == null)
            throw new NullPointerException("RoomStays can not be null.");
        DBConnection dbConnection = null;
        InventoryDBHandler inventoryDBHandler;
        ReservationDBHandler reservationDBHandler;
        Reservation newReservation = null;
        try {
            /* Encrypt Credit Card Info */
            CreditCard creditCard = reservation.getCustomer().getCreditCard();
            creditCard.setCardNumberEncrypted(connectionBeanLocal.encryptionPHP(ConnectionBeanLocal.ENCRYPT, creditCard.getCardNumber() + ""));
            creditCard.setExpireDateEncrypted(connectionBeanLocal.encryptionPHP(ConnectionBeanLocal.ENCRYPT, creditCard.getExpireDateString() + ""));
            creditCard.setSeriesCodeEncrypted(connectionBeanLocal.encryptionPHP(ConnectionBeanLocal.ENCRYPT, creditCard.getSeriesCode() + ""));
            dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            inventoryDBHandler = new InventoryDBHandler(dbConnection);
            reservationDBHandler = new ReservationDBHandler(inventoryDBHandler);
            // generate the confirmation codes
            String reservationId = null;
            Map<String, String> mapCodes;
            for (RoomStay roomStay : reservation.getRoomStays()) {
                String roomStayId = null;
                while (roomStayId == null || reservationId == null) {
                    if (roomStayId == null)
                        roomStayId = "P" + Reservation.generateRandomCode();
                    if (reservationId == null)
                        reservationId = "M" + Reservation.generateRandomCode();
                    mapCodes = reservationDBHandler.getReservationCodes(reservationId, roomStayId);
                    roomStayId = mapCodes.get("roomStayId");
                    reservationId = mapCodes.get("reservationId");
                }
                //adding the confirmation codes
                roomStay.setIdConfirmation(roomStayId);
            }
            //adding the confirmation codes
            reservation.setReservationId(reservationId);

            List<Reservation> reservationList = new ArrayList<>();
            reservationList.add(reservation);
            //inserting the reservation into the hotel's DB
            if (reservationDBHandler.insertReservations(reservationList) < 1) {
                logger.error("Empty Reservation. hotel:'" + hotelTicker + "' Exception:" + reservation);
                throw new FrontEndException("Empty Reservation. hotel:'" + hotelTicker);
            }
            newReservation = reservationDBHandler.getReservationByReservationId(reservationId);
            logger.debug("Added the reservation for the Hotel '" + hotelTicker + "' id:'" + newReservation.getReservationId() + "'");

            //Sending Confirmation Email, just if the Reservation is Confirmed
            if (newReservation.getStatus() == Reservation.ReservationStatus.RESERVATION) {
                String responseEmails = "";
                String clientEmailStatus = "";
                try {
                    responseEmails = connectionBeanLocal.sendConfirmationEmail(hotelTicker, newReservation.getReservationId());
                    List<MandrillMessageStatus> messageStatusReports = JsonUtils.gsonInstance().fromJson(responseEmails, new TypeToken<ArrayList<MandrillMessageStatus>>() {
                    }.getType());
                    for (MandrillMessageStatus mandrillMessageStatus : messageStatusReports) {
                        if (mandrillMessageStatus.getEmail().equals(reservation.getCustomer().getEmail())) {
                            clientEmailStatus = mandrillMessageStatus.getStatus();
                        }
                    }
                    Event.MandrillMessageEventType messageEventType = Event.MandrillMessageEventType.getFromValue(clientEmailStatus);
                    if (responseEmails == null) {
                        throw new RemoteServiceException("Error in Service Sending the Email. Response Email is NULL. ");
                    }
                    /*TODO: THIS SHOULD RUN IN A PARALLEL THREAD SAVE EMAIL DATA*/
                    saveReservationEmailData(responseEmails, reservation, hotelTicker, reservationDBHandler);
                    if (messageEventType != Event.MandrillMessageEventType.SENT &&
                            messageEventType != Event.MandrillMessageEventType.QUEUED) {
                        throw new RemoteServiceException("Error in Service Sending the Email.");
                    }
                } catch (Exception ex) {
                    logger.error("Error Sending the Email. hotel:'" + hotelTicker + "' Exception:" + ex);
                    EmailsUtils.sendEmailToAdmins("Error sending confirmation email for: " + hotelTicker,
                            "Error sending confirmation email for hotel: '" + hotelTicker +
                                    "' <br/> Reservation: '" + newReservation.getReservationId() + "' <br/>" +
                                    " <br/> Error: '" + ex + "' <br/>" +
                                    " <br/> ResponsePHP: '" + responseEmails + "' <br/>" +
                                    " <br/>Please Verify and resend the Emails if is necessary!<br/>",
                            Arrays.asList("WitBookerAPI Errors", "Error Confirmation Emails"), ex);
                }
            }
            //Post Process for insert a new Reservation
            integrationBeanLocal.postReservationProcess(Arrays.asList(newReservation), inventoryDBHandler);
        } catch (MiddlewareException e) {
            logger.error("Error in new Reservation hotel:'" + hotelTicker + "' Exception:" + e);
            throw new FrontEndException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
        return newReservation;
    }

    private int saveReservationEmailData(String emailStatusInformation, Reservation reservation, String hotelTicker, ReservationDBHandler reservationDBHandler) {
        try {
            List<MandrillMessageStatus> messageStatusReports = JsonUtils.gsonInstance().fromJson(emailStatusInformation, new TypeToken<ArrayList<MandrillMessageStatus>>() {
            }.getType());

            EmailDataDBHandler emailDataDBHandler = null;
            emailDataDBHandler = new EmailDataDBHandler();
            EmailData clientEmailData = new EmailData();
            EmailData hotelEmailData = new EmailData();

            for (MandrillMessageStatus mandrillMessageStatus : messageStatusReports) {
                EmailData emailData = new EmailData();
                emailData.setLastEmailStatus(Event.EventType.convertFromMandrillMessageEventType(Event.MandrillMessageEventType.getFromValue(mandrillMessageStatus.getStatus())));
                emailData.setEmailID(mandrillMessageStatus.getId());
                emailData.setHotelTicker(hotelTicker);
                emailData.setReservationID(reservation.getReservationId());
                if (mandrillMessageStatus.getEmail().equals(reservation.getCustomer().getEmail())) {
                    emailData.setMessageType(Message.MessageType.USER_CONFIRMATION);
                    clientEmailData = emailData;
                } else {
                    emailData.setMessageType(Message.MessageType.HOTEL_CONFIRMATION);
                    hotelEmailData = emailData;
                }
                emailDataDBHandler.saveEmailData(emailData);
            }

            reservationDBHandler.updateReservationEmailInfo(clientEmailData, hotelEmailData);

        } catch (JsonSyntaxException | ExternalFileException | DBAccessException | NonexistentValueException e) {
            logger.error("Could not save reservation email Data" + e);
        }


        return 0;
    }

    public Map<String, Establishment> getWitBookerVisualRepresentation(final String ticker,
                                                                       final List<String> propertyNames)
            throws FrontEndException {
        if (ticker == null)
            throw new NullPointerException("hotelTickers can not be null.");
        logger.debug("ticker: " + ticker + " propertyNames: " + propertyNames);

        if (propertyNames == null || propertyNames.isEmpty())
            logger.error("Empty propertyNames in hotelTicker: " + ticker + " propertyNames: " + propertyNames + " Date: " +
                    new Date());
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        Boolean isChain = null;
        boolean isDemo = false;
        boolean hasTransfers = false;
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            isChain = witMetaDataDBHandler.checkIsChain(ticker);
            isDemo = witMetaDataDBHandler.checkIsDemo(ticker);
            hasTransfers = witMetaDataDBHandler.checkHasTransferData(ticker);
        } catch (MiddlewareException e) {
            logger.error(e);
        } finally {
            closeHandler(witMetaDataDBHandler);
        }
        if (isChain == null) {
            throw new FrontEndException("Error checking if ticker '" + ticker + "' given is chain of hotel.");
        } else if (isChain) {
            return getWitBookerChainVisualRepresentation(ticker, propertyNames, isDemo);
        } else {
            return getWitBookerHotelVisualRepresentation(ticker, propertyNames, isDemo, hasTransfers);
        }
    }

    private Map<String, Establishment> getWitBookerChainVisualRepresentation(final String ticker,
                                                                             final List<String> propertyNames, final boolean isDemoChain)
            throws FrontEndException {
        if (ticker == null)
            throw new NullPointerException("ticker given can not be null.");
        Map<String, Establishment> chainInfo = new HashMap<>();
        Map<String, List<Establishment>> hotelList = new HashMap<>();
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        DBConnection dbConnection = null;
        try {
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            dbConnection = new DBConnection(ticker);
            HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
            final List<Language> activeLanguages = hotelConfigurationDBHandler.getActiveLanguages();
            List<String> hotelTickersFromChain = witMetaDataDBHandler.getHotelTickersFromChain(ticker, null);
            for (final String hotelTicker : hotelTickersFromChain) {
                boolean isDemoHotel = witMetaDataDBHandler.checkIsDemo(hotelTicker);
                boolean hasTransfers = witMetaDataDBHandler.checkHasTransferData(ticker);
                for (final Language language : activeLanguages) {
                    final String languageLocale = language.getLocale();
                    if (languageLocale != null) {
                        List<Establishment> hotels;
                        if (hotelList.get(languageLocale) == null)
                            hotels = new ArrayList<>();
                        else
                            hotels = hotelList.get(languageLocale);
                        HotelConfigurationDBHandler hotelCDBHandler;
                        DBConnection dbConnection1 = null;
                        try {
                            dbConnection1 = new DBConnection(hotelTicker);
                            hotelCDBHandler = new HotelConfigurationDBHandler(dbConnection1);
                            TransferData transferData = null;
                            if (hasTransfers) {
                                transferData = witMetaDataDBHandler.getTransferData(ticker, languageLocale);
                            }
                            hotels.add(hotelCDBHandler.getWitBookerHotelVisualRepresentation(propertyNames,
                                    languageLocale, isDemoHotel, transferData));
                        } catch (MiddlewareException e) {
                            logger.error(e);
                        } finally {
                            DAOUtil.close(dbConnection1);
                        }
                        hotelList.put(languageLocale, hotels);
                    }
                }
            }
            for (String languageLocale : hotelList.keySet()) {
                chainInfo.put(languageLocale, hotelConfigurationDBHandler.getWitBookerChainVisualRepresentation
                        (propertyNames, languageLocale, hotelList.get(languageLocale), isDemoChain));
            }
        } catch (MiddlewareException e) {
            logger.error(e);
        } finally {
            closeHandler(witMetaDataDBHandler);
            DAOUtil.close(dbConnection);
        }
        return chainInfo;
    }

    private Map<String, Establishment> getWitBookerHotelVisualRepresentation(final String ticker, final List<String> propertyNames, final boolean isDemo, final boolean hasTransfers)
            throws FrontEndException {
        if (ticker == null)
            throw new NullPointerException("hotelTicker can not be null.");
        Map<String, Establishment> hotelsInfo = new HashMap<>();
        DBConnection dbConnection = null;
        HotelConfigurationDBHandler hotelConfigurationDBHandler;
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        try {
            dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(ticker));
            hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
            for (final Language language : hotelConfigurationDBHandler.getActiveLanguages()) {
                final String languageLocale = (language != null) ? language.getLocale() : null;
                if (languageLocale != null) {
                    TransferData transferData = null;
                    if (hasTransfers) {
                        if (witMetaDataDBHandler == null)
                            witMetaDataDBHandler = new WitMetaDataDBHandler();
                        transferData = witMetaDataDBHandler.getTransferData(ticker, languageLocale);
                    }
                    hotelsInfo.put(languageLocale, hotelConfigurationDBHandler.getWitBookerHotelVisualRepresentation
                            (propertyNames, languageLocale, isDemo, transferData));
                }
            }
        } catch (MiddlewareException e) {
            logger.error(e);
            throw new FrontEndException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
        return hotelsInfo;
    }

    private DBCredentials getDBCredentials(String hotelTicker) throws FrontEndException {
        try {
            return DBProperties.getDBCustomerByTicker(hotelTicker);
        } catch (MiddlewareException ex) {
            logger.error("Error getting the DBCredential for the Hotel '" + hotelTicker + "': " + ex.getMessage());
            throw new FrontEndException(ex);
        }
    }

    private void closeHandler(DBHandler handler) throws FrontEndException {
        try {
            if (handler != null) {
                handler.closeDbConnection();
            }
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            throw new FrontEndException(ex);
        }
    }

    public HotelMedia insertHotelMediaIfNotExists(final String hotelTicker, final String entity,
                                                  final Integer idEntity, final String fileName) throws FrontEndException {
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
            return hotelConfigurationDBHandler.insertHotelMediaIfNotExists(hotelTicker, entity, idEntity, fileName);
        } catch (DBAccessException | ExternalFileException | NonexistentValueException e) {
            logger.error(e);
            throw new FrontEndException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }


    public List<Page> getPages(final String hotelTicker, final String locale) throws FrontEndException {
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(DBProperties.getDBCustomerByTicker(hotelTicker));
            WitHotelDBHandler witHotelDBHandler = new WitHotelDBHandler(dbConnection);
            return witHotelDBHandler.getPages(locale);
        } catch (DBAccessException | ExternalFileException | NonexistentValueException e) {
            logger.error(e);
            throw new FrontEndException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    public CurrencyExchange getCurrenciesExchange(String currency) throws FrontEndException {
        if (currency != null && !currency.isEmpty()) {
            DBConnection dbConnection = null;
            try {
                CurrencyDBHandler currencyDBHandler = new CurrencyDBHandler();
                dbConnection = currencyDBHandler.getDbConnection();
                return currencyDBHandler.getCurrencyExchange(currency);
            } catch (ExternalFileException | NonexistentValueException | DBAccessException e) {
                logger.error(e);
                throw new FrontEndException(e);
            } finally {
                DAOUtil.close(dbConnection);
            }
        }
        return null;
    }


    public Map getCountriesMap() throws FrontEndException {
        DBConnection dbConnection = null;
        try {
            WitMetaDataDBHandler witMetaDataDBHandler = new WitMetaDataDBHandler();
            dbConnection = witMetaDataDBHandler.getDbConnection();
            return witMetaDataDBHandler.getCountriesMap();
        } catch (ExternalFileException | NonexistentValueException | DBAccessException e) {
            logger.error(e);
            throw new FrontEndException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    public int insertConversionQuery(final String hotelTicker, final String clientIp, final Date checkInDate,
                                     final Date checkOutDate, final Integer rooms, final int adults,
                                     final int children, final int infants, final String language,
                                     final boolean isSoldOut, final boolean isChain,
                                     final String channelId, final String trackingId) throws FrontEndException {
        logger.debug("insertConversionQuery: " + hotelTicker + " ip:" + clientIp);
        DBConnection dbConnection = null;
        try {
            dbConnection = new DBConnection(hotelTicker);
            HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);
            int inserts = 0;
            inserts = inserts + hotelConfigurationDBHandler.insertConversionQuery(clientIp, checkInDate,
                    checkOutDate, rooms, adults, children, infants, language, isSoldOut, isChain);
            if (channelId != null && trackingId != null && !trackingId.trim().isEmpty() && !channelId.trim().isEmpty()) {
                try {
                    WitMetaDataDBHandler witMetaDataDBHandler = new WitMetaDataDBHandler();
                    inserts = inserts + witMetaDataDBHandler.insertTrackingQuery(checkInDate, checkOutDate, hotelTicker,
                            channelId, language, trackingId, clientIp);
                } catch (MiddlewareException e) {
                    logger.error("Error inserting the TrackingQuery : " + Arrays.asList(checkInDate, checkOutDate, hotelTicker,
                            channelId, language, trackingId, clientIp));
                    logger.error(e);
                }
            }
            return inserts;
        } catch (MiddlewareException e) {
            logger.error(e);
            throw new FrontEndException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    /**
     * Share the currency exchange store in our system. Give the exchange of all currency defined in our system to masterCurrency.
     *
     * @return
     * @throws FrontEndException
     */
    public CurrencyExchange getCurrencyExchange(String masterCurrency) throws FrontEndException {
        CurrencyExchange currencyExchange = null;
        DBConnection dbConnection = null;
        try {
            CurrencyDBHandler currencyDBHandler = new CurrencyDBHandler();
            currencyExchange = currencyDBHandler.getCurrencyExchange(masterCurrency);
        } catch (Exception e) {
            logger.error(e);
            throw new FrontEndException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
        return currencyExchange;
    }


    public List<String> sendEmail(String fromAddress, String fromName, String subject, String contentHTML,
                                  List<String> toAddress, String toName, List<String> tags) throws FrontEndException {
        try {
            return EmailsUtils.sendEmail(fromAddress, fromName, subject, contentHTML, toAddress, toName, tags);
        } catch (Exception e) {
            logger.error(e);
            throw new FrontEndException(e);
        }
    }

}