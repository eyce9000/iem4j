package com.github.eyce9000.iem.api;


import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.net.ssl.SSLContext;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.client.filter.HttpBasicAuthFilter;
import org.joda.time.DateTime;

import com.bigfix.schemas.bes.AbstractAction;
import com.bigfix.schemas.bes.Action;
import com.bigfix.schemas.bes.Analysis;
import com.bigfix.schemas.bes.BES;
import com.bigfix.schemas.bes.BES.CustomSite;
import com.bigfix.schemas.bes.Baseline;
import com.bigfix.schemas.bes.Fixlet;
import com.bigfix.schemas.bes.FixletWithActions;
import com.bigfix.schemas.bes.SingleAction;
import com.bigfix.schemas.bes.Site;
import com.bigfix.schemas.bes.SourcedFixletAction;
import com.bigfix.schemas.bes.Task;
import com.bigfix.schemas.besapi.BESAPI;
import com.bigfix.schemas.besapi.BESAPI.Query;
import com.bigfix.schemas.besapi.BESAPI.SiteFile;
import com.bigfix.schemas.besapi.ComputerSetting;
import com.bigfix.schemas.besapi.RelevanceAnswer;
import com.bigfix.schemas.besapi.RelevanceResult;
import com.github.eyce9000.iem.api.actions.ActionBuilder;
import com.github.eyce9000.iem.api.actions.ActionTargetBuilder;
import com.github.eyce9000.iem.api.actions.logger.ActionLogger;
import com.github.eyce9000.iem.api.actions.script.ActionScriptBuilder;
import com.github.eyce9000.iem.api.impl.AbstractIEMAPI;
import com.github.eyce9000.iem.api.model.ActionID;
import com.github.eyce9000.iem.api.model.FixletID;
import com.github.eyce9000.iem.api.model.SiteID;
import com.github.eyce9000.iem.api.model.FixletID.ResourceType;
import com.github.eyce9000.iem.api.relevance.DataType;
import com.github.eyce9000.iem.api.relevance.QueryResultColumn;
import com.github.eyce9000.iem.api.relevance.RESTResultParser;
import com.github.eyce9000.iem.api.relevance.RelevanceException;
import com.github.eyce9000.iem.api.relevance.RowSerializer;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceBuilder;
import com.github.eyce9000.iem.api.relevance.SessionRelevanceQuery;
import com.github.eyce9000.iem.api.relevance.handlers.HandlerException;
import com.github.eyce9000.iem.api.relevance.handlers.RawResultHandler;
import com.github.eyce9000.iem.api.relevance.handlers.impl.RawResultHandlerDefault;
import com.github.eyce9000.iem.api.relevance.handlers.impl.TypedResultListHandler;
import com.github.eyce9000.iem.api.relevance.results.QueryResult;
import com.github.eyce9000.iem.api.relevance.results.ResultTuple;
import com.github.eyce9000.iem.api.serialization.ResultAnswerAdapter.Answer;
import com.google.common.base.Optional;


public class IEMAPI extends AbstractIEMAPI {
	private URI baseURI;
	private Client client;
	private HttpBasicAuthFilter authFilter;
	private WebTarget apiRoot, root;
	private Unmarshaller queryUnmarshaller;
	private String username;
	private Set<ActionLogger> actionLoggers = new HashSet<ActionLogger>();
	protected HttpClient apacheHttpClient;
	
	protected IEMAPI(){}
	
	/**
	 * 
	 * @param url The hostname of the IEM server. For example <code>mybigfixsrv.ibm.com</code>. The client will by default connect on port 52311.
	 * @param username The IEM Console username to connect with.
	 * @param password The IEM Console password to connect with.
	 * @throws Exception
	 */
	public IEMAPI(String hostname, String username, String password) throws Exception{
		this(ClientBuilderWrapper.defaultBuilder().build()
			,hostname
			,username
			,password);
	}

	public IEMAPI(URI uri, String username, String password) throws Exception{
		this(ClientBuilderWrapper.defaultBuilder().build()
			,uri
			,username
			,password);
	}
	
	public IEMAPI(Client client, String hostname, String username, String password) throws JAXBException{
		this(client
				,UriBuilder.fromPath("/").scheme("https").host(hostname).port(52311).build()
				,username
				,password);
	}
	
