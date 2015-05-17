
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
 *       &lt;attGroup ref="{}attlist.date"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "content"
})
@XmlRootElement(name = "end_date")
public class EndDate {

    @XmlValue
    protected String content;
    @XmlAttribute(name = "year", required = true)
    protected long year;
    @XmlAttribute(name = "month", required = true)
    protected long month;
    @XmlAttribute(name = "day", required = true)
    protected long day;

    /**
     * Gets the value of the content property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContent(String value) {
        this.content = value;
    }

    /**
     * Gets the value of the year property.
     * 
     */
    public long getYear() {
        return year;
    }

    /**
     * Sets the value of the year property.
     * 
     */
    public void setYear(long value) {
        this.year = value;
    }

    /**
     * Gets the value of the month property.
     * 
     */
    public long getMonth() {
        return month;
    }

    /**
     * Sets the value of the month property.
     * 
     */
    public void setMonth(long value) {
        this.month = value;
    }

    /**
     * Gets the value of the day property.
     * 
     */
    public long getDay() {
        return day;
    }

    /**
     * Sets the value of the day property.
     * 
     */
    public void setDay(long value) {
        this.day = value;
    }

}
