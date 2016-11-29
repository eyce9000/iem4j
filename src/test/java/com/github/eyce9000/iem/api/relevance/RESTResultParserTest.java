package com.github.eyce9000.iem.api.relevance;

import static org.junit.Assert.*;

import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bigfix.schemas.relevance.ResultList;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.github.eyce9000.iem.api.relevance.handlers.HandlerException;
import com.github.eyce9000.iem.api.relevance.handlers.RawResultHandler;

public class RESTResultParserTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test()
			throws XMLStreamException, JAXBException, RelevanceException, HandlerException {
		RawResultHandler handler = new RawResultHandler() {

			@Override
			public void onResult(Map<String, Object> result) {
				System.out.print("\t");
				for (Entry<String, Object> entry : result.entrySet()) {
					System.out.print(entry + "\t");
				}
				System.out.println();
			}

			@Override
			public void onError(Exception ex) {
				System.out.println(ex.getMessage());
			}

			@Override
			public void onComplete(long timestamp) {
			}

		};
		new RESTResultParser().parse(new SessionRelevanceQuery(),
			RESTResultParserTest.class.getResourceAsStream("relevance1.xml"), handler);
		new RESTResultParser().parse(new SessionRelevanceQuery(),
			RESTResultParserTest.class.getResourceAsStream("relevance2.xml"), handler);
		RelevanceException relevanceException = null;
		

		relevanceException = null;
		try {
			new RESTResultParser().parse(new SessionRelevanceQuery(),
				RESTResultParserTest.class.getResourceAsStream("relevance-fail.xml"), handler);
		} catch (RelevanceException e) {
			relevanceException = e;
		}
		assertNotNull(relevanceException);
		

		relevanceException = null;
		try {
			new RESTResultParser().parse(new SessionRelevanceQuery(),
				RESTResultParserTest.class.getResourceAsStream("relevance4.xml"), handler);
		} catch (RelevanceException e) {
			relevanceException = e;
		}
		assertNotNull(relevanceException);

	}
}
