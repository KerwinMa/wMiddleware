/*
 *   Review.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 08/08/13
 */
public class Review implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String title;
    private String comments;
    private String author;
    private String language;
    private String address;
    private String source;
    private boolean visible;
    private Date creationDate;

    public Review() {
    }

    public Review(Integer id, String title, String comments, String customerName, String language,
                  String address, String source, boolean visible, Date creationDate) {
        this.id = id;
        this.title = title;
        this.comments = comments;
        this.author = customerName;
        this.language = language;
        this.address = address;
        this.source = source;
        this.visible = visible;
        this.creationDate = creationDate;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", title='" + title + "'" +
                ", comments='" + comments + "'" +
                ", author='" + author + "'" +
                ", language='" + language + "'" +
                ", address='" + address + "'" +
                ", source='" + source + "'" +
                ", visible=" + visible +
                ", creationDate=" + creationDate +
                "}";
    }
}
