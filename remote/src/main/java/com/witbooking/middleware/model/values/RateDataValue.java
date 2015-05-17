/*
 *  AbstractType.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values;

import com.witbooking.middleware.exceptions.DataValueFormatException;
import com.witbooking.middleware.model.values.types.*;
import org.apache.log4j.Logger;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 25-ene-2013
 */
public class RateDataValue extends DataValue<Float> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(RateDataValue.class);
    public static Float DEFAULT_VALUE = Float.valueOf(9999);

    public enum ValidValueTypes {

        OWN, SHARED, FORMULA;
    }

    /**
     * Creates a new instance of
     * <code>RateDataValue</code> without params.
     */
    public RateDataValue() {
    }

    public RateDataValue(Value<Float> value) {
        super(value);
    }

    public RateDataValue(String value, int typeValue) throws DataValueFormatException {
        this(value, EnumDataValueType.getValidDataValueType(typeValue));
    }

    public RateDataValue(String value, EnumDataValueType typeValue) throws DataValueFormatException {
        switch (typeValue) {
            case OWN:
                float defaultValue = DEFAULT_VALUE;
                //TODO: V7 should aks for the default value
//            try {
//               defaultValue = Float.valueOf(value.trim()).floatValue();
//            } catch (Exception nfe) {
//               logger.warn(" No Default Rate Value. NumberFormatException: " + nfe.getMessage());
//               //If there is a problem parsing the String, the default value for the Rate is 999
//               defaultValue = DEFAULT_VALUE;
//            }
                setValue(new OwnValue<Float>(defaultValue));
                break;
            case SHARED:
                setValue(new SharedValue<Float>(value));
                break;
            case FORMULA:
                setValue(new FormulaValue<Float>(value));
                break;
            case CONSTANT:
                //Constants value for Extras
                float constantValue;
                try {
                    constantValue = Float.valueOf(value.trim()).floatValue();
                } catch (Exception nfe) {
                    logger.error(" NumberFormatException: " + nfe.getMessage());
                    throw new DataValueFormatException("Invalid StayDataValue CONSTANT value {value:'" + value + "', typeValue:" + typeValue + "}");
                }
                setValue(new ConstantValue<Float>(constantValue));
                break;
            default:
                logger.debug("Invalid RateDataValue Type {value:'" + value + "', typeValue:" + typeValue + "}");
                throw new DataValueFormatException("Invalid RateDataValue Type {value:'" + value + "', typeValue:" + typeValue + "}");
        }
    }

    @Override
    public Object[] getValidValueTypes() {
        return ValidValueTypes.values();
    }

    @Override
    public String toString() {
        return "RateDataValue{" + "value=" + this.getValue() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RateDataValue other = (RateDataValue) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }


}
