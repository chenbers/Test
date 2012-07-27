package com.inthinc.pro.automation.rest;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.BaseEntity;
import com.inthinc.pro.automation.models.Event;
import com.inthinc.pro.automation.objects.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutoHTTPException;
import com.inthinc.pro.automation.utils.AutoServers;
import com.inthinc.pro.automation.utils.HTTPCommands;
import com.inthinc.pro.automation.utils.ObjectConverter;

public class RestCommands {
    
    private final HTTPCommands http;
    private final String baseUrl;
    private final static String service = "/service/api/";
    private final static String xmlStart = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
    private final static String contentType = "application/xml";
    
    public RestCommands(String username, String password){
        http = new HTTPCommands(username, password);
        AutoServers server = new AutoServers();
        baseUrl = server.getBaseAddress() + service;
    }
    
    public <T extends BaseEntity> T postObject(Class<T> type, BaseEntity instance, Object id){
        String name = type.getSimpleName().toLowerCase();
        String url = baseUrl + name + (id==null ? "" : "/" + id);
        try {
            PostMethod post = new PostMethod(url);
            String xml = xmlStart + ObjectConverter.objectToXML(instance, name);
            StringRequestEntity body = new StringRequestEntity(xml, contentType, "UTF-8");
            post.setRequestEntity(body);
            String response = http.httpRequest(post);
            return ObjectConverter.convertXMLToObject(response, name, type);
        } catch (UnsupportedEncodingException e) {
            Log.error(e);
        } catch (AutoHTTPException e) {
            Log.error(e.getReason());
            Log.error(http.getResults());
        }
        return null;
    }
    
    public <T extends BaseEntity> T putObject(Class<T> type, BaseEntity instance, Object id){
        String name = type.getSimpleName().toLowerCase();
        String url = baseUrl + name + (id==null ? "" : "/" + id);
        try {
            PutMethod post = new PutMethod(url);
            String xml = xmlStart + ObjectConverter.objectToXML(instance, name);
            StringRequestEntity body = new StringRequestEntity(xml, contentType, "UTF-8");
            post.setRequestEntity(body);
            String response = http.httpRequest(post);
            return ObjectConverter.convertXMLToObject(response, name, type);
        } catch (UnsupportedEncodingException e) {
            Log.error(e);
        } catch (AutoHTTPException e) {
            Log.error(e.getReason());
            Log.error(http.getResults());
        }
        return null;
    }
    
    public <T extends BaseEntity> boolean deleteObject(Class<T> type, int id){
        try {
            DeleteMethod delete = new DeleteMethod(baseUrl + type.getSimpleName().toLowerCase() + "/" + id);
            http.httpRequest(delete);
            return http.isSuccessful();
        } catch (AutoHTTPException e) {
            Log.error(e.getReason());
            Log.error(http.getResults());
        }
        return false;
    }
    /**
     * "{entity:driver|vehicle|group}/{entityID}/events/{eventTypes:all|.*}/{startDate}/{endDate}/{page}"
     * @param clazz
     * @param id
     * @param start
     * @param stop
     * @return
     */
    public List<Event> getEvents(Class<? extends BaseEntity> type, int id, AutomationCalendar start, AutomationCalendar stop, int page){
        String name = type.getSimpleName().toLowerCase();
        start.setFormat(WebDateFormat.WEB_SERVICE);
        stop.setFormat(WebDateFormat.WEB_SERVICE);
        GetMethod get = new GetMethod(baseUrl + name + "/" + id + "/events/all/" + start + "/" + stop + "/" + page);
        try {
            return ObjectConverter.convertXMLToList(http.httpRequest(get), "eventPage", Event.class);
        } catch (AutoHTTPException e) {
            Log.error(e.getReason());
            Log.error(http.getResults());
        }
        return null;
    }
    
    
    public <T extends BaseEntity> T getObject(Class<T> type, Object id){
        String name = type.getSimpleName().toLowerCase();
        GetMethod get = new GetMethod(baseUrl + name + (id==null ? "" : "/" + id));
        try {
            return ObjectConverter.convertXMLToObject(http.httpRequest(get), name, type);
        } catch (AutoHTTPException e) {
            Log.error(e.getReason());
            Log.error(http.getResults());
        }
        return null;
    }
    
    
    public <T extends BaseEntity> List<T> getBulk(Class<T> type){
        GetMethod get = new GetMethod(baseUrl + type.getSimpleName().toLowerCase() + "s");
        try {
            String result = http.httpRequest(get);
            return ObjectConverter.convertXMLToList(result, "collection", type);
        } catch (AutoHTTPException e) {
            Log.error(e.getReason());
            Log.error(http.getResults());
        }
        return null;
    }

    public HTTPCommands getHttp() {
        return http;
    }

    public <T extends BaseEntity> T createObject(Class<T> clazz, BaseEntity instance) {
        return postObject(clazz, instance, null);
    }
    
}


