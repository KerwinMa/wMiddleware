
package com.witbooking.middleware.integration.dingus.model;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "success",
        "errors"
})
@XmlRootElement(name = "HotelAvailRateUpdateRS")
public class HotelAvailRateUpdateRS implements Serializable {

    @XmlElement(name = "Success", namespace = "http://www.opentravel.org/OTA/2003/05")
    protected SuccessType success;
    @XmlElementWrapper(name = "Errors", namespace = "http://www.opentravel.org/OTA/2003/05")
    @XmlElement(name = "Error", namespace = "http://www.opentravel.org/OTA/2003/05")
    protected List<ErrorType> errors;

    public HotelAvailRateUpdateRS() {
    }

    public HotelAvailRateUpdateRS(ErrorType error) {
        this.errors = new ArrayList<>();
        this.errors.add(error);
    }

    /**
     * Gets the value of the success property.
     *
     * @return possible object is {@link SuccessType }
     */
    public SuccessType getSuccess() {
        return success;
    }

    /**
     * Sets the value of the success property.
     *
     * @param value allowed object is {@link SuccessType }
     */
    public void setSuccess(SuccessType value) {
        this.success = value;
    }

    public void setSuccess() {
        this.success = new SuccessType();
    }

    /**
     * Gets the value of the errors property.
     *
     * @return possible object is {@link ErrorType }
     */
    public List<ErrorType> getErrors() {
        return errors;
    }

    /**
     * Sets the value of the errors property.
     *
     * @param errors allowed object is {@link ErrorType }
     */

    public void setErrors(List<ErrorType> errors) {
        this.errors = errors;
    }

    public void addErrorMessage(String errorMessage) {
        if (errors == null) {
            errors = new ArrayList<>();
        }
        errors.add(new ErrorType(errorMessage));
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "SuccessType")
    public static class SuccessType {
        public SuccessType() {
        }
    }

}
