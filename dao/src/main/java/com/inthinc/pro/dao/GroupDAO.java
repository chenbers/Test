package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Group;

public interface GroupDAO extends GenericDAO<Group, Integer>
{
    List<Group> getGroupHierarchy(Integer acctID, Integer groupID);
    
    List<Group> getGroupsByAcctID(Integer acctID);
    
    Integer delete(Group group);

}
