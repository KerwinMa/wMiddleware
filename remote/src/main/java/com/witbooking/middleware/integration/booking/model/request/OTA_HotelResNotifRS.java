package com.witbooking.middleware.integration.booking.model.request;

import com.witbooking.middleware.integration.booking.model.Constants;
import com.witbooking.middleware.integration.booking.model.response.OTA_StandardResponse;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * OTA_HotelResNotifRS class is the object sent to Booking to inform the status
 * about {@link OTA_HotelResNotifRQ} reservations request.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 * @see OTA_HorelResNotifRQ
 */
@XmlRootElement(name = "OTA_HotelResNotifRS")
@XmlAccessorType(XmlAccessType.FIELD)
public final class OTA_HotelResNotifRS extends OTA_StandardResponse implements Serializable {

    @XmlElement(name = "Success")
    private static String succ = "";
    @XmlElement(name = "Errors")
    private static String err = null;
    /**
     * {@link HotelReservations} list has messages to send.
     */
    @XmlElement(name = "HotelReservations")
    private HotelReservations hotelReservations;

    /**
     * Constructor who initialized {@link #version} to
     * {@link Constants#VERSION_2_001} and assigns a new
     * {@link HotelReservations} to {@link #hotelReservations}.
     */
    public OTA_HotelResNotifRS() {
        version = Constants.VERSION_2_001;
        hotelReservations = new HotelReservations();
    }

    /**
     * Constructor who initialized {@link #hotelReservations} with a new new
     * {@link HotelReservations} and {@link #add(String, String)
     * } the values given.
     *
     * @param resIDValueBooking see {@link #add(String, String)
     *                          }
     * @ param resIDValueOwn see {@link #add(String, String)
     * }
     * @ see #add(java.lang.String, java.lang.String)
     */
    public OTA_HotelResNotifRS(String resIDValueBooking, String resIDValueOwn) {
        hotelReservations = new HotelReservations();
        add(resIDValueBooking, resIDValueOwn);
    }

    /**
     * Applies {@link HotelReservations#add(String, String)
     * } with the
     * <code>String</codes> givens..
     *
     * @param resIDValueBooking See {@link HotelReservations#add(String, String)
     *                          }.
     * @param resIDValueOwn     See {@link HotelReservations#add(String, String)
     *                          }.
     */
    public void add(String resIDValueBooking, String resIDValueOwn) {
        hotelReservations.add(resIDValueBooking, resIDValueOwn);
    }

    /**
     * HotelReservations class contains {@link HotelReservation} list.
     */
    private static final class HotelReservations implements Serializable {

        @XmlElement(name = "HotelReservation")
        private List<HotelReservation> reservations;

        /**
         * Creates new {@link HotelReservation#HotelReservation(String, String)
         * } with the
         * <code>String</code> givens.
         *
         * @param resIDValueBooking see {@link HotelReservation#HotelReservation(String, String)
         *                          }
         * @ param resIDValueOwn see {@link HotelReservation#HotelReservation(String, String)
         * }
         */
        public void add(String resIDValueBooking, String resIDValueOwn) {
            reservations.add(new HotelReservation(resIDValueBooking, resIDValueOwn));
        }

        /**
         * HotelReservation class is implements to be a XML Wrapper of
         * {@link HotelReservation.ResGlobalInfo.HotelReservationIDs.HotelReservationID}.
         */
        private static final class HotelReservation implements Serializable {

            @XmlElement(name = "ResGlobalInfo")
            private ResGlobalInfo resGlobalInfo;

            public HotelReservation(String resIDValueBooking, String resIDValueOwn) {
                resGlobalInfo = new ResGlobalInfo(resIDValueBooking, resIDValueOwn);
            }

            /**
             * ResGlobalInfo class is implements to be a XML Wrapper of
             * {@link ResGlobalInfo.HotelReservationIDs.HotelReservationID}.
             *
             * @see ResGlobalInfo.HotelReservationIDs.HotelReservationID
             */
            private static final class ResGlobalInfo implements Serializable {

                @XmlElement(name = "HotelReservationIDs")
                private HotelReservationIDs res;

                public ResGlobalInfo(String resIDValueBooking, String resIDValueOwn) {
                    res = new HotelReservationIDs(resIDValueBooking, resIDValueOwn);
                }

                @Override
                public String toString() {
                    return "ResGlobalInfo{ res=" + res + '}';
                }

