
package com.bigfix.schemas.bes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Comparison.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="Comparison">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *     &lt;enumeration value="Contains"/>
 *     &lt;enumeration value="DoesNotContain"/>
 *     &lt;enumeration value="Equals"/>
 *     &lt;enumeration value="DoesNotEqual"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "Comparison")
@XmlEnum
public enum Comparison {

    @XmlEnumValue("Contains")
    CONTAINS("Contains"),
    @XmlEnumValue("DoesNotContain")
    DOES_NOT_CONTAIN("DoesNotContain"),
    @XmlEnumValue("Equals")
    EQUALS("Equals"),
    @XmlEnumValue("DoesNotEqual")
    DOES_NOT_EQUAL("DoesNotEqual");
    private final String value;

    Comparison(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static Comparison fromValue(String v) {
        for (Comparison c: Comparison.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
