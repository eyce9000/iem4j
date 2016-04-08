package com.github.eyce9000.iem.api.content;

import com.bigfix.schemas.bes.Fixlet;
import com.github.eyce9000.iem.api.content.ContentAPI.RetrievableContent;
import com.github.eyce9000.iem.api.content.ContentAPI.UpdateableContent;
import com.github.eyce9000.iem.api.content.impl.SingleFixletHolderImpl;
import com.github.eyce9000.iem.api.content.impl.SingleSiteHolderImpl;
import com.github.eyce9000.iem.api.model.FixletID;

public interface SingleFixletHolder<F extends Fixlet> extends RetrievableContent<FixletID,F>,UpdateableContent<FixletID,F>,MultiActionProvider{

	@Override
	public SingleFixletHolder<F> update(F fixlet);
	
	public SingleSiteHolderImpl getSite();
}
