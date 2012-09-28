package com.inthinc.pro.dao;

import com.inthinc.pro.model.zone.ZonePublish;
import com.inthinc.pro.model.zone.option.type.ZoneVehicleType;

public interface ZonePublishDAO extends GenericDAO<ZonePublish, Integer> {
    
    void publishZone(ZonePublish zonePublish);
    
    ZonePublish getPublishedZone(Integer acctID, ZoneVehicleType zoneVehicleType);
    ZonePublish getPublishedZone(String mcmID);
    boolean zonesNeedPublish(Integer acctID);
	
}
