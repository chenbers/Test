package com.inthinc.pro.automation.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.apache.log4j.Logger;

public class HTTPCommands {
    
    public interface RallyFields{
        
    }
    
    private final static Logger logger = Logger.getLogger(HTTPCommands.class);


    protected HttpClient httpClient;

    protected String response;

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
    public void httpRequest(HttpMethod method) throws IOException,
            HttpException {
        response = null;
        try {
            int statusCode = httpClient.executeMethod(method);
            if (statusCode != HttpStatus.SC_OK) {
                logger.debug("POST method failed: " + method.getStatusLine());
                response = getResponseBodyFromStream(method
                        .getResponseBodyAsStream());
            } else {
                logger.debug("POST method succeeded: " + method.getStatusLine());
                response = getResponseBodyFromStream(method
                        .getResponseBodyAsStream());
            }
            method.releaseConnection();
        } catch (URIException e) {
            logger.fatal(method.getQueryString());
            throw new URIException("Failed");
        }

    }

    protected String encodeURLQuery(String string) throws URIException {
        return URIUtil.encodeQuery(string);
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

}
