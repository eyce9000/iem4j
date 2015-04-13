
package com.bigfix.schemas.bes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SitePermissionString.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="SitePermissionString">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *     &lt;enumeration value="Owner"/>
 *     &lt;enumeration value="Reader"/>
 *     &lt;enumeration value="Writer"/>
 *     &lt;enumeration value="None"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "SitePermissionString")
@XmlEnum
public enum SitePermissionString {

    @XmlEnumValue("Owner")
    OWNER("Owner"),
    @XmlEnumValue("Reader")
    READER("Reader"),
    @XmlEnumValue("Writer")
    WRITER("Writer"),
    @XmlEnumValue("None")
    NONE("None");
    private final String value;

    SitePermissionString(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SitePermissionString fromValue(String v) {
        for (SitePermissionString c: SitePermissionString.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
