
package com.bigfix.schemas.bes;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActionSettingsLocks complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ActionSettingsLocks">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HasMessage" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Message" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;group ref="{}MessageLocks" minOccurs="0"/>
 *                   &lt;element name="ShowActionButton" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="ShowCancelButton" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="Postponement" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="Timeout" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="ActionUITitle" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PreActionShowUI" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PreAction" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;group ref="{}MessageLocks" minOccurs="0"/>
 *                   &lt;element name="AskToSaveWork" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="ShowActionButton" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="ShowCancelButton" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="DeadlineBehavior" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="ShowConfirmation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="HasRunningMessage" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RunningMessage" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="TimeRange" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="StartDateTimeOffset" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="EndDateTimeOffset" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="DayOfWeekConstraint" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ActiveUserRequirement" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ActiveUserType" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Whose" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PreActionCacheDownload" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Reapply" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ReapplyLimit" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ReapplyInterval" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RetryCount" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RetryWait" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="TemporalDistribution" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="ContinueOnErrors" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="PostActionBehavior" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="Behavior" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="AllowCancel" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="Postponement" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="Timeout" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;element name="Deadline" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *                   &lt;group ref="{}MessageLocks" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="IsOffer" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="AnnounceOffer" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="OfferTitle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfferCategory" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="OfferDescriptionHTML" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActionSettingsLocks", propOrder = {
    "hasMessage",
    "message",
    "actionUITitle",
    "preActionShowUI",
    "preAction",
    "hasRunningMessage",
    "runningMessage",
    "timeRange",
    "startDateTimeOffset",
    "endDateTimeOffset",
    "dayOfWeekConstraint",
    "activeUserRequirement",
    "activeUserType",
    "whose",
    "preActionCacheDownload",
    "reapply",
    "reapplyLimit",
    "reapplyInterval",
    "retryCount",
    "retryWait",
    "temporalDistribution",
    "continueOnErrors",
    "postActionBehavior",
    "isOffer",
    "announceOffer",
    "offerTitle",
    "offerCategory",
    "offerDescriptionHTML"
})
public class ActionSettingsLocks {

    @XmlElement(name = "HasMessage")
    protected Boolean hasMessage;
    @XmlElement(name = "Message")
    protected ActionSettingsLocks.Message message;
    @XmlElement(name = "ActionUITitle")
    protected Boolean actionUITitle;
    @XmlElement(name = "PreActionShowUI")
    protected Boolean preActionShowUI;
    @XmlElement(name = "PreAction")
    protected ActionSettingsLocks.PreAction preAction;
    @XmlElement(name = "HasRunningMessage")
    protected Boolean hasRunningMessage;
    @XmlElement(name = "RunningMessage")
    protected ActionSettingsLocks.RunningMessage runningMessage;
    @XmlElement(name = "TimeRange")
    protected Boolean timeRange;
    @XmlElement(name = "StartDateTimeOffset")
    protected Boolean startDateTimeOffset;
    @XmlElement(name = "EndDateTimeOffset")
    protected Boolean endDateTimeOffset;
    @XmlElement(name = "DayOfWeekConstraint")
    protected Boolean dayOfWeekConstraint;
    @XmlElement(name = "ActiveUserRequirement")
    protected Boolean activeUserRequirement;
    @XmlElement(name = "ActiveUserType")
    protected Boolean activeUserType;
    @XmlElement(name = "Whose")
    protected Boolean whose;
    @XmlElement(name = "PreActionCacheDownload")
    protected Boolean preActionCacheDownload;
    @XmlElement(name = "Reapply")
    protected Boolean reapply;
    @XmlElement(name = "ReapplyLimit")
    protected Boolean reapplyLimit;
    @XmlElement(name = "ReapplyInterval")
    protected Boolean reapplyInterval;
    @XmlElement(name = "RetryCount")
    protected Boolean retryCount;
    @XmlElement(name = "RetryWait")
    protected Boolean retryWait;
    @XmlElement(name = "TemporalDistribution")
    protected Boolean temporalDistribution;
    @XmlElement(name = "ContinueOnErrors")
    protected Boolean continueOnErrors;
    @XmlElement(name = "PostActionBehavior")
    protected ActionSettingsLocks.PostActionBehavior postActionBehavior;
    @XmlElement(name = "IsOffer")
    protected Boolean isOffer;
    @XmlElement(name = "AnnounceOffer")
    protected Boolean announceOffer;
    @XmlElement(name = "OfferTitle")
    protected String offerTitle;
    @XmlElement(name = "OfferCategory")
    protected String offerCategory;
    @XmlElement(name = "OfferDescriptionHTML")
    protected String offerDescriptionHTML;

