/*
 *  ConnectionBeanLocal.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.RemoteServiceException;

import javax.ejb.Local;

/**
 * Local Interface for the Session Bean ConnectionBeanLocal
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 19-abr-2013
 */
@Local
public interface ConnectionBeanLocal {

    public static final String DECRYPT="decrypt";
    public static final String ENCRYPT="encrypt";

    /**
     * Performs the connection to internals web services to validate who validates username
     * and password, and return the list of hotels tickers related to an user.
     *
     * @param username
     * @param password
     * @return <code>-1</code> if a problem of authentication occurs, otherwise
     *         Json String with the list of hotels tickers related to the user.
     * @throws com.witbooking.middleware.exceptions.RemoteServiceException
     *
     */
    public String getHotelTickers(String username, String password) throws RemoteServiceException;

    /**
     * Performs the connection to internals web services to validate who validates username
     * and password.
     *
     * @param username
     * @param password
     * @return <code>-1</code> if a problem of authentication occurs, otherwise
     *         Json String with boolean value for the Authentication
     * @throws com.witbooking.middleware.exceptions.RemoteServiceException
     *
     */
    public Boolean validateAuthentication(String username, String password) throws RemoteServiceException;

    public String encryptionPHP(String action, String value) throws RemoteServiceException;

    public String sendConfirmationEmail(String hotelTicker, String reservationCode) throws RemoteServiceException;

}