	public IEMAPI(Client client, URI uri, String username, String password) throws JAXBException{
		this.client = client;
		this.baseURI = uri;

		authFilter = new HttpBasicAuthFilter(username,password);
		root = client.target(UriBuilder.fromUri(baseURI)).register(authFilter);
        apiRoot = root.path("/api/");
        
        this.username = username;
        JAXBContext context = JAXBContext.newInstance(QueryResult.class);
        JAXBContext besContext = JAXBContext.newInstance(BESAPI.class,BES.class);
		queryUnmarshaller = context.createUnmarshaller();
		besUnmarshaller = besContext.createUnmarshaller();
		
		
		SSLContext sslContext = client.getSslContext();
		SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Registry<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create()
				.register(uri.getScheme(),factory)
				.build();
		HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(r);
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, 
		    new UsernamePasswordCredentials(username, password));
		apacheHttpClient = HttpClients.custom()
			.setDefaultCredentialsProvider(credentialsProvider)
			.setConnectionManager(cm)
			.build();
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
	
	public List<BESAPI.Computer> getComputers(){
		return this.getBESAPIContent(
				apiRoot.path("computers").request().get(),
				BESAPI.Computer.class);
	}
	
	public Optional<BESAPI.Computer> getComputer(long computerid){
		WebTarget target = apiRoot
				.path("computer/{computerid}")
				.resolveTemplate("computerid",computerid);
		return getSingleBESAPIContent(target.request().get(),BESAPI.Computer.class);
	}
	
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
	// C O M P U T E R  G R O U P  M E T H O D S
	//*******************************************************************
	
	public List<BESAPI.ComputerGroup> getComputerGroups(){
		return getBESAPIContent(apiRoot.path("/computergroups").request().get(),BESAPI.ComputerGroup.class);
	}
	
	public Optional<BESAPI.ComputerGroup> getComputerGroup(String siteType, String site, long groupId){
		WebTarget target = buildSiteTarget(apiRoot.path("/computergroup/"),siteType,site)
				.path("/{groupId}")
				.resolveTemplate("groupId", groupId);
		return getSingleBESAPIContent(target.request().get(), BESAPI.ComputerGroup.class);
	}
	
