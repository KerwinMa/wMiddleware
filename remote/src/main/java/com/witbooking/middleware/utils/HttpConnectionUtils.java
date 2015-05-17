package com.witbooking.middleware.utils;

import com.sun.messaging.jmq.management.DefaultTrustManager;
import com.witbooking.middleware.exceptions.AuthenticationException;
import com.witbooking.middleware.exceptions.InvalidEntryException;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.BasicClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * HttpConnectionUtils class implements a library of utilities for HTTP connections.
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
public final class HttpConnectionUtils {

    private static final Logger logger = Logger.getLogger(HttpConnectionUtils.class);

    /**
     * Performs a POST connection. If an exception occurs during connection, this
     * exception is caught , printed in <code>System.err</code> and return
     * <code>null</code>.
     *
     * @param url      url to which you're connecting.
     * @param encoding Authorization Basic to add in header.
     * @param content  Content of connection.
     * @return Connect response.
     */
    public static HttpResponse postData(HttpClient client, String url, String encoding, String content) throws SSLException {
        // Create a new HttpClient and Post Header;
        HttpPost httppost = new HttpPost(url);
        HttpResponse response = null;
        try {
            httppost.setHeader(new BasicHeader("Authorization", "Basic " + encoding));
            // 	Add your data
            httppost.setEntity(new StringEntity(content));
            // Execute HTTP Post Request
            response = client.execute(httppost);
            //TODO; asegurar cerrar la conexion
//            client.getConnectionManager().shutdown();

        } catch (SSLException e) {
            logger.error(e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response;
    }

    public static void close(HttpClient client) {
        if (client != null && client.getConnectionManager() != null) {
            client.getConnectionManager().shutdown();
        }
    }

    public static HttpClient generateDefaultClientSSL() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new DefaultTrustManager();
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = new BasicClientConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));
            return new DefaultHttpClient(ccm);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            logger.error("Error generating DefaultClientSSL:" + e);
            //This shouldn't never happen
            return new DefaultHttpClient();
        }
    }

    /**
     * Performs a POST connection. If an exception occurs during connection, this
     * exception is caught , printed in <code>System.err</code> and return
     * <code>null</code>.
     *
     * @param url            url to which you're connecting.
     * @param encoding       Authorization Basic to add in header.
     * @param postParameters Parameters sended in the post request.
     * @return Connect response.
     */
    public static HttpResponse postData(HttpClient client, String url, String encoding,
                                        List<NameValuePair> postParameters) {
        // Create a new HttpClient and Post Header;
        HttpPost httppost = new HttpPost(url);
        HttpResponse response = null;
        try {
            httppost.setHeader(new BasicHeader("Authorization", "Basic " + encoding));
            // 	Add your Parameters
            httppost.setEntity(new UrlEncodedFormEntity(postParameters));
            // Execute HTTP Post Request
            response = client.execute(httppost);

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response;
    }

    /**
     * Performs a GET connection. If an exception occurs during connection, this
     * exception is caught , printed in <code>System.err</code> and return
     * <code>null</code>.
     *
     * @param url        url to which you're connecting.
     * @param encoding   Authorization Basic to add in header.
     * @param parameters Parameters sended in the get request.
     * @return Connect response.
     */
    public static HttpResponse getData(HttpClient client, String url, String encoding, Map<String, String> parameters) throws IOException {
        if (parameters != null && !parameters.isEmpty()) {
            List<NameValuePair> getParameters = new ArrayList<>();
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                getParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            StringBuilder requestUrl = new StringBuilder(url);
            String queryString = URLEncodedUtils.format(getParameters, "utf-8");
            requestUrl.append("?");
            requestUrl.append(queryString);
            return getData(client, requestUrl.toString(), encoding);
        } else {
            return getData(client, url, encoding);
        }
    }

    /**
     * Performs a GET connection. If an exception occurs during connection, this
     * exception is caught , printed in <code>System.err</code> and return
     * <code>null</code>.
     *
     * @param url      url to which you're connecting.
     * @param encoding Authorization Basic to add in header.
     * @return Connect response.
     */
    public static HttpResponse getData(HttpClient client, String url, String encoding) throws IOException {
        // Create a new Post Header;
        HttpGet httpGet = new HttpGet(url);
        HttpResponse response;
//        try {
        if (encoding != null)
            httpGet.setHeader(new BasicHeader("Authorization", "Basic " + encoding));
        // Execute HTTP Get Request
        response = client.execute(httpGet);

//        } catch (ClientProtocolException e) {
//            logger.error(e.getMessage());
//        } catch (IOException e) {
//            logger.error(e.getMessage());
//        } catch (Exception e) {
//            logger.error(e.getMessage());
//        }
        return response;
    }

    public static HttpResponse putData(HttpClient client, String url, String content, Properties properties) {
        // Create a new Post Header;
        HttpPut httpPut = new HttpPut(url);
        HttpResponse response = null;
        try {
            if (properties != null && properties.size() > 0) {
                for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
                    httpPut.setHeader((String) entry.getKey(), (String) entry.getValue());
                }
            }
            // 	Add your data
            httpPut.setEntity(new StringEntity(content));
            // Execute HTTP Post Request
            response = client.execute(httpPut);
            //TODO; asegurar cerrar la conexion
//            client.getConnectionManager().shutdown();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response;
    }

    public static HttpResponse deleteData(HttpClient client, String url, Properties properties) {
        // Create a new Post Header;
        HttpDelete httpDelete = new HttpDelete(url);
        HttpResponse response = null;
        try {
            if (properties != null && properties.size() > 0) {
                for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
                    httpDelete.setHeader((String) entry.getKey(), (String) entry.getValue());
                }
            }
            // Execute HTTP Post Request
            response = client.execute(httpDelete);
            //TODO; asegurar cerrar la conexion
//            client.getConnectionManager().shutdown();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response;
    }

    /**
     * Performs a GET connection. If an exception occurs during connection, this
     * exception is caught , printed in <code>System.err</code> and return
     * <code>null</code>.
     *
     * @param url        url to which you're connecting.
     * @param properties Authorization Basic to add in header.
     * @return Connect response.
     */
    public static HttpResponse getData(HttpClient client, String url, Properties properties) throws IOException {
        // Create a new HttpClient and Post Header;
//        final String encode = URLEncoder.encode(url, "UTF-8");
//        logger.debug("Url Encode: "+encode);
        HttpGet httpGet = new HttpGet(url);
        if (properties != null && properties.size() > 0) {
            for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
                httpGet.setHeader((String) entry.getKey(), (String) entry.getValue());
            }
        }
        // Execute HTTP Get Request
        return client.execute(httpGet);

    }

    public static HttpResponse getData(HttpClient client, String url) throws IOException, URISyntaxException {
        return getData(client, url, new Properties());
    }

    public static HttpResponse postData(HttpClient client, String url, String content, Properties properties) {
        // Create a new Post Header;
        HttpPost httppost = new HttpPost(url);
        HttpResponse response = null;
        try {
            if (properties != null && properties.size() > 0) {
                for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
                    httppost.setHeader((String) entry.getKey(), (String) entry.getValue());
                }
            }
            // 	Add your data
            httppost.setEntity(new StringEntity(content));
            // Execute HTTP Post Request
            response = client.execute(httppost);
            //TODO; asegurar cerrar la conexion
//            client.getConnectionManager().shutdown();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return response;
    }

    public static WebApplicationException generateJaxException(Exception ex, String contentType, String body) {
        Response.ResponseBuilder builder;
        if (ex instanceof AuthenticationException) {
            builder = Response.status(Response.Status.UNAUTHORIZED);
        } else if (ex instanceof InvalidEntryException) {
            builder = Response.status(Response.Status.BAD_REQUEST);
        } else {
            builder = Response.status(Response.Status.OK);
        }
        builder.type(contentType);
        builder.entity(body);
        return new WebApplicationException(builder.build());

    }
}
