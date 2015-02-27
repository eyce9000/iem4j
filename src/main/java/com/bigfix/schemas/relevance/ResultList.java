
package com.bigfix.schemas.relevance;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ResultList complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResultList">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;choice maxOccurs="unbounded" minOccurs="0">
 *           &lt;element name="Boolean" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *           &lt;element name="Integer" type="{http://www.w3.org/2001/XMLSchema}integer"/>
 *           &lt;element name="String" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *           &lt;element name="DateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *           &lt;element name="FloatingPoint" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *           &lt;element name="Tuple" type="{http://schemas.bigfix.com/Relevance}ResultList"/>
 *         &lt;/choice>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResultList", propOrder = {
    "booleanOrIntegerOrString"
})
public class ResultList {

    @XmlElements({
        @XmlElement(name = "Boolean", type = Boolean.class),
        @XmlElement(name = "Integer", type = BigInteger.class),
        @XmlElement(name = "String", type = String.class),
        @XmlElement(name = "DateTime", type = XMLGregorianCalendar.class),
        @XmlElement(name = "FloatingPoint", type = Double.class),
        @XmlElement(name = "Tuple", type = ResultList.class)
    })
    protected List<Object> booleanOrIntegerOrString;

    /**
     * Gets the value of the booleanOrIntegerOrString property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the booleanOrIntegerOrString property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getBooleanOrIntegerOrString().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Boolean }
     * {@link BigInteger }
     * {@link String }
     * {@link XMLGregorianCalendar }
     * {@link Double }
     * {@link ResultList }
     * 
     * 
     */
    public List<Object> getBooleanOrIntegerOrString() {
        if (booleanOrIntegerOrString == null) {
            booleanOrIntegerOrString = new ArrayList<Object>();
        }
        return this.booleanOrIntegerOrString;
    }

}
