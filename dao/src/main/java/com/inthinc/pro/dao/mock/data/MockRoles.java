package com.inthinc.pro.dao.mock.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.Role;


public class MockRoles
{
    static Map<Integer, Role> allRoles = new HashMap<Integer, Role>();
    static
    {
        Integer key = 1;
        key = 1;
        allRoles.put(key, new Role(key++, "readOnly"));
        allRoles.put(key, new Role(key++, "normalUser"));
        allRoles.put(key, new Role(key++, "supervisor"));
        allRoles.put(key, new Role(key++, "customUser"));
        allRoles.put(key, new Role(key++, "superUser"));
    }
    
    public static List<Role> getAll()
    {
        
        List<Role> roleList = new ArrayList<Role>(allRoles.values());
        return roleList;
    }

    public static Role getNormalUser()
    {
        return allRoles.get(2);
    }

    public static Role getCustomUser()
    {
        return allRoles.get(4);
    }

    public static Role getReadOnlyUser()
    {
        return allRoles.get(1);
    }

    public static Role getSuperUser()
    {
        return allRoles.get(5);
    }

    public static Role getSupervisor()
    {
        return allRoles.get(3);
    }


}
