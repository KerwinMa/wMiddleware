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

import com.google.common.collect.ImmutableSet;

/**
 * This class holds the API version constants
 */
public class Versions
{
    public static final int INVALID_VERSION = -1;

    public static final int VERSION_4 = 4;

    public static final ImmutableSet<Integer> VERSIONS = ImmutableSet.of(VERSION_4);

    public static final String S_VERSION_4 = "4";
}
