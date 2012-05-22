package com.inthinc.pro.automation.rest;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.inthinc.pro.automation.AutomationPropertiesBean;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.logging.Log;
import com.inthinc.pro.automation.models.BaseEntity;
import com.inthinc.pro.automation.models.Person;
import com.inthinc.pro.automation.selenium.AutomationProperties;
import com.inthinc.pro.automation.utils.HTTPCommands;
import com.inthinc.pro.automation.utils.ObjectConverter;

public class RestCommands {
    
    private final HTTPCommands http;
    private final String baseUrl;
    private final static String service = "/service/api/";
    
    public RestCommands(String username, String password){
        http = new HTTPCommands(username, password);
        AutomationPropertiesBean apb = AutomationProperties.getPropertyBean();
        baseUrl = Addresses.getSilo(apb.getSilo()).getBaseAddress() + service;
    }
    
    public <T extends BaseEntity> T postObject(Class<T> type, BaseEntity instance, Integer id){
        String name = type.getSimpleName().toLowerCase();
        try {
            PostMethod post = new PostMethod(baseUrl + name + 
                    id==null ? "" : "/" + id);
            String xml = ObjectConverter.objectToXML(instance, name);
            StringRequestEntity body = new StringRequestEntity(xml, null, null);
            post.setRequestEntity(body);
            return ObjectConverter.convertXMLToObject(http.httpRequest(post), name, type);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public <T extends BaseEntity> boolean deleteObject(Class<T> type, int id){
        try {
            DeleteMethod delete = new DeleteMethod(baseUrl + type.getSimpleName().toLowerCase() + "/" + id);
            http.httpRequest(delete);
            return http.isSuccessful();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
    public <T extends BaseEntity> T getObject(Class<T> type, int id){
        String name = type.getSimpleName().toLowerCase();
        GetMethod get = new GetMethod(baseUrl + name + "/" + id);
        try {
            return ObjectConverter.convertXMLToObject(http.httpRequest(get), name, type);
        } catch (HttpException e) {
            Log.error(e);
        } catch (IOException e) {
            Log.error(e);
        }
        return null;
    }
    
    public <T extends BaseEntity> List<T> getBulk(Class<T> type){
        GetMethod get = new GetMethod(baseUrl + type.getSimpleName().toLowerCase() + "s");
        try {
            String result = http.httpRequest(get);
            return ObjectConverter.convertXMLToList(result, "collection", type);
        } catch (HttpException e) {
            Log.error(e);
        } catch (IOException e) {
            Log.error(e);
        }
        return null;
    }
    
    
    public static void main(String[] args){
        RestCommands rc = new RestCommands("0001", "password");
        Log.info(rc.getObject(Person.class, 4).getUser().getRoles());
        
        
    }
    
}


