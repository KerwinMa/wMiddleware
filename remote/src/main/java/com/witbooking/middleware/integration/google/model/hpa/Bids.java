
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
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
 *         &lt;element name="Bid">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="PropertyList" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence maxOccurs="unbounded">
 *                             &lt;element name="Property" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="Sites" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence maxOccurs="unbounded">
 *                             &lt;element name="Site" type="{}sitetype"/>
 *                           &lt;/sequence>
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                   &lt;element name="BidRate" type="{}bidcurrencytype"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="timestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "bid"
})
@XmlRootElement(name = "Bids")
public class Bids {

    @XmlElement(name = "Bid")
    protected List<Bid> bid;
    @XmlAttribute(name = "timestamp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlAttribute(name = "id")
    protected String id;

    /**
     * Gets the value of the bid property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the bid property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBid().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Bids.Bid }
     *
     *
     */
    public List<Bid> getBid() {
        if (bid == null) {
            bid = new ArrayList<Bid>();
        }
        return this.bid;
    }

    public void addBid(Bids.Bid bidAdd) {
        if (bid == null) {
            bid = new ArrayList<Bid>();
        }
        if (bidAdd != null) bid.add(bidAdd);
    }

    /**
     * Gets the value of the timestamp property.
     *
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     *
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setId(String value) {
        this.id = value;
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
     *         &lt;element name="PropertyList" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence maxOccurs="unbounded">
     *                   &lt;element name="Property" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="Sites" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence maxOccurs="unbounded">
     *                   &lt;element name="Site" type="{}sitetype"/>
     *                 &lt;/sequence>
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
     *         &lt;element name="BidRate" type="{}bidcurrencytype"/>
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
        "propertyList",
        "sites",
        "bidRate"
    })
    public static class Bid {

        @XmlElement(name = "PropertyList")
        protected Bids.Bid.PropertyList propertyList;
        @XmlElement(name = "Sites")
        protected Bids.Bid.Sites sites;
        @XmlElement(name = "BidRate", required = true)
        protected Bidcurrencytype bidRate;

        public Bid() {
        }

        public Bid(final String hotelTicker, Bidcurrencytype.Type type, final String currency, final String value) {
            bidRate = new Bidcurrencytype(type, currency, value);
            propertyList = new PropertyList(hotelTicker);
        }

        public Bid(final String hotelTicker, Bidcurrencytype.Type type, final String currency, final String value, final Sitetype sitetype) {
            bidRate = new Bidcurrencytype(type, currency, value);
            propertyList = new PropertyList(hotelTicker);
            sites = new Sites(sitetype);
        }

        /**
         * Gets the value of the propertyList property.
         *
         * @return
         *     possible object is
         *     {@link Bids.Bid.PropertyList }
         *
         */
        public Bids.Bid.PropertyList getPropertyList() {
            return propertyList;
        }

        /**
         * Sets the value of the propertyList property.
         *
         * @param value
         *     allowed object is
         *     {@link Bids.Bid.PropertyList }
         *
         */
        public void setPropertyList(Bids.Bid.PropertyList value) {
            this.propertyList = value;
        }

        /**
         * Gets the value of the sites property.
         *
         * @return
         *     possible object is
         *     {@link Bids.Bid.Sites }
         *
         */
        public Bids.Bid.Sites getSites() {
            return sites;
        }

        /**
         * Sets the value of the sites property.
         *
         * @param value
         *     allowed object is
         *     {@link Bids.Bid.Sites }
         *
         */
        public void setSites(Bids.Bid.Sites value) {
            this.sites = value;
        }

        /**
         * Gets the value of the bidRate property.
         * 
         * @return
         *     possible object is
         *     {@link Bidcurrencytype }
         *     
         */
        public Bidcurrencytype getBidRate() {
            return bidRate;
        }

        /**
         * Sets the value of the bidRate property.
         * 
         * @param value
         *     allowed object is
         *     {@link Bidcurrencytype }
         *     
         */
        public void setBidRate(Bidcurrencytype value) {
            this.bidRate = value;
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
         *       &lt;sequence maxOccurs="unbounded">
         *         &lt;element name="Property" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
            "property"
        })
        public static class PropertyList {

            @XmlElement(name = "Property", required = true)
            protected List<String> property;


            public PropertyList() {
            }

            public PropertyList(final String hotelTicker) {
                this.property = new ArrayList<String>();
                this.property.add(hotelTicker);
            }

            /**
             * Gets the value of the property property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the property property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getProperty().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link String }
             * 
             * 
             */
            public List<String> getProperty() {
                if (property == null) {
                    property = new ArrayList<String>();
                }
                return this.property;
            }

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
         *       &lt;sequence maxOccurs="unbounded">
         *         &lt;element name="Site" type="{}sitetype"/>
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
            "site"
        })
        public static class Sites {

            @XmlElement(name = "Site", required = true)
            protected List<Sitetype> site;

            public Sites() {
            }

            public Sites(Sitetype type) {
                this.site = new ArrayList<Sitetype>();
                this.site.add(type);
            }


            public Sites(List<Sitetype> site) {
                this.site = site;
            }

            /**
             * Gets the value of the site property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the site property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getSite().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Sitetype }
             * 
             * 
             */
            public List<Sitetype> getSite() {
                if (site == null) {
                    site = new ArrayList<Sitetype>();
                }
                return this.site;
            }

        }

    }

}
