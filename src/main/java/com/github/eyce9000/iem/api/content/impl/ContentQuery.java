package com.github.eyce9000.iem.api.content.impl;

import static com.github.eyce9000.iem.api.content.impl.ContentQuery.toMap;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.eyce9000.iem.api.IEMAPI;
import com.github.eyce9000.iem.api.RelevanceAPI;
import com.github.eyce9000.iem.api.model.ActionID;
import com.github.eyce9000.iem.api.model.ActionID.State;
import com.github.eyce9000.iem.api.model.FixletID;
import com.github.eyce9000.iem.api.model.GroupID;
import com.github.eyce9000.iem.api.model.SiteID;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceBuilder;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.google.common.base.Optional;

public class ContentQuery {
	private static final String FIXLET_PROPERTIES = "" 
			+"(" 
			+ "	id of it, " 
			+ "	name of it, " 
			+ " url of current bes server," 
			+ "	(if analysis flag of it then \"Analysis\" " 
			+ "		else if task flag of it then \"Task\" "
			+ "		else if baseline flag of it then \"Baseline\" " 
			+ "		else \"Fixlet\") of it," + "	name of site of it,"
			+ "	(if external site flag of site of it then \"External\" " 
			+ "		else if master site flag of site of it then \"Master\" "
			+ "		else if operator site flag of site of it then \"Operator\" " 
			+ "		else \"Custom\") of it,"
			+ " url of site of it,"
			+ " display name of it" 
			+ ") of ";
	private static final String FIXLET_RELEVANCE = 
			FIXLET_PROPERTIES
			+ " bes fixlets";
	
	private static final String GROUP_RELEVANCE = "("
			+ " id of it,"
			+ " name of it, "
			+ " url of current bes server,"
			+ " (if automatic flag of it then \"Automatic\" "
			+ "		else \"Manual\")"
			+ ")"
			+ "of  bes computer groups";
	
	private static final String SITE_RELEVANCE = "(" 
			+ "	name of it, " 
			+ "	(if external site flag of it then \"External\" " 
			+ "		else if master site flag of it then \"Master\" "
			+ "		else if operator site flag of it then \"Operator\" " 
			+ "		else \"Custom\") of it,"
			+ " url of it,"
			+ "	display name of it" 
			+ ") "
			+ "of all bes sites";
	private static final String ACTION_RELEVANCE = "("
			+ " name of it,"
			+ " id of it,"
			+ "	state of it"
			+ ")";
	private static final String[] GROUP_COLUMNS = 
			new String[]{"groupId","groupName","serverUrl","groupType"};
	private static final String[] SITE_COLUMNS = 
			new String[]{"siteName", "siteType","siteUrl","siteDisplayName"};
	private static final String[] FIXLET_COLUMNS = 
			new String[]{"id", "name","serverUrl", "type", "siteName", "siteType", "siteUrl", "displayName"};
	private static final String[] ACTION_COLUMNS =
			new String[]{"name","id","state"};
	public static Map<String,String> toMap(String...args){
		if(args.length==0)
			return Collections.emptyMap();
		if(args.length%2!=0){
			throw new IllegalArgumentException("Odd number of arguments");
		}
		Map<String,String> map = new HashMap<String,String>();
		for(int i=0; i<args.length; i+=2){
			map.put(args[i], args[i+1]);
		}
		return map;
	}
	
	private RelevanceAPI client;
	
	public ContentQuery(RelevanceAPI client){
		this.client = client;
	}
	public List<FixletID> queryFixlets(String site,String relevance) throws RelevanceException{
		return queryFixlets(site,relevance,null);
	}
	public List<FixletID> queryFixlets(String site,String relevance,Map<String,String> parameters) throws RelevanceException{
		if(site==null)
			return queryFixlets(relevance,parameters);
		
		String siteRelevance = "name of it = '"+site+"'";
		String siteQuery = " all bes sites whose ( "+siteRelevance+" )";
		String fullRelevance = FIXLET_PROPERTIES+" whose ( "+relevance+" ) of fixlets of "+siteQuery;
		SessionRelevanceQuery srq = SessionRelevanceBuilder
				.fromRelevance(fullRelevance)
				.addColumns(FIXLET_COLUMNS).build();
		if(parameters!=null){
			for(Entry<String,String> entry:parameters.entrySet()){
				srq.getParameter(entry.getKey()).setValue(entry.getValue());
			}
		}

		return client.executeQuery(srq, FixletID.class);
	}
	
