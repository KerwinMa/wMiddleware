
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.CollapsedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for localizedText complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="localizedText">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Text" maxOccurs="unbounded">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;attribute name="language" use="required" type="{http://www.w3.org/2001/XMLSchema}language" />
 *                 &lt;attribute name="text" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "localizedText", propOrder = {
        "text"
})
public class LocalizedText {

    @XmlElement(name = "Text", required = true)
    protected List<Text> text;

    public LocalizedText() {
    }

    public LocalizedText(final String language, final String text) {
        this.text = new ArrayList<Text>();
        if (text != null && !text.isEmpty()) this.text.add(new Text(language, text));
    }


    /**
     * Gets the value of the text property.
     * <p/>
     * <p/>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the text property.
     * <p/>
     * <p/>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getText().add(newItem);
     * </pre>
     * <p/>
     * <p/>
     * <p/>
     * Objects of the following type(s) are allowed in the list
     * {@link LocalizedText.Text }
     */
    public List<Text> getText() {
        if (text == null) {
            text = new ArrayList<Text>();
        }
        return this.text;
    }

    public void addText(String text1) {
        if (text == null) {
            text = new ArrayList<Text>();
        }
        if (text1 != null) text.add(new Text(text1));
    }

    public void addText(String text1, String locale) {
        if (text == null) {
            text = new ArrayList<Text>();
        }
        if (text1 != null) text.add(new Text(text1, locale));
    }


    /**
     * <p>Java class for anonymous complex type.
     * <p/>
     * <p>The following schema fragment specifies the expected content contained within this class.
     * <p/>
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;attribute name="language" use="required" type="{http://www.w3.org/2001/XMLSchema}language" />
     *       &lt;attribute name="text" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "")
    public static class Text {

        @XmlAttribute(name = "language", required = true)
        @XmlJavaTypeAdapter(CollapsedStringAdapter.class)
        @XmlSchemaType(name = "language")
        protected String language;
        @XmlAttribute(name = "text", required = true)
        protected String text;

        public Text() {
        }

        public Text(String language, String text) {
            this.language = language;
            this.text = text;
        }

        public Text(String text) {
            this.text = text;
        }

        /**
         * Gets the value of the language property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getLanguage() {
            return language;
        }

        /**
         * Sets the value of the language property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setLanguage(String value) {
            this.language = value;
        }

        /**
         * Gets the value of the text property.
         *
         * @return possible object is
         * {@link String }
         */
        public String getText() {
            return text;
        }

        /**
         * Sets the value of the text property.
         *
         * @param value allowed object is
         *              {@link String }
         */
        public void setText(String value) {
            this.text = value;
        }

    }

}
