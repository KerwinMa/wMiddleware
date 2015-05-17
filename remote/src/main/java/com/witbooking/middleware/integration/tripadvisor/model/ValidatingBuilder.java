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

/**
 * Used for builders within the HAC API to denote objects that contain validation logic
 *
 */
public interface ValidatingBuilder<T extends Validatable> {
    
    /**
     * This function will build the object and then run validation on it.
     * This validation will ensure that the object matches the specifications in the API
     * This validation is not exhaustive but should be a good starting point, depending on your data you may have to run different tests
     * 
     * @throws ValidationException if the object does not pass validation
     */
    public T build();
    
    /**
     * This function will build the object and not run validation
     * Not recommended to be used in a production environment!
     * 
     */
    public T buildWithoutValidation();
    
}
