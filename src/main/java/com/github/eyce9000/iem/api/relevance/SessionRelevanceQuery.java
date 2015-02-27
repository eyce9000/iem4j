package com.github.eyce9000.iem.api.relevance;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement(name="SessionRelevanceQuery")
@XmlAccessorType(XmlAccessType.FIELD)
public class SessionRelevanceQuery {
	@XmlElementWrapper(name="PARAMETERS")
	@XmlElement(name="PARAMETER",required=false)
	private List<QueryParameter> parameters = new ArrayList<QueryParameter>();

	@XmlElementWrapper(name="QUERYRESULTCOLUMNS")
	@XmlElement(name="QUERYCOLUMN",required=false)
	private List<QueryResultColumn> columns = new ArrayList<QueryResultColumn>();

	@XmlElement(name="QUERY", required=false)
	private String query = " ";

	@XmlElement(name="SAMPLEDATA",required = false)
	private String sampleData = " ";
	
	@XmlTransient
	private LinkedHashMap<String,QueryParameter> parameterMap = null;

	@XmlTransient
	private LinkedHashMap<String, QueryResultColumn> columnMap;
	
	@XmlTransient
	private boolean columnsIndexed = false;

	
	
	public String getSampleData(){
		return this.sampleData;
	}
	/**
	 * Sets the Session Relevance query text
	 * @param query the Session Relevance query text
	 */
	public void setQuery(String query){
		this.query = query;
	}
	
	/**
	 * Returns the Session Relevance query text
	 * @return the Session Relevance query text
	 */
	public String getQuery(){
		return query;
	}
	/**
	 * Returns a cleaned copy of the Session Relevance query text that is appropriate for webservice use.<br>
	 * Removes all comments, tabs and newlines.
	 * @return a cleaned copy of the Session Relevance query text that is appropriate for webservice use.
	 */
	public String getCleanedQuery(){
		char[] cleanBuffer = query.toCharArray();
		//Find locations of strings

		Pattern p;
		Matcher m;
		
		p= Pattern.compile("\".*\"");
		m= p.matcher(query);
		
		char[] mask = new char[cleanBuffer.length];
		while(m.find()){
			for(int i=m.start(); i<m.end(); i++){
				mask[i] = 's';
			}
		}
		
		p = Pattern.compile("//.*");
		m = p.matcher(query);
		while(m.find()){
			if(mask[m.start()]!='s'){
				for(int i=m.start(); i<m.end(); i++){
					mask[i] = 'c';
				}
			}
		}
		
		p = Pattern.compile("\\s+");
		m = p.matcher(query);
		while(m.find()){
			if(mask[m.start()]!='s'){
				cleanBuffer[m.start()]=' ';
				for(int i=m.start()+1; i<m.end(); i++){
					mask[i]='c';
				}
			}
		}
		for(int i=0; i<mask.length; i++){
			if(mask[i]=='c'){
				cleanBuffer[i]='\n';
			}
		}
		
		String cleaned = new String(cleanBuffer);
		cleaned = cleaned.replace("\n", "");
		return cleaned;
	}
	/**
	 * Sets the parameters
	 * @param parameters a list of QueryParameters
	 */
	public void setParameters(List<QueryParameter> parameters){
		this.parameters = parameters;
		this.parameterMap = null;
	}
	/**
	 * Set the QueryParameter at the given index. This will replace any QueryParameter previously at this index.
	 * @param parameter the QueryParameter to set
	 * @param index the index where the QueryParameter will be set
	 */
	public void setParameter(QueryParameter parameter,int index){
		if(index >=0 && index<parameters.size()){
			parameters.set(index, parameter);
			if(parameterMap!=null)
				parameterMap.put(parameter.getName(), parameter);
		}
	}
	/**
	 * Returns the number of parameters.
	 * @return the number of parameters
	 */
	public int getParameterCount(){
		return parameters.size();
	}

	/**
	 * Returns the parameters
	 * @return a list of QueryParameters
	 */
	public List<QueryParameter> getParameters(){
		return parameters;
	}
	/**
	 * Get a query parameter by index. If the index does not exist, returns null.
	 * @param index the index of the parameter
	 * @return the QueryParameter with the given index
	 */
	public QueryParameter getParameter(int index){
		if(index<parameters.size() && index>=0){
			return parameters.get(index);
		}
		else{
			return null;
		}
	}
	/**
	 * Get a query parameter by its name. If there is no parameter with that name, returns null.
	 * @param paramName the name of the parameter
	 * @return the QueryParameter with the given name
	 */
	public QueryParameter getParameter(String paramName){
		return this.getParameterMap().get(paramName);
	}
	/**
	 * Get the index of a parameter by its name.<br>
	 * If there is no parameter with that name, -1 is returned.
	 * @param paramName the name of the parameter
	 * @return the index of the parameter with the given name.
	 */
	public int getParameterIndex(String paramName){
		QueryParameter param = this.getParameterMap().get(paramName);
		if(param!=null)
			return parameters.indexOf(param);
		else
			return -1;
	}
	
	public boolean putParameter(String paramName, Object value){
		int index = getParameterIndex(paramName);
		if(index == -1)
			return false;
		getParameter(index).setValue(value);
		return true;
	}
	/**
	 * Returns a map of the parameters, where the key is the name of the parameter
	 * @return the map of the parameters, where the key is the name of the parameter
	 */
	public Map<String,QueryParameter> getParameterMap(){
		if(parameterMap == null){
			parameterMap = new LinkedHashMap<String,QueryParameter>();
			for(QueryParameter param: parameters){
				String paramName = param.getName();
				parameterMap.put(paramName, param);
			}
		}
		return parameterMap;
	}
	/**
	 * Sets the QueryResultColumns
	 * @param columns a list of QueryResultColumns
	 */
	public void setColumns(List<QueryResultColumn> columns){
		columnsIndexed = false;
		this.columns = columns;
		indexColumns();
		getColumnMap();
	}
	
