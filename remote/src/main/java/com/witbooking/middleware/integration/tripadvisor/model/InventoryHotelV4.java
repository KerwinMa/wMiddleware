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
import java.util.Map;

/**
 * This class represents a hotel that is used in the "hotels" field of a @link {@link com.witbooking.middleware.integration.tripadvisor.model.InventoryResponseV4}
 *
 */
public class InventoryHotelV4 implements Validatable
{
    private static final String NAME = "InventoryHotel";
    
    @SerializedName("ta_id")
    private final Integer m_taId;
    @SerializedName("partner_id")
    private final String m_partnerId;
    @SerializedName("name")
    private final String m_name;
    @SerializedName("street")
    private final String m_street;
    @SerializedName("city")
    private final String m_city;
    @SerializedName("postal_code")
    private final String m_postalCode;
    @SerializedName("state")
    private final String m_state;
    @SerializedName("country")
    private final String m_country;
    @SerializedName("url")
    private final String m_url;
    @SerializedName("latitude")
    private final Float m_latitude;
    @SerializedName("longitude")
    private final Float m_longitude;
    @SerializedName("desc")
    private final String m_desc;
    @SerializedName("email")
    private final String m_email;
    @SerializedName("phone")
    private final String m_phone;
    @SerializedName("fax")
    private final String m_fax;

    @SerializedName("amenities")
    private final List<String> m_amenities;
    @SerializedName("room_types")
    private final Map<String, InventoryRoomTypeV4> m_roomTypes;

    public InventoryHotelV4(Builder builder)
    {
        m_taId       = builder.m_taId;
        m_partnerId  = builder.m_partnerId;
        m_name       = builder.m_name;
        m_street     = builder.m_street;
        m_city       = builder.m_city;
        m_postalCode = builder.m_postalCode;
        m_state      = builder.m_state;
        m_country    = builder.m_country;
        m_url        = builder.m_url;
        m_latitude   = builder.m_latitude;
        m_longitude  = builder.m_longitude;
        m_desc       = builder.m_desc;
        m_email      = builder.m_email;
        m_phone      = builder.m_phone;
        m_fax        = builder.m_fax;
        m_amenities  = HACUtils.createImmutableListOrNull(builder.m_amenities);
        m_roomTypes = HACUtils.createImmutableMapOrNull(builder.m_roomTypes);        
    }
    
    public static class Builder implements ValidatingBuilder<InventoryHotelV4>
    {
        private Integer m_taId;
        private String m_partnerId;
        private String m_name;
        private String m_street;
        private String m_city;
        private String m_postalCode;
        private String m_state;
        private String m_country;
        private String m_url;
        private Float m_latitude;
        private Float m_longitude;
        private String m_desc;
        private String m_email;
        private String m_phone;
        private String m_fax;
        private List<String> m_amenities;
        private Map<String, InventoryRoomTypeV4> m_roomTypes;
        
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
        
        public Builder name(String name)   
        { 
            m_name = name; 
            return this; 
        }
        
        public Builder street(String street)  
        { 
            m_street = street; 
            return this; 
        }
        
        public Builder city(String city)    
        { 
            m_city = city;
            return this; 
        }
        
        public Builder postalCode(String postalCode)
        { 
            m_postalCode = postalCode; 
            return this; 
        }
        
        public Builder state(String state)  
        { 
            m_state = state; 
            return this; 
        }
        
        public Builder country(String country)      
        {
            m_country = country; 
            return this;
        }
        
        public Builder url(String url) 
        {
            m_url = url; 
            return this; 
        }
        
        public Builder latitude(Float latitude)     
        { 
            m_latitude = latitude; 
            return this;
        }
        
        public Builder longitude(Float longitude) 
        { 
            m_longitude = longitude;
            return this; 
        }
        
        public Builder desc(String desc)
        { 
            m_desc = desc; 
            return this; 
        }
        
        public Builder email(String email) 
        { 
            m_email = email;
            return this; 
        }
        
        public Builder phone(String phone)
        { 
            m_phone = phone;
            return this; 
        }
        
