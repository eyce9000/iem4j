package com.github.eyce9000.iem.api.content;

import java.util.ArrayList;
import java.util.List;

import com.bigfix.schemas.bes.Site;
import com.github.eyce9000.iem.api.content.ContentAPI.MultiContent;
import com.github.eyce9000.iem.api.content.impl.SingleSiteHolderImpl;
import com.github.eyce9000.iem.api.model.SiteID;

public interface MultiSiteHolder extends MultiContent<SiteID,Site,SingleSiteHolder>{
}
