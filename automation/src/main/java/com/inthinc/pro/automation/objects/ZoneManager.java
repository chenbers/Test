package com.inthinc.pro.automation.objects;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inthinc.pro.automation.models.DeviceZone;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.zone.option.ZoneAvailableOption;
import com.inthinc.pro.model.zone.option.ZoneOption;
import com.inthinc.pro.model.zone.option.type.OptionValue;

public class ZoneManager implements Iterable<DeviceZone> {

    private Integer fileFormatVersion;
    private Integer fileVersion;
    private Map<Integer, DeviceZone> zones;
    

    public ZoneManager(byte[] zoneArray) {
        zones = new HashMap<Integer, DeviceZone>();

        ByteArrayInputStream bab = new ByteArrayInputStream(zoneArray);
        fileFormatVersion = byteToInt(bab, 4);
        fileVersion = byteToInt(bab, 4);
        int nZones = byteToInt(bab, 4);
        for (int i = 0; i < nZones; i++) {
            DeviceZone zone = new DeviceZone();
            zone.setZoneID(byteToInt(bab, 4));
            bab.skip(4 * 3);
            zone.setPoints(parseVertices(bab));
            zone.setOptions(parseAttributes(bab));
            zones.put(zone.getZoneID(), zone);
//            break;
        }
    }

    private List<LatLng> parseVertices(ByteArrayInputStream bais) {
        int nVertices = byteToInt(bais, 2);
        List<LatLng> pointList = new ArrayList<LatLng>();
        double lat = 0.0;
        double lng = 0.0;
        for (; nVertices > 0; nVertices--) {
            lng = formatLng(bais);
            lat = formatLat(bais);
            pointList.add(new LatLng(lat, lng));
        }
        return pointList;
    }
    
    private double formatLng(ByteArrayInputStream bais){
        double lng;
        lng = (latLngInt(bais) / (double) 0xFFFFFF) * 360.0;
        if (lng > 180.0) {
            lng -= 360.0;
        }
        return lng;
    }
    
    private double formatLat(ByteArrayInputStream bais){
        double lat;
        lat = 90.0 - ((latLngInt(bais) / (double) 0xFFFFFF) * 180.0);
        return lat;
    }

    private Integer latLngInt(ByteArrayInputStream bais) {
        return (bais.read() << 16) | (bais.read() << 8) | bais.read();
    }

    private List<ZoneOption> parseAttributes(ByteArrayInputStream bais) {
        int nAttributes = byteToInt(bais, 2);
        List<ZoneOption> attributes = new ArrayList<ZoneOption>();
        for (; nAttributes > 0; nAttributes--) {
            int id = byteToInt(bais, 1);
            int size = 0;
            if (id < 128) {
                size = 1;
            } else if (id < 192) {
                size = 2;
            } else if (id > 191) {
                size = 4;
            } else {
                throw new IllegalArgumentException("No such ID as " + id);
            }
            int attribute = byteToInt(bais, size);
            ZoneAvailableOption attributeType = ZoneAvailableOption.valueOf(id);
            if (attributeType == null){
                continue;
            }
            OptionValue value = ZoneAvailableOption.convertOptionValue(
                    attributeType.getOptionType(), attribute);
            attributes.add(new ZoneOption(attributeType, value));
        }

        return attributes;
    }

    
    private Integer byteToInt(ByteArrayInputStream bais, int numOfBytes) {
        return byteToLong(bais, numOfBytes).intValue();
    }

    private Long byteToLong(ByteArrayInputStream bais, int numOfBytes) {
        Long number = 0l;
        for (int shift = 0; numOfBytes-- > 0 && shift < 64; shift += 8) {
            number |= (bais.read() & 0xFF) << shift;
        }
        return number;
    }

    public int nextInt() {

        return 0;
    }
    
    public DeviceZone getZone(Integer zoneID){
        return zones.get(zoneID);
    }
    
    public Set<Integer> getZoneIDs(){
        return zones.keySet();
    }

    @Override
    public Iterator<DeviceZone> iterator() {
        return zones.values().iterator();
    }
}
