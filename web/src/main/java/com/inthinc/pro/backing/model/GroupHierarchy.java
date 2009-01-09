package com.inthinc.pro.backing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.inthinc.pro.model.Group;

public class GroupHierarchy implements Serializable
{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2855781183498326570L;
	
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
    
    public List<Group> getGroupsByLevel(GroupLevel groupLevel){
    	List<Group> filteredList = new ArrayList<Group>();
    	for(Group group: groupList){
    		if(getGroupLevel(group) == groupLevel){
    			filteredList.add(group);
    		}
    	}
    	
    	return filteredList;
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
        GroupLevel groupLevel = GroupLevel.DIVISION;
        switch(group.getType())
        {
        case FLEET: groupLevel = GroupLevel.FLEET;break;
        case DIVISION: groupLevel = GroupLevel.DIVISION;break;
        case TEAM: groupLevel = GroupLevel.TEAM;break;
        }

        return groupLevel;
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
    
    public Group getParentGroup(Group childGroup){
    	Group parentGroup = null;
    	for(Group group: groupList){
    		if(group.getGroupID().equals(childGroup.getParentID())){
    			parentGroup = group;
    		}
    	}
    	
    	return parentGroup;
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
