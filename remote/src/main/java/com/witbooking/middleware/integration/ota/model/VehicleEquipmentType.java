//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.28 at 02:33:51 PM CEST 
//


package com.witbooking.middleware.integration.ota.model;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


/**
 * <p>Java class for VehicleEquipmentType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VehicleEquipmentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{http://www.opentravel.org/OTA/2003/05}VehicleEquipmentCoreGroup"/>
 *       &lt;attribute name="Restriction" type="{http://www.opentravel.org/OTA/2003/05}EquipmentRestrictionType" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VehicleEquipmentType", propOrder = {
    "description"
})
@XmlSeeAlso({
    VehicleRentalTransactionType.PricedEquips.PricedEquip.Equipment.class
})
public class VehicleEquipmentType {

    @XmlElement(name = "Description")
    protected String description;
    @XmlAttribute(name = "Restriction")
    protected EquipmentRestrictionType restriction;
    @XmlAttribute(name = "EquipType", required = true)
    protected String equipType;
    @XmlAttribute(name = "Quantity")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger quantity;

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the restriction property.
     * 
     * @return
     *     possible object is
     *     {@link EquipmentRestrictionType }
     *     
     */
    public EquipmentRestrictionType getRestriction() {
        return restriction;
    }

    /**
     * Sets the value of the restriction property.
     * 
     * @param value
     *     allowed object is
     *     {@link EquipmentRestrictionType }
     *     
     */
    public void setRestriction(EquipmentRestrictionType value) {
        this.restriction = value;
    }

    /**
     * Gets the value of the equipType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquipType() {
        return equipType;
    }

    /**
     * Sets the value of the equipType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquipType(String value) {
        this.equipType = value;
    }

    /**
     * Gets the value of the quantity property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getQuantity() {
        return quantity;
    }

    /**
     * Sets the value of the quantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setQuantity(BigInteger value) {
        this.quantity = value;
    }

}
