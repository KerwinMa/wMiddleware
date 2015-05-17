
package com.witbooking.middleware.integration.google.model.feed;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="accept_online_booking" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="accessible_rooms" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="air_conditioned" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="airport_shuttle" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="banquet_facilities" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="bar_or_lounge" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="beach" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="boutique_or_shop" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="business_center" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="casino" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="family_friendly" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="fitness_facilities" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="free_breakfast" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="free_internet" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="free_newspaper" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="free_parking" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="golf_course" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="hot_tub" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="in_room_kitchen" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="indoor_pool" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="internet" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="kids_activities" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="laundry" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="non_smoking_rooms" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="outdoor_pool" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="parking" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="pets_allowed" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="pool" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="restaurant" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="room_service" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="spa" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="tennis_courts" type="{}amenity" minOccurs="0"/>
 *         &lt;element name="wakeup_service" type="{}amenity" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "acceptOnlineBooking",
        "accessibleRooms",
        "airConditioned",
        "airportShuttle",
        "banquetFacilities",
        "barOrLounge",
        "beach",
        "boutiqueOrShop",
        "businessCenter",
        "casino",
        "familyFriendly",
        "fitnessFacilities",
        "freeBreakfast",
        "freeInternet",
        "freeNewspaper",
        "freeParking",
        "golfCourse",
        "hotTub",
        "inRoomKitchen",
        "indoorPool",
        "internet",
        "kidsActivities",
        "laundry",
        "nonSmokingRooms",
        "outdoorPool",
        "parking",
        "petsAllowed",
        "pool",
        "restaurant",
        "roomService",
        "spa",
        "tennisCourts",
        "wakeupService"
})
@XmlRootElement(name = "amenities")
public class Amenities {

    @XmlElement(name = "accept_online_booking")
    public Amenity acceptOnlineBooking;
    @XmlElement(name = "accessible_rooms")
    public Amenity accessibleRooms;
    @XmlElement(name = "air_conditioned")
    public Amenity airConditioned;
    @XmlElement(name = "airport_shuttle")
    public Amenity airportShuttle;
    @XmlElement(name = "banquet_facilities")
    public Amenity banquetFacilities;
    @XmlElement(name = "bar_or_lounge")
    public Amenity barOrLounge;
    public Amenity beach;
    @XmlElement(name = "boutique_or_shop")
    public Amenity boutiqueOrShop;
    @XmlElement(name = "business_center")
    public Amenity businessCenter;
    public Amenity casino;
    @XmlElement(name = "family_friendly")
    public Amenity familyFriendly;
    @XmlElement(name = "fitness_facilities")
    public Amenity fitnessFacilities;
    @XmlElement(name = "free_breakfast")
    public Amenity freeBreakfast;
    @XmlElement(name = "free_internet")
    public Amenity freeInternet;
    @XmlElement(name = "free_newspaper")
    public Amenity freeNewspaper;
    @XmlElement(name = "free_parking")
    public Amenity freeParking;
    @XmlElement(name = "golf_course")
    public Amenity golfCourse;
    @XmlElement(name = "hot_tub")
    public Amenity hotTub;
    @XmlElement(name = "in_room_kitchen")
    public Amenity inRoomKitchen;
    @XmlElement(name = "indoor_pool")
    public Amenity indoorPool;
    public Amenity internet;
    @XmlElement(name = "kids_activities")
    public Amenity kidsActivities;
    public Amenity laundry;
    @XmlElement(name = "non_smoking_rooms")
    public Amenity nonSmokingRooms;
    @XmlElement(name = "outdoor_pool")
    public Amenity outdoorPool;
    public Amenity parking;
    @XmlElement(name = "pets_allowed")
    public Amenity petsAllowed;
    public Amenity pool;
    public Amenity restaurant;
    @XmlElement(name = "room_service")
    public Amenity roomService;
    public Amenity spa;
    @XmlElement(name = "tennis_courts")
    public Amenity tennisCourts;
    @XmlElement(name = "wakeup_service")
    public Amenity wakeupService;

