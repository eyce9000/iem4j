package com.github.eyce9000.iem.api.content;

import com.bigfix.schemas.bes.AbstractAction;
import com.bigfix.schemas.bes.Action;
import com.github.eyce9000.iem.api.content.ContentAPI.MultiContent;
import com.github.eyce9000.iem.api.model.ActionID;

public interface MultiActionHolder extends MultiContent<ActionID,AbstractAction,SingleActionHolder>{

}
