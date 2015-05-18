package com.witbooking.middleware.integration.booking.model.response;

import com.witbooking.middleware.integration.booking.model.Constants;
import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
@XmlRootElement(name = "OTA_HotelRateAmountNotifRS", namespace = Constants.XMLNS)
@XmlAccessorType(XmlAccessType.FIELD)
public class OTA_HotelRateAmountNotifRS extends OTA_StandardResponse implements Serializable  {

}
