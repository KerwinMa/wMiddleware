package com.witbooking.middleware.model.values;

import com.witbooking.middleware.model.values.types.Value;

/**
 * Insert description of the Interface here
 *
 * @author Christian Delgado
 * @date 31-ene-2013
 * @version 1.0
 * @since
 */
public interface DataValueInterface<E> {
   
   public Value<E> getValue();

   public void setValue(Value<E> value);

//   public E getFinalValue();
   public String printValue();

//   public void setFinalValue(E value);
   public Object[] getValidValueTypes();
}
