/*
 *  Accommodation.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 24-ene-2013
 */
public class Accommodation implements Serializable, Comparable<Accommodation> {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String ticker;
    private String name;
    private String description;
    private Date dateCreation;
    private Date dateModification;
    private String color;
    private List<Media> media;
    private boolean visible = true;
    private boolean deleted = false;
    private int order;

    /**
     * Creates a new instance of
     * <code>Accommodation</code> without params.
     */
    public Accommodation() {
    }

    public Accommodation(Integer id, String ticker, String name, String description,
                         Date dateCreation, Date dateModification, String color, boolean visible) {
        this.id = id;
        this.ticker = ticker;
        this.name = name;
        this.description = description;
        this.dateCreation = dateCreation;
        this.dateModification = dateModification;
        this.color = color;
        this.visible = visible;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
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

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateModification() {
        return dateModification;
    }

    public void setDateModification(Date dateModification) {
        this.dateModification = dateModification;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    @Override
    public String toString() {
        return "Accommodation{" + "id=" + id + ", ticker=" + ticker + ", name=" + name
                //              + ", description=" + description + ", dateCreation=" + dateCreation + ", dateModification=" + dateModification + ", color=" + color + ", visible=" + visible
                + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Accommodation other = (Accommodation) obj;
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
        if (this.dateCreation != other.dateCreation && (this.dateCreation == null || !this.dateCreation.equals(other.dateCreation))) {
            return false;
        }
        if (this.dateModification != other.dateModification && (this.dateModification == null || !this.dateModification.equals(other.dateModification))) {
            return false;
        }
        if ((this.color == null) ? (other.color != null) : !this.color.equals(other.color)) {
            return false;
        }
        if (this.visible != other.visible) {
            return false;
        }
        if (this.order != other.order) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(Accommodation o) {
        return order < o.getOrder() ? -1 : order > o.getOrder() ? 1 : 0;
    }
}
