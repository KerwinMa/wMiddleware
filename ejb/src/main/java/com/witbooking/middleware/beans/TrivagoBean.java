/*
 *   TrivagoBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.witbooking.middleware.db.DBConnection;
import com.witbooking.middleware.db.DBCredentials;
import com.witbooking.middleware.db.handlers.DailyValuesDBHandler;
import com.witbooking.middleware.db.handlers.HotelConfigurationDBHandler;
import com.witbooking.middleware.db.handlers.InventoryDBHandler;
import com.witbooking.middleware.db.handlers.WitMetaDataDBHandler;
import com.witbooking.middleware.exceptions.DateFormatException;
import com.witbooking.middleware.exceptions.ExternalFileException;
import com.witbooking.middleware.exceptions.MiddlewareException;
import com.witbooking.middleware.exceptions.db.DBAccessException;
import com.witbooking.middleware.exceptions.integration.trivago.TrivagoException;
import com.witbooking.middleware.integration.trivago.AvailabilityRequest;
import com.witbooking.middleware.integration.trivago.AvailabilityResponse;
import com.witbooking.middleware.integration.trivago.DeepLink;
import com.witbooking.middleware.integration.trivago.HotelList;
import com.witbooking.middleware.model.Inventory;
import com.witbooking.middleware.model.values.DailyValue;
import com.witbooking.middleware.model.values.RangeValue;
import com.witbooking.middleware.resources.DBProperties;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.DateUtil;
import com.witbooking.middleware.utils.FileUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Session Bean implementation class TrivagoBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 28/06/13
 */
@Stateless
public class TrivagoBean implements TrivagoBeanLocal, TrivagoBeanRemote {

    private static final Logger logger = Logger.getLogger(TrivagoBean.class);
    private HotelList hotels;

    @Override
    public HotelList getHotelList() throws TrivagoException {
        logger.debug("getHotelList");

        String hotelsFile;
        List<HotelList.HotelTrivago> hotelList;
        try {
            //Parsing the file with the hotel List, in JSON format
            try {
                FileInputStream file = null;
                try {
                    file = new FileInputStream(MiddlewareProperties.TRIVAGO_HOTELS_JSON_FILE);
                    byte[] b = new byte[file.available()];
                    file.read(b);
                    hotelsFile = new String(b, "UTF-8");
                } catch (FileNotFoundException ex) {
                    logger.error("Hotel List file not found ");
                    throw new ExternalFileException(ex, MiddlewareProperties.TRIVAGO_HOTELS_JSON_FILE);
                } finally {
                    FileUtils.close(file);
                }
            } catch (IOException ex) {
                logger.error("Problem reading the Hotel List File. ");
                throw new ExternalFileException(ex);
            }

            Gson gson = new GsonBuilder().setPrettyPrinting().setDateFormat("yyyy-MM-dd").create();
            hotelList = gson.fromJson(hotelsFile, new TypeToken<List<HotelList.HotelTrivago>>() {
            }.getType());

            if (hotelList == null) {
                logger.error("The Hotel List has not been filled.");
                throw new TrivagoException("The Hotel List has not been filled.");
            }
            hotels = new HotelList();
            for (HotelList.HotelTrivago trivagoHotel : hotelList) {
                if (trivagoHotel.isActive()) {
                    DBCredentials dbCredential = DBProperties.getDBCustomerByTicker(trivagoHotel.getTicker());
                    trivagoHotel.setDbCredentials(dbCredential);
                    hotels.add(trivagoHotel);
                }
            }
            logger.debug("The Hotel List has been filled.");
        } catch (MiddlewareException ex) {
            logger.error("Problem reading generating the list: " + ex.getMessage());
            throw new TrivagoException(ex);
        }
        return hotels;
    }

