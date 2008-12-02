package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.Column;
import com.inthinc.pro.dao.annotations.ID;

public class User extends BaseEntity
{
    @ID
    private Integer userID;

    private Integer personID;
    
    @Column(updateable = false)
    private Person  person;
    
    @Column(name = "roleID")
    private Role    role;
    
    private UserStatus status;
    
    private String  username;
    private String  password;
    
    public User(Integer userID, Integer personID, Role role, UserStatus status, String username, String password)
    {
        super();
        this.userID = userID;
        this.personID = personID;
        this.role = role;
        this.status = status;
        this.username = username;
        this.password = password;
    }

    public User()
    {
        super();
    }

    public Integer getUserID()
    {
        return userID;
    }

    public void setUserID(Integer userID)
    {
        this.userID = userID;
    }

    public Person getPerson()
    {
        return person;
    }

    public void setPerson(Person person)
    {
        this.person = person;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Role getRole()
    {
        return role;
    }

    public void setRole(Role role)
    {
        this.role = role;
    }

    public Boolean getActive()
    {
        return status.equals(UserStatus.ACTIVE);
    }

    public UserStatus getStatus()
    {
        return status;
    }

    public void setStatus(UserStatus status)
    {
        this.status = status;
    }

    public Integer getPersonID()
    {
        return personID;
    }

    public void setPersonID(Integer personID)
    {
        this.personID = personID;
    }

}
