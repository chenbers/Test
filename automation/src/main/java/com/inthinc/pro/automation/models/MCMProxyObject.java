package com.inthinc.pro.automation.models;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ByteArrayBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.log4j.Level;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.automation.deviceEnums.DeviceNoteTypes;
import com.inthinc.pro.automation.device_emulation.DeviceState;
import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.enums.AutomationCassandra;
import com.inthinc.pro.automation.models.NoteBC.Direction;
import com.inthinc.pro.automation.resources.DeviceStatistics;
import com.inthinc.pro.automation.utils.AutomationHessianFactory;
import com.inthinc.pro.automation.utils.HTTPCommands;
import com.inthinc.pro.automation.utils.MasterTest;
import com.inthinc.pro.automation.utils.StackToString;
import com.inthinc.pro.dao.hessian.proserver.MCMService;
import com.inthinc.pro.noteservice.NoteService;


public class MCMProxyObject implements MCMService{
    
    /**
     * 
     */
    private static final long serialVersionUID = 3563177742129607776L;



    private MCMService proxy;


    private Addresses server;


    public static boolean regularNote = true;
    

    public static Map<String, String> drivers;


    private static NoteService notes;
    
    public static NoteService getService(){
        return notes;
    }
    
    public MCMProxyObject(Addresses server) {
        this.server = server;
        AutomationHessianFactory getHessian = new AutomationHessianFactory();
        MasterTest.print("MCM Server is " + server, Level.DEBUG);
        proxy = getHessian.getMcmProxy(server);
    }
    

    private void printReply(Object reply){
        MasterTest.print(reply, Level.DEBUG, 3);
    }
    
    private void printNote(DeviceNote note){
        MasterTest.print(note, Level.INFO, 3);
    }
    
    private void printOther(Object other){
        MasterTest.print(other, Level.DEBUG, 3);
    }
    
    public static void processDrivers(Map<Integer, Map<String, String>> driversMap, String cassandraNode, Integer poolSize, boolean autoDiscovery){
        drivers = new HashMap<String, String>();
        Iterator<Integer> itr = driversMap.keySet().iterator();
        while (itr.hasNext()){
            Integer next = itr.next();
            Map<String, String> map = driversMap.get(next);
            drivers.put(map.get("device"), map.get("deviceID"));
        }
        notes = AutomationCassandra.createNode(cassandraNode, poolSize, autoDiscovery);
    }
    
    public List<Map<String, Object>> note(String mcmID, List<DeviceNote> noteList, boolean extra){
        if (regularNote ){
            List<byte[]> temp = new ArrayList<byte[]>(noteList.size());
            for (DeviceNote note : noteList){
                byte[] array = note.Package();
                temp.add(array);
                printNote(note);
                
                if (array.length <=17){
                    throw new IllegalArgumentException("Note cannot be 17 bytes long");
                }
            }
            return note(mcmID, temp);
        } else {
            List<Map<String, String>> list = new ArrayList<Map<String, String>>();
            for (DeviceNote note : noteList){
                Map<String, String> temp = ((TiwiNote)note).packageToMap();
                temp.put("32900", drivers.get(mcmID).toString());
                list.add(temp);
//                MutationResult mr = notes.insertNote(temp);
//                MasterTest.print(mr.getExecutionTimeMicro(), Level.DEBUG);
//                DeviceStatistics.addCall();
            }
            notes.insertNote(list);
        }
        return null;
    }
    

    @Override
    public List<Map<String, Object>> note(String mcmID, List<byte[]> noteList) {
        List<Map<String, Object>> reply = proxy.note(mcmID, noteList);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }
    
