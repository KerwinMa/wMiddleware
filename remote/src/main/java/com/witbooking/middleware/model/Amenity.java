package com.witbooking.middleware.model;

/**
 * Amenity.java
 * User: jose
 * Date: 4/11/14
 * Time: 11:11 AM
 */
public enum Amenity {
    BEACH(1, "beach", new String[]{"beach"}),
    WIFI(2, "wifi", new String[]{"internet"}),
    PARKING(3, "parking", new String[]{"parking"}),
    FAMILY_FRIENDLY_ROOMS(4, "family friendly rooms", new String[]{"familyFriendly"}),
    RESTAURANT(6, "restaurant", new String[]{"restaurant"}),
    OUTDOOR_POOL(8, "outdoor Pool", new String[]{"pool", "outdoorPool"}),
    GYM(9, "gym", new String[]{"fitnessFacilities"}),
    SPA(13, "spa", new String[]{"spa"}),
    ACCESSIBLE_ROOMS(14, "accessible room", new String[]{"accessibleRooms"}),
    AIR_CONDITIONED(15, "air conditioned", new String[]{"airConditioned"}),
    AIRPORT_SHUTTLE(16, "airport shuttle", new String[]{"airportShuttle"}),
    BOUTIQUE_SHOP(17, "boutique or shop", new String[]{"boutiqueOrShop"}),
    BUSINESS_CENTER(18, "business center", new String[]{"businessCenter"}),
    CASINO(19, "casino", new String[]{"casino"}),
    FREE_BREAKFAST(20, "free breakfast", new String[]{"freeBreakfast"}),
    FREE_NEWSPAPER(21, "free newspaper", new String[]{"freeNewspaper"}),
    FREE_INTERNET(22, "free internet", new String[]{"freeInternet", "internet"}),
    FREE_PARKING(23, "free parking", new String[]{"freeParking", "parking"}),
    GOLF_COURSE(24, "golf course", new String[]{"golfCourse"}),
    HOT_TUB(25, "hot tub", new String[]{"hotTub"}),
    IN_ROOM_KITCHEN(26, "in room kitchen", new String[]{"inRoomKitchen"}),
    INDOOR_POOL(27, "indoor pool", new String[]{"indoorPool"}),
    INTERNET(28, "internet", new String[]{"internet"}),
    KIDS_ACTIVITIES(29, "kids activities", new String[]{"kidsActivities"}),
    LAUNDRY(30, "laundry", new String[]{"laundry"}),
    NON_SMOKING_ROOMS(31, "non smoking rooms", new String[]{"nonSmokingRooms"}),
    PETS_ALLOWED(32, "pets allowed", new String[]{"petsAllowed"}),
    ROOM_SERVICE(33, "room service", new String[]{"roomService"}),
    TENNIS_COURTS(34, "tennis courts", new String[]{"tennisCourts"}),
    WAKE_UP_SERVICE(35, "wake up service", new String[]{"wakeupService"}),
    BANQUET_FACILITIES(36, "banquet facilities", new String[]{"banquetFacilities"}),
    BAR_OR_LOUNGE(37, "bar o lounge", new String[]{"barOrLounge"});

    private int id;
    private String name;
    private String[] itemsGoogle;

    Amenity(int id, String name, String[] itemsGoogle) {
        this.id = id;
        this.name = name;
        this.itemsGoogle = itemsGoogle;
    }

    public String[] getItemsGoogle() {
        return itemsGoogle;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Amenity fromString(String name) {
        if (name == null)
            return null;
        for (final Amenity amenity : Amenity.class.getEnumConstants()) {
            if (amenity.getName().toLowerCase().equals(name.toLowerCase())) {
                return amenity;
            }
        }
        return null;
    }

    public static Amenity fromId(int id) {
        for (final Amenity amenity : Amenity.class.getEnumConstants()) {
            if (amenity.getId() == id) {
                return amenity;
            }
        }
        return null;
    }
}