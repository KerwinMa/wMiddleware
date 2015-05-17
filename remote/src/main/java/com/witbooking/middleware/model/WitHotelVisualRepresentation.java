package com.witbooking.middleware.model;

import com.google.common.base.Objects;

import java.util.List;

/**
 * WitHotelVisualRepresentation.java
 * User: jose
 * Date: 2/24/14
 * Time: 10:40 AM
 */
public class WitHotelVisualRepresentation extends HotelVisualRepresentation {

    protected List<Accommodation> accommodations;
    protected List<LocationPoint> locationPoints;
    protected List<Review> reviews;


    public WitHotelVisualRepresentation(List<Language> languages, List<Currency> currencies, List<Discount> discounts, List<Service> services, List<FrontEndMessage> frontEndMessages, List<Accommodation> accommodations, List<LocationPoint> locationPoints, List<Review> reviews) {
        super(languages, currencies, discounts, services, frontEndMessages);
        this.accommodations = accommodations;
        this.locationPoints = locationPoints;
        this.reviews = reviews;
    }

    public List<Accommodation> getAccommodations() {
        return accommodations;
    }

    public void setAccommodations(List<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }

    public List<LocationPoint> getLocationPoints() {
        return locationPoints;
    }

    public void setLocationPoints(List<LocationPoint> locationPoints) {
        this.locationPoints = locationPoints;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("accommodations", accommodations)
                .add("locationPoints", locationPoints)
                .add("reviews", reviews)
                .toString();
    }
}