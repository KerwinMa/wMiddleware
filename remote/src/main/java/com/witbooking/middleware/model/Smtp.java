/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.witbooking.middleware.model;

import com.witbooking.middleware.resources.MiddlewareProperties;

import java.io.Serializable;
import java.util.Properties;

/**
 *
 * @author jose
 */
public class Smtp implements Serializable {

    private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(Smtp.class);

    private String host, user, password, port, kindConnection, security;
    private final String SMTP = "smtp";
    private final String HOST_GMAIL = "smtp.gmail.com";
    private final String PORT_GMAIL = "587";

    //Those are the default smtp properties from gmail
    public Smtp() {
        this.host = HOST_GMAIL;
        this.user = MiddlewareProperties.NOTIFICATIONS_EMAIL_ADDRESS;
        this.password = MiddlewareProperties.NOTIFICATIONS_EMAIL_PASSWORD;
        this.port = PORT_GMAIL;
        this.kindConnection = SMTP;
        this.security="tls";
    }

    public Smtp(String host, String user, String password, String port, String kindConnection, String security) {
        this.host = host;
        this.user = user;
        this.password = password;
        this.port = port;
        this.kindConnection = kindConnection;
        this.security = security;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }

    /**
     * @return the kindConnection
     */
    public String getKindConnection() {
        return kindConnection;
    }

    /**
     * @param kindConnection the kindConnection to set
     */
    public void setKindConnection(String kindConnection) {
        this.kindConnection = kindConnection;
    }

    public boolean isValid() {
        try {
            if (!this.host.isEmpty() && !this.port.isEmpty() && !this.user.isEmpty() && !this.password.isEmpty()) {
                return true;
            }
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
        return false;
    }

    public Properties toProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.user", user);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.quitwait", "false");
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.ssl.trust", host);

        if(security.trim().equalsIgnoreCase("ssl")){
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        }else{
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.ssl.enable", "false");
            props.put("mail.smtp.socketFactory.class", "javax.net.SocketFactory");
        }
        return props;
    }

    @Override
    public String toString() {
        return "Smtp{" + "host=" + host + ", user=" + user + ", password=" + password + ", port=" + port + ", kindConnection=" + kindConnection + '}';
    }
    
    
}
