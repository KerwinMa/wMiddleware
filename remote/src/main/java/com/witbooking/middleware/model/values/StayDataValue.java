/*
 *  AbstractType.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values;

import com.witbooking.middleware.exceptions.DataValueFormatException;
import com.witbooking.middleware.model.values.types.ConstantValue;
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
public class StayDataValue extends DataValue<Integer> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(StayDataValue.class);
    public static Integer DEFAULT_VALUE = 0;

    private enum ValidValueTypes {

        NULL_VALUE, CONSTANT, OWN, SHARED;
    }

    /**
     * Creates a new instance of
     * <code>NoticeDataValue</code> without params.
     */
    public StayDataValue() {
    }

    public StayDataValue(Value<Integer> value) {
        super(value);
    }

    public StayDataValue(String value, Integer typeValue) throws DataValueFormatException {
        this(value, EnumDataValueType.getValidDataValueType(typeValue));
    }

    public StayDataValue(String value, EnumDataValueType typeValue) throws DataValueFormatException {
        switch (typeValue) {
            case OWN:
                int defaultValue = DEFAULT_VALUE;
                //TODO: V7 should aks for the default value
//            try {
//               defaultValue = Integer.valueOf(value.trim()).intValue();
//            } catch (Exception nfe) {
//               logger.warn(" No Default Stay Value. NumberFormatException: " + nfe.getMessage());
//               //If there is a problem parsing the String, the default value for the Stay is 0
//               defaultValue = DEFAULT_VALUE;
//            }
                setValue(new OwnValue<Integer>(defaultValue));
                break;
            case SHARED:
                setValue(new SharedValue<Integer>(value));
                break;
            case CONSTANT:
                int constantValue;
                try {
                    constantValue = Integer.valueOf(value.trim()).intValue();
                } catch (Exception nfe) {
                    logger.error(" NumberFormatException: " + nfe.getMessage());
                    throw new DataValueFormatException("Invalid StayDataValue CONSTANT value {value:'" + value + "', typeValue:" + typeValue + "}");
                }
                setValue(new ConstantValue<Integer>(constantValue));
                break;
            case NULL_VALUE:
                setValue(null);
                break;
            default:
                logger.debug("Invalid StayDataValue Type {value:'" + value + "', typeValue:" + typeValue + "}");
                throw new DataValueFormatException("Invalid StayDataValue Type {value:'" + value + "', typeValue:" + typeValue + "}");
        }
    }

    @Override
    public Object[] getValidValueTypes() {
        return ValidValueTypes.values();
    }

    @Override
    public String toString() {
        return "StayDataValue{" + "value=" + this.getValue() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StayDataValue other = (StayDataValue) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }
}
