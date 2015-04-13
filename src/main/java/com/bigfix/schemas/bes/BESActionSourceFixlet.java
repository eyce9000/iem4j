
package com.bigfix.schemas.bes;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for BESActionSourceFixlet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="BESActionSourceFixlet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="Sitename" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *           &lt;element name="SiteID" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;/choice>
 *         &lt;element name="FixletID" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/>
 *         &lt;element name="Action" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BESActionSourceFixlet", propOrder = {
    "sitename",
    "siteID",
    "fixletID",
    "action"
})
public class BESActionSourceFixlet {

    @XmlElement(name = "Sitename")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String sitename;
    @XmlElement(name = "SiteID")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger siteID;
    @XmlElement(name = "FixletID", required = true)
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger fixletID;
    @XmlElement(name = "Action")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String action;

    /**
     * Gets the value of the sitename property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSitename() {
        return sitename;
    }

    /**
     * Sets the value of the sitename property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSitename(String value) {
        this.sitename = value;
    }

    /**
     * Gets the value of the siteID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSiteID() {
        return siteID;
    }

    /**
     * Sets the value of the siteID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSiteID(BigInteger value) {
        this.siteID = value;
    }

    /**
     * Gets the value of the fixletID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getFixletID() {
        return fixletID;
    }

    /**
     * Sets the value of the fixletID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setFixletID(BigInteger value) {
        this.fixletID = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAction() {
        return action;
    }

    /**
     * Sets the value of the action property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAction(String value) {
        this.action = value;
    }

}
