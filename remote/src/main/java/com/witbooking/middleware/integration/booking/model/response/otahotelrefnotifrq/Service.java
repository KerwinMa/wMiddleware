package com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author Jose Francisco Fiorillo
 * @author jffiorillo@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Service {

    /**
     * ServiceRPH: service identification, used to identify the RoomStay.
     */
    @XmlAttribute(name = "ServiceRPH")
    private int serviceRPH;
    /**
     * ServiceInventoryCode: the add-on id, according to the ServiceCode Enum:
     */
    @XmlAttribute(name = "ServiceInventoryCode")
    private int serviceInventoryCode;

    /**
     * ServicePricingType: the pricing type code, according to the ServicePricingType enum
     */
    @XmlAttribute(name = "ServicePricingType")
    private int servicePricingType;


    @XmlElement(name = "ServiceDetails")
    private ServiceDetail serviceDetail;

    public int getTotalAmount() {
        return (serviceDetail != null) ? serviceDetail.getTotalAmount() : 0;
    }


    public int getQuantity() {
        return (serviceDetail != null) ? serviceDetail.getQuantity() : 0;
    }

    public String getName() {
        return ServiceInventoryCode.fromId(serviceInventoryCode).getValue();
    }

    public String getTicker() {
        return ServiceInventoryCode.fromId(serviceInventoryCode)+"";
    }


    public int getServicePricingType() {
        return servicePricingType;
    }

    public Service() {
    }

    public int getServiceRPH() {
        return serviceRPH;
    }

    private static class ServiceDetail implements Serializable {
        /**
         * GuestCount: number of guests.
         */
        @XmlElement(name = "GuestCounts")
        private RoomStay.GuestCounts guestCount;
        /**
         * Timespan: number of nights of the services.
         */
        @XmlElement(name = "TimeSpan")
        private TimeSpan timeSpan;
        /**
         * Fees: price to be paid for the add-ons.
         */
        @XmlElement(name = "Fees")
        private Fees fees;

        public int getTotalAmount() {
            return (fees != null) ? fees.getTotalAmount() : 0;
        }

        public int getQuantity() {
            return (guestCount != null) ? guestCount.getGuestCount() : 1;
        }


        private static class TimeSpan implements Serializable {

            @XmlAttribute(name = "Duration")
            private Integer duration;

            @Override
            public String toString() {
                return "TimeStamp{" + "duration=" + duration + '}';
            }
        }

        private static class Fees implements Serializable {

            @XmlElement(name = "Fee")
            private List<Fee> fees;

            public int getTotalAmount() {
                return (fees != null && !fees.isEmpty()) ? fees.get(0).getTotalAmount() : 0;
            }


            public Fees() {
            }

            @Override
            public String toString() {
                return "Fees{" + "fees=" + fees + '}';
            }

            private static class Fee implements Serializable {

                @XmlAttribute(name = "Amount")
                private Integer amount;


                public int getTotalAmount() {
                    final int ret = (amount != null)
                            ? amount
                            : 0;
                    return ret;
                }

                @Override
                public String toString() {
                    return "Fee{" + "amount=" + amount + '}';
                }
            }
        }

        public ServiceDetail() {
        }

        @Override
        public String toString() {
            return "ServiceDetails{" + "guestCount=" + guestCount + ", timeStamp=" + timeSpan + ", fees=" + fees + '}';
        }
    }

    @Override
    public String toString() {
        return "Service{" + "serviceRPH=" + serviceRPH + ", serviceInventoryCode=" + serviceInventoryCode + ", servicePricingType=" + servicePricingType + ", serviceDetail=" + serviceDetail + '}';
    }

    public enum ServicePricingType {
        CPM_NOT_APPLICABLE(0, "CPM_NOT_APPLICABLE"),
        CPM_PER_STAY(1, "CPM_PER_STAY"),
        CPM_PER_PERSON_PER_STAY(2, "CPM_PER_PERSON_PER_STAY"),
        CPM_PER_NIGHT(3, "CPM_PER_NIGHT"),
        CPM_PER_PERSON_PER_NIGHT(4, "CPM_PER_PERSON_PER_NIGHT"),
        CPM_PERCENTAGE(5, "CPM_PERCENTAGE"),
        CPM_PER_PERSON_PER_NIGHT_RESTRICTED(6, "CPM_PER_PERSON_PER_NIGHT_RESTRICTED"),
        UNKNOWN(7, "UNKNOWN");

        private final String value;
        private final int id;

        private ServicePricingType(int id, String value) {
            this.id = id;
            this.value = value;
        }

        public static ServicePricingType fromValue(String v) {
            for (ServicePricingType c : ServicePricingType.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            return UNKNOWN;
        }

        public static ServicePricingType fromId(int v) {
            for (ServicePricingType c : ServicePricingType.values()) {
                if (c.id == v) {
                    return c;
                }
            }
            return UNKNOWN;
        }

        public String getValue() {
            return value;
        }

        public int getId() {
            return id;
        }
    }

    public enum ServiceInventoryCode {
        BREAKFAST(1, "Breakfast"),
        CONTINENTAL_BREAKFAST(2, "Continental breakfast"),
        AMERICAN_BREAKFAST(3, "American breakfast"),
        BUFFET_BREAKFAST(4, "Buffet breakfast"),
        FULL_ENGLISH_BREAKFAST(5, "Full english breakfast"),
        LUNCH(6, "Lunch"),
        DINNER(7, "Dinner"),
        HALF_BOARD(8, "Half board"),
        FULL_BOARD(9, "Full board"),
        BREAKFAST_FOR_CHILDREN(11, "Breakfast for Children"),
        CONTINENTAL_BREAKFAST_FOR_CHILDREN(12, "Continental breakfast for Children"),
        AMERICAN_BREAKFAST_FOR_CHILDREN(13, "American breakfast for Children"),
        BUFFET_BREAKFAST_FOR_CHILDREN(14, "Buffet breakfast for Children"),
        FULL_ENGLISH_BREAKFAST_FOR_CHILDREN(15, "Full english breakfast for Children"),
        LUNCH_FOR_CHILDREN(16, "Lunch for Children"),
        DINNER_FOR_CHILDREN(17, "Dinner for Children"),
        HALF_BOARD_FOR_CHILDREN(18, "Half board for Children"),
        FULL_BOARD_FOR_CHILDREN(19, "Full board for Children"),
        WIFI(20, "WiFi"),
        INTERNET(21, "Internet"),
        PARKING_SPACE(22, "Parking space"),
        EXTRABED(23, "Extrabed"),
        BABYCOT(24, "Babycot"),
        UNKNOWN(25, "Unknown");

        private final int id;
        private final String value;

        private ServiceInventoryCode(int id, String value) {
            this.id = id;
            this.value = value;
        }

        public static ServiceInventoryCode fromValue(String v) {
            for (ServiceInventoryCode c : ServiceInventoryCode.values()) {
                if (c.value.equals(v)) {
                    return c;
                }
            }
            return UNKNOWN;
        }

        public static ServiceInventoryCode fromId(int v) {
            for (ServiceInventoryCode c : ServiceInventoryCode.values()) {
                if (c.id == v) {
                    return c;
                }
            }
            return UNKNOWN;
        }

        public String getValue() {
            return value;
        }

        public int getId() {
            return id;
        }

    }

}
