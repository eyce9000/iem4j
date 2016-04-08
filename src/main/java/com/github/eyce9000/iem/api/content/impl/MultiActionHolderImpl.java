package com.github.eyce9000.iem.api.content.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bigfix.schemas.bes.AbstractAction;
import com.bigfix.schemas.bes.Site;
import com.github.eyce9000.iem.api.content.MultiActionHolder;
import com.github.eyce9000.iem.api.content.SingleActionHolder;
import com.github.eyce9000.iem.api.model.ActionID;
import com.google.common.base.Optional;

public class MultiActionHolderImpl implements MultiActionHolder {
	private ContentAPIImpl api;
	private List<ActionID> ids;
	private List<SingleActionHolder> allCache = null;

	MultiActionHolderImpl(ContentAPIImpl api,List<ActionID> ids){
		this.ids = ids;
		this.api = api;
	}
	@Override
	public List<ActionID> ids() {
		return Collections.unmodifiableList(ids);
	}

	@Override
	public List<AbstractAction> get() {
		List<AbstractAction> actions = new ArrayList<AbstractAction>();
		for(SingleActionHolder holder:all()){
			AbstractAction action = holder.get();
			actions.add(action);
		}
		return Collections.unmodifiableList(actions);
	}

	@Override
	public boolean isEmpty() {
		return ids.isEmpty();
	}

	@Override
	public Optional<SingleActionHolder> first() {
		if(isEmpty())
			return Optional.absent();
		List<SingleActionHolder> all = all();
		return Optional.of(all.get(0));
	}

	@Override
	public List<SingleActionHolder> all() {
		if(allCache==null){
			List<SingleActionHolder> cache = new ArrayList<SingleActionHolder>(ids.size());
			for(ActionID id:ids){
				cache.add(new SingleActionHolderImpl(api,id));
			}
			allCache = Collections.unmodifiableList(cache);
		}
		return allCache;
	}

}
