/*
 *   ConnectionBean.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.beans;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.witbooking.middleware.exceptions.RemoteServiceException;
import com.witbooking.middleware.resources.MiddlewareProperties;
import com.witbooking.middleware.utils.HttpConnectionUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Session Bean implementation class ConnectionBean
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 10/09/13
 */
@Stateless
@WebService()
public class ConnectionBean implements ConnectionBeanLocal {

    private static final Logger logger = Logger.getLogger(ConnectionBean.class);

    @WebMethod()
    @WebResult(name = "getHotelTickers")
    public String getHotelTickers(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password) throws RemoteServiceException {
        final String url = MiddlewareProperties.URL_WITBOOKER_V6 + "/validator/ValidatorUsers/authenticate";
        List<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("username", username));
        postParameters.add(new BasicNameValuePair("password", password));
        logger.debug(" request Login direction: " + url);
        logger.debug(" request Login for the user: " + username);
        return generatePostRequest(url, postParameters);
    }

    @WebMethod()
    @WebResult(name = "validateAuthentication")
    public Boolean validateAuthentication(
            @WebParam(name = "username") String username,
            @WebParam(name = "password") String password) throws RemoteServiceException {
        final String url = MiddlewareProperties.URL_WITBOOKER_V6 + "/validator/ValidatorUsers/authUser";
        List<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("username", username));
        postParameters.add(new BasicNameValuePair("password", password));
        logger.debug(" request Login direction: " + url);
        logger.debug(" request Login for the user: " + username);
        String jsonString = generatePostRequest(url, postParameters);
        if (jsonString == null) {
            throw new RemoteServiceException("Error in connection.");
        } else {
            try {
                final JsonElement responseObj = new JsonParser().parse(jsonString);
                if (responseObj != null && !responseObj.isJsonNull()) {
                    return responseObj.getAsBoolean();
                } else {
                    throw new RemoteServiceException("Error getting response of Web Services.");
                }
            } catch (JsonSyntaxException ex) {
                throw new RemoteServiceException(ex);
            }
        }

    }

    @WebMethod()
    @WebResult(name = "encryptionPHP")
    public String encryptionPHP(
            @WebParam(name = "action") String action,
            @WebParam(name = "value") String value) throws RemoteServiceException {
        final String url = MiddlewareProperties.URL_WITBOOKER_V6 + "/tools/crypter.php";
        List<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("action", action));
        postParameters.add(new BasicNameValuePair("value", value));
//        logger.debug(" Request crypter service  (" + action + ", " + value + ")");
        return generatePostRequest(url, postParameters);
    }

    @WebMethod()
    @WebResult(name = "sendConfirmationEmail")
    public String sendConfirmationEmail(
            @WebParam(name = "hotelTicker") String hotelTicker,
            @WebParam(name = "reservationCode") String reservationCode) throws RemoteServiceException {
        final String url = MiddlewareProperties.URL_WITBOOKER_V6 + "/email/sendEmail";
        List<NameValuePair> postParameters = new ArrayList<>();
        postParameters.add(new BasicNameValuePair("customer", hotelTicker));
        postParameters.add(new BasicNameValuePair("idgeneradomulti", reservationCode));
        return generatePostRequest(url, postParameters);
    }

    private String generatePostRequest(String url, List<NameValuePair> postParameters) throws RemoteServiceException {
        HttpClient httpclient = new DefaultHttpClient();
        String encoding = MiddlewareProperties.HTTP_AUTH_USER + ":" + MiddlewareProperties.HTTP_AUTH_PASS;
        encoding = Base64.encodeBase64String(encoding.getBytes());
        String responseString = "";
        boolean haveToShutdown = true;
        try {
            HttpResponse response = HttpConnectionUtils.postData(httpclient, url, encoding, postParameters);
            if (response == null) {
                throw new RemoteServiceException("Connection is null. Looks like PHP is down.");
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                responseString = EntityUtils.toString(entity);
            }
            httpclient.getConnectionManager().shutdown();
            haveToShutdown = false;
//            logger.debug(" ResponseCode:" + response.getStatusLine().getStatusCode());
            if (response.getStatusLine().getStatusCode() != 200) {
                logger.error(" ResponseCodeError:" + response.getStatusLine().getStatusCode());
                throw new RemoteServiceException("Error in connection.");
            }
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            throw new RemoteServiceException(ex);
        } finally {
            if (haveToShutdown)
                httpclient.getConnectionManager().shutdown();
        }
        return responseString;
    }
}
