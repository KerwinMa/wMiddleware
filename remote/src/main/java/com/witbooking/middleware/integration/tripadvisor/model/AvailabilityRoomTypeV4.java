/*
 * Copyright (C) 2013 Tripadvisor LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.witbooking.middleware.integration.tripadvisor.model;

import com.google.common.collect.Lists;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.List;

/**
 * This class is used to fill the AvailabilityRoomType object in the {@link com.witbooking.middleware.integration.tripadvisor.model.AvailabilityHotelV4} object
 * All instances of this class should be place in a map that maps "description strings" to AvailabilityRoomType objects
 *
 */
public class AvailabilityRoomTypeV4 implements Validatable
{
    private static final String NAME = "AvailabilityRoomType";

    // This should be more than close enough. Some providers seem to round off their responses.
    private static final float ACCCEPTABLE_ERROR_PERCENTAGE = .01f;

    @SerializedName("url") 
    private final String m_url;
    @SerializedName("price")
    private final BigDecimal m_price;
    @SerializedName("fees")
    private final BigDecimal m_fees;
    @SerializedName("fees_at_checkout")
    private final BigDecimal m_feesAtCheckout;
    @SerializedName("taxes")
    private final BigDecimal m_taxes;
    @SerializedName("taxes_at_checkout")
    private final BigDecimal m_taxesAtCheckout;
    @SerializedName("final_price")
    private final BigDecimal m_finalPrice;
    @SerializedName("discounts")
    private final List<RoomDiscountV4> m_discounts;
    @SerializedName("currency")
    private final String m_currency;
    @SerializedName("num_rooms")
    private final Integer m_numRooms;
    @SerializedName("room_code")
    private final String m_roomCode;
    @SerializedName("room_amenities")
    private final List<String> m_roomAmenities;

    private AvailabilityRoomTypeV4(Builder builder)
    {
        m_url             = builder.m_url;
        m_price           = builder.m_price;
        m_fees            = builder.m_fees;
        m_feesAtCheckout  = builder.m_feesAtCheckout;
        m_taxes           = builder.m_taxes;
        m_taxesAtCheckout = builder.m_taxesAtCheckout;
        m_finalPrice      = builder.m_finalPrice;
        m_discounts       = HACUtils.createImmutableListOrNull(builder.m_discounts);
        m_currency        = builder.m_currency.getCurrencyCode();
        m_numRooms        = builder.m_numRooms;
        m_roomCode        = builder.m_roomCode;
        m_roomAmenities   = HACUtils.createImmutableListOrNull(builder.m_roomAmenities);
    }

    public static class Builder implements ValidatingBuilder<AvailabilityRoomTypeV4>
    {
        private String m_url;
        private BigDecimal m_price;
        private BigDecimal m_fees;
        private BigDecimal m_feesAtCheckout;
        private BigDecimal m_taxes;
        private BigDecimal m_taxesAtCheckout;
        private BigDecimal m_finalPrice;
        private List<RoomDiscountV4> m_discounts;
        private Currency m_currency;
        private Integer m_numRooms;
        private String m_roomCode;
        private List<String> m_roomAmenities;

        public Builder url(String url)
        {
            m_url = url;
            return this;
        }
        
        public Builder price(BigDecimal price)
        {
            m_price = price;
            return this;
        }
        
        public Builder fees(BigDecimal fees) 
        {
            m_fees = fees;
            return this;
        }
        
        public Builder feesAtCheckout(BigDecimal feesAtCheckout)
        {
            m_feesAtCheckout = feesAtCheckout;
            return this;
        }
        
        public Builder taxes(BigDecimal taxes) 
        {
            m_taxes = taxes;
            return this;
        }
        
        public Builder taxesAtCheckout(BigDecimal taxesAtCheckout) 
        {
            m_taxesAtCheckout = taxesAtCheckout;
            return this;
        }
        
        public Builder finalPrice(BigDecimal finalPrice)
        {
            m_finalPrice = finalPrice;
            return this;
        }
        
        public Builder discounts(List<RoomDiscountV4> discounts)
        {
            m_discounts = discounts;
            return this;
        }
        
        public Builder currency(Currency currency) 
        {
            m_currency = currency;  
            return this;
        }
        
