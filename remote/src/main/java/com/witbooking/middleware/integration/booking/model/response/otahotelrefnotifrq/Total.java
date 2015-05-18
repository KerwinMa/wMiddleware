package com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 *
 * @author Jose Francisco Fiorillo
 * @author jffiorillo@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Total implements Serializable {

    /**
     * AmountAfterTax/AmountBeforeTax: price. (Note: if the room has both
     * 'Including VAT' and 'Including taxes' enabled in the BOOKING.COM system,
     * AmountAfterTax is returned, otherwise AmountBeforeTax.)
     */
    @XmlAttribute(name = "AmountAfterTax")
    private Integer amountAfterTax;
    @XmlAttribute(name = "AmountBeforeTax")
    private Integer amountBeforeTax;
    /**
     * DecimalPlaces: the number of decimal places for a particular currency
     * (eg. 8550 with DecimalPlaces="2" represents 85.50).
     */
    @XmlAttribute(name = "DecimalPlaces")
    private Integer decimalPlace;
    /**
     * CurrencyCode: the currency used for pricing is always the same for the
     * hotel and set by BOOKING.COM. (EG. CurrencyCode="EUR")
     */
    @XmlAttribute(name = "CurrencyCode")
    private String currencyCode;

    public Total() {
    }

    public Float getValue() {
        final int ret = (amountAfterTax != null)
                ? amountAfterTax
                : (amountBeforeTax != null)
                ? amountBeforeTax
                : 0;
        if (decimalPlace != null && decimalPlace > 0) {
            final double div = Math.pow(10, decimalPlace);
            return new Float(ret/ div);
        }
        return new Float(ret);
    }
    
    public String getCurrency(){
        return currencyCode == null ? "" : currencyCode;
    }

    @Override
    public String toString() {
        return "Total{" + "amountAfterTax=" + amountAfterTax + ", amountBeforeTax=" + amountBeforeTax + ", decimalPlace=" + decimalPlace + ", currencyCode=" + currencyCode + '}';
    }
}