    @Override
    public List<Map<String, Object>> dumpSet(String mcmID, Integer version,
            Map<Integer, String> settings) {
        printOther(settings);
        List<Map<String, Object>> reply = proxy.dumpSet(mcmID, version, settings);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<Integer, String>> reqSet(String imei) {
        List<Map<Integer, String>> reply = proxy.reqSet(imei);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> audioUpdate(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.audioUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> tiwiproUpdate(String mcmID,
            Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.tiwiproUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> witnessUpdate(String mcmID,
            Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.witnessUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> emuUpdate(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.emuUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> zoneUpdate(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.zoneUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> sbsUpdate(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.sbsUpdate(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<String, Object>> sbsCheck(String mcmID, int baselineID,
            List<Map<String, Object>> mapList) {
        printOther(mapList);
        List<Map<String, Object>> reply = proxy.sbsCheck(mcmID, baselineID, mapList);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<String, Object>> checkSbsSubscribed(String mcmID,
            Map<String, Object> map) {
        printOther(map);
        List<Map<String, Object>> reply = proxy.checkSbsSubscribed(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> getSbsBase(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.getSbsBase(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> getSbsEdit(String mcmID, Map<String, Object> map) {
        printOther(map);
        Map<String, Object> reply = proxy.getSbsEdit(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<String, Object>> checkSbsEdit(String mcmID,
            List<Map<String, Object>> map) {
        printOther(map);
        List<Map<String, Object>> reply = proxy.checkSbsEdit(mcmID, map);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }
    
    public List<Map<String, Object>> notebc(String mcmID, Direction comType,
            List<DeviceNote> noteList, String imei){
        List<byte[]> temp = new ArrayList<byte[]>(noteList.size());
        
        for (DeviceNote note : noteList){
            if (note.getType() == DeviceNoteTypes.INSTALL){
                List<byte[]> install = new ArrayList<byte[]> (1);
                install.add(note.Package());
                notebc(imei, Direction.sat.getCode(), install);
            } else {
                temp.add(note.Package());
            }
            printNote(note);
        }
        if (!temp.isEmpty()){
            return notebc(mcmID, comType.getCode(), temp);
        } else {
            return null;
        }
    }

    @Override
    public List<Map<String, Object>> notebc(String mcmID, int connectType,
            List<byte[]> noteList) {
        MasterTest.print("notebc(mcmID=%s, connectType=%d, noteList=%s)", Level.DEBUG, mcmID, connectType, noteList);
        List<Map<String, Object>> reply = proxy.notebc(mcmID, connectType, noteList);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }
    
    public String[] notews(String mcmID, Direction comType,
            List<DeviceNote> noteList, String imei){
        
        HTTPCommands http = new HTTPCommands();
        List<String> reply = new ArrayList<String>();
        
        for (DeviceNote note : noteList){
            MasterTest.print(server.getPortalUrl());
            String uri = 
                "http://" + server.getPortalUrl() + 
                ":" + server.getWaysPort() + 
                "/gprs_wifi/gprs.do?mcm_id=" +""+(comType.equals(Direction.sat) ? imei: mcmID )+
                "&commType="+comType.getCode()+
                "&sat_cmd="+note.getType().getCode()+
                "&event_time="+note.getTime();
            
            HttpPost method = new HttpPost(uri.toLowerCase());
            MultipartEntity entity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
            
            try {
                entity.addPart("mcm_id", new StringBody(mcmID, Charset.forName("UTF-8")));
                entity.addPart("imei", new StringBody(imei, Charset.forName("UTF-8")));
                entity.addPart("commType", new StringBody("" + comType.getCode(), Charset.forName("UTF-8")));
                entity.addPart("event_time", new StringBody("" + note.getTime(), Charset.forName("UTF-8")));
                entity.addPart("sat_cmd", new StringBody("" + note.getType(), Charset.forName("UTF-8")));
                entity.addPart("url", new StringBody(uri, Charset.forName("UTF-8")));
                entity.addPart("note", new StringBody(note.toString(), Charset.forName("UTF-8")));
                entity.addPart("vehicle_id_str", new StringBody("654", Charset.forName("UTF-8")));
                entity.addPart("company_id", new StringBody("3", Charset.forName("UTF-8")));
            } catch (Exception e) {
                
            }
            byte[] packaged = note.Package();
            
            entity.addPart("filename", new ByteArrayBody(packaged, "filename"));
//            StringWriter writer = new StringWriter();
//            int pos = 0;
//            for (byte bits : packaged){
//                writer.write(bits);
//                MasterTest.print(Integer.toHexString(bits) + " " + pos++);
//            }
//            MasterTest.print(writer.toString());
//            MasterTest.print(writer.toString().length());
            
//            entity.addPart("file", new ByteArrayBody(note.Package(), "file"));
            method.setEntity(entity);
            
            reply.add(http.httpRequest(method));
            
            printNote(note);
        }
        return reply.toArray(new String[]{});
    }
    
    @Override
    public List<Map<String, Object>> notews(String mcmID, int connectType,
            List<byte[]> noteList) {
        List<Map<String, Object>> reply = proxy.notews(mcmID, connectType, noteList);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }

    public static void closeService() {
        notes.shutdown();
    }

    public static void setupCassandra(NoteService service) {
        notes = service;
        regularNote = false;
    }
    
    public Object sendNotes(DeviceState state, Map<Class<? extends DeviceNote>, LinkedList<DeviceNote>> sendingQueue){
        Object reply = null;
        Class<?> noteClass = DeviceNote.class;
        try {
            if (sendingQueue.containsKey(NoteBC.class)) {
                noteClass = NoteBC.class;
                reply = notebc(state.getMcmID(),
                        state.getWaysDirection(),
                        sendingQueue.get(noteClass), state.getImei());
            } else if (sendingQueue.containsKey(NoteWS.class)) {
                noteClass = NoteWS.class;
                reply = notews(state.getMcmID(),
                        state.getWaysDirection(),
                        sendingQueue.get(noteClass), state.getImei());
            } else if (sendingQueue.containsKey(TiwiNote.class)) {
                noteClass = TiwiNote.class;
                reply = note(state.getImei(),
                        sendingQueue.get(noteClass), true);
            }
            sendingQueue.remove(noteClass);
        } catch (Exception e) {
            MasterTest.print(
                    "Error from Note with IMEI: " + state.getImei() + "  "
                            + StackToString.toString(e) + "\n"
                            + sendingQueue + "\nCurrent Note Count is "
                            + DeviceStatistics.getHessianCalls()
                            + "\nCurrent time is: "
                            + System.currentTimeMillis()
                            + "\nNotes Started at: "
                            + DeviceStatistics.getStart().epochTime(),
                    Level.FATAL);
        }
        
        return reply;
    }

    public Object sendNotes(DeviceState state, DeviceNote note) {
        Map<Class<? extends DeviceNote>, LinkedList<DeviceNote>> sendingQueue = new HashMap<Class<? extends DeviceNote>, LinkedList<DeviceNote>>();
        LinkedList<DeviceNote> list = new LinkedList<DeviceNote>();
        list.add(note);
        sendingQueue.put(note.getClass(), list);
        return sendNotes(state, sendingQueue);
    }

    @Override
    public Integer crash(String mcmID, List<byte[]> crashDataList)
            throws ProDAOException {
        return proxy.crash(mcmID, crashDataList);
    }
}
