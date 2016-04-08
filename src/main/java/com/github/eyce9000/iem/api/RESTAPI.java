package com.github.eyce9000.iem.api;


import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
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
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
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
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.joda.time.DateTime;

import com.bigfix.schemas.bes.AbstractAction;
import com.bigfix.schemas.bes.Action;
import com.bigfix.schemas.bes.Analysis;
import com.bigfix.schemas.bes.BES;
import com.bigfix.schemas.bes.BES.CustomSite;
import com.bigfix.schemas.bes.Baseline;
import com.bigfix.schemas.bes.ComputerGroup;
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
import com.github.eyce9000.iem.api.content.ContentAPI;
import com.github.eyce9000.iem.api.content.impl.ContentAPIImpl;
import com.github.eyce9000.iem.api.impl.AbstractRESTAPI;
import com.github.eyce9000.iem.api.impl.Paths;
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


public class RESTAPI extends AbstractRESTAPI {
	protected Unmarshaller queryUnmarshaller;
	protected HttpClient apacheHttpClient;
	protected ContentAPI content = null;
	
	protected RESTAPI(){}
	
	/**
	 * 
	 * @param url The hostname of the IEM server. For example <code>mybigfixsrv.ibm.com</code>. The client will by default connect on port 52311.
	 * @param username The IEM Console username to connect with.
	 * @param password The IEM Console password to connect with.
	 * @throws URISyntaxException 
	 * @throws Exception
	 */
	
	public RESTAPI(String hostname, String username, String password) throws URISyntaxException, Exception{
		this(new URIBuilder().setPath("/").setScheme("https").setHost(hostname).setPort(52311).build()
				,username
				,password);
	}
	public RESTAPI(HttpClient apacheHttpClient,URI uri, String username, String password) throws Exception{
		client = Executor.newInstance(apacheHttpClient).auth(username, password);
		baseURI = uri;
		initializeJAXB();
	}
	public RESTAPI(URI uri, String username, String password) throws Exception{
		this(new ApacheClientBuilder().build(),uri,username,password);
		
	}
	
	public RESTAPI(Executor client, URI uri) throws JAXBException{
		this.client = client;
		this.baseURI = uri;
		initializeJAXB();
	}
	
	private void initializeJAXB() throws JAXBException{
        JAXBContext context = JAXBContext.newInstance(QueryResult.class);
        JAXBContext besContext = JAXBContext.newInstance(BESAPI.class,BES.class);
		queryUnmarshaller = context.createUnmarshaller();
		besUnmarshaller = besContext.createUnmarshaller();
		besMarshaller = besContext.createMarshaller();
	}

	//*******************************************************************
	// C O N T E N T   M E T H O D S
	//*******************************************************************
	
	public ContentAPI content(){
		if(content == null)
			content =  new ContentAPIImpl(this);
		return content;
	}
	
	
	//*******************************************************************
	// C O M P U T E R   M E T H O D S
	//*******************************************************************
	
	public List<BESAPI.Computer> getComputers(){
		return getContent(get(Paths.computers),BESAPI.Computer.class);
	}
	
	public Optional<BESAPI.Computer> getComputer(long computerid){
		return getSingleContent(get(Paths.computer(computerid)),BESAPI.Computer.class);
	}
	
	public List<BESAPI.ComputerSettings> getComputerSettings(long computerid) throws Exception{
		return getContent(get(Paths.computerSettings(computerid)),BESAPI.ComputerSettings.class);
	}
	
	public Optional<BESAPI.ComputerSettings> getComputerSetting(long computerid, String settingid){
		return getSingleContent(get(Paths.computerSetting(computerid,settingid)),BESAPI.ComputerSettings.class);
	}
	
	public BESAPI.Action setComputerSetting(long computerid, String settingid, String value){
		LinkedList<ComputerSetting> settings = new LinkedList<ComputerSetting>();
		settings.add(new ComputerSetting()
			.withName(settingid)
			.withValue(value));
		return setComputerSettings(computerid,settings);
	}
	
	public BESAPI.Action setComputerSettings(long computerid, Map<String,String> settingsMap){
		LinkedList<ComputerSetting> settings = new LinkedList<ComputerSetting>();
		for(Entry<String,String> entry:settingsMap.entrySet()){
			settings.add(new ComputerSetting()
				.withName(entry.getKey())
				.withValue(entry.getValue()));
		}
		return setComputerSettings(computerid,settings);
	}
	
	private BESAPI.Action setComputerSettings(long computerid, List<ComputerSetting> settings){
		BESAPI.ComputerSettings csettings = new BESAPI.ComputerSettings();
		csettings.getSetting().addAll(settings);
		
		return getSingleContent(put(Paths.computerSettings(computerid),csettings),BESAPI.Action.class).get();
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
		return getSingleContent(delete(Paths.computerSetting(computerid,settingName)),BESAPI.Action.class).get();
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
		return getContent(get(Paths.computergroups+"/master"),BESAPI.ComputerGroup.class);
	}
	
