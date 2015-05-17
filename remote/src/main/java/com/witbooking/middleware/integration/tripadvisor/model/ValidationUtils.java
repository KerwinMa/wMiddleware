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

import com.google.common.base.Joiner;

import java.util.List;
import java.util.Map;

/**
 * Contains methods for logging validation errors.
 * 
 */
public class ValidationUtils
{
    /**
     * Checks if value is equal to expected
     * 
     * Adds any errors to the errors List
     * 
     * @param fieldName
     * @param className
     * @param value
     * @param expected
     * @param errors 
     */
    public static void same(String fieldName, String className, Object value, Object expected, List<String> errors)
    {
        // if value cannot be null then we will validate that somewhere else
        if (value != null && !value.equals(expected))
        {
            errors.add(fieldName + " in the " + className + " was not equal to the expected value. Expected: " + expected + " Actual: " + value);
        }
    }
    
    /**
     * Checks if the Object is not null
     * 
     * Adds any errors to the errors List
     * 
     * @param fieldName
     * @param className
     * @param value
     * @param errors
     */
    public static void notNull(String fieldName, String className, Object value, List<String> errors)
    {
        if (value == null)
        {
            errors.add(fieldName + " must be present in the " + className);
        }
    }
    
    /**
     * Checks if the Number value holds zero
     *
     * Adds any errors to the errors List 
     * 
     * @param fieldName
     * @param className
     * @param value should be an instance of Number
     * @param errors
     */
    public static void notZero(String fieldName, String className, Object value, List<String> errors)
    {
        if (value instanceof Number)
        {
            Number n = (Number) value;
            if (Math.abs(n.doubleValue()) < Float.MIN_NORMAL)
            {
                errors.add(fieldName + " must be present in the " + className+ " and grater than Zero.");
            }
        }
    }
    
    /**
     * Checks if the map has a size greater than 0
     * 
     * Adds any errors to the errors List
     * 
     * @param fieldName
     * @param className
     * @param value
     * @param errors
     */
    public static void notEmptyMap(String fieldName, String className, Map<?,?> value, List<String> errors)
    {
        // check for null separately
        if (value != null && value.size() < 1)
        {
            errors.add(fieldName + " must not be empty in the " + className);
        }
    }

    /**
     * Checks if the value String has length between min and max, inclusive 
     * 
     * Adds any errors to the errors List
     * 
     * @param fieldName
     * @param className
     * @param value
     * @param min
     * @param max
     * @param errors
     */
    public static void length(String fieldName, String className, String value, int min, int max, List<String> errors)
    {
        // if value cannot be null then we will validate that somewhere else
        if (value != null)
        {
            int length = value.length();
            if (length > max)
            {
                errors.add(fieldName + " cannot be longer than " + max + " characters. ("
                        + value.substring(0, (10 > max ? max : 10)) + ")");
            }
            else if (length < min)
            {
                errors.add(fieldName + " cannot be shorter than " + min + " characters. (" + value + ")");
            }
        }
    }
    
    /**
     * Checks if value is between min and max, inclusive
     * 
     * Adds any errors to the errors List
     * 
     * @param fieldName 
     * @param className
     * @param value
     * @param min
     * @param max
     * @param errors
     */
    public static void inRange(String fieldName, String className, int value, int min, int max, List<String> errors)
    {
        if (value > max)
        {
            errors.add(fieldName + " cannot be greater than " + max + ". (" + value + ")");
        }
        else if (value < min)
        {
            errors.add(fieldName + " cannot be less than " + min + ". (" + value + ")");
        }
    }
    
    /**
     * 
     * @param validationErrors, the list generated from the various validation methods
     * @throws com.witbooking.middleware.integration.tripadvisor.model.ValidationException exception if the list contains validation errors
     */
    static void generateFailureMessage(List<String> validationErrors)
    {
        if (!validationErrors.isEmpty())
        {
            throw new ValidationException("Object did not pass validation. Error(s) include: " + Joiner.on(",").join(validationErrors));
        }
    }
}