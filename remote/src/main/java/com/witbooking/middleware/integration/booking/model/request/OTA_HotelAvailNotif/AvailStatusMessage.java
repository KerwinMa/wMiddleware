package com.witbooking.middleware.integration.booking.model.request.OTA_HotelAvailNotif;

import com.witbooking.middleware.exceptions.integration.booking.BookingException;
import com.witbooking.middleware.integration.booking.model.request.OTA_HotelRateAmountNotif.RateAmountMessage;
import com.witbooking.middleware.integration.booking.model.request.StatusApplicationControl;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * AvailStatusMessage class represents messages sent to Booking in
 * {@link OTA_HotelAvailNotifRQ}.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @see OTA_HotelAvailNotifRQ
 */
@XmlRootElement(name = "AvailStatusMessage")
@XmlType(propOrder = {"statusAppControl", "lengthsOfStay", "restrictionStatus"})
@XmlAccessorType(XmlAccessType.FIELD)
public final class AvailStatusMessage implements Serializable {

    /**
     * The message to show when an entry start day is before today
     * {@link BookingException} occurs.
     *
     * @see BookingException
     */
    private static final String ERR_MSG_DATE_BEFORE_TODAY = "Entered start date is before today.";
    /**
     * The message to show when an start date is before end date
     * {@link BookingException} occurs.
     *
     * @see BookingException
     */
    private static final String ERR_MSG_START_BEFORE_END = "Start date is before end date.";
    /**
     * bookingLimit is an optional parameter and represents the amount of
     * available rooms to sell for a hotel. bookingLimit should be left out when
     * updating restrictions in the same "AvailStatusMessage" block, because a
     * "RatePlanCode" attribute is mandatory in that case and bookingLimit is
     * updated on room level, not rate level. Availability can be updated until
     * 254 rooms to sell. 255 will set the room to freesale (*) , which means
     * that there is no limit of amount of rooms to sell than can be sold until
     * the room/rate/date combination is closed with a restriction or until the
     * rooms to sell are decreased again. Setting the value of BookingLimit to
     * 256 or higher, will result in 254 rooms to sell in the Booking.com
     * system.
     */
    @XmlAttribute(name = "BookingLimit")
    private Integer bookingLimit;
    /**
     * id (called in booking locatorID) is an required parameter and should
     * contain a unique ID. (used as RecordID in {@link OTA_HotelAvailNotifRQ}).
     */
    @XmlAttribute(name = "LocatorID")
    private int id;
    /**
     * lengthsOfStay is an optional parameter and is mandatory when specifying
     * minimum or maximum number of days of stay.
     */
    @XmlElementWrapper(name = "LengthsOfStay")
    @XmlElement(name = "LengthOfStay")
    private LengthsOfStay lengthsOfStay;
    /**
     * Represents the restriction status.
     */
    @XmlElement(name = "RestrictionStatus")
    private RestrictionStatus restrictionStatus;
    /**
     * Contains the range of the period to be then updated, Booking Room Stay
     * and optional Booking Rate Plan is if needed.
     */
    @XmlElement(name = "StatusApplicationControl")
    private StatusApplicationControl statusAppControl;

    /**
     * Constructor empty needed to XML library.
     */
    public AvailStatusMessage() {
    }

    /**
     * Create new AvailStatusMessage with the values given.
     *
     * @param roomTicker     The Booking Room Stay ticker of the room to update.
     * @param start          The start day of the period to update.
     * @param end            The last day of the period to update.
     * @param ratePlanTicker The Booking Rate Plan ticker of the rate plan to
     *                       update.
     * @throws BookingException
     */
    public AvailStatusMessage(String roomTicker, Date start, Date end, String ratePlanTicker) throws BookingException {
        checkDates(start, end);
        statusAppControl = new StatusApplicationControl(start, end, roomTicker, ratePlanTicker);
    }

    /**
     * Adds a new message minimum stay.
     *
     * @param minStay Minimum number of days of stay.
     * @throws BookingException This exception occurs when you already have
     *                          defined a maximum of days defined in this entry.
     * @see LengthsOfStay#minStay(int)
     * @see LengthsOfStay#checkMin(int)
     * @see #lengthsOfStay
     */
    public void addMinStay(int minStay) throws BookingException {
        if (lengthsOfStay == null) {
            lengthsOfStay = new LengthsOfStay();
        }
        lengthsOfStay.minStay(minStay);
    }

    /**
     * Adds a new message maximum stay.
     *
     * @param maxStay Maximum number of days of stay.
     * @throws BookingException This exception occurs when you already have
     *                          defined a maximum of days defined in this entry.
     * @see LengthsOfStay#maxStay(int)
     * @see LengthsOfStay#checkMax()
     */
    public void addMaxStay(int maxStay) throws BookingException {
        if (lengthsOfStay == null) {
            lengthsOfStay = new LengthsOfStay();
        }
        lengthsOfStay.maxStay(maxStay);
    }

