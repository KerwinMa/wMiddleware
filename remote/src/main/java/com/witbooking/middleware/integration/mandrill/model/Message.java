package com.witbooking.middleware.integration.mandrill.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by mongoose on 1/20/15.
 */
public class Message {

    private String _id;
    private String ts;
    private String email;
    private String sender;
    private String subject;
    private List<String> tags;

    final public static String MESSAGE_TAG_PRE_STAY="PostStayMails";
    final public static String MESSAGE_TAG_POST_STAY="PreStayMails";
    final public static String MESSAGE_TAG_USER_CONFIRMATION="userConfirmation";
    final public static String MESSAGE_TAG_HOTEL_CONFIRMATION="hotelConfirmation";

    public enum MessageType implements Serializable {

        USER_CONFIRMATION("userConfirmation"), HOTEL_CONFIRMATION("hotelConfirmation"), PRE_STAY("PostStayMails"), POST_STAY("PreStayMails");

        String value;

        private MessageType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static MessageType getFromValue(String value) {
            for(MessageType e: MessageType.values()) {
                if(e.value.equals(value)) {
                    return e;
                }
            }
            return null;
        }
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
