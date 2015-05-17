/*
 *   NewsletterSubscription.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 07/08/13
 */
public class NewsletterSubscription  implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String email;
    private String language;
    private boolean active;

    public NewsletterSubscription() {
    }

    public NewsletterSubscription(Integer id, String email, String language, boolean active) {
        this.id = id;
        this.email = email;
        this.language = language;
        this.active = active;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "NewsletterSubscription{" +
                "id=" + id +", email='" + email +"', language='" + language +"', active=" + active +
                "'}";
    }
}
