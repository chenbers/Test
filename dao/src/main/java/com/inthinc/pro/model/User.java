package com.inthinc.pro.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;
import com.inthinc.pro.model.security.AccessPoint;

@XmlRootElement
public class User extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    @ID
    private Integer userID;
    private Integer personID;
    @Column(updateable = false)
    private Person person;
    private List<Integer> roles;
    private List<AccessPoint> accessPoints;
    private Status status;
    private String username;
    private String password;
    private Integer groupID;
    private Date lastLogin;
    private Date passwordDT;

    public User(Integer userID, Integer personID, List<Integer> roles, Status status, String username, String password, Integer groupID) {
        super();
        this.userID = userID;
        this.personID = personID;
        this.roles = roles;
        this.status = status;
        this.username = username;
        this.password = password;
        this.groupID = groupID;
    }

    public User() {
        super();
        roles = new ArrayList<Integer>();
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    @XmlTransient //Prevent Circular Reference on XML rendering 
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getPersonID() {
        if (personID == null && person != null) {
            setPersonID(person.getPersonID());
        }
        return personID;
    }

    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    public Integer getGroupID() {
        return groupID;
    }

    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    @Override
    public String toString() {
        return "User [groupID=" + groupID + ", password=" + password + ", personID=" + personID + ", role=" + roles.toString() + ", status=" + status + ", userID=" + userID + ", username="
                + username + "]";
    }

	public List<Integer> getRoles() {
		return roles;
	}

	public void setRoles(List<Integer> roles) {
		this.roles = roles;
	}

	public List<AccessPoint> getAccessPoints() {
		return accessPoints;
	}

	public void setAccessPoints(List<AccessPoint> accessPoints) {
		this.accessPoints = accessPoints;
	}

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setPasswordDT(Date passwordDT) {
        this.passwordDT = passwordDT;
    }

    public Date getPasswordDT() {
        return passwordDT;
    }
}
