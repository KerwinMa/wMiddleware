//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.09.20 at 09:44:34 AM CEST 
//


package com.witbooking.middleware.integration.ota.model;

import com.witbooking.middleware.utils.XMLUtils;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="POS" type="{http://www.opentravel.org/OTA/2003/05}POS_Type" minOccurs="0"/>
 *         &lt;element name="AvailRequestSegments">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;extension base="{http://www.opentravel.org/OTA/2003/05}AvailRequestSegmentsType">
 *                 &lt;attribute name="MaximumWaitTime" type="{http://www.w3.org/2001/XMLSchema}decimal" />
 *               &lt;/extension>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="HotelReservationIDs" type="{http://www.opentravel.org/OTA/2003/05}HotelReservationIDsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}MaxResponsesGroup"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}MapRequestedGroup"/>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}OTA_PayloadStdAttributes"/>
 *       &lt;attribute name="SummaryOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="SortOrder">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.opentravel.org/OTA/2003/05}AlphaLength1">
 *             &lt;enumeration value="A"/>
 *             &lt;enumeration value="D"/>
 *             &lt;enumeration value="N"/>
 *             &lt;enumeration value="C"/>
 *             &lt;enumeration value="P"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="AvailRatesOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="OnRequestInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="BestOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="RateRangeOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="ExactMatchOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="AllowPartialAvail" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="RequestedCurrency" type="{http://www.opentravel.org/OTA/2003/05}AlphaLength3" />
 *       &lt;attribute name="RequestedCurrencyIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="IsModify" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="SearchCacheLevel">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *             &lt;enumeration value="Live"/>
 *             &lt;enumeration value="VeryRecent"/>
 *             &lt;enumeration value="LessRecent"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="HotelStayOnly" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="RateDetailsInd" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "pos",
    "availRequestSegments",
    "hotelReservationIDs"
})
@XmlRootElement(name = "OTA_HotelAvailRQ")
public class OTAHotelAvailRQ  implements Serializable {

