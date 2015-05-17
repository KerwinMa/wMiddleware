
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for photoUrlType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="photoUrlType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Caption" type="{}localizedText" minOccurs="0"/>
 *         &lt;element name="URL" type="{http://www.w3.org/2001/XMLSchema}anyURI"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "photoUrlType", propOrder = {
        "caption",
        "url"
})
public class PhotoUrlType {

    @XmlElement(name = "Caption")
    protected LocalizedText caption;
    @XmlElement(name = "URL", required = true)
    @XmlSchemaType(name = "anyURI")
    protected String url;

    public PhotoUrlType() {
    }

    public PhotoUrlType(String language, String text, String url) {
        if (text != null && !text.isEmpty()) this.caption = new LocalizedText(language, text);
        this.url = url;
    }

    /**
     * Gets the value of the caption property.
     *
     * @return possible object is
     * {@link LocalizedText }
     */
    public LocalizedText getCaption() {
        return caption;
    }

    /**
     * Sets the value of the caption property.
     *
     * @param value allowed object is
     *              {@link LocalizedText }
     */
    public void setCaption(LocalizedText value) {
        this.caption = value;
    }

    /**
     * Gets the value of the url property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getURL() {
        return url;
    }

    /**
     * Sets the value of the url property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setURL(String value) {
        this.url = value;
    }

}
