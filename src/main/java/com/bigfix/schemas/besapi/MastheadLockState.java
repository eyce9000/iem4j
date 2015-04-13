
package com.bigfix.schemas.besapi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MastheadLockState.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MastheadLockState">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *     &lt;enumeration value="on"/>
 *     &lt;enumeration value="off"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MastheadLockState")
@XmlEnum
public enum MastheadLockState {

    @XmlEnumValue("on")
    ON("on"),
    @XmlEnumValue("off")
    OFF("off");
    private final String value;

    MastheadLockState(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MastheadLockState fromValue(String v) {
        for (MastheadLockState c: MastheadLockState.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
