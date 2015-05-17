
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
 *       &lt;choice maxOccurs="unbounded">
 *         &lt;element name="name" type="{}language_and_text" maxOccurs="unbounded"/>
 *         &lt;element name="description" type="{}language_and_text" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{}menu_item" maxOccurs="unbounded"/>
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
    "nameOrDescriptionOrMenuItem"
})
@XmlRootElement(name = "menu_section")
public class MenuSection {

    @XmlElementRefs({
        @XmlElementRef(name = "menu_item", type = MenuItem.class, required = false),
        @XmlElementRef(name = "name", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "description", type = JAXBElement.class, required = false)
    })
    protected List<Object> nameOrDescriptionOrMenuItem;

    /**
     * Gets the value of the nameOrDescriptionOrMenuItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nameOrDescriptionOrMenuItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNameOrDescriptionOrMenuItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link MenuItem }
     * {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}
     * {@link JAXBElement }{@code <}{@link LanguageAndText }{@code >}
     * 
     * 
     */
    public List<Object> getNameOrDescriptionOrMenuItem() {
        if (nameOrDescriptionOrMenuItem == null) {
            nameOrDescriptionOrMenuItem = new ArrayList<Object>();
        }
        return this.nameOrDescriptionOrMenuItem;
    }

}
