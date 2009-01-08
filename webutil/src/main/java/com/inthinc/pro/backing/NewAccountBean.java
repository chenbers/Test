package com.inthinc.pro.backing;


import java.util.Date;
import java.util.Map;
import java.util.TimeZone;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmailException;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateUsernameException;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Role;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.app.Roles;

public class NewAccountBean
{
    
    // gathered from UI
    private String email;
    private String username;
    
    private String errorMsg;
    private String successMsg;
    
    private AccountDAO accountDAO;
    private PersonDAO personDAO;
    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private RoleDAO roleDAO;

    private static final String PASSWORD="nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    public void init()
    {
        Roles roles = new Roles();
        roles.setRoleDAO(roleDAO);
        roles.init();

    }
    
    public void createAction()
    {
        
        setErrorMsg(null);
        
        Account account = new Account(null, null, null, Status.ACTIVE);
        
        // create an account
        Integer acctID = accountDAO.create(account);

        // create the account's top level group
        Group topGroup = new Group(0, acctID, "Top", 0, GroupType.FLEET, 0, "Initial top level group", 5, new LatLng(0.0, 0.0));
        Integer groupID = groupDAO.create(acctID, topGroup);
        
        // create the person record for the superuser
        Person person = new Person(new Integer(0),acctID,  TimeZone.getDefault(), null, null, "5555555555", "5555555555", 
                        email, "0", null, "title", "dept",
                        "first", "m", "last", "jr", Gender.FEMALE, 65, 180, new Date(), Status.ACTIVE);
        Integer personID = null;
        try
        {
            personID = personDAO.create(groupID, person);
            person.setPersonID(personID);
        }
        catch (DuplicateEmailException ex)
        {
            groupDAO.deleteByID(groupID);
            accountDAO.deleteByID(acctID);
            setErrorMsg("Duplicate email: [" + email + "]");
            
            return;
        }
        
        // create the superuser
        User user = new User(0, person.getPersonID(), getSuperUserRole(), Status.ACTIVE, getUsername(), PASSWORD, groupID);
        Integer userID = null;
        try
        {
            userID = userDAO.create(personID, user);
            user.setUserID(userID);
        }
        catch (DuplicateUsernameException ex)
        {
//            personDAO.deleteByID(personID);
            groupDAO.deleteByID(groupID);
            accountDAO.deleteByID(acctID);
            setErrorMsg("Duplicate username: [" + username + "]");
            return;
        }
        
        setSuccessMsg("Successful Account Creation<br/><ul><li>AccountID [" + acctID + "]</li><li>GroupID [" + groupID + "]</li><li>PersonID [" + personID + "]</li><li>UserID [" + userID + "]</li></ul>");

    }
    public void clearErrorAction()
    {
        setErrorMsg(null);
        setSuccessMsg(null);
    }
    public void reInitAction()
    {
        setErrorMsg(null);
        setSuccessMsg(null);
        setUsername(null);
        setEmail(null);
    }

    private Role getSuperUserRole()
    {
        Map<Integer, Role> roles = Roles.getRoles();
        for (Role role : roles.values())
        {
            if (role.getName().toUpperCase().startsWith("SUPERUSER"))
                return role;
        }
        return null;
    }

    public String getEmail()
    {
        return email;
    }



    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public AccountDAO getAccountDAO()
    {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    public PersonDAO getPersonDAO()
    {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }

    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO)
    {
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

    public RoleDAO getRoleDAO()
    {
        return roleDAO;
    }

    public void setRoleDAO(RoleDAO roleDAO)
    {
        this.roleDAO = roleDAO;
    }

    public String getErrorMsg()
    {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg)
    {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg()
    {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg)
    {
        this.successMsg = successMsg;
    }

}
