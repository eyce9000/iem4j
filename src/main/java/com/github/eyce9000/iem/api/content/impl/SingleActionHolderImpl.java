package com.github.eyce9000.iem.api.content.impl;

import com.bigfix.schemas.bes.AbstractAction;
import com.bigfix.schemas.bes.Action;
import com.github.eyce9000.iem.api.content.SingleActionHolder;
import com.github.eyce9000.iem.api.model.ActionID;

public class SingleActionHolderImpl implements SingleActionHolder {
	private AbstractAction cache = null;
	private ActionID id;
	private ContentAPIImpl api;
	private boolean deleted = false;

	SingleActionHolderImpl(ContentAPIImpl api,ActionID id){
		this.api = api;
		this.id = id;
	}
	
	@Override
	public ActionID id() {
		return id;
	}

	@Override
	public AbstractAction get() {
		if(deleted)
			throw new IllegalStateException(String.format("Action %d has been previously deleted",id.getId().longValue()));
		if(cache==null)
			cache = api.client.getAction(id.getId().longValue()).get();
		return cache;
	}

	@Override
	public void delete() {
		api.client.deleteAction(id.getId().longValue());
		deleted = true;
	}

}
