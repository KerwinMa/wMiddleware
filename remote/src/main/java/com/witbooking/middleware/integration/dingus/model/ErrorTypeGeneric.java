package com.witbooking.middleware.integration.dingus.model;


import com.witbooking.middleware.integration.dingus.ConstantsDingus;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Class created to return an error without a namespace
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Error")
public class ErrorTypeGeneric implements Serializable {

    @XmlAttribute(name = "Type", required = true)
    protected String type;

    public ErrorTypeGeneric() {
    }

    public ErrorTypeGeneric(String type) {
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


    public static ErrorTypeGeneric getErrorTypeAuthentication() {
        ErrorTypeGeneric error = new ErrorTypeGeneric();
        error.setType(ConstantsDingus.Error.AUTHENTICATION);
        return error;
    }

    public static ErrorTypeGeneric getErrorTypeInvalidRoom() {
        ErrorTypeGeneric error = new ErrorTypeGeneric();
        error.setType(ConstantsDingus.Error.INVALID_ROOM_RATE);
        return error;
    }

    public static ErrorTypeGeneric getErrorTypeInvalidHotelTicker() {
        ErrorTypeGeneric error = new ErrorTypeGeneric();
        error.setType(ConstantsDingus.Error.INVALID_ROOM_RATE);
        return error;
    }

}
