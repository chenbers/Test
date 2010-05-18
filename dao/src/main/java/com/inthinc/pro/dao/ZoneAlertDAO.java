package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.ZoneAlert;

public interface ZoneAlertDAO extends GenericDAO<ZoneAlert, Integer>
{
    List<ZoneAlert> getZoneAlerts(Integer accountID);
    List<ZoneAlert> getZoneAlertsByUserID(Integer userID);
    List<ZoneAlert> getZoneAlertsByUserIDDeep(Integer userID);

    Integer deleteByZoneID(Integer zoneID);
}
