package com.github.eyce9000.iem.api.model;

import java.math.BigInteger;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

@XmlRootElement(name="ResourceID")
public class FixletID {
	private String name;
	@JsonUnwrapped
	private SiteID site;
	private BigInteger id;
	private ResourceType type;
	private String serverUrl;
	private String displayName;
	
	public FixletID(){};
	
	public FixletID(String name, String site){
		this.name = name;
		this.site = new SiteID(site);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public SiteID getSite() {
		return site;
	}
	public void setSite(SiteID site) {
		this.site = site;
	}
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public ResourceType getType() {
		return type;
	}
	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public boolean isValidated(){
		return id!=null && site!=null;
	}
	public static enum ResourceType{
		Fixlet,
		Baseline,
		Task,
		Analysis
	}
	
	@Override
	public String toString(){
		return String.format("%s / [%s](%d)\"%s\"",site,type,id,name);
	}
	@Override
	public FixletID clone(){
		FixletID copy = new FixletID();
		copy.id = new BigInteger(id.toByteArray());
		copy.name = name;
		copy.site = site.clone();
		copy.type = type;
		
		return copy;
	}
}