        public Builder fax(String fax)
        {
            m_fax = fax; 
            return this;
        } 
        
        public Builder amenities(List<String> amenities)
        { 
            m_amenities = amenities;
            return this; 
        }
        
        public Builder roomTypes(Map<String, InventoryRoomTypeV4> roomTypes)
        { 
            m_roomTypes = roomTypes; 
            return this; 
        }
        
        @Override
        public InventoryHotelV4 buildWithoutValidation()
        {
            return new InventoryHotelV4(this);
        }
        
        @Override
        public InventoryHotelV4 build()
        {
            InventoryHotelV4 inventoryHotel = new InventoryHotelV4(this);
            List<String> validationErrors = inventoryHotel.validate();
            ValidationUtils.generateFailureMessage(validationErrors);
            return inventoryHotel;
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

    public String getName()
    {
        return m_name;
    }

    public String getStreet()
    {
        return m_street;
    }

    public String getCity()
    {
        return m_city;
    }

    public String getPostalCode()
    {
        return m_postalCode;
    }

    public String getState()
    {
        return m_state;
    }

    public String getCountry()
    {
        return m_country;
    }

    public String getUrl()
    {
        return m_url;
    }

    public Float getLatitude()
    {
        return m_latitude;
    }

    public Float getLongitude()
    {
        return m_longitude;
    }

    public String getDesc()
    {
        return m_desc;
    }

    public String getEmail()
    {
        return m_email;
    }
    
    public String getPhone()
    {
        return m_phone;
    }

    public String getFax()
    {
        return m_fax;
    }
    
    /**
     * @return Amenities list, will return empty list if not specified
     */
    public List<String> getAmenities()
    {
        return HACUtils.createEmptyListIfNull(m_amenities);
    }
    
    /**
     * @return RoomTypes map, will return empty map if not specified
     */
    public Map<String, InventoryRoomTypeV4> getRoomTypes()
    {
        return HACUtils.createEmptyMapIfNull(m_roomTypes);
    }

    public List<String> validate()
    {
        List<String> validationErrors = Lists.newArrayList();

        ValidationUtils.notNull("partner_id", NAME, m_partnerId, validationErrors);
        ValidationUtils.length("partner_id", NAME, m_partnerId, 0, 30, validationErrors);
        ValidationUtils.notNull("name", NAME, m_name, validationErrors);
        ValidationUtils.length("name", NAME, m_name, 0, 300, validationErrors);
        ValidationUtils.notNull("street", NAME, m_street, validationErrors);
        ValidationUtils.length("street", NAME, m_street, 0, 300, validationErrors);
        ValidationUtils.notNull("city", NAME, m_city, validationErrors);
        ValidationUtils.length("city", NAME, m_city, 0, 100, validationErrors);
        ValidationUtils.length("postal_code", NAME, m_postalCode, 0, 20, validationErrors);
        ValidationUtils.length("state", NAME, m_state, 0, 100, validationErrors);
        ValidationUtils.notNull("country", NAME, m_country, validationErrors);
        ValidationUtils.length("country", NAME, m_country, 0, 100, validationErrors);
        ValidationUtils.length("desc", NAME, m_desc, 0, 1000, validationErrors);
        ValidationUtils.length("url", NAME, m_url, 0, 2000, validationErrors);
        ValidationUtils.length("email", NAME, m_email, 0, 256, validationErrors);
        ValidationUtils.length("phone", NAME, m_email, 0, 50, validationErrors);
        ValidationUtils.length("fax", NAME, m_email, 0, 50, validationErrors);

        for (String sRoomTypeDescription : getRoomTypes().keySet())
        {
            ValidationUtils.length("room_type_short_desc", NAME, sRoomTypeDescription, 0, 100, validationErrors);
        }

        for (InventoryRoomTypeV4 roomType : getRoomTypes().values())
        {
            if (roomType != null)
            {
                validationErrors.addAll(roomType.validate());
            }
            ValidationUtils.notNull("room_type", NAME, roomType, validationErrors);
        }
        
        return validationErrors;
    }
}