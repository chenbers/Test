package com.inthinc.pro.automation.device_emulation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.inthinc.pro.automation.models.DeviceAttributes;
import com.inthinc.pro.automation.models.DeviceNote;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.model.event.EventAttr;

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
    
    public boolean replaceNotes(Queue<DeviceNote> notes){
        this.notes.removeAll(this.notes);
        if (!this.notes.isEmpty()){
            return false;
        }
        this.notes.addAll(notes);
        return true;
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
    

    public static Integer byteToInt(byte[] ba, int offset, int numOfBytes) {
        return byteToLong(ba, offset, numOfBytes).intValue();
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
    
    
    
    public static int concatenateTwoInts(int one, int two){
        MasterTest.print(one + "   " + two, Level.DEBUG);
        int value = (int)(  one << 4)  & 0xF0 | two & 0x0F;
        return value;
    }

    
    
    /**
     * @param baos
     * @param attrs 
     */
    public static void encodeAttributes(ByteArrayOutputStream baos, DeviceAttributes attrs) {
        Iterator<EventAttr> keys = attrs.iterator();
        
        while( keys.hasNext()){
            EventAttr key=null;

            key = keys.next();
            int keyCode = key.getIndex();
            if (keyCode < Math.pow(2, 1*Byte.SIZE)){
                longToByte(baos, keyCode, 1);    
            } else if (keyCode < Math.pow(2, 2*Byte.SIZE)){
                longToByte(baos, keyCode, 2);   
            } else if (keyCode < Math.pow(2, 3*Byte.SIZE)){
                longToByte(baos, keyCode, 3);   
            }
            encodeAttribute(baos, key, attrs.getValue(key));
        }
    }
    
    private static void encodeAttribute(ByteArrayOutputStream baos, EventAttr key, Object object) {
        try {
            if (object instanceof Number){
                if (object instanceof Integer){
                    longToByte(baos, ((Integer)object).longValue(), key.getSize());
                } else if (object instanceof Long){
                    longToByte(baos, (Long)object, key.getSize());    
                }
            } else if (object instanceof String){
                byte[] str = ((String)object).getBytes();
                int size = key.getSize();
                baos.write(str, 0, str.length);
                if (key.isZeroTerminated()){
                    baos.write(0x0);
                } else {
                    size -= str.length;
                    for (int i=0;i<size;i++){
                        baos.write(0x0);
                    }
                }
            }
        } catch (NullPointerException e){
            logger.info("Key: " + key + " Value: " + object);
            logger.error(StackToString.toString(e));
        }
    }
    
    public static void encodeAttributes(ByteArrayOutputStream baos, DeviceAttributes attrs, EventAttr[] attrList) {
        for (EventAttr attr : attrList){
            encodeAttribute(baos, attr, attrs.getValue(attr));
        }
    }

    public static Object byteToInt(List<Byte> m_pData, int numOfBytes) {
        byte[] temp = new byte[m_pData.size()];
        for (int i=0;i<m_pData.size();i++){
            temp[i] = m_pData.get(i);
        }
        return byteToInt(temp, 0, numOfBytes);
    }

}
