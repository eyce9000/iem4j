package com.github.eyce9000.iem.api.relevance.handlers.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.github.eyce9000.iem.api.relevance.handlers.RawResultHandler;


public class RawResultHandlerDefault implements RawResultHandler {

	private List<Map<String,Object>> results = new LinkedList<Map<String,Object>>();
	
	@Override
	public void onResult(Map<String, Object> rawResult) {
		results.add(rawResult);
	}
	
	public List<Map<String,Object>> getRawResults(){
		return results;
	}

	@Override
	public void onError(Exception ex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onComplete(long timestamp) {
		// TODO Auto-generated method stub
		
	}

}
