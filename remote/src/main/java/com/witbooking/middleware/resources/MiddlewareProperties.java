/*
 *  MiddlewareProperties.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.resources;

import com.google.common.io.BaseEncoding;
import com.witbooking.middleware.exceptions.PropertiesException;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 15-abr-2013
 */
public class MiddlewareProperties {

    /**
     * Internal flow logger for ApplicationProperties
     */
    static final Logger logger = Logger.getLogger(MiddlewareProperties.class);
    /**
     * Instance of MiddlewareProperties for the singleton class
     */
    private static MiddlewareProperties _instance = new MiddlewareProperties();
    /**
     * Configuration file Absolute Path
     */
    public static String CONFIG_FILE;
    public static String GEO_IP_DB_FILEPATH;
    public static String URL_WITBOOKER_V6;
    public static String URL_WITBOOKER_V5;
    public static String HTTP_AUTH_USER;
    public static String HTTP_AUTH_PASS;
    public static String TRIVAGO_HOTELS_JSON_FILE;
    public static String CUSTOMERS_DB_FILE;
    public static String SUPPORT_DB_FILE;
    public static String PROPERTY_RESERVATION_ALLOWED;
    public static String URL_TRIVAGO_V5;
    public static String SITEMINDER_RES_URL;
    public static String MANDRILL_API_KEY;
    public static String REDIS_SERVER;
    /**
     * Those values are stored here to create
     * <p/>
     * and logger purposes.
     */
    public static String BOOKING_ARI_URL;
    public static String BOOKING_RESERVATION_URL;
    /**
     * Those values are charged based on {@link #BOOKING_ARI_URL}.
     */
    public static String BOOKING_URL_AVAIL_NOTIF;
    public static String BOOKING_URL_RATE_AMOUNT_NOTIF;
    /**
     * Those values are charged based on {@link #BOOKING_RESERVATION_URL}
     */
    public static String BOOKING_URL_RESERVATION;
    public static String BOOKING_URL_MODIFIED_RESERVATIONS;

    public static String BOOKING_USER, BOOKING_PASSWORD, BOOKING_ENCODE;

    /**
     * TripAdvisor properties.
     */
    public static String TRIP_ADVISOR_KEY;
    public static String TRIP_ADVISOR_URL;

    public static String SITEMINDER_USER_ID;
    public static String SITEMINDER_PASSWORD;
    public static String RATEGAIN_RES_URL;
    public static String RATEGAIN_USER_ID;
    public static String RATEGAIN_PASSWORD;
    //Url used to tiny urls
    public static String URL_TINYURL;
    //Params to configure Mail Scheduler
    public static String SCHEDULER_MAIL_HOUR;
    public static String SCHEDULER_MAIL_MIN;
    public static String SCHEDULER_MAIL_SEG;
    //Params to configure Transfers Scheduler
    public static String SCHEDULER_TRANSFERS_HOUR;
    public static String SCHEDULER_TRANSFERS_MIN;
    public static String SCHEDULER_TRANSFERS_SEG;
    //Params to configure Booking.com Scheduler
    public static String SCHEDULER_BOOKING_HOUR;
    public static String SCHEDULER_BOOKING_MIN;
    public static String SCHEDULER_BOOKING_SEG;
    //Params to configure Review Reservations Scheduler
    public static String SCHEDULER_RESERVATIONS_HOUR;
    public static String SCHEDULER_RESERVATIONS_MIN;
    public static String SCHEDULER_RESERVATIONS_SEG;
    //Params to configure Integration Executor
    public static String SCHEDULER_EXECUTOR_HOUR;
    public static String SCHEDULER_EXECUTOR_MIN;
    public static String SCHEDULER_EXECUTOR_SEG;
    public static String DAYS_BEFORE_DEFAULT;
    //File address channel logger.
    public static String CHANNELS_LOGGER_DIR;
    //Integration Manager Max number of resend
    public static Integer INTEGRATION_MANAGER_MAX_RESEND;
    //Mails address to send
    public static List<String> NOTIFICATIONS_EMAIL_RECIPIENTS;
    public static String NOTIFICATIONS_EMAIL_ADDRESS;
    public static String NOTIFICATIONS_EMAIL_PASSWORD;
    public static String STATIC_ROOT_URL;
    //days to delete the creditCards
    public static String DAYS_BEFORE_DELETE_CC;

