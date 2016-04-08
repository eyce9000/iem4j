package com.github.eyce9000.iem.api.content;

import com.bigfix.schemas.bes.AbstractAction;
import com.bigfix.schemas.bes.Action;
import com.github.eyce9000.iem.api.content.ContentAPI.DeletableContent;
import com.github.eyce9000.iem.api.content.ContentAPI.RetrievableContent;
import com.github.eyce9000.iem.api.model.ActionID;

public interface SingleActionHolder extends RetrievableContent<ActionID,AbstractAction>,DeletableContent<ActionID,AbstractAction>{
}
