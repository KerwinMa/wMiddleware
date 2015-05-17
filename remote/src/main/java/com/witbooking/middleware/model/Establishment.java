/*
 *  Establishment.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 15-may-2013
 */
public abstract class Establishment implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    protected Integer id;
    protected String ticker;
    protected String name;
    protected String description;
    protected String phone;
    protected String emailAdmin;
    protected boolean active;
    protected Properties configuration;
    protected List<Media> media;
    protected Media logo;
    protected VisualRepresentation visualRepresentation;

    /**
     * Creates a new instance of
     * <code>Establishment</code> without params.
     */
    public Establishment() {
        this.configuration = new Properties();
    }

    public Establishment(Establishment other) {
        if (other != null) {
            this.id = other.id;
            this.ticker = other.ticker;
            this.name = other.name;
            this.description = other.description;
            this.phone = other.phone;
            this.emailAdmin = other.emailAdmin;
            this.active = other.active;
            this.configuration = other.configuration;
            this.visualRepresentation = other.visualRepresentation;
        }
    }

    public Establishment(Integer id, String ticker, String name, String description, String phone,
                         String emailAdmin, boolean active, Properties configuration) {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.description = description;
        this.phone = phone;
        this.emailAdmin = emailAdmin;
        this.active = active;
        this.configuration = configuration;
    }


    public VisualRepresentation getVisualRepresentation() {
        return visualRepresentation;
    }

    public void setVisualRepresentation(VisualRepresentation visualRepresentation) {
        this.visualRepresentation = visualRepresentation;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public Media getLogo() {
        return logo;
    }

    public void setLogo(Media logo) {
        this.logo = logo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailAdmin() {
        return emailAdmin;
    }

    public void setEmailAdmin(String emailAdmin) {
        this.emailAdmin = emailAdmin;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Properties getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Properties configuration) {
        this.configuration = configuration;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Establishment other = (Establishment) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.ticker == null) ? (other.ticker != null) : !this.ticker.equals(other.ticker)) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if ((this.phone == null) ? (other.phone != null) : !this.phone.equals(other.phone)) {
            return false;
        }
        if ((this.emailAdmin == null) ? (other.emailAdmin != null) : !this.emailAdmin.equals(other.emailAdmin)) {
            return false;
        }
        if (this.active != other.active) {
            return false;
        }
        if (this.configuration != other.configuration && (this.configuration == null || !this.configuration.equals(other.configuration))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("id", id)
                .add("ticker", ticker)
                .add("name", name)
                .add("description", description)
                .add("phone", phone)
                .add("emailAdmin", emailAdmin)
                .add("active", active)
                .add("configuration", configuration)
                .add("media", media)
                .toString();
    }


    //    @Override
//    public String toString() {
//        return Objects.toStringHelper(this)
//                .add("id", id)
//                .add("ticker", ticker)
//                .add("name", name)
//                .add("description", description)
//                .add("phone", phone)
//                .add("emailAdmin", emailAdmin)
//                .add("active", active)
//                .add("configuration", configuration)
//                .add("media", media)
//                .toString();
//    }
}

