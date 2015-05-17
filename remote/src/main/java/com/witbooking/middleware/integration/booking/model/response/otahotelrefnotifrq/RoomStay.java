package com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq;

import com.witbooking.middleware.model.ServiceRequested;
import com.witbooking.middleware.utils.DateUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.*;

/**
 * @author Jose Francisco Fiorillo
 * @author jffiorillo@gmail.com
 */
@XmlAccessorType(XmlAccessType.FIELD)
public final class RoomStay implements Serializable {

    @XmlAttribute(name = "IndexNumber")
    private Integer indexNumber;
    @XmlElement(name = "RoomTypes")
    private RoomTypes roomTypes;
    @XmlElement(name = "RatePlans")
    private RatePlans ratePlans;
    @XmlElement(name = "RoomRates")
    private RoomRates roomRates;
    @XmlElement(name = "GuestCounts")
    private GuestCounts guestCounts;
    @XmlElement(name = "Total")
    private Total total;
    @XmlElement(name = "BasicPropertyInfo")
    private BasicPropertyInfo prop;
    //TODO: traducir.
    /**
     * Representan el nombre del huésped principal de la habitación. No se
     * utiliza porque nosotros no guardamos este campo en la base de datos. V6.
     */
    @XmlElement(name = "ResGuestRPHs")
    private ResGuestRPHs resGuestRPHs;
    /**
     * Represents the guest's smoking preferences. That field is not used
     * because. Nowhere to store it in Database. Possibles values: Non-Smoking /
     * Smoking.
     */
    @XmlElement(name = "SpecialRequests")
    private SpecialRequests specialRequests;
    /**
     * Represents the services ordered to this room.
     */
    @XmlElement(name = "ServiceRPHs")
    private ServicesRPHs serviceRPHs;

    public RoomStay() {
    }

    public String getMealPlans() {
        return (roomTypes != null) ? roomTypes.getMealPlan() : "";
    }

    public String getRoomDescription() {
        return (roomTypes != null) ? roomTypes.getRoomDescription() : "";
    }

    public float getCommission() {
        return ratePlans.getCommission();
    }

    public String getCommissionCurrencyCode() {
        return ratePlans.getCurrencyCode();
    }

    public Date getDateCheckIn() {
        return roomRates.getDateCheckIn();
    }

    public Date getDateCheckOut() {
        return roomRates.getDateCheckOut();
    }

    public String getIdReservationRoom() {
        return Integer.toString(indexNumber);
    }

    public float getTotalAmount() {
        return total.getValue();
    }

    public List<Object[]> getPriceByDay() {
        return roomRates.getPriceByDay();
    }

    public int getGuestCount() {
        return guestCounts == null ? 0 : guestCounts.getGuestCount();
    }

    public String getHotelId() {
        return prop == null ? "" : prop.getHotelId();
    }

    public String getRoomTypeCode() {
        return roomTypes == null ? "" : roomTypes.getRoomTypeCode();
    }

    public String getRatePlanCode() {
        return roomRates == null ? "" : roomRates.getRatePlanCode();
    }

    public List<ServiceRequested> getServices(List<Service> services) {
        List<ServiceRequested> orderedServices = new ArrayList<>();
        if (serviceRPHs != null && serviceRPHs.servicesRPH != null) {
            for (RPH rph : serviceRPHs.servicesRPH) {
                final int serviceRPH = rph.rph;
                for (Service service : services) {
                    if (service.getServiceRPH() == serviceRPH) {
                        ServiceRequested serviceRequested = new ServiceRequested();
                        serviceRequested.setTotalServiceAmount(service.getTotalAmount());
                        serviceRequested.setQuantity(service.getQuantity());
                        serviceRequested.setServiceName(service.getName());
                        serviceRequested.setServiceTypeFromOTA(service.getServicePricingType());
                        serviceRequested.setServiceTicker(service.getTicker());
                        serviceRequested.setServiceId(0);
                        orderedServices.add(serviceRequested);
                        break;
                    }
                }
            }
        }
        return orderedServices;
    }