	public Optional<ComputerGroup> getComputerGroup(long groupId){
		return getSingleContent(get(Paths.computerGroup(groupId)),ComputerGroup.class);
	}
	
	public BESAPI.ComputerGroup updateComputerGroup(long id,ComputerGroup group){
		return getSingleContent(putBES(Paths.computerGroup(id),group),BESAPI.ComputerGroup.class).get();
	}
	
	public BESAPI.ComputerGroup createComputerGroup(ComputerGroup group){
		return getSingleContent(postBES(Paths.computergroups+"/master",group),BESAPI.ComputerGroup.class).get();
	}
	
	public List<BESAPI.Computer> getComputerGroupMembers(long groupId){
		return getContent(get(Paths.computerGroupMembers(groupId)),BESAPI.Computer.class);
	}
	
	
	//*******************************************************************
	// A C T I O N   M E T H O D S
	//*******************************************************************
	
	
	public Optional<AbstractAction> getAction(long actionId){
		return getSingleContent(get(Paths.action(actionId)),AbstractAction.class);
	}
	
	public BESAPI.ActionResults getActionStatus(BESAPI.Action action){
		return getActionStatus(action.getID().longValue());
	}
	
	public BESAPI.ActionResults getActionStatus(long actionId){
		return getSingleContent(get(Paths.actionStatus(actionId)),BESAPI.ActionResults.class).get();
	}
	
	public void stopAction(BESAPI.ActionResults results){
		stopAction(results.getActionID().longValue());
	}
	
	public void stopAction(BESAPI.Action action){
		stopAction(action.getID().longValue());
	}
	
	public void stopAction(long actionId){
		post(Paths.actionStop(actionId));
	}

	public void deleteAction(BESAPI.ActionResults results){
		deleteAction(results.getActionID().longValue());
	}
	
	public void deleteAction(BESAPI.Action action){
		deleteAction(action.getID().longValue());
	}
	public void deleteAction(long actionId){
		post(Paths.action(actionId));
				
	}
	public List<BESAPI.Action> getActions(){
		return getContent(get(Paths.actions),BESAPI.Action.class);
	}

	public BESAPI.Action createFixletSourcedAction(SourcedFixletAction sourcedAction){
		return getSingleContent(postBES(Paths.actions,sourcedAction),BESAPI.Action.class).get();
	}
	
	public BESAPI.Action createAction(com.bigfix.schemas.bes.Action besAction){
		return getSingleContent(postBES(Paths.actions,besAction),BESAPI.Action.class).get();
	}
	
	//*******************************************************************
	// S I T E   M E T H O D S
	//*******************************************************************
	
	public Optional<Site> getSite(String siteType, String site){
		return getSingleContent(get(Paths.site(site, siteType)),Site.class);
	}
	
	public List<BESAPI.Site> getSites(){
		return getContent(get(Paths.sites),BESAPI.Site.class);
	}
	
	public void updateCustomSite(CustomSite site){
		putBES(Paths.site(site.getName(),"custom"),site);
	}
	
	public BESAPI.CustomSite createCustomSite(CustomSite site){
		BES bes = new BES();
		bes.getFixletOrTaskOrAnalysis().add(site);
		return getContent(post("/api/sites",bes), BESAPI.CustomSite.class).get(0);
	}
	
	//*******************************************************************
	// C O N T E N T   M E T H O D S
	//*******************************************************************
	
	
	// Fixlets
 	public Optional<FixletWithActions> getFixlet(String siteType, String site, long id){
		return getSingleContent(get(Paths.fixlet(site,siteType,id)),FixletWithActions.class);
	}
	public Optional<BESAPI.Fixlet> createFixlet(String siteType, String site, FixletWithActions content){
		return getSingleContent(postBES(Paths.fixlets(site, siteType),content),BESAPI.Fixlet.class);
	}
	
	public void updateFixlet(String siteType, String site, long id, FixletWithActions content){
		putBES(Paths.fixlet(site,siteType,id),content);
	}
	
	public List<BESAPI.Fixlet> getFixlets(String siteType,String site){
		return getContent(get(Paths.fixlets(site, siteType)),BESAPI.Fixlet.class);
	}
	
	public void deleteFixlet(String siteType, String site, long id){
		delete(Paths.fixlet(site,siteType,id));
	}

	
	// Tasks
 	public Optional<Task> getTask(String siteType, String site, long id){
		return getSingleContent(get(Paths.task(site,siteType,id)),Task.class);
	}
	public Optional<BESAPI.Task> createTask(String siteType, String site, Task content){
		return getSingleContent(postBES(Paths.tasks(site, siteType),content),BESAPI.Task.class);
	}
	
	public void updateTask(String siteType, String site, long id, Task content){
		putBES(Paths.task(site,siteType,id),content);
	}
	
	public List<BESAPI.Task> getTasks(String siteType,String site){
		return getContent(get(Paths.tasks(site, siteType)),BESAPI.Task.class);
	}
	
