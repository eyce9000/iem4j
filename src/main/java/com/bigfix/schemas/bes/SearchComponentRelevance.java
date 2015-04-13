
package com.bigfix.schemas.bes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchComponentRelevance complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchComponentRelevance">
 *   &lt;complexContent>
 *     &lt;extension base="{}SearchComponent">
 *       &lt;sequence>
 *         &lt;element name="Relevance" type="{}RelevanceString"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Comparison" type="{}TrueFalseComparison" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchComponentRelevance", propOrder = {
    "rest"
})
public class SearchComponentRelevance
    extends SearchComponent
{

    @XmlElementRef(name = "Relevance", type = JAXBElement.class, required = false)
    protected List<JAXBElement<RelevanceString>> rest;
    @XmlAttribute(name = "Comparison")
    protected TrueFalseComparison comparison;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "Relevance" is used by two different parts of a schema. See: 
     * line 423 of file:/home/grlucche/workspace/iem-client/schema/9.2/BES.xsd
     * line 415 of file:/home/grlucche/workspace/iem-client/schema/9.2/BES.xsd
     * <p>
     * To get rid of this property, apply a property customization to one 
     * of both of the following declarations to change their names: 
     * Gets the value of the rest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link RelevanceString }{@code >}
     * 
     * 
     */
    public List<JAXBElement<RelevanceString>> getRest() {
        if (rest == null) {
            rest = new ArrayList<JAXBElement<RelevanceString>>();
        }
        return this.rest;
    }

    /**
     * Gets the value of the comparison property.
     * 
     * @return
     *     possible object is
     *     {@link TrueFalseComparison }
     *     
     */
    public TrueFalseComparison getComparison() {
        return comparison;
    }

    /**
     * Sets the value of the comparison property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrueFalseComparison }
     *     
     */
    public void setComparison(TrueFalseComparison value) {
        this.comparison = value;
    }

}
