package com.inthinc.pro.model.app;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.model.Role;

public class Roles implements BaseAppEntity
{
    Logger logger = Logger.getLogger(Roles.class);
    private static List<Role> roleList;
    private static Map<Integer, Role> roleMap;
    
    private RoleDAO roleDAO;

    public Roles()
    {
    }
    
    
    public void init()
    {
        roleList = roleDAO.getRoles();
        Collections.sort(roleList);
        roleList = Collections.unmodifiableList(roleList);
        Map<Integer, Role> map = new LinkedHashMap<Integer, Role>();

        for (Role role : roleList)
        {
               map.put(role.getRoleID(), role);
        }
        roleMap = Collections.unmodifiableMap(map);
    }
    

    public static Map<Integer, Role>  getRoleMap()
    {
        return roleMap;
    }
    
    public static List<Role> getRoleList()
    {
        return roleList;
    }
    
    public static Role getRoleById(Integer id)
    {
        return getRoleMap().get(id);
    }
    public static Role getRoleByName(String  name)
    {
        for (Role role : getRoleMap().values())
        {
            if (role.getName().equals(name))
                return role;
        }
        return null;
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

