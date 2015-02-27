package com.github.eyce9000.iem.api.relevance.results;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.eclipse.persistence.oxm.annotations.XmlPath;

@XmlType(name = "Query")
@XmlRootElement(name="BESAPI")
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryResult {
	@XmlPath("Query/@Resource")
	private String query;
	
	@XmlPath("Query/Result/Tuple")
	private List<ResultTuple> results = new ArrayList<ResultTuple>();
	
	@XmlPath("Query/Evaluation")
	private QueryEvaluation evaluationTime;

	@XmlPath("Query/Error/text()")
	private String error;
	
	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<ResultTuple> getResults() {
		return results;
	}

	public void setResults(List<ResultTuple> results) {
		this.results = results;
	}

	public QueryEvaluation getEvaluationTime() {
		return evaluationTime;
	}

	public void setEvaluationTime(QueryEvaluation evaluationTime) {
		this.evaluationTime = evaluationTime;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
	
}
