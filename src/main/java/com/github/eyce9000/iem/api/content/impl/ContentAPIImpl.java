package com.github.eyce9000.iem.api.content.impl;

import com.bigfix.schemas.bes.Analysis;
import com.bigfix.schemas.bes.Baseline;
import com.bigfix.schemas.bes.Fixlet;
import com.bigfix.schemas.bes.FixletWithActions;
import com.bigfix.schemas.bes.Site;
import com.bigfix.schemas.bes.Task;
import com.github.eyce9000.iem.api.RESTAPI;
import com.github.eyce9000.iem.api.content.ContentAPI;
import com.github.eyce9000.iem.api.content.MultiActionHolder;
import com.github.eyce9000.iem.api.content.MultiFixletHolder;
import com.github.eyce9000.iem.api.content.MultiSiteHolder;
import com.github.eyce9000.iem.api.content.SingleActionHolder;
import com.github.eyce9000.iem.api.content.SingleFixletHolder;
import com.github.eyce9000.iem.api.content.SingleGroupHolder;
import com.github.eyce9000.iem.api.content.SingleSiteHolder;
import com.github.eyce9000.iem.api.model.ActionID;
import com.github.eyce9000.iem.api.model.ActionID.State;
import com.github.eyce9000.iem.api.model.FixletID;
import com.github.eyce9000.iem.api.model.SiteID;
import com.github.eyce9000.iem.api.model.FixletID.ResourceType;
import com.github.eyce9000.iem.api.model.GroupID;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.google.common.base.Optional;

import static com.github.eyce9000.iem.api.content.impl.ContentQuery.*;

import java.util.ArrayList;
import java.util.List;

public class ContentAPIImpl implements ContentAPI {
	RESTAPI client;
	ContentQuery fixletQuery;

	public ContentAPIImpl(RESTAPI parent){
		this.client = parent;
		this.fixletQuery = new ContentQuery(parent);
	}
	
	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.impl.ContentAPI#baseline(java.lang.String)
	 */
	@Override
	public SingleFixletHolder<Baseline> baseline(String name) throws RelevanceException{
		List<FixletID> fixlets = fixletQuery.getBaselineIDsLike(name);
		if(fixlets.isEmpty())
			throw new RelevanceException("Unable to find fixlet with name '"+name+"'");
		if(fixlets.size()>1)
			throw new RelevanceException("Found multiple fixlets with name '"+name+"'");
		return new SingleFixletHolderImpl<Baseline>(this,fixlets.get(0));
	}

	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.impl.ContentAPI#baselines(java.lang.String)
	 */
	@Override
	public MultiFixletHolder<Baseline> baselines(String name) throws RelevanceException{
		return new MultiFixletHolderImpl<Baseline>(this,fixletQuery.getBaselineIDsLike(name));
	}

	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.impl.ContentAPI#task(java.lang.String)
	 */
	@Override
	public SingleFixletHolder<Task> task(String name) throws RelevanceException{
		List<FixletID> fixlets = fixletQuery.getTaskIDsLike(name);
		if(fixlets.isEmpty())
			throw new RelevanceException("Unable to find task with name '"+name+"'");
		if(fixlets.size()>1)
			throw new RelevanceException("Found multiple tasks with name '"+name+"'");
		return new SingleFixletHolderImpl<Task>(this,fixlets.get(0));
	}

	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.impl.ContentAPI#tasks(java.lang.String)
	 */
	@Override
	public MultiFixletHolder<Task> tasks(String name) throws RelevanceException{
		return new MultiFixletHolderImpl<Task>(this,fixletQuery.getTaskIDsLike(name));
	}

	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.impl.ContentAPI#analysis(java.lang.String)
	 */
	@Override
	public SingleFixletHolder<Analysis> analysis(String name) throws RelevanceException{
		List<FixletID> fixlets = fixletQuery.getAnalysisIDsLike(name);
		if(fixlets.isEmpty())
			throw new RelevanceException("Unable to find analysis with name '"+name+"'");
		if(fixlets.size()>1)
			throw new RelevanceException("Found multiple analyses with name '"+name+"'");
		return new SingleFixletHolderImpl<Analysis>(this,fixlets.get(0));
	}

	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.impl.ContentAPI#analyses(java.lang.String)
	 */
	@Override
	public MultiFixletHolder<Analysis> analyses(String name) throws RelevanceException{
		return new MultiFixletHolderImpl<Analysis>(this,fixletQuery.getAnalysisIDsLike(name));
	}
	
	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.impl.ContentAPI#fixlet(java.lang.String)
	 */
	@Override
	public SingleFixletHolder<Fixlet> fixlet(String name) throws RelevanceException{
		List<FixletID> fixlets = fixletQuery.getFixletIDsExact(name);
		if(fixlets.isEmpty())
			throw new RelevanceException("Unable to find fixlet with name '"+name+"'");
		if(fixlets.size()>1)
			throw new RelevanceException("Found multiple fixlets with name '"+name+"'");
		return new SingleFixletHolderImpl<Fixlet>(this,fixlets.get(0));
	}
	
	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.impl.ContentAPI#fixlets(java.lang.String)
	 */
	@Override
	public MultiFixletHolderImpl<Fixlet> fixlets(String name) throws RelevanceException{
		List<FixletID> fixlets = fixletQuery.getFixletIDsLike(name);
		return new MultiFixletHolderImpl<Fixlet>(this,fixlets);
	}

	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.impl.ContentAPI#site(java.lang.String)
	 */
	@Override
	public SingleSiteHolder site(String name) throws RelevanceException{
		List<SiteID> sites = fixletQuery.getSiteIDsExact(name);
		if(sites.isEmpty())
			throw new RelevanceException("Unable to find fixlet with name '"+name+"'");
		if(sites.size()>1)
			throw new RelevanceException("Found multiple fixlets with name '"+name+"'");
		return new SingleSiteHolderImpl(this,sites.get(0));
	}
	
	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.impl.ContentAPI#sites(java.lang.String)
	 */
	@Override
	public MultiSiteHolder sites(String name) throws RelevanceException {
		List<SiteID> sites = fixletQuery.getSiteIDsLike(name);
		return new MultiSiteHolderImpl(this,sites);
	}
	
