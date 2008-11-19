package com.inthinc.pro.dao.mock.proserver;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.dao.mock.data.MockData;
import com.inthinc.pro.dao.mock.data.SearchCriteria;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;

public abstract class AbstractServiceMockImpl
{

    public AbstractServiceMockImpl()
    {
        super();
    }

    protected void addChildren(List<Group> hierarchyGroups, List<Group> allGroups, Integer groupID)
    {
        for (Group group : allGroups)
        {
            if (group.getParentID().equals(groupID))
            {
                hierarchyGroups.add(group);
                addChildren(hierarchyGroups, allGroups, group.getGroupID());
            }
        }
    
    }

    protected List<Driver> getAllDriversInGroup(Group topGroup)
    {
        // TODO Auto-generated method stub
        List<Group> hierarchyGroups = new ArrayList<Group>();
        hierarchyGroups.add(topGroup);
    
        // filter out just the ones in the hierarchy
        List<Group> allGroups = MockData.getInstance().lookupObjectList(Group.class, new Group());
        addChildren(hierarchyGroups, allGroups, topGroup.getGroupID());
    
        List<Driver> returnDriverList = new ArrayList<Driver>();
        for (Group group : hierarchyGroups)
        {
            SearchCriteria searchCriteria = new SearchCriteria();
            searchCriteria.addKeyValue("groupID", group.getGroupID());
    
            List<Driver> driverList = MockData.getInstance().retrieveObjectList(Driver.class, searchCriteria);
            returnDriverList.addAll(driverList);
    
        }
    
        return returnDriverList;
    }

}