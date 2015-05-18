package com.witbooking.middleware.integration.dingus.model;


import com.witbooking.middleware.integration.dingus.ConstantsDingus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Error", namespace = "http://www.opentravel.org/OTA/2003/05")
public class ErrorType implements Serializable {

    @XmlAttribute(name = "Type", required = true)
    protected String type;

    public ErrorType() {
    }

    public ErrorType(String type) {
        this.type = type;
    }

    /**
     * Gets the value of the type property.
     *
     * @return possible object is {@link String }
     */
    public String getType() {
        return type;
    }


    /**
     * Sets the value of the type property.
     *
     * @param value allowed object is {@link String }
     */
    public void setType(String value) {
        this.type = value;
    }


    public static ErrorType getErrorTypeAuthentication() {
        ErrorType error = new ErrorType();
        error.setType(ConstantsDingus.Error.AUTHENTICATION);
        return error;
    }

    public static ErrorType getErrorTypeInvalidRoom() {
        ErrorType error = new ErrorType();
        error.setType(ConstantsDingus.Error.INVALID_ROOM_RATE);
        return error;
    }

    public static ErrorType getErrorTypeInvalidHotelTicker() {
        ErrorType error = new ErrorType();
        error.setType(ConstantsDingus.Error.INVALID_ROOM_RATE);
        return error;
    }

}
