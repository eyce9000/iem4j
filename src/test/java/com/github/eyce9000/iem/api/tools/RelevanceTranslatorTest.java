package com.github.eyce9000.iem.api.tools;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigfix.schemas.bes.GroupRelevance;
import com.bigfix.schemas.bes.RelevanceString;
import com.bigfix.schemas.bes.SearchComponentRelevance;
import com.bigfix.schemas.bes.TrueFalseComparison;
import com.github.eyce9000.iem.api.ConnectionDoc;
import com.github.eyce9000.iem.api.IEMAPI;
import com.github.eyce9000.iem.api.model.GroupID;
import com.github.eyce9000.iem.api.model.GroupID.GroupType;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceBuilder;

public class RelevanceTranslatorTest {

	private static Unmarshaller	unmarshaller;
	private static ConnectionDoc	webreportsDoc;
	private static ConnectionDoc	restapiDoc;
	private RelevanceTranslator	translator;
	private IEMAPI	temClient;

	@BeforeClass
	public static void setupClass(){
		JAXBContext context;
		try {
			context = JAXBContext.newInstance(ConnectionDoc.class);
			unmarshaller = context.createUnmarshaller();
			webreportsDoc = (ConnectionDoc)unmarshaller.unmarshal(new File("config/test-webreports.xml"));
			restapiDoc = (ConnectionDoc)unmarshaller.unmarshal(new File("config/test-restapi.xml"));
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Before
	public void setUp() throws Exception {
		temClient = new IEMAPI(restapiDoc.host,restapiDoc.username,restapiDoc.password);
		translator = new RelevanceTranslator(temClient);
	}
	
	@Test
	public void testBuildComputerGroupRelevance() throws RelevanceException{
		{
			String groupName = (String) temClient.executeQuery(
				SessionRelevanceBuilder
					.fromRelevance("names of bes computer groups whose (automatic flag of it = true)")
					.addColumn("name")
					.build()
				).get(0).get("name");
			GroupID groupId = translator.getGroupID(groupName).get();
			assertTrue(groupId.getName().equals(groupName));
			assertTrue(groupId.getType()==GroupType.Automatic);
			assertTrue(groupId.getId()!=null);
		}
		{
			String groupName = (String) temClient.executeQuery(
				SessionRelevanceBuilder
					.fromRelevance("names of bes computer groups whose (automatic flag of it = false)")
					.addColumn("name")
					.build()
				).get(0).get("name");
			GroupID groupId = translator.getGroupID(groupName).get();
			assertTrue(groupId.getName().equals(groupName));
			assertTrue(groupId.getType()==GroupType.Manual);
			assertTrue(groupId.getId()!=null);
		}
	}
	
	@Test
	public void testBuildRelevanceList() {
		List<RelevanceString> relevanceList = new ArrayList<RelevanceString>();
		for(int i=0; i<4; i++){
			RelevanceString relevanceString = new RelevanceString();
			relevanceString.setValue("r"+i);
			relevanceList.add(relevanceString);
		}
		RelevanceString builtRelevance = translator.buildRelevance(relevanceList);
		assertThat(builtRelevance.getValue(),is("(r0) AND (r1) AND (r2) AND (r3)"));
	}
	
	@Test
	public void testBuildRelevanceSingular() throws Exception{
		GroupRelevance groupRelevance = new GroupRelevance();
		groupRelevance.setJoinByIntersection(true);
		
		
		SearchComponentRelevance searchComponent = new SearchComponentRelevance();
		searchComponent.setComparison(TrueFalseComparison.IS_TRUE);
		RelevanceString relevance = new RelevanceString();
		relevance.setValue("r0 ");
		searchComponent.setRelevance(relevance);
		groupRelevance.getSearchComponent().add(searchComponent);
		
		RelevanceString builtRelevance = translator.buildRelevance(groupRelevance);
		assertTrue(builtRelevance.getValue().equals("(version of client >= \"6.0.0.0\") AND (exists true whose (if true then (r0) else false))"));
	}
	

	@Test
	public void testBuildGroupRelevance() throws Exception{
		GroupRelevance groupRelevance = new GroupRelevance();
		groupRelevance.setJoinByIntersection(false);
		
		{
			SearchComponentRelevance searchComponent = new SearchComponentRelevance();
			searchComponent.setComparison(TrueFalseComparison.IS_TRUE);
			RelevanceString relevance = new RelevanceString();
			relevance.setValue(" r0 ");
			searchComponent.setRelevance(relevance);
			groupRelevance.getSearchComponent().add(searchComponent);
		}
		{
			SearchComponentRelevance searchComponent = new SearchComponentRelevance();
			searchComponent.setComparison(TrueFalseComparison.IS_TRUE);
			RelevanceString relevance = new RelevanceString();
			relevance.setValue(" r1");
			searchComponent.setRelevance(relevance);
			groupRelevance.getSearchComponent().add(searchComponent);
		}
		
		RelevanceString builtRelevance = translator.buildRelevance(groupRelevance);
		assertThat(builtRelevance.getValue(), is("(version of client >= \"6.0.0.0\") AND ((exists true whose (if true then (r0) else false)) OR (exists true whose (if true then (r1) else false)))"));
	}
}
