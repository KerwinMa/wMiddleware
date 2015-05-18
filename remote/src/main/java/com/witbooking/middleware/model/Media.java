package com.witbooking.middleware.model;

import com.google.common.base.Objects;
import com.witbooking.middleware.resources.MiddlewareProperties;

import java.io.Serializable;

/**
 * Media.java
 * User: jose
 * Date: 12/9/13
 * Time: 11:57 AM
 */
public class Media implements Serializable,Comparable<Media> {

    private String file;
    private String title;
    private String description;

    private KindMedia kindMedia;

    private int order;

    public Media(String file, String title, String description, KindMedia kindMedia,int order) {
        this.file = file;
        this.title = title;
        this.description = description;
        this.kindMedia = kindMedia;
        this.order=order;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
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

    public KindMedia getKindMedia() {
        return kindMedia;
    }

    public void setKindMedia(KindMedia kindMedia) {
        this.kindMedia = kindMedia;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public String getUrlPhoto(final String hotelTicker, final Integer id) {
        final String url = MiddlewareProperties.STATIC_ROOT_URL + hotelTicker + "/" +
                kindMedia.getValue() + "/" + id + "/" + getFile();
        return url;
    }

    public String getFullTitleWithDescription() {
        return title != null && !title.isEmpty()
                ? description != null && !description.isEmpty()
                ? title + " " + description
                : title
                : description;
    }

    public String getFileNameWithSize(String size) {
        String ext = getFileExtension();
        String nameFile = file.replace("." + ext, "");
        return nameFile + "_" + size + "." + ext;
    }

    public String getFileName() {
        String ext = getFileExtension();
        return file.replace("." + ext, "");
    }

    public String getFileExtension() {
        String[] name = file.split("\\.");
        if (name.length < 2)
            return "";
        return name[name.length - 1];
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("file", file)
                .add("title", title)
                .add("description", description)
                .toString();
    }

    @Override
    public int compareTo(Media o) {
        return this.order-o.order;
    }
}