package com.inthinc.pro.security.userdetails;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.GrantedAuthority;

import com.inthinc.pro.backing.PersonBean;
import com.inthinc.pro.backing.UpdateCredentialsBean;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.AccountAttributes;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.Zone;

public class ProUser extends org.springframework.security.userdetails.User
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8015069498032652168L;

    private static final Logger logger = Logger.getLogger(ProUser.class);

    private User user;
    private GroupHierarchy groupHierarchy;
    private GroupHierarchy accountGroupHierarchy;
    private List<Zone>     zones;
    private Driver	unknownDriver;
    private boolean isAdmin;
    private AccountAttributes accountAttributes;
    private AccountHOSType accountHOSType;
    private Date previousLogin;
    
    public ProUser(User user, boolean loginNonExpired, boolean passwordNonExpired, GrantedAuthority[] grantedAuthorities)
    { 
        super(  user.getUsername(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE), // boolean enabled,
                loginNonExpired, // boolean accountNonExpired,
                passwordNonExpired, // boolean credentialsNonExpired // password expired
                true, // boolean accountNonLocked,
                grantedAuthorities);
        
        this.user = user;
        if(user != null && user.getLastLogin() != null) {
            this.previousLogin = new Date(user.getLastLogin().getTime()); //defensive copy
        }
    }

//    public ProUser(String username, String password, boolean enabled, boolean accountNonExpired,
//            boolean credentialsNonExpired, boolean accountNonLocked, GrantedAuthority[] authorities)
//            throws IllegalArgumentException
//    {
//        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
//    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public GroupHierarchy getGroupHierarchy()
    {
        return groupHierarchy;
    }

    public void setGroupHierarchy(GroupHierarchy groupHierarchy)
    {
        this.groupHierarchy = groupHierarchy;
    }

    public GroupHierarchy getAccountGroupHierarchy() {
        return accountGroupHierarchy;
    }

    public void setAccountGroupHierarchy(GroupHierarchy accountGroupHierarchy) {
        this.accountGroupHierarchy = accountGroupHierarchy;
    }

    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

    public Driver getUnknownDriver() {
		return unknownDriver;
	}

	public void setUnknownDriver(Driver unknownDriver) {
		this.unknownDriver = unknownDriver;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

    public AccountAttributes getAccountAttributes() {
        return accountAttributes;
    }

    public void setAccountAttributes(AccountAttributes accountAttributes) {
        this.accountAttributes = accountAttributes;
    }

    public AccountHOSType getAccountHOSType() {
        return accountHOSType;
    }

    public void setAccountHOSType(AccountHOSType accountHOSType) {
        this.accountHOSType = accountHOSType;
    }

    public Date getPreviousLogin() {
        return previousLogin;
    }

    public void setPreviousLogin(Date previousLogin) {
        this.previousLogin = previousLogin;
    }

}
