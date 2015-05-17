package com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jose Francisco Fiorillo
 * @author jffiorillo@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class RatePlan implements Serializable {

    @XmlElement(name = "Commission")
    private Commission commission;

    public RatePlan() {
    }

    public float getCommission() {
        return (commission == null)
                ? 0
                : commission.getCommission();
    }

    public String getCurrencyCode() {
        return (commission == null)
                ? ""
                : commission.getCurrencyCode();
    }

    private static class Commission implements Serializable {

        @XmlElement(name = "CommissionPayableAmount")
        private List<CommissionPayableAmount> items;

        @Override
        public String toString() {
            return "Commission{" + "items=" + items + '}';
        }

        public Commission() {
        }

        public float getCommission() {
            float comission = 0;
            if (items != null && !items.isEmpty()) {
                for (CommissionPayableAmount item : items) {
                    comission += item.getValue();
                }
            }
            return comission;
        }

        public String getCurrencyCode() {
            return (items == null || items.isEmpty()) ? "" : items.get(0).getCurrencyCode();
        }
    }

    private static class CommissionPayableAmount implements Serializable {

        /**
         * Amount: the total commission due for this room for all nights
         * combined.
         */
        @XmlAttribute(name = "Amount")
        private int amount;
        /**
         * DecimalPlaces: the number of decimal places for a particular currency
         * (eg. 8550 with DecimalPlaces="2" represents 85.50).
         */
        @XmlAttribute(name = "DecimalPlaces")
        private Integer decimalPlace;
        /**
         * CurrencyCode: the currency used for pricing is always the same for
         * the hotel and set by BOOKING.COM. (EG. CurrencyCode="EUR")
         */
        @XmlAttribute(name = "CurrencyCode")
        private String currencyCode;

        public CommissionPayableAmount() {
        }

        @Override
        public String toString() {
            return "CommissionPayableAmount{" + "amount=" + amount + ", decimalPlaces=" + decimalPlace + ", currencyCode=" + currencyCode + '}';
        }

        public Float getValue() {
            if (decimalPlace != null && decimalPlace > 0) {
                final double div = Math.pow(10, decimalPlace);
                return new Float(amount/ div) ;
            }
            return new Float(amount);
        }

        public String getCurrencyCode() {
            return (currencyCode != null)
                    ? currencyCode
                    : "";
        }
    }

    @Override
    public String toString() {
        return "RatePlan{" + "items=" + commission + '}';
    }
}
