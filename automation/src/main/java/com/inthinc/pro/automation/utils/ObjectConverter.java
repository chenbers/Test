package com.inthinc.pro.automation.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.inthinc.pro.automation.logging.Log;

public class ObjectConverter {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    
    public static String convertToXML(JSONObject jsonObject){
        try {
            return XML.toString(jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static JSONObject convertToJSONObject(Object entity, String name) {
        Throwable error;
        try {
            JSONObject jsonType = new JSONObject();
            jsonType.put(name, new JSONObject(mapper.writeValueAsString(entity)));
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
        
        throw new IllegalArgumentException(error);
    }
    

    public static JSONArray converToJSONArray(Object entity) {
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
            return mapper.readValue(source.getJSONObject(objectName).toString(), clazz);
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
        try {
            String name = clazz.getSimpleName().toLowerCase();
            JSONArray ja = XML.toJSONObject(object).getJSONObject(listName).getJSONArray(name);
            Log.debug(ja);
            List<T> list = new ArrayList<T>();
            for (int i=0; i<ja.length(); i++){
                list.add(mapper.readValue(ja.getJSONObject(i).toString(), clazz));
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
        Log.info(object);
        throw new IllegalArgumentException(error);
    }

}
