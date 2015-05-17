package com.witbooking.middleware.integration.google.model.feed;

import javax.xml.bind.annotation.*;
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
 *         &lt;element ref="{}language"/>
 *         &lt;element ref="{}datum" minOccurs="0"/>
 *         &lt;element ref="{}shared_content" minOccurs="0"/>
 *         &lt;element ref="{}listing" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "language",
        "datum",
        "sharedContent",
        "listing"
})
@XmlRootElement(name = "listings")
public class Listings {

    @XmlElement(required = true)
    protected LanguageContent language;
    protected String datum;
    @XmlElement(name = "shared_content")
    protected SharedContent sharedContent;
    @XmlElement(required = true)
    protected List<Listing> listing;

    public void addListing(Listing listing) {
        if (listing != null) {
            if (this.listing == null)
                this.listing = new ArrayList<Listing>();
            this.listing.add(listing);
        }
    }

    /**
     * Gets the value of the language property.
     *
     * @return possible object is
     * {@link LanguageContent }
     */
    public LanguageContent getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     *
     * @param value allowed object is
     *              {@link LanguageContent }
     */
    public void setLanguage(LanguageContent value) {
        this.language = value;
    }

    /**
     * Gets the value of the datum property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDatum() {
        return datum;
    }

    /**
     * Sets the value of the datum property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDatum(String value) {
        this.datum = value;
    }

    /**
     * Gets the value of the sharedContent property.
     *
     * @return possible object is
     * {@link SharedContent }
     */
    public SharedContent getSharedContent() {
        return sharedContent;
    }

    /**
     * Sets the value of the sharedContent property.
     *
     * @param value allowed object is
     *              {@link SharedContent }
     */
    public void setSharedContent(SharedContent value) {
        this.sharedContent = value;
    }

    /**
     * Gets the value of the listing property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the listing property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getListing().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Listing }
     */
    public List<Listing> getListing() {
        if (listing == null) {
            listing = new ArrayList<Listing>();
        }
        return this.listing;
    }

}
