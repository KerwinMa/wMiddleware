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
 * <p>Java class for StayUnitType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="StayUnitType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="Minutes"/>
 *     &lt;enumeration value="Hours"/>
 *     &lt;enumeration value="Days"/>
 *     &lt;enumeration value="Months"/>
 *     &lt;enumeration value="MON"/>
 *     &lt;enumeration value="TUES"/>
 *     &lt;enumeration value="WED"/>
 *     &lt;enumeration value="THU"/>
 *     &lt;enumeration value="FRI"/>
 *     &lt;enumeration value="SAT"/>
 *     &lt;enumeration value="SUN"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "StayUnitType")
@XmlEnum
public enum StayUnitType {

    @XmlEnumValue("Minutes")
    MINUTES("Minutes"),
    @XmlEnumValue("Hours")
    HOURS("Hours"),
    @XmlEnumValue("Days")
    DAYS("Days"),
    @XmlEnumValue("Months")
    MONTHS("Months"),
    MON("MON"),
    TUES("TUES"),
    WED("WED"),
    THU("THU"),
    FRI("FRI"),
    SAT("SAT"),
    SUN("SUN");
    private final String value;

    StayUnitType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static StayUnitType fromValue(String v) {
        for (StayUnitType c: StayUnitType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
