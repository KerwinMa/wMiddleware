/*
 *  RateGainValidationRQInterface.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.integration.rategain.model;

import java.math.BigDecimal;

/**
 *
 * @author Jose Francisco Fiorillo
 */
public abstract class RateGainValidationRQInterface {

    protected static final String MSG_ERROR_AUTHENTICATION_NULL = "Authentication attribute can't be null.";
    protected static final String MSG_ERROR_AUTHENTICATION_USERNAME_NULL = "Username field can't be null.";
    protected static final String MSG_ERROR_AUTHENTICATION_PASSWORD_NULL = "Password field can't be null.";

    public abstract AuthenticationType getAuthentication();

    protected abstract ErrorsType validateClass();

    public abstract BigDecimal getVersion();

    public abstract String getTarget();

    /**
     *
     * @return Returns username.
     * @throws NullPointerException When {@link #getAuthentication() }
     * is <code>null</code>
     * @see {@link #getAuthentication() }
     */
    public String getUserName() throws NullPointerException {
        return getAuthentication().getUserName();
    }

    /**
     *
     * @return Returns password.
     * @throws NullPointerException When {@link #getAuthentication() }
     * is <code>null</code>
     * @see {@link #getAuthentication() }
     */
    public String getPassword() throws NullPointerException {
        return getAuthentication().getPassword();
    }

    public ErrorsType validate() {
        ErrorType ret = null;
        if (getAuthentication() == null) {
            ret = ErrorType.getErrorTypeAuthenticationCodeRequiredMissing();
            ret.setValue(MSG_ERROR_AUTHENTICATION_NULL);
        } else if (getAuthentication().getUserName() == null
                || getAuthentication().getPassword() == null) {
            ret = ErrorType.getErrorTypeAuthenticationCodeRequiredMissing();
            ret.setValue(getAuthentication().getUserName() == null ? MSG_ERROR_AUTHENTICATION_NULL : MSG_ERROR_AUTHENTICATION_NULL);
        }
        return ret == null
                ? validateClass()
                : new ErrorsType(ret);
    }

    protected ErrorType errorFound() {
        return ErrorType.getErrorTypeAuthenticationModelCodeRequiredFieldMissing();
    }

    protected ErrorsType addErrorType(ErrorsType errorsType, final ErrorType item) {
        if (item != null) {
            if (errorsType == null) {
                errorsType = new ErrorsType();
            }
            errorsType.addErrorType(item);
        }
        return errorsType;
    }
}
