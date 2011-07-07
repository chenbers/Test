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
        Interval interval = new Interval(new DateTime(date).minusHours(1),new DateTime(date).plusHours(1));
        List<HOSRecord> hosRecords  = getLatLngs(vehicleID, date, interval);
        
        if(hosRecords==null) return "";
        
        String nearestCity = getAddress(hosRecords, date);
        
        return nearestCity;
    }
    public List<HOSRecord> getLatLngs(Integer vehicleID, Date date, Interval interval) {
        
        List<HOSRecord> hosRecords = hosDAO.getRecordsForVehicle(vehicleID, interval, false);
        if(hosRecords.isEmpty()) return null;
        
        hosRecords = removeNullLatLngs(hosRecords);
        if(hosRecords.isEmpty()) return null;
        
        return hosRecords;
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
    
    private String getAddress(List<HOSRecord> hosRecords, Date date){
        Map<Duration,List<LatLng>> latLngsByDuration = mapHosRecords(hosRecords, date);
        List<Duration> durationKeys= getSortedLatLngs(latLngsByDuration);
        String address = lookupAddress(latLngsByDuration,durationKeys);
        return address;
    }
    
    private Map<Duration,List<LatLng>> mapHosRecords(List<HOSRecord> hosRecords,Date date){
        Map<Duration,List<LatLng>> latLngsByDuration = new HashMap<Duration,List<LatLng>>();
        for(HOSRecord hosRecord : hosRecords){
            Duration duration = getAbsoluteDuration(hosRecord.getLogTime(), date);
            addMapRecord(latLngsByDuration,duration,new LatLng(hosRecord.getLat(),hosRecord.getLng()));
        }
        return latLngsByDuration;
    }
    private void addMapRecord(Map<Duration,List<LatLng>> latLngsByDuration,Duration duration, LatLng latLng){
        List<LatLng> latLngs = latLngsByDuration.get(duration);
        if (latLngs == null){
            latLngs = new ArrayList<LatLng>();
            latLngsByDuration.put(duration, latLngs);
        }
        latLngs.add(latLng);
    }
    private Duration getAbsoluteDuration(Date hosLogTime, Date date){
        if(hosLogTime.before(date) ||hosLogTime.equals(date)){
            return new Duration(new DateTime(hosLogTime),new DateTime(date));
        }
        else{
            return new Duration(new DateTime(date),new DateTime(hosLogTime));
        }
        
    }
    @SuppressWarnings("unchecked")
    private List<Duration> getSortedLatLngs(Map<Duration,List<LatLng>> latLngsByDuration){
        List<Duration> durations = new ArrayList<Duration>(latLngsByDuration.keySet());
        Collections.sort(durations);
        return durations;
    }
    private String lookupAddress(Map<Duration,List<LatLng>> latLngsByDuration,List<Duration> sortedDurationKeys){
        for(Duration duration : sortedDurationKeys){
            List<LatLng> latLngs = latLngsByDuration.get(duration);
            for(LatLng latLng : latLngs){
                try {
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
