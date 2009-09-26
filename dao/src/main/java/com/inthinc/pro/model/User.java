package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

import javax.xml.bind.annotation.XmlRootElement;

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
    @Column(name = "roleID")
    private Role role;
    private Status status;
    private String username;
    private String password;
    private Integer groupID;

    public User(Integer userID, Integer personID, Role role, Status status, String username, String password, Integer groupID) {
        super();
        this.userID = userID;
        this.personID = personID;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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
        return "User [groupID=" + groupID + ", password=" + password + ", personID=" + personID + ", role=" + role + ", status=" + status + ", userID=" + userID + ", username="
                + username + "]";
    }
}
