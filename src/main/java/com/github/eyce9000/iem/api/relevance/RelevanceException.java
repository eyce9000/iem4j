package com.github.eyce9000.iem.api.relevance;

import com.bigfix.schemas.besapi.BESAPI.Query;
import com.github.eyce9000.iem.api.relevance.results.QueryResult;


public class RelevanceException extends Exception {
	public RelevanceException(Exception ex){
		super(ex);
	}
	public RelevanceException(QueryResult result) {
		super("Relevance error:'"+result.getError()+"' for query '"+result.getQuery()+"'");
	}
	public RelevanceException(String faultString) {
		super(faultString);
	}
	public RelevanceException(String faultString,Exception source) {
		super(faultString,source);
	}
}
