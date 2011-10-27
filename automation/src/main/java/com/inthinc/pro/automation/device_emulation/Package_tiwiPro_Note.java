package com.inthinc.pro.automation.device_emulation;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.deviceEnums.TiwiNoteTypes;
import com.inthinc.pro.automation.interfaces.DeviceTypes;
import com.inthinc.pro.automation.interfaces.NoteBuilder;
import com.inthinc.pro.automation.utils.AutomationCalendar;
import com.inthinc.pro.automation.utils.AutomationNumberManager;
import com.inthinc.pro.automation.utils.StackToString;


public class Package_tiwiPro_Note implements NoteBuilder{
	private final static Logger logger = Logger.getLogger(Package_tiwiPro_Note.class);
    
    private final Integer sats, heading, maprev, Speed, odometer;
    private final AutomationCalendar nTime;
    private final Double lat, lng;
    private final HashMap<DeviceTypes, Integer> attrs;
    private final TiwiNoteTypes nType;
    
    
    public Package_tiwiPro_Note(TiwiNoteTypes type, AutomationCalendar time, int sats, int Heading, int Maprev, Double Lat, Double Lng, int speed, int Odometer){
        nType = type;
        nTime = time;
        this.sats = sats;
        this.heading = Heading;
        this.maprev = Maprev;
        this.lat = Lat;
        this.lng = Lng;
        this.Speed = speed;
        this.odometer = Odometer;
        attrs = new HashMap<DeviceTypes, Integer>();
    }
    
    
    
    public Package_tiwiPro_Note( TiwiNoteTypes type ){
        nType = type;
        nTime = new AutomationCalendar();
        sats = 0;
        heading = 0;
        maprev = 0;
        lat = 0.0;
        lng = 0.0;
        Speed = 0;
        odometer = 0;
        attrs = new HashMap<DeviceTypes, Integer>();
    }
    
    public Package_tiwiPro_Note(){
        this(TiwiNoteTypes.NOTE_TYPE_LOCATION);
    }

    public void AddAttrs(HashMap<? extends DeviceTypes, Integer> Attrs){
    	addAttrs(Attrs);
    }
    
    public void addAttr(DeviceTypes id, Object value){
        int cast;
        if (value instanceof Integer){
            cast = (Integer) value;
        } else if (value instanceof DeviceTypes){
            cast = ((DeviceTypes) value).getValue();
        } else {
            throw new IllegalArgumentException("Cannot add value of type: " + value.getClass());
        }
        attrs.put(id, cast);
    }
    
        
    public long DateToLong( String datetime ){
        long epoch_time = (int)(System.currentTimeMillis()/1000);
        
        try {
            Date date ; 
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(datetime); 
            epoch_time = date.getTime();
            
          }
          catch (ParseException e){
            logger.debug(StackToString.toString(e));
        	e.printStackTrace();
          }
        
        return epoch_time;
    }
    
    public String noteToString(){
    	String note = "";
    	try{
    		note = String.format("DeviceNote(nType=%d, nTime=\"%s\", sats=%d, heading=%d, maprev=%d, lat=%.5f, lng=%.5f, speed=%d, odometer=%d)", nType, nTime, sats, heading, maprev, lat, lng, Speed, odometer);
    	}catch(Exception e){
    		logger.debug(StackToString.toString(e));
    		e.printStackTrace();
    	}
    	logger.debug(note);
    	return note;
    }
    
    public void addAttrs(Map<? extends DeviceTypes, Integer> Attrs){
    	DeviceTypes attrID;
    	Iterator<? extends DeviceTypes> itr = Attrs.keySet().iterator();
    	while (itr.hasNext()){
    		attrID = itr.next();
    		attrs.put(attrID, Attrs.get(attrID));
    	}
    }
   
    
    public byte[] Package(){
    	logger.debug(String.format("DeviceNote(nType=%d, nTime=%s, " +
    			"sats=%d, heading=%d, maprev=%d, lat=%.5f, lng=%.5f, speed=%d, odometer=%d, attrs={ %s })",
    			nType, nTime, sats, heading, maprev, lat, lng, Speed, odometer, attrs));
    	
    	ByteArrayOutputStream bos = new ByteArrayOutputStream();
        
        //Headers  Convert the value to an integer, then pack it as a byte in the stream
        AutomationNumberManager.intToByte(bos, nType.getValue(), 1);
        AutomationNumberManager.intToByte(bos, nTime.toInt(), 4);
        AutomationNumberManager.intToByte(bos, AutomationNumberManager.concatenateTwoInts(heading, sats), 1);
        AutomationNumberManager.intToByte(bos, maprev, 1);
        AutomationNumberManager.intToByte(bos, AutomationNumberManager.encodeLat(lat), 3);
        AutomationNumberManager.intToByte(bos, AutomationNumberManager.encodeLng(lng), 3);
        AutomationNumberManager.intToByte(bos, odometer, 2);
        
        NoteManager.encodeAttributes(bos, attrs);
        
        return bos.toByteArray();   
    }

    @Override
    public String sendNote() {
        throw new IllegalAccessError("This method is only for Waysmart Notes");
    }   
}
    
