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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Helper class for JSON serialization/de-serialization.
 *
 */
public class GSONV4
{
    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final ThreadLocal<Gson> GSON = new ThreadLocal<Gson>()
    {
        @Override
        public Gson initialValue()
        {
            return new GsonBuilder()
                .setVersion(Versions.VERSION_4)
                .setDateFormat(DATE_FORMAT)
                .create();
        }
    };
}
