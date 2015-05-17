
package com.witbooking.middleware.integration.google.model.feed;

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
 *         &lt;element ref="{}text"/>
 *         &lt;element ref="{}review"/>
 *         &lt;element ref="{}image"/>
 *         &lt;element ref="{}attributes"/>
 *         &lt;element ref="{}coupon"/>
 *         &lt;element ref="{}menu"/>
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
    "textOrReviewOrImage"
})
@XmlRootElement(name = "content")
public class Content {

    @XmlElements({
        @XmlElement(name = "text", type = Text.class),
        @XmlElement(name = "review", type = Review.class),
        @XmlElement(name = "image", type = Image.class),
        @XmlElement(name = "attributes", type = Attributes.class),
        @XmlElement(name = "coupon", type = Coupon.class),
        @XmlElement(name = "menu", type = Menu.class)
    })
    protected List<Object> textOrReviewOrImage;

    /**
     * Gets the value of the textOrReviewOrImage property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the textOrReviewOrImage property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTextOrReviewOrImage().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Text }
     * {@link Review }
     * {@link Image }
     * {@link Attributes }
     * {@link Coupon }
     * {@link Menu }
     * 
     * 
     */
    public List<Object> getTextOrReviewOrImage() {
        if (textOrReviewOrImage == null) {
            textOrReviewOrImage = new ArrayList<Object>();
        }
        return this.textOrReviewOrImage;
    }

    public void addElement(Object object) {
        if (textOrReviewOrImage == null) {
            textOrReviewOrImage = new ArrayList<Object>();
        }
        if (object != null) textOrReviewOrImage.add(object);
    }

}
