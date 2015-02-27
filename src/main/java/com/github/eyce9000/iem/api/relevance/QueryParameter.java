package com.github.eyce9000.iem.api.relevance;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.lang.StringUtils;


@XmlRootElement(name="PARAMETER")
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryParameter {
	
	@XmlAttribute(name="NAME")
	private String name;
	
	@XmlAttribute(name="DATATYPE",required=false)
	private DataType datatype = DataType.string;
	
	@XmlValue
	private String strValue = "";
	
	public QueryParameter(){
		
	}
	/**
	 * Creates a new QueryParameter with the given name and sets the DataType to string
	 * @param name		the name of the parameter
	 */
	public QueryParameter(String name){
		this(name, DataType.string);
	}
	/**
	 * Creates a new QueryParameter with the given name and DataType
	 * @param name		the name of the parameter
	 * @param datatype	the type of the parameter
	 */
	public QueryParameter(String name, DataType datatype){
		this.name = name;
		this.datatype = datatype;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Returns the name of the parameter
	 * @return the name of the parameter
	 */
	public String getName(){
		return name;
	}
	
	public void setDataType(DataType type){
		this.datatype = type;
	}
	
	/**
	 * Returns the java.sql.Type of the parameter's DataType.
	 * @return the java.sql.Type of the parameter's DataType
	 */
	public int getSQLType(){
		switch(datatype){
		case string:
			return java.sql.Types.VARCHAR;
		case number:
			return java.sql.Types.NUMERIC;
		}
		return java.sql.Types.NULL;
	}
	/**
	 * Returns the DataType of the parameter
	 * @return the DataType of the parameter
	 */
	public DataType getDataType(){
		return datatype;
	}
	/**
	 * Returns the value of the parameter formatted as a string
	 * @return the value of the parameter formatted as a string
	 */
	public String getValueAsString(){
		return strValue;
	}
	
	/**
	 * Sets the value of the parameter. <br>
	 * If the java class of the object does not match the DataType, an IllegalArgumentException is thrown
	 * @param newValue the new value to assign to the parameter
	 */
	public void setValue(Object newValue){
		if(newValue == null){
			strValue = "";
			return;
		}
		switch(datatype){
		case number:
			setNumberValue(newValue);
			break;
		case set:
			setSetValue(newValue);
			break;
		default:
			strValue = newValue.toString();
		}
	}
	
	private void setSetValue(Object newValue) {
		if(!(newValue instanceof Collection))
			throw new IllegalArgumentException("Value for parameter '"+name+"' has incorrect type. Should be a subclass of Collection");

		Collection coll = (Collection)newValue;
		
		List<String> values = new ArrayList<String>(coll.size());
		
		for(Object obj:coll){
			values.add("\""+obj.toString()+"\"");
		}
		strValue = "set of ("+StringUtils.join(values, ";")+")";
	}
	private void setNumberValue(Object newValue){
		String format;
		if(newValue instanceof Integer){
			format = "%d";
		}
		else if(newValue instanceof Double || newValue instanceof Float){
			format = "%f";
		}
		else{
			throw new IllegalArgumentException("Value for parameter '"+name+"' has incorrect type. Should be an int, double or float");
		}
		strValue = String.format(format,newValue);
	}
	
	/**
	 * Takes a relevance query string and extracts all of the parameters contained within it.<br>
	 * A parameter is defined as a name surrounded by brackets preceeded by a '$' symbol. e.g.<br>
	 * <em>${Param1}</em>
	 * @param query a Session Relevance query string with zero, one or more embedded parameters
	 * @return a list of QueryParameters
	 */
	public static List<QueryParameter> extractParameters(String query){
		Map<String,QueryParameter> paramMap = new HashMap<String,QueryParameter>();
		List<QueryParameter> params = new ArrayList<QueryParameter>();
		Pattern p = Pattern.compile("\\$\\{\\w*\\}");
		Matcher matcher = p.matcher(query);
		while(matcher.find()){
			int beginIndex = matcher.start()+2;
			int endIndex = matcher.end()-1;
			String paramName = query.substring(beginIndex, endIndex);
			if(!paramMap.containsKey(paramName)){
				QueryParameter param = new QueryParameter(paramName);
				paramMap.put(paramName, param);
				params.add(param);
			}
			
		}
		return params;
	}
}
