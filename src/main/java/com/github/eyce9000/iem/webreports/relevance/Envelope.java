package com.github.eyce9000.iem.webreports.relevance;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="Envelope",namespace="http://schemas.xmlsoap.org/soap/envelope/")
@XmlAccessorType(XmlAccessType.FIELD)
public class Envelope {
	@XmlElement(name="Header",namespace="http://schemas.xmlsoap.org/soap/envelope/")
	private EnvelopeHeader header;
	
	@XmlElement(name="Body",namespace="http://schemas.xmlsoap.org/soap/envelope/")
	private EnvelopeBody body;

	public EnvelopeHeader getHeader() {
		return header;
	}

	public void setHeader(EnvelopeHeader header) {
		this.header = header;
	}

	public EnvelopeBody getBody() {
		return body;
	}

	public void setBody(EnvelopeBody body) {
		this.body = body;
	}

	
}
