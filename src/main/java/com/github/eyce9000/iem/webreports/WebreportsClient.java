package com.github.eyce9000.iem.webreports;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;
import javax.xml.bind.JAXBException;
import javax.xml.datatype.XMLGregorianCalendar;

import org.joda.time.DateTime;

import com.bigfix.schemas.relevance.ResultList;
import com.bigfix.schemas.relevance.StructuredRelevanceResult;
import com.github.eyce9000.iem.api.ClientBuilderWrapper;
import com.github.eyce9000.iem.api.RelevanceClient;
import com.github.eyce9000.iem.api.relevance.DataType;
import com.github.eyce9000.iem.api.relevance.QueryResultColumn;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.RowSerializer;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.github.eyce9000.iem.api.relevance.handlers.HandlerException;
import com.github.eyce9000.iem.api.relevance.handlers.RawResultHandler;
import com.github.eyce9000.iem.api.relevance.handlers.impl.RawResultHandlerDefault;
import com.github.eyce9000.iem.api.relevance.handlers.impl.TypedResultListHandler;
import com.github.eyce9000.iem.webreports.relevance.Envelope;
import com.github.eyce9000.iem.webreports.relevance.RequestBuilder;

public class WebreportsClient implements RelevanceClient{
	private String password;
	private String username;
	private WebTarget apiRoot;
	private URI	uriBase;
	private TokenHolder tokenHolder = new TimedTokenHolder();

	public WebreportsClient(URI uri, String username, String password) throws JAXBException, Exception{
		this(ClientBuilderWrapper.defaultBuilder().build(),uri,username,password);
	}
	
	public WebreportsClient(String host,String username, String password) throws IllegalArgumentException, UriBuilderException, Exception{
		this(ClientBuilderWrapper.defaultBuilder().build(),host,username,password);
	}
	
	public WebreportsClient(Client client, String host, String username, String password) throws IllegalArgumentException, UriBuilderException, JAXBException {
		this(client,UriBuilder.fromPath("/soap").host(host).scheme("http").port(80).build(),username,password);	
	}
	
	public WebreportsClient(Client client, URI uri, String username, String password) throws JAXBException{
		this.uriBase = uri;
		this.username = username;
		this.password = password;
	    
        apiRoot = client.target(UriBuilder.fromUri(uriBase));
	}
	
	public void setTokenHolder(TokenHolder holder){
		this.tokenHolder = holder;
	}
	
	private synchronized String getToken() throws RelevanceException{
		String token = tokenHolder.getToken();
		if(token==null){
			RequestBuilder builder = new RequestBuilder();
			builder.login(username, password);
			Envelope request = builder.buildRelevanceRequest("\"test\"");
			Envelope response = request(request);
			token = response.getHeader().getResponseHeader().getSessionToken();
			tokenHolder.setToken(token);
		}
		return token;
	}
	
	private StructuredRelevanceResult executeQuery(String relevance) throws JAXBException, RelevanceException{
		RequestBuilder builder = new RequestBuilder();
		builder.authenticate(username, getToken());
		
		Envelope envelope = builder.buildRelevanceRequest(relevance);
		Envelope response = request(envelope);
		
		return response.getBody().getStructuredRelevanceResponse().getStructuredRelevanceResult();
	}
	
	private Envelope request(Envelope request) throws RelevanceException{
		Entity<Envelope> entity = Entity.entity(request, MediaType.TEXT_XML);
		
		Envelope response = apiRoot.request().accept(MediaType.TEXT_XML).post(entity, Envelope.class);
		if(response.getBody().getFaultResponse()!=null){
			throw new RelevanceException(response.getBody().getFaultResponse().getFaultString());
		}
		return response;
	}
	
	@Override
	public List<Map<String,Object>> executeQuery(SessionRelevanceQuery srq) throws RelevanceException{
		RawResultHandlerDefault handler = new RawResultHandlerDefault();
		try{
		executeQueryWithHandler(srq,handler);
		}
		catch(HandlerException ex){
			throw new RelevanceException(ex);
		}
		return handler.getRawResults();
	}
	
