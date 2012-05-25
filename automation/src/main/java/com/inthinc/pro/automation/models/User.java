package com.inthinc.pro.automation.models;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.jasypt.util.password.StrongPasswordEncryptor;

public class User extends BaseEntity {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private static final StrongPasswordEncryptor encryptor = new StrongPasswordEncryptor();
    
    private Integer userID;
    private Integer personID;
    private Person person;
    private Roles roles;
    private List<AccessPoint> accessPoints;
    private Status status;
    private String username;
    private String password;
    @JsonIgnore
    private String encrypted;
    private Integer groupID;
    private Date lastLogin;
    private Date passwordDT;

    private GoogleMapType mapType;

    public User(Integer userID, Integer personID, List<Integer> roles, Status status, String username, String password, Integer groupID) {
        super();
        this.userID = userID;
        this.personID = personID;
        this.roles = new Roles(roles);
        this.status = status;
        this.username = username;
        this.password = password;
        this.groupID = groupID;
    }

    public User() {
        super();
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

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

    @JsonIgnore
    public String getPlainTextPassword(){
        return password;
    }
    
    /**
     * This method does not return the normal password<br />
     * This is to accomodate interacting with the Services<br />
     * API
     * @return
     */
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() < 16) {
            encodePassword();
        } else {
            this.password = password;
        }
    }

    @JsonIgnore
    public void encodePassword(){
        encrypted = encryptor.encryptPassword(password);
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
        return "User [groupID=" + groupID + ", password=" + password + ", personID=" + personID + ", role=" + roles==null?null:roles.toString() + ", status=" + status + ", userID=" + userID + ", username="
                + username + "]";
    }

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}

	public List<AccessPoint> getAccessPoints() {
	    if(accessPoints == null)
	        return Collections.emptyList();
		return accessPoints;
	}

	public void setAccessPoints(List<AccessPoint> accessPoints) {
	    Collections.sort(accessPoints);
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
    public boolean hasRoles(){
    	return roles != null;
    }
    

    public GoogleMapType getMapType() {
            return mapType;
    }

    public void setMapType(GoogleMapType mapType) {
        this.mapType = mapType;
    }
}
