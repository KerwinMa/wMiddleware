package com.witbooking.middleware.model;

import com.google.common.base.Objects;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * HotelVisualRepresentation.java
 * User: jose
 * Date: 2/21/14
 * Time: 11:31 AM
 */
public class HotelVisualRepresentation extends VisualRepresentation {

    private static final Logger logger = Logger.getLogger(HotelVisualRepresentation.class);

    //    protected List<Language> languages;
//    protected List<Currency> currencies;
//    protected List<Discount> discounts;
    protected List<Service> services;
//    protected List<FrontEndMessage> frontEndMessages;

    protected HotelVisualRepresentation(final HotelVisualRepresentation other) {
        super(other);
        if (other != null) {
            this.services = other.services;
        }
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public List<FrontEndMessage> getFrontEndMessages() {
        return frontEndMessages;
    }

    public void setFrontEndMessages(List<FrontEndMessage> frontEndMessages) {
        this.frontEndMessages = frontEndMessages;
    }

    protected HotelVisualRepresentation(List<Language> languages, List<Currency> currencies, List<Discount> discounts, List<Service> services, List<FrontEndMessage> frontEndMessages) {
        super(languages, currencies, frontEndMessages, discounts);
        this.services = services;
    }
//
//    protected HotelVisualRepresentation(final Establishment other) {
//        logger.debug("HotelVisualRepresentation : Establishment");
//    }

    //    public HotelVisualRepresentation(Establishment establishment, List<Inventory> inventories, List<Discount> discounts,
//                 List<Service> services, List<Language> languages, List<Currency> currencies, List<FrontEndMessage> frontEndMessages) {
//        super(establishment);
//        this.inventories = inventories;
//        this.discounts = discounts;
//        this.services = services;
//        this.languages = languages;
//        this.currencies = currencies;
//        this.frontEndMessages = frontEndMessages;
//    }

    @Override
    public String toString() {
        return super.toString() + Objects.toStringHelper(this)
                .add("languages", languages)
                .add("currencies", currencies)
                .add("discounts", discounts)
                .add("services", services)
                .add("frontEndMessages", frontEndMessages)
                .toString();
    }


//    @Override
//    public String toString() {
//        return Objects.toStringHelper(this)
//                .add("address", address)
//                .add("city", city)
//                .add("zone", zone)
//                .add("country", country)
//                .add("emailReservation", emailReservation)
//                .add("latitude", latitude)
//                .add("longitude", longitude)
//                .add("valuation", valuation)
//                .add("id", id)
//                .add("media", media)
//                .add("ticker", ticker)
//                .add("name", name)
//                .add("description", description)
//                .add("phone", phone)
//                .add("emailAdmin", emailAdmin)
//                .add("active", active)
//                .add("configuration", configuration)
//                .add("discounts", discounts)
//                .add("services", services)
//                .add("languages", languages)
//                .add("currencies", currencies)
//                .add("frontEndMessages", frontEndMessages)
//                .toString();
//    }


}