	public void deleteTask(String siteType, String site, long id){
		delete(Paths.task(site,siteType,id));
	}
		
	
	//Analyses
	public Optional<Analysis> getAnalysis(String siteType, String site, long id){
		return getSingleContent(get(Paths.analysis(site,siteType,id)),Analysis.class);
	}
	public Optional<BESAPI.Analysis> createAnalysis(String siteType, String site, Analysis content){
		return getSingleContent(postBES(Paths.analyses(site, siteType),content),BESAPI.Analysis.class);
	}
	
	public void updateAnalysis(String siteType, String site, long id, Analysis content){
		putBES(Paths.analysis(site,siteType,id),content);
	}
	
	public List<BESAPI.Analysis> getAnalysiss(String siteType,String site){
		return getContent(get(Paths.analyses(site, siteType)),BESAPI.Analysis.class);
	}
	
	public void deleteAnalysis(String siteType, String site, long id){
		delete(Paths.analysis(site,siteType,id));
	}
	
	//Baselines
	public Optional<Baseline> getBaseline(String siteType, String site, long id){
		return getSingleContent(get(Paths.fixlet(site,siteType,id)),Baseline.class);
	}
	public Optional<BESAPI.Baseline> createBaseline(String siteType, String site, Baseline content){
		return getSingleContent(postBES(Paths.siteImport(site, siteType),content),BESAPI.Baseline.class);
	}
	
	public void updateBaseline(String siteType, String site, long id, Baseline content){
		putBES(Paths.fixlet(site,siteType,id),content);
	}
	
	public List<BESAPI.Baseline> getBaselines(String siteType,String site){
		return getContent(get(Paths.baselines(site, siteType)),BESAPI.Baseline.class);
	}
	
	public void deleteBaseline(String siteType, String site, long id){
		delete(Paths.fixlet(site,siteType,id));
	}
	
	//Import
	public BESAPI importContent(String siteType, String site, BES content){
		return (BESAPI)handleResponse(post(Paths.siteImport(site, siteType),content));
	}
	

	
	//*******************************************************************
	// F I L E S
	//*******************************************************************
	public List<BESAPI.SiteFile> getFiles(String siteType, String site){
		return getContent(get(Paths.files(site,siteType)),BESAPI.SiteFile.class);
	}
	
	public File downloadFile(String siteType, String site, SiteFile siteFile) throws UnsupportedOperationException, IOException{
		HttpResponse response = get(Paths.file(site,siteType,siteFile.getID().longValue()));
		InputStream is = response.getEntity().getContent();
		File output = File.createTempFile("BESFile", ".bes");
		IOUtils.copy(is, new FileOutputStream(output));
		return output;
	}
	
	public void deleteFile(String siteType, String site, long id){
		delete(Paths.file(site,siteType,id));
	}
	
	public List<BESAPI.SiteFile> updateFile(String siteType, String site, SiteFile siteFile, File file) throws URISyntaxException{
		return updateFile(siteType,site,siteFile.getID().longValue(),file);
	}
	
	public List<BESAPI.SiteFile> updateFile(String siteType, String site, long id, File file) throws URISyntaxException{
		URI uri = new URIBuilder(baseURI).setPath(Paths.file(site,siteType,id)).build();
	    MultipartEntity reqEntity = new MultipartEntity();
    	FileBody uploadFilePart = new FileBody(file);
    	reqEntity.addPart("file", uploadFilePart);
	    try {
			HttpResponse response = client.execute(Request.Post(uri).body(reqEntity)).returnResponse();
			return getContent(response,BESAPI.SiteFile.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<BESAPI.SiteFile> createFiles(String siteType, String site, File... files) throws URISyntaxException{
		URI uri = new URIBuilder(baseURI).setPath(Paths.files(site,siteType)).build();
	    MultipartEntity reqEntity = new MultipartEntity();
	    for(File file:files){
	    	FileBody uploadFilePart = new FileBody(file);
	    	reqEntity.addPart("file", uploadFilePart);
	    }
	    try {
			HttpResponse response = client.execute(Request.Post(uri).body(reqEntity)).returnResponse();
			return getContent(response,BESAPI.SiteFile.class);
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
		
		try {
			String data = "relevance="+URLEncoder.encode(srq.constructQuery(), "UTF-8");
			HttpResponse response = request(Action.Post,Paths.query,data);
			
			if(response.getStatusLine().getStatusCode()>=200 && response.getStatusLine().getStatusCode()<300){
				RESTResultParser parser = new RESTResultParser();
				parser.parse(srq, response.getEntity().getContent(), handler);
			}
			else{
				try{
					String error = "";
					BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
					String line = null;
					while((line=reader.readLine())!=null)
						error += line +"\n";
					throw new RuntimeException(response.getStatusLine().getStatusCode()+": "+error);
				}
				catch(Exception ex){
					throw new RuntimeException(ex);
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
