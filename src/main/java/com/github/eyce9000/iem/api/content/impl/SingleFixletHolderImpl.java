package com.github.eyce9000.iem.api.content.impl;

import com.bigfix.schemas.bes.Fixlet;
import com.github.eyce9000.iem.api.content.ContentAPI;
import com.github.eyce9000.iem.api.content.ContentAPI.RetrievableContent;
import com.github.eyce9000.iem.api.content.ContentAPI.UpdateableContent;
import com.github.eyce9000.iem.api.content.MultiActionHolder;
import com.github.eyce9000.iem.api.content.SingleActionHolder;
import com.github.eyce9000.iem.api.content.SingleFixletHolder;
import com.github.eyce9000.iem.api.model.ActionID.State;
import com.github.eyce9000.iem.api.model.FixletID;
import com.github.eyce9000.iem.api.relevance.RelevanceException;

public class SingleFixletHolderImpl<F extends Fixlet> implements SingleFixletHolder<F>{
	private FixletID resource;
	private ContentAPIImpl api;

	SingleFixletHolderImpl(ContentAPIImpl api,FixletID value){
		this.resource = value;
		this.api = api;
	}

	@Override
	public FixletID id() {
		return resource;
	}

	@Override
	public F get() {
		return api.getFixlet(resource);
	}

	@Override
	public SingleFixletHolderImpl<F> update(F fixlet) {
		FixletID newId = api.updateFixlet(resource,fixlet);
		return new SingleFixletHolderImpl<F>(api,newId);
	}
	
	@Override
	public SingleSiteHolderImpl getSite(){
		return new SingleSiteHolderImpl(api,resource.getSite());
	}

	@Override
	public MultiActionHolder actions() throws RelevanceException {
		return new MultiActionHolderImpl(api,api.fixletQuery.getActionIDsForFixlet(resource));
	}

	@Override
	public MultiActionHolder actions(State state) throws RelevanceException {
		return new MultiActionHolderImpl(api,api.fixletQuery.getActionIDsForFixlet(resource,state));
	}


}