        public Builder numRooms(Integer numRooms)     
        {
            m_numRooms = numRooms;
            return this;
        }
        
        public Builder roomCode(String roomCode)   
        {
            m_roomCode = roomCode;
            return this;
        }
        
        public Builder roomAmenities(List<String> roomAmenities)
        {
            m_roomAmenities = roomAmenities;
            return this;
        }

        @Override
        public AvailabilityRoomTypeV4 buildWithoutValidation()
        {
            return new AvailabilityRoomTypeV4(this);
        }
        
        @Override
        public AvailabilityRoomTypeV4 build()
        {
            AvailabilityRoomTypeV4 roomType = new AvailabilityRoomTypeV4(this);
            List<String> validationErrors = roomType.validate();
            ValidationUtils.generateFailureMessage(validationErrors);
            return roomType;
        }
    }

    public String getUrl()
    {
        return m_url;
    }

    public BigDecimal getPrice()
    {
        return m_price;
    }

    public BigDecimal getFees()
    {
        return m_fees;
    }

    public BigDecimal getFeesAtCheckout()
    {
        return m_feesAtCheckout;
    }

    public BigDecimal getTaxes()
    {
        return m_taxes;
    }

    public BigDecimal getTaxesAtCheckout()
    {
        return m_taxesAtCheckout;
    }

    public BigDecimal getFinalPrice()
    {
        return m_finalPrice;
    }

    /**
     * @return RoomDiscount list, will return empty list if not specified
     */
    public List<RoomDiscountV4> getDiscounts()
    {
        return HACUtils.createEmptyListIfNull(m_discounts);
    }

    public Currency getCurrency()
    {
        return Currency.getInstance(m_currency);
    }

    public Integer getNumRooms()
    {
        return m_numRooms;
    }

    public String getRoomCode()
    {
        return m_roomCode;
    }

    /**
     * @return RoomAmenities list, will return empty list if not specified
     */
    public List<String> getRoomAmenities()
    {
        return HACUtils.createEmptyListIfNull(m_roomAmenities);
    }

    public List<String> validate()
    {
        List<String> validationErrors = Lists.newArrayList();

//        ValidationUtils.length("url", NAME, m_url, 0, 2000, validationErrors);
        ValidationUtils.notNull("price", NAME, m_price, validationErrors);
        //ValidationUtils.notZero("price", NAME, m_price, validationErrors);
        ValidationUtils.notNull("taxes", NAME, m_taxes, validationErrors);
        ValidationUtils.notNull("taxes_at_checkout", NAME, m_taxesAtCheckout, validationErrors);
        ValidationUtils.notNull("fees", NAME, m_fees, validationErrors);
        ValidationUtils.notNull("fees_at_checkout", NAME, m_feesAtCheckout, validationErrors);
        ValidationUtils.notNull("final_price", NAME, m_finalPrice, validationErrors);
        //ValidationUtils.notZero("final_price", NAME, m_finalPrice, validationErrors);
        ValidationUtils.notNull("currency", NAME, m_currency, validationErrors);
        ValidationUtils.notNull("num_rooms", NAME, m_numRooms, validationErrors);
        ValidationUtils.inRange("num_rooms", NAME, m_numRooms, 1, 10, validationErrors);

        BigDecimal nSumEverything = m_price.add(m_taxes).add(m_taxesAtCheckout).add(m_fees).add(m_feesAtCheckout);

        //use the percentage error calculation to find if it is close enough
        if (nSumEverything.subtract(m_finalPrice).abs().floatValue() > ACCCEPTABLE_ERROR_PERCENTAGE * m_finalPrice.floatValue() )
        {
            validationErrors.add("final price (" + m_finalPrice + ") is not equal to the sum of price (" + m_price +  "), fees (" + m_fees + "), fees_at_checkout (" + m_feesAtCheckout + "), taxes (" + m_taxes + "), and taxes_at_checkout (" + m_taxesAtCheckout +  "), which equals: (" + nSumEverything + ")");
        }

        for (RoomDiscountV4 discount : getDiscounts())
        {
            validationErrors.addAll(discount.validate());
        }

        return validationErrors;
    }
}
