package com.witbooking.middleware.model;

import java.io.Serializable;
import java.util.List;

/**
 * VisualRepresentation.java
 * User: jose
 * Date: 3/6/14
 * Time: 5:24 PM
 */
public abstract class VisualRepresentation implements Serializable {
    protected List<Language> languages;
    protected List<Currency> currencies;
    protected List<FrontEndMessage> frontEndMessages;
    protected List<Discount> discounts;

    protected VisualRepresentation(VisualRepresentation other) {
        if (other != null) {
            languages = other.languages;
            currencies = other.currencies;
            frontEndMessages = other.frontEndMessages;
            discounts = other.discounts;
        }
    }

    protected VisualRepresentation(List<Language> languages, List<Currency> currencies, List<FrontEndMessage> frontEndMessages, List<Discount> discounts) {
        this.languages = languages;
        this.currencies = currencies;
        this.frontEndMessages = frontEndMessages;
        this.discounts = discounts;
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

    public List<FrontEndMessage> getFrontEndMessages() {
        return frontEndMessages;
    }

    public List<Discount> getDiscounts() {
        return discounts;
    }

    public void setDiscounts(List<Discount> discounts) {
        this.discounts = discounts;
    }

    public void setFrontEndMessages(List<FrontEndMessage> frontEndMessages) {
        this.frontEndMessages = frontEndMessages;
    }

    @Override
    public String toString() {
        return "VisualRepresentation{" +
                "languages=" + languages +
                ", currencies=" + currencies +
                ", frontEndMessages=" + frontEndMessages +
                ", discounts=" + discounts +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VisualRepresentation)) return false;

        VisualRepresentation that = (VisualRepresentation) o;

        if (currencies != null ? !currencies.equals(that.currencies) : that.currencies != null) return false;
        if (frontEndMessages != null ? !frontEndMessages.equals(that.frontEndMessages) : that.frontEndMessages != null)
            return false;
        if (languages != null ? !languages.equals(that.languages) : that.languages != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = languages != null ? languages.hashCode() : 0;
        result = 31 * result + (currencies != null ? currencies.hashCode() : 0);
        result = 31 * result + (frontEndMessages != null ? frontEndMessages.hashCode() : 0);
        return result;
    }
}