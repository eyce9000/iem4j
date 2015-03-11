package com.github.eyce9000.iem.api;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;
import org.joda.time.DateTime;

import com.bigfix.schemas.bes.Action;
import com.bigfix.schemas.bes.Analysis;
import com.bigfix.schemas.bes.BES;
import com.bigfix.schemas.bes.Baseline;
import com.bigfix.schemas.bes.FixletWithActions;
import com.bigfix.schemas.bes.SingleAction;
import com.bigfix.schemas.bes.Site;
import com.bigfix.schemas.bes.SourcedFixletAction;
import com.bigfix.schemas.bes.Task;
import com.bigfix.schemas.besapi.BESAPI;
import com.bigfix.schemas.besapi.BESAPI.Query;
import com.bigfix.schemas.besapi.ComputerSetting;
import com.bigfix.schemas.besapi.RelevanceAnswer;
import com.bigfix.schemas.besapi.RelevanceResult;
import com.bigfix.schemas.besapi.RelevanceTuple;
import com.github.eyce9000.iem.api.actions.ActionBuilder;
import com.github.eyce9000.iem.api.actions.ActionTargetBuilder;
import com.github.eyce9000.iem.api.actions.logger.ActionLogger;
import com.github.eyce9000.iem.api.actions.script.ActionScriptBuilder;
import com.github.eyce9000.iem.api.relevance.DataType;
import com.github.eyce9000.iem.api.relevance.QueryResultColumn;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.RowSerializer;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.github.eyce9000.iem.api.relevance.handlers.HandlerException;
import com.github.eyce9000.iem.api.relevance.handlers.RawResultHandler;
import com.github.eyce9000.iem.api.relevance.handlers.impl.RawResultHandlerDefault;
import com.github.eyce9000.iem.api.relevance.handlers.impl.TypedResultListHandler;
import com.github.eyce9000.iem.api.relevance.results.QueryResult;
import com.github.eyce9000.iem.api.relevance.results.ResultTuple;
import com.github.eyce9000.iem.api.serialization.ResultAnswerAdapter.Answer;
import com.google.common.base.Optional;


public class IEMClient implements RelevanceClient{
	private URI baseURI;
	private Client client;
	private HttpBasicAuthFilter authFilter;
	private WebTarget apiRoot, root;
	private Unmarshaller unmarshaller;
	private String username;
	private Set<ActionLogger> actionLoggers = new HashSet<ActionLogger>();
	private int version = 92;
	
	protected IEMClient(){}
	
	/**
	 * 
	 * @param url The hostname of the IEM server. For example <code>mybigfixsrv.ibm.com</code>. The client will by default connect on port 52311.
	 * @param username The IEM Console username to connect with.
	 * @param password The IEM Console password to connect with.
	 * @throws Exception
	 */
	public IEMClient(String hostname, String username, String password) throws Exception{
		this(ClientBuilderWrapper.defaultBuilder().build()
			,hostname
			,username
			,password);
	}

	public IEMClient(Client client, String hostname, String username, String password) throws JAXBException{
		this(client
				,UriBuilder.fromPath("/").scheme("https").host(hostname).port(52311).build()
				,username
				,password);
	}
	
	public IEMClient(Client client, URI uri, String username, String password) throws JAXBException{
		this.client = client;
		this.baseURI = uri;
		switchUser(username, password);
        this.username = username;
        JAXBContext context = JAXBContext.newInstance(QueryResult.class);
		unmarshaller = context.createUnmarshaller();
		
	}
	
	public void switchUser(String username, String password){

		authFilter = new HttpBasicAuthFilter(username,password);
		root = client.target(UriBuilder.fromUri(baseURI)).register(authFilter);
        apiRoot = root.path("/api/");
	}
	
	public void addActionLogger(ActionLogger logger){
		actionLoggers.add(logger);
	}
	
	public boolean removeActionLogger(ActionLogger logger){
		return actionLoggers.remove(logger);
	}
	
	public String getUsername(){
		return username;
	}
	
	//*******************************************************************
	// C O M P U T E R   M E T H O D S
	//*******************************************************************
	
