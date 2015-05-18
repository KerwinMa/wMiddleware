package com.witbooking.middleware.integration.dingus.model;

import com.google.common.base.Objects;
import com.witbooking.middleware.utils.serializers.JaxbTimestampSerializer;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.util.Date;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "user",
        "password",
        "hotelCode",
        "reservationID",
        "modifiedDateFrom",
        "modifiedDateTo"
})
@XmlRootElement(name = "BookingRetrievalRQ")
public class BookingRetrievalRQ implements Serializable {

    @XmlElement(name = "User", required = true)
    private String user;
    @XmlElement(name = "Password", required = true)
    private String password;
    @XmlElement(name = "HotelCode", required = true)
    private String hotelCode;
    @XmlElement(name = "ReservationID", required = true)
    private String reservationID;
    @XmlElement(name = "ModifiedDateFrom", required = true)
    @XmlJavaTypeAdapter(JaxbTimestampSerializer.class)
    private Date modifiedDateFrom;
    @XmlElement(name = "ModifiedDateTo", required = true)
    @XmlJavaTypeAdapter(JaxbTimestampSerializer.class)
    private Date modifiedDateTo;


    public BookingRetrievalRQ() {
    }

    public BookingRetrievalRQ(String user, String password, String hotelCode) {
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

    /**
     * Gets the value of the reservationID property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getReservationID() {
        return reservationID;
    }

    /**
     * Sets the value of the reservationID property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setReservationID(String value) {
        this.reservationID = value;
    }

    /**
     * Gets the value of the modifiedDateFrom property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public Date getModifiedDateFrom() {
        return modifiedDateFrom;
    }

    /**
     * Sets the value of the modifiedDateFrom property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setModifiedDateFrom(Date value) {
        this.modifiedDateFrom = value;
    }

    /**
     * Gets the value of the modifiedDateTo property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public Date getModifiedDateTo() {
        return modifiedDateTo;
    }

    /**
     * Sets the value of the modifiedDateTo property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setModifiedDateTo(Date value) {
        this.modifiedDateTo = value;
    }


    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("user", user)
                .add("password", password)
                .add("hotelCode", hotelCode)
                .add("reservationID", reservationID)
                .add("modifiedDateFrom", modifiedDateFrom)
                .add("modifiedDateTo", modifiedDateTo)
                .toString();
    }
}
