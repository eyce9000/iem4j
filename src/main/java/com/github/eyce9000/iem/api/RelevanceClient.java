package com.github.eyce9000.iem.api;

import java.util.List;
import java.util.Map;

import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.RowSerializer;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.github.eyce9000.iem.api.relevance.handlers.HandlerException;
import com.github.eyce9000.iem.api.relevance.handlers.RawResultHandler;

public interface RelevanceClient {
	public List<Map<String,Object>> executeQuery(SessionRelevanceQuery srq) throws RelevanceException;
	
	public void executeQueryWithHandler(SessionRelevanceQuery srq, RawResultHandler handler) throws RelevanceException, HandlerException;
	
	public <T> List<T> executeQuery(SessionRelevanceQuery srq, Class<? extends T> clazz) throws RelevanceException;
	public <T> List<T> executeQuery(SessionRelevanceQuery srq, Class<? extends T> clazz, RowSerializer serializer) throws RelevanceException;
}
