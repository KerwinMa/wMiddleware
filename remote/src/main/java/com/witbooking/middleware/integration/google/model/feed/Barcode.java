
package com.witbooking.middleware.integration.google.model.feed;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="2" minOccurs="0">
 *         &lt;element ref="{}UPC_A"/>
 *         &lt;element ref="{}EAN_13"/>
 *         &lt;element ref="{}UCC_EAN_128"/>
 *         &lt;element ref="{}CODE_128"/>
 *         &lt;element ref="{}CODE_39"/>
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
    "upcaOrEAN13OrUCCEAN128"
})
@XmlRootElement(name = "barcode")
public class Barcode {

    @XmlElementRefs({
        @XmlElementRef(name = "UCC_EAN_128", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "CODE_128", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "CODE_39", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "UPC_A", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "EAN_13", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<String>> upcaOrEAN13OrUCCEAN128;

    /**
     * Gets the value of the upcaOrEAN13OrUCCEAN128 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the upcaOrEAN13OrUCCEAN128 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUPCAOrEAN13OrUCCEAN128().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * 
     * 
     */
    public List<JAXBElement<String>> getUPCAOrEAN13OrUCCEAN128() {
        if (upcaOrEAN13OrUCCEAN128 == null) {
            upcaOrEAN13OrUCCEAN128 = new ArrayList<JAXBElement<String>>();
        }
        return this.upcaOrEAN13OrUCCEAN128;
    }

}
