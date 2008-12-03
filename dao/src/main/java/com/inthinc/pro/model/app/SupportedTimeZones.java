package com.inthinc.pro.model.app;

import java.util.ArrayList;
import java.util.List;
import com.inthinc.pro.dao.TimeZoneDAO;
import com.inthinc.pro.model.SupportedTimeZone;

public class SupportedTimeZones implements BaseAppEntity
{
    private static List<String> tzList;
    
    private TimeZoneDAO timeZoneDAO;

    public SupportedTimeZones()
    {
    }
    public void init()
    {
        tzList = new ArrayList<String>();
        List<SupportedTimeZone> supportedTzList = timeZoneDAO.getSupportedTimeZones();
        for (SupportedTimeZone supportedTimeZone : supportedTzList)
        {
            tzList.add(supportedTimeZone.getTzName());
        }
    }
    
    
    public static List<String> getSupportedTimeZones()
    {
        return tzList;
    }
    
    public TimeZoneDAO getTimeZoneDAO()
    {
        return timeZoneDAO;
    }
    public void setTimeZoneDAO(TimeZoneDAO timeZoneDAO)
    {
        this.timeZoneDAO = timeZoneDAO;
    }
    


}
