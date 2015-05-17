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
 * This class is used to fill the hotels array in the {@link AvailabilityResponseV4}
 * 
 */
public class AvailabilityHotelV4 implements Validatable
{
    private static final String NAME = "AvailabilityHotel";

    @SerializedName("hotel_id")
    private final Integer m_hotelId;
    @SerializedName("room_types")
    private final Map<String, AvailabilityRoomTypeV4> m_roomTypes;

    private AvailabilityHotelV4(Builder builder) 
    {
        m_hotelId = builder.m_hotelId;
        m_roomTypes = HACUtils
                .createImmutableMapOrNull(builder.m_roomTypes);
    }

    public static class Builder implements ValidatingBuilder<AvailabilityHotelV4>
    {
        private Integer m_hotelId;
        private Map<String, AvailabilityRoomTypeV4> m_roomTypes;

        public Builder hotelId(Integer hotelId) 
        {
            m_hotelId = hotelId;
            return this;
        }

        public Builder roomTypes(Map<String, AvailabilityRoomTypeV4> roomTypes) 
        {
            m_roomTypes = roomTypes;
            return this;
        }

        @Override
        public AvailabilityHotelV4 buildWithoutValidation() 
        {
            return new AvailabilityHotelV4(this);
        }
        
        @Override
        public AvailabilityHotelV4 build() 
        {
            AvailabilityHotelV4 availabilityHotel = new AvailabilityHotelV4(this);
            List<String> validationErrors = availabilityHotel.validate();
            ValidationUtils.generateFailureMessage(validationErrors);
            return availabilityHotel;
        }
    }

    /**
     * @return RoomTypes map, will return empty map if not specified
     */
    public Map<String, AvailabilityRoomTypeV4> getRoomTypes() 
    {
        return HACUtils.createEmptyMapIfNull(m_roomTypes);
    }

    public Integer getHotelId() 
    {
        return m_hotelId;
    }

    @Override
    public List<String> validate() 
    {
        List<String> validationErrors = Lists.newArrayList();
        ValidationUtils.notNull("hotel_id", NAME, m_hotelId,
                validationErrors);
        ValidationUtils.notNull("room_types", NAME, m_roomTypes,
                validationErrors);
        ValidationUtils.notEmptyMap("room_types", NAME, m_roomTypes,
                validationErrors);

        for (String roomTypeDescription : getRoomTypes().keySet()) 
        {
            ValidationUtils.length("room_type_short_desc", NAME,
                    roomTypeDescription, 0, 100, validationErrors);
        }

        for (AvailabilityRoomTypeV4 roomType : getRoomTypes().values()) 
        {
            validationErrors.addAll(roomType.validate());
        }

        return validationErrors;
    }
}
