
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Item">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Property" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="MaxAdvancePurchase" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *                   &lt;element name="MaxLengthOfStay" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
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
    "item"
})
@XmlRootElement(name = "QueryControl")
public class QueryControl {

    @XmlElement(name = "Item")
    protected List<Item> item;

    /**
     * Gets the value of the item property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the item property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getItem().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link QueryControl.Item }
     *
     *
     */
    public List<Item> getItem() {
        if (item == null) {
            item = new ArrayList<Item>();
        }
        return this.item;
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
     *         &lt;element name="Property" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="MaxAdvancePurchase" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
     *         &lt;element name="MaxLengthOfStay" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
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
        "property",
        "maxAdvancePurchase",
        "maxLengthOfStay"
    })
    public static class Item {

        @XmlElement(name = "Property", required = true)
        protected String property;
        @XmlElement(name = "MaxAdvancePurchase", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger maxAdvancePurchase;
        @XmlElement(name = "MaxLengthOfStay", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger maxLengthOfStay;

        /**
         * Gets the value of the property property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getProperty() {
            return property;
        }

        /**
         * Sets the value of the property property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setProperty(String value) {
            this.property = value;
        }

        /**
         * Gets the value of the maxAdvancePurchase property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMaxAdvancePurchase() {
            return maxAdvancePurchase;
        }

        /**
         * Sets the value of the maxAdvancePurchase property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMaxAdvancePurchase(BigInteger value) {
            this.maxAdvancePurchase = value;
        }

        /**
         * Gets the value of the maxLengthOfStay property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getMaxLengthOfStay() {
            return maxLengthOfStay;
        }

        /**
         * Sets the value of the maxLengthOfStay property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setMaxLengthOfStay(BigInteger value) {
            this.maxLengthOfStay = value;
        }

    }

}
