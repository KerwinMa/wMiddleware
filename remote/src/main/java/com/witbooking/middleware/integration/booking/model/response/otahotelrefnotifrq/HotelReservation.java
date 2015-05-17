package com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class HotelReservation implements Serializable {

    @XmlElement(name = "RoomStays")
    private RoomStays roomStays;
    @XmlElement(name = "Services")
    private Services services;
    @XmlElement(name = "ResGuests")
    private ResGuests resGuest;
    @XmlElement(name = "ResGlobalInfo")
    private ResGlobalInfo resGlobalInfo;

    public HotelReservation() {
    }

    public String getReservationId() {
        return resGlobalInfo == null ? "" : resGlobalInfo.getReservationResID_Value();
    }

    public Date getDateCreation() {
        return resGlobalInfo == null ? null : resGlobalInfo.getDateCreation();
    }

    public ResGlobalInfo getResGlobalInfo() {
        return resGlobalInfo;
    }

    public List<RoomStay> getRoomStays() {
        return roomStays == null || roomStays.roomStays == null ? new ArrayList<RoomStay>() : roomStays.roomStays;
    }

    public String getComments() {
        return resGlobalInfo == null ? "" : resGlobalInfo.getComments();
    }

    public float getTotalAmount() {
        return resGlobalInfo == null ? 0 : resGlobalInfo.getTotalAmount();
    }

    public String getCurrency() {
        return resGlobalInfo == null ? "" : resGlobalInfo.getCurrency();
    }

    public List<Service> getServices() {
        return services == null || services.service == null
                ? new ArrayList<Service>()
                : services.service;
    }

    public List<ResGuest> getResGuest() {
        return resGuest == null || resGuest.resGuest == null
                ? new ArrayList<ResGuest>()
                : resGuest.resGuest;
    }

    public boolean isCancellation() {
        return roomStays == null || roomStays.roomStays == null || roomStays.roomStays.isEmpty();
    }

    public String getResID_Hotel() {
        return resGlobalInfo == null ? "" : resGlobalInfo.getResID_Hotel();
    }

    public String getHotelId() {
        return roomStays == null ? "" : roomStays.getHotelId();
    }

    @Override
    public String toString() {
        return "HotelReservation{" + "roomStays=" + roomStays + ", services=" + services + ", resGuest=" + resGuest + ", resGlobalInfo=" + resGlobalInfo + '}';
    }

    private static class RoomStays implements Serializable {

        @XmlElement(name = "RoomStay")
        private List<RoomStay> roomStays;

        @Override
        public String toString() {
            return "RoomStays{" + "roomStays=" + roomStays + '}';
        }

        public String getHotelId() {
            return roomStays == null || roomStays.isEmpty() ? "" : roomStays.get(0).getHotelId();
        }
    }

    private static class Services implements Serializable {

        @XmlElement(name = "Service")
        private List<Service> service;

        @Override
        public String toString() {
            return "Services{" + "services=" + service + '}';
        }
    }

    private static class ResGuests implements Serializable {

        @XmlElement(name = "ResGuest")
        private List<ResGuest> resGuest;

        @Override
        public String toString() {
            return "ResGuests{" + "resGuest=" + resGuest + '}';
        }
    }
}