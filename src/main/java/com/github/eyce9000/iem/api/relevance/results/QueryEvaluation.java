package com.github.eyce9000.iem.api.relevance.results;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.joda.time.Period;

import com.github.eyce9000.iem.api.serialization.XmlPeriodAdapter;

public class QueryEvaluation {
	public static enum Plurality{Plural,Singular,None}
	
	@XmlJavaTypeAdapter(value=XmlPeriodAdapter.class)
	@XmlElement(name="Time")
	private Period time;
	
	@XmlElement(name="Plurality")
	private Plurality plurality=Plurality.None;

	public Period getTime() {
		return time;
	}

	public void setTime(Period time) {
		this.time = time;
	}

	public Plurality getPlurality() {
		return plurality;
	}

	public void setPlurality(Plurality plurality) {
		this.plurality = plurality;
	}
}
