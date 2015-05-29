package com.github.eyce9000.iem.api.model;

public class ActionID {
	private String name;
	private String id;
	private State state;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}

	public static enum State{Open,Stopped,Expired};
}