	public BESAPI.ComputerSettings getComputerSettings(long computerid) throws Exception{
		WebTarget target = apiRoot
				.path("computer/{computerid}/settings")
				.resolveTemplate("computerid",computerid);
		return target.request().get(BESAPI.ComputerSettings.class);
	}
	
	public BESAPI.ComputerSettings getComputerSetting(long computerid, String settingid){
		WebTarget target = apiRoot
				.path("computer/{computerid}/setting/{settingid}")
				.resolveTemplate("computerid",computerid)
				.resolveTemplate("settingid",settingid);
		return target.request().get(BESAPI.ComputerSettings.class);
	}
	
	public BESAPI.Action setComputerSetting(long computerid, String settingid, String value){
		LinkedList<ComputerSetting> settings = new LinkedList<ComputerSetting>();
		ComputerSetting setting = new ComputerSetting();
		setting.setName(settingid);
		setting.setValue(value);
		settings.add(setting);
		return setComputerSettings(computerid,settings);
	}
	
	public BESAPI.Action setComputerSettings(long computerid, Map<String,String> settingsMap){
		LinkedList<ComputerSetting> settings = new LinkedList<ComputerSetting>();
		for(Entry<String,String> entry:settingsMap.entrySet()){
			ComputerSetting setting = new ComputerSetting();
			setting.setName(entry.getKey());
			setting.setValue(entry.getValue());
			settings.add(setting);
		}
		return setComputerSettings(computerid,settings);
	}
	
	private BESAPI.Action setComputerSettings(long computerid, List<ComputerSetting> settings){
		WebTarget target = apiRoot
				.path("computer/{computerid}/settings")
				.resolveTemplate("computerid",computerid);
		BESAPI.ComputerSettings csettings = new BESAPI.ComputerSettings();
		csettings.getSetting().addAll(settings);
		Entity<BESAPI.ComputerSettings> entity = Entity.entity(csettings, MediaType.APPLICATION_XML);
		return (BESAPI.Action)target.request().post(entity,BESAPI.class).getFixletOrReplicationServerOrReplicationLink().get(0).getValue();
	}

	public BESAPI.Action setComputerSettingLocalTime(long computerid, String settingid, String value){
		BESAPI.ComputerSettings settings = new BESAPI.ComputerSettings();
		ComputerSetting setting = new ComputerSetting();
		setting.setName(settingid);
		setting.setValue(value);
		settings.getSetting().add(setting);
		return setComputerSettingsLocalTime(computerid,settings);
	}
	public BESAPI.Action setComputerSettingsLocalTime(long computerid, Map<String,String> settingsMap){
		BESAPI.ComputerSettings settings = new BESAPI.ComputerSettings();
		for(Entry<String,String> entry:settingsMap.entrySet()){
			ComputerSetting setting = new ComputerSetting();
			setting.setName(entry.getKey());
			setting.setValue(entry.getValue());
			settings.getSetting().add(setting);
		}
		return setComputerSettingsLocalTime(computerid,settings);
	}
	
	public BESAPI.Action setComputerSettingsLocalTime(long computerid, List<ComputerSetting> settings){
		BESAPI.ComputerSettings csettings = new BESAPI.ComputerSettings();
		csettings.getSetting().addAll(settings);
		return setComputerSettingsLocalTime(computerid,csettings);
	}
	
	public BESAPI.Action setComputerSettingsLocalTime(long computerid, BESAPI.ComputerSettings settings){
		SingleAction action = ActionBuilder.singleAction()
				.actionScript(ActionScriptBuilder.start().putComputerSettingNow(settings).build())
				.relevance("true")
				.title("Change Computer Settings - Local Now")
				.build();
		action.setTarget(ActionTargetBuilder.targetComputers(computerid));
		return createAction(action);
	}
	
	public List<BESAPI.Action> deleteComputerSettings(long computerid, String...settingNames){
		return deleteComputerSettings(computerid, Arrays.asList(settingNames));
	}
	
