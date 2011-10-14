package com.inthinc.pro.automation.models;

import java.io.StringWriter;
import java.util.Iterator;
import java.util.Map;

import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.zone.option.ZoneAvailableOption;
import com.inthinc.pro.model.zone.option.type.OptionValue;

public class DeviceZone extends Zone {

    /**
     * 
     */
    private static final long serialVersionUID = 8480567018304591105L;

    public DeviceZone() {
    }

    public DeviceZone(Integer zoneID, Integer accountID, Status status,
            String name, String address, Integer groupID) {
        super(zoneID, accountID, status, name, address, groupID);
    }
    
    public DeviceZone (Zone zone){
        setAccountID(zone.getAccountID());
        setGroupID(zone.getGroupID());
        setAddress(zone.getAddress());
        setCreated(zone.getCreated());
        setModified(zone.getModified());
        setName(zone.getName());
        setOptions(zone.getOptions());
        setOptionsMap(zone.getOptionsMap());
        setPoints(zone.getPoints());
        setPointsString(zone.getPointsString());
        setStatus(zone.getStatus());
        setZoneID(zone.getZoneID());
    }
    
    @Override
    public String toString(){
        StringWriter writer = new StringWriter();
        writer.write("\nName:      " + getName());
        writer.write("\nAccountID: " + getAccountID());
        writer.write("\nGroupID:   " + getGroupID());
        writer.write("\nZoneID:    " + getZoneID());
        writer.write("\nOptions:");
        Iterator<ZoneAvailableOption> itr = getOptionsMap().keySet().iterator();
        while (itr.hasNext()){
            ZoneAvailableOption option = itr.next();
            writer.write("\n\t" + option + ": " + getOptionsMap().get(option));
        }
        return writer.toString();
    }
    
    @Override
    public boolean equals(Object obj){
        try {
            assertEquals(obj instanceof Zone);
            Zone zone = (Zone) obj;
            Iterator<ZoneAvailableOption> itr = getOptionsMap().keySet().iterator();
            Map<ZoneAvailableOption, OptionValue> objectMap = zone.getOptionsMap();
            Map<ZoneAvailableOption, OptionValue> thisMap = getOptionsMap();
            while (itr.hasNext()){
                ZoneAvailableOption next = itr.next();
                assertEquals(thisMap.get(next).toString().equals(objectMap.get(next).toString()));
            }
//            assert(getPoints().equals(zone.getPoints()));
//            assertEquals(getPointsString().equals(zone.getPointsString()));
//            assertEquals(getStatus().equals(zone.getStatus()));
            assertEquals(getZoneID().equals(zone.getZoneID()));
        } catch (AssertionError e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private void assertEquals(boolean equals) {
        if (!equals){
            throw new AssertionError();
        }
    }
    

}
