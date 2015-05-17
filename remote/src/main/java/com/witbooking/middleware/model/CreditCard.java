/*
 *  CreditCard.java
 * 
 * Copyright(c) 2013 Witbooking.com. All Rights Reserved.
 * This software is the proprietary information of Witbooking.com
 * 
 */
package com.witbooking.middleware.model;

import com.witbooking.middleware.utils.OTAUtils;

import java.io.Serializable;

/**
 * Insert description here
 *
 * @author Christian Delgado
 * @version 1.0
 * @date 28-ene-2013
 */
public class CreditCard implements Serializable {

    /**
     * Constant serialized ID used for compatibility.
     */
    private static final long serialVersionUID = 1L;
    private String id;
    private String cardHolderName;
    private String cardNumber;
    private short expireDate;// MMYY
    private String seriesCode;//Card security code
    private String cardCode;//tipe of card
    //encrypted
    private String cardNumberEncrypted;
    private String expireDateEncrypted;
    private String seriesCodeEncrypted;

    /**
     * Creates a new instance of
     * <code>CreditCard</code> without params.
     */
    public CreditCard() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
      /*
         Card Type            Code
         American Express     AX
         Bank Card            BC
         Carte Bleu           BL
         Carte Blanche        CB
         Diners Club          DN
         Discover Card        DS
         Eurocard             EC
         Japanesecredit       JC
         Maestro              MA
         Mastercard           MC
         Solo                 SO
         Universal Air 
         Travel Card          TP
         Visa Electron        VE
         Visa                 VI
         */
        this.cardCode = cardCode;
    }

    public void setCardCodeFromOTA(String otaCode) {
        this.cardCode = OTAUtils.getCardName(otaCode);
    }

    public String getCardOTACode() {
        return OTAUtils.getCardOTACode(this.cardCode);
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        //TODO change that, should implement the real encryptation method.
//        this.cardNumberEncrypted = "zf+Hk0AvBWbiev7IxdO/kg==";
        this.cardNumber = cardNumber;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public short getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(short expireDate) {
        this.expireDate = expireDate;
    }

    public String getExpireDateString() {
        String date = (expireDate < 999 ? "0" + expireDate : "" + expireDate);
        if (date.length() < 4) date = "0000";
        return date.substring(0, 2) + "/" + date.substring(2, 4);
    }

    public String getSeriesCode() {
        return seriesCode;
    }

    public void setSeriesCode(String seriesCode) {
        this.seriesCode = seriesCode;
    }

    public String getCardNumberEncrypted() {
        return cardNumberEncrypted;
    }

    public void setCardNumberEncrypted(String cardNumberEncrypted) {
        this.cardNumberEncrypted = cardNumberEncrypted;
    }

    public String getExpireDateEncrypted() {
        return expireDateEncrypted;
    }

    public void setExpireDateEncrypted(String expireDateEncrypted) {
        this.expireDateEncrypted = expireDateEncrypted;
    }

    public String getSeriesCodeEncrypted() {
        return seriesCodeEncrypted;
    }

    public void setSeriesCodeEncrypted(String seriesCodeEncrypted) {
        this.seriesCodeEncrypted = seriesCodeEncrypted;
    }

    public void setDefaultValues() {
        this.cardHolderName = "Credit Card Not Supplied";
        this.cardNumber = "4111111111111111";
        this.expireDate = 0;
        this.seriesCode = "123";
        this.cardCode = "Visa";
        this.cardNumberEncrypted = "zf+Hk0AvBWbiev7IxdO/kg==";
        this.seriesCodeEncrypted = "r/DMy9pgcaC1ApGlHPnU9w==";
        this.expireDateEncrypted = "uYHe6zvyURIl8Ndm8DBQsA==";
    }

    @Override
    public String toString() {
        return "CreditCard{" + "id=" + id + ", cardCode=" + cardCode + ", cardNumberEncrypted=" + cardNumberEncrypted + ", cardHolderName=" + cardHolderName + ", expireDateEncrypted=" + expireDateEncrypted + ", seriesCodeEncrypted=" + seriesCodeEncrypted + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CreditCard other = (CreditCard) obj;
        if ((this.id == null) ? (other.id != null) : !this.id.equals(other.id)) {
            return false;
        }
        if ((this.cardHolderName == null) ? (other.cardHolderName != null) : !this.cardHolderName.equals(other.cardHolderName)) {
            return false;
        }
        if ((this.cardNumber == null) ? (other.cardNumber != null) : !this.cardNumber.equals(other.cardNumber)) {
            return false;
        }
        if (this.expireDate != other.expireDate) {
            return false;
        }
        if ((this.seriesCode == null) ? (other.seriesCode != null) : !this.seriesCode.equals(other.seriesCode)) {
            return false;
        }
        if ((this.cardCode == null) ? (other.cardCode != null) : !this.cardCode.equals(other.cardCode)) {
            return false;
        }
        if ((this.cardNumberEncrypted == null) ? (other.cardNumberEncrypted != null) : !this.cardNumberEncrypted.equals(other.cardNumberEncrypted)) {
            return false;
        }
        if ((this.expireDateEncrypted == null) ? (other.expireDateEncrypted != null) : !this.expireDateEncrypted.equals(other.expireDateEncrypted)) {
            return false;
        }
        if ((this.seriesCodeEncrypted == null) ? (other.seriesCodeEncrypted != null) : !this.seriesCodeEncrypted.equals(other.seriesCodeEncrypted)) {
            return false;
        }
        return true;
    }
}
