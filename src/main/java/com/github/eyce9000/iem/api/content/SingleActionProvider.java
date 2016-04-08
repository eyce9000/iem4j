package com.github.eyce9000.iem.api.content;

import com.github.eyce9000.iem.api.relevance.RelevanceException;

public interface SingleActionProvider {
	public SingleActionHolder action(long id) throws RelevanceException;
}
