package com.github.eyce9000.iem.api.serialization;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.joda.time.Period;

public class XmlPeriodAdapter extends XmlAdapter<String,Period>{
	@Override
	public String marshal(Period period) throws Exception {
		if(period==null)
			return null;
		return period.getMillis()+".000ms";
	}
	
	@Override
	public Period unmarshal(String val) throws Exception {
		if(val==null || val.isEmpty())
			return null;
		try{
			return Period.millis(Integer.parseInt(val.split("\\.")[0]));
		}
		catch(Exception ex){
			
		}
		return null;
	}

}
