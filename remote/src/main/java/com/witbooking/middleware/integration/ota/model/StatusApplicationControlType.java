//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.28 at 02:33:51 PM CEST 
//


package com.witbooking.middleware.integration.ota.model;

import com.google.common.base.Objects;
import com.witbooking.middleware.utils.serializers.JaxbDateWithoutTimeSerializer;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * <p>Java class for StatusApplicationControlType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="StatusApplicationControlType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DestinationSystemCodes" type="{http://www.opentravel.org/OTA/2003/05}DestinationSystemCodesType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}StatusApplicationGroup"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}RatePlanCodeTypeGroup"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}DOW_PatternGroup"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}InvBlockCodeApplyGroup"/>
 *       &lt;attribute name="RateTier" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to64" />
 *       &lt;attribute name="AllRateCode" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="AllInvCode" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="InvBlockCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to32" />
 *       &lt;attribute name="Override" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="QuoteID" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to32" />
 *       &lt;attribute name="SubBlockCode" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to32" />
 *       &lt;attribute name="WingIdentifier" type="{http://www.opentravel.org/OTA/2003/05}StringLength1to32" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StatusApplicationControlType", propOrder = {
        "destinationSystemCodes"
})
public class StatusApplicationControlType {

    @XmlElement(name = "DestinationSystemCodes")
    protected DestinationSystemCodesType destinationSystemCodes;
    @XmlAttribute(name = "RateTier")
    protected String rateTier;
    @XmlAttribute(name = "AllRateCode")
    protected Boolean allRateCode;
    @XmlAttribute(name = "AllInvCode")
    protected Boolean allInvCode;
    @XmlAttribute(name = "InvBlockCode")
    protected String invBlockCode;
    @XmlAttribute(name = "Override")
    protected Boolean override;
    @XmlAttribute(name = "QuoteID")
    protected String quoteID;
    @XmlAttribute(name = "SubBlockCode")
    protected String subBlockCode;
    @XmlAttribute(name = "WingIdentifier")
    protected String wingIdentifier;
    /**
     * start is a required attribute and represents the day that begins the
     * period. Please note,only dates in the future can be updated.
     */
    @XmlAttribute(name = "Start")
    @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
    private Date start;
    /**
     * End (Required): represents the day that ends the period.
     */
    @XmlAttribute(name = "End")
    @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
    private Date end;
    /**
     * InvTypeCode is an required parameter and specify the the BOOKING.COM room
     * ID which you are updating.
     */
    @XmlAttribute(name = "InvTypeCode")
    private String invTypeCode;
    /**
     * RatePlanCode is an optional parameter and specify the BOOKING.COM rate
     * category ID which you are updating. Needs to be left out when specifying
     * {@link AvailStatusMessageType#bookingLimit} in {@link AvailStatusMessageType}, as
     * the availability is updated on room level.
     *
     * @see AvailStatusMessageType#bookingLimit
     */
    @XmlAttribute(name = "RatePlanCode")
    private String ratePlanCode;
    @XmlAttribute(name = "Duration")
    protected String duration;
    @XmlAttribute(name = "RatePlanType")
    protected String ratePlanType;
    @XmlAttribute(name = "RatePlanID")
    protected String ratePlanID;
    @XmlAttribute(name = "RatePlanQualifier")
    protected Boolean ratePlanQualifier;
    @XmlAttribute(name = "PromotionCode")
    protected String promotionCode;
    @XmlAttribute(name = "PromotionVendorCode")
    protected List<String> promotionVendorCode;
    @XmlAttribute(name = "RatePlanCategory")
    protected String ratePlanCategory;
    @XmlAttribute(name = "InvCodeApplication")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String invCodeApplication;
    @XmlAttribute(name = "InvCode")
    protected String invCode;
    @XmlAttribute(name = "InvType")
    protected String invType;
    @XmlAttribute(name = "IsRoom")
    protected Boolean isRoom;
    @XmlAttribute(name = "RatePlanCodeType")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String ratePlanCodeType;
    @XmlAttribute(name = "Mon")
    protected Boolean mon;
    @XmlAttribute(name = "Tue")
    protected Boolean tue;
    @XmlAttribute(name = "Weds")
    protected Boolean weds;
    @XmlAttribute(name = "Thur")
    protected Boolean thur;
    @XmlAttribute(name = "Fri")
    protected Boolean fri;
    @XmlAttribute(name = "Sat")
    protected Boolean sat;
    @XmlAttribute(name = "Sun")
    protected Boolean sun;
    @XmlAttribute(name = "InvBlockCodeApply")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String invBlockCodeApply;

