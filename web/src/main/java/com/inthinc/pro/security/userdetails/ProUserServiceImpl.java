package com.inthinc.pro.security.userdetails;



import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;


public class ProUserServiceImpl implements UserDetailsService
{
    private static final Logger logger = Logger.getLogger(ProUserServiceImpl.class);
    
    private UserDAO userDAO;
    private GroupDAO groupDAO;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
    {
        logger.debug("ProUserServiceImpl:loadUserByUsername " + username);
        try
        {
            User user = lookup(username);
            if (user == null || !user.getStatus().equals(Status.ACTIVE))
            {
                throw new UsernameNotFoundException("Username could not be found");
            }    
            ProUser proUser = new ProUser(user, user.getRole().toString());
            Group topGroup = groupDAO.findByID(user.getGroupID());
            List<Group> groupList = groupDAO.getGroupHierarchy(topGroup.getAccountID(), user.getGroupID());                  
            proUser.setGroupHierarchy(new GroupHierarchy(groupList));
            return proUser;
        }
        catch (EmptyResultSetException ex)
        {
            throw new UsernameNotFoundException("Username could not be found");
        }
    }

    private User lookup(String username)
    {
        logger.debug("lookup: " + username);
        
        return userDAO.findByUserName(username);
    }

    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO)
    {
        logger.debug("setUserDAO");
        this.userDAO = userDAO;
    }

    public GroupDAO getGroupDAO()
    {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO)
    {
        this.groupDAO = groupDAO;
    }
}
