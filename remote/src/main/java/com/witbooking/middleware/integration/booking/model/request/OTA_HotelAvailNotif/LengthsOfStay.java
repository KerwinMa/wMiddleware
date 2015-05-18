package com.witbooking.middleware.integration.booking.model.request.OTA_HotelAvailNotif;

import com.witbooking.middleware.exceptions.integration.booking.BookingException;
import com.witbooking.middleware.utils.serializers.JaxbBooleanAsIntegerSerializer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * This class represent the Wrapper of LenghtOfStay.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @version 1.0
 */
public class LengthsOfStay extends ArrayList<LengthsOfStay.LengthOfStay> implements Serializable {

    /**
     * Error message to display when an error occurs on day minimum stay is
     * already defined.
     */
    private static final String MIN_DAY_ALREADY = "LengthsOfStay already has an MIN stay date.";
    /**
     * Error message to show when an error occurs on day maximum stay is already
     * defined.
     */
    private static final String MAX_DAY_ALREADY = "LengthsOfStay already has an MAX stay date.";
    /**
     * arrivalDateBased is an optional parameter and if is set to 1, the
     * restriction set has an effect only on the arrival day of a booking,
     * whereas the '0' value may affect a search for availability or reservation
     * on all the dates that the query covers. The ArrivalDateBased attribute is
     * optional and when left out, '0' is assumed.
     */
    @XmlAttribute(name = "ArrivalDateBased")
    @XmlJavaTypeAdapter(JaxbBooleanAsIntegerSerializer.class)
    private Boolean arrivalDateBased;

    /**
     * Empty constructor required for XML library.
     */
    public LengthsOfStay() {
        super();
    }

    /**
     * Returns the message {@link LengthOfStay} in a specific position.
     *
     * @param index the position of the message to get.
     * @return the the requested message {@link LengthOfStay}.
     * @exception IndexOutOfBoundsException if the index is out of range
     * (<code> index < 0 || index >= size()</code>)
     */
    @Override
    @XmlElement(name = "LengthOfSay")
    public LengthOfStay get(int index) {
        return super.get(index);
    }

    /**
     * Adds a new message minimum stay.
     *
     * @param days Minimum number of days of stay.
     * @throws BookingException This exception occurs when you already have
     * defined a maximum of days defined in this entry.
     * @see #checkMin()
     */
    public void minStay(int days) throws BookingException {
        checkMin();
        add(new LengthOfStay(LengthOfStay.SET_STAY_MIN, days));
    }

    /**
     * Adds new message maximum stay.
     *
     * @param days Maximum number of days of stay.
     * @throws BookingException This exception occurs when you already have
     * defined a maximum of days defined in this entry.
     * @see #checkMax()
     */
    public void maxStay(int days) throws BookingException {
        checkMax();
        add(new LengthOfStay(LengthOfStay.SET_STAY_MAX, days));
    }

    /**
     * Add to messages , one of minimum stay and other of maximum stay.
     *
     * @param minDays Minimum number of days of stay.
     * @param maxDays Maximum number of days of stay.
     * @throws BookingException This exception occurs when you already have
     * defined a minimum or maximum of days defined in this entry.
     * @see #minStay(int)
     * @see #maxStay(int)
     */
    public void minMaxStay(int minDays, int maxDays) throws BookingException {
        minStay(minDays);
        maxStay(maxDays);
    }

    /**
     * Checks that there is no defined minimum days already.
     *
     * @throws BookingException when found a minimum days message already
     * defined.
     */
    private void checkMin() throws BookingException {
        Iterator<LengthOfStay> items = super.iterator();
        while (items.hasNext()) {
            if (items.next().isMin()) {
                throw new BookingException(MIN_DAY_ALREADY);
            }
        }
    }

    /**
     * Checks that there is no defined maximum days already.
     *
     * @throws BookingException when found a maximum days message already
     * defined.
     */
    private void checkMax() throws BookingException {
        Iterator<LengthOfStay> items = super.iterator();
        while (items.hasNext()) {
            if (items.next().isMax()) {
                throw new BookingException(MAX_DAY_ALREADY);
            }
        }
    }

    /**
     * LengthOfStay class represents the minimum or maximum stay for the given
     * room for the given date for the given rate category. If a booking takes
     * place on this day a minimum or maximum stay (for the whole booking or
     * arrival based, depending on <code>arrivalDateBased = 0 </code> or <code>arrivalDateBased = 1 </code>)
     * of this amount of days is required. Each day in a stay has a room and
     * rate category ID associated with it. Each set of consecutive days with
     * the same rate category ID in a stay, must comply with the minimum or
     * maximum stay setting.
     *
     * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
     */
    @XmlRootElement(name = "LengthOfStay")
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class LengthOfStay implements Serializable {

        /**
         * days (Time is called by Booking) is an required parameter and
         * contains the number of days of restriction.
         */
        @XmlAttribute(name = "Time")
        private int days;
        /**
         * minMaxMessageType (MinMaxMessageType is called by Booking) is an
         * required parameter and represents the type of restriction that is
         * indicating.
         * <p>
         * Values:
         * <ul>
         * <li>{@link #SET_STAY_MIN} </li>
         * <li> {@link #SET_STAY_MAX}</li>
         * </ul>
         */
        @XmlAttribute(name = "MinMaxMessageType")
        private String minMaxMessageType;
        /**
         * value that represents that message is a minimum stay restriction.
         */
        public static final String SET_STAY_MIN = "SetMinLOS";
        /**
         * value that represents that message is a maximum stay restriction.
         */
        public static final String SET_STAY_MAX = "SetMaxLOS";

        /**
         * Empty constructor required by XML library.
         */
        public LengthOfStay() {
        }

        /**
         * Construct that initialized {@link #minMaxMessageType} and
         * {@link #days} with the values given.
         *
         * @param messages the {@link #minMaxMessageType} value to set.
         * @param days the {@link #days} value to set.
         */
        public LengthOfStay(String messages, int days) {
            this.minMaxMessageType = messages;
            this.days = days;
        }

        /**
         *
         * @return returns {@link #days}
         */
        public int getDays() {
            return days;
        }

        /**
         *
         * @return returns {@link #minMaxMessageType}
         */
        public String getMinMaxMessageType() {
            return minMaxMessageType;
        }

        /**
         * Change the {@link #days} value.
         *
         * @param days the {@link #days} to set.
         */
        public void setDays(int days) {
            this.days = days;
        }

        /**
         * Change the {@link #minMaxMessageType} value.
         *
         * @param minMaxMessageType the {@link #minMaxMessageType} to set.
         */
        public void setMinMaxMessageType(String minMaxMessageType) {
            this.minMaxMessageType = minMaxMessageType;
        }

        /**
         * Check if the message type is {@link #SET_STAY_MIN}.
         *
         * @return <code>true</code> if the message type is
         * {@link #SET_STAY_MIN} , otherwise <code>false</code>.
         */
        public boolean isMin() {
            return SET_STAY_MIN.equals(minMaxMessageType);
        }

        /**
         * Check if the message type is {@link #SET_STAY_MAX}.
         *
         * @return <code>true</code> if the message type is
         * {@link #SET_STAY_MAX} , otherwise <code>false</code>.
         */
        public boolean isMax() {
            return SET_STAY_MAX.equals(minMaxMessageType);
        }
    }
}
