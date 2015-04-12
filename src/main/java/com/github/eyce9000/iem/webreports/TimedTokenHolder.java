package com.github.eyce9000.iem.webreports;

import org.joda.time.DateTime;
import org.joda.time.Period;

public class TimedTokenHolder implements TokenHolder{
	private DateTime setTime;
	private String token;
	private Period timeout = Period.minutes(15);
	
	@Override
	public void setToken(String token) {
		setTime = new DateTime();
		this.token = token;
	}

	
	@Override
	public String getToken() {
		if(token==null)
			return token;
		
		DateTime now = new DateTime();
		if(now.isAfter(setTime.plus(timeout))){
			this.token = null;
		}
		return token;
	}
}
