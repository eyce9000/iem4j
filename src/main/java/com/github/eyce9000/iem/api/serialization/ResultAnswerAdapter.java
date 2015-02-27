package com.github.eyce9000.iem.api.serialization;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

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
		case "integer":
			typed = Integer.parseInt(rawValue.value);
			break;
		case "time":
			typed = dateFormatter.parseDateTime(rawValue.value).toDate();
			break;
		case "boolean":
			typed = Boolean.parseBoolean(rawValue.value);
			break;
		case "floating point":
			typed = Double.parseDouble(rawValue.value);
			break;
		case "string":
		case "utf8 string":
		default:
			typed = rawValue.value;
		}
		return typed;
	}
	
	public static class Answer{
		@XmlAttribute(name="type")
		private String type = "string";
		@XmlValue
		private String value;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	}

}
