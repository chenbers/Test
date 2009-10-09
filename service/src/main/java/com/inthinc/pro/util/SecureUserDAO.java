package com.inthinc.pro.util;

import java.util.List;

import org.jboss.resteasy.spi.NotFoundException;
import org.jboss.resteasy.spi.UnauthorizedException;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;

public class SecureUserDAO extends BaseSecureDAO {

    private UserDAO userDAO;
    private SecurePersonDAO personDAO;
    private SecureGroupDAO groupDAO;

    private boolean isAuthorized(User user) {
        if (user != null) {
            // TODO do we give user access to all groups, regardless of the users group????
            // TODO if so, we need a fast security check to verify a group intersects with user's groups
            // TODO get Account from logged in user

            if (user.getPerson() == null) {
                Person person = personDAO.findByID(user.getPersonID());
                user.setPerson(person);
            }
 
            Group usergroup = groupDAO.findByID(user.getGroupID());

            if (getAccountID().equals(user.getPerson().getAcctID()))
                return true;

        }
        throw new UnauthorizedException("User not found");
    }

    public User findByID(Integer userID) {
        User user = userDAO.findByID(userID);
        if (user == null)
            throw new NotFoundException("userID not found: " + userID);
        
        try 
        {
            if (isAuthorized(user))
            	return user;
        	
        }
        catch (Exception ex)
        {
            throw new NotFoundException("userID not found: " + userID);
        }
        return null;
    }

    public List<User> getAll() {
        return userDAO.getUsersInGroupHierarchy(getUser().getGroupID());
    }

    public Integer create(User user) {
        if (isAuthorized(user))
        	return userDAO.create(user.getPersonID(), user);
        return -1;
    }

    public Integer update(User user) {
        if (isAuthorized(user))
            return userDAO.update(user);
        else
        	return -1;
    }

    public Integer deleteByID(Integer userID) {
        if (isAuthorized(userID))
            return userDAO.deleteByID(userID);
        else
        	return -1;
    }
    
    public boolean isAuthorized(Integer userID) {
        return isAuthorized(findByID(userID));
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

	public SecurePersonDAO getPersonDAO() {
		return personDAO;
	}

	public void setPersonDAO(SecurePersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	public SecureGroupDAO getGroupDAO() {
		return groupDAO;
	}

	public void setGroupDAO(SecureGroupDAO groupDAO) {
		this.groupDAO = groupDAO;
	}

}
