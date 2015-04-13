
package com.bigfix.schemas.bes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for MembershipComparison.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MembershipComparison">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *     &lt;enumeration value="IsMember"/>
 *     &lt;enumeration value="IsNotMember"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MembershipComparison")
@XmlEnum
public enum MembershipComparison {

    @XmlEnumValue("IsMember")
    IS_MEMBER("IsMember"),
    @XmlEnumValue("IsNotMember")
    IS_NOT_MEMBER("IsNotMember");
    private final String value;

    MembershipComparison(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MembershipComparison fromValue(String v) {
        for (MembershipComparison c: MembershipComparison.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
