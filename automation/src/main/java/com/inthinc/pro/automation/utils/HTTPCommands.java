package com.inthinc.pro.automation.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class HTTPCommands {
    
    
    private final static Logger logger = Logger.getLogger(HTTPCommands.class);


    protected HttpClient httpClient;

    protected String response;


    private DefaultHttpClient defaultClient;
    
    public HTTPCommands(){
        httpClient = new HttpClient();
        defaultClient = new DefaultHttpClient();
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
                logger.debug(method.getName() + " method failed: " + method.getStatusLine());
                response = getResponseBodyFromStream(method
                        .getResponseBodyAsStream());
            } else {
                logger.debug(method.getName() + " method succeeded: " + method.getStatusLine());
                response = getResponseBodyFromStream(method
                        .getResponseBodyAsStream());
            }
            method.releaseConnection();
        } catch (URIException e) {
            logger.fatal(method.getQueryString());
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

    public String httpRequest(HttpUriRequest method) {
        try {
            HttpResponse response = defaultClient.execute(method);
            MasterTest.print(response.getStatusLine());
            String returnResponse = getResponseBodyFromStream(response.getEntity().getContent()); 
            MasterTest.print(returnResponse, Level.INFO);
            return returnResponse;
        } catch (ClientProtocolException e) {
            MasterTest.print(e);
        } catch (IOException e) {
            MasterTest.print(e);
        }
        return "";
    }

}
