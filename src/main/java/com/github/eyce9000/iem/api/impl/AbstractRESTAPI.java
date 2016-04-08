package com.github.eyce9000.iem.api.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;

import com.bigfix.schemas.bes.BES;
import com.bigfix.schemas.besapi.BESAPI;
import com.github.eyce9000.iem.api.RelevanceAPI;
import com.github.eyce9000.iem.api.relevance.DataType;
import com.github.eyce9000.iem.api.relevance.QueryResultColumn;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.github.eyce9000.iem.api.relevance.handlers.HandlerException;
import com.github.eyce9000.iem.api.relevance.handlers.RawResultHandler;
import com.github.eyce9000.iem.api.relevance.results.QueryResult;
import com.github.eyce9000.iem.api.relevance.results.ResultTuple;
import com.google.common.base.Optional;

public abstract class AbstractRESTAPI implements RelevanceAPI {
	static {
		Logger.getLogger("org.apache.http.client.protocol").setLevel(Level.OFF);
	}
	public static enum Action {
		Get, Post, Put, Delete
	}

	protected Unmarshaller	besUnmarshaller;
	protected Marshaller	besMarshaller;
	protected URI			baseURI;
	protected Executor		client;


	
	protected <T> List<T> getContent(HttpResponse resp, Class<T> clazz){
		if (resp.getStatusLine().getStatusCode() == 404)
			return Collections.emptyList();
		Object response = handleResponse(resp);
		if(response instanceof BES){
			return getBESContent((BES) response);
		}
		else if(response instanceof BESAPI){
			return getBESAPIContent((BESAPI) response);
		}
		else{
			throw new RuntimeException("Unknown root class "+response.getClass().getName());
		}
	}
	
	protected <T> Optional<T> getSingleContent(HttpResponse resp, Class<T> clazz){
		List<T> response = getContent(resp,clazz);
		if(response.isEmpty())
			return Optional.absent();
		else
			return Optional.of(response.get(0));
	}

	protected <T> List<T> getBESContent(BES bes) {
		List<T> values = new ArrayList<T>();
		for (Object object : bes.getFixletOrTaskOrAnalysis()) {
			values.add((T) object);
		}
		return values;
	}
	protected <T> List<T> getBESAPIContent(BESAPI besapi) {
		List<T> values = new ArrayList<T>();
		for (JAXBElement<?> wrapper : besapi.getFixletOrReplicationServerOrReplicationLink()) {
			values.add((T) wrapper.getValue());
		}
		return values;
	}

	protected <T extends Serializable> HttpResponse requestBES(Action action, String path, T value){
		BES bes = new BES();
		bes.getFixletOrTaskOrAnalysis().add(value);
		return request(action, path, bes);
	}

	protected <T> HttpResponse request(Action action, String path, T value) {
		try {
			URI uri = new URIBuilder(baseURI).setPath(path).build();

			Request req = null;
			switch (action) {
				case Put: {
					req = Request.Put(uri);
					break;
				}
				case Post: {
					req = Request.Post(uri);
					break;
				}
				case Delete: {
					req = Request.Delete(uri);
					break;
				}
				case Get: {
					req = Request.Get(uri);
					break;
				}
			}
			if (value != null) {
				if(value instanceof String){
					req.bodyString((String)value, ContentType.TEXT_PLAIN);
				}
				else{
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
					besMarshaller.marshal(value, new OutputStreamWriter(baos));
	
					req.bodyByteArray(baos.toByteArray(), ContentType.APPLICATION_XML);
				}
			}
			return client.authPreemptive(baseURI.getHost()).execute(req).returnResponse();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}

	}

	protected Object handleResponse(HttpResponse resp) {
		int code = resp.getStatusLine().getStatusCode();
		if (code >= 200 && code < 300) {
			try {
				return besUnmarshaller.unmarshal(resp.getEntity().getContent());
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} else {
			try {

				throw new RuntimeException(code + ": " + toString(resp));
			} catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
	}

	protected String toString(HttpResponse response) {
		try {
			StringBuilder content = new StringBuilder();
			BufferedReader reader = new BufferedReader(
				new InputStreamReader(response.getEntity().getContent()));
			String line = null;
			while ((line = reader.readLine()) != null)
				content.append(line).append("\n");
			return content.toString();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	// *******************************************************************
	// R E S P O N S E / G E T M E T H O D S
	// *******************************************************************

	public String getRaw(String path) {
		return toString(get(path));
	}

	public HttpResponse get(String path) {
		return request(Action.Get, path, null);
	}
	public HttpResponse delete(String path) {
		return request(Action.Delete, path, null);
	}

	public HttpResponse post(String path, BESAPI besapi) {
		return request(Action.Post, path, besapi);
	}
	public HttpResponse post(String path, BES besapi) {
		return request(Action.Post, path, besapi);
	}
	public  HttpResponse post(String path) {
		return request(Action.Post, path, null);
	}
	public <T> HttpResponse post(String path, T bes) {
		return request(Action.Post, path, bes);
	}
	public <T extends Serializable> HttpResponse postBES(String path, T value) {
		return requestBES(Action.Post, path, value);
	}
	public <T extends Serializable,R> List<R> postBES(String path, T value, Class<R> type){
		return getContent(postBES(path,value),type);
	}

	public <T> HttpResponse put(String path, T value) {
		return request(Action.Put, path, value);
	}
	public <T extends Serializable> HttpResponse putBES(String path, T value) {
		return requestBES(Action.Put, path, value);
	}

	protected String buildSiteTarget(String base, String siteType, String site) {
		String target;
		target = base + "/" + siteType;
		if (site != null) {
			target += "/" + site;
		}
		return target;
	}

	// *******************************************************************
	// R E L E V A N C E M E T H O D S
	// *******************************************************************

}
