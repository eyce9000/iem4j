package com.github.eyce9000.iem.api.relevance;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@XmlRootElement(name = "QUERYCOLUMN")
@XmlAccessorType(XmlAccessType.FIELD)
public class QueryResultColumn{
	@XmlAttribute(name="NAME")
	private String name;
	@XmlAttribute(name="DATATYPE")
	private DataType datatype;

	@XmlTransient
	private int index = -1;
	
	public QueryResultColumn(){
		
	}
	/**
	 * Creates a new QueryResultColumn with the given name and datatype
	 * @param name
	 * @param datatype
	 */
	public QueryResultColumn(String name, DataType datatype){
		this.name = name;
		this.datatype = datatype;
	}
	
	public int getIndex(){
		return index;
	}
	public void setIndex(int index){
		this.index = index;
	}
	
	/**
	 * Returns the column name
	 * @return the column name
	 */
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	/**
	 * Returns the column DataType
	 * @return the column DataType
	 */
	public DataType getDataType(){
		return datatype;
	}
	
	public void setDataType(DataType datatype){
		this.datatype = datatype;
	}
	
	/**
	 * Returns the java.sql.Type of the column
	 * @return the java.sql.Type of the column
	 */
	public int getDataTypeSql(){
		switch(datatype){
		case number:
			return java.sql.Types.NUMERIC;
		case string:
		default:
			return java.sql.Types.VARCHAR;
		}
	}
	
	public static List<QueryResultColumn> extractColumns(String query){
		ArrayList<QueryResultColumn> results = new ArrayList<QueryResultColumn>();
		Pattern p = Pattern.compile("//@Column.*");
		Pattern typePattern = Pattern.compile("//@Column\\((\\w+)\\)");
		
		Matcher m = p.matcher(query);
		for(int i=0;m.find();i++){
			String match = query.substring(m.start(),m.end());
			Matcher typeMatcher = typePattern.matcher(match);
			DataType type = DataType.string;
			String name = null;
			if(typeMatcher.find()){
				String typeRaw = typeMatcher.group(1);
				if(typeRaw!=null)
					type = DataType.valueOf(typeRaw);
				int end1 = typeMatcher.end();
				name = match.substring(end1,match.length()).trim();
			}
			else{
				name = match.replace("//@Column", "").trim();
			}
			QueryResultColumn column = new QueryResultColumn(name,type);
			column.setIndex(i);
			results.add(column);
		}
		return results;
	}
	
	@Override
	public boolean equals(Object obj){
		if(obj instanceof QueryResultColumn){
			QueryResultColumn column = (QueryResultColumn) obj;
			return name.equalsIgnoreCase(column.name) && index==column.index;
		}
		else
			return false;
	}
}
