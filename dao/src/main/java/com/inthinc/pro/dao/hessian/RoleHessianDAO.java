package com.inthinc.pro.dao.hessian;

import java.util.List;

import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.model.security.AccessPoint;
import com.inthinc.pro.model.security.Role;
import com.inthinc.pro.model.security.SiteAccessPoint;

@SuppressWarnings("serial")
public class RoleHessianDAO extends GenericHessianDAO<Role, Integer> implements RoleDAO
{
	
	@Override
	public List<Role> getRoles(Integer acctID) {
		
        return getMapper().convertToModelObject(getSiloService().getRolesByAcctID(acctID), Role.class);	
        
	}

	@Override
	public List<SiteAccessPoint> getSiteAccessPts() {

        return getMapper().convertToModelObject(getSiloService().getSiteAccessPts(), SiteAccessPoint.class);
	}
	@Override
	public List<AccessPoint> getUsersAccessPts(Integer userID) {
		// TODO Auto-generated method stub
		return getMapper().convertToModelObject(getSiloService().getUsersAccessPts(userID),AccessPoint.class);
	}
 
}