	public List<BESAPI.Action> deleteComputerSettings(long computerid, List<String> settingNames){
		List<BESAPI.Action> actions = new ArrayList<BESAPI.Action>();
		for(String settingName:settingNames){
			actions.add(deleteComputerSetting(computerid, settingName));
		}
		return actions;
	}
	
	public BESAPI.Action deleteComputerSetting(long computerid, String settingName){
		WebTarget target = apiRoot
				.path("computer/{computerid}/setting/{settingName}")
				.resolveTemplate("computerid",computerid)
				.resolveTemplate("settingName",settingName);
		return (BESAPI.Action)target.request().get(BESAPI.class).getFixletOrReplicationServerOrReplicationLink().get(0).getValue();
	}
	
	public BESAPI.Action deleteComputerSettingsLocalTime(long computerid, String...settingNames){
		SingleAction action = ActionBuilder.singleAction()
				.actionScript(ActionScriptBuilder.start().deleteComputerSettingsNow(settingNames).build())
				.relevance("true")
				.title("Delete Computer Settings - Local Now")
				.build();
		action.setTarget(ActionTargetBuilder.targetComputers(computerid));
		return createAction(action);
	}

	//*******************************************************************
	// A C T I O N   M E T H O D S
	//*******************************************************************
	
	
	public Optional<Action> getAction(BigInteger actionId){
		WebTarget target = apiRoot
				.path("action/{actionid}")
				.resolveTemplate("actionid",actionId);
		return getSingleBESContent(target.request().get(),Action.class);
	}
	
	public BESAPI.ActionResults getActionStatus(BESAPI.Action action){
		return getActionStatus(action.getID());
	}
	
	public BESAPI.ActionResults getActionStatus(BigInteger actionId){
		WebTarget target = apiRoot
				.path("action/{actionid}/status")
				.resolveTemplate("actionid",actionId);
		return (BESAPI.ActionResults)target.request().get(BESAPI.class).getFixletOrReplicationServerOrReplicationLink().get(0).getValue();
	}
	
	public void stopAction(BESAPI.ActionResults results){
		stopAction(results.getActionID());
	}
	
	public void stopAction(BESAPI.Action action){
		stopAction(action.getID());
	}
	
	public void stopAction(BigInteger actionId){
		WebTarget target = apiRoot
				.path("action/{actionid}/stop")
				.resolveTemplate("actionid",actionId);
		target.request().post(Entity.text(""));
	}

	public void deleteAction(BESAPI.ActionResults results){
		deleteAction(results.getActionID());
	}
	
	public void deleteAction(BESAPI.Action action){
		deleteAction(action.getID());
	}
	public void deleteAction(BigInteger actionId){
		WebTarget target = apiRoot
				.path("action/{actionid}")
				.resolveTemplate("actionid",actionId);
		target.request().delete();
				
	}
	public List<BESAPI.Action> getActions(){
		WebTarget target = apiRoot
				.path("actions");
		return getBESAPIContent(target.request().get(),BESAPI.Action.class);
	}

	public BESAPI.Action createFixletSourcedAction(SourcedFixletAction sourcedAction){
		BESAPI.Action action = getSingleBESAPIContent(postBESContent(apiRoot.path("actions"),sourcedAction),BESAPI.Action.class).get();
		if(action!=null){
			for(ActionLogger logger:actionLoggers){
				logger.logAction(action, getUsername());
			}
		}
		return action;
	}
	
	public BESAPI.Action createAction(com.bigfix.schemas.bes.Action besAction){
		BESAPI.Action action = getSingleBESAPIContent(postBESContent(apiRoot.path("actions"),besAction),BESAPI.Action.class).get();
		if(action!=null){
			for(ActionLogger logger:actionLoggers){
				logger.logAction(action, getUsername());
			}
		}
		return action;
	}
	
	//*******************************************************************
	// S I T E   M E T H O D S
	//*******************************************************************
	
	public Optional<Site> getSite(String siteType, String site){
		WebTarget target = buildSiteTarget(apiRoot.path("/site/"),siteType,site);
		return getSingleBESContent(target.request().get(), Site.class);
	}
	
