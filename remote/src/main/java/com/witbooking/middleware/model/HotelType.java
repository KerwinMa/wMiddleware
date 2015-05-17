package com.witbooking.middleware.model;


/**
 * HotelType.java
 * User: jose
 * Date: 29/04/14
 * Time: 18:12
 */
public enum HotelType {
    HOTEL("Hotel", "Hotel"),
    APART_HOTEL("Aparthotel", "Aparthotel"),
    APARTMENT("Apartment", "Apartamento"),
    HOSTEL("Hostel", "Parador"),
    HOUSES("House", "Casas"),
    HOTEL_WITH_SPA("Hotel/Spa", "Hotel con spa"),
    RURAL("Rural", "Rural"),
    WATERING_PLACE("Watering Place", "Balneario"),
    WELLNESS("Wellness", "Wellness"),
    PARKING("Parking", "Parking"),
    EXCURSIONS("Excursions", "Excursiones");
    private String type;
    private String code;

    HotelType(String type, String code) {
        this.type = type;
        this.code = code;
    }

    public static HotelType getFromType(final String type) {
        if (type == null || type.trim().isEmpty())
            return HOTEL;
        for (final HotelType hotelType : values()) {
            if (hotelType.type.equals(type))
                return hotelType;
        }
        return HOTEL;
    }

    public static HotelType getFromCode(final String code) {
        if (code == null || code.trim().isEmpty())
            return HOTEL;
        for (final HotelType hotelType : values()) {
            if (hotelType.code.equals(code))
                return hotelType;
        }
        return HOTEL;
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }
}
