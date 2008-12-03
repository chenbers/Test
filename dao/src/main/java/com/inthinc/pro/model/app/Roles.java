package com.inthinc.pro.model.app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.model.Role;

public class Roles implements BaseAppEntity
{
    Logger logger = Logger.getLogger(Roles.class);
    private static Map<Integer, Role> rolesMap;
    
    private RoleDAO roleDAO;

    public Roles()
    {
    }
    
    
    public void init()
    {
        List<Role> rolesList = roleDAO.getRoles();
        rolesMap = new HashMap<Integer, Role>();

        for (Role role : rolesList)
        {
               rolesMap.put(role.getRoleID(), role);
        }
    }
    

    public static Map<Integer, Role>  getRoles()
    {
        return rolesMap;
    }
    
    public static Role getRoleById(Integer id)
    {
        return getRoles().get(id);
    }


    public RoleDAO getRoleDAO()
    {
        return roleDAO;
    }


    public void setRoleDAO(RoleDAO roleDAO)
    {
        this.roleDAO = roleDAO;
    }
    

}

