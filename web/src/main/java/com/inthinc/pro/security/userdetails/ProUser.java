package com.inthinc.pro.security.userdetails;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;

import com.inthinc.pro.backing.model.GroupHierarchy;
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
    
    public ProUser(User user, List<String> roles)
    {
        super(  user.getUsername(),
                user.getPassword(),
                user.getStatus().equals(Status.ACTIVE), // boolean enabled,
                true, // boolean accountNonExpired,
                true, // boolean credentialsNonExpired
                true, // boolean accountNonLocked,
                getAuthorities(roles));
        
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
    private static GrantedAuthority[] getAuthorities(List<String> roles){
		List<GrantedAuthorityImpl> grantedAuthoritiesList = new ArrayList<GrantedAuthorityImpl>();
	 	for (String role:roles){
	 		
	 		grantedAuthoritiesList.add(new GrantedAuthorityImpl(role));
	 	}
	 	GrantedAuthority[] grantedAuthorities = new GrantedAuthorityImpl[grantedAuthoritiesList.size()];
	 	return grantedAuthoritiesList.toArray(grantedAuthorities);
    }
    public List<Zone> getZones() {
        return zones;
    }

    public void setZones(List<Zone> zones) {
        this.zones = zones;
    }

}
