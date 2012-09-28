package com.inthinc.pro.backing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jdt.core.dom.ThisExpression;
import org.richfaces.json.JSONObject;

import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.util.MessageUtil;
import com.inthinc.pro.util.MiscUtil;

public class AddressBean extends BaseBean {
    
    private double zoneLat;
    private double zoneLng;
    private int    elemIndex;
    private String zoneName;  
    private List<Zone> zones;
    private String noteID;
    private Map<String,String> zoneData;
    private String  itemID;
    private String  subID;
    private int     callFuncID;
    
    public AddressBean() {
        super();
        zones = this.getProUser().getZones();
    }
    
    
    public double getZoneLat() {
        return zoneLat;
    }

    public void setZoneLat(double zoneLat) {
        this.zoneLat = zoneLat;
    }

    public double getZoneLng() {
        return zoneLng;
    }

    public void setZoneLng(double zoneLng) {
        this.zoneLng = zoneLng;
    }

    public int getElemIndex() {
        return elemIndex;
    }


    public void setElemIndex(int elemIndex) {
        this.elemIndex = elemIndex;
    }


    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public String getNoteID() {
        return noteID;
    }

    public void setNoteID(String noteID) {
        this.noteID = noteID;
    }


    public String getItemID() {
        return itemID;
    }


    public void setItemID(String itemID) {
        this.itemID = itemID;
    }


    public String getSubID() {
        return subID;
    }


    public void setSubID(String subID) {
        this.subID = subID;
    }


    public int getCallFuncID() {
        return callFuncID;
    }


    public void setCallFuncID(int callFuncID) {
        this.callFuncID = callFuncID;
    }


    public void lookForZone() {
        LatLng latLng = new LatLng();
        
        latLng.setLat(zoneLat);
        latLng.setLng(zoneLng);
        
        zoneName = MiscUtil.findZoneName(this.getProUser().getZones(), latLng);
        if ( zoneName == null ) {
            zoneName = MessageUtil.getMessageString("sbs_badLatLng");
        }
        zoneName += "," + new String(String.valueOf(elemIndex));
    }    

    public Map<String,String> getZoneData() {
//        JSONObject jSonObject = new JSONObject();
        Map<String,String> jSonObject = new HashMap<String,String>();
        
        try {
            jSonObject.put("lat", String.valueOf(this.getZoneLat()));
            jSonObject.put("lng", String.valueOf(this.getZoneLng()));
            jSonObject.put("name", this.getZoneName());
            jSonObject.put("noteID", this.getNoteID());
            jSonObject.put("itemID", this.itemID);
            jSonObject.put("subID", this.subID);
            jSonObject.put("callFuncID", String.valueOf(this.callFuncID));
        } catch (Exception e) {
            return null;
        }
        
        return jSonObject;
    }
}
