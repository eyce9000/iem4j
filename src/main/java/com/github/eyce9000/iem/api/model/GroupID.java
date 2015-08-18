package com.github.eyce9000.iem.api.model;

import java.math.BigInteger;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupID {
	@JsonProperty("groupName")
	private String name;
	@JsonProperty("groupId")
	private BigInteger id;
	@JsonProperty("serverUrl")
	private String serverUrl;
	@JsonProperty("groupType")
	private GroupType type;
	
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

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public GroupType getType() {
		return type;
	}

	public void setType(GroupType type) {
		this.type = type;
	}



	public static enum GroupType{
		Automatic,
		Manual
	}
	
	
}
