package com.inthinc.pro.dao;

import java.util.List;
 
import com.inthinc.pro.model.RedFlagAlert;

public interface RedFlagAlertDAO extends GenericDAO<RedFlagAlert, Integer>
{
    List<RedFlagAlert> getRedFlagAlerts(Integer accountID);
    List<RedFlagAlert> getRedFlagAlertsByUserID(Integer userID);
    List<RedFlagAlert> getRedFlagAlertsByUserIDDeep(Integer userID);
    List<RedFlagAlert> getAlertsByTeamGroupID(Integer groupID);
    
    Integer deleteAlertsByZoneID(Integer zoneID);
}