    /**
     * Gets the value of the hasMessage property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasMessage() {
        return hasMessage;
    }

    /**
     * Sets the value of the hasMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasMessage(Boolean value) {
        this.hasMessage = value;
    }

    /**
     * Gets the value of the message property.
     * 
     * @return
     *     possible object is
     *     {@link ActionSettingsLocks.Message }
     *     
     */
    public ActionSettingsLocks.Message getMessage() {
        return message;
    }

    /**
     * Sets the value of the message property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionSettingsLocks.Message }
     *     
     */
    public void setMessage(ActionSettingsLocks.Message value) {
        this.message = value;
    }

    /**
     * Gets the value of the actionUITitle property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActionUITitle() {
        return actionUITitle;
    }

    /**
     * Sets the value of the actionUITitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActionUITitle(Boolean value) {
        this.actionUITitle = value;
    }

    /**
     * Gets the value of the preActionShowUI property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPreActionShowUI() {
        return preActionShowUI;
    }

    /**
     * Sets the value of the preActionShowUI property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreActionShowUI(Boolean value) {
        this.preActionShowUI = value;
    }

    /**
     * Gets the value of the preAction property.
     * 
     * @return
     *     possible object is
     *     {@link ActionSettingsLocks.PreAction }
     *     
     */
    public ActionSettingsLocks.PreAction getPreAction() {
        return preAction;
    }

    /**
     * Sets the value of the preAction property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionSettingsLocks.PreAction }
     *     
     */
    public void setPreAction(ActionSettingsLocks.PreAction value) {
        this.preAction = value;
    }

    /**
     * Gets the value of the hasRunningMessage property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isHasRunningMessage() {
        return hasRunningMessage;
    }

    /**
     * Sets the value of the hasRunningMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setHasRunningMessage(Boolean value) {
        this.hasRunningMessage = value;
    }

    /**
     * Gets the value of the runningMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ActionSettingsLocks.RunningMessage }
     *     
     */
    public ActionSettingsLocks.RunningMessage getRunningMessage() {
        return runningMessage;
    }

    /**
     * Sets the value of the runningMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionSettingsLocks.RunningMessage }
     *     
     */
    public void setRunningMessage(ActionSettingsLocks.RunningMessage value) {
        this.runningMessage = value;
    }

