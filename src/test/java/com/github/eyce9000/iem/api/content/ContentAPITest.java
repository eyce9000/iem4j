package com.github.eyce9000.iem.api.content;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.client.HttpClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.eyce9000.iem.api.ApacheClientBuilder;
import com.github.eyce9000.iem.api.ConnectionDoc;
import com.github.eyce9000.iem.api.RESTAPI;
import com.github.eyce9000.iem.api.model.FixletID;
import com.github.eyce9000.iem.api.relevance.RelevanceException;

public class ContentAPITest {

	private static Unmarshaller	unmarshaller;
	private static ConnectionDoc restapiDoc;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(ConnectionDoc.class);
			unmarshaller = context.createUnmarshaller();
			restapiDoc = (ConnectionDoc)unmarshaller.unmarshal(new File("config/test-restapi.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}

	private RESTAPI temClient;

	@Before
	public void setUp() throws Exception {
		HttpClient client = new ApacheClientBuilder().insecure().build();
		temClient = new RESTAPI(client,restapiDoc.host,restapiDoc.username,restapiDoc.password);
	}

	@Test
	public void test() throws RelevanceException {
		List<FixletID> ids = temClient.content().site("BES Support").fixlets("WebUI").ids();
		assertTrue(ids.size() > 0);
	}

}
