package com.github.eyce9000.iem.api.tools;

import java.math.BigInteger;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bigfix.schemas.bes.Baseline;
import com.bigfix.schemas.bes.Baseline.BaselineComponentCollection.BaselineComponentGroup;
import com.bigfix.schemas.bes.Baseline.BaselineComponentCollection.BaselineComponentGroup.BaselineComponent;
import com.bigfix.schemas.bes.FixletAction;
import com.bigfix.schemas.bes.FixletWithActions;
import com.github.eyce9000.iem.api.IEMClient;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceBuilder;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.google.common.base.Optional;

public class BaselineSynchronizer {
	Logger log = LoggerFactory.getLogger(getClass());
	
	private SessionRelevanceQuery fixletQuery,groupQuery;
	private RelevanceTranslator translator;
	private IEMClient client;
	
	public BaselineSynchronizer(IEMClient client){
		this.client = client;
		fixletQuery = SessionRelevanceBuilder
				.fromRelevance("(name of site of it,tag of site of it, id of it) of bes fixlets whose (id of it = ${fixletId})")
				.addColumns("siteName","siteType","fixletId")
				.build();
		this.translator = new RelevanceTranslator(client);
	}
	public void synchronize(String siteType, String site, long id) throws Exception{
		Optional<Baseline> baselineHolder = client.getBaseline(siteType, site, id);
		if(!baselineHolder.isPresent())
			throw new Exception(String.format("Unable to find baseline %s %s %d",siteType,site,id));

		Baseline baseline = baselineHolder.get();
		rebuildBaselineComponents(baseline);
		client.updateBaseline(siteType, site, id, baseline);
	}
	
	public void rebuildBaselineComponents(Baseline baseline) throws Exception{
		for(BaselineComponentGroup group : baseline.getBaselineComponentCollection().getBaselineComponentGroup()){
			for(BaselineComponent component : group.getBaselineComponent()){
				updateComponent(component);
			}
		}
	}
	
	private void updateComponent(BaselineComponent component) throws Exception{
		BigInteger id = component.getSourceID();
		String actionName = component.getActionName();
		Optional<FixletWithActions> fixletHolder = findFixlet(id);
		if(!fixletHolder.isPresent())
			throw new Exception("Unable to find fixlet with id "+id.toString());
		
		FixletWithActions fixlet = fixletHolder.get();
		
		Optional<FixletAction> foundActionHolder = findAction(fixlet,actionName);
		if(!foundActionHolder.isPresent())
			throw new Exception("Unable to find action "+actionName+" in fixlet "+id);
		
		FixletAction action = foundActionHolder.get();
		component.setActionScript(action.getActionScript());
		component.setSuccessCriteria(action.getSuccessCriteria());
		component.setRelevance(translator.buildRelevance(fixlet));
		log.debug(component.getRelevance().getValue());
	}
	
	
	private Optional<FixletAction> findAction(FixletWithActions fixlet, String actionName){
		List<FixletAction> actions = new ArrayList<FixletAction>(fixlet.getAction());
		actions.add(fixlet.getDefaultAction());
		FixletAction foundAction = null;
		for(FixletAction action: actions){
			if(action.getID().equals(actionName))
				foundAction = action;
		}
		return Optional.fromNullable(foundAction);
	}
	
	private Optional<FixletWithActions> findFixlet(BigInteger id) throws MalformedURLException, RelevanceException{
		String idStr = id.toString();
		fixletQuery.getParameter(0).setValue(idStr);
		List<Map<String,Object>> results = client.executeQuery(fixletQuery);
		
		String siteType = (String)results.get(0).get("siteType");
		String siteName = (String)results.get(0).get("siteName");
		
		if(siteType.equals("actionsite")){
			siteType = "master";
			siteName = null;
		}
		else if(siteType.contains("opsite"))
			siteType = "operator";
		else if(siteType.contains("Custom"))
			siteType = "custom";
		
		
		return client.getFixlet(siteType,siteName,id.longValue());
	}
}