    //Name servers.
    public static String PROD_SERVER_NAME = "";
    public static String TEST_SERVER_NAME = "";
    public static String LUKE_SERVER_NAME = "";

    //static final Witbooking information
    public static final String WITBOOKING_PHONE = "+34-930-013-880";
    public static final String INTEGRATION_MANAGER_EMAIL = "c.delgado@witbooking.com";
    public static final String INTEGRATION_MANAGER_FULL_NAME = "Christian Delgado";
    public static final String SUPPORT_MAIL = "support@witbooking.com";
    public static final String SUPPORT_MAIL_ES = "soporte@witbooking.com";
    public static final String SUPPORT_FULL_NAME = "Support Witbooking";
//    public static final String = "";


    //Those variables can not be defined in witbooking.properties.
    //Activate Scheduler of integration
    public static String ACTIVATE_SCHEDULER_INTEGRATION = "true";
    //Activate Transfer Scheduler
    public static String ACTIVATE_SCHEDULER_TRANSFERS_VALIDATION = "true";
    //Activate mailing Scheduler of integration
    public static String ACTIVATE_MAILING_SCHEDULER_INTEGRATION = "true";
    private static final boolean NOT_FOUND_PROPERTY_EXCEPTION_ACTIVATED = true, NOT_FOUND_PROPERTY_EXCEPTION_DEACTIVATE = false;

