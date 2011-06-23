package com.inthinc.pro.service.security;

import org.springframework.security.context.SecurityContextHolder;

import com.inthinc.pro.dao.SuperuserDAO;
import com.inthinc.pro.model.GroupHierarchy;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;

/**
 * Convenience class to access the User and Principal information.
 * 
 * @see com.inthinc.pro.security.userdetails.ProUser
 * 
 * @author dcueva
 */
//@Component
public class TiwiproPrincipal {

    /*
     * TODO For dev purposes only. Remove once implementation in place.
     * User TEST_622 has been choosen arbitrarily as a DEV admin --> needs to be fixed.
     */
//    public static final String ADMIN_BACKDOOR_USERNAME = "TEST_622";

    private SuperuserDAO superuserDAO;
    private Boolean isSuperUser;
    /**
     * The ProUser getter.
     * 
     * @return the ProUser
     */
    private ProUser getProUser() {
        return (ProUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * The User getter.
     * 
     * @return the current User
     */
    public User getUser() {
        return getProUser().getUser();
    }

    /**
     * The Person getter.
     * 
     * @return the Person
     */
    public Person getPerson() {
        return getUser().getPerson();
    }

    /**
     * The GroupID getter.
     * 
     * @return the GroupID
     */
    public Integer getGroupID() {
        return getUser().getGroupID();
    }

    /**
     * The AccountID getter.
     * 
     * @return the AccountID
     */
    public Integer getAccountID() {
        return getUser().getPerson().getAcctID();
    }

    /**
     * The getter for Inthinc user flag.
     * 
     * @return true if the user is InthincUser
     */
    public boolean isInthincUser() {
        // TODO: Remove backdoor once implemented,
        // need to determine how to allow for an inthinc role at a later time
        // We could use superUser?
        if (isSuperUser == null){
            isSuperUser = superuserDAO.isSuperuser(getUser().getUserID());
        }
        return isSuperUser;
//        return getUser().getUsername().equalsIgnoreCase(ADMIN_BACKDOOR_USERNAME);
    }

    public GroupHierarchy getAccountGroupHierarchy() {
        return getProUser().getAccountGroupHierarchy();
    }

    public void setSuperuserDAO(SuperuserDAO superuserDAO) {
        this.superuserDAO = superuserDAO;
    }

}
