package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.RedFlag;


public interface RedFlagDAO extends GenericDAO<RedFlag, Integer>
{
    /**
     * Retrieve the list of red flags all of the drivers under the specified group.
     * 
     * @param groupID
     * @return
     */
    List<RedFlag> getRedFlags(Integer groupID);
}
