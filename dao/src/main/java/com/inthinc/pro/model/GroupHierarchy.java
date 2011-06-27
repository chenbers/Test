package com.inthinc.pro.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class GroupHierarchy implements Serializable
{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 2855781183498326570L;

	public static final String GROUP_SEPERATOR = " - ";
	public static final String CONTINUATION_PREFIX = "..";
	public static final int MAX_LEVEL = 2;
	Map<Integer, Group> groupMap;

    public GroupHierarchy()
    {
        groupMap = new TreeMap<Integer,Group>();
    }
    
    public GroupHierarchy(List<Group> groupList)
    {
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
    public List<Integer> getSubGroupIDList(Integer groupID)
    {
        List<Group> subGroupList = getSubGroupList(groupID);
        List<Integer> idList = new ArrayList<Integer>();

        if (subGroupList != null) {
            for (Group group : subGroupList) {
                idList.add(group.getGroupID());
            }
        }
        
        return idList;
    }

    public Group getTopGroup() {
    	Group initialGroup = groupMap.values().iterator().next();
    	return getTopGroup(initialGroup);
    }
    private Group getTopGroup(Group childGroup) {
    	Group potentialParent = getParentGroup(childGroup);
    	if(potentialParent == null)
    		return childGroup;
    	return getTopGroup(potentialParent);
    }
    public Group getGroup(Integer groupID)
    {
        try{
            return groupMap.get(groupID);
        }
        catch(NullPointerException npe){
            
            return null;
        }
        
    }
    
    public String getFullGroupName(Integer groupID) {
        return getFullGroupName(groupID, GROUP_SEPERATOR);
    }
    
    public String getFullGroupName(Integer groupID, String separator)
    {
    	StringBuilder builder = new StringBuilder();
    	Group group = groupMap.get(groupID);
    	if (group == null) 
    	    return "";
    	if (group.getParentID() != null && group.getParentID().intValue() != 0)
    	{
    		builder.append(getFullGroupName(group.getParentID(), separator));
    	}
    	if (builder.length() != 0)
            builder.append(separator);
    	builder.append(group.getName());
        return builder.toString();
    }
    
    /**
     * Get the truncated group path hierarchy
     * If the truncated group path ends by the separator, then this last separator is removed from the path
     * 
     * @param groupID    The group id from which we want the truncated hierarchy
     * @param separator  The separator used between child and parent groups
     */
    
    public String getShortGroupName(Integer groupID, String separator) {
        String result =  getShortGroupName(groupID, separator, 0);
        if (result.endsWith(separator)) {
            result = result.substring(0, result.length() - separator.length());
        }
        return result;
    }
    
    /**
     * Get the truncated group path hierarchy recursively
     * If we reached the max level group, we stop going up the hierarchy and place the continuation prefix
     * Otherwise we apply this method to the parent group
     * 
     * @param groupID    The group id from which we want the truncated hierarchy
     * @param separator  The separator used between child and parent groups
     * @param level      Level of the current group (from the leaf)
     */
    
    private String getShortGroupName(Integer groupID, String separator,int level)
    {
        StringBuilder builder = new StringBuilder();
        Group group = groupMap.get(groupID);
        if (group == null) 
            return "";
        
        if(level > MAX_LEVEL){
            builder.append(CONTINUATION_PREFIX + separator);
        }
        else 
        {   
            if (group.getParentID() != null && group.getParentID().intValue() != 0)
                builder.append(getShortGroupName(group.getParentID(), separator, level + 1));
            builder.append(group.getName() + separator);
        }    
        
        return builder.toString();
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
    
    public TreeMap<String, Integer> getTeams()
    {        
        final TreeMap<String, Integer> teams = new TreeMap<String, Integer>();
        for (final Group group : getGroupList())
            if (group.getType() == GroupType.TEAM) {
                String fullName = getFullGroupName(group.getGroupID());
                if (fullName.endsWith(GroupHierarchy.GROUP_SEPERATOR)) {
                    fullName = fullName.substring(0, fullName.length() - GroupHierarchy.GROUP_SEPERATOR.length());
                }
                teams.put(fullName, group.getGroupID());
        }
        return teams;
        
    }

}
