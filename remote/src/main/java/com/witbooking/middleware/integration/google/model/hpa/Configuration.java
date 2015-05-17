
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.*;
import java.math.BigInteger;


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
 *         &lt;element name="Model">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;enumeration value="push"/>
 *               &lt;enumeration value="pull"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="ConfigurationURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="QueryControlURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="ExchangeRatesURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="PointsOfSaleURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="PartnerURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="HintURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="QueryBudget" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="SimultaneousThreads" type="{http://www.w3.org/2001/XMLSchema}positiveInteger" minOccurs="0"/>
 *         &lt;element name="WhiteListedIPs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "model",
    "configurationURL",
    "queryControlURL",
    "exchangeRatesURL",
    "pointsOfSaleURL",
    "partnerURL",
    "hintURL",
    "queryBudget",
    "simultaneousThreads",
    "whiteListedIPs"
})
@XmlRootElement(name = "Configuration")
public class Configuration {

    @XmlElement(name = "Model", required = true)
    protected String model;
    @XmlElement(name = "ConfigurationURL")
    @XmlSchemaType(name = "anyURI")
    protected String configurationURL;
    @XmlElement(name = "QueryControlURL")
    @XmlSchemaType(name = "anyURI")
    protected String queryControlURL;
    @XmlElement(name = "ExchangeRatesURL")
    @XmlSchemaType(name = "anyURI")
    protected String exchangeRatesURL;
    @XmlElement(name = "PointsOfSaleURL")
    @XmlSchemaType(name = "anyURI")
    protected String pointsOfSaleURL;
    @XmlElement(name = "PartnerURL")
    @XmlSchemaType(name = "anyURI")
    protected String partnerURL;
    @XmlElement(name = "HintURL")
    @XmlSchemaType(name = "anyURI")
    protected String hintURL;
    @XmlElement(name = "QueryBudget")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger queryBudget;
    @XmlElement(name = "SimultaneousThreads")
    @XmlSchemaType(name = "positiveInteger")
    protected BigInteger simultaneousThreads;
    @XmlElement(name = "WhiteListedIPs")
    protected String whiteListedIPs;

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the configurationURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfigurationURL() {
        return configurationURL;
    }

    /**
     * Sets the value of the configurationURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfigurationURL(String value) {
        this.configurationURL = value;
    }

    /**
     * Gets the value of the queryControlURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQueryControlURL() {
        return queryControlURL;
    }

    /**
     * Sets the value of the queryControlURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQueryControlURL(String value) {
        this.queryControlURL = value;
    }

    /**
     * Gets the value of the exchangeRatesURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExchangeRatesURL() {
        return exchangeRatesURL;
    }

    /**
     * Sets the value of the exchangeRatesURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExchangeRatesURL(String value) {
        this.exchangeRatesURL = value;
    }

    /**
     * Gets the value of the pointsOfSaleURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPointsOfSaleURL() {
        return pointsOfSaleURL;
    }

    /**
     * Sets the value of the pointsOfSaleURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPointsOfSaleURL(String value) {
        this.pointsOfSaleURL = value;
    }

    /**
     * Gets the value of the partnerURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPartnerURL() {
        return partnerURL;
    }

    /**
     * Sets the value of the partnerURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPartnerURL(String value) {
        this.partnerURL = value;
    }

    /**
     * Gets the value of the hintURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHintURL() {
        return hintURL;
    }

    /**
     * Sets the value of the hintURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHintURL(String value) {
        this.hintURL = value;
    }

    /**
     * Gets the value of the queryBudget property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getQueryBudget() {
        return queryBudget;
    }

    /**
     * Sets the value of the queryBudget property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setQueryBudget(BigInteger value) {
        this.queryBudget = value;
    }

    /**
     * Gets the value of the simultaneousThreads property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSimultaneousThreads() {
        return simultaneousThreads;
    }

    /**
     * Sets the value of the simultaneousThreads property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSimultaneousThreads(BigInteger value) {
        this.simultaneousThreads = value;
    }

    /**
     * Gets the value of the whiteListedIPs property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhiteListedIPs() {
        return whiteListedIPs;
    }

    /**
     * Sets the value of the whiteListedIPs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhiteListedIPs(String value) {
        this.whiteListedIPs = value;
    }

}