    /**
     * Add to messages , one of minimum stay and other of maximum stay.
     *
     * @param minStay Minimum number of days of stay.
     * @param maxStay Maximum number of days of stay.
     * @throws BookingException This exception occurs when you already have
     *                          defined a minimum or maximum of days defined in this entry.
     * @see LengthsOfStay#maxStay(int)
     * @see LengthsOfStay#checkMax()
     * @see LengthsOfStay#minStay(int)
     * @see LengthsOfStay#checkMin()
     * @see #lengthsOfStay
     */
    public void addMinMaxStay(int minStay, int maxStay) throws BookingException {
        if (lengthsOfStay == null) {
            lengthsOfStay = new LengthsOfStay();
        }
        lengthsOfStay.minStay(minStay);
        lengthsOfStay.maxStay(maxStay);
    }

    /**
     * @return Get the amount of days available of the specific room with the
     *         specific rate plan.
     * @see #bookingLimit
     */
    public int getBookingLimit() {
        return bookingLimit;
    }

    /**
     * @return Returns the {@link #id}.
     * @see #id
     */
    public int getId() {
        return id;
    }

    /**
     * @return Returns {@link #lengthsOfStay} object.
     * @see LengthsOfStay
     * @see #lengthsOfStay
     */
    public LengthsOfStay getLengthsOfStay() {
        return lengthsOfStay;
    }

    /**
     * @return Returns {@link #restrictionStatus} object.
     * @see RestrictionStatus
     * @see #restrictionStatus
     */
    public RestrictionStatus getRestrictionStatus() {
        return restrictionStatus;
    }

    /**
     * Change the number of days.
     *
     * @param bookingLimit the number of days to set.
     * @see #bookingLimit
     */
    public void setBookingLimit(int bookingLimit) {
        this.bookingLimit = bookingLimit;
    }

    /**
     * Change the number of id.
     *
     * @param id The id to set.
     * @see #id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Change the {@link #lengthsOfStay} object.
     *
     * @param lengthsOfStay The {@link LengthsOfStay} object to set.
     * @see LengthsOfStay
     * @see #lengthsOfStay
     */
    public void setLengthsOfStay(LengthsOfStay lengthsOfStay) {
        this.lengthsOfStay = lengthsOfStay;
    }

    /**
     * Change the restrictions status.
     *
     * @param restrictionStatus The {@link RestrictionStatus} object to set.
     * @see RestrictionStatus
     * @see #restrictionStatus
     */
    public void setRestrictionStatus(RestrictionStatus restrictionStatus) {
        this.restrictionStatus = restrictionStatus;
    }

    /**
     * @return Returns the {@link #statusApplicationControl}.
     * @see StatusApplicationControl
     * @see #statusAppControl
     */
    public StatusApplicationControl getStatusAppControl() {
        return statusAppControl;
    }

    /**
     * Change the {@link #statusApplicationControl} object.
     *
     * @param statusAppControl The {@link StatusApplicationControl} object to
     *                         set.
     * @see StatusApplicationControl
     * @see #statusAppControl
     */
    public void setStatusAppControl(StatusApplicationControl statusAppControl) {
        this.statusAppControl = statusAppControl;
    }

    /**
     * Change the {@link #statusAppControl} , creating a new object with the
     * values given.
     *
     * @param ticker The Booking Room Stay ticker.
     * @param start  The start day {@link StatusApplicationControl#start} of the period to update.
     * @param end    The last day {@link StatusApplicationControl#end} of the period to update.
     * @see #statusAppControl
     * @see StatusApplicationControl
     */
    public void setAmountRoomsAvailable(String ticker, Date start, Date end) {
        this.statusAppControl = new StatusApplicationControl(start, end, ticker, null);
    }

    /**
     * This function is used to check the logic of the ranges of days. If used
     * in {@link #AvailStatusMessage(String, Date, Date, String)
     * } and {@link RateAmountMessage#RateAmountMessage(String, Date, Date, String)
     * }
     *
     * @param start The start day  {@link StatusApplicationControl#start} of the range to check.
     * @param end   The last day {@link StatusApplicationControl#end} of the range to check.
     * @throws BookingException occurs when:
     *                          <ul>
     *                          <li>The start day given is before today</li>
     *                          <li> The end day given is before start day given.</li>
     *                          </ul>
     */
    public static void checkDates(Date start, Date end) throws BookingException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
        try {
            final Date today = formatter.parse(formatter.format(new Date()));
            //Check Start Date is greater than today.
            if (formatter.parse(formatter.format(start)).before(today)) {
                throw new BookingException(ERR_MSG_DATE_BEFORE_TODAY);
            }
            if (end.before(start)) {
                throw new BookingException(ERR_MSG_START_BEFORE_END);
            }
        } catch (ParseException ex) {
            Logger.getLogger(AvailStatusMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
