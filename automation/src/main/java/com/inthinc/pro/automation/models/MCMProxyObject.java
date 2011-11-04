package com.inthinc.pro.automation.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;

import com.inthinc.pro.automation.device_emulation.NoteManager.DeviceNote;
import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.interfaces.MCMProxy;
import com.inthinc.pro.automation.resources.DeviceStatistics;
import com.inthinc.pro.automation.utils.AutomationHessianFactory;
import com.inthinc.pro.automation.utils.MasterTest;

import com.inthinc.noteservice.NoteService;


public class MCMProxyObject implements MCMProxy{
    
    private MCMProxy proxy;


    public static boolean regularNote = true;
    

    public static Map<Integer, Map<String, String>> drivers;
    
    public static boolean addDriversList(Map<Integer, Map<String, String>> driverSet){
        drivers = driverSet;
        return true;
    }

    public MCMProxyObject(Addresses server) {
        AutomationHessianFactory getHessian = new AutomationHessianFactory();
        MasterTest.print("MCM Server is " + server, Level.DEBUG);
        proxy = getHessian.getMcmProxy(server);
    }
    

    private void printReply(Object reply){
        MasterTest.print(reply, Level.DEBUG);
    }
    
    private void printNote(DeviceNote note){
        MasterTest.print(note, Level.DEBUG);
    }
    
    private void printOther(Object other){
        MasterTest.print(other, Level.DEBUG);
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
            for (DeviceNote note : noteList){
                NoteService notes = new NoteService("inthinc", "note", "cassandra-node0.tiwipro.com:9160,cassandra-node1.tiwipro.com:9160");
                notes.insertNote(((TiwiNote)note).packageToMap());
            }
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
    
    public List<Map<String, Object>> notebc(String mcmID, int connectType,
            List<DeviceNote> noteList, boolean extra){
        List<byte[]> temp = new ArrayList<byte[]>(noteList.size());
        for (DeviceNote note : noteList){
            temp.add(note.Package());
            printNote(note);
        }
        return note(mcmID, temp);
    }

    @Override
    public List<Map<String, Object>> notebc(String mcmID, int connectType,
            List<byte[]> noteList) {
        List<Map<String, Object>> reply = proxy.notebc(mcmID, connectType, noteList);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }
    
    public List<Map<String, Object>> notews(String mcmID, int connectType,
            List<DeviceNote> noteList, boolean extra){
        List<byte[]> temp = new ArrayList<byte[]>(noteList.size());
        for (DeviceNote note : noteList){
            temp.add(note.Package());
            printNote(note);
        }
        return note(mcmID, temp);
    }
    
    @Override
    public List<Map<String, Object>> notews(String mcmID, int connectType,
            List<byte[]> noteList) {
        List<Map<String, Object>> reply = proxy.notews(mcmID, connectType, noteList);
        printReply(reply);
        DeviceStatistics.addCall();
        return reply;
    }
}
