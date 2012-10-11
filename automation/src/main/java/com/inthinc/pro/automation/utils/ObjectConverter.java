package com.inthinc.pro.automation.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inthinc.pro.automation.logging.Log;

public class ObjectConverter {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    static {
        mapper.setSerializationInclusion(Include.NON_NULL);
    }
    
    public static ObjectMapper getObjectMapper(){
    	return mapper;
    }
    
    public static String convertToXML(JSONObject jsonObject){
        try {
            String xml = XML.toString(jsonObject);
            Log.debug(xml);
            return xml;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static JSONObject convertToJSONObject(Object entity, String name) {
        Throwable error;
        try {
            JSONObject jsonType = new JSONObject();
            jsonType.put(name, convertToJSONObject(entity));
            Log.debug(jsonType);
            return jsonType;
        } catch (NullPointerException e){
            throw new IllegalArgumentException("Should not be passing null arguments");
        } catch (JSONException e) {
            error = e;
        }
     
        Log.info(entity);
        throw new IllegalArgumentException(error);
    }
    
    public static JSONObject convertToJSONObject(Object entity) {
    	Throwable error;
        try {
            JSONObject jsonType = new JSONObject(mapper.writeValueAsString(entity));
            Log.debug(jsonType);
            return jsonType;
        } catch (NullPointerException e){
            throw new IllegalArgumentException("Should not be passing null arguments");
        } catch (JsonGenerationException e) {
            error = e;
        } catch (JsonMappingException e) {
            error = e;
        } catch (JSONException e) {
            error = e;
        } catch (IOException e) {
            error = e;
        }
        
        Log.info(entity);
        throw new IllegalArgumentException(error);
    }
    

    public static JSONArray convertToJSONArray(Object entity) {
        Throwable error;
        try {
            JSONArray jsonType = new JSONArray(mapper.writeValueAsString(entity));
            Log.debug(jsonType);
            return jsonType;
        } catch (NullPointerException e){
            throw new IllegalArgumentException("Should not be passing null arguments");
        } catch (JsonGenerationException e) {
            error = e;
        } catch (JsonMappingException e) {
            error = e;
        } catch (JSONException e) {
            error = e;
        } catch (IOException e) {
            error = e;
        }
        Log.info(entity);
        throw new IllegalArgumentException(error);
    }


    
    public static String objectToXML(Object entity, String name){
        return convertToXML(convertToJSONObject(entity, name));
    }
    
    public static <T> T convertXMLToObject(String object, String objectName, Class<T> clazz){
        try {
            return convertJSONObjectToObject(XML.toJSONObject(object), objectName, clazz);
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    private static <T> T convertJSONObjectToObject(JSONObject source, String objectName, Class<T> clazz){
        Throwable error;
        try {
            String object = source.getJSONObject(objectName).toString();
            return mapper.readValue(object, clazz);
        } catch (JsonGenerationException e) {
            error = e;
        } catch (JsonMappingException e) {
            error = e;
        } catch (JSONException e) {
            error = e;
        } catch (IOException e) {
            error = e;
        }
        
        throw new IllegalArgumentException(error);
    }
    
    public static <T> T convertJSONToObject(String object, String objectName, Class<T> clazz){
        try {
            return convertJSONObjectToObject(new JSONObject(object), objectName, clazz);
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }
    
    
    public static <T> List<T> convertXMLToList(String object, String listName, Class<T> clazz){
        Throwable error;
        JSONObject last = null;
        try {
            String name = clazz.getSimpleName().toLowerCase();
            JSONObject jo = XML.toJSONObject(object).getJSONObject(listName);
            List<T> list = new ArrayList<T>();
            if (jo.get(name) instanceof JSONArray){
                JSONArray ja = jo.getJSONArray(name);
                Log.debug(ja);
                for (int i=0; i<ja.length(); i++){
                    last = ja.getJSONObject(i);
                    list.add(mapper.readValue(ja.getJSONObject(i).toString(), clazz));
                }
            } else {
                list.add(mapper.readValue(jo.getJSONObject(name).toString(), clazz));
            }
            Log.debug(list);
            return list;
        } catch (JsonGenerationException e) {
            error = e;
        } catch (JsonMappingException e) {
            error = e;
        } catch (JSONException e) {
            error = e;
        } catch (IOException e) {
            error = e;
        }
        Log.info(error);
        Log.info(last); 
        throw new IllegalArgumentException(error);
    }

}
