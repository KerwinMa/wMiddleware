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

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to decode the HotelAvailabilityRequest hotel objects into a java class
 * Can be used to craft test requests
 */
public class AvailabilityRequestHotelV4 
{

    @SerializedName("ta_id")
    private final Integer m_taId;
    @SerializedName("partner_id")
    private final String m_partnerId;
    @SerializedName("partner_url")
    private final String m_partnerUrl;
 

    private AvailabilityRequestHotelV4(Builder builder)
    {
        m_taId       = builder.m_taId;
        m_partnerId  = builder.m_partnerId;
        m_partnerUrl = builder.m_partnerUrl;
    }

    public static class Builder
    {
        private Integer m_taId;
        private String  m_partnerId;
        private String  m_partnerUrl;

        public Builder taId(Integer taId)
        {
            m_taId = taId;
            return this;
        }
        
        public Builder partnerId(String partnerId)
        {
            m_partnerId = partnerId;
            return this;
        }
        
        public Builder partnerUrl(String partnerUrl)
        {
            m_partnerUrl = partnerUrl;
            return this;
        }
        
        public AvailabilityRequestHotelV4 buildWithoutValidation() 
        {
            return new AvailabilityRequestHotelV4(this);
        }
    }
    
    public Integer getTaId()
    {
        return m_taId;
    }
    
    public String getPartnerId()
    {
        return m_partnerId;
    }
    
    public String getPartnerUrl()
    {
        return m_partnerUrl;
    }
}