    /**
     * Gets the value of the acceptOnlineBooking property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getAcceptOnlineBooking() {
        return acceptOnlineBooking;
    }

    /**
     * Sets the value of the acceptOnlineBooking property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setAcceptOnlineBooking(Amenity value) {
        this.acceptOnlineBooking = value;
    }

    /**
     * Gets the value of the accessibleRooms property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getAccessibleRooms() {
        return accessibleRooms;
    }

    /**
     * Sets the value of the accessibleRooms property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setAccessibleRooms(Amenity value) {
        this.accessibleRooms = value;
    }

    /**
     * Gets the value of the airConditioned property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getAirConditioned() {
        return airConditioned;
    }

    /**
     * Sets the value of the airConditioned property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setAirConditioned(Amenity value) {
        this.airConditioned = value;
    }

    /**
     * Gets the value of the airportShuttle property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getAirportShuttle() {
        return airportShuttle;
    }

    /**
     * Sets the value of the airportShuttle property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setAirportShuttle(Amenity value) {
        this.airportShuttle = value;
    }

    /**
     * Gets the value of the banquetFacilities property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getBanquetFacilities() {
        return banquetFacilities;
    }

    /**
     * Sets the value of the banquetFacilities property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setBanquetFacilities(Amenity value) {
        this.banquetFacilities = value;
    }

    /**
     * Gets the value of the barOrLounge property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getBarOrLounge() {
        return barOrLounge;
    }

    /**
     * Sets the value of the barOrLounge property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setBarOrLounge(Amenity value) {
        this.barOrLounge = value;
    }

    /**
     * Gets the value of the beach property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getBeach() {
        return beach;
    }

    /**
     * Sets the value of the beach property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setBeach(Amenity value) {
        this.beach = value;
    }

    /**
     * Gets the value of the boutiqueOrShop property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getBoutiqueOrShop() {
        return boutiqueOrShop;
    }

    /**
     * Sets the value of the boutiqueOrShop property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setBoutiqueOrShop(Amenity value) {
        this.boutiqueOrShop = value;
    }

    /**
     * Gets the value of the businessCenter property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getBusinessCenter() {
        return businessCenter;
    }

    /**
     * Sets the value of the businessCenter property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setBusinessCenter(Amenity value) {
        this.businessCenter = value;
    }

    /**
     * Gets the value of the casino property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getCasino() {
        return casino;
    }

    /**
     * Sets the value of the casino property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setCasino(Amenity value) {
        this.casino = value;
    }

    /**
     * Gets the value of the familyFriendly property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getFamilyFriendly() {
        return familyFriendly;
    }

    /**
     * Sets the value of the familyFriendly property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setFamilyFriendly(Amenity value) {
        this.familyFriendly = value;
    }

    /**
     * Gets the value of the fitnessFacilities property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getFitnessFacilities() {
        return fitnessFacilities;
    }

    /**
     * Sets the value of the fitnessFacilities property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setFitnessFacilities(Amenity value) {
        this.fitnessFacilities = value;
    }

    /**
     * Gets the value of the freeBreakfast property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getFreeBreakfast() {
        return freeBreakfast;
    }

    /**
     * Sets the value of the freeBreakfast property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setFreeBreakfast(Amenity value) {
        this.freeBreakfast = value;
    }

    /**
     * Gets the value of the freeInternet property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getFreeInternet() {
        return freeInternet;
    }

    /**
     * Sets the value of the freeInternet property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setFreeInternet(Amenity value) {
        this.freeInternet = value;
    }

    /**
     * Gets the value of the freeNewspaper property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getFreeNewspaper() {
        return freeNewspaper;
    }

    /**
     * Sets the value of the freeNewspaper property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setFreeNewspaper(Amenity value) {
        this.freeNewspaper = value;
    }

    /**
     * Gets the value of the freeParking property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getFreeParking() {
        return freeParking;
    }

    /**
     * Sets the value of the freeParking property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setFreeParking(Amenity value) {
        this.freeParking = value;
    }

    /**
     * Gets the value of the golfCourse property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getGolfCourse() {
        return golfCourse;
    }

    /**
     * Sets the value of the golfCourse property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setGolfCourse(Amenity value) {
        this.golfCourse = value;
    }

    /**
     * Gets the value of the hotTub property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getHotTub() {
        return hotTub;
    }

    /**
     * Sets the value of the hotTub property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setHotTub(Amenity value) {
        this.hotTub = value;
    }

    /**
     * Gets the value of the inRoomKitchen property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getInRoomKitchen() {
        return inRoomKitchen;
    }

    /**
     * Sets the value of the inRoomKitchen property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setInRoomKitchen(Amenity value) {
        this.inRoomKitchen = value;
    }

    /**
     * Gets the value of the indoorPool property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getIndoorPool() {
        return indoorPool;
    }

    /**
     * Sets the value of the indoorPool property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setIndoorPool(Amenity value) {
        this.indoorPool = value;
    }

    /**
     * Gets the value of the internet property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getInternet() {
        return internet;
    }

    /**
     * Sets the value of the internet property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setInternet(Amenity value) {
        this.internet = value;
    }

    /**
     * Gets the value of the kidsActivities property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getKidsActivities() {
        return kidsActivities;
    }

    /**
     * Sets the value of the kidsActivities property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setKidsActivities(Amenity value) {
        this.kidsActivities = value;
    }

    /**
     * Gets the value of the laundry property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getLaundry() {
        return laundry;
    }

    /**
     * Sets the value of the laundry property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setLaundry(Amenity value) {
        this.laundry = value;
    }

    /**
     * Gets the value of the nonSmokingRooms property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getNonSmokingRooms() {
        return nonSmokingRooms;
    }

    /**
     * Sets the value of the nonSmokingRooms property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setNonSmokingRooms(Amenity value) {
        this.nonSmokingRooms = value;
    }

    /**
     * Gets the value of the outdoorPool property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getOutdoorPool() {
        return outdoorPool;
    }

    /**
     * Sets the value of the outdoorPool property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setOutdoorPool(Amenity value) {
        this.outdoorPool = value;
    }

    /**
     * Gets the value of the parking property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getParking() {
        return parking;
    }

    /**
     * Sets the value of the parking property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setParking(Amenity value) {
        this.parking = value;
    }

    /**
     * Gets the value of the petsAllowed property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getPetsAllowed() {
        return petsAllowed;
    }

    /**
     * Sets the value of the petsAllowed property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setPetsAllowed(Amenity value) {
        this.petsAllowed = value;
    }

    /**
     * Gets the value of the pool property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getPool() {
        return pool;
    }

    /**
     * Sets the value of the pool property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setPool(Amenity value) {
        this.pool = value;
    }

    /**
     * Gets the value of the restaurant property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getRestaurant() {
        return restaurant;
    }

    /**
     * Sets the value of the restaurant property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setRestaurant(Amenity value) {
        this.restaurant = value;
    }

    /**
     * Gets the value of the roomService property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getRoomService() {
        return roomService;
    }

    /**
     * Sets the value of the roomService property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setRoomService(Amenity value) {
        this.roomService = value;
    }

    /**
     * Gets the value of the spa property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getSpa() {
        return spa;
    }

    /**
     * Sets the value of the spa property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setSpa(Amenity value) {
        this.spa = value;
    }

    /**
     * Gets the value of the tennisCourts property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getTennisCourts() {
        return tennisCourts;
    }

    /**
     * Sets the value of the tennisCourts property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setTennisCourts(Amenity value) {
        this.tennisCourts = value;
    }

    /**
     * Gets the value of the wakeupService property.
     *
     * @return possible object is
     * {@link Amenity }
     */
    public Amenity getWakeupService() {
        return wakeupService;
    }

    /**
     * Sets the value of the wakeupService property.
     *
     * @param value allowed object is
     *              {@link Amenity }
     */
    public void setWakeupService(Amenity value) {
        this.wakeupService = value;
    }

}
