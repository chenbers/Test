package com.inthinc.pro.service.it;


import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScheme;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.AuthState;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.junit.Ignore;
import org.junit.Test;

//@Ignore
public class CreateVehiclesITTestSSL {

    private String scheme = "https";
    private String host = "weatherford.inthinc.com"; 
//    private String username = "sclavell"; 
//    private String password = "w7tness"; 

//    private String username = "weatherfordinternationalltd"; 
//    private String password = "NrsryjpqCfpx"; 

    private String username = "testweatherford"; 
    private String password = "password"; 

    private int port = 443;
    private TrustManager trustManager;
    private DefaultHttpClient httpclient;
    private BasicHttpContext localcontext;

	@Test
	public void weatherfordSSLCreateVehiclesTest(){
		//Hardcoded to run against the qa account in the Weatherford
		try {
			setUpRequest();
			
	        sendCreateVehiclesRequest();
		}
		catch(Exception e){
			
		}
	}
	@Test
	public void weatherfordSSLCreateVehicleTest(){
		//Hardcoded to run against the qa account in the Weatherford
		try {
			setUpRequest();
			
	        sendCreateVehicleRequest();
		}
		catch(Exception e){
			
		}
	}
	@Test
	public void weatherfordSSLCreateVehicleBadVINTest(){
		//Hardcoded to run against the qa account in the Weatherford
		try {
			setUpRequest();
			
	        sendCreateVehicleRequestBadVIN();
		}
		catch(Exception e){
			
		}
	}
    private void setUpRequest() throws IOException, NoSuchAlgorithmException, KeyManagementException {
        scheme = "https";
        
        host = "weatherford.inthinc.com"; 
//        username = "sclavell"; 
//        password = "w7tness"; 
//        username = "weatherfordinternationalltd"; 
//        password = "NrsryjpqCfpx"; 
        username = "testweatherford"; 
        password = "password"; 

        port = 443;
        
        trustManager = new X509TrustManager() {

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            //do nothing
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            //do nothing
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

        };
        SSLContext sslcontext = SSLContext.getInstance("TLS");
        sslcontext.init(null, new TrustManager[] { trustManager }, null);

        SSLSocketFactory socketFactory = new SSLSocketFactory(sslcontext);
        socketFactory.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        SSLSocket socket = (SSLSocket) socketFactory.createSocket();
        socket.setEnabledCipherSuites(new String[] { "SSL_RSA_WITH_RC4_128_MD5" });

        httpclient = new DefaultHttpClient();
        Scheme sch = new Scheme("https", socketFactory, 443);
        httpclient.getConnectionManager().getSchemeRegistry().register(sch);

        httpclient.getCredentialsProvider().setCredentials(new AuthScope(host, port), new UsernamePasswordCredentials(username, password));

        localcontext = new BasicHttpContext();

        // Generate BASIC scheme object and stick it to the local
        // execution context
        BasicScheme basicAuth = new BasicScheme();
        localcontext.setAttribute("preemptive-auth", basicAuth);

        // Add as the first request interceptor
        httpclient.addRequestInterceptor(new PreemptiveAuth(), 0);


    }
    private void sendCreateVehiclesRequest() throws ClientProtocolException, IOException{
        HttpHost targetHost = new HttpHost(host, port, scheme);
        String uri = "/service/api/vehicles";
        HttpPost httpPost = new HttpPost(uri);
        String vehicle1 = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>184549725</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137726</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";
        String vehicle2 = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>184549725</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137727</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";
        String vehicle3 = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>184549725</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137728</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";

        StringEntity entity = new StringEntity( "<vehicles>"+vehicle1+vehicle2+vehicle3+"</vehicles>",HTTP.UTF_8);
        entity.setContentType("application/xml");
        httpPost.setEntity(entity);
        System.out.println("executing request: " + httpPost.getRequestLine());
        System.out.println("to target: " + targetHost);
        
        HttpResponse response = httpclient.execute(targetHost, httpPost, localcontext);
        HttpEntity responseEntity = response.getEntity();

        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        if (responseEntity != null) {
            System.out.println("Response content length: " + responseEntity.getContentLength());
            System.out.println(EntityUtils.toString(responseEntity));
            responseEntity.consumeContent();
        }

        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
    }
    private void sendCreateVehicleRequest() throws ClientProtocolException, IOException{
        HttpHost targetHost = new HttpHost(host, port, scheme);
        String uri = "/service/api/vehicle";

        HttpPost httpPost = new HttpPost(uri);
//        String vehicle1 = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>184549725</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137729</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";
    	String vehicle1 = "<?xml version=\"1.0\" standalone=\"yes\"?><vehicle><groupID>184549724</groupID><name>20558</name><make>Inter'l</make><model>4700 LP</model><color></color><status>ACTIVE</status><VIN>1HTSLAAM0WH520459</VIN><year>1997</year><license>R38 050</license><state><stateID>44</stateID></state></vehicle>";

        StringEntity entity = new StringEntity( vehicle1,HTTP.UTF_8);
        entity.setContentType("application/xml");
        httpPost.setEntity(entity);
        System.out.println("executing request: " + httpPost.getRequestLine());
        System.out.println("to target: " + targetHost);
        
        HttpResponse response = httpclient.execute(targetHost, httpPost, localcontext);
        HttpEntity responseEntity = response.getEntity();

        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        if (responseEntity != null) {
            System.out.println("Response content length: " + responseEntity.getContentLength());
            System.out.println(EntityUtils.toString(responseEntity));
            responseEntity.consumeContent();
        }

        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
    }
    private void sendCreateVehicleRequestBadVIN() throws ClientProtocolException, IOException{
        HttpHost targetHost = new HttpHost(host, port, scheme);
        String uri = "/service/api/vehicle";

        HttpPost httpPost = new HttpPost(uri);
        String vehicle1 = "<vehicle><color/><dot>PROMPT_FOR_DOT_TRIP</dot><groupID>184549725</groupID><license/><make>Ford</make><model>Explorer</model><name>Ford Exp Transistor</name><state><stateID>45</stateID></state><status>ACTIVE</status><VIN>21111111111137730x</VIN><vtype>HEAVY</vtype><year>2012</year></vehicle>";

        StringEntity entity = new StringEntity( vehicle1,HTTP.UTF_8);
        entity.setContentType("application/xml");
        httpPost.setEntity(entity);
        System.out.println("executing request: " + httpPost.getRequestLine());
        System.out.println("to target: " + targetHost);
        
        HttpResponse response = httpclient.execute(targetHost, httpPost, localcontext);
        HttpEntity responseEntity = response.getEntity();

        System.out.println("----------------------------------------");
        System.out.println(response.getStatusLine());
        if (responseEntity != null) {
            System.out.println("Response content length: " + responseEntity.getContentLength());
            System.out.println(EntityUtils.toString(responseEntity));
            responseEntity.consumeContent();
        }

        // When HttpClient instance is no longer needed,
        // shut down the connection manager to ensure
        // immediate deallocation of all system resources
        httpclient.getConnectionManager().shutdown();
    }

    static class PreemptiveAuth implements HttpRequestInterceptor {

        public void process(final HttpRequest request, final HttpContext context) throws HttpException, IOException {

            AuthState authState = (AuthState) context.getAttribute(ClientContext.TARGET_AUTH_STATE);

            // If no auth scheme avaialble yet, try to initialize it preemptively
            if (authState.getAuthScheme() == null) {
                AuthScheme authScheme = (AuthScheme) context.getAttribute("preemptive-auth");
                CredentialsProvider credsProvider = (CredentialsProvider) context.getAttribute(ClientContext.CREDS_PROVIDER);
                HttpHost targetHost = (HttpHost) context.getAttribute(ExecutionContext.HTTP_TARGET_HOST);
                if (authScheme != null) {
                    Credentials creds = credsProvider.getCredentials(new AuthScope(targetHost.getHostName(), targetHost.getPort()));
                    if (creds == null) {
                        throw new HttpException("No credentials for preemptive authentication");
                    }
                    authState.setAuthScheme(authScheme);
                    authState.setCredentials(creds);
                }
            }

        }

    }

}
