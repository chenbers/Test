package com.inthinc.pro.device.tiwiPro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class Package_Note {
    
    private static Integer nType, sats, heading, maprev, Speed, odometer, nTime;
    private static Double lat, lng;
    private static HashMap<Integer, Integer> attrs;
    
    
    public Package_Note(Integer type,String time, Integer Sats, Integer Heading, Integer Maprev, Double Lat, Double Lng, Integer speed, Integer Odometer, HashMap<Integer, Integer> Attrs){
        nType = type;
        nTime = (int)DateToLong(time);
        sats = Sats;
        heading = Heading;
        maprev = Maprev;
        lat = Lat;
        lng = Lng;
        Speed = speed;
        odometer = Odometer;
        attrs = Attrs;
        
    }
    
    public Package_Note(Integer type, Integer time, Integer Sats, Integer Heading, Integer Maprev, Double Lat, Double Lng, Integer speed, Integer Odometer, HashMap<Integer, Integer> Attrs){
        nType = type;
        if ( time > (int)(System.currentTimeMillis()/100 )) nTime = (int)( time/1000 );
        else{ nTime = time; }
        sats = Sats;
        heading = Heading;
        maprev = Maprev;
        lat = Lat;
        lng = Lng;
        Speed = speed;
        odometer = Odometer;
        attrs = Attrs;
        
    }
    
    public Package_Note( Integer type, HashMap<Integer, Integer> Attrs ){
        nType = type;
        nTime = (int)(System.currentTimeMillis()/1000);
        sats = 0;
        heading = 0;
        maprev = 0;
        lat = 0.0;
        lng = 0.0;
        Speed = 0;
        odometer = 0;
        attrs = Attrs;
        
    }
    
    public Package_Note(){
        nType = Constants.NOTE_TYPE_LOCATION.getCode();
        nTime = (int)(System.currentTimeMillis()/1000);
        sats = 0;
        heading = 0;
        maprev = 0;
        lat = 0.0;
        lng = 0.0;
        Speed = 0;
        odometer = 0;
        attrs = new HashMap<Integer, Integer>();
        
        
    }
        
        
    public long DateToLong( String datetime ){
        long epoch_time = (int)(System.currentTimeMillis()/1000);
        
        try {
            Date date ; 
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(datetime); 
            epoch_time = date.getTime();
            
          }
          catch (ParseException e){
            System.out.println("Exception :"+e);   
          }
        
        return epoch_time;
        
    }
    
    public String noteToString(){
    	String note = "";
    	try{
    		note = String.format("DeviceNote(nType=%d, nTime=\"%s\", sats=%d, heading=%d, maprev=%d, lat=%.5f, lng=%.5f, speed=%d, odometer=%d)", nType, nTime, sats, heading, maprev, lat, lng, Speed, odometer);
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    	return note;
    }
    
    
    public byte[] Package(){
        
        Iterator<Integer> keys = attrs.keySet().iterator();
        
        if (lng < 0.0 ){
            lng += 360.0;
        }
       
        byte[] eventBytes = new byte[200];
        
        double latitude = ( 90.0 - lat ) / 180.0;
        double longitude = (int)( lng / 360.0 );
        
        int idx   = 0;
        int val1  = (int)( latitude  * 0x00FFFFFF );
        int val2  = (int)( longitude * 0x00FFFFFF );
        int flags = (int)(  heading << 4)  & 0xF0 | sats & 0x0F;
        
        //Headers  Convert the value to an integer, then pack it as a byte in the array
        eventBytes[idx++] = (byte)(  nType          & 0xFF );
        eventBytes[idx++] = (byte)(( nTime >> 24 )  & 0xFF );
        eventBytes[idx++] = (byte)(( nTime >> 16 )  & 0xFF );
        eventBytes[idx++] = (byte)(( nTime >>  8 )  & 0xFF );
        eventBytes[idx++] = (byte)(  nTime          & 0xFF );
        eventBytes[idx++] = (byte) flags;
        eventBytes[idx++] = (byte)(  maprev         & 0xFF );
        eventBytes[idx++] = (byte)(( val1 >> 16 )   & 0xFF );
        eventBytes[idx++] = (byte)(( val1 >>  8 )   & 0xFF );
        eventBytes[idx++] = (byte)(  val1           & 0xFF );
        eventBytes[idx++] = (byte)(( val2 >> 16 )   & 0xFF );
        eventBytes[idx++] = (byte)(( val2 >>  8 )   & 0xFF );
        eventBytes[idx++] = (byte)(  val2           & 0xFF );
        eventBytes[idx++] = (byte)(  Speed          & 0xFF );
        eventBytes[idx++] = (byte)(( odometer >> 8) & 0xFF );
        eventBytes[idx++] = (byte)(  odometer       & 0xFF );
        
        
        while( keys.hasNext()){
    
            int key = keys.next();
            int value = attrs.get(key);
            
            if ( key < 128 ){
                eventBytes[idx++] = (byte)(  key   & 0xFF );
                eventBytes[idx++] = (byte)(  value & 0xFF );                
            }
            
            else if ( key < 192 && key >= 128 ){
                eventBytes[idx++] = (byte)(   key           & 0xFF );
                eventBytes[idx++] = (byte)( ( value >>  8 ) & 0xFF );
                eventBytes[idx++] = (byte)(   value         & 0xFF );                
            }
            
            else if ( key < 255 && key >= 192 ){
                eventBytes[idx++] = (byte)(   key           & 0xFF );
                eventBytes[idx++] = (byte)( ( value >> 24 ) & 0xFF );
                eventBytes[idx++] = (byte)( ( value >> 16 ) & 0xFF );
                eventBytes[idx++] = (byte)( ( value >>  8 ) & 0xFF );
                eventBytes[idx++] = (byte)(   value         & 0xFF );
            }
            
        }
        
        return eventBytes;   
    }   
}
    
