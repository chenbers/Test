package com.inthinc.pro.notegen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.inthinc.pro.model.Device;


public class WSNoteSender implements SendNote {

    private String url;         
    private Integer port;
    
    private DefaultHttpClient defaultClient = new DefaultHttpClient();

    
    private static int WIFI_COMM_TYPE = 3;
       
    @Override
    public void sendNote(Integer noteType, Date noteTime, byte[] notePackage, Device device) throws Exception {
        String uri = 
                        "http://" + url + ":" + port + 
                        "/gprs_wifi/gprs.do?mcm_id=" +// device.getImei() +  // (comType.equals(Direction.sat) ? imei: mcmID )+
                        "&commType="+WIFI_COMM_TYPE+         // hard code to sat
                        "&sat_cmd="+noteType+
                        "&event_time="+(noteTime.getTime()/1000l);
System.out.println("sendNote: " + uri);        
        HttpPost method = new HttpPost(uri.toLowerCase());
        method.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, retryhandler);
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                
        entity.addPart("mcm_id", new StringBody(device.getMcmid()==null?device.getImei() : device.getMcmid(), Charset.forName("UTF-8")));
        entity.addPart("imei", new StringBody(device.getImei(), Charset.forName("UTF-8")));
        entity.addPart("commType", new StringBody("" + WIFI_COMM_TYPE, Charset.forName("UTF-8")));
        entity.addPart("event_time", new StringBody("" + noteTime.getTime(), Charset.forName("UTF-8")));
        entity.addPart("sat_cmd", new StringBody("" + noteType, Charset.forName("UTF-8")));
        entity.addPart("url", new StringBody(uri, Charset.forName("UTF-8")));
//        entity.addPart("processForwardCommands", new StringBody("false", Charset.forName("UTF-8")));
                
                
        entity.addPart("filename", new ByteArrayBody(notePackage, "filename"));
        method.setEntity(entity);
                    
        
        String response = httpRequest(method);
        
//        System.out.println("response: " + response);
    }
    
    @SuppressWarnings("finally")
    private String httpRequest(HttpUriRequest method) {
        try {
            HttpResponse response = defaultClient.execute(method);
            String returnResponse = getResponseBodyFromStream(response.getEntity().getContent()); 
            return returnResponse;
        } 
        finally {
            return "";
        }
    }

    
    private String getResponseBodyFromStream(InputStream is) {
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
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    HttpMethodRetryHandler retryhandler = new HttpMethodRetryHandler() {
        public boolean retryMethod(
            final HttpMethod method, 
            final IOException exception, 
            int executionCount) {
            if (executionCount >= 5) {
                // Do not retry if over max retry count
                return false;
            }
            if (exception instanceof NoHttpResponseException) {
                // Retry if the server dropped connection on us
                return true;
            }
            if (!method.isRequestSent()) {
                // Retry if the request has not been sent fully or
                // if it's OK to retry methods that have been sent
                return true;
            }
            // otherwise do not retry
            return false;
        }
    };
    
}