    @Override
    public DeepLink getDeepLink(String hotelId, Date check_in, Date check_out, String paramLanguage,
                                int adults, int roomType, String currency) throws TrivagoException {
        logger.debug("getDeepLink");
        HotelConfigurationDBHandler hotelConfigurationDBHandler = null;
        WitMetaDataDBHandler witMetaDataDBHandler = null;
        DeepLink deepLink = null;
        try {
            if (hotels == null) {
                hotels = getHotelList();
            }
            HotelList.HotelTrivago hotel = hotels.getHotelById(hotelId);
            if (hotel == null) {
                throw new TrivagoException("The hotel is not found.");
            }
            hotelConfigurationDBHandler = new HotelConfigurationDBHandler(new DBConnection(hotel.getDbCredentials()));

            if (!hotelConfigurationDBHandler.isVersion6()) {
                try {
                    return getDeepLinkV5(hotelId, check_in, check_out, paramLanguage, adults, roomType, currency);
                } catch (IOException ex) {
                    logger.error(ex);
                    throw new TrivagoException(ex);
                }
            }

            String[] language = getLanguage(hotelConfigurationDBHandler, paramLanguage);

            String ticker = hotel.getTicker();
            String bucket = hotel.getBucketid();
            String trackingId = "trivago__" + language[0] + "__" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String urlLink = MiddlewareProperties.URL_WITBOOKER_V6
                    + "select/" + ticker
                    + "/" + language[0] + "/reservationsv6/step1"
                    + "?fini=" + DateUtil.calendarFormat(check_in)
                    + "&noches=" + DateUtil.daysBetweenDates(check_in, check_out)
                    + "&adultos=" + adults
                    + "&ninos=" + 0
                    + "&bebes=" + 0
                    + "&tracking_id=" + trackingId
                    + "&bucket=" + bucket;

            deepLink = new DeepLink.Link(urlLink);
            witMetaDataDBHandler = new WitMetaDataDBHandler();
            String urlLogger = DateUtil.timestampFormat(new Date());
            urlLogger = urlLogger + " Trivago.deeplink: " + urlLink;
            witMetaDataDBHandler.insertDeeplinkLog(urlLogger, "trivago/TrvgHotels/deeplink",
                    Integer.parseInt(hotelId), check_in, check_out, adults, paramLanguage,
                    currency, roomType, trackingId, bucket);

            logger.debug("logger Inserted: " + urlLogger + "-" + "trivago/TrvgHotels/deeplink" + "-"
                    + Integer.parseInt(hotelId) + "-" + check_in + "-" + check_out + "-" + adults + "-"
                    + paramLanguage + "-" + currency + "-" + roomType + "-" + trackingId + "-" + bucket);
        } catch (MiddlewareException ex) {
            logger.error(ex);
            throw new TrivagoException(ex);
        } finally {
            try {
                if (hotelConfigurationDBHandler != null) {
                    hotelConfigurationDBHandler.closeDbConnection();
                }
            } catch (DBAccessException ex) {
                logger.error(ex);
                throw new TrivagoException(ex);
            }
            try {
                if (witMetaDataDBHandler != null) {
                    witMetaDataDBHandler.closeDbConnection();
                }
            } catch (DBAccessException ex) {
                logger.error(ex);
                throw new TrivagoException(ex);
            }
        }
        return deepLink;
    }

