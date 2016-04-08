package com.github.eyce9000.iem.api.content.impl;

import java.util.ArrayList;
import java.util.List;

import com.bigfix.schemas.bes.Site;
import com.github.eyce9000.iem.api.content.ContentAPI;
import com.github.eyce9000.iem.api.content.ContentAPI.MultiContent;
import com.github.eyce9000.iem.api.content.MultiSiteHolder;
import com.github.eyce9000.iem.api.content.SingleSiteHolder;
import com.github.eyce9000.iem.api.model.SiteID;
import com.google.common.base.Optional;

public class MultiSiteHolderImpl implements MultiSiteHolder{
	private List<SiteID> ids;
	private ContentAPIImpl api;
	private List<SingleSiteHolder> allCache=null;

	public MultiSiteHolderImpl(ContentAPIImpl api,List<SiteID> sites){
		this.ids = sites;
		this.api = api;
	}
	@Override
	public List<SiteID> ids() {
		return ids;
	}

	@Override
	public List<Site> get() {
		List<Site> sites = new ArrayList<Site>();
		for(SingleSiteHolder holder:all()){
			Site site = holder.get();
			sites.add(site);
		}
		return sites;
	}
	@Override
	public Optional<SingleSiteHolder> first() {
		if(isEmpty())
			return Optional.absent();
		List<SingleSiteHolder> all = all();
		return Optional.of(all.get(0));
	}
	@Override
	public List<SingleSiteHolder> all() {
		if(allCache==null){
			allCache = new ArrayList<SingleSiteHolder>(ids.size());
			for(SiteID id:ids){
				allCache.add(new SingleSiteHolderImpl(api,id));
			}
		}
		return allCache;
	}
	@Override
	public boolean isEmpty() {
		return ids.isEmpty();
	}
	
}
