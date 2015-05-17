/*
 *  DBProperties.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.resources;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 15-abr-2013
 */
public class DBProperties {
    /**
     * Object used to report on the Server's Log using Apache log4j.
     */
    private static final Logger logger = Logger.getLogger(DBProperties.class);


    /**
     * Creates a new instance of
     * <code>DBProperties</code> without jsonCredentials.
     */
    public DBProperties() {
    }

    public static DBCredentials getDBSupportByTicker(String dbTicker) throws ExternalFileException, NonexistentValueException {

        String supportDBFile = "";
        JsonObject jsonCredentials = null;

        //Parsing the file with the DB credentials, in JSON format
        FileInputStream file = null;
        try {
            file = new FileInputStream(MiddlewareProperties.SUPPORT_DB_FILE);
            byte[] b = new byte[file.available()];
            file.read(b);
            supportDBFile = new String(b, "UTF-8");
        } catch (FileNotFoundException ex) {
            logger.error("Support DB file not found ");
            throw new ExternalFileException(ex, MiddlewareProperties.SUPPORT_DB_FILE);
        } catch (IOException ex) {
            logger.error("Problem reading the Support DB File.");
            throw new ExternalFileException(ex);
        } finally {
            if (file != null)
                try {
                    file.close();
                } catch (IOException e) {
                    logger.error(e);
                }
        }
        try {
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(supportDBFile).getAsJsonObject().get(dbTicker);
            if (jsonElement == null) {
                logger.info("DataBase '" + dbTicker + "' Not found.");
                throw new NonexistentValueException("DataBase '" + dbTicker + "' Not found.");
            }
            jsonCredentials = jsonElement.getAsJsonObject();
            if (jsonCredentials == null) {
                logger.info("DataBase '" + dbTicker + "' Not found.");
                throw new NonexistentValueException("DataBase " + dbTicker + " Not found.");
            }
            return getDBCredentialsFromJson(jsonCredentials, dbTicker);
        } catch (Exception ex) {
            logger.error("There is something wrong with the Support DB File.");
            throw new ExternalFileException(ex);
        }
    }

    public static DBCredentials getDBCustomerByTicker(String hotelTicker) throws ExternalFileException, NonexistentValueException {
        String customersDBFile = "";
        JsonObject jsonCredentials = null;

        //Parsing the file with the DB credentials, in JSON format
        FileInputStream file = null;
        try {
            file = new FileInputStream(MiddlewareProperties.CUSTOMERS_DB_FILE);
            byte[] b = new byte[file.available()];
            file.read(b);
            customersDBFile = new String(b, "UTF-8");
        } catch (FileNotFoundException ex) {
            logger.error("Customers DB file not found.");
            throw new ExternalFileException(ex, MiddlewareProperties.CUSTOMERS_DB_FILE);
        } catch (IOException ex) {
            logger.error("Problem reading the Customers DB File.");
            throw new ExternalFileException(ex);
        } finally {
            if (file != null)
                try {
                    file.close();
                } catch (IOException e) {
                    logger.error(e);
                }
        }
        try {
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(customersDBFile).getAsJsonObject().get(hotelTicker);
            if (jsonElement == null) {
                logger.info("DataBase '" + hotelTicker + "' Not found.");
                throw new NonexistentValueException("DataBase '" + hotelTicker + "' Not found.");
            }
            jsonCredentials = jsonElement.getAsJsonObject();
            if (jsonCredentials == null) {
                logger.info("DataBase '" + hotelTicker + "' Not found.");
                throw new NonexistentValueException("DataBase '" + hotelTicker + "' Not found.");
            }
            return getDBCredentialsFromJson(jsonCredentials, hotelTicker);
        } catch (Exception ex) {
            logger.error("There is something wrong with the Customers DB File. "+ ex);
            throw new ExternalFileException(ex);
        }
    }

    private static DBCredentials getDBCredentialsFromJson(JsonObject jsonCredentials, String ticker) throws
            ExternalFileException {
        DBCredentials dbCredential = new DBCredentials();
        dbCredential.setHost(jsonCredentials.get("host").getAsString());
        dbCredential.setNameDB(jsonCredentials.get("database").getAsString());
        dbCredential.setUserDB(jsonCredentials.get("login").getAsString());
        dbCredential.setPassDB(jsonCredentials.get("password").getAsString());
        dbCredential.setTicker(ticker);
        return dbCredential;
    }
}