    public List<com.witbooking.middleware.model.ResGuest> getGuests(List<ResGuest> resGuests) {
        List<com.witbooking.middleware.model.ResGuest> guestList = new ArrayList<>();
        if (resGuestRPHs != null && resGuestRPHs.resGuestRPH != null) {
            for (RPH rph : resGuestRPHs.resGuestRPH) {
                final int guestRPH = rph.rph;
                for (ResGuest resGuest : resGuests) {
                    if (resGuest.getResGuestRPH() == guestRPH) {
                        com.witbooking.middleware.model.ResGuest guest = new com.witbooking.middleware.model.ResGuest();
                        guest.setName(resGuest.getSurname());
                        guest.setId(guestRPH);
                        guestList.add(guest);
                        break;
                    }
                }
            }
        }
        return guestList;
    }

    public Map<String, String> getAdditionalRequests() {
        return specialRequests == null || specialRequests.specialRequest == null ? null : specialRequests.getAdditionalRequests();
    }

    private static final class RoomTypes implements Serializable {

        @XmlElement(name = "RoomType")
        private List<RoomType> roomTypes;

        public RoomTypes() {
        }

        @Override
        public String toString() {
            return "RoomTypes{" + "roomTypes=" + roomTypes + '}';
        }

        public String getMealPlan() {
            if (roomTypes == null || roomTypes.isEmpty()) {
                return "";
            }
            String ret = roomTypes.get(0).getMealPlan();
            for (int i = 1; i < roomTypes.size(); i++) {
                ret += (roomTypes.get(i) == null) ? "" : ", " + roomTypes.get(i).getMealPlan();
            }
            return ret;
        }

        public String getRoomTypeCode() {
            return roomTypes == null || roomTypes.isEmpty() ? "" : roomTypes.get(0).getRoomTypeCode();
        }

        public String getRoomDescription() {
            String ret = "";
            if (roomTypes != null) {
                for (RoomType item : roomTypes) {
                    ret += item.getRoomDescription() + "\n";
                }
            }
            return ret;
        }
    }

    private static final class RatePlans implements Serializable {

        @XmlElement(name = "RatePlan")
        private List<RatePlan> ratePlans;

        public RatePlans() {
        }

        @Override
        public String toString() {
            return "RatePlans{" + "ratePlans=" + ratePlans + '}';
        }

        public float getCommission() {
            float comission = 0;
            if (ratePlans != null && ratePlans.size() > 0) {
                for (RatePlan item : ratePlans) {
                    comission += item.getCommission();
                }
            }
            return comission;
        }

        public String getCurrencyCode() {
            return (ratePlans == null || ratePlans.isEmpty())
                    ? ""
                    : ratePlans.get(0).getCurrencyCode();
        }
    }

    private static final class RoomRates implements Serializable {

        @XmlElement(name = "RoomRate")
        private List<RoomRate> roomRates;

        public RoomRates() {
        }

        @Override
        public String toString() {
            return "RoomRates{" + "roomRates=" + roomRates + '}';
        }

        public Date getDateCheckIn() {
            return getSortDates().get(0);
        }

        /**
         * Returns checkout date. Get the last stay day given for Booking and
         * return the next day.
         *
         * @return The checkout day.
         */
        public Date getDateCheckOut() {
            List<Date> ret = getSortDates();
            Date toRet = ret.get(ret.size() - 1);
            return DateUtil.cloneAndIncrementDays(toRet, 1);
        }

        private List<Date> getSortDates() {
            List<Date> ret = new ArrayList<>();
            for (RoomRate item : roomRates) {
                ret.add(item.getDate());
            }
            Collections.sort(ret);
            return ret;
        }

        public List<Object[]> getPriceByDay() {
            List<Object[]> ret = new ArrayList<>();
            for (RoomRate item : roomRates) {
                ret.add(new Object[]{item.getDate(), item.getPrice()});
            }
            return ret;
        }

