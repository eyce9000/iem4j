package com.github.eyce9000.iem.webreports.relevance;

import com.bigfix.schemas.relevance.AuthenticateHeader;
import com.bigfix.schemas.relevance.GetStructuredRelevanceResult;
import com.bigfix.schemas.relevance.LoginHeader;

public class RequestBuilder {
	private Envelope env;
	
	public RequestBuilder(){
		 env = new Envelope();
		 env.setBody(new EnvelopeBody());
		 env.setHeader(new EnvelopeHeader());
	}
	
	public RequestBuilder login(String username, String password){
		LoginHeader header = new LoginHeader();
		header.setUsername(username);
		header.setPassword(password);
		
		env.getHeader().setRequestHeader(header);
		return this;
	}
	
	public RequestBuilder authenticate(String username, String token){
		AuthenticateHeader header = new AuthenticateHeader();
		header.setUsername(username);
		header.setSessionToken(token);
		
		env.getHeader().setRequestHeader(header);
		
		return this;
	}
	
	public Envelope buildRelevanceRequest(String relevanceQuery){
		GetStructuredRelevanceResult request = new GetStructuredRelevanceResult();
		request.setRelevanceExpr(relevanceQuery);
		env.getBody().setStructuredRelevanceRequest(request);
		return env;
	}
}
