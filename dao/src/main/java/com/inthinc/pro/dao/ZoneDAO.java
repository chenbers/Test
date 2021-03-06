package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Zone;

public interface ZoneDAO extends GenericDAO<Zone, Integer>
{
    List<Zone> getZones(Integer accountID);
    
    Integer publishZones(Integer accountID);
}
