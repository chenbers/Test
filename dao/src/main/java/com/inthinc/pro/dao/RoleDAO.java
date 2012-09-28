package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.security.AccessPoint;
import com.inthinc.pro.model.security.Role;
import com.inthinc.pro.model.security.SiteAccessPoint;

public interface RoleDAO extends GenericDAO<Role, Integer>
{
    List<Role> getRoles(Integer acctID);
    
    List<SiteAccessPoint> getSiteAccessPts();
    
    List<AccessPoint> getUsersAccessPts(Integer userID);
    
}

