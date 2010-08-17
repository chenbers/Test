package com.inthinc.pro.security.userdetails;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.GrantedAuthority;

import com.inthinc.pro.backing.model.GroupHierarchy;
import com.inthinc.pro.model.AccountAttributes;
import com.inthinc.pro.model.AccountHOSType;
import com.inthinc.pro.model.Driver;
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
    private List<Zone>     zones;
    private Driver	unknownDriver;
    private boolean isAdmin;
    private AccountAttributes accountAttributes;
    private AccountHOSType accountHOSType;
  
    public ProUser(User user, GrantedAuthority[] grantedAuthorities)
    {
        super(  user.getUsername(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE), // boolean enabled,
                true, // boolean accountNonExpired,
                true, // boolean credentialsNonExpired
                true, // boolean accountNonLocked,
                grantedAuthorities);
        
        this.user = user;
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

}
