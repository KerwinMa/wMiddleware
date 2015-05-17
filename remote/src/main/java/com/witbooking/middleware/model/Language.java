/*
 *  Language.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import java.io.Serializable;
import java.util.List;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 31-may-2013
 */
public class Language implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String code;
    private String locale;
    private String charset;

    /**
     * Creates a new instance of
     * <code>Language</code> without params.
     */
    public Language() {
    }

    public Language(Integer id, String name, String code, String locale, String charset) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.locale = locale;
        this.charset = charset;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    @Override
    public String toString() {
        return "Language{" + "id=" + id + ", name=" + name + ", code=" + code +
                ", locale=" + locale + ", charset=" + charset + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Language other = (Language) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.code == null) ? (other.code != null) : !this.code.equals(other.code)) {
            return false;
        }
        if ((this.locale == null) ? (other.locale != null) : !this.locale.equals(other.locale)) {
            return false;
        }
        if ((this.charset == null) ? (other.charset != null) : !this.charset.equals(other.charset)) {
            return false;
        }
        return true;
    }

    public static Language getActiveLanguageByLocale(List<Language> languageList, String locale) {
        for (Language language : languageList) {
            String langLocale = language.getLocale() + "";
            if (langLocale.equals(locale + ""))
                return language;
        }
        return null;
    }

    public static Language getActiveLanguageByCode(List<Language> languageList, String code) {
        for (Language language : languageList) {
            String langCode = language.getCode() + "";
            if (langCode.equals(code + ""))
                return language;
        }
        return null;
    }

}
