
package com.witbooking.middleware.integration.google.model.feed;

import javax.xml.bind.annotation.*;
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
 *       &lt;sequence>
 *         &lt;element ref="{}link" minOccurs="0"/>
 *         &lt;element ref="{}title" minOccurs="0"/>
 *         &lt;element ref="{}author" minOccurs="0"/>
 *         &lt;element ref="{}email" minOccurs="0"/>
 *         &lt;element ref="{}website" minOccurs="0"/>
 *         &lt;element ref="{}attr" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}hiddenattr" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}date" minOccurs="0"/>
 *         &lt;element ref="{}hotel_data" minOccurs="0"/>
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
    "link",
    "title",
    "author",
    "email",
    "website",
    "attr",
    "hiddenattr",
    "date",
    "hotelData"
})
@XmlRootElement(name = "attributes")
public class Attributes {

    protected String link;
    protected String title;
    protected Author author;
    protected String email;
    protected String website;
    protected List<Attr> attr;
    protected List<Hiddenattr> hiddenattr;
    protected Date date;
    @XmlElement(name = "hotel_data")
    protected HotelData hotelData;

    /**
     * Gets the value of the link property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLink() {
        return link;
    }

    /**
     * Sets the value of the link property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLink(String value) {
        this.link = value;
    }

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the author property.
     * 
     * @return
     *     possible object is
     *     {@link Author }
     *     
     */
    public Author getAuthor() {
        return author;
    }

    /**
     * Sets the value of the author property.
     * 
     * @param value
     *     allowed object is
     *     {@link Author }
     *     
     */
    public void setAuthor(Author value) {
        this.author = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the website property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Sets the value of the website property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebsite(String value) {
        this.website = value;
    }

    /**
     * Gets the value of the attr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the attr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAttr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Attr }
     * 
     * 
     */
    public List<Attr> getAttr() {
        if (attr == null) {
            attr = new ArrayList<Attr>();
        }
        return this.attr;
    }

    /**
     * Gets the value of the hiddenattr property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the hiddenattr property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getHiddenattr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Hiddenattr }
     * 
     * 
     */
    public List<Hiddenattr> getHiddenattr() {
        if (hiddenattr == null) {
            hiddenattr = new ArrayList<Hiddenattr>();
        }
        return this.hiddenattr;
    }

    /**
     * Gets the value of the date property.
     * 
     * @return
     *     possible object is
     *     {@link Date }
     *     
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     * 
     * @param value
     *     allowed object is
     *     {@link Date }
     *     
     */
    public void setDate(Date value) {
        this.date = value;
    }

    /**
     * Gets the value of the hotelData property.
     * 
     * @return
     *     possible object is
     *     {@link HotelData }
     *     
     */
    public HotelData getHotelData() {
        return hotelData;
    }

    /**
     * Sets the value of the hotelData property.
     * 
     * @param value
     *     allowed object is
     *     {@link HotelData }
     *     
     */
    public void setHotelData(HotelData value) {
        this.hotelData = value;
    }

}
