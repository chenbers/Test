package com.inthinc.pro.dao.mock.data;

import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import com.inthinc.pro.model.SupportedTimeZone;

public class MockTimeZones
{
    
    static List<SupportedTimeZone> tzList = new ArrayList<SupportedTimeZone>();
    static
    {
        tzList.add(new SupportedTimeZone(1, "US/Mountain"));
        tzList.add(new SupportedTimeZone(2, "US/Central"));
        tzList.add(new SupportedTimeZone(3, "US/Eastern"));
        tzList.add(new SupportedTimeZone(4, "US/Hawaii"));
        tzList.add(new SupportedTimeZone(5, "US/Pacific"));
        
    }

    public static List<SupportedTimeZone> getAll()
    {
        return tzList;
    }
    public static TimeZone getRandomTimezone()
    {
        int tz = randomInt(0, tzList.size()-1);
        return TimeZone.getTimeZone(tzList.get(tz).getTzName());
    }
    static int randomInt(int min, int max)
    {
        return (int) (Math.random() * ((max - min) + 1)) + min;
    }

}
