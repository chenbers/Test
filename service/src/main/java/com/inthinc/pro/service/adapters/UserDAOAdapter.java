/**
 * 
 */
package com.inthinc.pro.service.adapters;

import java.util.List;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;

/**
 * Adapter for the User resources.
 *   
 * @author dcueva
 *
 */
public class UserDAOAdapter extends BaseDAOAdapter<User> {

	private UserDAO userDAO;	
	
	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected GenericDAO<User, Integer> getDAO() {
		return userDAO;
	}

    /**
     * Retrieve the ID to be used in the creation of the user. </br>
     * Overriding because we need the Person ID, not the account ID (default).</br>
     * The create() method from BaseDAOAdapter will call this overriden method.</br>
     * 
     * @see com.inthinc.pro.service.adapters.BaseDAOAdapter#getResourceCreationID(java.lang.Object)
	 */
	@Override
	protected Integer getResourceCreationID(User user) {
		return user.getPersonID();
	}

	@Override
	protected Integer getResourceID(User user) {
		return user.getUserID();
	}
	
}
