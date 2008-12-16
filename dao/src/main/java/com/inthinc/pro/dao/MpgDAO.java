package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Duration;
import com.inthinc.pro.model.Group;
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
    List<MpgEntity> getEntities(Group group, Duration duration);
}
