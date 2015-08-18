package com.github.eyce9000.iem.webreports;

import java.io.InputStream;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLStreamException;

import org.joda.time.DateTime;

import com.bigfix.schemas.relevance.ResultList;
import com.bigfix.schemas.relevance.StructuredRelevanceResult;
import com.github.eyce9000.iem.api.ClientBuilderWrapper;
import com.github.eyce9000.iem.api.RelevanceAPI;
import com.github.eyce9000.iem.api.relevance.DataType;
import com.github.eyce9000.iem.api.relevance.QueryResultColumn;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.RowSerializer;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.github.eyce9000.iem.api.relevance.handlers.HandlerException;
import com.github.eyce9000.iem.api.relevance.handlers.RawResultHandler;
import com.github.eyce9000.iem.api.relevance.handlers.impl.RawResultHandlerDefault;
import com.github.eyce9000.iem.api.relevance.handlers.impl.TypedResultListHandler;
import com.github.eyce9000.iem.api.serialization.BESContextProvider;
import com.github.eyce9000.iem.webreports.relevance.Envelope;
import com.github.eyce9000.iem.webreports.relevance.RequestBuilder;
import com.github.eyce9000.iem.webreports.relevance.ResultParser;
import com.github.eyce9000.iem.webreports.relevance.ResultParser.TokenHandler;

public class WebreportsAPI implements RelevanceAPI{
	private String password;
	private String username;
	private WebTarget apiRoot;
	private URI	uriBase;
	private TokenHolder tokenHolder = new TimedTokenHolder();
	private Marshaller	marshaller;
	private Unmarshaller	unmarshaller;

	public WebreportsAPI(URI uri, String username, String password) throws JAXBException, Exception{
		this(ClientBuilderWrapper.defaultBuilder().build(),uri,username,password);
	}
	
	public WebreportsAPI(String host,String username, String password) throws IllegalArgumentException, UriBuilderException, Exception{
		this(ClientBuilderWrapper.defaultBuilder().build(),host,username,password);
	}
	
	public WebreportsAPI(Client client, String host, String username, String password) throws IllegalArgumentException, UriBuilderException, JAXBException {
		this(client,UriBuilder.fromPath("/soap").host(host).scheme("http").port(80).build(),username,password);	
	}
	
	public WebreportsAPI(Client client, URI uri, String username, String password) throws JAXBException{
		this.uriBase = uri;
		this.username = username;
		this.password = password;

		JAXBContext context = JAXBContext.newInstance(Envelope.class);
		unmarshaller = context.createUnmarshaller();
		marshaller = context.createMarshaller();
        apiRoot = client.target(UriBuilder.fromUri(uriBase));
	}
	
	public void setTokenHolder(TokenHolder holder){
		this.tokenHolder = holder;
	}
	
	private RequestBuilder getBuilder(){
		RequestBuilder builder = new RequestBuilder();
		String token = tokenHolder.getToken();
		if(token==null){
			builder.login(username, password);
		}
		else{
			builder.authenticate(username, token);
		}
		return builder;
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
	public void executeQueryWithHandler(SessionRelevanceQuery srq, final RawResultHandler handler) throws RelevanceException, HandlerException{
		RequestBuilder builder = getBuilder();
		Envelope envelope = builder.buildRelevanceRequest(srq.constructQuery());
		Entity<Envelope> entity = Entity.entity(envelope, MediaType.TEXT_XML);
		TokenHandler wrapper = new TokenHandler(handler){
			@Override
			public void onToken(String token) {
				tokenHolder.setToken(token);
			}
		};
		Response response = apiRoot.request().accept(MediaType.TEXT_XML).post(entity);
		try{
			ResultParser parser = new ResultParser();
			parser.parse(srq,(InputStream)response.getEntity(), wrapper);
		}
		catch(Exception e){
			throw new RelevanceException(e);
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

	
}


