package com.witbooking.middleware.integration.booking.model.request.OTA_HotelRateAmountNotif;

import com.witbooking.middleware.exceptions.integration.booking.BookingException;
import com.witbooking.middleware.integration.booking.model.Constants;
import com.witbooking.middleware.integration.booking.model.request.OTA_HotelAvailNotif.AvailStatusMessage;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * OTA_HotelRateAmountNotifRQ class represents the object to set the rate amount
 * price per rate plan/room stay/date. This object should be sent to Booking
 * each time the hotel's prices should be changed.
 * <p>
 * Almost all the class attributes XML is fixed and mandatory, except
 * {@link OTA_HotelAvailNotifRQ#target} and
 * {@link OTA_HotelAvailNotifRQ#currentTimestamp}.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
@XmlRootElement(name = "OTA_HotelRateAmountNotifRQ")
@XmlAccessorType(XmlAccessType.FIELD)
public class OTA_HotelRateAmountNotifRQ implements Serializable {

    @XmlAttribute(name = "xmlns:xsi")
    private static final String xmlns_xsi = Constants.XMLNS_XSI;
    @XmlAttribute(name = "xmlns")
    private static final String xmlns = Constants.XMLNS;
    @XmlAttribute(name = "xsi:schemaLocation")
    private static final String xmlns_schemaLocation = Constants.XMLNS_SCHEMA_LOCATION_RATE_AMOUNT;
    @XmlAttribute(name = "TimeStamp")
    /**
     * Should contain the current time and date.
     */
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
    private static final String version = Constants.VERSION_3_000;
    /**
     * Contains all messages to be sent in this request. Each message added
     * contains a unique identifier {@link RateAmountMessage#id} and is set by
     * {@link #addToList(com.witbooking.middleware.integration.booking.model.request.OTA_HotelRateAmountNotif.RateAmountMessage) }
     * .
     */
    @XmlElementWrapper(name = "RateAmountMessages")
    @XmlElement(name = "RateAmountMessage")
    List<RateAmountMessage> messages;

    /**
     * Empty constructor required by XML library.
     */
    public OTA_HotelRateAmountNotifRQ() {
        messages = new ArrayList<RateAmountMessage>();
        currentTimestamp = new Date();
    }

    /**
     * Adds new messages prices
     * {@link RateAmountMessage#RateAmountMessage(String, Date, Date, String)}
     * for the rate plan/room stay/day range and prices given.
     *
     * @param roomTicker Booking room stay ticker to set.
     * @param ratePlan Booking Rate Plan ticker to set.
     * @param start Start day of day range.
     * @param end End day of day range.
     * @param prices Prices list to set.
     * @throws BookingException occurs when the day range given is wrong
     * {@link AvailStatusMessage#checkDates(Date, Date)}
     * @see AvailStatusMessage#checkDates(Date, Date)
     * @see RateAmountMessage#RateAmountMessage(String, Date, Date, String)
     * @see #addToList(com.witbooking.middleware.integration.booking.model.request.OTA_HotelRateAmountNotif.RateAmountMessage)
     */
    public void addRate(String roomTicker, String ratePlan, Date start, Date end, List<BaseByGuestAmt> prices) throws BookingException {
        RateAmountMessage item = new RateAmountMessage(roomTicker, start, end, ratePlan);
        item.addRate(prices);
        addToList(item);
    }

        /**
     * Adds new message price
     * {@link RateAmountMessage#RateAmountMessage(String, Date, Date, String)}
     * for the rate plan/room stay/day range and prices given.
     *
     * @param roomTicker Booking room stay ticker to set.
     * @param ratePlan Booking Rate Plan ticker to set.
     * @param start Start day of day range.
     * @param end End day of day range.
     * @param prices Prices list to set.
     * @throws BookingException occurs when the day range given is wrong
     * {@link AvailStatusMessage#checkDates(Date, Date)}
     * @see AvailStatusMessage#checkDates(Date, Date)
     * @see RateAmountMessage#RateAmountMessage(String, Date, Date, String)
     * @see #addToList(com.witbooking.middleware.integration.booking.model.request.OTA_HotelRateAmountNotif.RateAmountMessage)
     */
    public void addRate(String roomTicker, String ratePlan, Date start, Date end, BaseByGuestAmt prices) throws BookingException {
        RateAmountMessage item = new RateAmountMessage(roomTicker, start, end, ratePlan);
        item.addRate(prices);
        addToList(item);
    }
    
    /**
     * Adds new message to messages list and set the {@link RateAmountMessage#id}.
     * @param item message to add.
     */
    private void addToList(RateAmountMessage item) {
        if (messages == null) {
            messages = new ArrayList<RateAmountMessage>();
        }
        item.setId(messages.size() + 1);
        messages.add(item);
    }
}
