package com.inthinc.pro.dao;

import java.util.List;

import com.inthinc.pro.model.Role;

public interface RoleDAO extends GenericDAO<Role, Integer>
{
    List<Role> getRoles();
    
}

