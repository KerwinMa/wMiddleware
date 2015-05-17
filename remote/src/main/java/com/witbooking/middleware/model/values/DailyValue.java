/*
 *  DailyValue.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model.values;

import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.serializers.JaxbDateWithoutTimeSerializer;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.Date;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 29-ene-2013
 */
@XmlRootElement(name = "DailyValue")
@XmlAccessorType(XmlAccessType.FIELD)
public class DailyValue<E> implements Serializable, Comparable<DailyValue> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    @XmlElement(name = "startDate")
    @XmlSchemaType(name = "date")
    @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
    private Date startDate;
    @XmlElement(name = "endDate")
    @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
    private Date endDate;
    @XmlElement(name = "value")
    private E value;

    /**
     * Creates a new instance of
     * <code>DailyValue</code> without params.
     */
    public DailyValue() {
    }

    public DailyValue(Date startDate, Date endDate, E value) {
        this.startDate = DateUtil.toBeginningOfTheDay(startDate);
        this.endDate = DateUtil.toBeginningOfTheDay(endDate);
        this.value = value;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = DateUtil.toBeginningOfTheDay(startDate);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = DateUtil.toBeginningOfTheDay(endDate);
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public boolean dateBetweenDaysRange(Date date) {
        return DateUtil.dateBetweenDaysRange(DateUtil.toBeginningOfTheDay(date), startDate, endDate);
    }

    public int daysBetweenDates() {
        return DateUtil.daysBetweenDates(this.startDate, this.endDate);
    }

    public float getSumValues() {
        //if is a DailyValue of Number, this method give the total Sum for all days
        float totalSum = 0;
        if (value != null && value instanceof Number) {
            totalSum = ((Number) value).floatValue();
            totalSum = (totalSum * (daysBetweenDates() + 1));
        }
        return totalSum;
    }

    @Override
    public String toString() {
        return "{DailyValue:{" + "startDate:" + DateUtil.calendarFormat(startDate) + ", endDate:" + DateUtil.calendarFormat(endDate) + ", value:" + value + "}}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyValue that = (DailyValue) o;

        if (endDate != null ? !endDate.equals(that.endDate) : that.endDate != null) return false;
        if (startDate != null ? !startDate.equals(that.startDate) : that.startDate != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = startDate != null ? startDate.hashCode() : 0;
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(DailyValue dailyValue) {
        if (startDate == dailyValue.getStartDate() || startDate.equals(dailyValue.getStartDate())) {
            return 0;
        }
        if (startDate.before(dailyValue.getStartDate())) {
            return -1;
        }
        if (startDate.after(dailyValue.getStartDate())) {
            return 1;
        }
        return 0;
    }
}
