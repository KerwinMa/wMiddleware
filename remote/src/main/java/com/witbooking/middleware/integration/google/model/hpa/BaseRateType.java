
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for baseRateType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="baseRateType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;>priceCurrencyType">
 *       &lt;attribute name="all_inclusive" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "baseRateType")
public class BaseRateType
    extends PriceCurrencyType
{

    public BaseRateType() {
    }

    public BaseRateType(float value, String currency) {
        super(value, currency);
    }

    public BaseRateType(float value) {
        super(value);
    }

    @XmlAttribute(name = "all_inclusive")
    protected Boolean allInclusive;

    /**
     * Gets the value of the allInclusive property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAllInclusive() {
        return allInclusive;
    }

    /**
     * Sets the value of the allInclusive property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAllInclusive(Boolean value) {
        this.allInclusive = value;
    }

}
