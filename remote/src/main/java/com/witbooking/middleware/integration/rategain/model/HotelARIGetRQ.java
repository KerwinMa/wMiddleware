//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.20 at 03:01:00 PM CEST 
//

package com.witbooking.middleware.integration.rategain.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


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
 *         &lt;element name="Authentication" type="{http://cgbridge.rategain.com/OTA/2012/05}AuthenticationType"/>
 *         &lt;element name="HotelARIGetRequests">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="HotelARIGetRequest" maxOccurs="unbounded">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="ProductReference" type="{http://cgbridge.rategain.com/OTA/2012/05}ProductReferenceType"/>
 *                             &lt;element name="ApplicationControl" type="{http://cgbridge.rategain.com/OTA/2012/05}ApplicationControlType"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *                 &lt;attribute name="HotelCode" use="required" type="{http://cgbridge.rategain.com/OTA/2012/05}StringLength1to64" />
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
    "hotelARIGetRequests"
})
@XmlRootElement(name = "HotelARIGetRQ")
public class HotelARIGetRQ extends RateGainValidationRQInterface {

    @XmlElement(name = "Authentication", required = true)
    protected AuthenticationType authentication;
    @XmlElement(name = "HotelARIGetRequests", required = true)
    protected HotelARIGetRQ.HotelARIGetRequests hotelARIGetRequests;
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
     * @return
     *     possible object is
     *     {@link AuthenticationType }
     *     
     */
    @Override
    public AuthenticationType getAuthentication() {
        return authentication;
    }
    @Override
    protected ErrorsType validateClass(){
        if (getHotelARIGetRequests() == null
                || getHotelARIGetRequests().getHotelCode() == null
                || getHotelARIGetRequests().getHotelARIGetRequest() == null
                || getHotelARIGetRequests().getHotelARIGetRequest().isEmpty()){
            return new ErrorsType(errorFound());
        }
        final Iterator<HotelARIGetRQ.HotelARIGetRequests.HotelARIGetRequest> iterator =  getHotelARIGetRequests().getHotelARIGetRequest().iterator();
        while(iterator.hasNext()){
            final HotelARIGetRQ.HotelARIGetRequests.HotelARIGetRequest elem = iterator.next();
            if (elem.getApplicationControl() == null
                || elem.getApplicationControl().getStart() == null
                || elem.getApplicationControl().getEnd() == null
                || elem.getProductReference() == null
                || elem.getProductReference().getInvTypeCode() == null
                || elem.getProductReference().getRatePlanCode()== null
                ){
                return new ErrorsType(errorFound());
            }
        }
        return null;
    }

    /**
     * Sets the value of the authentication property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuthenticationType }
     *     
     */
    public void setAuthentication(AuthenticationType value) {
        this.authentication = value;
    }

    /**
     * Gets the value of the hotelARIGetRequests property.
     * 
     * @return
     *     possible object is
     *     {@link HotelARIGetRQ.HotelARIGetRequests }
     *     
     */
    public HotelARIGetRQ.HotelARIGetRequests getHotelARIGetRequests() {
        return hotelARIGetRequests;
    }
    
    public String getHotelARIGetRequestHotelTicker() {
        return hotelARIGetRequests != null ? hotelARIGetRequests.getHotelCode() : null;
    }

    /**
     * Sets the value of the hotelARIGetRequests property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelARIGetRQ.HotelARIGetRequests }
     *     
     */
    public void setHotelARIGetRequests(HotelARIGetRQ.HotelARIGetRequests value) {
        this.hotelARIGetRequests = value;
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
    @Override
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
    @Override
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="HotelARIGetRequest" maxOccurs="unbounded">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="ProductReference" type="{http://cgbridge.rategain.com/OTA/2012/05}ProductReferenceType"/>
     *                   &lt;element name="ApplicationControl" type="{http://cgbridge.rategain.com/OTA/2012/05}ApplicationControlType"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *       &lt;/sequence>
     *       &lt;attribute name="HotelCode" use="required" type="{http://cgbridge.rategain.com/OTA/2012/05}StringLength1to64" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "hotelARIGetRequest"
    })
    public static class HotelARIGetRequests {

        @XmlElement(name = "HotelARIGetRequest", required = true)
        protected List<HotelARIGetRQ.HotelARIGetRequests.HotelARIGetRequest> hotelARIGetRequest;
        @XmlAttribute(name = "HotelCode", required = true)
        protected String hotelCode;

        /**
         * Gets the value of the hotelARIGetRequest property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hotelARIGetRequest property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHotelARIGetRequest().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link HotelARIGetRQ.HotelARIGetRequests.HotelARIGetRequest }
         * 
         * 
         */
        public List<HotelARIGetRQ.HotelARIGetRequests.HotelARIGetRequest> getHotelARIGetRequest() {
            if (hotelARIGetRequest == null) {
                hotelARIGetRequest = new ArrayList<HotelARIGetRQ.HotelARIGetRequests.HotelARIGetRequest>();
            }
            return this.hotelARIGetRequest;
        }

        /**
         * Gets the value of the hotelCode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getHotelCode() {
            return hotelCode;
        }

        /**
         * Sets the value of the hotelCode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setHotelCode(String value) {
            this.hotelCode = value;
        }


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
         *         &lt;element name="ProductReference" type="{http://cgbridge.rategain.com/OTA/2012/05}ProductReferenceType"/>
         *         &lt;element name="ApplicationControl" type="{http://cgbridge.rategain.com/OTA/2012/05}ApplicationControlType"/>
         *       &lt;/sequence>
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "productReference",
            "applicationControl"
        })
        public static class HotelARIGetRequest {

            @XmlElement(name = "ProductReference", required = true)
            protected ProductReferenceType productReference;
            @XmlElement(name = "ApplicationControl", required = true)
            protected ApplicationControlType applicationControl;

            /**
             * Gets the value of the productReference property.
             * 
             * @return
             *     possible object is
             *     {@link ProductReferenceType }
             *     
             */
            public ProductReferenceType getProductReference() {
                return productReference;
            }

            /**
             * Sets the value of the productReference property.
             * 
             * @param value
             *     allowed object is
             *     {@link ProductReferenceType }
             *     
             */
            public void setProductReference(ProductReferenceType value) {
                this.productReference = value;
            }

            /**
             * Gets the value of the applicationControl property.
             * 
             * @return
             *     possible object is
             *     {@link ApplicationControlType }
             *     
             */
            public ApplicationControlType getApplicationControl() {
                return applicationControl;
            }

            /**
             * Sets the value of the applicationControl property.
             * 
             * @param value
             *     allowed object is
             *     {@link ApplicationControlType }
             *     
             */
            public void setApplicationControl(ApplicationControlType value) {
                this.applicationControl = value;
            }

        }

    }

}
