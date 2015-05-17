
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for sitetype.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="sitetype">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="localuniversal"/>
 *     &lt;enumeration value="placepage"/>
 *     &lt;enumeration value="mapresults"/>
 *     &lt;enumeration value="hotelfinder"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "sitetype")
@XmlEnum
public enum Sitetype {

    @XmlEnumValue("localuniversal")
    LOCALUNIVERSAL("localuniversal"),
    @XmlEnumValue("placepage")
    PLACEPAGE("placepage"),
    @XmlEnumValue("mapresults")
    MAPRESULTS("mapresults"),
    @XmlEnumValue("hotelfinder")
    HOTELFINDER("hotelfinder");
    private final String value;

    Sitetype(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Sitetype fromValue(String v) {
        for (Sitetype c: Sitetype.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
