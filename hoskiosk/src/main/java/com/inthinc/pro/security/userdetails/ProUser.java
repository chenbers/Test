package com.inthinc.pro.security.userdetails;

import org.springframework.security.GrantedAuthority;

import com.inthinc.pro.model.Driver;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Status;

public class ProUser extends org.springframework.security.userdetails.User
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -8015069498032652168L;

//    private static final Logger logger = Logger.getLogger(ProUser.class);

    private Driver driver;
    private GroupHierarchy accountGroupHierarchy;
  
    public ProUser(Driver driver, GrantedAuthority[] grantedAuthorities) throws IllegalArgumentException
    {
        super(  driver.getPerson().getEmpid(),
                driver.getPerson().getLast(),
                driver.getStatus().equals(Status.ACTIVE), // boolean enabled,
                true, // boolean accountNonExpired,
                true, // boolean credentialsNonExpired
                true, // boolean accountNonLocked,
                grantedAuthorities);
        
        this.driver = driver;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public GroupHierarchy getAccountGroupHierarchy() {
        return accountGroupHierarchy;
    }

    public void setAccountGroupHierarchy(GroupHierarchy accountGroupHierarchy) {
        this.accountGroupHierarchy = accountGroupHierarchy;
    }

}
