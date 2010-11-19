package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.AlertEscalationItem;
import com.inthinc.pro.model.RedFlagOrZoneAlert;
import com.inthinc.pro.model.ZoneAlert;

public interface RedFlagAndZoneAlertsDAO extends GenericDAO<RedFlagOrZoneAlert, Integer>{
    
    List<RedFlagOrZoneAlert> getRedFlagAndZoneAlerts(Integer accountID);
    List<RedFlagOrZoneAlert> getRedFlagAndZoneAlertsByUserID(Integer userID);
    List<RedFlagOrZoneAlert> getRedFlagAndZoneAlertsByUserIDDeep(Integer userID);
    
    Integer deleteByEntity(RedFlagOrZoneAlert redFlagOrZoneAlert);
    
    List<ZoneAlert> getZoneAlerts(Integer accountID);
    List<ZoneAlert> getZoneAlertsByUserID(Integer userID);
    List<ZoneAlert> getZoneAlertsByUserIDDeep(Integer userID);

    Integer deleteByZoneID(Integer zoneID);


    List<AlertEscalationItem> getAlertEscalationItemsByAlert(Integer alertID);
}
