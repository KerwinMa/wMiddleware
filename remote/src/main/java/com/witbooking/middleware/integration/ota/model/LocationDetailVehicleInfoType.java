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
 * <p>Java class for LocationDetailVehicleInfoType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="LocationDetailVehicleInfoType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="GeneralInformation"/>
 *     &lt;enumeration value="Disclaimer"/>
 *     &lt;enumeration value="AdvancedBooking"/>
 *     &lt;enumeration value="NonSmokingVehicles"/>
 *     &lt;enumeration value="SpecialityVehicles"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "LocationDetailVehicleInfoType")
@XmlEnum
public enum LocationDetailVehicleInfoType {

    @XmlEnumValue("GeneralInformation")
    GENERAL_INFORMATION("GeneralInformation"),
    @XmlEnumValue("Disclaimer")
    DISCLAIMER("Disclaimer"),
    @XmlEnumValue("AdvancedBooking")
    ADVANCED_BOOKING("AdvancedBooking"),
    @XmlEnumValue("NonSmokingVehicles")
    NON_SMOKING_VEHICLES("NonSmokingVehicles"),
    @XmlEnumValue("SpecialityVehicles")
    SPECIALITY_VEHICLES("SpecialityVehicles");
    private final String value;

    LocationDetailVehicleInfoType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static LocationDetailVehicleInfoType fromValue(String v) {
        for (LocationDetailVehicleInfoType c: LocationDetailVehicleInfoType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
