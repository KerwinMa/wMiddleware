/*
 *   ResGuest.java
 * 
 * Copyright(c) 2015 Witbooking.com. All Rights Reserved.
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
 * @date 22/01/2015
 */
public class ResGuest implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;


    public ResGuest(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public ResGuest() {

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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResGuest)) return false;

        ResGuest resGuest = (ResGuest) o;

        if (id != null ? !id.equals(resGuest.id) : resGuest.id != null) return false;
        if (name != null ? !name.equals(resGuest.name) : resGuest.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "'" + name + "'";
    }
}
