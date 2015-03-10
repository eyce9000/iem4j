package com.github.eyce9000.iem.api.relevance;

import com.github.eyce9000.iem.api.relevance.results.QueryResult;

public class RelevanceException extends Exception {
	public RelevanceException(String exception){
		super(exception);
	}
	public RelevanceException(Exception ex){
		super(ex);
	}
	public RelevanceException(QueryResult result) {
		super("Relevance error:'"+result.getError()+"' for query '"+result.getQuery()+"'");
	}
}
