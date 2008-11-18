package com.inthinc.pro.model;

import java.util.List;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Zone extends BaseEntity
{
    @ID
    private Integer      zoneID;
    @Column(name = "acctID")
    private Integer      accountID;
    private Integer      groupID;
    private String       name;
    private String       address;
    private ZoneType     zoneType;
    @Column(type = LatLng.class)
    private List<LatLng> points;

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

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
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

    public ZoneType getZoneType()
    {
        return zoneType;
    }

    public void setZoneType(ZoneType zoneType)
    {
        this.zoneType = zoneType;
    }

    public List<LatLng> getPoints()
    {
        return points;
    }

    public String getPointsString(String delim)
    {
        StringBuilder sb = new StringBuilder();
        if (points != null)
            for (final LatLng point : points)
            {
                if (sb.length() > 0)
                    sb.append(delim);
                sb.append(point);
            }
        return sb.toString();
    }

    public void setPoints(List<LatLng> points)
    {
        this.points = points;
    }
}
