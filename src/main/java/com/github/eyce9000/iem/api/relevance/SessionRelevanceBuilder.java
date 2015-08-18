package com.github.eyce9000.iem.api.relevance;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

public abstract class SessionRelevanceBuilder {
	static Unmarshaller unmarshaller;
	static {
		try{
			JAXBContext ctxt = JAXBContext.newInstance(SessionRelevanceQuery.class);
			unmarshaller = ctxt.createUnmarshaller();
		}
		catch(Exception ex){
			
		}
	}
	
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
	
	public static SessionRelevanceQuery fromFile(File file) throws JAXBException{
		return (SessionRelevanceQuery)unmarshaller.unmarshal(file);
	}
	public static SessionRelevanceQuery fromStream(InputStream is) throws JAXBException{
		return (SessionRelevanceQuery)unmarshaller.unmarshal(is);
	}
}
