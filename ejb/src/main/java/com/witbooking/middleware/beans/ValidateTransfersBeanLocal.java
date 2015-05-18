/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.TransfersValidationException;
import com.witbooking.middleware.model.TransferData;

import javax.ejb.Local;

/**
 * Local Interface for the Session Bean ValidateTransfersBeanLocal
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 30/10/14
 */
@Local
public interface ValidateTransfersBeanLocal {

    public int confirmReservationStatusTransfers(TransferData transferData) throws TransfersValidationException;

}
