package com.github.eyce9000.iem.api.relevance;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class QueryResultColumnTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		List<QueryResultColumn> cols = QueryResultColumn.extractColumns("//@Column(date) lastResponseTime\n//@Column name\n//@Column(number) id");
		assertTrue(cols.size()==3);
		assertTrue(cols.get(0).getDataType()==DataType.date);
		assertTrue(cols.get(0).getName().equals("lastResponseTime"));
		assertTrue(cols.get(1).getDataType()==DataType.string);
		assertTrue(cols.get(1).getName().equals("name"));
		assertTrue(cols.get(2).getDataType()==DataType.number);
		assertTrue(cols.get(2).getName().equals("id"));
	}

}
