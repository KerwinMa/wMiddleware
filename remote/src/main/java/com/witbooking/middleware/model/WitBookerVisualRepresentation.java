package com.witbooking.middleware.model;

import com.google.common.base.Objects;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * WitBookerVisualRepresentation.java
 * User: jose
 * Date: 2/24/14
 * Time: 10:40 AM
 */
public class WitBookerVisualRepresentation extends HotelVisualRepresentation {

    private static final Logger logger = Logger.getLogger(WitBookerVisualRepresentation.class);
    protected List<Inventory> inventories;
    protected TransferData transferData;
    protected Map<String, String> creditCardsAllowed;
    protected Map<Markup.Phase,List<Markup>>  markups;

    public WitBookerVisualRepresentation(List<Language> languages, List<Currency> currencies, List<Discount> discounts,
                                         List<Service> services, List<FrontEndMessage> frontEndMessages,
                                         List<Inventory> inventories, Map<String, String> creditCardsAllowed, TransferData transferData,Map<Markup.Phase,List<Markup>> markups) {
        super(languages, currencies, discounts, services, frontEndMessages);
        this.inventories = inventories;
        this.creditCardsAllowed = creditCardsAllowed;
        this.transferData = transferData;
        this.markups = markups;
    }

    @Override
    public String toString() {
        return super.toString() + Objects.toStringHelper(this)
                .add("creditCardsAllowed", creditCardsAllowed)
                .add("transferData", transferData)
                .add("inventories", inventories)
                .toString();
    }
}