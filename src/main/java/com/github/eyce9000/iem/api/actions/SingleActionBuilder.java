package com.github.eyce9000.iem.api.actions;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.joda.time.Period;

import com.bigfix.schemas.bes.ActionSettings;
import com.bigfix.schemas.bes.BESActionTarget;
import com.bigfix.schemas.bes.SingleAction;

public class SingleActionBuilder extends ActionBuilder<SingleAction> {
	DatatypeFactory dataFactory;
	public SingleActionBuilder(){
		try {
			 dataFactory = DatatypeFactory.newInstance();
		} catch (DatatypeConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public SingleActionBuilder target(BESActionTarget target){
		action.setTarget(target);
		return this;
	}
	private void createSettings(){
		if(action.getSettings()==null)
			action.setSettings(new ActionSettings());
	}
	
	public SingleActionBuilder setExpirationTimeOffset(Period expiration){
		createSettings();
		action.getSettings().setHasEndTime(true);
		action.getSettings().setEndDateTimeOffset(dataFactory.newDuration(expiration.toString()));
		return this;
	}
}
