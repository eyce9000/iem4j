package com.github.eyce9000.iem.api.relevance;


import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class RowSerializer {
	TypeReference<HashMap<String,Object>> defObj = new TypeReference<HashMap<String,Object>>(){};
	TypeReference<HashMap<String,String>> defStr = new TypeReference<HashMap<String,String>>(){};
	ObjectMapper mapper;
	public RowSerializer(){
		mapper = new ObjectMapper();
		mapper.setAnnotationIntrospector(new AnnotationIntrospectorPair(
				new JacksonAnnotationIntrospector(),
				new JaxbAnnotationIntrospector(mapper.getTypeFactory())
		));
	}
	public RowSerializer(ObjectMapper mapper){
		this.mapper = mapper;
	}
	public ObjectMapper getMapper(){
		return mapper;
	}
	
	public <T> T deserializeString(Class<? extends T> clazz, Map<String,String> row){
		return (T)mapper.convertValue(row,clazz);
	}
	
	public <T> T deserialize(Class<? extends T> clazz, Map<String,Object> row){
		return (T)mapper.convertValue(row, clazz);
	}
	
	public Map<String,String> serializeString(Object value){
		return mapper.convertValue(value, defStr);
	}
	
	public Map<String,Object> serialize(Object value){
		return mapper.convertValue(value, defObj);
	}
	
	public static RowSerializer getJAXBandJacksonSerializer(){
		RowSerializer serializer = new RowSerializer();
		serializer.mapper.setAnnotationIntrospector(new AnnotationIntrospectorPair(
				new JacksonAnnotationIntrospector(),
				new JaxbAnnotationIntrospector(serializer.mapper.getTypeFactory())
		));
		return serializer;
	}
	public static RowSerializer getJacksonSerializer(){
		RowSerializer serializer= new RowSerializer();
		serializer.mapper = new ObjectMapper();
		return serializer;
	}
	public static RowSerializer getJAXBSerializer(){
		RowSerializer serializer = new RowSerializer();
		serializer.mapper.setAnnotationIntrospector(new JaxbAnnotationIntrospector(serializer.mapper.getTypeFactory()));
		return serializer;
	}
}
