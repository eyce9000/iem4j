
package com.bigfix.schemas.besapi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelevancePlurality.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RelevancePlurality">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *     &lt;enumeration value="Plural"/>
 *     &lt;enumeration value="Singular"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "RelevancePlurality")
@XmlEnum
public enum RelevancePlurality {

    @XmlEnumValue("Plural")
    PLURAL("Plural"),
    @XmlEnumValue("Singular")
    SINGULAR("Singular");
    private final String value;

    RelevancePlurality(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static RelevancePlurality fromValue(String v) {
        for (RelevancePlurality c: RelevancePlurality.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
