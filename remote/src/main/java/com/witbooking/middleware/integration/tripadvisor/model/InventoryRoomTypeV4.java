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
 * This class represents a room type used in the {@link InventoryHotelV4} objects
 * 
 */
public class InventoryRoomTypeV4 implements Validatable
{
    private static final String NAME = "InventoryRoomType";

    @SerializedName("url")
    private final String m_url;
    @SerializedName("desc")
    private final String m_desc;

    public InventoryRoomTypeV4(Builder builder)
    {
        m_url  = builder.m_url;
        m_desc = builder.m_desc;
    }
    
    public static class Builder implements ValidatingBuilder<InventoryRoomTypeV4>
    {
        private String m_url;
        private String m_desc;

        public Builder url(String url)
        { 
            m_url  = url; 
            return this; 
        }
        
        public Builder desc(String desc) 
        { 
            m_desc = desc; 
            return this; 
        }        

        @Override
        public InventoryRoomTypeV4 buildWithoutValidation() 
        {
            return new InventoryRoomTypeV4(this);
        }    
        
        @Override
        public InventoryRoomTypeV4 build()
        {
            InventoryRoomTypeV4 roomType = new InventoryRoomTypeV4(this);
            List<String> validationErrors = roomType.validate();
            ValidationUtils.generateFailureMessage(validationErrors);
            return roomType;
        }
    }

    public String getUrl()
    {
        return m_url;
    }

    public String getDesc()
    {
        return m_desc;
    }

    @Override
    public List<String> validate()
    {
        List<String> validationErrors = Lists.newArrayList();
        ValidationUtils.length("url", NAME, m_url, 0, 2000, validationErrors);
        ValidationUtils.length("desc", NAME, m_desc, 0, 1000, validationErrors);

        return validationErrors;
    }

}
