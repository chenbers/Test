package com.inthinc.pro.automation.device_emulation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.interfaces.DeviceTypes;
import com.inthinc.pro.automation.models.DeviceAttributes;
import com.inthinc.pro.automation.utils.StackToString;

public class NoteManager {
    
    private final static Logger logger = Logger.getLogger(NoteManager.class);
    private Queue<DeviceNote> notes;
    
    public static enum AttrTypes{
        BYTE(1),
        SHORT(2),
        STRING,
        INT(4),
        DOUBLE(8),
        BINARY,
        
        ;
        private Integer bytes;
        
        private AttrTypes(){}
        private AttrTypes(int bytes){
            this.bytes = bytes;
        }
        
        public boolean hasSize(){
            return bytes != null;
        }
        
        public Integer getSize(){
            return bytes;
        }
        
        
        public static AttrTypes getType(DeviceTypes id){
            AttrTypes temp;
            int key = id.getValue();
            if ( key < 128 ){
                temp = BYTE;
            } else if ( key < 192 && key >= 128 ){
                temp = SHORT;
            } else if ( key < 255 && key >= 192 ){
                temp = INT;
            } else if ( key < 16384 && key >= 8192){
                temp = BYTE;
            } else if ( key < 24576 && key >= 16384){
                temp = SHORT;
            } else if ( key < 32768 && key >= 24576 ){
                temp = STRING;
            } else if ( key < 40960 && key >= 32768){
                temp = INT;
            } else if ( key < 49152 && key >= 40960){
                temp = DOUBLE;
            } else if ( key < 49153 && key >= 49152){
                temp = BINARY;
            } else {
                throw new IllegalArgumentException("The key " + key + " doesn't exist!!!");
            }
            return temp;
        }
        
    }
    
    
    public NoteManager(){
        notes = new LinkedList<DeviceNote>();
    }
    
    public boolean addNote(DeviceNote note){
        return notes.add(note);
    }
    
    public Queue<DeviceNote> getNotes(){
        return notes;
    }
    
    public int size(){
        return notes.size();
    }
    
    public boolean hasNext(){
        return !notes.isEmpty();
    }
    
    public Map<Class<? extends DeviceNote>, LinkedList<byte[]>> getNotes(int queueSize){
        Map<Class<? extends DeviceNote>, LinkedList<byte[]>> temp = new HashMap<Class<? extends DeviceNote>, LinkedList<byte[]>>();
        for (int i = 0; i < queueSize && hasNext(); i++) {
            DeviceNote temp2 = notes.poll(); 
            byte[] array = temp2.Package();
            if (!temp.containsKey(temp2.getClass())){
                temp.put(temp2.getClass(), new LinkedList<byte[]>());
            }
            temp.get(temp2.getClass()).offer(array);
            
        }
        return temp;
    }
    
    public interface DeviceNote{
        public byte[] Package();
    }
    
    
    public static Integer byteToInt(ByteArrayInputStream bais, int numOfBytes) {
        return byteToLong(bais, numOfBytes).intValue();
    }

    public static Long byteToLong(ByteArrayInputStream bais, int numOfBytes) {
        Long number = 0l;
        for (int shift = 0; numOfBytes-- > 0 && shift < 64; shift += 8) {
            number |= (bais.read() & 0xFF) << shift;
        }
        return number;
    }
    
    public static void longToByte(ByteArrayOutputStream baos, Long toAdd, int numOfBytes){
        int size = Byte.SIZE;
        int offset = numOfBytes * size - size;
        for (;offset>=0;offset-=size){
            Long temp = (toAdd >> offset) & 0xFF;
            baos.write(temp.intValue());
        }
    }
    
    public static byte longToByte(Long toAdd){
        Long temp = toAdd & 0xFF;
        return (byte) temp.intValue();
    }
    
    public static void longToByte(ByteArrayOutputStream baos, Integer toAdd, int numOfBytes){
        longToByte(baos, toAdd.longValue(), numOfBytes);
    }
    public static int encodeLat(double lat){
        double latitude = ( 90.0 - lat ) / 180.0;
        int val  = (int)( latitude  * 0x00FFFFFF );
        return val;
    }
    
    public static int encodeLng(double lng){
        if (lng < 0.0 ){
            lng += 360.0;
        }
        double longitude = ( lng / 360.0 );
        int val = (int)( longitude * 0x00FFFFFF );
        return val;
    }
    
    public static int concatenateTwoInts(int one, int two){
        int value = (int)(  one << 4)  & 0xF0 | two & 0x0F;
        return value;
    }

    
    
    /**
     * @param baos
     * @param attrs 
     */
    public static void encodeAttributes(ByteArrayOutputStream baos, DeviceAttributes attrs) {
        Iterator<? extends DeviceTypes> keys = attrs.iterator();
        
        while( keys.hasNext()){
            DeviceTypes key=null;
            int size=0;
            
            try {
                key = keys.next();
                size = attrs.getSize(key);
                longToByte(baos, key.getValue().longValue(), 1);
                
                Object object = attrs.getValue(key);
                if (object instanceof Number){
                    if (object instanceof Integer){
                        longToByte(baos, ((Integer)object).longValue(), size);
                    } else if (object instanceof Long){
                        longToByte(baos, (Long)object, size);    
                    }
                } else if (object instanceof String){
                    baos.write(((String)object).getBytes());
                }
            } catch (NullPointerException e){
                logger.info("Key: " + key + " had no value attached to it");
                logger.error(StackToString.toString(e));
                continue;
            } catch (IOException e) {
                logger.info("Key: " + key + " had an error when trying to add the string");
                continue;
            }
            
        }
    }
    

}
