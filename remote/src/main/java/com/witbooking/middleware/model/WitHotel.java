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
public class WitHotel implements Serializable {
    private int id;
    private String ticker , url;
    
    public WitHotel(int id,String ticker,String url){
        this.id = id;
        this.ticker = ticker;
        this.url = url;
    }
    public WitHotel(){}
    
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WitHotel other = (WitHotel) obj;
        if (this.id != other.id) {
            return false;
        }
        if ((this.ticker == null) ? (other.ticker != null) : !this.ticker.equals(other.ticker)) {
            return false;
        }
        if ((this.url == null) ? (other.url != null) : !this.url.equals(other.url)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.id;
        hash = 29 * hash + (this.ticker != null ? this.ticker.hashCode() : 0);
        hash = 29 * hash + (this.url != null ? this.url.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "WitHotel{" + "id=" + id + ", ticker=" + ticker + ", url=" + url + '}';
    }
    
}