    /**
     * Gets the value of the destinationSystemCodes property.
     *
     * @return possible object is
     * {@link DestinationSystemCodesType }
     */
    public DestinationSystemCodesType getDestinationSystemCodes() {
        return destinationSystemCodes;
    }

    /**
     * Sets the value of the destinationSystemCodes property.
     *
     * @param value allowed object is
     *              {@link DestinationSystemCodesType }
     */
    public void setDestinationSystemCodes(DestinationSystemCodesType value) {
        this.destinationSystemCodes = value;
    }

    /**
     * Gets the value of the rateTier property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRateTier() {
        return rateTier;
    }

    /**
     * Sets the value of the rateTier property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRateTier(String value) {
        this.rateTier = value;
    }

    /**
     * Gets the value of the allRateCode property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isAllRateCode() {
        return allRateCode;
    }

    /**
     * Sets the value of the allRateCode property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setAllRateCode(Boolean value) {
        this.allRateCode = value;
    }

    /**
     * Gets the value of the allInvCode property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isAllInvCode() {
        return allInvCode;
    }

    /**
     * Sets the value of the allInvCode property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setAllInvCode(Boolean value) {
        this.allInvCode = value;
    }

    /**
     * Gets the value of the invBlockCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getInvBlockCode() {
        return invBlockCode;
    }

    /**
     * Sets the value of the invBlockCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInvBlockCode(String value) {
        this.invBlockCode = value;
    }

    /**
     * Gets the value of the override property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isOverride() {
        return override;
    }

    /**
     * Sets the value of the override property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setOverride(Boolean value) {
        this.override = value;
    }

    /**
     * Gets the value of the quoteID property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getQuoteID() {
        return quoteID;
    }

    /**
     * Sets the value of the quoteID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setQuoteID(String value) {
        this.quoteID = value;
    }

    /**
     * Gets the value of the subBlockCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSubBlockCode() {
        return subBlockCode;
    }

    /**
     * Sets the value of the subBlockCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSubBlockCode(String value) {
        this.subBlockCode = value;
    }

    /**
     * Gets the value of the wingIdentifier property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getWingIdentifier() {
        return wingIdentifier;
    }

    /**
     * Sets the value of the wingIdentifier property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setWingIdentifier(String value) {
        this.wingIdentifier = value;
    }

    /**
     * Gets the value of the start property.
     *
     * @return possible object is
     * {@link String }
     */
    public Date getStart() {
        return start;
    }

