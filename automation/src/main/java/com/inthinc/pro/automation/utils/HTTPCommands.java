package com.inthinc.pro.automation.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Level;



public class HTTPCommands {
    
    
    protected HttpClient httpClient;

    protected String response;


    private DefaultHttpClient defaultClient;
    
    public HTTPCommands(){
        httpClient = new HttpClient();
        defaultClient = new DefaultHttpClient();
    }

    public HTTPCommands(String userID, String password) {
        HttpClientParams params = new HttpClientParams();
        params.setAuthenticationPreemptive(true);
        HttpClient httpClient = new HttpClient(params);
        Credentials defaultcreds = new UsernamePasswordCredentials(userID, password);
        httpClient.getState().setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT, AuthScope.ANY_REALM), defaultcreds);
    }

    /**
     * Method httpRequest<br />
     * <br />
     * Using the given HttpMethod, execute it and get the status code<br />
     * Depending on the status, print an error or success method, consume the body<br />
     * then finally release the connection
     * 
     * @param method
     * @throws IOException
     * @throws HttpException
     */
    public String httpRequest(HttpMethod method) throws IOException,
            HttpException {
        response = null;
        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                MasterTest.print(method.getName() + " method failed: " + method.getStatusLine(), Level.DEBUG);
                response = getResponseBodyFromStream(method
                        .getResponseBodyAsStream());
            } else {
            	MasterTest.print(method.getName() + " method succeeded: " + method.getStatusLine(), Level.DEBUG);
                response = getResponseBodyFromStream(method
                        .getResponseBodyAsStream());
            }
            method.releaseConnection();
        } catch (URIException e) {
        	MasterTest.print(method.getQueryString());
            throw new URIException("Failed");
        }
        return response;
    }
    
    public String constructQuery(String ...items) {
        StringWriter query = new StringWriter();
        for (int i=0; i<items.length;i++){
            if (i!=0){
                query.write("&");
            }
            query.write(items[i] + "=" + items[++i]);
        }
        return encodeURLQuery(query.toString());
    }

    public String encodeURLQuery(String string) {
        try {
            return URIUtil.encodeQuery(string);
        } catch (URIException e) {
        	MasterTest.print(e, Level.DEBUG);
        }
        return "";
    }

    public static String getResponseBodyFromStream(InputStream is) {
        String str = "";
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[128];
            int size = 0;
            while ((size = is.read(buffer)) > 0) {
                baos.write(buffer, 0, size);
            }
            str = new String(baos.toByteArray());
        } catch (IOException ioe) {}
        return str;
    }

    public String httpRequest(HttpUriRequest method) throws ClientProtocolException, IOException {
        HttpResponse response;
        response = defaultClient.execute(method);
        MasterTest.print(response.getStatusLine(), Level.DEBUG);
        String returnResponse = getResponseBodyFromStream(response.getEntity().getContent()); 
        MasterTest.print(returnResponse, Level.DEBUG);
        return returnResponse;
    }

}
