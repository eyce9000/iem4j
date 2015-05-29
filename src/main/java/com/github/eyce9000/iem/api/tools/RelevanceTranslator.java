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
import com.github.eyce9000.iem.api.IEMAPI;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceBuilder;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.google.common.base.Optional;

public class RelevanceTranslator {
	private SessionRelevanceQuery groupQuery;
	private IEMAPI client;
	public enum Function{AND,OR}

	protected RelevanceTranslator(){};
	
	public RelevanceTranslator(IEMAPI client){
		this.client = client;
		groupQuery = SessionRelevanceBuilder
				.fromRelevance("(id of it,name of it) of bes computer groups whose (name of it = \"${groupName}\")")
				.addColumns("groupId","groupName")
				.build();
	}

	public RelevanceString buildRelevance(Fixlet fixlet) throws Exception{
		if(fixlet.getRelevance()!=null && fixlet.getRelevance().size() > 0)
			return buildRelevance(fixlet.getRelevance());
		else
			return buildRelevance(fixlet.getGroupRelevance());
	}
	
	public RelevanceString buildRelevance(GroupRelevance relevance) throws Exception{
		Function function;
		if(relevance.getJoinByIntersection())
			function = Function.AND;
		else
			function = Function.OR;
		
		List<String> relevanceStrings = new ArrayList<String>();
		
		for(SearchComponent component : relevance.getSearchComponent()){
			String r = null;
			if(component instanceof SearchComponentRelevance){
				r = buildRelevance((SearchComponentRelevance)component).getValue();
			}
			if(component instanceof SearchComponentPropertyReference){
				r = buildRelevance((SearchComponentPropertyReference)component).getValue();
			}
			if(component instanceof SearchComponentGroupReference){
				r = buildRelevance((SearchComponentGroupReference)component).getValue();
			}
			if(r!=null)
				relevanceStrings.add("("+r+")");
		}
		String joined = "(version of client >= \"6.0.0.0\") AND ("+StringUtils.join(relevanceStrings," "+function.name()+" ")+")";
		RelevanceString result = new RelevanceString();
		result.setValue(joined);
		return result;
	}

	public RelevanceString buildRelevance(SearchComponentPropertyReference component) throws Exception{
		String r = component.getRelevance().getValue();
		String format = "exists true whose (if true then ("+r+") else false)";
		RelevanceString formattedRelevance = new RelevanceString();
		formattedRelevance.setValue(format);
		return formattedRelevance;
	}
	public RelevanceString buildRelevance(SearchComponentRelevance component) throws Exception{
		String r = component.getRelevance().getValue();
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
		String format = "exists true whose (if true then (member of group %d of site \"actionsite\") else false)";
		switch(component.getComparison()){
		case IS_MEMBER:
			break;
		case IS_NOT_MEMBER:
			format = "not ("+format+")";
			break;
		}
		
		Optional<Integer> idHolder = getGroupID(component.getGroupName());
		if(!idHolder.isPresent())
			throw new Exception("Unable to find group id for group name \""+component.getGroupName()+"\"");
		
		RelevanceString relevance = new RelevanceString();
		relevance.setValue(String.format(format,idHolder.get()));
		return relevance;
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
		for(int i=0; i<relevanceStrings.size(); i++){
			String singleRelevance = relevanceStrings.get(i);
			if(i> 0){
				builtRelevance += " "+function.name()+" "+singleRelevance; 
				if(i<relevanceStrings.size()-1){
					builtRelevance = "("+builtRelevance+")";
				}
			}
			else{
				builtRelevance = singleRelevance;
			}
		}
		RelevanceString newRelevance = new RelevanceString();
		newRelevance.setValue(builtRelevance);
		return newRelevance;
	}
	
	
	
	private Optional<Integer> getGroupID(String groupName) throws RelevanceException{
		groupQuery.getParameter(0).setValue(groupName);
		List<Map<String,Object>> results = client.executeQuery(groupQuery);
		if(results.size()==0)
			return Optional.absent();
		else
			return Optional.of((Integer)results.get(0).get("groupId"));
	}
	
}

