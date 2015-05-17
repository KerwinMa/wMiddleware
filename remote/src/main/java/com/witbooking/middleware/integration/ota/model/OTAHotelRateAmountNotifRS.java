package com.witbooking.middleware.integration.ota.model;

/**
 * OTAHotelAvailNotifRS.java
 * User: jose
 * Date: 9/27/13
 * Time: 12:24 PM
 */

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "OTA_HotelRateAmountNotifRS")
public class OTAHotelRateAmountNotifRS extends MessageAcknowledgementType {
    public OTAHotelRateAmountNotifRS() {
    }

    public OTAHotelRateAmountNotifRS(ErrorsType errors, String echoToken, BigDecimal version) {
        super(errors, echoToken, version);
    }

    public OTAHotelRateAmountNotifRS(String echoToken, BigDecimal version) {
        super(echoToken, version);
    }
}