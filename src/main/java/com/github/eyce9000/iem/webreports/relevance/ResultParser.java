package com.github.eyce9000.iem.webreports.relevance;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
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

public class ResultParser {
	private JAXBContext jc;
	private Unmarshaller unmarshaller;
	private boolean columnsProcessed;
	public ResultParser() throws JAXBException{

		jc = JAXBContext.newInstance(ResultList.class);
        unmarshaller = jc.createUnmarshaller();
	}
	
	public void parse(SessionRelevanceQuery srq,InputStream in,RawResultHandler handler) throws XMLStreamException, JAXBException, RelevanceException, HandlerException{
		XMLInputFactory factory = XMLInputFactory.newInstance();
		factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
		XMLEventReader reader = factory.createXMLEventReader(new BufferedReader(new InputStreamReader(in)));

		
		boolean inResults = false;
		
		while(reader.hasNext()){
			reader.nextEvent();
			XMLEvent event = reader.peek();
			if(event==null)
				break;
			if(event.isStartElement()){
				StartElement el = event.asStartElement();
				String localName = el.getName().getLocalPart();

				if(localName.equals("sessionToken")){
					JAXBElement<String> element = unmarshaller.unmarshal(reader,String.class);
					if(handler instanceof TokenHandler){
						((TokenHandler)handler).onToken(element.getValue());
					}
				}
				if(localName.equals("faultstring")){
					JAXBElement<String> element = unmarshaller.unmarshal(reader,String.class);
					handler.onError(new RelevanceException(element.getValue()));
					throw new RelevanceException(element.getValue());
				}
				if(localName.equals("error")){
					JAXBElement<String> element = unmarshaller.unmarshal(reader,String.class);
					handler.onError(new RelevanceException(element.getValue()));
					throw new RelevanceException(element.getValue());
				}
				
				if(localName.equals("results")){
					inResults = true;
				}
				else if(inResults){
					Map<String,Object> result = processElement(srq,el,reader);
					
					try{
						handler.onResult(result);
					}
					catch(Exception e){
						throw new HandlerException(e);
					}
					
				}
			}
			if(event.isEndElement()){
				EndElement el = event.asEndElement();
				String localName = el.getName().getLocalPart();
				if(localName.equals("results"))
					inResults = false;
				
			}
		}
	}
	
	private Map<String,Object> processElement(SessionRelevanceQuery srq,StartElement el,XMLEventReader reader) throws JAXBException{
		ResultList row = null;
		
		JAXBElement element = null;
		String localName = el.getName().getLocalPart();
		if(localName.equals("String"))
			element = unmarshaller.unmarshal(reader,String.class);
		else if(localName.equals("Boolean"))
			element = unmarshaller.unmarshal(reader,Boolean.class);
		else if(localName.equals("Integer"))
			element = unmarshaller.unmarshal(reader,Integer.class);
		else if(localName.equals("DateTime"))
			element = unmarshaller.unmarshal(reader,XMLGregorianCalendar.class);
		else if(localName.equals("FloatingPoint"))
			element = unmarshaller.unmarshal(reader,Double.class);
		else if(localName.equals("Tuple")){
			JAXBElement<ResultList> jb = unmarshaller.unmarshal(reader, ResultList.class);
			row = jb.getValue();
		}
		if(row==null){
			row = new ResultList();
			row.getBooleanOrIntegerOrString().add(element.getValue());
		}
		return processResultList(row,srq);
	}
	
	private Map<String,Object> processResultList(ResultList result,SessionRelevanceQuery srq){
		if(!columnsProcessed){
			processColumns(srq,result);
			columnsProcessed = true;
		}

		List<QueryResultColumn> columns = srq.getColumns();
		Map<String,Object> hash = new HashMap<String,Object>();
		List<Object> row = result.getBooleanOrIntegerOrString();
		for(int colNum=0; colNum<columns.size(); colNum++){
    		QueryResultColumn column = columns.get(colNum);
			Object value;
			Object rawValue = row.get(colNum);
			if(rawValue instanceof BigDecimal)
				value = ((BigDecimal)rawValue).doubleValue();
			else if(rawValue instanceof BigInteger)
				value = ((BigInteger)rawValue).intValue();
			else if(rawValue instanceof XMLGregorianCalendar){
				value = new DateTime(((XMLGregorianCalendar)rawValue).toGregorianCalendar().getTimeInMillis()).toDate();
			}
			else
				value = rawValue;
			
			hash.put(column.getName(),value);
    	}
		return hash;
	}
	
	private void processColumns(SessionRelevanceQuery srq,ResultList result){
		List<QueryResultColumn> columns = srq.getColumns();
		List<Object> firstRow = result.getBooleanOrIntegerOrString();
		
		int diff = firstRow.size() - columns.size();
    	if(diff > 0){
    		List<QueryResultColumn> newColumns = new ArrayList<QueryResultColumn>(columns);
    		for(int i=0; i<firstRow.size(); i++){
    			if(i>=columns.size()){
    				newColumns.add(new QueryResultColumn("col"+i,DataType.string));
    			}
    		}
    		columns = newColumns;
    	}
    	else if (diff < 0){
    		while(columns.size() > firstRow.size()){
        		columns.remove(columns.size()-1);
    		}
    	}
    	srq.setColumns(columns);
	}
	
	
	public static abstract class TokenHandler implements RawResultHandler{
		private RawResultHandler handler;

		public TokenHandler(RawResultHandler handler){
			this.handler = handler;
		}
		
		public abstract void onToken(String token);
		@Override
		public void onError(Exception ex) {
			handler.onError(ex);
		}

		@Override
		public void onResult(Map<String, Object> rawResult) throws Exception {
			handler.onResult(rawResult);
		}

		@Override
		public void onComplete(long timestamp) {
			handler.onComplete(timestamp);
		}

	}
}
