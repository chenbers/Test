package com.inthinc.pro.model.security;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.inthinc.pro.dao.RoleDAO;

public class Roles {
	
    Logger logger = Logger.getLogger(Roles.class);
    private List<Role> roleList;
    private Map<Integer, Role> roleMapById;
    private Map<String,Role> roleMapByName;
    
    private RoleDAO roleDAO;
    
    public void init(Integer acctID)
    {
        roleList = roleDAO.getRoles(acctID);
        Collections.sort(roleList);
        roleMapById = new LinkedHashMap<Integer, Role>();
        roleMapByName = new LinkedHashMap<String, Role>();
        
        for (Role role : roleList)
        {
        	roleMapById.put(role.getRoleID(), role);
        	roleMapByName.put(role.getName(), role);
        }
    }
    

    public Map<Integer, Role>  getRoleMapById()
    {
        return roleMapById;
    }
    
    public  List<Role> getRoleList()
    {
        return roleList;
    }
    
    public Role getRoleById(Integer id)
    {
        return roleMapById.get(id);
    }
    public Role getRoleByName(String  name)
    {
    	return roleMapByName.get(name);
    }



    public RoleDAO getRoleDAO()
    {
        return roleDAO;
    }

    public void setRoleDAO(RoleDAO roleDAO)
    {
        this.roleDAO = roleDAO;
    }
    
    public void removeUneditableRoles(){
    	
    	Role admin = getRoleByName("Admin");
    	roleMapByName.remove("Admin");
    	roleMapById.remove(admin.getRoleID());
    	roleList.remove(admin);
    	
       	Role normal = getRoleByName("Normal");
    	roleMapByName.remove("Normal");
    	roleMapById.remove(normal.getRoleID());
    	roleList.remove(normal);
   }

}
