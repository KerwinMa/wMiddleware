
package com.witbooking.middleware.integration.google.model.feed;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attGroup ref="{}attlist.locationinfo"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "locationinfo")
public class Locationinfo {

    @XmlAttribute(name = "geocode")
    protected String geocode;

    /**
     * Gets the value of the geocode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeocode() {
        return geocode;
    }

    /**
     * Sets the value of the geocode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeocode(String value) {
        this.geocode = value;
    }

}
