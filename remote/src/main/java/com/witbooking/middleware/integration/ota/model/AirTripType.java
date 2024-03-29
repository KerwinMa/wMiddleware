//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.5-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.08.28 at 02:33:51 PM CEST 
//


package com.witbooking.middleware.integration.ota.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AirTripType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="AirTripType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="OneWay"/>
 *     &lt;enumeration value="OneWayOnly"/>
 *     &lt;enumeration value="Return"/>
 *     &lt;enumeration value="Circle"/>
 *     &lt;enumeration value="OpenJaw"/>
 *     &lt;enumeration value="Other"/>
 *     &lt;enumeration value="Outbound"/>
 *     &lt;enumeration value="OutboundSeasonRoundtrip"/>
 *     &lt;enumeration value="Non-directional"/>
 *     &lt;enumeration value="Inbound"/>
 *     &lt;enumeration value="Roundtrip"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "AirTripType")
@XmlEnum
public enum AirTripType {

    @XmlEnumValue("OneWay")
    ONE_WAY("OneWay"),
    @XmlEnumValue("OneWayOnly")
    ONE_WAY_ONLY("OneWayOnly"),
    @XmlEnumValue("Return")
    RETURN("Return"),
    @XmlEnumValue("Circle")
    CIRCLE("Circle"),
    @XmlEnumValue("OpenJaw")
    OPEN_JAW("OpenJaw"),
    @XmlEnumValue("Other")
    OTHER("Other"),
    @XmlEnumValue("Outbound")
    OUTBOUND("Outbound"),
    @XmlEnumValue("OutboundSeasonRoundtrip")
    OUTBOUND_SEASON_ROUNDTRIP("OutboundSeasonRoundtrip"),
    @XmlEnumValue("Non-directional")
    NON_DIRECTIONAL("Non-directional"),
    @XmlEnumValue("Inbound")
    INBOUND("Inbound"),
    @XmlEnumValue("Roundtrip")
    ROUNDTRIP("Roundtrip");
    private final String value;

    AirTripType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AirTripType fromValue(String v) {
        for (AirTripType c: AirTripType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
