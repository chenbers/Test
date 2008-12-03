package com.inthinc.pro.dao.hessian;

import java.util.Collections;
import java.util.List;

import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Role;

public class RoleHessianDAO extends GenericHessianDAO<Role, Integer> implements RoleDAO
{

    @Override
    public List<Role> getRoles()
    {
        try
        {
            return getMapper().convertToModelObject(getSiloService().getRoles(), Role.class);
        }
        catch (EmptyResultSetException e)
        {
            return Collections.emptyList();
        }
        
    }

}
