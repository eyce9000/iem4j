package com.github.eyce9000.iem.api;

import java.net.URI;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Executor;

@Deprecated
public class IEMAPI extends RESTAPI {

	public IEMAPI() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IEMAPI(Executor client, URI uri) throws JAXBException {
		super(client, uri);
		// TODO Auto-generated constructor stub
	}

	public IEMAPI(HttpClient apacheHttpClient, URI uri, String username, String password)
			throws Exception {
		super(apacheHttpClient, uri, username, password);
		// TODO Auto-generated constructor stub
	}

	public IEMAPI(String hostname, String username, String password)
			throws URISyntaxException, Exception {
		super(hostname, username, password);
		// TODO Auto-generated constructor stub
	}

	public IEMAPI(URI uri, String username, String password) throws Exception {
		super(uri, username, password);
		// TODO Auto-generated constructor stub
	}

}
