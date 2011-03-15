package com.inthinc.pro.security.userdetails;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.security.Authentication;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.security.userdetails.UsernameNotFoundException;

//import com.inthinc.pro.backing.ExternalConfigBean;
import com.inthinc.pro.backing.PersonBean;
import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.DriverDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.ZoneDAO;
import com.inthinc.pro.dao.hessian.exceptions.EmptyResultSetException;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Zone;
import com.inthinc.pro.model.app.SiteAccessPoints;
import com.inthinc.pro.model.security.AccessPoint;
import com.inthinc.pro.model.security.Roles;


public class ProUserServiceImpl implements UserDetailsService
{
    private static final Logger logger = Logger.getLogger(ProUserServiceImpl.class);
    
    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private ZoneDAO zoneDAO;
    private RoleDAO roleDAO;
    private AccountDAO accountDAO;
    private DriverDAO driverDAO;
    
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
            
            Account account = accountDAO.findByID(user.getPerson().getAcctID());        
            
            user.setAccessPoints(roleDAO.getUsersAccessPts(user.getUserID()));
            
            boolean loginDaysRemaining = PersonBean.getLoginDaysRemaining(account, user) > 0;
            boolean passwordDaysRemaining = PersonBean.getPasswordDaysRemaining(account, user) > 0;

            boolean isAdmin = userIsAdmin(user);
            ProUser proUser = new ProUser(user, loginDaysRemaining, passwordDaysRemaining, getGrantedAuthorities(user, isAdmin, account.getHos() == AccountHOSType.HOS_SUPPORT ));
            proUser.setAdmin(isAdmin);
            
            Group topGroup = groupDAO.findByID(user.getGroupID());
            List<Group> accountGroupList = groupDAO.getGroupsByAcctID(topGroup.getAccountID());
            proUser.setAccountGroupHierarchy(new GroupHierarchy(accountGroupList));
            List<Group> groupList = getUserGroupList(accountGroupList, user.getGroupID());                  
            proUser.setGroupHierarchy(new GroupHierarchy(groupList));
            
            List<Zone> zoneList = zoneDAO.getZones(user.getPerson().getAcctID());
            proUser.setZones(zoneList);

            proUser.setAccountAttributes(account.getProps());
            proUser.setAccountHOSType(account.getHos());
            
            Driver unknownDriver = driverDAO.findByID(account.getUnkDriverID());
            unknownDriver.getPerson().setDriver(unknownDriver);
            proUser.setUnknownDriver(unknownDriver);
            
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null) {
                user.setLastLogin(new Date());
                userDAO.update(user);
            }
            
            return proUser;
        }
        catch (EmptyResultSetException ex)
        {
            throw new UsernameNotFoundException("Username could not be found");
        }
    }

    private List<Group> getUserGroupList(List<Group> accountGroupList, Integer groupID) {
        List <Group> groupList = new ArrayList<Group>();
        for (Group group : accountGroupList)
        {
            if (group.getGroupID().equals(groupID))
            {
                groupList.add(group);
                addChildren(accountGroupList, groupList, group.getGroupID());
                break;
            }
        }

        return groupList;
    }
    
    private void addChildren(List<Group> allGroups , List<Group> groupList, Integer parentID)
    {
        for (Group group : allGroups)
        {
            if (group.getParentID().equals(parentID))
            {
                groupList.add(group);
                addChildren(allGroups, groupList, group.getGroupID());
            }
        }
        
    }


    private User lookup(String username)
    {
        logger.debug("lookup: " + username);
        
        User user = userDAO.findByUserName(username);
        return user;
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

    public ZoneDAO getZoneDAO() {
        return zoneDAO;
    }

    public AccountDAO getAccountDAO() {
		return accountDAO;
	}

	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	public void setZoneDAO(ZoneDAO zoneDAO) {
        this.zoneDAO = zoneDAO;
    }

	public RoleDAO getRoleDAO(){ 
		return roleDAO;
	}

	public void setRoleDAO(RoleDAO roleDAO) {
		this.roleDAO = roleDAO;
	}
    public DriverDAO getDriverDAO() {
		return driverDAO;
	}

	public void setDriverDAO(DriverDAO driverDAO) {
		this.driverDAO = driverDAO;
	}

    private GrantedAuthority[] getGrantedAuthorities(User user, boolean isAdmin, boolean isAccountHOS){
		
		//TODO make an enum for all role related things
		
		String adminAccessPointsArray[]
		  = {"usersAccess",
			"vehiclesAccess",
			"devicesAccess",
			"zonesAccess",
			"zoneAlertsAccess",
			"redFlagsAccess",
			"reportsAccess",
			"organizationAccess",
			"speedByStreetAccess"};
		List<String> adminPoints = new ArrayList<String>();
		adminPoints.addAll(Arrays.asList(adminAccessPointsArray));
        
        List<GrantedAuthorityImpl> grantedAuthoritiesList = new ArrayList<GrantedAuthorityImpl>();		

		// this will take into account the site access points instead of the original roles as follows
		if(isAdmin){
			
			//Will cover all access points
			grantedAuthoritiesList.add(new GrantedAuthorityImpl("ROLE_ADMIN"));
			if (isAccountHOS)
	            grantedAuthoritiesList.add(new GrantedAuthorityImpl("ROLE_HOSADMIN"));
			
		}
		else if (!user.getAccessPoints().isEmpty()){
			
			boolean isAdminSubset = false;
			
			for(AccessPoint ap: SiteAccessPoints.getAccessPoints()){
				
				if(user.getAccessPoints().contains(ap)){
					
					if (adminPoints.contains(SiteAccessPoints.getAccessPointById(ap.getAccessPtID()).getMsgKey())){
						
						isAdminSubset = true;
					}
					grantedAuthoritiesList.add(new GrantedAuthorityImpl(SiteAccessPoints.getAccessPointById(ap.getAccessPtID()).toString()));
				}
			}
			if(isAdminSubset){
				
				grantedAuthoritiesList.add(new GrantedAuthorityImpl("ROLE_ADMIN_SUBSET"));
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

		List<Integer> userRoles = user.getRoles();

		for(Integer id:userRoles){
			
			if (roles.getRoleById(id).getName().equals("Admin")){
				
				return true;
			}
		}
		return false;
	}
}
