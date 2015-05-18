//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.20 at 03:01:00 PM CEST 
//

package com.witbooking.middleware.integration.rategain.model;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for HotelARIStatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HotelARIStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProductReference" type="{http://cgbridge.rategain.com/OTA/2012/05}ProductReferenceType"/>
 *         &lt;element name="ApplicationControl" type="{http://cgbridge.rategain.com/OTA/2012/05}ApplicationControlType"/>
 *         &lt;element name="Status">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="Code" use="required" type="{http://cgbridge.rategain.com/OTA/2012/05}CodeType" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="ItemIdentifier" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelARIStatusType", propOrder = {
    "productReference",
    "applicationControl",
    "status"
})
public class HotelARIStatusType {

    @XmlElement(name = "ProductReference", required = true)
    protected ProductReferenceType productReference;
    @XmlElement(name = "ApplicationControl", required = true)
    protected ApplicationControlType applicationControl;
    @XmlElement(name = "Status", required = true)
    protected HotelARIStatusType.Status status;
    @XmlAttribute(name = "ItemIdentifier")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected Integer itemIdentifier;
    
    public HotelARIStatusType(){}
    
    public HotelARIStatusType(final int itemIdentifier){
        this.itemIdentifier = itemIdentifier;
    }

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

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link HotelARIStatusType.Status }
     *     
     */
    public HotelARIStatusType.Status getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelARIStatusType.Status }
     *     
     */
    public void setStatus(HotelARIStatusType.Status value) {
        this.status = value;
    }

    /**
     * Gets the value of the itemIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public Integer getItemIdentifier() {
        return itemIdentifier;
    }

    /**
     * Sets the value of the itemIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setItemIdentifier(Integer value) {
        this.itemIdentifier = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="Code" use="required" type="{http://cgbridge.rategain.com/OTA/2012/05}CodeType" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Status {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "Code", required = true)
        protected String code;

        /**
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * Gets the value of the code property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCode() {
            return code;
        }

        /**
         * Sets the value of the code property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCode(String value) {
            this.code = value;
        }

    }

}
