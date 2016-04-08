package com.github.eyce9000.iem.api.content.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.bigfix.schemas.bes.Fixlet;
import com.github.eyce9000.iem.api.content.ContentAPI;
import com.github.eyce9000.iem.api.content.MultiFixletHolder;
import com.github.eyce9000.iem.api.content.SingleFixletHolder;
import com.github.eyce9000.iem.api.content.ContentAPI.MultiContent;
import com.github.eyce9000.iem.api.model.FixletID;
import com.google.common.base.Optional;

public class MultiFixletHolderImpl<F extends Fixlet> implements MultiFixletHolder<F>{
	List<FixletID> ids;
	private List<SingleFixletHolder<F>> allCache;
	private ContentAPIImpl api;
	
	MultiFixletHolderImpl(ContentAPIImpl api,List<FixletID> ids){
		this.api = api;
		this.ids = ids;
	}
	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.content.impl.IMultiFixletHolder#ids()
	 */
	@Override
	public List<FixletID> ids() {
		return ids;
	}

	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.content.impl.IMultiFixletHolder#get()
	 */
	@Override
	public List<F> get() {
		List<F> all = new ArrayList<F>();
		for(SingleFixletHolder<F> holder:all()){
			all.add(holder.get());
		}
		return Collections.unmodifiableList(all);
	}
	
	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.content.impl.IMultiFixletHolder#isEmpty()
	 */
	@Override
	public boolean isEmpty(){
		return ids.isEmpty();
	}
	
	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.content.impl.IMultiFixletHolder#first()
	 */
	@Override
	public Optional<SingleFixletHolder<F>> first(){
		if(isEmpty())
			return Optional.absent();
		List<SingleFixletHolder<F>> all = all();
		return Optional.of(all.get(0));
	}
	/* (non-Javadoc)
	 * @see com.github.eyce9000.iem.api.content.impl.IMultiFixletHolder#all()
	 */
	@Override
	public List<SingleFixletHolder<F>> all() {
		if(allCache==null){
			List<SingleFixletHolder<F>> all = new ArrayList<SingleFixletHolder<F>>(ids.size());
			for(FixletID id:ids){
				all.add(new SingleFixletHolderImpl(api,id));
			}
			allCache = Collections.unmodifiableList(all);
		}
		
		return allCache;
	}
}
