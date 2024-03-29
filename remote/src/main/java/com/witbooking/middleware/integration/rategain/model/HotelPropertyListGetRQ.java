//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.20 at 03:01:00 PM CEST 
//
package com.witbooking.middleware.integration.rategain.model;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;

/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained
 * within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Authentication" type="{http://cgbridge.rategain.com/OTA/2012/05}AuthenticationType"/>
 *         &lt;element name="HotelPropertyListRequest">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="ChainCode" type="{http://cgbridge.rategain.com/OTA/2012/05}StringLength1to8" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://cgbridge.rategain.com/OTA/2012/05}MessageAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "authentication",
    "hotelPropertyListRequest"
})
@XmlRootElement(name = "HotelPropertyListGetRQ")
public class HotelPropertyListGetRQ extends RateGainValidationRQInterface {
    
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(HotelPropertyListGetRQ.class);

    @XmlElement(name = "Authentication", required = true)
    protected AuthenticationType authentication;
    @XmlElement(name = "HotelPropertyListRequest", required = true)
    protected HotelPropertyListGetRQ.HotelPropertyListRequest hotelPropertyListRequest;
    @XmlAttribute(name = "EchoToken")
    protected String echoToken;
    @XmlAttribute(name = "TimeStamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeStamp;
    @XmlAttribute(name = "Target")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String target;
    @XmlAttribute(name = "Version", required = true)
    protected BigDecimal version;
    @XmlAttribute(name = "TransactionIdentifier")
    protected String transactionIdentifier;

    /**
     * Gets the value of the authentication property.
     *
     * @return possible object is {@link AuthenticationType }
     *
     */
    @Override
    public AuthenticationType getAuthentication() {
        return authentication;
    }

    @Override
    protected ErrorsType validateClass() {
        return getHotelPropertyListRequest() == null
                               ? new ErrorsType(errorFound())
                               : null;
    }

    /**
     * Sets the value of the authentication property.
     *
     * @param value allowed object is {@link AuthenticationType }
     *
     */
    public void setAuthentication(AuthenticationType value) {
        this.authentication = value;
    }

    /**
     * Gets the value of the hotelPropertyListRequest property.
     *
     * @return possible object is
     *     {@link HotelPropertyListGetRQ.HotelPropertyListRequest }
     *
     */
    public HotelPropertyListGetRQ.HotelPropertyListRequest getHotelPropertyListRequest() {
        return hotelPropertyListRequest;
    }

    /**
     * Sets the value of the hotelPropertyListRequest property.
     *
     * @param value allowed object is
     *     {@link HotelPropertyListGetRQ.HotelPropertyListRequest }
     *
     */
    public void setHotelPropertyListRequest(HotelPropertyListGetRQ.HotelPropertyListRequest value) {
        this.hotelPropertyListRequest = value;
    }

    /**
     * Gets the value of the echoToken property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getEchoToken() {
        return echoToken;
    }

    /**
     * Sets the value of the echoToken property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setEchoToken(String value) {
        this.echoToken = value;
    }

    /**
     * Gets the value of the timeStamp property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    /**
     * Gets the value of the target property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    @Override
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setVersion(BigDecimal value) {
        this.version = value;
    }

    /**
     * Gets the value of the transactionIdentifier property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets the value of the transactionIdentifier property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTransactionIdentifier(String value) {
        this.transactionIdentifier = value;
    }

    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained
     * within this class.
     *
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="ChainCode" type="{http://cgbridge.rategain.com/OTA/2012/05}StringLength1to8" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     *
     *
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class HotelPropertyListRequest {

        @XmlAttribute(name = "ChainCode")
        protected String chainCode;

        /**
         * Gets the value of the chainCode property.
         *
         * @return possible object is {@link String }
         *
         */
        public String getChainCode() {
            return chainCode;
        }

        /**
         * Sets the value of the chainCode property.
         *
         * @param value allowed object is {@link String }
         *
         */
        public void setChainCode(String value) {
            this.chainCode = value;
        }
    }
}
