package com.inthinc.pro.automation.device_emulation.tiwiPro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.device_emulation.tiwiPro.enums.TiwiAttrs;
import com.inthinc.pro.automation.device_emulation.tiwiPro.enums.TiwiNoteTypes;
import com.inthinc.pro.automation.device_emulation.tiwiPro.enums.TiwiGenerals.FwdCmdStatus;

//import com.inthinc.QA.util.QALogger;//TODO: jwimmer: dtanner: commented to get this to compile
//import com.inthinc.QA.util.StackToString;

public class Package_tiwiPro_Note {
	private final static Logger logger = Logger.getLogger(Package_tiwiPro_Note.class);
    
    private Integer nType, sats, heading, maprev, Speed, odometer, nTime;
    private Double lat, lng;
    private HashMap<Integer, Integer> attrs;
    
    
    public Package_tiwiPro_Note(TiwiNoteTypes type, String time, int Sats, int Heading, int Maprev, Double Lat, Double Lng, int speed, int Odometer, HashMap<TiwiAttrs, Integer> Attrs){
        nType = type.getCode();
        nTime = (int)DateToLong(time);
        sats = Sats;
        heading = Heading;
        maprev = Maprev;
        lat = Lat;
        lng = Lng;
        Speed = speed;
        odometer = Odometer;
        processAttrs(Attrs);
    }
    
    
    public Package_tiwiPro_Note(TiwiNoteTypes type, long time, int Sats, int Heading, int Maprev, Double Lat, Double Lng, int speed, int Odometer, HashMap<TiwiAttrs, Integer> Attrs){
        nType = type.getCode();
        if ( time > (System.currentTimeMillis()/100 )) nTime = (int)( time/1000 );
        else{ nTime = (int)time; }
        sats = Sats;
        heading = Heading;
        maprev = Maprev;
        lat = Lat;
        lng = Lng;
        Speed = speed;
        odometer = Odometer;
        processAttrs(Attrs);
    }
    
    public Package_tiwiPro_Note( TiwiNoteTypes type, HashMap<TiwiAttrs, Integer> Attrs ){
        nType = type.getCode();
        nTime = (int)(System.currentTimeMillis()/1000);
        sats = 0;
        heading = 0;
        maprev = 0;
        lat = 0.0;
        lng = 0.0;
        Speed = 0;
        odometer = 0;
        processAttrs(Attrs);
    }
    
    public Package_tiwiPro_Note( TiwiNoteTypes type ){
        nType = type.getCode();
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
    
    public Package_tiwiPro_Note(){
        nType = TiwiNoteTypes.NOTE_TYPE_LOCATION.getCode();
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
    
    

	public void AddAttrs(HashMap<TiwiAttrs, Integer> Attrs){
    	processAttrs(Attrs);
    }
    public void AddAttrs(FwdCmdStatus cmd, Integer reply){
    	attrs.put(cmd.getCode(), reply);
    }
    public void AddAttrs(TiwiAttrs cmd, Integer reply){
    	attrs.put(cmd.getCode(), reply);
    }
    public void AddAttrs(TiwiAttrs cmd, Object reply){
    	attrs.put(cmd.getCode(), (Integer)reply);
    }
    public void AddAttrs(TiwiAttrs cmd, FwdCmdStatus reply) {
    	attrs.put(cmd.getCode(), reply.getCode());
    }
    
    public void AddAttrs(TiwiAttrs cmd, TiwiAttrs reply){
    	HashMap<TiwiAttrs, Integer> Attrs = new HashMap<TiwiAttrs, Integer>();
    	Attrs.put(cmd, reply.getCode());
    	processAttrs(Attrs);
    }
        
        
    public long DateToLong( String datetime ){
        long epoch_time = (int)(System.currentTimeMillis()/1000);
        
        try {
            Date date ; 
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(datetime); 
            epoch_time = date.getTime();
            
          }
          catch (ParseException e){
            //logger.debug(StackToString.toString(e));//TODO: jwimmer: dtanner: commented to get this to compile
        	e.printStackTrace();
          }
        
        return epoch_time;
    }
    
    public String noteToString(){
    	String note = "";
    	try{
    		note = String.format("DeviceNote(nType=%d, nTime=\"%s\", sats=%d, heading=%d, maprev=%d, lat=%.5f, lng=%.5f, speed=%d, odometer=%d)", nType, nTime, sats, heading, maprev, lat, lng, Speed, odometer);
    	}catch(Exception e){
    		//logger.debug(StackToString.toString(e));//TODO: jwimmer: dtanner: commented to get this to compile
    		e.printStackTrace();
    	}
    	logger.debug(note);
    	return note;
    }
    
    public void processAttrs(HashMap<TiwiAttrs, Integer> Attrs){
    	attrs = new HashMap<Integer, Integer>();
    	TiwiAttrs attrID;
    	Iterator<TiwiAttrs> itr = Attrs.keySet().iterator();
    	while (itr.hasNext()){
    		attrID = itr.next();
    		attrs.put(attrID.getCode(), Attrs.get(attrID));
    	}
    }
   
    
    public byte[] Package(){
    	logger.debug(String.format("DeviceNote(nType=%d, nTime=\"%s\", " +
    			"sats=%d, heading=%d, maprev=%d, lat=%.5f, lng=%.5f, speed=%d, odometer=%d)",
    			nType, nTime, sats, heading, maprev, lat, lng, Speed, odometer));
    	
        
        Iterator<Integer> keys = attrs.keySet().iterator();
        
        if (lng < 0.0 ){
            lng += 360.0;
        }
       
        byte[] eventBytes = new byte[200];
        
        double latitude = ( 90.0 - lat ) / 180.0;
        double longitude = ( lng / 360.0 );
        
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
    
