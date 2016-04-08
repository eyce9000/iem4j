package com.github.eyce9000.iem.api.content.impl;

import java.util.List;

import com.bigfix.schemas.bes.Analysis;
import com.bigfix.schemas.bes.Baseline;
import com.bigfix.schemas.bes.Fixlet;
import com.bigfix.schemas.bes.Site;
import com.bigfix.schemas.bes.Task;
import com.github.eyce9000.iem.api.content.ContentAPI;
import com.github.eyce9000.iem.api.content.ContentAPI.RetrievableContent;
import com.github.eyce9000.iem.api.content.MultiActionHolder;
import com.github.eyce9000.iem.api.content.MultiFixletHolder;
import com.github.eyce9000.iem.api.content.SingleActionHolder;
import com.github.eyce9000.iem.api.content.SingleFixletHolder;
import com.github.eyce9000.iem.api.content.SingleSiteHolder;
import com.github.eyce9000.iem.api.model.ActionID;
import com.github.eyce9000.iem.api.model.ActionID.State;
import com.github.eyce9000.iem.api.model.FixletID;
import com.github.eyce9000.iem.api.model.SiteID;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.google.common.base.Optional;

public class SingleSiteHolderImpl implements SingleSiteHolder{
	private SiteID siteId;
	private ContentAPIImpl api;
	private ContentQuery fixletQuery;

	SingleSiteHolderImpl(ContentAPIImpl api,SiteID siteId){
		this.fixletQuery = api.fixletQuery;
		this.siteId = siteId;
		this.api = api;
	}
	@Override
	public SiteID id() {
		return siteId;
	}

	@Override
	public Site get() {
		String siteType = siteId.getType().format();
		String siteName = siteId.getFormattedName();
		return api.client.getSite(siteType, siteName).get();
	}
	
	@Override
	public <F extends Fixlet> SingleFixletHolderImpl<F> createFixlet(Fixlet fixlet){
		return new SingleFixletHolderImpl(api,api.createFixlet(siteId,fixlet));
	}
	@Override
	public SingleFixletHolder<Baseline> baseline(String name) throws RelevanceException {
		List<FixletID> fixlets = fixletQuery.getBaselineIDsLike(siteId.getName(),name);
		if(fixlets.isEmpty())
			throw new RelevanceException("Unable to find fixlet with name '"+name+"'");
		if(fixlets.size()>1)
			throw new RelevanceException("Found multiple fixlets with name '"+name+"'");
		return new SingleFixletHolderImpl<Baseline>(api,fixlets.get(0));
	}
	@Override
	public MultiFixletHolder<Baseline> baselines(String name) throws RelevanceException {
		return new MultiFixletHolderImpl<Baseline>(api,fixletQuery.getBaselineIDsLike(siteId.getName(),name));
	}
	@Override
	public SingleFixletHolder<Task> task(String name) throws RelevanceException {
		List<FixletID> fixlets = fixletQuery.getTaskIDsLike(siteId.getName(),name);
		if(fixlets.isEmpty())
			throw new RelevanceException("Unable to find task with name '"+name+"'");
		if(fixlets.size()>1)
			throw new RelevanceException("Found multiple tasks with name '"+name+"'");
		return new SingleFixletHolderImpl<Task>(api,fixlets.get(0));
	}
	@Override
	public MultiFixletHolder<Task> tasks(String name) throws RelevanceException {
		return new MultiFixletHolderImpl<Task>(api,fixletQuery.getTaskIDsLike(siteId.getName(),name));
	}
	@Override
	public SingleFixletHolder<Analysis> analysis(String name) throws RelevanceException {
		List<FixletID> fixlets = fixletQuery.getAnalysisIDsLike(siteId.getName(),name);
		if(fixlets.isEmpty())
			throw new RelevanceException("Unable to find analysis with name '"+name+"'");
		if(fixlets.size()>1)
			throw new RelevanceException("Found multiple analyses with name '"+name+"'");
		return new SingleFixletHolderImpl<Analysis>(api,fixlets.get(0));
	}
	@Override
	public MultiFixletHolder<Analysis> analyses(String name) throws RelevanceException {
		return new MultiFixletHolderImpl<Analysis>(api,fixletQuery.getAnalysisIDsLike(siteId.getName(),name));
	}
	@Override
	public SingleFixletHolder<Fixlet> fixlet(String name) throws RelevanceException {
		List<FixletID> fixlets = fixletQuery.getFixletIDsExact(siteId.getName(),name);
		if(fixlets.isEmpty())
			throw new RelevanceException("Unable to find fixlet with name '"+name+"'");
		if(fixlets.size()>1)
			throw new RelevanceException("Found multiple fixlets with name '"+name+"'");
		return new SingleFixletHolderImpl<Fixlet>(api,fixlets.get(0));
	}
	@Override
	public MultiFixletHolder<Fixlet> fixlets(String name) throws RelevanceException {
		List<FixletID> fixlets = fixletQuery.getFixletIDsLike(siteId.getName(),name);
		return new MultiFixletHolderImpl<Fixlet>(api,fixlets);
	}
	@Override
	public MultiActionHolder actions() throws RelevanceException {
		return new MultiActionHolderImpl(api,fixletQuery.getActionIDsForSite(siteId));
	}
	@Override
	public MultiActionHolder actions(State state) throws RelevanceException {
		return new MultiActionHolderImpl(api,fixletQuery.getActionIDsForSite(siteId,state));
	}
}
