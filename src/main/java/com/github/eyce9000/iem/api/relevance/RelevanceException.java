package com.github.eyce9000.iem.api.relevance;

public class RelevanceException extends Exception {
	public RelevanceException(String exception){
		super(exception);
	}
	public RelevanceException(Exception ex){
		super(ex);
	}
}
