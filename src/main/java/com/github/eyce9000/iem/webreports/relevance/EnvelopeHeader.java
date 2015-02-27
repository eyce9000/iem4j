package com.github.eyce9000.iem.webreports.relevance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.bigfix.schemas.relevance.RequestHeader;
import com.bigfix.schemas.relevance.ResponseHeader;

@XmlRootElement(name="Header",namespace="http://schemas.xmlsoap.org/soap/envelope/")
@XmlAccessorType(XmlAccessType.FIELD)
public class EnvelopeHeader {
	@XmlElement(name="RequestHeaderElement",namespace = "http://schemas.bigfix.com/Relevance")
	private RequestHeader requestHeader;
	@XmlElement(name="ResponseHeaderElement",namespace = "http://schemas.bigfix.com/Relevance")
	private ResponseHeader responseHeader;
	
	public RequestHeader getRequestHeader() {
		return requestHeader;
	}
	public void setRequestHeader(RequestHeader requestHeader) {
		this.requestHeader = requestHeader;
	}
	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}
	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}
	
	
}