	public List<FixletID> queryFixlets(String relevance) throws RelevanceException{
		return queryFixlets(relevance,Collections.EMPTY_MAP);
	}
	
	public List<FixletID> queryFixlets(String relevance, Map<String,String> parameters) throws RelevanceException{
		String fullRelevance = FIXLET_RELEVANCE+" whose ("+relevance+") ";
		SessionRelevanceQuery srq = SessionRelevanceBuilder
				.fromRelevance(fullRelevance)
				.addColumns(FIXLET_COLUMNS).build();
		
		if(parameters!=null){
			for(Entry<String,String> entry:parameters.entrySet()){
				srq.getParameter(entry.getKey()).setValue(entry.getValue());
			}
		}

		return client.executeQuery(srq, FixletID.class);
	}

	public List<FixletID> getFixletIDsExact(String fixletName) throws RelevanceException{
		return getFixletIDsExact(null,fixletName);
	}
	public List<FixletID> getFixletIDsExact(String siteName,String fixletName) throws RelevanceException{
		return queryFixlets(siteName,"name of it=\"${name}\" and not baseline flag of it=true and not analysis flag of it=true and not task flag of it=true", 
			toMap("name",fixletName));
	}
	
	public List<FixletID> getFixletIDsLike(String name) throws RelevanceException{
		return getFixletIDsLike(null,name);
	}
	public List<FixletID> getFixletIDsLike(String siteName, String name) throws RelevanceException{
		return queryFixlets(siteName,"name of it as lowercase contains \"${name}\" and not baseline flag of it=true and not analysis flag of it=true and not task flag of it=true", 
			toMap("name",name.toLowerCase()));
	}

	public List<FixletID> getBaselineIDsExact(String fixletName) throws RelevanceException{
		return getBaselineIDsExact(null,fixletName);
	}
	public List<FixletID> getBaselineIDsExact(String siteName,String fixletName) throws RelevanceException{
		return queryFixlets(siteName,"name of it=\"${name}\" and baseline flag of it=true", 
			toMap("name",fixletName));
	}
	public List<FixletID> getBaselineIDsLike(String name) throws RelevanceException {
		return getBaselineIDsLike(null,name);
	}
	public List<FixletID> getBaselineIDsLike(String siteName,String name) throws RelevanceException {
		return queryFixlets(siteName,"name of it as lowercase contains \"${name}\" and baseline flag of it=true", toMap("name",name.toLowerCase()));
	}

	public List<FixletID> getTaskIDsExact(String fixletName) throws RelevanceException{
		return getTaskIDsExact(null,fixletName);
	}
	public List<FixletID> getTaskIDsExact(String siteName,String fixletName) throws RelevanceException{
		return queryFixlets(siteName,"name of it=\"${name}\" and task flag of it=true", 
			toMap("name",fixletName));
	}

	public List<FixletID> getTaskIDsLike(String name) throws RelevanceException {
		return getTaskIDsLike(null,name);
	}
	public List<FixletID> getTaskIDsLike(String siteName,String name) throws RelevanceException {
		return queryFixlets(siteName,"name of it as lowercase contains \"${name}\" and task flag of it=true", toMap("name",name.toLowerCase()));
	}

	public List<FixletID> getAnalysisIDsExact(String fixletName) throws RelevanceException{
		return getAnalysisIDsExact(null,fixletName);
	}
	public List<FixletID> getAnalysisIDsExact(String siteName,String fixletName) throws RelevanceException{
		return queryFixlets(siteName,"name of it=\"${name}\" and analysis flag of it=true", 
			toMap("name",fixletName));
	}
	public List<FixletID> getAnalysisIDsLike(String name) throws RelevanceException {
		return getAnalysisIDsLike(null,name);
	}
	public List<FixletID> getAnalysisIDsLike(String siteName,String name) throws RelevanceException {
		return queryFixlets(siteName,"name of it as lowercase contains \"${name}\" and analysis flag of it=true", toMap("name",name.toLowerCase()));
	}
	
