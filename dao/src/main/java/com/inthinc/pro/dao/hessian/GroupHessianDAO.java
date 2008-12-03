package com.inthinc.pro.dao.hessian;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.ProDAOException;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.Group;

public class GroupHessianDAO extends GenericHessianDAO<Group, Integer> implements GroupDAO
{

    @Override
    public List<Group> getGroupHierarchy(Integer acctID, Integer groupID)
    {
        try
        {
            List <Group> allGroups = getGroupsByAcctID(acctID);
            List <Group> groupHierarchy = new ArrayList<Group>();
            for (Group group : allGroups)
            {
                if (group.getGroupID().equals(groupID))
                {
                    groupHierarchy.add(group);
                    addChildren(allGroups, groupHierarchy, group.getGroupID());
                    break;
                }
            }
            
            
            return groupHierarchy;
            
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }
    
    private void addChildren(List<Group> allGroups , List<Group> groupHierarchy, Integer parentID)
    {
        for (Group group : allGroups)
        {
            if (group.getParentID().equals(parentID))
            {
                groupHierarchy.add(group);
                addChildren(allGroups, groupHierarchy, group.getGroupID());
            }
        }
        
    }
    
    @Override
    public List<Group> getGroupsByAcctID(Integer acctID)
    {
        try
        {
            List<Map<String, Object>> groups = getSiloService().getGroupsByAcctID(acctID);
            return getMapper().convertToModelObject(groups, Group.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }


}
