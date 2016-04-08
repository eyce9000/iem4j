package com.github.eyce9000.iem.api.content;

import com.bigfix.schemas.bes.ComputerGroup;
import com.github.eyce9000.iem.api.content.ContentAPI.RetrievableContent;
import com.github.eyce9000.iem.api.content.ContentAPI.UpdateableContent;
import com.github.eyce9000.iem.api.model.GroupID;

public interface SingleGroupHolder  extends RetrievableContent<GroupID,ComputerGroup>,UpdateableContent<GroupID,ComputerGroup>{

}
