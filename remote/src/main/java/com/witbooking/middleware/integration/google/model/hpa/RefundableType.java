
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for refundableType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="refundableType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="available" use="required" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="refundable_until_days">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger">
 *             &lt;minInclusive value="0"/>
 *             &lt;maxInclusive value="15"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *       &lt;attribute name="refundable_until_time" type="{http://www.w3.org/2001/XMLSchema}time" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "refundableType")
public class RefundableType {

    @XmlAttribute(name = "available", required = true)
    protected boolean available;
    @XmlAttribute(name = "refundable_until_days")
    protected Integer refundableUntilDays;
    @XmlAttribute(name = "refundable_until_time")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar refundableUntilTime;

    public RefundableType() {
    }

    public RefundableType(boolean available) {
        this.available = available;
    }

    /**
     * Gets the value of the available property.
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Sets the value of the available property.
     */
    public void setAvailable(boolean value) {
        this.available = value;
    }

    /**
     * Gets the value of the refundableUntilDays property.
     *
     * @return possible object is
     * {@link Integer }
     */
    public Integer getRefundableUntilDays() {
        return refundableUntilDays;
    }

    /**
     * Sets the value of the refundableUntilDays property.
     *
     * @param value allowed object is
     *              {@link Integer }
     */
    public void setRefundableUntilDays(Integer value) {
        this.refundableUntilDays = value;
    }

    /**
     * Gets the value of the refundableUntilTime property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getRefundableUntilTime() {
        return refundableUntilTime;
    }

    /**
     * Sets the value of the refundableUntilTime property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setRefundableUntilTime(XMLGregorianCalendar value) {
        this.refundableUntilTime = value;
    }

}