	<F extends Fixlet> F getFixlet(FixletID resource){
		SiteID siteId = resource.getSite();
		String siteType = siteId.getType().format();
		String siteName = siteId.getFormattedName();
		long fixletId = resource.getId().longValue();
		
		switch (resource.getType()) {
		case Fixlet:
			return (F)client.getFixlet(siteType, siteName, fixletId).get();
		case Baseline:
			return (F)client.getBaseline(siteType, siteName, fixletId).get();
		case Task:
			return (F)client.getTask(siteType, siteName, fixletId).get();
		case Analysis:
			return (F)client.getAnalysis(siteType, siteName, fixletId).get();
		default:
			return null;
		}
	}
	
	FixletID updateFixlet(FixletID resource,Fixlet fixlet){
		SiteID siteId = resource.getSite();
		String siteType = siteId.getType().format();
		String siteName = siteId.getFormattedName();
		long fixletId = resource.getId().longValue();

		switch (resource.getType()) {
			case Fixlet:
				client.updateFixlet(siteType, siteName, fixletId, (FixletWithActions) fixlet);
				break;
			case Baseline:
				client.updateBaseline(siteType, siteName, fixletId, (Baseline) fixlet);
				break;
			case Task:
				client.updateTask(siteType, siteName, fixletId, (Task) fixlet);
				break;
			case Analysis:
				client.updateAnalysis(siteType, siteName, fixletId, (Analysis) fixlet);
				break;
		}
		
		FixletID newId = resource.clone();
		newId.setName(fixlet.getTitle());
		return newId;
	}
	
	FixletID createFixlet(SiteID siteId, Fixlet fixlet){
		String siteType = siteId.getType().format();
		String siteName = siteId.getFormattedName();
		
		FixletID id = new FixletID();
		id.setSite(siteId);
		id.setName(fixlet.getTitle());

		if(fixlet instanceof Baseline){
			com.bigfix.schemas.besapi.BESAPI.Baseline response;
			response = client.createBaseline(siteType, siteName, (Baseline) fixlet).get();
			id.setId(response.getID());
			id.setType(ResourceType.Baseline);
		}
		else if(fixlet instanceof Task){
			com.bigfix.schemas.besapi.BESAPI.Task response;
			response = client.createTask(siteType, siteName, (Task) fixlet).get();
			id.setId(response.getID());
			id.setType(ResourceType.Task);
		}
		else if(fixlet instanceof Analysis){
			com.bigfix.schemas.besapi.BESAPI.Analysis response;
			response = client.createAnalysis(siteType, siteName, (Analysis) fixlet).get();
			id.setId(response.getID());
			id.setType(ResourceType.Analysis);
		}
		else{
			com.bigfix.schemas.besapi.BESAPI.Fixlet response;
			response = client.createFixlet(siteType, siteName, (FixletWithActions) fixlet)
					.get();
			id.setId(response.getID());
			id.setType(ResourceType.Fixlet);
		}
		return id;
	}

	@Override
	public SingleGroupHolder group(String name) throws RelevanceException {
		List<GroupID> groups = fixletQuery.getGroupIDsExact(name);
		if(groups.isEmpty())
			throw new RelevanceException("Unable to find group with name '"+name+"'");
		if(groups.size()>1)
			throw new RelevanceException("Found multiple groups with name '"+name+"'");
		return new SingleGroupHolderImpl(this,groups.get(0));
	}

	@Override
	public SingleActionHolder action(long id) throws RelevanceException {
		Optional<ActionID> idholder = fixletQuery.getActionID(id);
		if(!idholder.isPresent())
			throw new RelevanceException("Unable to find action with id "+id);
		return new SingleActionHolderImpl(this,idholder.get());
	}

	@Override
	public MultiActionHolder actions(String name) throws RelevanceException {
		return new MultiActionHolderImpl(this,fixletQuery.getActionIDsLike(name));
	}

	@Override
	public MultiActionHolder actions(String name, State state) throws RelevanceException {
		return new MultiActionHolderImpl(this,fixletQuery.getActionIDsLike(name,state));
	}
}
