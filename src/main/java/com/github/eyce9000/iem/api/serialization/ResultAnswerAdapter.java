package com.github.eyce9000.iem.api.serialization;

import java.math.BigInteger;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.bigfix.schemas.bes.TrueFalseComparison;

public class ResultAnswerAdapter extends XmlAdapter<ResultAnswerAdapter.Answer,Object>{
	DateTimeFormatter dateFormatter =DateTimeFormat.forPattern("EEE, dd MMM yyyy HH:mm:ss Z");
	
	@Override
	public Answer marshal(Object value) throws Exception {
		return null;
	}

	@Override
	public Object unmarshal(Answer rawValue) throws Exception {
		Object typed = null;
		switch(rawValue.type){
		case INTEGER:
			typed = Long.parseLong(rawValue.value);
			break;
		case TIME:
			typed = dateFormatter.parseDateTime(rawValue.value).toDate();
			break;
		case BOOLEAN:
			typed = Boolean.parseBoolean(rawValue.value);
			break;
		case FLOATING_POINT:
			typed = Double.parseDouble(rawValue.value);
			break;
		case STRING:
		case UTF8_STRING:
		default:
			typed = rawValue.value;
		}
		return typed;
	}
	
	@XmlRootElement(name="Answer")
	public static class Answer{
		@XmlAttribute(name="type")
		private AnswerValueType type = AnswerValueType.STRING;
		@XmlValue
		private String value;
		public AnswerValueType getType() {
			return type;
		}
		public void setType(AnswerValueType type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}
	
	public static enum AnswerValueType{

		@XmlEnumValue("integer")
		INTEGER("integer"),
		@XmlEnumValue("time")
		TIME("time"),
		@XmlEnumValue("boolean")
		BOOLEAN("boolean"),
		@XmlEnumValue("floating point")
		FLOATING_POINT("floating point"),
		@XmlEnumValue("string")
		STRING("string"),
		@XmlEnumValue("utf8 string")
		UTF8_STRING("utf8 string");
	    private final String value;
	    
	    AnswerValueType(String value){
	    	this.value = value;
	    }
	    
	    public String value() {
	        return value;
	    }

	    public static AnswerValueType fromValue(String v) {
	        for (AnswerValueType c: AnswerValueType.values()) {
	            if (c.value.equals(v)) {
	                return c;
	            }
	        }
	        throw new IllegalArgumentException(v);
	    }
		
	}

}
