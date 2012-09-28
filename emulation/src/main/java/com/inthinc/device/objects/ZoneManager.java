package com.inthinc.device.objects;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.util.Log;

import com.inthinc.device.emulation.notes.DeviceNote;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.device.models.OptionValue;
import com.inthinc.device.models.Zone;
import com.inthinc.device.models.ZoneAvailableOption;
import com.inthinc.device.models.ZoneOption;

public class ZoneManager implements Iterable<Zone>{

    private Integer fileFormatVersion;
    private Integer fileVersion;
	private HashMap<Integer, Zone> zones;
	
	private int readLittleEndian(ByteArrayInputStream bab, int size){
		byte[] bigEndian = new byte[size];
		for (int i=size; i>0; i--){
			bigEndian[i-1] = (byte) (bab.read() & 0xFF);
		}
		ByteArrayInputStream reversed = new ByteArrayInputStream(bigEndian);
		int value = DeviceNote.byteToInt(reversed, size);
		return value;
	}
	
    public void processArray(byte[] zoneArray) {
        zones = new HashMap<Integer, Zone>();

        ByteArrayInputStream bab = new ByteArrayInputStream(zoneArray);
        
//        while (true){
//        	int next = bab.read();
//        	if (next==-1){
//        		break;
//        	} else {
//        		Log.info("0x%02x", next & 0xFF);
//        	}
//        }
        
        setFileFormatVersion(readLittleEndian(bab, 4));
        setFileVersion(readLittleEndian(bab, 4));
        int nZones = readLittleEndian(bab, 4);
        for (int i = 0; i < nZones; i++) {
            Zone zone = new Zone();
            zone.setZoneID(readLittleEndian(bab, 4));
            bab.skip((4 * 3));
            zone.setPoints(parseVertices(bab));
            zone.setOptions(parseAttributes(bab));
            zones.put(zone.getZoneID(), zone);
//            break;
        }
    }

    private List<GeoPoint> parseVertices(ByteArrayInputStream bais) {
        int nVertices = readLittleEndian(bais, 2);
        List<GeoPoint> pointList = new ArrayList<GeoPoint>();
        for (; nVertices > 0; nVertices--) {
        	GeoPoint next = new GeoPoint();
            next.decodeLng(DeviceNote.byteToLong(bais, 3));
            next.decodeLat(DeviceNote.byteToLong(bais, 3));
            pointList.add(next);
        }
        return pointList;
    }
    

    private List<ZoneOption> parseAttributes(ByteArrayInputStream bais) {
        int nAttributes = readLittleEndian(bais, 2);
        List<ZoneOption> attributes = new ArrayList<ZoneOption>();
        for (; nAttributes > 0; nAttributes--) {
            int id = readLittleEndian(bais, 1);
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
            
            int attribute = readLittleEndian(bais, size);
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

    

    public Zone getZone(Integer zoneID){
        return zones.get(zoneID);
    }
    
    public Set<Integer> getZoneIDs(){
        return zones.keySet();
    }

    @Override
    public Iterator<Zone> iterator() {
        return zones.values().iterator();
    }

	public Integer getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(Integer fileVersion) {
		this.fileVersion = fileVersion;
	}

	public Integer getFileFormatVersion() {
		return fileFormatVersion;
	}

	public void setFileFormatVersion(Integer fileFormatVersion) {
		this.fileFormatVersion = fileFormatVersion;
	}
}
