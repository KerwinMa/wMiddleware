package com.witbooking.middleware.integration.booking.model.request.OTA_HotelAvailNotif;

import com.witbooking.middleware.exceptions.integration.booking.BookingException;
import com.witbooking.middleware.integration.booking.model.Constants;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OTA_HotelAvailNotifRQ class is used to communicate with Booking XML standards
 * for exchanging reservation. Using this objects you can send messages to
 * Booking notifying:
 * <ul>
 * <li>The change of the amount of rooms available per room/date</li>
 * <li>Restrictions per rate/room/date.</li>
 * </ul>
 * <p/>
 * Almost all the class attributes XML is fixed and mandatory, except
 * {@link OTA_HotelAvailNotifRQ#target} and
 * {@link OTA_HotelAvailNotifRQ#currentTimestamp}.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @version 1.0
 * @see AvailStatusMessage
 */
@XmlRootElement(name = "OTA_HotelAvailNotifRQ")
@XmlAccessorType(XmlAccessType.FIELD)
public class OTA_HotelAvailNotifRQ implements Serializable {

    @XmlAttribute(name = "xmlns:xsi")
    private static final String xmlns_xsi = Constants.XMLNS_XSI;
    @XmlAttribute(name = "xmlns")
    private static final String xmlns = Constants.XMLNS;
    @XmlAttribute(name = "xsi:schemaLocation")
    private static final String xmlns_schemaLocation = Constants.XMLNS_SCHEMA_LOCATION_AVAIL_NOTIF;

    public static enum Lock {
        OPEN("Open"), CLOSE("Close");
        private final String value;

        Lock(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

    /**
     * Should contain the current time and date.
     */
    @XmlAttribute(name = "TimeStamp")
    private Date currentTimestamp;
    /**
     * specifies the request Target. Either
     * <code>"Production"</code> or, may contain
     * <code>"Test"</code>, which will not update anything in the Booking.com
     * database.
     */
    @XmlAttribute(name = "Target")
    private static final String target = Constants.TARGET_PRODUCTION;
    @XmlAttribute(name = "Version")
    private static final String version = Constants.VERSION_1_005;
    /**
     * Contains all messages to be sent in this request. Each message added
     * contains a unique identifier {@link AvailStatusMessage#id} and is set by
     * {@link OTA_HotelAvailNotifRQ#addToList(com.witbooking.middleware.integration.booking.model.request.OTA_HotelAvailNotif.AvailStatusMessage)}
     * .
     */
    @XmlElementWrapper(name = "AvailStatusMessages")
    @XmlElement(name = "AvailStatusMessage")
    private List<AvailStatusMessage> messages;

    /**
     * Constructor without parameters.
     * Initializes the {@link OTA_HotelAvailNotifRQ#messages} with a empty list
     * and set {@link OTA_HotelAvailNotifRQ#currentTimestamp} to the current
     * timestamp.
     */
    public OTA_HotelAvailNotifRQ() {
        messages = new ArrayList<AvailStatusMessage>();
        currentTimestamp = new Date();
    }

    /**
     * Adds new rooms available message to the
     * {@link OTA_HotelAvailNotifRQ#messages} list to modify the room amount of
     * specify room for specific day range.
     *
     * @param roomTicker The ticket of the room to be modified to availability.
     * @param start      The initial day of the range.
     * @param end        The last day of the range.
     * @param amount     Number of days offered the room.
     * @throws BookingException thrown when an error occurs with the date
     *                          indicated.
     *                          {@link AvailStatusMessage#checkDates(Date, Date)}
     * @see {{@link AvailStatusMessage#checkDates(Date, Date)}
     */
    public OTA_HotelAvailNotifRQ addAmountRoomsAvailable(String roomTicker, Date start, Date end, int amount) throws BookingException {
        AvailStatusMessage item = new AvailStatusMessage(roomTicker, start, end, null);
        item.setBookingLimit(amount);
        addToList(item);
        return this;
    }

    /**
     * Adds a new message minimum stay restriction for a room to the
     * {@link OTA_HotelAvailNotifRQ#messages} list of messages in a specified
     * date range.
     *
     * @param roomTicker The ticket of the room to be added restrictions.
     * @param ratePlan   Ticker of the rate plan to modify.
     * @param start      The initial day of the range.
     * @param end        The last day of the range.
     * @param minStay    Number of days minimum stay.
     * @throws BookingException thrown when an error occurs with the date
     *                          indicated.
     *                          {@link AvailStatusMessage#checkDates(Date, Date)}
     * @see {{@link AvailStatusMessage#checkDates(Date, Date)}
     */
    public OTA_HotelAvailNotifRQ addRestrictionOfStayMin(String roomTicker, String ratePlan, Date start, Date end, int minStay) throws BookingException {
        AvailStatusMessage item = new AvailStatusMessage(roomTicker, start, end, ratePlan);
        item.addMinStay(minStay);
        addToList(item);
        return this;
    }

    /**
     * Adds a new message maximum stay restriction for a room and payment
     * conditions (ratePlan) to the {@link OTA_HotelAvailNotifRQ#messages} of
     * messages in a specified date range.
     *
     * @param roomTicker The ticket of the room to be added restrictions.
     * @param ratePlan   Ticker of the rate plan to modify.
     * @param start      The initial day of the range.
     * @param end        The last day of the range.
     * @param maxStay    Number of days maximum stay.
     * @throws BookingException thrown when an error occurs with the date
     *                          indicated.
     *                          {@link AvailStatusMessage#checkDates(Date, Date)}
     * @see {{@link AvailStatusMessage#checkDates(Date, Date)}
     */
    public OTA_HotelAvailNotifRQ addRestrictionOfStayMax(String roomTicker, String ratePlan, Date start, Date end, int maxStay) throws BookingException {
        AvailStatusMessage item = new AvailStatusMessage(roomTicker, start, end, ratePlan);
        item.addMaxStay(maxStay);
        addToList(item);
        return this;
    }

    /**
     * Adds a new message minimum and maximum stay restriction for a room and
     * payment conditions (ratePlan) to the
     * {@link OTA_HotelAvailNotifRQ#messages} of messages in a specified date
     * range.
     *
     * @param roomTicker The ticket of the room to be added restrictions.
     * @param ratePlan   Ticker of the rate plan to modify.
     * @param start      The initial day of the range.
     * @param end        The last day of the range.
     * @param minStay    Number of days minimum stay.
     * @param maxStay    Number of days maximum stay.
     * @throws BookingException thrown when an error occurs with the range given.
     * @see AvailStatusMessage#checkDates(Date, Date)
     */
    public OTA_HotelAvailNotifRQ addRestrictionOfStayBoth(String roomTicker, String ratePlan, Date start, Date end, int minStay, int maxStay) throws BookingException {
        AvailStatusMessage item = new AvailStatusMessage(roomTicker, start, end, ratePlan);
        item.addMinMaxStay(minStay, maxStay);
        addToList(item);
        return this;
    }

    public OTA_HotelAvailNotifRQ addLockRestriction(String roomTicker, String ratePlan, Date start, Date end, Lock lock) throws BookingException {
        AvailStatusMessage item = new AvailStatusMessage(roomTicker, start, end, ratePlan);
        RestrictionStatus restrictionStatus = new RestrictionStatus();
        restrictionStatus.setStatus(lock.getValue());
        item.setRestrictionStatus(restrictionStatus);
        addToList(item);
        return this;
    }

    /**
     * Adds the message given to the {@link OTA_HotelAvailNotifRQ#messages} of
     * messages. this function is implemented to prevent NullPointerException of
     * {@link OTA_HotelAvailNotifRQ#messages} and set the id of each message
     * added.
     *
     * @param item Message to be added
     * @see #messages
     */
    private void addToList(AvailStatusMessage item) {
        if (messages == null) {
            messages = new ArrayList<>();
        }
        item.setId(messages.size() + 1);
        messages.add(item);
    }
}