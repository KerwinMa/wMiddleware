/*
 *  RangeValue.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values;

import com.google.gson.annotations.SerializedName;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.utils.DateUtil;

import java.io.Serializable;
import java.util.*;

/**
 * A RangeValue is a group of DailyValues
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 08-feb-2013
 */
//@XmlRootElement(name = "RangeValue")
//@XmlAccessorType(XmlAccessType.FIELD)
public class RangeValue<E> implements Serializable {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RangeValue.class);
    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    //    @XmlElementWrapper(name = "dailySet")
//    @XmlElement(name = "DailyValue")
    private TreeSet<DailyValue<E>> dailySet;
    //    @XmlAttribute(name = "defaultValue")
    private E defaultValue;
    //    @XmlAttribute(name = "type")
    private OperatorType operator;


    public enum OperatorType implements Serializable {
        @SerializedName("=")
        EQUALS("="),
        @SerializedName("+")
        PLUS("+"),
        @SerializedName("-")
        MINUS("-"),
        @SerializedName("*")
        PRODUCT("*");
        private String value;

        OperatorType(String item) {
            value = item;
        }

        public String getStringValue() {
            return value;
        }

        public static OperatorType getValueOf(String item) throws NonexistentValueException {
            for (OperatorType operatorType : values()) {
                if (operatorType.value.equals(item))
                    return operatorType;
            }
            throw new NonexistentValueException("Invalid operator '" + item + "'");
        }

        @Override
        public String toString() {
            return value;
        }
    }

    /**
     * Creates a new instance of
     * <code>RangeValue</code> without params.
     */
    public RangeValue() {
        this.dailySet = new TreeSet<>(new DateComparator());
        this.defaultValue = null;
    }

    public RangeValue(E defaultValue) {
        this.dailySet = new TreeSet<>(new DateComparator());
        this.defaultValue = defaultValue;
    }

    public RangeValue(TreeSet<DailyValue<E>> dailySet, E defaultValue) {
        if (dailySet != null) {
            this.dailySet = dailySet;
        } else {
            this.dailySet = new TreeSet<>(new DateComparator());
        }
        this.defaultValue = defaultValue;
    }

    public RangeValue(TreeSet<DailyValue<E>> dailySet, E defaultValue, OperatorType operator) {
        this.dailySet = dailySet;
        this.defaultValue = defaultValue;
        this.operator = operator;
    }

    public RangeValue(DailyValue<E> dailyValue, E defaultValue) {
        this.dailySet = new TreeSet<>(new DateComparator());
        this.dailySet.add(dailyValue);
        this.defaultValue = defaultValue;
    }

    public RangeValue(Date startDate, Date endDate, E value, E defaultValue) {
        this.dailySet = new TreeSet<>(new DateComparator());
        this.dailySet.add(new DailyValue<>(startDate, endDate, value));
        this.defaultValue = defaultValue;
    }

    public Set<DailyValue<E>> getDailySet() {
        return dailySet;
    }

    public void setDailySet(TreeSet<DailyValue<E>> dailySet) {
        this.dailySet = dailySet;
    }

    public E getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(E defaultValue) {
        this.defaultValue = defaultValue;
    }

    public OperatorType getOperator() {
        return operator;
    }

    public boolean isOperator() {
        return (operator != null && operator != OperatorType.EQUALS);
    }

    public void setOperator(OperatorType operator) {
        this.operator = operator;
    }


    public void setOperator(String operator) throws NonexistentValueException {
        this.operator = OperatorType.getValueOf(operator);
    }


    public Date getRangeStartDate() {
        if (dailySet.size() > 0) {
            return dailySet.first().getStartDate();
        }
        return null;
    }

    public Date getRangeEndDate() {
        if (dailySet.size() > 0) {
            return dailySet.last().getEndDate();
        }
        return null;
    }

    public void applyVariation(Double variation, boolean isPercentage) {
        for (Iterator<DailyValue<E>> it1 = dailySet.iterator(); it1.hasNext(); ) {
            DailyValue<E> dailyValue = it1.next();
            if (dailyValue.getValue() != null) {
                try {
                    Float rate = (Float) dailyValue.getValue();
                    rate = isPercentage ? rate * (1 + (variation.floatValue() / 100)) : rate + variation.floatValue();
                    dailyValue.setValue((E) rate);
                } catch (ClassCastException e) {
                    logger.error("Tried to use applyVariation with a Non Float DailyValue");
                }
            }
        }
    }

    public boolean dateBetweenRange(Date date) {
        if (dailySet.size() <= 0) {
            return true;
        }
        Date compareDate = DateUtil.toBeginningOfTheDay(date);
        if (getRangeStartDate().before(compareDate)
                && getRangeEndDate().after(compareDate)) {
            return true;
        } else if (getRangeStartDate().equals(compareDate)
                || getRangeEndDate().equals(compareDate)) {
            return true;
        }
        return false;
    }

    public E getValueForADate(Date date) {
        for (DailyValue<E> dailyValue : dailySet) {
            if (dailyValue.dateBetweenDaysRange(date)) {
                return dailyValue.getValue();
            }
        }
        return null;
    }

    public E getFinalValueForADate(Date date) {
        E value = getValueForADate(date);
        if (value == null) {
            value = defaultValue;
        }
        return value;
    }

    /**
     * Return true if the param val is in the RangeValue
     */
    public boolean hasValueEqualsTo(E val) {
        for (DailyValue<E> dailyValue : dailySet) {
            if (dailyValue.getValue().equals(val)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasValueEqualsToFloat(float val) {
        try {
            for (DailyValue<E> dailyValue : dailySet) {
                if (dailyValue.getValue() != null &&
                        ((Number) dailyValue.getValue()).floatValue() == val) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public void putDailyValue(DailyValue<E> dailyValue) {
        Date dateIterator = (Date) dailyValue.getStartDate().clone();
        while (DateUtil.dateBetweenDaysRange(dateIterator, dailyValue.getStartDate(), dailyValue.getEndDate())) {
            putValueForADate(dateIterator, dailyValue.getValue());
            DateUtil.incrementDays(dateIterator, 1);
        }
    }

    public void putValueForADate(Date date, E value) {
        if (value == null) {
            return;
        }
        for (Iterator<DailyValue<E>> it = dailySet.iterator(); it.hasNext(); ) {
            DailyValue<E> dailyValue = it.next();
            if (dailyValue.dateBetweenDaysRange(date)) {

                if (dailyValue.getValue().equals(value)) {
                    //The value is already in the Range
                    return;
                } else if (dailyValue.getEndDate().equals(dailyValue.getStartDate())) {
                    //The dailyValue is just one Day, and change the new value
                    dailyValue.setValue(value);
                    return;
                } else if (dailyValue.getStartDate().equals(date)) {
                    //add one day to the StartDate and insert the new DailyValue
                    DateUtil.incrementDays(dailyValue.getStartDate(), 1);
                    dailySet.add(new DailyValue<>(date, date, value));
                    return;
                } else if (dailyValue.getEndDate().equals(date)) {
                    //Subtract one day to the EndDate and insert the new DailyValue
                    DateUtil.incrementDays(dailyValue.getEndDate(), -1);
                    dailySet.add(new DailyValue<>(date, date, value));
                    return;
                } else {
                    //Divide the DailyValue in 3 pieces
                    Date oldEndDate = (Date) dailyValue.getEndDate().clone();
                    Date newStartDate = (Date) date.clone();
                    dailyValue.setEndDate(date);
                    DateUtil.incrementDays(dailyValue.getEndDate(), -1);
                    DateUtil.incrementDays(newStartDate, 1);
                    dailySet.add(new DailyValue<>(date, date, value));
                    dailySet.add(new DailyValue<>(newStartDate, oldEndDate, dailyValue.getValue()));
                    return;
                }
            } else if (dailyValue.getValue().equals(value)) {
                Date closeDate = (Date) date.clone();

                //Add one day to ask if for the day before to this dailyValue, have the same value
                DateUtil.incrementDays(closeDate, 1);
                if (closeDate.equals(dailyValue.getStartDate())) {
                    DateUtil.incrementDays(dailyValue.getStartDate(), -1);
                    return;
                }

                //Subtract two days to ask if for the day after to this dailyValue, have the same value
                DateUtil.incrementDays(closeDate, -2);
                if (closeDate.equals(dailyValue.getEndDate())) {
                    DateUtil.incrementDays(dailyValue.getEndDate(), 1);
                    //Have to check if the next dailyValue, belongs to the same Range that the previous dailyValue
                    if (it.hasNext()) {
                        DailyValue<E> nextDailyValue = it.next();
                        DateUtil.incrementDays(closeDate, 2);
                        //if the next DailyValue have the same value, join the two DailyValues if I have to
                        if (dailyValue.getValue().equals(nextDailyValue.getValue())) {
                            if (dailyValue.getEndDate().equals(nextDailyValue.getStartDate())
                                    || closeDate.equals(nextDailyValue.getStartDate())) {
                                dailyValue.setEndDate(nextDailyValue.getEndDate());
                                it.remove();
                                return;
                            }
                        }
                        //If the actual dailyValue overlap with the nextDailyValue
                        if (dailyValue.getEndDate().equals(nextDailyValue.getStartDate())) {
                            //If the next DailyValue have just one day, remove it
                            if (nextDailyValue.getEndDate().equals(nextDailyValue.getStartDate())) {
                                it.remove();
                            } else {
                                DateUtil.incrementDays(nextDailyValue.getStartDate(), 1);
                            }
                            return;
                        }
                    }
                    return;
                }
            }

        }
        dailySet.add(new DailyValue<>(date, date, value));
    }

    public List<E> getValuesForEachDay() {
        List<E> values = new ArrayList<>();
        for (DailyValue<E> dailyValue : dailySet) {
            Date dateIterator = (Date) dailyValue.getStartDate().clone();
            while (DateUtil.dateBetweenDaysRange(dateIterator, dailyValue.getStartDate(), dailyValue.getEndDate())) {
                if (dailyValue.getValue() != null) {
                    values.add(dailyValue.getValue());
                } else {
                    values.add(defaultValue);
                }
                DateUtil.incrementDays(dateIterator, 1);
            }
        }
        return values;
    }


    public Map<Date, E> getMapValuesForEachDay() {
        Map<Date, E> values = new TreeMap<>();
        for (DailyValue<E> dailyValue : dailySet) {
            Date dateIterator = (Date) dailyValue.getStartDate().clone();
            while (DateUtil.dateBetweenDaysRange(dateIterator, dailyValue.getStartDate(), dailyValue.getEndDate())) {
                if (dailyValue.getValue() != null) {
                    values.put(dateIterator, dailyValue.getValue());
                } else {
                    values.put(dateIterator, defaultValue);
                }
                dateIterator = DateUtil.cloneAndIncrementDays(dateIterator, 1);
            }
        }
        return values;
    }

    public List<E> getValuesForEachDayForContinuousRange() {
        List<E> values = new ArrayList<>();
        if (dailySet == null || dailySet.isEmpty())
            return values;
        for (DailyValue<E> dailyValue : dailySet) {
            Date dateIterator = (Date) dailyValue.getStartDate().clone();
            while (DateUtil.dateBetweenDaysRange(dateIterator, dailyValue.getStartDate(), dailyValue.getEndDate())) {
                if (dailyValue.getValue() != null) {
                    values.add(dailyValue.getValue());
                } else {
                    throw new NullPointerException("There is no value for a Date");
                }
                DateUtil.incrementDays(dateIterator, 1);
            }
        }
        int numValues = DateUtil.daysBetweenDates(dailySet.first().getStartDate(), dailySet.last().getEndDate()) + 1;
        if (values.size() == numValues)
            return values;
        else
            throw new NullPointerException("The values and the number of day are different.");
    }

    public <E extends Number & Comparable<? super E>> E getMinValue() {
        List values = getValuesForEachDay();
        return values != null && !values.isEmpty() ? (E) Collections.min(values) : null;
    }

    public <E extends Number & Comparable<? super E>> E getMaxValue() {
        List values = getValuesForEachDay();
        return values != null && !values.isEmpty() ? (E) Collections.max(values) : null;
    }

    public float getSumValues() {
        //if is a RangeValue of Number, this method give the total Sum for all days
        float totalSum = 0;
        for (DailyValue<E> dayValue : dailySet) {
            float val;
            if (dayValue.getValue() != null) {
                val = ((Number) dayValue.getValue()).floatValue();
            } else {
                val = ((Number) defaultValue).floatValue();
            }
            totalSum = totalSum + (val * (dayValue.daysBetweenDates() + 1));
        }
        return totalSum;
    }

    /**
     * Return the List of long for the dates that the RangeValue has values
     * (Used in DailyValuesDBHandler.updateDailyValuesByTicker)
     */
    public List<Long> dateLongListWithValues() {
        List<Long> longDates = new ArrayList<>();
        for (DailyValue<E> dailyValue : dailySet) {
            Date dateIterator = (Date) dailyValue.getStartDate().clone();
            while (DateUtil.dateBetweenDaysRange(dateIterator, dailyValue.getStartDate(), dailyValue.getEndDate())) {
                if (dailyValue.getValue() != null) {
                    longDates.add(dateIterator.getTime());
                }
                DateUtil.incrementDays(dateIterator, 1);
            }
        }
        return longDates;
    }

    @Override
    public RangeValue<E> clone() {
        TreeSet<DailyValue<E>> treeSet = new TreeSet<>(new DateComparator());
        for (DailyValue<E> dailyValue : dailySet) {
            DailyValue<E> newDailyValue = new DailyValue<>((Date) dailyValue.getStartDate().clone(), (Date) dailyValue.getEndDate().clone(), dailyValue.getValue());
            treeSet.add(newDailyValue);
        }
        return new RangeValue<>(treeSet, defaultValue);
    }

    private class DateComparator implements Comparator<DailyValue<E>>, Serializable {

        @Override
        public int compare(DailyValue<E> dailyValue1, DailyValue<E> dailyValue2) {
            if (dailyValue1.getStartDate().before(dailyValue2.getStartDate())) {
                return -1;
            }
            if (dailyValue2.getStartDate().before(dailyValue1.getStartDate())) {
                return 1;
            }
            return 0;
        }
    }

    public void operateRangeValue(RangeValue<Number> numberRangeValue, boolean isOperator) throws NonexistentValueException {
        for (DailyValue<Number> dailyValue : numberRangeValue.dailySet) {
            Date dateIterator = (Date) dailyValue.getStartDate().clone();
            while (DateUtil.dateBetweenDaysRange(dateIterator, dailyValue.getStartDate(), dailyValue.getEndDate())) {
                Number actualValue = (Number) getValueForADate(dateIterator);
                if (actualValue == null)
                    actualValue = 0;
                Number newValue;
                if (isOperator)
                    newValue = operateValue(dailyValue.getValue(), actualValue, operator);
                else
                    newValue = operateValue(actualValue, dailyValue.getValue(), numberRangeValue.operator);
                if (newValue.floatValue() != actualValue.floatValue())
                    putValueForADate(dateIterator, (E) newValue);
                DateUtil.incrementDays(dateIterator, 1);
            }
        }
        return;
    }

    public static Number operateValue(Number value1, Number value2, OperatorType operator) throws NonexistentValueException {
        switch (operator) {
            case PLUS:
                return value1.floatValue() + value2.floatValue();
            case MINUS:
                return value1.floatValue() - value2.floatValue();
            case PRODUCT:
                return value1.floatValue() * value2.floatValue();
            default:
                throw new NonexistentValueException("Invalid operator '" + operator + "'");
        }
    }

    @Override
    public String toString() {
        if (isOperator())
            return "RangeValue{" + "dailySet=" + dailySet + ", defaultValue=" + defaultValue + ", operator=" + operator + '}';
        else
            return "RangeValue{" + "dailySet=" + dailySet + ", defaultValue=" + defaultValue + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RangeValue<E> other = (RangeValue<E>) obj;
        if (this.dailySet != other.dailySet && (this.dailySet == null || !this.dailySet.equals(other.dailySet))) {
            return false;
        }
        if (this.defaultValue != other.defaultValue && (this.defaultValue == null || !this.defaultValue.equals(other.defaultValue))) {
            return false;
        }
        return true;
    }


}
