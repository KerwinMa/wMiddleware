
package com.witbooking.middleware.integration.google.model.hpa;

import com.witbooking.middleware.utils.XMLUtils;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
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
 *         &lt;element name="PartnerData" type="{}roomPriceDataType" minOccurs="0"/>
 *         &lt;element name="Property" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="PropertyData" type="{}roomPriceDataType" minOccurs="0"/>
 *                   &lt;element name="RoomData" type="{}roomPriceDataType" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="timestamp" use="required" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="partner" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "partnerData",
        "property"
})
@XmlRootElement(name = "HotelInfoFeed")
public class HotelInfoFeed {

    @XmlElement(name = "PartnerData")
    protected RoomPriceDataType partnerData;
    @XmlElement(name = "Property")
    protected List<Property> property;
    @XmlAttribute(name = "timestamp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "partner")
    protected String partner;

    public HotelInfoFeed() {
    }

    public HotelInfoFeed(String id) {
        this.id = id;
        this.timestamp = XMLUtils.getNow();
    }

    /**
     * Gets the value of the partnerData property.
     *
     * @return possible object is
     * {@link RoomPriceDataType }
     */
    public RoomPriceDataType getPartnerData() {
        return partnerData;
    }

    /**
     * Sets the value of the partnerData property.
     *
     * @param value allowed object is
     *              {@link RoomPriceDataType }
     */
    public void setPartnerData(RoomPriceDataType value) {
        this.partnerData = value;
    }

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
     * {@link HotelInfoFeed.Property }
     */
    public List<Property> getProperty() {
        if (property == null) {
            property = new ArrayList<Property>();
        }
        return this.property;
    }

    public void addProperty(HotelInfoFeed.Property property1) {
        if (property == null) {
            property = new ArrayList<Property>();
        }
        if (property1 != null) property.add(property1);
    }

    /**
     * Gets the value of the timestamp property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setTimestamp(XMLGregorianCalendar value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the partner property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPartner() {
        return partner;
    }

    /**
     * Sets the value of the partner property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPartner(String value) {
        this.partner = value;
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
     *         &lt;element name="ID" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="PropertyData" type="{}roomPriceDataType" minOccurs="0"/>
     *         &lt;element name="RoomData" type="{}roomPriceDataType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "id",
            "propertyData",
            "roomData"
    })
    public static class Property {

        @XmlElement(name = "ID", required = true)
        protected String id;
        @XmlElement(name = "PropertyData")
        protected RoomPriceDataType propertyData;
        @XmlElement(name = "RoomData")
        protected List<RoomPriceDataType> roomData;

        public Property() {
        }

        public Property(String id, final RoomPriceDataType roomPriceDataType) {
            this.id = id;
            this.roomData = new ArrayList<RoomPriceDataType>();
            if (roomPriceDataType != null) this.roomData.add(roomPriceDataType);
        }

        /**
         * Gets the value of the id property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getID() {
            return id;
        }

        /**
         * Sets the value of the id property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setID(String value) {
            this.id = value;
        }

        /**
         * Gets the value of the propertyData property.
         *
         * @return possible object is
         * {@link RoomPriceDataType }
         */
        public RoomPriceDataType getPropertyData() {
            return propertyData;
        }

        /**
         * Sets the value of the propertyData property.
         *
         * @param value allowed object is
         *              {@link RoomPriceDataType }
         */
        public void setPropertyData(RoomPriceDataType value) {
            this.propertyData = value;
        }

        /**
         * Gets the value of the roomData property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the roomData property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRoomData().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link RoomPriceDataType }
         */
        public List<RoomPriceDataType> getRoomData() {
            if (roomData == null) {
                roomData = new ArrayList<RoomPriceDataType>();
            }
            return this.roomData;
        }

    }

}
