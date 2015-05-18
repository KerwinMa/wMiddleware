package com.witbooking.middleware.beans;

import com.witbooking.middleware.exceptions.InvalidEntryException;
import com.witbooking.middleware.exceptions.integration.IntegrationException;
import com.witbooking.middleware.integration.EntryQueue;

/**
 * IntegrationExecuteBeanLocal.java
 * User: Christian Delgado
 * Date: 11/26/13
 * Time: 1:52 PM
 */
public interface IntegrationExecuteBeanLocal {

    public void executePending() throws IntegrationException;

    public String executeEntryQueue(EntryQueue entryQueue) throws IntegrationException, InvalidEntryException;

}
