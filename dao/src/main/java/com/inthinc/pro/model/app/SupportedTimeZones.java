package com.inthinc.pro.model.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.dao.TimeZoneDAO;
import com.inthinc.pro.model.SupportedTimeZone;

public class SupportedTimeZones implements BaseAppEntity
{
    private static List<String> tzList;
    
    private static Map<Integer, String> tzMap;
    
    private TimeZoneDAO timeZoneDAO;

    public SupportedTimeZones()
    {
    }
    public void init()
    {
        tzList = new ArrayList<String>();
        tzMap = new HashMap<Integer, String>();
        List<SupportedTimeZone> supportedTzList = timeZoneDAO.getSupportedTimeZones();
        for (SupportedTimeZone supportedTimeZone : supportedTzList)
        {
            tzList.add(supportedTimeZone.getTzName());
            tzMap.put(supportedTimeZone.getTzID(), supportedTimeZone.getTzName());
        }
    }
    
    public static String lookup(Integer tzID)
    {
        return tzMap.get(tzID);
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
