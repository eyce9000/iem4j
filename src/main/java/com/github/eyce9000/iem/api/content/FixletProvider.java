package com.github.eyce9000.iem.api.content;

import com.bigfix.schemas.bes.Analysis;
import com.bigfix.schemas.bes.Baseline;
import com.bigfix.schemas.bes.Fixlet;
import com.bigfix.schemas.bes.Task;
import com.github.eyce9000.iem.api.relevance.RelevanceException;

public interface FixletProvider {
	SingleFixletHolder<Baseline> baseline(String name) throws RelevanceException;

	MultiFixletHolder<Baseline> baselines(String name) throws RelevanceException;
	
	SingleFixletHolder<Task> task(String name) throws RelevanceException;

	MultiFixletHolder<Task> tasks(String name) throws RelevanceException;
	
	SingleFixletHolder<Analysis> analysis(String name) throws RelevanceException;

	MultiFixletHolder<Analysis> analyses(String name) throws RelevanceException;
	
	SingleFixletHolder<Fixlet> fixlet(String name) throws RelevanceException;

	MultiFixletHolder<Fixlet> fixlets(String name) throws RelevanceException;
}
