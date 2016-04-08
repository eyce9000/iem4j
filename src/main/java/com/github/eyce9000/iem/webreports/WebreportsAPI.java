package com.github.eyce9000.iem.webreports;

import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLStreamException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.joda.time.DateTime;

import com.bigfix.schemas.relevance.ResultList;
import com.bigfix.schemas.relevance.StructuredRelevanceResult;
import com.github.eyce9000.iem.api.ApacheClientBuilder;
import com.github.eyce9000.iem.api.RelevanceAPI;
import com.github.eyce9000.iem.api.impl.AbstractRESTAPI;
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
import com.github.eyce9000.iem.webreports.relevance.ResultParser;
import com.github.eyce9000.iem.webreports.relevance.ResultParser.TokenHandler;

public class WebreportsAPI extends AbstractRESTAPI {
	private String			password;
	private String			username;
	private TokenHolder		tokenHolder	= new TimedTokenHolder();

	public WebreportsAPI(HttpClient apacheHttpClient,URI uri, String username, String password)
			throws JAXBException, Exception {
		this.baseURI = uri;
		
		client = Executor.newInstance(apacheHttpClient);
		this.username = username;
		this.password = password;
		initializeJAXB();
	}

	public WebreportsAPI(URI uri, String username, String password) throws Exception {
		this(new ApacheClientBuilder().build(),uri,username,password);
	}

	private void initializeJAXB() throws JAXBException {
		JAXBContext context = JAXBContext.newInstance(Envelope.class);
		besUnmarshaller = context.createUnmarshaller();
		besMarshaller = context.createMarshaller();
	}

	public void setTokenHolder(TokenHolder holder) {
		this.tokenHolder = holder;
	}

	private RequestBuilder getBuilder() {
		RequestBuilder builder = new RequestBuilder();
		String token = tokenHolder.getToken();
		if (token == null) {
			builder.login(username, password);
		} else {
			builder.authenticate(username, token);
		}
		return builder;
	}

	@Override
	public List<Map<String, Object>> executeQuery(SessionRelevanceQuery srq)
			throws RelevanceException {
		RawResultHandlerDefault handler = new RawResultHandlerDefault();
		try {
			executeQueryWithHandler(srq, handler);
		} catch (HandlerException ex) {
			throw new RelevanceException(ex);
		}
		return handler.getRawResults();
	}

	@Override
	public void executeQueryWithHandler(SessionRelevanceQuery srq, final RawResultHandler handler)
			throws RelevanceException, HandlerException {
		RequestBuilder builder = getBuilder();
		Envelope envelope = builder.buildRelevanceRequest(srq.constructQuery());

		TokenHandler wrapper = new TokenHandler(handler) {
			@Override
			public void onToken(String token) {
				tokenHolder.setToken(token);
			}
		};
		HttpResponse response = request(Action.Post, "/soap", envelope);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RelevanceException(
				"API Response " + response.getStatusLine().getStatusCode());
		}
		try {
			ResultParser parser = new ResultParser();
			parser.parse(srq, response.getEntity().getContent(), wrapper);
		} catch (Exception e) {
			throw new RelevanceException(e);
		}
	}

	@Override
	public <T> List<T> executeQuery(SessionRelevanceQuery srq, Class<? extends T> clazz)
			throws RelevanceException {
		return executeQuery(srq, clazz, null);
	}

	@Override
	public <T> List<T> executeQuery(SessionRelevanceQuery srq, Class<? extends T> clazz,
			RowSerializer serializer) throws RelevanceException {
		TypedResultListHandler<T> handler = new TypedResultListHandler<T>(clazz);
		if (serializer != null)
			handler.setSerializer(serializer);
		try {
			executeQueryWithHandler(srq, handler);
		} catch (HandlerException ex) {
			throw new RelevanceException(ex);
		}
		return handler.getResults();
	}

}