	public List<SiteID> getSiteIDsExact(String siteName) throws RelevanceException {
		SessionRelevanceQuery srq = SessionRelevanceBuilder
				.fromRelevance(SITE_RELEVANCE+" whose (display name of it=\"${siteName}\" or name of it=\"${siteName}\")"
								).addColumns(SITE_COLUMNS).build();

		srq.getParameter("siteName").setValue(siteName);

		return client.executeQuery(srq, SiteID.class);
	}
	

	public List<SiteID> getSiteIDsLike(String siteName) throws RelevanceException {
		SessionRelevanceQuery srq = SessionRelevanceBuilder
				.fromRelevance(SITE_RELEVANCE+" whose (display name as lowercase of it contains \"${siteName}\" or name of it as lowercase contains \"${siteName}\")"
								).addColumns(SITE_COLUMNS).build();

		srq.getParameter("siteName").setValue(siteName.toLowerCase());

		return client.executeQuery(srq, SiteID.class);
	}
	
	public List<GroupID> getGroupIDsExact(String name) throws RelevanceException{
		SessionRelevanceQuery srq = SessionRelevanceBuilder
				.fromRelevance(GROUP_RELEVANCE+" whose (name of it=\"${name}\")"
						).addColumns(GROUP_COLUMNS).build();

		srq.getParameter("name").setValue(name);

		return client.executeQuery(srq, GroupID.class);
	}
	
	public Optional<ActionID> getActionID(long id) throws RelevanceException{
		List<ActionID> actions = getActionIDs(" of bes actions whose (id of it=${actionid})",toMap("actionid",Long.toString(id)));
		if(actions.size()==0)
			return Optional.absent();
		else
			return Optional.of(actions.get(0));
	}
	
	public List<ActionID> getActionIDsForSite(SiteID site) throws RelevanceException{
		return getActionIDs(
			" of elements of taken action sets of elements of fixlet sets of all bes sites whose "
			+ "(url of it=\"${siteurl}\")",
			toMap("siteurl",site.getUrl()));
	}

	public List<ActionID> getActionIDsForSite(SiteID siteId, State state) throws RelevanceException {
		return getActionIDs(
			" of elements whose (state of it = \"${state}\" of taken action sets of elements of fixlet sets of all bes sites whose "
			+ "(url of it=\"${siteurl}\")",
			toMap("siteurl",siteId.getUrl(),
				  "state",state.name()));
	}
	public List<ActionID> getActionIDsForFixlet(FixletID fixlet) throws RelevanceException{
		return getActionIDs(
			" of elements of taken action set of bes fixlet whose (id of it =${fixletid})",
			toMap("fixletid",fixlet.getId().toString()));
	}
	public List<ActionID> getActionIDsForFixlet(FixletID fixlet,State state) throws RelevanceException{
		return getActionIDs(
			" of elements whose (state of it = \"${state}\") of taken action set of bes fixlet whose (id of it =${fixletid})",
			toMap(
				"fixletid",fixlet.getId().toString(),
				"state",state.name()
			));
	}
	public List<ActionID> getActionIDsLike(String name) throws RelevanceException{
		return getActionIDs(" of bes actions whose (name of it as lowercase contains \"${name}\")",toMap("name",name.toLowerCase()));
	}

	public List<ActionID> getActionIDsLike(String name, State state) throws RelevanceException {
		return getActionIDs(" of bes actions whose (name of it as lowercase contains \"${name}\" and state of it =\"${state}\")"
			,toMap("name",name.toLowerCase(),
				"state",state.name()));
	}
	public List<ActionID> getActionIDsAll() throws RelevanceException {
		return getActionIDs(" of bes actions",toMap());
	}
	
	public List<ActionID> getActionIDsAllState(ActionID.State state) throws RelevanceException {
		return getActionIDs(" of bes actions whose (state of it=\"${state}\")",toMap("state",state.name()));
	}
	
	public List<ActionID> getActionIDs(String customRelevance,Map<String,String> params) throws RelevanceException{
		SessionRelevanceQuery srq = SessionRelevanceBuilder
				.fromRelevance(ACTION_RELEVANCE+customRelevance).addColumns(ACTION_COLUMNS).build();
		for(Entry<String,String> param:params.entrySet()){
			srq.getParameter(param.getKey()).setValue(param.getValue());
		}

		return client.executeQuery(srq, ActionID.class);
	}
	

	
}
