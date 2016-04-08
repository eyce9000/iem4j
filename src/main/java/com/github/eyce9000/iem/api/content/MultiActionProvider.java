package com.github.eyce9000.iem.api.content;

import com.github.eyce9000.iem.api.model.ActionID;
import com.github.eyce9000.iem.api.relevance.RelevanceException;

public interface MultiActionProvider{
	public MultiActionHolder actions() throws RelevanceException;
	public MultiActionHolder actions(ActionID.State state)throws RelevanceException;
}
