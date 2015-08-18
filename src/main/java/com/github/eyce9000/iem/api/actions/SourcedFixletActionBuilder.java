package com.github.eyce9000.iem.api.actions;

import java.math.BigInteger;

import com.bigfix.schemas.bes.BESActionSourceFixlet;
import com.bigfix.schemas.bes.BESActionTarget;
import com.bigfix.schemas.bes.SourcedFixletAction;

public class SourcedFixletActionBuilder {
	SourcedFixletAction action = new SourcedFixletAction();
	
	public SourcedFixletAction build(){
		return action;
	}
	
	public SourcedFixletActionBuilder target(BESActionTarget target){
		action.setTarget(target);
		return this;
	}
	
	public static SourcedFixletActionBuilder start(String siteName, BigInteger fixletId){
		return start(siteName,fixletId,"Action1");
	}
	
	public static SourcedFixletActionBuilder start(String siteName, BigInteger fixletId,String actionName){
		SourcedFixletActionBuilder builder = new SourcedFixletActionBuilder();
		
		BESActionSourceFixlet sourceFixlet = new BESActionSourceFixlet();
		sourceFixlet.setSitename(siteName);
		sourceFixlet.setFixletID(fixletId);
		sourceFixlet.setAction(actionName);
		builder.action.setSourceFixlet(sourceFixlet);
		
		return builder;
	}
}
