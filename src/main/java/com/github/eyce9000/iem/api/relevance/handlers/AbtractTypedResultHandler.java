package com.github.eyce9000.iem.api.relevance.handlers;

import java.util.Map;

import com.github.eyce9000.iem.api.relevance.RowSerializer;


public abstract class AbtractTypedResultHandler<T> implements RawResultHandler{
	Class<? extends T> clazz;
	RowSerializer serializer = new RowSerializer();
	public AbtractTypedResultHandler(Class<? extends T> clazz){
		this.clazz = clazz;
	}
	public AbtractTypedResultHandler(Class<? extends T> clazz,RowSerializer serializer){
		this.clazz = clazz;
		this.serializer = serializer;
	}
	public T serialize(Map<String, Object> rawResult){
		return serializer.deserialize(clazz, rawResult);
	}
	
	@Override
	public void onResult(Map<String, Object> rawResult) throws Exception{
		onResult(serialize(rawResult));
	}
	public void setSerializer(RowSerializer serializer){
		this.serializer = serializer;
	}
	public RowSerializer getSerializer() {
		return serializer;
	}
	public abstract void onResult(T result);
	@Override
	public void onError(Exception ex) {}
	@Override
	public void onComplete(long timestamp) {}
}
