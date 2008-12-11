package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class Zone extends BaseEntity
{
    @Column(updateable = false)
    private static final long serialVersionUID = 7505601232108995094L;

    @ID
    private Integer           zoneID;
    @Column(name = "acctID")
    private Integer           accountID;
    private Integer           groupID;
    private String            name;
    private String            address;
    @Column(type = LatLng.class)
    private List<LatLng>      points;

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

    public List<LatLng> getPoints()
    {
        return points;
    }

    public void setPoints(List<LatLng> points)
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
            for (final LatLng point : points)
            {
                if (sb.length() > 0)
                    sb.append(';');
                sb.append(point);
            }
        return sb.toString();
    }

    /**
     * @param pointsString
     *            The points formatted as <code>(lat,lng);(lat,lng),...</code>
     */
    public void setPointsString(String pointsString)
    {
        points = new ArrayList<LatLng>();

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
                points.add(new LatLng(lat, lng));
            }
        }
    }
}
