package com.witbooking.middleware.db;

import com.maxmind.geoip2.DatabaseReader;
import com.witbooking.middleware.resources.MiddlewareProperties;

import java.io.File;
import java.io.IOException;

/**
 * Created by mongoose on 11/23/14.
 */
public enum GeoIpDB {

    INSTANCE;

    private DatabaseReader databaseReader;

    GeoIpDB()
    {
        File database = new File(MiddlewareProperties.GEO_IP_DB_FILEPATH);
        try {
            databaseReader = new DatabaseReader.Builder(database).build();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GeoIpDB getInstance()
    {
        return INSTANCE;
    }

    public DatabaseReader getDatabaseReader()
    {
        return databaseReader;
    }
}