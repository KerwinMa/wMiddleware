package com.witbooking.middleware.integration.booking.model;

import com.witbooking.middleware.integration.booking.model.request.OTA_HotelResNotifRS;
import com.witbooking.middleware.utils.FileUtils;
import com.witbooking.middleware.utils.XMLUtils;

import java.util.List;

/**
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
public class Constants {

    //https://supply-xml.booking.com/hotels/xml/rooms?xml=%3Crequest%3E%3Cusername%3EwitbookingXML%3C/username%3E%3Cpassword%3E4546tyf57y7%3C/password%3E%3C/request%3E
    //https://supply-xml.booking.com/hotels/xml/rates?xml=%3Crequest%3E%3Cusername%3EwitbookingXML%3C/username%3E%3Cpassword%3E4546tyf57y7%3C/password%3E%3C/request%3E
    public static final String XMLNS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
    public static final String XMLNS = "http://www.opentravel.org/OTA/2003/05";
    public static final String XMLNS_SCHEMA_LOCATION_RATE_AMOUNT = "http://www.opentravel.org/OTA/2003/05 OTA_HotelRateAmountNotifRQ.xsd";
    public static final String XMLNS_SCHEMA_LOCATION_AVAIL_NOTIF = "http://www.opentravel.org/OTA/2003/05 OTA_HotelAvailNotifRQ.xsd";
    public static final String XMLNS_SCHEMA_LOCATION_MODIFY_NOTIF = "http://www.opentravel.org/OTA/2003/05 OTA_HotelResModifyNotifRQ.xsd";
    public static final String VERSION_1_004 = "1.004";
    public static final String VERSION_1_005 = "1.005";
    public static final String VERSION_3_000 = "3.000";
    public static final String VERSION_2_001 = "2.001";
    public static final String TARGET_PRODUCTION = "Production";

    /**
     * CHANNEL_TICKER static field who represents ticker of Booking type entries
     * and whose value is {@value #CHANNEL_TICKER}.
     */
    public static final String CHANNEL_TICKER = "BOOKING";
    /**
     * KEY_ROOM_STAY static field who represents the key of Booking roomStay on
     * mapping and whose value is {@value #KEY_ROOM_STAY}.
     */
    public static final String KEY_ROOM_STAY = "Room Stay";
    /**
     * KEY_RATE_PLAN static field who represents the key of Booking ratePlan on
     * mapping and whose value is {@value #KEY_RATE_PLAN}.
     */
    public static final String KEY_RATE_PLAN = "Rate Plan";
    /**
     * NUMBER_OF_KEYS static field who represents the number Booking type codes
     * and whose value is {@value #NUMBER_OF_KEYS}.
     */
    public static final int NUMBER_OF_KEYS = 2;

    /**
     * Is used in {@link XMLUtils#getRUID(String) }.
     */
    public static final String RUID = "RUID";
    /**
     * PARAM_VALUE_RES_ID static field who represents the Parameter to request reservation by Booking reservation ID
     * and whose value is '{@value #PARAM_VALUE_RES_ID}'.
     */
    public static final String PARAM_VALUE_RES_ID = "id";
    /**
     * PARAM_VALUE_RES_ID static field who represents the Parameter to request all reservations by Booking
     * Hotel ID and whose value is '{@value #PARAM_VALUE_HOTEL_ID}'.
     */
    public static final String PARAM_VALUE_HOTEL_ID = "hotel_ids";
    /**
     * PARAM_VALUE_LAST_CHANGE static field who represents the Parameter to request all reservations by Booking
     * since a date selected and whose value is '{@value #PARAM_VALUE_LAST_CHANGE}'.
     */
    public static final String PARAM_VALUE_LAST_CHANGE = "last_change";

    /**
     * This 3 constants are used in {@link OTA_HotelResNotifRS.HotelReservations.HotelReservation.ResGlobalInfo.HotelReservationIDs} constructor.
     */
    public static final String OTA_HOTEL_RES_NOTIF_RS_SOURCE_BOOKING_SOURCE = "BOOKING.COM";
    public static final String OTA_HOTEL_RES_NOTIF_RS_SOURCE_OWN_SOURCE = "RT";
    public static final String OTA_HOTEL_RES_NOTIF_RS_TYPE_RESERVATION_TYPE = "14";
    /**
     * Is used in BookingBean
     */
    public static final int ONE_ROOM_OF_THIS_TYPE = 1;


    public static final String BOOKING_RESERVATION_ID_PREFIX = "B";
    public static final String BOOKING_RESERVATION_ROOM_ID_PREFIX = "R";
    /**
     * Utilities for tests.
     */
    public static final String DIR_TEST_FILES = "../testFiles/Booking/";
    public static final String DIR_FOLDER_OTA_HOTEL_RES_NOTIF = DIR_TEST_FILES + "OTA_HotelResNotif/";
    public static final String DIR_FOLDER_OTA_HOTEL_REF_MODIF_NOTIF = DIR_TEST_FILES + "OTA_HotelRefModifNotif/";
    public static final String DIR_FOLDER_RANDOMLY_GENERATED_FILES = DIR_TEST_FILES + "randomly_generated_files/";
    public static final String DIR_FILE_OUTPUT = DIR_FOLDER_OTA_HOTEL_RES_NOTIF + "OUTPUT_OTA_HotelResNotifRQ.xml";
    public static final String FILE_ORIGINAL_OUTPUT = DIR_FOLDER_OTA_HOTEL_RES_NOTIF + "ORIGINAL_OUTPUT_OTA_HotelResNotifRQ.xml";
    public static final String FILE_2HAB_2NIGHTS_ORIGINAL_OUTPUT = DIR_FOLDER_OTA_HOTEL_RES_NOTIF + "2HAB_2NOCHES_OTA_HotelResNotifRQ.xml";
    public static final String EXAMPLE_FILE_OTA_HOTEL_REF_NOTIF_RQ = DIR_FOLDER_OTA_HOTEL_RES_NOTIF + "OTA_HotelResNotifRQ.xml";
    public static final String EXAMPLE_FILE_OTA_HOTEL_REF_MODIF_NOTIF_RQ = DIR_FOLDER_OTA_HOTEL_REF_MODIF_NOTIF + "OTA_HotelRefModifNotifRQ.xml";
    public static final String CUSTOM_MODIFY_FILE_OTA_HOTEL_REF_MODIF_NOTIF_RQ = DIR_FOLDER_OTA_HOTEL_REF_MODIF_NOTIF + "OTA_HotelRefModifNotifRQ_MODIFY.xml";
    public static final List<String> FILES_IN_OTA_HOTEL_REF_NOTIF;
    public static final String STANDARD_SINGLE_TICKER = "59273507";
    public static final String STANDARD_DOUBLE_TICKER = "59273508";
    public static final String JUNIOR_SUITE_TICKER = "59273509";
    public static final String RATE_PLAN_STANDARD_TICKER = "1925965";
    public static final String RATE_PLAN_NON_REFUNDABLE_TICKER = "1925966";
    public static final String RATE_PLAN_SPECIAL_TICKER = "1925967";

    static {
        FILES_IN_OTA_HOTEL_REF_NOTIF = FileUtils.listFilesWithPathInFolder(DIR_FOLDER_OTA_HOTEL_RES_NOTIF);
    }
}
