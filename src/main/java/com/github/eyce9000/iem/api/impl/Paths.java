package com.github.eyce9000.iem.api.impl;

public class Paths {
	public static final String api="/api",
			computers=api+"/computers",
			computergroups=api+"/computergroups",
			actions=api+"/actions",
			sites=api+"/sites",
			IMPORT=api+"/import",
			query=api+"/query";
	
	public static String site(String site, String siteType){
		return buildSiteTarget(api+"/site",siteType,site);
	}
	public static String fixlets(String site, String siteType){
		return buildSiteTarget(api+"/fixlets",siteType,site);
	}
	public static String fixlet(String site,String siteType,long id){
		return buildSiteTarget(api+"/fixlet",siteType,site)+"/"+id;
	}
	public static String baselines(String site, String siteType){
		return buildSiteTarget(api+"/baselines",siteType,site);
	}
	public static String tasks(String site, String siteType){
		return buildSiteTarget(api+"/tasks",siteType,site);
	}
	public static String task(String site,String siteType,long id){
		return buildSiteTarget(api+"/task",siteType,site)+"/"+id;
	}
	public static String analyses(String site, String siteType){
		return buildSiteTarget(api+"/analyses",siteType,site);
	}
	public static String analysis(String site,String siteType,long id){
		return buildSiteTarget(api+"/analysis",siteType,site)+"/"+id;
	}
	public static String siteImport(String site,String siteType){
		return buildSiteTarget(api+"/import",siteType,site);
	}
	public static String files(String site, String siteType){
		return siteImport(site,siteType)+"/files";
	}
	public static String file(String site, String siteType,long id){
		return siteImport(site,siteType)+"/file/"+id;
	}
	public static String computer(long id){
		return api+"/computer/"+id;
	}
	public static String computerSettings(long id){
		return computer(id)+"/settings";
	}
	public static String computerSetting(long id,String settingName){
		return computer(id)+"/setting/"+settingName;
	}
	public static String computerGroup(String site, String siteType){
		return buildSiteTarget(api+"/computergroup",site,siteType);
	}
	public static String computerGroupMembers(String site, String siteType){
		return buildSiteTarget(api+"/computergroup",site,siteType)+"/computers";
	}
	public static String action(long id){
		return api+"/action/"+id;
	}
	public static String actionStatus(long id){
		return api+"/action/"+id+"/status";
	}
	public static String actionStop(long id){
		return api+"/action/"+id+"/stop";
	}
	
	protected static String buildSiteTarget(String base, String siteType, String site){
		String target;
		target = base+"/"+siteType;
		if(site!=null){
			target += "/"+site;
		}
		return target;
	}
}
