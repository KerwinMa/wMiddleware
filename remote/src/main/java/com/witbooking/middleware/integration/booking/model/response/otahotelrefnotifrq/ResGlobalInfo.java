package com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jose Francisco Fiorillo
 * @author jffiorillo@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ResGlobalInfo implements Serializable {

    @XmlElement(name = "Comments")
    private Comments comments;
    @XmlElement(name = "Guarantee")
    private Guarantee guarantee;
    @XmlElement(name = "Total")
    private Total total;
    @XmlElement(name = "HotelReservationIDs")
    private HotelReservationIDs hotelReservationIDs;
    @XmlElement(name = "Profiles")
    private ResGuest.Profiles profiles;

    public String getReservationResID_Value() {
        return hotelReservationIDs == null ? "" : hotelReservationIDs.getReservationResID_Value();
    }

    public String getResID_Hotel() {
        return hotelReservationIDs == null ? "" : hotelReservationIDs.getResID_Hotel();
    }

    public Date getDateCreation() {
        return hotelReservationIDs == null ? null : hotelReservationIDs.getDateCreation();
    }

    public String getGivenName() {
        return profiles == null ? "" : profiles.getGivenName();
    }

    public String getSurname() {
        return profiles == null ? "" : profiles.getSurname();
    }

    public String getEmail() {
        return profiles == null ? "" : profiles.getEmail();
    }

    public String getTelephone() {
        return profiles == null ? "" : profiles.getTelephone();
    }

    public String getCountry() {
        return profiles == null ? "" : profiles.getCountry();
    }

    public String getAddress() {
        return profiles == null ? "" : profiles.getAddress();
    }

    public String getCardHolderName() {
        return (guarantee == null) ? "" : guarantee.getCardHolderName();
    }

    public String getCardNumber() {
        return (guarantee == null) ? "" : guarantee.getCardNumber();
    }

    public short getExpireDate() {
        return (guarantee == null) ? 0 : guarantee.getExpireDate();
    }

    public String getSeriesCode() {
        return (guarantee == null) ? "" : guarantee.getSeriesCode();
    }

    public String getCardCode() {
        return (guarantee == null) ? "" : guarantee.getCardCode();
    }

    public String getComments() {
        return comments == null ? "" : comments.getComments();
    }

    public float getTotalAmount() {
        return total == null ? 0 : total.getValue();
    }

    public String getCurrency() {
        return total == null ? "" : total.getCurrency();
    }

    public Guarantee getGuarantee() {
        return guarantee;
    }

    public void setGuarantee(Guarantee guarantee) {
        this.guarantee = guarantee;
    }

    private static final class HotelReservationIDs implements Serializable {

        @XmlElement(name = "HotelReservationID")
        private List<HotelReservationID> hotelReservationIDs;

        public HotelReservationIDs() {
        }

        @Override
        public String toString() {
            return "HotelReservationIDs{" + "hotelReservationIDs=" + hotelReservationIDs + '}';
        }

        public String getReservationResID_Value() {
            return (hotelReservationIDs == null || hotelReservationIDs.isEmpty()) ? "" : hotelReservationIDs.get(0).getResID_Value();
        }

        public String getResID_Hotel() {
            return (hotelReservationIDs == null || hotelReservationIDs.isEmpty()) ? "" : hotelReservationIDs.get(0).getResID_Hotel();
        }

        public Date getDateCreation() {
            return (hotelReservationIDs == null || hotelReservationIDs.isEmpty()) ? null : hotelReservationIDs.get(0).getDateCreation();
        }
    }

    private static final class Comments implements Serializable {

        @XmlElement(name = "Comment")
        private List<Comment> comments;

        public Comments() {
        }

        @Override
        public String toString() {
            return "Comments{" + "comments=" + comments + '}';
        }

        public String getComments() {
            if (comments == null) {
                return "";
            }
            String ret = comments.isEmpty() ? "" : comments.get(0).text;
            for (int i = 1; i < comments.size(); i++) {
                ret += " " + comments.get(i);
            }
            return ret;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private static final class HotelReservationID {

        @XmlAttribute(name = "ResID_Value")
        private String resID_Value;
        @XmlAttribute(name = "ResID_Date")
        private Date resID_Date;
        @XmlAttribute(name = "ResID_SourceContext")
        private String resID_Hotel
;        

        @Override
        public String toString() {
            return "HotelReservationID{" + "resID_Value=" + resID_Value + ", resID_Date=" + resID_Date + '}';
        }

        public String getResID_Hotel() {
            return resID_Hotel;
        }

        public String getResID_Value() {
            return resID_Value;
        }

        public Date getDateCreation() {
            return resID_Date;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private static final class Guarantee implements Serializable {

        @XmlElement(name = "GuaranteesAccepted")
        private GuaranteesAccepted guaranteeAccepted;

        @Override
        public String toString() {
            return "Guarantee{" + "guaranteeAccepted=" + guaranteeAccepted + '}';
        }

        public String getCardHolderName() {
            return (guaranteeAccepted == null) ? "" : guaranteeAccepted.getCardHolderName();
        }

        public String getCardNumber() {
            return (guaranteeAccepted == null) ? "" : guaranteeAccepted.getCardNumber();
        }

        public short getExpireDate() {
            return (guaranteeAccepted == null) ? 0 : guaranteeAccepted.getExpireDate();
        }

        public String getSeriesCode() {
            return (guaranteeAccepted == null) ? "" : guaranteeAccepted.getSeriesCode();
        }

        public String getCardCode() {
            return (guaranteeAccepted == null) ? "" : guaranteeAccepted.getCardCode();
        }

        private static final class GuaranteesAccepted implements Serializable {

            @XmlElement(name = "GuaranteeAccepted")
            private List<GuaranteeAccepted> guaranteeAccepted;

            public GuaranteesAccepted() {
            }

            @Override
            public String toString() {
                return "GuaranteesAccepted{" + "guaranteeAccepted=" + guaranteeAccepted + '}';
            }

            public String getCardHolderName() {
                return (guaranteeAccepted == null || guaranteeAccepted.isEmpty()) ? "" : guaranteeAccepted.get(0).getCardHolderName();
            }

            public String getCardNumber() {
                return (guaranteeAccepted == null || guaranteeAccepted.isEmpty()) ? "" : guaranteeAccepted.get(0).getCardNumber();
            }

            public short getExpireDate() {
                return (guaranteeAccepted == null || guaranteeAccepted.isEmpty()) ? 0 : guaranteeAccepted.get(0).getExpireDate();
            }

            public String getSeriesCode() {
                return (guaranteeAccepted == null || guaranteeAccepted.isEmpty()) ? "" : guaranteeAccepted.get(0).getSeriesCode();
            }

            public String getCardCode() {
                return (guaranteeAccepted == null || guaranteeAccepted.isEmpty()) ? "" : guaranteeAccepted.get(0).getCardCode();
            }
        }
    }

    public ResGlobalInfo() {
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private static final class Comment implements Serializable {

        @XmlElement(name = "Text")
        private String text;

        public Comment() {
        }

        @Override
        public String toString() {
            return "Comment{" + "text=" + text + '}';
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private static final class GuaranteeAccepted implements Serializable {

        @XmlElement(name = "PaymentCard")
        private PaymentCard paymentCard;

        public GuaranteeAccepted() {
        }

        public String getCardHolderName() {
            return paymentCard == null ? "" : paymentCard.getCardHolderName();
        }

        public String getCardNumber() {
            return paymentCard == null ? "" : paymentCard.getCardNumber();
        }

        public short getExpireDate() {
            return paymentCard == null ? 0 : paymentCard.getExpireDate();
        }

        public String getSeriesCode() {
            return paymentCard == null ? "" : paymentCard.getSeriesCode();
        }

        public String getCardCode() {
            return paymentCard == null ? "" : paymentCard.getCardCode();
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        private static class PaymentCard implements Serializable {

            /**
             * CardCode: OTA 2 character code of the credit card issuer. If
             * "XX", something went wrong when retrieving the issuer code
             * (please try again later).
             */
            /*
             Card     Code Description
             AX       American Express 
             BC       Bank Card 
             BL       Carte Bleu 
             CB       Carte Blanche 
             DN       Diners Club 
             DS       Discover Card 
             EC       Eurocard 
             JC       Japanese Credit Bureau Credit Card 
             MA       Maestro (Switch) 
             MC       Mastercard 
             TP       Universal Air Travel Card 
             VI       Visa
             */
            @XmlAttribute(name = "CardCode")
            private String cardCode;
            /**
             * CardNumber: credit card number as supplied by the customer. If
             * all 0, something went wrong when retrieving the number (please
             * try again later).
             */
            @XmlAttribute(name = "CardNumber")
            private String cardNumber;
            /**
             * SeriesCode: credit card CVC-code as supplied by the customer. If
             * all 0, something went wrong when retrieving the CVC-code (please
             * try again later).
             */
            @XmlAttribute(name = "SeriesCode")
            private String seriesCode;
            /**
             * ExpireDate: credit card expiration date as supplied by the
             * customer. If all 0, something went wrong when retrieving the
             * expiration date (please try again later).
             */
            @XmlAttribute(name = "ExpireDate")
            private short expireDate;
            /**
             * CardHolderName: credit card holder's name as supplied by the
             * customer. If "-", something went wrong when retrieving the
             * holder's name (please try again later).
             */
            @XmlElement(name = "CardHolderName")
            private String cardHolderName;

            public PaymentCard() {
            }

            public String getCardCode() {
                return cardCode == null ? "" : cardCode;
            }

            public String getCardNumber() {
                return cardNumber == null ? "" : cardNumber;
            }

            public String getSeriesCode() {
                return seriesCode == null ? "" : seriesCode;
            }

            public short getExpireDate() {
                //return expireDate == null ? "" : expireDate;
                return expireDate;
            }

            public String getCardHolderName() {
                return cardHolderName == null ? "" : cardHolderName;
            }

            @Override
            public String toString() {
                return "PaymentCard{" + "cardCode=" + cardCode + ", cardNumber=" + cardNumber + ", seriesCode=" + seriesCode + ", expireDate=" + expireDate + ", cardHolderName=" + cardHolderName + '}';
            }
        }

        @Override
        public String toString() {
            return "GuaranteeAccepted{" + "paymentCard=" + paymentCard + '}';
        }
    }

    @Override
    public String toString() {
        return "ResGlobalInfo{" + "comments=" + comments + ", guarantee=" + guarantee + ", total=" + total + ", hotelReservationIDs=" + hotelReservationIDs + ", profile=" + profiles + '}';
    }
}
