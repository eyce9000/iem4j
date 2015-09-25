package com.github.eyce9000.iem.api.relevance;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.joda.time.DateTime;

import com.bigfix.schemas.relevance.ResultList;
import com.github.eyce9000.iem.api.relevance.DataType;
import com.github.eyce9000.iem.api.relevance.QueryResultColumn;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.github.eyce9000.iem.api.relevance.handlers.HandlerException;
import com.github.eyce9000.iem.api.relevance.handlers.RawResultHandler;
import com.github.eyce9000.iem.api.relevance.results.QueryEvaluation;
import com.github.eyce9000.iem.api.relevance.results.ResultTuple;
import com.github.eyce9000.iem.api.serialization.ResultAnswerAdapter;
import com.github.eyce9000.iem.api.serialization.ResultAnswerAdapter.Answer;

public class RESTResultParser {
	private JAXBContext jc;
	private Unmarshaller unmarshaller;
	private ResultAnswerAdapter adapter = new ResultAnswerAdapter();
	List<QueryResultColumn> columns = null;
	
	public RESTResultParser() throws JAXBException{

		jc = JAXBContext.newInstance(ResultTuple.class,Answer.class,QueryEvaluation.class);
        unmarshaller = jc.createUnmarshaller();
	}
	
	public synchronized void parse(SessionRelevanceQuery srq,InputStream in,RawResultHandler handler) throws XMLStreamException, JAXBException, RelevanceException, HandlerException{
		columns = null;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
		XMLEventReader reader = factory.createXMLEventReader(new BufferedReader(new InputStreamReader(in)));

		QueryEvaluation evaluation = null;
		boolean inResults = false;
		
		while(reader.hasNext()){
			reader.nextEvent();
			XMLEvent event = reader.peek();
			if(event==null)
				break;
			if(event.isStartElement()){
				StartElement el = event.asStartElement();
				String localName = el.getName().getLocalPart();

				if(localName.equals("Error")){
					JAXBElement<String> element = unmarshaller.unmarshal(reader,String.class);
					RelevanceException ex = new RelevanceException(element.getValue());
					handler.onError(ex);
					throw ex;
				}
				else if(localName.equals("Result")){
					inResults = true;
				}
				else if(inResults){
					try{
						Map<String,Object> result = processElement(srq,el,reader);
						handler.onResult(result);
					}
					catch(Exception e){
						throw new HandlerException(e);
					}
					
				}
				else if(localName.equals("Evaluation")){
					evaluation = (QueryEvaluation)unmarshaller.unmarshal(reader);
				}
			}
			if(event.isEndElement()){
				EndElement el = event.asEndElement();
				String localName = el.getName().getLocalPart();
				if(localName.equals("Result"))
					inResults = false;
			}
		}
		if(evaluation!=null)
			handler.onComplete(evaluation.getTime().getMillis());
	}
	
	private Map<String,Object> processElement(SessionRelevanceQuery srq,StartElement el,XMLEventReader reader) throws Exception{
		ResultTuple row = null;
		
		JAXBElement element = null;
		String localName = el.getName().getLocalPart();
		if(localName.equals("Answer")){
			Answer answer = (Answer)unmarshaller.unmarshal(reader);
			row = new ResultTuple();
			row.setAnswers(Arrays.asList(adapter.unmarshal(answer)));
		}
		else if(localName.equals("Tuple")){
			row = (ResultTuple)unmarshaller.unmarshal(reader);
		}
		if(columns==null){
			columns = buildColumns(row.getAnswers(),srq);
		}
		List<Object> rowData = row.getAnswers();
		HashMap<String,Object> sampleRowValues = new LinkedHashMap<String,Object>();
    	for(int colNum=0; colNum<columns.size(); colNum++){
    		QueryResultColumn column = columns.get(colNum);
			sampleRowValues.put(column.getName(),rowData.get(colNum));
    	}
    	return sampleRowValues;
	}
	
	private List<QueryResultColumn> buildColumns(List<Object> hintRow,SessionRelevanceQuery srq){
		List<QueryResultColumn> columns = new ArrayList<QueryResultColumn>(srq.getColumns());
		int diff = hintRow.size() - columns.size();
    	if(diff > 0){
    		List<QueryResultColumn> newColumns = new ArrayList<QueryResultColumn>(columns);
    		for(int i=0; i<hintRow.size(); i++){
    			if(i>=columns.size()){
    				newColumns.add(new QueryResultColumn("col"+i,DataType.string));
    			}
    		}
    		columns = newColumns;
    	}
    	else if (diff < 0){
    		while(columns.size() > hintRow.size()){
        		columns.remove(columns.size()-1);
    		}
    	}
    	return columns;
	}
}
