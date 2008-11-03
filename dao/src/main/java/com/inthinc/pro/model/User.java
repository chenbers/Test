package com.inthinc.pro.model;

import com.inthinc.pro.dao.annotations.ID;

public class User extends BaseEntity
{
    @ID
    private Integer userID;
    private Person  person;
    private Role    role;
    private Boolean active;
    private String  username;
    private String  password;

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
        return active;
    }

    public void setActive(Boolean active)
    {
        this.active = active;
    }
}
