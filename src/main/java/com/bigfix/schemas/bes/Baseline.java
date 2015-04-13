
package com.bigfix.schemas.bes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;


/**
 * <p>Java class for Baseline complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Baseline">
 *   &lt;complexContent>
 *     &lt;extension base="{}Fixlet">
 *       &lt;sequence>
 *         &lt;element name="BaselineComponentCollection">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="BaselineComponentGroup" maxOccurs="unbounded" minOccurs="0">
 *                     &lt;complexType>
 *                       &lt;complexContent>
 *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                           &lt;sequence>
 *                             &lt;element name="BaselineComponent" maxOccurs="unbounded" minOccurs="0">
 *                               &lt;complexType>
 *                                 &lt;complexContent>
 *                                   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                                     &lt;sequence>
 *                                       &lt;element name="ActionScript" type="{}ActionScript"/>
 *                                       &lt;element name="SuccessCriteria" type="{}ActionSuccessCriteria" minOccurs="0"/>
 *                                       &lt;element name="Relevance" type="{}RelevanceString"/>
 *                                       &lt;element name="Delay" type="{}NonNegativeTimeInterval" minOccurs="0"/>
 *                                     &lt;/sequence>
 *                                     &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                                     &lt;attribute name="ActionName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                                     &lt;attribute name="IncludeInRelevance" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *                                     &lt;attribute name="SourceSiteURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
 *                                     &lt;attribute name="SourceID" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
 *                                   &lt;/restriction>
 *                                 &lt;/complexContent>
 *                               &lt;/complexType>
 *                             &lt;/element>
 *                           &lt;/sequence>
 *                           &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *                         &lt;/restriction>
 *                       &lt;/complexContent>
 *                     &lt;/complexType>
 *                   &lt;/element>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Settings" type="{}ActionSettings" minOccurs="0"/>
 *         &lt;element name="SettingsLocks" type="{}ActionSettingsLocks" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Baseline", propOrder = {
    "baselineComponentCollection",
    "settings",
    "settingsLocks"
})
public class Baseline
    extends Fixlet
{

    @XmlElement(name = "BaselineComponentCollection", required = true)
    protected Baseline.BaselineComponentCollection baselineComponentCollection;
    @XmlElement(name = "Settings")
    protected ActionSettings settings;
    @XmlElement(name = "SettingsLocks")
    protected ActionSettingsLocks settingsLocks;

    /**
     * Gets the value of the baselineComponentCollection property.
     * 
     * @return
     *     possible object is
     *     {@link Baseline.BaselineComponentCollection }
     *     
     */
    public Baseline.BaselineComponentCollection getBaselineComponentCollection() {
        return baselineComponentCollection;
    }

    /**
     * Sets the value of the baselineComponentCollection property.
     * 
     * @param value
     *     allowed object is
     *     {@link Baseline.BaselineComponentCollection }
     *     
     */
    public void setBaselineComponentCollection(Baseline.BaselineComponentCollection value) {
        this.baselineComponentCollection = value;
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
     * Gets the value of the settingsLocks property.
     * 
     * @return
     *     possible object is
     *     {@link ActionSettingsLocks }
     *     
     */
    public ActionSettingsLocks getSettingsLocks() {
        return settingsLocks;
    }

    /**
     * Sets the value of the settingsLocks property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionSettingsLocks }
     *     
     */
    public void setSettingsLocks(ActionSettingsLocks value) {
        this.settingsLocks = value;
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
     *         &lt;element name="BaselineComponentGroup" maxOccurs="unbounded" minOccurs="0">
     *           &lt;complexType>
     *             &lt;complexContent>
     *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                 &lt;sequence>
     *                   &lt;element name="BaselineComponent" maxOccurs="unbounded" minOccurs="0">
     *                     &lt;complexType>
     *                       &lt;complexContent>
     *                         &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *                           &lt;sequence>
     *                             &lt;element name="ActionScript" type="{}ActionScript"/>
     *                             &lt;element name="SuccessCriteria" type="{}ActionSuccessCriteria" minOccurs="0"/>
     *                             &lt;element name="Relevance" type="{}RelevanceString"/>
     *                             &lt;element name="Delay" type="{}NonNegativeTimeInterval" minOccurs="0"/>
     *                           &lt;/sequence>
     *                           &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *                           &lt;attribute name="ActionName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *                           &lt;attribute name="IncludeInRelevance" type="{http://www.w3.org/2001/XMLSchema}boolean" />
     *                           &lt;attribute name="SourceSiteURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
     *                           &lt;attribute name="SourceID" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
     *                         &lt;/restriction>
     *                       &lt;/complexContent>
     *                     &lt;/complexType>
     *                   &lt;/element>
     *                 &lt;/sequence>
     *                 &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
     *               &lt;/restriction>
     *             &lt;/complexContent>
     *           &lt;/complexType>
     *         &lt;/element>
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
        "baselineComponentGroup"
    })
    public static class BaselineComponentCollection {

        @XmlElement(name = "BaselineComponentGroup")
        protected List<Baseline.BaselineComponentCollection.BaselineComponentGroup> baselineComponentGroup;

        /**
         * Gets the value of the baselineComponentGroup property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the baselineComponentGroup property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getBaselineComponentGroup().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Baseline.BaselineComponentCollection.BaselineComponentGroup }
         * 
         * 
         */
        public List<Baseline.BaselineComponentCollection.BaselineComponentGroup> getBaselineComponentGroup() {
            if (baselineComponentGroup == null) {
                baselineComponentGroup = new ArrayList<Baseline.BaselineComponentCollection.BaselineComponentGroup>();
            }
            return this.baselineComponentGroup;
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
         *         &lt;element name="BaselineComponent" maxOccurs="unbounded" minOccurs="0">
         *           &lt;complexType>
         *             &lt;complexContent>
         *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
         *                 &lt;sequence>
         *                   &lt;element name="ActionScript" type="{}ActionScript"/>
         *                   &lt;element name="SuccessCriteria" type="{}ActionSuccessCriteria" minOccurs="0"/>
         *                   &lt;element name="Relevance" type="{}RelevanceString"/>
         *                   &lt;element name="Delay" type="{}NonNegativeTimeInterval" minOccurs="0"/>
         *                 &lt;/sequence>
         *                 &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
         *                 &lt;attribute name="ActionName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
         *                 &lt;attribute name="IncludeInRelevance" type="{http://www.w3.org/2001/XMLSchema}boolean" />
         *                 &lt;attribute name="SourceSiteURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
         *                 &lt;attribute name="SourceID" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
         *               &lt;/restriction>
         *             &lt;/complexContent>
         *           &lt;/complexType>
         *         &lt;/element>
         *       &lt;/sequence>
         *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
         *     &lt;/restriction>
         *   &lt;/complexContent>
         * &lt;/complexType>
         * </pre>
         * 
         * 
         */
        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "baselineComponent"
        })
        public static class BaselineComponentGroup {

            @XmlElement(name = "BaselineComponent")
            protected List<Baseline.BaselineComponentCollection.BaselineComponentGroup.BaselineComponent> baselineComponent;
            @XmlAttribute(name = "Name")
            @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
            @XmlSchemaType(name = "normalizedString")
            protected String name;

            /**
             * Gets the value of the baselineComponent property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the baselineComponent property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getBaselineComponent().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Baseline.BaselineComponentCollection.BaselineComponentGroup.BaselineComponent }
             * 
             * 
             */
            public List<Baseline.BaselineComponentCollection.BaselineComponentGroup.BaselineComponent> getBaselineComponent() {
                if (baselineComponent == null) {
                    baselineComponent = new ArrayList<Baseline.BaselineComponentCollection.BaselineComponentGroup.BaselineComponent>();
                }
                return this.baselineComponent;
            }

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
             * <p>Java class for anonymous complex type.
             * 
             * <p>The following schema fragment specifies the expected content contained within this class.
             * 
             * <pre>
             * &lt;complexType>
             *   &lt;complexContent>
             *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
             *       &lt;sequence>
             *         &lt;element name="ActionScript" type="{}ActionScript"/>
             *         &lt;element name="SuccessCriteria" type="{}ActionSuccessCriteria" minOccurs="0"/>
             *         &lt;element name="Relevance" type="{}RelevanceString"/>
             *         &lt;element name="Delay" type="{}NonNegativeTimeInterval" minOccurs="0"/>
             *       &lt;/sequence>
             *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
             *       &lt;attribute name="ActionName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
             *       &lt;attribute name="IncludeInRelevance" type="{http://www.w3.org/2001/XMLSchema}boolean" />
             *       &lt;attribute name="SourceSiteURL" type="{http://www.w3.org/2001/XMLSchema}anyURI" />
             *       &lt;attribute name="SourceID" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" />
             *     &lt;/restriction>
             *   &lt;/complexContent>
             * &lt;/complexType>
             * </pre>
             * 
             * 
             */
            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "actionScript",
                "successCriteria",
                "relevance",
                "delay"
            })
            public static class BaselineComponent {

                @XmlElement(name = "ActionScript", required = true)
                protected ActionScript actionScript;
                @XmlElement(name = "SuccessCriteria")
                protected ActionSuccessCriteria successCriteria;
                @XmlElement(name = "Relevance", required = true)
                protected RelevanceString relevance;
                @XmlElement(name = "Delay")
                protected Duration delay;
                @XmlAttribute(name = "Name")
                @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
                @XmlSchemaType(name = "normalizedString")
                protected String name;
                @XmlAttribute(name = "ActionName")
                @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
                @XmlSchemaType(name = "normalizedString")
                protected String actionName;
                @XmlAttribute(name = "IncludeInRelevance")
                protected Boolean includeInRelevance;
                @XmlAttribute(name = "SourceSiteURL")
                @XmlSchemaType(name = "anyURI")
                protected String sourceSiteURL;
                @XmlAttribute(name = "SourceID")
                @XmlSchemaType(name = "nonNegativeInteger")
                protected BigInteger sourceID;

                /**
                 * Gets the value of the actionScript property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ActionScript }
                 *     
                 */
                public ActionScript getActionScript() {
                    return actionScript;
                }

                /**
                 * Sets the value of the actionScript property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ActionScript }
                 *     
                 */
                public void setActionScript(ActionScript value) {
                    this.actionScript = value;
                }

                /**
                 * Gets the value of the successCriteria property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link ActionSuccessCriteria }
                 *     
                 */
                public ActionSuccessCriteria getSuccessCriteria() {
                    return successCriteria;
                }

                /**
                 * Sets the value of the successCriteria property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link ActionSuccessCriteria }
                 *     
                 */
                public void setSuccessCriteria(ActionSuccessCriteria value) {
                    this.successCriteria = value;
                }

                /**
                 * Gets the value of the relevance property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link RelevanceString }
                 *     
                 */
                public RelevanceString getRelevance() {
                    return relevance;
                }

                /**
                 * Sets the value of the relevance property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link RelevanceString }
                 *     
                 */
                public void setRelevance(RelevanceString value) {
                    this.relevance = value;
                }

                /**
                 * Gets the value of the delay property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Duration }
                 *     
                 */
                public Duration getDelay() {
                    return delay;
                }

                /**
                 * Sets the value of the delay property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Duration }
                 *     
                 */
                public void setDelay(Duration value) {
                    this.delay = value;
                }

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
                 * Gets the value of the actionName property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getActionName() {
                    return actionName;
                }

                /**
                 * Sets the value of the actionName property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setActionName(String value) {
                    this.actionName = value;
                }

                /**
                 * Gets the value of the includeInRelevance property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link Boolean }
                 *     
                 */
                public Boolean isIncludeInRelevance() {
                    return includeInRelevance;
                }

                /**
                 * Sets the value of the includeInRelevance property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link Boolean }
                 *     
                 */
                public void setIncludeInRelevance(Boolean value) {
                    this.includeInRelevance = value;
                }

                /**
                 * Gets the value of the sourceSiteURL property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getSourceSiteURL() {
                    return sourceSiteURL;
                }

                /**
                 * Sets the value of the sourceSiteURL property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setSourceSiteURL(String value) {
                    this.sourceSiteURL = value;
                }

                /**
                 * Gets the value of the sourceID property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link BigInteger }
                 *     
                 */
                public BigInteger getSourceID() {
                    return sourceID;
                }

                /**
                 * Sets the value of the sourceID property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link BigInteger }
                 *     
                 */
                public void setSourceID(BigInteger value) {
                    this.sourceID = value;
                }

            }

        }

    }

}
