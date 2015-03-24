package com.github.eyce9000.iem.api.tools;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.bigfix.schemas.bes.RelevanceString;
import com.github.eyce9000.iem.api.IEMClient;

public class RelevanceTranslatorTest {

	private RelevanceTranslator	translator;

	@Before
	public void setUp() throws Exception {
		translator = new RelevanceTranslatorStub();
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
		assertTrue(builtRelevance.getValue().equals("(((r0) AND (r1)) AND (r2)) AND (r3)"));
	}
	
	private static class RelevanceTranslatorStub extends RelevanceTranslator{

		public RelevanceTranslatorStub() {
			super();
		}
		
	}
}
