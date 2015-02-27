package com.github.eyce9000.iem.api;

import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.logging.Logger;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.glassfish.jersey.filter.LoggingFilter;


public class ClientBuilderWrapper {
	private ClientBuilder builder;
	
	public ClientBuilderWrapper(){
		this(ClientBuilder.newBuilder());
	}
	
	public ClientBuilderWrapper(ClientBuilder builder){
		this.builder = builder;
	}
	
	public ClientBuilderWrapper timeout(int timeout){
		builder = builder
				.property("jersey.client.config.connectTimeout",timeout)
				.property("jersey.client.config.readTimeout",timeout);
		return this;
	}
	
	public ClientBuilderWrapper hostnameVerifier(X509HostnameVerifier verifier){
		builder.hostnameVerifier(verifier);
		return this;
	}
	
	public ClientBuilderWrapper enableLogging(){
		builder = builder.register(new LoggingFilter(Logger.getAnonymousLogger(),true));
		return this;
	}
	
	public ClientBuilderWrapper trustAll() throws NoSuchAlgorithmException, KeyManagementException{
		SSLContext sslContext = SSLContext.getInstance("TLS");


        TrustManager tm = new X509TrustManager() {
            @Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
			public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sslContext.init(null, new TrustManager[] { tm }, null);
		builder = builder.sslContext(sslContext);
		return this;
	}
	
	public ClientBuilderWrapper trustStore(KeyStore ks){
		builder = builder.trustStore(ks);
		return this;
	}
	
	public ClientBuilder clientBuilder(){
		return builder;
	}
	
	public Client build() throws Exception{
		return builder.build();
	}
	
	public static ClientBuilderWrapper defaultBuilder() throws Exception{
		return new ClientBuilderWrapper()
		.timeout(60000)
		.trustAll()
		.hostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
	}
}
