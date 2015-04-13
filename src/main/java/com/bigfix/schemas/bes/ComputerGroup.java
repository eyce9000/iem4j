
package com.bigfix.schemas.bes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for ComputerGroup complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ComputerGroup">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Title" type="{}ObjectName"/>
 *         &lt;element name="Domain" type="{}Domain" minOccurs="0"/>
 *         &lt;element name="JoinByIntersection" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="IsDynamic" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="EvaluateOnClient" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;choice maxOccurs="unbounded">
 *           &lt;element name="SearchComponentRelevance" type="{}SearchComponentRelevance"/>
 *           &lt;element name="SearchComponentPropertyReference" type="{}SearchComponentPropertyReference"/>
 *           &lt;element name="SearchComponentGroupReference" type="{}SearchComponentGroupReference"/>
 *         &lt;/choice>
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
@XmlType(name = "ComputerGroup", propOrder = {
    "title",
    "domain",
    "joinByIntersection",
    "isDynamic",
    "evaluateOnClient",
    "searchComponent"
})
public class ComputerGroup {

    @XmlElement(name = "Title", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String title;
    @XmlElement(name = "Domain")
    protected String domain;
    @XmlElement(name = "JoinByIntersection")
    protected boolean joinByIntersection;
    @XmlElement(name = "IsDynamic")
    protected Boolean isDynamic;
    @XmlElement(name = "EvaluateOnClient")
    protected Boolean evaluateOnClient;
    @XmlElements({
        @XmlElement(name = "SearchComponentRelevance", type = SearchComponentRelevance.class),
        @XmlElement(name = "SearchComponentPropertyReference", type = SearchComponentPropertyReference.class),
        @XmlElement(name = "SearchComponentGroupReference", type = SearchComponentGroupReference.class)
    })
    protected List<SearchComponent> searchComponent;
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
     * Gets the value of the joinByIntersection property.
     * 
     */
    public boolean isJoinByIntersection() {
        return joinByIntersection;
    }

    /**
     * Sets the value of the joinByIntersection property.
     * 
     */
    public void setJoinByIntersection(boolean value) {
        this.joinByIntersection = value;
    }

    /**
     * Gets the value of the isDynamic property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsDynamic() {
        return isDynamic;
    }

    /**
     * Sets the value of the isDynamic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsDynamic(Boolean value) {
        this.isDynamic = value;
    }

    /**
     * Gets the value of the evaluateOnClient property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEvaluateOnClient() {
        return evaluateOnClient;
    }

    /**
     * Sets the value of the evaluateOnClient property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEvaluateOnClient(Boolean value) {
        this.evaluateOnClient = value;
    }

    /**
     * Gets the value of the searchComponent property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the searchComponent property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSearchComponent().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SearchComponentRelevance }
     * {@link SearchComponentPropertyReference }
     * {@link SearchComponentGroupReference }
     * 
     * 
     */
    public List<SearchComponent> getSearchComponent() {
        if (searchComponent == null) {
            searchComponent = new ArrayList<SearchComponent>();
        }
        return this.searchComponent;
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
