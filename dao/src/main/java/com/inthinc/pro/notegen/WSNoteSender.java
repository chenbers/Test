package com.inthinc.pro.notegen;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Date;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

import com.inthinc.pro.model.Device;


public class WSNoteSender implements SendNote {

    private String url;         // mina?
    private Integer port;
    
    private static int WIFI_COMM_TYPE = 3;
       
    @Override
    public void sendNote(Integer noteType, Date noteTime, byte[] notePackage, Device device) {
//        List<String> reply = new ArrayList<String>();
        String uri = 
                        "http://" + url + ":" + port + 
                        "/gprs_wifi/gprs.do?mcm_id=" + device.getImei() +  // (comType.equals(Direction.sat) ? imei: mcmID )+
                        "&commType="+WIFI_COMM_TYPE+         // hard code to sat
                        "&sat_cmd="+noteType+
                        "&event_time="+(noteTime.getTime()/1000l);
System.out.println("uri: " + uri);                    
        HttpPost method = new HttpPost(uri.toLowerCase());
        MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                
        try {
            entity.addPart("mcm_id", new StringBody(device.getMcmid()==null?device.getImei() : device.getMcmid(), Charset.forName("UTF-8")));
            entity.addPart("imei", new StringBody(device.getImei(), Charset.forName("UTF-8")));
            entity.addPart("commType", new StringBody("" + WIFI_COMM_TYPE, Charset.forName("UTF-8")));
            entity.addPart("event_time", new StringBody("" + noteTime.getTime(), Charset.forName("UTF-8")));
            entity.addPart("sat_cmd", new StringBody("" + noteType, Charset.forName("UTF-8")));
            entity.addPart("url", new StringBody(uri, Charset.forName("UTF-8")));
//                    entity.addPart("note", new StringBody(note.toString(), Charset.forName("UTF-8")));
//                    entity.addPart("vehicle_id_str", new StringBody("654", Charset.forName("UTF-8")));
//                    entity.addPart("company_id", new StringBody("3", Charset.forName("UTF-8")));
        } catch (Exception e) {
        e.printStackTrace();            
        }
                
                
        entity.addPart("filename", new ByteArrayBody(notePackage, "filename"));
        method.setEntity(entity);
                    
        String response = httpRequest(method);
        
        System.out.println("response: " + response);
//        reply.add(httpRequest(method));
                
         //   return reply.toArray(new String[]{});
    }
    
    private String httpRequest(HttpUriRequest method) {
        try {
            HttpResponse response = new DefaultHttpClient().execute(method);
            String returnResponse = getResponseBodyFromStream(response.getEntity().getContent()); 
            return returnResponse;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
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


    
}