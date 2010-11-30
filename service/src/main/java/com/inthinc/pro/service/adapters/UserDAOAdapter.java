package com.inthinc.pro.service.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.inthinc.pro.dao.GenericDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;

/**
 * Adapter for the User resources.
 *   
 * @author dcueva
 *
 */
@Component
public class UserDAOAdapter extends BaseDAOAdapter<User> {

    @Autowired
	private UserDAO userDAO;	
	
	@Override
	public List<User> getAll() {
        return userDAO.getUsersInGroupHierarchy(getGroupID());
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

	// Specialized methods ----------------------------------------------------

    public User findByUserName(String userName) {
    	return userDAO.findByUserName(userName);
    }	

    public User login(String userName, String password) {
    	// Advice will take care of verifying user credentials
    	return userDAO.findByUserName(userName);
    }

	// Getters and setters -----------------------------------------------------
    
	/**
	 * @return the userDAO
	 */
	public UserDAO getUserDAO() {
		return userDAO;
	}

	/**
	 * @param userDAO the userDAO to set
	 */
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}    
    
}
