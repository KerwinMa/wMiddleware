
package com.witbooking.middleware.integration.google.model.hpa;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for chargeCurrencyType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="chargeCurrencyType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="web"/>
 *     &lt;enumeration value="hotel"/>
 *     &lt;enumeration value="deposit"/>
 *     &lt;enumeration value="installment"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "chargeCurrencyType")
@XmlEnum
public enum ChargeCurrencyType {

    @XmlEnumValue("web")
    WEB("web"),
    @XmlEnumValue("hotel")
    HOTEL("hotel"),
    @XmlEnumValue("deposit")
    DEPOSIT("deposit"),
    @XmlEnumValue("installment")
    INSTALLMENT("installment");
    private final String value;

    ChargeCurrencyType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChargeCurrencyType fromValue(String v) {
        for (ChargeCurrencyType c: ChargeCurrencyType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
