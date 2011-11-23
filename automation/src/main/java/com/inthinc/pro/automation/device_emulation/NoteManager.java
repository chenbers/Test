package com.inthinc.pro.automation.device_emulation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.deviceEnums.DeviceAttrs;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.models.DeviceAttributes;
import com.inthinc.pro.automation.utils.StackToString;

public class NoteManager {
    
    private final static Logger logger = Logger.getLogger(NoteManager.class);
    private Queue<DeviceNote> notes;
    
    
    
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
    
    public Map<Class<? extends DeviceNote>, LinkedList<DeviceNote>> getNotes(int queueSize){
        Map<Class<? extends DeviceNote>, LinkedList<DeviceNote>> noteList = new HashMap<Class<? extends DeviceNote>, LinkedList<DeviceNote>>();
        for (int i = 0; i < queueSize && hasNext(); i++) {
            DeviceNote note = notes.poll(); 
            if (!noteList.containsKey(note.getClass())){
                noteList.put(note.getClass(), new LinkedList<DeviceNote>());
            }
            noteList.get(note.getClass()).offer(note);
        }
        return noteList;
    }
    
    public interface DeviceNote{
        public byte[] Package();
        public DeviceNoteTypes getType();
        public Long getTime();
    }
    
    
    public static Integer byteToInt(ByteArrayInputStream bais, int numOfBytes) {
        return byteToLong(bais, numOfBytes).intValue();
    }

    public static Long byteToLong(ByteArrayInputStream bais, int numOfBytes) {
        Long number = 0l;
        for (int shift = (numOfBytes-1) * Byte.SIZE; shift >= 0; shift -= Byte.SIZE) {
            number |= (bais.read() & 0xFF) << shift;
        }
        return number;
    }
    
    public static Long byteToLong(byte[] ba, int offset, int numOfBytes) {
        Long number = 0l;
        for (int shift = (numOfBytes-1) * Byte.SIZE; shift >= 0; shift -= Byte.SIZE) {
            number |= (ba[offset++] & 0xFF) << shift;
        }
        return number;
    }
    
    public static void longToByte(ByteArrayOutputStream baos, Long toAdd, int numOfBytes){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        byte[] array = null;
        
        try {
            dos.writeLong(toAdd);
            dos.flush();
            array = bos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=array.length-numOfBytes; i<array.length;i++){
            baos.write(array[i]);
        }
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
        Iterator<DeviceAttrs> keys = attrs.iterator();
        
        while( keys.hasNext()){
            DeviceAttrs key=null;

            try {
                key = keys.next();
                if (key.getCode() < Math.pow(2, 1*Byte.SIZE)){
                    longToByte(baos, key.getCode(), 1);    
                } else if (key.getCode() < Math.pow(2, 2*Byte.SIZE)){
                    longToByte(baos, key.getCode(), 2);   
                } else if (key.getCode() < Math.pow(2, 3*Byte.SIZE)){
                    longToByte(baos, key.getCode(), 3);   
                }
                
                Object object = attrs.getValue(key);
                if (object instanceof Number){
                    if (object instanceof Integer){
                        longToByte(baos, ((Integer)object).longValue(), key.getSize());
                    } else if (object instanceof Long){
                        longToByte(baos, (Long)object, key.getSize());    
                    }
                } else if (object instanceof String){
                    byte[] str = ((String)object).getBytes();
                    baos.write(str);
                    baos.write(0x0);
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
