
package com.bigfix.schemas.bes;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlElementRefs;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for SearchComponentPropertyReference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchComponentPropertyReference">
 *   &lt;complexContent>
 *     &lt;extension base="{}SearchComponent">
 *       &lt;sequence>
 *         &lt;element name="SearchText" type="{http://www.w3.org/2001/XMLSchema}normalizedString"/>
 *         &lt;element name="Relevance" type="{}RelevanceString"/>
 *       &lt;/sequence>
 *       &lt;attribute name="PropertyName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *       &lt;attribute name="Comparison" type="{}Comparison" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchComponentPropertyReference", propOrder = {
    "rest"
})
public class SearchComponentPropertyReference
    extends SearchComponent
{

    @XmlElementRefs({
        @XmlElementRef(name = "SearchText", type = JAXBElement.class, required = false),
        @XmlElementRef(name = "Relevance", type = JAXBElement.class, required = false)
    })
    protected List<JAXBElement<?>> rest;
    @XmlAttribute(name = "PropertyName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String propertyName;
    @XmlAttribute(name = "Comparison")
    protected Comparison comparison;

    /**
     * Gets the rest of the content model. 
     * 
     * <p>
     * You are getting this "catch-all" property because of the following reason: 
     * The field name "Relevance" is used by two different parts of a schema. See: 
     * line 451 of file:/home/grlucche/workspace/iem-client/schema/9.2/BES.xsd
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
     * {@link JAXBElement }{@code <}{@link String }{@code >}
     * {@link JAXBElement }{@code <}{@link RelevanceString }{@code >}
     * 
     * 
     */
    public List<JAXBElement<?>> getRest() {
        if (rest == null) {
            rest = new ArrayList<JAXBElement<?>>();
        }
        return this.rest;
    }

    /**
     * Gets the value of the propertyName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPropertyName() {
        return propertyName;
    }

    /**
     * Sets the value of the propertyName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPropertyName(String value) {
        this.propertyName = value;
    }

    /**
     * Gets the value of the comparison property.
     * 
     * @return
     *     possible object is
     *     {@link Comparison }
     *     
     */
    public Comparison getComparison() {
        return comparison;
    }

    /**
     * Sets the value of the comparison property.
     * 
     * @param value
     *     allowed object is
     *     {@link Comparison }
     *     
     */
    public void setComparison(Comparison value) {
        this.comparison = value;
    }

}