    /**
     * Gets the value of the timeRange property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTimeRange() {
        return timeRange;
    }

    /**
     * Sets the value of the timeRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTimeRange(Boolean value) {
        this.timeRange = value;
    }

    /**
     * Gets the value of the startDateTimeOffset property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isStartDateTimeOffset() {
        return startDateTimeOffset;
    }

    /**
     * Sets the value of the startDateTimeOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setStartDateTimeOffset(Boolean value) {
        this.startDateTimeOffset = value;
    }

    /**
     * Gets the value of the endDateTimeOffset property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isEndDateTimeOffset() {
        return endDateTimeOffset;
    }

    /**
     * Sets the value of the endDateTimeOffset property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setEndDateTimeOffset(Boolean value) {
        this.endDateTimeOffset = value;
    }

    /**
     * Gets the value of the dayOfWeekConstraint property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDayOfWeekConstraint() {
        return dayOfWeekConstraint;
    }

    /**
     * Sets the value of the dayOfWeekConstraint property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDayOfWeekConstraint(Boolean value) {
        this.dayOfWeekConstraint = value;
    }

    /**
     * Gets the value of the activeUserRequirement property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActiveUserRequirement() {
        return activeUserRequirement;
    }

    /**
     * Sets the value of the activeUserRequirement property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActiveUserRequirement(Boolean value) {
        this.activeUserRequirement = value;
    }

    /**
     * Gets the value of the activeUserType property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isActiveUserType() {
        return activeUserType;
    }

    /**
     * Sets the value of the activeUserType property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setActiveUserType(Boolean value) {
        this.activeUserType = value;
    }

    /**
     * Gets the value of the whose property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isWhose() {
        return whose;
    }

    /**
     * Sets the value of the whose property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setWhose(Boolean value) {
        this.whose = value;
    }

    /**
     * Gets the value of the preActionCacheDownload property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPreActionCacheDownload() {
        return preActionCacheDownload;
    }

    /**
     * Sets the value of the preActionCacheDownload property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPreActionCacheDownload(Boolean value) {
        this.preActionCacheDownload = value;
    }

    /**
     * Gets the value of the reapply property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReapply() {
        return reapply;
    }

    /**
     * Sets the value of the reapply property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReapply(Boolean value) {
        this.reapply = value;
    }

    /**
     * Gets the value of the reapplyLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReapplyLimit() {
        return reapplyLimit;
    }

    /**
     * Sets the value of the reapplyLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReapplyLimit(Boolean value) {
        this.reapplyLimit = value;
    }

    /**
     * Gets the value of the reapplyInterval property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReapplyInterval() {
        return reapplyInterval;
    }

    /**
     * Sets the value of the reapplyInterval property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReapplyInterval(Boolean value) {
        this.reapplyInterval = value;
    }

    /**
     * Gets the value of the retryCount property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetryCount() {
        return retryCount;
    }

    /**
     * Sets the value of the retryCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetryCount(Boolean value) {
        this.retryCount = value;
    }

    /**
     * Gets the value of the retryWait property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetryWait() {
        return retryWait;
    }

    /**
     * Sets the value of the retryWait property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetryWait(Boolean value) {
        this.retryWait = value;
    }

    /**
     * Gets the value of the temporalDistribution property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isTemporalDistribution() {
        return temporalDistribution;
    }

    /**
     * Sets the value of the temporalDistribution property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setTemporalDistribution(Boolean value) {
        this.temporalDistribution = value;
    }

    /**
     * Gets the value of the continueOnErrors property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isContinueOnErrors() {
        return continueOnErrors;
    }

    /**
     * Sets the value of the continueOnErrors property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setContinueOnErrors(Boolean value) {
        this.continueOnErrors = value;
    }

    /**
     * Gets the value of the postActionBehavior property.
     * 
     * @return
     *     possible object is
     *     {@link ActionSettingsLocks.PostActionBehavior }
     *     
     */
    public ActionSettingsLocks.PostActionBehavior getPostActionBehavior() {
        return postActionBehavior;
    }

    /**
     * Sets the value of the postActionBehavior property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActionSettingsLocks.PostActionBehavior }
     *     
     */
    public void setPostActionBehavior(ActionSettingsLocks.PostActionBehavior value) {
        this.postActionBehavior = value;
    }

