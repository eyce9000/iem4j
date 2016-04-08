package com.github.eyce9000.iem.api.content;

import com.bigfix.schemas.bes.Fixlet;
import com.bigfix.schemas.bes.Site;
import com.github.eyce9000.iem.api.content.ContentAPI.RetrievableContent;
import com.github.eyce9000.iem.api.content.impl.SingleFixletHolderImpl;
import com.github.eyce9000.iem.api.model.SiteID;

public interface SingleSiteHolder extends RetrievableContent<SiteID,Site>,FixletProvider,MultiActionProvider{
	public <F extends Fixlet> SingleFixletHolderImpl<F> createFixlet(Fixlet fixlet);
}
