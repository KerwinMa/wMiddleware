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
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.Currency;
import com.witbooking.middleware.model.CurrencyExchange;
import com.witbooking.middleware.resources.DBProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 31-ene-2013
 */
public class CurrencyDBHandler extends DBHandler {


    /**
     * Creates a new instance of
     * <code>HotelConfigurationDBHandler</code> without params.
     */
    public CurrencyDBHandler() throws ExternalFileException, DBAccessException, NonexistentValueException {
        this.setDbConnection(new DBConnection(DBProperties.getDBSupportByTicker(SQLInstructions.CurrencyDBHandler.DB_CURRENCY_TICKER)));
    }

    public CurrencyDBHandler(DBConnection dbConnection) {
        super(dbConnection);
    }

    public CurrencyExchange getCurrencyExchange(String currency) throws DBAccessException, NonexistentValueException {
        Map<String, Float> prices = new HashMap<String, Float>();
        List values = new ArrayList();
        values.add(currency);
        values.add(currency);
        PreparedStatement statement = prepareStatement(SQLInstructions.CurrencyDBHandler.GET_CURRENCY_EXCHANGE_SQL_COMMAND, values);
        ResultSet resultSet = execute(statement);
        while (next(resultSet)) {
            prices.put(getString(resultSet, 1), getFloat(resultSet, 2));
        }
        DAOUtil.close(statement, resultSet);
        if(prices.size() == 0) {
            throw new NonexistentValueException("Invalid Currency Value");
        }
        return new CurrencyExchange(currency, prices);
    }

    public Currency getCurrency(String code) throws DBAccessException {
        List<Object> values = new ArrayList<Object>();
        values.add(code);
        PreparedStatement statement = prepareStatement(SQLInstructions.CurrencyDBHandler.GET_CURRENCY_SQL_COMMAND, values);
        ResultSet resultSet = execute(statement);
        if (next(resultSet)) {
            Currency currency = new Currency(getString(resultSet, 1), getString(resultSet, 2));
            DAOUtil.close(statement, resultSet);
            return currency;
        } else {
            DAOUtil.close(statement, resultSet);
            throw new DBAccessException("Currency " + code + " not found.");
        }
    }

    /*
     * Function make to test get all Currencies and test the function getCurrency.
     */
    public List<Currency> getAllCurrenciesActives() throws DBAccessException {
        List<Currency> values = new ArrayList<Currency>();
        PreparedStatement statement = prepareStatement(SQLInstructions.CurrencyDBHandler.GET_ALL_CURRENCIES_ACTIVES_SQL_COMMAND);
        ResultSet resultSet = execute(statement);
        while (next(resultSet)) {
            values.add(new Currency(getString(resultSet, 1), getString(resultSet, 2)));
        }
        return values;
    }
}