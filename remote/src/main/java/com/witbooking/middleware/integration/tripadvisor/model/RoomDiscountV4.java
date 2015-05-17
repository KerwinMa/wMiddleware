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
import java.util.List;

/**
 * Represents the discount applied to the Price of a specific RoomType
 * Used in the {@link com.witbooking.middleware.integration.tripadvisor.model.AvailabilityRoomTypeV4} objects
 *
 */
public class RoomDiscountV4 implements Validatable
{
    private static final String NAME = "Discount";
    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(RoomDiscountV4.class);

    @SerializedName("marketing_text")
    private final String m_marketingText;    
    @SerializedName("is_percent")
    private final Boolean m_isPercent;
    @SerializedName("amount")
    private final BigDecimal m_amount;    
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

    private RoomDiscountV4(Builder builder) 
    {
        m_marketingText    = builder.m_marketingText;   
        m_isPercent        = builder.m_isPercent; 
        m_amount           = builder.m_amount;    
        m_price            = builder.m_price;  
        m_fees             = builder.m_fees;   
        m_feesAtCheckout   = builder.m_feesAtCheckout;   
        m_taxes            = builder.m_taxes;
        m_taxesAtCheckout  = builder.m_taxesAtCheckout; 
        m_finalPrice       = builder.m_finalPrice;
    }
    
    public static class Builder implements ValidatingBuilder<RoomDiscountV4>
    {
        private String m_marketingText;
        private Boolean m_isPercent;
        private BigDecimal m_amount;
        private BigDecimal m_price;
        private BigDecimal m_fees;
        private BigDecimal m_feesAtCheckout;
        private BigDecimal m_taxes;
        private BigDecimal m_taxesAtCheckout;
        private BigDecimal m_finalPrice;

        public Builder marketingText(String marketingText)
        {
            m_marketingText   = marketingText;
            return this; 
        }
        
        public Builder isPercent(Boolean isPercent)  
        { 
            m_isPercent = isPercent; 
            return this; 
        }
        
        public Builder amount(BigDecimal amount)
        { 
            m_amount = amount; 
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
        
        @Override
        public RoomDiscountV4 buildWithoutValidation()
        {
            return new RoomDiscountV4(this);
        }
        
        @Override
        public RoomDiscountV4 build()
        {
            RoomDiscountV4 configuration = new RoomDiscountV4(this);
            List<String> validationErrors = configuration.validate();
            ValidationUtils.generateFailureMessage(validationErrors);
            return configuration;
        }        
    }

    public String getMarketingText()
    {
        return m_marketingText;
    }

    public Boolean getIsPercent()
    {
        return m_isPercent;
    }

    public BigDecimal getAmount()
    {
        return m_amount;
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

    @Override
    public List<String> validate()
    {
        List<String> validationErrors = Lists.newArrayList();

        ValidationUtils.notNull("is_percent", NAME, m_isPercent, validationErrors);
        ValidationUtils.notNull("amount", NAME, m_amount, validationErrors);
        ValidationUtils.notNull("price", NAME, m_price, validationErrors);
        ValidationUtils.notNull("fees", NAME, m_fees, validationErrors);
        ValidationUtils.notNull("fees_at_checkout", NAME, m_feesAtCheckout, validationErrors);
        ValidationUtils.notNull("taxes", NAME, m_taxes, validationErrors);
        ValidationUtils.notNull("taxes_at_checkout", NAME, m_taxesAtCheckout, validationErrors);
        ValidationUtils.notNull("final_price", NAME, m_finalPrice, validationErrors);
        return validationErrors;
    }
}