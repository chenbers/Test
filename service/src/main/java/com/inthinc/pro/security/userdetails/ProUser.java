package com.inthinc.pro.security.userdetails;

import org.apache.log4j.Logger;
import org.springframework.security.GrantedAuthority;

import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;

public class ProUser extends org.springframework.security.userdetails.User
{
    
    private static final long serialVersionUID = -8015069498032652168L;

    private static final Logger logger = Logger.getLogger(ProUser.class);

    private User user;
    private GroupHierarchy accountGroupHierarchy;
    
    public ProUser(User user, GrantedAuthority[] authorities)
    {
        this(   user.getUsername(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE), // boolean enabled,
                true, // boolean accountNonExpired,
                true, // boolean credentialsNonExpired
                true, // boolean accountNonLocked,
                authorities);
        
        this.user = user;
    }

    public ProUser(String username, String password, boolean enabled, boolean accountNonExpired,
            boolean credentialsNonExpired, boolean accountNonLocked, GrantedAuthority[] authorities)
            throws IllegalArgumentException
    {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

	/**
	 * @return the accountGroupHierarchy
	 */
	public GroupHierarchy getAccountGroupHierarchy() {
		return accountGroupHierarchy;
	}

	/**
	 * @param accountGroupHierarchy the accountGroupHierarchy to set
	 */
	public void setAccountGroupHierarchy(GroupHierarchy accountGroupHierarchy) {
		this.accountGroupHierarchy = accountGroupHierarchy;
	}


    
}
