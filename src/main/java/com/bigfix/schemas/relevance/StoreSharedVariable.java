
package com.bigfix.schemas.relevance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dashboardVariableIdentifier" type="{http://schemas.bigfix.com/Relevance}DashboardVariableIdentifier"/>
 *         &lt;element name="variableValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "dashboardVariableIdentifier",
    "variableValue"
})
@XmlRootElement(name = "StoreSharedVariable")
public class StoreSharedVariable {

    @XmlElement(required = true)
    protected DashboardVariableIdentifier dashboardVariableIdentifier;
    @XmlElement(required = true)
    protected String variableValue;

    /**
     * Gets the value of the dashboardVariableIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link DashboardVariableIdentifier }
     *     
     */
    public DashboardVariableIdentifier getDashboardVariableIdentifier() {
        return dashboardVariableIdentifier;
    }

    /**
     * Sets the value of the dashboardVariableIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link DashboardVariableIdentifier }
     *     
     */
    public void setDashboardVariableIdentifier(DashboardVariableIdentifier value) {
        this.dashboardVariableIdentifier = value;
    }

    /**
     * Gets the value of the variableValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariableValue() {
        return variableValue;
    }

    /**
     * Sets the value of the variableValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariableValue(String value) {
        this.variableValue = value;
    }

}
