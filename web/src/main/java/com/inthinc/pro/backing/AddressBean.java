package com.inthinc.pro.backing;

import java.util.List;

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
}
