
package com.bigfix.schemas.bes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Site complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Site">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{}ObjectName"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Domain" type="{}Domain" minOccurs="0"/>
 *         &lt;element name="GlobalReadPermission" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Subscription" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Mode">
 *                     &lt;simpleType>
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *                         &lt;enumeration value="All"/>
 *                         &lt;enumeration value="None"/>
 *                         &lt;enumeration value="AdHoc"/>
 *                         &lt;enumeration value="Custom"/>
 *                       &lt;/restriction>
 *                     &lt;/simpleType>
 *                   &lt;/element>
 *                   &lt;element name="CustomGroup" type="{}GroupRelevance" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="SiteRelevance" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Site", propOrder = {
    "name",
    "description",
    "domain",
    "globalReadPermission",
    "subscription",
    "siteRelevance"
})
@XmlSeeAlso({
    com.bigfix.schemas.bes.BES.CustomSite.class,
    com.bigfix.schemas.bes.BES.ActionSite.class,
    com.bigfix.schemas.bes.BES.ExternalSite.class
})
public class Site {

    @XmlElement(name = "Name", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String name;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "Domain")
    protected String domain;
    @XmlElement(name = "GlobalReadPermission")
    protected Boolean globalReadPermission;
    @XmlElement(name = "Subscription")
    protected Site.Subscription subscription;
    @XmlElement(name = "SiteRelevance")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String siteRelevance;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the domain property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Sets the value of the domain property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDomain(String value) {
        this.domain = value;
    }

    /**
     * Gets the value of the globalReadPermission property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isGlobalReadPermission() {
        return globalReadPermission;
    }

    /**
     * Sets the value of the globalReadPermission property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setGlobalReadPermission(Boolean value) {
        this.globalReadPermission = value;
    }

    /**
     * Gets the value of the subscription property.
     * 
     * @return
     *     possible object is
     *     {@link Site.Subscription }
     *     
     */
    public Site.Subscription getSubscription() {
        return subscription;
    }

    /**
     * Sets the value of the subscription property.
     * 
     * @param value
     *     allowed object is
     *     {@link Site.Subscription }
     *     
     */
    public void setSubscription(Site.Subscription value) {
        this.subscription = value;
    }

    /**
     * Gets the value of the siteRelevance property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiteRelevance() {
        return siteRelevance;
    }

    /**
     * Sets the value of the siteRelevance property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiteRelevance(String value) {
        this.siteRelevance = value;
    }


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
     *         &lt;element name="Mode">
     *           &lt;simpleType>
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
     *               &lt;enumeration value="All"/>
     *               &lt;enumeration value="None"/>
     *               &lt;enumeration value="AdHoc"/>
     *               &lt;enumeration value="Custom"/>
     *             &lt;/restriction>
     *           &lt;/simpleType>
     *         &lt;/element>
     *         &lt;element name="CustomGroup" type="{}GroupRelevance" minOccurs="0"/>
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
        "mode",
        "customGroup"
    })
    public static class Subscription {

        @XmlElement(name = "Mode", required = true)
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        protected String mode;
        @XmlElement(name = "CustomGroup")
        protected GroupRelevance customGroup;

        /**
         * Gets the value of the mode property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMode() {
            return mode;
        }

        /**
         * Sets the value of the mode property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMode(String value) {
            this.mode = value;
        }

        /**
         * Gets the value of the customGroup property.
         * 
         * @return
         *     possible object is
         *     {@link GroupRelevance }
         *     
         */
        public GroupRelevance getCustomGroup() {
            return customGroup;
        }

        /**
         * Sets the value of the customGroup property.
         * 
         * @param value
         *     allowed object is
         *     {@link GroupRelevance }
         *     
         */
        public void setCustomGroup(GroupRelevance value) {
            this.customGroup = value;
        }

    }

}
