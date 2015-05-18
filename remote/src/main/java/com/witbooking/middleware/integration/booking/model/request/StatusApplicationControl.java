package com.witbooking.middleware.integration.booking.model.request;

import com.witbooking.middleware.integration.booking.model.request.OTA_HotelAvailNotif.AvailStatusMessage;
import com.witbooking.middleware.utils.serializers.JaxbDateWithoutTimeSerializer;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * StatusApplicationControl contains the range of the period to be then updated,
 * Booking Room Stay and optional Booking Rate Plan is if needed.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
@XmlRootElement(name = "StatusApplicationControl")
@XmlAccessorType(XmlAccessType.FIELD)
public class StatusApplicationControl {

    /**
     * start is a required attribute and represents the day that begins the
     * period. Please note,only dates in the future can be updated.
     */
    @XmlAttribute(name = "Start")
    @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
    private Date start;
    /**
     * End (Required): represents the day that ends the period.
     */
    @XmlAttribute(name = "End")
    @XmlJavaTypeAdapter(JaxbDateWithoutTimeSerializer.class)
    private Date end;
    /**
     * InvTypeCode is an required parameter and specify the the BOOKING.COM room
     * ID which you are updating.
     */
    @XmlAttribute(name = "InvTypeCode")
    private String invTypeCode;
    /**
     * RatePlanCode is an optional parameter and specify the BOOKING.COM rate
     * category ID which you are updating. Needs to be left out when specifying
     * {@link AvailStatusMessage#bookingLimit} in {@link AvailStatusMessage}, as
     * the availability is updated on room level.
     *
     * @see AvailStatusMessage#bookingLimit
     */
    @XmlAttribute(name = "RatePlanCode")
    private String ratePlanCode;

    public StatusApplicationControl() {
    }

    public StatusApplicationControl(Date start, Date end, String roomTicker, String ratePlanCode) {
        this.start = start;
        this.end = end;
        this.invTypeCode = roomTicker;
        this.ratePlanCode = ratePlanCode;
    }

    public Date getStart() {
        return start;
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
//                return dateFormat.format(start);
    }

    public Date getEnd() {
        return end;
    }

    public String getInvTypeCode() {
        return invTypeCode;
    }

    public String getRatePlanCode() {
        return ratePlanCode;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setInvTypeCode(String invTypeCode) {
        this.invTypeCode = invTypeCode;
    }

    public void setRatePlanCode(String ratePlanCode) {
        this.ratePlanCode = ratePlanCode;
    }
}
