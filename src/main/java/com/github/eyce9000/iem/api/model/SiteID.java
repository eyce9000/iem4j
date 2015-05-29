package com.github.eyce9000.iem.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SiteID{
	@JsonProperty("siteName")
	private String name;
	@JsonProperty("siteType")
	private SiteID.SiteType type;
	@JsonProperty("siteUrl")
	private String url;
	@JsonProperty("siteDisplayName")
	private String displayName;
	
	public SiteID(){}
	
	public SiteID(String name){
		this.name = name;
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

	public SiteID.SiteType getType() {
		return type;
	}

	public void setType(SiteID.SiteType type) {
		this.type = type;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isValidated(){
		return this.name!=null && this.type!=null;
	}
	
	public String getFormattedName(){
		if(type==SiteType.Master)
			return null;
		return name;
	}

	public static enum SiteType{
		Custom,
		Master,
		External,
		Operator;
		
		public String format(){
			switch(this){
			case Custom:
				return "custom";
			case Master:
				return "master";
			case External:
				return "external";
			case Operator:
				return "operator";
			default:
				return null;
			}
		}
	}
	
	@Override
	public String toString(){
		return String.format("[%s]\"%s\"",type,name);
	}
	
	@Override
	public SiteID clone(){
		SiteID copy = new SiteID();
		copy.name = name;
		copy.type = type;
		return copy;
	}
}