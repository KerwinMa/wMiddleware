package com.witbooking.middleware.integration.booking.model.request.OTA_HotelAvailNotif;

import com.witbooking.middleware.integration.booking.model.request.StatusApplicationControl;

import javax.xml.bind.annotation.*;
import java.io.Serializable;

/**
 *
 * RestrictionStatus represents the restriction status. Where a
 * <code>"Close"</code> for Restriction
 * <code>"Arrival"</code> doesn't allow a reservation to be made when guests
 * want to arrive on the selected date. When the restriction is set to
 * <code>"Open"</code> for a certain date, guests are free to make a reservation
 * with arrival on this date whereas a
 * <code>"Close"</code> will restrict guests to book rooms with this arrival
 * date. A
 * <code>"Close"</code> for Restriction
 * <code>"Departure"</code> doesn't allow a reservation to be made when visitors
 * want to depart on the selected date. When the restriction is set to
 * <code>"Open"</code> for a certain date, guests are free to make a reservation
 * with departure on this date whereas a
 * <code>"Close"</code> will restrict guests to book rooms with this departure
 * date. Setting restrictions requires passing a RatePlanCode. As RatePlanCode
 * is optional, a message without RatePlanCode does nothing. NB: no errors, no
 * warnings are provided in the response message.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
@XmlRootElement(name = "RestrictionStatus")
@XmlAccessorType(XmlAccessType.FIELD)
public class RestrictionStatus implements Serializable{

    /**
     * status is a required attribute and if set to
     * <code>"Close"</code> (or
     * <code>"Open"</code>), this room will be closed (or opened) for the given
     * date for the given rate category. All other information (Bookinglimit
     * ({@link AvailStatusMessage#bookingLimit}), prices
     * ({@link StatusApplicationControl#ratePlanCode}), etc) is preserved. This
     * attribute alone, without the {@link #restriction} attribute defined,
     * functions as a restriction and defines whether a room is bookable or not.
     */
    @XmlAttribute(name = "Status")
    private String status;
    /**
     * restriction is an optional parameter and may contain <code>"Departure"</code>
     * or <code>"Arrival"</code>.
     */
    @XmlElement(name = "Restriction")
    private String restriction;
    /**
     * Empty constructor required by XML library.
     */
    public RestrictionStatus() {
    }
    /**
     * 
     * @return restriction status {@link #status}
     */
    public String getStatus() {
        return status;
    }
    /**
     * 
     * @return returns {@link #restriction} value.
     */
    public String getRestriction() {
        return restriction;
    }
    /**
     * Set the {@link #status} value.
     * @param status the value to set.
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * Set the {@link #restriction} value.
     * @param restriction  the value to set.
     */
    public void setRestriction(String restriction) {
        this.restriction = restriction;
    }
}
