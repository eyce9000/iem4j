package com.github.eyce9000.iem.api.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bigfix.schemas.bes.Fixlet;
import com.bigfix.schemas.bes.FixletWithActions;
import com.bigfix.schemas.bes.GroupRelevance;
import com.bigfix.schemas.bes.RelevanceString;
import com.bigfix.schemas.bes.SearchComponent;
import com.bigfix.schemas.bes.SearchComponentGroupReference;
import com.bigfix.schemas.bes.SearchComponentPropertyReference;
import com.bigfix.schemas.bes.SearchComponentRelevance;
import com.bigfix.schemas.bes.TrueFalseComparison;
import com.github.eyce9000.iem.api.RESTAPI;
import com.github.eyce9000.iem.api.model.GroupID;
import com.github.eyce9000.iem.api.model.GroupID.GroupType;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceBuilder;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.google.common.base.Optional;

public class RelevanceTranslator {
	private static final String[] GROUP_QUERY_COLUMNS = new String[]{
		"groupId","groupName","serverUrl","groupType"
	};
	private static final String GROUP_QUERY="("
			+ "	id of it, " 
			+ "	name of it, " 
			+ " url of current bes server,"
			+ " (if automatic flag of it = true then \"Automatic\""
			+ "  else \"Manual\")"
			+ ") of bes computer groups whose (name of it = \"${groupName}\")"; 
	
	private SessionRelevanceQuery groupQuery;
	private RESTAPI client;
	public enum Function{AND,OR}

	protected RelevanceTranslator(){};
	
	public RelevanceTranslator(RESTAPI client){
		this.client = client;
		groupQuery = SessionRelevanceBuilder
				.fromRelevance(GROUP_QUERY)
				.addColumns(GROUP_QUERY_COLUMNS)
				.build();
	}

	public RelevanceString buildRelevance(Fixlet fixlet) throws Exception{
		if(fixlet.getRelevance()!=null && fixlet.getRelevance().size() > 0){
			return buildRelevance(fixlet.getRelevance());
		}
		else
			return buildRelevance(fixlet.getGroupRelevance());
	}
	
	public RelevanceString buildRelevance(GroupRelevance relevance) throws Exception{
		Function function;
		if(relevance.isJoinByIntersection())
			function = Function.AND;
		else
			function = Function.OR;
		
		List<RelevanceString> relevanceStrings = new ArrayList<RelevanceString>();
		
		for(SearchComponent component : relevance.getSearchComponent()){
			RelevanceString r = null;
			if(component instanceof SearchComponentRelevance){
				r = buildRelevance((SearchComponentRelevance)component);
			}
			if(component instanceof SearchComponentPropertyReference){
				r = buildRelevance((SearchComponentPropertyReference)component);
			}
			if(component instanceof SearchComponentGroupReference){
				r = buildRelevance((SearchComponentGroupReference)component);
			}
			if(r!=null)
				relevanceStrings.add(r);
		}
		String joined = "(version of client >= \"6.0.0.0\") AND ("+buildRelevance(relevanceStrings, function).getValue()+")";
		
		RelevanceString result = new RelevanceString();
		result.setValue(joined);
		return result;
	}

	public RelevanceString buildRelevance(SearchComponentPropertyReference component) throws Exception{
		String r = component.getRelevance().getValue().trim();
		String format = "exists true whose (if true then ("+r+") else false)";
		RelevanceString formattedRelevance = new RelevanceString();
		formattedRelevance.setValue(format);
		return formattedRelevance;
	}
	public RelevanceString buildRelevance(SearchComponentRelevance component) throws Exception{
		String r = component.getRelevance().getValue().trim();
		String format = "exists true whose (if true then ("+r+") else false)";
		switch(component.getComparison()){
			case IS_TRUE:
				break;
			case IS_FALSE:
				format = "not ("+format+")";
				break;
		}
		RelevanceString formattedRelevance = new RelevanceString();
		formattedRelevance.setValue(format);
		return formattedRelevance;
	}
	
	public RelevanceString buildRelevance(SearchComponentGroupReference component) throws Exception{
		Optional<GroupID> idHolder = getGroupID(component.getGroupName());
		if(!idHolder.isPresent())
			throw new Exception("Unable to find group id for group name \""+component.getGroupName()+"\"");
		
		GroupID groupId = idHolder.get();
		
		if(groupId.getType()==GroupType.Automatic){
			String format = "exists true whose (if true then (member of group %d of site \"actionsite\") else false)";
			switch(component.getComparison()){
			case IS_MEMBER:
				break;
			case IS_NOT_MEMBER:
				format = "not ("+format+")";
				break;
			}
			RelevanceString relevance = new RelevanceString();
			relevance.setValue(String.format(format,groupId.getId()));
			return relevance;
		}
		else{
			String format = "exists true whose (if true then (exists setting \"__Group_0_%s\" whose (value of it is \"True\") of client) else false)";
			switch(component.getComparison()){
			case IS_MEMBER:
				break;
			case IS_NOT_MEMBER:
				format = "not ("+format+")";
				break;
			}
			RelevanceString relevance = new RelevanceString();
			relevance.setValue(String.format(format,groupId.getName()));
			return relevance;
		}
	}

	public RelevanceString buildRelevance(List<RelevanceString> relevance){
		return buildRelevance(relevance,Function.AND);
	}
	
	public RelevanceString buildRelevance(List<RelevanceString> relevance, Function function){
		if(relevance.size()==1)
			return relevance.get(0);
		List<String> relevanceStrings = new ArrayList<String>();
		for(RelevanceString r : relevance){
			relevanceStrings.add("("+r.getValue().trim()+")");
		}
		String builtRelevance = "";
		//Specific to an earlier version?
//		for(int i=0; i<relevanceStrings.size(); i++){
//			String singleRelevance = relevanceStrings.get(i);
//			if(i> 0){
//				builtRelevance += " "+function.name()+" "+singleRelevance; 
//				if(i<relevanceStrings.size()-1){
//					builtRelevance = "("+builtRelevance+")";
//				}
//			}
//			else{
//				builtRelevance = singleRelevance;
//			}
//		}
		
		
		
		RelevanceString newRelevance = new RelevanceString();
		newRelevance.setValue(StringUtils.join(relevanceStrings," "+function.name()+" "));
		return newRelevance;
	}
	
	protected Optional<GroupID> getGroupID(String groupName) throws RelevanceException{
		groupQuery.getParameter(0).setValue(groupName);
		List<GroupID> results = client.executeQuery(groupQuery, GroupID.class);
		if(results.size()==0)
			return Optional.absent();
		else
			return Optional.of(results.get(0));
	}
	
}

