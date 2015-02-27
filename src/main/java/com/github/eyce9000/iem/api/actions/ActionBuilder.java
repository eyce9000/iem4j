package com.github.eyce9000.iem.api.actions;

import com.bigfix.schemas.bes.Action;
import com.bigfix.schemas.bes.ActionScript;
import com.bigfix.schemas.bes.RelevanceString;
import com.bigfix.schemas.bes.SingleAction;

public class ActionBuilder<T extends Action> {
	T action;
	
	
	public ActionBuilder<T> title(String title){
		action.setTitle(title);
		return this;
	}
	
	public ActionBuilder<T> relevance(String relevanceString){
		RelevanceString relevance = new RelevanceString();
		relevance.setValue(relevanceString);
		action.setRelevance(relevance);
		return this;
	}
	
	public ActionBuilder<T> actionScript(ActionScript script){
		action.setActionScript(script);
		return this;
	}
	
	public T build(){
		return action;
	}
	
	public static SingleActionBuilder singleAction(){
		SingleActionBuilder builder = new SingleActionBuilder();
		builder.action = new SingleAction();
		return builder;
	}
}
