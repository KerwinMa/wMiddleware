package com.witbooking.middleware.model;

import com.google.common.base.Objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Page.java
 * User: jose
 * Date: 4/1/14
 * Time: 5:48 PM
 */
public class Page implements Serializable {

    int id;
    String ticker;
    String title;
    String description;
    String seo;
    List<Media> media;
    List<Page> children;

    public Page(int id, String ticker, String title, String description, String seo, List<Media> media) {
        this.id = id;
        this.ticker = ticker;
        this.title = title;
        this.description = description;
        this.seo = seo;
        this.media = media;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSeo() {
        return seo;
    }

    public void setSeo(String seo) {
        this.seo = seo;
    }

    public List<Media> getMedia() {
        return media;
    }

    public void setMedia(List<Media> media) {
        this.media = media;
    }

    public List<Page> getChildren() {
        return children;
    }

    public void setChildren(List<Page> children) {
        this.children = children;
    }

    public void addChildren(Page page) {
        if (children == null) {
            children = new ArrayList<Page>();
        }
        children.add(page);
    }

    public Boolean hasChildren() {
        return children == null || children.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Page)) return false;

        Page page = (Page) o;

        if (id != page.id) return false;
        if (children != null ? !children.equals(page.children) : page.children != null) return false;
        if (description != null ? !description.equals(page.description) : page.description != null) return false;
        if (media != null ? !media.equals(page.media) : page.media != null) return false;
        if (seo != null ? !seo.equals(page.seo) : page.seo != null) return false;
        if (ticker != null ? !ticker.equals(page.ticker) : page.ticker != null) return false;
        if (title != null ? !title.equals(page.title) : page.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (ticker != null ? ticker.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (seo != null ? seo.hashCode() : 0);
        result = 31 * result + (media != null ? media.hashCode() : 0);
        result = 31 * result + (children != null ? children.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return super.toString() + Objects.toStringHelper(this)
                .add("id", id)
                .add("ticker", ticker)
                .add("title", title)
                .add("description", description)
                .add("seo", seo)
                .add("media", media)
                .add("children", children)
                .toString();
    }
}