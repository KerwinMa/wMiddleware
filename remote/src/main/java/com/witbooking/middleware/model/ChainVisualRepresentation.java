package com.witbooking.middleware.model;

import java.util.List;

/**
 * ChainVisualRepresentation.java
 * User: jose
 * Date: 2/24/14
 * Time: 2:10 PM
 */
public class ChainVisualRepresentation extends VisualRepresentation {

    public ChainVisualRepresentation(List<Language> languages, List<Currency> currencies, List<FrontEndMessage> frontEndMessages, List<Discount> discounts) {
        super(languages, currencies, frontEndMessages, discounts);
    }
}