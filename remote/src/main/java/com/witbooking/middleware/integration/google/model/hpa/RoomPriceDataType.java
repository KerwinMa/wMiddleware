
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for roomPriceDataType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="roomPriceDataType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Tagline1" type="{}localizedText" minOccurs="0"/>
 *         &lt;element name="Tagline2" type="{}localizedText" minOccurs="0"/>
 *         &lt;element name="RoomID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RatePlanID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Name" type="{}localizedText" minOccurs="0"/>
 *         &lt;element name="Description" type="{}localizedText" minOccurs="0"/>
 *         &lt;element name="PhotoURL" type="{}photoUrlType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Capacity" type="{}occupancyType" minOccurs="0"/>
 *         &lt;element name="Occupancy" type="{}occupancyType" minOccurs="0"/>
 *         &lt;element name="ChargeCurrency" type="{}chargeCurrencyType" minOccurs="0"/>
 *         &lt;element name="Refundable" type="{}refundableType" minOccurs="0"/>
 *         &lt;element name="BreakfastIncluded" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ParkingIncluded" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="InternetIncluded" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Baserate" type="{}baseRateType" minOccurs="0"/>
 *         &lt;element name="Tax" type="{}priceCurrencyType" minOccurs="0"/>
 *         &lt;element name="OtherFees" type="{}priceCurrencyType" minOccurs="0"/>
 *         &lt;element name="Custom1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Custom5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="AllowablePointsOfSale" type="{}allowablePointsOfSaleType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "roomPriceDataType", propOrder = {
        "tagline1",
        "tagline2",
        "roomID",
        "ratePlanID",
        "name",
        "description",
        "photoURL",
        "capacity",
        "occupancy",
        "chargeCurrency",
        "refundable",
        "breakfastIncluded",
        "parkingIncluded",
        "internetIncluded",
        "baserate",
        "tax",
        "otherFees",
        "custom1",
        "custom2",
        "custom3",
        "custom4",
        "custom5",
        "allowablePointsOfSale"
})
public class RoomPriceDataType {

    @XmlElement(name = "Tagline1")
    protected LocalizedText tagline1;
    @XmlElement(name = "Tagline2")
    protected LocalizedText tagline2;
    @XmlElement(name = "RoomID")
    protected String roomID;
    @XmlElement(name = "RatePlanID")
    protected String ratePlanID;
    @XmlElement(name = "Name")
    protected LocalizedText name;
    @XmlElement(name = "Description")
    protected LocalizedText description;
    @XmlElement(name = "PhotoURL")
    protected List<PhotoUrlType> photoURL;
    @XmlElement(name = "Capacity")
    protected Integer capacity;
    @XmlElement(name = "Occupancy")
    protected Integer occupancy;
    @XmlElement(name = "ChargeCurrency")
    protected ChargeCurrencyType chargeCurrency;
    @XmlElement(name = "Refundable")
    protected RefundableType refundable;
    @XmlElement(name = "BreakfastIncluded")
    protected Boolean breakfastIncluded;
    @XmlElement(name = "ParkingIncluded")
    protected Boolean parkingIncluded;
    @XmlElement(name = "InternetIncluded")
    protected Boolean internetIncluded;
    @XmlElement(name = "Baserate")
    protected BaseRateType baserate;
    @XmlElement(name = "Tax")
    protected PriceCurrencyType tax;
    @XmlElement(name = "OtherFees")
    protected PriceCurrencyType otherFees;
    @XmlElement(name = "Custom1")
    protected String custom1;
    @XmlElement(name = "Custom2")
    protected String custom2;
    @XmlElement(name = "Custom3")
    protected String custom3;
    @XmlElement(name = "Custom4")
    protected String custom4;
    @XmlElement(name = "Custom5")
    protected String custom5;
    @XmlElement(name = "AllowablePointsOfSale")
    protected AllowablePointsOfSaleType allowablePointsOfSale;

    /**
     * Gets the value of the tagline1 property.
     *
     * @return possible object is
     * {@link LocalizedText }
     */
    public LocalizedText getTagline1() {
        return tagline1;
    }

    /**
     * Sets the value of the tagline1 property.
     *
     * @param value allowed object is
     *              {@link LocalizedText }
     */
    public void setTagline1(LocalizedText value) {
        this.tagline1 = value;
    }

    /**
     * Gets the value of the tagline2 property.
     *
     * @return possible object is
     * {@link LocalizedText }
     */
    public LocalizedText getTagline2() {
        return tagline2;
    }

    /**
     * Sets the value of the tagline2 property.
     *
     * @param value allowed object is
     *              {@link LocalizedText }
     */
    public void setTagline2(LocalizedText value) {
        this.tagline2 = value;
    }