	public List<BESAPI.Site> getSites(){
		return getBESAPIContent(apiRoot.path("/sites").request().get(),BESAPI.Site.class);
	}
	
	
	//*******************************************************************
	// C O N T E N T   M E T H O D S
	//*******************************************************************
	
	
	// Fixlets
	public Optional<FixletWithActions> getFixlet(BESAPI.Fixlet apiFixlet) throws MalformedURLException{
		URL url = new URL(apiFixlet.getResource());
		WebTarget target =root.path(url.getPath());
		return getSingleBESContent(target.request().get(),FixletWithActions.class);
	}
 
	public Optional<FixletWithActions> getFixlet(String siteType, String site, long id) throws MalformedURLException{
		WebTarget target = buildSiteTarget(apiRoot.path("/fixlet/"),siteType,site).path("/{id}").resolveTemplate("id", id+"");
		return getSingleBESContent(target.request().get(),FixletWithActions.class);
	}
	public Optional<BESAPI.Fixlet> createFixlet(String siteType, String site, FixletWithActions content) throws MalformedURLException{
		WebTarget target = buildSiteTarget(apiRoot.path("/fixlets/"),siteType,site);
		return getSingleBESAPIContent(postBESContent(target,content),BESAPI.Fixlet.class);
	}
	
	public void updateFixlet(String siteType, String site, long id, FixletWithActions content) throws MalformedURLException{
		WebTarget target = buildSiteTarget(apiRoot.path("/fixlet/"),siteType,site).path("/{id}").resolveTemplate("id", id+"");
		putBESContent(target,content,FixletWithActions.class);
	}
	
	public void updateFixlet(BESAPI.Fixlet resource, FixletWithActions content) throws MalformedURLException{
		URL url = new URL(resource.getResource());
		WebTarget target =root.path(url.getPath());
		putBESContent(target,content,FixletWithActions.class);
	}
	
	public List<BESAPI.Fixlet> getFixlets(String siteType,String site){
		return getBESAPIContent(buildSiteTarget(apiRoot.path("/fixlets/"),siteType,site).request().get(),BESAPI.Fixlet.class);
	}
	
	public void deleteFixlet(String siteType, String site, long id){
		WebTarget target = buildSiteTarget(apiRoot.path("/fixlet/"),siteType,site).path("/{id}").resolveTemplate("id", id+"");
		target.request().delete();
	}

	
	// Tasks
	public Optional<Task> getTask(BESAPI.Task apiFixlet) throws MalformedURLException{
		URL url = new URL(apiFixlet.getResource());
		WebTarget target =root.path(url.getPath());
		return getSingleBESContent(target.request().get(),Task.class);
	}

	public Optional<Task> getTask(String siteType, String site, long id) throws MalformedURLException{
		WebTarget target = buildSiteTarget(apiRoot.path("/task/"),siteType,site).path("/{id}").resolveTemplate("id", id+"");
		return getSingleBESContent(target.request().get(),Task.class);
	}
	public Optional<BESAPI.Task> createTask(String siteType, String site, Task content) throws MalformedURLException{
		WebTarget target = buildSiteTarget(apiRoot.path("/tasks/"),siteType,site);
		return getSingleBESAPIContent(postBESContent(target,content),BESAPI.Task.class);
	}
	
	public void updateTask(String siteType, String site, long id, Task content) throws MalformedURLException{
		WebTarget target = buildSiteTarget(apiRoot.path("/task/"),siteType,site).path("/{id}").resolveTemplate("id", id+"");
		putBESContent(target,content,Task.class);
	}
	
	public void updateTask(BESAPI.Task resource, Task content) throws MalformedURLException{
		URL url = new URL(resource.getResource());
		WebTarget target =root.path(url.getPath());
		putBESContent(target,content,Task.class);
	}
	
	public List<BESAPI.Task> getTasks(String siteType,String site){
		return getBESAPIContent(buildSiteTarget(apiRoot.path("/tasks/"),siteType,site).request().get(),BESAPI.Task.class);
	}

