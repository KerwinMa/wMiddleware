/*
 *  InventoryDBHandler.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.db.handlers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.sqlinstructions.SQLInstructions;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.Currency;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 31-ene-2013
 */
public class HotelConfigurationDBHandler extends DBHandler {
    /**
     * Object used to report on the Server's Log using Apache log4j.
     */
    private static final Logger logger = Logger.getLogger(HotelConfigurationDBHandler.class);

    /**
     * Creates a new instance of
     * <code>HotelConfigurationDBHandler</code> without params.
     */
    public HotelConfigurationDBHandler() {
    }

    public HotelConfigurationDBHandler(DBConnection dbConnection) {
        super(dbConnection);
    }

    //TODO: use the new object Language, with the method getActiveLanguages() for trivago WS
    public List<String[]> getActiveLanguageCodes() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_ACTIVE_LANGUAGES_CODE_SQL_COMMAND);
            resultSet = execute(statement);
            List<String[]> codesList = new ArrayList<String[]>();
            while (next(resultSet)) {
                codesList.add(new String[]{getString(resultSet, 1), getString(resultSet, 2)});
            }
            return codesList;
        } finally {
            DAOUtil.close(statement, resultSet);
        }

    }

    public List<Language> getActiveLanguages() throws DBAccessException {
        PreparedStatement statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_ACTIVE_LANGUAGES_SQL_COMMAND);
        ResultSet resultSet = execute(statement);
        List<Language> codesList = new ArrayList<Language>();
        while (next(resultSet)) {
            codesList.add(new Language(getInt(resultSet, 1), getString(resultSet, 2), getString(resultSet, 3), getString(resultSet, 4), getString(resultSet, 5)));
        }
        DAOUtil.close(statement, resultSet);
        return codesList;
    }

    public boolean isCancellationAllowed() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.IS_CANCELLEATION_ALLOWED_SQL_COMMAND);
            resultSet = execute(statement);
            return next(resultSet) ? getInt(resultSet, 1) >= 0 : false;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public boolean isVersion6() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.IS_VERSION_6_SQL_COMMAND);
            resultSet = execute(statement);
            return next(resultSet) ? getInt(resultSet, 1) > 0 : false;
        } finally {
            DAOUtil.close(statement, resultSet);
        }

    }

    public String getDefaultLanguageCode() throws DBAccessException {
        List<String> list = new ArrayList<String>();
        list.add("defaultLanguage");
        Properties properties = this.getHotelProperties(list);
        String defCode = "es";
        if (properties != null && !properties.isEmpty() && properties.getProperty("defaultLanguage") != null) {
            defCode = properties.getProperty("defaultLanguage").trim();
        }
        return defCode;
    }

    public Language getDefaultLanguage() throws DBAccessException {
        List<Language> languageList;
        languageList = this.getActiveLanguages();
        if (languageList.isEmpty())
            return new Language(44, "Español", "es", "spa", "utf-8");
        String defCode = this.getDefaultLanguageCode();
        Language defaultLanguage = Language.getActiveLanguageByCode(languageList, defCode);
        if (defaultLanguage != null)
            return defaultLanguage;
        defaultLanguage = Language.getActiveLanguageByCode(languageList, "es");
        if (defaultLanguage != null)
            return defaultLanguage;
        defaultLanguage = Language.getActiveLanguageByCode(languageList, "en");
        if (defaultLanguage != null)
            return defaultLanguage;
        return languageList.get(0);
    }

    public Language getActiveLanguageByLocale(String locale) throws DBAccessException {
        List<Language> languageList;
        languageList = this.getActiveLanguages();
        if (languageList.isEmpty())
            return new Language(44, "Español", "es", "spa", "utf-8");
        Language activeLanguage = Language.getActiveLanguageByLocale(languageList, locale);
        if (activeLanguage != null)
            return activeLanguage;
        //If there is any language with this locale, return the default Language
        String defCode = this.getDefaultLanguageCode();
        Language defaultLanguage = Language.getActiveLanguageByCode(languageList, defCode);
        if (defaultLanguage != null)
            return defaultLanguage;
        defaultLanguage = Language.getActiveLanguageByCode(languageList, "es");
        if (defaultLanguage != null)
            return defaultLanguage;
        defaultLanguage = Language.getActiveLanguageByCode(languageList, "en");
        if (defaultLanguage != null)
            return defaultLanguage;
        return languageList.get(0);
    }

    public Map<String, String> getActiveCreditCards() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_ACTIVE_CREDIT_CARDS);
            resultSet = execute(statement);
            Map<String, String> activeCreditCards = new HashMap<>();
            Map<String, String> allCreditCards = new HashMap<>();
            while (next(resultSet)) {
                allCreditCards.put(getString(resultSet, 1), getString(resultSet, 2));
                if (getBoolean(resultSet, 3) != null && getBoolean(resultSet, 3))
                    activeCreditCards.put(getString(resultSet, 1), getString(resultSet, 2));
            }
            //If there isn't any active CC, return all the CC
            if (activeCreditCards.isEmpty())
                return allCreditCards;
            else
                return activeCreditCards;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public Properties getHotelProperties(List<String> values) throws DBAccessException {
        Properties properties = new Properties();
        String sqlCommand = "SELECT confp.clave, confp.valor "
                + "FROM configuracionpropiedades AS confp "
                + "WHERE confp.clave IN (";
        if (values.size() > 0) {
            sqlCommand += "? ";
            if (values.size() > 1) {
                sqlCommand += new String(new char[values.size() - 1]).replace("\0", ",? ");
            }
        }
        sqlCommand += ");";
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(sqlCommand, values);
            resultSet = execute(statement);
            while (next(resultSet)) {
                String key = getString(resultSet, 1);
                String value = getString(resultSet, 2);
                if (key != null && value != null && !key.isEmpty() && !value.isEmpty()) {
                    properties.put(key, value);
                }
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
        return properties;
    }

    public Properties getMaxPersons() throws DBAccessException {
        Properties properties = new Properties();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_MAX_PERSONS);
            resultSet = execute(statement);
            if (next(resultSet)) {
                properties.put("maxAdults", getString(resultSet, 1));
                properties.put("maxChildren", getString(resultSet, 2));
                properties.put("maxBabies", getString(resultSet, 3));
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
        return properties;
    }

    //This get the properties used in WitBooker from 'perfilescolor' table
    public Properties getWitBookerAppearanceProperties() throws DBAccessException {
        Properties properties = new Properties();
        String sqlCommand = "SELECT c.fondoCuerpo, c.colorCabecera, c.fondoCabecera, c.fondoBotonesCuerpo, "
                + "c.colorCabeceraInactivo, c.colorCuerpo, c.colorLinia, c.alturaCabecera "
                + "FROM perfilescolor AS c "
                + "LIMIT 1;";
        PreparedStatement preparedStatement = prepareStatement(sqlCommand);
        ResultSet resultSet = execute(preparedStatement);
        if (next(resultSet)) {
            properties.put("mainBgColor", getString(resultSet, 1));
            properties.put("secondaryBgColor", getString(resultSet, 2));
            properties.put("logoBgColor", getString(resultSet, 3));
            properties.put("mainFontColor1", getString(resultSet, 4));
            properties.put("mainFontColor2", getString(resultSet, 5));
            properties.put("secondaryFontColor", getString(resultSet, 6));
            properties.put("lineColor", getString(resultSet, 7));
            properties.put("marginLogo", getInt(resultSet, 8));
        }
        DAOUtil.close(preparedStatement, resultSet);
        return properties;
    }

    public String getWebHotelURL() {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_DEFAULT_URL_WEBHOTEL);
            resultSet = execute(statement);
            return next(resultSet) ? getString(resultSet, 1) : null;
        } catch (DBAccessException ex) {
            logger.error(ex.getMessage());
            return null;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public String getProcessedURL(String hotelTicker, String language) {
        String urlLink = getWebHotelURL();
        //If the hotel don't have Web URL, we send the reservation web in step1
        if (urlLink == null || urlLink.isEmpty() || urlLink.contains("www.witbooking.com")) {
            urlLink = MiddlewareProperties.URL_WITBOOKER_V6
                    + "select/" + hotelTicker + "/" + language + "/reservationsv6/step1";
        }
        return urlLink;
    }

    public String getDefaultCurrency() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_DEFAULT_CURRENCY_SQL_COMMAND);
            resultSet = execute(statement);
            return next(resultSet) ? getString(resultSet, 1) : "EUR";
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<Currency> getActivesCurrencies() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_ACTIVES_CURRENCIES_SQL_COMMAND);
            resultSet = execute(statement);
            String jsonObject = "[\"EUR\",\"GBP\",\"USD\"]";
            if (next(resultSet)) {
                jsonObject = getString(resultSet, 2);
            }
            DAOUtil.close(statement, resultSet);
            List<Currency> currencies = new ArrayList<Currency>();
            Gson gson = new GsonBuilder().create();
            List<String> jsonCurrencies = gson.fromJson(jsonObject, new TypeToken<List<String>>() {
            }.getType());
            for (String code : jsonCurrencies) {
                Currency currency = new Currency();
                //TODO: should find the Currency name in DB conversorv2.divisas?
                //                    currency.setName(resultSet.getString(2));
                currency.setCode(code);
                currencies.add(currency);
            }
            return currencies;
        } finally {
            DAOUtil.close(statement, resultSet);
        }

    }

    public Smtp getSMTPProperties() throws DBAccessException {
        Smtp smtp = null;
        PreparedStatement statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_SMTP_PROPERTIES_SQL_COMMAND);
        ResultSet resultSet = execute(statement);
        if (next(resultSet)) {
            smtp = new Smtp(getString(resultSet, 1), getString(resultSet, 2), getString(resultSet, 3),
                    getString(resultSet, 4), getString(resultSet, 5), getString(resultSet, 6));
//        } else {
//            DAOUtil.close(statement, resultSet);
//            throw new DBAccessException("Smtp Property not found.");
        }
        DAOUtil.close(statement, resultSet);
        return smtp;
    }

    public Poll getPoll(String locale, boolean postStay) throws DBAccessException {
        Poll poll = null;
        ArrayList<Object> values = new ArrayList<Object>(1);
        values.add(locale);
        //(TODO: reescribir la tabla 'encuestas' a tabla 'mail')
        //If is a PostStay Mail, The Poll is the row 1.
        if (postStay) {
            values.add(1);
        } else {
            values.add(2);
        }
        PreparedStatement statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_POLL_SQL_COMMAND, values);
        ResultSet resultSet = execute(statement);
        if (next(resultSet)) {
            poll = new Poll();
            poll.setId(getInt(resultSet, 1));
            poll.setLanguage(getString(resultSet, 4));
            //i18n attribute
            poll.setTitle(getTranslation(resultSet, 5, 2));
            poll.setContent(getTranslation(resultSet, 6, 3));
        }
        DAOUtil.close(statement, resultSet);
        return poll;
    }


    public List<NewsletterSubscription> getNewsletterSubscriptions(String language) throws DBAccessException {
        ArrayList<Object> values = new ArrayList<Object>(1);
        values.add(language);
        PreparedStatement statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_NEWSLETTER_LANGUAGE_SQL_COMMAND, values);
        ResultSet resultSet = execute(statement);
        List<NewsletterSubscription> codesList = new ArrayList<NewsletterSubscription>();
        while (next(resultSet)) {
            codesList.add(new NewsletterSubscription(getInt(resultSet, 1), getString(resultSet, 2),
                    getString(resultSet, 3), getBoolean(resultSet, 4)));
        }
        DAOUtil.close(statement, resultSet);
        return codesList;
    }

    public int insertNewsletterSubscriptions(NewsletterSubscription newsletterSubscription) throws DBAccessException {
        String sqlCommand;
        PreparedStatement statement;
        sqlCommand = "INSERT INTO newsletter (email , language , active) " +
                "VALUES ( ?,  ?,  ?) ON DUPLICATE KEY UPDATE language=?, active=?;";
        ArrayList<Object> values = new ArrayList<Object>(1);
        values.add(newsletterSubscription.getEmail());
        values.add(newsletterSubscription.getLanguage());
        values.add(newsletterSubscription.isActive());
        values.add(newsletterSubscription.getLanguage());
        values.add(newsletterSubscription.isActive());
        statement = prepareStatement(sqlCommand, values);

        int totalInserted = executeUpdate(statement);
        DAOUtil.close(statement);
        return totalInserted;
    }

    public String getHotelName() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_HOTEL_NAME);
            resultSet = execute(statement);
            return next(resultSet) ? getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableEstablishment.ATT_NOMBRE) : null;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    //TODO Change that to private
    public Integer checkIfExistsHotelMedia(final String entity, final Integer idEntity, final String fileName)
            throws DBAccessException {
        PreparedStatement statementCheckIfExists = null;
        ResultSet resultSetIfExists = null;
        try {
            List values = new ArrayList();
            values.add(entity);
            values.add(idEntity);
            values.add(fileName);
            statementCheckIfExists = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.CHECK_IF_EXISTS_MULTIMEDIA, values);
            resultSetIfExists = execute(statementCheckIfExists);
            return next(resultSetIfExists)
                    ? getInt(resultSetIfExists, SQLInstructions.HotelConfigurationDBHandler.TableMultimedia.ATT_ID)
                    : null;
        } finally {
            DAOUtil.close(statementCheckIfExists, resultSetIfExists);
        }
    }

    private HotelMedia hotelMediaFromResultSet(ResultSet resultSet, String hotelTicker) throws DBAccessException {
        final String title = getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableMultimedia.ATT_TITLE);
        final String description = getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableMultimedia.ATT_DESCRIPTION);
        final String entity = getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableMultimedia.ATT_ENTITY);
        final Integer idEntity = getInt(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableMultimedia.ATT_ID_ENTITY);
        final Integer id = getInt(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableMultimedia.ATT_ID);
        final String fileName = getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableMultimedia.ATT_FILE_NAME);
        return new HotelMedia(hotelTicker, id, entity, idEntity, fileName, title, description);
    }

    private HotelMedia executeSaveHotelMedia(final String hotelTicker, final String entity, final Integer idEntity, final String fileName,
                                             final String title, final String description, Boolean exist) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            List values = new ArrayList();
            values.add(title);
            values.add(description);
            values.add(entity);
            values.add(idEntity);
            values.add(fileName);
            statement = prepareStatement(exist
                    ? SQLInstructions.HotelConfigurationDBHandler.UPDATE_MULTIMEDIA
                    : SQLInstructions.HotelConfigurationDBHandler.INSERT_MULTIMEDIA
                    , values);
            int numRowUpdate = executeUpdate(statement);
            logger.debug("number of rows updated updating  hotel media: " + numRowUpdate);
            return new HotelMedia(hotelTicker, entity, idEntity, fileName, title, description);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public HotelMedia insertHotelMediaIfNotExists(final String hotelTicker, final String entity, final Integer idEntity, final String fileName)
            throws DBAccessException {
        Integer exist = checkIfExistsHotelMedia(entity, idEntity, fileName);
        final HotelMedia hotelMedia = (exist == null)
                ? executeSaveHotelMedia(hotelTicker, entity, idEntity, fileName, "", null, exist != null)
                : null;
        return hotelMedia;
    }

    public void saveMediaHotel(HotelMedia hotelMedia)
            throws DBAccessException {
        executeSaveHotelMedia(hotelMedia.getHotelTicker(), hotelMedia.getEntity(), hotelMedia.getIdEntity(),
                hotelMedia.getFileName(), hotelMedia.getTitle(), hotelMedia.getDescription(), hotelMedia.getId() != null);
    }

    public List<Amenity> getAmenities() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List<Amenity> amenities = new ArrayList<Amenity>();
        try {
            statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_AMENITIES);
            resultSet = execute(statement);
            while (next(resultSet)) {
                final int id = getInt(resultSet, "id");
                final Amenity amenity = Amenity.fromId(id);
                if (amenity != null) amenities.add(amenity);
            }
            return amenities;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<FrontEndMessage> getFrontEndMessages(final String locale) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List values = new ArrayList();
        try {
            values.add(locale);
            statement = prepareStatement(SQLInstructions.HotelConfigurationDBHandler.GET_FRONT_END_MESSAGES, values);
            resultSet = execute(statement);
            return getFrontEndMessagesFromResultSet(resultSet);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public Map<Markup.Phase,List<Markup>> getScripts() throws DBAccessException {
        Map<Markup.Phase,List<Markup>> scripts = new HashMap<Markup.Phase,List<Markup>>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.MarkupDBHandler.GET_MARKUP_SQL_COMMAND);
            resultSet = execute(statement);
            while (next(resultSet)) {
                Markup markup = new Markup();
                markup.setId(getInt(resultSet,1));
                markup.setName(getString(resultSet, 2));
                markup.setCode(getString(resultSet, 3));
                markup.setPosition(Markup.Position.getPositionFromSqlString(getString(resultSet, 4)));
                markup.setPhase(Markup.Phase.getTypeFromSqlString(getString(resultSet, 5)));
                markup.setActive(getBoolean(resultSet,6));
                if(!scripts.containsKey(markup.getPhase())){
                    scripts.put(markup.getPhase(),new ArrayList<Markup>());
                }
                if(markup.isActive()){
                    scripts.get(markup.getPhase()).add(markup);
                }
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }

        return scripts;
    }


    private List<FrontEndMessage> getFrontEndMessagesFromResultSet(ResultSet resultSet) throws DBAccessException {
        List<FrontEndMessage> frontEndMessages = new ArrayList<FrontEndMessage>();
        while (resultSet != null && next(resultSet)) {
            final String sqlStringPosition = getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_POSITION);
            final String sqlStringType = getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_TYPE);
            FrontEndMessage.Position position = FrontEndMessage.Position.getPositionFromSqlString(sqlStringPosition);
            FrontEndMessage.Type type = FrontEndMessage.Type.getTypeFromSqlString(sqlStringType);
            final Boolean hidden = !getBoolean(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_HIDDEN);
            if (position != null && type != null) {
                final String sqlAlojamientos = getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_ALOJAMIENTOS);
                final Boolean unavailable = sqlAlojamientos != null && "1".equals(sqlAlojamientos);
                final String descriptionTrans = getString(resultSet, "trans.descripcion_trans");
                final String description = descriptionTrans == null
                        ? getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_DESCRIPTION)
                        : descriptionTrans;
                frontEndMessages.add(new FrontEndMessage(getInt(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_ID),
                        getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_USERNAME),
                        getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_EDITNAME),
                        description,
                        getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_TITLE),
                        position, type, hidden, unavailable,
                        getDate(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_INITIAL_DATE),
                        getDate(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_END_DATE),
                        getDate(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_CREATION_DATE),
                        getDate(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableFrontEndMessage.ATT_MODIFICATION_DATE)
                ));
            }
        }
        return frontEndMessages;
    }

    public Hotel getWitBookerHotelVisualRepresentation(List<String> propertyNames, final String locale, final boolean isDemo,
                                                       final TransferData transferData) throws DBAccessException {
        //Finding FrontEndMessages configured for this hotel.
        final List<FrontEndMessage> frontEndMessages = getFrontEndMessages(locale);
        InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(getDbConnection(), locale);
        //Finding hotel information.
        final Hotel hotel = inventoryDBHandler.getHotel();
        if (hotel == null)
            throw new DBAccessException("Error in DB '" + this.getDbConnection().getDbCredentials().getNameDB() + "' (Hotel is null)");
        //Finding inventory information.
        final List<Inventory> inventories = inventoryDBHandler.getInventoriesValid();
        //Finding discounts information
        final List<Discount> discounts = inventoryDBHandler.getDiscountsActives();
        //Finding services information
        final List<Service> services = inventoryDBHandler.getServicesActives();
        //Finding currencies information
        final List<Currency> currencies = getActivesCurrencies();
        //Finding languages information
        final List<Language> languages = getActiveLanguages();
        //Finding scripts information
        final Map<Markup.Phase,List<Markup>> scripts = getScripts();

        //Finding Credit Card information
        final Map<String, String> creditCardsAllowed = getActiveCreditCards();
        Properties properties = getEstablishmentConfiguration(propertyNames);

        //added the isDemo parameter
        if (isDemo)
            properties.setProperty("isDemo", "1");
        hotel.setConfiguration(properties);
        hotel.setVisualRepresentation(new WitBookerVisualRepresentation(languages, currencies, discounts, services,
                frontEndMessages, inventories, creditCardsAllowed, transferData,scripts));
        return hotel;
    }

    public Chain getWitBookerChainVisualRepresentation(List<String> propertyNames, final String locale, final List<Establishment> hotels, final boolean isDemo) throws DBAccessException {
        final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(getDbConnection(), locale);
        final Chain chain = inventoryDBHandler.getChain();
        if (chain == null)
            throw new DBAccessException("Error in DB '" + this.getDbConnection().getDbCredentials().getNameDB() + "' (chain is null)");
        chain.setEstablishments(hotels);
        final List<Language> activeLanguages = getActiveLanguages();
        final List<Currency> activesCurrencies = getActivesCurrencies();
        final List<FrontEndMessage> frontEndMessages = getFrontEndMessages(locale);
        //Finding discounts information
        final List<Discount> discounts = inventoryDBHandler.getDiscountsActives();
        Properties properties = getEstablishmentConfiguration(propertyNames);
        //added the isDemo parameter
        if (isDemo)
            properties.setProperty("isDemo", "1");
        chain.setConfiguration(properties);
        chain.setVisualRepresentation(new ChainVisualRepresentation(activeLanguages, activesCurrencies, frontEndMessages, discounts));
        return chain;
    }

    //TODO Pasar esto a getChain y getHotel de InventoryDBHandler cuando las cosas no esten cableadas.
    private Properties getEstablishmentConfiguration(List<String> propertyNames) throws DBAccessException {
        //Finding properties requested by the names given on propertyNames.
        Properties properties = (propertyNames != null && !propertyNames.isEmpty())
                ? getHotelProperties(propertyNames)
                : new Properties();

        //Additional Properties to add, (Not in configuracion_propiedades)
        properties.putAll(getMaxPersons());
        properties.putAll(getWitBookerAppearanceProperties());
        final String defaultCurrency = getDefaultCurrency();
        if (defaultCurrency != null) {
            properties.put("defaultCurrency", defaultCurrency);
        }
        final String urlHotel = getWebHotelURL();
        if (urlHotel != null) {
            properties.put("urlwebhotel", urlHotel);
        }
        //TODO: rename fondoBotonesCuerpo2
        if (properties.get("fondoBotonesCuerpo2") != null) {
            properties.put("buttonSecondaryColor", properties.get("fondoBotonesCuerpo2"));
            properties.remove("fondoBotonesCuerpo2");
        }
        return properties;
    }


    public Hotel getWitHotelVisualRepresentation(final String hotelTicker,
                                                 final List<String> propertyNames,
                                                 final String locale)
            throws DBAccessException, NonexistentValueException, ExternalFileException {
        return getWitHotelVisualRepresentation(hotelTicker, propertyNames, locale, null, null, null);
    }

    public Hotel getWitHotelVisualRepresentation(final String hotelTicker,
                                                 List<String> propertyNames,
                                                 final String locale,
                                                 List<Currency> currencies,
                                                 List<Language> languages,
                                                 Properties properties)
            throws DBAccessException, NonexistentValueException, ExternalFileException {
        //Checking if the properties were not given.
        if (properties == null) {
            //Finding properties requested by the names given on propertyNames.
            if (propertyNames != null && !propertyNames.isEmpty()) {
                properties = getHotelProperties(propertyNames);
            }
        }
        //Finding FrontEndMessages configured for this hotel.
        final List<FrontEndMessage> frontEndMessages = getFrontEndMessages(locale);
        InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(getDbConnection(), locale);
        //Finding hotel information.
        final Hotel hotel = inventoryDBHandler.getHotel();
        if (hotel == null)
            throw new DBAccessException("Error in DB '" + this.getDbConnection().getDbCredentials().getNameDB() + "' (Hotel is null)");
        //Finding discounts information
        final List<Discount> discounts = inventoryDBHandler.getDiscountsActives();
        //Finding services information
        final List<Service> services = inventoryDBHandler.getServicesActives();
        //Checking if the currencies were not given.
        if (currencies == null) {
            //Finding currencies information
            currencies = getActivesCurrencies();
        }
        //Checking if the languages wer not given.
        if (languages == null) {
            //Finding languages information
            languages = getActiveLanguages();
        }
        //Finding accommodations information
        final List<Accommodation> accommodations = inventoryDBHandler.getAccommodationsActives();
        WitHotelDBHandler witHotelDBHandler = null;
        try {
            witHotelDBHandler = new WitHotelDBHandler();
            //Finding locationPoints information
            final List<LocationPoint> locationPoints = witHotelDBHandler.getLocationPointsByTicker(hotelTicker, locale);
            //Finding review/comments information
            final List<Review> reviews = witHotelDBHandler.getReviewByTicker(hotelTicker, locale);
            //Checking if have the properties requested to put on the object.
            if (properties != null) {
                hotel.setConfiguration(properties);
            }
            hotel.setVisualRepresentation(new WitHotelVisualRepresentation(languages, currencies, discounts, services, frontEndMessages, accommodations, locationPoints, reviews));
            return hotel;
        } finally {
            if (witHotelDBHandler != null) {
                DAOUtil.close(witHotelDBHandler.getDbConnection());
            }
        }
    }


    /**
     * Insert a request made by a client in the Step 1, for this specific hotel, in the ConversionQuery Table
     *
     * @param clientIp     String with the Ip address client's
     * @param checkInDate  Check In date for the Query in the step 1
     * @param checkOutDate Check Out date for the Query in the step 1
     * @param rooms        number of inventories with availability in the Query made for the client in the Step 1
     * @param adults       number of adults requested in the Query made for the client in the Step 1
     * @param children     number of children requested in the Query made for the client in the Step 1
     * @param infants      number of infants requested in the Query made for the client in the Step 1
     * @param language     code for the language requested in the Query made for the client in the Step 1
     * @param isSoldOut    this boolean represents if the Query made for the client in the Step 1,
     *                     not returned available inventories
     * @param isChain      this boolean represent if the the Query made for the client in the Step 1 came from a Chain
     *                     aggregator.
     * @return the number of inserts in the ConversionQuery table. If is correct, It should be always 1.
     * @throws DBAccessException
     */
    public int insertConversionQuery(String clientIp, Date checkInDate, Date checkOutDate, Integer rooms,
                                     int adults, int children, int infants,
                                     String language, boolean isSoldOut, boolean isChain) throws DBAccessException {
        String sqlCommand;
        PreparedStatement statement;
        sqlCommand = SQLInstructions.HotelConfigurationDBHandler.INSERT_CONVERSION_QUERY;
        ArrayList<Object> values = new ArrayList<Object>();
        values.add(clientIp);
        values.add(new java.sql.Date(checkInDate.getTime()));
        values.add(new java.sql.Date(checkOutDate.getTime()));
        values.add(DateUtil.daysBetweenDates(DateUtil.toBeginningOfTheDay(checkInDate), DateUtil.toBeginningOfTheDay(checkOutDate)));
        values.add(rooms);
        values.add(adults + children + infants);
        values.add(adults);
        values.add(children);
        values.add(infants);
        values.add(language);
        values.add(isSoldOut);
        values.add(isChain);
        statement = prepareStatement(sqlCommand, values);
        int totalInserted = executeUpdate(statement);
        DAOUtil.close(statement);
        return totalInserted;
    }
}