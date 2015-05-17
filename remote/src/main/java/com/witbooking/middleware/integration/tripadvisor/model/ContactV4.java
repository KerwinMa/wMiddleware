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
 * Represents the contact information for a partner that is used in the {@link com.witbooking.middleware.integration.tripadvisor.model.ConfigurationV4}
 * 
 */
public class ContactV4 implements Validatable
{
    private static final String NAME = "Contact";

    @SerializedName("full_name")
    private final String m_fullName;
    @SerializedName("email")
    private final String m_email;
    @SerializedName("phone_number")
    private final String m_phoneNumber;

    private ContactV4(Builder builder)
    {
        m_fullName    = builder.m_fullName;
        m_email       = builder.m_email;
        m_phoneNumber = builder.m_phoneNumber;
    }
    
    public static class Builder implements ValidatingBuilder<ContactV4>
    {
        private String m_fullName;
        private String m_email;
        private String m_phoneNumber;
        
        public Builder fullName(String fullName)
        { 
            m_fullName = fullName;
            return this; 
        }
        
        public Builder email(String email)  
        { 
            m_email = email; 
            return this; 
        }
        
        public Builder phoneNumber(String phoneNumber)
        { 
            m_phoneNumber = phoneNumber; 
            return this; 
        }
        
        @Override
        public ContactV4 buildWithoutValidation()
        {
            return new ContactV4(this);
        }
        
        @Override
        public ContactV4 build()
        {
            ContactV4 contact = new ContactV4(this);
            List<String> validationErrors = contact.validate();
            ValidationUtils.generateFailureMessage(validationErrors);
            return contact;
        }        
    }

    public String getFullName()
    {
        return m_fullName;
    }

    public String getEmail()
    {
        return m_email;
    }

    public String getPhoneNumber()
    {
        return m_phoneNumber;
    }

    @Override
    public List<String> validate()
    {
        List<String> validationErrors = Lists.newArrayList();

        ValidationUtils.notNull("full_name", NAME, m_fullName, validationErrors);
        ValidationUtils.notNull("email", NAME, m_email, validationErrors);
        ValidationUtils.length("email", NAME, m_email, 0, 256, validationErrors);
        ValidationUtils.notNull("phone_number", NAME, m_phoneNumber, validationErrors);
        ValidationUtils.length("phone_number", NAME, m_phoneNumber, 0, 50, validationErrors);

        return validationErrors;
    }
}