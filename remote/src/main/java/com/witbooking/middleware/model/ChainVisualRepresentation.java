package com.witbooking.middleware.model;

import java.util.List;
import java.util.Map;

/**
 * ChainVisualRepresentation.java
 * User: jose
 * Date: 2/24/14
 * Time: 2:10 PM
 */
public class ChainVisualRepresentation extends VisualRepresentation {
    protected Map<Markup.Phase,List<Markup>>  markups;

    public ChainVisualRepresentation(List<Language> languages, List<Currency> currencies, List<FrontEndMessage> frontEndMessages, List<Discount> discounts,Map<Markup.Phase,List<Markup>> markups) {
        super(languages, currencies, frontEndMessages, discounts);
        this.markups=markups;
    }
}