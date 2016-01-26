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
import com.bigfix.schemas.bes.ActionSuccessCriteria;
import com.bigfix.schemas.bes.FixletAction;
import com.bigfix.schemas.bes.FixletWithActions;
import com.bigfix.schemas.bes.Task;
import com.github.eyce9000.iem.api.RESTAPI;
import com.github.eyce9000.iem.api.model.FixletID;
import com.github.eyce9000.iem.api.model.SiteID;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceBuilder;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.google.common.base.Optional;

public class BaselineSynchronizer {
	Logger log = LoggerFactory.getLogger(getClass());

	private static final String FIXLET_RELEVANCE = "" 
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
			+ ") "
			+ "of bes fixlets";

	private static final String[] FIXLET_COLUMNS = new String[]{"id", "name","serverUrl", "type", "siteName", "siteType", "siteUrl", "displayName"};
	
	private SessionRelevanceQuery fixletQuery,groupQuery;
	private RelevanceTranslator translator;
	protected RESTAPI client;
	
	public BaselineSynchronizer(RESTAPI client){
		this.client = client;
		fixletQuery = SessionRelevanceBuilder
				.fromRelevance(FIXLET_RELEVANCE+" whose (id of it = ${fixletId} and (url of site of it = \"${siteUrl}\" or url of bes site whose(master site flag of it = true) = \"${siteUrl}\"))")
				.addColumns(FIXLET_COLUMNS)
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
		Optional<FixletWithActions> fixletHolder = findFixlet(id,component.getSourceSiteURL());
		if(!fixletHolder.isPresent())
			throw new Exception("Unable to find fixlet "+component.getSourceSiteURL()+" "+id.toString());
		
		FixletWithActions fixlet = fixletHolder.get();
		
		Optional<FixletAction> foundActionHolder = findAction(fixlet,actionName);
		if(!foundActionHolder.isPresent())
			throw new Exception("Unable to find action "+actionName+" in fixlet "+id);
		
		FixletAction action = foundActionHolder.get();
		component.setActionScript(action.getActionScript());
		ActionSuccessCriteria criteria = action.getSuccessCriteria();
		if(criteria==null){
			criteria = new ActionSuccessCriteria();
			if(fixlet instanceof Task)
				criteria.setOption("RunToCompletion");
			else 
				criteria.setOption("OriginalRelevance");
		}
		component.setSuccessCriteria(criteria);
		component.setRelevance(translator.buildRelevance(fixlet));
		log.trace(component.getRelevance().getValue());
	}
	

	private Optional<FixletAction> findAction(FixletWithActions fixlet, String actionName){
		List<FixletAction> actions = new ArrayList<FixletAction>(fixlet.getAction());
		if(fixlet.getDefaultAction()!=null)
			actions.add(fixlet.getDefaultAction());
		
		FixletAction foundAction = null;
		for(FixletAction action: actions){
			if(action.getID().equals(actionName))
				foundAction = action;
		}
		return Optional.fromNullable(foundAction);
	}
	
	private Optional<FixletWithActions> findFixlet(BigInteger id, String siteUrl) throws Exception{
		String idStr = id.toString();
		fixletQuery.getParameter("fixletId").setValue(idStr);
		fixletQuery.getParameter("siteUrl").setValue(siteUrl);
		List<FixletID> results = client.executeQuery(fixletQuery,FixletID.class);
		
		if(results.size()==0)
			throw new Exception("Unable to find fixlet "+siteUrl+" "+idStr);
		
		FixletID fixletId = results.get(0);
		SiteID siteId = fixletId.getSite();
		
		return client.getFixlet(siteId.getType().format(),siteId.getFormattedName(),id.longValue());
	}
}
