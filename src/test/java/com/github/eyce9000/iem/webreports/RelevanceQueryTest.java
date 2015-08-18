package com.github.eyce9000.iem.webreports;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.eyce9000.iem.api.ClientBuilderWrapper;
import com.github.eyce9000.iem.api.ConnectionDoc;
import com.github.eyce9000.iem.api.IEMAPI;
import com.github.eyce9000.iem.api.RelevanceAPI;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.RowSerializer;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.github.eyce9000.iem.api.relevance.handlers.AbtractTypedResultHandler;
import com.github.eyce9000.iem.api.relevance.handlers.HandlerException;
import com.github.eyce9000.iem.api.serialization.ResultAnswerAdapter;
import com.github.eyce9000.iem.api.serialization.ResultAnswerAdapter.AnswerValueType;
import com.github.eyce9000.iem.webreports.WebreportsAPI;

public class RelevanceQueryTest {
	IEMAPI temClient;
	RelevanceAPI webreportsClient;
	private static Unmarshaller	unmarshaller;
	private static ConnectionDoc	webreportsDoc;
	private static ConnectionDoc	restapiDoc;

	@BeforeClass
	public static void setupClass(){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(ConnectionDoc.class);
			unmarshaller = context.createUnmarshaller();
			webreportsDoc = (ConnectionDoc)unmarshaller.unmarshal(new File("config/test-webreports.xml"));
			restapiDoc = (ConnectionDoc)unmarshaller.unmarshal(new File("config/test-restapi.xml"));
		} catch (JAXBException e) {
			e.printStackTrace();
		}
	}
	
	@Before
	public void setUp() throws Exception {
		Client jaxrsclient = ClientBuilderWrapper.defaultBuilder().enableLogging().build();
		temClient = new IEMAPI(jaxrsclient,restapiDoc.host,restapiDoc.username,restapiDoc.password);
		webreportsClient = new WebreportsAPI(jaxrsclient,webreportsDoc.host,webreportsDoc.username,webreportsDoc.password);
	}

	@Test
	public void testParser() throws Exception{
		ResultAnswerAdapter adapter = new ResultAnswerAdapter();
		
		ResultAnswerAdapter.Answer answer;
		answer = new ResultAnswerAdapter.Answer();
		answer.setType(AnswerValueType.TIME);
		answer.setValue("Thu, 13 Mar 2014 09:23:25 -0400");
		
		Date time = (Date)adapter.unmarshal(answer);
		assertNotNull(time);
		
		answer = new ResultAnswerAdapter.Answer();
		answer.setType(AnswerValueType.BOOLEAN);
		answer.setValue("True");
		
		Boolean booleanVal = (Boolean)adapter.unmarshal(answer);
		assertNotNull(booleanVal);
	}
	
	@Test
	public void testSessionRelevanceQuery() throws RelevanceException, HandlerException{
		try{
			testSessionRelevanceQuery(temClient);
			testSessionRelevanceQuery(webreportsClient);
			testSessionRelevanceQuery(webreportsClient);
		}
		catch(RelevanceException ex){
			ex.printStackTrace();
			System.out.println(ex.getMessage());
			throw ex;
		}
	}
	
	
	public void testSessionRelevanceQuery(RelevanceAPI client) throws RelevanceException, HandlerException{
		SessionRelevanceQuery srq = SessionRelevanceQuery.parseQuery(
				"(name of it,//@Column computerName\n" +
				" id of it,//@Column computerId\n" +
				" last report time of it //@Column computerReportTime\n" +
				") of bes computers whose (name of it contains \"TEMTESTSRV\")");
		
		List<Map<String,Object>> result = client.executeQuery(srq);
		
		assertTrue(result.size()>0);
		
		for(Map<String,Object> row:result){
			assertNotNull(row.get("computerName"));
			assertTrue(row.get("computerName") instanceof String);
			assertNotNull(row.get("computerId"));
			assertTrue(row.get("computerId") instanceof Integer);
			assertNotNull(row.get("computerReportTime"));
			assertTrue(row.get("computerReportTime") instanceof Date);
		}
		
		
		List<ComputerData> data = client.executeQuery(srq, ComputerData.class, RowSerializer.getJAXBSerializer());
		assertTrue(data.size() > 0);
		for(ComputerData row : data){
			assertNotNull(row.name);
			assertNotNull(row.id);
			assertNotNull(row.time);
		}
		
		srq = SessionRelevanceQuery.parseQuery(
				"names //@Column jscomputerName\n" +
				" of bes computers whose (name of it contains \"TEMTESTSRV\")");
		data = client.executeQuery(srq, ComputerData.class, RowSerializer.getJacksonSerializer());
		assertTrue(data.size() > 0);
		for(ComputerData row : data){
			assertNotNull(row.name);
		}
	}

	
	static class ComputerData{
		@XmlElement(name="computerName")
		@JsonProperty("jscomputerName")
		String name;
		@XmlElement(name="computerId")
		@JsonProperty("jscomputerId")
		int id;
		@XmlElement(name="computerReportTime")
		@JsonProperty("jscomputerReportTime")
		DateTime time;
	}
}
