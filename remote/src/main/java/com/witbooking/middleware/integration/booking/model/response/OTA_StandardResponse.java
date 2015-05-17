package com.witbooking.middleware.integration.booking.model.response;

import com.witbooking.middleware.integration.booking.model.Constants;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Jose Francisco Fiorillo < jffiorillo@gmail.com >
 */
public abstract class OTA_StandardResponse implements Serializable {

    @XmlAttribute(name = "xmlns")
    private final String xmlns = Constants.XMLNS;
    @XmlAttribute(name = "xmlns:xsi")
    private final String xmlns_xsi = Constants.XMLNS_XSI;
    @XmlAttribute(name = "xsi:schemaLocation")
    private final String xmlns_schemaLocation = "http://www.opentravel.org/OTA/2003/05 OTA_HotelAvailNotifRS.xsd";
    @XmlAttribute(name = "TimeStamp")
    private Date currentTimestamp;
    @XmlAttribute(name = "Target")
    private String target;
    @XmlAttribute(name = "Version")
    protected String version = Constants.VERSION_1_004;
    /**
     * Warnings can be combined with success messages if the request was still
     * processed.
     */
    @XmlElement(name = "Warnings")
    private OTA_HotelAvailNotifRS.Warnings warnings;
    //private Warnings warnings;
    /**
     * Errors are optional and only used alone, without success/warnings.
     */
    @XmlElement(name = "Errors")
    private OTA_HotelAvailNotifRS.Errors errors;
    private static String RUID;

    public void setRUID(String ruid) {
        this.RUID = ruid;
    }

    public String getRUID() {
        return RUID;
    }

    public Warnings getWarnings() {
        return warnings;
    }

    public Errors getErrors() {
        return errors;
    }

    public OTA_StandardResponse() {
        currentTimestamp = new Date();
        target = Constants.TARGET_PRODUCTION;
    }

    public boolean success() {
        return errors == null;
    }

    @Override
    public String toString() {
        final String status = (errors == null) ? "Success" : "Error";
        String ret = "OTA_StandardResponse{" + "RUID='" + RUID + "', status="
                + status + ", currentTimestamp=" + currentTimestamp + ", target="
                + target;
        if (!warningsEmpty()) {
            ret += ", warnings=" + warnings;
        }
        if (!errorsEmpty()) {
            ret += ", errors=" + errors;
        }
        ret += '}';
        return ret;
    }


    public String toStringStandardResponse() {
        final String status = (errors == null) ? "Success" : "Error";
        String ret = "OTA_StandardResponse{" + "RUID='" + RUID + "', status="
                + status + ", currentTimestamp=" + currentTimestamp + ", target="
                + target;
        if (!warningsEmpty()) {
            ret += ", warnings=" + warnings;
        }
        if (!errorsEmpty()) {
            ret += ", errors=" + errors;
        }
        ret += '}';
        return ret;
    }

    public boolean warningsEmpty() {
        return (warnings == null) || warnings.isEmpty();
    }

    public boolean errorsEmpty() {
        return (errors == null) || errors.isEmpty();
    }

    @XmlRootElement(name = "Warnings")
    public static class Warnings implements Serializable {

        @XmlElement(name = "Warning")
        List<Message> warnings;

        public int size() {
            return warnings.size();
        }

        @Override
        public String toString() {
            return "Warnings{" + "warnings=" + warnings + '}';
        }

        public boolean isEmpty() {
            return (warnings == null) || (warnings.isEmpty());
        }
    }

    @XmlRootElement(name = "Errors")
    public static class Errors implements Serializable {

        @XmlElement(name = "Error")
        List<Message> errors;

        public int size() {
            return errors.size();
        }

        @Override
        public String toString() {
            return "Errors{" + "errors=" + errors + '}';
        }

        public boolean isEmpty() {
            return (errors == null) || (errors.isEmpty());
        }
    }
}
