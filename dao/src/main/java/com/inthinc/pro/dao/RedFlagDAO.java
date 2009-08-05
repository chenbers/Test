package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.RedFlag;


public interface RedFlagDAO extends GenericDAO<RedFlag, Integer>
{
    public static final Integer EXCLUDE_FORGIVEN = 0;
    public static final Integer INCLUDE_FORGIVEN = 1;
    /**
     * Retrieve the list of red flags all of the drivers under the specified group.
     * 
     * @param groupID
     * @return
     */
    List<RedFlag> getRedFlags(Integer groupID, Integer daysBack, Integer includeForgiven);
}