    @Override
    public AvailabilityResponse getAvailability(AvailabilityRequest availabilityRequest) throws TrivagoException {
        logger.debug("getAvailability: " + availabilityRequest);
        AvailabilityResponse availabilityResponse = new AvailabilityResponse();
        if (availabilityRequest == null) {
            availabilityResponse.setError("Invalid request. AvailabilityRequest is null.");
            return availabilityResponse;
        }
        InventoryDBHandler inventoryDBHandler;
        DailyValuesDBHandler dailyValuesDBHandler;
        HotelConfigurationDBHandler hotelConfigurationDBHandler = null;
        HotelList.HotelTrivago hotelTrivago;
        Date checkIn;
        Date checkOut;

        try {
            checkIn = DateUtil.stringToCalendarDate(availabilityRequest.getArrivalDate());
            checkOut = DateUtil.stringToCalendarDate(availabilityRequest.getDepartureDate());
            //I don't calculate the rates and Availability for the check out day
            DateUtil.incrementDays(checkOut, -1);
        } catch (DateFormatException ex) {
            logger.warn(ex);
            availabilityResponse.setError("Invalid request. Error in the Dates.");
            return availabilityResponse;
        }
        String currency = availabilityRequest.getCurrency();

        List<String> hotelsV5 = new ArrayList<String>();

        //This tis always the same, because we required the Credit Card all the time
        AvailabilityResponse.CreditCard creditCard = new AvailabilityResponse.CreditCard();
        try {
            if (hotels == null) {
                hotels = getHotelList();
            }
            for (String hotelId : availabilityRequest.getHotelIdentifier()) {
                AvailabilityResponse.HotelRate hotelRate = new AvailabilityResponse.HotelRate();
                hotelRate.setId(hotelId);
                hotelTrivago = hotels.getHotelById(hotelId);
                if (hotelTrivago != null) {
                    DBConnection dbConnection = null;
                    try {
                        dbConnection = new DBConnection(hotelTrivago.getDbCredentials());
                    } catch (DBAccessException ex) {
                        logger.error(ex);
                    }
                    if (dbConnection != null) {
                        try {
                            hotelConfigurationDBHandler = new HotelConfigurationDBHandler(dbConnection);

                            if (hotelConfigurationDBHandler.isVersion6()) {

                                String[] language = getLanguage(hotelConfigurationDBHandler, availabilityRequest.getLanguage());
                                inventoryDBHandler = new InventoryDBHandler(dbConnection, language[1]);
                                dailyValuesDBHandler = new DailyValuesDBHandler(inventoryDBHandler, checkIn, checkOut);
                                dailyValuesDBHandler.getInventoryValuesBetweenDates();
                                //Ask if the hotel allows Cancellation
                                boolean hasCancellation = hotelConfigurationDBHandler.isCancellationAllowed();

                                AvailabilityResponse.Cancellation cancellation = new AvailabilityResponse.Cancellation(hasCancellation);
                                //Ask for number of adults, and No children
                                List<Inventory> inventoryList = inventoryDBHandler.getInventoriesByAdultsAndChildren(availabilityRequest.getAdults(), 0);
                                if (!inventoryList.isEmpty()) {
                                    hotelRate.setRooms(new ArrayList<AvailabilityResponse.Room>());
                                    for (Inventory inventory : inventoryList) {
                                        RangeValue<Integer> availability = dailyValuesDBHandler.getAvailabilityByTicker(inventory.getTicker());
                                        RangeValue<Boolean> lock = dailyValuesDBHandler.getLockByTicker(inventory.getTicker());
                                        //There should be availability grater than zero, And isn't locked
                                        if (!availability.hasValueEqualsTo(0) && !lock.hasValueEqualsTo(true)) {
                                            RangeValue<Float> rates = dailyValuesDBHandler.getFullRatesByTicker(inventory.getTicker(), currency);
                                            float totalRate = 0;
                                            for (DailyValue<Float> dayValue : rates.getDailySet()) {
                                                totalRate = totalRate + (dayValue.getValue() * (dayValue.daysBetweenDates() + 1));
                                            }
                                            AvailabilityResponse.Breakfast breakfast = new AvailabilityResponse.Breakfast(inventory.getMealPlan().hasBreakfast());
                                            AvailabilityResponse.Price price = new AvailabilityResponse.Price(totalRate, currency, breakfast);
                                            String rateName = inventory.getConfiguration().getName() + ", " + inventory.getMealPlan().getName() + ", "
                                                    + inventory.getCondition().getName();
                                            AvailabilityResponse.Rate rate = new AvailabilityResponse.Rate(rateName, price, creditCard, cancellation);
                                            AvailabilityResponse.Room room = new AvailabilityResponse.Room(inventory.getAccommodation().getName(), rate);
//                                 logger.debug("Ticker: " + inventory.getTicker() + ", Room Name: " + room.getName() + ", Rate Name: " + rate.getName());
                                            hotelRate.getRooms().add(room);
                                        }
                                    }
                                }
                                //TODO: quit this add, when dont use the trivago V5 services anymore
                                availabilityResponse.getHotels().add(hotelRate);
                            } else {
                                hotelsV5.add(hotelId);
                            }
                        } finally {
                            if (hotelConfigurationDBHandler != null) {
                                hotelConfigurationDBHandler.closeDbConnection();
                            }
                        }
                    } else {
                        hotelsV5.add(hotelId);
                    }
                } else {
                    //TODO: quit this add, when don't use the trivago V5 services anymore
                    //If the hotelId isn't in the HotelList, return the hotel empty
                    availabilityResponse.getHotels().add(hotelRate);
                }
                //TODO: add the hotel here, when we don't use the trivago V5 services anymore
//            availabilityResponse.getHotels().add(hotelRate);
            }
        } catch (MiddlewareException ex) {
            logger.error(ex);
            throw new TrivagoException(ex);
        }

        //TODO: This do the call to trivago PHP Web services for the hotels with V5
//        availabilityRequest.setHotelIdentifier(hotelsV5);
//        if (!hotelsV5.isEmpty()) {
//            AvailabilityResponse availabilityResponseV5;
//            try {
//                logger.debug("Asking for Trivago V5: " + availabilityRequest);
//                availabilityResponseV5 = getAvailabilityV5(availabilityRequest);
//                availabilityResponse.getHotels().addAll(availabilityResponseV5.getHotels());
//            } catch (Exception ex) {
//                logger.error(ex);
//                throw new TrivagoException(ex);
//            }
//        }
//      logger.debug("availabilityResponse: " +availabilityResponse);
        return availabilityResponse;

    }

