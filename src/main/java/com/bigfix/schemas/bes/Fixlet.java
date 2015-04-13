
package com.bigfix.schemas.bes;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Fixlet complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Fixlet">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Title" type="{}ObjectName"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;choice>
 *           &lt;element name="Relevance" type="{}RelevanceString" maxOccurs="unbounded"/>
 *           &lt;element name="GroupRelevance" type="{}GroupRelevance"/>
 *         &lt;/choice>
 *         &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="WizardData" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="DataStore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="StartURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="SkipUI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="DownloadSize" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" minOccurs="0"/>
 *         &lt;element name="Source" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="SourceID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="SourceReleaseDate" type="{}NonNegativeDate" minOccurs="0"/>
 *         &lt;element name="SourceSeverity" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="CVENames" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="SANSID" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="MIMEField" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *                   &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="Domain" type="{}Domain" minOccurs="0"/>
 *         &lt;element name="Delay" type="{}NonNegativeTimeInterval" minOccurs="0"/>
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
@XmlType(name = "Fixlet", propOrder = {
    "title",
    "description",
    "relevance",
    "groupRelevance",
    "category",
    "wizardData",
    "downloadSize",
    "source",
    "sourceID",
    "sourceReleaseDate",
    "sourceSeverity",
    "cveNames",
    "sansid",
    "mimeField",
    "domain",
    "delay"
})
@XmlSeeAlso({
    FixletWithActions.class,
    Analysis.class,
    Baseline.class
})
public class Fixlet {

    @XmlElement(name = "Title", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String title;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "Relevance")
    protected List<RelevanceString> relevance;
    @XmlElement(name = "GroupRelevance")
    protected GroupRelevance groupRelevance;
    @XmlElement(name = "Category")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String category;
    @XmlElement(name = "WizardData")
    protected Fixlet.WizardData wizardData;
    @XmlElement(name = "DownloadSize")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger downloadSize;
    @XmlElement(name = "Source")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String source;
    @XmlElement(name = "SourceID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String sourceID;
    @XmlElement(name = "SourceReleaseDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar sourceReleaseDate;
    @XmlElement(name = "SourceSeverity")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String sourceSeverity;
    @XmlElement(name = "CVENames")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String cveNames;
    @XmlElement(name = "SANSID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String sansid;
    @XmlElement(name = "MIMEField")
    protected List<Fixlet.MIMEField> mimeField;
    @XmlElement(name = "Domain")
    protected String domain;
    @XmlElement(name = "Delay")
    protected Duration delay;
    @XmlAttribute(name = "SkipUI")
    protected Boolean skipUI;

    /**
     * Gets the value of the title property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitle(String value) {
        this.title = value;
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
     * Gets the value of the relevance property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relevance property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelevance().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelevanceString }
     * 
     * 
     */
    public List<RelevanceString> getRelevance() {
        if (relevance == null) {
            relevance = new ArrayList<RelevanceString>();
        }
        return this.relevance;
    }

    /**
     * Gets the value of the groupRelevance property.
     * 
     * @return
     *     possible object is
     *     {@link GroupRelevance }
     *     
     */
    public GroupRelevance getGroupRelevance() {
        return groupRelevance;
    }

    /**
     * Sets the value of the groupRelevance property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupRelevance }
     *     
     */
    public void setGroupRelevance(GroupRelevance value) {
        this.groupRelevance = value;
    }

    /**
     * Gets the value of the category property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the value of the category property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategory(String value) {
        this.category = value;
    }

    /**
     * Gets the value of the wizardData property.
     * 
     * @return
     *     possible object is
     *     {@link Fixlet.WizardData }
     *     
     */
    public Fixlet.WizardData getWizardData() {
        return wizardData;
    }

    /**
     * Sets the value of the wizardData property.
     * 
     * @param value
     *     allowed object is
     *     {@link Fixlet.WizardData }
     *     
     */
    public void setWizardData(Fixlet.WizardData value) {
        this.wizardData = value;
    }

    /**
     * Gets the value of the downloadSize property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDownloadSize() {
        return downloadSize;
    }

    /**
     * Sets the value of the downloadSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDownloadSize(BigInteger value) {
        this.downloadSize = value;
    }

    /**
     * Gets the value of the source property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSource() {
        return source;
    }

    /**
     * Sets the value of the source property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSource(String value) {
        this.source = value;
    }

    /**
     * Gets the value of the sourceID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceID() {
        return sourceID;
    }

    /**
     * Sets the value of the sourceID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceID(String value) {
        this.sourceID = value;
    }

    /**
     * Gets the value of the sourceReleaseDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getSourceReleaseDate() {
        return sourceReleaseDate;
    }

    /**
     * Sets the value of the sourceReleaseDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setSourceReleaseDate(XMLGregorianCalendar value) {
        this.sourceReleaseDate = value;
    }

    /**
     * Gets the value of the sourceSeverity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceSeverity() {
        return sourceSeverity;
    }

    /**
     * Sets the value of the sourceSeverity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceSeverity(String value) {
        this.sourceSeverity = value;
    }

    /**
     * Gets the value of the cveNames property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCVENames() {
        return cveNames;
    }

    /**
     * Sets the value of the cveNames property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCVENames(String value) {
        this.cveNames = value;
    }

    /**
     * Gets the value of the sansid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSANSID() {
        return sansid;
    }

    /**
     * Sets the value of the sansid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSANSID(String value) {
        this.sansid = value;
    }

    /**
     * Gets the value of the mimeField property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mimeField property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMIMEField().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Fixlet.MIMEField }
     * 
     * 
     */
    public List<Fixlet.MIMEField> getMIMEField() {
        if (mimeField == null) {
            mimeField = new ArrayList<Fixlet.MIMEField>();
        }
        return this.mimeField;
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
     *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
     *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
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
        "name",
        "value"
    })
    public static class MIMEField {

        @XmlElement(name = "Name", required = true)
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String name;
        @XmlElement(name = "Value", required = true)
        @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
        @XmlSchemaType(name = "normalizedString")
        protected String value;

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
         * Gets the value of the value property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

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
     *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="DataStore" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="StartURL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="SkipUI" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "name",
        "dataStore",
        "startURL",
        "skipUI"
    })
    public static class WizardData {

        @XmlElement(name = "Name")
        protected String name;
        @XmlElement(name = "DataStore")
        protected String dataStore;
        @XmlElement(name = "StartURL")
        protected String startURL;
        @XmlElement(name = "SkipUI")
        protected String skipUI;

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
         * Gets the value of the dataStore property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDataStore() {
            return dataStore;
        }

        /**
         * Sets the value of the dataStore property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDataStore(String value) {
            this.dataStore = value;
        }

        /**
         * Gets the value of the startURL property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getStartURL() {
            return startURL;
        }

        /**
         * Sets the value of the startURL property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setStartURL(String value) {
            this.startURL = value;
        }

        /**
         * Gets the value of the skipUI property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSkipUI() {
            return skipUI;
        }

        /**
         * Sets the value of the skipUI property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSkipUI(String value) {
            this.skipUI = value;
        }

    }

}
