package com.github.eyce9000.iem.api.relevance;

import java.util.ArrayList;
import java.util.List;

public abstract class SessionRelevanceBuilder {
	String relevance;
	List<QueryResultColumn> columns = new ArrayList<QueryResultColumn>();
	
	public SessionRelevanceQuery build(){
		SessionRelevanceQuery query = SessionRelevanceQuery.parseQuery(relevance);
		query.setQuery(relevance);
		query.setColumns(columns);
		
		return query;
	}
	
	public SessionRelevanceBuilder addColumns(String... names){
		for(String name:names){
			addColumn(name);
		}
		return this;
	}
	
	public SessionRelevanceBuilder addColumns(List<String> names){
		for(String name:names){
			addColumn(name);
		}
		return this;
	}
	
	public SessionRelevanceBuilder addColumn(String name){
		columns.add(new QueryResultColumn(name,DataType.string));
		return this;
	}
	
	public static SessionRelevanceBuilder fromRelevance(String relevance){
		SessionRelevanceBuilder builder = new SessionRelevanceBuilder(){};
		builder.relevance = relevance;
		return builder;
	}
}
