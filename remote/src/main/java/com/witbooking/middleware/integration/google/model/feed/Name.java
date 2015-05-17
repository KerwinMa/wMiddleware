
package com.witbooking.middleware.integration.google.model.feed;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;>name.content">
 *       &lt;attribute name="language" type="{}language.content" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "value"
})
@XmlRootElement(name = "name")
public class Name {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "language")
    protected LanguageContent language;

    public Name() {
    }


    public Name(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the value property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValue(String value) {
        this.value = value;
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

}
