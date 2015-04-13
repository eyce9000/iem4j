
package com.bigfix.schemas.bes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for Action complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Action">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Title" type="{}ObjectName"/>
 *         &lt;element name="Relevance" type="{}RelevanceString"/>
 *         &lt;element name="ActionScript" type="{}ActionScript"/>
 *         &lt;element name="SuccessCriteria" type="{}ActionSuccessCriteria" minOccurs="0"/>
 *         &lt;element name="SuccessCriteriaLocked" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Parameter" type="{}BESActionParameter" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SecureParameter" type="{}BESActionParameter" maxOccurs="unbounded" minOccurs="0"/>
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
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Action", propOrder = {
    "title",
    "relevance",
    "actionScript",
    "successCriteria",
    "successCriteriaLocked",
    "parameter",
    "secureParameter",
    "mimeField"
})
@XmlSeeAlso({
    SingleAction.class,
    com.bigfix.schemas.bes.MultipleActionGroup.MemberAction.class
})
public class Action {

    @XmlElement(name = "Title", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String title;
    @XmlElement(name = "Relevance", required = true)
    protected RelevanceString relevance;
    @XmlElement(name = "ActionScript", required = true)
    protected ActionScript actionScript;
    @XmlElement(name = "SuccessCriteria")
    protected ActionSuccessCriteria successCriteria;
    @XmlElement(name = "SuccessCriteriaLocked")
    protected Boolean successCriteriaLocked;
    @XmlElement(name = "Parameter")
    protected List<BESActionParameter> parameter;
    @XmlElement(name = "SecureParameter")
    protected List<BESActionParameter> secureParameter;
    @XmlElement(name = "MIMEField")
    protected List<Action.MIMEField> mimeField;

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
     * Gets the value of the successCriteriaLocked property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSuccessCriteriaLocked() {
        return successCriteriaLocked;
    }

    /**
     * Sets the value of the successCriteriaLocked property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSuccessCriteriaLocked(Boolean value) {
        this.successCriteriaLocked = value;
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
     * {@link Action.MIMEField }
     * 
     * 
     */
    public List<Action.MIMEField> getMIMEField() {
        if (mimeField == null) {
            mimeField = new ArrayList<Action.MIMEField>();
        }
        return this.mimeField;
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

}
