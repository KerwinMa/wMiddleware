package com.witbooking.middleware.integration.google.model.hpa;

import com.witbooking.middleware.utils.XMLUtils;

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
 *         &lt;element name="Result" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Property" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="Checkin" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *                   &lt;element name="Nights" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
 *                   &lt;element name="RoomID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Baserate" type="{}baseRateType" minOccurs="0"/>
 *                   &lt;element name="Tax" type="{}priceCurrencyType" minOccurs="0"/>
 *                   &lt;element name="OtherFees" type="{}priceCurrencyType" minOccurs="0"/>
 *                   &lt;element name="ChargeCurrency" type="{}chargeCurrencyType" minOccurs="0"/>
 *                   &lt;element name="Custom1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Custom2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Custom3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Custom4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="Custom5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="AllowablePointsOfSale" type="{}allowablePointsOfSaleType" minOccurs="0"/>
 *                   &lt;element name="RoomBundle" type="{}roomPriceDataType" maxOccurs="unbounded" minOccurs="0"/>
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
        "result"
})
@XmlRootElement(name = "Transaction")
public class Transaction {

    @XmlElement(name = "Result")
    protected List<Result> result;
    @XmlAttribute(name = "timestamp", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timestamp;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "partner")
    protected String partner;

    public Transaction() {
        timestamp = XMLUtils.getNow();
    }

    /**
     * Gets the value of the result property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the result property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResult().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Transaction.Result }
     */
    public List<Result> getResult() {
        if (result == null) {
            result = new ArrayList<Result>();
        }
        return this.result;
    }

