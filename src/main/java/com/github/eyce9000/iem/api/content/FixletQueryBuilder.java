package com.github.eyce9000.iem.api.content;

import com.bigfix.schemas.bes.Analysis;
import com.bigfix.schemas.bes.Baseline;
import com.bigfix.schemas.bes.Fixlet;
import com.bigfix.schemas.bes.FixletWithActions;
import com.bigfix.schemas.bes.Task;
import com.github.eyce9000.iem.api.model.SiteID;

public interface FixletQueryBuilder {
	public FixletQueryBuilder withName(String name);
	public FixletQueryBuilder withId(long id);
	public FixletQueryBuilder inSite(String siteName);
	public FixletQueryBuilder inSite(SiteID siteId);
	
	public SingleFixletHolder<Task> task();
	public SingleFixletHolder<Analysis> analysis();
	public SingleFixletHolder<FixletWithActions> fixlet();
	public SingleFixletHolder<Baseline> baseline();
	
	public <F extends Fixlet> MultiFixletHolder<F> search();
	public <F extends Fixlet> MultiFixletHolder<F> search(String relevanceQuery);
	public <F extends Fixlet> SingleFixletHolder<F> findOne();
	public <F extends Fixlet> SingleFixletHolder<F> findOne(String relevanceQuery);
}
