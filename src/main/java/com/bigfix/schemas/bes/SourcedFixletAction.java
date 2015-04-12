//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.04.12 at 02:23:24 PM CDT 
//


package com.bigfix.schemas.bes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SourcedFixletAction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SourcedFixletAction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SourceFixlet" type="{}BESActionSourceFixlet"/>
 *         &lt;element name="Target" type="{}BESActionTarget" minOccurs="0"/>
 *         &lt;element name="Parameter" type="{}BESActionParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SecureParameter" type="{}BESActionParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Settings" type="{}ActionSettings" minOccurs="0"/>
 *         &lt;element name="IsUrgent" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="SkipUI" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SourcedFixletAction", propOrder = {
    "sourceFixlet",
    "target",
    "parameter",
    "secureParameter",
    "settings",
    "isUrgent"
})
public class SourcedFixletAction {

    @XmlElement(name = "SourceFixlet", required = true)
    protected BESActionSourceFixlet sourceFixlet;
    @XmlElement(name = "Target")
    protected BESActionTarget target;
    @XmlElement(name = "Parameter")
    protected List<BESActionParameter> parameter;
    @XmlElement(name = "SecureParameter")
    protected List<BESActionParameter> secureParameter;
    @XmlElement(name = "Settings")
    protected ActionSettings settings;
    @XmlElement(name = "IsUrgent")
    protected Boolean isUrgent;
    @XmlAttribute(name = "SkipUI")
    protected Boolean skipUI;

    /**
     * Gets the value of the sourceFixlet property.
     * 
     * @return
     *     possible object is
     *     {@link BESActionSourceFixlet }
     *     
     */
    public BESActionSourceFixlet getSourceFixlet() {
        return sourceFixlet;
    }

    /**
     * Sets the value of the sourceFixlet property.
     * 
     * @param value
     *     allowed object is
     *     {@link BESActionSourceFixlet }
     *     
     */
    public void setSourceFixlet(BESActionSourceFixlet value) {
        this.sourceFixlet = value;
    }

    /**
     * Gets the value of the target property.
     * 
     * @return
     *     possible object is
     *     {@link BESActionTarget }
     *     
     */
    public BESActionTarget getTarget() {
        return target;
    }

    /**
     * Sets the value of the target property.
     * 
     * @param value
     *     allowed object is
     *     {@link BESActionTarget }
     *     
     */
    public void setTarget(BESActionTarget value) {
        this.target = value;
    }

    /**
     * Gets the value of the parameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the parameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BESActionParameter }
     * 
     * 
     */
    public List<BESActionParameter> getParameter() {
        if (parameter == null) {
            parameter = new ArrayList<BESActionParameter>();
        }
        return this.parameter;
    }

    /**
     * Gets the value of the secureParameter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the secureParameter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSecureParameter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link BESActionParameter }
     * 
     * 
     */
    public List<BESActionParameter> getSecureParameter() {
        if (secureParameter == null) {
            secureParameter = new ArrayList<BESActionParameter>();
        }
        return this.secureParameter;
    }

    /**
     * Gets the value of the settings property.
     * 
     * @return
     *     possible object is
     *     {@link ActionSettings }
     *     
     */
    public ActionSettings getSettings() {
        return settings;
    }

    /**
     * Sets the value of the settings property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionSettings }
     *     
     */
    public void setSettings(ActionSettings value) {
        this.settings = value;
    }

    /**
     * Gets the value of the isUrgent property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsUrgent() {
        return isUrgent;
    }

    /**
     * Sets the value of the isUrgent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsUrgent(Boolean value) {
        this.isUrgent = value;
    }

    /**
     * Gets the value of the skipUI property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSkipUI() {
        return skipUI;
    }

    /**
     * Sets the value of the skipUI property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSkipUI(Boolean value) {
        this.skipUI = value;
    }

}
