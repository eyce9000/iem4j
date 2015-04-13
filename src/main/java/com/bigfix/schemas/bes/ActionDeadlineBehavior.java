
package com.bigfix.schemas.bes;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActionDeadlineBehavior.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActionDeadlineBehavior">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="ForceToRun"/>
 *     &lt;enumeration value="RunAutomatically"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActionDeadlineBehavior")
@XmlEnum
public enum ActionDeadlineBehavior {

    @XmlEnumValue("ForceToRun")
    FORCE_TO_RUN("ForceToRun"),
    @XmlEnumValue("RunAutomatically")
    RUN_AUTOMATICALLY("RunAutomatically");
    private final String value;

    ActionDeadlineBehavior(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActionDeadlineBehavior fromValue(String v) {
        for (ActionDeadlineBehavior c: ActionDeadlineBehavior.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
