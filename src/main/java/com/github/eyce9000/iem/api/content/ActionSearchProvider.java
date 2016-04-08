package com.github.eyce9000.iem.api.content;

import com.github.eyce9000.iem.api.model.ActionID;
import com.github.eyce9000.iem.api.relevance.RelevanceException;

public interface ActionSearchProvider {
	public MultiActionHolder actions(String name) throws RelevanceException;
	public MultiActionHolder actions(String name,ActionID.State state) throws RelevanceException;
}
