package com.github.eyce9000.iem.api.relevance.handlers.impl;

import java.util.LinkedList;
import java.util.List;

import com.github.eyce9000.iem.api.relevance.handlers.AbtractTypedResultHandler;

public class TypedResultListHandler<T> extends AbtractTypedResultHandler<T>{

	public List<T> results = new LinkedList<T>();
	
	public TypedResultListHandler(Class<? extends T> clazz) {
		super(clazz);
	}

	@Override
	public void onResult(T result) {
		results.add(result);
	}

	public List<T> getResults(){
		return results;
	}

	@Override
	public void onError(Exception ex) {
		ex.printStackTrace();
	}

	@Override
	public void onComplete(long timestamp) {
		// TODO Auto-generated method stub
		
	}
}
