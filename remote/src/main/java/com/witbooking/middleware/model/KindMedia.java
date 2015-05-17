package com.witbooking.middleware.model;

/**
 * KindMedia.java
 * User: jose
 * Date: 28/05/14
 * Time: 9:35
 */
public enum KindMedia {
    ACCOMMODATIONS("tiposalojamiento"), ESTABLISHMENT("establecimientos"), SERVICES("extras"), LOGO("logo"), PAGES("pages");
    private String value;

    KindMedia(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static KindMedia fromString(String s) {
        KindMedia kindMedia = null;
        for (final KindMedia media : KindMedia.values()) {
            if (media.getValue().equalsIgnoreCase(s)) {
                kindMedia = media;
                break;
            }
        }
        return kindMedia;
    }

    @Override
    public String toString() {
        return value;
    }
}