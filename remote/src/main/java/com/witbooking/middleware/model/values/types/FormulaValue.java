/*
 *  FormulaValue.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values.types;

import com.witbooking.middleware.exceptions.CalculateFormulaException;
import com.witbooking.middleware.model.values.EnumDataValueType;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 30-ene-2013
 */
public class FormulaValue<E> implements Value<E>, Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(FormulaValue.class);
    private String formulaValue;
    private Set<String> tickersSet;

    /**
     * Creates a new instance of
     * <code>FormulaValue</code> without params.
     */
    public FormulaValue() {
        this.formulaValue = "";
        this.tickersSet = new TreeSet<String>(new LengthComparator());
    }

    public FormulaValue(String formulaValue) {
        if (formulaValue != null) {
            //remove the blank spaces, and put a 0 to the numbers with the format ".decimal"
            this.formulaValue = formulaValue.replaceAll("\\s", "").replaceAll("([\\W])([\\.\\,])([0-9]+)", "$10.$3");
            tickersSet = new TreeSet<String>(new LengthComparator());

            //Split the formula to take all the numbers and the tickers
            String[] parts = this.formulaValue.split("[[\\W&&[^\\.\\,]]]");
            for (String value : parts) {
                //add to tickersSet just the tickers
                if (!value.equals("") && !value.matches("[\\.0-9]*[0-9][\\.0-9]*")) {
                    tickersSet.add(value);
                }
            }
        }
    }

    public String getFormulaValue() {
        return formulaValue;
    }

    public void setFormulaValue(String formulaValue) {
        this.formulaValue = formulaValue;
    }

    public Set<String> getTickersSet() {
        return tickersSet;
    }

    public void setTickersSet(Set<String> tickersSet) {
        this.tickersSet = tickersSet;
    }

    @Override
    public String toString() {
        return "FormulaValue{" + "formulaValue=" + formulaValue + '}';
    }

    @Override
    public EnumDataValueType getValueType() {
        return EnumDataValueType.FORMULA;
    }

    @Override
    public String printStringValue() {
        return formulaValue;
    }

    private class LengthComparator implements Comparator<String>, Serializable {

        @Override
        public int compare(String o1, String o2) {
            if (o1.length() < o2.length()) {
                return 1;
            } else if (o1.length() > o2.length()) {
                return -1;
            }
            return -1 * o1.compareTo(o2);
        }
    }

    //TODO: this requires extensive testing
    public Float calculateFormula(Map<String, Float> variableValues) throws CalculateFormulaException {

        String calculatedFormula = this.formulaValue;
        if (variableValues.keySet().size() != this.tickersSet.size()) {
            logger.error("Incomplete values for all variables.");
            throw new CalculateFormulaException("Incomplete values for all variables.");
        }

        for (String ticker : this.tickersSet) {
            Float value = variableValues.get(ticker);
            if (value == null) {
                logger.error("Missing value for a variable in the formula.");
                throw new CalculateFormulaException("Missing value for a variable in the formula.");
            }
            calculatedFormula = calculatedFormula.replace(ticker, value + "");
        }

//      logger.debug("  formulaValue: {" + this.formulaValue + "}");
//      logger.debug(" formulaToCalc: {" + calculatedFormula + "}");

        try {
            de.congrace.exp4j.ExpressionBuilder expression = new de.congrace.exp4j.ExpressionBuilder(calculatedFormula);

         /* 
          * TODO: V7
          * This is the way how ExpressionBuilder replace Variables names,
          * but we don't use this method because the BackOffice allows different types
          * of variable names (Ex: 1_variable)
         
          //for (String ticker : this.tickersSet) {
          //   Float value = variableValues.get(ticker);
          //   if (value == null) {
          //      logger.error(ex.getMessage());
          //      throw new CalculateFormulaException();
          //   }
          //   expression.withVariable(ticker, value);
          //}

          */

            de.congrace.exp4j.Calculable calc = expression.build();
            Double finalValue;
            try {
                finalValue = calc.calculate();
            } catch (Exception ex) {
                logger.error(" error: {" + ex.getMessage() + "}");
                return null;
            }
//         logger.debug(" calculated: {" + finalValue + "}");

            return finalValue.floatValue();

        } catch (Exception ex) {
            logger.error(ex.getMessage());
            throw new CalculateFormulaException(ex);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FormulaValue<E> other = (FormulaValue<E>) obj;
        if ((this.formulaValue == null) ? (other.formulaValue != null) : !this.formulaValue.equals(other.formulaValue)) {
            return false;
        }
        return true;
    }


}
