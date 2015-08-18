package com.github.eyce9000.iem.api.relevance.results;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.github.eyce9000.iem.api.serialization.ResultAnswerAdapter;


@XmlRootElement(name="Tuple")
@XmlAccessorType(XmlAccessType.FIELD)
public class ResultTuple {
	@XmlElement(name="Answer")
	@XmlJavaTypeAdapter(value=ResultAnswerAdapter.class)
	List<Object> answers = new ArrayList<Object>();

	public List<Object> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Object> answers) {
		this.answers = answers;
	}
}
