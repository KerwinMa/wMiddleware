/*
 *  InventoryDBHandler.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.db.handlers;

import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.sqlinstructions.SQLInstructions;
import com.witbooking.middleware.exceptions.DataValueFormatException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.model.values.*;
import com.witbooking.middleware.model.values.types.DaysCondition;
import com.witbooking.middleware.model.values.types.FormulaValue;
import com.witbooking.middleware.model.values.types.SharedValue;
import org.apache.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
public class InventoryDBHandler extends DBHandler {

    /**
     * Object used to report on the Server's Log using Apache log4j.
     */
    private static final Logger logger = Logger.getLogger(InventoryDBHandler.class);
    //Map<dbClassName, Map<id, Object>>
    private Map<String, Map<Integer, Object>> entityHash;
    //Map<ticker, id>
    private Map<String, Map<String, Integer>> tickerHash;
    private String locale = "eng";
    //ARI Optimization Step1
    private boolean complexInventory = true;

    /**
     * Creates a new instance of
     * <code>InventoryDBHandler</code> without params.
     */
    public InventoryDBHandler() {
        tickerHash = new HashMap<String, Map<String, Integer>>();
        entityHash = new HashMap<String, Map<Integer, Object>>();
        this.setLocale("");
    }

    public InventoryDBHandler(DBConnection dbConnection, String locale) {
        super(dbConnection);
        tickerHash = new HashMap<String, Map<String, Integer>>();
        entityHash = new HashMap<String, Map<Integer, Object>>();
        this.setLocale(locale);
    }

    public InventoryDBHandler(DBConnection dbConnection) {
        super(dbConnection);
        tickerHash = new HashMap<String, Map<String, Integer>>();
        entityHash = new HashMap<String, Map<Integer, Object>>();
        this.setLocale("");
    }

    public InventoryDBHandler(DBConnection dbConnection, boolean complexInventory) {
        super(dbConnection);
        tickerHash = new HashMap<String, Map<String, Integer>>();
        entityHash = new HashMap<String, Map<Integer, Object>>();
        this.setLocale("");
        setComplexInventory(complexInventory);
        //TODO: hacer el reset cache en el connection generico
        if (dbConnection.profilingMode) {
            try {
                this.clearDBCache();
            } catch (DBAccessException ex) {
                logger.error(ex.getMessage());
            }
        }
    }

    public InventoryDBHandler(DBConnection dbConnection, Map<String, Map<Integer, Object>> entityHash, Map<String, Map<String, Integer>> tickerHash, String locale) {
        super(dbConnection);
        this.entityHash = entityHash;
        this.tickerHash = tickerHash;
        this.setLocale(locale);
    }

    public boolean isComplexInventory() {
        return complexInventory;
    }

    public void setComplexInventory(boolean complexInventory) {
        if (!complexInventory) {
            Accommodation accommodation = new Accommodation();
            Configuration configuration = new Configuration();
            MealPlan mealPlan = new MealPlan();
            Condition condition = new Condition();
            putInMapper(-1, "accommodationDummy", accommodation);
            putInMapper(-1, "configurationDummy", configuration);
            putInMapper(-1, "mealPlanDummy", mealPlan);
            putInMapper(-1, "conditionDummy", condition);
        }
        this.complexInventory = complexInventory;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        HotelConfigurationDBHandler hotelConfigurationDBHandler = new HotelConfigurationDBHandler(getDbConnection());
        Language language;
        try {
            language = hotelConfigurationDBHandler.getActiveLanguageByLocale(locale);
        } catch (DBAccessException e) {
            logger.error(e);
            language = new Language(44, "Español", "es", "spa", "utf-8");
        }
        this.locale = language.getLocale();
    }

    /**
     * @return Chain without configuration filled. If you want to fill this chain, use HotelConfigurationDBHandler.getEstablishmentConfiguration.
     * @throws DBAccessException
     */
    public Chain getChain() throws DBAccessException {
        List<Chain> chains = getFullListObjectFromMapper(Chain.class.getSimpleName());
        return (chains != null && !chains.isEmpty() ? chains.get(0) : null);
    }

    /**
     * @return Hotel without configuration filled. If you want to fill this chain, use HotelConfigurationDBHandler.getEstablishmentConfiguration.
     * @throws DBAccessException
     */
    public Hotel getHotel() throws DBAccessException {
        List<Hotel> hotels = getFullListObjectFromMapper(Hotel.class.getSimpleName());
        return (hotels != null && !hotels.isEmpty() ? hotels.get(0) : null);
    }

    public List<Media> getImages(KindMedia type, Integer id) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List values = new ArrayList();
        List<Media> ret = new ArrayList<Media>();
        try {
            if (id != null && type != null) {
                values.add(type.getValue());
                values.add(id);
                statement = preparedStatementWithLocate(SQLInstructions.InventoryDBHandler.SELECT_MULTIMEDIA, values);
                resultSet = execute(statement);
                while (next(resultSet)) {
                    final String file = getString(resultSet, SQLInstructions.HotelConfigurationDBHandler.TableMultimedia.ATT_FILE_NAME);
                    final String title = getTranslation(resultSet, 5, 2);
                    final String description = getTranslation(resultSet, 6, 3);
                    final int order = getInt(resultSet, 4);
                    if (file != null) ret.add(new Media(file, title, description, type, order));
                }
            }
            Collections.sort(ret);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
        return ret;
    }

    public List<Tax> getTaxes() throws DBAccessException {
        return getFullListObjectFromMapper(Tax.class.getSimpleName());
    }

    public List<Accommodation> getAccommodationsActives() throws DBAccessException {

        List<Accommodation> fullList = getFullListObjectFromMapper(Accommodation.class.getSimpleName());
        List<Accommodation> accommodationList = new ArrayList<Accommodation>();
        for (Accommodation accommodation : fullList) {
            if (!accommodation.isDeleted()) {
                accommodationList.add(accommodation);
            }
        }
        Collections.sort(accommodationList);
        return accommodationList;
    }

    public Accommodation getAccommodationById(int idAccommodation) throws DBAccessException {
        return (Accommodation) getObjectInMapperFromId(Accommodation.class.getSimpleName(), idAccommodation);
    }

    public Configuration getConfigurationById(int idConfiguration) throws DBAccessException {
        return (Configuration) getObjectInMapperFromId(Configuration.class.getSimpleName(), idConfiguration);
    }

    public MealPlan getMealPlanById(int idMealPlan) throws DBAccessException {
        return (MealPlan) getObjectInMapperFromId(MealPlan.class.getSimpleName(), idMealPlan);
    }

    public Condition getConditionById(int idCondition) throws DBAccessException {
        return (Condition) getObjectInMapperFromId(Condition.class.getSimpleName(), idCondition);
    }

    public PaymentType getPaymentTypeById(int idPaymentType) throws DBAccessException {
        return (PaymentType) getObjectInMapperFromId(PaymentType.class.getSimpleName(), idPaymentType);
    }


    public List<Service> getServicesValid() throws DBAccessException {
        List<Service> fullList = getFullListObjectFromMapper(Service.class.getSimpleName());
        Collections.sort(fullList);
        return fullList;
    }

    public List<Service> getServicesActives() throws DBAccessException {
        List<Service> fullList = getFullListObjectFromMapper(Service.class.getSimpleName());
        List<Service> serviceList = new ArrayList<Service>();
        for (Service service : fullList) {
            if (service.isActive()) {
                serviceList.add(service);
            }
        }
        Collections.sort(serviceList);
        return serviceList;
    }

    public Service getServicesById(int idService) throws DBAccessException {
        return (Service) getObjectInMapperFromId(Service.class.getSimpleName(), idService);
    }

    public Service getServicesByTicker(String tickerService) throws DBAccessException {
        return (Service) getObjectInMapperFromTicker(Service.class.getSimpleName(), tickerService);
    }

    public List<String> getInventoryTickersFromDiscount(int idDiscount) throws DBAccessException {
        final String sqlCommand = SQLInstructions.InventoryDBHandler.SELECT_INVENTORY_TICKER_FROM_DISCOUNT_ID;
        List values = new ArrayList();
        values.add(idDiscount);
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(sqlCommand, values);
            resultSet = execute(statement);
            List<String> discountList = new ArrayList<String>();
            while (next(resultSet)) {
                final String inventoryTicker = getString(resultSet, 1);
                if (inventoryTicker != null)
                    discountList.add(inventoryTicker);
            }
            return discountList;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<Discount> getDiscountsValid() throws DBAccessException {
        List<Discount> fullList = getFullListObjectFromMapper(Discount.class.getSimpleName());
        Collections.sort(fullList);
        return fullList;
    }

    public List<Discount> getDiscountsActives() throws DBAccessException {
        List<Discount> fullList = getFullListObjectFromMapper(Discount.class.getSimpleName());
        List<Discount> discountList = new ArrayList<Discount>();
        for (Discount discount : fullList) {
            if (discount.isActive()) {
                discountList.add(discount);
            }
        }
        Collections.sort(discountList);
        return discountList;
    }


    public Discount getDiscountById(int idDiscount) throws DBAccessException {
        return (Discount) getObjectInMapperFromId(Discount.class.getSimpleName(), idDiscount);
    }

    public Discount getDiscountByTicker(String tickerDiscount) throws DBAccessException {
        return (Discount) getObjectInMapperFromTicker(Discount.class.getSimpleName(), tickerDiscount);
    }

    /**
     * Is used to find the Availability Tree of any reservation made. This function does not care if the
     * inventory is deleted .
     *
     * @param ticker
     * @return
     * @throws DBAccessException
     */
    public List<Inventory> getTreeAvailability(String ticker) throws DBAccessException {
        List<Inventory> fullList = getFullListObjectFromMapper(Inventory.class.getSimpleName());
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        for (Inventory inventory : fullList) {
            AvailabilityDataValue dataValue = inventory.getAvailability();
            if ((dataValue.isOwnValue() && inventory.getTicker().equals(ticker))
                    || (dataValue.isSharedValue() && ((SharedValue) dataValue.getValue()).getTicker().equals(ticker))) {
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
    }

    public List<Inventory> getInventoriesByAdults(int adults) throws DBAccessException {
        return getInventories(null, adults, null, null, null);
    }

    public List<Inventory> getInventoriesByAdults(int adults, String promotionalCode) throws DBAccessException {
        return getInventories(null, adults, null, null, promotionalCode);
    }

    public List<Inventory> getInventoriesByAdultsAndChildren(int adults, int children) throws DBAccessException {
        return getInventories(null, adults, children, null, null);
    }

    public List<Inventory> getInventoriesByAdultsAndChildren(int adults, int children, String promotionalCode) throws DBAccessException {
        return getInventories(null, adults, children, null, promotionalCode);
    }

    public List<Inventory> getInventoriesByAdultsAndChildrenAndInfants(int adults, int children, int infants) throws DBAccessException {
        return getInventories(null, adults, children, infants, null);
    }

    public List<Inventory> getInventoriesByAdultsAndChildrenAndInfants(int adults, int children,
                                                                       int infants, String promotionalCode) throws DBAccessException {
        return getInventories(null, adults, children, infants, promotionalCode);
    }

    public List<Inventory> getInventoriesByPeople(int people) throws DBAccessException {
        return getInventories(people, null, null, null, null);
    }

    public List<Inventory> getInventories(Integer people, Integer adults, Integer children, Integer infants,
                                          String promotionalCode) throws DBAccessException {
        //This was created for Trivago Services
        final boolean peopleIsNull = people == null;
        final boolean promotionalCodeIsNull = promotionalCode == null;
        final boolean adultsIsNull = adults == null;
        final boolean childrenIsNull = children == null;
        final boolean infantsIsNull = infants == null;
        if (peopleIsNull && adultsIsNull && childrenIsNull && infantsIsNull) {
            throw new DBAccessException(new NullPointerException("All values given for Guests were null."));
        }
        List<Inventory> fullList = getFullListObjectFromMapper(Inventory.class.getSimpleName());
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        for (Inventory inventory : fullList) {
            if (inventory.isValid() && inventory.isVisible()
                    && (peopleIsNull || (inventory.getConfiguration().getTotalGuests() == people))
                    && (adultsIsNull || (inventory.getConfiguration().getAdults() == adults))
                    && (childrenIsNull || (inventory.getConfiguration().getChildren() == children))
                    && (infantsIsNull || (inventory.getConfiguration().getInfants() == infants))
                    && (inventory.getAccessCode() == null || inventory.getAccessCode().trim().isEmpty() ||
                    (!promotionalCodeIsNull && inventory.getAccessCode().equals(promotionalCode)))) {
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
    }


    public List<Inventory> getInventoriesValid() throws DBAccessException {
        List<Inventory> fullList = getFullListObjectFromMapper(Inventory.class.getSimpleName());
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        for (Inventory inventory : fullList) {
            if (inventory.isValid()) {
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
    }

    public List<Inventory> getInventoriesActives() throws DBAccessException {
        List<Inventory> fullList = getFullListObjectFromMapper(Inventory.class.getSimpleName());
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        for (Inventory inventory : fullList) {
            if (inventory.isValid() && inventory.isVisible()) {
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
    }

    public List<Inventory> getInventoryForScraping() throws DBAccessException {
        List<Inventory> fullList = getFullListObjectFromMapper(Inventory.class.getSimpleName());
        List<Inventory> inventoryList = new ArrayList<>();
        for (Inventory inventory : fullList) {
            if (inventory.isValid() && inventory.getRate().isOwnValue() && inventory.getAvailability().isOwnValue() &&
                    inventory.getLock().isOwnValue() && inventory.getMinStay().isOwnValue()) {
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
    }

    public List<Inventory> getInventoriesOwnRates() throws DBAccessException {
        List<Inventory> fullList = getFullListObjectFromMapper(Inventory.class.getSimpleName());
        List<Inventory> inventoryList = new ArrayList<>();
        for (Inventory inventory : fullList) {
            if (inventory.isValid() && inventory.getRate().isOwnValue()) {
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
    }

    public List<Inventory> getInventoriesByRoomMealPlanRatePlan(String roomCode, String mealPlanCode, String ratePlanCode) throws
            DBAccessException {
        List<Inventory> fullList = getFullListObjectFromMapper(Inventory.class.getSimpleName());
        List<Inventory> inventoryList = new ArrayList<>();
        for (Inventory inventory : fullList) {
            if (inventory.isValid() && inventory.isVisible() &&
                    inventory.getAccommodation().getTicker().equals(roomCode) &&
                    inventory.getMealPlan().getTicker().equals(mealPlanCode) &&
                    inventory.getCondition().getTicker().equals(ratePlanCode)) {
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
    }

    public List<Inventory> getInventoriesByTickers(List<String> tickers) throws DBAccessException {
        List<Inventory> inventoryList = new ArrayList<>();
        for (String ticker : tickers) {
            Inventory inventory = getInventoryByTicker(ticker);
            inventoryList.add(inventory);
        }
        return inventoryList;
    }

    public Inventory getInventoryByTicker(String tickerInventory) throws DBAccessException {
        return (Inventory) getObjectInMapperFromTicker(Inventory.class.getSimpleName(), tickerInventory);
    }

    public Inventory getInventoryById(int idInventory) throws DBAccessException {
        return (Inventory) getObjectInMapperFromId(Inventory.class.getSimpleName(), idInventory);
    }

    public List<Inventory> getChildInventories(String ticker) throws DBAccessException {
        List<Inventory> fullList = getFullListObjectFromMapper(Inventory.class.getSimpleName());
        List<Inventory> inventoryList = new ArrayList<>();
        for (Inventory inventory : fullList) {
            for (DataValue dataValue : inventory.getDataValues()) {
                if ((dataValue.isSharedValue() && ((SharedValue) dataValue.getValue()).getTicker().equals(ticker))
                        || (dataValue.isFormulaValue() && ((FormulaValue) dataValue.getValue()).getTickersSet().contains(ticker))) {
                    inventoryList.add(inventory);
                }
            }
        }
        return inventoryList;
    }

    public List<Inventory> getRateChildInventories(String ticker) throws DBAccessException {
        List<Inventory> fullList = getFullListObjectFromMapper(Inventory.class.getSimpleName());
        List<Inventory> inventoryList = new ArrayList<>();
        for (Inventory inventory : fullList) {
            RateDataValue dataValue = inventory.getRate();
            if ((dataValue.isSharedValue() && ((SharedValue) dataValue.getValue()).getTicker().equals(ticker))
                    || (dataValue.isFormulaValue() && ((FormulaValue) dataValue.getValue()).getTickersSet().contains(ticker))) {
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
    }

    private PreparedStatement preparedStatementWithLocate(final String tableName, List values) throws DBAccessException {
        if (values == null) {
            values = new ArrayList();
        }
        values.add(0, this.locale);
        return prepareStatement(tableName, values);
    }

    private List<Hotel> getHotelFromDataBase(String sqlCommand) throws DBAccessException {
        List values = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = preparedStatementWithLocate(sqlCommand, values);
            resultSet = execute(statement);
            return getHotelFromResultSet(resultSet);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    private List<Chain> getChainFromDataBase(String sqlCommand) throws DBAccessException {
        List values = new ArrayList();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = preparedStatementWithLocate(sqlCommand, values);
            resultSet = execute(statement);
            return getChainFromResultSet(resultSet);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    private List<Chain> getChainFromResultSet(ResultSet resultSet) throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        List<Chain> establishmentList = new ArrayList<Chain>();
        while (next(resultSet)) {
            //If the Establishment is in the mapper, return this one
            Chain chain = (Chain) getObjectInMapperFromId(Chain.class.getSimpleName(), getInt(resultSet, 1));
            if (chain == null) {
                chain = new Chain();
                chain.setId(getInt(resultSet, 1));
                chain.setTicker(getString(resultSet, 2));
                chain.setPhone(getString(resultSet, 11));
                chain.setEmailAdmin(getString(resultSet, 12));
                chain.setActive(getBoolean(resultSet, 14));
                //i18n attributes
                chain.setName(getTranslation(resultSet, 17, 3));
                chain.setDescription(getTranslation(resultSet, 18, 4));
                //add to the mapper
                putInMapper(chain.getId(), chain.getTicker(), chain);
            }
            establishmentList.add(chain);
        }
        for (final Establishment establishment : establishmentList) {
            if (establishment != null && complexInventory) {
                establishment.setMedia(getImages(KindMedia.ESTABLISHMENT, establishment.getId()));
                List<Media> logo = getImages(KindMedia.LOGO, establishment.getId());
                if (logo != null && !logo.isEmpty()) {
                    establishment.setLogo(logo.get(0));
                }
            }
        }
        return establishmentList;
    }

    private List<Hotel> getHotelFromResultSet(ResultSet resultSet) throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        List<Hotel> hotelList = new ArrayList<Hotel>();
        while (next(resultSet)) {
            //If the Establishment is in the mapper, return this one
            Hotel hotel = (Hotel) getObjectInMapperFromId(Hotel.class.getSimpleName(), getInt(resultSet, 1));
            String type = getString(resultSet, 16);
            //TODO: check if is a Chain
            //if(type.isEmpty()||type.contains("Hotel"))
            if (hotel == null) {
                hotel = new Hotel();
                //Establishment
                hotel.setId(getInt(resultSet, 1));
                hotel.setTicker(getString(resultSet, 2));
                hotel.setPhone(getString(resultSet, 11));
                hotel.setEmailAdmin(getString(resultSet, 12));
                hotel.setActive(getBoolean(resultSet, 14));
                //Hotel
                hotel.setCity(getString(resultSet, 5));
                hotel.setZone(getString(resultSet, 6));
                hotel.setCountry(getString(resultSet, 7));
                hotel.setLatitude(getFloat(resultSet, 8));
                hotel.setLongitude(getFloat(resultSet, 9));
                hotel.setAddress(getString(resultSet, 10));
                hotel.setEmailReservation(getString(resultSet, 13));
                hotel.setValuation(getByte(resultSet, 15));
                //i18n attributes
                hotel.setName(getTranslation(resultSet, 17, 3));
                hotel.setDescription(getTranslation(resultSet, 18, 4));
                hotel.setType(HotelType.getFromCode(type));
                //add to the mapper
                putInMapper(hotel.getId(), hotel.getTicker(), hotel);
            }
            hotelList.add(hotel);
        }
        for (final Establishment establishment : hotelList) {
            if (establishment != null && complexInventory) {
                establishment.setMedia(getImages(KindMedia.ESTABLISHMENT, establishment.getId()));
                List<Media> logo = getImages(KindMedia.LOGO, establishment.getId());
                if (logo != null && !logo.isEmpty()) {
                    establishment.setLogo(logo.get(0));
                }
            }
        }
        return hotelList;
    }

    private List<Accommodation> getAccommodationsFromResultSet(ResultSet resultSet) throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        List<Accommodation> accommodationList = new ArrayList<Accommodation>();
        while (next(resultSet)) {
            //If the Accommodation is in the mapper, return this one
            Accommodation accommodation = (Accommodation) getObjectInMapperFromId(Accommodation.class.getSimpleName(), getInt(resultSet, 1));
            if (accommodation == null) {
                accommodation = new Accommodation();

                accommodation.setId(getInt(resultSet, 1));
                accommodation.setTicker(getString(resultSet, 2));
                accommodation.setColor(getString(resultSet, 7));
                accommodation.setVisible(getBoolean(resultSet, 8));
                accommodation.setOrder(getInt(resultSet, 11));
                accommodation.setDeleted(getBoolean(resultSet, 12));
                //Can find troubles parsing the null values
                accommodation.setDateCreation(tryToGetTimestamp(resultSet, 5));
//                accommodation.setDateCreation(tryToGetTimestamp(resultSet, SQLInstructions.InventoryDBHandler.TableAlojamiento_tipos.CREACION));
                accommodation.setDateModification(tryToGetTimestamp(resultSet, 6));

                //i18n attributes
                accommodation.setName(getTranslation(resultSet, 9, 3));
                accommodation.setDescription(getTranslation(resultSet, 10, 4));
                //add to the mapper
                putInMapper(accommodation.getId(), accommodation.getTicker(), accommodation);

            }
            accommodationList.add(accommodation);
        }
        for (final Accommodation accommodation : accommodationList) {
            if (accommodation != null && complexInventory)
                accommodation.setMedia(getImages(KindMedia.ACCOMMODATIONS, accommodation.getId()));
        }
        return accommodationList;
    }

    private List<Configuration> getConfigurationsFromResultSet(ResultSet resultSet) throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        List<Configuration> configurationList = new ArrayList<Configuration>();
        while (next(resultSet)) {
            //If the Configuration is in the mapper, return this one
            Configuration configuration = (Configuration) getObjectInMapperFromId(Configuration.class.getSimpleName(), getInt(resultSet, 1));
            if (configuration == null) {
                configuration = new Configuration();

                configuration.setId(getInt(resultSet, 1));
                configuration.setTicker(getString(resultSet, 2));

                //i18n attribute
                configuration.setName(getTranslation(resultSet, 9, 3));

                //Can find troubles parsing some values
                configuration.setDateCreation(tryToGetTimestamp(resultSet, 4));
                configuration.setDateModification(tryToGetTimestamp(resultSet, 5));

                int guest = 0;

                guest = getInt(resultSet, 6);
                if (guest > 0) {
                    configuration.getGuests().put(Guest.getAdult(), guest);
                }
                guest = getInt(resultSet, 7);
                if (guest > 0) {
                    configuration.getGuests().put(Guest.getChild(), guest);
                }
                guest = getInt(resultSet, 8);
                if (guest > 0) {
                    configuration.getGuests().put(Guest.getInfant(), guest);
                }

                //add to the mapper
                putInMapper(configuration.getId(), configuration.getTicker(), configuration);
            }
            configurationList.add(configuration);
        }
        return configurationList;
    }

    private List<MealPlan> getMealPlansFromResultSet(ResultSet resultSet) throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        List<MealPlan> mealPlanList = new ArrayList<MealPlan>();
        while (next(resultSet)) {
            //If the MealPlan is in the mapper, return this one
            MealPlan mealPlan = (MealPlan) getObjectInMapperFromId(MealPlan.class.getSimpleName(), getInt(resultSet, 1));
            if (mealPlan == null) {
                mealPlan = new MealPlan();

                mealPlan.setId(getInt(resultSet, 1));
                mealPlan.setTicker(getString(resultSet, 2));
//             mealPlan.setActive(resultSet.getDate());
                mealPlan.setDateModification(tryToGetTimestamp(resultSet, 4));
                mealPlan.setOrder(getInt(resultSet, 6));
                //i18n attribute
                mealPlan.setName(getTranslation(resultSet, 5, 3));
                //add to the mapper
                putInMapper(mealPlan.getId(), mealPlan.getTicker(), mealPlan);
            }
            mealPlanList.add(mealPlan);
        }
        return mealPlanList;
    }

    private List<Condition> getConditionsFromResultSet(ResultSet resultSet) throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        List<Condition> conditionList = new ArrayList<Condition>();
        while (next(resultSet)) {
            //If the Conditions is in the mapper, return this one
            Condition condition = (Condition) getObjectInMapperFromId(Condition.class.getSimpleName(), getInt(resultSet, 1));
            if (condition == null) {
                condition = new Condition();

                condition.setId(getInt(resultSet, 1));
                condition.setTicker(getString(resultSet, 2));
                condition.setEarlyCharge(getFloat(resultSet, 3));
                condition.setMinimumCharge(getFloat(resultSet, 4));
                condition.setDateModification(tryToGetTimestamp(resultSet, 5));
                condition.setColor(getString(resultSet, 6));
                condition.setOrder(getInt(resultSet, 25));
                condition.setPayFirstNight(getBoolean(resultSet, 26));
                //i18n attributes
                String[] functions = new String[]{"setName", "setDescription", "setHtmlEntrada", "setHtmlSalida",
                        "setHtmlCancelaciones", "setHtmlCondNinos", "setHtmlMascotas", "setHtmlGrupos",
                        "setHtmlInfoAdicional"};
                Integer initialValue = 6;
                Class<? extends Condition> c = condition.getClass();
                for (int i = 1; i <= functions.length; i++) {
                    try {
                        final String valueTrans = getString(resultSet, initialValue + (2 * i));
                        final String value = getString(resultSet, initialValue + 2 * i - 1);
                        Method method = c.getDeclaredMethod(functions[i - 1], String.class);
                        method.invoke(condition, (valueTrans != null && !valueTrans.isEmpty())
                                ? valueTrans
                                : value);
                    } catch (NoSuchMethodException e) {
                        logger.error(e);
                    } catch (InvocationTargetException e) {
                        logger.error(e);
                    } catch (IllegalAccessException e) {
                        logger.error(e);
                    }
                }
                //add to the mapper
                putInMapper(condition.getId(), condition.getTicker(), condition);
            }
            conditionList.add(condition);
        }
        return conditionList;
    }

    private List<PaymentType> getPaymentTypesFromResultSet(ResultSet resultSet) throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        List<PaymentType> paymentTypeList = new ArrayList<PaymentType>();
        while (next(resultSet)) {
            //If the MealPlan is in the mapper, return this one
            PaymentType paymentType = (PaymentType) getObjectInMapperFromId(PaymentType.class.getSimpleName(), getInt(resultSet, 1));
            if (paymentType == null) {
                paymentType = new PaymentType();
                paymentType.setId(getInt(resultSet, 1));
                paymentType.setTicker(getString(resultSet, 2));
                paymentType.setName(getString(resultSet, 3));
                paymentType.setDescription(getString(resultSet, 4));
                paymentType.setPaymentPercentage(getFloat(resultSet, 5));
                paymentType.setAdvancePayment(getBoolean(resultSet, 6));
                paymentType.setActive(getBoolean(resultSet, 7));
                paymentType.setDateCreation(tryToGetTimestamp(resultSet, 8));
                paymentType.setDateModification(tryToGetTimestamp(resultSet, 9));
//                paymentType.setOrder(getInt(resultSet, 6));
                //add to the mapper
                putInMapper(paymentType.getId(), paymentType.getTicker(), paymentType);
            }
            paymentTypeList.add(paymentType);
        }
        return paymentTypeList;
    }

    private List<Service> getServicesFromResultSet(ResultSet resultSet) throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        List<Service> serviceList = new ArrayList<Service>();
        while (next(resultSet)) {
            //boolean for validate the correct inventory (Migration V6)
            boolean correct = true;
            //If the Service is in the mapper, return this one
            Service service = (Service) getObjectInMapperFromId(Service.class.getSimpleName(), getInt(resultSet, 1));
            if (service == null) {
                service = new Service();

                service.setId(getInt(resultSet, 1));
                service.setTicker(getString(resultSet, 2));
                //i18n attributes
                service.setName(getTranslation(resultSet, 24, 3));
                service.setDescription(getTranslation(resultSet, 25, 4));
                service.setStartValidPeriod(tryToGetTimestamp(resultSet, 10));
                service.setEndValidPeriod(tryToGetTimestamp(resultSet, 11));
                service.setOrder(getInt(resultSet, 26));
                service.setObligatory(getBoolean(resultSet, 27));
                service.setPromoCode(getString(resultSet, 28));

                //0=habitacion 1=persona 2=unidad
                if (getBoolean(resultSet, 6)) {
                    service.setType((byte) 1);
                } else if (getBoolean(resultSet, 7)) {
                    service.setType((byte) 2);
                } else {
                    service.setType((byte) 0);
                }
                service.setDaily(getBoolean(resultSet, 5));
                service.setMaxUnits(getInt(resultSet, 8));
                service.setActive(getBoolean(resultSet, 9));

                //variables
                try {
                    service.setLock(new LockDataValue(getString(resultSet, 14), getInt(resultSet, 15)));
                    service.setRate(new RateDataValue(getString(resultSet, 12), getInt(resultSet, 13)));
                    //TODO: V7 This attributes Should be eliminated
                    service.setMinStay(new StayDataValue(getString(resultSet, 16), getInt(resultSet, 17)));
                    service.setMaxStay(new StayDataValue(getString(resultSet, 18), getInt(resultSet, 19)));
                    service.setMinNotice(new NoticeDataValue(getString(resultSet, 20), getInt(resultSet, 21)));
                    service.setMaxNotice(new NoticeDataValue(getString(resultSet, 22), getInt(resultSet, 23)));
                } catch (DataValueFormatException ex) {
                    logger.debug(" DataValueFormatException: " + ex.getMessage()
                            + " service.ticker: " + service.getTicker() + " DB:" + getDbConnection().getDbCredentials().getNameDB());
//                    throw DBAccessException.dbParseEntityError(ex, "Error Parsing the Service Object.");
                    correct = false;
                }

                //validate for errors in Migrations V6
                if (correct) {
                    //add to the mapper
                    putInMapper(service.getId(), service.getTicker(), service);
                    if (service != null && service.getId() != null && complexInventory) {
                        service.setMedia(getImages(KindMedia.SERVICES, service.getId()));
                    }
                }
            }
            //validate for errors in Migrations V6
            if (correct) {
                serviceList.add(service);
            }
        }
        return serviceList;
    }

    private List<Discount> getDiscountsFromResultSet(ResultSet resultSet) throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        List<Discount> discountList = new ArrayList<Discount>();
        while (next(resultSet)) {
            //boolean for validate the correct inventory (Migration V6)
            boolean correct = true;
            //If the Discount is in the mapper, return this one
            Discount discount = (Discount) getObjectInMapperFromId(Discount.class.getSimpleName(), getInt(resultSet, 1));
            if (discount == null) {

                discount = new Discount();

                discount.setId(getInt(resultSet, 1));
                discount.setTicker(getString(resultSet, 2));
                discount.setPromoCode(getString(resultSet, 21));
                discount.setActive(getBoolean(resultSet, 22));
                discount.setOrder(getInt(resultSet, 25));

                //ask if the reduction is in percentage or a fixed price
                Float reduction = getFloat(resultSet, 6);
                if (reduction == null || reduction == 0) {
                    reduction = getFloat(resultSet, 26);
                    if (reduction != null && reduction != 0) {
                        discount.setPercentage(false);
                    }
                }
                discount.setReduction(reduction);

                //i18n attributes
                discount.setName(getTranslation(resultSet, 23, 3));
                discount.setDescription(getTranslation(resultSet, 24, 4));

//               try {
//                  discount.setDateCreation(getDate(resultSet,5));
//               } catch (DBAccessException ex) {
//                  discount.setDateCreation(null);
//               }
                discount.setStartValidPeriod(tryToGetTimestamp(resultSet, 9));
                discount.setEndValidPeriod(tryToGetTimestamp(resultSet, 10));
                discount.setStartContractPeriod(tryToGetTimestamp(resultSet, 11));
                discount.setEndContractPeriod(tryToGetTimestamp(resultSet, 12));
                try {
                    discount.setLock(new LockDataValue(getString(resultSet, 7), getInt(resultSet, 8)));
                    discount.setMinStay(new StayDataValue(getString(resultSet, 13), getInt(resultSet, 14)));
                    discount.setMaxStay(new StayDataValue(getString(resultSet, 15), getInt(resultSet, 16)));
                    discount.setMinNotice(new NoticeDataValue(getString(resultSet, 17), getInt(resultSet, 18)));
                    discount.setMaxNotice(new NoticeDataValue(getString(resultSet, 19), getInt(resultSet, 20)));
                } catch (DataValueFormatException ex) {
                    logger.debug(" DataValueFormatException: " + ex.getMessage()
                            + " discount.ticker: " + discount.getTicker() + " DB:" + getDbConnection().getDbCredentials().getNameDB());
//                    throw DBAccessException.dbParseEntityError(ex, "Error Parsing the Discount Object.");
                    correct = false;
                }
                //validate for errors in Migrations V6
                if (correct) {
                    //add to the mapper
                    putInMapper(discount.getId(), discount.getTicker(), discount);
                }
            }
            //validate for errors in Migrations V6
            if (correct) {
                discountList.add(discount);
            }
        }
       /* final String sqlCommand =  "SELECT DISTINCT promocione_id,tiposhab_id FROM promocionestiposhabs;";
        PreparedStatement statement = null;
        ResultSet discountResultSet = null;

        try {
            statement = prepareStatement(sqlCommand);
            resultSet = execute(statement);
            while (next(resultSet)) {
                final Integer discountId = getInt(resultSet, 1);
                final Integer inventoryId = getInt(resultSet, 2);
                Discount currentDiscount= (Discount)getInMapper(Discount.class.toString(),discountId);
                Inventory currentInventory= (Inventory) getInMapper(Inventory.class.toString(), inventoryId);
                if(currentDiscount!=null && currentInventory!=null){
                    currentDiscount.getInventoryTickerAppliedList().add(currentInventory.getTicker());
                    currentInventory.getDiscountList().add(currentDiscount);
                }
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }*/
        return discountList;
    }

    private List<Inventory> getInventoriesFromResultSet(ResultSet resultSet) throws DBAccessException {
        if (resultSet == null) {
            return null;
        }
        List<Inventory> inventoryList = new ArrayList<Inventory>();
        while (next(resultSet)) {
            //boolean for validate the correct inventory (Migration V6)
            boolean correct = true;
            //If The Inventory is in the mapper, return this one
            Inventory inventory = (Inventory) getObjectInMapperFromId(Inventory.class.getSimpleName(), getInt(resultSet, 1));
            if (inventory == null) {
                inventory = new Inventory();

                inventory.setId(getInt(resultSet, 1));
                inventory.setTicker(getString(resultSet, 2));
                inventory.setHotelRef(getString(resultSet, 4));
                inventory.setDateCreation(tryToGetTimestamp(resultSet, 5));
                inventory.setDateModification(tryToGetTimestamp(resultSet, 6));
                inventory.setVisible(getBoolean(resultSet, 7));
                inventory.setAccessCode(getString(resultSet, 8));
                inventory.setRack((float) getInt(resultSet, 33));
                inventory.setDeleted(getBoolean(resultSet, 34));

                //i18n attribute
                inventory.setName(getTranslation(resultSet, 30, 3));
                //variables
                try {
                    final String rateString = getString(resultSet, 9);
                    final String availabilityString = getString(resultSet, 11);
                    correct = availabilityString != null && !availabilityString.isEmpty()
                            && rateString != null && !rateString.isEmpty();
                    inventory.setRate(new RateDataValue(rateString, getInt(resultSet, 10)));
                    inventory.setAvailability(new AvailabilityDataValue(availabilityString, getInt(resultSet, 12)));
                    inventory.setLock(new LockDataValue(getString(resultSet, 21), getInt(resultSet, 22)));
                    inventory.setMinStay(new StayDataValue(getString(resultSet, 13), getInt(resultSet, 14)));
                    inventory.setMaxStay(new StayDataValue(getString(resultSet, 15), getInt(resultSet, 16)));
                    inventory.setMinNotice(new NoticeDataValue(getString(resultSet, 17), getInt(resultSet, 18)));
                    inventory.setMaxNotice(new NoticeDataValue(getString(resultSet, 19), getInt(resultSet, 20)));
                } catch (DataValueFormatException ex) {
                    logger.debug(" DataValueFormatException: " + ex.getMessage()
                            + " inventory.ticker: " + inventory.getTicker() + " DB:" + getDbConnection().getDbCredentials().getNameDB());
                    //delete this error throws, for migrations V6
                    //throw DBAccessException.dbParseEntityError(ex, "Error Parsing the Inventory Object.");
                    correct = false;
                }
                inventory.setCheckInDays(new DaysCondition(getString(resultSet, 23)));
                inventory.setCheckOutDays(new DaysCondition(getString(resultSet, 24)));

                //This is a validation to simple Type Of InventoryHandler (we don't need the 4 bucket)
                if (complexInventory) {
                    inventory.setAccommodation(getAccommodationById(getInt(resultSet, 26)));
                    inventory.setConfiguration(getConfigurationById(getInt(resultSet, 27)));
                    inventory.setMealPlan(getMealPlanById(getInt(resultSet, 28)));
                    inventory.setCondition(getConditionById(getInt(resultSet, 29)));
                    //TODO: Right now not using the taxes on the platform, see if it should be added
//                inventory.setTax(null);//25
                } else {
                    inventory.setAccommodation(getAccommodationById(-1));
                    inventory.setConfiguration(getConfigurationById(-1));
                    inventory.setMealPlan(getMealPlanById(-1));
                    inventory.setCondition(getConditionById(-1));
                }
                try {
                    inventory.setDateStartValidation(getDate(resultSet, 31));
                    inventory.setDateEndValidation(getDate(resultSet, 32));
                } catch (DBAccessException ex) {
                    logger.error(" SQLException: " + ex.getMessage()
                            + " inventory.ticker: " + inventory.getTicker() + " DB:" + getDbConnection().getDbCredentials().getNameDB());
                }
                //validate for errors in Migrations V6
                if (correct) {
                    //add to the mapper
                    putInMapper(inventory.getId(), inventory.getTicker(), inventory);
//                    logger.debug(inventory);
                }
            }
            //validate for errors in Migrations V6
            if (correct) {
                inventoryList.add(inventory);
            }
        }
        return inventoryList;
    }

    protected Map<String, Map<Integer, Object>> getEntityHash() {
        return entityHash;
    }

    protected void setEntityHash(Map<String, Map<Integer, Object>> entityHash) {
        this.entityHash = entityHash;
    }

    protected Map<String, Map<String, Integer>> getTickerHash() {
        return tickerHash;
    }

    protected void setTickerHash(Map<String, Map<String, Integer>> tickerHash) {
        this.tickerHash = tickerHash;
    }

    protected Integer getIdInMapperFromTicker(String classSimpleName, String ticker) throws DBAccessException {
        if (tickerHash.get(classSimpleName) != null) {
            return tickerHash.get(classSimpleName).get(ticker);
        } else {
            fillObjectMapper(classSimpleName);
            return tickerHash.get(classSimpleName).get(ticker);
        }
    }

    protected Object getObjectInMapperFromId(String classSimpleName, int id) throws DBAccessException {
        if (entityHash.get(classSimpleName) != null) {
            return entityHash.get(classSimpleName).get(id);
        } else {
            fillObjectMapper(classSimpleName);
            return entityHash.get(classSimpleName).get(id);
        }
    }

    public Object getObjectInMapperFromTicker(String classSimpleName, String ticker) throws DBAccessException {
        Integer id = getIdInMapperFromTicker(classSimpleName, ticker);
        if (id != null) {
            return getObjectInMapperFromId(classSimpleName, id);
        }
        return null;
    }

    protected void putInMapper(int id, String ticker, Object object) {
        Map<Integer, Object> mapper;
        Map<String, Integer> mapperTicker;
        if (entityHash.get(object.getClass().getSimpleName()) != null) {
            mapper = entityHash.get(object.getClass().getSimpleName());
        } else {
            mapper = new HashMap<Integer, Object>();
            entityHash.put(object.getClass().getSimpleName(), mapper);
        }
        if (mapper.get(id) == null) {
            mapper.put(id, object);
            if (ticker != null || !"".equals(ticker)) {
                if (tickerHash.get(object.getClass().getSimpleName()) != null) {
                    mapperTicker = tickerHash.get(object.getClass().getSimpleName());
                } else {
                    mapperTicker = new HashMap<String, Integer>();
                    tickerHash.put(object.getClass().getSimpleName(), mapperTicker);
                }
                mapperTicker.put(ticker, id);
            }
        } else {
            mapper.put(id, object);
//            logger.debug("The object is already in the entityMapper");
        }
    }

    public String printMapper() {
        String print = "entityHash{";
        for (Map.Entry<String, Map<Integer, Object>> entry : entityHash.entrySet()) {
            String string = entry.getKey();
            print = print + string + "{ ";
            Map<Integer, Object> map = entry.getValue();
            for (Map.Entry<Integer, Object> entry1 : map.entrySet()) {
                Integer integer = entry1.getKey();
                Object object = entry1.getValue();
                print = print + integer + ":" + object + ", ";
            }
            print = print + " }";
        }
        print = print + " }";
        return print;
    }

    public String printTickers() {
        String print = "entityHash{";
        for (Map.Entry<String, Map<String, Integer>> entry1 : tickerHash.entrySet()) {
            String string = entry1.getKey();
            Map integer = entry1.getValue();
            print = print + string + ":" + integer.values().toArray() + ", ";
        }
        print = print + " }";
        return print;
    }

    /**
     * Methods to fill the Mappers
     */
    public List getFullListObjectFromMapper(String classSimpleName) throws DBAccessException {
        if (classSimpleName == null) {
            return null;
        }
        if (entityHash.get(classSimpleName) != null) {
            return new ArrayList(entityHash.get(classSimpleName).values());
        } else {
            List list = fillObjectMapper(classSimpleName);
            if (entityHash.get(classSimpleName) != null) {
                return new ArrayList(entityHash.get(classSimpleName).values());
            } else {
                entityHash.put(classSimpleName, new HashMap<Integer, Object>());
                return list;
            }
        }
    }

    public List fillObjectMapper(String classSimpleName) throws DBAccessException {
        if (classSimpleName == null) {
            logger.error("Class: '" + classSimpleName + "' not Found.");
            return null;
        }
        tickerHash.put(classSimpleName, new HashMap<String, Integer>());
        entityHash.put(classSimpleName, new HashMap<Integer, Object>());
        if (classSimpleName.equals(Inventory.class.getSimpleName())) {
            return getAllInventories();
        } else if (classSimpleName.equals(Discount.class.getSimpleName())) {
            return getAllDiscounts();
        } else if (classSimpleName.equals(Service.class.getSimpleName())) {
            return getAllServices();
        } else if (classSimpleName.equals(Accommodation.class.getSimpleName())) {
            return getAllAccommodations();
        } else if (classSimpleName.equals(Condition.class.getSimpleName())) {
            return getAllConditions();
        } else if (classSimpleName.equals(MealPlan.class.getSimpleName())) {
            return getAllMealPlan();
        } else if (classSimpleName.equals(Configuration.class.getSimpleName())) {
            return getAllConfigurations();
        } else if (classSimpleName.equals(Tax.class.getSimpleName())) {
            return getAllTaxes();
        } else if (classSimpleName.equals(Hotel.class.getSimpleName())) {
            return getAllHotels();
        } else if (classSimpleName.equals(Chain.class.getSimpleName())) {
            return getAllChains();
        } else if (classSimpleName.equals(PaymentType.class.getSimpleName())) {
            return getAllPaymentTypes();
        }
        logger.error("Class: '" + classSimpleName + "' not Found.");
        return null;
    }

    public List<Hotel> getAllHotels() throws DBAccessException {
        final String sqlCommand = SQLInstructions.InventoryDBHandler.SELECT_ESTABLISHMENT
                + "Limit 1;";
        return getHotelFromDataBase(sqlCommand);
    }

    public List<Chain> getAllChains() throws DBAccessException {
        final String sqlCommand = SQLInstructions.InventoryDBHandler.SELECT_ESTABLISHMENT
                + "Limit 1;";
        return getChainFromDataBase(sqlCommand);
    }


    public List<String> getAllVariables() throws DBAccessException {
        final String sqlCommand = SQLInstructions.InventoryDBHandler.SELECT_VARIABLES + ";";
        List<String> variables = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(sqlCommand);
            resultSet = execute(statement);
            while (next(resultSet)) {
                variables.add(getString(resultSet, "ticker"));
            }
            return variables;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }


    public List<Tax> getAllTaxes() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = preparedStatementWithLocate(SQLInstructions.InventoryDBHandler.SELECT_TAXES + ";", null);
            resultSet = execute(statement);
            List<Tax> taxes = new ArrayList<Tax>();
            while (next(resultSet)) {
                final String name = (getString(resultSet, 5) != null && !getString(resultSet, 5).equals(""))
                        ? getString(resultSet, 5)
                        : getString(resultSet, 2);
                Tax tax = new Tax(getInt(resultSet, 1), getString(resultSet, 4), name, getFloat(resultSet, 3));
                taxes.add(tax);
                //add to the mapper
                putInMapper(tax.getId(), tax.getTicker(), tax);
            }
            return taxes;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<Configuration> getAllConfigurations() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = preparedStatementWithLocate(SQLInstructions.InventoryDBHandler.SELECT_CONFIGURATION, null);
            resultSet = execute(statement);
            return getConfigurationsFromResultSet(resultSet);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<MealPlan> getAllMealPlan() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = preparedStatementWithLocate(SQLInstructions.InventoryDBHandler.SELECT_MEAL_PLAN, null);
            resultSet = execute(statement);
            return getMealPlansFromResultSet(resultSet);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<Condition> getAllConditions() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = preparedStatementWithLocate(SQLInstructions.InventoryDBHandler.SELECT_CONDITION, null);
            resultSet = execute(statement);
            List<Condition> conditionList = getConditionsFromResultSet(resultSet);
            if (complexInventory) {
                addPaymentTypesToConditions();
            }
            return conditionList;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<PaymentType> getAllPaymentTypes() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.InventoryDBHandler.SELECT_PAYMENT_TYPE);
            resultSet = execute(statement);
            return getPaymentTypesFromResultSet(resultSet);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<Accommodation> getAllAccommodations() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = preparedStatementWithLocate(SQLInstructions.InventoryDBHandler.SELECT_ACCOMMODATION, null);
            resultSet = execute(statement);
            return getAccommodationsFromResultSet(resultSet);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<Discount> getAllDiscounts() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = preparedStatementWithLocate(SQLInstructions.InventoryDBHandler.SELECT_DISCOUNT, null);
            resultSet = execute(statement);
            List<Discount> discountList = getDiscountsFromResultSet(resultSet);
            if (entityHash.get(Inventory.class.getSimpleName()) != null
                    && !entityHash.get(Inventory.class.getSimpleName()).isEmpty()) {
                addDiscountsToInventories();
            }
            return discountList;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<Service> getAllServices() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = preparedStatementWithLocate(SQLInstructions.InventoryDBHandler.SELECT_SERVICE, null);
            resultSet = execute(statement);
            List<Service> serviceList = getServicesFromResultSet(resultSet);
            if (entityHash.get(Inventory.class.getSimpleName()) != null
                    && !entityHash.get(Inventory.class.getSimpleName()).isEmpty()
                    && complexInventory) {
                addServicesToInventories();
            }
            return serviceList;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    public List<Inventory> getAllInventories() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = preparedStatementWithLocate(SQLInstructions.InventoryDBHandler.SELECT_INVENTORY, null);
            resultSet = execute(statement);
            List<Inventory> inventoryList = getInventoriesFromResultSet(resultSet);
            if (entityHash.get(Discount.class.getSimpleName()) != null
                    && !entityHash.get(Discount.class.getSimpleName()).isEmpty()) {
                addDiscountsToInventories();
            }
            if (entityHash.get(Service.class.getSimpleName()) != null
                    && !entityHash.get(Service.class.getSimpleName()).isEmpty()
                    && complexInventory) {
                addServicesToInventories();
            }
            return inventoryList;
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    //This link all the discounts in the Mapper to the Inventories in the Mapper
    private void addDiscountsToInventories() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.InventoryDBHandler.SELECT_INVENTORIES_AND_DISCOUNTS_LINKED);
            resultSet = execute(statement);
            while (next(resultSet)) {
                final Integer discountId = getInt(resultSet, 1);
                final Integer inventoryId = getInt(resultSet, 2);
                Discount discount = (Discount) getObjectInMapperFromId(Discount.class.getSimpleName(), discountId);
                Inventory inventory = (Inventory) getObjectInMapperFromId(Inventory.class.getSimpleName(), inventoryId);
                if (discount != null && inventory != null) {
//                    discount.getInventoryTickerAppliedList().add(inventory.getTicker());
                    inventory.addDiscount(discount);
                }
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }

    //This link all the Services in the Mapper to the Inventories in the Mapper
    private void addServicesToInventories() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.InventoryDBHandler.SELECT_INVENTORIES_AND_SERVICES_LINKED);
            resultSet = execute(statement);
            while (next(resultSet)) {
                final Integer serviceId = getInt(resultSet, 1);
                final Integer inventoryId = getInt(resultSet, 2);
                Service service = (Service) getObjectInMapperFromId(Service.class.getSimpleName(), serviceId);
                Inventory inventory = (Inventory) getObjectInMapperFromId(Inventory.class.getSimpleName(), inventoryId);
                if (service != null && inventory != null) {
//                    service.getInventoryTickerAppliedList().add(inventory.getTicker());
                    inventory.addService(service);
                }
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }


    //This links all the PaymentTypes in the Mapper to the Conditions in the Mapper
    private void addPaymentTypesToConditions() throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            statement = prepareStatement(SQLInstructions.InventoryDBHandler.SELECT_CONDITIONS_AND_PAYMENTS_LINKED);
            resultSet = execute(statement);
            while (next(resultSet)) {
                final Integer conditionId = getInt(resultSet, 1);
                final Integer paymentTypeId = getInt(resultSet, 2);
                PaymentType paymentType = (PaymentType) getObjectInMapperFromId(PaymentType.class.getSimpleName(), paymentTypeId);
                Condition condition = (Condition) getObjectInMapperFromId(Condition.class.getSimpleName(), conditionId);
                if (paymentType != null && condition != null) {
                    condition.addPaymentType(paymentType);
                }
            }
        } finally {
            DAOUtil.close(statement, resultSet);
        }
    }
}