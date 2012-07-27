package com.inthinc.device.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.inthinc.device.emulation.utils.GeoPoint;
import com.inthinc.pro.automation.models.BaseEntity;
import com.inthinc.pro.automation.models.LatLng;
import com.inthinc.pro.automation.models.Status;

@XmlRootElement
public class Zone extends BaseEntity implements Cloneable
{
    private static final long serialVersionUID = 7505601232108995094L;

    private Integer           zoneID;
    @JsonProperty("acctID")
    private Integer           accountID;
    private Status            status;
    private String            name;
    private String            address;
    @JsonProperty("latLng")
    private List<GeoPoint>      points;
    private Integer           groupID;
    
    private List<ZoneOption>    options;
    private transient Map<ZoneAvailableOption, OptionValue> optionsMap;
    
    public Zone()
    {
        super();
    }
    public Zone(Integer zoneID, Integer accountID, Status status, String name, String address, Integer groupID)
    {
        super();
        this.zoneID = zoneID;
        this.accountID = accountID;
        this.status = status;
        this.name = name;
        this.address = address;
        this.groupID = groupID;
    }

    public Integer getZoneID()
    {
        return zoneID;
    }

    public void setZoneID(Integer zoneID)
    {
        this.zoneID = zoneID;
    }

    public Integer getAccountID()
    {
        return accountID;
    }

    public void setAccountID(Integer accountID)
    {
        this.accountID = accountID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public List<GeoPoint> getPoints()
    {
        return points;
    }

    public void setPoints(List<GeoPoint> points)
    {
        this.points = points;
    }

    /**
     * @return The points formatted as <code>(lat,lng);(lat,lng),...</code>
     */
    public String getPointsString()
    {
        StringBuilder sb = new StringBuilder();
        if (points != null)
            for (final GeoPoint point : points)
            {
                if (sb.length() > 0)
                    sb.append(';');
                sb.append(point.toString());
            }
        return sb.toString();
    }

    /**
     * @param pointsString
     *            The points formatted as <code>(lat,lng);(lat,lng),...</code>
     */
    public void setPointsString(String pointsString)
    {
        points = new ArrayList<GeoPoint>();

        if (pointsString != null)
        {
            final StringTokenizer tokenizer = new StringTokenizer(pointsString, ";");
            while (tokenizer.hasMoreTokens())
            {
                final String latlngToken = tokenizer.nextToken();
                final int idx = latlngToken.indexOf(',');
                if (idx == -1)
                    throw new IllegalArgumentException("invalid point pair string: " + latlngToken);
                final float lat = new Float(latlngToken.substring(1, idx));
                final float lng = new Float(latlngToken.substring(idx + 1, latlngToken.length() - 1));
                points.add(new GeoPoint(lat, lng));
            }
        }
    }
    

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }
    public Integer getGroupID()
    {
        return groupID;
    }
    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    public List<ZoneOption> getOptions() {
        return options;
    }
    public void setOptions(List<ZoneOption> options) {
        this.options = options;
    }
    

    public Map<ZoneAvailableOption, OptionValue> getOptionsMap() {
        if (optionsMap == null) {
            optionsMap = new HashMap<ZoneAvailableOption, OptionValue>();
            List<ZoneOption> options = getOptions();
            for (ZoneAvailableOption availOption : ZoneAvailableOption.values())
            {
                OptionValue value = availOption.getDefaultValue();
                if (options != null) {
                    for (ZoneOption zoneOption : options) {
                        if (zoneOption.getOption() == availOption) {
                            value = ZoneAvailableOption.convertOptionValue(availOption.getOptionType(), zoneOption.getValue().getValue());
                        }
                    }
                }
                optionsMap.put(availOption, value);
            }
        }
        return optionsMap;
    }
    public void setOptionsMap(Map<ZoneAvailableOption, OptionValue> optionsMap) {
        this.optionsMap = optionsMap;
    }
    

    public Zone clone() {
        try {
            return (Zone)super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
        
    }
    
    public boolean containsLatLng(LatLng latLng){
        
        boolean inZone = false; 
        
        //  We save the first point TWICE, at 0 and at the end, therefore, subtract 1 to get the unique points
        int numPoints = getPoints().size() - 1;           
        int j = numPoints-1;
        
        for(int i=0; i < numPoints; i++) { 
        	GeoPoint vertex1 = getPoints().get(i);
        	GeoPoint vertex2 = getPoints().get(j);                        

            if (    vertex1.getLng() < latLng.getLng() && 
                    vertex2.getLng() >= latLng.getLng() || 
                    vertex2.getLng() < latLng.getLng() && 
                    vertex1.getLng() >= latLng.getLng() )                                  
            {                                       
                               
                if (vertex1.getLat() + (latLng.getLng() - vertex1.getLng()) / (vertex2.getLng() - vertex1.getLng()) * (vertex2.getLat() - vertex1.getLat()) < latLng.getLat()) {               
                    inZone = !inZone;                    
                }
            }

            j = i;
        }   
        return inZone;
    }
}
