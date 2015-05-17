
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;


/**
 * <p>Java class for priceCurrencyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="priceCurrencyType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;>priceType">
 *       &lt;attribute name="currency">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;pattern value="[A-Z][A-Z][A-Z]"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "priceCurrencyType", propOrder = {
    "value"
})
@XmlSeeAlso({
    BaseRateType.class
})
public class PriceCurrencyType {

    @XmlValue
    protected BigDecimal value;
    @XmlAttribute(name = "currency")
    protected String currency;

    public PriceCurrencyType() {
    }

    public PriceCurrencyType(float value, String currency) {
        this.value = new BigDecimal(value).setScale(2, BigDecimal.ROUND_HALF_UP);
        this.currency = currency;
    }

    public PriceCurrencyType(float value) {
        this.value = new BigDecimal(value).setScale(BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

}
