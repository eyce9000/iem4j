
package com.bigfix.schemas.relevance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DashboardVariableIdentifier complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DashboardVariableIdentifier">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dashboardID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="variableName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="databaseID" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DashboardVariableIdentifier", propOrder = {
    "dashboardID",
    "variableName",
    "databaseID"
})
public class DashboardVariableIdentifier {

    @XmlElement(required = true)
    protected String dashboardID;
    @XmlElement(required = true)
    protected String variableName;
    protected Long databaseID;

    /**
     * Gets the value of the dashboardID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDashboardID() {
        return dashboardID;
    }

    /**
     * Sets the value of the dashboardID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDashboardID(String value) {
        this.dashboardID = value;
    }

    /**
     * Gets the value of the variableName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVariableName() {
        return variableName;
    }

    /**
     * Sets the value of the variableName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVariableName(String value) {
        this.variableName = value;
    }

    /**
     * Gets the value of the databaseID property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDatabaseID() {
        return databaseID;
    }

    /**
     * Sets the value of the databaseID property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDatabaseID(Long value) {
        this.databaseID = value;
    }

}
