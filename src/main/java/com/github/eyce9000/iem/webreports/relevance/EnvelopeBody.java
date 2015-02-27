package com.github.eyce9000.iem.webreports.relevance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bigfix.schemas.relevance.GetStructuredRelevanceResult;
import com.bigfix.schemas.relevance.GetStructuredRelevanceResultResponse;

@XmlRootElement(name="Body",namespace="http://schemas.xmlsoap.org/soap/envelope/")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnvelopeBody {
	//REQUESTS
	@XmlElement(name="GetStructuredRelevanceResult",namespace = "http://schemas.bigfix.com/Relevance")
	private GetStructuredRelevanceResult structuredRelevanceRequest;
	
	//RESPONSES
	@XmlElement(name="GetStructuredRelevanceResultResponse",namespace = "http://schemas.bigfix.com/Relevance")
	private GetStructuredRelevanceResultResponse structuredRelevanceResponse;

	@XmlElement(name="Fault",namespace = "http://schemas.xmlsoap.org/soap/envelope/")
	private FaultResponse faultResponse;
	
	public FaultResponse getFaultResponse() {
		return faultResponse;
	}

	public void setFaultResponse(FaultResponse faultResponse) {
		this.faultResponse = faultResponse;
	}

	public GetStructuredRelevanceResult getStructuredRelevanceRequest() {
		return structuredRelevanceRequest;
	}

	public void setStructuredRelevanceRequest(
			GetStructuredRelevanceResult structuredRelevanceRequest) {
		this.structuredRelevanceRequest = structuredRelevanceRequest;
	}

	public GetStructuredRelevanceResultResponse getStructuredRelevanceResponse() {
		return structuredRelevanceResponse;
	}

	public void setStructuredRelevanceResponse(
			GetStructuredRelevanceResultResponse structuredRelevanceResponse) {
		this.structuredRelevanceResponse = structuredRelevanceResponse;
	}
	
	
}
