package com.inthinc.pro.backing.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;

public class GroupHierarchy implements Serializable
{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2855781183498326570L;

	public static final String GROUP_SEPERATOR = " - ";
//	List<Group> groupList;
	Map<Integer, Group> groupMap;

    public GroupHierarchy()
    {
        groupMap = new TreeMap<Integer,Group>();
    }
    
    public GroupHierarchy(List<Group> groupList)
    {
//        this.groupList = groupList;
        setGroupList(groupList);
    }

    public List<Group> getGroupList()
    {
        return new ArrayList<Group>(groupMap.values());
    }

    public void setGroupList(List<Group> groupList)
    {
        groupMap = new TreeMap<Integer,Group>();
        for(Group group:groupList){
            groupMap.put(group.getGroupID(), group);
        }
    }
    public List<Group> getSubGroupList(Integer groupID)
    {
    	Group group = getGroup(groupID);
    	if (group == null)
    		return null;
    	
    	List<Group> subGroupList = new ArrayList<Group>();
    	subGroupList.add(group);
    	for (Group subgroup : groupMap.values()) {
    		
    		if (group.getGroupID().equals(subgroup.getParentID())) {
    			List<Group> subList = getSubGroupList(subgroup.getGroupID());
    			if (subList != null)
    				subGroupList.addAll(subList);
    		}
    	}
    	
        return subGroupList;
    }
    
    public Group getTopGroup()
    {
        return groupMap.values().iterator().next();
    }
    
    public List<Group> getGroupsByLevel(GroupLevel groupLevel){
    	List<Group> filteredList = new ArrayList<Group>();
    	for(Group group: groupMap.values()){
    		if(getGroupLevel(group) == groupLevel){
    			filteredList.add(group);
    		}
    	}
    	
    	return filteredList;
    }

    public GroupLevel getGroupLevel(Integer groupID)
    {
        for (Group group : groupMap.values())
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
        return groupMap.get(groupID);
//        for (Group group : groupList)
//        {
//            if (group.getGroupID().equals(groupID))
//            {
//                return group;
//            }
//        }
//        return null;
    }
    
    public String getFullGroupName(Integer groupID)
    {
    	StringBuilder builder = new StringBuilder();
//        for (Group group : groupList)
//        {
//            if (group.getGroupID().equals(groupID))
//            {
    	Group group = groupMap.get(groupID);
    	if (group.getParentID() != null && group.getParentID().intValue() != 0)
    	{
    		builder.append(getFullGroupName(group.getParentID()));
    	}
    	builder.append(group.getName());
    	builder.append(GROUP_SEPERATOR);
//            }
//        }
        return builder.toString();
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
        for (Group group : groupMap.values())
        {
            if (group.getParentID().equals(parent.getGroupID()))
            {
            	children.add(group);
            }
        }
        
        return children.size()>0?children:null;
    }
    
    public Group getParentGroup(Group childGroup){
    	if(childGroup == null)
    	{
    		return null;
    	}
    	Group parentGroup = null;
    	for(Group group: groupMap.values()){
    		if(group.getGroupID().equals(childGroup.getParentID())){
    			parentGroup = group;
    		}
    	}
    	
    	return parentGroup;
    }
    
//    private boolean groupHasChildren(Group groupToCheck)
//    {
//        for (Group group : groupMap.values())
//        {
//            if (group.getParentID().equals(groupToCheck.getGroupID()))
//            {
//                return true;
//            }
//        }
//        return false;
//    }
    
    public boolean containsGroupTypes(List<GroupType> groupTypeList)
    {
        for(Group group: groupMap.values())
        {
            if(groupTypeList.contains(group.getType())){
                return true;
            }
        }
        
        return false;
    }
    
    
    /**
     * 
     * @param  groupID The child group for which we are request it's parents
     * @param  heirarchyLevel Is the number of levels proceed up the hierarchy tree.
     * @return Returns an ordered list of groups. The list of groups (starting with the lowest level first) contains 
     *         the the complete hierarchy from the supplied group and up. 
     *         (Does not contain any groups that are lower than the given group)
     */
    public List<Group> getGroupHierarchicalList(Integer groupID,Integer heirarchyLevel)
    {
    	Group tempGroup = getGroup(groupID);
    	int counter = 0;
    	List<Group> groupHierarchicalList = new ArrayList<Group>();
    	groupHierarchicalList.add(tempGroup);
    	while(getParentGroup(tempGroup) != null && counter < heirarchyLevel){
    		Group parentGroup = getParentGroup(tempGroup);
    		groupHierarchicalList.add(0, parentGroup);
    		
    		tempGroup = parentGroup;
    		counter++;
    	}
    	
    	return groupHierarchicalList;
    }    
}
