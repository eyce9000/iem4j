
package com.bigfix.schemas.besapi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MastheadController.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MastheadController">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *     &lt;enumeration value="nobody"/>
 *     &lt;enumeration value="client"/>
 *     &lt;enumeration value="console"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MastheadController")
@XmlEnum
public enum MastheadController {

    @XmlEnumValue("nobody")
    NOBODY("nobody"),
    @XmlEnumValue("client")
    CLIENT("client"),
    @XmlEnumValue("console")
    CONSOLE("console");
    private final String value;

    MastheadController(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MastheadController fromValue(String v) {
        for (MastheadController c: MastheadController.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
