package com.inthinc.pro.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.PathParam;

import com.inthinc.pro.dao.hessian.proserver.MCMService;
import com.inthinc.pro.service.NoteService;
import com.inthinc.pro.service.note.Attribute;
import com.inthinc.pro.service.note.AttributeType;
import com.inthinc.pro.service.note.Heading;
import com.inthinc.pro.service.note.Note;
import com.inthinc.pro.service.note.NoteType;
public class NoteServiceImpl implements NoteService {
    private MCMService mcmService;
    private static final Integer BOUNDRY = 146;

    public String createSpeedEvent(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer averageSpeed, Integer distance) {
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute averageSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_AVG_SPEED, averageSpeed);
        Attribute topSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_TOP_SPEED, speed);
        Attribute distanceAttribute = new Attribute(AttributeType.ATTR_TYPE_DISTANCE, distance);
        Note note = new Note(NoteType.SPEEDING, new Date(), latitude, longitude, speed, 10, 9, 1, speedLimitAttribute, averageSpeedAttribute, topSpeedAttribute, distanceAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    public String createIdlingEvent(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer averageSpeed, Integer distance) {
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute averageSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_AVG_SPEED, averageSpeed);
        Attribute topSpeedAttribute = new Attribute(AttributeType.ATTR_TYPE_TOP_SPEED, speed);
        Attribute distanceAttribute = new Attribute(AttributeType.ATTR_TYPE_DISTANCE, distance);
        Note note = new Note(NoteType.SPEEDING, new Date(), latitude, longitude, speed, 10, 9, 1, speedLimitAttribute, averageSpeedAttribute, topSpeedAttribute, distanceAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    @Override
    public String createHardAccelerationEvent(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer bearing, Integer deltaX, Integer deltaY,
            Integer deltaZ) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute boundryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDRY, BOUNDRY); // /146 = Utah
        Attribute deltaXAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVX, deltaX);
        Attribute deltaYAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVY, deltaY);
        Attribute deltaZAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVZ, deltaZ);
        Note note = new Note(NoteType.NOTE, new Date(), latitude, longitude, speed, 10, 9, heading, speedLimitAttribute, boundryAttribute, deltaXAttribute, deltaYAttribute,
                deltaZAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    public String createBeginTripEvent(String imei, Double latitude, Double longitude, Integer speed) {
        Attribute boundryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDRY, 146);// /146 = Utah
        Note note = new Note(NoteType.IGNITION_ON, new Date(), latitude, longitude, speed, 10, 9, 1, boundryAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    public String createEndTripEvent(@PathParam("imei") String imei, Double latitude, Double longitude, Integer speed) {
        Attribute mpgAttribute = new Attribute(AttributeType.ATTR_TYPE_MPG, 25);
        Attribute boundryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDRY, 146);// /146 = Utah
        Note note = new Note(NoteType.IGNITION_OFF, new Date(), latitude, longitude, speed, 10, 9, 1, boundryAttribute, mpgAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    public String createCurrentLocationEvent(@PathParam("imei") String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer bearing) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute boundryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDRY, 146);// /146 = Utah
        Note note = new Note(NoteType.LOCATION, new Date(), latitude, longitude, speed, 10, 9, heading, speedLimitAttribute, boundryAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    @RolesAllowed("ROLE_NORMALUSER")
    public String createCrashEvent(@PathParam("imei") String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer bearing, Integer deltaX,
            Integer deltaY, Integer deltaZ) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute boundryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDRY, 146); // /146 = Utah
        Attribute deltaXAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVX, deltaX);
        Attribute deltaYAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVY, deltaY);
        Attribute deltaZAttribute = new Attribute(AttributeType.ATTR_TYPE_DELTAVZ, deltaZ);
        Note note = new Note(NoteType.CRASH, new Date(), latitude, longitude, speed, 10, 9, heading, speedLimitAttribute, boundryAttribute, deltaXAttribute, deltaYAttribute,
                deltaZAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    public void setMcmService(MCMService mcmService) {
        this.mcmService = mcmService;
    }

    public MCMService getMcmService() {
        return mcmService;
    }

    @Override
    public String createZoneArrivalEvent(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer bearing, Integer zoneID) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute boundryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDRY, BOUNDRY); // /146 = Utah
        Attribute zoneIdAttribute = new Attribute(AttributeType.ATTR_TYPE_ZONE_ID, zoneID);
        Note note = new Note(NoteType.ZONES_ARRIVAL, new Date(), latitude, longitude, speed, 10, 9, heading, speedLimitAttribute, boundryAttribute, zoneIdAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }

    @Override
    public String createTamperingEvent(String imei, Double latitude, Double longitude, Integer speed, Integer speedLimit, Integer bearing, Integer backupBatteryValue) {
        Integer heading = Heading.valueOf(bearing).getCode();
        Attribute speedLimitAttribute = new Attribute(AttributeType.ATTR_TYPE_SPEED_LIMIT, speedLimit);
        Attribute boundryAttribute = new Attribute(AttributeType.ATTR_TYPE_BOUNDRY, 146);// /146 = Utah
        Attribute backupBatteryAttribute = new Attribute(AttributeType.ATTR_TYPE_BACKUP_BATTERY, backupBatteryValue);
        Note note = new Note(NoteType.UNPLUGGED, new Date(), latitude, longitude, speed, 10, 9, heading, speedLimitAttribute, boundryAttribute,backupBatteryAttribute);
        List<byte[]> byteList = new ArrayList<byte[]>();
        byteList.add(note.getBytes());
        List<Map> mapList = mcmService.note(imei, byteList);
        if (mapList.size() > 0) {
            return mapList.get(0).get("data").toString();
        }
        return "1";
    }
}
