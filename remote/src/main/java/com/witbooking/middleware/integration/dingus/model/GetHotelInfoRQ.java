package com.witbooking.middleware.integration.dingus.model;

import com.google.common.base.Objects;

import javax.xml.bind.annotation.*;
import java.io.Serializable;


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
 *         &lt;element name="User" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Password" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="HotelCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "user",
        "password",
        "hotelCode"
})
@XmlRootElement(name = "GetHotelInfoRQ")
public class GetHotelInfoRQ implements Serializable {

    @XmlElement(name = "User", required = true)
    protected String user;
    @XmlElement(name = "Password", required = true)
    protected String password;
    @XmlElement(name = "HotelCode", required = true)
    protected String hotelCode;


    public GetHotelInfoRQ() {
    }

    public GetHotelInfoRQ(String user, String password, String hotelCode) {
        this.user = user;
        this.password = password;
        this.hotelCode = hotelCode;
    }


    /**
     * Gets the value of the user property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Gets the value of the password property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPassword(String value) {
        this.password = value;
    }

    /**
     * Gets the value of the hotelCode property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getHotelCode() {
        return hotelCode;
    }

    /**
     * Sets the value of the hotelCode property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setHotelCode(String value) {
        this.hotelCode = value;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("user", user)
                .add("password", password)
                .add("hotelCode", hotelCode)
                .toString();
    }
}
