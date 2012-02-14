package com.inthinc.pro.notegen;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.inthinc.pro.model.event.Event;

public class NoteFileParser {
    private static final Logger logger = Logger.getLogger(NoteFileParser.class);
    
    

    // expected format:
    // 1st line titles 
    // subsequent lines are an event per line 
    // attrMap,avgSpeed,created,deviceID,distance,driverID,forgiven,groupID,heading,lat,lng,maprev,noteID,odometer,sats,speed,speedLimit,state,time,topSpeed,type,vehicleID
    public List<Event> parseFile(String path)
    {
        List<Event> eventList = new ArrayList<Event>();
        List<Map<String, Object>> eventMaps = new ArrayList<Map<String, Object>>();
        Date maxDate = new Date(0);
        
        BufferedReader in;
        try {

            String[] keys = null;
            InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        
            in = new BufferedReader(new InputStreamReader(stream));
            String str; 
            while ((str = in.readLine()) != null) {  
                String values[] = str.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
                for (int i = 0; i < values.length; i++) {
                    if (values[i].startsWith("\"") && values[i].endsWith("\"")) {
                        values[i] = values[i].substring(1, values[i].length()-1);
                    }
                }
                // 1st line are titles or keys
                if (keys == null) {
                    keys = values;
                    continue;
                }
                Map<String, Object> eventMap = new HashMap<String, Object>();
                int col=0;
                for (String key : keys) {
                    if (key.contains("ID")) {
                        col++;
                        continue;
                    }
                    eventMap.put(key, values[col++]);
                }
                eventMaps.add(eventMap);
            }
        
        } catch (FileNotFoundException e) {
            logger.error("File not found: " + path, e);
            return null;
        } catch (IOException e) {
            logger.error("IOException: " + path, e);
            return null;
        }
        
        Collections.reverse(eventMaps);
        for (Map<String, Object> eventMap : eventMaps)
            eventList.add(constructEvent(eventMap));
        return eventList;
        
    }
    
    private Event constructEvent(Map<String, Object> eventMap) {
        
        
        NoteMapper mapper = new NoteMapper();
        Event event = mapper.convertToModelObject(eventMap, Event.class);
        
//        System.out.println(event.getClass().getName() + " " + event.toString());
        return event;
    }

    
    protected static List<Field> getAllFields(Class<?> type) {
        List<Field> fieldList = new ArrayList<Field>();
        Class<?> clazz = type;
        while (clazz != null) {
            fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        return fieldList;
    }



    private static DateTimeFormatter NOTE_DATE_FORMAT = DateTimeFormat.forPattern("MM/dd/yyyy HH:mm:ss");
    private Date getNoteTime(String dateString) {
        return NOTE_DATE_FORMAT.parseDateTime(dateString).toDate();
    }


}
