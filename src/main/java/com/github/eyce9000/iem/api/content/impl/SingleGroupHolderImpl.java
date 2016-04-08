package com.github.eyce9000.iem.api.content.impl;

import org.apache.commons.lang.NotImplementedException;

import com.bigfix.schemas.bes.ComputerGroup;
import com.github.eyce9000.iem.api.content.ContentAPI.UpdateableContent;
import com.github.eyce9000.iem.api.content.SingleGroupHolder;
import com.github.eyce9000.iem.api.model.GroupID;
import com.github.eyce9000.iem.api.model.GroupID.GroupType;

public class SingleGroupHolderImpl implements SingleGroupHolder{

	private GroupID id;
	private ContentAPIImpl api;

	public SingleGroupHolderImpl(ContentAPIImpl api,GroupID id){
		this.id = id;
		this.api = api;
	}
	
	@Override
	public GroupID id() {
		return id;
	}

	@Override
	public ComputerGroup get() {
		if(id.getType()==GroupType.Manual)
			throw new RuntimeException("Manual groups cannot be managed by the REST API");
		return api.client.getComputerGroup(id.getId().longValue()).get();
	}

	@Override
	public UpdateableContent<GroupID, ComputerGroup> update(ComputerGroup value) {
		
		api.client.updateComputerGroup(id.getId().longValue(),value);
		return this;
	}

}
