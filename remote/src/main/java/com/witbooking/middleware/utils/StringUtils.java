package com.witbooking.middleware.utils;

import org.apache.log4j.Logger;

import java.util.Arrays;

/**
 * StringUtils.java
 * User: jose
 * Date: 1/30/14
 * Time: 10:27 AM
 */
public final class StringUtils {

    private static final Logger logger = Logger.getLogger(StringUtils.class);

    public static Boolean arrayContainsCaseInsensitive(final String[] arrString, final String strToFind) {
        return arrayContainsCaseInsensitive(Arrays.asList(arrString), strToFind);
    }

    public static Boolean arrayContainsCaseInsensitive(final Iterable<String> arrString, final String strToFind) {
        if (arrString == null || strToFind == null)
            return false;
        for (final String s : arrString) {
            if (s.equalsIgnoreCase(strToFind))
                return true;
        }
        return false;
    }
}