package com.inthinc.pro.security.userdetails;



import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.security.AccessPoint;
import com.inthinc.pro.model.security.Roles;
import com.inthinc.pro.model.app.SiteAccessPoints;

public class ProUserServiceImpl implements UserDetailsService
{
    private static final Logger logger = Logger.getLogger(ProUserServiceImpl.class);
    
    private UserDAO userDAO;
    private RoleDAO roleDAO;
    private GroupDAO groupDAO;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException
    {
        logger.debug("ProUserServiceImpl:loadUserByUsername " + username);
        try
        {
            
            User user = lookup(username);
            if (user == null)
            {
                throw new UsernameNotFoundException("Username could not be found");
            }    
            user.setAccessPoints(roleDAO.getUsersAccessPts(user.getUserID()));

            ProUser proUser = new ProUser(user, getGrantedAuthorities(user));

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

	public RoleDAO getRoleDAO(){ 
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}

	private GrantedAuthority[] getGrantedAuthorities(User user){
		
        
        List<GrantedAuthorityImpl> grantedAuthoritiesList = new ArrayList<GrantedAuthorityImpl>();		

		// this will take into account the site access points instead of the original roles as follows
		if(userIsAdmin(user)){
			
			//Will cover all access points
			grantedAuthoritiesList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
		}
		else{
			
			for(AccessPoint ap:user.getAccessPoints()){

				grantedAuthoritiesList.add(new GrantedAuthorityImpl(SiteAccessPoints.getAccessPointById(ap.getAccessPtID()).toString()));
				
			}
		}
		grantedAuthoritiesList.add(new GrantedAuthorityImpl("ROLE_NORMAL"));
		
	 	GrantedAuthority[] grantedAuthorities = new GrantedAuthorityImpl[grantedAuthoritiesList.size()];
		
		return grantedAuthoritiesList.toArray(grantedAuthorities);
	}

	private boolean userIsAdmin(User user){
		
        Roles roles = new Roles();
        roles.setRoleDAO(roleDAO);
        roles.init(user.getPerson().getAcctID());
        
		if(!user.hasRoles()) return false;
		List<Integer> userRoles = user.getRoles();

		for(Integer id:userRoles){
			
			if (roles.getRoleById(id).getName().equals("Admin")){
				
				return true;
			}
		}
		return false;
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
