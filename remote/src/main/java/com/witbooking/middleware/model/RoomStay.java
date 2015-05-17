/*
 *  RoomStay.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.model.values.DailyValue;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.utils.DateUtil;

import java.io.Serializable;
import java.util.*;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 28-ene-2013
 */
public class RoomStay implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    //In version V6. this is the value  "id"
    private String id;
    //In version V6. this is the value  "idgenerado"
    private String idConfirmation;
    // Representa el elemento de la tabla llamado tiposhabs_id.
    private Integer inventoryId;
    //
    private Date dateCheckIn;
    private Date dateCheckOut;
    //Services and discounts
    private List<ServiceRequested> services;
    private List<DiscountApplied> discounts;
    //Payment
    //In version V6. Rates with taxes
    private RangeValue<Float> roomRates;
    private float guaranteePercentage;
    private float guaranteeAmount;
    private float totalAmount;
    //v6
    private int capacity;
    private int quantity;
    //
    private String comments;
    //Guest list for OTAs
    private List<ResGuest> guestList;
    private Map<String,String> additionalRequests;

    //TODO: traducir
    /**
     * Estos dos campos son utilizados cuando la reserva ha sido cancelada. En
     * el campo cancellationCause se almacena la razón que indicó el huésped y
     * en cancellationDate la fecha en que ocurrió la cancelación.
     */
    private Date cancellationDate;
    private String cancellationCause;
    private boolean canceledByClient;//es_solicita_cancelacion
    private Date dateModification;
    /**
     * Commission for external channels.
     */
    private Float externalCommission;
    //TODO: traducir
    /**
     * Los siguientes Strings representan los tickers de la linea de inventario
     * y sus cuatro buckets. Ahora no se estan guardando en base de datos. V6.
     */
    private String inventoryTicker;
    private String accommodationType;
    private String mealPlanType;
    private String configurationType;
    private String conditionType;

    /**
     * Creates a new instance of
     * <code>RoomStay</code> without params.
     */
    public RoomStay() {
    }

    /**
     * Set channel commission.
     *
     * @param externalCommission value to set.
     */
    public void setExternalCommission(Float externalCommission) {
        this.externalCommission = externalCommission;
    }

    /**
     * @return If this reservation has been from a channel then returns channel
     * commission value , <code>0</code> otherwise.
     */
    public Float getExternalCommission() {
        return externalCommission;
    }

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventory) {
        this.inventoryId = inventory;
    }

    public Date getDateCheckIn() {
        return dateCheckIn;
    }

    public void setDateCheckIn(Date dateCheckIn) {
        this.dateCheckIn = DateUtil.toBeginningOfTheDay(dateCheckIn);
    }

    public Date getDateCheckOut() {
        return dateCheckOut;
    }

    public void setDateCheckOut(Date dateCheckOut) {
        this.dateCheckOut = DateUtil.toBeginningOfTheDay(dateCheckOut);
    }

    public RangeValue<Float> getRoomRates() {
        return roomRates;
    }

    public Set<DailyValue<Float>> getRoomRatesSetDailyValues() {
        return roomRates == null ? new TreeSet<DailyValue<Float>>() : roomRates.getDailySet();
    }

    public void setRoomRates(RangeValue<Float> roomRates) {
        this.roomRates = roomRates;
    }

    public List<ServiceRequested> getServices() {
        return services;
    }

    public void setServices(List<ServiceRequested> services) {
        this.services = services;
    }

    public String getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(String accommodationType) {
        this.accommodationType = accommodationType;
    }

    public String getMealPlanType() {
        return mealPlanType;
    }

    public void setMealPlanType(String mealPlanType) {
        this.mealPlanType = mealPlanType;
    }

    public String getConfigurationType() {
        return configurationType;
    }

    public void setConfigurationType(String configurationType) {
        this.configurationType = configurationType;
    }

    public String getConditionType() {
        return conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdConfirmation() {
        return idConfirmation;
    }

    public void setIdConfirmation(String idConfirmation) {
        this.idConfirmation = idConfirmation;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public float getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(float totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public float getGuaranteeAmount() {
        return guaranteeAmount;
    }

    public void setGuaranteeAmount(float guaranteeAmount) {
        this.guaranteeAmount = guaranteeAmount;
    }

    public float getGuaranteePercentage() {
        return guaranteePercentage;
    }

    public void setGuaranteePercentage(float guaranteePercentage) {
        this.guaranteePercentage = guaranteePercentage;
    }

    public List<DiscountApplied> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<DiscountApplied> discounts) {
        this.discounts = discounts;
    }

    //get babies for the V6
    public int getBabies() {
        try {
            return Integer.parseInt(configurationType.substring(configurationType.length() - 1));
        } catch (Exception e) {
            return 0;
        }
    }

    public String getInventoryTicker(DBConnection dbConnection) throws DBAccessException {
        if (inventoryTicker == null && dbConnection != null && inventoryId != null) {
            final InventoryDBHandler inventoryDBHandler = new InventoryDBHandler(dbConnection);
            final Inventory inventoryById = inventoryDBHandler.getInventoryById(inventoryId);
            inventoryTicker = inventoryById.getTicker();
        }
        return inventoryTicker;
    }

    public String getInventoryTicker() {
        return inventoryTicker;
    }

    public void setInventoryTicker(String inventoryTicker) {
        this.inventoryTicker = inventoryTicker;
    }

    public boolean isOccupied(Date date) {
        Date dateAdded = (Date) dateCheckOut.clone();
        DateUtil.incrementDays(dateAdded, -1);
        return DateUtil.dateBetweenDaysRange(DateUtil.toBeginningOfTheDay(date), dateCheckIn, dateAdded);
    }

    public int getNights() {
        return DateUtil.daysBetweenDates(dateCheckIn, dateCheckOut);
    }

    public Boolean getCanceledByClient() {
        return canceledByClient;
    }

    public void setCanceledByClient(Boolean canceledByClient) {
        this.canceledByClient = canceledByClient;
    }

    public String getCancellationCause() {
        return cancellationCause;
    }

    public void setCancellationCause(String cancellationCause) {
        this.cancellationCause = cancellationCause;
    }

    public Date getCancellationDate() {
        return cancellationDate;
    }

    public void setCancellationDate(Date cancellationDate) {
        this.cancellationDate = cancellationDate;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public List<ResGuest> getGuestList() {
        return guestList;
    }

    public void setGuestList(List<ResGuest> guestList) {
        this.guestList = guestList;
    }

    public Map<String, String> getAdditionalRequests() {
        return additionalRequests;
    }

    public void setAdditionalRequests(Map<String, String> additionalRequests) {
        this.additionalRequests = additionalRequests;
    }

    @Override
    public String toString() {
        return "RoomStay{" +
                "id='" + id + '\'' +
                ", idConfirmation='" + idConfirmation + '\'' +
                ", inventoryId=" + inventoryId +
                ", dateCheckIn=" + DateUtil.calendarFormat(dateCheckIn) +
                ", dateCheckOut=" + DateUtil.calendarFormat(dateCheckOut) +
                ", roomRates=" + roomRates +
                ", services=" + services +
                ", discounts=" + discounts +
                ", totalAmount=" + totalAmount +
                ", quantity=" + quantity +
                ", capacity=" + capacity +
                ", comments='" + comments + '\'' +
                ", guestList='" + guestList + '\'' +
                ", externalCommission=" + externalCommission +
                ", inventoryTicker='" + inventoryTicker + '\'' +
                ", accommodationType='" + accommodationType + '\'' +
                ", mealPlanType='" + mealPlanType + '\'' +
                ", configurationType='" + configurationType + '\'' +
                ", conditionType='" + conditionType + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RoomStay))
            return false;

        RoomStay roomStay = (RoomStay) o;

        if (canceledByClient != roomStay.canceledByClient)
            return false;
        if (capacity != roomStay.capacity)
            return false;
        if (Float.compare(roomStay.guaranteeAmount, guaranteeAmount) != 0)
            return false;
        if (Float.compare(roomStay.guaranteePercentage, guaranteePercentage) != 0)
            return false;
        if (quantity != roomStay.quantity)
            return false;
        if (Float.compare(roomStay.totalAmount, totalAmount) != 0)
            return false;
        if (accommodationType != null ? !accommodationType.equals(
                roomStay.accommodationType) : roomStay.accommodationType != null)
            return false;
        if (additionalRequests != null ? !additionalRequests.equals(
                roomStay.additionalRequests) : roomStay.additionalRequests != null)
            return false;
        if (cancellationCause != null ? !cancellationCause.equals(
                roomStay.cancellationCause) : roomStay.cancellationCause != null)
            return false;
        if (cancellationDate != null ? !cancellationDate.equals(
                roomStay.cancellationDate) : roomStay.cancellationDate != null)
            return false;
        if (comments != null ? !comments.equals(roomStay.comments) : roomStay.comments != null)
            return false;
        if (conditionType != null ? !conditionType.equals(roomStay.conditionType) : roomStay.conditionType != null)
            return false;
        if (configurationType != null ? !configurationType.equals(
                roomStay.configurationType) : roomStay.configurationType != null)
            return false;
        if (dateCheckIn != null ? !dateCheckIn.equals(roomStay.dateCheckIn) : roomStay.dateCheckIn != null)
            return false;
        if (dateCheckOut != null ? !dateCheckOut.equals(roomStay.dateCheckOut) : roomStay.dateCheckOut != null)
            return false;
        if (dateModification != null ? !dateModification.equals(
                roomStay.dateModification) : roomStay.dateModification != null)
            return false;
        if (discounts != null ? !discounts.equals(roomStay.discounts) : roomStay.discounts != null)
            return false;
        if (externalCommission != null ? !externalCommission.equals(
                roomStay.externalCommission) : roomStay.externalCommission != null)
            return false;
        if (guestList != null ? !guestList.equals(roomStay.guestList) : roomStay.guestList != null)
            return false;
        if (id != null ? !id.equals(roomStay.id) : roomStay.id != null)
            return false;
        if (idConfirmation != null ? !idConfirmation.equals(roomStay.idConfirmation) : roomStay.idConfirmation != null)
            return false;
        if (inventoryId != null ? !inventoryId.equals(roomStay.inventoryId) : roomStay.inventoryId != null)
            return false;
        if (inventoryTicker != null ? !inventoryTicker.equals(
                roomStay.inventoryTicker) : roomStay.inventoryTicker != null)
            return false;
        if (mealPlanType != null ? !mealPlanType.equals(roomStay.mealPlanType) : roomStay.mealPlanType != null)
            return false;
        if (roomRates != null ? !roomRates.equals(roomStay.roomRates) : roomStay.roomRates != null)
            return false;
        if (services != null ? !services.equals(roomStay.services) : roomStay.services != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (idConfirmation != null ? idConfirmation.hashCode() : 0);
        result = 31 * result + (inventoryId != null ? inventoryId.hashCode() : 0);
        result = 31 * result + (dateCheckIn != null ? dateCheckIn.hashCode() : 0);
        result = 31 * result + (dateCheckOut != null ? dateCheckOut.hashCode() : 0);
        result = 31 * result + (services != null ? services.hashCode() : 0);
        result = 31 * result + (discounts != null ? discounts.hashCode() : 0);
        result = 31 * result + (roomRates != null ? roomRates.hashCode() : 0);
        result = 31 * result + (guaranteePercentage != +0.0f ? Float.floatToIntBits(guaranteePercentage) : 0);
        result = 31 * result + (guaranteeAmount != +0.0f ? Float.floatToIntBits(guaranteeAmount) : 0);
        result = 31 * result + (totalAmount != +0.0f ? Float.floatToIntBits(totalAmount) : 0);
        result = 31 * result + capacity;
        result = 31 * result + quantity;
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (guestList != null ? guestList.hashCode() : 0);
        result = 31 * result + (additionalRequests != null ? additionalRequests.hashCode() : 0);
        result = 31 * result + (cancellationDate != null ? cancellationDate.hashCode() : 0);
        result = 31 * result + (cancellationCause != null ? cancellationCause.hashCode() : 0);
        result = 31 * result + (canceledByClient ? 1 : 0);
        result = 31 * result + (dateModification != null ? dateModification.hashCode() : 0);
        result = 31 * result + (externalCommission != null ? externalCommission.hashCode() : 0);
        result = 31 * result + (inventoryTicker != null ? inventoryTicker.hashCode() : 0);
        result = 31 * result + (accommodationType != null ? accommodationType.hashCode() : 0);
        result = 31 * result + (mealPlanType != null ? mealPlanType.hashCode() : 0);
        result = 31 * result + (configurationType != null ? configurationType.hashCode() : 0);
        result = 31 * result + (conditionType != null ? conditionType.hashCode() : 0);
        return result;
    }
}
