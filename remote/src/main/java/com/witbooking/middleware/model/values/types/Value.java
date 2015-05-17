/*
 *  Value.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values.types;

import com.witbooking.middleware.model.values.EnumDataValueType;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @date 25-ene-2013
 * @version 1.0
 * @since
 */
public interface Value<E> {

   public String printStringValue();

//   public boolean equalsValues(Value<E> otherValue);

   public EnumDataValueType getValueType();
}
