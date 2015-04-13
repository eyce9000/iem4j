
package com.bigfix.schemas.besapi;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RelevanceResult complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RelevanceResult">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;choice maxOccurs="unbounded" minOccurs="0">
 *         &lt;element name="Answer" type="{}RelevanceAnswer"/>
 *         &lt;element name="Tuple" type="{}RelevanceResult"/>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RelevanceResult", propOrder = {
    "answerOrTuple"
})
public class RelevanceResult {

    @XmlElements({
        @XmlElement(name = "Answer", type = RelevanceAnswer.class),
        @XmlElement(name = "Tuple", type = RelevanceResult.class)
    })
    protected List<Object> answerOrTuple;

    /**
     * Gets the value of the answerOrTuple property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the answerOrTuple property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAnswerOrTuple().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelevanceAnswer }
     * {@link RelevanceResult }
     * 
     * 
     */
    public List<Object> getAnswerOrTuple() {
        if (answerOrTuple == null) {
            answerOrTuple = new ArrayList<Object>();
        }
        return this.answerOrTuple;
    }

}
