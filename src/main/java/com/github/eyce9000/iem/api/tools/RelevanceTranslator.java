package com.github.eyce9000.iem.api.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.bigfix.schemas.bes.FixletWithActions;
import com.bigfix.schemas.bes.GroupRelevance;
import com.bigfix.schemas.bes.RelevanceString;
import com.bigfix.schemas.bes.SearchComponent;
import com.bigfix.schemas.bes.SearchComponentGroupReference;
import com.bigfix.schemas.bes.SearchComponentPropertyReference;
import com.bigfix.schemas.bes.SearchComponentRelevance;
import com.github.eyce9000.iem.api.IEMClient;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceBuilder;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.google.common.base.Optional;

public class RelevanceTranslator {
	private SessionRelevanceQuery groupQuery;
	private IEMClient client;

	public RelevanceTranslator(IEMClient client){
		this.client = client;
		groupQuery = SessionRelevanceBuilder
				.fromRelevance("(id of it,name of it) of bes computer groups whose (name of it = \"${groupName}\")")
				.addColumns("groupId","groupName")
				.build();
	}

	public RelevanceString buildRelevance(FixletWithActions fixlet) throws Exception{
		if(fixlet.getRelevance()!=null && fixlet.getRelevance().size() > 0)
			return buildRelevance(fixlet.getRelevance());
		else
			return buildRelevance(fixlet.getGroupRelevance());
	}
	
	public RelevanceString buildRelevance(GroupRelevance relevance) throws Exception{
		String function;
		if(relevance.isJoinByIntersection())
			function = " AND ";
		else
			function = " OR ";
		
		List<String> relevanceStrings = new ArrayList<String>();
		
		for(SearchComponent component : relevance.getSearchComponent()){
			String r = null;
			if(component instanceof SearchComponentRelevance){
				r = ((SearchComponentRelevance)component).getRelevance().getValue();
				r = "exists true whose (if true then ("+r+") else false)";
			}
			if(component instanceof SearchComponentPropertyReference){
				r = ((SearchComponentPropertyReference)component).getRelevance().getValue();
				r = "exists true whose (if true then ("+r+") else false)";
			}
			if(component instanceof SearchComponentGroupReference){
				r = buildRelevance((SearchComponentGroupReference)component).getValue();
			}
			if(r!=null)
				relevanceStrings.add("("+r+")");
		}
		String joined = "(version of client >= \"6.0.0.0\") AND ("+StringUtils.join(relevanceStrings,function)+")";
		RelevanceString result = new RelevanceString();
		result.setValue(joined);
		return result;
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
		if(relevance.size()==1)
			return relevance.get(0);
		List<String> relevanceStrings = new ArrayList<String>();
		for(RelevanceString r : relevance){
			relevanceStrings.add("("+r.getValue()+")");
		}
		RelevanceString newRelevance = new RelevanceString();
		newRelevance.setValue(StringUtils.join(relevanceStrings," AND "));
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