	public List<BESAPI.Computer> getComputerGroupMembers(String siteType, String site, long groupId){
		WebTarget target = buildSiteTarget(apiRoot.path("/computergroup/"),siteType,site)
				.path("/{groupId}/computers")
				.resolveTemplate("groupId", groupId);
		return getBESAPIContent(target.request().get(),BESAPI.Computer.class);
	}
	
	
	//*******************************************************************
	// A C T I O N   M E T H O D S
	//*******************************************************************
	
	
	public Optional<AbstractAction> getAction(BigInteger actionId){
		WebTarget target = apiRoot
				.path("action/{actionid}")
				.resolveTemplate("actionid",actionId);
		return getSingleBESContent(target.request().get(),AbstractAction.class);
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
	
	public void updateCustomSite(CustomSite site){
		WebTarget target = buildSiteTarget(apiRoot.path("/site/"),"custom",site.getName());
		putBESContent(target,site,Site.class);
	}
	
	public BESAPI.CustomSite createCustomSite(CustomSite site){
		BES bes = new BES();
		bes.getFixletOrTaskOrAnalysis().add(site);
		return this.getBESAPIContent(post("/api/sites",bes), BESAPI.CustomSite.class).get(0);
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
		Optional<Object> wrapper = getSingleBESAPIContent(resp,Object.class);
		if(!wrapper.isPresent())
			return Optional.absent();
		else{
			Object original = wrapper.get();
			if(original instanceof BESAPI.Baseline)
				return Optional.of((BESAPI.Baseline) original);
			else{
				throw new RuntimeException("Unexpected BESAPI type: "+original.getClass().getCanonicalName());
			}
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
	// F I L E S
	//*******************************************************************
	public List<BESAPI.SiteFile> getFiles(String siteType, String site){
		WebTarget target = buildSiteTarget(apiRoot.path("/site/"),siteType,site).path("/files");
		return getBESAPIContent(target.request().get(),BESAPI.SiteFile.class);
	}
	
	public File downloadFile(String siteType, String site, SiteFile siteFile){
		WebTarget target = buildSiteTarget(apiRoot.path("/site/"),siteType,site).path("/file/{fileid}").resolveTemplate("fileid", siteFile.getID().toString());
		File file = target.request().get(File.class);
		File outputFile = new File(file.getParentFile(),siteFile.getName());
		file.renameTo(outputFile);
		return outputFile;
	}
	
	public void deleteFile(String siteType, String site, SiteFile siteFile){
		deleteFile(siteType,site,siteFile.getID());
	}
	
	public void deleteFile(String siteType, String site, BigInteger fileId){
		WebTarget target = buildSiteTarget(apiRoot.path("/site/"),siteType,site).path("/file/{fileid}").resolveTemplate("fileid", fileId.toString());
		Response resp = target.request().delete();
	}

	public List<BESAPI.SiteFile> updateFile(String siteType, String site, SiteFile siteFile, File file){
		return updateFile(siteType,site,siteFile.getID(),file);
	}
	
	public List<BESAPI.SiteFile> updateFile(String siteType, String site, BigInteger fileId, File file){
		WebTarget target = buildSiteTarget(apiRoot.path("/site/"),siteType,site).path("/file/{fileid}").resolveTemplate("fileid", fileId.toString());
		
	    HttpPut httpPut = new HttpPut(target.getUri());
	    MultipartEntity reqEntity = new MultipartEntity();
	    	FileBody uploadFilePart = new FileBody(file);
	    	reqEntity.addPart("file", uploadFilePart);
	    httpPut.setEntity(reqEntity);
	    try {
			HttpResponse response = apacheHttpClient.execute(httpPut);
			return getBESAPIContent(response,BESAPI.SiteFile.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<BESAPI.SiteFile> createFiles(String siteType, String site, File... files){
		WebTarget target = buildSiteTarget(apiRoot.path("/site/"),siteType,site).path("/files");
		
	    HttpPost httpPost = new HttpPost(target.getUri());
	    MultipartEntity reqEntity = new MultipartEntity();
	    for(File file:files){
	    	FileBody uploadFilePart = new FileBody(file);
	    	reqEntity.addPart("file", uploadFilePart);
	    }
	    httpPost.setEntity(reqEntity);
	    try {
			HttpResponse response = apacheHttpClient.execute(httpPost);
			return getBESAPIContent(response,BESAPI.SiteFile.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	//*******************************************************************
	// O P E R A T O R S
	//*******************************************************************
	
	//*******************************************************************
	// D A S H B O A R D
	//*******************************************************************
	
	
	//*******************************************************************
	// R E S P O N S E  /  G E T  M E T H O D S
	//*******************************************************************

	public String getRaw(String path){
		return root.path(path).request().get(String.class);
	}
	
	public Response get(String path){
		return root.path(path).request().get();
	}
	
	public Response delete(String path){
		return root.path(path).request().delete();
	}
	public Response post(String path,BESAPI besapi){
		Entity<BESAPI> entity = Entity.entity(besapi, MediaType.APPLICATION_XML);
		return root.path(path).request().post(entity);
	}
	public Response post(String path,BES bes){
		Entity<BES> entity = Entity.entity(bes, MediaType.APPLICATION_XML);
		return root.path(path).request().post(entity);
	}
	public Response put(String path,BES bes){
		Entity<BES> entity = Entity.entity(bes, MediaType.APPLICATION_XML);
		return root.path(path).request().put(entity);
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
		WebTarget target = apiRoot.path("query");
		try {
			String data = "relevance="+URLEncoder.encode(srq.constructQuery(), "UTF-8");
			Response response = target.request(MediaType.APPLICATION_XML).post(Entity.text(data));

			if(response.getStatus()>=200 && response.getStatus()<300){
				RESTResultParser parser = new RESTResultParser();
				parser.parse(srq, (InputStream)response.getEntity(), handler);
			}
			else{
				try{
					String error = "";
					BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream)response.getEntity()));
					String line = null;
					while((line=reader.readLine())!=null)
						error += line +"\n";
					throw new BadRequestException(response.getStatus()+": "+error);
				}
				catch(Exception ex){
					throw new BadRequestException(ex);
				}
			}
		} 
		catch (RelevanceException e){
			throw e;
		}
		catch (HandlerException e){
			throw e;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
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
	
}
