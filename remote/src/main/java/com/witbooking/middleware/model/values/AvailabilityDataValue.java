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
 * @date 25-ene-2013
 * @version 1.0
 * @since
 */
public class AvailabilityDataValue extends DataValue<Integer> {

   /**
    * Constant serialized ID used for compatibility.
    */
   private static final long serialVersionUID = 1L;
   
   private static final Logger logger = Logger.getLogger(AvailabilityDataValue.class);
   
   public static Integer DEFAULT_VALUE=0;
   
   private enum ValidValueType {
      OWN, SHARED;
   }

   /**
    * Creates a new instance of
    * <code>AvailabilityDataValue</code> without params.
    */
   public AvailabilityDataValue() {
   }

   public AvailabilityDataValue(Value<Integer> value) {
      super(value);
   }
     
   public AvailabilityDataValue(String value, int typeValue) throws DataValueFormatException {
      this(value,EnumDataValueType.getValidDataValueType(typeValue));
   }

   public AvailabilityDataValue(String value, EnumDataValueType typeValue) throws DataValueFormatException {
      switch (typeValue) {
         case OWN:
            int defaultValue= DEFAULT_VALUE;
            //TODO: V7 should aks for the default value
//            try {
//               defaultValue = Integer.valueOf(value.trim()).intValue();
//            } catch (Exception nfe) {
//               logger.warn(" No Default Availability Value. NumberFormatException: " + nfe.getMessage());
//               //If there is a problem parsing the String, the default value for the Availability is 0
//               defaultValue = DEFAULT_VALUE;
//            }
            setValue(new OwnValue<Integer>(defaultValue));
            break;
         case SHARED:
            setValue(new SharedValue<Integer>(value));
            break;
         default:
            logger.debug("Invalid AvailabilityDataValue Type {value:'"+value+"', typeValue:"+typeValue+"}");
            throw new DataValueFormatException("Invalid AvailabilityDataValue Type {value:'"+value+"', typeValue:"+typeValue+"}");
      }
   }

   @Override
   public Object[] getValidValueTypes() {
      return ValidValueType.values();
   }

   @Override
   public String toString() {
      return "AvailabilityDataValue{" + "value=" + this.getValue() + '}';
   }
}
