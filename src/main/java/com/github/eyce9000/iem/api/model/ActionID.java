package com.github.eyce9000.iem.api.model;

import java.math.BigInteger;

public class ActionID {
	private String name;
	private BigInteger id;
	private State state;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}

	public static enum State{Open,Stopped,Expired};
	
	@Override
	public String toString(){
		return String.format("(%d)\"%s\"[%s]",id,name,state.name());
	}
	@Override
	public ActionID clone(){
		ActionID copy = new ActionID();
		copy.id = new BigInteger(id.toByteArray());
		copy.name = name;
		copy.state = state;
		
		return copy;
	}
}
