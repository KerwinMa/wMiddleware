/*
 *   ValidateTransfersBean.java
 * 
 * Copyright(c) 2014 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.db.DAOUtil;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.ReservationDBHandler;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.TransfersValidationException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.Reservation;
import com.witbooking.middleware.model.TransferData;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.utils.DateUtil;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Session Bean implementation class ValidateTransfersBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 30/10/14
 */
@Stateless
public class ValidateTransfersBean implements ValidateTransfersBeanLocal {

    private static final Logger logger = Logger.getLogger(ValidateTransfersBean.class);

    public int confirmReservationStatusTransfers(TransferData transferData) throws TransfersValidationException {
        logger.debug("confirmReservationStatusTransfers: transferData: " + transferData);
        if (transferData == null)
            return 0;
        DBConnection dbConnection = null;
        try {
            int resNumber = 0;
            // Get the credentials
            DBCredentials dbCredential = getDBCredentials(transferData.getTicker());
            //Make the database connection
            dbConnection = new DBConnection(dbCredential);
            final ReservationDBHandler resDB = new ReservationDBHandler(dbConnection);
            Date dateToAsk = new Date();
            List<Reservation> reservations;
            //Get all the reservations, By Status
            reservations = resDB.getReservationsByStatus(Reservation.ReservationStatus.RESERVATION, (byte) 4);
            logger.debug("confirmReservationStatusTransfers: reservations: " + reservations.size());
            for (final Reservation item : reservations) {
                Date createDate = item.getDateCreation();
                if (DateUtil.hoursBetweenDates(createDate, dateToAsk) >= transferData.getLockHours()) {
                    logger.debug("updateReservation to PRE_RESERVATION No Confirmed: " + item.getReservationId());
                    item.setStatus(Reservation.ReservationStatus.PRE_RESERVATION);
                    item.setPaymentStatus((byte) 6);
                    resDB.updateReservations(Arrays.asList(item));
                    resNumber++;
                }
            }
            return resNumber;
        } catch (DBAccessException e) {
            logger.error("Error: hotel: '" + transferData + "' :" + e);
            throw new TransfersValidationException(e);
        } finally {
            DAOUtil.close(dbConnection);
        }
    }

    private DBCredentials getDBCredentials(String hotelTicker) throws TransfersValidationException {
        try {
            return DBProperties.getDBCustomerByTicker(hotelTicker);
        } catch (MiddlewareException ex) {
            logger.error("Error getting the DBCredential for the Hotel '" + hotelTicker + "': " + ex.getMessage());
            throw new TransfersValidationException(ex);
        }
    }


}
