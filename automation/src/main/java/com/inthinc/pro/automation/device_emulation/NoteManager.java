package com.inthinc.pro.automation.device_emulation;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.interfaces.DeviceTypes;

public class NoteManager {
    
    private final static Logger logger = Logger.getLogger(NoteManager.class);
    
    public static enum AttrTypes{
        TIWI,
        BYTE,
        SHORT,
        STRING,
        INT,
        DOUBLE,
        BINARY,
        
    }
    
    
    /**
     * @param baos
     * @param attrs2 
     */
    public static void encodeAttributes(ByteArrayOutputStream baos, Map<? extends DeviceTypes, Integer> attrs2) {
        Iterator<? extends DeviceTypes> keys = attrs2.keySet().iterator();
        
        while( keys.hasNext()){
            int key=0;
            int value=0;
            try {

                key = keys.next().getValue();
                value = attrs2.get(key);
            } catch (NullPointerException e){
                logger.info("Key: " + key);
                continue;
            }
            
            if ( key < 128 ){
                baos.write( key   & 0xFF );
                baos.write( value & 0xFF );                
            }
            
            else if ( key < 192 && key >= 128 ){
                baos.write(  key           & 0xFF );
                baos.write(( value >>  8 ) & 0xFF );
                baos.write(  value         & 0xFF );                
            }
            
            else if ( key < 255 && key >= 192 ){
                baos.write(  key           & 0xFF );
                baos.write(( value >> 24 ) & 0xFF );
                baos.write(( value >> 16 ) & 0xFF );
                baos.write(( value >>  8 ) & 0xFF );
                baos.write(  value         & 0xFF );
            }
        }
    }
    

}