	public void deleteTask(String siteType, String site, long id){
		WebTarget target = buildSiteTarget(apiRoot.path("/task/"),siteType,site).path("/{id}").resolveTemplate("id", id+"");
		target.request().delete();
	}
	
	//Analyses
	public Optional<Analysis> getAnalysis(BESAPI.Analysis resource) throws MalformedURLException{
		URL url = new URL(resource.getResource());
		WebTarget target =root.path(url.getPath());
		return getSingleBESContent(target.request().get(),Analysis.class);
	}

	public Optional<Analysis> getAnalysis(String siteType, String site, long id) throws MalformedURLException{
		WebTarget target = buildSiteTarget(apiRoot.path("/analysis/"),siteType,site).path("/{id}").resolveTemplate("id", id+"");
		return getSingleBESContent(target.request().get(),Analysis.class);
	}

	public Optional<BESAPI.Analysis> createAnalysis(String siteType, String site, Analysis analysis) throws MalformedURLException{
		WebTarget target = buildSiteTarget(apiRoot.path("/analyses/"),siteType,site);
		Response resp = postBESContent(target,analysis);
		return getSingleBESAPIContent(resp,BESAPI.Analysis.class);
	}
	
	public void updateAnalysis(String siteType, String site, long id, Analysis analysis) throws MalformedURLException{
		WebTarget target = buildSiteTarget(apiRoot.path("/analysis/"),siteType,site).path("/{id}").resolveTemplate("id", id+"");
		putBESContent(target,analysis,Analysis.class);
	}
	
	public void updateAnalysis(BESAPI.Analysis resource, Analysis analysis) throws MalformedURLException{
		URL url = new URL(resource.getResource());
		WebTarget target =root.path(url.getPath());
		putBESContent(target,analysis,Analysis.class);
	}
	
	public List<BESAPI.Analysis> getAnalyses(String siteType,String site){
		WebTarget target = buildSiteTarget(apiRoot.path("/analyses/"),siteType,site);
		return getBESAPIContent(target.request().get(),BESAPI.Analysis.class);
	}

	public void deleteAnalysis(String siteType, String site, long id){
		WebTarget target = buildSiteTarget(apiRoot.path("/analysis/"),siteType,site).path("/{id}").resolveTemplate("id", id+"");
		target.request().delete();
	}
	
	//Baselines
	public Optional<Baseline> getBaseline(BESAPI.Baseline apiFixlet) throws MalformedURLException{
		URL url = new URL(apiFixlet.getResource());
		WebTarget target =root.path(url.getPath());
		return getSingleBESContent(target.request().get(),Baseline.class);
	}

	public Optional<Baseline> getBaseline(String siteType, String site, long id) throws MalformedURLException{
		WebTarget target = buildSiteTarget(apiRoot.path("/fixlet/"),siteType,site).path("/{id}").resolveTemplate("id", id+"");
		return getSingleBESContent(target.request().get(),Baseline.class);
	}
	
	public Optional<BESAPI.Baseline> createBaseline(String siteType, String site, Baseline content) throws MalformedURLException{
		WebTarget target = buildSiteTarget(apiRoot.path("/import/"),siteType,site);
		Response resp = postBESContent(target,content);
		Optional<BESAPI.Unknown> wrapper = getSingleBESAPIContent(resp,BESAPI.Unknown.class);
		if(!wrapper.isPresent())
			return Optional.absent();
		else{
			BESAPI.Unknown original = wrapper.get();
			BESAPI.Baseline baseline = new BESAPI.Baseline();
			baseline.setID(original.getID());
			baseline.setResource(original.getResource());
			baseline.setName(original.getName());
			baseline.setLastModified(original.getLastModified());
			return Optional.of(baseline);
		}
			
	}
	
	public void updateBaseline(String siteType, String site, long id, Baseline content) throws MalformedURLException{
		WebTarget target;
		target = apiRoot.path("/fixlet/");
		target = buildSiteTarget(target,siteType,site).path("/{id}").resolveTemplate("id", id+"");
		putBESContent(target,content,Baseline.class);
		
	}
	
