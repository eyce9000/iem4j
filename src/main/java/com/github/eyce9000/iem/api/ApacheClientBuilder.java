package com.github.eyce9000.iem.api;

import java.net.URI;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.BasicHttpClientConnectionManager;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class ApacheClientBuilder {
	
	private boolean pooled = false;
	private boolean insecure;

	public ApacheClientBuilder pooled(){
		this.pooled  = true;
		return this;
	}
	
	public ApacheClientBuilder insecure(){
		this.insecure = true;
		return this;
	}
	
	private SSLConnectionSocketFactory insecureSocketFactory(){
		try{
		SSLContext sslContext = SSLContext.getInstance("TLS");
		sslContext.init(null, new TrustManager[] { new X509TrustManager() {
		            @Override
					public X509Certificate[] getAcceptedIssuers() {
		                    return null;
		            }
		
		            @Override
					public void checkClientTrusted(X509Certificate[] certs,
		                            String authType) {
		            }
		
		            @Override
					public void checkServerTrusted(X509Certificate[] certs,
		                            String authType) {
		            }
		} }, new SecureRandom());
		SSLConnectionSocketFactory factory = new SSLConnectionSocketFactory(sslContext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		return factory;
		}
		catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}
	
	public HttpClient build() {
		Registry<ConnectionSocketFactory> r = null;
		if(insecure){
			RegistryBuilder rbuilder  = RegistryBuilder.<ConnectionSocketFactory>create();
			rbuilder = rbuilder.register("https",insecureSocketFactory());
			r = rbuilder.build();
		}
		
		
		HttpClientConnectionManager cm;
		if(pooled){
			if(r!=null)
				cm = new PoolingHttpClientConnectionManager(r);
			else
				cm = new PoolingHttpClientConnectionManager();
		}
		else{
			if(r!=null)
				cm = new BasicHttpClientConnectionManager(r);
			else
				cm = new BasicHttpClientConnectionManager();
		}
		CloseableHttpClient apacheHttpClient = HttpClients.custom()
			.setConnectionManager(cm)
			.build();
		return apacheHttpClient;
	}
}
