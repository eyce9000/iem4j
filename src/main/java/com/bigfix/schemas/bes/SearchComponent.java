
package com.bigfix.schemas.bes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SearchComponent complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchComponent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Relevance" type="{}RelevanceString"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchComponent", propOrder = {
    "relevance"
})
@XmlSeeAlso({
    SearchComponentPropertyReference.class,
    SearchComponentGroupReference.class,
    SearchComponentRelevance.class
})
public class SearchComponent {

    @XmlElement(name = "Relevance", required = true)
    protected RelevanceString relevance;

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

}