        private String getRatePlanCode() {
            return roomRates == null || roomRates.isEmpty() ? "" : roomRates.get(0).getRatePlanCode();

        }
    }

    public static final class GuestCounts implements Serializable {

        @XmlElement(name = "GuestCount")
        private List<GuestCount> guestCount;

        public GuestCounts() {
        }

        @Override
        public String toString() {
            return "GuestCounts{" + "guestCount=" + guestCount + '}';
        }

        public int getGuestCount() {
            return (guestCount == null || guestCount.isEmpty())
                    ? 0
                    : guestCount.get(0).count;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        private static class GuestCount implements Serializable {
            //TODO: traducir.

            /**
             * Indica la cantidad de huespedes en la habitación. Esta
             * representado en ReservationRS.RoomStay.capacity.
             */
            @XmlAttribute(name = "Count")
            private int count;

            public GuestCount() {
            }

            @Override
            public String toString() {
                return "GuestCount{" + "count=" + count + '}';
            }
        }
    }

    private static final class ResGuestRPHs implements Serializable {

        @XmlElement(name = "ResGuestRPH")
        private List<RPH> resGuestRPH;

        public ResGuestRPHs() {
        }

        @Override
        public String toString() {
            return "ResGuestRPHs{" + "resGuestRPH=" + resGuestRPH + '}';
        }
    }

    private static final class SpecialRequests implements Serializable {

        @XmlElement(name = "SpecialRequest")
        private List<SpecialRequest> specialRequest;

        public SpecialRequests() {
        }

        public Map<String, String> getAdditionalRequests() {
            Map<String, String> map = new HashMap<>();
            for (SpecialRequest request : specialRequest) {
                map.put(request.getName().replace(" ", "_"), request.getText());
            }
            return map;
        }

        @Override
        public String toString() {
            return "SpecialRequests{" + "specialRequest=" + specialRequest + '}';
        }
    }

    private static final class ServicesRPHs implements Serializable {

        @XmlElement(name = "ServiceRPH")
        private List<RPH> servicesRPH;

        public ServicesRPHs() {
        }

        @Override
        public String toString() {
            return "ServicesRPHs{" + "servicesRPH=" + servicesRPH + '}';
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private static final class BasicPropertyInfo implements Serializable {

        /**
         * HotelCode: the hotel ID as used by BOOKING.COM.
         */
        @XmlAttribute(name = "HotelCode")
        private String hotelCode;

        public BasicPropertyInfo() {
        }

        @Override
        public String toString() {
            return "BasicPropertyInfo{" + "hotelCode=" + hotelCode + '}';
        }

        public String getHotelId() {
            return hotelCode;
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private static final class RPH implements Serializable {

        /**
         * RPH: index for the room guest in this reservation.
         */
        @XmlAttribute(name = "RPH")
        private int rph;

        public RPH() {
        }

        @Override
        public String toString() {
            return "RPH{" + "rph=" + rph + '}';
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    private static final class SpecialRequest implements Serializable {

        /**
         * Name: name of the special request.
         */
        @XmlAttribute(name = "Name")
        private String name;
        /**
         * Text: Non-Smoking / Smoking.
         */
        @XmlElement(name = "Text")
        private String text;

        public String getName() {
            return name == null ? "" : name;
        }

        public String getText() {
            return text == null ? "" : text;
        }

        public SpecialRequest() {
        }

        @Override
        public String toString() {
            return "SpecialRequest{" + "name=" + name + ", text=" + text + '}';
        }
    }

    @Override
    public String toString() {
        return "RoomStay{" + "indexNumber=" + indexNumber + ", roomTypes=" + roomTypes + ", ratePlans=" + ratePlans + ", roomRates=" + roomRates + ", guestCounts=" + guestCounts + ", total=" + total + ", prop=" + prop + ", resGuestRPHs=" + resGuestRPHs + ", specialRequests=" + specialRequests + ", serviceRPHs=" + serviceRPHs + '}';
    }
}