    /**
     * Sets the value of the start property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStart(Date value) {
        this.start = value;
    }

    /**
     * Gets the value of the duration property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDuration(String value) {
        this.duration = value;
    }

    /**
     * Gets the value of the end property.
     *
     * @return possible object is
     * {@link String }
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Sets the value of the end property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setEnd(Date value) {
        this.end = value;
    }

    /**
     * Gets the value of the ratePlanType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRatePlanType() {
        return ratePlanType;
    }

    /**
     * Sets the value of the ratePlanType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRatePlanType(String value) {
        this.ratePlanType = value;
    }

    /**
     * Gets the value of the ratePlanCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRatePlanCode() {
        return ratePlanCode;
    }

    /**
     * Sets the value of the ratePlanCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRatePlanCode(String value) {
        this.ratePlanCode = value;
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
     * Gets the value of the ratePlanQualifier property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isRatePlanQualifier() {
        return ratePlanQualifier;
    }

    /**
     * Sets the value of the ratePlanQualifier property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setRatePlanQualifier(Boolean value) {
        this.ratePlanQualifier = value;
    }

    /**
     * Gets the value of the promotionCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPromotionCode() {
        return promotionCode;
    }

    /**
     * Sets the value of the promotionCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPromotionCode(String value) {
        this.promotionCode = value;
    }

    /**
     * Gets the value of the promotionVendorCode property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the promotionVendorCode property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPromotionVendorCode().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getPromotionVendorCode() {
        if (promotionVendorCode == null) {
            promotionVendorCode = new ArrayList<String>();
        }
        return this.promotionVendorCode;
    }

    /**
     * Gets the value of the ratePlanCategory property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRatePlanCategory() {
        return ratePlanCategory;
    }

    /**
     * Sets the value of the ratePlanCategory property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRatePlanCategory(String value) {
        this.ratePlanCategory = value;
    }

    /**
     * Gets the value of the invCodeApplication property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getInvCodeApplication() {
        return invCodeApplication;
    }

    /**
     * Sets the value of the invCodeApplication property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInvCodeApplication(String value) {
        this.invCodeApplication = value;
    }

    /**
     * Gets the value of the invCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getInvCode() {
        return invCode;
    }

    /**
     * Sets the value of the invCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInvCode(String value) {
        this.invCode = value;
    }

    /**
     * Gets the value of the invType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getInvType() {
        return invType;
    }

    /**
     * Sets the value of the invType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInvType(String value) {
        this.invType = value;
    }

    /**
     * Gets the value of the invTypeCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getInvTypeCode() {
        return invTypeCode;
    }

    /**
     * Sets the value of the invTypeCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInvTypeCode(String value) {
        this.invTypeCode = value;
    }

    /**
     * Gets the value of the isRoom property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isIsRoom() {
        return isRoom;
    }

    /**
     * Sets the value of the isRoom property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setIsRoom(Boolean value) {
        this.isRoom = value;
    }

    /**
     * Gets the value of the ratePlanCodeType property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRatePlanCodeType() {
        return ratePlanCodeType;
    }

    /**
     * Sets the value of the ratePlanCodeType property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRatePlanCodeType(String value) {
        this.ratePlanCodeType = value;
    }

    /**
     * Gets the value of the mon property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isMon() {
        return mon;
    }

    /**
     * Sets the value of the mon property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setMon(Boolean value) {
        this.mon = value;
    }

    /**
     * Gets the value of the tue property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isTue() {
        return tue;
    }

    /**
     * Sets the value of the tue property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setTue(Boolean value) {
        this.tue = value;
    }

    /**
     * Gets the value of the weds property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isWeds() {
        return weds;
    }

    /**
     * Sets the value of the weds property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setWeds(Boolean value) {
        this.weds = value;
    }

    /**
     * Gets the value of the thur property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isThur() {
        return thur;
    }

    /**
     * Sets the value of the thur property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setThur(Boolean value) {
        this.thur = value;
    }

    /**
     * Gets the value of the fri property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isFri() {
        return fri;
    }

    /**
     * Sets the value of the fri property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setFri(Boolean value) {
        this.fri = value;
    }

    /**
     * Gets the value of the sat property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isSat() {
        return sat;
    }

    /**
     * Sets the value of the sat property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setSat(Boolean value) {
        this.sat = value;
    }

    /**
     * Gets the value of the sun property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isSun() {
        return sun;
    }

    /**
     * Sets the value of the sun property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setSun(Boolean value) {
        this.sun = value;
    }

    /**
     * Gets the value of the invBlockCodeApply property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getInvBlockCodeApply() {
        return invBlockCodeApply;
    }

    /**
     * Sets the value of the invBlockCodeApply property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setInvBlockCodeApply(String value) {
        this.invBlockCodeApply = value;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("destinationSystemCodes", destinationSystemCodes)
                .add("rateTier", rateTier)
                .add("allRateCode", allRateCode)
                .add("allInvCode", allInvCode)
                .add("invBlockCode", invBlockCode)
                .add("override", override)
                .add("quoteID", quoteID)
                .add("subBlockCode", subBlockCode)
                .add("wingIdentifier", wingIdentifier)
                .add("start", start)
                .add("end", end)
                .add("invTypeCode", invTypeCode)
                .add("ratePlanCode", ratePlanCode)
                .add("duration", duration)
                .add("ratePlanType", ratePlanType)
                .add("ratePlanID", ratePlanID)
                .add("ratePlanQualifier", ratePlanQualifier)
                .add("promotionCode", promotionCode)
                .add("promotionVendorCode", promotionVendorCode)
                .add("ratePlanCategory", ratePlanCategory)
                .add("invCodeApplication", invCodeApplication)
                .add("invCode", invCode)
                .add("invType", invType)
                .add("isRoom", isRoom)
                .add("ratePlanCodeType", ratePlanCodeType)
                .add("mon", mon)
                .add("tue", tue)
                .add("weds", weds)
                .add("thur", thur)
                .add("fri", fri)
                .add("sat", sat)
                .add("sun", sun)
                .add("invBlockCodeApply", invBlockCodeApply)
                .toString();
    }
}
