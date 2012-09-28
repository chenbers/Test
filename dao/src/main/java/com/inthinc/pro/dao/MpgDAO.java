package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.MpgEntity;

public interface MpgDAO extends GenericDAO<MpgEntity, Integer>
{
    
     /**
     * Retrieve the list of {@link MpgEntity} objects for the sub groups or drivers (one level down) under the specified group.
     * 
     * @param groupID
     * @param startDate
     * @param endDate
     * @return
     */
    List<MpgEntity> getEntities(Group group, Duration duration, GroupHierarchy gh);
    
    /**
    * Retrieve a list of MpgEntity objects for a driver.
    * 
    * @param driverID
    * @param mileage
    * @param count
    * @return
    */
    List<MpgEntity> getDriverEntities(Integer driverID, Duration duration, Integer count);
    
    /**
     * Retrieve a list of MpgEntity objects for a vehicle.
     * 
     * @param vehicleID
     * @param mileage
     * @param count
     * @return
     */
     List<MpgEntity> getVehicleEntities(Integer vehicleID, Duration duration, Integer count);
}