	@Override
	public void executeQueryWithHandler(SessionRelevanceQuery srq, RawResultHandler handler) throws RelevanceException, HandlerException{
		String query = srq.constructQuery();
		try{
			StructuredRelevanceResult relevanceResult = executeQuery(query);
			String error = relevanceResult.getError();
			if(error!=null){
				RelevanceException exp = new RelevanceException(error);
				handler.onError(exp);
				throw exp;
			}
			processResponse(relevanceResult, srq,handler);
		}
		catch(JAXBException jaxex){
			throw new RelevanceException(jaxex);
		}
	}
	
	@Override
	public <T> List<T> executeQuery(SessionRelevanceQuery srq, Class<? extends T> clazz) throws RelevanceException{
		return executeQuery(srq,clazz,null);
	}

	@Override
	public <T> List<T> executeQuery(SessionRelevanceQuery srq, Class<? extends T> clazz,RowSerializer serializer) throws RelevanceException{
		TypedResultListHandler<T> handler = new TypedResultListHandler<T>(clazz);
		if(serializer!=null)
			handler.setSerializer(serializer);
		try{
			executeQueryWithHandler(srq,handler);
		}
		catch(HandlerException ex){
			throw new RelevanceException(ex);
		}
		return handler.getResults();
	}

	
	private void processResponse(StructuredRelevanceResult relevanceResult, SessionRelevanceQuery srq, RawResultHandler handler) throws HandlerException{
		List<QueryResultColumn> columns = srq.getColumns();
		List<Object> untypedResults = relevanceResult.getResults().getBooleanOrIntegerOrString();
		if(untypedResults.isEmpty()){
			handler.onComplete(relevanceResult.getEvaltime().longValue());
			return;
		}
		
		List firstRow = getRow(0,untypedResults);
		int diff = firstRow.size() - columns.size();
    	if(diff > 0){
    		List<QueryResultColumn> newColumns = new ArrayList<QueryResultColumn>(columns);
    		for(int i=0; i<firstRow.size(); i++){
    			if(i>=columns.size()){
    				newColumns.add(new QueryResultColumn("col"+i,DataType.string));
    			}
    		}
    		columns = newColumns;
    	}
    	else if (diff < 0){
    		while(columns.size() > firstRow.size()){
        		columns.remove(columns.size()-1);
    		}
    	}
    	srq.setColumns(columns);
		for(int rowNum=0; rowNum<untypedResults.size(); rowNum++){
			List row = getRow(rowNum,untypedResults);
			Object[] rowValues = new Object[columns.size()];
			HashMap<String,Object> sampleRowValues = new HashMap<String,Object>();
	    	for(int colNum=0; colNum<columns.size(); colNum++){
	    		QueryResultColumn column = columns.get(colNum);
				Object value;
				Object rawValue = row.get(colNum);
				if(rawValue instanceof BigDecimal)
					value = ((BigDecimal)rawValue).doubleValue();
				else if(rawValue instanceof BigInteger)
					value = ((BigInteger)rawValue).intValue();
				else if(rawValue instanceof XMLGregorianCalendar){
					value = new DateTime(((XMLGregorianCalendar)rawValue).toGregorianCalendar().getTimeInMillis()).toDate();
				}
				else
					value = rawValue;
				
				rowValues[colNum] = value;
				sampleRowValues.put(column.getName(),value);
	    	}
	    	try{
	    		handler.onResult(sampleRowValues);
	    	}
	    	catch(Exception ex){
	    		throw new HandlerException(ex);
	    	}
		}
		handler.onComplete(relevanceResult.getEvaltime().longValue());
	}
	private List getRow(int rowNumber,List<Object> untypedResults){
		Object result = untypedResults.get(rowNumber);
		List row;
    	if(result instanceof ResultList){
    		row = ((ResultList)result).getBooleanOrIntegerOrString();
    	}
    	else{
    		row = Arrays.asList(new Object[]{result});
    	}
    	return row;
	}
	
}


