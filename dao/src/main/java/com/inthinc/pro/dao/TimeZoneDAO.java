package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.SupportedTimeZone;

public interface TimeZoneDAO extends GenericDAO<SupportedTimeZone, Integer>
{
    List<SupportedTimeZone> getSupportedTimeZones();
    
}
