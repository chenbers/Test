package com.inthinc.pro.model;

import java.util.Date;
import java.util.TimeZone;

import com.inthinc.pro.dao.annotations.ID;

public class Person extends BaseEntity
{
    @ID
    private Integer  personID;
    private Integer  accountID;
    private Integer  groupID;
    private TimeZone timeZone;
    // contact information
    private Address  address;
    private String   homePhone;
    private String   workPhone;
    private String   email;
    // employee information
    private Integer  reportsTo; // userID
    private String   title;
    private String   dept;
    // personal information
    private String   first;
    private String   middle;
    private String   last;
    private String   suffix;
    private Gender   gender;
    private Integer  height;   // inches
    private Integer  weight;   // pounds
    private Date     dob;
    // user, driver (may be null)
    private User     user;
    private Driver   driver;

    public Integer getPersonID()
    {
        return personID;
    }

    public void setPersonID(Integer personID)
    {
        this.personID = personID;
    }

    public Integer getAccountID()
    {
        return accountID;
    }

    public void setAccountID(Integer accountID)
    {
        this.accountID = accountID;
    }

    public Integer getGroupID()
    {
        return groupID;
    }

    public void setGroupID(Integer groupID)
    {
        this.groupID = groupID;
    }

    public TimeZone getTimeZone()
    {
        return timeZone;
    }

    public void setTimeZone(TimeZone timeZone)
    {
        this.timeZone = timeZone;
    }

    public Address getAddress()
    {
        return address;
    }

    public void setAddress(Address address)
    {
        this.address = address;
    }

    public String getHomePhone()
    {
        return homePhone;
    }

    public void setHomePhone(String homePhone)
    {
        this.homePhone = homePhone;
    }

    public String getWorkPhone()
    {
        return workPhone;
    }

    public void setWorkPhone(String workPhone)
    {
        this.workPhone = workPhone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public Integer getReportsTo()
    {
        return reportsTo;
    }

    public void setReportsTo(Integer reportsTo)
    {
        this.reportsTo = reportsTo;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDept()
    {
        return dept;
    }

    public void setDept(String dept)
    {
        this.dept = dept;
    }

    public String getFirst()
    {
        return first;
    }

    public void setFirst(String first)
    {
        this.first = first;
    }

    public String getMiddle()
    {
        return middle;
    }

    public void setMiddle(String middle)
    {
        this.middle = middle;
    }

    public String getLast()
    {
        return last;
    }

    public void setLast(String last)
    {
        this.last = last;
    }

    public String getSuffix()
    {
        return suffix;
    }

    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }

    public Gender getGender()
    {
        return gender;
    }

    public void setGender(Gender gender)
    {
        this.gender = gender;
    }

    public Integer getHeight()
    {
        return height;
    }

    public void setHeight(Integer height)
    {
        this.height = height;
    }

    public Integer getWeight()
    {
        return weight;
    }

    public void setWeight(Integer weight)
    {
        this.weight = weight;
    }

    public Date getDob()
    {
        return dob;
    }

    public void setDob(Date dob)
    {
        this.dob = dob;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Driver getDriver()
    {
        return driver;
    }

    public void setDriver(Driver driver)
    {
        this.driver = driver;
    }
}