	/**
	 * Sets thes QueryResultColumn at the given index. This column will replace the column previously at this index
	 * @param column the column to place
	 * @param index the index where the column will be put
	 */
	public void setColumn(QueryResultColumn column, int index){
		if(index>=0 && index < columns.size()){
			columns.set(index, column);
			column.setIndex(index);
			if(columnMap!=null)
				columnMap.put(column.getName(), column);
		}
	}
	private void indexColumns(){
		if(!columnsIndexed){
			for(int i=0; i<columns.size(); i++){
				columns.get(i).setIndex(i);
			}
			columnsIndexed = true;
		}
	}
	
	public void addColumn(QueryResultColumn column){
		columns.add(column);
		indexColumns();
	}
	
	/**
	 * Returns the number of columns
	 * @return the number of columns
	 */
	public int getColumnCount(){
		return columns.size();
	}
	/**
	 * Returns the QueryResultColumns
	 * @return the QueryResultColumns
	 */
	public List<QueryResultColumn> getColumns(){
		indexColumns();
		return columns;
	}
	/**
	 * Returns the QueryResultColumn at the given index.
	 * @param index the index of the column
	 * @return the QueryResultColumn at the given index
	 */
	public QueryResultColumn getColumn(int index){
		if(index>=0 && index<columns.size()){
			QueryResultColumn column = columns.get(index);
			column.setIndex(index);
			return column;
		}
		return null;
	}
	/**
	 * Returns the QueryResultColumn with the given name. 
	 * @param name the name of the column to retrieve
	 * @return the QueryResultColumn with the given name
	 */
	public QueryResultColumn getColumn(String name){
		QueryResultColumn result = getColumnMap().get(name);
		if(result!=null && result.getIndex()==-1){
			result.setIndex(columns.indexOf(result));
		}
		return result;
	}
	/**
	 * Returns a map of QueryResultColumns keyed on the column name.
	 * @return a map of QueryResultColumns keyed on the column name
	 */
	public Map<String,QueryResultColumn> getColumnMap(){
		if(columnMap==null){
			columnMap = new LinkedHashMap<String,QueryResultColumn>();
			for(QueryResultColumn column: columns){
				String paramName = column.getName();
				columnMap.put(paramName, column);
			}

		}
		return columnMap;
	}
	/**
	 * Builds the Session Relevance query, substituting parameter values where necessary.
	 * @return 	the Session Relevance query with parameter values substituted in
	 */
	public String constructQuery(){
		String resultQuery = getCleanedQuery();
		for(QueryParameter parameter:parameters){
			Pattern p = Pattern.compile("\\$\\{"+Pattern.quote(parameter.getName())+"\\}");
			Matcher matcher = p.matcher(resultQuery);
			resultQuery = matcher.replaceAll(parameter.getValueAsString());
		}
		return resultQuery;
	}
	/**
	 * Serialize this SessionRelevanceQuery as xml to a file
	 * @param file 		an xml file to serialize to
	 * @throws JAXBException
	 * @throws IOException 
	 */
	public void serialize(File file) throws JAXBException, IOException{
		serialize(new FileWriter(file));
	}
	/**
	 * Serialize this SessionRelevanceQuery as xml to a Writer
	 * @param writer 	a writer to serialize to
	 * @throws JAXBException 
	 * @throws Exception
	 */
	public void serialize(Writer writer) throws JAXBException {
		JAXBContext jc = JAXBContext.newInstance(SessionRelevanceQuery.class);
		Marshaller m = jc.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.marshal(this, writer);
	}
	
	public void serialize(OutputStream stream) throws JAXBException{
		serialize(new OutputStreamWriter(stream));
	}
	/**
	 * Get this SessionRelevanceQuery as xml serialized to a String
	 * @return	the xml string value of this SessionRelevanceQuery
	 * @throws Exception
	 */
	public String serializeToString(){
		try{
			StringWriter writer = new StringWriter();
			this.serialize(writer);
			return writer.toString();
		}
		catch(Exception ex){
			return "";
		}
	}
	
	public static SessionRelevanceQuery deserialize(InputStream in) throws Exception{
		JAXBContext jc = JAXBContext.newInstance(SessionRelevanceQuery.class);
		return (SessionRelevanceQuery)jc.createUnmarshaller().unmarshal(in);
	}
	
	/**
	 * Create a new SessionRelevanceQuery from an xml file
	 * @param file	a SessionRelevanceQuery xml file (typically with the extension <em>.srq</em>)
	 * @return	a new SessionRelevanceQuery 
	 * @throws Exception
	 */
	public static SessionRelevanceQuery deserialize(File file) throws Exception{
		return deserialize(new FileInputStream(file));
	}

	/**
	 * Create a new SessionRelevanceQuery from an string
	 * @param file	a SessionRelevanceQuery xml string
	 * @return	a new SessionRelevanceQuery 
	 * @throws Exception
	 */
	public static SessionRelevanceQuery deserialize(String data) throws Exception{
		return deserialize(new ByteArrayInputStream(data.getBytes("UTF-8")));
	}
	
	public static SessionRelevanceQuery parseQuery(String query){
		SessionRelevanceQuery srq = new SessionRelevanceQuery();
		srq.setParameters(QueryParameter.extractParameters(query));
		srq.setColumns(QueryResultColumn.extractColumns(query));
		srq.setQuery(query);
		return srq;
	}
}

