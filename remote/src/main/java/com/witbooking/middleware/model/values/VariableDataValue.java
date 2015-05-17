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
import com.witbooking.middleware.model.values.types.FormulaValue;
import com.witbooking.middleware.model.values.types.OwnValue;
import com.witbooking.middleware.model.values.types.Value;
import org.apache.log4j.Logger;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 25-ene-2013
 * @version 1.0
 * @since
 */
public class VariableDataValue extends DataValue<Float> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(VariableDataValue.class);
    public static Float DEFAULT_VALUE = Float.valueOf(999);

    private enum ValidValueTypes {

        OWN, CONSTANT, FORMULA;
    }

    /**
     * Creates a new instance of
     * <code>VariableDataValue</code> without params.
     */
    public VariableDataValue() {
    }

    public VariableDataValue(Value<Float> value) {
        setValue(value);
    }

    public VariableDataValue(String value, EnumDataValueType typeValue) throws DataValueFormatException {
        switch (typeValue) {
            case OWN:
                float defaultValue = DEFAULT_VALUE;
                //TODO: V7 should aks for the default value
//            try {
//               defaultValue = Float.valueOf(value.trim()).intValue();
//            } catch (Exception nfe) {
//               logger.warn(" NumberFormatException: " + nfe.getMessage());
//               //If there is a problem parsing the String, the default value for the Variable is 0
//               defaultValue = DEFAULT_VALUE;
//            }
                setValue(new OwnValue<Float>(defaultValue));
                break;
            case CONSTANT:
                float constantValue;
                try {
                    constantValue = Float.valueOf(value.trim()).intValue();
                } catch (Exception nfe) {
                    logger.error(" NumberFormatException: " + nfe.getMessage());
                    throw new DataValueFormatException("Invalid NoticeDataValue CONSTANT value {value:'" + value + "', typeValue:" + typeValue + "}");
                }
                setValue(new ConstantValue<Float>(constantValue));
                break;
            case FORMULA:
                setValue(new FormulaValue<Float>(value));
                break;
            default:
                logger.debug("Invalid VariableDataValue Type {value:'" + value + "', typeValue:" + typeValue + "}");
                throw new DataValueFormatException("Invalid VariableDataValue Type {value:'" + value + "', typeValue:" + typeValue + "}");
        }
    }

    @Override
    public Object[] getValidValueTypes() {
        return ValidValueTypes.values();
    }

    @Override
    public String toString() {
        return "VariableDataValue{" + "value=" + this.getValue() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final VariableDataValue other = (VariableDataValue) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        return true;
    }
}
