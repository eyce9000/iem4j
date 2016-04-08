package com.github.eyce9000.iem.api.content;

import java.util.List;

import com.bigfix.schemas.bes.Analysis;
import com.bigfix.schemas.bes.Baseline;
import com.bigfix.schemas.bes.Fixlet;
import com.bigfix.schemas.bes.Task;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.google.common.base.Optional;

public interface ContentAPI extends FixletProvider,GroupProvider,SingleActionProvider,ActionSearchProvider{
	
	SingleSiteHolder site(String name) throws RelevanceException;

	MultiSiteHolder sites(String name) throws RelevanceException;
	
	public interface MultiContent<T,R,SINGLETYPE>{
		public List<T> ids();
		public List<R> get();
		public boolean isEmpty();
		public Optional<SINGLETYPE> first();
		public List<SINGLETYPE> all();
	}
	
	public interface RetrievableContent<T,R>{
		public T id();
		public R get();
	}
	
	public interface UpdateableContent<T,R>{
		public T id();
		public UpdateableContent<T,R> update(R value);
	}
	
	public interface DeletableContent<T,R>{
		public T id();
		public void delete();
	}
}