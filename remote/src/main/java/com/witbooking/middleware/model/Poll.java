/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.witbooking.middleware.model;

import java.io.Serializable;

/**
 *
 * @author jose
 */
public class Poll implements Serializable {
    private int id;
    private String title;
    private String content;
    private String language;
    
    
    public Poll(){}

    public Poll(int id, String title, String content, String language) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.language = language;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the tittle
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the tittle to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the text
     */
    public String getContent() {
        return content;
    }

    /**
     * @param text the text to set
     */
    public void setContent(String text) {
        this.content = text;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Poll{" + "id=" + id + ", title=" + title + ", content=" + content + ", language=" + language + '}';
    }
    
}
