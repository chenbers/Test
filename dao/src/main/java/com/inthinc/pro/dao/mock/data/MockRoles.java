package com.inthinc.pro.dao.mock.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.inthinc.pro.model.security.AccessPoint;
import com.inthinc.pro.model.security.Role;


public class MockRoles
{
    static Map<Integer, Role> allRoles = new HashMap<Integer, Role>();
    static Map<Integer, AccessPoint> allAccessPoints = new HashMap<Integer,AccessPoint>();
    static
    {
        Integer key = 1;
        allAccessPoints.put(key, new AccessPoint(key++,15));
        allAccessPoints.put(key, new AccessPoint(key++,15));
        allAccessPoints.put(key, new AccessPoint(key++,15));
        allAccessPoints.put(key, new AccessPoint(key++,15));
        allAccessPoints.put(key, new AccessPoint(key++,15));
        allAccessPoints.put(key, new AccessPoint(key++,15));
 
        key = 1;

//        allRoles.put(key, new Role(1, key++, "readOnly"));
//        allRoles.put(key, new Role(1, key++, "normalUser"));
//        allRoles.put(key, new Role(1, key++, "supervisor"));
//        allRoles.put(key, new Role(1, key++, "customUser"));
//        allRoles.put(key, new Role(1, key++, "superUser"));
        allRoles.put(key, new Role(1, key++, "Admin",new ArrayList<AccessPoint>(allAccessPoints.values())));
        allRoles.put(key, new Role(1, key++, "Normal",new ArrayList<AccessPoint>(allAccessPoints.values())));
        
        
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

    public static Role getAdminUser()
    {
        return allRoles.get(1);
    }

//    public static Role getReadOnlyUser()
//    {
//        return allRoles.get(1);
//    }
//
//    public static Role getSuperUser()
//    {
//        return allRoles.get(5);
//    }
//
//    public static Role getSupervisor()
//    {
//        return allRoles.get(3);
//    }
    public static List<AccessPoint> getAllAccessPoints()
    {
        
        List<AccessPoint> apList = new ArrayList<AccessPoint>(allAccessPoints.values());
        return apList;
    }


}
