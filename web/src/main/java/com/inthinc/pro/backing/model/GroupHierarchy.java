package com.inthinc.pro.backing.model;

import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.Group;

public class GroupHierarchy
{
    List<Group> groupList;

    public GroupHierarchy()
    {
        
    }
    public GroupHierarchy(List<Group> groupList)
    {
        this.groupList = groupList;
    }

    public List<Group> getGroupList()
    {
        return groupList;
    }

    public void setGroupList(List<Group> groupList)
    {
        this.groupList = groupList;
    }
    
    public Group getTopGroup()
    {
        return groupList.get(0);
    }

    public GroupLevel getGroupLevel(Integer groupID)
    {
        for (Group group : groupList)
        {
            if (group.getGroupID().equals(groupID))
            {
                return getGroupLevel(group);
            }
        }
        return null;
    }
    public Group getGroup(Integer groupID)
    {
        for (Group group : groupList)
        {
            if (group.getGroupID().equals(groupID))
            {
                return group;
            }
        }
        return null;
    }

    public GroupLevel getGroupLevel(Group group)
    {
        if (group.getParentID().intValue() == 0)
        {
            return GroupLevel.FLEET;
        }
        
        if (!groupHasChildren(group))
        {
            return GroupLevel.TEAM;
        }
        
        
        return GroupLevel.REGION;
        
    }
    public List<Group> getChildren(Group parent){
    	
    	List<Group> children = new ArrayList<Group>();
        for (Group group : groupList)
        {
            if (group.getParentID().equals(parent.getGroupID()))
            {
            	children.add(group);
            }
        }
        
        return children.size()>0?children:null;
    }
    
    private boolean groupHasChildren(Group groupToCheck)
    {
        for (Group group : groupList)
        {
            if (group.getParentID().equals(groupToCheck.getGroupID()))
            {
                return true;
            }
        }
        return false;
    }
    
}
