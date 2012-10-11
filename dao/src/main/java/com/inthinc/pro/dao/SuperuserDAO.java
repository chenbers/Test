package com.inthinc.pro.dao;

import com.inthinc.pro.model.User;

public interface SuperuserDAO extends GenericDAO<User, Integer> {
	
    void setSuperuser(Integer userID);
    Boolean isSuperuser(Integer userID);
    void clearSuperuser(Integer userID);
    


}
