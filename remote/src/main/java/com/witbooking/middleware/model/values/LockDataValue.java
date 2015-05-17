/*
 *  AbstractType.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values;

import com.witbooking.middleware.exceptions.DataValueFormatException;
import com.witbooking.middleware.model.values.types.OwnValue;
import com.witbooking.middleware.model.values.types.SharedValue;
import com.witbooking.middleware.model.values.types.Value;
import org.apache.log4j.Logger;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 25-ene-2013
 */
public class LockDataValue extends DataValue<Boolean> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(LockDataValue.class);

    public static Boolean DEFAULT_VALUE = false;

    private enum ValidValueTypes {

        NULL_VALUE, OWN, SHARED;
    }

    /**
     * Creates a new instance of
     * <code>LockDataValue</code> without params.
     */
    public LockDataValue() {
    }

    public LockDataValue(Value<Boolean> value) {
        super(value);
    }

    public LockDataValue(String value, int typeValue) throws DataValueFormatException {
        this(value, EnumDataValueType.getValidDataValueType(typeValue));
    }

    public LockDataValue(String value, EnumDataValueType typeValue) throws DataValueFormatException {
        switch (typeValue) {
            case OWN:
                boolean defaultValue = DEFAULT_VALUE;
                //TODO: V7 should aks for the default value
//            try {
//               defaultValue = Integer.valueOf(value.trim()).intValue();
//            } catch (Exception nfe) {
//               logger.warn(" No Default Stay Value. NumberFormatException: " + nfe.getMessage());
//               //If there is a problem parsing the String, the default value for the Stay is 0
//               defaultValue = DEFAULT_VALUE;
//            }
                setValue(new OwnValue<Boolean>(defaultValue));
                break;
            case SHARED:
                setValue(new SharedValue<Boolean>(value));
                break;
            case NULL_VALUE:
                setValue(null);
                break;
            default:
                logger.debug("Invalid LockDataValue Type {value:'" + value + "', typeValue:" + typeValue + "}");
                throw new DataValueFormatException("Invalid LockDataValue Type {value:'" + value + "', typeValue:" + typeValue + "}");
        }
    }

    @Override
    public Object[] getValidValueTypes() {
        return ValidValueTypes.values();
    }

    @Override
    public String toString() {
        return "LockDataValue{" + "value=" + this.getValue() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LockDataValue other = (LockDataValue) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }
}
