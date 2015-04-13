
package com.bigfix.schemas.besapi;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DefaultFixletVisibilityType.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="DefaultFixletVisibilityType">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *     &lt;enumeration value="Visible"/>
 *     &lt;enumeration value="Hidden"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "DefaultFixletVisibilityType")
@XmlEnum
public enum DefaultFixletVisibilityType {

    @XmlEnumValue("Visible")
    VISIBLE("Visible"),
    @XmlEnumValue("Hidden")
    HIDDEN("Hidden");
    private final String value;

    DefaultFixletVisibilityType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static DefaultFixletVisibilityType fromValue(String v) {
        for (DefaultFixletVisibilityType c: DefaultFixletVisibilityType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