    public void addResult(Result result1) {
        if (result == null) {
            result = new ArrayList<Result>();
        }
        result.add(result1);
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
     *         &lt;element name="Property" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="Checkin" type="{http://www.w3.org/2001/XMLSchema}date"/>
     *         &lt;element name="Nights" type="{http://www.w3.org/2001/XMLSchema}positiveInteger"/>
     *         &lt;element name="RoomID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Baserate" type="{}baseRateType" minOccurs="0"/>
     *         &lt;element name="Tax" type="{}priceCurrencyType" minOccurs="0"/>
     *         &lt;element name="OtherFees" type="{}priceCurrencyType" minOccurs="0"/>
     *         &lt;element name="ChargeCurrency" type="{}chargeCurrencyType" minOccurs="0"/>
     *         &lt;element name="Custom1" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Custom2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Custom3" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Custom4" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="Custom5" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="AllowablePointsOfSale" type="{}allowablePointsOfSaleType" minOccurs="0"/>
     *         &lt;element name="RoomBundle" type="{}roomPriceDataType" maxOccurs="unbounded" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "property",
            "checkin",
            "nights",
            "roomID",
            "baserate",
            "tax",
            "otherFees",
            "chargeCurrency",
            "custom1",
            "custom2",
            "custom3",
            "custom4",
            "custom5",
            "allowablePointsOfSale",
            "roomBundle"
    })
    public static class Result {

        @XmlElement(name = "Property", required = true)
        protected String property;
        @XmlElement(name = "Checkin", required = true)
        @XmlSchemaType(name = "date")
        protected XMLGregorianCalendar checkin;
        @XmlElement(name = "Nights", required = true)
        @XmlSchemaType(name = "positiveInteger")
        protected BigInteger nights;
        @XmlElement(name = "RoomID")
        protected String roomID;
        @XmlElement(name = "Baserate")
        protected BaseRateType baserate;
        @XmlElement(name = "Tax")
        protected PriceCurrencyType tax;
        @XmlElement(name = "OtherFees")
        protected PriceCurrencyType otherFees;
        @XmlElement(name = "ChargeCurrency")
        protected ChargeCurrencyType chargeCurrency;
        @XmlElement(name = "Custom1")
        protected String custom1;
        @XmlElement(name = "Custom2")
        protected String custom2;
        @XmlElement(name = "Custom3")
        protected String custom3;
        @XmlElement(name = "Custom4")
        protected String custom4;
        @XmlElement(name = "Custom5")
        protected String custom5;
        @XmlElement(name = "AllowablePointsOfSale")
        protected AllowablePointsOfSaleType allowablePointsOfSale;
        @XmlElement(name = "RoomBundle")
        protected List<RoomPriceDataType> roomBundle;


        public void addRoomBundle(RoomPriceDataType roomPriceDataType) {
            if (roomPriceDataType != null) {
                if (roomBundle == null) {
                    roomBundle = new ArrayList<RoomPriceDataType>();
                }
                roomBundle.add(roomPriceDataType);
            }
        }

        /**
         * Gets the value of the property property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getProperty() {
            return property;
        }

        /**
         * Sets the value of the property property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setProperty(String value) {
            this.property = value;
        }

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
         * Gets the value of the roomID property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getRoomID() {
            return roomID;
        }

        /**
         * Sets the value of the roomID property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setRoomID(String value) {
            this.roomID = value;
        }

        /**
         * Gets the value of the baserate property.
         *
         * @return possible object is
         * {@link BaseRateType }
         */
        public BaseRateType getBaserate() {
            return baserate;
        }

        /**
         * Sets the value of the baserate property.
         *
         * @param value allowed object is
         *              {@link BaseRateType }
         */
        public void setBaserate(BaseRateType value) {
            this.baserate = value;
        }

        public void setUnavailable() {
            this.baserate = new BaseRateType(-1);
        }

        /**
         * Gets the value of the tax property.
         *
         * @return possible object is
         * {@link PriceCurrencyType }
         */
        public PriceCurrencyType getTax() {
            return tax;
        }

        /**
         * Sets the value of the tax property.
         *
         * @param value allowed object is
         *              {@link PriceCurrencyType }
         */
        public void setTax(PriceCurrencyType value) {
            this.tax = value;
        }

        /**
         * Gets the value of the otherFees property.
         *
         * @return possible object is
         * {@link PriceCurrencyType }
         */
        public PriceCurrencyType getOtherFees() {
            return otherFees;
        }

        /**
         * Sets the value of the otherFees property.
         *
         * @param value allowed object is
         *              {@link PriceCurrencyType }
         */
        public void setOtherFees(PriceCurrencyType value) {
            this.otherFees = value;
        }

        /**
         * Gets the value of the chargeCurrency property.
         *
         * @return possible object is
         * {@link ChargeCurrencyType }
         */
        public ChargeCurrencyType getChargeCurrency() {
            return chargeCurrency;
        }

        /**
         * Sets the value of the chargeCurrency property.
         *
         * @param value allowed object is
         *              {@link ChargeCurrencyType }
         */
        public void setChargeCurrency(ChargeCurrencyType value) {
            this.chargeCurrency = value;
        }

        /**
         * Gets the value of the custom1 property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCustom1() {
            return custom1;
        }

        /**
         * Sets the value of the custom1 property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCustom1(String value) {
            this.custom1 = value;
        }

        /**
         * Gets the value of the custom2 property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCustom2() {
            return custom2;
        }

        /**
         * Sets the value of the custom2 property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCustom2(String value) {
            this.custom2 = value;
        }

        /**
         * Gets the value of the custom3 property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCustom3() {
            return custom3;
        }

        /**
         * Sets the value of the custom3 property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCustom3(String value) {
            this.custom3 = value;
        }

        /**
         * Gets the value of the custom4 property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCustom4() {
            return custom4;
        }

        /**
         * Sets the value of the custom4 property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCustom4(String value) {
            this.custom4 = value;
        }

        /**
         * Gets the value of the custom5 property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getCustom5() {
            return custom5;
        }

        /**
         * Sets the value of the custom5 property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setCustom5(String value) {
            this.custom5 = value;
        }

        /**
         * Gets the value of the allowablePointsOfSale property.
         *
         * @return possible object is
         * {@link AllowablePointsOfSaleType }
         */
        public AllowablePointsOfSaleType getAllowablePointsOfSale() {
            return allowablePointsOfSale;
        }

        /**
         * Sets the value of the allowablePointsOfSale property.
         *
         * @param value allowed object is
         *              {@link AllowablePointsOfSaleType }
         */
        public void setAllowablePointsOfSale(AllowablePointsOfSaleType value) {
            this.allowablePointsOfSale = value;
        }

        /**
         * Gets the value of the roomBundle property.
         * <p/>
         * <p/>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the roomBundle property.
         * <p/>
         * <p/>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRoomBundle().add(newItem);
         * </pre>
         * <p/>
         * <p/>
         * <p/>
         * Objects of the following type(s) are allowed in the list
         * {@link RoomPriceDataType }
         */
        public List<RoomPriceDataType> getRoomBundle() {
            if (roomBundle == null) {
                roomBundle = new ArrayList<RoomPriceDataType>();
            }
            return this.roomBundle;
        }

    }

}
