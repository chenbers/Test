package com.inthinc.pro.service.note;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;


public class CrashData {
    
    private Date date;
    private Double lat;
    private Double lng;
    private Integer gpsSpeed;
    private Integer odbSpeed;
    private Integer rpm;
       
    public CrashData(Date date, Double lat, Double lng, Integer gpsSpeed, Integer odbSpeed, Integer rpm) {
        super();
        this.date = date;
        this.lat = lat;
        this.lng = lng;
        this.gpsSpeed = gpsSpeed;
        this.odbSpeed = odbSpeed;
        this.rpm = rpm;
    }
    
    public byte[] getBytes(){
        byte[] byteArray = new byte[17];
        int idx = 0;
        idx = puti4(byteArray, idx, (int)(date.getTime()/1000l));
        idx = putBinary(byteArray,encodeLatLngVerbose(lat, lng),idx);
        idx = puti2(byteArray, idx, rpm);
        byteArray[idx++] = (byte) (odbSpeed & 0x000000FF);
        byteArray[idx++] = (byte) (gpsSpeed & 0x000000FF);
        
        while(idx < byteArray.length){
            byteArray[idx++] = (byte) (0 & 0x000000FF);
        }
        return byteArray;
    }

    private int puti4(byte[] byteArray, int idx, Integer i) {
        byteArray[idx++] = (byte) ((i >> 24) & 0x000000FF);
        byteArray[idx++] = (byte) ((i >> 16) & 0x000000FF);
        byteArray[idx++] = (byte) ((i >> 8) & 0x000000FF);
        byteArray[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }

    private int puti2(byte[] eventBytes, int idx, Integer i) {
        eventBytes[idx++] = (byte) ((i >> 8) & 0x000000FF);
        eventBytes[idx++] = (byte) (i & 0x000000FF);
        return idx;
    }
    
    private int putBinary(byte[] bytes,byte[] data, int idx){
        for(int i = 0;i < data.length;i++){
            bytes[idx++] = data[i];
        }        
        return idx;
    }
    
    private byte[] encodeLatLngVerbose(double lat,double lng){
        byte[] latLngArray = new byte[8];
        long position_encoded;
        double position;
        
        if(lng < 0.0){
            position = (lng + 360.0)/360.0;
        } else {
            position = lng / 360.0;
        }

        position_encoded = 
            new BigDecimal(new BigInteger("FFFFFFFF",16)).multiply(new BigDecimal(position)).toBigInteger().and(new BigInteger("FFFFFFFF",16)).longValue();
        latLngArray[4] = (byte) ((position_encoded & 0xFF000000) >> 24);
        latLngArray[5] = (byte) ((position_encoded & 0x00FF0000) >> 16);
        latLngArray[6] = (byte) ((position_encoded & 0x0000FF00) >> 8);
        latLngArray[7] = (byte) (position_encoded & 0x000000FF);
        
        position = -(lat - 90)/180;
        position_encoded = 
            new BigDecimal(new BigInteger("FFFFFFFF",16)).multiply(new BigDecimal(position)).toBigInteger().and(new BigInteger("FFFFFFFF",16)).longValue();
        latLngArray[0] = (byte) ((position_encoded & 0xFF000000) >> 24);
        latLngArray[1] = (byte) ((position_encoded & 0x00FF0000) >> 16);
        latLngArray[2] = (byte) ((position_encoded & 0x0000FF00) >> 8);
        latLngArray[3] = (byte) new BigInteger("000000FF",16).and(new BigInteger(Long.valueOf(position_encoded).toString())).byteValue();
        
        return latLngArray;
    }

    public Date getDate() {
        return date;
    }
    
    public void setDate(Date date) {
        this.date = date;
    }
    
    public Double getLat() {
        return lat;
    }
    
    public void setLat(Double lat) {
        this.lat = lat;
    }
    
    public Double getLng() {
        return lng;
    }
    
    public void setLng(Double lng) {
        this.lng = lng;
    }
    
    public Integer getGpsSpeed() {
        return gpsSpeed;
    }
    
    public void setGpsSpeed(Integer gpsSpeed) {
        this.gpsSpeed = gpsSpeed;
    }
    
    public Integer getOdbSpeed() {
        return odbSpeed;
    }
    
    public void setOdbSpeed(Integer odbSpeed) {
        this.odbSpeed = odbSpeed;
    }
    
    
    
}
