package com.github.eyce9000.iem.api.content;

import com.github.eyce9000.iem.api.relevance.RelevanceException;

public interface GroupProvider {
	public SingleGroupHolder group(String name) throws RelevanceException;
}
