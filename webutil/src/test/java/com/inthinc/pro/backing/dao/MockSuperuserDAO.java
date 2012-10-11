package com.inthinc.pro.backing.dao;

import com.inthinc.pro.dao.SuperuserDAO;
import com.inthinc.pro.model.User;

public class MockSuperuserDAO implements SuperuserDAO {

	Boolean superuser;
	
	
	public Boolean getSuperuser() {
		return superuser;
	}

	public void setSuperuser(Boolean superuser) {
		this.superuser = superuser;
	}

	@Override
	public void clearSuperuser(Integer userID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Boolean isSuperuser(Integer userID) {
		return superuser;
	}

	@Override
	public void setSuperuser(Integer userID) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Integer create(Integer id, User entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer deleteByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findByID(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer update(User entity) {
		// TODO Auto-generated method stub
		return null;
	}

}
