
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
 *       &lt;sequence>
 *         &lt;element name="card_name">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="30"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="card_logo_url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="add_to_card_message" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="1"/>
 *               &lt;maxLength value="60"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="add_to_card_url" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="business_logo_url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{}attlist.club_card"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "cardName",
    "cardLogoUrl",
    "addToCardMessage",
    "addToCardUrl",
    "businessLogoUrl"
})
@XmlRootElement(name = "club_card")
public class ClubCard {

    @XmlElement(name = "card_name", required = true)
    protected String cardName;
    @XmlElement(name = "card_logo_url", required = true)
    protected String cardLogoUrl;
    @XmlElement(name = "add_to_card_message")
    protected String addToCardMessage;
    @XmlElement(name = "add_to_card_url", required = true)
    protected String addToCardUrl;
    @XmlElement(name = "business_logo_url")
    protected String businessLogoUrl;
    @XmlAttribute(name = "id")
    protected String id;
    @XmlAttribute(name = "idref")
    @XmlSchemaType(name = "anySimpleType")
    protected String idref;

    /**
     * Gets the value of the cardName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardName() {
        return cardName;
    }

    /**
     * Sets the value of the cardName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardName(String value) {
        this.cardName = value;
    }

    /**
     * Gets the value of the cardLogoUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardLogoUrl() {
        return cardLogoUrl;
    }

    /**
     * Sets the value of the cardLogoUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardLogoUrl(String value) {
        this.cardLogoUrl = value;
    }

    /**
     * Gets the value of the addToCardMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddToCardMessage() {
        return addToCardMessage;
    }

    /**
     * Sets the value of the addToCardMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddToCardMessage(String value) {
        this.addToCardMessage = value;
    }

    /**
     * Gets the value of the addToCardUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddToCardUrl() {
        return addToCardUrl;
    }

    /**
     * Sets the value of the addToCardUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddToCardUrl(String value) {
        this.addToCardUrl = value;
    }

    /**
     * Gets the value of the businessLogoUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessLogoUrl() {
        return businessLogoUrl;
    }

    /**
     * Sets the value of the businessLogoUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessLogoUrl(String value) {
        this.businessLogoUrl = value;
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
     * Gets the value of the idref property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIdref() {
        return idref;
    }

    /**
     * Sets the value of the idref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIdref(String value) {
        this.idref = value;
    }

}
