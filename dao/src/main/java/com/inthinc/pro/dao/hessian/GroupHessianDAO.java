package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.dao.hessian.proserver.CentralService;
import com.inthinc.pro.model.Group;

public class GroupHessianDAO extends GenericHessianDAO<Group, Integer, CentralService> implements GroupDAO
{

    @Override
    public List<Group> getGroupHierarchy(Integer groupID)
    {
        try
        {
            return convertToModelObject(getSiloService().getGroupHierarchy(groupID));
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
    }

}
