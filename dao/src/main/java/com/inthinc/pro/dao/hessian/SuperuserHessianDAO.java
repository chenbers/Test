package com.inthinc.pro.dao.hessian;

import java.util.Map;

import com.inthinc.pro.dao.SuperuserDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.User;

public class SuperuserHessianDAO  extends GenericHessianDAO<User, Integer> implements SuperuserDAO {

	private static final long serialVersionUID = 961549745816090466L;

	@Override
	public void clearSuperuser(Integer userID) {
		getSiloService().clearSuperuser(userID);
		
	}

	@Override
	public Boolean isSuperuser(Integer userID) {
		try {
			Map<String, Object> returnMap = getSiloService().isSuperuser(userID);
			return getCount(returnMap) == 1;
		} catch (EmptyResultSetException ex) {
			return false;
		}
	}

	@Override
	public void setSuperuser(Integer userID) {
		getSiloService().setSuperuser(userID);
	}

}