    @XmlElement(name = "POS")
    protected POSType pos;
    @XmlElement(name = "AvailRequestSegments", required = true)
    protected AvailRequestSegments availRequestSegments;
    @XmlElement(name = "HotelReservationIDs")
    protected HotelReservationIDsType hotelReservationIDs;
    @XmlAttribute(name = "SummaryOnly")
    protected Boolean summaryOnly;
    @XmlAttribute(name = "SortOrder")
    protected String sortOrder;
    @XmlAttribute(name = "AvailRatesOnly")
    protected Boolean availRatesOnly;
    @XmlAttribute(name = "OnRequestInd")
    protected Boolean onRequestInd;
    @XmlAttribute(name = "BestOnly")
    protected Boolean bestOnly;
    @XmlAttribute(name = "RateRangeOnly")
    protected Boolean rateRangeOnly;
    @XmlAttribute(name = "ExactMatchOnly")
    protected Boolean exactMatchOnly;
    @XmlAttribute(name = "AllowPartialAvail")
    protected Boolean allowPartialAvail;
    @XmlAttribute(name = "RequestedCurrency")
    protected String requestedCurrency;
    @XmlAttribute(name = "RequestedCurrencyIndicator")
    protected Boolean requestedCurrencyIndicator;
    @XmlAttribute(name = "IsModify")
    protected Boolean isModify;
    @XmlAttribute(name = "SearchCacheLevel")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String searchCacheLevel;
    @XmlAttribute(name = "HotelStayOnly")
    protected Boolean hotelStayOnly;
    @XmlAttribute(name = "RateDetailsInd")
    protected Boolean rateDetailsInd;
    @XmlAttribute(name = "MaxResponses")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger maxResponses;
    @XmlAttribute(name = "MapRequired")
    protected Boolean mapRequired;
    @XmlAttribute(name = "MapHeight")
    protected Integer mapHeight;
    @XmlAttribute(name = "MapWidth")
    protected Integer mapWidth;
    @XmlAttribute(name = "EchoToken")
    protected String echoToken;
    @XmlAttribute(name = "TimeStamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeStamp = XMLUtils.getNow();
    @XmlAttribute(name = "Target")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String target;
    @XmlAttribute(name = "Version", required = true)
    protected BigDecimal version;
    @XmlAttribute(name = "TransactionIdentifier")
    protected String transactionIdentifier;
    @XmlAttribute(name = "SequenceNmbr")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger sequenceNmbr;
    @XmlAttribute(name = "TransactionStatusCode")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String transactionStatusCode;
    @XmlAttribute(name = "RetransmissionIndicator")
    protected Boolean retransmissionIndicator;
    @XmlAttribute(name = "CorrelationID")
    protected String correlationID;
    @XmlAttribute(name = "AltLangID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String altLangID;
    @XmlAttribute(name = "PrimaryLangID")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    @XmlSchemaType(name = "language")
    protected String primaryLangID;

    /**
     * Gets the value of the pos property.
     * 
     * @return
     *     possible object is
     *     {@link com.witbooking.middleware.integration.ota.model.POSType }
     *
     */
    public POSType getPOS() {
        return pos;
    }

    /**
     * Sets the value of the pos property.
     *
     * @param value
     *     allowed object is
     *     {@link com.witbooking.middleware.integration.ota.model.POSType }
     *
     */
    public void setPOS(POSType value) {
        this.pos = value;
    }

    /**
     * Gets the value of the availRequestSegments property.
     *
     * @return
     *     possible object is
     *     {@link com.witbooking.middleware.integration.ota.model.OTAHotelAvailRQ.AvailRequestSegments }
     *
     */
    public AvailRequestSegments getAvailRequestSegments() {
        return availRequestSegments;
    }

    /**
     * Sets the value of the availRequestSegments property.
     *
     * @param value
     *     allowed object is
     *     {@link com.witbooking.middleware.integration.ota.model.OTAHotelAvailRQ.AvailRequestSegments }
     *
     */
    public void setAvailRequestSegments(AvailRequestSegments value) {
        this.availRequestSegments = value;
    }

    /**
     * Gets the value of the hotelReservationIDs property.
     *
     * @return
     *     possible object is
     *     {@link com.witbooking.middleware.integration.ota.model.HotelReservationIDsType }
     *
     */
    public HotelReservationIDsType getHotelReservationIDs() {
        return hotelReservationIDs;
    }

    /**
     * Sets the value of the hotelReservationIDs property.
     *
     * @param value
     *     allowed object is
     *     {@link com.witbooking.middleware.integration.ota.model.HotelReservationIDsType }
     *
     */
    public void setHotelReservationIDs(HotelReservationIDsType value) {
        this.hotelReservationIDs = value;
    }

    /**
     * Gets the value of the summaryOnly property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isSummaryOnly() {
        return summaryOnly;
    }

    /**
     * Sets the value of the summaryOnly property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setSummaryOnly(Boolean value) {
        this.summaryOnly = value;
    }

    /**
     * Gets the value of the sortOrder property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSortOrder() {
        return sortOrder;
    }

    /**
     * Sets the value of the sortOrder property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSortOrder(String value) {
        this.sortOrder = value;
    }

    /**
     * Gets the value of the availRatesOnly property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isAvailRatesOnly() {
        return availRatesOnly;
    }

    /**
     * Sets the value of the availRatesOnly property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setAvailRatesOnly(Boolean value) {
        this.availRatesOnly = value;
    }

    /**
     * Gets the value of the onRequestInd property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isOnRequestInd() {
        return onRequestInd;
    }

    /**
     * Sets the value of the onRequestInd property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setOnRequestInd(Boolean value) {
        this.onRequestInd = value;
    }

    /**
     * Gets the value of the bestOnly property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isBestOnly() {
        return bestOnly;
    }

    /**
     * Sets the value of the bestOnly property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setBestOnly(Boolean value) {
        this.bestOnly = value;
    }

    /**
     * Gets the value of the rateRangeOnly property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isRateRangeOnly() {
        return rateRangeOnly;
    }

    /**
     * Sets the value of the rateRangeOnly property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setRateRangeOnly(Boolean value) {
        this.rateRangeOnly = value;
    }

    /**
     * Gets the value of the exactMatchOnly property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isExactMatchOnly() {
        return exactMatchOnly;
    }

    /**
     * Sets the value of the exactMatchOnly property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setExactMatchOnly(Boolean value) {
        this.exactMatchOnly = value;
    }

    /**
     * Gets the value of the allowPartialAvail property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isAllowPartialAvail() {
        return allowPartialAvail;
    }

    /**
     * Sets the value of the allowPartialAvail property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setAllowPartialAvail(Boolean value) {
        this.allowPartialAvail = value;
    }

    /**
     * Gets the value of the requestedCurrency property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getRequestedCurrency() {
        return requestedCurrency;
    }

    /**
     * Sets the value of the requestedCurrency property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setRequestedCurrency(String value) {
        this.requestedCurrency = value;
    }

    /**
     * Gets the value of the requestedCurrencyIndicator property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isRequestedCurrencyIndicator() {
        return requestedCurrencyIndicator;
    }

    /**
     * Sets the value of the requestedCurrencyIndicator property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setRequestedCurrencyIndicator(Boolean value) {
        this.requestedCurrencyIndicator = value;
    }

    /**
     * Gets the value of the isModify property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isIsModify() {
        return isModify;
    }

    /**
     * Sets the value of the isModify property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setIsModify(Boolean value) {
        this.isModify = value;
    }

    /**
     * Gets the value of the searchCacheLevel property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getSearchCacheLevel() {
        return searchCacheLevel;
    }

    /**
     * Sets the value of the searchCacheLevel property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setSearchCacheLevel(String value) {
        this.searchCacheLevel = value;
    }

    /**
     * Gets the value of the hotelStayOnly property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isHotelStayOnly() {
        return hotelStayOnly;
    }

    /**
     * Sets the value of the hotelStayOnly property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setHotelStayOnly(Boolean value) {
        this.hotelStayOnly = value;
    }

    /**
     * Gets the value of the rateDetailsInd property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isRateDetailsInd() {
        return rateDetailsInd;
    }

    /**
     * Sets the value of the rateDetailsInd property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setRateDetailsInd(Boolean value) {
        this.rateDetailsInd = value;
    }

    /**
     * Gets the value of the maxResponses property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getMaxResponses() {
        return maxResponses;
    }

    /**
     * Sets the value of the maxResponses property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setMaxResponses(BigInteger value) {
        this.maxResponses = value;
    }

    /**
     * Gets the value of the mapRequired property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isMapRequired() {
        return mapRequired;
    }

    /**
     * Sets the value of the mapRequired property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setMapRequired(Boolean value) {
        this.mapRequired = value;
    }

    /**
     * Gets the value of the mapHeight property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getMapHeight() {
        return mapHeight;
    }

    /**
     * Sets the value of the mapHeight property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setMapHeight(Integer value) {
        this.mapHeight = value;
    }

    /**
     * Gets the value of the mapWidth property.
     *
     * @return
     *     possible object is
     *     {@link Integer }
     *
     */
    public Integer getMapWidth() {
        return mapWidth;
    }

    /**
     * Sets the value of the mapWidth property.
     *
     * @param value
     *     allowed object is
     *     {@link Integer }
     *
     */
    public void setMapWidth(Integer value) {
        this.mapWidth = value;
    }

    /**
     * Gets the value of the echoToken property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getEchoToken() {
        return echoToken;
    }

    /**
     * Sets the value of the echoToken property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setEchoToken(String value) {
        this.echoToken = value;
    }

    /**
     * Gets the value of the timeStamp property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    /**
     * Gets the value of the target property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *
     */
    public void setVersion(BigDecimal value) {
        this.version = value;
    }

    /**
     * Gets the value of the transactionIdentifier property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets the value of the transactionIdentifier property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTransactionIdentifier(String value) {
        this.transactionIdentifier = value;
    }

    /**
     * Gets the value of the sequenceNmbr property.
     *
     * @return
     *     possible object is
     *     {@link BigInteger }
     *
     */
    public BigInteger getSequenceNmbr() {
        return sequenceNmbr;
    }

    /**
     * Sets the value of the sequenceNmbr property.
     *
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *
     */
    public void setSequenceNmbr(BigInteger value) {
        this.sequenceNmbr = value;
    }

    /**
     * Gets the value of the transactionStatusCode property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getTransactionStatusCode() {
        return transactionStatusCode;
    }

    /**
     * Sets the value of the transactionStatusCode property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setTransactionStatusCode(String value) {
        this.transactionStatusCode = value;
    }

    /**
     * Gets the value of the retransmissionIndicator property.
     *
     * @return
     *     possible object is
     *     {@link Boolean }
     *
     */
    public Boolean isRetransmissionIndicator() {
        return retransmissionIndicator;
    }

    /**
     * Sets the value of the retransmissionIndicator property.
     *
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *
     */
    public void setRetransmissionIndicator(Boolean value) {
        this.retransmissionIndicator = value;
    }

    /**
     * Gets the value of the correlationID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCorrelationID() {
        return correlationID;
    }

    /**
     * Sets the value of the correlationID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCorrelationID(String value) {
        this.correlationID = value;
    }

    /**
     * Gets the value of the altLangID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAltLangID() {
        return altLangID;
    }

    /**
     * Sets the value of the altLangID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAltLangID(String value) {
        this.altLangID = value;
    }

    /**
     * Gets the value of the primaryLangID property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getPrimaryLangID() {
        return primaryLangID;
    }

    /**
     * Sets the value of the primaryLangID property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setPrimaryLangID(String value) {
        this.primaryLangID = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;extension base="{http://www.opentravel.org/OTA/2003/05}AvailRequestSegmentsType">
     *       &lt;attribute name="MaximumWaitTime" type="{http://www.w3.org/2001/XMLSchema}decimal" />
     *     &lt;/extension>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class AvailRequestSegments
        extends AvailRequestSegmentsType
    {

        @XmlAttribute(name = "MaximumWaitTime")
        protected BigDecimal maximumWaitTime;

        /**
         * Gets the value of the maximumWaitTime property.
         *
         * @return
         *     possible object is
         *     {@link BigDecimal }
         *
         */
        public BigDecimal getMaximumWaitTime() {
            return maximumWaitTime;
        }

        /**
         * Sets the value of the maximumWaitTime property.
         *
         * @param value
         *     allowed object is
         *     {@link BigDecimal }
         *     
         */
        public void setMaximumWaitTime(BigDecimal value) {
            this.maximumWaitTime = value;
        }

    }

}
