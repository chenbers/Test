package com.inthinc.pro.reports.hos.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.inthinc.pro.model.Group;

public class GroupHierarchy {
    Map<Integer, Group> groupMap;

    public GroupHierarchy()
    {
        groupMap = new HashMap<Integer,Group>();
    }
    
    public GroupHierarchy(Group parentGroup, List<Group> descendentsList)
    {
        groupMap = new HashMap<Integer,Group>();
        groupMap.put(parentGroup.getGroupID(), parentGroup);
        for(Group group:descendentsList){
            groupMap.put(group.getGroupID(), group);
        }
        
    }
    
    public String getFullName(Group group) {
        
        StringBuffer buffer = new StringBuffer();
        
        Group parentGroup = getGroup(group.getParentID());
        if (parentGroup != null) {
            buffer.append(getFullName(parentGroup));
            buffer.append("->");
        }
        buffer.append(group.getName());
        
        
        return buffer.toString();
    }


    public List<Group> getChildren(Group parentGroup) {
        
        List<Group> childList = new ArrayList<Group>();
        
        for (Group group : groupMap.values())
            if (group.getParentID().equals(parentGroup.getGroupID()))
                childList.add(group);
        
        return childList;
    }
    
    public Group getGroup(Integer groupID)
    {
        return groupMap.get(groupID);
    }
    
    public Group getTopAncestor(Group driverGroup, List<Group> ancestorList) {
        
        for (Group group : ancestorList) {
            if (group.getGroupID().equals(driverGroup.getGroupID()))
                return group;
        }
        Group parent = getGroup(driverGroup.getParentID());
        if (parent == null)
            return null;
        return getTopAncestor(parent, ancestorList);
    }
}
