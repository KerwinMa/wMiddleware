//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.20 at 03:01:00 PM CEST 
//
package com.witbooking.middleware.integration.rategain.model;

import com.witbooking.middleware.utils.XMLUtils;
import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained
 * within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice>
 *         &lt;sequence>
 *           &lt;element name="Success" type="{http://cgbridge.rategain.com/OTA/2012/05}SuccessType"/>
 *           &lt;element name="Warnings" type="{http://cgbridge.rategain.com/OTA/2012/05}WarningsType" minOccurs="0"/>
 *           &lt;element name="HotelARIDataSet" type="{http://cgbridge.rategain.com/OTA/2012/05}HotelARIDataSetType"/>
 *         &lt;/sequence>
 *         &lt;element name="Errors" type="{http://cgbridge.rategain.com/OTA/2012/05}ErrorsType"/>
 *       &lt;/choice>
 *       &lt;attGroup ref="{http://cgbridge.rategain.com/OTA/2012/05}MessageAttributes"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "success",
    "warnings",
    "hotelARIDataSet",
    "errors"
})
@XmlRootElement(name = "HotelARIGetRS")
public class HotelARIGetRS implements RateGainRSInterface {

    @XmlElement(name = "Success")
    protected SuccessType success;
    @XmlElement(name = "Warnings")
    protected WarningsType warnings;
    @XmlElement(name = "HotelARIDataSet")
    protected HotelARIDataSetType hotelARIDataSet;
    @XmlElement(name = "Errors")
    protected ErrorsType errors;
    @XmlAttribute(name = "EchoToken")
    protected String echoToken;
    @XmlAttribute(name = "TimeStamp")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar timeStamp = XMLUtils.getNow();
    @XmlAttribute(name = "Target")
    @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
    protected String target;
    @XmlAttribute(name = "Version", required = true)
    protected BigDecimal version;
    @XmlAttribute(name = "TransactionIdentifier")
    protected String transactionIdentifier;

    public HotelARIGetRS(){}
    public HotelARIGetRS(ErrorType error) {
        this.errors = new ErrorsType(error);
    }
    
    public HotelARIGetRS(ErrorsType error) {
        this.errors = error;
    }
    
    public HotelARIGetRS(final ErrorType error,final BigDecimal version,final String target) {
        this.version = version;
        this.target = target;
        this.errors = new ErrorsType(error);
    }
    public HotelARIGetRS(final String target,final BigDecimal version,final HotelARIDataSetType hotelARIDataSetType){
        this.target = target;
        this.version = version;
        this.success = new SuccessType();
        this.hotelARIDataSet = hotelARIDataSetType;
    }

    /**
     * Gets the value of the success property.
     *
     * @return possible object is {@link SuccessType }
     *
     */
    public SuccessType getSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     *
     * @param value allowed object is {@link SuccessType }
     *
     */
    public void setSuccess(SuccessType value) {
        this.success = value;
    }

    /**
     * Sets the value of the success property.
     *
     * @param value allowed object is {@link SuccessType }
     *
     */
    @Override
    public void setSuccess() {
        this.success = new SuccessType();
    }

    /**
     * Gets the value of the warnings property.
     *
     * @return possible object is {@link WarningsType }
     *
     */
    public WarningsType getWarnings() {
        return warnings;
    }

    /**
     * Sets the value of the warnings property.
     *
     * @param value allowed object is {@link WarningsType }
     *
     */
    public void setWarnings(WarningsType value) {
        this.warnings = value;
    }

    /**
     * Gets the value of the hotelARIDataSet property.
     *
     * @return possible object is {@link HotelARIDataSetType }
     *
     */
    public HotelARIDataSetType getHotelARIDataSet() {
        return hotelARIDataSet;
    }

    /**
     * Sets the value of the hotelARIDataSet property.
     *
     * @param value allowed object is {@link HotelARIDataSetType }
     *
     */
    public void setHotelARIDataSet(HotelARIDataSetType value) {
        this.hotelARIDataSet = value;
    }

    /**
     * Gets the value of the errors property.
     *
     * @return possible object is {@link ErrorsType }
     *
     */
    public ErrorsType getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     *
     * @param value allowed object is {@link ErrorsType }
     *
     */
    public void setErrors(ErrorsType value) {
        this.errors = value;
    }

    /**
     * Gets the value of the echoToken property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getEchoToken() {
        return echoToken;
    }

    /**
     * Sets the value of the echoToken property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setEchoToken(String value) {
        this.echoToken = value;
    }

    /**
     * Gets the value of the timeStamp property.
     *
     * @return possible object is {@link XMLGregorianCalendar }
     *
     */
    public XMLGregorianCalendar getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the value of the timeStamp property.
     *
     * @param value allowed object is {@link XMLGregorianCalendar }
     *
     */
    public void setTimeStamp(XMLGregorianCalendar value) {
        this.timeStamp = value;
    }

    /**
     * Gets the value of the target property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTarget(String value) {
        this.target = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is {@link BigDecimal }
     *
     */
    public BigDecimal getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is {@link BigDecimal }
     *
     */
    public void setVersion(BigDecimal value) {
        this.version = value;
    }

    /**
     * Gets the value of the transactionIdentifier property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTransactionIdentifier() {
        return transactionIdentifier;
    }

    /**
     * Sets the value of the transactionIdentifier property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTransactionIdentifier(String value) {
        this.transactionIdentifier = value;
    }
}
