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
 * This class represents the response to a configuration request
 * Use toJson to serialize into the correct format for the response to be sent
 *
 */
public class ConfigurationResponseV4 implements Validatable
{
    private static final String NAME = "ConfigurationResponse";

    @SerializedName("api_version")
    private final Integer m_apiVersion = Versions.VERSION_4;
    @SerializedName("configuration")
    private final ConfigurationV4 m_configuration;
    @SerializedName("debug_info")
    private final String m_debugInfo;

    private ConfigurationResponseV4(Builder builder)
    {
        m_configuration = builder.m_configuration;
        m_debugInfo     = builder.m_debugInfo;
    }
    
    public static class Builder implements ValidatingBuilder<ConfigurationResponseV4>
    {
        private ConfigurationV4 m_configuration;
        private String m_debugInfo;
        
        public Builder configuration(ConfigurationV4 configuration) 
        { 
            m_configuration = configuration; 
            return this; 
        }
        
        public Builder debugInfo(String sDebugInfo)  
        { 
            m_debugInfo = sDebugInfo;
            return this; 
        }       
        
        @Override
        public ConfigurationResponseV4 buildWithoutValidation() 
        {
            return new ConfigurationResponseV4(this);
        }
        
        @Override
        public ConfigurationResponseV4 build()
        {
            ConfigurationResponseV4 configurationResponse = new ConfigurationResponseV4(this);
            List<String> validationErrors = configurationResponse.validate();
            ValidationUtils.generateFailureMessage(validationErrors);
            return configurationResponse;
        }
    }

    public Integer getApiVersion()
    {
        return m_apiVersion;
    }

    public String getDebugInfo()
    {
        return m_debugInfo;
    }

    public ConfigurationV4 getConfiguration()
    {
        return m_configuration;
    }

    @Override
    public List<String> validate()
    {
        List<String> validationErrors = Lists.newArrayList();

        ValidationUtils.notNull("api_version", NAME, m_apiVersion, validationErrors);
        ValidationUtils.notNull("configuration", NAME, m_configuration, validationErrors);
        validationErrors.addAll(m_configuration.validate());

        return validationErrors;
    }

    public String toJson() 
    {
        return GSONV4.GSON.get().toJson(this);
    }

    public static ConfigurationResponseV4 fromJson(String sJsonString) 
    {
        return GSONV4.GSON.get().fromJson(sJsonString, ConfigurationResponseV4.class); 
    }
}