    private MiddlewareProperties() {
        //Environment file inside of EAR, determine path for the Properties File
        CONFIG_FILE = "../environment.properties";
        try {
            Properties properties = new Properties();
            InputStream input = null;
            try {
                input = this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE);

                if (input == null) {
                    // Loads the Properties file, in the file system. (for testing)
                    input = new FileInputStream(CONFIG_FILE);
                }
                properties.load(input);
            } finally {
                if (input != null)
                    input.close();
            }
//         logger.debug(" environment.properties: " + properties);
            String value;
            if ((value = properties.getProperty("ENVIRONMENT")) == null) {
                throw PropertiesException.propertyNotFound("ENVIRONMENT");
            }
            if ((value = properties.getProperty(value.trim())) == null) {
                throw PropertiesException.propertyNotFound(value);
            }
            CONFIG_FILE = value.trim();
        } catch (Throwable e) {
            File file = new File(CONFIG_FILE);
            System.out.println("Environment file location: " + file.getAbsolutePath());
            logger.error("Error with the Environment Configuration file: " + e.getMessage());
            System.out.println("Error with the Environment Configuration file: " + e.getMessage());
            //Throwing a unchecked Exception to stop the deployment
            throw new RuntimeException("Error loading the Properties File: " + e.getMessage());
        }
        logger.debug("Loading the properties file: " + CONFIG_FILE);
        try {
            configure(NOT_FOUND_PROPERTY_EXCEPTION_ACTIVATED);
        } catch (PropertiesException e) {
            logger.error("Error loading the Properties File: " + e.getMessage());
            System.out.println("Error loading the Properties File: " + e.getMessage());
            //Throwing a unchecked Exception to stop the deployment
            throw new RuntimeException("Error loading the Properties File: " + e.getMessage());
        }
    }

    /**
     * @return
     */
    public static MiddlewareProperties getInstance() {
        return _instance;
    }

    /**
     * Method used to initialize all the Properties
     */
    private static void configure(Boolean notFoundPropertyException) throws PropertiesException {

        Properties properties = new Properties();
        String value = null;

        //-----------------------------------------------------------------
        // Opening Properties file
        //-----------------------------------------------------------------
        File file = new File(CONFIG_FILE);
        FileInputStream input = null;
        try {
            System.out.println("Property file location: " + file.getAbsolutePath());
            logger.debug("Property file location: " + file.getAbsolutePath());
            logger.debug("Read permissions:" + file.canRead() + ".");
            // Loads the Properties file
            input = new FileInputStream(CONFIG_FILE);
            properties.load(input);
        } catch (Exception e) {
            logger.error("Configuration file not found: " + e.getMessage());
            logger.error("File Path Required: " + file.getAbsolutePath());
            if (notFoundPropertyException)
                throw PropertiesException.propertyFileNotFound(e);
            else
                return;
        } finally {
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    logger.error(e);
                }
        }
        try {
            //-----------------------------------------------------------------
            // Setting properties object from Properties file
            //-----------------------------------------------------------------
            String[] constants = {"URL_WITBOOKER_V6", "URL_WITBOOKER_V5", "HTTP_AUTH_USER",
                    "HTTP_AUTH_PASS", "TRIVAGO_HOTELS_JSON_FILE", "CUSTOMERS_DB_FILE",
                    "SUPPORT_DB_FILE", "PROPERTY_RESERVATION_ALLOWED", "URL_TRIVAGO_V5",
                    "URL_TINYURL", "SCHEDULER_MAIL_HOUR", "SCHEDULER_MAIL_MIN", "SCHEDULER_MAIL_SEG",
                    "SCHEDULER_TRANSFERS_HOUR", "SCHEDULER_TRANSFERS_MIN", "SCHEDULER_TRANSFERS_SEG",
                    "SCHEDULER_BOOKING_HOUR", "SCHEDULER_BOOKING_MIN", "SCHEDULER_BOOKING_SEG",
                    "SCHEDULER_RESERVATIONS_HOUR", "SCHEDULER_RESERVATIONS_MIN", "SCHEDULER_RESERVATIONS_SEG",
                    "BOOKING_ARI_URL", "BOOKING_RESERVATION_URL",
                    "CHANNELS_LOGGER_DIR", "SITEMINDER_RES_URL", "SITEMINDER_USER_ID", "SITEMINDER_PASSWORD", "RATEGAIN_RES_URL",
                    "RATEGAIN_USER_ID", "RATEGAIN_PASSWORD",
                    "TRIP_ADVISOR_KEY", "TRIP_ADVISOR_URL", "BOOKING_USER", "BOOKING_PASSWORD",
                    "NOTIFICATIONS_EMAIL_ADDRESS", "NOTIFICATIONS_EMAIL_PASSWORD",
                    "SCHEDULER_EXECUTOR_HOUR", "SCHEDULER_EXECUTOR_MIN", "SCHEDULER_EXECUTOR_SEG",
                    "STATIC_ROOT_URL", "MANDRILL_API_KEY", "REDIS_SERVER"
            };
            for (String item : constants) {
                if ((value = properties.getProperty(item)) == null) {
                    if (notFoundPropertyException)
                        throw PropertiesException.propertyNotFound(item);
                } else {
                    try {
                        MiddlewareProperties.class.getField(item).set(null, value.trim());
                        //logger.debug(" " + item + ": " + MiddlewareProperties.class.getField(item).get(item));
                    } catch (Exception ex) {
                        logger.error(ex);
                        if (notFoundPropertyException)
                            throw PropertiesException.propertyNotFound(item);
                    }
                }
            }
            //Those variables could not be defined on witbooking.properties.
            String[] variables = {"ACTIVATE_SCHEDULER_INTEGRATION", "ACTIVATE_MAILING_SCHEDULER_INTEGRATION",
                    "PROD_SERVER_NAME", "LUKE_SERVER_NAME", "TEST_SERVER_NAME", "GEO_IP_DB_FILEPATH",
                    "DAYS_BEFORE_DELETE_CC", "ACTIVATE_SCHEDULER_TRANSFERS_VALIDATION"};
            for (String item : variables) {
                if ((value = properties.getProperty(item)) != null) {
                    try {
                        MiddlewareProperties.class.getField(item).set(null, value.trim());
                    } catch (Exception ex) {
                        logger.error(ex);
                        if (notFoundPropertyException)
                            throw PropertiesException.propertyNotFound(item);
                    }
                }
            }

            BOOKING_ENCODE = BaseEncoding.base64().encode((BOOKING_USER + ":" + BOOKING_PASSWORD).getBytes());
            if (properties.getProperty("INTEGRATION_MANAGER_MAX_RESEND") == null) {
                if (notFoundPropertyException)
                    throw PropertiesException.propertyNotFound("INTEGRATION_MANAGER_MAX_RESEND");
            } else {
                value = properties.getProperty("INTEGRATION_MANAGER_MAX_RESEND");
                try {
                    MiddlewareProperties.INTEGRATION_MANAGER_MAX_RESEND = Integer.parseInt(value);
                } catch (Exception ex) {
                    logger.error(ex);
                    if (notFoundPropertyException)
                        throw PropertiesException.propertyNotFound("INTEGRATION_MANAGER_MAX_RESEND");
                }
            }
            NOTIFICATIONS_EMAIL_RECIPIENTS = new ArrayList<>();
            if (properties.getProperty("NOTIFICATIONS_EMAIL_RECIPIENTS") == null) {
                if (notFoundPropertyException) {
                    throw PropertiesException.propertyNotFound("NOTIFICATIONS_EMAIL_RECIPIENTS");
                }
            } else {
                value = properties.getProperty("NOTIFICATIONS_EMAIL_RECIPIENTS");
                try {
                    for (String email : value.split(";")) {
                        NOTIFICATIONS_EMAIL_RECIPIENTS.add(email.trim());
                    }
                } catch (Exception ex) {
                    logger.error(ex);
                    if (notFoundPropertyException)
                        throw PropertiesException.propertyNotFound("NOTIFICATIONS_EMAIL_RECIPIENTS");
                }
            }

            //Setting Booking constants.
            try {
                //Generating booking urls.
                BOOKING_URL_AVAIL_NOTIF = BOOKING_ARI_URL + "OTA_HotelAvailNotif";
                BOOKING_URL_RATE_AMOUNT_NOTIF = BOOKING_ARI_URL + "OTA_HotelRateAmountNotif";
                BOOKING_URL_RESERVATION = BOOKING_RESERVATION_URL + "OTA_HotelResNotif";
                BOOKING_URL_MODIFIED_RESERVATIONS = BOOKING_RESERVATION_URL + "OTA_HotelResModifyNotif";
            } catch (Exception ex) {
                logger.error(ex);
                if (notFoundPropertyException)
                    throw new PropertiesException(ex);
            }
        } catch (PropertiesException e) {
            System.out.println(e.getUserMessage());
            logger.error(e);
            if (notFoundPropertyException)
                throw e;
        }
    }

    public static Boolean schedulerIsActivate() {
        return !"false".equalsIgnoreCase(ACTIVATE_SCHEDULER_INTEGRATION);
    }
    public static Boolean transfersSchedulerIsActivate() {
        return !"false".equalsIgnoreCase(ACTIVATE_SCHEDULER_TRANSFERS_VALIDATION);
    }

    public static Boolean mailingSchedulerIsActivate() {
        return !"false".equalsIgnoreCase(ACTIVATE_MAILING_SCHEDULER_INTEGRATION);
    }

    public static void updateParameters() throws NoSuchFieldException, PropertiesException, IllegalAccessException, IOException {
        configure(NOT_FOUND_PROPERTY_EXCEPTION_DEACTIVATE);
    }

    public static void updateParameter(final String parameterName) throws IOException, NoSuchFieldException, PropertiesException, IllegalAccessException {
        if (parameterName == null) {
//            throw new NullPointerException("param parameterName given is null");
            logger.debug("The parameterName given is null, updating all MiddlewareProperties.");
            configure(NOT_FOUND_PROPERTY_EXCEPTION_DEACTIVATE);
        } else {
            try {
                Properties properties = new Properties();
                String value;
                FileInputStream input = null;
                try {
                    input = new FileInputStream(CONFIG_FILE);
                    properties.load(input);
                } finally {
                    if (input != null)
                        input.close();
                }
                if ((value = properties.getProperty(parameterName)) == null) {
                    throw PropertiesException.propertyNotFound(parameterName);
                }
                try {
                    final String valueTrim = value.trim();
                    final String oldValue = MiddlewareProperties.class.getField(parameterName).get(null) + "";
                    MiddlewareProperties.class.getField(parameterName).set(null, valueTrim);
                    final String newValue = MiddlewareProperties.class.getField(parameterName).get(null) + "";
                    if (!oldValue.equals(newValue)) {
                        logger.debug("MiddlewareProperties." + parameterName + " updated from: '" + oldValue +
                                "' to: '" + newValue + "'");
                    }
                } catch (IllegalAccessException e) {
                    logger.error(e);
                    throw e;
                }
            } catch (NoSuchFieldException | IOException | PropertiesException e) {
                logger.error(e);
                throw e;
            }
        }
    }
}
