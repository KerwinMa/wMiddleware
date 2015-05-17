/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.witbooking.middleware.utils;

import com.witbooking.middleware.resources.MiddlewareProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
public final class TinyURLUtils {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(TinyURLUtils.class);

    //private static final String tinyUrl = "http://tinyurl.com/api-create.php?url=";

    public static String shorter(String url) {
        String tinyUrlLookup = MiddlewareProperties.URL_TINYURL + url;
        BufferedReader reader = null;
        InputStreamReader streamReader = null;
        try {
            streamReader = new InputStreamReader(new URL(tinyUrlLookup).openStream());
            reader = new BufferedReader(streamReader);
            String ret = reader.readLine();
            return ret;
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        } finally {
            try {
                if (streamReader != null)
                    streamReader.close();
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                logger.error(e);
            }
        }
        return url;
    }

}
