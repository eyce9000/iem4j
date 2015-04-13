
package com.bigfix.schemas.bes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * <p>Java class for SearchComponentGroupReference complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchComponentGroupReference">
 *   &lt;complexContent>
 *     &lt;extension base="{}SearchComponent">
 *       &lt;attribute name="GroupName" type="{http://www.w3.org/2001/XMLSchema}normalizedString" />
 *       &lt;attribute name="Comparison" type="{}MembershipComparison" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchComponentGroupReference")
public class SearchComponentGroupReference
    extends SearchComponent
{

    @XmlAttribute(name = "GroupName")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String groupName;
    @XmlAttribute(name = "Comparison")
    protected MembershipComparison comparison;

    /**
     * Gets the value of the groupName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     * Sets the value of the groupName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupName(String value) {
        this.groupName = value;
    }

    /**
     * Gets the value of the comparison property.
     * 
     * @return
     *     possible object is
     *     {@link MembershipComparison }
     *     
     */
    public MembershipComparison getComparison() {
        return comparison;
    }

    /**
     * Sets the value of the comparison property.
     * 
     * @param value
     *     allowed object is
     *     {@link MembershipComparison }
     *     
     */
    public void setComparison(MembershipComparison value) {
        this.comparison = value;
    }

}
