package com.witbooking.middleware.integration.booking.model.request.OTA_HotelRateAmountNotif;

import com.witbooking.middleware.exceptions.integration.booking.BookingException;
import com.witbooking.middleware.integration.booking.model.request.OTA_HotelAvailNotif.AvailStatusMessage;
import com.witbooking.middleware.integration.booking.model.request.StatusApplicationControl;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * RateAmountMessage represents the price change message sent to Booking.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
@XmlRootElement(name = "RateAmountMessage")
@XmlAccessorType(XmlAccessType.FIELD)
public class RateAmountMessage implements Serializable {

    /**
     * id (LocatorID called by Booking) is a required parameter and should
     * contain a unique ID.
     */
    @XmlAttribute(name = "LocatorID")
    private int id;
    /**
     * Contains the range of the period to be then updated, Booking Room Stay
     * and optional Booking Rate Plan is if needed.
     */
    @XmlElement(name = "StatusApplicationControl")
    private StatusApplicationControl statusAppControl;
    /**
     * Rates list for {@link #statusAppControl} defined.
     */
    @XmlElement(name = "Rates")
    private Rates rates;

    /**
     * Change the id.
     *
     * @param id id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Initialized {@link #statusAppControl} with the values given.
     *
     * @param roomTicker Booking Room Stay ticker.
     * @param start The start day of the period to update.
     * @param end The last day of the period to update.
     * @param ratePlanTicker Booking Rate Plan ticker to set.
     * @throws BookingException occurs when something is wrong with the day
     * range checked by 
     * {@link AvailStatusMessage#checkDates(Date, Date) }.
     * @see AvailStatusMessage#checkDates(Date, Date)
     */
    public RateAmountMessage(String roomTicker, Date start, Date end, String ratePlanTicker) throws BookingException {
        AvailStatusMessage.checkDates(start, end);
        statusAppControl = new StatusApplicationControl(start, end, roomTicker, ratePlanTicker);
        rates = new Rates();
    }
    /**
     * Empty constructor required by XML library.
     */
    public RateAmountMessage() {
    }

    /**
     * Adds prices list given to {@link #rates} list.
     * @param prices the prices list to add.
     */
    public void addRate(List<BaseByGuestAmt> prices) {
        if (rates == null) {
            rates = new Rates();
        }
        rates.add(prices);
    }

    /**
     * Adds price to {@link #rates} list.
     * @param price
     */
    public void addRate(BaseByGuestAmt price) {
        if (rates == null) {
            rates = new Rates();
        }
        rates.add(price);
    }

    /**
     * Rates class is a container class who have the {@link BaseByGuestAmts} object.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Rates implements Serializable {
        /**
         * {@link BaseByGuestAmts} object.
         */
        @XmlElement(name = "Rate")
        private BaseByGuestAmts rates;
        /**
         * Empty constructor required by XML library.
         */
        public Rates() {
            rates = new BaseByGuestAmts();
        }

        /**
         * Adds all elements to {@link #rates}.
         *
         * @param items{@link BaseByGuestAmt} elements to add.
         * @return {@link BaseByGuestAmts#add(List)}
         * @see BaseByGuestAmts#add(List)
         */
        public boolean add(List<BaseByGuestAmt> items) {
            return rates.add(items);
        }
        /**
         * Adds an {@link BaseByGuestAmt} element to {@link #rates}.
         *
         * @param items {@link BaseByGuestAmt} element to add.
         * @return {@link BaseByGuestAmts#add(com.witbooking.middleware.integration.booking.model.request.OTA_HotelRateAmountNotif.BaseByGuestAmt) }
         * @see BaseByGuestAmts#add(com.witbooking.middleware.integration.booking.model.request.OTA_HotelRateAmountNotif.BaseByGuestAmt)
         */
        public boolean add(BaseByGuestAmt items) {
            return rates.add(items);
        }
    }

    /**
     * BaseByGuestAmts class is a container class who have the {@link BaseByGuestAmt} list.
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    public static class BaseByGuestAmts implements Serializable {

        @XmlElementWrapper(name = "BaseByGuestAmts")
        @XmlElement(name = "BaseByGuestAmt")
        private List<BaseByGuestAmt> rates;

        public BaseByGuestAmts() {
            rates = new ArrayList<BaseByGuestAmt>();
        }
        /**
         * Adds all elements to {@link #rates}.
         *
         * @param items {@link BaseByGuestAmt} elements to add.
         * @return {@link List#addAll(java.util.Collection)}
         * @see List#addAll(java.util.Collection)
         */
        public boolean add(List<BaseByGuestAmt> items) {
            return rates.addAll(items);
        }
        /**
         * Adds an {@link BaseByGuestAmt} element to {@link #rates}.
         *
         * @param items {@link BaseByGuestAmt} element to add.
         * @return {@link List#add(Object) }
         * @see List#add(Object)
         */
        public boolean add(BaseByGuestAmt items) {
            return rates.add(items);
        }
    }
}
