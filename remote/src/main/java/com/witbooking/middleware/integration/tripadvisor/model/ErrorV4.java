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
 * Represents any error that is sent back in a response
 *
 */
public class ErrorV4 implements Validatable
{
    private static final String NAME = "Error";
    public static final int UNKNOWN_ERROR = 1;
    public static final int CANNOT_PARSE_REQUEST = 2;
    public static final int INVALID_HOTEL_ID = 3;
    public static final int TIMEOUT_REQUESTED = 4;
    public static final int RECOVERABLE_ERROR = 5;


    @SerializedName("error_code")
    private final Integer m_errorCode;
    @SerializedName("timeout")
    private final Integer m_timeout;
    @SerializedName("hotel_ids")
    private final List<Integer> m_hotelIds;
    @SerializedName("message")
    private final String m_message;

    private ErrorV4(Builder builder)
    {
        m_errorCode = builder.m_errorCode;
        m_timeout   = builder.m_timeout;
        m_hotelIds  = HACUtils.createImmutableListOrNull(builder.m_hotelIds);
        m_message   = builder.m_message;
    }
    
    public static class Builder implements ValidatingBuilder<ErrorV4>
    {
        private Integer m_errorCode;
        private Integer m_timeout;
        private List<Integer> m_hotelIds;
        private String m_message;
     
        public Builder errorCode(Integer errorCode) 
        { 
            m_errorCode = errorCode;
            return this; 
        }
        
        public Builder timeout(Integer timeout)
        {
            m_timeout = timeout;
            return this; 
        }
        
        public Builder hotelIds(List<Integer> hotelIds) 
        { 
            m_hotelIds = hotelIds; 
            return this; 
        }
        
        public Builder message(String message)  
        { 
            m_message = message;
            return this; 
        }
        
        @Override
        public ErrorV4 buildWithoutValidation()
        {
            return new ErrorV4(this);
        }
        
        @Override
        public ErrorV4 build()
        {
            ErrorV4 error = new ErrorV4(this);
            List<String> validationErrors = error.validate();
            ValidationUtils.generateFailureMessage(validationErrors);
            return error;
        }        
    }

    public Integer getErrorCode()
    {
        return m_errorCode;
    }

    /**
     * @return timeout in seconds
     */
    public Integer getTimeout()
    {
        return m_timeout;
    }

    public boolean hasHotelIds()
    {
        return m_hotelIds != null && !m_hotelIds.isEmpty();
    }

    /**
     * @return HotelIds list, will return empty list if not specified
     */
    public List<Integer> getHotelIds()
    {
        return HACUtils.createEmptyListIfNull(m_hotelIds);
    }

    @Override
    public List<String> validate()
    {
        List<String> validationErrors = Lists.newArrayList();

        ValidationUtils.notNull("error_code", NAME, m_errorCode, validationErrors);
        ValidationUtils.inRange("error_code", NAME, m_errorCode, 1, 5, validationErrors);
        ValidationUtils.length("message", NAME, m_message, 0, 1000, validationErrors);

        return validationErrors;
    }

    public String errorMessage()
    {
        StringBuffer sb = new StringBuffer();
        sb.append("Error Code: ");
        sb.append(m_errorCode);
        sb.append(". Message: ");
        sb.append(m_message);
        sb.append(". Hotels: ");

        if (m_hotelIds != null)
        {
            for (Integer hotel : m_hotelIds)
            {
                sb.append(hotel);
                sb.append(" ");
            }
        }

        return sb.toString();
    }
    
    public String toJson()
    {
        return GSONV4.GSON.get().toJson(this);
    }

    public static ErrorV4 ErrorUnknown(){
        ErrorV4 error = new Builder().errorCode(ErrorV4.UNKNOWN_ERROR).message("Unknown Error").timeout(0).build();
        return error;
    }

    public static ErrorV4 ErrorVersionAPI(){
        ErrorV4 error = new Builder().errorCode(ErrorV4.CANNOT_PARSE_REQUEST).message("Version API Error.").timeout(0)
                .build
                ();
        return error;
    }

    public static ErrorV4 ErrorHotelListIsEmpty(){
        ErrorV4 error = new Builder().errorCode(ErrorV4.CANNOT_PARSE_REQUEST).message("Hotel List is Empty.").timeout
                (0)
                .build
                        ();
        return error;
    }

    public static String generateErrorResponse(Exception e) {
        // Make sure to internally log any exceptions to ensure that you can fix any errors you get
        ErrorV4.Builder responseBuilder = new ErrorV4.Builder();
        // can only send back an error message of maximum size 1000 characters, so make sure to substring in order to not throw another error
        e.printStackTrace();
        if (e.getMessage() != null) {
            responseBuilder.message(e.getMessage().substring(0, Math.min(1000, e.getMessage().length())));
        }

        return responseBuilder
                .errorCode(ErrorV4.UNKNOWN_ERROR)
                .build()
                .toJson();
    }

}
