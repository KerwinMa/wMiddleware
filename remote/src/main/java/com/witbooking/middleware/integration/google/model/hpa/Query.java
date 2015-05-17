
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Checkin" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Nights" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *         &lt;element name="PropertyList">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Property" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
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
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "checkin",
        "nights",
        "propertyList"
})
@XmlRootElement(name = "Query")
public class Query {

    @XmlElement(name = "Checkin", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar checkin;
    @XmlElement(name = "Nights", required = true)
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger nights;
    @XmlElement(name = "PropertyList", required = true)
    protected Query.PropertyList propertyList;

    /**
     * Gets the value of the checkin property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getCheckin() {
        return checkin;
    }

    /**
     * Sets the value of the checkin property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setCheckin(XMLGregorianCalendar value) {
        this.checkin = value;
    }

    /**
     * Gets the value of the nights property.
     *
     * @return possible object is
     * {@link BigInteger }
     */
    public BigInteger getNights() {
        return nights;
    }

    /**
     * Sets the value of the nights property.
     *
     * @param value allowed object is
     *              {@link BigInteger }
     */
    public void setNights(BigInteger value) {
        this.nights = value;
    }

    /**
     * Gets the value of the propertyList property.
     *
     * @return possible object is
     * {@link Query.PropertyList }
     */
    public Query.PropertyList getPropertyList() {
        return propertyList;
    }

    /**
     * Sets the value of the propertyList property.
     *
     * @param value allowed object is
     *              {@link Query.PropertyList }
     */
    public void setPropertyList(Query.PropertyList value) {
        this.propertyList = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * <p/>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p/>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="Property" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "property"
    })
    public static class PropertyList {

        @XmlElement(name = "Property", required = true)
        protected List<String> property;

        /**
         * Gets the value of the property property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the property property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getProperty().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         */
        public List<String> getProperty() {
            if (property == null) {
                property = new ArrayList<String>();
            }
            return this.property;
        }

        public void addElement(String e) {
            if (property == null) {
                property = new ArrayList<String>();
            }
            if (e != null) property.add(e);
        }

        public void addAll(List<String> elemets) {
            if (elemets != null) {
                if (property == null)
                    property = elemets;
                else
                    property.addAll(elemets);
            }
        }

    }

}
