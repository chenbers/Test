package com.inthinc.device.emulation.utils;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import com.inthinc.device.emulation.notes.DeviceNote;

public class NoteManager {
    
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

}