	public void updateBaseline(BESAPI.Baseline resource, Baseline content) throws MalformedURLException{
		URL url = new URL(resource.getResource());
		WebTarget target =root.path(url.getPath());
		putBESContent(target,content,Baseline.class);
	}
	

	public void deleteBaseline(String siteType, String site, long id){
		WebTarget target = buildSiteTarget(apiRoot.path("/fixlet/"),siteType,site).path("/{id}").resolveTemplate("id", id+"");
		target.request().delete();
	}
	
	public List<BESAPI.Baseline> getBaselines(String siteType,String site){
		WebTarget target;
		target = apiRoot.path("/baselines/{siteType}")
				.resolveTemplate("siteType", siteType);
		if(site!=null){
			target = target.path("/{site}").resolveTemplate("site", site);
		}
		return getBESAPIContent(target.request().get(),BESAPI.Baseline.class);
	}
	
	//Import
	public BESAPI importContent(String siteType, String site, BES content){
		WebTarget target = buildSiteTarget(apiRoot.path("/import/"),siteType,site);
		Entity<BES> entity = Entity.entity(content, MediaType.APPLICATION_XML);
		return target.request().post(entity,BESAPI.class);
	}
	

	//*******************************************************************
	// R E S P O N S E  /  G E T  M E T H O D S
	//*******************************************************************

	public String getRaw(String path){
		return root.path(path).request().get(String.class);
	}
	
	protected <T> Optional<T> getSingleBESContent(Response resp, Class<T> clazz){
		if(resp.getStatus()==404)
			return Optional.absent();
		List<T> list = getBESContent(resp,clazz);
		if(list.isEmpty())
			return Optional.absent();
		else
			return Optional.of(list.get(0));
	}
	
	protected <T> List<T> getBESContent(Response resp, Class<T> clazz){
		BES bes = handleResponse(resp,BES.class);
		bes.getFixletOrTaskOrAnalysis();
		List<T> values = new ArrayList<T>();
		for(Object object : bes.getFixletOrTaskOrAnalysis()){
			values.add((T)object);
		}
		return values;
	}

	protected <T> Optional<T> getSingleBESAPIContent(Response resp, Class<T> clazz){
		if(resp.getStatus()==404)
			return Optional.absent();
		List<T> list = getBESAPIContent(resp,clazz);
		if(list.isEmpty())
			return Optional.absent();
		else
			return Optional.of(list.get(0));
	}
	
	protected <T> List<T> getBESAPIContent(Response resp, Class<T> clazz){
		BESAPI besapi = handleResponse(resp,BESAPI.class);
		List<T> values = new ArrayList<T>();
		for(JAXBElement<?> wrapper : besapi.getFixletOrReplicationServerOrReplicationLink()){
			values.add((T)wrapper.getValue());
		}
		return values;
	}
	
	protected <T> void putBESContent(WebTarget target,T value,Class<T> clazz){
		BES bes = new BES();
		bes.getFixletOrTaskOrAnalysis()
			.add(value);

		Entity<BES> entity = Entity.entity(bes, MediaType.APPLICATION_XML);
		Response response = target.request().put(entity);
		handleResponse(response,String.class);
	}

	protected <T> Response postBESContent(WebTarget target,T value){
		BES bes = new BES();
		bes.getFixletOrTaskOrAnalysis()
			.add(value);

		Entity<BES> entity = Entity.entity(bes, MediaType.APPLICATION_XML);
		return target.request().post(entity);
	}
	
