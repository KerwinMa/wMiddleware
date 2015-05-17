package com.witbooking.middleware.integration.mandrill.model;


import java.io.Serializable;

/**
 * Created by mongoose on 1/20/15.
 */
public class Event {
    private String _id;
    private String event;
    private String ts;
    private Message message;

    public enum MandrillMessageEventType implements Serializable, Comparable<MandrillMessageEventType> {

        SENT("sent"), QUEUED("queued"), SCHEDULED("scheduled"), REJECTED("rejected"), INVALID("invalid");

        String value;

        private MandrillMessageEventType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static MandrillMessageEventType getFromValue(String value) {
            for(MandrillMessageEventType e: MandrillMessageEventType.values()) {
                if(e.value.equals(value)) {
                    return e;
                }
            }
            return null;
        }
    }

    public enum EventType implements Serializable, Comparable<EventType> {

        NOT_SENT("not_set"), SEND("send"), INVALID("invalid"), DEFERRAL("deferral"), HARD_BOUNCE("hard_bounce"), SOFT_BOUNCE("soft_bounce"), SPAM("spam"), UNSUB("unsub"), REJECT("reject"), OPEN("open"), CLICK("click");

        String value;

        private EventType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public static EventType convertFromMandrillMessageEventType(MandrillMessageEventType value) {
            if(value.compareTo(MandrillMessageEventType.SENT)==0){
                return SEND;
            }else if(value.compareTo(MandrillMessageEventType.QUEUED)==0){
                return NOT_SENT;
            }else if(value.compareTo(MandrillMessageEventType.SCHEDULED)==0){
                return NOT_SENT;
            }else if(value.compareTo(MandrillMessageEventType.REJECTED)==0){
                return REJECT;
            }else if(value.compareTo(MandrillMessageEventType.INVALID)==0){
                return INVALID;
            }
            return null;
        }

        public static EventType getFromValue(String value) {
            for(EventType e: EventType.values()) {
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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
