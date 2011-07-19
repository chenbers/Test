package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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
        
        Interval interval = new Interval(new DateTime(date).minusDays(1),new DateTime(date).plusDays(1));
        Map<Duration,List<HOSRecord>> hosRecordsByDuration  = getHOSRecords(vehicleID, date, interval);
        
        if(hosRecordsByDuration.isEmpty()) return "";
        
        String nearestCity = getAddress(hosRecordsByDuration, date);
        
        return nearestCity;
    }
    public Map<Duration,List<HOSRecord>> getHOSRecords(Integer vehicleID, Date date, Interval interval) {
        
        List<HOSRecord> hosRecords = hosDAO.getRecordsForVehicle(vehicleID, interval, false);
        if(hosRecords.isEmpty()) return null;
        
        hosRecords = removeNullLatLngs(hosRecords);
        if(hosRecords.isEmpty()) return null;

        Map<Duration,List<HOSRecord>> hosRecordsByDuration = mapHosRecords(hosRecords, date);

        return hosRecordsByDuration;
    }
    private List<HOSRecord> removeNullLatLngs(List<HOSRecord> hosRecords){
        Iterator<HOSRecord> it = hosRecords.iterator();
        while (it.hasNext()){
            HOSRecord hos = it.next();
            if (nullLatLng(hos.getLat(), hos.getLng())){
                it.remove();
            }
        }
        
        return hosRecords;
    }
    private boolean nullLatLng(Float lat, Float lng){
        return (lat == null) || (lng == null);
    }
    
    private String getAddress(Map<Duration,List<HOSRecord>> hosRecordsByDuration, Date date){
        List<Duration> durationKeys= getSortedHosRecords(hosRecordsByDuration);
        String address = lookupAddress(hosRecordsByDuration,durationKeys);

        return address;
    }
    
    private Map<Duration,List<HOSRecord>> mapHosRecords(List<HOSRecord> hosRecords,Date date){
        Map<Duration,List<HOSRecord>> hosRecordsByDuration = new HashMap<Duration,List<HOSRecord>>();
        for(HOSRecord hosRecord : hosRecords){
            Duration duration = getAbsoluteDuration(hosRecord.getLogTime(), date);
            addMapRecord(hosRecordsByDuration,duration,hosRecord);
        }
        return hosRecordsByDuration;
    }
    private void addMapRecord(Map<Duration,List<HOSRecord>> hosRecordsByDuration,Duration duration, HOSRecord hosRecord){
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
    private List<Duration> getSortedHosRecords(Map<Duration,List<HOSRecord>> latLngsByDuration){
        List<Duration> durations = new ArrayList<Duration>(latLngsByDuration.keySet());
        Collections.sort(durations);
        return durations;
    }
    private String lookupAddress(Map<Duration,List<HOSRecord>> hosRecordsByDuration,List<Duration> sortedDurationKeys){
        for(Duration duration : sortedDurationKeys){
            List<HOSRecord> hosRecords = hosRecordsByDuration.get(duration);
            for(HOSRecord hosRecord : hosRecords){
            	if (hosRecord.getLocation() != null){
            		return hosRecord.getLocation();
            	}
                try {
                	LatLng latLng = new LatLng(hosRecord.getLat(),hosRecord.getLng());
                    String address = googleAddressLookupBean.getClosestTownString(latLng, MeasurementType.ENGLISH);
                    return address;
                } 
                catch (NoAddressFoundException e) {
                    // try again
                }
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
