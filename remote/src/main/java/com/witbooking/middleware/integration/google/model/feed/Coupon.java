
package com.witbooking.middleware.integration.google.model.feed;

import javax.xml.bind.JAXBElement;
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
 *         &lt;element ref="{}offer" minOccurs="0"/>
 *         &lt;element ref="{}details" minOccurs="0"/>
 *         &lt;element ref="{}link" minOccurs="0"/>
 *         &lt;element ref="{}start_date" minOccurs="0"/>
 *         &lt;element ref="{}end_date" minOccurs="0"/>
 *         &lt;element ref="{}expiry_date" minOccurs="0"/>
 *         &lt;element ref="{}redeem" minOccurs="0"/>
 *         &lt;element ref="{}business_name" minOccurs="0"/>
 *         &lt;element ref="{}expiry_period" minOccurs="0"/>
 *         &lt;element ref="{}merchant_offer_id" minOccurs="0"/>
 *         &lt;element name="image_url" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="searchable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element ref="{}club_card" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}barcode" minOccurs="0"/>
 *         &lt;element ref="{}provider_info" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attGroup ref="{}attlist.coupon"/>
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
@XmlRootElement(name = "coupon")
public class Coupon {

    @XmlElementRefs({
        @XmlElementRef(name = "details", type = Details.class, required = false),
        @XmlElementRef(name = "merchant_offer_id", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "barcode", type = Barcode.class, required = false),
        @XmlElementRef(name = "link", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "searchable", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "business_name", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "offer", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "expiry_date", type = ExpiryDate.class, required = false),
        @XmlElementRef(name = "start_date", type = StartDate.class, required = false),
        @XmlElementRef(name = "club_card", type = ClubCard.class, required = false),
        @XmlElementRef(name = "expiry_period", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "redeem", type = Redeem.class, required = false),
        @XmlElementRef(name = "end_date", type = EndDate.class, required = false),
        @XmlElementRef(name = "image_url", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "provider_info", type = ProviderInfo.class, required = false)
    })
    @XmlMixed
    protected List<Object> content;
    @XmlAttribute(name = "id")
    @XmlSchemaType(name = "anySimpleType")
    protected String id;
    @XmlAttribute(name = "idref")
    @XmlSchemaType(name = "anySimpleType")
    protected String idref;

    /**
     * Gets the value of the content property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the content property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getContent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Details }
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link Barcode }
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link Boolean }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link ExpiryDate }
     * {@link StartDate }
     * {@link ClubCard }
     * {@link JAXBElement }{@code <}{@link Integer }{@code >}
     * {@link String }
     * {@link Redeem }
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link EndDate }
     * {@link ProviderInfo }
     * 
     * 
     */
    public List<Object> getContent() {
        if (content == null) {
            content = new ArrayList<Object>();
        }
        return this.content;
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
