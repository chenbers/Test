package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.ZoneAlert;

public interface ZoneAlertDAO extends GenericDAO<ZoneAlert, Integer>
{
    List<ZoneAlert> getZoneAlertsInGroupHierarchy(Integer groupID);

    Integer deleteByZoneID(Integer zoneID);
}