    private DeepLink getDeepLinkV5(String hotelId, Date check_in, Date check_out, String paramLanguage,
                                   int adults, int roomType, String currency) throws IOException {
        String url = MiddlewareProperties.URL_TRIVAGO_V5 + "deeplink";
        url = url + "?hotel_id=" + hotelId;
        url = url + "&check_in=" + DateUtil.calendarFormat(check_in);
        url = url + "&check_out=" + DateUtil.calendarFormat(check_out);
        url = url + "&language=" + paramLanguage;
        url = url + "&adults=" + adults;
        url = url + "&room_type=" + roomType;
        url = url + "&currency=" + currency;
        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response = httpclient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String xmlResponse = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            httpclient.getConnectionManager().shutdown();
            InputStream inputStream = null;
            try {
                JAXBContext jaxbContext = JAXBContext.newInstance(DeepLink.Link.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                byte[] bytes = xmlResponse.getBytes("UTF-8");
                inputStream = new ByteArrayInputStream(bytes);
                return (DeepLink) jaxbUnmarshaller.unmarshal(inputStream);
            } catch (JAXBException ex) {
                logger.warn(" Invalid Response: " + ex.getMessage());
                return new DeepLink.LinkError("Bad Request");
            } finally {
                if (inputStream != null)
                    inputStream.close();
            }
        }
        return new DeepLink.LinkError("Bad Request");
    }

    private AvailabilityResponse getAvailabilityV5(AvailabilityRequest availabilityRequest) throws TrivagoException, IOException {
        JAXBContext jaxbContext;
        String availabilityRequestXML;
        StringWriter stringWriter = null;
        try {
            jaxbContext = JAXBContext.newInstance(AvailabilityRequest.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            stringWriter = new StringWriter();
            jaxbMarshaller.marshal(availabilityRequest, stringWriter);
            availabilityRequestXML = stringWriter.toString();
        } catch (JAXBException ex) {
            logger.error(" Invalid Response: " + ex.getMessage());
            throw new TrivagoException(ex);
        } finally {
            if (stringWriter != null)
                try {
                    stringWriter.close();
                } catch (IOException e) {
                    logger.error(e);
                }
        }
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(MiddlewareProperties.URL_TRIVAGO_V5
                + "availabilityrequest");

        List<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("xml", availabilityRequestXML));
        httpPost.setEntity(new UrlEncodedFormEntity(postParameters));

        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        if (entity != null) {
            String xmlResponse = EntityUtils.toString(entity);
//            httpPost.releaseConnection();
            httpclient.getConnectionManager().shutdown();

            logger.debug(" connections closed.");
            InputStream inputStream = null;
            try {
                jaxbContext = JAXBContext.newInstance(AvailabilityResponse.class);
                Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                byte[] bytes = xmlResponse.getBytes("UTF-8");
                inputStream = new ByteArrayInputStream(bytes);
                return (AvailabilityResponse) jaxbUnmarshaller.unmarshal(inputStream);
            } catch (JAXBException ex) {
                logger.warn(" Invalid Response: " + ex.getMessage());
                throw new TrivagoException(ex);
            } finally {
                if (inputStream != null)
                    inputStream.close();
            }
        }
        return null;
    }

    @Override
    public AvailabilityResponse testServicesTrivago() {
        File filePath = new File("PATH_FILE");
        logger.debug("file: " + filePath.getAbsolutePath());
        AvailabilityResponse availabilityResponse = new AvailabilityResponse("ALL ITS OK!");
        logger.debug("availabilityResponse: " + availabilityResponse);
        return availabilityResponse;
    }

    private String[] getLanguage(HotelConfigurationDBHandler hotelConfigurationDBHandler, String language) throws DBAccessException {
        List<String[]> codes = hotelConfigurationDBHandler.getActiveLanguageCodes();
        for (String[] langCode : codes) {
            if (language.contains(langCode[0])) {
                return langCode;
            }
        }
        //If the hotel doesn't contents the languade, find for the ENG as default
        if (codes != null && !codes.isEmpty() && language.contains("en")) {
            for (String[] code : codes) {
                if ("en".equalsIgnoreCase(code[0])) {
                    return new String[]{"en", "eng"};
                }
            }
            return codes.get(0);
        }
        return new String[]{"es", "spa"};
    }
}
