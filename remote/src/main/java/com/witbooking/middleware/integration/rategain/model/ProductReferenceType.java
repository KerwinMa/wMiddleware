//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.20 at 03:01:00 PM CEST 
//

package com.witbooking.middleware.integration.rategain.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for ProductReferenceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProductReferenceType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="InvTypeCode" use="required" type="{http://cgbridge.rategain.com/OTA/2012/05}StringLength1to64" />
 *       &lt;attribute name="RatePlanCode" use="required" type="{http://cgbridge.rategain.com/OTA/2012/05}StringLength1to64" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProductReferenceType", propOrder = {
    "value"
})
public class ProductReferenceType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "InvTypeCode", required = true)
    protected String invTypeCode;
    @XmlAttribute(name = "RatePlanCode", required = true)
    protected String ratePlanCode;
    
    public ProductReferenceType(){}
    
    public ProductReferenceType(String roomStayCode,String ratePlanCode){
        this.invTypeCode = roomStayCode;
        this.ratePlanCode = ratePlanCode;
    }
    
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
     * Gets the value of the invTypeCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvTypeCode() {
        return invTypeCode;
    }

    /**
     * Sets the value of the invTypeCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvTypeCode(String value) {
        this.invTypeCode = value;
    }

    /**
     * Gets the value of the ratePlanCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanCode() {
        return ratePlanCode;
    }

    /**
     * Sets the value of the ratePlanCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanCode(String value) {
        this.ratePlanCode = value;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductReferenceType other = (ProductReferenceType) obj;
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        if ((this.invTypeCode == null) ? (other.invTypeCode != null) : !this.invTypeCode.equals(other.invTypeCode)) {
            return false;
        }
        if ((this.ratePlanCode == null) ? (other.ratePlanCode != null) : !this.ratePlanCode.equals(other.ratePlanCode)) {
            return false;
        }
        return true;
    }

}
