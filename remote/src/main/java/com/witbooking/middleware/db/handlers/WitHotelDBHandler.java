/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.witbooking.middleware.db.handlers;

import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.sqlinstructions.SQLInstructions;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.*;
import com.witbooking.middleware.resources.DBProperties;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author jose fiorillo
 */
public class WitHotelDBHandler extends DBHandler {

    private static final Logger logger = Logger.getLogger(WitMetaDataDBHandler.class);

    public WitHotelDBHandler() throws ExternalFileException, DBAccessException, NonexistentValueException {
        this.setDbConnection(new DBConnection(DBProperties.getDBSupportByTicker(SQLInstructions.WitHotelDBHandler.DB_WITMETADATA_TICKER)));
    }

    public WitHotelDBHandler(DBConnection dbConnection) {
        super(dbConnection);
    }

    public WitHotel getHotelByTicker(String hotelTicker) throws DBAccessException {
        WitHotel hotel;
        ArrayList<Object> values = new ArrayList<Object>(1);
        values.add(hotelTicker);
        PreparedStatement statement = prepareStatement(SQLInstructions.WitHotelDBHandler.GET_HOTEL_SQL_COMMAND, values);
        ResultSet resultSet = execute(statement);
        if (next(resultSet)) {
            hotel = new WitHotel(getInt(resultSet, 1), getString(resultSet, 2), getString(resultSet, 3));
        } else {
            hotel = null;
        }
        return hotel;
    }

    public Properties getConfigurationsByTicker(final String hotelTicker) throws DBAccessException {
        Properties props = new Properties();
        ArrayList<Object> values = new ArrayList<Object>(1);
        values.add(hotelTicker);
        PreparedStatement statement = prepareStatement(SQLInstructions.WitHotelDBHandler.GET_CONFIGURATIONS_SQL_COMMAND, values);
        ResultSet resultSet = execute(statement);
        while (next(resultSet)) {
            props.put(getString(resultSet, 1), getString(resultSet, 2));
        }
        DAOUtil.close(statement, resultSet);
        return props;
    }

    public List<LocationPoint> getLocationPointsByTicker(final String hotelTicker, final String locale) throws DBAccessException {
        List<LocationPoint> locations = new ArrayList<LocationPoint>();
        ArrayList<Object> values = new ArrayList<Object>(1);
        values.add(locale);
        values.add(hotelTicker);
        PreparedStatement statement = prepareStatement(SQLInstructions.WitHotelDBHandler.GET_LOCATIONS_POINT_SQL_COMMAND, values);
        ResultSet resultSet = execute(statement);
        while (next(resultSet)) {
            LocationPoint item = new LocationPoint(getInt(resultSet, 1), getString(resultSet, 2), getString(resultSet, 3),
                    getString(resultSet, 4), getString(resultSet, 8), getFloat(resultSet, 5), getFloat(resultSet, 6),
                    getString(resultSet, 7));
            //i18n attribute
            item.setName(getTranslation(resultSet, 10, 9));
            item.setDescription(getTranslation(resultSet, 12, 11));
            //item.setType(getString(resultSet,8));
            locations.add(item);
        }
        DAOUtil.close(statement, resultSet);
        return locations;
    }

    public List<String> getLocationsTypes() throws DBAccessException {
        List<String> locationsTypes = new ArrayList<String>();
        PreparedStatement statement = prepareStatement(SQLInstructions.WitHotelDBHandler.GET_LOCATIONS_POINT_TYPES_SQL_COMMAND);
        ResultSet resultSet = execute(statement);
        while (next(resultSet)) {
            locationsTypes.add(getString(resultSet, 1));
        }
        DAOUtil.close(statement, resultSet);
        return locationsTypes;
    }


    public List<Review> getReviewByTicker(final String hotelTicker, final String language) throws DBAccessException {
        List<Review> reviews = new ArrayList<Review>();
        ArrayList<Object> values = new ArrayList<Object>(2);
        values.add(hotelTicker);
        values.add(language);
        PreparedStatement statement = prepareStatement(SQLInstructions.WitHotelDBHandler.GET_REVIEWS_SQL_COMMAND, values);
        ResultSet resultSet = execute(statement);
        while (next(resultSet)) {
            Review item = new Review(getInt(resultSet, 1), getString(resultSet, 3), getString(resultSet, 4), getString(resultSet, 5),
                    getString(resultSet, 6), getString(resultSet, 7), getString(resultSet, 8),
                    getBoolean(resultSet, 9), null);
            //item.setType(getString(resultSet,8));
            reviews.add(item);
        }
        DAOUtil.close(statement, resultSet);
        return reviews;
    }

    public List<Page> getPages(final String locale) throws DBAccessException {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        List values = new ArrayList();
        List<Page> pages = new ArrayList<Page>();
        try {
            values.add(locale);
            statement = prepareStatement(SQLInstructions.WitHotelDBHandler.GET_PAGES, values);
            resultSet = execute(statement);
            pages = getPagesFromResultSet(resultSet, locale);
        } finally {
            DAOUtil.close(statement, resultSet);
        }
        return pages;
    }

    private List<Page> getPagesFromResultSet(final ResultSet resultSet, final String locale) throws DBAccessException {
        List<Page> pages = new ArrayList<Page>();
        final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(getDbConnection(), locale);
        while (next(resultSet)) {
            final int id = getInt(resultSet, 1);
            final String ticker = getString(resultSet, 2);
            final String title = getTranslation(resultSet, 7, 3);
            final String description = getTranslation(resultSet, 8, 4);
            final String seo = getString(resultSet, 5);
            final Integer parent = getInt(resultSet, 6);
            final Page page = new Page(id, ticker, title, description, seo, inventoryDBHandler.getImages(KindMedia.PAGES, id));
            if (!checkContainsPageId(pages, parent, page))
                pages.add(page);
        }
        return pages;
    }

    private Boolean checkContainsPageId(List<Page> page, int id, Page newPage) {
        if (page != null && !page.isEmpty()) {
            for (final Page pageDummy : page) {
                if (pageDummy.getId() == id) {
                    pageDummy.addChildren(newPage);
                    return true;
                } else if (pageDummy.getChildren() != null && checkContainsPageId(pageDummy.getChildren(), id, newPage)) {
                    return true;
                }
            }
        }
        return false;
    }
}