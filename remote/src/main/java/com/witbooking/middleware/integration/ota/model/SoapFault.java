/*
 *  SoapFault.java
 *
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 *
 */

package com.witbooking.middleware.integration.ota.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Fault", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
public class SoapFault {

    @XmlElement(name = "faultstring")
    protected String faultText;
    @XmlElement(name = "faultcode")
    protected String faultCode;

    public SoapFault() {
    }

    public SoapFault(String faultText, String faultCode) {
        this.faultText = faultText;
        this.faultCode = faultCode;
    }

    /**
     * Gets the value of the faultText property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getFaultText() {
        return faultText;
    }

    /**
     * Sets the value of the faultText property.
     *
     * @param faultText allowed object is
     *                  {@link String }
     */
    public void setFaultText(String faultText) {
        this.faultText = faultText;
    }

    /**
     * Gets the value of the recordID property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getFaultCode() {
        return faultCode;
    }

    /**
     * Sets the value of the recordID property.
     *
     * @param faultCode allowed object is
     *                  {@link String }
     */
    public void setFaultCode(String faultCode) {
        this.faultCode = faultCode;
    }

}