    /**
     * Gets the value of the isOffer property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isIsOffer() {
        return isOffer;
    }

    /**
     * Sets the value of the isOffer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIsOffer(Boolean value) {
        this.isOffer = value;
    }

    /**
     * Gets the value of the announceOffer property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isAnnounceOffer() {
        return announceOffer;
    }

    /**
     * Sets the value of the announceOffer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setAnnounceOffer(Boolean value) {
        this.announceOffer = value;
    }

    /**
     * Gets the value of the offerTitle property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferTitle() {
        return offerTitle;
    }

    /**
     * Sets the value of the offerTitle property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferTitle(String value) {
        this.offerTitle = value;
    }

    /**
     * Gets the value of the offerCategory property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferCategory() {
        return offerCategory;
    }

    /**
     * Sets the value of the offerCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferCategory(String value) {
        this.offerCategory = value;
    }

    /**
     * Gets the value of the offerDescriptionHTML property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferDescriptionHTML() {
        return offerDescriptionHTML;
    }

    /**
     * Sets the value of the offerDescriptionHTML property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferDescriptionHTML(String value) {
        this.offerDescriptionHTML = value;
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
     *         &lt;group ref="{}MessageLocks" minOccurs="0"/>
     *         &lt;element name="ShowActionButton" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="ShowCancelButton" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="Postponement" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="Timeout" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "title",
        "text",
        "showActionButton",
        "showCancelButton",
        "postponement",
        "timeout"
    })
    public static class Message {

        @XmlElement(name = "Title")
        protected Boolean title;
        @XmlElement(name = "Text")
        protected Boolean text;
        @XmlElement(name = "ShowActionButton")
        protected Boolean showActionButton;
        @XmlElement(name = "ShowCancelButton")
        protected Boolean showCancelButton;
        @XmlElement(name = "Postponement")
        protected Boolean postponement;
        @XmlElement(name = "Timeout")
        protected Boolean timeout;

        /**
         * Gets the value of the title property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setTitle(Boolean value) {
            this.title = value;
        }

        /**
         * Gets the value of the text property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isText() {
            return text;
        }

        /**
         * Sets the value of the text property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setText(Boolean value) {
            this.text = value;
        }

        /**
         * Gets the value of the showActionButton property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isShowActionButton() {
            return showActionButton;
        }

        /**
         * Sets the value of the showActionButton property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setShowActionButton(Boolean value) {
            this.showActionButton = value;
        }

        /**
         * Gets the value of the showCancelButton property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isShowCancelButton() {
            return showCancelButton;
        }

        /**
         * Sets the value of the showCancelButton property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setShowCancelButton(Boolean value) {
            this.showCancelButton = value;
        }

        /**
         * Gets the value of the postponement property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isPostponement() {
            return postponement;
        }

        /**
         * Sets the value of the postponement property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setPostponement(Boolean value) {
            this.postponement = value;
        }

        /**
         * Gets the value of the timeout property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isTimeout() {
            return timeout;
        }

        /**
         * Sets the value of the timeout property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setTimeout(Boolean value) {
            this.timeout = value;
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
     *         &lt;element name="Behavior" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="AllowCancel" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="Postponement" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="Timeout" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="Deadline" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;group ref="{}MessageLocks" minOccurs="0"/>
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
        "behavior",
        "allowCancel",
        "postponement",
        "timeout",
        "deadline",
        "title",
        "text"
    })
    public static class PostActionBehavior {

        @XmlElement(name = "Behavior")
        protected Boolean behavior;
        @XmlElement(name = "AllowCancel")
        protected Boolean allowCancel;
        @XmlElement(name = "Postponement")
        protected Boolean postponement;
        @XmlElement(name = "Timeout")
        protected Boolean timeout;
        @XmlElement(name = "Deadline")
        protected Boolean deadline;
        @XmlElement(name = "Title")
        protected Boolean title;
        @XmlElement(name = "Text")
        protected Boolean text;

        /**
         * Gets the value of the behavior property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isBehavior() {
            return behavior;
        }

        /**
         * Sets the value of the behavior property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setBehavior(Boolean value) {
            this.behavior = value;
        }

        /**
         * Gets the value of the allowCancel property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isAllowCancel() {
            return allowCancel;
        }

        /**
         * Sets the value of the allowCancel property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setAllowCancel(Boolean value) {
            this.allowCancel = value;
        }

        /**
         * Gets the value of the postponement property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isPostponement() {
            return postponement;
        }

        /**
         * Sets the value of the postponement property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setPostponement(Boolean value) {
            this.postponement = value;
        }

        /**
         * Gets the value of the timeout property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isTimeout() {
            return timeout;
        }

        /**
         * Sets the value of the timeout property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setTimeout(Boolean value) {
            this.timeout = value;
        }

        /**
         * Gets the value of the deadline property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isDeadline() {
            return deadline;
        }

        /**
         * Sets the value of the deadline property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setDeadline(Boolean value) {
            this.deadline = value;
        }

        /**
         * Gets the value of the title property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setTitle(Boolean value) {
            this.title = value;
        }

        /**
         * Gets the value of the text property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isText() {
            return text;
        }

        /**
         * Sets the value of the text property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setText(Boolean value) {
            this.text = value;
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
     *         &lt;group ref="{}MessageLocks" minOccurs="0"/>
     *         &lt;element name="AskToSaveWork" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="ShowActionButton" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="ShowCancelButton" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="DeadlineBehavior" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="ShowConfirmation" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "title",
        "text",
        "askToSaveWork",
        "showActionButton",
        "showCancelButton",
        "deadlineBehavior",
        "showConfirmation"
    })
    public static class PreAction {

        @XmlElement(name = "Title")
        protected Boolean title;
        @XmlElement(name = "Text")
        protected Boolean text;
        @XmlElement(name = "AskToSaveWork")
        protected Boolean askToSaveWork;
        @XmlElement(name = "ShowActionButton")
        protected Boolean showActionButton;
        @XmlElement(name = "ShowCancelButton")
        protected Boolean showCancelButton;
        @XmlElement(name = "DeadlineBehavior")
        protected Boolean deadlineBehavior;
        @XmlElement(name = "ShowConfirmation")
        protected Boolean showConfirmation;

        /**
         * Gets the value of the title property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setTitle(Boolean value) {
            this.title = value;
        }

        /**
         * Gets the value of the text property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isText() {
            return text;
        }

        /**
         * Sets the value of the text property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setText(Boolean value) {
            this.text = value;
        }

        /**
         * Gets the value of the askToSaveWork property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isAskToSaveWork() {
            return askToSaveWork;
        }

        /**
         * Sets the value of the askToSaveWork property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setAskToSaveWork(Boolean value) {
            this.askToSaveWork = value;
        }

        /**
         * Gets the value of the showActionButton property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isShowActionButton() {
            return showActionButton;
        }

        /**
         * Sets the value of the showActionButton property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setShowActionButton(Boolean value) {
            this.showActionButton = value;
        }

        /**
         * Gets the value of the showCancelButton property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isShowCancelButton() {
            return showCancelButton;
        }

        /**
         * Sets the value of the showCancelButton property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setShowCancelButton(Boolean value) {
            this.showCancelButton = value;
        }

        /**
         * Gets the value of the deadlineBehavior property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isDeadlineBehavior() {
            return deadlineBehavior;
        }

        /**
         * Sets the value of the deadlineBehavior property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setDeadlineBehavior(Boolean value) {
            this.deadlineBehavior = value;
        }

        /**
         * Gets the value of the showConfirmation property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isShowConfirmation() {
            return showConfirmation;
        }

        /**
         * Sets the value of the showConfirmation property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setShowConfirmation(Boolean value) {
            this.showConfirmation = value;
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
     *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
     *         &lt;element name="Text" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
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
        "title",
        "text"
    })
    public static class RunningMessage {

        @XmlElement(name = "Title")
        protected Boolean title;
        @XmlElement(name = "Text")
        protected Boolean text;

        /**
         * Gets the value of the title property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isTitle() {
            return title;
        }

        /**
         * Sets the value of the title property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setTitle(Boolean value) {
            this.title = value;
        }

        /**
         * Gets the value of the text property.
         * 
         * @return
         *     possible object is
         *     {@link Boolean }
         *     
         */
        public Boolean isText() {
            return text;
        }

        /**
         * Sets the value of the text property.
         * 
         * @param value
         *     allowed object is
         *     {@link Boolean }
         *     
         */
        public void setText(Boolean value) {
            this.text = value;
        }

    }

}