    /**
     * Gets the value of the roomID property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRoomID() {
        return roomID;
    }

    /**
     * Sets the value of the roomID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRoomID(String value) {
        this.roomID = value;
    }

    /**
     * Gets the value of the ratePlanID property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRatePlanID() {
        return ratePlanID;
    }

    /**
     * Sets the value of the ratePlanID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRatePlanID(String value) {
        this.ratePlanID = value;
    }

    /**
     * Gets the value of the name property.
     *
     * @return possible object is
     * {@link LocalizedText }
     */
    public LocalizedText getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     *
     * @param value allowed object is
     *              {@link LocalizedText }
     */
    public void setName(LocalizedText value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     *
     * @return possible object is
     * {@link LocalizedText }
     */
    public LocalizedText getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     *
     * @param value allowed object is
     *              {@link LocalizedText }
     */
    public void setDescription(LocalizedText value) {
        this.description = value;
    }

    /**
     * Gets the value of the photoURL property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the photoURL property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhotoURL().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link PhotoUrlType }
     */
    public List<PhotoUrlType> getPhotoURL() {
        if (photoURL == null) {
            photoURL = new ArrayList<PhotoUrlType>();
        }
        return this.photoURL;
    }

    public void addPhotoUrl(PhotoUrlType photoUrlType) {
        if (photoURL == null) {
            photoURL = new ArrayList<PhotoUrlType>();
        }
        if (photoUrlType != null) photoURL.add(photoUrlType);
    }

    /**
     * Gets the value of the capacity property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getCapacity() {
        return capacity;
    }

    /**
     * Sets the value of the capacity property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setCapacity(Integer value) {
        this.capacity = value;
    }

    /**
     * Gets the value of the occupancy property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getOccupancy() {
        return occupancy;
    }

    /**
     * Sets the value of the occupancy property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setOccupancy(Integer value) {
        this.occupancy = value;
    }

    /**
     * Gets the value of the chargeCurrency property.
     *
     * @return possible object is
     * {@link ChargeCurrencyType }
     */
    public ChargeCurrencyType getChargeCurrency() {
        return chargeCurrency;
    }

    /**
     * Sets the value of the chargeCurrency property.
     *
     * @param value allowed object is
     *              {@link ChargeCurrencyType }
     */
    public void setChargeCurrency(ChargeCurrencyType value) {
        this.chargeCurrency = value;
    }

    /**
     * Gets the value of the refundable property.
     *
     * @return possible object is
     * {@link RefundableType }
     */
    public RefundableType getRefundable() {
        return refundable;
    }

    /**
     * Sets the value of the refundable property.
     *
     * @param value allowed object is
     *              {@link RefundableType }
     */
    public void setRefundable(RefundableType value) {
        this.refundable = value;
    }

    public void setRefundable(boolean value) {
        this.refundable = new RefundableType(value);
    }

    /**
     * Gets the value of the breakfastIncluded property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isBreakfastIncluded() {
        return breakfastIncluded;
    }

    /**
     * Sets the value of the breakfastIncluded property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setBreakfastIncluded(Boolean value) {
        this.breakfastIncluded = value;
    }

    /**
     * Gets the value of the parkingIncluded property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isParkingIncluded() {
        return parkingIncluded;
    }

    /**
     * Sets the value of the parkingIncluded property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setParkingIncluded(Boolean value) {
        this.parkingIncluded = value;
    }

    /**
     * Gets the value of the internetIncluded property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isInternetIncluded() {
        return internetIncluded;
    }

    /**
     * Sets the value of the internetIncluded property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setInternetIncluded(Boolean value) {
        this.internetIncluded = value;
    }

    /**
     * Gets the value of the baserate property.
     *
     * @return possible object is
     * {@link BaseRateType }
     */
    public BaseRateType getBaserate() {
        return baserate;
    }

    /**
     * Sets the value of the baserate property.
     *
     * @param value allowed object is
     *              {@link BaseRateType }
     */
    public void setBaserate(BaseRateType value) {
        this.baserate = value;
    }

    /**
     * Gets the value of the tax property.
     *
     * @return possible object is
     * {@link PriceCurrencyType }
     */
    public PriceCurrencyType getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     *
     * @param value allowed object is
     *              {@link PriceCurrencyType }
     */
    public void setTax(PriceCurrencyType value) {
        this.tax = value;
    }

    /**
     * Gets the value of the otherFees property.
     *
     * @return possible object is
     * {@link PriceCurrencyType }
     */
    public PriceCurrencyType getOtherFees() {
        return otherFees;
    }

    /**
     * Sets the value of the otherFees property.
     *
     * @param value allowed object is
     *              {@link PriceCurrencyType }
     */
    public void setOtherFees(PriceCurrencyType value) {
        this.otherFees = value;
    }

    /**
     * Gets the value of the custom1 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCustom1() {
        return custom1;
    }

    /**
     * Sets the value of the custom1 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustom1(String value) {
        this.custom1 = value;
    }

    /**
     * Gets the value of the custom2 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCustom2() {
        return custom2;
    }

    /**
     * Sets the value of the custom2 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustom2(String value) {
        this.custom2 = value;
    }

    /**
     * Gets the value of the custom3 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCustom3() {
        return custom3;
    }

    /**
     * Sets the value of the custom3 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustom3(String value) {
        this.custom3 = value;
    }

    /**
     * Gets the value of the custom4 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCustom4() {
        return custom4;
    }

    /**
     * Sets the value of the custom4 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustom4(String value) {
        this.custom4 = value;
    }

    /**
     * Gets the value of the custom5 property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCustom5() {
        return custom5;
    }

    /**
     * Sets the value of the custom5 property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCustom5(String value) {
        this.custom5 = value;
    }

    /**
     * Gets the value of the allowablePointsOfSale property.
     *
     * @return possible object is
     * {@link AllowablePointsOfSaleType }
     */
    public AllowablePointsOfSaleType getAllowablePointsOfSale() {
        return allowablePointsOfSale;
    }

    /**
     * Sets the value of the allowablePointsOfSale property.
     *
     * @param value allowed object is
     *              {@link AllowablePointsOfSaleType }
     */
    public void setAllowablePointsOfSale(AllowablePointsOfSaleType value) {
        this.allowablePointsOfSale = value;
    }

}
