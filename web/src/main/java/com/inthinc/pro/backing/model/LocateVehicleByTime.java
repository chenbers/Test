package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;

import com.inthinc.pro.dao.HOSDAO;
import com.inthinc.pro.map.GoogleAddressLookup;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.NoAddressFoundException;
import com.inthinc.pro.model.hos.HOSRecord;

public class LocateVehicleByTime {
    
    private GoogleAddressLookup googleAddressLookupBean;
    private HOSDAO hosDAO;
    
    public String getNearestCity(Integer vehicleID, Date date){
        
    	if(vehicleID==null) return "";
    	
        List<HOSRecord> hosRecordsByDuration  = getHOSRecordsSortedByDurationFromDate(vehicleID, date);
        
        if(hosRecordsByDuration==null) return "";
        
        String nearestCity = lookupAddress(hosRecordsByDuration);
        
        return nearestCity;
    }
    private List<HOSRecord> getHOSRecordsSortedByDurationFromDate(Integer vehicleID, Date date) {
    	
        Interval interval = new Interval(new DateTime(date).minusDays(1),new DateTime(date).plusDays(1));
        List<HOSRecord> hosRecords = hosDAO.getRecordsForVehicle(vehicleID, interval, false);
        if(hosRecords.isEmpty()) return null;
        
        hosRecords = removeNullLatLngs(hosRecords);
        if(hosRecords.isEmpty()) return null;

        return sortHOSRecordsByDurationFromDate(hosRecords, date);
    }
    private List<HOSRecord> removeNullLatLngs(List<HOSRecord> hosRecords){
        Iterator<HOSRecord> it = hosRecords.iterator();
        while (it.hasNext()){
            HOSRecord hos = it.next();
            if ((hos.getLat() == null) || (hos.getLng() == null)){
                it.remove();
            }
        }
        return hosRecords;
    }
    private List<HOSRecord> sortHOSRecordsByDurationFromDate(List<HOSRecord> hosRecords,  Date date){
    	
    	 Map<Duration,List<HOSRecord>> hosRecordsByDuration = calculateDurations(hosRecords, date);
         List<Duration> sortedDurations= getSortedDurations(hosRecordsByDuration.keySet());
         List<HOSRecord> sortedHOSRecords = getHOSRecordsInDurationOrder(hosRecordsByDuration,sortedDurations);
         
         return sortedHOSRecords;
    }
    private List<HOSRecord> getHOSRecordsInDurationOrder( Map<Duration,List<HOSRecord>> hosRecordsByDuration,List<Duration> sortedDurationKeys){
    	List<HOSRecord> sortedList = new ArrayList<HOSRecord>();
        for(Duration duration : sortedDurationKeys){
            sortedList.addAll(hosRecordsByDuration.get(duration));
        }
        return sortedList;
    }
    
    private Map<Duration,List<HOSRecord>> calculateDurations(List<HOSRecord> hosRecords,Date date){
        Map<Duration,List<HOSRecord>> hosRecordsByDuration = new HashMap<Duration,List<HOSRecord>>();
        for(HOSRecord hosRecord : hosRecords){
            Duration duration = getAbsoluteDuration(hosRecord.getLogTime(), date);
            addHOSRecord(hosRecordsByDuration,duration,hosRecord);
        }
        return hosRecordsByDuration;
    }
    private void addHOSRecord(Map<Duration,List<HOSRecord>> hosRecordsByDuration,Duration duration, HOSRecord hosRecord){
        List<HOSRecord> hosRecords = hosRecordsByDuration.get(duration);
        if (hosRecords == null){
            hosRecords = new ArrayList<HOSRecord>();
            hosRecordsByDuration.put(duration, hosRecords);
        }
        hosRecords.add(hosRecord);
    }
    private Duration getAbsoluteDuration(Date hosLogTime, Date date){
        if(hosLogTime.before(date) || hosLogTime.equals(date)){
            return new Duration(new DateTime(hosLogTime),new DateTime(date));
        }
        else{
            return new Duration(new DateTime(date),new DateTime(hosLogTime));
        }
    }
    @SuppressWarnings("unchecked")
    private List<Duration> getSortedDurations(Set<Duration> durationSet){
        List<Duration> durations = new ArrayList<Duration>(durationSet);
        Collections.sort(durations);
        return durations;
    }
    private String lookupAddress(List<HOSRecord> hosRecordsByDuration){
        for(HOSRecord hosRecord : hosRecordsByDuration){
        	if (hosRecord.getLocation() != null){
        		return hosRecord.getLocation();
        	}
            try {
                String address = googleAddressLookupBean.getClosestTownString(new LatLng(hosRecord.getLat(),hosRecord.getLng()), MeasurementType.ENGLISH);
                return address;
            } 
            catch (NoAddressFoundException e) {
                // try again
            }
        }
        return null;
    }
    
    public void setGoogleAddressLookupBean(GoogleAddressLookup googleAddressLookupBean) {
        this.googleAddressLookupBean = googleAddressLookupBean;
    }
    public void setHosDAO(HOSDAO hosDAO) {
        this.hosDAO = hosDAO;
    }
}
