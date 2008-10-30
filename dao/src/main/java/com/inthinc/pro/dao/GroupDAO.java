package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Group;

public interface GroupDAO extends GenericDAO<Group, Integer>
{
    List<Group> getGroupHierarchy(Integer groupID);
}