                /**
                 * HotelReservationIDs class is implements to be a XML Wrapper
                 * of {@link HotelReservationID}.
                 *
                 * @see HotelReservationID
                 */
                private static final class HotelReservationIDs implements Serializable {

                    /**
                     * {@link HotelReservationID} array who contains two
                     * elements. One element has the
                     * <code>resIDValueBooking</code> and the other has the
                     * <code>resIDValueOwn</code>.
                     */
                    @XmlElement(name = "HotelReservationID")
                    private HotelReservationID[] reservationsMapper;

                    /**
                     * Constructor who creates the two
                     * {@link HotelReservationID} objects stored in
                     * {@link #reservationsMapper}. The first element contains
                     * the ReservationRS Booking id and the second contains our id
                     * how correspond to this reservation.
                     *
                     * @param resIDValueBooking Booking reservation id.
                     * @param resIDValueOwn     Own system reservation id.
                     */
                    public HotelReservationIDs(String resIDValueBooking, String resIDValueOwn) {
                        reservationsMapper = new HotelReservationID[2];
                        reservationsMapper[0] = new HotelReservationID(resIDValueBooking,
                                Constants.OTA_HOTEL_RES_NOTIF_RS_SOURCE_BOOKING_SOURCE,
                                Constants.OTA_HOTEL_RES_NOTIF_RS_TYPE_RESERVATION_TYPE);
                        reservationsMapper[1] = new HotelReservationID(resIDValueOwn,
                                Constants.OTA_HOTEL_RES_NOTIF_RS_SOURCE_OWN_SOURCE,
                                Constants.OTA_HOTEL_RES_NOTIF_RS_TYPE_RESERVATION_TYPE);
                    }

                    /**
                     * HotelReservationID class is used to indicate the mapping
                     * between Booking reservation identifiers and our system
                     * reservation identifiers.
                     */
                    private static final class HotelReservationID implements Serializable {

                        /**
                         * resIDValue contains the actual value associated with
                         * ResID_Type as generated by the system that is the
                         * source of the ResID_Type.
                         */
                        @XmlAttribute(name = "ResID_Value")
                        private String resIDValue;
                        /**
                         * A unique identifier to indicate the source system
                         * which generated the ResID_Value. In this case
                         * BOOKING.COM if the id value correspond to booking.com
                         * or RT if the id value is our services.
                         */
                        @XmlAttribute(name = "ResID_Source")
                        private String resIDSource;
                        /**
                         * Defines the type of ReservationRS ID (EG reservation
                         * number, cancellation number). Refer to OpenTravel
                         * Code List Unique ID Type (UIT). <a target="_blank"
                         * href="http://www.opentravelcommunityforum.com/forum/viewtopic.php?f=8&t=147">This</a>
                         * link shows this Code.
                         */
                        @XmlAttribute(name = "ResID_Type")
                        private String resIDType;

                        /**
                         * Constructor empty required by XML Library.
                         */
                        public HotelReservationID() {
                        }

                        /**
                         * Constructor initialized {@link #resIDValue}, {@link #resIDSource} and {@link #resIDType} with the values given.
                         *
                         * @param resIDValue
                         * @param resIDSource
                         * @param resIDType
                         */
                        public HotelReservationID(String resIDValue, String resIDSource, String resIDType) {
                            this.resIDValue = resIDValue;
                            this.resIDSource = resIDSource;
                            this.resIDType = resIDType;
                        }

                        @Override
                        public String toString() {
                            return "HotelReservationID{" + "resIDValue=" + resIDValue + ", resIDSource=" + resIDSource + ", resIDType=" + resIDType + '}';
                        }
                    }

                    /**
                     * Constructor empty required by XML Library.
                     */
                    public HotelReservationIDs() {
                    }

                    @Override
                    public String toString() {
                        return "ResGlobalInfo{" + "res=" + reservationsMapper + '}';
                    }
                }
            }

            /**
             * Constructor empty required by XML library.
             */
            public HotelReservation() {
            }

            @Override
            public String toString() {
                return "HotelReservation{" + "resGlobalInfo=" + resGlobalInfo + '}';
            }
        }

        /**
         * Constructor empty required by XML library.
         */
        public HotelReservations() {
            reservations = new ArrayList<HotelReservation>();
        }

        @Override
        public String toString() {
            return "HotelReservations{" + "reservations=" + reservations + '}';
        }
    }

    @Override
    public String toString() {
        return "OTA_HotelResNotifRS{" + "hotelReservations=" + hotelReservations + '}';
    }
}