	protected <T> T handleResponse(Response resp,Class<T> clazz){
		if(resp.getStatus()>=200 && resp.getStatus()<300){
			return resp.readEntity(clazz);
		}
		else{
			try{
				String error = "";
				BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)resp.getEntity()));
				String line = null;
				while((line=reader.readLine())!=null)
					error += line +"\n";
				throw new BadRequestException(resp.getStatus()+": "+error);
			}
			catch(Exception ex){
				throw new BadRequestException(ex);
			}
		}
	}
	
	protected WebTarget buildSiteTarget(WebTarget base, String siteType, String site){
		WebTarget target;
		target = base.path("{siteType}")
				.resolveTemplate("siteType", siteType);
		if(site!=null){
			target = target.path("/{site}").resolveTemplate("site", site);
		}
		return target;
	}
	
	//*******************************************************************
	// R E L E V A N C E   M E T H O D S
	//*******************************************************************
	
	@Override
	public List<Map<String,Object>> executeQuery(SessionRelevanceQuery srq) throws RelevanceException{
		RawResultHandlerDefault handler = new RawResultHandlerDefault();
		try{
			executeQueryWithHandler(srq,handler);
		}
		catch(HandlerException ex){
			throw new RelevanceException(ex);
		}
		return handler.getRawResults();
	}
	
	@Override
	public void executeQueryWithHandler(SessionRelevanceQuery srq, RawResultHandler handler) throws RelevanceException, HandlerException{
		QueryResult result = runRelevanceQuery(srq.constructQuery());
		processResponse(result, srq,handler);
	}
	@Override
	public <T> List<T> executeQuery(SessionRelevanceQuery srq, Class<? extends T> clazz) throws RelevanceException{
		return executeQuery(srq,clazz,null);
	}
	@Override
	public <T> List<T> executeQuery(SessionRelevanceQuery srq, Class<? extends T> clazz, RowSerializer serializer) throws RelevanceException{
		TypedResultListHandler<T> handler = new TypedResultListHandler<T>(clazz);
		if(serializer!=null)
			handler.setSerializer(serializer);
		try{
			executeQueryWithHandler(srq,handler);
		}
		catch(HandlerException ex){
			throw new RelevanceException(ex);
		}
		return handler.getResults();
	}
	
	private QueryResult runRelevanceQuery(String query){
		WebTarget target = apiRoot.path("query");
		try {
			String data = "relevance="+URLEncoder.encode(query, "UTF-8");
			Response response = target.request(MediaType.APPLICATION_XML).post(Entity.text(data));
			return (QueryResult)unmarshaller.unmarshal((InputStream)response.getEntity());
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	

	private void processResponse(QueryResult result, SessionRelevanceQuery srq, RawResultHandler handler) throws HandlerException, RelevanceException{
		if(result.getError()!=null){
			throw new RelevanceException(result);
		}
		
		switch(result.getEvaluation().getPlurality()){
			case Plural:
				processPluralResult(result,srq,handler);
				break;
			case Singular:
				processSingleResult(result,srq,handler);
				break;
			default:
				break;
		}
		handler.onComplete(result.getEvaluation().getTime().getMillis());
	}
	
	private void processSingleResult(QueryResult result, SessionRelevanceQuery srq, RawResultHandler handler) throws HandlerException{
		List<Object> firstRow = Arrays.asList(result.getSingleResult());
		List<QueryResultColumn> columns = buildColumns(firstRow,srq);
		handleRow(firstRow,columns,handler);
	}
	
	private void processPluralResult(QueryResult result, SessionRelevanceQuery srq, RawResultHandler handler) throws HandlerException{
		if(result.getPluralResults().isEmpty()){
			return;
		}
		
		List<Object> firstRow = result.getPluralResults().get(0).getAnswers();
		List<QueryResultColumn> columns = buildColumns(firstRow,srq);
		for(ResultTuple row : result.getPluralResults()){
			
			List<Object> rowData = row.getAnswers();
			handleRow(rowData,columns,handler);
		}
	}
	
	
	private void handleRow(List<Object> rowData, List<QueryResultColumn> columns,RawResultHandler handler) throws HandlerException{
		HashMap<String,Object> sampleRowValues = new HashMap<String,Object>();
    	for(int colNum=0; colNum<columns.size(); colNum++){
    		QueryResultColumn column = columns.get(colNum);
			sampleRowValues.put(column.getName(),rowData.get(colNum));
    	}
    	try{
    		handler.onResult(sampleRowValues);
    	}
    	catch(Exception ex){
    		throw new HandlerException(ex);
    	}
	}
	
	private List<QueryResultColumn> buildColumns(List<Object> hintRow,SessionRelevanceQuery srq){
		List<QueryResultColumn> columns = srq.getColumns();
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
