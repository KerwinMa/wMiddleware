package com.witbooking.middleware.integration.booking.model.request.OTA_HotelRateAmountNotif;

import com.witbooking.middleware.exceptions.integration.booking.BookingException;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import java.io.Serializable;

/**
 * BaseByGuestAmt class represents a message to set the price for specific
 * Booking Room Stay ticker and Booking Rate Plan ticker. Only have the amount
 * before tax , amount after tax and number of guests optional for specific
 * types.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BaseByGuestAmt implements Serializable {

    /**
     * Error message to show when a error of price before tax is higher than the
     * price after tax.
     */
    private static final String BEFORE_BIGGER_THAN_AFTER = "Price before tax is higher than the price after tax.";
    /**
     * AmountAfterTax/AmountBeforeTax are a required parameters and set the
     * price for the given room for the given date for the given rate category.
     * The currency used for pricing is always the same for the hotel and set by
     * Booking.com. The currencies used per country can be found in the
     * documentation under Overview, static information. Prices cannot be
     * removed after a value has been set. Note: if the room has both 'Including
     * VAT' and 'Including taxes' enabled in the Booking.com system,
     * AmountAfterTax must be supplied, otherwise AmountBeforeTax.
     */
    @XmlAttribute(name = "AmountAfterTax")
    private Integer amountAfterTax;
    /**
     * See {@link #amountAfterTax}.
     */
    @XmlAttribute(name = "AmountBeforeTax")
    private Integer amountBeforeTax;
    /**
     * decimalPlaces is an optional parameter and specific the number of decimal
     * places for a particular currency. (Eg. 8550 with DecimalPlaces="2"
     * represents 85.50).
     */
    @XmlAttribute(name = "DecimalPlaces")
    private Integer decimalPlace;
    /**
     * numberOfGuests is an optional parameter.if set to
     * <code>1</code>, the single use price is set. Please note, Booking.com is
     * only able to set prices per room night or for 1 person in a room, per
     * night (so called single-use).
     */
    @XmlAttribute(name = "NumberOfGuests")
    private Integer numberOfGuests;

    /**
     * Empty constructor required by XML library.
     */
    public BaseByGuestAmt() {
    }

    /**
     * Checks
     * <code> if (amountBeforeTax != null && amountAfterTax != null && amountBeforeTax > amountAfterTax)</code>.
     *
     * @throws BookingException when
     * <code>(amountBeforeTax != null && amountAfterTax != null && amountBeforeTax > amountAfterTax)</code>
     * occurs.
     */
    public void check() throws BookingException {
        if (amountBeforeTax != null && amountAfterTax != null && amountBeforeTax > amountAfterTax) {
            throw new BookingException(BEFORE_BIGGER_THAN_AFTER);
        }
    }

    /**
     * Try to set the {@link #amountAfterTax} value. if {@link BookingException}
     * occurs, set {@link #amountAfterTax} to its previous value.
     *
     * @param amountAfterTax the value to set.
     * @throws BookingException trow by {@link #check() }
     * @see #check()
     */
    public void setAmountAfterTax(Integer amountAfterTax) throws BookingException {
        final Integer valueBeforeChange = this.amountAfterTax;
        try {
            this.amountAfterTax = amountAfterTax;
            check();
        } catch (BookingException ex) {
            this.amountAfterTax = valueBeforeChange;
            throw ex;
        }
    }

    /**
     * Try to set the {@link #amountBeforeTax} value. if
     * {@link BookingException} occurs, set {@link #amountBeforeTax} to its
     * previous value.
     *
     * @param amountBeforeTax
     * @throws BookingException trow by {@link #check() }
     * @see #check()
     */
    public void setAmountBeforeTax(Integer amountBeforeTax) throws BookingException {
        final Integer valueBeforeChange = this.amountBeforeTax;
        try {
            this.amountBeforeTax = amountBeforeTax;
            check();
        } catch (BookingException ex) {
            this.amountBeforeTax = valueBeforeChange;
            throw ex;
        }
    }

    /**
     * Set the number of guests.
     *
     * @param numberOfGuests the value to set.
     */
    public void setNumberOfGuests(Integer numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }
}
