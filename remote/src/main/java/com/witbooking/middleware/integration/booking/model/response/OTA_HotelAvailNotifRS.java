package com.witbooking.middleware.integration.booking.model.response;

import com.witbooking.middleware.integration.booking.model.Constants;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
@XmlRootElement(name = "OTA_HotelAvailNotifRS", namespace = Constants.XMLNS)
@XmlAccessorType(XmlAccessType.FIELD)
public class OTA_HotelAvailNotifRS extends OTA_StandardResponse implements Serializable {
}
