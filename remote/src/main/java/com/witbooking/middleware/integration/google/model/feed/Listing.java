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
 *         &lt;element ref="{}id" minOccurs="0"/>
 *         &lt;element ref="{}relation" minOccurs="0"/>
 *         &lt;element ref="{}name" maxOccurs="unbounded"/>
 *         &lt;element ref="{}address" maxOccurs="unbounded"/>
 *         &lt;element ref="{}country"/>
 *         &lt;element ref="{}latitude" minOccurs="0"/>
 *         &lt;element ref="{}longitude" minOccurs="0"/>
 *         &lt;element ref="{}locationinfo" minOccurs="0"/>
 *         &lt;element ref="{}phone" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}category" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}date" minOccurs="0"/>
 *         &lt;element ref="{}content" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "id",
        "relation",
        "name",
        "address",
        "country",
        "latitude",
        "longitude",
        "locationinfo",
        "phone",
        "category",
        "date",
        "content"
})
@XmlRootElement(name = "listing")
public class Listing {

    protected String id;
    protected Relation relation;
    @XmlElement(required = true)
    protected List<Name> name;
    @XmlElement(required = true)
    protected List<Address> address;
    @XmlElement(required = true)
    protected String country;
    protected Float latitude;
    protected Float longitude;
    protected Locationinfo locationinfo;
    protected List<Phone> phone;
    protected List<Category> category;
    protected Date date;
    protected Content content;


    public void setAddress(Address address1) {
        if (this.address == null)
            this.address = new ArrayList<Address>();
        if (address1 != null)
            this.address.add(address1);
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
     * Gets the value of the relation property.
     *
     * @return possible object is
     * {@link Relation }
     */
    public Relation getRelation() {
        return relation;
    }

    /**
     * Sets the value of the relation property.
     *
     * @param value allowed object is
     *              {@link Relation }
     */
    public void setRelation(Relation value) {
        this.relation = value;
    }

    /**
     * Gets the value of the name property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the name property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getName().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Name }
     */
    public List<Name> getName() {
        if (name == null) {
            name = new ArrayList<Name>();
        }
        return this.name;
    }

    /**
     * Gets the value of the address property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the address property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAddress().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Address }
     */
    public List<Address> getAddress() {
        if (address == null) {
            address = new ArrayList<Address>();
        }
        return this.address;
    }

    /**
     * Gets the value of the country property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCountry(String value) {
        this.country = value;
    }

    /**
     * Gets the value of the latitude property.
     *
     * @return possible object is
     * {@link Float }
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     *
     * @param value allowed object is
     *              {@link Float }
     */
    public void setLatitude(Float value) {
        this.latitude = value;
    }

    /**
     * Gets the value of the longitude property.
     *
     * @return possible object is
     * {@link Float }
     */
    public Float getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     *
     * @param value allowed object is
     *              {@link Float }
     */
    public void setLongitude(Float value) {
        this.longitude = value;
    }

    /**
     * Gets the value of the locationinfo property.
     *
     * @return possible object is
     * {@link Locationinfo }
     */
    public Locationinfo getLocationinfo() {
        return locationinfo;
    }

    /**
     * Sets the value of the locationinfo property.
     *
     * @param value allowed object is
     *              {@link Locationinfo }
     */
    public void setLocationinfo(Locationinfo value) {
        this.locationinfo = value;
    }

    /**
     * Gets the value of the phone property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the phone property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPhone().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Phone }
     */
    public List<Phone> getPhone() {
        if (phone == null) {
            phone = new ArrayList<Phone>();
        }
        return this.phone;
    }

    public void addPhone(Phone phone) {
        if (this.phone == null) this.phone = new ArrayList<Phone>();
        if (phone != null) this.phone.add(phone);
    }

    /**
     * Gets the value of the category property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the category property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCategory().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link Category }
     */
    public List<Category> getCategory() {
        if (category == null) {
            category = new ArrayList<Category>();
        }
        return this.category;
    }

    public void addCategory(Category category) {
        if (this.category == null) {
            this.category = new ArrayList<Category>();
        }
        this.category.add(category);
    }

    /**
     * Gets the value of the date property.
     *
     * @return possible object is
     * {@link Date }
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets the value of the date property.
     *
     * @param value allowed object is
     *              {@link Date }
     */
    public void setDate(Date value) {
        this.date = value;
    }

    /**
     * Gets the value of the content property.
     *
     * @return possible object is
     * {@link Content }
     */
    public Content getContent() {
        return content;
    }

    /**
     * Sets the value of the content property.
     *
     * @param value allowed object is
     *              {@link Content }
     */
    public void setContent(Content value) {
        this.content = value;
    }


    public void addName(String nameOne) {
        if (this.name == null) {
            this.name = new ArrayList<Name>();
        }
        if (nameOne != null) {
            this.name.add(new Name(nameOne));
        }
    }
}
