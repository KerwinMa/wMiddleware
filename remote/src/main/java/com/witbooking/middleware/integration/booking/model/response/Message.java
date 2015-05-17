package com.witbooking.middleware.integration.booking.model.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * Warnings can be combined with success messages if the request was still processed.
 * 
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable{
    
    /**
     * Type: OTA error code (see OTA codetable).
     */
    @XmlAttribute(name="Type")
    private String type;
    /**
     * RecordID: same as LocatorID in OTA_HotelAvailNotifRQ.
     */
    @XmlAttribute(name="Code")
    private Integer code;
    /**
     *  RecordID: same as LocatorID in OTA_HotelAvailNotifRQ.
     */
    @XmlAttribute(name="RecordID")
    private Integer recordId;
    
    /**
     * Status: NotProcessed (error) / Complete (only warning).
     */
    @XmlAttribute(name="Status")
    private String status;
    /**
     * ShortText (may be empty): warning message.
     */
    @XmlAttribute(name="ShortText")
    private String shortText;
    
    public Message(){}

    public void setType(String type) {
        this.type = type;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setRecordId(Integer recordId) {
        this.recordId = recordId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setShortText(String shortText) {
        this.shortText = shortText;
    }

    public String getType() {
        return type;
    }

    public Integer getCode() {
        return code;
    }

    public Integer getRecordId() {
        return recordId;
    }

    public String getStatus() {
        return status;
    }

    public String getShortText() {
        return shortText;
    }
    
    

    @Override
    public String toString() {
        return "Message{" + "type=" + type + ", code=" + code + ", recordId=" + recordId + ", status=" + status + ", shortText=" + shortText + '}';
    }
}
