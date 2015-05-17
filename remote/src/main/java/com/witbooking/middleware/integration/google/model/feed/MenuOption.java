
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
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="name" type="{}language_and_text" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="description" type="{}language_and_text" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}price" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="allergen_absent" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="allergen_present" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="dietary_restriction" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="calories" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element ref="{}spiciness" minOccurs="0"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "nameOrDescriptionOrPrice"
})
@XmlRootElement(name = "menu_option")
public class MenuOption {

    @XmlElementRefs({
        @XmlElementRef(name = "allergen_present", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "name", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "dietary_restriction", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "allergen_absent", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "description", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "spiciness", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "calories", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "price", type = Price.class, required = false)
    })
    protected List<Object> nameOrDescriptionOrPrice;

    /**
     * Gets the value of the nameOrDescriptionOrPrice property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nameOrDescriptionOrPrice property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNameOrDescriptionOrPrice().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}
     * {@link Price }
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<Object> getNameOrDescriptionOrPrice() {
        if (nameOrDescriptionOrPrice == null) {
            nameOrDescriptionOrPrice = new ArrayList<Object>();
        }
        return this.nameOrDescriptionOrPrice;
    }

}
