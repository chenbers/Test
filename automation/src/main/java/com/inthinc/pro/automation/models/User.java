package com.inthinc.pro.automation.models;

import java.util.Collections;
import java.util.List;

import org.jasypt.util.password.StrongPasswordEncryptor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.inthinc.pro.automation.enums.WebDateFormat;
import com.inthinc.pro.automation.objects.AutomationCalendar;

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
    
    @JsonIgnore
    private final AutomationCalendar lastLogin = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);
    
    @JsonIgnore
    private final AutomationCalendar passwordDT = new AutomationCalendar(WebDateFormat.RALLY_DATE_FORMAT);

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

    @JsonProperty("password")
    public String getEncryptedPassword() {
        return encrypted;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        encrypted = encryptor.encryptPassword(password);
        this.password = password;
    }

    @JsonProperty("password")
    public void setEncryptedPassword(String encrypted) {
        this.password = null;
        this.encrypted = encrypted;
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
        return "User [groupID=" + groupID + ", password=" + password + ", personID=" + personID + ", role=" + roles == null ? null : roles.toString() + ", status=" + status + ", userID=" + userID
                + ", username=" + username + "]";
    }

    public Roles getRoles() {
        return roles;
    }

    public void setRoles(Roles roles) {
        this.roles = roles;
    }

    public List<AccessPoint> getAccessPoints() {
        if (accessPoints == null)
            return Collections.emptyList();
        return accessPoints;
    }

    public void setAccessPoints(List<AccessPoint> accessPoints) {
        Collections.sort(accessPoints);
        this.accessPoints = accessPoints;
    }

    @JsonProperty("lastLogin")
    public void setLastLogin(String lastLogin) {
        this.lastLogin.setDate(lastLogin);
    }

    public AutomationCalendar getLastLogin() {
        return lastLogin;
    }

    @JsonProperty("passwordDT")
    public void setPasswordDT(String passwordDT) {
        this.passwordDT.setDate(passwordDT);
    }

    public AutomationCalendar getPasswordDT() {
        return passwordDT;
    }

    public boolean hasRoles() {
        return roles != null;
    }

    public GoogleMapType getMapType() {
        return mapType;
    }

    public void setMapType(GoogleMapType mapType) {
        this.mapType = mapType;
    }

    public boolean doesPasswordMatch(String testPassword) {
        return encryptor.checkPassword(testPassword, encrypted);
    }
}
