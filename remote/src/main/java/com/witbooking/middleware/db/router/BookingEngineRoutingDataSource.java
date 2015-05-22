package com.witbooking.middleware.db.router;

/**
 * Created by mongoose on 19/05/15.
 */

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.NonexistentValueException;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.resources.MiddlewareProperties;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BookingEngineRoutingDataSource extends AbstractRoutingDataSource {

    boolean isDataSourceCacheEmpty = true;

    protected DBCredentials getDBCredentialsFromJson(JsonObject jsonCredentials, String ticker) throws
            ExternalFileException {
        DBCredentials dbCredential = new DBCredentials();
        dbCredential.setHost(jsonCredentials.get("host").getAsString());
        dbCredential.setNameDB(jsonCredentials.get("database").getAsString());
        dbCredential.setUserDB(jsonCredentials.get("login").getAsString());
        dbCredential.setPassDB(jsonCredentials.get("password").getAsString());
        dbCredential.setTicker(ticker);
        return dbCredential;
    }

    protected Map<String,DBCredentials> readCredentialsFromConfigFile() throws ExternalFileException {
        Map<String,DBCredentials> credentialsMap=new HashMap<>();
        String customersDBFile = "";
        JsonObject jsonCredentials = null;

        //Parsing the file with the DB credentials, in JSON format
        FileInputStream file = null;
        try {
            file = new FileInputStream(MiddlewareProperties.CUSTOMERS_DB_FILE);
            byte[] b = new byte[file.available()];
            file.read(b);
            customersDBFile = new String(b, "UTF-8");
        } catch (FileNotFoundException ex) {
            logger.error("Customers DB file not found.");
            throw new ExternalFileException(ex, MiddlewareProperties.CUSTOMERS_DB_FILE);
        } catch (IOException ex) {
            logger.error("Problem reading the Customers DB File.");
            throw new ExternalFileException(ex);
        } finally {
            if (file != null)
                try {
                    file.close();
                } catch (IOException e) {
                    logger.error(e);
                }
        }
        try {
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse(customersDBFile).getAsJsonObject();
            if (jsonElement == null) {
                logger.info("Invalid Config File");
                throw new NonexistentValueException("Invalid Config File");
            }
            jsonCredentials = jsonElement.getAsJsonObject();

            for (Map.Entry<String,JsonElement> entry : jsonCredentials.entrySet()) {
                String hotelTicker= entry.getKey();
                DBCredentials dbCredentials=this.getDBCredentialsFromJson(entry.getValue().getAsJsonObject(), hotelTicker);
                credentialsMap.put(hotelTicker,dbCredentials);
            }

            isDataSourceCacheEmpty=credentialsMap.isEmpty();

            return credentialsMap;
        } catch (Exception ex) {
            logger.error("There is something wrong with the Customers DB File. "+ ex);
            throw new ExternalFileException(ex);
        }

    }

    @Cacheable("datasources")
    public Map<Object, Object> getDynamicTargetDataSources(){
        Map<Object,Object> dataSourcesMap=new HashMap<>();
        try {
            Map<String,DBCredentials> credentialsMap=readCredentialsFromConfigFile();
            for (Map.Entry<String,DBCredentials> entry : credentialsMap.entrySet()) {
                DBCredentials dbCredentials = entry.getValue();
                String hotelTicker = entry.getKey();

                String dbURL = "jdbc:mysql://" +dbCredentials.getHost()+":" +dbCredentials.getPort()+"/"+dbCredentials.getNameDB();

                final DriverManagerDataSource dataSource = new DriverManagerDataSource();
                dataSource.setDriverClassName("com.mysql.jdbc.Driver");
                dataSource.setUrl(dbURL);
                dataSource.setUsername(dbCredentials.getUserDB());
                dataSource.setPassword(dbCredentials.getPassDB());

                dataSourcesMap.put(hotelTicker,dataSource);
            }

        } catch (ExternalFileException e) {
            e.printStackTrace();
        }
        return dataSourcesMap;
    }

    @CacheEvict("datasources")
    public void clearDynamicTargetDataSources(){
        isDataSourceCacheEmpty=true;
    }

    @Override
    protected DataSource determineTargetDataSource() {
        if(isDataSourceCacheEmpty){
            setTargetDataSources(getDynamicTargetDataSources());
            afterPropertiesSet();
        }
        return super.determineTargetDataSource();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        return BookingEngineContextHolder.getBookingEngineData()!=null ? BookingEngineContextHolder.getBookingEngineData().getHotelTicker() : "hoteldemo.com.v6";
    }
}

