package com.witbooking.middleware.integration.booking.model.response;

import com.witbooking.middleware.integration.booking.model.Constants;
import com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq.HotelReservation;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
@XmlRootElement(name = "OTA_HotelResModifyNotifRQ", namespace = Constants.XMLNS)
@XmlAccessorType(XmlAccessType.FIELD)
public class OTA_HotelResModifyNotifRQ implements Serializable {

    @XmlAttribute(name = "xmlns:xsi")
    private static final String xmlns_xsi = Constants.XMLNS_XSI;
    @XmlAttribute(name = "xmlns")
    private static final String xmlns = Constants.XMLNS;
    @XmlAttribute(name = "xsi:schemaLocation")
    private static final String xmlns_schemaLocation = Constants.XMLNS_SCHEMA_LOCATION_MODIFY_NOTIF;
    @XmlAttribute(name = "TimeStamp")
    private Date currentTimestamp;
    @XmlAttribute(name = "Target")
    private static String target = Constants.TARGET_PRODUCTION;
    @XmlAttribute(name = "Version")
    private static String version = Constants.VERSION_2_001;
    @XmlElement(name = "HotelResModifies")
    private HotelResModify hotelResModifies;
    private static String RUID;

    @Override
    public String toString() {
        //System.out.println("Comments: "+Comments);
        return "OTA_HotelResModifyNotifRQ{" + "RUID=" + RUID + ", currentTimestamp=" + currentTimestamp + ", HotelResModifies=" + hotelResModifies + '}';
    }

    public String getHotelId() {
        return hotelResModifies == null ? "" : hotelResModifies.getHotelId();
    }

    public void setRUID(String ruid) {
        this.RUID = ruid;
    }

    public boolean isEmpty() {
        return (hotelResModifies == null) ? true : (hotelResModifies.isEmpty());
    }

    public int size() {
        return hotelResModifies == null ? 0 : hotelResModifies.size();
    }

    public List<HotelReservation> getHotelReservations() {
        return (hotelResModifies == null || hotelResModifies.hotelResModify == null)
                ? new ArrayList<HotelReservation>()
                : hotelResModifies.hotelResModify;
    }

    private static class HotelResModify implements Serializable {

        @XmlElement(name = "HotelResModify")
        private List<HotelReservation> hotelResModify;

        public HotelResModify() {
        }

        public int size() {
            return hotelResModify == null ? 0 : hotelResModify.size();
        }

        public boolean isEmpty() {
            return hotelResModify == null || hotelResModify.isEmpty();
        }

        public String getHotelId() {
            return hotelResModify == null || hotelResModify.isEmpty() ? "" : hotelResModify.get(0).getHotelId();
        }

        @Override
        public String toString() {
            return "HotelResModifies{" + "HotelResModify=" + hotelResModify + '}';
        }

        public List<String> getIdHotelReservations() {
            List<String> ret = new ArrayList<String>();
            for (HotelReservation res : hotelResModify) {
                ret.add(res.getReservationId());
            }
            return ret;
        }
    }
}
