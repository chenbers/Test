package com.inthinc.pro.service.security;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.security.userdetails.ProUser;

@Component
public class TiwiproPrincipal {

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
        return true;// getUser().getUsername().equalsIgnoreCase("admin");
    }
}
