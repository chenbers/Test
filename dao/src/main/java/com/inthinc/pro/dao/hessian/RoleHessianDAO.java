package com.inthinc.pro.dao.hessian;

import java.util.List;

import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.model.Role;

public class RoleHessianDAO extends GenericHessianDAO<Role, Integer> implements RoleDAO
{

    @Override
    public List<Role> getRoles()
    {
        // don't catch the empty result set exception for this, because an empty result set in this case is a real error
        return getMapper().convertToModelObject(getSiloService().getRoles(), Role.class);
    }

}
