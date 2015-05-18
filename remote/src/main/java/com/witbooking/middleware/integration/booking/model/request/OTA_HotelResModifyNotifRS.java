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
 * @see OTA_HotelResNotifRQ
 */
@XmlRootElement(name = "OTA_HotelResModifyNotifRS")
@XmlAccessorType(XmlAccessType.FIELD)
public final class OTA_HotelResModifyNotifRS extends OTA_StandardResponse implements Serializable {

    @XmlElement(name = "Success")
    private static String succ = "";
    @XmlElement(name = "Errors")
    private static String err = null;
    /**
     * {@link HotelResModifies} list has messages to send.
     */
    @XmlElement(name = "HotelResModifies")
    private HotelResModifies hotelModifiedReservations;

    /**
     * Constructor who initialized {@link #version} to
     * {@link Constants#VERSION_2_001} and assigns a new
     * {@link HotelResModifies} to {@link #hotelModifiedReservations}.
     */
    public OTA_HotelResModifyNotifRS() {
        version = Constants.VERSION_2_001;
        hotelModifiedReservations = new HotelResModifies();
    }

    /**
     * Constructor who initialized {@link #hotelModifiedReservations} with a new new
     * {@link HotelResModifies} and {@link #add(java.lang.String, java.lang.String)
     * } the values given.
     *
     * @param resIDValueBooking see {@link #add(java.lang.String, java.lang.String)
     *                          }
     * @ param resIDValueOwn see {@link #add(java.lang.String, java.lang.String)
     * }
     * @ see #add(java.lang.String, java.lang.String)
     */
    public OTA_HotelResModifyNotifRS(String resIDValueBooking, String resIDValueOwn) {
        hotelModifiedReservations = new HotelResModifies();
        add(resIDValueBooking, resIDValueOwn);
    }

    /**
     * Applies {@link HotelResModifies#add(java.lang.String, java.lang.String)
     * } with the
     * <code>String</codes> givens..
     *
     * @param resIDValueBooking See {@link HotelResModifies#add(java.lang.String, java.lang.String)
     *                          }.
     * @param resIDValueOwn     See {@link HotelResModifies#add(java.lang.String, java.lang.String)
     *                          }.
     */
    public void add(String resIDValueBooking, String resIDValueOwn) {
        hotelModifiedReservations.add(resIDValueBooking, resIDValueOwn);
    }

    /**
     * HotelReservations class contains {@link HotelResModifies} list.
     */
    private static final class HotelResModifies implements Serializable {

        @XmlElement(name = "HotelResModify")
        private List<HotelResModify> reservations;

        /**
         * Creates new {@link HotelResModifies#HotelResModify(java.lang.String, java.lang.String)
         * } with the
         * <code>String</code> givens.
         *
         * @param resIDValueBooking see {@link HotelResModifies#HotelReservation(java.lang.String, java.lang.String)
         *                          }
         * @ param resIDValueOwn see {@link HotelResModifies#HotelReservation(java.lang.String, java.lang.String)
         * }
         */
        public void add(String resIDValueBooking, String resIDValueOwn) {
            reservations.add(new HotelResModify(resIDValueBooking, resIDValueOwn));
        }

        /**
         */
        private static final class HotelResModify implements Serializable {

            @XmlElement(name = "ResGlobalInfo")
            private ResGlobalInfo resGlobalInfo;

            public HotelResModify(String resIDValueBooking, String resIDValueOwn) {
                resGlobalInfo = new ResGlobalInfo(resIDValueBooking, resIDValueOwn);
            }

            /**
             * ResGlobalInfo class is implements to be a XML Wrapper of
             * {@link ResGlobalInfo.HotelResModify.HotelReservationID}.
             *
             * @see ResGlobalInfo.HotelResModify.HotelReservationID
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
                 * of {@link HotelResModifies}.
                 *
                 * @see HotelResModifies
                 */
                private static final class HotelReservationIDs implements Serializable {

                    /**
                     * {@link HotelResModifies} array who contains two
                     * elements. One element has the
                     * <code>resIDValueBooking</code> and the other has the
                     * <code>resIDValueOwn</code>.
                     */
                    @XmlElement(name = "HotelReservationID")
                    private HotelReservationID[] reservationsMapper;

                    /**
                     * Constructor who creates the two
                     * {@link HotelResModifies} objects stored in
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
            public HotelResModify() {
            }

            @Override
            public String toString() {
                return "HotelReservation{" + "resGlobalInfo=" + resGlobalInfo + '}';
            }
        }

        /**
         * Constructor empty required by XML library.
         */
        public HotelResModifies() {
            reservations = new ArrayList<HotelResModify>();
        }

        @Override
        public String toString() {
            return "HotelReservations{" + "reservations=" + reservations + '}';
        }
    }

    @Override
    public String toString() {
        return "OTA_HotelResNotifRS{" + "hotelReservations=" + hotelModifiedReservations + '}';
    }
}