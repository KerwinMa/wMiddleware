package com.witbooking.middleware.exceptions.integration;

import com.witbooking.middleware.exceptions.MiddlewareException;

/**
 * IntegrationException.java
 * User: jose
 * Date: 11/5/13
 * Time: 12:39 PM
 */
public class IntegrationException extends MiddlewareException {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructs an instance of
     * <code>TrivagoException</code> with the specified detail message.
     *
     * @param msg the detail message.
     */
    public IntegrationException(String msg) {
        super(ERR_INTEGRATION_SERVICE, DESERR_INTEGRATION_SERVICE, msg);
    }

    public IntegrationException(Exception ex, String code, String description, String userMessage) {
        super(ex, code, description, userMessage);
    }

    public IntegrationException(Exception ex) {
        super(ex, ERR_INTEGRATION_SERVICE, ex.getMessage(), DESERR_INTEGRATION_SERVICE);
    }

    public IntegrationException(Exception ex, String message) {
        super(ex, ERR_INTEGRATION_SERVICE, DESERR_INTEGRATION_SERVICE, message);
    }

    public IntegrationException(MiddlewareException ex) {
        super(ex);
    }
}