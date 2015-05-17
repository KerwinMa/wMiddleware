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

import java.util.List;

/**
 * This class will hold the majority of the information send back in the @see {@link com.witbooking.middleware.integration.tripadvisor.model.ConfigurationResponseV4}
 *
 */
public class ConfigurationV4 implements Validatable
{
    private static final String NAME = "Configuration";

    @SerializedName("emergency_contacts")
    private final List<ContactV4> m_emergencyContacts;
    @SerializedName("info_contacts")
    private final List<ContactV4> m_infoContacts;
    @SerializedName("languages")
    private final List<String> m_languages;
    @SerializedName("pref_hotels")
    private final Integer m_prefHotels;
    @SerializedName("five_min_rate_limit")
    private final Integer m_fiveMinuteRateLimit;

    private ConfigurationV4(Builder builder)
    {
        m_emergencyContacts   = HACUtils.createImmutableListOrNull(builder.m_emergencyContacts);
        m_infoContacts        = HACUtils.createImmutableListOrNull(builder.m_infoContacts);
        m_languages           = HACUtils.createImmutableListOrNull(builder.m_languages);
        m_prefHotels          = builder.m_prefHotels;
        m_fiveMinuteRateLimit = builder.m_fiveMinuteRateLimit;
    }
    
    public static class Builder implements ValidatingBuilder<ConfigurationV4>
    {
        private List<ContactV4> m_emergencyContacts;
        private List<ContactV4> m_infoContacts;
        private List<String> m_languages;
        private Integer m_prefHotels;
        private Integer m_fiveMinuteRateLimit;
        
        public Builder emergencyContacts(List<ContactV4> emergencyContacts)
        {
            m_emergencyContacts   = emergencyContacts; 
            return this; 
        }
        
        public Builder infoContacts(List<ContactV4> infoContacts)   
        { 
            m_infoContacts = infoContacts; 
            return this; 
        }
        
        public Builder languages(List<String> languages) 
        {
            m_languages = languages;
            return this; 
        }
        
        public Builder prefHotels(Integer prefHotels)  
        {
            m_prefHotels = prefHotels; 
            return this; 
        }
        
        public Builder fiveMinuteRateLimit(Integer fiveMinuteRateLimit) 
        { 
            m_fiveMinuteRateLimit = fiveMinuteRateLimit;
            return this;
        }
        
        @Override
        public ConfigurationV4 buildWithoutValidation()
        {
            return new ConfigurationV4(this);
        }
        
        @Override
        public ConfigurationV4 build()
        {
            ConfigurationV4 configuration = new ConfigurationV4(this);
            List<String> validationErrors = configuration.validate();
            ValidationUtils.generateFailureMessage(validationErrors);
            return configuration;
        }        
    }
    
    /**
     * @return Languages list, will return empty list if not specified
     */
    public List<String> getLanguages()
    {
        return HACUtils.createEmptyListIfNull(m_languages);
    }

    public Integer getPrefHotels()
    {
        return m_prefHotels;
    }
    
    /**
     * @return EmergencyContacts list, will return empty list if not specified
     */
    public List<ContactV4> getEmergencyContacts()
    {
        return HACUtils.createEmptyListIfNull(m_emergencyContacts);
    }
    
    /**
     * @return InfoContacts list, will return empty list if not specified
     */
    public List<ContactV4> getInfoContacts()
    {
        return HACUtils.createEmptyListIfNull(m_infoContacts);
    }

    public Integer getFiveMinuteRateLimit()
    {
        return m_fiveMinuteRateLimit;
    }
   
    public List<String> validate()
    {
        List<String> validationErrors = Lists.newArrayList();

        ValidationUtils.notNull("emergency_contacts", NAME, m_emergencyContacts, validationErrors);
        ValidationUtils.notNull("info_contacts", NAME, m_infoContacts, validationErrors);
 
        for (ContactV4 contact : getEmergencyContacts())
        {
            validationErrors.addAll(contact.validate());
        }

        for (ContactV4 contact : getInfoContacts())
        {
            validationErrors.addAll(contact.validate());
        }

        return validationErrors;
    }
}
