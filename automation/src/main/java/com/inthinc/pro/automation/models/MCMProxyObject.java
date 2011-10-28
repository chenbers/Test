package com.inthinc.pro.automation.models;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.automation.enums.Addresses;
import com.inthinc.pro.automation.interfaces.MCMProxy;
import com.inthinc.pro.automation.resources.DeviceStatistics;
import com.inthinc.pro.automation.utils.AutomationHessianFactory;


public class MCMProxyObject implements MCMProxy{
    
    private final static Logger logger = Logger.getLogger(MCMProxyObject.class);
    
    private MCMProxy proxy;

    public MCMProxyObject(Addresses server) {
        AutomationHessianFactory getHessian = new AutomationHessianFactory();
        logger.debug("MCM Server is " + server);
        proxy = getHessian.getMcmProxy(server);
    }

    @Override
    public List<Map<String, Object>> note(String mcmID, List<byte[]> noteList) {
        List<Map<String, Object>> reply = proxy.note(mcmID, noteList);
        DeviceStatistics.addCall();
        return reply;
    }
    
    @Override
    public List<Map<String, Object>> dumpSet(String mcmID, Integer version,
            Map<Integer, String> settings) {
        List<Map<String, Object>> reply = proxy.dumpSet(mcmID, version, settings);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<Integer, String>> reqSet(String imei) {
        List<Map<Integer, String>> reply = proxy.reqSet(imei);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> audioUpdate(String mcmID, Map<String, Object> map) {
        Map<String, Object> reply = proxy.audioUpdate(mcmID, map);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> tiwiproUpdate(String mcmID,
            Map<String, Object> map) {
        Map<String, Object> reply = proxy.tiwiproUpdate(mcmID, map);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> witnessUpdate(String mcmID,
            Map<String, Object> map) {
        Map<String, Object> reply = proxy.witnessUpdate(mcmID, map);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> emuUpdate(String mcmID, Map<String, Object> map) {
        Map<String, Object> reply = proxy.emuUpdate(mcmID, map);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> zoneUpdate(String mcmID, Map<String, Object> map) {
        Map<String, Object> reply = proxy.zoneUpdate(mcmID, map);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> sbsUpdate(String mcmID, Map<String, Object> map) {
        Map<String, Object> reply = proxy.sbsUpdate(mcmID, map);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<String, Object>> sbsCheck(String mcmID, int baselineID,
            List<Map<String, Object>> mapList) {
        List<Map<String, Object>> reply = proxy.sbsCheck(mcmID, baselineID, mapList);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<String, Object>> checkSbsSubscribed(String mcmID,
            Map<String, Object> map) {
        List<Map<String, Object>> reply = proxy.checkSbsSubscribed(mcmID, map);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> getSbsBase(String mcmID, Map<String, Object> map) {
        Map<String, Object> reply = proxy.getSbsBase(mcmID, map);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public Map<String, Object> getSbsEdit(String mcmID, Map<String, Object> map) {
        Map<String, Object> reply = proxy.getSbsEdit(mcmID, map);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<String, Object>> checkSbsEdit(String mcmID,
            List<Map<String, Object>> map) {
        List<Map<String, Object>> reply = proxy.checkSbsEdit(mcmID, map);
        DeviceStatistics.addCall();
        return reply;
    }

    @Override
    public List<Map<String, Object>> notebc(String mcmID, int connectType,
            List<byte[]> noteList) {
        List<Map<String, Object>> reply = proxy.notebc(mcmID, connectType, noteList);
        DeviceStatistics.addCall();
        return reply;
    }
    
    @Override
    public List<Map<String, Object>> notews(String mcmID, int connectType,
            List<byte[]> noteList) {
        List<Map<String, Object>> reply = proxy.notews(mcmID, connectType, noteList);
        DeviceStatistics.addCall();
        return reply;
    }
}
