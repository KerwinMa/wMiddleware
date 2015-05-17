package com.witbooking.middleware.integration.booking.model.response.otahotelrefnotifrq;

import com.witbooking.middleware.integration.booking.model.Constants;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
@XmlRootElement(name = "OTA_HotelResNotifRQ", namespace = Constants.XMLNS)
@XmlAccessorType(XmlAccessType.FIELD)
public class OTA_HotelResNotifRQ implements Serializable {

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
    @XmlElement(name = "HotelReservations")
    private HotelReservations hotelReservations;
    private static String RUID;

    public String getVersion() {
        return version;
    }

    public OTA_HotelResNotifRQ() {
    }

    public String getHotelId() {
        return hotelReservations == null ? "" : hotelReservations.getHotelId();
    }

    public void setRUID(String ruid) {
        this.RUID = ruid;
    }

    /**
     * Generates the idConfirmation (idgeneradomulti on our database) from {@link HotelReservation}.
     *
     * @param item
     * @return
     */
    public static String generateIdConfirmation(HotelReservation item) {
        return Constants.BOOKING_RESERVATION_ID_PREFIX + item.getReservationId();
    }


    public List<HotelReservation> getHotelReservations() {
        return (hotelReservations == null || hotelReservations.hotelReservations == null)
                ? new ArrayList<HotelReservation>()
                : hotelReservations.hotelReservations;
    }

    public boolean isEmpty() {
        return hotelReservations == null ? true : (hotelReservations.size() == 0);
    }

    public int size() {
        return hotelReservations == null ? 0 : hotelReservations.size();
    }

    @Override
    public String toString() {
        //System.out.println("Comments: "+Comments);
        return "OTA_HotelResNotifRQ{" + "RUID=" + RUID + ", currentTimestamp=" + currentTimestamp + ", hotelReservations=" + hotelReservations + '}';
    }

    //CAMBIAR ESTA CLASE A SU PROPIO ARCHIVO
    private static class HotelReservations implements Serializable {

        @XmlElement(name = "HotelReservation")
        private List<HotelReservation> hotelReservations;

        public HotelReservations() {
        }

        public int size() {
            return hotelReservations == null ? 0 : hotelReservations.size();
        }

        public boolean isEmpty() {
            return hotelReservations == null || hotelReservations.isEmpty();
        }

        @Override
        public String toString() {
            return "HotelReservations{" + "hotelReservations=" + hotelReservations + '}';
        }

        public String getHotelId() {
            return hotelReservations == null || hotelReservations.isEmpty() ? "" : hotelReservations.get(0).getHotelId();
        }

        public List<String> getIdHotelReservations() {
            List<String> ret = new ArrayList<String>();
            for (HotelReservation res : hotelReservations) {
                ret.add(res.getReservationId());
            }
            return ret;
        }
    }
}
