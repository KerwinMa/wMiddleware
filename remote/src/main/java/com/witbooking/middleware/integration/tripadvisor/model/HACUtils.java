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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

/*
 * These functions allows us to internally use nulls for empty and null lists and maps, thus removing them from the serialized JSON that is sent back 
 */
class HACUtils {

    static String createStringOrNull(String string)
    {
        if (string != null && !string.isEmpty() && !string.trim().isEmpty())
        {
            return string;
        }
        else
        {
            return null;
        }
    }

    static <E> List<E> createImmutableListOrNull(List<E> list)
    {        
        if (list != null && !list.isEmpty())
        {
            return ImmutableList.<E>copyOf(list);
        } 
        else 
        {
            return null;
        }
    }
    
    static <K, V> Map<K, V> createImmutableMapOrNull(Map<K, V> map)
    {        
        if (map != null && !map.isEmpty())
        {
            return ImmutableMap.<K, V>copyOf(map);
        } 
        else 
        {
            return null;
        }
    }
    
    static <E> List<E> createEmptyListIfNull(List<E> list)
    {
        if (list != null)
        {
            return list;
        } 
        else
        {
            return ImmutableList.<E>of();
        }
    }
    
    static <K, V> Map<K, V> createEmptyMapIfNull(Map<K, V> map)
    {
        if (map != null)
        {
            return map;
        } 
        else 
        {
            return ImmutableMap.<K, V>of();
        }
    }
}