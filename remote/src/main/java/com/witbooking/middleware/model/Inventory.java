/*
 *  Inventory.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.google.common.base.Joiner;
import com.witbooking.middleware.model.values.*;
import com.witbooking.middleware.model.values.types.DaysCondition;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 24-ene-2013
 */

public class Inventory implements Serializable, DataValueHolder, Comparable<Inventory> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String ticker;
    private String name;
    private String hotelRef;
    //   private Tax tax;
    //Objects
    private Accommodation accommodation;
    private Configuration configuration;
    private MealPlan mealPlan;
    private Condition condition;
    //
    private RateDataValue rate;
    private AvailabilityDataValue availability;//TODO: This should be in Accommodation
    private LockDataValue lock;
    private StayDataValue minStay;
    private StayDataValue maxStay;
    private NoticeDataValue minNotice;
    private NoticeDataValue maxNotice;
    private String accessCode;
    private DaysCondition checkInDays;
    private DaysCondition checkOutDays;
    private boolean visible = true;
    private boolean deleted = false;
    //
    private Date dateCreation;
    private Date dateModification;
    private Date dateStartValidation;
    private Date dateEndValidation;
    //
    private List<Discount> discountList;
    private List<Service> serviceList;

    private Float rack;


    /**
     * Creates a new instance of
     * <code>Inventory</code> without params.
     */
    public Inventory() {
    }

    public Float getRack() {
        return rack;
    }

    public void setRack(Float rack) {
        this.rack = rack;
    }

    public Inventory(Integer id) {
        this.id = id;
    }


    public Date getDateStartValidation() {
        return dateStartValidation;
    }

    public void setDateStartValidation(Date dateStartValidation) {
        this.dateStartValidation = dateStartValidation;
    }

    public Date getDateEndValidation() {
        return dateEndValidation;
    }

    public void setDateEndValidation(Date dateEndValidation) {
        this.dateEndValidation = dateEndValidation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHotelRef() {
        return hotelRef;
    }

    public void setHotelRef(String hotelRef) {
        this.hotelRef = hotelRef;
    }

    public Accommodation getAccommodation() {
        return accommodation;
    }

    public String getAccommodationName() {
        return accommodation == null
                ? ""
                : accommodation.getName();
    }

    public void setAccommodation(Accommodation accommodation) {
        this.accommodation = accommodation;
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getConfigurationName() {
        return configuration == null ? "" : configuration.getName();
    }

    public String getFullName() {
        final String accommodationName = getAccommodationName().trim();
        final String configurationName = getConfigurationName().trim();
        final String mealPlanName = getMealPlanName().trim();
        final String conditionName = getConditionName().trim();
        final String fullName = Joiner.on(", ").join(new String[]{accommodationName, configurationName, mealPlanName, conditionName});
        return fullName == "" ? getName() : fullName;

    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public MealPlan getMealPlan() {
        return mealPlan;
    }

    public String getMealPlanName() {
        return mealPlan == null ? "" : mealPlan.getName();
    }

    public void setMealPlan(MealPlan mealPlan) {
        this.mealPlan = mealPlan;
    }

    public Condition getCondition() {
        return condition;
    }

    public String getConditionName() {
        return condition == null ? "" : condition.getName();
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public RateDataValue getRate() {
        return rate;
    }

    public void setRate(RateDataValue rate) {
        this.rate = rate;
    }

    public AvailabilityDataValue getAvailability() {
        return availability;
    }

    public void setAvailability(AvailabilityDataValue availability) {
        this.availability = availability;
    }

    public LockDataValue getLock() {
        return lock;
    }

    public void setLock(LockDataValue lock) {
        this.lock = lock;
    }

    public StayDataValue getMinStay() {
        return minStay;
    }

    public void setMinStay(StayDataValue minStay) {
        this.minStay = minStay;
    }

    public StayDataValue getMaxStay() {
        return maxStay;
    }

    public void setMaxStay(StayDataValue maxStay) {
        this.maxStay = maxStay;
    }

    public NoticeDataValue getMinNotice() {
        return minNotice;
    }

    public void setMinNotice(NoticeDataValue minNotice) {
        this.minNotice = minNotice;
    }

    public NoticeDataValue getMaxNotice() {
        return maxNotice;
    }

    public void setMaxNotice(NoticeDataValue maxNotice) {
        this.maxNotice = maxNotice;
    }

    @Override
    public boolean isActive() {
        return visible;
    }

    @Override
    public List<DataValue> getDataValues() {
        List<DataValue> dataValues = new ArrayList<>();
        dataValues.add(rate);
        dataValues.add(availability);
        dataValues.add(lock);
        dataValues.add(minStay);
        dataValues.add(maxStay);
        dataValues.add(minNotice);
        dataValues.add(maxNotice);
        return dataValues;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public DaysCondition getCheckInDays() {
        return checkInDays;
    }

    public void setCheckInDays(DaysCondition checkInDays) {
        this.checkInDays = checkInDays;
    }

    public DaysCondition getCheckOutDays() {
        return checkOutDays;
    }

    public void setCheckOutDays(DaysCondition checkOutDays) {
        this.checkOutDays = checkOutDays;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public List<Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<Discount> discountList) {
        this.discountList = discountList;
    }


    public void addDiscount(Discount discount) {
        if (discountList == null) {
            discountList = new ArrayList<Discount>();
        }
        int index = discountList.indexOf(discount);
        if (index < 0) {
            discountList.add(discount);
        }
    }

    public List<Service> getServiceList() {
        return serviceList;
    }

    public void setServiceList(List<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public void addService(Service service) {
        if (serviceList == null) {
            serviceList = new ArrayList<Service>();
        }
        int index = serviceList.indexOf(service);
        if (index < 0) {
            serviceList.add(service);
        }
    }

    public static List<Integer> listOfId(List<Inventory> inventoryList) {
        if (inventoryList == null) {
            return null;
        }
        List<Integer> idList = new ArrayList<Integer>();
        for (Inventory inventory : inventoryList) {
            idList.add(inventory.getId());
        }
        return idList;
    }


    public boolean isValid() {
        if (this.id == null || this.ticker == null || this.ticker.isEmpty() || this.accommodation == null
                || this.configuration == null || this.mealPlan == null || this.condition == null
                || this.rate == null || this.availability == null || this.lock == null || this.deleted) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Inventory{" + "id=" + id + ", ticker=" + ticker + ", name=" + name +
                ", accommodation=" + (accommodation != null ? accommodation.getTicker() : "null") +
                ", configuration=" + (configuration != null ? configuration.getTicker() : "null") +
                ", mealPlan=" + (mealPlan != null ? mealPlan.getTicker() : "null") +
                ", condition=" + (condition != null ? condition.getTicker() : "null")
                //              + ", hotelRef=" + hotelRef + ", tax=" + tax + ", rate=" + rate + ", availability=" + availability + ", lock=" + lock + ", minStay=" + minStay + ", maxStay=" + maxStay + ", minNotice=" + minNotice + ", maxNotice=" + maxNotice + ", accessCode=" + accessCode + ", checkInDays=" + checkInDays + ", checkOutDays=" + checkOutDays + ", visible=" + visible + ", dateCreation=" + dateCreation + ", dateModification=" + dateModification + ", discountList=" + discountList
                + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Inventory other = (Inventory) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.ticker == null) ? (other.ticker != null) : !this.ticker.equals(other.ticker)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.hotelRef == null) ? (other.hotelRef != null) : !this.hotelRef.equals(other.hotelRef)) {
            return false;
        }
//        if (this.tax != other.tax && (this.tax == null || !this.tax.equals(other.tax))) {
//            return false;
//        }
        if (this.accommodation != other.accommodation && (this.accommodation == null || !this.accommodation.equals(other.accommodation))) {
            return false;
        }
        if (this.configuration != other.configuration && (this.configuration == null || !this.configuration.equals(other.configuration))) {
            return false;
        }
        if (this.mealPlan != other.mealPlan && (this.mealPlan == null || !this.mealPlan.equals(other.mealPlan))) {
            return false;
        }
        if (this.condition != other.condition && (this.condition == null || !this.condition.equals(other.condition))) {
            return false;
        }
        if (this.rate != other.rate && (this.rate == null || !this.rate.equals(other.rate))) {
            return false;
        }
        if (this.availability != other.availability && (this.availability == null || !this.availability.equals(other.availability))) {
            return false;
        }
        if (this.lock != other.lock && (this.lock == null || !this.lock.equals(other.lock))) {
            return false;
        }
        if (this.minStay != other.minStay && (this.minStay == null || !this.minStay.equals(other.minStay))) {
            return false;
        }
        if (this.maxStay != other.maxStay && (this.maxStay == null || !this.maxStay.equals(other.maxStay))) {
            return false;
        }
        if (this.minNotice != other.minNotice && (this.minNotice == null || !this.minNotice.equals(other.minNotice))) {
            return false;
        }
        if (this.maxNotice != other.maxNotice && (this.maxNotice == null || !this.maxNotice.equals(other.maxNotice))) {
            return false;
        }
        if ((this.accessCode == null) ? (other.accessCode != null) : !this.accessCode.equals(other.accessCode)) {
            return false;
        }
        if (this.checkInDays != other.checkInDays && (this.checkInDays == null || !this.checkInDays.equals(other.checkInDays))) {
            return false;
        }
        if (this.checkOutDays != other.checkOutDays && (this.checkOutDays == null || !this.checkOutDays.equals(other.checkOutDays))) {
            return false;
        }
        if (this.visible != other.visible) {
            return false;
        }
        if (this.dateCreation != other.dateCreation && (this.dateCreation == null || !this.dateCreation.equals(other.dateCreation))) {
            return false;
        }
        if (this.dateModification != other.dateModification && (this.dateModification == null || !this.dateModification.equals(other.dateModification))) {
            return false;
        }
        if (this.discountList != other.discountList && (this.discountList == null || !this.discountList.equals(other.discountList))) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Inventory o) {
        int accomOrder = this.accommodation.compareTo(o.accommodation);
        if (accomOrder == 0) {
            int confOrder = this.configuration.compareTo(o.configuration);
            if (confOrder == 0) {
                int mealOrder = this.mealPlan.compareTo(o.mealPlan);
                if (mealOrder == 0) {
                    int condOrder = this.condition.compareTo(o.condition);
                    if (condOrder == 0) {
                        return this.ticker.compareTo(o.ticker);
                    } else return condOrder;
                } else return mealOrder;
            } else return confOrder;
        } else return accomOrder;
    }

}
