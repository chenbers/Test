package com.inthinc.pro.backing;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.GroupDAO;
import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.RoleDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.hessian.RoleHessianDAO;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateEmailException;
import com.inthinc.pro.dao.hessian.exceptions.DuplicateUsernameException;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.Address;
import com.inthinc.pro.model.FuelEfficiencyType;
import com.inthinc.pro.model.Gender;
import com.inthinc.pro.model.Group;
import com.inthinc.pro.model.GroupType;
import com.inthinc.pro.model.LatLng;
import com.inthinc.pro.model.MeasurementType;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.Status;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.app.SiteAccessPoints;
import com.inthinc.pro.model.security.Role;

public class NewAccountBean {
    // gathered from UI
    private String accountName;
    private String email;
    private String username;
    private String errorMsg;
    private String successMsg;
    private AccountDAO accountDAO;
    private PersonDAO personDAO;
    private UserDAO userDAO;
    private GroupDAO groupDAO;
    private RoleDAO roleDAO;
    private static final String PASSWORD = "nuN5q/jdjEpJKKA4A6jLTZufWZfIXtxqzjVjifqFjbGg6tfmQFGXbTtcXtEIg4Z7"; // password

    public void init() {
        SiteAccessPoints accessPoints = new SiteAccessPoints();
        accessPoints.setRoleDAO(roleDAO);
        accessPoints.init();
    }

    public void createAction() {
        setErrorMsg(null);
        Account account = new Account(null, getAccountName(), Status.ACTIVE);
        // create an account
        Integer acctID = accountDAO.create(account);
        // create the account's top level group
        Group topGroup = new Group(0, acctID, "Top", 0, GroupType.FLEET, null, "Initial top level group", 5, new LatLng(0.0, 0.0));
        Integer groupID = groupDAO.create(acctID, topGroup);
        // create the person record for the superuser
        Person person = new Person(new Integer(0), acctID, TimeZone.getDefault(), null, email, null, "5555555555", "5555555555", null, null, null, null, null, "0", null,
                "title", "dept", "first", "m", "last", "jr", Gender.FEMALE, 65, 180, new Date(), Status.ACTIVE, MeasurementType.ENGLISH, FuelEfficiencyType.MPG_US, Locale
                        .getDefault());
        person.setAddress(new Address(null, "", null, "", null, "", acctID));
        Integer personID = null;
        try {
            personID = personDAO.create(acctID, person);
            person.setPersonID(personID);
        }
        catch (DuplicateEmailException ex) {
            groupDAO.deleteByID(groupID);
            accountDAO.deleteByID(acctID);
            setErrorMsg("Duplicate email: [" + email + "]");
            return;
        }
        // create the superuser
        // TODO: not sure if this is correct for roles, but added to get it to compile
        User user = new User(0, person.getPersonID(), getAccountDefaultRoles(acctID),/*getSuperUserRole(), */Status.ACTIVE, getUsername(), PASSWORD, groupID);
        Integer userID = null;
        try {
            userID = userDAO.create(personID, user);
            user.setUserID(userID);
        }
        catch (DuplicateUsernameException ex) {
            // personDAO.deleteByID(personID);
            groupDAO.deleteByID(groupID);
            accountDAO.deleteByID(acctID);
            setErrorMsg("Duplicate username: [" + username + "]");
            return;
        }
        setSuccessMsg("Successful Account Creation<br/><ul><li>AccountID [" + acctID + "]</li><li>Account Name [" + accountName + "]</li><li>GroupID [" + groupID
                + "]</li><li>PersonID [" + personID + "]</li><li>UserID [" + userID + "]</li></ul>");
    }

    public void clearErrorAction() {
        setErrorMsg(null);
        setSuccessMsg(null);
    }

    public void reInitAction() {
        setErrorMsg(null);
        setSuccessMsg(null);
        setAccountName(null);
        setUsername(null);
        setEmail(null);
    }
    private List<Integer> getAccountDefaultRoles(Integer acctID)
    {
		List<Role> roles = roleDAO.getRoles(acctID);
		List<Integer> roleIDs = new ArrayList<Integer>();
		for (Role role : roles)
			roleIDs.add(role.getRoleID());
		return roleIDs;
	
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public GroupDAO getGroupDAO() {
        return groupDAO;
    }

    public void setGroupDAO(GroupDAO groupDAO) {
        this.groupDAO = groupDAO;
    }

    public RoleDAO getRoleDAO() {
        return roleDAO;
    }

    public void setRoleDAO(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getSuccessMsg() {
        return successMsg;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }
}
